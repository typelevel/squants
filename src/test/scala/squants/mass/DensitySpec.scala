/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.CubicMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class DensitySpec extends FlatSpec with Matchers {

  behavior of "Density and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerCubicMeter(1).toKilogramsPerCubicMeter should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerCubicMeter(1)
    x.toKilogramsPerCubicMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerCubicMeter(1).toString should be("1.0 kg/m³")
  }

  it should "return Mass when multiplied by Volume" in {
    KilogramsPerCubicMeter(1) * CubicMeters(1) should be(Kilograms(1))
  }

  behavior of "DensityConversion"

  it should "provide aliases for single unit values" in {
    import DensityConversions._

    kilogramPerCubicMeter should be(KilogramsPerCubicMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import DensityConversions._

    val d = 10.22d
    d.kilogramsPerCubicMeter should be(KilogramsPerCubicMeter(d))
  }

  it should "provide Numeric support" in {
    import DensityConversions.DensityNumeric

    val as = List(KilogramsPerCubicMeter(100), KilogramsPerCubicMeter(10))
    as.sum should be(KilogramsPerCubicMeter(110))
  }
}
