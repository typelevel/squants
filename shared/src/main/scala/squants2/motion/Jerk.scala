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

final case class Jerk[A: Numeric] private [squants2]  (value: A, unit: JerkUnit)
  extends Quantity[A, Jerk.type] {
  override type Q[B] = Jerk[B]

  // BEGIN CUSTOM OPS
  //  def *[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): Velocity[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeIntegral[A] = ???
  // END CUSTOM OPS

  def toFeetPerSecondCubed: A = to(FeetPerSecondCubed)
  def toMetersPerSecondCubed: A = to(MetersPerSecondCubed)
}

object Jerk extends Dimension("Jerk") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = MetersPerSecondCubed
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = MetersPerSecondCubed
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(FeetPerSecondCubed, MetersPerSecondCubed)

  implicit class JerkCons[A](a: A)(implicit num: Numeric[A]) {
    def feetPerSecondCubed: Jerk[A] = FeetPerSecondCubed(a)
    def metersPerSecondCubed: Jerk[A] = MetersPerSecondCubed(a)
  }

  lazy val feetPerSecondCubed: Jerk[Int] = FeetPerSecondCubed(1)
  lazy val metersPerSecondCubed: Jerk[Int] = MetersPerSecondCubed(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = JerkNumeric[A]()
  private case class JerkNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Jerk.type], y: Quantity[A, Jerk.type]): Quantity[A, Jerk.this.type] =
      MetersPerSecondCubed(x.to(MetersPerSecondCubed) * y.to(MetersPerSecondCubed))
  }
}

abstract class JerkUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Jerk.type] {
  override def dimension: Jerk.type = Jerk
  override def apply[A: Numeric](value: A): Jerk[A] = Jerk(value, this)
}

case object FeetPerSecondCubed extends JerkUnit("ft/s³", 0.3048006096)
case object MetersPerSecondCubed extends JerkUnit("m/s³", 1) with PrimaryUnit with SiUnit
