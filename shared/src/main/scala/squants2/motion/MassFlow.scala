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

final case class MassFlow[A: Numeric] private[squants2] (value: A, unit: MassFlowUnit)
  extends Quantity[A, MassFlow] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  // END CUSTOM OPS

  def toPoundsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](PoundsPerHour)
  def toKilopoundsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](KilopoundsPerHour)
  def toPoundsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](PoundsPerSecond)
  def toKilogramsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerSecond)
  def toMegapoundsPerHour[B: Numeric](implicit f: A => B): B = toNum[B](MegapoundsPerHour)
}

object MassFlow extends Dimension[MassFlow]("Mass Flow") {

  override def primaryUnit: UnitOfMeasure[MassFlow] with PrimaryUnit[MassFlow] = KilogramsPerSecond
  override def siUnit: UnitOfMeasure[MassFlow] with SiUnit[MassFlow] = KilogramsPerSecond
  override lazy val units: Set[UnitOfMeasure[MassFlow]] = 
    Set(PoundsPerHour, KilopoundsPerHour, PoundsPerSecond, KilogramsPerSecond, MegapoundsPerHour)

  implicit class MassFlowCons[A](a: A)(implicit num: Numeric[A]) {
    def poundsPerHour: MassFlow[A] = PoundsPerHour(a)
    def kilopoundsPerHour: MassFlow[A] = KilopoundsPerHour(a)
    def poundsPerSecond: MassFlow[A] = PoundsPerSecond(a)
    def kilogramsPerSecond: MassFlow[A] = KilogramsPerSecond(a)
    def megapoundsPerHour: MassFlow[A] = MegapoundsPerHour(a)
  }

  lazy val poundsPerHour: MassFlow[Int] = PoundsPerHour(1)
  lazy val kilopoundsPerHour: MassFlow[Int] = KilopoundsPerHour(1)
  lazy val poundsPerSecond: MassFlow[Int] = PoundsPerSecond(1)
  lazy val kilogramsPerSecond: MassFlow[Int] = KilogramsPerSecond(1)
  lazy val megapoundsPerHour: MassFlow[Int] = MegapoundsPerHour(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, MassFlow] = MassFlowNumeric[A]()
  private case class MassFlowNumeric[A: Numeric]() extends QuantityNumeric[A, MassFlow](this) {
    override def times(x: Quantity[A, MassFlow], y: Quantity[A, MassFlow]): Quantity[A, MassFlow] =
      KilogramsPerSecond(x.to(KilogramsPerSecond) * y.to(KilogramsPerSecond))
  }
}

abstract class MassFlowUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MassFlow] {
  override def dimension: Dimension[MassFlow] = MassFlow
  override def apply[A: Numeric](value: A): MassFlow[A] = MassFlow(value, this)
}

case object PoundsPerHour extends MassFlowUnit("lb/hr", 1.2599788055555556E-4)
case object KilopoundsPerHour extends MassFlowUnit("klb/hr", 0.12599788055555555)
case object PoundsPerSecond extends MassFlowUnit("lb/s", 0.45359237)
case object KilogramsPerSecond extends MassFlowUnit("kg/s", 1) with PrimaryUnit[MassFlow] with SiUnit[MassFlow]
case object MegapoundsPerHour extends MassFlowUnit("Mlb/hr", 125.99788055555557)
