/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import scala.language.postfixOps
import org.scalatest.{ Matchers, FlatSpec }
import squants.mass.Kilograms
import squants.time.Seconds
import squants.QuantityParseException

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassFlowSpec extends FlatSpec with Matchers {

  behavior of "MassFlow and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerSecond(10.22).toKilogramsPerSecond should be(10.22)
    PoundsPerSecond(10.22).toPoundsPerSecond should be(10.22 +- 1e-15)
    KilopoundsPerHour(10.22).toKilopoundsPerHour should be(10.22 +- 1e-15)
  }

  it should "create values from properly formatted Strings" in {
    MassFlow("10.22 kg/s").get should be(KilogramsPerSecond(10.22))
    MassFlow("10.22 lbs/s").get should be(PoundsPerSecond(10.22))
    MassFlow("10.22 Mlbs/hr").get should be(KilopoundsPerHour(10.22))
    MassFlow("10.22 zz").failed.get should be(QuantityParseException("Unable to parse MassFlow", "10.22 zz"))
    MassFlow("zz kg/s").failed.get should be(QuantityParseException("Unable to parse MassFlow", "zz kg/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSecond(1)
    x.toKilogramsPerSecond should be(1)
    x.toPoundsPerSecond should be(1 / PoundsPerSecond.conversionFactor)
    x.toKilopoundsPerHour should be(1 / (PoundsPerSecond.conversionFactor * 1000 / 3600))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerSecond(1).toString should be("1.0 kg/s")
  }

  it should "return Mass when multiplied by Time" in {
    KilogramsPerSecond(1) * Seconds(1) should be(Kilograms(1))
  }

  behavior of "MassFlowConversions"

  it should "provide aliases for single unit values" in {
    import MassFlowConversions._

    kilogramPerSecond should be(KilogramsPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import MassFlowConversions._

    val d = 10.22d
    d.kilogramsPerSecond should be(KilogramsPerSecond(d))
  }

  it should "provide Numeric support" in {
    import MassFlowConversions.MassFlowNumeric

    val mfrs = List(KilogramsPerSecond(100), KilogramsPerSecond(10))
    mfrs.sum should be(KilogramsPerSecond(110))
  }
}
