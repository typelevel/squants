/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Mass[A: Numeric] private [squants2]  (value: A, unit: MassUnit)
  extends Quantity[A, Mass.type] {
  override type Q[B] = Mass[B]

  // BEGIN CUSTOM OPS
  //  def /[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: Frequency[B])(implicit f: B => A): Quantity[A] = ???
  //  def *[B](that: SpecificEnergy[B])(implicit f: B => A): Energy[A] = ???
  //  def *[B](that: Velocity[B])(implicit f: B => A): Momentum[A] = ???
  //  def *[B](that: Acceleration[B])(implicit f: B => A): Force[A] = ???
  //  def /[B](that: Density[B])(implicit f: B => A): Volume[A] = ???
  //  def /[B](that: Volume[B])(implicit f: B => A): Density[A] = ???
  //  def /[B](that: AreaDensity[B])(implicit f: B => A): Area[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): AreaDensity[A] = ???
  // END CUSTOM OPS

  def toMilliElectronVoltMass: A = to(MilliElectronVoltMass)
  def toElectronVoltMass: A = to(ElectronVoltMass)
  def toKiloElectronVoltMass: A = to(KiloElectronVoltMass)
  def toMegaElectronVoltMass: A = to(MegaElectronVoltMass)
  def toGigaElectronVoltMass: A = to(GigaElectronVoltMass)
  def toDalton: A = to(Dalton)
  def toTeraElectronVoltMass: A = to(TeraElectronVoltMass)
  def toPetaElectronVoltMass: A = to(PetaElectronVoltMass)
  def toExaElectronVoltMass: A = to(ExaElectronVoltMass)
  def toNanograms: A = to(Nanograms)
  def toMicrograms: A = to(Micrograms)
  def toMilligrams: A = to(Milligrams)
  def toTroyGrains: A = to(TroyGrains)
  def toCarats: A = to(Carats)
  def toGrams: A = to(Grams)
  def toPennyweights: A = to(Pennyweights)
  def toTolas: A = to(Tolas)
  def toOunces: A = to(Ounces)
  def toTroyOunces: A = to(TroyOunces)
  def toTroyPounds: A = to(TroyPounds)
  def toPounds: A = to(Pounds)
  def toKilograms: A = to(Kilograms)
  def toStone: A = to(Stone)
  def toMegapounds: A = to(Megapounds)
  def toKilopounds: A = to(Kilopounds)
  def toTonnes: A = to(Tonnes)
  def toSolarMasses: A = to(SolarMasses)
}

object Mass extends BaseDimension("Mass", "M") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Grams
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Kilograms
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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
  lazy val nanograms: Mass[Int] = Nanograms(1)
  lazy val micrograms: Mass[Int] = Micrograms(1)
  lazy val milligrams: Mass[Int] = Milligrams(1)
  lazy val troyGrains: Mass[Int] = TroyGrains(1)
  lazy val carats: Mass[Int] = Carats(1)
  lazy val grams: Mass[Int] = Grams(1)
  lazy val pennyweights: Mass[Int] = Pennyweights(1)
  lazy val tolas: Mass[Int] = Tolas(1)
  lazy val ounces: Mass[Int] = Ounces(1)
  lazy val troyOunces: Mass[Int] = TroyOunces(1)
  lazy val troyPounds: Mass[Int] = TroyPounds(1)
  lazy val pounds: Mass[Int] = Pounds(1)
  lazy val kilograms: Mass[Int] = Kilograms(1)
  lazy val stone: Mass[Int] = Stone(1)
  lazy val megapounds: Mass[Int] = Megapounds(1)
  lazy val kilopounds: Mass[Int] = Kilopounds(1)
  lazy val tonnes: Mass[Int] = Tonnes(1)
  lazy val solarMasses: Mass[Int] = SolarMasses(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = MassNumeric[A]()
  private case class MassNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Mass.type], y: Quantity[A, Mass.type]): Quantity[A, Mass.this.type] =
      Grams(x.to(Grams) * y.to(Grams))
  }
}

abstract class MassUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Mass.type] {
  override def dimension: Mass.type = Mass
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
case object Nanograms extends MassUnit("ng", MetricSystem.Nano) with SiUnit
case object Micrograms extends MassUnit("mcg", MetricSystem.Micro) with SiUnit
case object Milligrams extends MassUnit("mg", MetricSystem.Milli) with SiUnit
case object TroyGrains extends MassUnit("gr", 0.06479891)
case object Carats extends MassUnit("ct", 0.2)
case object Grams extends MassUnit("g", 1) with PrimaryUnit with SiUnit
case object Pennyweights extends MassUnit("dwt", 1.5551738400000001)
case object Tolas extends MassUnit("tola", 11.6638038)
case object Ounces extends MassUnit("oz", 28.349523125)
case object TroyOunces extends MassUnit("oz t", 31.1034768)
case object TroyPounds extends MassUnit("lb t", 373.2417216)
case object Pounds extends MassUnit("lb", 453.59237)
case object Kilograms extends MassUnit("kg", MetricSystem.Kilo) with SiBaseUnit
case object Stone extends MassUnit("st", 6350.293180000001)
case object Megapounds extends MassUnit("Mlb", 453592.37)
case object Kilopounds extends MassUnit("klb", 453592.37)
case object Tonnes extends MassUnit("t", MetricSystem.Mega)
case object SolarMasses extends MassUnit("M☉", 1.98855E33)
