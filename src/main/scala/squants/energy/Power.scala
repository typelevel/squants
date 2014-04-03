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

  def valueUnit = Watts
  def change = WattHours(value)
  def time = Hours(1)

  def /(that: Time): PowerRamp = PowerRamp(this, that)
  def /(that: PowerRamp): Time = Hours(value / that.value)
  def /(that: Length): SpectralPower = WattsPerMeter(toWatts / that.toMeters)
  def /(that: SpectralPower): Length = Meters(toWatts / that.toWattsPerMeter)
  def /(that: Area): Irradiance = Irradiance(this, that)
  def /(that: Irradiance): Area = SquareMeters(this.toWatts / that.toWattsPerSquareMeter)
  def /(that: RadiantIntensity): SolidAngle = SquaredRadians(toWatts / that.toWattsPerSteradian)
  def /(that: SolidAngle): RadiantIntensity = RadiantIntensity(this, that)
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
object Power {
  private[energy] def apply(value: Double) = new Power(value)
  def apply(energy: Energy, time: Time): Power = apply(energy.toWattHours / time.toHours)
  def apply(s: String): Either[String, Power] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+) *(mW|W|kW|MW|GW|Btu/hr)".r
    s match {
      case regex(value, Milliwatts.symbol)  ⇒ Right(Milliwatts(value.toDouble))
      case regex(value, Watts.symbol)       ⇒ Right(Watts(value.toDouble))
      case regex(value, Kilowatts.symbol)   ⇒ Right(Kilowatts(value.toDouble))
      case regex(value, Megawatts.symbol)   ⇒ Right(Megawatts(value.toDouble))
      case regex(value, Gigawatts.symbol)   ⇒ Right(Gigawatts(value.toDouble))
      case regex(value, BtusPerHour.symbol) ⇒ Right(BtusPerHour(value.toDouble))
      case _                                ⇒ Left(s"Unable to parse $s as Power")
    }
  }
}

trait PowerUnit extends UnitOfMeasure[Power] with UnitMultiplier {
  def apply(p: Double): Power = Power(convertFrom(p))
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

  implicit class PowerConversions(d: Double) {
    def mW = Milliwatts(d)
    def W = Watts(d)
    def kW = Kilowatts(d)
    def MW = Megawatts(d)
    def GW = Gigawatts(d)
    def milliwatts = Milliwatts(d)
    def watts = Watts(d)
    def kilowatts = Kilowatts(d)
    def megawatts = Megawatts(d)
    def gigawatts = Gigawatts(d)
    def BTUph = BtusPerHour(d)
  }

  implicit class PowerStringConversions(s: String) {
    def toPower = Power(s)
  }

  implicit object PowerNumeric extends AbstractQuantityNumeric[Power](Watts)
}

