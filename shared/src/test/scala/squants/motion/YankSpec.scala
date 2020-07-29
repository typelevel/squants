/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.time.Seconds
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class YankSpec extends AnyFlatSpec with Matchers {

  behavior of "Yank and its Units of Measure"

  it should "create values using UOM factories" in {
    NewtonsPerSecond(1).toNewtonsPerSecond should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Yank("10.22 N/s").get should be(NewtonsPerSecond(10.22))
    Yank("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Yank", "10.22 zz"))
    Yank("zz N/s").failed.get should be(QuantityParseException("Unable to parse Yank", "zz N/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = NewtonsPerSecond(1)
    x.toNewtonsPerSecond should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    NewtonsPerSecond(1).toString(NewtonsPerSecond) should be("1.0 N/s")
  }

  it should "return Force when multiplied by Time" in {
    NewtonsPerSecond(1) * Seconds(10) should be(Newtons(10))
    NewtonsPerSecond(10) * Seconds(1) should be(Newtons(10))
  }

  behavior of "YankConversions"

  it should "provide aliases for single unit values" in {
    import YankConversions._

    newtonPerSecond should be(NewtonsPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import YankConversions._

    val d = 10.22d
    d.newtonsPerSecond should be(NewtonsPerSecond(d))
  }

  it should "provide Numeric support" in {
    import YankConversions.YankNumeric

    val ys = List(NewtonsPerSecond(100), NewtonsPerSecond(10))
    ys.sum should be(NewtonsPerSecond(110))
  }
}
