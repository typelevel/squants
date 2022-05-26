/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.energy

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class EnergyDensity[A: Numeric] private[squants2] (value: A, unit: EnergyDensityUnit)
  extends Quantity[A, EnergyDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Volume[B])(implicit f: B => A): Energy[A] = ???
  // END CUSTOM OPS

  def toJoulesPerCubicMeter[B: Numeric](implicit f: A => B): B = toNum[B](JoulesPerCubicMeter)
}

object EnergyDensity extends Dimension[EnergyDensity]("Energy Density") {

  override def primaryUnit: UnitOfMeasure[EnergyDensity] with PrimaryUnit[EnergyDensity] = JoulesPerCubicMeter
  override def siUnit: UnitOfMeasure[EnergyDensity] with SiUnit[EnergyDensity] = JoulesPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[EnergyDensity]] = 
    Set(JoulesPerCubicMeter)

  implicit class EnergyDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def joulesPerCubicMeter: EnergyDensity[A] = JoulesPerCubicMeter(a)
  }

  lazy val joulesPerCubicMeter: EnergyDensity[Int] = JoulesPerCubicMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, EnergyDensity] = EnergyDensityNumeric[A]()
  private case class EnergyDensityNumeric[A: Numeric]() extends QuantityNumeric[A, EnergyDensity](this) {
    override def times(x: Quantity[A, EnergyDensity], y: Quantity[A, EnergyDensity]): Quantity[A, EnergyDensity] =
      JoulesPerCubicMeter(x.to(JoulesPerCubicMeter) * y.to(JoulesPerCubicMeter))
  }
}

abstract class EnergyDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[EnergyDensity] {
  override def dimension: Dimension[EnergyDensity] = EnergyDensity
  override def apply[A: Numeric](value: A): EnergyDensity[A] = EnergyDensity(value, this)
}

case object JoulesPerCubicMeter extends EnergyDensityUnit("J/m³", 1) with PrimaryUnit[EnergyDensity] with SiUnit[EnergyDensity]
