/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.time.TimeIntegral
import squants.energy.{ Watts, Joules }
import squants.radio.WattsPerSteradian
import squants.electro._
import squants.radio.SpectralPower
import squants.motion.Velocity
import squants.radio.RadiantIntensity
import scala.Some
import squants.radio.SpectralIntensity

/**
 * Represents a quantity of length
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in  [[squants.space.Meters]]
 */
final class Length private (val value: Double) extends Quantity[Length]
    with BaseQuantity with TimeIntegral[Velocity] {

  def valueUnit = Meters
  def baseUnit = Meters

  def *(that: Length): Area = Area(this, that)
  def *(that: Area): Volume = Volume(that, this)
  def *(that: Force): Energy = Joules(toMeters * that.toNewtons)
  def *(that: SpectralIntensity): RadiantIntensity = WattsPerSteradian(toMeters * that.toWattsPerSteradianPerMeter)
  def *(that: SpectralPower): Power = Watts(toMeters * that.toWattsPerMeter)
  def *(that: Conductivity): ElectricalConductance = Siemens(toMeters * that.toSiemensPerMeter)
  def *(that: ElectricalResistance): Resistivity = OhmMeters(toMeters * that.toOhms)
  def /(that: Time): Velocity = Velocity(this, that)
  def /(that: Velocity): Time = that.time * (this / that.change)

  def squared = this * this
  def cubed = this * this * this

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

  override def toString = toString(baseUnit)
}

/**
 * Factory singleton for length
 */
object Length {
  private[space] def apply(b: Double) = new Length(b)
  def apply(s: String): Either[String, Length] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+) *(mm|cm|m|km|in|ft|yd|mi|nmi|au|ly)".r
    s match {
      case regex(value, Millimeters.symbol)       ⇒ Right(Millimeters(value.toDouble))
      case regex(value, Centimeters.symbol)       ⇒ Right(Centimeters(value.toDouble))
      case regex(value, Meters.symbol)            ⇒ Right(Meters(value.toDouble))
      case regex(value, Kilometers.symbol)        ⇒ Right(Kilometers(value.toDouble))
      case regex(value, Inches.symbol)            ⇒ Right(Inches(value.toDouble))
      case regex(value, Feet.symbol)              ⇒ Right(Feet(value.toDouble))
      case regex(value, Yards.symbol)             ⇒ Right(Yards(value.toDouble))
      case regex(value, UsMiles.symbol)           ⇒ Right(UsMiles(value.toDouble))
      case regex(value, NauticalMiles.symbol)     ⇒ Right(NauticalMiles(value.toDouble))
      case regex(value, AstronomicalUnits.symbol) ⇒ Right(AstronomicalUnits(value.toDouble))
      case regex(value, LightYears.symbol)        ⇒ Right(LightYears(value.toDouble))
      case _                                      ⇒ Left(s"Unable to parse $s as Length")
    }
  }
}

/**
 * Base trait for units of [[squants.space.Length]]
 */
trait LengthUnit extends BaseQuantityUnit[Length] with UnitMultiplier {
  val dimensionSymbol = "L"
  def apply(d: Double) = Length(convertFrom(d))
  def unapply(l: Length) = Some(convertTo(l.value))
}

object Microns extends LengthUnit {
  val symbol = "µm"
  val multiplier = MetricSystem.Micro
}

object Millimeters extends LengthUnit {
  val symbol = "mm"
  val multiplier = MetricSystem.Milli
}

object Centimeters extends LengthUnit {
  val symbol = "cm"
  val multiplier = MetricSystem.Centi
}

object Decimeters extends LengthUnit {
  val symbol = "dm"
  val multiplier = MetricSystem.Deci
}

object Meters extends LengthUnit with ValueUnit with BaseUnit {
  val symbol = "m"
}

object Decameters extends LengthUnit {
  val symbol = "dam"
  val multiplier = MetricSystem.Deca
}

object Hectometers extends LengthUnit {
  val symbol = "hm"
  val multiplier = MetricSystem.Hecto
}

object Kilometers extends LengthUnit {
  val symbol = "km"
  val multiplier = MetricSystem.Kilo
}

object Inches extends LengthUnit {
  val multiplier = Feet.multiplier / 12
  val symbol = "in"
}

object Feet extends LengthUnit {
  val multiplier = 0.3048006096
  val symbol = "ft"
}

object Yards extends LengthUnit {
  val multiplier = Feet.multiplier * 3
  val symbol = "yd"
}

object UsMiles extends LengthUnit {
  val multiplier = Feet.multiplier * 5280
  val symbol = "mi"
}

object InternationalMiles extends LengthUnit {
  val multiplier = 1609.344
  val symbol = "mile"
}

object NauticalMiles extends LengthUnit {
  val multiplier = 1852D
  val symbol = "nmi"
}

object AstronomicalUnits extends LengthUnit {
  val multiplier = 149597870700D
  val symbol = "au"
}

object LightYears extends LengthUnit {
  val multiplier = 9460730472580800D
  val symbol = "ly"
}

object LengthConversions {
  lazy val micron = Microns(1)
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

  implicit class LengthConversions(d: Double) {
    def µm = Microns(d)
    def microns = Microns(d)
    def mm = Millimeters(d)
    def millimeters = Millimeters(d)
    def millimetres = Millimeters(d)
    def cm = Centimeters(d)
    def centimeters = Centimeters(d)
    def centimetres = Centimeters(d)
    def dm = Decimeters(d)
    def meters = Meters(d)
    def metres = Meters(d)
    def dam = Decameters(d)
    def hm = Hectometers(d)
    def km = Kilometers(d)
    def kilometers = Kilometers(d)
    def kilometres = Kilometers(d)
    def inches = Inches(d)
    def ft = Feet(d)
    def feet = Feet(d)
    def yd = Yards(d)
    def yards = Yards(d)
    def miles = UsMiles(d)
    def nmi = NauticalMiles(d)
    def au = AstronomicalUnits(d)
    def ly = LightYears(d)
    def lightYears = LightYears(d)
  }

  implicit class LengthStringConversions(s: String) {
    def toLength = Length(s)
  }

  implicit object LengthNumeric extends AbstractQuantityNumeric[Length](Meters)
}

