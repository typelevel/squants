/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2

import scala.math.Numeric.Implicits.infixNumericOps

final case class Dimensionless[A: Numeric] private [squants2]  (value: A, unit: DimensionlessUnit)
  extends Quantity[A, Dimensionless.type] {
  override type Q[B] = Dimensionless[B]

  // BEGIN CUSTOM OPS
  def *[B](that: Dimensionless[B])(implicit f: B => A): Dimensionless[A] = Each(to(Each) * f(that.to(Each)))
  def *[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = that.asNum[A] * to(Each)

  def +[B](that: B)(implicit f: B => A): Dimensionless[A] = Each(to(Each) + f(that))
  // END CUSTOM OPS

  def toPercent: A = to(Percent)
  def toEach: A = to(Each)
  def toDozen: A = to(Dozen)
  def toScore: A = to(Score)
  def toGross: A = to(Gross)
}

object Dimensionless extends Dimension("Dimensionless") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Each
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Each
  override lazy val units: Set[UnitOfMeasure[this.type]] =
    Set(Percent, Each, Dozen, Score, Gross)

  implicit class DimensionlessCons[A](a: A)(implicit num: Numeric[A]) {
    def percent: Dimensionless[A] = Percent(a)
    def each: Dimensionless[A] = Each(a)
    def dozen: Dimensionless[A] = Dozen(a)
    def score: Dimensionless[A] = Score(a)
    def gross: Dimensionless[A] = Gross(a)
    def hundred: Dimensionless[A] = Each(a * num.fromInt(100))
    def thousand: Dimensionless[A] = Each(a * num.fromInt(1000))
    def million: Dimensionless[A] = Each(a * num.fromInt(1000000))
  }

  lazy val percent: Dimensionless[Int] = Percent(1)
  lazy val each: Dimensionless[Int] = Each(1)
  lazy val dozen: Dimensionless[Int] = Dozen(1)
  lazy val score: Dimensionless[Int] = Score(1)
  lazy val gross: Dimensionless[Int] = Gross(1)
  lazy val hundred: Dimensionless[Int] = Each(100)
  lazy val thousand: Dimensionless[Int] = Each(1000)
  lazy val million: Dimensionless[Int] = Each(1000000)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = DimensionlessNumeric[A]()
  private case class DimensionlessNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Dimensionless.type], y: Quantity[A, Dimensionless.type]): Quantity[A, Dimensionless.this.type] =
      Each(x.to(Each) * y.to(Each))
  }
}

abstract class DimensionlessUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Dimensionless.type] {
  override lazy val dimension: Dimensionless.type = Dimensionless
  override def apply[A: Numeric](value: A): Dimensionless[A] = Dimensionless(value, this)
}

case object Percent extends DimensionlessUnit("%", 0.01)
case object Each extends DimensionlessUnit("ea", 1) with PrimaryUnit with SiUnit
case object Dozen extends DimensionlessUnit("dz", 12)
case object Score extends DimensionlessUnit("score", 20)
case object Gross extends DimensionlessUnit("gr", 144)
