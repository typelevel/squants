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

final case class ElectricCurrentDensity[A: Numeric] private [squants2]  (value: A, unit: ElectricCurrentDensityUnit)
  extends Quantity[A, ElectricCurrentDensity.type] {
  override type Q[B] = ElectricCurrentDensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toAmperesPerSquareMeter: A = to(AmperesPerSquareMeter)
}

object ElectricCurrentDensity extends Dimension("ElectricCurrentDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = AmperesPerSquareMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = AmperesPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(AmperesPerSquareMeter)

  implicit class ElectricCurrentDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def amperesPerSquareMeter: ElectricCurrentDensity[A] = AmperesPerSquareMeter(a)
  }

  lazy val amperesPerSquareMeter: ElectricCurrentDensity[Int] = AmperesPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricCurrentDensityNumeric[A]()
  private case class ElectricCurrentDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricCurrentDensity.type], y: Quantity[A, ElectricCurrentDensity.type]): Quantity[A, ElectricCurrentDensity.this.type] =
      AmperesPerSquareMeter(x.to(AmperesPerSquareMeter) * y.to(AmperesPerSquareMeter))
  }
}

abstract class ElectricCurrentDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricCurrentDensity.type] {
  override lazy val dimension: ElectricCurrentDensity.type = ElectricCurrentDensity
  override def apply[A: Numeric](value: A): ElectricCurrentDensity[A] = ElectricCurrentDensity(value, this)
}

case object AmperesPerSquareMeter extends ElectricCurrentDensityUnit("A/m²", 1.0) with PrimaryUnit with SiUnit
