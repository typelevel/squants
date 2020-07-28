/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import squants.QuantityParseException
import squants.time.Seconds
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousEnergySpec extends AnyFlatSpec with Matchers {

  behavior of "LuminousEnergy and its Units of Measure"

  it should "create values using UOM factories" in {
    LumenSeconds(1).toLumenSeconds should be(1)
  }

  it should "create values from properly formatted Strings" in {
    LuminousEnergy("10.22 lm⋅s").get should be(LumenSeconds(10.22))
    LuminousEnergy("10.45 zz").failed.get should be(QuantityParseException("Unable to parse LuminousEnergy", "10.45 zz"))
    LuminousEnergy("zz lm⋅s").failed.get should be(QuantityParseException("Unable to parse LuminousEnergy", "zz lm⋅s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = LumenSeconds(1)
    x.toLumenSeconds should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    LumenSeconds(1).toString(LumenSeconds) should be("1.0 lm⋅s")
  }

  it should "return LuminousFlux when divided by Time" in {
    LumenSeconds(1) / Seconds(1) should be(Lumens(1))
  }

  it should "return Time when divided by LuminousFlux" in {
    LumenSeconds(1) / Lumens(1) should be(Seconds(1))
  }

  behavior of "LuminousEnergyConversions"

  it should "provide aliases for single unit values" in {
    import LuminousEnergyConversions._

    lumenSecond should be(LumenSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import LuminousEnergyConversions._

    val d = 10d
    d.lumenSeconds should be(LumenSeconds(d))
  }

  it should "provide Numeric support" in {
    import LuminousEnergyConversions.LuminousEnergyNumeric

    val les = List(LumenSeconds(100), LumenSeconds(1))
    les.sum should be(LumenSeconds(101))
  }
}
