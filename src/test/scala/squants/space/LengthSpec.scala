/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalatest.{ Matchers, FlatSpec }
import squants.MetricSystem
import squants.motion.{ MetersPerSecond, Newtons }
import squants.energy.Joules
import squants.time.Seconds
import squants.electro.{ Ohms, OhmMeters, Siemens, SiemensPerMeter }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LengthSpec extends FlatSpec with Matchers {

  behavior of "Length and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(Meters(1).toMeters == 1)
    assert(Microns(1).toMicrons == 1)
    assert(Millimeters(1).toMillimeters == 1)
    assert(Centimeters(1).toCentimeters == 1)
    assert(Decimeters(1).toDecimeters == 1)
    assert(Decameters(1).toDecameters == 1)
    assert(Hectometers(1).toHectometers == 1)
    assert(Kilometers(1).toKilometers == 1)

    assert(Inches(1).toInches == 1)
    assert(Feet(1).toFeet == 1)
    assert(Yards(1).toYards == 1)
    assert(UsMiles(1).toUsMiles == 1)
    assert(InternationalMiles(1).toInternationalMiles == 1)
    assert(NauticalMiles(1).toNauticalMiles == 1)
    assert(AstronomicalUnits(1).toAstronomicalUnits == 1)
    assert(LightYears(1).toLightYears == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Meters(1)
    assert(x.toMeters == 1)
    assert(x.toMicrons == 1 / MetricSystem.Micro)
    assert(x.toMillimeters == 1 / MetricSystem.Milli)
    assert(x.toCentimeters == 1 / MetricSystem.Centi)
    assert(x.toDecimeters == 1 / MetricSystem.Deci)
    assert(x.toDecameters == 1 / MetricSystem.Deca)
    assert(x.toHectometers == 1 / MetricSystem.Hecto)
    assert(x.toKilometers == 1 / MetricSystem.Kilo)

    val metersPerFoot = 0.3048006096
    assert(x.toInches == 1 / (metersPerFoot / 12))
    assert(x.toFeet == 1 / metersPerFoot)
    assert(x.toYards == 1 / (metersPerFoot * 3))
    assert(x.toUsMiles == 1 / (metersPerFoot * 5280))
    assert(x.toInternationalMiles == 1 / 1609.344)
    assert(x.toNauticalMiles == 1 / 1852d)
    assert(x.toAstronomicalUnits == 1 / 149597870700d)
    assert(x.toLightYears == 1 / 9460730472580800d)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Meters(1).toString(Meters) == "1.0 m")
    assert(Microns(1).toString(Microns) == "1.0 µm")
    assert(Millimeters(1).toString(Millimeters) == "1.0 mm")
    assert(Centimeters(1).toString(Centimeters) == "1.0 cm")
    assert(Decimeters(1).toString(Decimeters) == "1.0 dm")
    assert(Decameters(1).toString(Decameters) == "1.0 dam")
    assert(Hectometers(1).toString(Hectometers) == "1.0 hm")
    assert(Kilometers(1).toString(Kilometers) == "1.0 km")

    assert(Inches(1).toString(Inches) == "1.0 in")
    assert(Feet(1).toString(Feet) == "1.0 ft")
    assert(Yards(1).toString(Yards) == "1.0 yd")
    assert(UsMiles(1).toString(UsMiles) == "1.0 mi")
    assert(InternationalMiles(1).toString(InternationalMiles) == "1.0 mile")
    assert(NauticalMiles(1).toString(NauticalMiles) == "1.0 nmi")
    assert(AstronomicalUnits(1).toString(AstronomicalUnits) == "1.0 au")
    assert(LightYears(1).toString(LightYears) == "1.0 ly")
  }

  it should "return Area when multiplied by Length" in {
    assert(Meters(1) * Meters(1) == SquareMeters(1))
  }

  it should "return Volume when multiplied by Area" in {
    assert(Meters(1) * SquareMeters(1) == CubicMeters(1))
  }

  it should "return Energy when multiplied by Force" in {
    assert(Meters(1) * Newtons(1) == Joules(1))
  }

  it should "return ElectricalConductance when multiplied by Conductivity" in {
    assert(Meters(1) * SiemensPerMeter(1) == Siemens(1))
  }

  it should "return Resistivity when multiplied by ElectricalResistance" in {
    assert(Meters(1) * Ohms(1) == OhmMeters(1))
  }

  it should "return Velocity when divided by Time" in {
    assert(Meters(1) / Seconds(1) == MetersPerSecond(1))
  }

  it should "return Time when divided by Velocity" in {
    assert(Meters(1) / MetersPerSecond(1) == Seconds(1))
  }

  it should "return an Area when squared" in {
    assert(Meters(4).squared == SquareMeters(16))
  }

  it should "return a Volume when cubed" in {
    assert(Meters(3).cubed == CubicMeters(27))
  }

  behavior of "LengthConversions"

  it should "provide aliases for single unit values" in {
    import LengthConversions._

    assert(micron == Microns(1))
    assert(millimeter == Millimeters(1))
    assert(millimetre == Millimeters(1))
    assert(centimeter == Centimeters(1))
    assert(centimetre == Centimeters(1))
    assert(decimeter == Decimeters(1))
    assert(decimetre == Decimeters(1))
    assert(meter == Meters(1))
    assert(metre == Meters(1))
    assert(decameter == Decameters(1))
    assert(decametre == Decameters(1))
    assert(hectometer == Hectometers(1))
    assert(hectometre == Hectometers(1))
    assert(kilometer == Kilometers(1))
    assert(kilometre == Kilometers(1))
    assert(inch == Inches(1))
    assert(foot == Feet(1))
    assert(yard == Yards(1))
    assert(mile == UsMiles(1))
    assert(nauticalMile == NauticalMiles(1))
    assert(astronomicalUnit == AstronomicalUnits(1))
    assert(lightYear == LightYears(1))
  }

  it should "provide implicit conversion from Double" in {
    import LengthConversions._

    val d = 10d
    assert(d.µm == Microns(d))
    assert(d.microns == Microns(d))
    assert(d.mm == Millimeters(d))
    assert(d.millimeters == Millimeters(d))
    assert(d.millimetres == Millimeters(d))
    assert(d.cm == Centimeters(d))
    assert(d.centimeters == Centimeters(d))
    assert(d.centimetres == Centimeters(d))
    assert(d.dm == Decimeters(d))
    assert(d.meters == Meters(d))
    assert(d.metres == Meters(d))
    assert(d.dam == Decameters(d))
    assert(d.hm == Hectometers(d))
    assert(d.km == Kilometers(d))
    assert(d.kilometers == Kilometers(d))
    assert(d.kilometres == Kilometers(d))
    assert(d.inches == Inches(d))
    assert(d.ft == Feet(d))
    assert(d.feet == Feet(d))
    assert(d.yd == Yards(d))
    assert(d.yards == Yards(d))
    assert(d.miles == UsMiles(d))
    assert(d.nmi == NauticalMiles(d))
    assert(d.au == AstronomicalUnits(d))
    assert(d.ly == LightYears(d))
    assert(d.lightYears == LightYears(d))
  }

  it should "provide implicit conversion from String" in {
    import LengthConversions._

    assert("10.33 mm".toLength.right.get == Millimeters(10.33))
    assert("10.33 cm".toLength.right.get == Centimeters(10.33))
    assert("10.33 m".toLength.right.get == Meters(10.33))
    assert("10.33 km".toLength.right.get == Kilometers(10.33))
    assert("10.33 in".toLength.right.get == Inches(10.33))
    assert("10.33 ft".toLength.right.get == Feet(10.33))
    assert("10.33 yd".toLength.right.get == Yards(10.33))
    assert("10.33 mi".toLength.right.get == UsMiles(10.33))
    assert("10.33 nmi".toLength.right.get == NauticalMiles(10.33))
    assert("10.33 au".toLength.right.get == AstronomicalUnits(10.33))
    assert("10.33 ly".toLength.right.get == LightYears(10.33))
    assert("10.33 zz".toLength.left.get == "Unable to parse 10.33 zz as Length")
  }
}
