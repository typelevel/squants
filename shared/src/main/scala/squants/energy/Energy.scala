/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.electro.{Coulombs, ElectricCharge, ElectricPotential, Volts}
import squants.mass.{ChemicalAmount, Kilograms}
import squants.motion.{NewtonMeters, Newtons, Torque}
import squants.space.CubicMeters
import squants.thermal.{JoulesPerKelvin, Kelvin, ThermalCapacity}
import squants.time.{Time, _}
import squants.radio.{ Irradiance, ParticleFlux, WattsPerSquareMeter }

/**
 * Represents a quantity of energy
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.WattHours]]
 */
final class Energy private (val value: Double, val unit: EnergyUnit)
    extends Quantity[Energy]
    with TimeIntegral[Power]
    with SecondTimeIntegral[PowerRamp] {

  def dimension = Energy

  protected def timeDerived = Watts(toWattHours)
  protected def time = Hours(1)

  def *(that: ParticleFlux): Irradiance = WattsPerSquareMeter(
    Hours(1).toSeconds * this.toWattHours *
      that.toBecquerelsPerSquareMeterSecond)
  def /(that: Length): Force = Newtons(this.toJoules / that.toMeters)
  def /(that: Force): Length = Meters(this.toJoules / that.toNewtons)
  def /(that: Mass): SpecificEnergy = Grays(this.toJoules / that.toKilograms)
  def /(that: SpecificEnergy): Mass = Kilograms(this.toJoules / that.toGrays)
  def /(that: Volume): EnergyDensity = JoulesPerCubicMeter(this.toJoules / that.toCubicMeters)
  def /(that: EnergyDensity): Volume = CubicMeters(this.toJoules / that.toJoulesPerCubicMeter)
  def /(that: ElectricCharge): ElectricPotential = Volts(this.toJoules / that.toCoulombs)
  def /(that: ElectricPotential): ElectricCharge = Coulombs(this.toJoules / that.toVolts)
  def /(that: Temperature): ThermalCapacity = JoulesPerKelvin(this.toJoules / that.toKelvinDegrees)
  def /(that: ThermalCapacity) = Kelvin(this.toJoules / that.toJoulesPerKelvin)

  def /(that: ChemicalAmount): MolarEnergy = JoulesPerMole(this.toJoules / that.toMoles)
  def /(that: Angle): Torque = NewtonMeters(toJoules / that.toRadians)
  def /(that: Area) = ??? // Insolation, Energy Area Density

  def /(that: TimeSquared): PowerRamp = this / that.time1 / that.time2
  def /(that: PowerRamp): TimeSquared = (this / that.timeIntegrated) * time

  def toWattHours = to(WattHours)
  def toMilliwattHours = to(MilliwattHours)
  def toKilowattHours = to(KilowattHours)
  def toMegawattHours = to(MegawattHours)
  def toGigawattHours = to(GigawattHours)

  def toJoules = to(Joules)
  def toPicojoules = to(Picojoules)
  def toNanojoules = to(Nanojoules)
  def toMicrojoules = to(Microjoules)
  def toMillijoules = to(Millijoules)
  def toKilojoules = to(Kilojoules)
  def toMegajoules = to(Megajoules)
  def toGigajoules = to(Gigajoules)
  def toTerajoules = to(Terajoules)

  def toeV = to(ElectronVolt)
  def tomeV = to(MilliElectronVolt)
  def tokeV = to(KiloElectronVolt)
  def toMeV = to(MegaElectronVolt)
  def toGeV = to(GigaElectronVolt)
  def toTeV = to(TeraElectronVolt)
  def toPeV = to(PetaElectronVolt)
  def toEeV = to(ExaElectronVolt)

  def toBtus = to(BritishThermalUnits)
  def toMBtus = to(MBtus)
  def toMMBtus = to(MMBtus)
  def toErgs = to(Ergs)

  /**
    * Energy and torque have the same unit, so convert appropriately
    * @return numerically equivalent value in newton-meters
    */
  def asTorque = NewtonMeters(toJoules)
}

/**
 * Companion object for [[squants.energy.Energy]]
 */
object Energy extends Dimension[Energy] {
  private[energy] def apply[A](n: A, unit: EnergyUnit)(implicit num: Numeric[A]) = new Energy(num.toDouble(n), unit)
  def apply(load: Power, time: Time): Energy = load * time
  def apply(value: Any) = parse(value)

  def name = "Energy"
  def primaryUnit = WattHours
  def siUnit = Joules
  def units = Set(WattHours, MilliwattHours, KilowattHours, MegawattHours, GigawattHours,
    Joules, Picojoules, Nanojoules, Microjoules, Millijoules,
    Kilojoules, Megajoules, Gigajoules, Terajoules,
    BritishThermalUnits, MBtus, MMBtus, Ergs,
    ElectronVolt, MilliElectronVolt, KiloElectronVolt, MegaElectronVolt,
    GigaElectronVolt, TeraElectronVolt, PetaElectronVolt, ExaElectronVolt)
}

/**
 * Base trait for units of [[squants.energy.Energy]]
 */
trait EnergyUnit extends UnitOfMeasure[Energy] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Energy(n, this)
}

object WattHours extends EnergyUnit with PrimaryUnit {
  val symbol = "Wh"
}

object MilliwattHours extends EnergyUnit {
  val conversionFactor = Watts.conversionFactor * MetricSystem.Milli
  val symbol = "mWh"
}

object KilowattHours extends EnergyUnit {
  val conversionFactor = Watts.conversionFactor * MetricSystem.Kilo
  val symbol = "kWh"
}

object MegawattHours extends EnergyUnit {
  val conversionFactor = Watts.conversionFactor * MetricSystem.Mega
  val symbol = "MWh"
}

object GigawattHours extends EnergyUnit {
  val conversionFactor = Watts.conversionFactor * MetricSystem.Giga
  val symbol = "GWh"
}

object Joules extends EnergyUnit with SiUnit {
  val conversionFactor = 1.0 / Time.SecondsPerHour
  val symbol = "J"
}

object Picojoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Pico
  val symbol = "pJ"
}

object Nanojoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Nano
  val symbol = "nJ"
}

object Microjoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Micro
  val symbol = "µJ"
}

object Millijoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Milli
  val symbol = "mJ"
}

object Kilojoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Kilo
  val symbol = "kJ"
}

object Megajoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Mega
  val symbol = "MJ"
}

object Gigajoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Giga
  val symbol = "GJ"
}

object Terajoules extends EnergyUnit with SiUnit {
  val conversionFactor = Joules.conversionFactor * MetricSystem.Tera
  val symbol = "TJ"
}

object BritishThermalUnits extends EnergyUnit {
  val conversionFactor = EnergyConversions.btuMultiplier
  val symbol = "Btu"
}

object MBtus extends EnergyUnit {
  val conversionFactor = EnergyConversions.btuMultiplier * MetricSystem.Kilo
  val symbol = "MBtu"
}

object MMBtus extends EnergyUnit {
  val conversionFactor = EnergyConversions.btuMultiplier * MetricSystem.Mega
  val symbol = "MMBtu"
}

object Ergs extends EnergyUnit {
  val conversionFactor = 100.0 * Nanojoules.conversionFactor
  val symbol = "erg"
}

object ElectronVolt extends EnergyUnit {
  val conversionFactor = Joules.conversionFactor * 1.602176565e-19
  val symbol = "eV"
}

object MilliElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Milli
  val symbol = "meV"
}

object KiloElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Kilo
  val symbol = "keV"
}

object MegaElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Mega
  val symbol = "MeV"
}

object GigaElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Giga
  val symbol = "GeV"
}

object TeraElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Tera
  val symbol = "TeV"
}

object PetaElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Peta
  val symbol = "PeV"
}

object ExaElectronVolt extends EnergyUnit {
  val conversionFactor = ElectronVolt.conversionFactor * MetricSystem.Exa
  val symbol = "EeV"
}

object EnergyConversions {
  lazy val wattHour = WattHours(1)
  lazy val Wh = wattHour
  lazy val milliwattHour = MilliwattHours(1)
  lazy val mWh = milliwattHour
  lazy val kilowattHour = KilowattHours(1)
  lazy val kWh = kilowattHour
  lazy val megawattHour = MegawattHours(1)
  lazy val MWh = megawattHour
  lazy val gigawattHour = GigawattHours(1)
  lazy val GWh = gigawattHour

  lazy val joule = Joules(1)
  lazy val picojoule = Picojoules(1)
  lazy val nanojoule = Nanojoules(1)
  lazy val microjoule = Microjoules(1)
  lazy val millijoule = Millijoules(1)
  lazy val kilojoule = Kilojoules(1)
  lazy val megajoule = Megajoules(1)
  lazy val gigajoule = Gigajoules(1)
  lazy val terajoule = Terajoules(1)

  lazy val btu = BritishThermalUnits(1)
  lazy val btuMultiplier = 2.930710701722222e-1

  lazy val eV = ElectronVolt(1)
  lazy val meV = MilliElectronVolt(1)
  lazy val keV = KiloElectronVolt(1)
  lazy val MeV = MegaElectronVolt(1)
  lazy val GeV = GigaElectronVolt(1)
  lazy val TeV = TeraElectronVolt(1)
  lazy val PeV = PetaElectronVolt(1)
  lazy val EeV = ExaElectronVolt(1)

  implicit class EnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def J = Joules(n)
    def joules = Joules(n)
    def pJ = Picojoules(n)
    def picojoules = Picojoules(n)
    def nJ = Nanojoules(n)
    def nanojoules = Nanojoules(n)
    def µJ = Microjoules(n)
    def microjoules = Microjoules(n)
    def mJ = Millijoules(n)
    def milljoules = Millijoules(n)
    def kJ = Kilojoules(n)
    def kilojoules = Kilojoules(n)
    def MJ = Megajoules(n)
    def megajoules = Megajoules(n)
    def GJ = Gigajoules(n)
    def gigajoules = Gigajoules(n)
    def TJ = Terajoules(n)
    def terajoules = Terajoules(n)

    def Wh = WattHours(n)
    def mWh = MilliwattHours(n)
    def kWh = KilowattHours(n)
    def MWh = MegawattHours(n)
    def GWh = GigawattHours(n)
    def Btu = BritishThermalUnits(n)
    def MBtu = MBtus(n)
    def MMBtu = MMBtus(n)
    def ergs = Ergs(n)
    def wattHours = WattHours(n)
    def kilowattHours = KilowattHours(n)
    def megawattHours = MegawattHours(n)
    def gigawattHours = GigawattHours(n)

    def eV = ElectronVolt(n)
    def meV = MilliElectronVolt(n)
    def keV = KiloElectronVolt(n)
    def MeV = MegaElectronVolt(n)
    def GeV = GigaElectronVolt(n)
    def TeV = TeraElectronVolt(n)
    def PeV = PetaElectronVolt(n)
    def EeV = ExaElectronVolt(n)
  }

  implicit class EnergyStringConversions(s: String) {
    def toEnergy = Energy(s)
  }

  implicit object EnergyNumeric extends AbstractQuantityNumeric[Energy](Energy.primaryUnit)
}
