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
}
