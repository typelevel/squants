/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import squants.QuantityParseException
import squants.space.SquareMeters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class IlluminanceSpec extends AnyFlatSpec with Matchers {

  behavior of "Illuminance and its Units of Measure"

  it should "create values using UOM factories" in {
    Lux(1).toLux should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Illuminance("10.22 lx").get should be(Lux(10.22))
    Illuminance("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Illuminance", "10.45 zz"))
    Illuminance("zz lx").failed.get should be(QuantityParseException("Unable to parse Illuminance", "zz lx"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Lux(1)
    x.toLux should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Lux(1).toString(Lux) should be("1.0 lx")
  }

  it should "return LuminousFlux when multiplied by Area" in {
    Lux(1) * SquareMeters(1) should be(Lumens(1))
  }

  behavior of "IlluminanceConversions"

  it should "provide aliases for single unit values" in {
    import IlluminanceConversions._

    lux should be(Lux(1))
  }

  it should "provide implicit conversion from Double" in {
    import IlluminanceConversions._

    val d = 10d
    d.lux should be(Lux(d))
  }

  it should "provide Numeric support" in {
    import IlluminanceConversions.IlluminanceNumeric

    val ls = List(Lux(100), Lux(10))
    ls.sum should be(Lux(110))
  }
}
