/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Luminance[A: Numeric] private [squants2]  (value: A, unit: LuminanceUnit)
  extends Quantity[A, Luminance.type] {
  override type Q[B] = Luminance[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toCandelasPerSquareMeter: A = to(CandelasPerSquareMeter)
}

object Luminance extends Dimension("Luminance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CandelasPerSquareMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CandelasPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(CandelasPerSquareMeter)

  implicit class LuminanceCons[A](a: A)(implicit num: Numeric[A]) {
    def candelasPerSquareMeter: Luminance[A] = CandelasPerSquareMeter(a)
  }

  lazy val candelasPerSquareMeter: Luminance[Int] = CandelasPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LuminanceNumeric[A]()
  private case class LuminanceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Luminance.type], y: Quantity[A, Luminance.type]): Quantity[A, Luminance.this.type] =
      CandelasPerSquareMeter(x.to(CandelasPerSquareMeter) * y.to(CandelasPerSquareMeter))
  }
}

abstract class LuminanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Luminance.type] {
  override lazy val dimension: Luminance.type = Luminance
  override def apply[A: Numeric](value: A): Luminance[A] = Luminance(value, this)
}

case object CandelasPerSquareMeter extends LuminanceUnit("cd/m²", 1) with PrimaryUnit with SiUnit
