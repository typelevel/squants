/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.time.{ TimeIntegral, TimeDerivative, Hours }
import squants.radio._
import squants.space.{ SquaredRadians, SquareMeters, SolidAngle }
import squants.electro.{ Volts, ElectricCurrent, Amperes, ElectricPotential }
import squants.radio.Irradiance
import squants.radio.SpectralPower
import squants.radio.RadiantIntensity
import scala.Some

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
final class Power private (val value: Double)
    extends Quantity[Power] with PhysicalQuantity
    with TimeDerivative[Energy] with TimeIntegral[PowerRamp] {

  def valueUnit = Power.valueUnit
  def change = WattHours(value)
  def time = Hours(1)

  def /(that: Time): PowerRamp = PowerRamp(this, that)
  def /(that: PowerRamp): Time = Hours(value / that.value)
  def /(that: Length): SpectralPower = WattsPerMeter(toWatts / that.toMeters)
  def /(that: SpectralPower): Length = Meters(toWatts / that.toWattsPerMeter)
  def /(that: Area): Irradiance = WattsPerSquareMeter(toWatts / that.toSquareMeters)
  def /(that: Irradiance): Area = SquareMeters(this.toWatts / that.toWattsPerSquareMeter)
  def /(that: RadiantIntensity): SolidAngle = SquaredRadians(toWatts / that.toWattsPerSteradian)
  def /(that: SolidAngle): RadiantIntensity = WattsPerSteradian(toWatts / that.toSteradians)
  def /(that: ElectricPotential): ElectricCurrent = Amperes(toWatts / that.toVolts)
  def /(that: ElectricCurrent): ElectricPotential = Volts(toWatts / that.toAmperes)

  def toMilliwatts = to(Milliwatts)
  def toWatts = to(Watts)
  def toKilowatts = to(Kilowatts)
  def toMegawatts = to(Megawatts)
  def toGigawatts = to(Gigawatts)
  def toBtusPerHour = to(BtusPerHour)

  override def toString = toString(this match {
    case Gigawatts(gw) if gw >= 1.0 ⇒ Gigawatts
    case Megawatts(mw) if mw >= 1.0 ⇒ Megawatts
    case Kilowatts(kw) if kw >= 1.0 ⇒ Kilowatts
    case Watts(w) if w >= 1.0       ⇒ Watts
    case Watts(0)                   ⇒ Watts
    case _                          ⇒ Milliwatts
  })
}

/**
 * Companion object for [[squants.energy.Power]]
 */
object Power extends QuantityCompanion[Power] {
  private[energy] def apply[A](n: A)(implicit num: Numeric[A]) = new Power(num.toDouble(n))
  def apply(energy: Energy, time: Time): Power = apply(energy.toWattHours / time.toHours)
  def apply(s: String) = parseString(s)

  def name = "Power"
  def valueUnit = Watts
  def units = Set(Watts, Milliwatts, Kilowatts, Megawatts, Gigawatts, BtusPerHour)
}

trait PowerUnit extends UnitOfMeasure[Power] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Power(convertFrom(n))
  def unapply(power: Power) = Some(convertTo(power.value))
}

object Milliwatts extends PowerUnit {
  val multiplier = MetricSystem.Milli
  val symbol = "mW"
}

object Watts extends PowerUnit with ValueUnit {
  val symbol = "W"
}

object Kilowatts extends PowerUnit {
  val multiplier = MetricSystem.Kilo
  val symbol = "kW"
}

object Megawatts extends PowerUnit {
  val multiplier = MetricSystem.Mega
  val symbol = "MW"
}

object Gigawatts extends PowerUnit {
  val multiplier = MetricSystem.Giga
  val symbol = "GW"
}

object BtusPerHour extends PowerUnit {
  val multiplier = EnergyConversions.btuMultiplier
  val symbol = "Btu/hr"
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
  }

  implicit class PowerStringConversions(s: String) {
    def toPower = Power(s)
  }

  implicit object PowerNumeric extends AbstractQuantityNumeric[Power](Power.valueUnit)
}

