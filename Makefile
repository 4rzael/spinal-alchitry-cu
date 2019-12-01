verilog:
	sbt run && mv CuTop.v CuTop.v

yosys:
	yosys -p "synth_ice40 -json build/firmware.json" *.v

pnr:
	nextpnr-ice40 --hx8k --json build/firmware.json --asc build/firmware.asc --package cb132 --pcf constraints/*.pcf --freq 100

bin:
	icepack build/firmware.asc build/firmware.bin

icestorm: yosys pnr bin

upload:
	iceprog build/firmware.bin

update: icestorm upload

all: verilog icestorm upload
