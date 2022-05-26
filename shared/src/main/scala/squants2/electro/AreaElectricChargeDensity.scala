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

final case class AreaElectricChargeDensity[A: Numeric] private[squants2] (value: A, unit: AreaElectricChargeDensityUnit)
  extends Quantity[A, AreaElectricChargeDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): ElectricCharge[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): LinearElectricChargeDensity[A] = ???
  // END CUSTOM OPS

  def toCoulombsPerSquareMeter[B: Numeric](implicit f: A => B): B = toNum[B](CoulombsPerSquareMeter)
}

object AreaElectricChargeDensity extends Dimension[AreaElectricChargeDensity]("Area Electric Charge Density") {

  override def primaryUnit: UnitOfMeasure[AreaElectricChargeDensity] with PrimaryUnit[AreaElectricChargeDensity] = CoulombsPerSquareMeter
  override def siUnit: UnitOfMeasure[AreaElectricChargeDensity] with SiUnit[AreaElectricChargeDensity] = CoulombsPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[AreaElectricChargeDensity]] = 
    Set(CoulombsPerSquareMeter)

  implicit class AreaElectricChargeDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerSquareMeter: AreaElectricChargeDensity[A] = CoulombsPerSquareMeter(a)
  }

  lazy val coulombsPerSquareMeter: AreaElectricChargeDensity[Int] = CoulombsPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, AreaElectricChargeDensity] = AreaElectricChargeDensityNumeric[A]()
  private case class AreaElectricChargeDensityNumeric[A: Numeric]() extends QuantityNumeric[A, AreaElectricChargeDensity](this) {
    override def times(x: Quantity[A, AreaElectricChargeDensity], y: Quantity[A, AreaElectricChargeDensity]): Quantity[A, AreaElectricChargeDensity] =
      CoulombsPerSquareMeter(x.to(CoulombsPerSquareMeter) * y.to(CoulombsPerSquareMeter))
  }
}

abstract class AreaElectricChargeDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AreaElectricChargeDensity] {
  override def dimension: Dimension[AreaElectricChargeDensity] = AreaElectricChargeDensity
  override def apply[A: Numeric](value: A): AreaElectricChargeDensity[A] = AreaElectricChargeDensity(value, this)
}

case object CoulombsPerSquareMeter extends AreaElectricChargeDensityUnit("C/m²", 1) with PrimaryUnit[AreaElectricChargeDensity] with SiUnit[AreaElectricChargeDensity]
