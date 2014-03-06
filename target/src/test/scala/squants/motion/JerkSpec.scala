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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class JerkSpec extends FlatSpec with Matchers {

  behavior of "Jerk and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(MetersPerSecondCubed(1).toMetersPerSecondCubed == 1)
    assert(FeetPerSecondCubed(1).toFeetPerSecondCubed == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecondCubed(1)
    assert(x.toMetersPerSecondCubed == 1)
    assert(x.toFeetPerSecondCubed == Meters(1).toFeet)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(MetersPerSecondCubed(1).toString(MetersPerSecondCubed) == "1.0 m/s³")
    assert(FeetPerSecondCubed(1).toString(FeetPerSecondCubed) == "1.0 ft/s³")
  }

  it should "return Acceleration when multiplied by Time" in {
    assert(MetersPerSecondCubed(1) * Seconds(1) == MetersPerSecondSquared(1))
  }
}
