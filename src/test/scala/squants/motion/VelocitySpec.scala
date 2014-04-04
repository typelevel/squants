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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VelocitySpec extends FlatSpec with Matchers {

  behavior of "Velocity and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(MetersPerSecond(1).toMetersPerSeconds == 1)
    assert(FeetPerSecond(1).toFeetPerSecond == 1)
    assert(KilometersPerHour(1).toKilometersPerHour == 1)
    assert(UsMilesPerHour(1).toUsMilesPerHour == 1)
    assert(InternationalMilesPerHour(1).toInternationalMilesPerHour == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecond(1)
    assert(x.toMetersPerSeconds == 1)
    assert(x.toFeetPerSecond == Meters(1).toFeet)
    assert(x.toKilometersPerHour == Meters(1).toKilometers / Seconds(1).toHours)
    assert(x.toUsMilesPerHour == Meters(1).toUsMiles / Seconds(1).toHours)
    assert(x.toInternationalMilesPerHour == Meters(1).toInternationalMiles / Seconds(1).toHours)
    assert(x.toKnots == Meters(1).toNauticalMiles / Seconds(1).toHours)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(MetersPerSecond(1).toString(MetersPerSecond) == "1.0 m/s")
    assert(FeetPerSecond(1).toString(FeetPerSecond) == "1.0 ft/s")
    assert(KilometersPerHour(1).toString(KilometersPerHour) == "1.0 km/s")
    assert(UsMilesPerHour(1).toString(UsMilesPerHour) == "1.0 mph")
    assert(InternationalMilesPerHour(1).toString(InternationalMilesPerHour) == "1.0 mph")
    assert(Knots(1).toString(Knots) == "1.0 kn")
  }

  it should "return Distance when multiplied by Time" in {
    assert(MetersPerSecond(1) * Seconds(1) == Meters(1))
  }

  it should "return Momentum when multiplied by Mass" in {
    assert(MetersPerSecond(1) * Kilograms(1) == NewtonSeconds(1))
  }

  it should "return Acceleration when divided by Time" in {
    assert(MetersPerSecond(1) / Seconds(1) == MetersPerSecondSquared(1))
  }

  it should "return a dimensionless ration when divided by an Acceleration" in {
    assert(MetersPerSecond(10) / MetersPerSecond(4) == 2.5)
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = MetersPerSecond(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Velocity](ser)
    assert(x == des)
  }

  behavior of "VelocityConversions"

  it should "provide implicit conversion from Double" in {
    import VelocityConversions._

    val d = 10d
    assert(d.fps == FeetPerSecond(d))
    assert(d.mps == MetersPerSecond(d))
    assert(d.kph == KilometersPerHour(d))
    assert(d.mph == UsMilesPerHour(d))
    assert(d.knots == Knots(d))
  }
}
