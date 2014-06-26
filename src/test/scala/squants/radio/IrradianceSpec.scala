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
    WattsPerSquareMeter(1).toWattsPerSquareMeter should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSquareMeter(1)
    x.toWattsPerSquareMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSquareMeter(1).toString should be("1.0 W/m²")
  }

  it should "return Power when multiplied by Area" in {
    WattsPerSquareMeter(1) * SquareMeters(1) should be(Watts(1))
  }

  it should "return Area when divided by Power" in {
    WattsPerSquareMeter(1) / Watts(1) should be(SquareMeters(1))
  }

  behavior of "IrradianceConversions"

  it should "provide aliases for single unit values" in {
    import IrradianceConversions._

    wattPerSquareMeter should be(WattsPerSquareMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import IrradianceConversions._

    val d = 10.22d
    d.wattsPerSquareMeter should be(WattsPerSquareMeter(d))
  }

  it should "provide Numeric support" in {
    import IrradianceConversions.IrradianceNumeric

    val irrs = List(WattsPerSquareMeter(10), WattsPerSquareMeter(100))
    irrs.sum should be(WattsPerSquareMeter(110))
  }
}
