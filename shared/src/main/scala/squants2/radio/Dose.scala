/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class Dose[A: Numeric] private[squants2] (value: A, unit: DoseUnit)
  extends Quantity[A, Dose] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Mass[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: Time[B])(implicit f: B => A): Nothing$ = ???
  // END CUSTOM OPS

  def toRems[B: Numeric](implicit f: A => B): B = toNum[B](Rems)
  def toSieverts[B: Numeric](implicit f: A => B): B = toNum[B](Sieverts)
}

object Dose extends Dimension[Dose]("Dose") {

  override def primaryUnit: UnitOfMeasure[Dose] with PrimaryUnit[Dose] = Sieverts
  override def siUnit: UnitOfMeasure[Dose] with SiUnit[Dose] = Sieverts
  override lazy val units: Set[UnitOfMeasure[Dose]] = 
    Set(Rems, Sieverts)

  implicit class DoseCons[A](a: A)(implicit num: Numeric[A]) {
    def rems: Dose[A] = Rems(a)
    def sieverts: Dose[A] = Sieverts(a)
  }

  lazy val rem: Dose[Int] = Rems(1)
  lazy val sievert: Dose[Int] = Sieverts(1)

}

abstract class DoseUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Dose] {
  override def dimension: Dimension[Dose] = Dose
  override def apply[A: Numeric](value: A): Dose[A] = Dose(value, this)
}

case object Rems extends DoseUnit("rem", MetricSystem.Centi)
case object Sieverts extends DoseUnit("Sv", 1) with PrimaryUnit[Dose] with SiUnit[Dose]
