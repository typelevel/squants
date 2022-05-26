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

final case class Momentum[A: Numeric] private[squants2] (value: A, unit: MomentumUnit)
  extends Quantity[A, Momentum] {

  // BEGIN CUSTOM OPS

  //  def per[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  //  def /[B](that: Velocity[B])(implicit f: B => A): Mass[A] = ???
  //  def /[B](that: Mass[B])(implicit f: B => A): Velocity[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): Yank[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  // END CUSTOM OPS

  def toNewtonSeconds[B: Numeric](implicit f: A => B): B = toNum[B](NewtonSeconds)
}

object Momentum extends Dimension[Momentum]("Momentum") {

  override def primaryUnit: UnitOfMeasure[Momentum] with PrimaryUnit[Momentum] = NewtonSeconds
  override def siUnit: UnitOfMeasure[Momentum] with SiUnit[Momentum] = NewtonSeconds
  override lazy val units: Set[UnitOfMeasure[Momentum]] = 
    Set(NewtonSeconds)

  implicit class MomentumCons[A](a: A)(implicit num: Numeric[A]) {
    def newtonSeconds: Momentum[A] = NewtonSeconds(a)
  }

  lazy val newtonSeconds: Momentum[Int] = NewtonSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Momentum] = MomentumNumeric[A]()
  private case class MomentumNumeric[A: Numeric]() extends QuantityNumeric[A, Momentum](this) {
    override def times(x: Quantity[A, Momentum], y: Quantity[A, Momentum]): Quantity[A, Momentum] =
      NewtonSeconds(x.to(NewtonSeconds) * y.to(NewtonSeconds))
  }
}

abstract class MomentumUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Momentum] {
  override def dimension: Dimension[Momentum] = Momentum
  override def apply[A: Numeric](value: A): Momentum[A] = Momentum(value, this)
}

case object NewtonSeconds extends MomentumUnit("Ns", 1) with PrimaryUnit[Momentum] with SiUnit[Momentum]
