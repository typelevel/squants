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

final case class LinearElectricChargeDensity[A: Numeric] private [squants2]  (value: A, unit: LinearElectricChargeDensityUnit)
  extends Quantity[A, LinearElectricChargeDensity.type] {
  override type Q[B] = LinearElectricChargeDensity[B]

  def toCoulombsPerMeter: A = to(CoulombsPerMeter)
}

object LinearElectricChargeDensity extends Dimension("LinearElectricChargeDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CoulombsPerMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CoulombsPerMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(CoulombsPerMeter)

  implicit class LinearElectricChargeDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerMeter: LinearElectricChargeDensity[A] = CoulombsPerMeter(a)
  }

  lazy val coulombsPerMeter: LinearElectricChargeDensity[Int] = CoulombsPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LinearElectricChargeDensityNumeric[A]()
  private case class LinearElectricChargeDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, LinearElectricChargeDensity.type], y: Quantity[A, LinearElectricChargeDensity.type]): Quantity[A, LinearElectricChargeDensity.this.type] =
      CoulombsPerMeter(x.to(CoulombsPerMeter) * y.to(CoulombsPerMeter))
  }
}

abstract class LinearElectricChargeDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LinearElectricChargeDensity.type] {
  override lazy val dimension: LinearElectricChargeDensity.type = LinearElectricChargeDensity
  override def apply[A: Numeric](value: A): LinearElectricChargeDensity[A] = LinearElectricChargeDensity(value, this)
}

case object CoulombsPerMeter extends LinearElectricChargeDensityUnit("C/m", 1.0) with PrimaryUnit with SiUnit
