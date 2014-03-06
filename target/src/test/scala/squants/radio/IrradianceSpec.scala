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
import squants.energy.Watts

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class IrradianceSpec extends FlatSpec with Matchers {

  behavior of "Irradiance and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(WattsPerSquareMeter(1).toWattsPerSquareMeter == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSquareMeter(1)
    assert(x.toWattsPerSquareMeter == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(WattsPerSquareMeter(1).toString == "1.0 W/m²")
  }

  it should "return Power when multiplied by Area" in {
    assert(WattsPerSquareMeter(1) * SquareMeters(1) == Watts(1))
  }

  it should "return Area when divided by Power" in {
    assert(WattsPerSquareMeter(1) / Watts(1) == SquareMeters(1))
  }
}
