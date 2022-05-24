/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Momentum[A: Numeric] private [squants2]  (value: A, unit: MomentumUnit)
  extends Quantity[A, Momentum.type] {
  override type Q[B] = Momentum[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toNewtonSeconds: A = to(NewtonSeconds)
}

object Momentum extends Dimension("Momentum") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = NewtonSeconds
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = NewtonSeconds
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(NewtonSeconds)

  implicit class MomentumCons[A](a: A)(implicit num: Numeric[A]) {
    def newtonSeconds: Momentum[A] = NewtonSeconds(a)
  }

  lazy val newtonSeconds: Momentum[Int] = NewtonSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = MomentumNumeric[A]()
  private case class MomentumNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Momentum.type], y: Quantity[A, Momentum.type]): Quantity[A, Momentum.this.type] =
      NewtonSeconds(x.to(NewtonSeconds) * y.to(NewtonSeconds))
  }
}

abstract class MomentumUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Momentum.type] {
  override def dimension: Momentum.type = Momentum
  override def apply[A: Numeric](value: A): Momentum[A] = Momentum(value, this)
}

case object NewtonSeconds extends MomentumUnit("Ns", 1) with PrimaryUnit with SiUnit
