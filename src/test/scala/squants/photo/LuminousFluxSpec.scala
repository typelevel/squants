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
    assert(Lumens(1).toLumens == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Lumens(1)
    assert(x.toLumens == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Lumens(1).toString(Lumens) == "1.0 lm")
  }

  it should "return LuminousEnergy when multiplied by Time" in {
    assert(Lumens(1) * Seconds(1) == LumenSeconds(1))
  }

  it should "return Illuminance when divided by Area" in {
    assert(Lumens(1) / SquareMeters(1) == Lux(1))
  }

  it should "return LuminousIntensity when divided by SolidAngle" in {
    assert(Lumens(1) / SquaredRadians(1) == Candelas(1))
  }

  it should "return Area when divided by Illuminance" in {
    assert(Lumens(1) / Lux(1) == SquareMeters(1))
  }

  it should "return SolidAngle when divided by LuminousIntensity" in {
    assert(Lumens(1) / Candelas(1) == SquaredRadians(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Lumens(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[LuminousFlux](ser)
    assert(x == des)
  }

  behavior of "LuminousFluxConversions"

  it should "provide aliases for single unit values" in {
    import LuminousFluxConversions._

    assert(lumen == Lumens(1))
  }
  it should "provide implicit conversion from Double" in {
    import LuminousFluxConversions._

    val d = 10d
    assert(d.lumens == Lumens(d))
  }
}
