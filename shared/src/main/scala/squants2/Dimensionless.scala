package squants2

import squants2.QNumeric.QNumericOps

final case class Dimensionless[A: QNumeric] private[squants2] (value: A, unit: DimensionlessUnit) extends Quantity[A, Dimensionless.type] {
  override type Q[B] = Dimensionless[B]

  def *[B](that: Dimensionless[B])(implicit f: B => A): Dimensionless[A] = Each(to(Each) * that.to(Each))
  def *[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = that.asNum[A] * to(Each)

  def +[B](that: B)(implicit f: B => A): Dimensionless[A] = Each(to(Each) + that)

  def toPercent: A = to(Percent)
  def toEach: A = to(Each)
  def toDozen: A = to(Dozen)
  def toScore: A = to(Score)
  def toGross: A = to(Gross)
}

object Dimensionless extends Dimension("Dimensionless") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Each
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Each
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Each, Percent, Dozen, Score, Gross)

  // Constructors from QNumeric values
  implicit class DimensionlessCons[A](a: A)(implicit num: QNumeric[A]) {
    def percent: Dimensionless[A] = Percent(a)
    def each: Dimensionless[A] = Each(a)
    def dozen: Dimensionless[A] = Dozen(a)
    def score: Dimensionless[A] = Score(a)
    def gross: Dimensionless[A] = Gross(a)
    def hundred: Dimensionless[A] = Each(a * num.fromDouble(1e2))
    def thousand: Dimensionless[A] = Each(a * num.fromDouble(1e3))
    def million: Dimensionless[A] = Each(a * num.fromDouble(1e6))
  }

  // Constants
  lazy val percent: Dimensionless[Int] = Percent(1)
  lazy val each: Dimensionless[Int] = Each(1)
  lazy val dozen: Dimensionless[Int] = Dozen(1)
  lazy val score: Dimensionless[Int] = Score(1)
  lazy val gross: Dimensionless[Int] = Gross(1)
  lazy val hundred: Dimensionless[Int] = Each(100)
  lazy val thousand: Dimensionless[Int] = Each(1000)
  lazy val million: Dimensionless[Int] = Each(1000000)

}

abstract class DimensionlessUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[Dimensionless.type] {
  override lazy val dimension: Dimensionless.type = Dimensionless
  override def apply[A: QNumeric](value: A): Dimensionless[A] = Dimensionless(value, this)
}

case object Each extends DimensionlessUnit("ea", 1) with PrimaryUnit with SiUnit
case object Percent extends DimensionlessUnit("%", 1e-2)
case object Dozen extends DimensionlessUnit("dz", 12d)
case object Score extends DimensionlessUnit("score", 20d)
case object Gross extends DimensionlessUnit("gr", 144d)
