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
    MetersPerSecondSquared(1).toMetersPerSecondSquared should be(1)
    FeetPerSecondSquared(1).toFeetPerSecondSquared should be(1)
    UsMilesPerHourSquared(1).toUsMilesPerHourSquared should be(1)
    EarthGravities(1).toEarthGravities should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecondSquared(1)
    x.toMetersPerSecondSquared should be(1)
    x.toFeetPerSecondSquared should be(Meters(1).toFeet)
    x.toUsMilesPerHourSquared should be(Meters(1).toUsMiles / (Seconds(1).toHours * Seconds(1).toHours))
    x.toEarthGravities should be(1d / squants.motion.StandardEarthGravity.toMetersPerSecondSquared)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    MetersPerSecondSquared(1).toString(MetersPerSecondSquared) should be("1.0 m/s²")
    FeetPerSecondSquared(1).toString(FeetPerSecondSquared) should be("1.0 ft/s²")
    UsMilesPerHourSquared(1).toString(UsMilesPerHourSquared) should be("1.0 mph²")
    EarthGravities(1).toString(EarthGravities) should be("1.0 g")
  }

  it should "return Velocity when multiplied by Time" in {
    MetersPerSecondSquared(1) * Seconds(1) should be(MetersPerSecond(1))
  }

  it should "Force when multiplied by Mass" in {
    MetersPerSecondSquared(1) * Kilograms(1) should be(Newtons(1))
  }

  it should "return Jerk when divided by Time" in {
    MetersPerSecondSquared(1) / Seconds(1) should be(MetersPerSecondCubed(1))
  }

  it should "return Time when divided by Jerk" in {
    MetersPerSecondSquared(1) / MetersPerSecondCubed(1) should be(Seconds(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = MetersPerSecondSquared(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Acceleration](ser)
    x should be(des)
  }

  behavior of "VelocityConversions"

  it should "provide implicit conversion from Double" in {
    import AccelerationConversions._

    val d = 10d
    d.mpss should be(MetersPerSecondSquared(d))
    d.fpss should be(FeetPerSecondSquared(d))
  }
}
