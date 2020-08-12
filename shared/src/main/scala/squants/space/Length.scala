/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.electro._
import squants.energy.{ Joules, Watts }
import squants.motion.{ MetersPerSecond, Velocity }
import squants.radio.{ RadiantIntensity, SpectralIntensity, SpectralPower, WattsPerSteradian }
import squants.time.{ SecondTimeIntegral, TimeIntegral, TimeSquared }

/**
 * Represents a quantity of length
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in  [[squants.space.Meters]]
 */
final class Length private (val value: Double, val unit: LengthUnit)
    extends Quantity[Length]
    with TimeIntegral[Velocity]
    with SecondTimeIntegral[Acceleration] {

  def dimension = Length

  protected def timeDerived = MetersPerSecond(toMeters)
  protected[squants] def time = Seconds(1)

  def *(that: Length): Area = unit match {
    case Centimeters ⇒ SquareCentimeters(this.value * that.toCentimeters)
    case Kilometers  ⇒ SquareKilometers(this.value * that.toKilometers)
    case UsMiles     ⇒ SquareUsMiles(this.value * that.toUsMiles)
    case Yards       ⇒ SquareYards(this.value * that.toYards)
    case Feet        ⇒ SquareFeet(this.value * that.toFeet)
    case Inches      ⇒ SquareInches(this.value * that.toInches)
    case _           ⇒ SquareMeters(toMeters * that.toMeters)
  }

  def *(that: Area): Volume = unit match {
    case Yards  ⇒ CubicYards(this.value * that.toSquareYards)
    case Feet   ⇒ CubicFeet(this.value * that.toSquareFeet)
    case Inches ⇒ CubicInches(this.value * that.toSquareInches)
    case _      ⇒ CubicMeters(this.toMeters * that.toSquareMeters)
  }

  def *(that: Force): Energy = Joules(this.toMeters * that.toNewtons)
  def *(that: SpectralIntensity): RadiantIntensity = WattsPerSteradian(this.toMeters * that.toWattsPerSteradianPerMeter)
  def *(that: SpectralPower): Power = Watts(this.toMeters * that.toWattsPerMeter)
  def *(that: Conductivity): ElectricalConductance = Siemens(this.toMeters * that.toSiemensPerMeter)
  def *(that: ElectricalResistance): Resistivity = OhmMeters(this.toMeters * that.toOhms)

  def /(that: TimeSquared): Acceleration = this / that.time1 / that.time2
  def /(that: Acceleration): TimeSquared = (this / that.timeIntegrated) * time

  def squared = this * this
  def cubed = this * this * this

  def toAngstroms = to(Angstroms)
  def toNanometers = to(Nanometers)
  def toMicrons = to(Microns)
  def toMillimeters = to(Millimeters)
  def toCentimeters = to(Centimeters)
  def toDecimeters = to(Decimeters)
  def toMeters = to(Meters)
  def toDecameters = to(Decameters)
  def toHectometers = to(Hectometers)
  def toKilometers = to(Kilometers)
  def toInches = to(Inches)
  def toFeet = to(Feet)
  def toYards = to(Yards)
  def toUsMiles = to(UsMiles)
  def toInternationalMiles = to(InternationalMiles)
  def toNauticalMiles = to(NauticalMiles)
  def toAstronomicalUnits = to(AstronomicalUnits)
  def toLightYears = to(LightYears)
  def toParsecs = to(Parsecs)
  def toKiloParsecs = to(KiloParsecs)
  def toMegaParsecs = to(MegaParsecs)
  def toGigaParsecs = to(GigaParsecs)
  def toSolarRadii = to(SolarRadii)
  def toNominalSolarRadii = to(NominalSolarRadii)
  def toeV = to(ElectronVoltLength)
  def tomeV = to(MilliElectronVoltLength)
  def tokeV = to(KiloElectronVoltLength)
  def toMeV = to(MegaElectronVoltLength)
  def toGeV = to(GigaElectronVoltLength)
  def toTeV = to(TeraElectronVoltLength)
  def toPeV = to(PetaElectronVoltLength)
  def toEeV = to(ExaElectronVoltLength)

}

/**
 * Factory singleton for length
 */
object Length extends Dimension[Length] with BaseDimension {
  private[space] def apply[A](n: A, unit: LengthUnit)(implicit num: Numeric[A]) = new Length(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Length"
  def primaryUnit = Meters
  def siUnit = Meters
  def units = Set(Angstroms, Nanometers, Microns, Millimeters, Centimeters,
    Decimeters, Meters, Decameters, Hectometers, Kilometers,
    Inches, Feet, Yards, UsMiles, InternationalMiles, NauticalMiles,
    AstronomicalUnits, LightYears, Parsecs, KiloParsecs, MegaParsecs, GigaParsecs, SolarRadii, NominalSolarRadii,
    ElectronVoltLength, MilliElectronVoltLength, KiloElectronVoltLength, MegaElectronVoltLength,
    GigaElectronVoltLength, TeraElectronVoltLength, PetaElectronVoltLength, ExaElectronVoltLength)
  def dimensionSymbol = "L"
}

/**
 * Base trait for units of [[squants.space.Length]]
 */
trait LengthUnit extends UnitOfMeasure[Length] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Length(n, this)
}

object Angstroms extends LengthUnit {
  // note: the symbol used here is the letter "\u00C5" which is to be preferred over the angstrom sign "\u212B"
  // see also: https://en.wikipedia.org/wiki/Å and http://www.fileformat.info/info/unicode/char/00c5/index.htm
  val symbol = "Å"
  val conversionFactor = 100 * MetricSystem.Pico
}

object Nanometers extends LengthUnit with SiUnit {
  val symbol = "nm"
  val conversionFactor = MetricSystem.Nano
}

object Microns extends LengthUnit with SiUnit {
  val symbol = "µm"
  val conversionFactor = MetricSystem.Micro
}

object Millimeters extends LengthUnit with SiUnit {
  val symbol = "mm"
  val conversionFactor = MetricSystem.Milli
}

object Centimeters extends LengthUnit with SiUnit {
  val symbol = "cm"
  val conversionFactor = MetricSystem.Centi
}

object Decimeters extends LengthUnit with SiUnit {
  val symbol = "dm"
  val conversionFactor = MetricSystem.Deci
}

object Meters extends LengthUnit with PrimaryUnit with SiBaseUnit {
  val symbol = "m"
}

object Decameters extends LengthUnit with SiUnit {
  val symbol = "dam"
  val conversionFactor = MetricSystem.Deca
}

object Hectometers extends LengthUnit with SiUnit {
  val symbol = "hm"
  val conversionFactor = MetricSystem.Hecto
}

object Kilometers extends LengthUnit with SiUnit {
  val symbol = "km"
  val conversionFactor = MetricSystem.Kilo
}

object Inches extends LengthUnit {
  val conversionFactor = Feet.conversionFactor / 12d
  val symbol = "in"
}

object Feet extends LengthUnit {
  val conversionFactor = 3.048006096e-1
  val symbol = "ft"
}

object Yards extends LengthUnit {
  val conversionFactor = Feet.conversionFactor * 3d
  val symbol = "yd"
}

object UsMiles extends LengthUnit {
  val conversionFactor = Feet.conversionFactor * 5.28e3
  val symbol = "mi"
}

object InternationalMiles extends LengthUnit {
  val conversionFactor = 1.609344e3
  val symbol = "mile"
}

object NauticalMiles extends LengthUnit {
  val conversionFactor = 1.852e3
  val symbol = "nmi"
}

object AstronomicalUnits extends LengthUnit {
  val conversionFactor = 1.495978707e11
  val symbol = "au"
}

object LightYears extends LengthUnit {
  val conversionFactor = 9.4607304725808e15
  val symbol = "ly"
}

object Parsecs extends LengthUnit {
  val conversionFactor = 3.08567758149137e16
  val symbol = "pc"
}

object KiloParsecs extends LengthUnit {
  val conversionFactor = Parsecs.conversionFactor * MetricSystem.Kilo
  val symbol = "kpc"
}

object MegaParsecs extends LengthUnit {
  val conversionFactor = Parsecs.conversionFactor * MetricSystem.Mega
  val symbol = "Mpc"
}

object GigaParsecs extends LengthUnit {
  val conversionFactor = Parsecs.conversionFactor * MetricSystem.Giga
  val symbol = "Gpc"
}

object SolarRadii extends LengthUnit {
  val conversionFactor = 6.957e8
  val symbol = "R☉"
}

object NominalSolarRadii extends LengthUnit {
  val conversionFactor = 6.957e8
  val symbol = "RN☉"
}

object ElectronVoltLength extends LengthUnit {
  val conversionFactor = 1.97327e-7
  val symbol = "ħc/eV"
}

object MilliElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Milli
  val symbol = "mħc/eV"
}

object KiloElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Kilo
  val symbol = "kħc/eV"
}

object MegaElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Mega
  val symbol = "Mħc/eV"
}

object GigaElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Giga
  val symbol = "Għc/eV"
}

object TeraElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Tera
  val symbol = "Tħc/eV"
}

object PetaElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Peta
  val symbol = "Pħc/eV"
}

object ExaElectronVoltLength extends LengthUnit {
  val conversionFactor = ElectronVoltLength.conversionFactor * MetricSystem.Exa
  val symbol = "Eħc/eV"
}

object LengthConversions {
  lazy val angstrom = Angstroms(1)
  lazy val nanometer = Nanometers(1)
  lazy val nanometre = Nanometers(1)
  lazy val micron = Microns(1)
  lazy val micrometer = Microns(1)
  lazy val micrometre = Microns(1)
  lazy val millimeter = Millimeters(1)
  lazy val millimetre = Millimeters(1)
  lazy val centimeter = Centimeters(1)
  lazy val centimetre = Centimeters(1)
  lazy val decimeter = Decimeters(1)
  lazy val decimetre = Decimeters(1)
  lazy val meter = Meters(1)
  lazy val metre = Meters(1)
  lazy val decameter = Decameters(1)
  lazy val decametre = Decameters(1)
  lazy val hectometer = Hectometers(1)
  lazy val hectometre = Hectometers(1)
  lazy val kilometer = Kilometers(1)
  lazy val kilometre = Kilometers(1)
  lazy val inch = Inches(1)
  lazy val foot = Feet(1)
  lazy val yard = Yards(1)
  lazy val mile = UsMiles(1)
  lazy val nauticalMile = NauticalMiles(1)
  lazy val astronomicalUnit = AstronomicalUnits(1)
  lazy val lightYear = LightYears(1)
  lazy val parsec = Parsecs(1)
  lazy val kiloparsec = KiloParsecs(1)
  lazy val megaparsec = MegaParsecs(1)
  lazy val gigaparsec = GigaParsecs(1)
  lazy val solarRadius = SolarRadii(1)
  lazy val nominalSolarRadius = NominalSolarRadii(1)

  lazy val eV = ElectronVoltLength(1)
  lazy val meV = MilliElectronVoltLength(1)
  lazy val keV = KiloElectronVoltLength(1)
  lazy val MeV = MegaElectronVoltLength(1)
  lazy val GeV = GigaElectronVoltLength(1)
  lazy val TeV = TeraElectronVoltLength(1)
  lazy val PeV = PetaElectronVoltLength(1)
  lazy val EeV = ExaElectronVoltLength(1)

  implicit class LengthConversions[A](n: A)(implicit num: Numeric[A]) {
    def Å = Angstroms(n)
    def angstroms = Angstroms(n)
    def nm = Nanometers(n)
    def nanometers = Nanometers(n)
    def nanometres = Nanometers(n)
    def µm = Microns(n)
    def microns = Microns(n)
    def micrometer = Microns(n)
    def micrometre = Microns(n)
    def mm = Millimeters(n)
    def millimeters = Millimeters(n)
    def millimetres = Millimeters(n)
    def cm = Centimeters(n)
    def centimeters = Centimeters(n)
    def centimetres = Centimeters(n)
    def dm = Decimeters(n)
    def meters = Meters(n)
    def metres = Meters(n)
    def dam = Decameters(n)
    def hm = Hectometers(n)
    def km = Kilometers(n)
    def kilometers = Kilometers(n)
    def kilometres = Kilometers(n)
    def inches = Inches(n)
    def ft = Feet(n)
    def feet = Feet(n)
    def yd = Yards(n)
    def yards = Yards(n)
    def miles = UsMiles(n)
    def nmi = NauticalMiles(n)
    def au = AstronomicalUnits(n)
    def ly = LightYears(n)
    def lightYears = LightYears(n)
    def parsecs = Parsecs(n)
    def pc = Parsecs(n)
    def kpc = KiloParsecs(n)
    def Mpc = MegaParsecs(n)
    def Gpc = GigaParsecs(n)
    def solarRadii = SolarRadii(n)
    def nominalSolarRadii = NominalSolarRadii(n)
    def eV = ElectronVoltLength(n)
    def meV = MilliElectronVoltLength(n)
    def keV = KiloElectronVoltLength(n)
    def MeV = MegaElectronVoltLength(n)
    def GeV = GigaElectronVoltLength(n)
    def TeV = TeraElectronVoltLength(n)
    def PeV = PetaElectronVoltLength(n)
    def EeV = ExaElectronVoltLength(n)
  }

  implicit class LengthStringConversions(s: String) {
    def toLength = Length(s)
  }

  implicit object LengthNumeric extends AbstractQuantityNumeric[Length](Length.primaryUnit)
}

