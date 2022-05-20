package squants2.thermal

import squants2.QNumeric.QNumericOps
import squants2._

final case class Temperature[A: QNumeric] private [thermal]  (value: A, unit: TemperatureUnit) extends Quantity[A, Temperature.type] {
  override type Q[B] = Temperature[B]
}

object Temperature extends BaseDimension("Temperature", "Θ") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Kelvin
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Kelvin
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Kelvin, Rankine, Celsius, Fahrenheit)

  // Constructors from QNumeric values
  implicit class TemperatureCons[A: QNumeric](a: A) {
    def kelvin: Temperature[A] = Kelvin(a)
    def rankine: Temperature[A] = Rankine(a)
    def celsius: Temperature[A] = Celsius(a)
    def fahrenheit: Temperature[A] = Fahrenheit(a)
  }

  // Constants
  lazy val absoluteZero: Temperature[Int] = Kelvin(0)

}

abstract class TemperatureUnit(val symbol: String, val conversionFactor: Double, val zeroOffset: Double) extends UnitOfMeasure[Temperature.type] {
  override def dimension: Temperature.type = Temperature

  override def apply[A: QNumeric](value: A): Temperature[A] = Temperature(value, this)

  override def convertTo[A](quantity: Quantity[A, Temperature.type], uom: UnitOfMeasure[Temperature.type])(implicit qNum: QNumeric[A]): Quantity[A, Temperature.type] = {
    (quantity.unit, uom) match {
      case (Kelvin, Kelvin)         => quantity
      case (Rankine, Rankine)       => quantity
      case (Celsius, Celsius)       => quantity
      case (Fahrenheit, Fahrenheit) => quantity

      case (Kelvin, Rankine)        => Rankine(quantity.value * qNum.fromDouble(9) / qNum.fromDouble(5))
      case (Rankine, Kelvin)        => Kelvin(quantity.value * qNum.fromDouble(5) / qNum.fromDouble(9))

      case (Kelvin, Celsius)        => Celsius(quantity.value - qNum.fromDouble(Celsius.zeroOffset))
      case (Celsius, Kelvin)        => Kelvin(quantity.value + qNum.fromDouble(Celsius.zeroOffset))

      case (Kelvin, Fahrenheit)     => Fahrenheit(quantity.value * qNum.fromDouble(9) / qNum.fromDouble(5) + qNum.fromDouble(32))
      case (Fahrenheit, Kelvin)     => Celsius((quantity.value - qNum.fromDouble(32)) * qNum.fromDouble(5) / qNum.fromDouble(9))

      case (Rankine, Fahrenheit)    => Fahrenheit(quantity.value - qNum.fromDouble(Fahrenheit.zeroOffset))
      case (Fahrenheit, Rankine)    => Rankine(quantity.value + qNum.fromDouble(Fahrenheit.zeroOffset))

      case (Rankine, Celsius)       => Celsius((quantity.value - qNum.fromDouble(491.67)) * qNum.fromDouble(5) / qNum.fromDouble(9))
      case (Celsius, Rankine)       => Rankine((quantity.value + qNum.fromDouble(Celsius.zeroOffset)) * qNum.fromDouble(9) / qNum.fromDouble(5))

      case (Celsius, Fahrenheit)    => Fahrenheit(quantity.value * qNum.fromDouble(9) / qNum.fromDouble(5) + qNum.fromDouble(32))
      case (Fahrenheit, Celsius)    => Celsius((quantity.value - qNum.fromDouble(32)) * qNum.fromDouble(5) / qNum.fromDouble(9))

    }
  }
}

case object Kelvin extends TemperatureUnit("K", 1, 0) with PrimaryUnit with SiBaseUnit
case object Rankine extends TemperatureUnit("°R", 5 / 9, 0)
case object Celsius extends TemperatureUnit("°C", 1, 273.15)
case object Fahrenheit extends TemperatureUnit("°F", 5 / 9, 459.67)
