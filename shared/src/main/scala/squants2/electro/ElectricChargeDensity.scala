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

final case class ElectricChargeDensity[A: Numeric] private [squants2]  (value: A, unit: ElectricChargeDensityUnit)
  extends Quantity[A, ElectricChargeDensity.type] {
  override type Q[B] = ElectricChargeDensity[B]

  def toCoulombsPerCubicMeter: A = to(CoulombsPerCubicMeter)
}

object ElectricChargeDensity extends Dimension("ElectricChargeDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CoulombsPerCubicMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CoulombsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(CoulombsPerCubicMeter)

  implicit class ElectricChargeDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerCubicMeter: ElectricChargeDensity[A] = CoulombsPerCubicMeter(a)
  }

  lazy val coulombsPerCubicMeter: ElectricChargeDensity[Int] = CoulombsPerCubicMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricChargeDensityNumeric[A]()
  private case class ElectricChargeDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricChargeDensity.type], y: Quantity[A, ElectricChargeDensity.type]): Quantity[A, ElectricChargeDensity.this.type] =
      CoulombsPerCubicMeter(x.to(CoulombsPerCubicMeter) * y.to(CoulombsPerCubicMeter))
  }
}

abstract class ElectricChargeDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricChargeDensity.type] {
  override lazy val dimension: ElectricChargeDensity.type = ElectricChargeDensity
  override def apply[A: Numeric](value: A): ElectricChargeDensity[A] = ElectricChargeDensity(value, this)
}

case object CoulombsPerCubicMeter extends ElectricChargeDensityUnit("C/m³", 1.0) with PrimaryUnit with SiUnit
