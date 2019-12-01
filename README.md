# Alchitry Cu with Spinal HDL template

A template project to use Spinal HDL with an Alchitry Cu (With support for the Alchitry IO board)

## Requirements

You will need:
* A linux machine (tested on Manjaro)
* An ICE40 open toolchain:
  * Yosys
  * Nextpnr
  * Icestom (icepack + iceprog)
* Java JDK 8+ (tested on 13.0.1)
* Scala
* sbt
* The `make` make

Also, optionally:
* Alchitry Labs, if you want to retrieve additional PCF files (for the IO board for example)
* If you have problems with the last step (upload), it can be fixed by using apio to install the FTDI drivers

## Installation

* Install the toolchain:
```sh
git clone https://github.com/cliffordwolf/icestorm.git icestorm
cd icestorm
make -j$(nproc)
sudo make install
cd ..

git clone https://github.com/YosysHQ/nextpnr.git nextpnr
cd nextpnr
cmake -DARCH=ice40 .
make -j$(nproc)
sudo make install
cd ..

git clone https://github.com/YosysHQ/yosys.git yosys
cd yosys
make -j$(nproc)
sudo make install
cd ..
```

* Install Java and Scala (I used `yay` on Archlinux)
* Clone this repo

## Programming the FPGA

By default, the code is in `src/main/scala/mylib/MyTopLevel.scala`. You can edit it however you want using a text editor

Then we need to compile it to verilog. To do so, there are two ways:
* Fast way: run the command `sbt`, keep it open, and type `run` everytime you want to recompile
* Slow way: `make verilog` (it restarts `sbt` everytime, which is slow)

We then need to generate a `.bin` file. To do so, run `make icestorm`

Finally, we need to flash the FPGA. To do so, run `make upload`

##### TIPS:
* `make update` combines `make icestorm` and `make upload`
* `make all` combines `make verilog`, `make icestorm` and `make upload`
