/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants.QuantityParseException
import squants.space.Meters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpectralIntensitySpec extends AnyFlatSpec with Matchers {

  behavior of "SpectralIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerSteradianPerMeter(1).toWattsPerSteradianPerMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    SpectralIntensity("10.22 W/sr/m").get should be(WattsPerSteradianPerMeter(10.22))
    SpectralIntensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse SpectralIntensity", "10.22 zz"))
    SpectralIntensity("zz W/sr/m").failed.get should be(QuantityParseException("Unable to parse SpectralIntensity", "zz W/sr/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradianPerMeter(1)
    x.toWattsPerSteradianPerMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSteradianPerMeter(1).toString should be("1.0 W/sr/m")
  }

  it should "return RadiantIntensity when multiplied by Length" in {
    WattsPerSteradianPerMeter(1) * Meters(1) should be(WattsPerSteradian(1))
  }

  it should "return Length when divided by RadiantIntensity" in {
    WattsPerSteradianPerMeter(1) / WattsPerSteradian(1) should be(Meters(1))
  }

  behavior of "SpectralIntensityConversions"

  it should "provide aliases for single unit values" in {
    import SpectralIntensityConversions._

    wattPerSteradianPerMeter should be(WattsPerSteradianPerMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import SpectralIntensityConversions._

    val d = 10.22d
    d.wattsPerSteradianPerMeter should be(WattsPerSteradianPerMeter(d))
  }

  it should "provide Numeric support" in {
    import SpectralIntensityConversions.SpectralIntensityNumeric

    val rs = List(WattsPerSteradianPerMeter(10), WattsPerSteradianPerMeter(100))
    rs.sum should be(WattsPerSteradianPerMeter(110))
  }
}
