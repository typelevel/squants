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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminanceSpec extends FlatSpec with Matchers {

  behavior of "Luminance and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(CandelasPerSquareMeter(1).toCandelasPerSquareMeters == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CandelasPerSquareMeter(1)
    assert(x.toCandelasPerSquareMeters == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(CandelasPerSquareMeter(1).toString == "1.0 cd/m²")
  }

  it should "return LuminousIntensity when multiplied by Area" in {
    assert(CandelasPerSquareMeter(1) * SquareMeters(1) == Candelas(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = CandelasPerSquareMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Luminance](ser)
    assert(x == des)
  }
}
