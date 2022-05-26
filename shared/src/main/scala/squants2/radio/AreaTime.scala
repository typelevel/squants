/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class AreaTime[A: Numeric] private[squants2] (value: A, unit: AreaTimeUnit)
  extends Quantity[A, AreaTime] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Time[B])(implicit f: B => A): Area[A] = ???
  // END CUSTOM OPS

  def toSquareCentimeterSeconds[B: Numeric](implicit f: A => B): B = toNum[B](SquareCentimeterSeconds)
  def toSquareMeterSeconds[B: Numeric](implicit f: A => B): B = toNum[B](SquareMeterSeconds)
}

object AreaTime extends Dimension[AreaTime]("Area Time") {

  override def primaryUnit: UnitOfMeasure[AreaTime] with PrimaryUnit[AreaTime] = SquareMeterSeconds
  override def siUnit: UnitOfMeasure[AreaTime] with SiUnit[AreaTime] = SquareMeterSeconds
  override lazy val units: Set[UnitOfMeasure[AreaTime]] = 
    Set(SquareCentimeterSeconds, SquareMeterSeconds)

  implicit class AreaTimeCons[A](a: A)(implicit num: Numeric[A]) {
    def squareCentimeterSeconds: AreaTime[A] = SquareCentimeterSeconds(a)
    def squareMeterSeconds: AreaTime[A] = SquareMeterSeconds(a)
  }

  lazy val squareCentimeterSeconds: AreaTime[Int] = SquareCentimeterSeconds(1)
  lazy val squareMeterSeconds: AreaTime[Int] = SquareMeterSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, AreaTime] = AreaTimeNumeric[A]()
  private case class AreaTimeNumeric[A: Numeric]() extends QuantityNumeric[A, AreaTime](this) {
    override def times(x: Quantity[A, AreaTime], y: Quantity[A, AreaTime]): Quantity[A, AreaTime] =
      SquareMeterSeconds(x.to(SquareMeterSeconds) * y.to(SquareMeterSeconds))
  }
}

abstract class AreaTimeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AreaTime] {
  override def dimension: Dimension[AreaTime] = AreaTime
  override def apply[A: Numeric](value: A): AreaTime[A] = AreaTime(value, this)
}

case object SquareCentimeterSeconds extends AreaTimeUnit("cm²‧s", 1.0E-4)
case object SquareMeterSeconds extends AreaTimeUnit("m²‧s", 1) with PrimaryUnit[AreaTime] with SiUnit[AreaTime]
