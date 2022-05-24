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

final case class PowerDensity[A: Numeric] private [squants2]  (value: A, unit: PowerDensityUnit)
  extends Quantity[A, PowerDensity.type] {
  override type Q[B] = PowerDensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWattsPerCubicMeter: A = to(WattsPerCubicMeter)
}

object PowerDensity extends Dimension("Power Density") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerCubicMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(WattsPerCubicMeter)

  implicit class PowerDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerCubicMeter: PowerDensity[A] = WattsPerCubicMeter(a)
  }

  lazy val wattsPerCubicMeter: PowerDensity[Int] = WattsPerCubicMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PowerDensityNumeric[A]()
  private case class PowerDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, PowerDensity.type], y: Quantity[A, PowerDensity.type]): Quantity[A, PowerDensity.this.type] =
      WattsPerCubicMeter(x.to(WattsPerCubicMeter) * y.to(WattsPerCubicMeter))
  }
}

abstract class PowerDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[PowerDensity.type] {
  override def dimension: PowerDensity.type = PowerDensity
  override def apply[A: Numeric](value: A): PowerDensity[A] = PowerDensity(value, this)
}

case object WattsPerCubicMeter extends PowerDensityUnit("W/m³", 1) with PrimaryUnit with SiUnit
