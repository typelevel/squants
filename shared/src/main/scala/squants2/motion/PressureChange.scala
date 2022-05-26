/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._

final case class PressureChange[A: Numeric] private[squants2] (value: A, unit: PressureChangeUnit)
  extends Quantity[A, PressureChange] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  // END CUSTOM OPS

  def toPascalsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](PascalsPerSecond)
  def toPoundsPerSquareInchPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](PoundsPerSquareInchPerSecond)
  def toBarsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](BarsPerSecond)
  def toStandardAtmospheresPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](StandardAtmospheresPerSecond)
}

object PressureChange extends Dimension[PressureChange]("Pressure Change") {

  override def primaryUnit: UnitOfMeasure[PressureChange] with PrimaryUnit[PressureChange] = PascalsPerSecond
  override def siUnit: UnitOfMeasure[PressureChange] with SiUnit[PressureChange] = PascalsPerSecond
  override lazy val units: Set[UnitOfMeasure[PressureChange]] = 
    Set(PascalsPerSecond, PoundsPerSquareInchPerSecond, BarsPerSecond, StandardAtmospheresPerSecond)

  implicit class PressureChangeCons[A](a: A)(implicit num: Numeric[A]) {
    def pascalsPerSecond: PressureChange[A] = PascalsPerSecond(a)
    def poundsPerSquareInchPerSecond: PressureChange[A] = PoundsPerSquareInchPerSecond(a)
    def barsPerSecond: PressureChange[A] = BarsPerSecond(a)
    def standardAtmospheresPerSecond: PressureChange[A] = StandardAtmospheresPerSecond(a)
  }

  lazy val pascalsPerSecond: PressureChange[Int] = PascalsPerSecond(1)
  lazy val poundsPerSquareInchPerSecond: PressureChange[Int] = PoundsPerSquareInchPerSecond(1)
  lazy val barsPerSecond: PressureChange[Int] = BarsPerSecond(1)
  lazy val standardAtmospheresPerSecond: PressureChange[Int] = StandardAtmospheresPerSecond(1)

}

abstract class PressureChangeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[PressureChange] {
  override def dimension: Dimension[PressureChange] = PressureChange
  override def apply[A: Numeric](value: A): PressureChange[A] = PressureChange(value, this)
}

case object PascalsPerSecond extends PressureChangeUnit("Pa/s", 1) with PrimaryUnit[PressureChange] with SiUnit[PressureChange]
case object PoundsPerSquareInchPerSecond extends PressureChangeUnit("psi/s", 6894.757293168361)
case object BarsPerSecond extends PressureChangeUnit("bar/s", 100000)
case object StandardAtmospheresPerSecond extends PressureChangeUnit("atm/s", 101325)
