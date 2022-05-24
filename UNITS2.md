# Squants - Supported Dimensions and Units
## Index

|Package|Dimensions|
|----------------------------|-----------------------------------------------------------|
|squants2|[Dimensionless](#dimensionless)|
|squants2.electro|[AreaElectricChargeDensity](#areaelectricchargedensity), [Capacitance](#capacitance), [Conductivity](#conductivity), [Electric Current](#electriccurrent), [ElectricCharge](#electriccharge), [ElectricChargeDensity](#electricchargedensity), [ElectricChargeMassRatio](#electricchargemassratio), [ElectricCurrentDensity](#electriccurrentdensity), [ElectricFieldStrength](#electricfieldstrength), [ElectricPotential](#electricpotential), [ElectricalConductance](#electricalconductance), [ElectricalResistance](#electricalresistance), [Inductance](#inductance), [LinearElectricChargeDensity](#linearelectricchargedensity), [MagneticFlux](#magneticflux), [MagneticFluxDensity](#magneticfluxdensity), [Permeability](#permeability), [Permittivity](#permittivity), [Resistivity](#resistivity)|
|squants2.energy|[Energy](#energy), [EnergyDensity](#energydensity), [MolarEnergy](#molarenergy), [Power](#power), [PowerDensity](#powerdensity), [PowerRamp](#powerramp), [SpecificEnergy](#specificenergy)|
|squants2.information|[DataRate](#datarate), [Information](#information)|
|squants2.mass|[AreaDensity](#areadensity), [Chemical Amount](#chemicalamount), [Density](#density), [Mass](#mass), [MomentOfInertia](#momentofinertia)|
|squants2.motion|[Acceleration](#acceleration), [AngularAcceleration](#angularacceleration), [AngularVelocity](#angularvelocity), [Force](#force), [Jerk](#jerk), [MassFlow](#massflow), [Momentum](#momentum), [Pressure](#pressure), [PressureChange](#pressurechange), [Torque](#torque), [VolumeFlow](#volumeflow), [Yank](#yank)|
|squants2.photo|[Illuminance](#illuminance), [Luminance](#luminance), [Luminous Intensity](#luminousintensity), [LuminousEnergy](#luminousenergy), [LuminousExposure](#luminousexposure), [LuminousFlux](#luminousflux)|
|squants2.radio|[Activity](#activity), [AreaTime](#areatime), [Dose](#dose), [Irradiance](#irradiance), [ParticleFlux](#particleflux), [Radiance](#radiance), [RadiantIntensity](#radiantintensity), [SpectralIntensity](#spectralintensity), [SpectralIrradiance](#spectralirradiance), [SpectralPower](#spectralpower)|
|squants2.space|[Angle](#angle), [Area](#area), [Length](#length), [SolidAngle](#solidangle), [Volume](#volume)|
|squants2.thermal|[Temperature](#temperature), [ThermalCapacity](#thermalcapacity)|
|squants2.time|[Frequency](#frequency), [Time](#time)|
#### Dimension Count: 71
#### Unit Count: 219

## Temperature
##### Dimensional Symbol:  Θ
##### SI Base Unit: Kelvin (symbol: K)
##### Primary Unit: Kelvin (symbol: K)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Rankine| 1 °R = 0.5555555555555555555555555555555556 K|
|Fahrenheit| 1 °F = 0.5555555555555555555555555555555556 K|
|Celsius| 1 °C = 1 K|

[Go to Code](shared/src/main/scala/squants2/thermal/Temperature.scala)

## ElectricCurrent
##### Dimensional Symbol:  I
##### SI Base Unit: Amperes (symbol: A)
##### Primary Unit: Amperes (symbol: A)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Milliamperes| 1 mA = 0.001 A|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricCurrent.scala)

## Mass
##### Dimensional Symbol:  M
##### SI Base Unit: Kilograms (symbol: kg)
##### Primary Unit: Grams (symbol: g)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|MilliElectronVoltMass| 1 meV/c² = 1.782662E-39 g|
|ElectronVoltMass| 1 eV/c² = 1.782662E-36 g|
|KiloElectronVoltMass| 1 keV/c² = 1.7826620000E-33 g|
|MegaElectronVoltMass| 1 MeV/c² = 1.7826620000000E-30 g|
|GigaElectronVoltMass| 1 GeV/c² = 1.7826620E-27 g|
|Dalton| 1 Da = 1.6605390666E-24 g|
|TeraElectronVoltMass| 1 TeV/c² = 1.7826620E-24 g|
|PetaElectronVoltMass| 1 PeV/c² = 1.7826620E-21 g|
|ExaElectronVoltMass| 1 EeV/c² = 1.7826620E-18 g|
|Nanograms| 1 ng = 1.0E-9 g|
|PlankMass| 1 mp = 2.176434E-8 g|
|Micrograms| 1 mcg = 0.0000010 g|
|Milligrams| 1 mg = 0.001 g|
|TroyGrains| 1 gr = 0.06479891 g|
|Carats| 1 ct = 0.2 g|
|Pennyweights| 1 dwt = 1.555173840 g|
|Tolas| 1 tola = 11.663803800 g|
|Ounces| 1 oz = 28.349523125 g|
|TroyOunces| 1 oz t = 31.103476800 g|
|TroyPounds| 1 lb t = 373.2417216 g|
|Pounds| 1 lb = 453.59237 g|
|Kilograms| 1 kg = 1000.0 g|
|Stone| 1 st = 6350.293180 g|
|Kilopounds| 1 klb = 453592.370000 g|
|Tonnes| 1 t = 1000000.0 g|
|Megapounds| 1 Mlb = 453592370.000000 g|
|SolarMasses| 1 M☉ = 1.98855E+33 g|

[Go to Code](shared/src/main/scala/squants2/mass/Mass.scala)

## LuminousIntensity
##### Dimensional Symbol:  J
##### SI Base Unit: Candelas (symbol: cd)
##### Primary Unit: Candelas (symbol: cd)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/photo/LuminousIntensity.scala)

## ChemicalAmount
##### Dimensional Symbol:  N
##### SI Base Unit: Moles (symbol: mol)
##### Primary Unit: Moles (symbol: mol)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundMoles| 1 lb-mol = 453.59237 mol|

[Go to Code](shared/src/main/scala/squants2/mass/ChemicalAmount.scala)

## Time
##### Dimensional Symbol:  T
##### SI Base Unit: Seconds (symbol: s)
##### Primary Unit: Seconds (symbol: s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PlankTime| 1 tp = 5.39E-44 s|
|Picoseconds| 1 ps = 1.0E-12 s|
|Nanoseconds| 1 ns = 1.0E-9 s|
|Microseconds| 1 µs = 0.0000010 s|
|Milliseconds| 1 ms = 0.001 s|
|Minutes| 1 min = 60 s|
|Hours| 1 h = 3600 s|
|Days| 1 d = 86400 s|
|Weeks| 1 w = 604800 s|

[Go to Code](shared/src/main/scala/squants2/time/Time.scala)

## Length
##### Dimensional Symbol:  L
##### SI Base Unit: Meters (symbol: m)
##### Primary Unit: Meters (symbol: m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PlankLength| 1 ℓp = 1.616255E-35 m|
|Angstroms| 1 Å = 1.0E-10 m|
|MilliElectronVoltLength| 1 mħc/eV = 1.97327E-10 m|
|Nanometers| 1 nm = 1.0E-9 m|
|ElectronVoltLength| 1 ħc/eV = 1.97327E-7 m|
|Microns| 1 µm = 0.0000010 m|
|KiloElectronVoltLength| 1 kħc/eV = 0.0001973270000 m|
|Millimeters| 1 mm = 0.001 m|
|Centimeters| 1 cm = 0.01 m|
|Inches| 1 in = 0.0254000508 m|
|Decimeters| 1 dm = 0.1 m|
|MegaElectronVoltLength| 1 Mħc/eV = 0.1973270000000 m|
|Feet| 1 ft = 0.3048006096 m|
|Yards| 1 yd = 0.91440182880 m|
|Decameters| 1 dam = 10.0 m|
|Hectometers| 1 hm = 100.0 m|
|GigaElectronVoltLength| 1 Għc/eV = 197.3270 m|
|Kilometers| 1 km = 1000.0 m|
|InternationalMiles| 1 mile = 1609.344 m|
|UsMiles| 1 mi = 1609.34721868800 m|
|NauticalMiles| 1 nmi = 1852.0 m|
|TeraElectronVoltLength| 1 Tħc/eV = 197327.0 m|
|PetaElectronVoltLength| 1 Pħc/eV = 1.973270E+8 m|
|NominalSolarRadii| 1 RN☉ = 6.957E+8 m|
|SolarRadii| 1 R☉ = 6.957E+8 m|
|AstronomicalUnits| 1 au = 1.495978707E+11 m|
|ExaElectronVoltLength| 1 Eħc/eV = 1.973270E+11 m|
|LightYears| 1 ly = 9.4607304725808E+15 m|
|Parsecs| 1 pc = 3.08567758149137E+16 m|
|KiloParsecs| 1 kpc = 3.085677581491370000E+19 m|
|MegaParsecs| 1 Mpc = 3.085677581491370000000E+22 m|
|GigaParsecs| 1 Gpc = 3.085677581491370E+25 m|

[Go to Code](shared/src/main/scala/squants2/space/Length.scala)

## AreaTime
#### SI Unit: SquareMeterSeconds (symbol: m²‧s)
#### Primary Unit: SquareMeterSeconds (symbol: m²‧s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|SquareCentimeterSeconds| 1 cm²‧s = 0.00010 m²‧s|

[Go to Code](shared/src/main/scala/squants2/radio/AreaTime.scala)

## Momentum
#### SI Unit: NewtonSeconds (symbol: Ns)
#### Primary Unit: NewtonSeconds (symbol: Ns)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/motion/Momentum.scala)

## AngularVelocity
#### SI Unit: RadiansPerSecond (symbol: rad/s)
#### Primary Unit: RadiansPerSecond (symbol: rad/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|GradiansPerSecond| 1 grad/s = 0.015707963267948967 rad/s|
|DegreesPerSecond| 1 °/s = 0.017453292519943295 rad/s|
|TurnsPerSecond| 1 turns/s = 6.283185307179586 rad/s|

[Go to Code](shared/src/main/scala/squants2/motion/AngularVelocity.scala)

## LuminousEnergy
#### SI Unit: LumenSeconds (symbol: lm⋅s)
#### Primary Unit: LumenSeconds (symbol: lm⋅s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/photo/LuminousEnergy.scala)

## MagneticFluxDensity
#### SI Unit: Teslas (symbol: T)
#### Primary Unit: Teslas (symbol: T)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Gauss| 1 Gs = 0.00009999999999999999 T|

[Go to Code](shared/src/main/scala/squants2/electro/MagneticFluxDensity.scala)

## Area
#### SI Unit: SquareMeters (symbol: m²)
#### Primary Unit: SquareMeters (symbol: m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|SquareFeet| 1 ft² = 0.09290304 m²|

[Go to Code](shared/src/main/scala/squants2/space/Area.scala)

## Resistivity
#### SI Unit: OhmMeters (symbol: Ω⋅m)
#### Primary Unit: OhmMeters (symbol: Ω⋅m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/Resistivity.scala)

## Conductivity
#### SI Unit: SiemensPerMeter (symbol: S/m)
#### Primary Unit: SiemensPerMeter (symbol: S/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/Conductivity.scala)

## EnergyDensity
#### SI Unit: JoulesPerCubicMeter (symbol: J/m³)
#### Primary Unit: JoulesPerCubicMeter (symbol: J/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/energy/EnergyDensity.scala)

## Yank
#### SI Unit: NewtonsPerSecond (symbol: N/s)
#### Primary Unit: NewtonsPerSecond (symbol: N/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/motion/Yank.scala)

## Acceleration
#### SI Unit: MetersPerSecondSquared (symbol: m/s²)
#### Primary Unit: MetersPerSecondSquared (symbol: m/s²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|UsMilesPerHourSquared| 1 mph² = 0.00012417802613333333 m/s²|
|MillimetersPerSecondSquared| 1 mm/s² = 0.001 m/s²|
|FeetPerSecondSquared| 1 ft/s² = 0.3048006096 m/s²|
|EarthGravities| 1 g = 9.80665 m/s²|

[Go to Code](shared/src/main/scala/squants2/motion/Acceleration.scala)

## ElectricalConductance
#### SI Unit: Siemens (symbol: S)
#### Primary Unit: Siemens (symbol: S)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricalConductance.scala)

## SpectralIntensity
#### SI Unit: WattsPerSteradianPerMeter (symbol: W/sr/m)
#### Primary Unit: WattsPerSteradianPerMeter (symbol: W/sr/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/radio/SpectralIntensity.scala)

## Inductance
#### SI Unit: Henry (symbol: H)
#### Primary Unit: Henry (symbol: H)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Picohenry| 1 pH = 1.0E-12 H|
|Nanohenry| 1 nH = 1.0E-9 H|
|Microhenry| 1 μH = 0.0000010 H|
|Millihenry| 1 mH = 0.001 H|

[Go to Code](shared/src/main/scala/squants2/electro/Inductance.scala)

## Energy
#### SI Unit: Joules (symbol: J)
#### Primary Unit: WattHours (symbol: Wh)
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
|ExaElectronVolt| 1 EeV = 0.000044504904583333335 Wh|
|Joules| 1 J = 0.0002777777777777778 Wh|
|MilliwattHours| 1 mWh = 0.001 Wh|
|Kilojoules| 1 kJ = 0.2777777777777778 Wh|
|BritishThermalUnits| 1 Btu = 0.2930710701722222 Wh|
|Megajoules| 1 MJ = 277.77777777777777 Wh|
|MBtus| 1 MBtu = 293.0710701722222 Wh|
|KilowattHours| 1 kWh = 1000.0 Wh|
|Gigajoules| 1 GJ = 277777.77777777775 Wh|
|MMBtus| 1 MMBtu = 293071.0701722222 Wh|
|MegawattHours| 1 MWh = 1000000.0 Wh|
|Terajoules| 1 TJ = 277777777.7777778 Wh|
|GigawattHours| 1 GWh = 1.0E+9 Wh|

[Go to Code](shared/src/main/scala/squants2/energy/Energy.scala)

## Power
#### SI Unit: Watts (symbol: W)
#### Primary Unit: Watts (symbol: W)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ErgsPerSecond| 1 erg/s = 1.0E-7 W|
|Milliwatts| 1 mW = 0.001 W|
|BtusPerHour| 1 Btu/hr = 0.2930710701722222 W|
|Kilowatts| 1 kW = 1000.0 W|
|Megawatts| 1 MW = 1000000.0 W|
|Gigawatts| 1 GW = 1.0E+9 W|
|SolarLuminosities| 1 L☉ = 3.828E+26 W|

[Go to Code](shared/src/main/scala/squants2/energy/Power.scala)

## Activity
#### SI Unit: Becquerels (symbol: Bq)
#### Primary Unit: Becquerels (symbol: Bq)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Rutherfords| 1 Rd = 1000000.0 Bq|
|Curies| 1 Ci = 3.7E+10 Bq|

[Go to Code](shared/src/main/scala/squants2/radio/Activity.scala)

## Dimensionless
#### SI Unit: Each (symbol: ea)
#### Primary Unit: Each (symbol: ea)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Percent| 1 % = 0.01 ea|
|Dozen| 1 dz = 12 ea|
|Score| 1 score = 20 ea|
|Gross| 1 gr = 144 ea|

[Go to Code](shared/src/main/scala/squants2/Dimensionless.scala)

## AreaDensity
#### SI Unit: KilogramsPerSquareMeter (symbol: kg/m²)
#### Primary Unit: KilogramsPerSquareMeter (symbol: kg/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|KilogramsPerHectare| 1 kg/hectare = 0.00010 kg/m²|
|PoundsPerAcre| 1 lb/acre = 0.00011208511561944561 kg/m²|
|GramsPerSquareCentimeter| 1 g/cm² = 10.0 kg/m²|

[Go to Code](shared/src/main/scala/squants2/mass/AreaDensity.scala)

## MagneticFlux
#### SI Unit: Webers (symbol: Wb)
#### Primary Unit: Webers (symbol: Wb)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/MagneticFlux.scala)

## PressureChange
#### SI Unit: PascalsPerSecond (symbol: Pa/s)
#### Primary Unit: PascalsPerSecond (symbol: Pa/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundsPerSquareInchPerSecond| 1 psi/s = 6894.757293168361 Pa/s|
|BarsPerSecond| 1 bar/s = 100000.0 Pa/s|
|StandardAtmospheresPerSecond| 1 atm/s = 101325.0 Pa/s|

[Go to Code](shared/src/main/scala/squants2/motion/PressureChange.scala)

## ElectricChargeMassRatio
#### SI Unit: CoulombsPerKilogram (symbol: C/kg)
#### Primary Unit: CoulombsPerKilogram (symbol: C/kg)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricChargeMassRatio.scala)

## Pressure
#### SI Unit: Pascals (symbol: Pa)
#### Primary Unit: Pascals (symbol: Pa)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Torrs| 1 Torr = 133.32236842105263 Pa|
|MillimetersOfMercury| 1 mmHg = 133.322387415 Pa|
|InchesOfMercury| 1 inHg = 3386.389 Pa|
|PoundsPerSquareInch| 1 psi = 6894.757293168361 Pa|
|Bars| 1 bar = 100000.0 Pa|
|StandardAtmospheres| 1 atm = 101325.0 Pa|

[Go to Code](shared/src/main/scala/squants2/motion/Pressure.scala)

## Angle
#### SI Unit: Radians (symbol: rad)
#### Primary Unit: Radians (symbol: rad)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Arcseconds| 1 ° = 0.000004848136811095360000000000000000001 rad|
|Arcminutes| 1 ° = 0.0002908882086657216 rad|
|Gradians| 1 grad = 0.015707963267948965 rad|
|Degrees| 1 ° = 0.017453292519943295 rad|
|Turns| 1 ° = 6.283185307179586 rad|

[Go to Code](shared/src/main/scala/squants2/space/Angle.scala)

## SolidAngle
#### SI Unit: SquaredRadians (symbol: sr)
#### Primary Unit: SquaredRadians (symbol: sr)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/space/SolidAngle.scala)

## ThermalCapacity
#### SI Unit: JoulesPerKelvin (symbol: J/K)
#### Primary Unit: JoulesPerKelvin (symbol: J/K)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/thermal/ThermalCapacity.scala)

## Torque
#### SI Unit: NewtonMeters (symbol: N‧m)
#### Primary Unit: NewtonMeters (symbol: N‧m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundFeet| 1 lb‧ft = 1.3558206599672968 N‧m|

[Go to Code](shared/src/main/scala/squants2/motion/Torque.scala)

## Information
#### SI Unit: Bytes (symbol: B)
#### Primary Unit: Bytes (symbol: B)
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
|Gigabits| 1 Gbit = 1.25E+8 B|
|Gibibits| 1 Gibit = 134217728 B|
|Gigabytes| 1 GB = 1.0E+9 B|
|Gibibytes| 1 GiB = 1073741824 B|
|Terabits| 1 Tbit = 1.25E+11 B|
|Tebibits| 1 Tibit = 137438953472 B|
|Terabytes| 1 TB = 1.0E+12 B|
|Tebibytes| 1 TiB = 1099511627776 B|
|Petabits| 1 Pbit = 1.25E+14 B|
|Pebibits| 1 Pibit = 140737488355328 B|
|Petabytes| 1 PB = 1.0E+15 B|
|Pebibytes| 1 PiB = 1125899906842624 B|
|Exabits| 1 Ebit = 1.25E+17 B|
|Exbibits| 1 Eibit = 144115188075855872 B|
|Exabytes| 1 EB = 1.0E+18 B|
|Exbibytes| 1 EiB = 1.15292150460684698E+18 B|
|Zettabits| 1 Zbit = 1.25E+20 B|
|Zebibits| 1 Zibit = 1.4757395258967641E+20 B|
|Zettabytes| 1 ZB = 1.0E+21 B|
|Zebibytes| 1 ZiB = 1.1805916207174113E+21 B|
|Yottabits| 1 Ybit = 1.25E+23 B|
|Yobibits| 1 Yibit = 1.5111572745182865E+23 B|
|Yottabytes| 1 YB = 1.0E+24 B|
|Yobibytes| 1 YiB = 1.2089258196146292E+24 B|

[Go to Code](shared/src/main/scala/squants2/information/Information.scala)

## ElectricChargeDensity
#### SI Unit: CoulombsPerCubicMeter (symbol: C/m³)
#### Primary Unit: CoulombsPerCubicMeter (symbol: C/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricChargeDensity.scala)

## DataRate
#### SI Unit: BytesPerSecond (symbol: B/s)
#### Primary Unit: BytesPerSecond (symbol: B/s)
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
|GigabitsPerSecond| 1 Gbps = 1.25E+8 B/s|
|GibibitsPerSecond| 1 Gibps = 134217728 B/s|
|GigabytesPerSecond| 1 GB/s = 1.0E+9 B/s|
|GibibytesPerSecond| 1 GiB/s = 1073741824 B/s|
|TerabitsPerSecond| 1 Tbps = 1.25E+11 B/s|
|TebibitsPerSecond| 1 Tibps = 137438953472 B/s|
|TerabytesPerSecond| 1 TB/s = 1.0E+12 B/s|
|TebibytesPerSecond| 1 TiB/s = 1099511627776 B/s|
|PetabitsPerSecond| 1 Pbps = 1.25E+14 B/s|
|PebibitsPerSecond| 1 Pibps = 140737488355328 B/s|
|PetabytesPerSecond| 1 PB/s = 1.0E+15 B/s|
|PebibytesPerSecond| 1 PiB/s = 1125899906842624 B/s|
|ExabitsPerSecond| 1 Ebps = 1.25E+17 B/s|
|ExbibitsPerSecond| 1 Eibps = 144115188075855872 B/s|
|ExabytesPerSecond| 1 EB/s = 1.0E+18 B/s|
|ExbibytesPerSecond| 1 EiB/s = 1.15292150460684698E+18 B/s|
|ZettabitsPerSecond| 1 Zbps = 1.25E+20 B/s|
|ZebibitsPerSecond| 1 Zibps = 1.4757395258967641E+20 B/s|
|ZettabytesPerSecond| 1 ZB/s = 1.0E+21 B/s|
|ZebibytesPerSecond| 1 ZiB/s = 1.1805916207174113E+21 B/s|
|YottabitsPerSecond| 1 Ybps = 1.25E+23 B/s|
|YobibitsPerSecond| 1 Yibps = 1.5111572745182865E+23 B/s|
|YottabytesPerSecond| 1 YB/s = 1.0E+24 B/s|
|YobibytesPerSecond| 1 YiB/s = 1.2089258196146292E+24 B/s|

[Go to Code](shared/src/main/scala/squants2/information/DataRate.scala)

## ElectricCurrentDensity
#### SI Unit: AmperesPerSquareMeter (symbol: A/m²)
#### Primary Unit: AmperesPerSquareMeter (symbol: A/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricCurrentDensity.scala)

## PowerDensity
#### SI Unit: WattsPerCubicMeter (symbol: W/m³)
#### Primary Unit: WattsPerCubicMeter (symbol: W/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/energy/PowerDensity.scala)

## Luminance
#### SI Unit: CandelasPerSquareMeter (symbol: cd/m²)
#### Primary Unit: CandelasPerSquareMeter (symbol: cd/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/photo/Luminance.scala)

## LuminousFlux
#### SI Unit: Lumens (symbol: lm)
#### Primary Unit: Lumens (symbol: lm)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/photo/LuminousFlux.scala)

## ElectricCharge
#### SI Unit: Coulombs (symbol: C)
#### Primary Unit: Coulombs (symbol: C)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Picocoulombs| 1 pC = 1.0E-12 C|
|Nanocoulombs| 1 nC = 1.0E-9 C|
|Microcoulombs| 1 µC = 0.0000010 C|
|MilliampereSeconds| 1 mAs = 0.001 C|
|Millicoulombs| 1 mC = 0.001 C|
|MilliampereHours| 1 mAh = 3.6 C|
|Abcoulombs| 1 aC = 10.0 C|
|AmpereHours| 1 Ah = 3600.0 C|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricCharge.scala)

## Force
#### SI Unit: Newtons (symbol: N)
#### Primary Unit: Newtons (symbol: N)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|MegaElectronVoltsPerCentimeter| 1 MeV/cm = 1.602176565E-11 N|
|KiloElectronVoltsPerMicrometer| 1 keV/μm = 1.602176565E-10 N|
|PoundForce| 1 lbf = 4.4482216152605 N|
|KilogramForce| 1 kgf = 9.80665 N|

[Go to Code](shared/src/main/scala/squants2/motion/Force.scala)

## PowerRamp
#### SI Unit: WattsPerHour (symbol: W/h)
#### Primary Unit: WattsPerHour (symbol: W/h)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|WattsPerMinute| 1 W/m = 0.016666666666666666 W/h|
|KilowattsPerMinute| 1 kW/m = 16.666666666666668 W/h|
|KilowattsPerHour| 1 kW/h = 1000.0 W/h|
|MegawattsPerHour| 1 MW/h = 1000000.0 W/h|
|GigawattsPerHour| 1 GW/h = 1.0E+9 W/h|

[Go to Code](shared/src/main/scala/squants2/energy/PowerRamp.scala)

## LuminousExposure
#### SI Unit: LuxSeconds (symbol: lx⋅s)
#### Primary Unit: LuxSeconds (symbol: lx⋅s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/photo/LuminousExposure.scala)

## Capacitance
#### SI Unit: Farads (symbol: F)
#### Primary Unit: Farads (symbol: F)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Picofarads| 1 pF = 1.0E-12 F|
|Nanofarads| 1 nF = 1.0E-9 F|
|Microfarads| 1 μF = 0.0000010 F|
|Millifarads| 1 mF = 0.001 F|
|Kilofarads| 1 kF = 1000.0 F|

[Go to Code](shared/src/main/scala/squants2/electro/Capacitance.scala)

## ElectricalResistance
#### SI Unit: Ohms (symbol: Ω)
#### Primary Unit: Ohms (symbol: Ω)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Nanohms| 1 nΩ = 1.0E-9 Ω|
|Microohms| 1 µΩ = 0.0000010 Ω|
|Milliohms| 1 mΩ = 0.001 Ω|
|Kilohms| 1 kΩ = 1000.0 Ω|
|Megohms| 1 MΩ = 1000000.0 Ω|
|Gigohms| 1 GΩ = 1.0E+9 Ω|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricalResistance.scala)

## Jerk
#### SI Unit: MetersPerSecondCubed (symbol: m/s³)
#### Primary Unit: MetersPerSecondCubed (symbol: m/s³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|FeetPerSecondCubed| 1 ft/s³ = 0.3048006096 m/s³|

[Go to Code](shared/src/main/scala/squants2/motion/Jerk.scala)

## LinearElectricChargeDensity
#### SI Unit: CoulombsPerMeter (symbol: C/m)
#### Primary Unit: CoulombsPerMeter (symbol: C/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/LinearElectricChargeDensity.scala)

## ParticleFlux
#### SI Unit: BecquerelsPerSquareMeterSecond (symbol: Bq/m²‧s)
#### Primary Unit: BecquerelsPerSquareMeterSecond (symbol: Bq/m²‧s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|BecquerelsPerSquareCentimeterSecond| 1 Bq/cm²‧s = 10000.0 Bq/m²‧s|

[Go to Code](shared/src/main/scala/squants2/radio/ParticleFlux.scala)

## AreaElectricChargeDensity
#### SI Unit: CoulombsPerSquareMeter (symbol: C/m²)
#### Primary Unit: CoulombsPerSquareMeter (symbol: C/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/AreaElectricChargeDensity.scala)

## VolumeFlow
#### SI Unit: CubicMetersPerSecond (symbol: m³/s)
#### Primary Unit: CubicMetersPerSecond (symbol: m³/s)
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
|MillilitresPerSecond| 1 ml/s = 0.0000010 m³/s|
|GallonsPerHour| 1 GPH = 0.0000010515032733333332 m³/s|
|CubicFeetPerHour| 1 ft³/hr = 0.00000786583791483871 m³/s|
|LitresPerMinute| 1 L/min = 0.000016666666666666667 m³/s|
|GallonsPerMinute| 1 GPM = 0.00006309019639999999 m³/s|
|LitresPerSecond| 1 L/s = 0.001 m³/s|
|GallonsPerSecond| 1 GPS = 0.0037854117839999997 m³/s|

[Go to Code](shared/src/main/scala/squants2/motion/VolumeFlow.scala)

## SpectralIrradiance
#### SI Unit: WattsPerCubicMeter (symbol: W/m³)
#### Primary Unit: WattsPerCubicMeter (symbol: W/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|WattsPerSquareMeterPerMicron| 1 W/m²/µm = 1000000.0 W/m³|
|ErgsPerSecondPerSquareCentimeterPerAngstrom| 1 erg/s/cm²/Å = 9999999.999999998 W/m³|
|WattsPerSquareMeterPerNanometer| 1 W/m²/nm = 999999999.9999999 W/m³|

[Go to Code](shared/src/main/scala/squants2/radio/SpectralIrradiance.scala)

## Permittivity
#### SI Unit: FaradsPerMeter (symbol: F/m)
#### Primary Unit: FaradsPerMeter (symbol: F/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/Permittivity.scala)

## Illuminance
#### SI Unit: Lux (symbol: lx)
#### Primary Unit: Lux (symbol: lx)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/photo/Illuminance.scala)

## ElectricFieldStrength
#### SI Unit: VoltsPerMeter (symbol: V/m)
#### Primary Unit: VoltsPerMeter (symbol: V/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricFieldStrength.scala)

## Volume
#### SI Unit: CubicMeters (symbol: m³)
#### Primary Unit: CubicMeters (symbol: m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|CubicFeet| 1 ft³ = 0.09290304 m³|

[Go to Code](shared/src/main/scala/squants2/space/Volume.scala)

## Frequency
#### SI Unit: Hertz (symbol: Hz)
#### Primary Unit: Hertz (symbol: Hz)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|RevolutionsPerMinute| 1 rpm = 0.016666666666666666 Hz|
|Kilohertz| 1 kHz = 1000.0 Hz|
|Megahertz| 1 MHz = 1000000.0 Hz|
|Gigahertz| 1 GHz = 1.0E+9 Hz|
|Terahertz| 1 THz = 1.0E+12 Hz|

[Go to Code](shared/src/main/scala/squants2/time/Frequency.scala)

## MomentOfInertia
#### SI Unit: KilogramsMetersSquared (symbol: kg‧m²)
#### Primary Unit: KilogramsMetersSquared (symbol: kg‧m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundsSquareFeet| 1 lb‧ft² = 42.14027865441374 kg‧m²|

[Go to Code](shared/src/main/scala/squants2/mass/MomentOfInertia.scala)

## MassFlow
#### SI Unit: KilogramsPerSecond (symbol: kg/s)
#### Primary Unit: KilogramsPerSecond (symbol: kg/s)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|PoundsPerHour| 1 lb/hr = 0.00012599788055555556 kg/s|
|KilopoundsPerHour| 1 klb/hr = 0.12599788055555555 kg/s|
|PoundsPerSecond| 1 lb/s = 0.45359237 kg/s|
|MegapoundsPerHour| 1 Mlb/hr = 125.99788055555557 kg/s|

[Go to Code](shared/src/main/scala/squants2/motion/MassFlow.scala)

## RadiantIntensity
#### SI Unit: WattsPerSteradian (symbol: W/sr)
#### Primary Unit: WattsPerSteradian (symbol: W/sr)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/radio/RadiantIntensity.scala)

## Radiance
#### SI Unit: WattsPerSteradianPerSquareMeter (symbol: W/sr/m²)
#### Primary Unit: WattsPerSteradianPerSquareMeter (symbol: W/sr/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/radio/Radiance.scala)

## SpecificEnergy
#### SI Unit: Grays (symbol: Gy)
#### Primary Unit: Grays (symbol: Gy)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ErgsPerGram| 1 erg/g = 0.00010 Gy|
|Rads| 1 rad = 0.01 Gy|

[Go to Code](shared/src/main/scala/squants2/energy/SpecificEnergy.scala)

## Permeability
#### SI Unit: HenriesPerMeter (symbol: H/m)
#### Primary Unit: HenriesPerMeter (symbol: H/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|NewtonsPerAmperesSquared| 1 N/A² = 1.0 H/m|

[Go to Code](shared/src/main/scala/squants2/electro/Permeability.scala)

## SpectralPower
#### SI Unit: WattsPerMeter (symbol: W/m)
#### Primary Unit: WattsPerMeter (symbol: W/m)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/radio/SpectralPower.scala)

## Dose
#### SI Unit: Sieverts (symbol: Sv)
#### Primary Unit: Sieverts (symbol: Sv)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Rems| 1 rem = 0.01 Sv|

[Go to Code](shared/src/main/scala/squants2/radio/Dose.scala)

## Density
#### SI Unit: KilogramsPerCubicMeter (symbol: kg/m³)
#### Primary Unit: KilogramsPerCubicMeter (symbol: kg/m³)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|NanogramsPerLitre| 1 ng/L = 1.0E-9 kg/m³|
|MicrogramsPerLitre| 1 µg/L = 9.999999999999997E-7 kg/m³|
|NanogramsPerMillilitre| 1 ng/ml = 0.0000010 kg/m³|
|MicrogramsPerMillilitre| 1 µg/ml = 0.0009999999999999998 kg/m³|
|MilligramsPerLitre| 1 mg/L = 0.001 kg/m³|
|NanogramsPerMicrolitre| 1 ng/µl = 0.001 kg/m³|
|MicrogramsPerMicrolitre| 1 µg/µl = 0.9999999999999998 kg/m³|
|NanogramsPerNanolitre| 1 ng/nl = 0.9999999999999998 kg/m³|
|GramsPerLitre| 1 g/L = 1.0 kg/m³|
|MilligramsPerMillilitre| 1 mg/ml = 1.0 kg/m³|
|MicrogramsPerNanolitre| 1 µg/nl = 999.9999999999997 kg/m³|
|MilligramsPerMicrolitre| 1 mg/µl = 999.9999999999999 kg/m³|
|KilogramsPerLitre| 1 kg/L = 1000.0 kg/m³|
|GramsPerMillilitre| 1 g/ml = 1000.0000000000001 kg/m³|
|MilligramsPerNanolitre| 1 mg/nl = 999999.9999999998 kg/m³|
|GramsPerMicrolitre| 1 g/µl = 1000000.0 kg/m³|
|KilogramsPerMillilitre| 1 kg/ml = 1000000.0 kg/m³|
|GramsPerNanolitre| 1 g/nl = 999999999.9999999 kg/m³|
|KilogramsPerMicrolitre| 1 kg/µl = 999999999.9999999 kg/m³|
|KilogramsPerNanolitre| 1 kg/nl = 999999999999.9999 kg/m³|

[Go to Code](shared/src/main/scala/squants2/mass/Density.scala)

## MolarEnergy
#### SI Unit: JoulesPerMole (symbol: J/mol)
#### Primary Unit: JoulesPerMole (symbol: J/mol)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|

[Go to Code](shared/src/main/scala/squants2/energy/MolarEnergy.scala)

## AngularAcceleration
#### SI Unit: RadiansPerSecondSquared (symbol: rad/s²)
#### Primary Unit: RadiansPerSecondSquared (symbol: rad/s²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ArcsecondsPerSecondSquared| 1 asec/s² = 0.00000484813681109536 rad/s²|
|ArcminutesPerSecondSquared| 1 amin/s² = 0.0002908882086657216 rad/s²|
|GradiansPerSecondSquared| 1 grad/s² = 0.015707963267948967 rad/s²|
|DegreesPerSecondSquared| 1 °/s² = 0.017453292519943295 rad/s²|
|TurnsPerSecondSquared| 1 turns/s² = 6.283185307179586 rad/s²|

[Go to Code](shared/src/main/scala/squants2/motion/AngularAcceleration.scala)

## Irradiance
#### SI Unit: WattsPerSquareMeter (symbol: W/m²)
#### Primary Unit: WattsPerSquareMeter (symbol: W/m²)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|ErgsPerSecondPerSquareCentimeter| 1 erg/s/cm² = 0.0009999999999999998 W/m²|

[Go to Code](shared/src/main/scala/squants2/radio/Irradiance.scala)

## ElectricPotential
#### SI Unit: Volts (symbol: V)
#### Primary Unit: Volts (symbol: V)
|Unit|Conversion Factor|
|----------------------------|-----------------------------------------------------------|
|Microvolts| 1 μV = 0.0000010 V|
|Millivolts| 1 mV = 0.001 V|
|Kilovolts| 1 kV = 1000.0 V|
|Megavolts| 1 MV = 1000000.0 V|

[Go to Code](shared/src/main/scala/squants2/electro/ElectricPotential.scala)
