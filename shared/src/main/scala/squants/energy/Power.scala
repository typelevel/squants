/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.electro.{ Amperes, ElectricCurrent, ElectricPotential, Volts }
import squants.radio.{ Irradiance, RadiantIntensity, SpectralPower, _ }
import squants.space.{ SolidAngle, SquareMeters, SquaredRadians }
import squants.time.{ Hours, TimeDerivative, TimeIntegral }

/**
 * Represents a quantity of power / load, the rate at which energy produced or used
 *
 * The first time derivative of [[squants.energy.Energy]]
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.Watts]]
 */
final class Power private (val value: Double, val unit: PowerUnit)
    extends Quantity[Power]
    with TimeDerivative[Energy]
    with TimeIntegral[PowerRamp] {

  def dimension = Power

  protected[squants] def timeIntegrated = WattHours(toWatts)
  protected def timeDerived = WattsPerHour(toWatts)
  protected[squants] def time = Hours(1)

  def /(that: Length): SpectralPower = WattsPerMeter(toWatts / that.toMeters)
  def /(that: SpectralPower): Length = Meters(toWatts / that.toWattsPerMeter)
  def /(that: Area): Irradiance = WattsPerSquareMeter(toWatts / that.toSquareMeters)
  def /(that: Irradiance): Area = SquareMeters(this.toWatts / that.toWattsPerSquareMeter)
  def /(that: RadiantIntensity): SolidAngle = SquaredRadians(toWatts / that.toWattsPerSteradian)
  def /(that: SolidAngle): RadiantIntensity = WattsPerSteradian(toWatts / that.toSteradians)
  def /(that: ElectricPotential): ElectricCurrent = Amperes(toWatts / that.toVolts)
  def /(that: ElectricCurrent): ElectricPotential = Volts(toWatts / that.toAmperes)
  def /(that: Volume) = ??? // Power Density

  def toMilliwatts = to(Milliwatts)
  def toWatts = to(Watts)
  def toKilowatts = to(Kilowatts)
  def toMegawatts = to(Megawatts)
  def toGigawatts = to(Gigawatts)
  def toBtusPerHour = to(BtusPerHour)
  def toErgsPerSecond = to(ErgsPerSecond)
  def toSolarLuminosities = to(SolarLuminosities)
}

/**
 * Companion object for [[squants.energy.Power]]
 */
object Power extends Dimension[Power] {
  private[energy] def apply[A](n: A, unit: PowerUnit)(implicit num: Numeric[A]) = new Power(num.toDouble(n), unit)
  def apply(energy: Energy, time: Time): Power = apply(energy.toWattHours / time.toHours, Watts)
  def apply = parse _

  def name = "Power"
  def primaryUnit = Watts
  def siUnit = Watts
  def units = Set(Watts, Milliwatts, Kilowatts, Megawatts, Gigawatts, BtusPerHour, ErgsPerSecond, SolarLuminosities)
}

trait PowerUnit extends UnitOfMeasure[Power] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Power(n, this)
}

object Milliwatts extends PowerUnit {
  val conversionFactor = MetricSystem.Milli
  val symbol = "mW"
}

object Watts extends PowerUnit with PrimaryUnit with SiUnit {
  val symbol = "W"
}

object Kilowatts extends PowerUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kW"
}

object Megawatts extends PowerUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "MW"
}

object Gigawatts extends PowerUnit {
  val conversionFactor = MetricSystem.Giga
  val symbol = "GW"
}

object BtusPerHour extends PowerUnit {
  val conversionFactor = EnergyConversions.btuMultiplier
  val symbol = "Btu/hr"
}

object ErgsPerSecond extends PowerUnit {
  val conversionFactor = 1e-7
  val symbol = Ergs.symbol + "/" + Seconds.symbol
}

object SolarLuminosities extends PowerUnit {
  val conversionFactor = 3.828e26
  val symbol = "L☉"
}

object PowerConversions {
  lazy val milliwatt = Milliwatts(1)
  lazy val mW = milliwatt
  lazy val watt = Watts(1)
  lazy val W = watt
  lazy val kilowatt = Kilowatts(1)
  lazy val kW = kilowatt
  lazy val megawatt = Megawatts(1)
  lazy val MW = megawatt
  lazy val gigawatt = Gigawatts(1)
  lazy val GW = gigawatt
  lazy val solarLuminosity = SolarLuminosities(1)

  implicit class PowerConversions[A](n: A)(implicit num: Numeric[A]) {
    def mW = Milliwatts(n)
    def W = Watts(n)
    def kW = Kilowatts(n)
    def MW = Megawatts(n)
    def GW = Gigawatts(n)
    def milliwatts = Milliwatts(n)
    def watts = Watts(n)
    def kilowatts = Kilowatts(n)
    def megawatts = Megawatts(n)
    def gigawatts = Gigawatts(n)
    def BTUph = BtusPerHour(n)
    def ergsPerSecond = ErgsPerSecond(n)
    def solarLuminosities = SolarLuminosities(n)
  }

  implicit class PowerStringConversions(s: String) {
    def toPower = Power(s)
  }

  implicit object PowerNumeric extends AbstractQuantityNumeric[Power](Power.primaryUnit)
}

