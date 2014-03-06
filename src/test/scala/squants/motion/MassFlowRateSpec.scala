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
import squants.mass.Kilograms
import squants.time.Seconds

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassFlowRateSpec extends FlatSpec with Matchers {

  behavior of "MassFlowRate and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(KilogramsPerSecond(1).toKilogramsPerSecond == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSecond(1)
    assert(x.toKilogramsPerSecond == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(KilogramsPerSecond(1).toString == "1.0 kg/s")
  }

  it should "return Mass when multiplied by Time" in {
    assert(KilogramsPerSecond(1) * Seconds(1) == Kilograms(1))
  }
}
