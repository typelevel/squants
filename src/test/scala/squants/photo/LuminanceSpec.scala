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
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminanceSpec extends FlatSpec with Matchers {

  behavior of "Luminance and its Units of Measure"

  it should "create values using UOM factories" in {
    CandelasPerSquareMeter(1).toCandelasPerSquareMeters should be(1)
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
