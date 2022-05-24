/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.thermal

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Temperature[A: Numeric] private [squants2]  (value: A, unit: TemperatureUnit)
  extends Quantity[A, Temperature.type] {
  override type Q[B] = Temperature[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toRankine: A = to(Rankine)
  def toKelvin: A = to(Kelvin)
  def toFahrenheit: A = to(Fahrenheit)
  def toCelsius: A = to(Celsius)
}

object Temperature extends BaseDimension("Temperature", "Θ") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Kelvin
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Kelvin
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Rankine, Kelvin, Fahrenheit, Celsius)

  implicit class TemperatureCons[A](a: A)(implicit num: Numeric[A]) {
    def rankine: Temperature[A] = Rankine(a)
    def kelvin: Temperature[A] = Kelvin(a)
    def fahrenheit: Temperature[A] = Fahrenheit(a)
    def celsius: Temperature[A] = Celsius(a)
  }

  lazy val rankine: Temperature[Int] = Rankine(1)
  lazy val kelvin: Temperature[Int] = Kelvin(1)
  lazy val fahrenheit: Temperature[Int] = Fahrenheit(1)
  lazy val celsius: Temperature[Int] = Celsius(1)

  lazy val absoluteZero: Temperature[Int] = Kelvin(0)

}

abstract class TemperatureUnit(val symbol: String, val conversionFactor: ConversionFactor, val zeroOffset: Double) extends UnitOfMeasure[Temperature.type] {
  override def dimension: Temperature.type = Temperature

  override def apply[A: Numeric](value: A): Temperature[A] = Temperature(value, this)

  override def convertTo[A](quantity: Quantity[A, Temperature.type], uom: UnitOfMeasure[Temperature.type])(implicit num: Numeric[A]): Quantity[A, Temperature.type] = {
    (quantity.unit, uom) match {
      case (Kelvin, Kelvin)         => quantity
      case (Rankine, Rankine)       => quantity
      case (Celsius, Celsius)       => quantity
      case (Fahrenheit, Fahrenheit) => quantity

      case (Kelvin, Rankine)        => Rankine(quantity.value * nineFifths)
      case (Rankine, Kelvin)        => Kelvin(quantity.value * fiveNinths)

      case (Kelvin, Celsius)        => Celsius(quantity.value - celsOffset)
      case (Celsius, Kelvin)        => Kelvin(quantity.value + celsOffset)

      case (Kelvin, Fahrenheit)     => Fahrenheit(quantity.value * nineFifths + num.fromInt(32))
      case (Fahrenheit, Kelvin)     => Celsius((quantity.value - num.fromInt(32)) * fiveNinths)

      case (Rankine, Fahrenheit)    => Fahrenheit(quantity.value - fahrOffset)
      case (Fahrenheit, Rankine)    => Rankine(quantity.value + fahrOffset)

      case (Rankine, Celsius)       => Celsius((quantity.value - (fahrOffset + num.fromInt(32))) * fiveNinths)
      case (Celsius, Rankine)       => Rankine((quantity.value + celsOffset) * nineFifths)

      case (Celsius, Fahrenheit)    => Fahrenheit(quantity.value * nineFifths + num.fromInt(32))
      case (Fahrenheit, Celsius)    => Celsius((quantity.value - num.fromInt(32)) * fiveNinths)

    }
  }

  private def fiveNinths[A](implicit num: Numeric[A]): A = num match {
    case fnum: Fractional[A] => fnum.div(fnum.fromInt(5), fnum.fromInt(9))
    case _ => throw new UnsupportedOperationException("Unknown Numeric Type")
  }
  private def nineFifths[A](implicit num: Numeric[A]): A = num match {
    case fnum: Fractional[A] => fnum.div(fnum.fromInt(9), fnum.fromInt(5))
    case _ => throw new UnsupportedOperationException("Unknown Numeric Type")
  }
  private def celsOffset[A](implicit num: Numeric[A]): A = num.parseString(Celsius.zeroOffset.toString).get
  private def fahrOffset[A](implicit num: Numeric[A]): A = num.parseString(Fahrenheit.zeroOffset.toString).get

}

case object Kelvin extends TemperatureUnit("K", 1, 0) with PrimaryUnit with SiBaseUnit
case object Rankine extends TemperatureUnit("°R", BigDecimal(5) / BigDecimal(9), 0)
case object Celsius extends TemperatureUnit("°C", 1, 273.15)
case object Fahrenheit extends TemperatureUnit("°F", BigDecimal(5) / BigDecimal(9), 459.67)
