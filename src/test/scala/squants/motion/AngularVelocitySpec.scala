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
    assert(RadiansPerSecond(1).toRadiansPerSecond == 1)
    assert(DegreesPerSecond(1).toDegreesPerSecond == 1)
    assert(GradsPerSecond(1).toGradsPerSecond == 1)
    assert(TurnsPerSecond(1).toTurnsPerSecond == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = RadiansPerSecond(1)
    assert(x.toRadiansPerSecond == 1)
    assert(x.toDegreesPerSecond == Radians(1).toDegrees)
    assert(x.toGradsPerSecond == Radians(1).toGradians)
    assert(x.toTurnsPerSecond == Radians(1).toTurns)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(RadiansPerSecond(1).toString(RadiansPerSecond) == "1.0 rad/s")
    assert(DegreesPerSecond(1).toString(DegreesPerSecond) == "1.0 °/s")
    assert(GradsPerSecond(1).toString(GradsPerSecond) == "1.0 grad/s")
    assert(TurnsPerSecond(1).toString(TurnsPerSecond) == "1.0 turns/s")
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = RadiansPerSecond(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[AngularVelocity](ser)
    assert(x == des)
  }
}
