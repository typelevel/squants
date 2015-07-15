/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import org.scalatest.{ FlatSpec, Matchers }
import squants.QuantityParseException
import squants.mass.Kilograms
import squants.time.Seconds

import scala.language.postfixOps

/**
 * @author  stevebarham
 * @since   0.5.2
 *
 */
class PressureChangeSpec extends FlatSpec with Matchers {

  behavior of "PressureChange and its Units of Measure"

  it should "create values using UOM factories" in {
    PascalsPerSecond(10.22).toPascalsPerSecond should be(10.22)
    BarsPerSecond(10.22).toBarsPerSecond should be(10.22 +- 1e-15)
    PoundsPerSquareInchPerSecond(10.22).toPoundsPerSquareInchPerSecond should be(10.22 +- 1e-15)
    StandardAtmospheresPerSecond(10.22).toStandardAtmospheresPerSecond should be(10.22 +- 1e-15)
  }

  it should "create values from properly formatted Strings" in {
    PressureChange("10.22 Pa/s").get should be(PascalsPerSecond(10.22))
    PressureChange("10.22 bar/s").get should be(BarsPerSecond(10.22))
    PressureChange("10.22 psi/s").get should be(PoundsPerSquareInchPerSecond(10.22))
    PressureChange("10.22 atm/s").get should be(StandardAtmospheresPerSecond(10.22))
    PressureChange("10.22 zz").failed.get should be(QuantityParseException("Unable to parse PressureChange", "10.22 zz"))
    PressureChange("zz Pa/s").failed.get should be(QuantityParseException("Unable to parse PressureChange", "zz Pa/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = PascalsPerSecond(1)
    x.toPascalsPerSecond should be(1)
    x.toBarsPerSecond should be(1 / BarsPerSecond.conversionFactor)
    x.toPoundsPerSquareInchPerSecond should be(1 / PoundsPerSquareInchPerSecond.conversionFactor)
    x.toStandardAtmospheresPerSecond should be(1 / StandardAtmospheresPerSecond.conversionFactor)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    PascalsPerSecond(1).toString should be("1.0 Pa/s")
    BarsPerSecond(1).toString should be("1.0 bar/s")
    PoundsPerSquareInchPerSecond(1).toString should be("1.0 psi/s")
    StandardAtmospheresPerSecond(1).toString should be("1.0 atm/s")
  }

  it should "return Pressure when multiplied by Time" in {
    PascalsPerSecond(1) * Seconds(1) should be(Pascals(1))
  }

  behavior of "PressureChangeConversions"

  it should "provide aliases for single unit values" in {
    import PressureChangeConversions._
    pascalsPerSecond should be(PascalsPerSecond(1))
    barsPerSecond should be(BarsPerSecond(1))
    poundsPerSquareInchPerSecond should be(PoundsPerSquareInchPerSecond(1))
    standardAtmospheresPerSecond should be(StandardAtmospheresPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import PressureChangeConversions._

    val d = 10.22d
    d.pascalsPerSecond should be(PascalsPerSecond(10.22d))
    d.barsPerSecond should be(BarsPerSecond(10.22d))
    d.poundsPerSquareInchPerSecond should be(PoundsPerSquareInchPerSecond(10.22d))
    d.standardAtmospheresPerSecond should be(StandardAtmospheresPerSecond(10.22d))
  }

  it should "provide Numeric support" in {
    import PressureChangeConversions.PressureChangeNumeric

    val mfrs = List(PascalsPerSecond(100), PascalsPerSecond(10))
    mfrs.sum should be(PascalsPerSecond(110))
  }
}
