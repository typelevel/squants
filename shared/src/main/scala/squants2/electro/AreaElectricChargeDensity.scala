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

final case class AreaElectricChargeDensity[A: Numeric] private [squants2]  (value: A, unit: AreaElectricChargeDensityUnit)
  extends Quantity[A, AreaElectricChargeDensity.type] {
  override type Q[B] = AreaElectricChargeDensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toCoulombsPerSquareMeter: A = to(CoulombsPerSquareMeter)
}

object AreaElectricChargeDensity extends Dimension("AreaElectricChargeDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CoulombsPerSquareMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CoulombsPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(CoulombsPerSquareMeter)

  implicit class AreaElectricChargeDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerSquareMeter: AreaElectricChargeDensity[A] = CoulombsPerSquareMeter(a)
  }

  lazy val coulombsPerSquareMeter: AreaElectricChargeDensity[Int] = CoulombsPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AreaElectricChargeDensityNumeric[A]()
  private case class AreaElectricChargeDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, AreaElectricChargeDensity.type], y: Quantity[A, AreaElectricChargeDensity.type]): Quantity[A, AreaElectricChargeDensity.this.type] =
      CoulombsPerSquareMeter(x.to(CoulombsPerSquareMeter) * y.to(CoulombsPerSquareMeter))
  }
}

abstract class AreaElectricChargeDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AreaElectricChargeDensity.type] {
  override lazy val dimension: AreaElectricChargeDensity.type = AreaElectricChargeDensity
  override def apply[A: Numeric](value: A): AreaElectricChargeDensity[A] = AreaElectricChargeDensity(value, this)
}

case object CoulombsPerSquareMeter extends AreaElectricChargeDensityUnit("C/m²", 1.0) with PrimaryUnit with SiUnit
