/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.thermal

import squants2._

final case class ThermalCapacity[A: Numeric] private[squants2] (value: A, unit: ThermalCapacityUnit)
  extends Quantity[A, ThermalCapacity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Temperature[B])(implicit f: B => A): Energy[A] = ???
  // END CUSTOM OPS

  def toJoulesPerKelvin[B: Numeric](implicit f: A => B): B = toNum[B](JoulesPerKelvin)
}

object ThermalCapacity extends Dimension[ThermalCapacity]("Thermal Capacity") {

  override def primaryUnit: UnitOfMeasure[ThermalCapacity] with PrimaryUnit[ThermalCapacity] = JoulesPerKelvin
  override def siUnit: UnitOfMeasure[ThermalCapacity] with SiUnit[ThermalCapacity] = JoulesPerKelvin
  override lazy val units: Set[UnitOfMeasure[ThermalCapacity]] = 
    Set(JoulesPerKelvin)

  implicit class ThermalCapacityCons[A](a: A)(implicit num: Numeric[A]) {
    def joulesPerKelvin: ThermalCapacity[A] = JoulesPerKelvin(a)
  }

  lazy val joulePerKelvin: ThermalCapacity[Int] = JoulesPerKelvin(1)

}

abstract class ThermalCapacityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ThermalCapacity] {
  override def dimension: Dimension[ThermalCapacity] = ThermalCapacity
  override def apply[A: Numeric](value: A): ThermalCapacity[A] = ThermalCapacity(value, this)
}

case object JoulesPerKelvin extends ThermalCapacityUnit("J/K", 1) with PrimaryUnit[ThermalCapacity] with SiUnit[ThermalCapacity]
