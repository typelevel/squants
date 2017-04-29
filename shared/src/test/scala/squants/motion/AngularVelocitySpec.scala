/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import org.scalatest.{ Matchers, FlatSpec }
import squants.space.Radians
import squants.QuantityParseException
import squants.time.Seconds

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

  it should "create values from properly formatted Strings" in {
    AngularVelocity("10.22 rad/s").get should be(RadiansPerSecond(10.22))
    AngularVelocity("10.22 °/s").get should be(DegreesPerSecond(10.22))
    AngularVelocity("10.22 grad/s").get should be(GradsPerSecond(10.22))
    AngularVelocity("10.22 turns/s").get should be(TurnsPerSecond(10.22))
    AngularVelocity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse AngularVelocity", "10.22 zz"))
    AngularVelocity("zz rad/s").failed.get should be(QuantityParseException("Unable to parse AngularVelocity", "zz rad/s"))
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

  it should "return Angle when multiplied by Time" in {
    RadiansPerSecond(1) * Seconds(1) should be(Radians(1))
  }

//  it should ""

  behavior of "AngularVelocityConversions"

  it should "provide aliases for single unit values" in {
    import AngularVelocityConversions._

    radianPerSecond should be(RadiansPerSecond(1))
    degreePerSecond should be(DegreesPerSecond(1))
    gradPerSecond should be(GradsPerSecond(1))
    turnPerSecond should be(TurnsPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import AngularVelocityConversions._

    val d = 10.22d
    d.radiansPerSecond should be(RadiansPerSecond(d))
    d.degreesPerSecond should be(DegreesPerSecond(d))
    d.gradsPerSecond should be(GradsPerSecond(d))
    d.turnsPerSecond should be(TurnsPerSecond(d))
  }

  it should "provide Numeric support" in {
    import AngularVelocityConversions.AngularVelocityNumeric

    val avs = List(RadiansPerSecond(100), RadiansPerSecond(10))
    avs.sum should be(RadiansPerSecond(110))
  }
}
