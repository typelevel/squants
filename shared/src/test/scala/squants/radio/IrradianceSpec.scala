/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import org.scalatest.{FlatSpec, Matchers}
import squants.QuantityParseException
import squants.energy.Watts
import squants.space.{SquareCentimeters, SquareMeters}

import scala.language.postfixOps

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class IrradianceSpec extends FlatSpec with Matchers {

  behavior of "Irradiance and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerSquareMeter(1).toWattsPerSquareMeter should be(1)
    ErgsPerSecondPerSquareCentimeter(1).toErgsPerSecondPerSquareCentimeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Irradiance("10.22 W/m²").get should be(WattsPerSquareMeter(10.22))
    Irradiance("5 erg/s/cm²").get should be(ErgsPerSecondPerSquareCentimeter(5))
    Irradiance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Irradiance", "10.22 zz"))
    Irradiance("zz W/m²").failed.get should be(QuantityParseException("Unable to parse Irradiance", "zz W/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSquareMeter(1)
    x.toWattsPerSquareMeter should be(1)
    x.toErgsPerSecondPerSquareCentimeter should be(1000.0 +- 1e-5)

    val y = ErgsPerSecondPerSquareCentimeter(1)
    y.toErgsPerSecondPerSquareCentimeter should be(1)
    y.toWattsPerSquareMeter should be(0.001 +- 1e-4)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSquareMeter(1).toString should be("1.0 W/m²")
    ErgsPerSecondPerSquareCentimeter(1).toString should be("1.0 erg/s/cm²")
  }

  it should "return Power when multiplied by Area" in {
    WattsPerSquareMeter(1) * SquareMeters(1) should be(Watts(1))
    (ErgsPerSecondPerSquareCentimeter(1) * SquareCentimeters(1)).toErgsPerSecond should be(1.0 +- 1e-5)
  }

  behavior of "IrradianceConversions"

  it should "provide aliases for single unit values" in {
    import IrradianceConversions._

    wattPerSquareMeter should be(WattsPerSquareMeter(1))
    ergsPerSecondPerSquareCentimeter should be(ErgsPerSecondPerSquareCentimeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import IrradianceConversions._

    val d = 10.22d
    d.wattsPerSquareMeter should be(WattsPerSquareMeter(d))
    d.ergsPerSecondPerSquareCentimeter should be(ErgsPerSecondPerSquareCentimeter(d))
  }

  it should "provide Numeric support" in {
    import IrradianceConversions.IrradianceNumeric

    val irrs = List(WattsPerSquareMeter(10), WattsPerSquareMeter(100))
    irrs.sum should be(WattsPerSquareMeter(110))

    val irrsErg = List(ErgsPerSecondPerSquareCentimeter(10), ErgsPerSecondPerSquareCentimeter(100))
    irrsErg.sum should be(ErgsPerSecondPerSquareCentimeter(110))
  }
}
