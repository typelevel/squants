/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import squants.QuantityParseException
import squants.space.{ SquareMeters, SquaredRadians }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousIntensitySpec extends AnyFlatSpec with Matchers {

  behavior of "LuminousIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    Candelas(1).toCandelas should be(1)
  }

  it should "create values from properly formatted Strings" in {
    LuminousIntensity("10.22 cd").get should be(Candelas(10.22))
    LuminousIntensity("10.45 zz").failed.get should be(QuantityParseException("Unable to parse LuminousIntensity", "10.45 zz"))
    LuminousIntensity("zz cd").failed.get should be(QuantityParseException("Unable to parse LuminousIntensity", "zz cd"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Candelas(1)
    x.toCandelas should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Candelas(1).toString(Candelas) should be("1.0 cd")
  }

  it should "return LuminousFlux when multiplied by SolidAngle" in {
    Candelas(1) * SquaredRadians(1) should be(Lumens(1))
  }

  it should "return Luminance when divided by Area" in {
    Candelas(1) / SquareMeters(1) should be(CandelasPerSquareMeter(1))
  }

  it should "return Area when divided by Luminance" in {
    Candelas(1) / CandelasPerSquareMeter(1) should be(SquareMeters(1))
  }

  behavior of "LuminousIntensityConversions"

  it should "provide aliases for single unit values" in {
    import LuminousIntensityConversions._

    candela should be(Candelas(1))
  }

  it should "provide implicit conversion from Double" in {
    import LuminousIntensityConversions._

    val d = 10d
    d.candelas should be(Candelas(d))
  }

  it should "provide Numeric support" in {
    import LuminousIntensityConversions.LuminousIntensityNumeric

    val lis = List(Candelas(100), Candelas(1))
    lis.sum should be(Candelas(101))
  }
}
