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

final case class Power[A: Numeric] private[squants2] (value: A, unit: PowerUnit)
  extends Quantity[A, Power] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
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

  def toErgsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](ErgsPerSecond)
  def toMilliwatts[B: Numeric](implicit f: A => B): B = toNum[B](Milliwatts)
  def toBtusPerHour[B: Numeric](implicit f: A => B): B = toNum[B](BtusPerHour)
  def toWatts[B: Numeric](implicit f: A => B): B = toNum[B](Watts)
  def toKilowatts[B: Numeric](implicit f: A => B): B = toNum[B](Kilowatts)
  def toMegawatts[B: Numeric](implicit f: A => B): B = toNum[B](Megawatts)
  def toGigawatts[B: Numeric](implicit f: A => B): B = toNum[B](Gigawatts)
  def toSolarLuminosities[B: Numeric](implicit f: A => B): B = toNum[B](SolarLuminosities)
}

object Power extends Dimension[Power]("Power") {

  override def primaryUnit: UnitOfMeasure[Power] with PrimaryUnit[Power] = Watts
  override def siUnit: UnitOfMeasure[Power] with SiUnit[Power] = Watts
  override lazy val units: Set[UnitOfMeasure[Power]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, Power] = PowerNumeric[A]()
  private case class PowerNumeric[A: Numeric]() extends QuantityNumeric[A, Power](this) {
    override def times(x: Quantity[A, Power], y: Quantity[A, Power]): Quantity[A, Power] =
      Watts(x.to(Watts) * y.to(Watts))
  }
}

abstract class PowerUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Power] {
  override def dimension: Dimension[Power] = Power
  override def apply[A: Numeric](value: A): Power[A] = Power(value, this)
}

case object ErgsPerSecond extends PowerUnit("erg/s", 1.0E-7)
case object Milliwatts extends PowerUnit("mW", MetricSystem.Milli) with SiUnit[Power]
case object BtusPerHour extends PowerUnit("Btu/hr", 0.2930710701722222)
case object Watts extends PowerUnit("W", 1) with PrimaryUnit[Power] with SiUnit[Power]
case object Kilowatts extends PowerUnit("kW", MetricSystem.Kilo) with SiUnit[Power]
case object Megawatts extends PowerUnit("MW", MetricSystem.Mega) with SiUnit[Power]
case object Gigawatts extends PowerUnit("GW", MetricSystem.Giga) with SiUnit[Power]
case object SolarLuminosities extends PowerUnit("L☉", 3.828E26)
