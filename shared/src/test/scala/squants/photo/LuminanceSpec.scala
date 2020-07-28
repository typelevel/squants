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
class LuminanceSpec extends AnyFlatSpec with Matchers {

  behavior of "Luminance and its Units of Measure"

  it should "create values using UOM factories" in {
    CandelasPerSquareMeter(1).toCandelasPerSquareMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Luminance("10.22 cd/m²").get should be(CandelasPerSquareMeter(10.22))
    Luminance("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Luminance", "10.45 zz"))
    Luminance("zz cd/m²").failed.get should be(QuantityParseException("Unable to parse Luminance", "zz cd/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CandelasPerSquareMeter(1)
    x.toCandelasPerSquareMeters should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CandelasPerSquareMeter(1).toString should be("1.0 cd/m²")
  }

  it should "return LuminousIntensity when multiplied by Area" in {
    CandelasPerSquareMeter(1) * SquareMeters(1) should be(Candelas(1))
  }

  behavior of "LuminanceConversions"

  it should "provide aliases for single unit values" in {
    import LuminanceConversions._

    candelaPerSquareMeter should be(CandelasPerSquareMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import LuminanceConversions._

    val d = 10d
    d.candelasPerSquareMeter should be(CandelasPerSquareMeter(d))
  }

  it should "provide Numeric support" in {
    import LuminanceConversions.LuminanceNumeric

    val ls = List(CandelasPerSquareMeter(100), CandelasPerSquareMeter(10))
    ls.sum should be(CandelasPerSquareMeter(110))
  }
}
