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
import squants.energy.Watts
import squants.space.Meters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpectralPowerSpec extends FlatSpec with Matchers {

  behavior of "SpectralPower and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerMeter(1).toWattsPerMeter should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerMeter(1)
    x.toWattsPerMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerMeter(1).toString should be("1.0 W/m")
  }

  it should "return Power when multiplied by Length" in {
    WattsPerMeter(1) * Meters(1) should be(Watts(1))
  }

  it should "return Length when divided by Power" in {
    WattsPerMeter(1) / Watts(1) should be(Meters(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = WattsPerMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[SpectralPower](ser)
    x should be(des)
  }
}
