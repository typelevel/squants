/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._
import squants2.motion._
import squants2.time._

final case class Mass[A: Numeric] private[squants2] (value: A, unit: MassUnit)
  extends Quantity[A, Mass] with TimeIntegral[A, MassFlow] {

  override protected[squants2] def timeDerived: MassFlow[A] = KilogramsPerSecond(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def *[B](that: SpecificEnergy[B])(implicit f: B => A): Energy[A] = ???
  //  def *[B](that: Velocity[B])(implicit f: B => A): Momentum[A] = ???
  //  def *[B](that: Acceleration[B])(implicit f: B => A): Force[A] = ???
  //  def /[B](that: Density[B])(implicit f: B => A): Volume[A] = ???
  //  def /[B](that: Volume[B])(implicit f: B => A): Density[A] = ???
  //  def /[B](that: AreaDensity[B])(implicit f: B => A): Area[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): AreaDensity[A] = ???
  //  def onRadius[B](radius: Length[B])(implicit f: B => A): MomentOfInertia[A] = ???
  // END CUSTOM OPS

  def toMilliElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](MilliElectronVoltMass)
  def toElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](ElectronVoltMass)
  def toKiloElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](KiloElectronVoltMass)
  def toMegaElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](MegaElectronVoltMass)
  def toGigaElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](GigaElectronVoltMass)
  def toDalton[B: Numeric](implicit f: A => B): B = toNum[B](Dalton)
  def toTeraElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](TeraElectronVoltMass)
  def toPetaElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](PetaElectronVoltMass)
  def toExaElectronVoltMass[B: Numeric](implicit f: A => B): B = toNum[B](ExaElectronVoltMass)
  def toNanograms[B: Numeric](implicit f: A => B): B = toNum[B](Nanograms)
  def toMicrograms[B: Numeric](implicit f: A => B): B = toNum[B](Micrograms)
  def toMilligrams[B: Numeric](implicit f: A => B): B = toNum[B](Milligrams)
  def toTroyGrains[B: Numeric](implicit f: A => B): B = toNum[B](TroyGrains)
  def toCarats[B: Numeric](implicit f: A => B): B = toNum[B](Carats)
  def toGrams[B: Numeric](implicit f: A => B): B = toNum[B](Grams)
  def toPennyweights[B: Numeric](implicit f: A => B): B = toNum[B](Pennyweights)
  def toTolas[B: Numeric](implicit f: A => B): B = toNum[B](Tolas)
  def toOunces[B: Numeric](implicit f: A => B): B = toNum[B](Ounces)
  def toTroyOunces[B: Numeric](implicit f: A => B): B = toNum[B](TroyOunces)
  def toTroyPounds[B: Numeric](implicit f: A => B): B = toNum[B](TroyPounds)
  def toPounds[B: Numeric](implicit f: A => B): B = toNum[B](Pounds)
  def toKilograms[B: Numeric](implicit f: A => B): B = toNum[B](Kilograms)
  def toStone[B: Numeric](implicit f: A => B): B = toNum[B](Stone)
  def toMegapounds[B: Numeric](implicit f: A => B): B = toNum[B](Megapounds)
  def toKilopounds[B: Numeric](implicit f: A => B): B = toNum[B](Kilopounds)
  def toTonnes[B: Numeric](implicit f: A => B): B = toNum[B](Tonnes)
  def toSolarMasses[B: Numeric](implicit f: A => B): B = toNum[B](SolarMasses)
}

object Mass extends BaseDimension[Mass]("Mass", "M") {

  override def primaryUnit: UnitOfMeasure[Mass] with PrimaryUnit[Mass] = Grams
  override def siUnit: UnitOfMeasure[Mass] with SiBaseUnit[Mass] = Kilograms
  override lazy val units: Set[UnitOfMeasure[Mass]] = 
    Set(MilliElectronVoltMass, ElectronVoltMass, KiloElectronVoltMass, MegaElectronVoltMass, GigaElectronVoltMass, Dalton, TeraElectronVoltMass, PetaElectronVoltMass, ExaElectronVoltMass, Nanograms, Micrograms, Milligrams, TroyGrains, Carats, Grams, Pennyweights, Tolas, Ounces, TroyOunces, TroyPounds, Pounds, Kilograms, Stone, Megapounds, Kilopounds, Tonnes, SolarMasses)

  implicit class MassCons[A](a: A)(implicit num: Numeric[A]) {
    def milliElectronVoltMass: Mass[A] = MilliElectronVoltMass(a)
    def electronVoltMass: Mass[A] = ElectronVoltMass(a)
    def kiloElectronVoltMass: Mass[A] = KiloElectronVoltMass(a)
    def megaElectronVoltMass: Mass[A] = MegaElectronVoltMass(a)
    def gigaElectronVoltMass: Mass[A] = GigaElectronVoltMass(a)
    def dalton: Mass[A] = Dalton(a)
    def teraElectronVoltMass: Mass[A] = TeraElectronVoltMass(a)
    def petaElectronVoltMass: Mass[A] = PetaElectronVoltMass(a)
    def exaElectronVoltMass: Mass[A] = ExaElectronVoltMass(a)
    def nanograms: Mass[A] = Nanograms(a)
    def micrograms: Mass[A] = Micrograms(a)
    def milligrams: Mass[A] = Milligrams(a)
    def troyGrains: Mass[A] = TroyGrains(a)
    def carats: Mass[A] = Carats(a)
    def grams: Mass[A] = Grams(a)
    def pennyweights: Mass[A] = Pennyweights(a)
    def tolas: Mass[A] = Tolas(a)
    def ounces: Mass[A] = Ounces(a)
    def troyOunces: Mass[A] = TroyOunces(a)
    def troyPounds: Mass[A] = TroyPounds(a)
    def pounds: Mass[A] = Pounds(a)
    def kilograms: Mass[A] = Kilograms(a)
    def stone: Mass[A] = Stone(a)
    def megapounds: Mass[A] = Megapounds(a)
    def kilopounds: Mass[A] = Kilopounds(a)
    def tonnes: Mass[A] = Tonnes(a)
    def solarMasses: Mass[A] = SolarMasses(a)
  }

  lazy val milliElectronVoltMass: Mass[Int] = MilliElectronVoltMass(1)
  lazy val electronVoltMass: Mass[Int] = ElectronVoltMass(1)
  lazy val kiloElectronVoltMass: Mass[Int] = KiloElectronVoltMass(1)
  lazy val megaElectronVoltMass: Mass[Int] = MegaElectronVoltMass(1)
  lazy val gigaElectronVoltMass: Mass[Int] = GigaElectronVoltMass(1)
  lazy val dalton: Mass[Int] = Dalton(1)
  lazy val teraElectronVoltMass: Mass[Int] = TeraElectronVoltMass(1)
  lazy val petaElectronVoltMass: Mass[Int] = PetaElectronVoltMass(1)
  lazy val exaElectronVoltMass: Mass[Int] = ExaElectronVoltMass(1)
  lazy val nanogram: Mass[Int] = Nanograms(1)
  lazy val microgram: Mass[Int] = Micrograms(1)
  lazy val milligram: Mass[Int] = Milligrams(1)
  lazy val troyGrain: Mass[Int] = TroyGrains(1)
  lazy val carat: Mass[Int] = Carats(1)
  lazy val gram: Mass[Int] = Grams(1)
  lazy val pennyweight: Mass[Int] = Pennyweights(1)
  lazy val tola: Mass[Int] = Tolas(1)
  lazy val ounce: Mass[Int] = Ounces(1)
  lazy val troyOunce: Mass[Int] = TroyOunces(1)
  lazy val troyPound: Mass[Int] = TroyPounds(1)
  lazy val pound: Mass[Int] = Pounds(1)
  lazy val kilogram: Mass[Int] = Kilograms(1)
  lazy val stone: Mass[Int] = Stone(1)
  lazy val megapound: Mass[Int] = Megapounds(1)
  lazy val kilopound: Mass[Int] = Kilopounds(1)
  lazy val tonne: Mass[Int] = Tonnes(1)
  lazy val solarMasses: Mass[Int] = SolarMasses(1)

}

abstract class MassUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Mass] {
  override def dimension: Dimension[Mass] = Mass
  override def apply[A: Numeric](value: A): Mass[A] = Mass(value, this)
}

case object MilliElectronVoltMass extends MassUnit("meV/c²", 1.782662E-39)
case object ElectronVoltMass extends MassUnit("eV/c²", 1.782662E-36)
case object KiloElectronVoltMass extends MassUnit("keV/c²", 1.782662E-33)
case object MegaElectronVoltMass extends MassUnit("MeV/c²", 1.782662E-30)
case object GigaElectronVoltMass extends MassUnit("GeV/c²", 1.782662E-27)
case object Dalton extends MassUnit("Da", 1.6605390666E-24)
case object TeraElectronVoltMass extends MassUnit("TeV/c²", 1.782662E-24)
case object PetaElectronVoltMass extends MassUnit("PeV/c²", 1.782662E-21)
case object ExaElectronVoltMass extends MassUnit("EeV/c²", 1.782662E-18)
case object Nanograms extends MassUnit("ng", MetricSystem.Nano) with SiUnit[Mass]
case object Micrograms extends MassUnit("mcg", MetricSystem.Micro) with SiUnit[Mass]
case object Milligrams extends MassUnit("mg", MetricSystem.Milli) with SiUnit[Mass]
case object TroyGrains extends MassUnit("gr", 0.06479891)
case object Carats extends MassUnit("ct", 0.2)
case object Grams extends MassUnit("g", 1) with PrimaryUnit[Mass] with SiUnit[Mass]
case object Pennyweights extends MassUnit("dwt", 1.5551738400000001)
case object Tolas extends MassUnit("tola", 11.6638038)
case object Ounces extends MassUnit("oz", 28.349523125)
case object TroyOunces extends MassUnit("oz t", 31.1034768)
case object TroyPounds extends MassUnit("lb t", 373.2417216)
case object Pounds extends MassUnit("lb", 453.59237)
case object Kilograms extends MassUnit("kg", MetricSystem.Kilo) with SiBaseUnit[Mass]
case object Stone extends MassUnit("st", 6350.293180000001)
case object Megapounds extends MassUnit("Mlb", 453592.37)
case object Kilopounds extends MassUnit("klb", 453592.37)
case object Tonnes extends MassUnit("t", MetricSystem.Mega)
case object SolarMasses extends MassUnit("M☉", 1.98855E33)
