package squants2.electro

import squants2._

final case class ElectricCurrent[A: QNumeric] private [electro] (value: A, unit: ElectricCurrentUnit) extends Quantity[A, ElectricCurrent.type] {
  override type Q[B] = ElectricCurrent[B]
}

object ElectricCurrent extends BaseDimension("Electric Current", "I") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Amperes
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Amperes
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Amperes, Milliamperes)

  // Constructors from QNumeric values
  implicit class ElectricCurrentCons[A: QNumeric](a: A) {
    def amperes: ElectricCurrent[A] = Amperes(a)
    def milliamperes: ElectricCurrent[A] = Milliamperes(a)
  }

  // Constants
  lazy val amperes: ElectricCurrent[Int] = Amperes(1)
  lazy val milliampere: ElectricCurrent[Int] = Milliamperes(1)

}

abstract class ElectricCurrentUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[ElectricCurrent.type] {
  override def dimension: ElectricCurrent.type = ElectricCurrent
  override def apply[A: QNumeric](value: A): ElectricCurrent[A] = ElectricCurrent(value, this)
}

case object Amperes extends ElectricCurrentUnit("A", 1) with PrimaryUnit with SiBaseUnit
case object Milliamperes extends ElectricCurrentUnit("mA", MetricSystem.Milli) with SiBaseUnit

