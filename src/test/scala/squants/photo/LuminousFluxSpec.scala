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
import squants.time.Seconds
import squants.space.{ SquaredRadians, SquareMeters }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousFluxSpec extends FlatSpec with Matchers {

  behavior of "LuminousFlux and its Units of Measure"

  it should "create values using UOM factories" in {
    Lumens(1).toLumens should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Lumens(1)
    x.toLumens should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Lumens(1).toString(Lumens) should be("1.0 lm")
  }

  it should "return LuminousEnergy when multiplied by Time" in {
    Lumens(1) * Seconds(1) should be(LumenSeconds(1))
  }

  it should "return Illuminance when divided by Area" in {
    Lumens(1) / SquareMeters(1) should be(Lux(1))
  }

  it should "return LuminousIntensity when divided by SolidAngle" in {
    Lumens(1) / SquaredRadians(1) should be(Candelas(1))
  }

  it should "return Area when divided by Illuminance" in {
    Lumens(1) / Lux(1) should be(SquareMeters(1))
  }

  it should "return SolidAngle when divided by LuminousIntensity" in {
    Lumens(1) / Candelas(1) should be(SquaredRadians(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Lumens(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[LuminousFlux](ser)
    x should be(des)
  }

  behavior of "LuminousFluxConversions"

  it should "provide aliases for single unit values" in {
    import LuminousFluxConversions._

    lumen should be(Lumens(1))
  }
  it should "provide implicit conversion from Double" in {
    import LuminousFluxConversions._

    val d = 10d
    d.lumens should be(Lumens(d))
  }

  it should "provide Numeric support" in {
    import LuminousFluxConversions.LuminousFluxNumeric

    val lfs = List(Lumens(100), Lumens(1))
    lfs.sum should be(Lumens(101))
  }
}
