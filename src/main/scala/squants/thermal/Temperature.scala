/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import scala.language.implicitConversions
import squants._
import squants.energy.Joules

/**
 * Represents a quantity of temperature
 *
 * Temperatures are somewhat unique in the world of quantities for a couple of reasons.
 *
 * First, different units (scales) have different "zero" values.  This means that these scales
 * are not simple multiples of the others.  There is also an "zero offset" that must be applied to conversions
 * from one scale to another.
 *
 * Second, temperatures are often quoted as though they were quantities, when in fact they are just points
 * on a scale.  Similar to a mile marker on a highway, the quantity represented is the number degrees (miles)
 * from a specific epoch on the scale.
 *
 * In fact an absolute quantity of thermodynamic temperature should be measured from absolute zero.
 * Thus, kelvinDegrees, is the SI Base unit for temperature.
 *
 * The other scales supported here, Celsius and Fahrenheit, are known as empirical scales.
 *
 * Two conversion types are supported: Degrees and Scale.  Degrees based conversions DO NOT adjust
 * for the zero point.  Thus 5 degrees C|K is the same amount of temperature as 9 degrees F.
 *
 * Scale based conversions DO adjust for the zero offset.  Thus 5 degrees C is the same as 41 degrees F
 * on the thermometer.
 *
 * When creating a temperature it is not important to consider these differences.  It is also irrelevant
 * when performing operation on temperatures in the same scale.  However, when performing
 * operations on two temperatures of different scales these factors do become important.
 *
 * The Quantity.to(unit) and Quantity.in(unit) methods are implemented to use Scale conversions
 *
 * The Ordered.compare method is implemented to use Scale conversions
 *
 * The Quantity.plus and Quantity.minus methods are implemented to use Degree conversions.
 * (This supports creating quantity ranges with Fahrenheit(60) +- Celsius(5)
 *
 * The toDegrees(unit) methods are implemented to use Degree conversions.
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value of the temperature
 */
sealed abstract class Temperature protected (val value: Double)
    extends Quantity[Temperature] with BaseQuantity {

  def baseUnit = Kelvin
  def valueUnit: TemperatureScale

  override def plus(that: Temperature): Temperature = Temperature(value + that.convert(valueUnit, withOffset = false).value, valueUnit)
  override def minus(that: Temperature): Temperature = Temperature(value - that.convert(valueUnit, withOffset = false).value, valueUnit)
  override def compare(that: Temperature): Int = if (this.value > that.toScale(valueUnit)) 1 else if (this.value < that.toScale(valueUnit)) -1 else 0
  override def equals(that: Any) = that match {
    case t: Temperature ⇒ value == t.toScale(valueUnit)
    case _              ⇒ false
  }

  def *(that: ThermalCapacity) = Joules(toKelvinScale * that.toJoulesPerKelvin)

  override def toString: String = value + valueUnit.symbol
  def toString(unit: TemperatureScale): String = in(unit).toString

  private def convert(toScale: TemperatureScale, withOffset: Boolean = true): Temperature = (valueUnit, toScale, withOffset) match {
    case (Fahrenheit, Fahrenheit, _)  ⇒ this
    case (Celsius, Celsius, _)        ⇒ this
    case (Kelvin, Kelvin, _)          ⇒ this

    case (Fahrenheit, Celsius, true)  ⇒ Celsius(TemperatureConversions.fahrenheitToCelsiusScale(value))
    case (Celsius, Fahrenheit, true)  ⇒ Fahrenheit(TemperatureConversions.celsiusToFahrenheitScale(value))
    case (Celsius, Kelvin, true)      ⇒ Kelvin(TemperatureConversions.celsiusToKelvinScale(value))
    case (Kelvin, Celsius, true)      ⇒ Celsius(TemperatureConversions.kelvinToCelsiusScale(value))
    case (Fahrenheit, Kelvin, true)   ⇒ Kelvin(TemperatureConversions.fahrenheitToKelvinScale(value))
    case (Kelvin, Fahrenheit, true)   ⇒ Fahrenheit(TemperatureConversions.kelvinToFahrenheitScale(value))

    case (Fahrenheit, Celsius, false) ⇒ Celsius(TemperatureConversions.fahrenheitToCelsiusDegrees(value))
    case (Celsius, Fahrenheit, false) ⇒ Fahrenheit(TemperatureConversions.celsiusToFahrenheitDegrees(value))
    case (Celsius, Kelvin, false)     ⇒ Kelvin(TemperatureConversions.celsiusToKelvinDegrees(value))
    case (Kelvin, Celsius, false)     ⇒ Celsius(TemperatureConversions.kelvinToCelsiusDegrees(value))
    case (Fahrenheit, Kelvin, false)  ⇒ Kelvin(TemperatureConversions.fahrenheitToKelvinDegrees(value))
    case (Kelvin, Fahrenheit, false)  ⇒ Fahrenheit(TemperatureConversions.kelvinToFahrenheitDegrees(value))
  }

  def in(unit: TemperatureScale) = convert(unit, withOffset = true)
  def inFahrenheit: Temperature = convert(Fahrenheit)
  def inCelsius: Temperature = convert(Celsius)
  def inKelvin: Temperature = convert(Kelvin)

  def to(unit: TemperatureScale) = toScale(unit)
  def toScale(unit: TemperatureScale) = convert(unit, withOffset = true).value
  def toFahrenheitScale = toScale(Fahrenheit)
  def toCelsiusScale = toScale(Celsius)
  def toKelvinScale = toScale(Kelvin)

  def toDegrees(unit: TemperatureScale) = convert(unit, withOffset = false).value
  def toFahrenheitDegrees = toDegrees(Fahrenheit)
  def toCelsiusDegrees = toDegrees(Celsius)
  def toKelvinDegrees = toDegrees(Kelvin)
}

/**
 * Temperature companion object
 */
object Temperature {
  val scales = Seq(Fahrenheit, Celsius, Kelvin)
  val defaultScale = Fahrenheit

  def apply(d: Double): Temperature = defaultScale(d)
  def apply(d: Double, scale: TemperatureScale) = scale match {
    case Fahrenheit ⇒ Fahrenheit(d)
    case Celsius    ⇒ Celsius(d)
    case Kelvin     ⇒ Kelvin(d)
  }
  def apply(s: String): Either[String, Temperature] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+)[ °]*(f|F|c|C|k|K)".r
    s match {
      case regex(value, Fahrenheit.symbol) ⇒ Right(Fahrenheit(value.toDouble))
      case regex(value, "f")               ⇒ Right(Fahrenheit(value.toDouble))
      case regex(value, "F")               ⇒ Right(Fahrenheit(value.toDouble))
      case regex(value, Celsius.symbol)    ⇒ Right(Celsius(value.toDouble))
      case regex(value, "c")               ⇒ Right(Celsius(value.toDouble))
      case regex(value, "C")               ⇒ Right(Celsius(value.toDouble))
      case regex(value, Kelvin.symbol)     ⇒ Right(Kelvin(value.toDouble))
      case regex(value, "k")               ⇒ Right(Kelvin(value.toDouble))
      case regex(value, "K")               ⇒ Right(Kelvin(value.toDouble))
      case _                               ⇒ Left(s"Unable to parse $s as Temperature")
    }
  }
}

final class Celsius(value: Double) extends Temperature(value) { def valueUnit = Celsius }
final class Fahrenheit(value: Double) extends Temperature(value) { def valueUnit = Fahrenheit }
final class Kelvin(value: Double) extends Temperature(value) { def valueUnit = Kelvin }

/**
 * Base trait for units of [[squants.thermal.Temperature]]
 */
sealed trait TemperatureScale extends BaseQuantityUnit[Temperature] with UnitConverter {
  def dimensionSymbol = "Θ"
  def self: TemperatureScale
}

object Celsius extends TemperatureScale {
  val symbol = "°C"
  val self = this
  protected def converterFrom = TemperatureConversions.celsiusToKelvinScale
  protected def converterTo = TemperatureConversions.kelvinToCelsiusScale
  def apply(temperature: Temperature): Temperature = temperature.inCelsius
  def apply(degrees: Double) = new Celsius(degrees)
}

object Fahrenheit extends TemperatureScale {
  val symbol = "°F"
  val self = this
  protected def converterFrom = TemperatureConversions.fahrenheitToKelvinScale
  protected def converterTo = TemperatureConversions.kelvinToFahrenheitScale
  def apply(temperature: Temperature): Temperature = temperature.inFahrenheit
  def apply(degrees: Double) = new Fahrenheit(degrees)
}

object Kelvin extends TemperatureScale with ValueUnit with BaseUnit {
  val symbol = "°K"
  val self = this
  def apply(temperature: Temperature): Temperature = temperature.inKelvin
  def apply(degrees: Double) = new Kelvin(degrees)
}

object TemperatureConversions {
  lazy val kelvin = Kelvin(1)
  lazy val fahrenheit = Fahrenheit(1)
  lazy val celsius = Celsius(1)
  lazy val freezingTemp = Celsius(0d)
  lazy val boilingTemp = Celsius(100d)
  lazy val normalBodyTemp = Fahrenheit(98.6)
  lazy val absoluteZero = Kelvin(0d)

  /*
   * Degree conversions are used to convert a quantity of degrees from one scale to another.
   * These conversions do not adjust for the zero offset.
   * Essentially they only do the 9:5 conversion between F degrees and C|K degrees
   */
  def celsiusToFahrenheitDegrees(celsius: Double) = celsius * 9d / 5d
  def fahrenheitToCelsiusDegrees(fahrenheit: Double) = fahrenheit * 5d / 9d
  def celsiusToKelvinDegrees(celsius: Double) = celsius
  def kelvinToCelsiusDegrees(kelvin: Double) = kelvin
  def fahrenheitToKelvinDegrees(fahrenheit: Double) = fahrenheit * 5d / 9d
  def kelvinToFahrenheitDegrees(kelvin: Double) = kelvin * 9d / 5d

  /*
   * Scale conversions are used to convert a "thermometer" temperature from one scale to another.
   * These conversions will adjust the result by the zero offset.
   * They are used to find the equivalent absolute temperature in the other scale.
   */
  def celsiusToFahrenheitScale(celsius: Double) = celsius * 9d / 5d + 32d
  def fahrenheitToCelsiusScale(fahrenheit: Double) = (fahrenheit - 32d) * 5d / 9d
  def celsiusToKelvinScale(celsius: Double) = celsius + 273.15
  def kelvinToCelsiusScale(kelvin: Double) = kelvin - 273.15
  def fahrenheitToKelvinScale(fahrenheit: Double) = (fahrenheit + 459.67) * 5d / 9d
  def kelvinToFahrenheitScale(kelvin: Double) = kelvin * 9d / 5d - 459.67

  implicit class TemperatureConversions(d: Double) {
    def C = Celsius(d)
    def celsius = Celsius(d)
    def degreesCelsius = Celsius(d)
    def F = Fahrenheit(d)
    def Fah = Fahrenheit(d) // F conflicts with (Float) in the console; Fah is provided as an alternative
    def fahrenheit = Fahrenheit(d)
    def degreesFahrenheit = Fahrenheit(d)
    def K = Kelvin(d)
    def kelvin = Kelvin(d)
    def degreesKelvin = Kelvin(d)
  }

  implicit class TemperatureStringConversion(s: String) {
    def toTemperature = Temperature(s)
  }
}

