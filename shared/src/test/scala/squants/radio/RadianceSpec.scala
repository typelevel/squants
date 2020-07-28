/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants.QuantityParseException
import squants.space.SquareMeters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class RadianceSpec extends AnyFlatSpec with Matchers {

  behavior of "Radiance and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerSteradianPerSquareMeter(1).toWattsPerSteradianPerSquareMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Radiance("10.22 W/sr/m²").get should be(WattsPerSteradianPerSquareMeter(10.22))
    Radiance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Radiance", "10.22 zz"))
    Radiance("zz W/sr/m²").failed.get should be(QuantityParseException("Unable to parse Radiance", "zz W/sr/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradianPerSquareMeter(1)
    x.toWattsPerSteradianPerSquareMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSteradianPerSquareMeter(1).toString should be("1.0 W/sr/m²")
  }

  it should "return RadiantIntensity when multiplied by Area" in {
    WattsPerSteradianPerSquareMeter(1) * SquareMeters(1) should be(WattsPerSteradian(1))
  }

  it should "return Area when divided by RadiantIntensity" in {
    WattsPerSteradianPerSquareMeter(1) / WattsPerSteradian(1) should be(SquareMeters(1))
  }

  behavior of "RadianceConversions"

  it should "provide aliases for single unit values" in {
    import RadianceConversions._

    wattPerSteradianPerSquareMeter should be(WattsPerSteradianPerSquareMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import RadianceConversions._

    val d = 10.22d
    d.wattsPerSteradianPerSquareMeter should be(WattsPerSteradianPerSquareMeter(d))
  }

  it should "provide Numeric support" in {
    import RadianceConversions.RadianceNumeric

    val rs = List(WattsPerSteradianPerSquareMeter(10), WattsPerSteradianPerSquareMeter(100))
    rs.sum should be(WattsPerSteradianPerSquareMeter(110))
  }
}
