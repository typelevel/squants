/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._
import squants.space.{CubicMeters, Litres, Microlitres, Millilitres, Nanolitres}

import scala.util.Try

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Density private (val value: Double, val unit: DensityUnit)
    extends Quantity[Density] {

  def dimension: Dimension[Density] = Density

  def *(that: Volume): Mass = Kilograms(this.toKilogramsPerCubicMeter * that.toCubicMeters)

  def toKilogramsPerCubicMeter: Double = to(KilogramsPerCubicMeter)
  def toKilogramsPerLitre: Double = to(KilogramsPerLitre)
  def toGramsPerLitre: Double = to(GramsPerLitre)
  def toMilligramsPerLitre: Double = to(MilligramsPerLitre)
  def toMicrogramsPerLitre: Double = to(MicrogramsPerLitre)
  def toNanogramsPerLitre: Double = to(NanogramsPerLitre)
  def toKilogramsPerMillilitre: Double = to(KilogramsPerMillilitre)
  def toGramsPerMillilitre: Double = to(GramsPerMillilitre)
  def toMilligramsPerMillilitre: Double = to(MilligramsPerMillilitre)
  def toMicrogramsPerMillilitre: Double = to(MicrogramsPerMillilitre)
  def toNanogramsPerMillilitre: Double = to(NanogramsPerMillilitre)
  def toKilogramsPerMicrolitre: Double = to(KilogramsPerMicrolitre)
  def toGramsPerMicrolitre: Double = to(GramsPerMicrolitre)
  def toMilligramsPerMicrolitre: Double = to(MilligramsPerMicrolitre)
  def toMicrogramsPerMicrolitre: Double = to(MicrogramsPerMicrolitre)
  def toNanogramsPerMicrolitre: Double = to(NanogramsPerMicrolitre)
  def toKilogramsPerNanolitre: Double = to(KilogramsPerNanolitre)
  def toGramsPerNanolitre: Double = to(GramsPerNanolitre)
  def toMilligramsPerNanolitre: Double = to(MilligramsPerNanolitre)
  def toMicrogramsPerNanolitre: Double = to(MicrogramsPerNanolitre)
  def toNanogramsPerNanolitre: Double = to(NanogramsPerNanolitre)
}

object Density extends Dimension[Density] {
  private[mass] def apply[A](n: A, unit: DensityUnit)(implicit num: Numeric[A]) = new Density(num.toDouble(n), unit)
  def apply(m: Mass, v: Volume): Density = KilogramsPerCubicMeter(m.toKilograms / v.toCubicMeters)
  def apply(value: Any): Try[Density] = parse(value)
  def name = "Density"
  def primaryUnit: UnitOfMeasure[Density] with PrimaryUnit = KilogramsPerCubicMeter
  def siUnit: UnitOfMeasure[Density] with SiUnit = KilogramsPerCubicMeter
  def units = Set(KilogramsPerCubicMeter,
    KilogramsPerLitre, GramsPerLitre, MilligramsPerLitre, MicrogramsPerLitre, NanogramsPerLitre,
    KilogramsPerMillilitre, GramsPerMillilitre, MilligramsPerMillilitre, MicrogramsPerMillilitre, NanogramsPerMillilitre,
    KilogramsPerMicrolitre, GramsPerMicrolitre, MilligramsPerMicrolitre, MicrogramsPerMicrolitre, NanogramsPerMicrolitre,
    KilogramsPerNanolitre, GramsPerNanolitre, MilligramsPerNanolitre, MicrogramsPerNanolitre, NanogramsPerNanolitre
  )
}

trait DensityUnit extends UnitOfMeasure[Density] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]): Density = Density(n, this)
}

object KilogramsPerCubicMeter extends DensityUnit with PrimaryUnit with SiUnit {
  val symbol = "kg/m³"
}

object KilogramsPerLitre extends DensityUnit {
  val symbol = "kg/L"
  val conversionFactor: Double = CubicMeters.conversionFactor / Litres.conversionFactor
}

object GramsPerLitre extends DensityUnit {
  val symbol = "g/L"
  val conversionFactor: Double = (Grams.conversionFactor / Kilograms.conversionFactor) /
    (Litres.conversionFactor / CubicMeters.conversionFactor)
}

object MilligramsPerLitre extends DensityUnit {
  val symbol = "mg/L"
  val conversionFactor: Double = (Milligrams.conversionFactor / Kilograms.conversionFactor) /
    (Litres.conversionFactor / CubicMeters.conversionFactor)
}

object MicrogramsPerLitre extends DensityUnit {
  val symbol = "µg/L"
  val conversionFactor: Double = (Micrograms.conversionFactor / Kilograms.conversionFactor) /
    (Litres.conversionFactor / CubicMeters.conversionFactor)
}

object NanogramsPerLitre extends DensityUnit {
  val symbol = "ng/L"
  val conversionFactor: Double = (Nanograms.conversionFactor / Kilograms.conversionFactor) /
    (Litres.conversionFactor / CubicMeters.conversionFactor)
}

object KilogramsPerMillilitre extends DensityUnit {
  val symbol = "kg/ml"
  val conversionFactor: Double = CubicMeters.conversionFactor / Millilitres.conversionFactor
}

object GramsPerMillilitre extends DensityUnit {
  val symbol = "g/ml"
  val conversionFactor: Double = (Grams.conversionFactor / Kilograms.conversionFactor) /
    (Millilitres.conversionFactor / CubicMeters.conversionFactor)
}

object MilligramsPerMillilitre extends DensityUnit {
  val symbol = "mg/ml"
  val conversionFactor: Double = (Milligrams.conversionFactor / Kilograms.conversionFactor) /
    (Millilitres.conversionFactor / CubicMeters.conversionFactor)
}

object MicrogramsPerMillilitre extends DensityUnit {
  val symbol = "µg/ml"
  val conversionFactor: Double = (Micrograms.conversionFactor / Kilograms.conversionFactor) /
    (Millilitres.conversionFactor / CubicMeters.conversionFactor)
}

object NanogramsPerMillilitre extends DensityUnit {
  val symbol = "ng/ml"
  val conversionFactor: Double = (Nanograms.conversionFactor / Kilograms.conversionFactor) /
    (Millilitres.conversionFactor / CubicMeters.conversionFactor)
}

object KilogramsPerMicrolitre extends DensityUnit {
  val symbol = "kg/µl"
  val conversionFactor: Double = CubicMeters.conversionFactor / Microlitres.conversionFactor
}

object GramsPerMicrolitre extends DensityUnit {
  val symbol = "g/µl"
  val conversionFactor: Double = (Grams.conversionFactor / Kilograms.conversionFactor) /
    (Microlitres.conversionFactor / CubicMeters.conversionFactor)
}

object MilligramsPerMicrolitre extends DensityUnit {
  val symbol = "mg/µl"
  val conversionFactor: Double = (Milligrams.conversionFactor / Kilograms.conversionFactor) /
    (Microlitres.conversionFactor / CubicMeters.conversionFactor)
}

object MicrogramsPerMicrolitre extends DensityUnit {
  val symbol = "µg/µl"
  val conversionFactor: Double = (Micrograms.conversionFactor / Kilograms.conversionFactor) /
    (Microlitres.conversionFactor / CubicMeters.conversionFactor)
}

object NanogramsPerMicrolitre extends DensityUnit {
  val symbol = "ng/µl"
  val conversionFactor: Double = (Nanograms.conversionFactor / Kilograms.conversionFactor) /
    (Microlitres.conversionFactor / CubicMeters.conversionFactor)
}

object KilogramsPerNanolitre extends DensityUnit {
  val symbol = "kg/nl"
  val conversionFactor: Double = CubicMeters.conversionFactor / Nanolitres.conversionFactor
}

object GramsPerNanolitre extends DensityUnit {
  val symbol = "g/nl"
  val conversionFactor: Double = (Grams.conversionFactor / Kilograms.conversionFactor) /
    (Nanolitres.conversionFactor / CubicMeters.conversionFactor)
}

object MilligramsPerNanolitre extends DensityUnit {
  val symbol = "mg/nl"
  val conversionFactor: Double = (Milligrams.conversionFactor / Kilograms.conversionFactor) /
    (Nanolitres.conversionFactor / CubicMeters.conversionFactor)
}

object MicrogramsPerNanolitre extends DensityUnit {
  val symbol = "µg/nl"
  val conversionFactor: Double = (Micrograms.conversionFactor / Kilograms.conversionFactor) /
    (Nanolitres.conversionFactor / CubicMeters.conversionFactor)
}

object NanogramsPerNanolitre extends DensityUnit {
  val symbol = "ng/nl"
  val conversionFactor: Double = (Nanograms.conversionFactor / Kilograms.conversionFactor) /
    (Nanolitres.conversionFactor / CubicMeters.conversionFactor)
}

object DensityConversions {
  lazy val kilogramPerCubicMeter: Density = KilogramsPerCubicMeter(1)

  lazy val kilogramsPerLitre: Density = KilogramsPerLitre(1)
  lazy val kilogramsPerLiter: Density = KilogramsPerLitre(1)
  lazy val gramsPerLitre: Density = GramsPerLitre(1)
  lazy val gramsPerLiter: Density = GramsPerLitre(1)
  lazy val milligramsPerLitre: Density = MilligramsPerLitre(1)
  lazy val milligramsPerLiter: Density = MilligramsPerLitre(1)
  lazy val microgramsPerLitre: Density = MicrogramsPerLitre(1)
  lazy val microgramsPerLiter: Density = MicrogramsPerLitre(1)
  lazy val nanogramsPerLitre: Density = NanogramsPerLitre(1)
  lazy val nanogramsPerLiter: Density = NanogramsPerLitre(1)

  lazy val kilogramsPerMillilitre: Density = KilogramsPerMillilitre(1)
  lazy val kilogramsPerMilliliter: Density = KilogramsPerMillilitre(1)
  lazy val gramsPerMillilitre: Density = GramsPerMillilitre(1)
  lazy val gramsPerMilliliter: Density = GramsPerMillilitre(1)
  lazy val milligramsPerMillilitre: Density = MilligramsPerMillilitre(1)
  lazy val milligramsPerMilliliter: Density = MilligramsPerMillilitre(1)
  lazy val microgramsPerMillilitre: Density = MicrogramsPerMillilitre(1)
  lazy val microgramsPerMilliliter: Density = MicrogramsPerMillilitre(1)
  lazy val nanogramsPerMillilitre: Density = NanogramsPerMillilitre(1)
  lazy val nanogramsPerMilliliter: Density = NanogramsPerMillilitre(1)

  lazy val kilogramsPerMicrolitre: Density = KilogramsPerMicrolitre(1)
  lazy val kilogramsPerMicroliter: Density = KilogramsPerMicrolitre(1)
  lazy val gramsPerMicrolitre: Density = GramsPerMicrolitre(1)
  lazy val gramsPerMicroliter: Density = GramsPerMicrolitre(1)
  lazy val milligramsPerMicrolitre: Density = MilligramsPerMicrolitre(1)
  lazy val milligramsPerMicroliter: Density = MilligramsPerMicrolitre(1)
  lazy val microgramsPerMicrolitre: Density = MicrogramsPerMicrolitre(1)
  lazy val microgramsPerMicroliter: Density = MicrogramsPerMicrolitre(1)
  lazy val nanogramsPerMicrolitre: Density = NanogramsPerMicrolitre(1)
  lazy val nanogramsPerMicroliter: Density = NanogramsPerMicrolitre(1)

  lazy val kilogramsPerNanolitre: Density = KilogramsPerNanolitre(1)
  lazy val kilogramsPerNanoliter: Density = KilogramsPerNanolitre(1)
  lazy val gramsPerNanolitre: Density = GramsPerNanolitre(1)
  lazy val gramsPerNanoliter: Density = GramsPerNanolitre(1)
  lazy val milligramsPerNanolitre: Density = MilligramsPerNanolitre(1)
  lazy val milligramsPerNanoliter: Density = MilligramsPerNanolitre(1)
  lazy val microgramsPerNanolitre: Density = MicrogramsPerNanolitre(1)
  lazy val microgramsPerNanoliter: Density = MicrogramsPerNanolitre(1)
  lazy val nanogramsPerNanolitre: Density = NanogramsPerNanolitre(1)
  lazy val nanogramsPerNanoliter: Density = NanogramsPerNanolitre(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerCubicMeter: Density = KilogramsPerCubicMeter(n)

    def kilogramsPerLitre: Density = KilogramsPerLitre(n)
    def kilogramsPerLiter: Density = KilogramsPerLitre(n)
    def gramsPerLitre: Density = GramsPerLitre(n)
    def gramsPerLiter: Density = GramsPerLitre(n)
    def milligramsPerLitre: Density = MilligramsPerLitre(n)
    def milligramsPerLiter: Density = MilligramsPerLitre(n)
    def microgramsPerLitre: Density = MicrogramsPerLitre(n)
    def microgramsPerLiter: Density = MicrogramsPerLitre(n)
    def nanogramsPerLitre: Density = NanogramsPerLitre(n)
    def nanogramsPerLiter: Density = NanogramsPerLitre(n)

    def kilogramsPerMillilitre: Density = KilogramsPerMillilitre(n)
    def kilogramsPerMilliliter: Density = KilogramsPerMillilitre(n)
    def gramsPerMillilitre: Density = GramsPerMillilitre(n)
    def gramsPerMilliliter: Density = GramsPerMillilitre(n)
    def milligramsPerMillilitre: Density = MilligramsPerMillilitre(n)
    def milligramsPerMilliliter: Density = MilligramsPerMillilitre(n)
    def microgramsPerMillilitre: Density = MicrogramsPerMillilitre(n)
    def microgramsPerMilliliter: Density = MicrogramsPerMillilitre(n)
    def nanogramsPerMillilitre: Density = NanogramsPerMillilitre(n)
    def nanogramsPerMilliliter: Density = NanogramsPerMillilitre(n)

    def kilogramsPerMicrolitre: Density = KilogramsPerMicrolitre(n)
    def kilogramsPerMicroliter: Density = KilogramsPerMicrolitre(n)
    def gramsPerMicrolitre: Density = GramsPerMicrolitre(n)
    def gramsPerMicroliter: Density = GramsPerMicrolitre(n)
    def milligramsPerMicrolitre: Density = MilligramsPerMicrolitre(n)
    def milligramsPerMicroliter: Density = MilligramsPerMicrolitre(n)
    def microgramsPerMicrolitre: Density = MicrogramsPerMicrolitre(n)
    def microgramsPerMicroliter: Density = MicrogramsPerMicrolitre(n)
    def nanogramsPerMicrolitre: Density = NanogramsPerMicrolitre(n)
    def nanogramsPerMicroliter: Density = NanogramsPerMicrolitre(n)

    def kilogramsPerNanolitre: Density = KilogramsPerNanolitre(n)
    def kilogramsPerNanoliter: Density = KilogramsPerNanolitre(n)
    def gramsPerNanolitre: Density = GramsPerNanolitre(n)
    def gramsPerNanoliter: Density = GramsPerNanolitre(n)
    def milligramsPerNanolitre: Density = MilligramsPerNanolitre(n)
    def milligramsPerNanoliter: Density = MilligramsPerNanolitre(n)
    def microgramsPerNanolitre: Density = MicrogramsPerNanolitre(n)
    def microgramsPerNanoliter: Density = MicrogramsPerNanolitre(n)
    def nanogramsPerNanolitre: Density = NanogramsPerNanolitre(n)
    def nanogramsPerNanoliter: Density = NanogramsPerNanolitre(n)
  }

  implicit object DensityNumeric extends AbstractQuantityNumeric[Density](KilogramsPerCubicMeter)
}