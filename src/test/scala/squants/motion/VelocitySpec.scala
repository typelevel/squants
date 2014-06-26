/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.time.Seconds
import squants.space.Meters
import squants.mass.Kilograms

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VelocitySpec extends FlatSpec with Matchers {

  behavior of "Velocity and its Units of Measure"

  it should "create values using UOM factories" in {
    MetersPerSecond(1).toMetersPerSeconds should be(1)
    FeetPerSecond(1).toFeetPerSecond should be(1)
    KilometersPerHour(1).toKilometersPerHour should be(1)
    UsMilesPerHour(1).toUsMilesPerHour should be(1)
    InternationalMilesPerHour(1).toInternationalMilesPerHour should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecond(1)
    x.toMetersPerSeconds should be(1)
    x.toFeetPerSecond should be(Meters(1).toFeet)
    x.toKilometersPerHour should be(Meters(1).toKilometers / Seconds(1).toHours)
    x.toUsMilesPerHour should be(Meters(1).toUsMiles / Seconds(1).toHours)
    x.toInternationalMilesPerHour should be(Meters(1).toInternationalMiles / Seconds(1).toHours)
    x.toKnots should be(Meters(1).toNauticalMiles / Seconds(1).toHours)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    MetersPerSecond(1).toString(MetersPerSecond) should be("1.0 m/s")
    FeetPerSecond(1).toString(FeetPerSecond) should be("1.0 ft/s")
    KilometersPerHour(1).toString(KilometersPerHour) should be("1.0 km/s")
    UsMilesPerHour(1).toString(UsMilesPerHour) should be("1.0 mph")
    InternationalMilesPerHour(1).toString(InternationalMilesPerHour) should be("1.0 mph")
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
    kilometerPerSecond should be(KilogramsPerSecond(1))
    milePerHour should be(UsMilesPerHour(1))
    knot should be(Knots(1))
  }

  it should "provide implicit conversion from Double" in {
    import VelocityConversions._

    val d = 10d
    d.fps should be(FeetPerSecond(d))
    d.mps should be(MetersPerSecond(d))
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
