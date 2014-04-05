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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class JerkSpec extends FlatSpec with Matchers {

  behavior of "Jerk and its Units of Measure"

  it should "create values using UOM factories" in {
    MetersPerSecondCubed(1).toMetersPerSecondCubed should be(1)
    FeetPerSecondCubed(1).toFeetPerSecondCubed should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecondCubed(1)
    x.toMetersPerSecondCubed should be(1)
    x.toFeetPerSecondCubed should be(Meters(1).toFeet)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    MetersPerSecondCubed(1).toString(MetersPerSecondCubed) should be("1.0 m/s³")
    FeetPerSecondCubed(1).toString(FeetPerSecondCubed) should be("1.0 ft/s³")
  }

  it should "return Acceleration when multiplied by Time" in {
    MetersPerSecondCubed(1) * Seconds(1) should be(MetersPerSecondSquared(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = MetersPerSecondCubed(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Jerk](ser)
    x should be(des)
  }
}
