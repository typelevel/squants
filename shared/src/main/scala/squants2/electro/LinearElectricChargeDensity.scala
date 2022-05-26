/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class LinearElectricChargeDensity[A: Numeric] private[squants2] (value: A, unit: LinearElectricChargeDensityUnit)
  extends Quantity[A, LinearElectricChargeDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): ElectricCharge[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): AreaElectricChargeDensity[A] = ???
  // END CUSTOM OPS

  def toCoulombsPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](CoulombsPerMeter)
}

object LinearElectricChargeDensity extends Dimension[LinearElectricChargeDensity]("Linear Electric Charge Density") {

  override def primaryUnit: UnitOfMeasure[LinearElectricChargeDensity] with PrimaryUnit[LinearElectricChargeDensity] = CoulombsPerMeter
  override def siUnit: UnitOfMeasure[LinearElectricChargeDensity] with SiUnit[LinearElectricChargeDensity] = CoulombsPerMeter
  override lazy val units: Set[UnitOfMeasure[LinearElectricChargeDensity]] = 
    Set(CoulombsPerMeter)

  implicit class LinearElectricChargeDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerMeter: LinearElectricChargeDensity[A] = CoulombsPerMeter(a)
  }

  lazy val coulombsPerMeter: LinearElectricChargeDensity[Int] = CoulombsPerMeter(1)

}

abstract class LinearElectricChargeDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LinearElectricChargeDensity] {
  override def dimension: Dimension[LinearElectricChargeDensity] = LinearElectricChargeDensity
  override def apply[A: Numeric](value: A): LinearElectricChargeDensity[A] = LinearElectricChargeDensity(value, this)
}

case object CoulombsPerMeter extends LinearElectricChargeDensityUnit("C/m", 1) with PrimaryUnit[LinearElectricChargeDensity] with SiUnit[LinearElectricChargeDensity]
