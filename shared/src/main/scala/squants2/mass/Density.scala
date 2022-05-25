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

  //  def *[B](that: Volume[B])(implicit f: B => A): Mass[A] = ???
  // END CUSTOM OPS

  def toNanogramsPerLitre[B: Numeric](implicit f: A => B): B = toNum[B](NanogramsPerLitre)
  def toMicrogramsPerLitre[B: Numeric](implicit f: A => B): B = toNum[B](MicrogramsPerLitre)
  def toNanogramsPerMillilitre[B: Numeric](implicit f: A => B): B = toNum[B](NanogramsPerMillilitre)
  def toMicrogramsPerMillilitre[B: Numeric](implicit f: A => B): B = toNum[B](MicrogramsPerMillilitre)
  def toMilligramsPerLitre[B: Numeric](implicit f: A => B): B = toNum[B](MilligramsPerLitre)
  def toNanogramsPerMicrolitre[B: Numeric](implicit f: A => B): B = toNum[B](NanogramsPerMicrolitre)
  def toNanogramsPerNanolitre[B: Numeric](implicit f: A => B): B = toNum[B](NanogramsPerNanolitre)
  def toMicrogramsPerMicrolitre[B: Numeric](implicit f: A => B): B = toNum[B](MicrogramsPerMicrolitre)
  def toGramsPerLitre[B: Numeric](implicit f: A => B): B = toNum[B](GramsPerLitre)
  def toKilogramsPerCubicMeter[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerCubicMeter)
  def toMilligramsPerMillilitre[B: Numeric](implicit f: A => B): B = toNum[B](MilligramsPerMillilitre)
  def toMicrogramsPerNanolitre[B: Numeric](implicit f: A => B): B = toNum[B](MicrogramsPerNanolitre)
  def toMilligramsPerMicrolitre[B: Numeric](implicit f: A => B): B = toNum[B](MilligramsPerMicrolitre)
  def toKilogramsPerLitre[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerLitre)
  def toGramsPerMillilitre[B: Numeric](implicit f: A => B): B = toNum[B](GramsPerMillilitre)
  def toMilligramsPerNanolitre[B: Numeric](implicit f: A => B): B = toNum[B](MilligramsPerNanolitre)
  def toGramsPerMicrolitre[B: Numeric](implicit f: A => B): B = toNum[B](GramsPerMicrolitre)
  def toKilogramsPerMillilitre[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerMillilitre)
  def toGramsPerNanolitre[B: Numeric](implicit f: A => B): B = toNum[B](GramsPerNanolitre)
  def toKilogramsPerMicrolitre[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerMicrolitre)
  def toKilogramsPerNanolitre[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerNanolitre)
}

object Density extends Dimension("Density") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = KilogramsPerCubicMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = KilogramsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(NanogramsPerLitre, MicrogramsPerLitre, NanogramsPerMillilitre, MicrogramsPerMillilitre, MilligramsPerLitre, NanogramsPerMicrolitre, NanogramsPerNanolitre, MicrogramsPerMicrolitre, GramsPerLitre, KilogramsPerCubicMeter, MilligramsPerMillilitre, MicrogramsPerNanolitre, MilligramsPerMicrolitre, KilogramsPerLitre, GramsPerMillilitre, MilligramsPerNanolitre, GramsPerMicrolitre, KilogramsPerMillilitre, GramsPerNanolitre, KilogramsPerMicrolitre, KilogramsPerNanolitre)

  implicit class DensityCons[A](a: A)(implicit num: Numeric[A]) {
    def nanogramsPerLitre: Density[A] = NanogramsPerLitre(a)
    def microgramsPerLitre: Density[A] = MicrogramsPerLitre(a)
    def nanogramsPerMillilitre: Density[A] = NanogramsPerMillilitre(a)
    def microgramsPerMillilitre: Density[A] = MicrogramsPerMillilitre(a)
    def milligramsPerLitre: Density[A] = MilligramsPerLitre(a)
    def nanogramsPerMicrolitre: Density[A] = NanogramsPerMicrolitre(a)
    def nanogramsPerNanolitre: Density[A] = NanogramsPerNanolitre(a)
    def microgramsPerMicrolitre: Density[A] = MicrogramsPerMicrolitre(a)
    def gramsPerLitre: Density[A] = GramsPerLitre(a)
    def kilogramsPerCubicMeter: Density[A] = KilogramsPerCubicMeter(a)
    def milligramsPerMillilitre: Density[A] = MilligramsPerMillilitre(a)
    def microgramsPerNanolitre: Density[A] = MicrogramsPerNanolitre(a)
    def milligramsPerMicrolitre: Density[A] = MilligramsPerMicrolitre(a)
    def kilogramsPerLitre: Density[A] = KilogramsPerLitre(a)
    def gramsPerMillilitre: Density[A] = GramsPerMillilitre(a)
    def milligramsPerNanolitre: Density[A] = MilligramsPerNanolitre(a)
    def gramsPerMicrolitre: Density[A] = GramsPerMicrolitre(a)
    def kilogramsPerMillilitre: Density[A] = KilogramsPerMillilitre(a)
    def gramsPerNanolitre: Density[A] = GramsPerNanolitre(a)
    def kilogramsPerMicrolitre: Density[A] = KilogramsPerMicrolitre(a)
    def kilogramsPerNanolitre: Density[A] = KilogramsPerNanolitre(a)
  }

  lazy val nanogramsPerLitre: Density[Int] = NanogramsPerLitre(1)
  lazy val microgramsPerLitre: Density[Int] = MicrogramsPerLitre(1)
  lazy val nanogramsPerMillilitre: Density[Int] = NanogramsPerMillilitre(1)
  lazy val microgramsPerMillilitre: Density[Int] = MicrogramsPerMillilitre(1)
  lazy val milligramsPerLitre: Density[Int] = MilligramsPerLitre(1)
  lazy val nanogramsPerMicrolitre: Density[Int] = NanogramsPerMicrolitre(1)
  lazy val nanogramsPerNanolitre: Density[Int] = NanogramsPerNanolitre(1)
  lazy val microgramsPerMicrolitre: Density[Int] = MicrogramsPerMicrolitre(1)
  lazy val gramsPerLitre: Density[Int] = GramsPerLitre(1)
  lazy val kilogramsPerCubicMeter: Density[Int] = KilogramsPerCubicMeter(1)
  lazy val milligramsPerMillilitre: Density[Int] = MilligramsPerMillilitre(1)
  lazy val microgramsPerNanolitre: Density[Int] = MicrogramsPerNanolitre(1)
  lazy val milligramsPerMicrolitre: Density[Int] = MilligramsPerMicrolitre(1)
  lazy val kilogramsPerLitre: Density[Int] = KilogramsPerLitre(1)
  lazy val gramsPerMillilitre: Density[Int] = GramsPerMillilitre(1)
  lazy val milligramsPerNanolitre: Density[Int] = MilligramsPerNanolitre(1)
  lazy val gramsPerMicrolitre: Density[Int] = GramsPerMicrolitre(1)
  lazy val kilogramsPerMillilitre: Density[Int] = KilogramsPerMillilitre(1)
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
  override def dimension: Density.type = Density
  override def apply[A: Numeric](value: A): Density[A] = Density(value, this)
}

case object NanogramsPerLitre extends DensityUnit("ng/L", MetricSystem.Nano)
case object MicrogramsPerLitre extends DensityUnit("µg/L", 9.999999999999997E-7)
case object NanogramsPerMillilitre extends DensityUnit("ng/ml", MetricSystem.Micro)
case object MicrogramsPerMillilitre extends DensityUnit("µg/ml", 9.999999999999998E-4)
case object MilligramsPerLitre extends DensityUnit("mg/L", MetricSystem.Milli)
case object NanogramsPerMicrolitre extends DensityUnit("ng/µl", MetricSystem.Milli)
case object NanogramsPerNanolitre extends DensityUnit("ng/nl", 0.9999999999999998)
case object MicrogramsPerMicrolitre extends DensityUnit("µg/µl", 0.9999999999999998)
case object GramsPerLitre extends DensityUnit("g/L", 1)
case object KilogramsPerCubicMeter extends DensityUnit("kg/m³", 1) with PrimaryUnit with SiUnit
case object MilligramsPerMillilitre extends DensityUnit("mg/ml", 1)
case object MicrogramsPerNanolitre extends DensityUnit("µg/nl", 999.9999999999997)
case object MilligramsPerMicrolitre extends DensityUnit("mg/µl", 999.9999999999999)
case object KilogramsPerLitre extends DensityUnit("kg/L", MetricSystem.Kilo)
case object GramsPerMillilitre extends DensityUnit("g/ml", 1000.0000000000001)
case object MilligramsPerNanolitre extends DensityUnit("mg/nl", 999999.9999999998)
case object GramsPerMicrolitre extends DensityUnit("g/µl", MetricSystem.Mega)
case object KilogramsPerMillilitre extends DensityUnit("kg/ml", MetricSystem.Mega)
case object GramsPerNanolitre extends DensityUnit("g/nl", 9.999999999999999E8)
case object KilogramsPerMicrolitre extends DensityUnit("kg/µl", 9.999999999999999E8)
case object KilogramsPerNanolitre extends DensityUnit("kg/nl", 9.999999999999999E11)
