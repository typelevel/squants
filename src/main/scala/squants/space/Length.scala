/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.energy.{ Watts, Joules }
import squants.radio.WattsPerSteradian
import squants.electro._
import squants.radio.SpectralPower
import squants.motion.Velocity
import squants.radio.RadiantIntensity
import squants.radio.SpectralIntensity
import squants.Time

/**
 * Represents a quantity of length
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in  [[squants.space.Meters]]
 */
final class Length private (val value: Double) extends Quantity[Length] {

  def valueUnit = Length.valueUnit

  def *(that: Length): Area = Area(this, that)
  def *(that: Area): Volume = Volume(that, this)
  def *(that: Force): Energy = Joules(toMeters * that.toNewtons)
  def *(that: SpectralIntensity): RadiantIntensity = WattsPerSteradian(toMeters * that.toWattsPerSteradianPerMeter)
  def *(that: SpectralPower): Power = Watts(toMeters * that.toWattsPerMeter)
  def *(that: Conductivity): ElectricalConductance = Siemens(toMeters * that.toSiemensPerMeter)
  def *(that: ElectricalResistance): Resistivity = OhmMeters(toMeters * that.toOhms)
  def /(that: Time): Velocity = Velocity(this, that)
  def /(that: Velocity): Time = Seconds(toMeters / that.toMetersPerSecond)

  def squared = this * this
  def cubed = this * this * this

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
}

/**
 * Factory singleton for length
 */
object Length extends QuantityCompanion[Length] with BaseQuantity {
  private[space] def apply[A](b: A)(implicit num: Numeric[A]) = new Length(num.toDouble(b))
  def apply = parseString _
  def name = "Length"
  def valueUnit = Meters
  def units = Set(Nanometers, Microns, Millimeters, Centimeters,
    Decimeters, Meters, Decameters, Hectometers, Kilometers,
    Inches, Feet, Yards, UsMiles, InternationalMiles, NauticalMiles,
    AstronomicalUnits, LightYears)
  def baseUnit = Meters
  def dimensionSymbol = "L"
}

/**
 * Base trait for units of [[squants.space.Length]]
 */
trait LengthUnit extends UnitOfMeasure[Length] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Length(convertFrom(n))
}

object Nanometers extends LengthUnit {
  val symbol = "nm"
  val conversionFactor = MetricSystem.Nano
}

object Microns extends LengthUnit {
  val symbol = "µm"
  val conversionFactor = MetricSystem.Micro
}

object Millimeters extends LengthUnit {
  val symbol = "mm"
  val conversionFactor = MetricSystem.Milli
}

object Centimeters extends LengthUnit {
  val symbol = "cm"
  val conversionFactor = MetricSystem.Centi
}

object Decimeters extends LengthUnit {
  val symbol = "dm"
  val conversionFactor = MetricSystem.Deci
}

object Meters extends LengthUnit with ValueUnit with BaseUnit {
  val symbol = "m"
}

object Decameters extends LengthUnit {
  val symbol = "dam"
  val conversionFactor = MetricSystem.Deca
}

object Hectometers extends LengthUnit {
  val symbol = "hm"
  val conversionFactor = MetricSystem.Hecto
}

object Kilometers extends LengthUnit {
  val symbol = "km"
  val conversionFactor = MetricSystem.Kilo
}

object Inches extends LengthUnit {
  val conversionFactor = Feet.conversionFactor / 12
  val symbol = "in"
}

object Feet extends LengthUnit {
  val conversionFactor = 0.3048006096
  val symbol = "ft"
}

object Yards extends LengthUnit {
  val conversionFactor = Feet.conversionFactor * 3D
  val symbol = "yd"
}

object UsMiles extends LengthUnit {
  val conversionFactor = Feet.conversionFactor * 5280D
  val symbol = "mi"
}

object InternationalMiles extends LengthUnit {
  val conversionFactor = 1609.344
  val symbol = "mile"
}

object NauticalMiles extends LengthUnit {
  val conversionFactor = 1852D
  val symbol = "nmi"
}

object AstronomicalUnits extends LengthUnit {
  val conversionFactor = 149597870700D
  val symbol = "au"
}

object LightYears extends LengthUnit {
  val conversionFactor = 9460730472580800D
  val symbol = "ly"
}

object LengthConversions {
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

  implicit class LengthConversions[A](n: A)(implicit num: Numeric[A]) {
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
  }

  implicit class LengthStringConversions(s: String) {
    def toLength = Length(s)
  }

  implicit object LengthNumeric extends AbstractQuantityNumeric[Length](Length.valueUnit)
}

