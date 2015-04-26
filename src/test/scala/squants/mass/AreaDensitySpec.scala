/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.SquareMeters
import squants.QuantityStringParseException

/**
 * @author  garyKeorkunian
 * @since   0.2.3
 *
 */
class AreaDensitySpec extends FlatSpec with Matchers {

  behavior of "AreaDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerSquareMeter(1).toKilogramsPerSquareMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    AreaDensity("10.22 kg/m²").get should be(KilogramsPerSquareMeter(10.22))
    AreaDensity("10.45 zz").failed.get should be(QuantityStringParseException("Unable to parse AreaDensity", "10.45 zz"))
    AreaDensity("zz kg/m²").failed.get should be(QuantityStringParseException("Unable to parse AreaDensity", "zz kg/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSquareMeter(1)
    x.toKilogramsPerSquareMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerSquareMeter(1).toString should be("1.0 kg/m²")
  }

  it should "return Mass when multiplied by Volume" in {
    KilogramsPerSquareMeter(1) * SquareMeters(1) should be(Kilograms(1))
  }

  behavior of "AreaDensityConversion"

  it should "provide aliases for single unit values" in {
    import AreaDensityConversions._

    kilogramPerSquareMeter should be(KilogramsPerSquareMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import AreaDensityConversions._

    val d = 10.22d
    d.kilogramsPerSquareMeter should be(KilogramsPerSquareMeter(d))
  }

  it should "provide Numeric support" in {
    import AreaDensityConversions.AreaDensityNumeric

    val as = List(KilogramsPerSquareMeter(100), KilogramsPerSquareMeter(10))
    as.sum should be(KilogramsPerSquareMeter(110))
  }
}
