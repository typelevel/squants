/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants.QuantityParseException
import squants.energy.Watts
import squants.space.{ Meters, SquareMeters, SquaredRadians }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class RadiantIntensitySpec extends AnyFlatSpec with Matchers {

  behavior of "RadiantIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerSteradian(1).toWattsPerSteradian should be(1)
  }

  it should "create values from properly formatted Strings" in {
    RadiantIntensity("10.22 W/sr").get should be(WattsPerSteradian(10.22))
    RadiantIntensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse RadiantIntensity", "10.22 zz"))
    RadiantIntensity("zz W/sr").failed.get should be(QuantityParseException("Unable to parse RadiantIntensity", "zz W/sr"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradian(1)
    x.toWattsPerSteradian should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSteradian(1).toString should be("1.0 W/sr")
  }

  it should "return Power when multiplied by SolidAngle" in {
    WattsPerSteradian(1) * SquaredRadians(1) should be(Watts(1))
  }

  it should "return SolidAngle when divided by Power" in {
    WattsPerSteradian(1) / Watts(1) should be(SquaredRadians(1))
  }

  it should "return SpectralIntensity when divided by Length" in {
    WattsPerSteradian(1) / Meters(1) should be(WattsPerSteradianPerMeter(1))
  }

  it should "Radiance when divided by Area" in {
    WattsPerSteradian(1) / SquareMeters(1) should be(WattsPerSteradianPerSquareMeter(1))
  }

  behavior of "RadiantIntensityConversions"

  it should "provide aliases for single unit values" in {
    import RadiantIntensityConversions._

    wattPerSteradian should be(WattsPerSteradian(1))
  }

  it should "provide implicit conversion from Double" in {
    import RadiantIntensityConversions._

    val d = 10.22d
    d.wattsPerSteradian should be(WattsPerSteradian(d))
  }

  it should "provide Numeric support" in {
    import RadiantIntensityConversions.RadiantIntensityNumeric

    val rs = List(WattsPerSteradian(10), WattsPerSteradian(100))
    rs.sum should be(WattsPerSteradian(110))
  }
}
