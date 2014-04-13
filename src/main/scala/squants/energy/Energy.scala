/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.time.{ Time, Seconds, TimeIntegral, Hours }
import squants.electro.{ Coulombs, ElectricPotential, Volts, ElectricCharge }
import squants.mass.{ ChemicalAmount, Kilograms }
import squants.motion.Newtons
import squants.thermal.{ Kelvin, ThermalCapacity, JoulesPerKelvin }
import squants.space.CubicMeters

/**
 * Represents a quantity of energy
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.WattHours]]
 */
final class Energy private (val value: Double)
    extends Quantity[Energy] with PhysicalQuantity
    with TimeIntegral[Power] {

  def valueUnit = WattHours

  def /(that: Time): Power = Power(value / that.toHours)
  def /(that: Power): Time = Hours(value / that.value)

  def /(that: Length): Force = Newtons(toJoules / that.toMeters)
  def /(that: Force): Length = Meters(toJoules / that.toNewtons)
  def /(that: Mass): SpecificEnergy = Grays(toJoules / that.toKilograms)
  def /(that: SpecificEnergy): Mass = Kilograms(toJoules / that.toGrays)
  def /(that: Volume): EnergyDensity = JoulesPerCubicMeter(toJoules / that.toCubicMeters)
  def /(that: EnergyDensity): Volume = CubicMeters(toJoules / that.toJoulesPerCubicMeter)
  def /(that: ElectricCharge): ElectricPotential = Volts(this.toJoules / that.toCoulombs)
  def /(that: ElectricPotential): ElectricCharge = Coulombs(this.toJoules / that.toVolts)
  def /(that: Temperature): ThermalCapacity = JoulesPerKelvin(toJoules / that.toKelvinDegrees)
  def /(that: ThermalCapacity) = Kelvin(toJoules / that.toJoulesPerKelvin)

  def /(that: ChemicalAmount) = ??? // return MolarEnergy
  def /(that: Angle) = ??? // return Torque (dimensionally equivalent to energy as Angles are dimensionless)

  def toWattHours = to(WattHours)
  def toJoules = to(Joules)
  def toKilowattHours = to(KilowattHours)
  def toMegawattHours = to(MegawattHours)
  def toGigawattHours = to(GigawattHours)
  def toBtus = to(BritishThermalUnits)
  def toMBtus = to(MBtus)
  def toMMBtus = to(MMBtus)

  override def toString = toString(this match {
    case GigawattHours(gwh) if gwh >= 1.0 ⇒ GigawattHours
    case MegawattHours(mwh) if mwh >= 1.0 ⇒ MegawattHours
    case KilowattHours(kwh) if kwh >= 1.0 ⇒ KilowattHours
    case WattHours(wh) if wh >= 1.0       ⇒ WattHours
    case _                                ⇒ Joules
  })
}

/**
 * Companion object for [[squants.energy.Energy]]
 */
object Energy {
  private[energy] def apply[A](n: A)(implicit num: Numeric[A]) = new Energy(num.toDouble(n))
  def apply(load: Power, time: Time): Energy = load * time
  def apply(s: String): Either[String, Energy] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+) *(J|Wh|kWh|MWh|GWh|Btu|MBtu|MMBtu)".r
    s match {
      case regex(value, Joules.symbol)              ⇒ Right(Joules(value.toDouble))
      case regex(value, WattHours.symbol)           ⇒ Right(WattHours(value.toDouble))
      case regex(value, KilowattHours.symbol)       ⇒ Right(KilowattHours(value.toDouble))
      case regex(value, MegawattHours.symbol)       ⇒ Right(MegawattHours(value.toDouble))
      case regex(value, GigawattHours.symbol)       ⇒ Right(GigawattHours(value.toDouble))
      case regex(value, BritishThermalUnits.symbol) ⇒ Right(BritishThermalUnits(value.toDouble))
      case regex(value, MBtus.symbol)               ⇒ Right(MBtus(value.toDouble))
      case regex(value, MMBtus.symbol)              ⇒ Right(MMBtus(value.toDouble))
      case _                                        ⇒ Left(s"Unable to parse $s as Energy")
    }
  }

  def unapply(energy: Energy) = Some(energy.value)
}

/**
 * Base trait for units of [[squants.energy.Energy]]
 */
trait EnergyUnit extends UnitOfMeasure[Energy] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Energy(convertFrom(n))
  def unapply(energy: Energy) = Some(convertTo(energy.value))
}

object WattHours extends EnergyUnit with ValueUnit {
  val symbol = "Wh"
}

object Joules extends EnergyUnit {
  val multiplier = 1.0 / Time.SecondsPerHour
  val symbol = "J"
}

object KilowattHours extends EnergyUnit {
  val multiplier = MetricSystem.Kilo
  val symbol = "kWh"
}

object MegawattHours extends EnergyUnit {
  val multiplier = MetricSystem.Mega
  val symbol = "MWh"
}

object GigawattHours extends EnergyUnit {
  val multiplier = MetricSystem.Giga
  val symbol = "GWh"
}

object BritishThermalUnits extends EnergyUnit {
  val multiplier = EnergyConversions.btuMultiplier
  val symbol = "Btu"
}

object MBtus extends EnergyUnit {
  val multiplier = EnergyConversions.btuMultiplier * MetricSystem.Kilo
  val symbol = "MBtu"
}

object MMBtus extends EnergyUnit {
  val multiplier = EnergyConversions.btuMultiplier * MetricSystem.Mega
  val symbol = "MMBtu"
}

object EnergyConversions {
  lazy val wattHour = WattHours(1)
  lazy val Wh = wattHour
  lazy val kilowattHour = KilowattHours(1)
  lazy val kWh = kilowattHour
  lazy val megawattHour = MegawattHours(1)
  lazy val MWh = megawattHour
  lazy val gigawattHour = GigawattHours(1)
  lazy val GWh = gigawattHour
  lazy val btu = BritishThermalUnits(1)
  lazy val joule = Watts(1) * Seconds(1)
  lazy val btuMultiplier = joule.value * 1055.05585262

  implicit class EnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def J = Joules(n)
    def joules = Joules(n)
    def Wh = WattHours(n)
    def kWh = KilowattHours(n)
    def MWh = MegawattHours(n)
    def GWh = GigawattHours(n)
    def Btu = BritishThermalUnits(n)
    def MBtu = MBtus(n)
    def MMBtu = MMBtus(n)
    def wattHours = WattHours(n)
    def kilowattHours = KilowattHours(n)
    def megawattHours = MegawattHours(n)
    def gigawattHours = GigawattHours(n)
  }

  implicit class EnergyStringConversions(s: String) {
    def toEnergy = Energy(s)
  }

  implicit object EnergyNumeric extends AbstractQuantityNumeric[Energy](WattHours)
}