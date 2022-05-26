/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.energy

import squants2._

final case class PowerDensity[A: Numeric] private[squants2] (value: A, unit: PowerDensityUnit)
  extends Quantity[A, PowerDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Volume[B])(implicit f: B => A): Power[A] = ???
  // END CUSTOM OPS

  def toWattsPerCubicMeter[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerCubicMeter)
}

object PowerDensity extends Dimension[PowerDensity]("Power Density") {

  override def primaryUnit: UnitOfMeasure[PowerDensity] with PrimaryUnit[PowerDensity] = WattsPerCubicMeter
  override def siUnit: UnitOfMeasure[PowerDensity] with SiUnit[PowerDensity] = WattsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[PowerDensity]] = 
    Set(WattsPerCubicMeter)

  implicit class PowerDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerCubicMeter: PowerDensity[A] = WattsPerCubicMeter(a)
  }

  lazy val wattsPerCubicMeter: PowerDensity[Int] = WattsPerCubicMeter(1)

}

abstract class PowerDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[PowerDensity] {
  override def dimension: Dimension[PowerDensity] = PowerDensity
  override def apply[A: Numeric](value: A): PowerDensity[A] = PowerDensity(value, this)
}

case object WattsPerCubicMeter extends PowerDensityUnit("W/m³", 1) with PrimaryUnit[PowerDensity] with SiUnit[PowerDensity]
