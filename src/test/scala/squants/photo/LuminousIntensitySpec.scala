/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.{ SquaredRadians, SquareMeters }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousIntensitySpec extends FlatSpec with Matchers {

  behavior of "LuminousIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    Candelas(1).toCandelas should be(1)
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

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Candelas(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[LuminousIntensity](ser)
    x should be(des)
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
}
