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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousEnergySpec extends FlatSpec with Matchers {

  behavior of "LuminousEnergy and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(LumenSeconds(1).toLumenSeconds == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = LumenSeconds(1)
    assert(x.toLumenSeconds == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(LumenSeconds(1).toString(LumenSeconds) == "1.0 lm⋅s")
  }

  it should "return LuminousFlux when divided by Time" in {
    assert(LumenSeconds(1) / Seconds(1) == Lumens(1))
  }

  it should "return Time when divided by LuminousFlux" in {
    assert(LumenSeconds(1) / Lumens(1) == Seconds(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = LumenSeconds(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[LuminousEnergy](ser)
    assert(x == des)
  }

  behavior of "LuminousEnergyConversions"

  it should "provide aliases for single unit values" in {
    import LuminousEnergyConversions._

    assert(lumenSecond == LumenSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import LuminousEnergyConversions._

    val d = 10d
    assert(d.lumenSeconds == LumenSeconds(d))
  }
}
