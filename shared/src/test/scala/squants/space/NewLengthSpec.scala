package squants.space

import squants.electro.{OhmMeters, Ohms, Siemens, SiemensPerMeter}
import squants.energy.Joules
import squants.motion.{MetersPerSecond, Newtons}
import squants.time.Seconds
import squants.{GenericSpec, MetricSystem}


class NewLengthSpec extends GenericSpec(Length) {

  private val x = Meters(1)
  private val metersPerFoot = 0.3048006096
  
  override val unitConversionsTable = Table(
    ("actual", "expected"),
    (x.toMeters, 1.0),
    (x.toAngstroms, 1 / (100*MetricSystem.Pico)),
    (x.toNanometers, 1 / MetricSystem.Nano),
    (x.toMicrons, 1 / MetricSystem.Micro),
    (x.toMillimeters, 1 / MetricSystem.Milli),
    (x.toCentimeters, 1 / MetricSystem.Centi),
    (x.toDecimeters, 1 / MetricSystem.Deci),
    (x.toDecameters, 1 / MetricSystem.Deca),
    (x.toHectometers, 1 / MetricSystem.Hecto),
    (x.toKilometers, 1 / MetricSystem.Kilo),

    (x.toInches, 1 / (metersPerFoot / 12)),
    (x.toFeet, 1 / metersPerFoot),
    (x.toYards, 1 / (metersPerFoot * 3)),
    (x.toUsMiles, 1 / (metersPerFoot * 5280)),
    (x.toInternationalMiles, 1 / 1609.344),
    (x.toNauticalMiles, 1 / 1852d),
    (x.toAstronomicalUnits, 1 / 149597870700d),
    (x.toLightYears, 1 / 9460730472580800d),
    (x.toParsecs, 1 / 3.08567758149137e16),
    (x.toSolarRadii, 1 / 6.957e8),
    (x.toNominalSolarRadii, 1 / 6.957e8)
  )


  import LengthConversions._
  override val singleUnitValues = Table(
    ("unitValue", "expected"),
    (angstrom, Angstroms(1)),
    (nanometer, Nanometers(1)),
    (nanometre, Nanometers(1)),
    (micron, Microns(1)),
    (micrometer, Microns(1)),
    (micrometre, Microns(1)),
    (millimeter, Millimeters(1)),
    (millimetre, Millimeters(1)),
    (centimeter, Centimeters(1)),
    (centimetre, Centimeters(1)),
    (decimeter, Decimeters(1)),
    (decimetre, Decimeters(1)),
    (meter, Meters(1)),
    (metre, Meters(1)),
    (decameter, Decameters(1)),
    (decametre, Decameters(1)),
    (hectometer, Hectometers(1)),
    (hectometre, Hectometers(1)),
    (kilometer, Kilometers(1)),
    (kilometre, Kilometers(1)),
    (inch, Inches(1)),
    (foot, Feet(1)),
    (yard, Yards(1)),
    (mile, UsMiles(1)),
    (nauticalMile, NauticalMiles(1)),
    (astronomicalUnit, AstronomicalUnits(1)),
    (lightYear, LightYears(1)),
    (parsec, Parsecs(1)),
    (solarRadius, SolarRadii(1)),
    (nominalSolarRadius, NominalSolarRadii(1))
  )

  private val d = 10d
  override val doubleImplicitConversionValues = Table(
    ("actual", "expected"),
    (d.Å, Angstroms(d)),
    (d.angstroms, Angstroms(d)),
    (d.nm, Nanometers(d)),
    (d.nanometers, Nanometers(d)),
    (d.nanometres, Nanometers(d)),
    (d.µm, Microns(d)),
    (d.microns, Microns(d)),
    (d.mm, Millimeters(d)),
    (d.millimeters, Millimeters(d)),
    (d.millimetres, Millimeters(d)),
    (d.cm, Centimeters(d)),
    (d.centimeters, Centimeters(d)),
    (d.centimetres, Centimeters(d)),
    (d.dm, Decimeters(d)),
    (d.meters, Meters(d)),
    (d.metres, Meters(d)),
    (d.dam, Decameters(d)),
    (d.hm, Hectometers(d)),
    (d.km, Kilometers(d)),
    (d.kilometers, Kilometers(d)),
    (d.kilometres, Kilometers(d)),
    (d.inches, Inches(d)),
    (d.ft, Feet(d)),
    (d.feet, Feet(d)),
    (d.yd, Yards(d)),
    (d.yards, Yards(d)),
    (d.miles, UsMiles(d)),
    (d.nmi, NauticalMiles(d)),
    (d.au, AstronomicalUnits(d)),
    (d.ly, LightYears(d)),
    (d.lightYears, LightYears(d)),
    (d.pc, Parsecs(d)),
    (d.parsecs, Parsecs(d)),
    (d.solarRadii, SolarRadii(d)),
    (d.nominalSolarRadii, NominalSolarRadii(d))
  )

  def implicitStringConversion = { _.toLength }

  checkNumeric

  behavior of "Length"

  it should "return Area when multiplied by Length" in {
    Meters(1) * Meters(1) should be(SquareMeters(1))
  }

  it should "return Volume when multiplied by Area" in {
    Meters(1) * SquareMeters(1) should be(CubicMeters(1))
  }

  it should "return Energy when multiplied by Force" in {
    Meters(1) * Newtons(1) should be(Joules(1))
  }

  it should "return ElectricalConductance when multiplied by Conductivity" in {
    Meters(1) * SiemensPerMeter(1) should be(Siemens(1))
  }

  it should "return Resistivity when multiplied by ElectricalResistance" in {
    Meters(1) * Ohms(1) should be(OhmMeters(1))
  }

  it should "return Velocity when divided by Time" in {
    Meters(1) / Seconds(1) should be(MetersPerSecond(1))
  }

  it should "return Time when divided by Velocity" in {
    Meters(1) / MetersPerSecond(1) should be(Seconds(1))
  }

  it should "return an Area when squared" in {
    Meters(4).squared should be(SquareMeters(16))
  }

  it should "return a Volume when cubed" in {
    Meters(3).cubed should be(CubicMeters(27))
  }
}
