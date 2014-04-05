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
import squants.space.Radians
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class AngularVelocitySpec extends FlatSpec with Matchers {

  behavior of "AngularVelocity and its Units of Measure"

  it should "create values using UOM factories" in {
    RadiansPerSecond(1).toRadiansPerSecond should be(1)
    DegreesPerSecond(1).toDegreesPerSecond should be(1)
    GradsPerSecond(1).toGradsPerSecond should be(1)
    TurnsPerSecond(1).toTurnsPerSecond should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = RadiansPerSecond(1)
    x.toRadiansPerSecond should be(1)
    x.toDegreesPerSecond should be(Radians(1).toDegrees)
    x.toGradsPerSecond should be(Radians(1).toGradians)
    x.toTurnsPerSecond should be(Radians(1).toTurns)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    RadiansPerSecond(1).toString(RadiansPerSecond) should be("1.0 rad/s")
    DegreesPerSecond(1).toString(DegreesPerSecond) should be("1.0 °/s")
    GradsPerSecond(1).toString(GradsPerSecond) should be("1.0 grad/s")
    TurnsPerSecond(1).toString(TurnsPerSecond) should be("1.0 turns/s")
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = RadiansPerSecond(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[AngularVelocity](ser)
    x should be(des)
  }
}
