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

final case class PowerRamp[A: Numeric] private [squants2]  (value: A, unit: PowerRampUnit)
  extends Quantity[A, PowerRamp.type] {
  override type Q[B] = PowerRamp[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWattsPerMinute: A = to(WattsPerMinute)
  def toWattsPerHour: A = to(WattsPerHour)
  def toKilowattsPerMinute: A = to(KilowattsPerMinute)
  def toKilowattsPerHour: A = to(KilowattsPerHour)
  def toMegawattsPerHour: A = to(MegawattsPerHour)
  def toGigawattsPerHour: A = to(GigawattsPerHour)
}

object PowerRamp extends Dimension("Power Ramp") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerHour
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerHour
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PowerRampNumeric[A]()
  private case class PowerRampNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, PowerRamp.type], y: Quantity[A, PowerRamp.type]): Quantity[A, PowerRamp.this.type] =
      WattsPerHour(x.to(WattsPerHour) * y.to(WattsPerHour))
  }
}

abstract class PowerRampUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[PowerRamp.type] {
  override def dimension: PowerRamp.type = PowerRamp
  override def apply[A: Numeric](value: A): PowerRamp[A] = PowerRamp(value, this)
}

case object WattsPerMinute extends PowerRampUnit("W/m", 0.016666666666666666) with SiUnit
case object WattsPerHour extends PowerRampUnit("W/h", 1) with PrimaryUnit with SiUnit
case object KilowattsPerMinute extends PowerRampUnit("kW/m", 16.666666666666668) with SiUnit
case object KilowattsPerHour extends PowerRampUnit("kW/h", 1000) with SiUnit
case object MegawattsPerHour extends PowerRampUnit("MW/h", 1000000) with SiUnit
case object GigawattsPerHour extends PowerRampUnit("GW/h", 1000000000) with SiUnit
