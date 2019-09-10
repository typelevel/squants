/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.energy.{ Energy, Joules, SpecificEnergy }
import squants.motion.{ Force, MassFlow, Momentum, _ }
import squants.space.{ CubicMeters, SquareMeters }
import squants.time.TimeIntegral
import squants.{ Acceleration, Energy ⇒ _, Velocity, _ }

/**
 * Represents a quantity of Mass
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in the [[squants.mass.Grams]]
 */
final class Mass private (val value: Double, val unit: MassUnit)
    extends Quantity[Mass]
    with TimeIntegral[MassFlow] {

  def dimension = Mass

  protected def timeDerived = KilogramsPerSecond(toKilograms)
  protected def time = Seconds(1)

  def *(that: SpecificEnergy): Energy = Joules(this.toKilograms * that.toGrays)
  def *(that: Velocity): Momentum = Momentum(this, that)
  def *(that: Acceleration): Force = Newtons(this.toKilograms * that.toMetersPerSecondSquared)
  def /(that: Density): Volume = CubicMeters(this.toKilograms / that.toKilogramsPerCubicMeter)
  def /(that: Volume): Density = Density(this, that)
  def /(that: AreaDensity): Area = SquareMeters(this.toKilograms / that.toKilogramsPerSquareMeter)
  def /(that: Area): AreaDensity = KilogramsPerSquareMeter(this.toKilograms / that.toSquareMeters)

  /**
    * Moment of inertia of a point mass with with this mass and the given
    * radius from the center of rotation
    * @param radius length to center of rotation
    * @return moment of inertia of a point mass with given mass and radius
    */
  def onRadius(radius: Length): MomentOfInertia = KilogramsMetersSquared(toKilograms * radius.squared.toSquareMeters)

  def toNanograms = to(Nanograms)
  def toMicrograms = to(Micrograms)
  def toMilligrams = to(Milligrams)
  def toGrams = to(Grams)
  def toKilograms = to(Kilograms)
  def toTonnes = to(Tonnes)
  def toOunces = to(Ounces)
  def toPounds = to(Pounds)
  def toKilopounds = to(Kilopounds)
  def toMegapounds = to(Megapounds)
  def toStone = to(Stone)
  def toTroyGrains = to(TroyGrains)
  def toPennyweights = to(Pennyweights)
  def toTroyOunces = to(TroyOunces)
  def toTroyPounds = to(TroyPounds)
  def toTolas = to(Tolas)
  def toCarats = to(Carats)
  def toSolarMasses = to(SolarMasses)

  def toeV = to(ElectronVoltMass)
  def tomeV = to(MilliElectronVoltMass)
  def tokeV = to(KiloElectronVoltMass)
  def toMeV = to(MegaElectronVoltMass)
  def toGeV = to(GigaElectronVoltMass)
  def toTeV = to(TeraElectronVoltMass)
  def toPeV = to(PetaElectronVoltMass)
  def toEeV = to(ExaElectronVoltMass)
}

/**
 * Factory singleton for [[squants.mass.Mass]] values
 */
object Mass extends Dimension[Mass] with BaseDimension {
  private[mass] def apply[A](n: A, unit: MassUnit)(implicit num: Numeric[A]) = new Mass(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Mass"
  def primaryUnit = Grams
  def siUnit = Kilograms
  def units = Set(Nanograms, Micrograms, Milligrams, Grams, Kilograms, Tonnes, Ounces, Pounds, Kilopounds, Megapounds,
    Stone, TroyGrains, Pennyweights, TroyOunces, TroyPounds, Tolas, Carats, SolarMasses,
    ElectronVoltMass, MilliElectronVoltMass, KiloElectronVoltMass, MegaElectronVoltMass,
    GigaElectronVoltMass, TeraElectronVoltMass, PetaElectronVoltMass, ExaElectronVoltMass)
  def dimensionSymbol = "M"
}

/**
 * Base trait for units of [[squants.mass.Mass]]
 */
trait MassUnit extends UnitOfMeasure[Mass] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Mass(n, this)
}

object Grams extends MassUnit with PrimaryUnit with SiUnit {
  val symbol = "g"
}

object Nanograms extends MassUnit with SiUnit {
  val conversionFactor = MetricSystem.Nano
  val symbol = "ng"
}

object Micrograms extends MassUnit with SiUnit {
  val conversionFactor = MetricSystem.Micro
  val symbol = "mcg"
}

object Milligrams extends MassUnit with SiUnit {
  val conversionFactor = MetricSystem.Milli
  val symbol = "mg"
}

object Kilograms extends MassUnit with SiBaseUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kg"
}

object Tonnes extends MassUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "t"
}

object Ounces extends MassUnit {
  val conversionFactor = Pounds.conversionFactor / 16d
  val symbol = "oz"
}

object Pounds extends MassUnit {
  val conversionFactor = Kilograms.conversionFactor * 4.5359237e-1
  val symbol = "lb"
}

object Kilopounds extends MassUnit {
  val conversionFactor = Pounds.conversionFactor * MetricSystem.Kilo
  val symbol = "klb"
}

object Megapounds extends MassUnit {
  val conversionFactor = Pounds.conversionFactor * MetricSystem.Kilo
  val symbol = "Mlb"
}

object Stone extends MassUnit {
  val conversionFactor = Pounds.conversionFactor * 14d
  val symbol = "st"
}

object TroyGrains extends MassUnit {
  val conversionFactor = 64.79891 * Milligrams.conversionFactor
  val symbol = "gr"
}

object Pennyweights extends MassUnit {
  val conversionFactor = 24d * TroyGrains.conversionFactor
  val symbol = "dwt"
}

object TroyOunces extends MassUnit {
  val conversionFactor = 480d * TroyGrains.conversionFactor
  val symbol = "oz t"
}

object TroyPounds extends MassUnit {
  val conversionFactor = 12d * TroyOunces.conversionFactor
  val symbol = "lb t"
}

object Tolas extends MassUnit {
  val conversionFactor = 180d * TroyGrains.conversionFactor
  val symbol = "tola"
}

object Carats extends MassUnit {
  val conversionFactor = 200d * Milligrams.conversionFactor
  val symbol = "ct"
}

object SolarMasses extends MassUnit {
  val conversionFactor = 1.98855e33
  val symbol = "M☉"
}

object ElectronVoltMass extends MassUnit {
  val conversionFactor = 1.782662e-36
  val symbol = "eV/c²"
}

object MilliElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Milli
  val symbol = "meV/c²"
}

object KiloElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Kilo
  val symbol = "keV/c²"
}

object MegaElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Mega
  val symbol = "MeV/c²"
}

object GigaElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Giga
  val symbol = "GeV/c²"
}

object TeraElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Tera
  val symbol = "TeV/c²"
}

object PetaElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Peta
  val symbol = "PeV/c²"
}

object ExaElectronVoltMass extends MassUnit {
  val conversionFactor = ElectronVoltMass.conversionFactor * MetricSystem.Exa
  val symbol = "EeV/c²"
}
/**
 * Implicit conversions for [[squants.mass.Mass]]
 *
 * Provides support fot the DSL
 */
object MassConversions {
  lazy val nanogram = Nanograms(1)
  lazy val microgram = Micrograms(1)
  lazy val milligram = Milligrams(1)
  lazy val gram = Grams(1)
  lazy val kilogram = Kilograms(1)
  lazy val tonne = Tonnes(1)
  lazy val ounce = Ounces(1)
  lazy val pound = Pounds(1)
  lazy val kilopound = Kilopounds(1)
  lazy val megapound = Megapounds(1)
  lazy val stone = Stone(1)
  lazy val troyGrain = TroyGrains(1)
  lazy val pennyweight = Pennyweights(1)
  lazy val troyOunce = TroyOunces(1)
  lazy val troyPound = TroyPounds(1)
  lazy val tola = Tolas(1)
  lazy val carat = Carats(1)
  lazy val solarMass = SolarMasses(1)

  lazy val eV = ElectronVoltMass(1)
  lazy val meV = MilliElectronVoltMass(1)
  lazy val keV = KiloElectronVoltMass(1)
  lazy val MeV = MegaElectronVoltMass(1)
  lazy val GeV = GigaElectronVoltMass(1)
  lazy val TeV = TeraElectronVoltMass(1)
  lazy val PeV = PetaElectronVoltMass(1)
  lazy val EeV = ExaElectronVoltMass(1)

  implicit class MassConversions[A](n: A)(implicit num: Numeric[A]) {
    def ng = Nanograms(n)
    def nanograms = ng
    def mcg = Micrograms(n)
    def micrograms = mcg
    def mg = Milligrams(n)
    def milligrams = mg
    def g = Grams(n)
    def grams = g
    def kg = Kilograms(n)
    def kilograms = kg
    def tonnes = Tonnes(n)
    def ounces = Ounces(n)
    def pounds = Pounds(n)
    def kilopounds = Kilopounds(n)
    def megapounds = Megapounds(n)
    def stone = Stone(n)
    def troyGrains = TroyGrains(n)
    def dwt = Pennyweights(n)
    def pennyweights = Pennyweights(n)
    def troyOunces = TroyOunces(n)
    def troyPounds = TroyPounds(n)
    def tolas = Tolas(n)
    def ct = Carats(n)
    def carats = Carats(n)
    def solarMasses = SolarMasses(n)

    def eV = ElectronVoltMass(n)
    def meV = MilliElectronVoltMass(n)
    def keV = KiloElectronVoltMass(n)
    def MeV = MegaElectronVoltMass(n)
    def GeV = GigaElectronVoltMass(n)
    def TeV = TeraElectronVoltMass(n)
    def PeV = PetaElectronVoltMass(n)
    def EeV = ExaElectronVoltMass(n)
  }

  implicit class MassStringConversions(val s: String) {
    def toMass = Mass(s)
  }

  implicit object MassNumeric extends AbstractQuantityNumeric[Mass](Mass.primaryUnit)
}

