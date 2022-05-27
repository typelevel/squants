/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2

import squants2.time.{ Frequency, Hertz, Seconds, Time, TimeIntegral }

import scala.math.Numeric.Implicits.infixNumericOps

final case class Dimensionless[A: Numeric] private[squants2] (value: A, unit: DimensionlessUnit)
  extends Quantity[A, Dimensionless] with TimeIntegral[A, Frequency] {

  // BEGIN CUSTOM OPS

  def *[B](that: Dimensionless[B])(implicit f: B => A): Dimensionless[A] = Each(to(Each) * that.toNum[A](Each))
  def *[B, Q[N] <: Quantity[N, Q]](that: Q[B])(implicit f: B => A): Q[A] = that.asNum[A] * to(Each)
  def +[B](that: B)(implicit f: B => A): Dimensionless[A] = Each(to(Each) + f(that))
  // END CUSTOM OPS


  override protected[squants2] def timeDerived: Frequency[A] with Quantity[A, Frequency] = Hertz(num.one)
  override protected[squants2] def time: Time[A] = Seconds(num.one)

  def toPercent[B: Numeric](implicit f: A => B): B = toNum[B](Percent)
  def toEach[B: Numeric](implicit f: A => B): B = toNum[B](Each)
  def toDozen[B: Numeric](implicit f: A => B): B = toNum[B](Dozen)
  def toScore[B: Numeric](implicit f: A => B): B = toNum[B](Score)
  def toGross[B: Numeric](implicit f: A => B): B = toNum[B](Gross)
}

object Dimensionless extends Dimension[Dimensionless]("Dimensionless") {

  override def primaryUnit: UnitOfMeasure[Dimensionless] with PrimaryUnit[Dimensionless] = Each
  override def siUnit: UnitOfMeasure[Dimensionless] with SiUnit[Dimensionless] = Each
  override lazy val units: Set[UnitOfMeasure[Dimensionless]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, Dimensionless] = new QuantityNumeric[A, Dimensionless](this) {
    override def times(x: Dimensionless[A], y: Dimensionless[A]): Dimensionless[A] = Each(x.to(Each) * y.to(Each))
  }
}

abstract class DimensionlessUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Dimensionless] {
  override def dimension: Dimension[Dimensionless] = Dimensionless
  override def apply[A: Numeric](value: A): Dimensionless[A] = Dimensionless(value, this)
}

case object Percent extends DimensionlessUnit("%", MetricSystem.Centi)
case object Each extends DimensionlessUnit("ea", 1) with PrimaryUnit[Dimensionless] with SiUnit[Dimensionless]
case object Dozen extends DimensionlessUnit("dz", 12)
case object Score extends DimensionlessUnit("score", 20)
case object Gross extends DimensionlessUnit("gr", 144)
