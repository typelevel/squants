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
import squants.space.{ SquareMeters, Meters, SquaredRadians }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class RadiantIntensitySpec extends FlatSpec with Matchers {

  behavior of "RadiantIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(WattsPerSteradian(1).toWattsPerSteradian == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradian(1)
    assert(x.toWattsPerSteradian == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(WattsPerSteradian(1).toString == "1.0 W/sr")
  }

  it should "return Power when multiplied by SolidAngle" in {
    assert(WattsPerSteradian(1) * SquaredRadians(1) == Watts(1))
  }

  it should "return SolidAngle when divided by Power" in {
    assert(WattsPerSteradian(1) / Watts(1) == SquaredRadians(1))
  }

  it should "return SpectralIntensity when divided by Length" in {
    assert(WattsPerSteradian(1) / Meters(1) == WattsPerSteradianPerMeter(1))
  }

  it should "Radiance when divided by Area" in {
    assert(WattsPerSteradian(1) / SquareMeters(1) == WattsPerSteradianPerSquareMeter(1))
  }
}
