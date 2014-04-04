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
import squants.space.Meters
import squants.time.Seconds
import squants.mass.Kilograms
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class AccelerationSpec extends FlatSpec with Matchers {

  behavior of "Acceleration and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(MetersPerSecondSquared(1).toMetersPerSecondSquared == 1)
    assert(FeetPerSecondSquared(1).toFeetPerSecondSquared == 1)
    assert(UsMilesPerHourSquared(1).toUsMilesPerHourSquared == 1)
    assert(EarthGravities(1).toEarthGravities == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecondSquared(1)
    assert(x.toMetersPerSecondSquared == 1)
    assert(x.toFeetPerSecondSquared == Meters(1).toFeet)
    assert(x.toUsMilesPerHourSquared == Meters(1).toUsMiles / (Seconds(1).toHours * Seconds(1).toHours))
    assert(x.toEarthGravities == 1d / squants.motion.StandardEarthGravity.toMetersPerSecondSquared)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(MetersPerSecondSquared(1).toString(MetersPerSecondSquared) == "1.0 m/s²")
    assert(FeetPerSecondSquared(1).toString(FeetPerSecondSquared) == "1.0 ft/s²")
    assert(UsMilesPerHourSquared(1).toString(UsMilesPerHourSquared) == "1.0 mph²")
    assert(EarthGravities(1).toString(EarthGravities) == "1.0 g")
  }

  it should "return Velocity when multiplied by Time" in {
    assert(MetersPerSecondSquared(1) * Seconds(1) == MetersPerSecond(1))
  }

  it should "Force when multiplied by Mass" in {
    assert(MetersPerSecondSquared(1) * Kilograms(1) == Newtons(1))
  }

  it should "return Jerk when divided by Time" in {
    assert(MetersPerSecondSquared(1) / Seconds(1) == MetersPerSecondCubed(1))
  }

  it should "return Time when divided by Jerk" in {
    assert(MetersPerSecondSquared(1) / MetersPerSecondCubed(1) == Seconds(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = MetersPerSecondSquared(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Acceleration](ser)
    assert(x == des)
  }

  behavior of "VelocityConversions"

  it should "provide implicit conversion from Double" in {
    import AccelerationConversions._

    val d = 10d
    assert(d.mpss == MetersPerSecondSquared(d))
    assert(d.fpss == FeetPerSecondSquared(d))
  }
}
