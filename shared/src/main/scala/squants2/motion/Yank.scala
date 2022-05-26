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

final case class Yank[A: Numeric] private[squants2] (value: A, unit: YankUnit)
  extends Quantity[A, Yank] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): Momentum[A] = ???
  //  def *[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeIntegral[A] = ???
  // END CUSTOM OPS

  def toNewtonsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](NewtonsPerSecond)
}

object Yank extends Dimension[Yank]("Yank") {

  override def primaryUnit: UnitOfMeasure[Yank] with PrimaryUnit[Yank] = NewtonsPerSecond
  override def siUnit: UnitOfMeasure[Yank] with SiUnit[Yank] = NewtonsPerSecond
  override lazy val units: Set[UnitOfMeasure[Yank]] = 
    Set(NewtonsPerSecond)

  implicit class YankCons[A](a: A)(implicit num: Numeric[A]) {
    def newtonsPerSecond: Yank[A] = NewtonsPerSecond(a)
  }

  lazy val newtonsPerSecond: Yank[Int] = NewtonsPerSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Yank] = YankNumeric[A]()
  private case class YankNumeric[A: Numeric]() extends QuantityNumeric[A, Yank](this) {
    override def times(x: Quantity[A, Yank], y: Quantity[A, Yank]): Quantity[A, Yank] =
      NewtonsPerSecond(x.to(NewtonsPerSecond) * y.to(NewtonsPerSecond))
  }
}

abstract class YankUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Yank] {
  override def dimension: Dimension[Yank] = Yank
  override def apply[A: Numeric](value: A): Yank[A] = Yank(value, this)
}

case object NewtonsPerSecond extends YankUnit("N/s", 1) with PrimaryUnit[Yank] with SiUnit[Yank]
