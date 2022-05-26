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

final case class Jerk[A: Numeric] private[squants2] (value: A, unit: JerkUnit)
  extends Quantity[A, Jerk] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): Velocity[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeIntegral[A] = ???
  // END CUSTOM OPS

  def toFeetPerSecondCubed[B: Numeric](implicit f: A => B): B = toNum[B](FeetPerSecondCubed)
  def toMetersPerSecondCubed[B: Numeric](implicit f: A => B): B = toNum[B](MetersPerSecondCubed)
}

object Jerk extends Dimension[Jerk]("Jerk") {

  override def primaryUnit: UnitOfMeasure[Jerk] with PrimaryUnit[Jerk] = MetersPerSecondCubed
  override def siUnit: UnitOfMeasure[Jerk] with SiUnit[Jerk] = MetersPerSecondCubed
  override lazy val units: Set[UnitOfMeasure[Jerk]] = 
    Set(FeetPerSecondCubed, MetersPerSecondCubed)

  implicit class JerkCons[A](a: A)(implicit num: Numeric[A]) {
    def feetPerSecondCubed: Jerk[A] = FeetPerSecondCubed(a)
    def metersPerSecondCubed: Jerk[A] = MetersPerSecondCubed(a)
  }

  lazy val feetPerSecondCubed: Jerk[Int] = FeetPerSecondCubed(1)
  lazy val metersPerSecondCubed: Jerk[Int] = MetersPerSecondCubed(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Jerk] = JerkNumeric[A]()
  private case class JerkNumeric[A: Numeric]() extends QuantityNumeric[A, Jerk](this) {
    override def times(x: Quantity[A, Jerk], y: Quantity[A, Jerk]): Quantity[A, Jerk] =
      MetersPerSecondCubed(x.to(MetersPerSecondCubed) * y.to(MetersPerSecondCubed))
  }
}

abstract class JerkUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Jerk] {
  override def dimension: Dimension[Jerk] = Jerk
  override def apply[A: Numeric](value: A): Jerk[A] = Jerk(value, this)
}

case object FeetPerSecondCubed extends JerkUnit("ft/s³", 0.3048006096)
case object MetersPerSecondCubed extends JerkUnit("m/s³", 1) with PrimaryUnit[Jerk] with SiUnit[Jerk]
