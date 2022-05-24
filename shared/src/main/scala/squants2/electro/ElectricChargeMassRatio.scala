/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class ElectricChargeMassRatio[A: Numeric] private [squants2]  (value: A, unit: ElectricChargeMassRatioUnit)
  extends Quantity[A, ElectricChargeMassRatio.type] {
  override type Q[B] = ElectricChargeMassRatio[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toCoulombsPerKilogram: A = to(CoulombsPerKilogram)
}

object ElectricChargeMassRatio extends Dimension("Electric Charge Mass Ratio") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CoulombsPerKilogram
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CoulombsPerKilogram
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(CoulombsPerKilogram)

  implicit class ElectricChargeMassRatioCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerKilogram: ElectricChargeMassRatio[A] = CoulombsPerKilogram(a)
  }

  lazy val coulombsPerKilogram: ElectricChargeMassRatio[Int] = CoulombsPerKilogram(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricChargeMassRatioNumeric[A]()
  private case class ElectricChargeMassRatioNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricChargeMassRatio.type], y: Quantity[A, ElectricChargeMassRatio.type]): Quantity[A, ElectricChargeMassRatio.this.type] =
      CoulombsPerKilogram(x.to(CoulombsPerKilogram) * y.to(CoulombsPerKilogram))
  }
}

abstract class ElectricChargeMassRatioUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricChargeMassRatio.type] {
  override lazy val dimension: ElectricChargeMassRatio.type = ElectricChargeMassRatio
  override def apply[A: Numeric](value: A): ElectricChargeMassRatio[A] = ElectricChargeMassRatio(value, this)
}

case object CoulombsPerKilogram extends ElectricChargeMassRatioUnit("C/kg", 1) with PrimaryUnit with SiUnit
