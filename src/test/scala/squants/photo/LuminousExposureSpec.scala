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
class LuminousExposureSpec extends FlatSpec with Matchers {

  behavior of "LuminousExposure and its Units of Measure"

  it should "create values using UOM factories" in {
    LuxSeconds(1).toLuxSeconds should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = LuxSeconds(1)
    x.toLuxSeconds should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    LuxSeconds(1).toString(LuxSeconds) should be("1.0 lx⋅s")
  }

  it should "return Illuminance when divided by Time" in {
    LuxSeconds(1) / Seconds(1) should be(Lux(1))
  }

  it should "return Time when divided by Illuminance" in {
    LuxSeconds(1) / Lux(1) should be(Seconds(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = LuxSeconds(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[LuminousExposure](ser)
    x should be(des)
  }

  behavior of "LuminousExposureConversions"

  it should "provide aliases for single unit values" ignore {
  }

  it should "provide implicit conversion from Double" ignore {
  }
}
