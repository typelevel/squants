/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.space

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class SolidAngle[A: Numeric] private [squants2]  (value: A, unit: SolidAngleUnit)
  extends Quantity[A, SolidAngle.type] {
  override type Q[B] = SolidAngle[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toSquaredRadians: A = to(SquaredRadians)
}

object SolidAngle extends Dimension("SolidAngle") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = SquaredRadians
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = SquaredRadians
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(SquaredRadians)

  implicit class SolidAngleCons[A](a: A)(implicit num: Numeric[A]) {
    def squaredRadians: SolidAngle[A] = SquaredRadians(a)
  }

  lazy val squaredRadians: SolidAngle[Int] = SquaredRadians(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = SolidAngleNumeric[A]()
  private case class SolidAngleNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, SolidAngle.type], y: Quantity[A, SolidAngle.type]): Quantity[A, SolidAngle.this.type] =
      SquaredRadians(x.to(SquaredRadians) * y.to(SquaredRadians))
  }
}

abstract class SolidAngleUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SolidAngle.type] {
  override lazy val dimension: SolidAngle.type = SolidAngle
  override def apply[A: Numeric](value: A): SolidAngle[A] = SolidAngle(value, this)
}

case object SquaredRadians extends SolidAngleUnit("sr", 1) with PrimaryUnit with SiUnit
