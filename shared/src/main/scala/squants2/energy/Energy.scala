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

final case class Energy[A: Numeric] private[squants2] (value: A, unit: EnergyUnit)
  extends Quantity[A, Energy] {

  // BEGIN CUSTOM OPS

  //  def per[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  //  def *[B](that: ParticleFlux[B])(implicit f: B => A): Irradiance[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): Force[A] = ???
  //  def /[B](that: Force[B])(implicit f: B => A): Length[A] = ???
  //  def /[B](that: Mass[B])(implicit f: B => A): SpecificEnergy[A] = ???
  //  def /[B](that: SpecificEnergy[B])(implicit f: B => A): Mass[A] = ???
  //  def /[B](that: Volume[B])(implicit f: B => A): EnergyDensity[A] = ???
  //  def /[B](that: EnergyDensity[B])(implicit f: B => A): Volume[A] = ???
  //  def /[B](that: ElectricCharge[B])(implicit f: B => A): ElectricPotential[A] = ???
  //  def /[B](that: ElectricPotential[B])(implicit f: B => A): ElectricCharge[A] = ???
  //  def /[B](that: Temperature[B])(implicit f: B => A): ThermalCapacity[A] = ???
  //  def /[B](that: ThermalCapacity[B])(implicit f: B => A): Temperature[A] = ???
  //  def /[B](that: ChemicalAmount[B])(implicit f: B => A): MolarEnergy[A] = ???
  //  def /[B](that: Angle[B])(implicit f: B => A): Torque[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Nothing$ = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): PowerRamp[A] = ???
  //  def asTorque[B]()(implicit f: B => A): Torque[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  // END CUSTOM OPS

  def toMilliElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](MilliElectronVolt)
  def toElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](ElectronVolt)
  def toKiloElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](KiloElectronVolt)
  def toMegaElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](MegaElectronVolt)
  def toPicojoules[B: Numeric](implicit f: A => B): B = toNum[B](Picojoules)
  def toGigaElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](GigaElectronVolt)
  def toNanojoules[B: Numeric](implicit f: A => B): B = toNum[B](Nanojoules)
  def toErgs[B: Numeric](implicit f: A => B): B = toNum[B](Ergs)
  def toTeraElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](TeraElectronVolt)
  def toMicrojoules[B: Numeric](implicit f: A => B): B = toNum[B](Microjoules)
  def toPetaElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](PetaElectronVolt)
  def toMillijoules[B: Numeric](implicit f: A => B): B = toNum[B](Millijoules)
  def toExaElectronVolt[B: Numeric](implicit f: A => B): B = toNum[B](ExaElectronVolt)
  def toJoules[B: Numeric](implicit f: A => B): B = toNum[B](Joules)
  def toMilliwattHours[B: Numeric](implicit f: A => B): B = toNum[B](MilliwattHours)
  def toKilojoules[B: Numeric](implicit f: A => B): B = toNum[B](Kilojoules)
  def toBritishThermalUnits[B: Numeric](implicit f: A => B): B = toNum[B](BritishThermalUnits)
  def toWattHours[B: Numeric](implicit f: A => B): B = toNum[B](WattHours)
  def toMegajoules[B: Numeric](implicit f: A => B): B = toNum[B](Megajoules)
  def toMBtus[B: Numeric](implicit f: A => B): B = toNum[B](MBtus)
  def toKilowattHours[B: Numeric](implicit f: A => B): B = toNum[B](KilowattHours)
  def toGigajoules[B: Numeric](implicit f: A => B): B = toNum[B](Gigajoules)
  def toMMBtus[B: Numeric](implicit f: A => B): B = toNum[B](MMBtus)
  def toMegawattHours[B: Numeric](implicit f: A => B): B = toNum[B](MegawattHours)
  def toTerajoules[B: Numeric](implicit f: A => B): B = toNum[B](Terajoules)
  def toGigawattHours[B: Numeric](implicit f: A => B): B = toNum[B](GigawattHours)
}

object Energy extends Dimension[Energy]("Energy") {

  override def primaryUnit: UnitOfMeasure[Energy] with PrimaryUnit[Energy] = WattHours
  override def siUnit: UnitOfMeasure[Energy] with SiUnit[Energy] = Joules
  override lazy val units: Set[UnitOfMeasure[Energy]] = 
    Set(MilliElectronVolt, ElectronVolt, KiloElectronVolt, MegaElectronVolt, Picojoules, GigaElectronVolt, Nanojoules, Ergs, TeraElectronVolt, Microjoules, PetaElectronVolt, Millijoules, ExaElectronVolt, Joules, MilliwattHours, Kilojoules, BritishThermalUnits, WattHours, Megajoules, MBtus, KilowattHours, Gigajoules, MMBtus, MegawattHours, Terajoules, GigawattHours)

  implicit class EnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def milliElectronVolt: Energy[A] = MilliElectronVolt(a)
    def electronVolt: Energy[A] = ElectronVolt(a)
    def kiloElectronVolt: Energy[A] = KiloElectronVolt(a)
    def megaElectronVolt: Energy[A] = MegaElectronVolt(a)
    def picojoules: Energy[A] = Picojoules(a)
    def gigaElectronVolt: Energy[A] = GigaElectronVolt(a)
    def nanojoules: Energy[A] = Nanojoules(a)
    def ergs: Energy[A] = Ergs(a)
    def teraElectronVolt: Energy[A] = TeraElectronVolt(a)
    def microjoules: Energy[A] = Microjoules(a)
    def petaElectronVolt: Energy[A] = PetaElectronVolt(a)
    def millijoules: Energy[A] = Millijoules(a)
    def exaElectronVolt: Energy[A] = ExaElectronVolt(a)
    def joules: Energy[A] = Joules(a)
    def milliwattHours: Energy[A] = MilliwattHours(a)
    def kilojoules: Energy[A] = Kilojoules(a)
    def britishThermalUnits: Energy[A] = BritishThermalUnits(a)
    def wattHours: Energy[A] = WattHours(a)
    def megajoules: Energy[A] = Megajoules(a)
    def mBtus: Energy[A] = MBtus(a)
    def kilowattHours: Energy[A] = KilowattHours(a)
    def gigajoules: Energy[A] = Gigajoules(a)
    def mMBtus: Energy[A] = MMBtus(a)
    def megawattHours: Energy[A] = MegawattHours(a)
    def terajoules: Energy[A] = Terajoules(a)
    def gigawattHours: Energy[A] = GigawattHours(a)
  }

  lazy val milliElectronVolt: Energy[Int] = MilliElectronVolt(1)
  lazy val electronVolt: Energy[Int] = ElectronVolt(1)
  lazy val kiloElectronVolt: Energy[Int] = KiloElectronVolt(1)
  lazy val megaElectronVolt: Energy[Int] = MegaElectronVolt(1)
  lazy val picojoules: Energy[Int] = Picojoules(1)
  lazy val gigaElectronVolt: Energy[Int] = GigaElectronVolt(1)
  lazy val nanojoules: Energy[Int] = Nanojoules(1)
  lazy val ergs: Energy[Int] = Ergs(1)
  lazy val teraElectronVolt: Energy[Int] = TeraElectronVolt(1)
  lazy val microjoules: Energy[Int] = Microjoules(1)
  lazy val petaElectronVolt: Energy[Int] = PetaElectronVolt(1)
  lazy val millijoules: Energy[Int] = Millijoules(1)
  lazy val exaElectronVolt: Energy[Int] = ExaElectronVolt(1)
  lazy val joules: Energy[Int] = Joules(1)
  lazy val milliwattHours: Energy[Int] = MilliwattHours(1)
  lazy val kilojoules: Energy[Int] = Kilojoules(1)
  lazy val britishThermalUnits: Energy[Int] = BritishThermalUnits(1)
  lazy val wattHours: Energy[Int] = WattHours(1)
  lazy val megajoules: Energy[Int] = Megajoules(1)
  lazy val mBtus: Energy[Int] = MBtus(1)
  lazy val kilowattHours: Energy[Int] = KilowattHours(1)
  lazy val gigajoules: Energy[Int] = Gigajoules(1)
  lazy val mMBtus: Energy[Int] = MMBtus(1)
  lazy val megawattHours: Energy[Int] = MegawattHours(1)
  lazy val terajoules: Energy[Int] = Terajoules(1)
  lazy val gigawattHours: Energy[Int] = GigawattHours(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Energy] = EnergyNumeric[A]()
  private case class EnergyNumeric[A: Numeric]() extends QuantityNumeric[A, Energy](this) {
    override def times(x: Quantity[A, Energy], y: Quantity[A, Energy]): Quantity[A, Energy] =
      WattHours(x.to(WattHours) * y.to(WattHours))
  }
}

abstract class EnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Energy] {
  override def dimension: Dimension[Energy] = Energy
  override def apply[A: Numeric](value: A): Energy[A] = Energy(value, this)
}

case object MilliElectronVolt extends EnergyUnit("meV", 4.4504904583333334E-26)
case object ElectronVolt extends EnergyUnit("eV", 4.4504904583333335E-23)
case object KiloElectronVolt extends EnergyUnit("keV", 4.450490458333334E-20)
case object MegaElectronVolt extends EnergyUnit("MeV", 4.4504904583333334E-17)
case object Picojoules extends EnergyUnit("pJ", 2.7777777777777775E-16) with SiUnit[Energy]
case object GigaElectronVolt extends EnergyUnit("GeV", 4.450490458333334E-14)
case object Nanojoules extends EnergyUnit("nJ", 2.777777777777778E-13) with SiUnit[Energy]
case object Ergs extends EnergyUnit("erg", 2.777777777777778E-11)
case object TeraElectronVolt extends EnergyUnit("TeV", 4.4504904583333334E-11)
case object Microjoules extends EnergyUnit("µJ", 2.7777777777777777E-10) with SiUnit[Energy]
case object PetaElectronVolt extends EnergyUnit("PeV", 4.4504904583333336E-8)
case object Millijoules extends EnergyUnit("mJ", 2.7777777777777776E-7) with SiUnit[Energy]
case object ExaElectronVolt extends EnergyUnit("EeV", 4.4504904583333335E-5)
case object Joules extends EnergyUnit("J", 2.777777777777778E-4) with SiUnit[Energy]
case object MilliwattHours extends EnergyUnit("mWh", MetricSystem.Milli)
case object Kilojoules extends EnergyUnit("kJ", 0.2777777777777778) with SiUnit[Energy]
case object BritishThermalUnits extends EnergyUnit("Btu", 0.2930710701722222)
case object WattHours extends EnergyUnit("Wh", 1) with PrimaryUnit[Energy]
case object Megajoules extends EnergyUnit("MJ", 277.77777777777777) with SiUnit[Energy]
case object MBtus extends EnergyUnit("MBtu", 293.0710701722222)
case object KilowattHours extends EnergyUnit("kWh", MetricSystem.Kilo)
case object Gigajoules extends EnergyUnit("GJ", 277777.77777777775) with SiUnit[Energy]
case object MMBtus extends EnergyUnit("MMBtu", 293071.0701722222)
case object MegawattHours extends EnergyUnit("MWh", MetricSystem.Mega)
case object Terajoules extends EnergyUnit("TJ", 2.777777777777778E8) with SiUnit[Energy]
case object GigawattHours extends EnergyUnit("GWh", MetricSystem.Giga)
