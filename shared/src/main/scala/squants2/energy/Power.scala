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

final case class Power[A: Numeric] private [squants2]  (value: A, unit: PowerUnit)
  extends Quantity[A, Power.type] {
  override type Q[B] = Power[B]

  // BEGIN CUSTOM OPS
  //  def /[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: Frequency[B])(implicit f: B => A): Quantity[A] = ???
  //  def *[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): SpectralPower[A] = ???
  //  def /[B](that: SpectralPower[B])(implicit f: B => A): Length[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Irradiance[A] = ???
  //  def /[B](that: Irradiance[B])(implicit f: B => A): Area[A] = ???
  //  def /[B](that: RadiantIntensity[B])(implicit f: B => A): SolidAngle[A] = ???
  //  def /[B](that: SolidAngle[B])(implicit f: B => A): RadiantIntensity[A] = ???
  //  def /[B](that: ElectricPotential[B])(implicit f: B => A): ElectricCurrent[A] = ???
  //  def /[B](that: ElectricCurrent[B])(implicit f: B => A): ElectricPotential[A] = ???
  //  def /[B](that: Volume[B])(implicit f: B => A): PowerDensity[A] = ???
  // END CUSTOM OPS

  def toErgsPerSecond: A = to(ErgsPerSecond)
  def toMilliwatts: A = to(Milliwatts)
  def toBtusPerHour: A = to(BtusPerHour)
  def toWatts: A = to(Watts)
  def toKilowatts: A = to(Kilowatts)
  def toMegawatts: A = to(Megawatts)
  def toGigawatts: A = to(Gigawatts)
  def toSolarLuminosities: A = to(SolarLuminosities)
}

object Power extends Dimension("Power") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Watts
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Watts
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(ErgsPerSecond, Milliwatts, BtusPerHour, Watts, Kilowatts, Megawatts, Gigawatts, SolarLuminosities)

  implicit class PowerCons[A](a: A)(implicit num: Numeric[A]) {
    def ergsPerSecond: Power[A] = ErgsPerSecond(a)
    def milliwatts: Power[A] = Milliwatts(a)
    def btusPerHour: Power[A] = BtusPerHour(a)
    def watts: Power[A] = Watts(a)
    def kilowatts: Power[A] = Kilowatts(a)
    def megawatts: Power[A] = Megawatts(a)
    def gigawatts: Power[A] = Gigawatts(a)
    def solarLuminosities: Power[A] = SolarLuminosities(a)
  }

  lazy val ergsPerSecond: Power[Int] = ErgsPerSecond(1)
  lazy val milliwatts: Power[Int] = Milliwatts(1)
  lazy val btusPerHour: Power[Int] = BtusPerHour(1)
  lazy val watts: Power[Int] = Watts(1)
  lazy val kilowatts: Power[Int] = Kilowatts(1)
  lazy val megawatts: Power[Int] = Megawatts(1)
  lazy val gigawatts: Power[Int] = Gigawatts(1)
  lazy val solarLuminosities: Power[Int] = SolarLuminosities(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PowerNumeric[A]()
  private case class PowerNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Power.type], y: Quantity[A, Power.type]): Quantity[A, Power.this.type] =
      Watts(x.to(Watts) * y.to(Watts))
  }
}

abstract class PowerUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Power.type] {
  override def dimension: Power.type = Power
  override def apply[A: Numeric](value: A): Power[A] = Power(value, this)
}

case object ErgsPerSecond extends PowerUnit("erg/s", 1.0E-7)
case object Milliwatts extends PowerUnit("mW", MetricSystem.Milli) with SiUnit
case object BtusPerHour extends PowerUnit("Btu/hr", 0.2930710701722222)
case object Watts extends PowerUnit("W", 1) with PrimaryUnit with SiUnit
case object Kilowatts extends PowerUnit("kW", MetricSystem.Kilo) with SiUnit
case object Megawatts extends PowerUnit("MW", MetricSystem.Mega) with SiUnit
case object Gigawatts extends PowerUnit("GW", MetricSystem.Giga) with SiUnit
case object SolarLuminosities extends PowerUnit("L☉", 3.828E26)
