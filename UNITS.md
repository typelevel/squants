# Squants - Supported Dimensions and Units
## Index

|Package|Dimensions (# of Units)|
|----------------------------|-----------------------------------------------------------|
|squants|[Dimensionless](#dimensionless) (5)|
|squants.electro|[AreaElectricChargeDensity](#areaelectricchargedensity) (1), [Capacitance](#capacitance) (6), [Conductivity](#conductivity) (1), [ElectricCharge](#electriccharge) (9), [ElectricChargeDensity](#electricchargedensity) (1), [ElectricChargeMassRatio](#electricchargemassratio) (1), [ElectricCurrent](#electriccurrent) (2), [ElectricCurrentDensity](#electriccurrentdensity) (1), [ElectricFieldStrength](#electricfieldstrength) (1), [ElectricPotential](#electricpotential) (5), [ElectricalConductance](#electricalconductance) (1), [ElectricalResistance](#electricalresistance) (7), [Inductance](#inductance) (5), [LinearElectricChargeDensity](#linearelectricchargedensity) (1), [MagneticFlux](#magneticflux) (1), [MagneticFluxDensity](#magneticfluxdensity) (2), [Permeability](#permeability) (2), [Permittivity](#permittivity) (1), [Resistivity](#resistivity) (1)|
|squants.energy|[Energy](#energy) (26), [EnergyDensity](#energydensity) (1), [MolarEnergy](#molarenergy) (1), [Power](#power) (8), [PowerDensity](#powerdensity) (1), [PowerRamp](#powerramp) (6), [SpecificEnergy](#specificenergy) (3)|
|squants.information|[DataRate](#datarate) (34), [Information](#information) (34)|
|squants.mass|[AreaDensity](#areadensity) (4), [ChemicalAmount](#chemicalamount) (2), [Density](#density) (21), [Mass](#mass) (27), [MomentOfInertia](#momentofinertia) (2)|
|squants.motion|[Acceleration](#acceleration) (5), [AngularAcceleration](#angularacceleration) (6), [AngularVelocity](#angularvelocity) (4), [Force](#force) (5), [Jerk](#jerk) (2), [MassFlow](#massflow) (5), [Momentum](#momentum) (1), [Pressure](#pressure) (7), [PressureChange](#pressurechange) (4), [Torque](#torque) (2), [VolumeFlow](#volumeflow) (22), [Yank](#yank) (1)|
|squants.photo|[Illuminance](#illuminance) (1), [Luminance](#luminance) (1), [LuminousEnergy](#luminousenergy) (1), [LuminousExposure](#luminousexposure) (1), [LuminousFlux](#luminousflux) (1), [LuminousIntensity](#luminousintensity) (1)|
|squants.radio|[Activity](#activity) (3), [AreaTime](#areatime) (2), [Dose](#dose) (2), [Irradiance](#irradiance) (2), [ParticleFlux](#particleflux) (2), [Radiance](#radiance) (1), [RadiantIntensity](#radiantintensity) (1), [SpectralIntensity](#spectralintensity) (1), [SpectralIrradiance](#spectralirradiance) (4), [SpectralPower](#spectralpower) (1)|
|squants.space|[Angle](#angle) (6), [Area](#area) (10), [Length](#length) (32), [SolidAngle](#solidangle) (1), [Volume](#volume) (20)|
|squants.thermal|[Temperature](#temperature) (4), [ThermalCapacity](#thermalcapacity) (1)|
|squants.time|[Frequency](#frequency) (6), [Time](#time) (7)|
|squants.market|[Money](#money) (30)|
#### Dimension Count: 72
#### Unit Count: 237

## ChemicalAmount
##### Dimensional Symbol:  N
#### Primary Unit: Moles (1 mol)
#### SI Base Unit: Moles (1 mol)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundMoles| 1 lb-mol = 453.59237 mol|

[Go to Code](shared/src/main/scala/squants/mass/ChemicalAmount.scala)

## ElectricCurrent
##### Dimensional Symbol:  I
#### Primary Unit: Amperes (1 A)
#### SI Base Unit: Amperes (1 A)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Milliamperes| 1 mA = 0.001 A|

[Go to Code](shared/src/main/scala/squants/electro/ElectricCurrent.scala)

## Information
##### Dimensional Symbol:  B
#### Primary Unit: Bytes (1 B)
#### SI Base Unit: Bytes (1 B)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Bits| 1 bit = 0.125 B|
|Kilobits| 1 Kbit = 125.0 B|
|Kibibits| 1 Kibit = 128.0 B|
|Kilobytes| 1 KB = 1000.0 B|
|Kibibytes| 1 KiB = 1024.0 B|
|Megabits| 1 Mbit = 125000.0 B|
|Mebibits| 1 Mibit = 131072.0 B|
|Megabytes| 1 MB = 1000000.0 B|
|Mebibytes| 1 MiB = 1048576.0 B|
|Gigabits| 1 Gbit = 1.25E8 B|
|Gibibits| 1 Gibit = 1.34217728E8 B|
|Gigabytes| 1 GB = 1.0E9 B|
|Gibibytes| 1 GiB = 1.073741824E9 B|
|Terabits| 1 Tbit = 1.25E11 B|
|Tebibits| 1 Tibit = 1.37438953472E11 B|
|Terabytes| 1 TB = 1.0E12 B|
|Tebibytes| 1 TiB = 1.099511627776E12 B|
|Petabits| 1 Pbit = 1.25E14 B|
|Pebibits| 1 Pibit = 1.40737488355328E14 B|
|Petabytes| 1 PB = 1.0E15 B|
|Pebibytes| 1 PiB = 1.125899906842624E15 B|
|Exabits| 1 Ebit = 1.25E17 B|
|Exbibits| 1 Eibit = 1.44115188075855872E17 B|
|Exabytes| 1 EB = 1.0E18 B|
|Exbibytes| 1 EiB = 1.15292150460684698E18 B|
|Zettabits| 1 Zbit = 1.25E20 B|
|Zebibits| 1 Zibit = 1.4757395258967641E20 B|
|Zettabytes| 1 ZB = 1.0E21 B|
|Zebibytes| 1 ZiB = 1.1805916207174113E21 B|
|Yottabits| 1 Ybit = 1.25E23 B|
|Yobibits| 1 Yibit = 1.5111572745182865E23 B|
|Yottabytes| 1 YB = 1.0E24 B|
|Yobibytes| 1 YiB = 1.2089258196146292E24 B|

[Go to Code](shared/src/main/scala/squants/information/Information.scala)

## Length
##### Dimensional Symbol:  L
#### Primary Unit: Meters (1 m)
#### SI Base Unit: Meters (1 m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Angstroms| 1 Å = 1.0E-10 m|
|MilliElectronVoltLength| 1 mħc/eV = 1.97327E-10 m|
|Nanometers| 1 nm = 1.0E-9 m|
|ElectronVoltLength| 1 ħc/eV = 1.97327E-7 m|
|Microns| 1 µm = 1.0E-6 m|
|KiloElectronVoltLength| 1 kħc/eV = 1.9732700000000002E-4 m|
|Millimeters| 1 mm = 0.001 m|
|Centimeters| 1 cm = 0.01 m|
|Inches| 1 in = 0.0254000508 m|
|Decimeters| 1 dm = 0.1 m|
|MegaElectronVoltLength| 1 Mħc/eV = 0.197327 m|
|Feet| 1 ft = 0.3048006096 m|
|Yards| 1 yd = 0.9144018288 m|
|Decameters| 1 dam = 10.0 m|
|Hectometers| 1 hm = 100.0 m|
|GigaElectronVoltLength| 1 Għc/eV = 197.327 m|
|Kilometers| 1 km = 1000.0 m|
|InternationalMiles| 1 mile = 1609.344 m|
|UsMiles| 1 mi = 1609.3472186879999 m|
|NauticalMiles| 1 nmi = 1852.0 m|
|TeraElectronVoltLength| 1 Tħc/eV = 197327.0 m|
|PetaElectronVoltLength| 1 Pħc/eV = 1.97327E8 m|
|NominalSolarRadii| 1 RN☉ = 6.957E8 m|
|SolarRadii| 1 R☉ = 6.957E8 m|
|AstronomicalUnits| 1 au = 1.495978707E11 m|
|ExaElectronVoltLength| 1 Eħc/eV = 1.97327E11 m|
|LightYears| 1 ly = 9.4607304725808E15 m|
|Parsecs| 1 pc = 3.08567758149137E16 m|
|KiloParsecs| 1 kpc = 3.08567758149137E19 m|
|MegaParsecs| 1 Mpc = 3.08567758149137E22 m|
|GigaParsecs| 1 Gpc = 3.08567758149137E25 m|

[Go to Code](shared/src/main/scala/squants/space/Length.scala)

## LuminousIntensity
##### Dimensional Symbol:  J
#### Primary Unit: Candelas (1 cd)
#### SI Base Unit: Candelas (1 cd)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/photo/LuminousIntensity.scala)

## Mass
##### Dimensional Symbol:  M
#### Primary Unit: Grams (1 g)
#### SI Base Unit: Kilograms (1 kg)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|MilliElectronVoltMass| 1 meV/c² = 1.782662E-39 g|
|ElectronVoltMass| 1 eV/c² = 1.782662E-36 g|
|KiloElectronVoltMass| 1 keV/c² = 1.782662E-33 g|
|MegaElectronVoltMass| 1 MeV/c² = 1.782662E-30 g|
|GigaElectronVoltMass| 1 GeV/c² = 1.782662E-27 g|
|Dalton| 1 Da = 1.6605390666E-24 g|
|TeraElectronVoltMass| 1 TeV/c² = 1.782662E-24 g|
|PetaElectronVoltMass| 1 PeV/c² = 1.782662E-21 g|
|ExaElectronVoltMass| 1 EeV/c² = 1.782662E-18 g|
|Nanograms| 1 ng = 1.0E-9 g|
|Micrograms| 1 mcg = 1.0E-6 g|
|Milligrams| 1 mg = 0.001 g|
|TroyGrains| 1 gr = 0.06479891 g|
|Carats| 1 ct = 0.2 g|
|Pennyweights| 1 dwt = 1.5551738400000001 g|
|Tolas| 1 tola = 11.6638038 g|
|Ounces| 1 oz = 28.349523125 g|
|TroyOunces| 1 oz t = 31.1034768 g|
|TroyPounds| 1 lb t = 373.2417216 g|
|Pounds| 1 lb = 453.59237 g|
|Kilograms| 1 kg = 1000.0 g|
|Stone| 1 st = 6350.293180000001 g|
|Megapounds| 1 Mlb = 453592.37 g|
|Kilopounds| 1 klb = 453592.37 g|
|Tonnes| 1 t = 1000000.0 g|
|SolarMasses| 1 M☉ = 1.98855E33 g|

[Go to Code](shared/src/main/scala/squants/mass/Mass.scala)

## Temperature
##### Dimensional Symbol:  Θ
#### Primary Unit: Kelvin (1 K)
#### SI Base Unit: Kelvin (1 K)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Rankine| 1 °R = 0.5555555555555556 K|
|Fahrenheit| 1 °F = 255.92777777777778 K|
|Celsius| 1 °C = 274.15 K|

[Go to Code](shared/src/main/scala/squants/thermal/Temperature.scala)

## Time
##### Dimensional Symbol:  T
#### Primary Unit: Milliseconds (1 ms)
#### SI Base Unit: Seconds (1 s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Nanoseconds| 1 ns = 1.0E-6 ms|
|Microseconds| 1 µs = 0.001 ms|
|Seconds| 1 s = 1000.0 ms|
|Minutes| 1 min = 60000.0 ms|
|Hours| 1 h = 3600000.0 ms|
|Days| 1 d = 8.64E7 ms|

[Go to Code](shared/src/main/scala/squants/time/Time.scala)

## Acceleration
#### Primary Unit: MetersPerSecondSquared (1 m/s²)
#### SI Unit: MetersPerSecondSquared (1 m/s²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|UsMilesPerHourSquared| 1 mph² = 1.2417802613333333E-4 m/s²|
|MillimetersPerSecondSquared| 1 mm/s² = 0.001 m/s²|
|FeetPerSecondSquared| 1 ft/s² = 0.3048006096 m/s²|
|EarthGravities| 1 g = 9.80665 m/s²|

[Go to Code](shared/src/main/scala/squants/motion/Acceleration.scala)

## Activity
#### Primary Unit: Becquerels (1 Bq)
#### SI Unit: Becquerels (1 Bq)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Rutherfords| 1 Rd = 1000000.0 Bq|
|Curies| 1 Ci = 3.7E10 Bq|

[Go to Code](shared/src/main/scala/squants/radio/Activity.scala)

## Angle
#### Primary Unit: Radians (1 rad)
#### SI Unit: Radians (1 rad)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Arcseconds| 1 asec = 4.84813681109536E-6 rad|
|Arcminutes| 1 amin = 2.908882086657216E-4 rad|
|Gradians| 1 grad = 0.015707963267948967 rad|
|Degrees| 1 ° = 0.017453292519943295 rad|
|Turns| 1 turns = 6.283185307179586 rad|

[Go to Code](shared/src/main/scala/squants/space/Angle.scala)

## AngularAcceleration
#### Primary Unit: RadiansPerSecondSquared (1 rad/s²)
#### SI Unit: RadiansPerSecondSquared (1 rad/s²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ArcsecondsPerSecondSquared| 1 asec/s² = 4.84813681109536E-6 rad/s²|
|ArcminutesPerSecondSquared| 1 amin/s² = 2.908882086657216E-4 rad/s²|
|GradiansPerSecondSquared| 1 grad/s² = 0.015707963267948967 rad/s²|
|DegreesPerSecondSquared| 1 °/s² = 0.017453292519943295 rad/s²|
|TurnsPerSecondSquared| 1 turns/s² = 6.283185307179586 rad/s²|

[Go to Code](shared/src/main/scala/squants/motion/AngularAcceleration.scala)

## AngularVelocity
#### Primary Unit: RadiansPerSecond (1 rad/s)
#### SI Unit: RadiansPerSecond (1 rad/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|GradiansPerSecond| 1 grad/s = 0.015707963267948967 rad/s|
|DegreesPerSecond| 1 °/s = 0.017453292519943295 rad/s|
|TurnsPerSecond| 1 turns/s = 6.283185307179586 rad/s|

[Go to Code](shared/src/main/scala/squants/motion/AngularVelocity.scala)

## Area
#### Primary Unit: SquareMeters (1 m²)
#### SI Unit: SquareMeters (1 m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Barnes| 1 b = 1.0E-28 m²|
|SquareCentimeters| 1 cm² = 1.0E-4 m²|
|SquareInches| 1 in² = 6.4516E-4 m²|
|SquareFeet| 1 ft² = 0.09290304 m²|
|SquareYards| 1 yd² = 0.83612736 m²|
|Acres| 1 acre = 4046.8564224 m²|
|Hectares| 1 ha = 10000.0 m²|
|SquareKilometers| 1 km² = 1000000.0 m²|
|SquareUsMiles| 1 mi² = 2589988.1103359996 m²|

[Go to Code](shared/src/main/scala/squants/space/Area.scala)

## AreaDensity
#### Primary Unit: KilogramsPerSquareMeter (1 kg/m²)
#### SI Unit: KilogramsPerSquareMeter (1 kg/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|KilogramsPerHectare| 1 kg/hectare = 1.0E-4 kg/m²|
|PoundsPerAcre| 1 lb/acre = 1.1208511561944561E-4 kg/m²|
|GramsPerSquareCentimeter| 1 g/cm² = 10.0 kg/m²|

[Go to Code](shared/src/main/scala/squants/mass/AreaDensity.scala)

## AreaElectricChargeDensity
#### Primary Unit: CoulombsPerSquareMeter (1 C/m²)
#### SI Unit: CoulombsPerSquareMeter (1 C/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/AreaElectricChargeDensity.scala)

## AreaTime
#### Primary Unit: SquareMeterSeconds (1 m²‧s)
#### SI Unit: SquareMeterSeconds (1 m²‧s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|SquareCentimeterSeconds| 1 cm²‧s = 1.0E-4 m²‧s|

[Go to Code](shared/src/main/scala/squants/radio/AreaTime.scala)

## Capacitance
#### Primary Unit: Farads (1 F)
#### SI Unit: Farads (1 F)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Picofarads| 1 pF = 1.0E-12 F|
|Nanofarads| 1 nF = 1.0E-9 F|
|Microfarads| 1 μF = 1.0E-6 F|
|Millifarads| 1 mF = 0.001 F|
|Kilofarads| 1 kF = 1000.0 F|

[Go to Code](shared/src/main/scala/squants/electro/Capacitance.scala)

## Conductivity
#### Primary Unit: SiemensPerMeter (1 S/m)
#### SI Unit: SiemensPerMeter (1 S/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/Conductivity.scala)

## DataRate
#### Primary Unit: BytesPerSecond (1 B/s)
#### SI Unit: BytesPerSecond (1 B/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|BitsPerSecond| 1 bps = 0.125 B/s|
|KilobitsPerSecond| 1 Kbps = 125.0 B/s|
|KibibitsPerSecond| 1 Kibps = 128.0 B/s|
|KilobytesPerSecond| 1 KB/s = 1000.0 B/s|
|KibibytesPerSecond| 1 KiB/s = 1024.0 B/s|
|MegabitsPerSecond| 1 Mbps = 125000.0 B/s|
|MebibitsPerSecond| 1 Mibps = 131072.0 B/s|
|MegabytesPerSecond| 1 MB/s = 1000000.0 B/s|
|MebibytesPerSecond| 1 MiB/s = 1048576.0 B/s|
|GigabitsPerSecond| 1 Gbps = 1.25E8 B/s|
|GibibitsPerSecond| 1 Gibps = 1.34217728E8 B/s|
|GigabytesPerSecond| 1 GB/s = 1.0E9 B/s|
|GibibytesPerSecond| 1 GiB/s = 1.073741824E9 B/s|
|TerabitsPerSecond| 1 Tbps = 1.25E11 B/s|
|TebibitsPerSecond| 1 Tibps = 1.37438953472E11 B/s|
|TerabytesPerSecond| 1 TB/s = 1.0E12 B/s|
|TebibytesPerSecond| 1 TiB/s = 1.099511627776E12 B/s|
|PetabitsPerSecond| 1 Pbps = 1.25E14 B/s|
|PebibitsPerSecond| 1 Pibps = 1.40737488355328E14 B/s|
|PetabytesPerSecond| 1 PB/s = 1.0E15 B/s|
|PebibytesPerSecond| 1 PiB/s = 1.125899906842624E15 B/s|
|ExabitsPerSecond| 1 Ebps = 1.25E17 B/s|
|ExbibitsPerSecond| 1 Eibps = 1.44115188075855872E17 B/s|
|ExabytesPerSecond| 1 EB/s = 1.0E18 B/s|
|ExbibytesPerSecond| 1 EiB/s = 1.15292150460684698E18 B/s|
|ZettabitsPerSecond| 1 Zbps = 1.25E20 B/s|
|ZebibitsPerSecond| 1 Zibps = 1.4757395258967641E20 B/s|
|ZettabytesPerSecond| 1 ZB/s = 1.0E21 B/s|
|ZebibytesPerSecond| 1 ZiB/s = 1.1805916207174113E21 B/s|
|YottabitsPerSecond| 1 Ybps = 1.25E23 B/s|
|YobibitsPerSecond| 1 Yibps = 1.5111572745182865E23 B/s|
|YottabytesPerSecond| 1 YB/s = 1.0E24 B/s|
|YobibytesPerSecond| 1 YiB/s = 1.2089258196146292E24 B/s|

[Go to Code](shared/src/main/scala/squants/information/DataRate.scala)

## Density
#### Primary Unit: KilogramsPerCubicMeter (1 kg/m³)
#### SI Unit: KilogramsPerCubicMeter (1 kg/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|NanogramsPerLitre| 1 ng/L = 1.0E-9 kg/m³|
|MicrogramsPerLitre| 1 µg/L = 9.999999999999997E-7 kg/m³|
|NanogramsPerMillilitre| 1 ng/ml = 1.0E-6 kg/m³|
|MicrogramsPerMillilitre| 1 µg/ml = 9.999999999999998E-4 kg/m³|
|MilligramsPerLitre| 1 mg/L = 0.001 kg/m³|
|NanogramsPerMicrolitre| 1 ng/µl = 0.001 kg/m³|
|NanogramsPerNanolitre| 1 ng/nl = 0.9999999999999998 kg/m³|
|MicrogramsPerMicrolitre| 1 µg/µl = 0.9999999999999998 kg/m³|
|GramsPerLitre| 1 g/L = 1.0 kg/m³|
|MilligramsPerMillilitre| 1 mg/ml = 1.0 kg/m³|
|MicrogramsPerNanolitre| 1 µg/nl = 999.9999999999997 kg/m³|
|MilligramsPerMicrolitre| 1 mg/µl = 999.9999999999999 kg/m³|
|KilogramsPerLitre| 1 kg/L = 1000.0 kg/m³|
|GramsPerMillilitre| 1 g/ml = 1000.0000000000001 kg/m³|
|MilligramsPerNanolitre| 1 mg/nl = 999999.9999999998 kg/m³|
|GramsPerMicrolitre| 1 g/µl = 1000000.0 kg/m³|
|KilogramsPerMillilitre| 1 kg/ml = 1000000.0 kg/m³|
|KilogramsPerMicrolitre| 1 kg/µl = 9.999999999999999E8 kg/m³|
|GramsPerNanolitre| 1 g/nl = 9.999999999999999E8 kg/m³|
|KilogramsPerNanolitre| 1 kg/nl = 9.999999999999999E11 kg/m³|

[Go to Code](shared/src/main/scala/squants/mass/Density.scala)

## Dimensionless
#### Primary Unit: Each (1 ea)
#### SI Unit: Each (1 ea)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Percent| 1 % = 0.01 ea|
|Dozen| 1 dz = 12.0 ea|
|Score| 1 score = 20.0 ea|
|Gross| 1 gr = 144.0 ea|

[Go to Code](shared/src/main/scala/squants/Dimensionless.scala)

## Dose
#### Primary Unit: Sieverts (1 Sv)
#### SI Unit: Sieverts (1 Sv)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Rems| 1 rem = 0.01 Sv|

[Go to Code](shared/src/main/scala/squants/radio/Dose.scala)

## ElectricCharge
#### Primary Unit: Coulombs (1 C)
#### SI Unit: Coulombs (1 C)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Picocoulombs| 1 pC = 1.0E-12 C|
|Nanocoulombs| 1 nC = 1.0E-9 C|
|Microcoulombs| 1 µC = 1.0E-6 C|
|Millicoulombs| 1 mC = 0.001 C|
|MilliampereSeconds| 1 mAs = 0.001 C|
|MilliampereHours| 1 mAh = 3.6 C|
|Abcoulombs| 1 aC = 10.0 C|
|AmpereHours| 1 Ah = 3600.0 C|

[Go to Code](shared/src/main/scala/squants/electro/ElectricCharge.scala)

## ElectricChargeDensity
#### Primary Unit: CoulombsPerCubicMeter (1 C/m³)
#### SI Unit: CoulombsPerCubicMeter (1 C/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/ElectricChargeDensity.scala)

## ElectricChargeMassRatio
#### Primary Unit: CoulombsPerKilogram (1 C/kg)
#### SI Unit: CoulombsPerKilogram (1 C/kg)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/ElectricChargeMassRatio.scala)

## ElectricCurrentDensity
#### Primary Unit: AmperesPerSquareMeter (1 A/m²)
#### SI Unit: AmperesPerSquareMeter (1 A/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/ElectricCurrentDensity.scala)

## ElectricFieldStrength
#### Primary Unit: VoltsPerMeter (1 V/m)
#### SI Unit: VoltsPerMeter (1 V/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/ElectricFieldStrength.scala)

## ElectricPotential
#### Primary Unit: Volts (1 V)
#### SI Unit: Volts (1 V)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Microvolts| 1 μV = 1.0E-6 V|
|Millivolts| 1 mV = 0.001 V|
|Kilovolts| 1 kV = 1000.0 V|
|Megavolts| 1 MV = 1000000.0 V|

[Go to Code](shared/src/main/scala/squants/electro/ElectricPotential.scala)

## ElectricalConductance
#### Primary Unit: Siemens (1 S)
#### SI Unit: Siemens (1 S)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/ElectricalConductance.scala)

## ElectricalResistance
#### Primary Unit: Ohms (1 Ω)
#### SI Unit: Ohms (1 Ω)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Nanohms| 1 nΩ = 1.0E-9 Ω|
|Microohms| 1 µΩ = 1.0E-6 Ω|
|Milliohms| 1 mΩ = 0.001 Ω|
|Kilohms| 1 kΩ = 1000.0 Ω|
|Megohms| 1 MΩ = 1000000.0 Ω|
|Gigohms| 1 GΩ = 1.0E9 Ω|

[Go to Code](shared/src/main/scala/squants/electro/ElectricalResistance.scala)

## Energy
#### Primary Unit: WattHours (1 Wh)
#### SI Unit: Joules (1 J)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|MilliElectronVolt| 1 meV = 4.4504904583333334E-26 Wh|
|ElectronVolt| 1 eV = 4.4504904583333335E-23 Wh|
|KiloElectronVolt| 1 keV = 4.450490458333334E-20 Wh|
|MegaElectronVolt| 1 MeV = 4.4504904583333334E-17 Wh|
|Picojoules| 1 pJ = 2.7777777777777775E-16 Wh|
|GigaElectronVolt| 1 GeV = 4.450490458333334E-14 Wh|
|Nanojoules| 1 nJ = 2.777777777777778E-13 Wh|
|Ergs| 1 erg = 2.777777777777778E-11 Wh|
|TeraElectronVolt| 1 TeV = 4.4504904583333334E-11 Wh|
|Microjoules| 1 µJ = 2.7777777777777777E-10 Wh|
|PetaElectronVolt| 1 PeV = 4.4504904583333336E-8 Wh|
|Millijoules| 1 mJ = 2.7777777777777776E-7 Wh|
|ExaElectronVolt| 1 EeV = 4.4504904583333335E-5 Wh|
|Joules| 1 J = 2.777777777777778E-4 Wh|
|MilliwattHours| 1 mWh = 0.001 Wh|
|Kilojoules| 1 kJ = 0.2777777777777778 Wh|
|BritishThermalUnits| 1 Btu = 0.2930710701722222 Wh|
|Megajoules| 1 MJ = 277.77777777777777 Wh|
|MBtus| 1 MBtu = 293.0710701722222 Wh|
|KilowattHours| 1 kWh = 1000.0 Wh|
|Gigajoules| 1 GJ = 277777.77777777775 Wh|
|MMBtus| 1 MMBtu = 293071.0701722222 Wh|
|MegawattHours| 1 MWh = 1000000.0 Wh|
|Terajoules| 1 TJ = 2.777777777777778E8 Wh|
|GigawattHours| 1 GWh = 1.0E9 Wh|

[Go to Code](shared/src/main/scala/squants/energy/Energy.scala)

## EnergyDensity
#### Primary Unit: JoulesPerCubicMeter (1 J/m³)
#### SI Unit: JoulesPerCubicMeter (1 J/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/energy/EnergyDensity.scala)

## Force
#### Primary Unit: Newtons (1 N)
#### SI Unit: Newtons (1 N)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|MegaElectronVoltsPerCentimeter| 1 MeV/cm = 1.602176565E-11 N|
|KiloElectronVoltsPerMicrometer| 1 keV/μm = 1.602176565E-10 N|
|PoundForce| 1 lbf = 4.4482216152605 N|
|KilogramForce| 1 kgf = 9.80665 N|

[Go to Code](shared/src/main/scala/squants/motion/Force.scala)

## Frequency
#### Primary Unit: Hertz (1 Hz)
#### SI Unit: Hertz (1 Hz)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|RevolutionsPerMinute| 1 rpm = 0.016666666666666666 Hz|
|Kilohertz| 1 kHz = 1000.0 Hz|
|Megahertz| 1 MHz = 1000000.0 Hz|
|Gigahertz| 1 GHz = 1.0E9 Hz|
|Terahertz| 1 THz = 1.0E12 Hz|

[Go to Code](shared/src/main/scala/squants/time/Frequency.scala)

## Illuminance
#### Primary Unit: Lux (1 lx)
#### SI Unit: Lux (1 lx)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/photo/Illuminance.scala)

## Inductance
#### Primary Unit: Henry (1 H)
#### SI Unit: Henry (1 H)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Picohenry| 1 pH = 1.0E-12 H|
|Nanohenry| 1 nH = 1.0E-9 H|
|Microhenry| 1 μH = 1.0E-6 H|
|Millihenry| 1 mH = 0.001 H|

[Go to Code](shared/src/main/scala/squants/electro/Inductance.scala)

## Irradiance
#### Primary Unit: WattsPerSquareMeter (1 W/m²)
#### SI Unit: WattsPerSquareMeter (1 W/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ErgsPerSecondPerSquareCentimeter| 1 erg/s/cm² = 9.999999999999998E-4 W/m²|

[Go to Code](shared/src/main/scala/squants/radio/Irradiance.scala)

## Jerk
#### Primary Unit: MetersPerSecondCubed (1 m/s³)
#### SI Unit: MetersPerSecondCubed (1 m/s³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|FeetPerSecondCubed| 1 ft/s³ = 0.3048006096 m/s³|

[Go to Code](shared/src/main/scala/squants/motion/Jerk.scala)

## LinearElectricChargeDensity
#### Primary Unit: CoulombsPerMeter (1 C/m)
#### SI Unit: CoulombsPerMeter (1 C/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/LinearElectricChargeDensity.scala)

## Luminance
#### Primary Unit: CandelasPerSquareMeter (1 cd/m²)
#### SI Unit: CandelasPerSquareMeter (1 cd/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/photo/Luminance.scala)

## LuminousEnergy
#### Primary Unit: LumenSeconds (1 lm⋅s)
#### SI Unit: LumenSeconds (1 lm⋅s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/photo/LuminousEnergy.scala)

## LuminousExposure
#### Primary Unit: LuxSeconds (1 lx⋅s)
#### SI Unit: LuxSeconds (1 lx⋅s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/photo/LuminousExposure.scala)

## LuminousFlux
#### Primary Unit: Lumens (1 lm)
#### SI Unit: Lumens (1 lm)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/photo/LuminousFlux.scala)

## MagneticFlux
#### Primary Unit: Webers (1 Wb)
#### SI Unit: Webers (1 Wb)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/MagneticFlux.scala)

## MagneticFluxDensity
#### Primary Unit: Teslas (1 T)
#### SI Unit: Teslas (1 T)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Gauss| 1 Gs = 9.999999999999999E-5 T|

[Go to Code](shared/src/main/scala/squants/electro/MagneticFluxDensity.scala)

## MassFlow
#### Primary Unit: KilogramsPerSecond (1 kg/s)
#### SI Unit: KilogramsPerSecond (1 kg/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundsPerHour| 1 lb/hr = 1.2599788055555556E-4 kg/s|
|KilopoundsPerHour| 1 klb/hr = 0.12599788055555555 kg/s|
|PoundsPerSecond| 1 lb/s = 0.45359237 kg/s|
|MegapoundsPerHour| 1 Mlb/hr = 125.99788055555557 kg/s|

[Go to Code](shared/src/main/scala/squants/motion/MassFlow.scala)

## MolarEnergy
#### Primary Unit: JoulesPerMole (1 J/mol)
#### SI Unit: JoulesPerMole (1 J/mol)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/energy/MolarEnergy.scala)

## MomentOfInertia
#### Primary Unit: KilogramsMetersSquared (1 kg‧m²)
#### SI Unit: KilogramsMetersSquared (1 kg‧m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundsSquareFeet| 1 lb‧ft² = 42.14027865441374 kg‧m²|

[Go to Code](shared/src/main/scala/squants/mass/MomentOfInertia.scala)

## Momentum
#### Primary Unit: NewtonSeconds (1 Ns)
#### SI Unit: NewtonSeconds (1 Ns)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/motion/Momentum.scala)

## ParticleFlux
#### Primary Unit: BecquerelsPerSquareMeterSecond (1 Bq/m²‧s)
#### SI Unit: BecquerelsPerSquareMeterSecond (1 Bq/m²‧s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|BecquerelsPerSquareCentimeterSecond| 1 Bq/cm²‧s = 10000.0 Bq/m²‧s|

[Go to Code](shared/src/main/scala/squants/radio/ParticleFlux.scala)

## Permeability
#### Primary Unit: HenriesPerMeter (1 H/m)
#### SI Unit: HenriesPerMeter (1 H/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|NewtonsPerAmperesSquared| 1 N/A² = 1.0 H/m|

[Go to Code](shared/src/main/scala/squants/electro/Permeability.scala)

## Permittivity
#### Primary Unit: FaradsPerMeter (1 F/m)
#### SI Unit: FaradsPerMeter (1 F/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/Permittivity.scala)

## Power
#### Primary Unit: Watts (1 W)
#### SI Unit: Watts (1 W)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ErgsPerSecond| 1 erg/s = 1.0E-7 W|
|Milliwatts| 1 mW = 0.001 W|
|BtusPerHour| 1 Btu/hr = 0.2930710701722222 W|
|Kilowatts| 1 kW = 1000.0 W|
|Megawatts| 1 MW = 1000000.0 W|
|Gigawatts| 1 GW = 1.0E9 W|
|SolarLuminosities| 1 L☉ = 3.828E26 W|

[Go to Code](shared/src/main/scala/squants/energy/Power.scala)

## PowerDensity
#### Primary Unit: WattsPerCubicMeter (1 W/m³)
#### SI Unit: WattsPerCubicMeter (1 W/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/energy/PowerDensity.scala)

## PowerRamp
#### Primary Unit: WattsPerHour (1 W/h)
#### SI Unit: WattsPerHour (1 W/h)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|WattsPerMinute| 1 W/m = 0.016666666666666666 W/h|
|KilowattsPerMinute| 1 kW/m = 16.666666666666668 W/h|
|KilowattsPerHour| 1 kW/h = 1000.0 W/h|
|MegawattsPerHour| 1 MW/h = 1000000.0 W/h|
|GigawattsPerHour| 1 GW/h = 1.0E9 W/h|

[Go to Code](shared/src/main/scala/squants/energy/PowerRamp.scala)

## Pressure
#### Primary Unit: Pascals (1 Pa)
#### SI Unit: Pascals (1 Pa)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Torrs| 1 Torr = 133.32236842105263 Pa|
|MillimetersOfMercury| 1 mmHg = 133.322387415 Pa|
|InchesOfMercury| 1 inHg = 3386.389 Pa|
|PoundsPerSquareInch| 1 psi = 6894.757293168361 Pa|
|Bars| 1 bar = 100000.0 Pa|
|StandardAtmospheres| 1 atm = 101325.0 Pa|

[Go to Code](shared/src/main/scala/squants/motion/Pressure.scala)

## PressureChange
#### Primary Unit: PascalsPerSecond (1 Pa/s)
#### SI Unit: PascalsPerSecond (1 Pa/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundsPerSquareInchPerSecond| 1 psi/s = 6894.757293168361 Pa/s|
|BarsPerSecond| 1 bar/s = 100000.0 Pa/s|
|StandardAtmospheresPerSecond| 1 atm/s = 101325.0 Pa/s|

[Go to Code](shared/src/main/scala/squants/motion/PressureChange.scala)

## Radiance
#### Primary Unit: WattsPerSteradianPerSquareMeter (1 W/sr/m²)
#### SI Unit: WattsPerSteradianPerSquareMeter (1 W/sr/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/radio/Radiance.scala)

## RadiantIntensity
#### Primary Unit: WattsPerSteradian (1 W/sr)
#### SI Unit: WattsPerSteradian (1 W/sr)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/radio/RadiantIntensity.scala)

## Resistivity
#### Primary Unit: OhmMeters (1 Ω⋅m)
#### SI Unit: OhmMeters (1 Ω⋅m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/electro/Resistivity.scala)

## SolidAngle
#### Primary Unit: SquaredRadians (1 sr)
#### SI Unit: SquaredRadians (1 sr)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/space/SolidAngle.scala)

## SpecificEnergy
#### Primary Unit: Grays (1 Gy)
#### SI Unit: Grays (1 Gy)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ErgsPerGram| 1 erg/g = 1.0E-4 Gy|
|Rads| 1 rad = 0.01 Gy|

[Go to Code](shared/src/main/scala/squants/energy/SpecificEnergy.scala)

## SpectralIntensity
#### Primary Unit: WattsPerSteradianPerMeter (1 W/sr/m)
#### SI Unit: WattsPerSteradianPerMeter (1 W/sr/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/radio/SpectralIntensity.scala)

## SpectralIrradiance
#### Primary Unit: WattsPerCubicMeter (1 W/m³)
#### SI Unit: WattsPerCubicMeter (1 W/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|WattsPerSquareMeterPerMicron| 1 W/m²/µm = 1000000.0 W/m³|
|ErgsPerSecondPerSquareCentimeterPerAngstrom| 1 erg/s/cm²/Å = 9999999.999999998 W/m³|
|WattsPerSquareMeterPerNanometer| 1 W/m²/nm = 9.999999999999999E8 W/m³|

[Go to Code](shared/src/main/scala/squants/radio/SpectralIrradiance.scala)

## SpectralPower
#### Primary Unit: WattsPerMeter (1 W/m)
#### SI Unit: WattsPerMeter (1 W/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/radio/SpectralPower.scala)

## ThermalCapacity
#### Primary Unit: JoulesPerKelvin (1 J/K)
#### SI Unit: JoulesPerKelvin (1 J/K)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/thermal/ThermalCapacity.scala)

## Torque
#### Primary Unit: NewtonMeters (1 N‧m)
#### SI Unit: NewtonMeters (1 N‧m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundFeet| 1 lb‧ft = 1.3558206599672968 N‧m|

[Go to Code](shared/src/main/scala/squants/motion/Torque.scala)

## Volume
#### Primary Unit: CubicMeters (1 m³)
#### SI Unit: CubicMeters (1 m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Nanolitres| 1 nl = 1.0000000000000002E-12 m³|
|Microlitres| 1 µl = 1.0E-9 m³|
|Millilitres| 1 ml = 1.0E-6 m³|
|Teaspoons| 1 tsp = 4.92892159375E-6 m³|
|Centilitres| 1 cl = 1.0E-5 m³|
|Tablespoons| 1 tbsp = 1.4786764781249999E-5 m³|
|CubicInches| 1 in³ = 1.6387162322580647E-5 m³|
|FluidOunces| 1 oz = 2.9573529562499998E-5 m³|
|Decilitres| 1 dl = 1.0E-4 m³|
|UsCups| 1 c = 2.3658823649999998E-4 m³|
|UsPints| 1 pt = 4.7317647299999996E-4 m³|
|UsQuarts| 1 qt = 9.463529459999999E-4 m³|
|Litres| 1 L = 0.001 m³|
|UsGallons| 1 gal = 0.0037854117839999997 m³|
|CubicFeet| 1 ft³ = 0.028317016493419354 m³|
|Hectolitres| 1 hl = 0.1 m³|
|CubicYards| 1 yd³ = 0.7645594453223226 m³|
|AcreFeet| 1 acft = 1233.489238453347 m³|
|CubicUsMiles| 1 mi³ = 4.1682068345815496E9 m³|

[Go to Code](shared/src/main/scala/squants/space/Volume.scala)

## VolumeFlow
#### Primary Unit: CubicMetersPerSecond (1 m³/s)
#### SI Unit: CubicMetersPerSecond (1 m³/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|NanolitresPerDay| 1 nl/d = 1.1574074074074075E-17 m³/s|
|NanolitresPerHour| 1 nl/h = 2.7777777777777785E-16 m³/s|
|MicrolitresPerDay| 1 µl/d = 1.1574074074074075E-14 m³/s|
|NanolitresPerMinute| 1 nl/min = 1.666666666666667E-14 m³/s|
|MicrolitresPerHour| 1 µl/h = 2.777777777777778E-13 m³/s|
|NanolitresPerSecond| 1 nl/s = 1.0000000000000002E-12 m³/s|
|MillilitresPerDay| 1 ml/d = 1.1574074074074074E-11 m³/s|
|MicrolitresPerMinute| 1 µl/min = 1.6666666666666667E-11 m³/s|
|MillilitresPerHour| 1 ml/h = 2.7777777777777777E-10 m³/s|
|MicrolitresPerSecond| 1 µl/s = 1.0E-9 m³/s|
|LitresPerDay| 1 L/d = 1.1574074074074074E-8 m³/s|
|MillilitresPerMinute| 1 ml/min = 1.6666666666666667E-8 m³/s|
|GallonsPerDay| 1 GPD = 4.3812636388888886E-8 m³/s|
|LitresPerHour| 1 L/h = 2.7777777777777776E-7 m³/s|
|MillilitresPerSecond| 1 ml/s = 1.0E-6 m³/s|
|GallonsPerHour| 1 GPH = 1.0515032733333332E-6 m³/s|
|CubicFeetPerHour| 1 ft³/hr = 7.86583791483871E-6 m³/s|
|LitresPerMinute| 1 L/min = 1.6666666666666667E-5 m³/s|
|GallonsPerMinute| 1 GPM = 6.309019639999999E-5 m³/s|
|LitresPerSecond| 1 L/s = 0.001 m³/s|
|GallonsPerSecond| 1 GPS = 0.0037854117839999997 m³/s|

[Go to Code](shared/src/main/scala/squants/motion/VolumeFlow.scala)

## Yank
#### Primary Unit: NewtonsPerSecond (1 N/s)
#### SI Unit: NewtonsPerSecond (1 N/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants/motion/Yank.scala)

## Money
|Currency|ISO Code|Symbol|Precision|Default Format|
|---------------------|-------|-----------------------|--------------------|----------------|
|Argentinean Peso| ARS| $|2|$1.00|
|Australian Dollar| AUD| $|2|$1.00|
|Bitcoin| BTC| ₿|15|₿1.000000000000000|
|Brazilian Real| BRL| R$|2|R$1.00|
|British Pound| GBP| £|2|£1.00|
|Canadian Dollar| CAD| $|2|$1.00|
|Chilean Peso| CLP| $|2|$1.00|
|Chinese Yuan Renminbi| CNY| ¥|2|¥1.00|
|Czech Republic Koruny| CZK| Kč|2|Kč1.00|
|Danish Kroner| DKK| kr|2|kr1.00|
|Ether| ETH| Ξ|15|Ξ1.000000000000000|
|Euro| EUR| €|2|€1.00|
|Gold| XAU| oz|4|oz1.0000|
|Hong Kong Dollar| HKD| $|2|$1.00|
|Indian Rupee| INR| ₹|2|₹1.00|
|Japanese Yen| JPY| ¥|0|¥1|
|Litecoin| LTC| Ł|15|Ł1.000000000000000|
|Malaysian Ringgit| MYR| RM|2|RM1.00|
|Mexican Peso| MXN| $|2|$1.00|
|Namibian Dollar| NAD| N$|2|N$1.00|
|New Zealand Dollar| NZD| $|2|$1.00|
|Norwegian Krone| NOK| kr|2|kr1.00|
|Russian Ruble| RUB| ₽|2|₽1.00|
|Silver| XAG| oz|4|oz1.0000|
|South African Rand| ZAR| R|2|R1.00|
|South Korean Won| KRW| ₩|0|₩1|
|Swedish Kroner| SEK| kr|2|kr1.00|
|Swiss Franc| CHF| CHF|2|CHF1.00|
|Turkish lira| TRY| ₺|2|₺1.00|
|US Dollar| USD| $|2|$1.00|

[Go to Code](shared/src/main/scala/squants/market/Money.scala)
