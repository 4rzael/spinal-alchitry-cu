/*
 * SpinalHDL
 * Copyright (c) Dolu, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package mylib

import spinal.core._
import spinal.lib._

import scala.util.Random

case class LedBinaryCounter(val ledCount: Int) extends Component {
  val io = new Bundle {
    val leds = out Bits(ledCount bits)
  }
  val cnt = Reg(U(0, ledCount bits))
  cnt := cnt + 1
  io.leds := B(cnt)
}

//Hardware definition
class CuTop extends Component {
  val io = new Bundle {
    val leds = out Bits(8 bits)
  }

  val areaVerySlow = new SlowArea(1024 * 1024){
    val counter = LedBinaryCounter(8)
    io.leds := counter.io.leds
  }
}

//Generate the MyTopLevel's Verilog using the above custom configuration.
object MyTopLevelVerilogWithCustomConfig {
  def main(args: Array[String]) {
    new SpinalConfig(
    defaultClockDomainFrequency = FixedFrequency(100 MHz),
    defaultConfigForClockDomains = ClockDomainConfig(resetKind=SYNC, resetActiveLevel=LOW)
  ).generateVerilog(new CuTop)
  }
}