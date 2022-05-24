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

final case class ThermalCapacity[A: Numeric] private [squants2]  (value: A, unit: ThermalCapacityUnit)
  extends Quantity[A, ThermalCapacity.type] {
  override type Q[B] = ThermalCapacity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toJoulesPerKelvin: A = to(JoulesPerKelvin)
}

object ThermalCapacity extends Dimension("ThermalCapacity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = JoulesPerKelvin
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = JoulesPerKelvin
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(JoulesPerKelvin)

  implicit class ThermalCapacityCons[A](a: A)(implicit num: Numeric[A]) {
    def joulesPerKelvin: ThermalCapacity[A] = JoulesPerKelvin(a)
  }

  lazy val joulesPerKelvin: ThermalCapacity[Int] = JoulesPerKelvin(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ThermalCapacityNumeric[A]()
  private case class ThermalCapacityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ThermalCapacity.type], y: Quantity[A, ThermalCapacity.type]): Quantity[A, ThermalCapacity.this.type] =
      JoulesPerKelvin(x.to(JoulesPerKelvin) * y.to(JoulesPerKelvin))
  }
}

abstract class ThermalCapacityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ThermalCapacity.type] {
  override lazy val dimension: ThermalCapacity.type = ThermalCapacity
  override def apply[A: Numeric](value: A): ThermalCapacity[A] = ThermalCapacity(value, this)
}

case object JoulesPerKelvin extends ThermalCapacityUnit("J/K", 1.0) with PrimaryUnit with SiUnit
