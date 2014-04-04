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
import squants.space.Meters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpectralIntensitySpec extends FlatSpec with Matchers {

  behavior of "SpectralIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(WattsPerSteradianPerMeter(1).toWattsPerSteradianPerMeter == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradianPerMeter(1)
    assert(x.toWattsPerSteradianPerMeter == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(WattsPerSteradianPerMeter(1).toString == "1.0 W/sr/m")
  }

  it should "return RadiantIntensity when multiplied by Length" in {
    assert(WattsPerSteradianPerMeter(1) * Meters(1) == WattsPerSteradian(1))
  }

  it should "return Length when divided by RadiantIntensity" in {
    assert(WattsPerSteradianPerMeter(1) / WattsPerSteradian(1) == Meters(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = WattsPerSteradianPerMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[SpectralIntensity](ser)
    assert(x == des)
  }
}
