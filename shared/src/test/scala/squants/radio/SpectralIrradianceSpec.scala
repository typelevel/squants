/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  cquiroz@gemini.edu
 * @since   0.6.1
 *
 */
class SpectralIrradianceSpec extends AnyFlatSpec with Matchers {

  behavior of "SpectralIrradiance and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerCubicMeter(1).toWattsPerCubicMeter should be(1)
    WattsPerSquareMeterPerNanometer(1).toWattsPerSquareMeterPerNanometer should be(1)
    WattsPerSquareMeterPerMicron(1).toWattsPerSquareMeterPerMicron should be(1)
    ErgsPerSecondPerSquareCentimeterPerAngstrom(1).toErgsPerSecondPerSquareCentimeterPerAngstrom should be(1)
  }

  it should "create values from properly formatted Strings" in {
    SpectralIrradiance("10.22 W/m³").get should be(WattsPerCubicMeter(10.22))
    SpectralIrradiance("10.22 W/m²/nm").get should be(WattsPerSquareMeterPerNanometer(10.22))
    SpectralIrradiance("10.22 W/m²/µm").get should be(WattsPerSquareMeterPerMicron(10.22))
    SpectralIrradiance("10.22 erg/s/cm²/Å").get should be(ErgsPerSecondPerSquareCentimeterPerAngstrom(10.22))
    SpectralIrradiance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse SpectralIrradiance", "10.22 zz"))
    SpectralIrradiance("zz W/m²").failed.get should be(QuantityParseException("Unable to parse SpectralIrradiance", "zz W/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerCubicMeter(1)
    x.toWattsPerSquareMeterPerNanometer should be(1.0e-9 +- 1.0e-12)
    x.toWattsPerSquareMeterPerMicron should be(1.0e-6 +-1.0e-12)
    x.toErgsPerSecondPerSquareCentimeterPerAngstrom should be(1.0e-7 +-1.0e-12)

    val y = WattsPerSquareMeterPerNanometer(1)
    y.toWattsPerCubicMeter should be(1.0e+9 +- 1.0e-5)
    y.toWattsPerSquareMeterPerMicron should be(1.0e+3 +- 1.0e-5)
    y.toErgsPerSecondPerSquareCentimeterPerAngstrom should be(100.0 +- 1.0e-5)

    val z = WattsPerSquareMeterPerMicron(1)
    z.toWattsPerCubicMeter should be(1.0e+6 +- 1.0e-5)
    z.toWattsPerSquareMeterPerNanometer should be(1.0e-3 +- 1.0e-5)
    z.toErgsPerSecondPerSquareCentimeterPerAngstrom should be(1.0e-1 +- 1.0e-5)

    val u = ErgsPerSecondPerSquareCentimeterPerAngstrom(1)
    u.toWattsPerCubicMeter should be(1.0e+7 +- 1.0e-5)
    u.toWattsPerSquareMeterPerNanometer should be(1.0e-2 +- 1.0e-5)
    u.toWattsPerSquareMeterPerMicron should be(10.0 +- 1.0e-5)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerCubicMeter(1).toString should be("1.0 W/m³")
    WattsPerSquareMeterPerNanometer(1).toString should be("1.0 W/m²/nm")
    WattsPerSquareMeterPerMicron(1).toString should be("1.0 W/m²/µm")
    ErgsPerSecondPerSquareCentimeterPerAngstrom(1).toString should be("1.0 erg/s/cm²/Å")
  }

  behavior of "SpectralIrradianceConversions"

  it should "provide aliases for single unit values" in {
    import SpectralIrradianceConversions._

    wattPerCubicMeter should be(WattsPerCubicMeter(1))
    wattPerSquareMeterPerNanometer should be(WattsPerSquareMeterPerNanometer(1))
    wattPerSquareMeterPerMicron should be(WattsPerSquareMeterPerMicron(1))
    ergPerSecondPerSquareCentimeterPerAngstrom should be(ErgsPerSecondPerSquareCentimeterPerAngstrom(1))
  }

  it should "provide implicit conversion from Double" in {
    import SpectralIrradianceConversions._

    val d = 10.22d
    d.wattsPerCubicMeter should be(WattsPerCubicMeter(d))
    d.wattsPerSquareMeterPerMicron should be(WattsPerSquareMeterPerMicron(d))
    d.wattsPerSquareMeterPerNanometer should be(WattsPerSquareMeterPerNanometer(d))
    d.ergsPerSecondPerSquareCentimeterPerAngstrom should be(ErgsPerSecondPerSquareCentimeterPerAngstrom(d))
  }

  it should "provide Numeric support" in {
    import SpectralIrradianceConversions.SpectralIrradianceNumeric

    val irrsWPCM = List(WattsPerCubicMeter(10), WattsPerCubicMeter(100))
    irrsWPCM.sum should be(WattsPerCubicMeter(110))

    val irrsWPSMPM = List(WattsPerSquareMeterPerMicron(10), WattsPerSquareMeterPerMicron(100))
    irrsWPSMPM.sum should be(WattsPerSquareMeterPerMicron(110))

    val irrsWPSMPN = List(WattsPerSquareMeterPerNanometer(10), WattsPerSquareMeterPerNanometer(100))
    irrsWPSMPN.sum should be(WattsPerSquareMeterPerNanometer(110))

    val irrsEPSCA = List(ErgsPerSecondPerSquareCentimeterPerAngstrom(10), ErgsPerSecondPerSquareCentimeterPerAngstrom(100))
    irrsEPSCA.sum should be(ErgsPerSecondPerSquareCentimeterPerAngstrom(110))
  }
}
