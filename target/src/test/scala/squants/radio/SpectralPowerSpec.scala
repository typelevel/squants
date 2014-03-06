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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpectralPowerSpec extends FlatSpec with Matchers {

  behavior of "SpectralPower and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(WattsPerMeter(1).toWattsPerMeter == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerMeter(1)
    assert(x.toWattsPerMeter == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(WattsPerMeter(1).toString == "1.0 W/m")
  }

  it should "return Power when multiplied by Length" in {
    assert(WattsPerMeter(1) * Meters(1) == Watts(1))
  }

  it should "return Length when divided by Power" in {
    assert(WattsPerMeter(1) / Watts(1) == Meters(1))
  }
}
