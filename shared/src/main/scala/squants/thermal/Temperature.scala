/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import squants._
import squants.Platform.crossFormat
import squants.energy.Joules
import scala.util.{ Failure, Success, Try }

/**
 * Represents a quantity of temperature
 *
 * Temperatures are somewhat unique in the world of quantities for a couple of reasons.
 *
 * First, different units (scales) have different "zero" values.  This means that these scales
 * are not simple multiples of the others.  There is a "zero offset" that must be applied to conversions
 * from one scale to another.
 *
 * Second, temperatures are often quoted as though they were quantities, when in fact they are just points
 * on a scale.  Similar to a mile marker on a highway, the quantity represented is the number degrees (miles)
 * from a specific "zero" value on the scale.
 *
 * In fact an absolute quantity of thermodynamic temperature should be measured from absolute zero.
 * Thus, Kelvin, is the SI Base unit for temperature.
 *
 * The other scales supported here, Celsius and Fahrenheit, are known as empirical scales.
 * Of course, these scales set their respective zero values well above absolute zero.
 * This is done to provide a granular and reasonably sized ranges of values for dealing with everyday temperatures.
 *
 * This library supports another absolute scale, the Rankine scale. Rankine sets its zero at absolute zero,
 * but degrees are measure in Fahrenheit (as opposed to Celsius, as the Kelvin scale uses).
 *
 * In consideration of these more unique scale conversions, two conversion types are supported: Degrees and Scale.
 *
 * Scale based conversions DO adjust for the zero offset.
 * Thus 5 degrees C is the same as 41 degrees F on the thermometer.
 *
 * Degrees based conversions DO NOT adjust for the zero point.
 * Thus 5 degrees C|K is the same amount of temperature as 9 degrees F|R.
 *
 * When creating a temperature it is not important to consider these differences.
 * It is also irrelevant when performing operation on temperatures in the same scale.
 * However, when performing operations on two temperatures of different scales these factors do become important.
 *
 * The Quantity.to(unit) and Quantity.in(unit) methods are overridden to use Scale conversions for convenience
 *
 * The Ordered.compare method is implemented to use Scale conversions
 *
 * The Quantity.plus and Quantity.minus methods are implemented to treat right operands as Quantity of Degrees and not a scale Temperature.
 * Operands that differ in scale will use Degree conversions.
 * This supports mixed scale expressions:
 *
 * val temp = Fahrenheit(100) - Celsius(5) // returns Fahrenheit(91)
 *
 * This also supports declaring temperature ranges using typical nomenclature:
 *
 * val tempRange = 65.F +- 5.C // returns QuantityRange(56.0°F,74.0°F)
 *
 * The toDegrees(unit) methods are implemented to use Degree conversions.
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value of the temperature
 */

final class Temperature private (val value: Double, val unit: TemperatureScale)
    extends Quantity[Temperature] {

  def dimension = Temperature

  override def plus(that: Temperature): Temperature = Temperature(this.value + that.convert(unit, withOffset = false).value, unit)
  override def minus(that: Temperature): Temperature = Temperature(this.value - that.convert(unit, withOffset = false).value, unit)

  def *(that: ThermalCapacity) = Joules(this.toKelvinScale * that.toJoulesPerKelvin)

  override def toString: String = unit match {
    case Kelvin => super.toString
    case _ => crossFormat(value) + unit.symbol // Non-Kelvin units are treated in a special manner, they do not get a space between the value and symbol.
  }

  def toString(unit: TemperatureScale): String = in(unit).toString

  private def convert(toScale: TemperatureScale, withOffset: Boolean = true): Temperature = (unit, toScale, withOffset) match {
    case (Fahrenheit, Fahrenheit, _)  ⇒ this
    case (Celsius, Celsius, _)        ⇒ this
    case (Kelvin, Kelvin, _)          ⇒ this
    case (Rankine, Rankine, _)        ⇒ this

    case (Fahrenheit, Celsius, true)  ⇒ Celsius(TemperatureConversions.fahrenheitToCelsiusScale(value))
    case (Celsius, Fahrenheit, true)  ⇒ Fahrenheit(TemperatureConversions.celsiusToFahrenheitScale(value))
    case (Celsius, Kelvin, true)      ⇒ Kelvin(TemperatureConversions.celsiusToKelvinScale(value))
    case (Kelvin, Celsius, true)      ⇒ Celsius(TemperatureConversions.kelvinToCelsiusScale(value))
    case (Fahrenheit, Kelvin, true)   ⇒ Kelvin(TemperatureConversions.fahrenheitToKelvinScale(value))
    case (Kelvin, Fahrenheit, true)   ⇒ Fahrenheit(TemperatureConversions.kelvinToFahrenheitScale(value))
    case (Fahrenheit, Rankine, true)  ⇒ Rankine(TemperatureConversions.fahrenheitToRankineScale(value))
    case (Rankine, Fahrenheit, true)  ⇒ Fahrenheit(TemperatureConversions.rankineToFahrenheitScale(value))
    case (Celsius, Rankine, true)     ⇒ Rankine(TemperatureConversions.celsiusToRankineScale(value))
    case (Rankine, Celsius, true)     ⇒ Celsius(TemperatureConversions.rankineToCelsiusScale(value))
    case (Kelvin, Rankine, true)      ⇒ Rankine(TemperatureConversions.kelvinToRankineScale(value))
    case (Rankine, Kelvin, true)      ⇒ Kelvin(TemperatureConversions.rankineToKelvinScale(value))


    case (Fahrenheit, Celsius, false) ⇒ Celsius(TemperatureConversions.fahrenheitToCelsiusDegrees(value))
    case (Celsius, Fahrenheit, false) ⇒ Fahrenheit(TemperatureConversions.celsiusToFahrenheitDegrees(value))
    case (Celsius, Kelvin, false)     ⇒ Kelvin(TemperatureConversions.celsiusToKelvinDegrees(value))
    case (Kelvin, Celsius, false)     ⇒ Celsius(TemperatureConversions.kelvinToCelsiusDegrees(value))
    case (Fahrenheit, Kelvin, false)  ⇒ Kelvin(TemperatureConversions.fahrenheitToKelvinDegrees(value))
    case (Kelvin, Fahrenheit, false)  ⇒ Fahrenheit(TemperatureConversions.kelvinToFahrenheitDegrees(value))
    case (Fahrenheit, Rankine, false) ⇒ Rankine(TemperatureConversions.fahrenheitToRankineDegrees(value))
    case (Rankine, Fahrenheit, false) ⇒ Fahrenheit(TemperatureConversions.rankineToFahrenheitDegrees(value))
    case (Celsius, Rankine, false)    ⇒ Rankine(TemperatureConversions.celsiusToRankineDegrees(value))
    case (Rankine, Celsius, false)    ⇒ Celsius(TemperatureConversions.rankineToCelsiusDegrees(value))
    case (Kelvin, Rankine, false)     ⇒ Rankine(TemperatureConversions.kelvinToRankineDegrees(value))
    case (Rankine, Kelvin, false)     ⇒ Kelvin(TemperatureConversions.rankineToKelvinDegrees(value))
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
object Temperature extends Dimension[Temperature] with BaseDimension {
  def apply[A](n: A, scale: TemperatureScale)(implicit num: Numeric[A]) = new Temperature(num.toDouble(n), scale)

  def apply(s: String): Try[Temperature] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?) *°? *(f|F|c|C|k|K|r|R)".r
    s match {
      case regex(value, unit) => unit match {
        case "f" | "F" => Success(Fahrenheit(value.toDouble))
        case "c" | "C" => Success(Celsius(value.toDouble))
        case "k" | "K" => Success(Kelvin(value.toDouble))
        case "r" | "R" => Success(Rankine(value.toDouble))
      }
      case _ => Failure(QuantityParseException("Unable to parse Temperature", s))
    }
  }

  def name = "Temperature"
  def primaryUnit = Kelvin
  def siUnit = Kelvin
  def units = Set(Kelvin, Fahrenheit, Celsius, Rankine)
  def dimensionSymbol = "Θ"
}

/**
 * Base trait for units of [[squants.thermal.Temperature]]
 */
sealed trait TemperatureScale extends UnitOfMeasure[Temperature] {
  def self: TemperatureScale
  def apply[A](n: A)(implicit num: Numeric[A]) = Temperature(num.toDouble(n), this)
}

object Celsius extends TemperatureScale {
  val symbol = "°C"
  val self = this
  protected def converterFrom = TemperatureConversions.celsiusToKelvinScale
  protected def converterTo = TemperatureConversions.kelvinToCelsiusScale
  def apply(temperature: Temperature): Temperature = temperature.inCelsius
}

object Fahrenheit extends TemperatureScale {
  val symbol = "°F"
  val self = this
  protected def converterFrom = TemperatureConversions.fahrenheitToKelvinScale
  protected def converterTo = TemperatureConversions.kelvinToFahrenheitScale
  def apply(temperature: Temperature): Temperature = temperature.inFahrenheit
}

object Kelvin extends TemperatureScale with PrimaryUnit with SiBaseUnit {
  val symbol = "K"
  val self = this
  def apply(temperature: Temperature): Temperature = temperature.inKelvin
}

object Rankine extends TemperatureScale {
  val symbol = "°R"
  val self = this
  protected def converterFrom = TemperatureConversions.rankineToKelvinScale
  protected def converterTo = TemperatureConversions.kelvinToRankineScale
  def apply(temperature: Temperature): Temperature = temperature.in(Rankine)
}

object TemperatureConversions {
  lazy val kelvin = Kelvin(1)
  lazy val fahrenheit = Fahrenheit(1)
  lazy val celsius = Celsius(1)
  lazy val rankine = Rankine(1)

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
  def celsiusToRankineDegrees(celsius: Double) = celsius * 9d / 5d
  def rankineToCelsiusDegrees(rankine: Double) = rankine * 5d / 9d
  def fahrenheitToRankineDegrees(fahrenheit: Double) = fahrenheit
  def rankineToFahrenheitDegrees(rankine: Double) = rankine
  def kelvinToRankineDegrees(kelvin: Double) = kelvin * 9d / 5d
  def rankineToKelvinDegrees(rankine: Double) = rankine * 5d / 9d


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
  def celsiusToRankineScale(celsius: Double) = (celsius + 273.15) * 9d / 5d
  def rankineToCelsiusScale(rankine: Double) = (rankine - 491.67) * 5d / 9d
  def fahrenheitToRankineScale(fahrenheit: Double) = fahrenheit + 459.67
  def rankineToFahrenheitScale(rankine: Double) = rankine - 459.67
  def kelvinToRankineScale(kelvin: Double) = kelvin * 9d / 5d
  def rankineToKelvinScale(rankine: Double) = rankine * 5d / 9d

  implicit class TemperatureConversions[A](n: A)(implicit num: Numeric[A]) {
    def C = Celsius(n)
    def celsius = Celsius(n)
    def degreesCelsius = Celsius(n)
    def F = Fahrenheit(n)
    def Fah = Fahrenheit(n) // F conflicts with (Float) in the console; Fah is provided as an alternative
    def fahrenheit = Fahrenheit(n)
    def degreesFahrenheit = Fahrenheit(n)
    def K = Kelvin(n)
    def kelvin = Kelvin(n)
    def degreesKelvin = Kelvin(n)
    def R = Rankine(n)
    def rankine = Rankine(n)
    def degreesRankine = Rankine(n)
  }

  implicit class TemperatureStringConversion(s: String) {
    def toTemperature = Temperature(s)
  }
}

