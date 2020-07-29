/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.time.Seconds
import squants.space.Meters
import squants.mass.Kilograms
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VelocitySpec extends AnyFlatSpec with Matchers {

  behavior of "Velocity and its Units of Measure"

  it should "create values using UOM factories" in {
    MetersPerSecond(1).toMetersPerSecond should be(1)
    FeetPerSecond(1).toFeetPerSecond should be(1)
    MillimetersPerSecond(1).toMillimetersPerSecond should be(1)
    KilometersPerSecond(1).toKilometersPerSecond should be(1)
    KilometersPerHour(1).toKilometersPerHour should be(1)
    UsMilesPerHour(1).toUsMilesPerHour should be(1)
    InternationalMilesPerHour(1).toInternationalMilesPerHour should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Velocity("10.22 m/s").get should be(MetersPerSecond(10.22))
    Velocity("10.22 ft/s").get should be(FeetPerSecond(10.22))
    Velocity("10.22 mm/s").get should be(MillimetersPerSecond(10.22))
    Velocity("10.22 km/s").get should be(KilometersPerSecond(10.22))
    Velocity("10.22 km/h").get should be(KilometersPerHour(10.22))
    Velocity("10.22 mph").get should be(UsMilesPerHour(10.22))
    Velocity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Velocity", "10.22 zz"))
    Velocity("zz m/s").failed.get should be(QuantityParseException("Unable to parse Velocity", "zz m/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val tolerance: Double = 0.000000000000001
    val x = MetersPerSecond(1)
    x.toMetersPerSecond should be(1)
    x.toFeetPerSecond should be(Meters(1).toFeet)
    x.toMillimetersPerSecond should be(Meters(1).toMillimeters)
    x.toKilometersPerSecond should be(Meters(1).toKilometers)
    x.toKilometersPerHour should be(Meters(1).toKilometers / Seconds(1).toHours +- tolerance)
    x.toUsMilesPerHour should be(Meters(1).toUsMiles / Seconds(1).toHours +- tolerance)
    x.toInternationalMilesPerHour should be(Meters(1).toInternationalMiles / Seconds(1).toHours +- tolerance)
    x.toKnots should be(Meters(1).toNauticalMiles / Seconds(1).toHours +- tolerance)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    MetersPerSecond(1).toString(MetersPerSecond) should be("1.0 m/s")
    FeetPerSecond(1).toString(FeetPerSecond) should be("1.0 ft/s")
    MillimetersPerSecond(1).toString(MillimetersPerSecond) should be("1.0 mm/s")
    KilometersPerSecond(1).toString(KilometersPerSecond) should be("1.0 km/s")
    KilometersPerHour(1).toString(KilometersPerHour) should be("1.0 km/h")
    UsMilesPerHour(1).toString(UsMilesPerHour) should be("1.0 mph")
    InternationalMilesPerHour(1).toString(InternationalMilesPerHour) should be("1.0 imph")
    Knots(1).toString(Knots) should be("1.0 kn")
  }

  it should "return Distance when multiplied by Time" in {
    MetersPerSecond(1) * Seconds(1) should be(Meters(1))
  }

  it should "return Momentum when multiplied by Mass" in {
    MetersPerSecond(1) * Kilograms(1) should be(NewtonSeconds(1))
  }

  it should "return Acceleration when divided by Time" in {
    MetersPerSecond(1) / Seconds(1) should be(MetersPerSecondSquared(1))
  }

  it should "return a dimensionless ration when divided by an Acceleration" in {
    MetersPerSecond(10) / MetersPerSecond(4) should be(2.5)
  }

  behavior of "VelocityConversions"

  it should "provide aliases for single unit values" in {
    import VelocityConversions._

    meterPerSecond should be(MetersPerSecond(1))
    footPerSecond should be(FeetPerSecond(1))
    millimeterPerSecond should be(MillimetersPerSecond(1))
    kilometerPerSecond should be(KilometersPerSecond(1))
    kilometerPerHour should be(KilometersPerHour(1))
    milePerHour should be(UsMilesPerHour(1))
    knot should be(Knots(1))
  }

  it should "provide implicit conversion from Double" in {
    import VelocityConversions._

    val d = 10d
    d.fps should be(FeetPerSecond(d))
    d.mps should be(MetersPerSecond(d))
    d.kps should be(KilometersPerSecond(d))
    d.kph should be(KilometersPerHour(d))
    d.mph should be(UsMilesPerHour(d))
    d.knots should be(Knots(d))
  }

  it should "provide Numeric support" in {
    import VelocityConversions.VelocityNumeric

    val vs = List(MetersPerSecond(100), MetersPerSecond(1))
    vs.sum should be(MetersPerSecond(101))
  }
}
