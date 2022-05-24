/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Density[A: Numeric] private [squants2]  (value: A, unit: DensityUnit)
  extends Quantity[A, Density.type] {
  override type Q[B] = Density[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toNanogramsPerLitre: A = to(NanogramsPerLitre)
  def toMicrogramsPerLitre: A = to(MicrogramsPerLitre)
  def toNanogramsPerMillilitre: A = to(NanogramsPerMillilitre)
  def toMicrogramsPerMillilitre: A = to(MicrogramsPerMillilitre)
  def toNanogramsPerMicrolitre: A = to(NanogramsPerMicrolitre)
  def toMilligramsPerLitre: A = to(MilligramsPerLitre)
  def toNanogramsPerNanolitre: A = to(NanogramsPerNanolitre)
  def toMicrogramsPerMicrolitre: A = to(MicrogramsPerMicrolitre)
  def toKilogramsPerCubicMeter: A = to(KilogramsPerCubicMeter)
  def toGramsPerLitre: A = to(GramsPerLitre)
  def toMilligramsPerMillilitre: A = to(MilligramsPerMillilitre)
  def toMicrogramsPerNanolitre: A = to(MicrogramsPerNanolitre)
  def toMilligramsPerMicrolitre: A = to(MilligramsPerMicrolitre)
  def toKilogramsPerLitre: A = to(KilogramsPerLitre)
  def toGramsPerMillilitre: A = to(GramsPerMillilitre)
  def toMilligramsPerNanolitre: A = to(MilligramsPerNanolitre)
  def toKilogramsPerMillilitre: A = to(KilogramsPerMillilitre)
  def toGramsPerMicrolitre: A = to(GramsPerMicrolitre)
  def toGramsPerNanolitre: A = to(GramsPerNanolitre)
  def toKilogramsPerMicrolitre: A = to(KilogramsPerMicrolitre)
  def toKilogramsPerNanolitre: A = to(KilogramsPerNanolitre)
}

object Density extends Dimension("Density") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = KilogramsPerCubicMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = KilogramsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(NanogramsPerLitre, MicrogramsPerLitre, NanogramsPerMillilitre, MicrogramsPerMillilitre, NanogramsPerMicrolitre, MilligramsPerLitre, NanogramsPerNanolitre, MicrogramsPerMicrolitre, KilogramsPerCubicMeter, GramsPerLitre, MilligramsPerMillilitre, MicrogramsPerNanolitre, MilligramsPerMicrolitre, KilogramsPerLitre, GramsPerMillilitre, MilligramsPerNanolitre, KilogramsPerMillilitre, GramsPerMicrolitre, GramsPerNanolitre, KilogramsPerMicrolitre, KilogramsPerNanolitre)

  implicit class DensityCons[A](a: A)(implicit num: Numeric[A]) {
    def nanogramsPerLitre: Density[A] = NanogramsPerLitre(a)
    def microgramsPerLitre: Density[A] = MicrogramsPerLitre(a)
    def nanogramsPerMillilitre: Density[A] = NanogramsPerMillilitre(a)
    def microgramsPerMillilitre: Density[A] = MicrogramsPerMillilitre(a)
    def nanogramsPerMicrolitre: Density[A] = NanogramsPerMicrolitre(a)
    def milligramsPerLitre: Density[A] = MilligramsPerLitre(a)
    def nanogramsPerNanolitre: Density[A] = NanogramsPerNanolitre(a)
    def microgramsPerMicrolitre: Density[A] = MicrogramsPerMicrolitre(a)
    def kilogramsPerCubicMeter: Density[A] = KilogramsPerCubicMeter(a)
    def gramsPerLitre: Density[A] = GramsPerLitre(a)
    def milligramsPerMillilitre: Density[A] = MilligramsPerMillilitre(a)
    def microgramsPerNanolitre: Density[A] = MicrogramsPerNanolitre(a)
    def milligramsPerMicrolitre: Density[A] = MilligramsPerMicrolitre(a)
    def kilogramsPerLitre: Density[A] = KilogramsPerLitre(a)
    def gramsPerMillilitre: Density[A] = GramsPerMillilitre(a)
    def milligramsPerNanolitre: Density[A] = MilligramsPerNanolitre(a)
    def kilogramsPerMillilitre: Density[A] = KilogramsPerMillilitre(a)
    def gramsPerMicrolitre: Density[A] = GramsPerMicrolitre(a)
    def gramsPerNanolitre: Density[A] = GramsPerNanolitre(a)
    def kilogramsPerMicrolitre: Density[A] = KilogramsPerMicrolitre(a)
    def kilogramsPerNanolitre: Density[A] = KilogramsPerNanolitre(a)
  }

  lazy val nanogramsPerLitre: Density[Int] = NanogramsPerLitre(1)
  lazy val microgramsPerLitre: Density[Int] = MicrogramsPerLitre(1)
  lazy val nanogramsPerMillilitre: Density[Int] = NanogramsPerMillilitre(1)
  lazy val microgramsPerMillilitre: Density[Int] = MicrogramsPerMillilitre(1)
  lazy val nanogramsPerMicrolitre: Density[Int] = NanogramsPerMicrolitre(1)
  lazy val milligramsPerLitre: Density[Int] = MilligramsPerLitre(1)
  lazy val nanogramsPerNanolitre: Density[Int] = NanogramsPerNanolitre(1)
  lazy val microgramsPerMicrolitre: Density[Int] = MicrogramsPerMicrolitre(1)
  lazy val kilogramsPerCubicMeter: Density[Int] = KilogramsPerCubicMeter(1)
  lazy val gramsPerLitre: Density[Int] = GramsPerLitre(1)
  lazy val milligramsPerMillilitre: Density[Int] = MilligramsPerMillilitre(1)
  lazy val microgramsPerNanolitre: Density[Int] = MicrogramsPerNanolitre(1)
  lazy val milligramsPerMicrolitre: Density[Int] = MilligramsPerMicrolitre(1)
  lazy val kilogramsPerLitre: Density[Int] = KilogramsPerLitre(1)
  lazy val gramsPerMillilitre: Density[Int] = GramsPerMillilitre(1)
  lazy val milligramsPerNanolitre: Density[Int] = MilligramsPerNanolitre(1)
  lazy val kilogramsPerMillilitre: Density[Int] = KilogramsPerMillilitre(1)
  lazy val gramsPerMicrolitre: Density[Int] = GramsPerMicrolitre(1)
  lazy val gramsPerNanolitre: Density[Int] = GramsPerNanolitre(1)
  lazy val kilogramsPerMicrolitre: Density[Int] = KilogramsPerMicrolitre(1)
  lazy val kilogramsPerNanolitre: Density[Int] = KilogramsPerNanolitre(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = DensityNumeric[A]()
  private case class DensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Density.type], y: Quantity[A, Density.type]): Quantity[A, Density.this.type] =
      KilogramsPerCubicMeter(x.to(KilogramsPerCubicMeter) * y.to(KilogramsPerCubicMeter))
  }
}

abstract class DensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Density.type] {
  override lazy val dimension: Density.type = Density
  override def apply[A: Numeric](value: A): Density[A] = Density(value, this)
}

case object NanogramsPerLitre extends DensityUnit("ng/L", 1.0E-9)
case object MicrogramsPerLitre extends DensityUnit("µg/L", 9.999999999999997E-7)
case object NanogramsPerMillilitre extends DensityUnit("ng/ml", 1.0E-6)
case object MicrogramsPerMillilitre extends DensityUnit("µg/ml", 9.999999999999998E-4)
case object NanogramsPerMicrolitre extends DensityUnit("ng/µl", 0.001)
case object MilligramsPerLitre extends DensityUnit("mg/L", 0.001)
case object NanogramsPerNanolitre extends DensityUnit("ng/nl", 0.9999999999999998)
case object MicrogramsPerMicrolitre extends DensityUnit("µg/µl", 0.9999999999999998)
case object KilogramsPerCubicMeter extends DensityUnit("kg/m³", 1) with PrimaryUnit with SiUnit
case object GramsPerLitre extends DensityUnit("g/L", 1.0)
case object MilligramsPerMillilitre extends DensityUnit("mg/ml", 1.0)
case object MicrogramsPerNanolitre extends DensityUnit("µg/nl", 999.9999999999997)
case object MilligramsPerMicrolitre extends DensityUnit("mg/µl", 999.9999999999999)
case object KilogramsPerLitre extends DensityUnit("kg/L", 1000.0)
case object GramsPerMillilitre extends DensityUnit("g/ml", 1000.0000000000001)
case object MilligramsPerNanolitre extends DensityUnit("mg/nl", 999999.9999999998)
case object KilogramsPerMillilitre extends DensityUnit("kg/ml", 1000000.0)
case object GramsPerMicrolitre extends DensityUnit("g/µl", 1000000.0)
case object GramsPerNanolitre extends DensityUnit("g/nl", 9.999999999999999E8)
case object KilogramsPerMicrolitre extends DensityUnit("kg/µl", 9.999999999999999E8)
case object KilogramsPerNanolitre extends DensityUnit("kg/nl", 9.999999999999999E11)
