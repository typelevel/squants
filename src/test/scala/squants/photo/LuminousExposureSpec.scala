/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.time.Seconds
import squants.QuantityParseException

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousExposureSpec extends FlatSpec with Matchers {

  behavior of "LuminousExposure and its Units of Measure"

  it should "create values using UOM factories" in {
    LuxSeconds(1).toLuxSeconds should be(1)
  }

  it should "create values from properly formatted Strings" in {
    LuminousExposure("10.22 lx⋅s").get should be(LuxSeconds(10.22))
    LuminousExposure("10.45 zz").failed.get should be(QuantityParseException("Unable to parse LuminousExposure", "10.45 zz"))
    LuminousExposure("zz lx⋅s").failed.get should be(QuantityParseException("Unable to parse LuminousExposure", "zz lx⋅s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = LuxSeconds(1)
    x.toLuxSeconds should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    LuxSeconds(1).toString(LuxSeconds) should be("1.0 lx⋅s")
  }

  it should "return Illuminance when divided by Time" in {
    LuxSeconds(1) / Seconds(1) should be(Lux(1))
  }

  it should "return Time when divided by Illuminance" in {
    LuxSeconds(1) / Lux(1) should be(Seconds(1))
  }

  behavior of "LuminousExposureConversions"

  it should "provide aliases for single unit values" in {
    import LuminousExposureConversions._

    luxSecond should be(LuxSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import LuminousExposureConversions._

    val d = 10.22d
    d.luxSeconds should be(LuxSeconds(d))
  }

  it should "provide Numeric support" in {
    import LuminousExposureConversions.LuminousExposureNumeric

    val ls = List(LuxSeconds(10), LuxSeconds(20))
    ls.sum should be(LuxSeconds(30))
  }
}
