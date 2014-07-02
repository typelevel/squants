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
import squants.QuantityStringParseException

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassFlowRateSpec extends FlatSpec with Matchers {

  behavior of "MassFlowRate and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerSecond(1).toKilogramsPerSecond should be(1)
  }

  it should "create values from properly formatted Strings" in {
    MassFlowRate("10.22 kg/s").get should be(KilogramsPerSecond(10.22))
    MassFlowRate("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse MassFlowRate", "10.22 zz"))
    MassFlowRate("zz kg/s").failed.get should be(QuantityStringParseException("Unable to parse MassFlowRate", "zz kg/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSecond(1)
    x.toKilogramsPerSecond should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerSecond(1).toString should be("1.0 kg/s")
  }

  it should "return Mass when multiplied by Time" in {
    KilogramsPerSecond(1) * Seconds(1) should be(Kilograms(1))
  }

  behavior of "MassFlowRateConversions"

  it should "provide aliases for single unit values" in {
    import MassFlowRateConversions._

    kilogramPerSecond should be(KilogramsPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import MassFlowRateConversions._

    val d = 10.22d
    d.kilogramsPerSecond should be(KilogramsPerSecond(d))
  }

  it should "provide Numeric support" in {
    import MassFlowRateConversions.MassFlowRateNumeric

    val mfrs = List(KilogramsPerSecond(100), KilogramsPerSecond(10))
    mfrs.sum should be(KilogramsPerSecond(110))
  }
}
