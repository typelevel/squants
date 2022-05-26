/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class VolumeFlow[A: Numeric] private[squants2] (value: A, unit: VolumeFlowUnit)
  extends Quantity[A, VolumeFlow] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  // END CUSTOM OPS

  def toNanolitresPerDay[B: Numeric](implicit f: A => B): B = toNum[B](NanolitresPerDay)
  def toNanolitresPerHour[B: Numeric](implicit f: A => B): B = toNum[B](NanolitresPerHour)
  def toMicrolitresPerDay[B: Numeric](implicit f: A => B): B = toNum[B](MicrolitresPerDay)
  def toNanolitresPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](NanolitresPerMinute)
  def toMicrolitresPerHour[B: Numeric](implicit f: A => B): B = toNum[B](MicrolitresPerHour)
  def toNanolitresPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](NanolitresPerSecond)
  def toMillilitresPerDay[B: Numeric](implicit f: A => B): B = toNum[B](MillilitresPerDay)
  def toMicrolitresPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](MicrolitresPerMinute)
  def toMillilitresPerHour[B: Numeric](implicit f: A => B): B = toNum[B](MillilitresPerHour)
  def toMicrolitresPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](MicrolitresPerSecond)
  def toLitresPerDay[B: Numeric](implicit f: A => B): B = toNum[B](LitresPerDay)
  def toMillilitresPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](MillilitresPerMinute)
  def toGallonsPerDay[B: Numeric](implicit f: A => B): B = toNum[B](GallonsPerDay)
  def toLitresPerHour[B: Numeric](implicit f: A => B): B = toNum[B](LitresPerHour)
  def toMillilitresPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](MillilitresPerSecond)
  def toGallonsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](GallonsPerHour)
  def toCubicFeetPerHour[B: Numeric](implicit f: A => B): B = toNum[B](CubicFeetPerHour)
  def toLitresPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](LitresPerMinute)
  def toGallonsPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](GallonsPerMinute)
  def toLitresPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](LitresPerSecond)
  def toGallonsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](GallonsPerSecond)
  def toCubicMetersPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](CubicMetersPerSecond)
}

object VolumeFlow extends Dimension[VolumeFlow]("Volume Flow") {

  override def primaryUnit: UnitOfMeasure[VolumeFlow] with PrimaryUnit[VolumeFlow] = CubicMetersPerSecond
  override def siUnit: UnitOfMeasure[VolumeFlow] with SiUnit[VolumeFlow] = CubicMetersPerSecond
  override lazy val units: Set[UnitOfMeasure[VolumeFlow]] = 
    Set(NanolitresPerDay, NanolitresPerHour, MicrolitresPerDay, NanolitresPerMinute, MicrolitresPerHour, NanolitresPerSecond, MillilitresPerDay, MicrolitresPerMinute, MillilitresPerHour, MicrolitresPerSecond, LitresPerDay, MillilitresPerMinute, GallonsPerDay, LitresPerHour, MillilitresPerSecond, GallonsPerHour, CubicFeetPerHour, LitresPerMinute, GallonsPerMinute, LitresPerSecond, GallonsPerSecond, CubicMetersPerSecond)

  implicit class VolumeFlowCons[A](a: A)(implicit num: Numeric[A]) {
    def nanolitresPerDay: VolumeFlow[A] = NanolitresPerDay(a)
    def nanolitresPerHour: VolumeFlow[A] = NanolitresPerHour(a)
    def microlitresPerDay: VolumeFlow[A] = MicrolitresPerDay(a)
    def nanolitresPerMinute: VolumeFlow[A] = NanolitresPerMinute(a)
    def microlitresPerHour: VolumeFlow[A] = MicrolitresPerHour(a)
    def nanolitresPerSecond: VolumeFlow[A] = NanolitresPerSecond(a)
    def millilitresPerDay: VolumeFlow[A] = MillilitresPerDay(a)
    def microlitresPerMinute: VolumeFlow[A] = MicrolitresPerMinute(a)
    def millilitresPerHour: VolumeFlow[A] = MillilitresPerHour(a)
    def microlitresPerSecond: VolumeFlow[A] = MicrolitresPerSecond(a)
    def litresPerDay: VolumeFlow[A] = LitresPerDay(a)
    def millilitresPerMinute: VolumeFlow[A] = MillilitresPerMinute(a)
    def gallonsPerDay: VolumeFlow[A] = GallonsPerDay(a)
    def litresPerHour: VolumeFlow[A] = LitresPerHour(a)
    def millilitresPerSecond: VolumeFlow[A] = MillilitresPerSecond(a)
    def gallonsPerHour: VolumeFlow[A] = GallonsPerHour(a)
    def cubicFeetPerHour: VolumeFlow[A] = CubicFeetPerHour(a)
    def litresPerMinute: VolumeFlow[A] = LitresPerMinute(a)
    def gallonsPerMinute: VolumeFlow[A] = GallonsPerMinute(a)
    def litresPerSecond: VolumeFlow[A] = LitresPerSecond(a)
    def gallonsPerSecond: VolumeFlow[A] = GallonsPerSecond(a)
    def cubicMetersPerSecond: VolumeFlow[A] = CubicMetersPerSecond(a)
  }

  lazy val nanolitresPerDay: VolumeFlow[Int] = NanolitresPerDay(1)
  lazy val nanolitresPerHour: VolumeFlow[Int] = NanolitresPerHour(1)
  lazy val microlitresPerDay: VolumeFlow[Int] = MicrolitresPerDay(1)
  lazy val nanolitresPerMinute: VolumeFlow[Int] = NanolitresPerMinute(1)
  lazy val microlitresPerHour: VolumeFlow[Int] = MicrolitresPerHour(1)
  lazy val nanolitresPerSecond: VolumeFlow[Int] = NanolitresPerSecond(1)
  lazy val millilitresPerDay: VolumeFlow[Int] = MillilitresPerDay(1)
  lazy val microlitresPerMinute: VolumeFlow[Int] = MicrolitresPerMinute(1)
  lazy val millilitresPerHour: VolumeFlow[Int] = MillilitresPerHour(1)
  lazy val microlitresPerSecond: VolumeFlow[Int] = MicrolitresPerSecond(1)
  lazy val litresPerDay: VolumeFlow[Int] = LitresPerDay(1)
  lazy val millilitresPerMinute: VolumeFlow[Int] = MillilitresPerMinute(1)
  lazy val gallonsPerDay: VolumeFlow[Int] = GallonsPerDay(1)
  lazy val litresPerHour: VolumeFlow[Int] = LitresPerHour(1)
  lazy val millilitresPerSecond: VolumeFlow[Int] = MillilitresPerSecond(1)
  lazy val gallonsPerHour: VolumeFlow[Int] = GallonsPerHour(1)
  lazy val cubicFeetPerHour: VolumeFlow[Int] = CubicFeetPerHour(1)
  lazy val litresPerMinute: VolumeFlow[Int] = LitresPerMinute(1)
  lazy val gallonsPerMinute: VolumeFlow[Int] = GallonsPerMinute(1)
  lazy val litresPerSecond: VolumeFlow[Int] = LitresPerSecond(1)
  lazy val gallonsPerSecond: VolumeFlow[Int] = GallonsPerSecond(1)
  lazy val cubicMetersPerSecond: VolumeFlow[Int] = CubicMetersPerSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, VolumeFlow] = VolumeFlowNumeric[A]()
  private case class VolumeFlowNumeric[A: Numeric]() extends QuantityNumeric[A, VolumeFlow](this) {
    override def times(x: Quantity[A, VolumeFlow], y: Quantity[A, VolumeFlow]): Quantity[A, VolumeFlow] =
      CubicMetersPerSecond(x.to(CubicMetersPerSecond) * y.to(CubicMetersPerSecond))
  }
}

abstract class VolumeFlowUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[VolumeFlow] {
  override def dimension: Dimension[VolumeFlow] = VolumeFlow
  override def apply[A: Numeric](value: A): VolumeFlow[A] = VolumeFlow(value, this)
}

case object NanolitresPerDay extends VolumeFlowUnit("nl/d", 1.1574074074074075E-17) with SiUnit[VolumeFlow]
case object NanolitresPerHour extends VolumeFlowUnit("nl/h", 2.7777777777777785E-16) with SiUnit[VolumeFlow]
case object MicrolitresPerDay extends VolumeFlowUnit("µl/d", 1.1574074074074075E-14) with SiUnit[VolumeFlow]
case object NanolitresPerMinute extends VolumeFlowUnit("nl/min", 1.666666666666667E-14) with SiUnit[VolumeFlow]
case object MicrolitresPerHour extends VolumeFlowUnit("µl/h", 2.777777777777778E-13) with SiUnit[VolumeFlow]
case object NanolitresPerSecond extends VolumeFlowUnit("nl/s", 1.0000000000000002E-12) with SiUnit[VolumeFlow]
case object MillilitresPerDay extends VolumeFlowUnit("ml/d", 1.1574074074074074E-11) with SiUnit[VolumeFlow]
case object MicrolitresPerMinute extends VolumeFlowUnit("µl/min", 1.6666666666666667E-11) with SiUnit[VolumeFlow]
case object MillilitresPerHour extends VolumeFlowUnit("ml/h", 2.7777777777777777E-10) with SiUnit[VolumeFlow]
case object MicrolitresPerSecond extends VolumeFlowUnit("µl/s", MetricSystem.Nano) with SiUnit[VolumeFlow]
case object LitresPerDay extends VolumeFlowUnit("L/d", 1.1574074074074074E-8) with SiUnit[VolumeFlow]
case object MillilitresPerMinute extends VolumeFlowUnit("ml/min", 1.6666666666666667E-8) with SiUnit[VolumeFlow]
case object GallonsPerDay extends VolumeFlowUnit("GPD", 4.3812636388888886E-8)
case object LitresPerHour extends VolumeFlowUnit("L/h", 2.7777777777777776E-7) with SiUnit[VolumeFlow]
case object MillilitresPerSecond extends VolumeFlowUnit("ml/s", MetricSystem.Micro) with SiUnit[VolumeFlow]
case object GallonsPerHour extends VolumeFlowUnit("GPH", 1.0515032733333332E-6)
case object CubicFeetPerHour extends VolumeFlowUnit("ft³/hr", 7.86583791483871E-6)
case object LitresPerMinute extends VolumeFlowUnit("L/min", 1.6666666666666667E-5) with SiUnit[VolumeFlow]
case object GallonsPerMinute extends VolumeFlowUnit("GPM", 6.309019639999999E-5)
case object LitresPerSecond extends VolumeFlowUnit("L/s", MetricSystem.Milli) with SiUnit[VolumeFlow]
case object GallonsPerSecond extends VolumeFlowUnit("GPS", 0.0037854117839999997)
case object CubicMetersPerSecond extends VolumeFlowUnit("m³/s", 1) with PrimaryUnit[VolumeFlow] with SiUnit[VolumeFlow]
