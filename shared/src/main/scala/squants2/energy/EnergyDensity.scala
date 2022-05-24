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

final case class EnergyDensity[A: Numeric] private [squants2]  (value: A, unit: EnergyDensityUnit)
  extends Quantity[A, EnergyDensity.type] {
  override type Q[B] = EnergyDensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toJoulesPerCubicMeter: A = to(JoulesPerCubicMeter)
}

object EnergyDensity extends Dimension("EnergyDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = JoulesPerCubicMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = JoulesPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(JoulesPerCubicMeter)

  implicit class EnergyDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def joulesPerCubicMeter: EnergyDensity[A] = JoulesPerCubicMeter(a)
  }

  lazy val joulesPerCubicMeter: EnergyDensity[Int] = JoulesPerCubicMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = EnergyDensityNumeric[A]()
  private case class EnergyDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, EnergyDensity.type], y: Quantity[A, EnergyDensity.type]): Quantity[A, EnergyDensity.this.type] =
      JoulesPerCubicMeter(x.to(JoulesPerCubicMeter) * y.to(JoulesPerCubicMeter))
  }
}

abstract class EnergyDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[EnergyDensity.type] {
  override lazy val dimension: EnergyDensity.type = EnergyDensity
  override def apply[A: Numeric](value: A): EnergyDensity[A] = EnergyDensity(value, this)
}

case object JoulesPerCubicMeter extends EnergyDensityUnit("J/m³", 1.0) with PrimaryUnit with SiUnit
