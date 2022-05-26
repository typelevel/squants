/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class ElectricChargeDensity[A: Numeric] private[squants2] (value: A, unit: ElectricChargeDensityUnit)
  extends Quantity[A, ElectricChargeDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Volume[B])(implicit f: B => A): ElectricCharge[A] = ???
  //  def *[B](that: Area[B])(implicit f: B => A): LinearElectricChargeDensity[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): AreaElectricChargeDensity[A] = ???
  // END CUSTOM OPS

  def toCoulombsPerCubicMeter[B: Numeric](implicit f: A => B): B = toNum[B](CoulombsPerCubicMeter)
}

object ElectricChargeDensity extends Dimension[ElectricChargeDensity]("Electric Charge Density") {

  override def primaryUnit: UnitOfMeasure[ElectricChargeDensity] with PrimaryUnit[ElectricChargeDensity] = CoulombsPerCubicMeter
  override def siUnit: UnitOfMeasure[ElectricChargeDensity] with SiUnit[ElectricChargeDensity] = CoulombsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[ElectricChargeDensity]] = 
    Set(CoulombsPerCubicMeter)

  implicit class ElectricChargeDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerCubicMeter: ElectricChargeDensity[A] = CoulombsPerCubicMeter(a)
  }

  lazy val coulombsPerCubicMeter: ElectricChargeDensity[Int] = CoulombsPerCubicMeter(1)

}

abstract class ElectricChargeDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricChargeDensity] {
  override def dimension: Dimension[ElectricChargeDensity] = ElectricChargeDensity
  override def apply[A: Numeric](value: A): ElectricChargeDensity[A] = ElectricChargeDensity(value, this)
}

case object CoulombsPerCubicMeter extends ElectricChargeDensityUnit("C/m³", 1) with PrimaryUnit[ElectricChargeDensity] with SiUnit[ElectricChargeDensity]
