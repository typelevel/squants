/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.energy

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class PowerRamp[A: Numeric] private[squants2] (value: A, unit: PowerRampUnit)
  extends Quantity[A, PowerRamp] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): Energy[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeIntegral[A] = ???
  // END CUSTOM OPS

  def toWattsPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerMinute)
  def toWattsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerHour)
  def toKilowattsPerMinute[B: Numeric](implicit f: A => B): B = toNum[B](KilowattsPerMinute)
  def toKilowattsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](KilowattsPerHour)
  def toMegawattsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](MegawattsPerHour)
  def toGigawattsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](GigawattsPerHour)
}

object PowerRamp extends Dimension[PowerRamp]("Power Ramp") {

  override def primaryUnit: UnitOfMeasure[PowerRamp] with PrimaryUnit[PowerRamp] = WattsPerHour
  override def siUnit: UnitOfMeasure[PowerRamp] with SiUnit[PowerRamp] = WattsPerHour
  override lazy val units: Set[UnitOfMeasure[PowerRamp]] = 
    Set(WattsPerMinute, WattsPerHour, KilowattsPerMinute, KilowattsPerHour, MegawattsPerHour, GigawattsPerHour)

  implicit class PowerRampCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerMinute: PowerRamp[A] = WattsPerMinute(a)
    def wattsPerHour: PowerRamp[A] = WattsPerHour(a)
    def kilowattsPerMinute: PowerRamp[A] = KilowattsPerMinute(a)
    def kilowattsPerHour: PowerRamp[A] = KilowattsPerHour(a)
    def megawattsPerHour: PowerRamp[A] = MegawattsPerHour(a)
    def gigawattsPerHour: PowerRamp[A] = GigawattsPerHour(a)
  }

  lazy val wattsPerMinute: PowerRamp[Int] = WattsPerMinute(1)
  lazy val wattsPerHour: PowerRamp[Int] = WattsPerHour(1)
  lazy val kilowattsPerMinute: PowerRamp[Int] = KilowattsPerMinute(1)
  lazy val kilowattsPerHour: PowerRamp[Int] = KilowattsPerHour(1)
  lazy val megawattsPerHour: PowerRamp[Int] = MegawattsPerHour(1)
  lazy val gigawattsPerHour: PowerRamp[Int] = GigawattsPerHour(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, PowerRamp] = PowerRampNumeric[A]()
  private case class PowerRampNumeric[A: Numeric]() extends QuantityNumeric[A, PowerRamp](this) {
    override def times(x: Quantity[A, PowerRamp], y: Quantity[A, PowerRamp]): Quantity[A, PowerRamp] =
      WattsPerHour(x.to(WattsPerHour) * y.to(WattsPerHour))
  }
}

abstract class PowerRampUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[PowerRamp] {
  override def dimension: Dimension[PowerRamp] = PowerRamp
  override def apply[A: Numeric](value: A): PowerRamp[A] = PowerRamp(value, this)
}

case object WattsPerMinute extends PowerRampUnit("W/m", 0.016666666666666666) with SiUnit[PowerRamp]
case object WattsPerHour extends PowerRampUnit("W/h", 1) with PrimaryUnit[PowerRamp] with SiUnit[PowerRamp]
case object KilowattsPerMinute extends PowerRampUnit("kW/m", 16.666666666666668) with SiUnit[PowerRamp]
case object KilowattsPerHour extends PowerRampUnit("kW/h", MetricSystem.Kilo) with SiUnit[PowerRamp]
case object MegawattsPerHour extends PowerRampUnit("MW/h", MetricSystem.Mega) with SiUnit[PowerRamp]
case object GigawattsPerHour extends PowerRampUnit("GW/h", MetricSystem.Giga) with SiUnit[PowerRamp]
