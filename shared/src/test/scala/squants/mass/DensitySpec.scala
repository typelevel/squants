/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.QuantityParseException
import squants.space.{CubicMeters, Litres, Millilitres}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class DensitySpec extends AnyFlatSpec with Matchers {

  behavior of "Density and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerCubicMeter(1).toKilogramsPerCubicMeter should be(1)
    KilogramsPerLitre(1).toKilogramsPerLitre should be(1)
    GramsPerLitre(1).toGramsPerLitre should be(1)
    MilligramsPerLitre(1).toMilligramsPerLitre should be(1)
    MicrogramsPerLitre(1).toMicrogramsPerLitre should be(1)
    NanogramsPerLitre(1).toNanogramsPerLitre should be(1)
    KilogramsPerMillilitre(1).toKilogramsPerMillilitre should be(1)
    GramsPerMillilitre(1).toGramsPerMillilitre should be(1)
    MilligramsPerMillilitre(1).toMilligramsPerMillilitre should be(1)
    MicrogramsPerMillilitre(1).toMicrogramsPerMillilitre should be(1)
    NanogramsPerMillilitre(1).toNanogramsPerMillilitre should be(1)
    KilogramsPerMicrolitre(1).toKilogramsPerMicrolitre should be(1)
    GramsPerMicrolitre(1).toGramsPerMicrolitre should be(1)
    MilligramsPerMicrolitre(1).toMilligramsPerMicrolitre should be(1)
    MicrogramsPerMicrolitre(1).toMicrogramsPerMicrolitre should be(1)
    NanogramsPerMicrolitre(1).toNanogramsPerMicrolitre should be(1)
    KilogramsPerNanolitre(1).toKilogramsPerNanolitre should be(1)
    GramsPerNanolitre(1).toGramsPerNanolitre should be(1)
    MilligramsPerNanolitre(1).toMilligramsPerNanolitre should be(1)
    MicrogramsPerNanolitre(1).toMicrogramsPerNanolitre should be(1)
    NanogramsPerNanolitre(1).toNanogramsPerNanolitre should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Density("10.22 kg/m³").get should be(KilogramsPerCubicMeter(10.22))
    Density("10.22 mg/µl").get should be(MilligramsPerMicrolitre(10.22))
    Density("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Density", "10.45 zz"))
    Density("zz kg/m³").failed.get should be(QuantityParseException("Unable to parse Density", "zz kg/m³"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerCubicMeter(1)
    x.toKilogramsPerCubicMeter should be(1)
    x.toKilogramsPerLitre should be(Kilograms(1).toKilograms / CubicMeters(1).toLitres)
    x.toGramsPerLitre should be(Kilograms(1).toGrams / CubicMeters(1).toLitres)
    x.toMilligramsPerLitre should be(Kilograms(1).toMilligrams / CubicMeters(1).toLitres)
    x.toMicrogramsPerLitre should be(Kilograms(1).toMicrograms / CubicMeters(1).toLitres +- 1E-9)
    x.toNanogramsPerLitre should be(Kilograms(1).toNanograms / CubicMeters(1).toLitres)
    x.toKilogramsPerMillilitre should be(Kilograms(1).toKilograms / CubicMeters(1).toMillilitres)
    x.toGramsPerMillilitre should be(Kilograms(1).toGrams / CubicMeters(1).toMillilitres +- 1E-9)
    x.toMilligramsPerMillilitre should be(Kilograms(1).toMilligrams / CubicMeters(1).toMillilitres)
    x.toMicrogramsPerMillilitre should be(Kilograms(1).toMicrograms / CubicMeters(1).toMillilitres +- 1E-9)
    x.toNanogramsPerMillilitre should be(Kilograms(1).toNanograms / CubicMeters(1).toMillilitres +- 1E-9)
    x.toKilogramsPerMicrolitre should be(Kilograms(1).toKilograms / CubicMeters(1).toMicrolitres)
    x.toGramsPerMicrolitre should be(Kilograms(1).toGrams / CubicMeters(1).toMicrolitres +- 1E-7)
    x.toMilligramsPerMicrolitre should be(Kilograms(1).toMilligrams / CubicMeters(1).toMicrolitres)
    x.toMicrogramsPerMicrolitre should be(Kilograms(1).toMicrograms / CubicMeters(1).toMicrolitres)
    x.toNanogramsPerMicrolitre should be(Kilograms(1).toNanograms / CubicMeters(1).toMicrolitres)
    x.toKilogramsPerNanolitre should be(Kilograms(1).toKilograms / CubicMeters(1).toNanolitres)
    x.toGramsPerNanolitre should be(Kilograms(1).toGrams / CubicMeters(1).toNanolitres)
    x.toMilligramsPerNanolitre should be(Kilograms(1).toMilligrams / CubicMeters(1).toNanolitres)
    x.toMicrogramsPerNanolitre should be(Kilograms(1).toMicrograms / CubicMeters(1).toNanolitres +- 1E-9)
    x.toNanogramsPerNanolitre should be(Kilograms(1).toNanograms / CubicMeters(1).toNanolitres +- 1E-9)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerCubicMeter(1).toString should be("1.0 kg/m³")
    KilogramsPerLitre(1).toString should be("1.0 kg/L")
    GramsPerLitre(1).toString should be("1.0 g/L")
    MilligramsPerLitre(1).toString should be("1.0 mg/L")
    MicrogramsPerLitre(1).toString should be("1.0 µg/L")
    NanogramsPerLitre(1).toString should be("1.0 ng/L")
    KilogramsPerMillilitre(1).toString should be("1.0 kg/ml")
    GramsPerMillilitre(1).toString should be("1.0 g/ml")
    MilligramsPerMillilitre(1).toString should be("1.0 mg/ml")
    MicrogramsPerMillilitre(1).toString should be("1.0 µg/ml")
    NanogramsPerMillilitre(1).toString should be("1.0 ng/ml")
    KilogramsPerMicrolitre(1).toString should be("1.0 kg/µl")
    GramsPerMicrolitre(1).toString should be("1.0 g/µl")
    MilligramsPerMicrolitre(1).toString should be("1.0 mg/µl")
    MicrogramsPerMicrolitre(1).toString should be("1.0 µg/µl")
    NanogramsPerMicrolitre(1).toString should be("1.0 ng/µl")
    KilogramsPerNanolitre(1).toString should be("1.0 kg/nl")
    GramsPerNanolitre(1).toString should be("1.0 g/nl")
    MilligramsPerNanolitre(1).toString should be("1.0 mg/nl")
    MicrogramsPerNanolitre(1).toString should be("1.0 µg/nl")
    NanogramsPerNanolitre(1).toString should be("1.0 ng/nl")
  }

  it should "return Mass when multiplied by Volume" in {
    KilogramsPerCubicMeter(1) * CubicMeters(1) should be(Kilograms(1))
    KilogramsPerLitre(1) * Litres(1) should be(Kilograms(1))
    GramsPerMicrolitre(0.5) * Millilitres(2) should be(Grams(1000))
  }

  behavior of "DensityConversion"

  it should "provide aliases for single unit values" in {
    import DensityConversions._

    kilogramPerCubicMeter should be(KilogramsPerCubicMeter(1))
    kilogramsPerLitre should be(KilogramsPerLitre(1))
    kilogramsPerLiter should be(KilogramsPerLitre(1))
    gramsPerLitre should be(GramsPerLitre(1))
    gramsPerLiter should be(GramsPerLitre(1))
    milligramsPerLitre should be(MilligramsPerLitre(1))
    milligramsPerLiter should be(MilligramsPerLitre(1))
    microgramsPerLitre should be(MicrogramsPerLitre(1))
    microgramsPerLiter should be(MicrogramsPerLitre(1))
    nanogramsPerLitre should be(NanogramsPerLitre(1))
    nanogramsPerLiter should be(NanogramsPerLitre(1))
    kilogramsPerMillilitre should be(KilogramsPerMillilitre(1))
    kilogramsPerMilliliter should be(KilogramsPerMillilitre(1))
    gramsPerMillilitre should be(GramsPerMillilitre(1))
    gramsPerMilliliter should be(GramsPerMillilitre(1))
    milligramsPerMillilitre should be(MilligramsPerMillilitre(1))
    milligramsPerMilliliter should be(MilligramsPerMillilitre(1))
    microgramsPerMillilitre should be(MicrogramsPerMillilitre(1))
    microgramsPerMilliliter should be(MicrogramsPerMillilitre(1))
    nanogramsPerMillilitre should be(NanogramsPerMillilitre(1))
    nanogramsPerMilliliter should be(NanogramsPerMillilitre(1))
    kilogramsPerMicrolitre should be(KilogramsPerMicrolitre(1))
    kilogramsPerMicroliter should be(KilogramsPerMicrolitre(1))
    gramsPerMicrolitre should be(GramsPerMicrolitre(1))
    gramsPerMicroliter should be(GramsPerMicrolitre(1))
    milligramsPerMicrolitre should be(MilligramsPerMicrolitre(1))
    milligramsPerMicroliter should be(MilligramsPerMicrolitre(1))
    microgramsPerMicrolitre should be(MicrogramsPerMicrolitre(1))
    microgramsPerMicroliter should be(MicrogramsPerMicrolitre(1))
    nanogramsPerMicrolitre should be(NanogramsPerMicrolitre(1))
    nanogramsPerMicroliter should be(NanogramsPerMicrolitre(1))
    kilogramsPerNanolitre should be(KilogramsPerNanolitre(1))
    kilogramsPerNanoliter should be(KilogramsPerNanolitre(1))
    gramsPerNanolitre should be(GramsPerNanolitre(1))
    gramsPerNanoliter should be(GramsPerNanolitre(1))
    milligramsPerNanolitre should be(MilligramsPerNanolitre(1))
    milligramsPerNanoliter should be(MilligramsPerNanolitre(1))
    microgramsPerNanolitre should be(MicrogramsPerNanolitre(1))
    microgramsPerNanoliter should be(MicrogramsPerNanolitre(1))
    nanogramsPerNanolitre should be(NanogramsPerNanolitre(1))
    nanogramsPerNanoliter should be(NanogramsPerNanolitre(1))
  }

  it should "provide implicit conversion from Double" in {
    import DensityConversions._

    val d = 10.22d
    d.kilogramsPerCubicMeter should be(KilogramsPerCubicMeter(d))
    d.kilogramsPerLitre should be(KilogramsPerLitre(d))
    d.gramsPerLitre should be(GramsPerLitre(d))
    d.milligramsPerLitre should be(MilligramsPerLitre(d))
    d.microgramsPerLitre should be(MicrogramsPerLitre(d))
    d.nanogramsPerLitre should be(NanogramsPerLitre(d))
    d.kilogramsPerMillilitre should be(KilogramsPerMillilitre(d))
    d.gramsPerMillilitre should be(GramsPerMillilitre(d))
    d.milligramsPerMillilitre should be(MilligramsPerMillilitre(d))
    d.microgramsPerMillilitre should be(MicrogramsPerMillilitre(d))
    d.nanogramsPerMillilitre should be(NanogramsPerMillilitre(d))
    d.kilogramsPerMicrolitre should be(KilogramsPerMicrolitre(d))
    d.gramsPerMicrolitre should be(GramsPerMicrolitre(d))
    d.milligramsPerMicrolitre should be(MilligramsPerMicrolitre(d))
    d.microgramsPerMicrolitre should be(MicrogramsPerMicrolitre(d))
    d.nanogramsPerMicrolitre should be(NanogramsPerMicrolitre(d))
    d.kilogramsPerNanolitre should be(KilogramsPerNanolitre(d))
    d.gramsPerNanolitre should be(GramsPerNanolitre(d))
    d.milligramsPerNanolitre should be(MilligramsPerNanolitre(d))
    d.microgramsPerNanolitre should be(MicrogramsPerNanolitre(d))
    d.nanogramsPerNanolitre should be(NanogramsPerNanolitre(d))
  }

  it should "provide Numeric support" in {
    import DensityConversions.DensityNumeric

    val as = List(KilogramsPerCubicMeter(2), MicrogramsPerMillilitre(500))
    as.sum should be(KilogramsPerCubicMeter(2.5))
  }
}
