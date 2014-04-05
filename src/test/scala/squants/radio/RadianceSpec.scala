/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

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
class RadianceSpec extends FlatSpec with Matchers {

  behavior of "Radiance and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerSteradianPerSquareMeter(1).toWattsPerSteradianPerSquareMeter should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradianPerSquareMeter(1)
    x.toWattsPerSteradianPerSquareMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSteradianPerSquareMeter(1).toString should be("1.0 W/sr/m²")
  }

  it should "return RadiantIntensity when multiplied by Area" in {
    WattsPerSteradianPerSquareMeter(1) * SquareMeters(1) should be(WattsPerSteradian(1))
  }

  it should "return Area when divided by RadiantIntensity" in {
    WattsPerSteradianPerSquareMeter(1) / WattsPerSteradian(1) should be(SquareMeters(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = WattsPerSteradianPerSquareMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Radiance](ser)
    x should be(des)
  }
}
