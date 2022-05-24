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

final case class AreaTime[A: Numeric] private [squants2]  (value: A, unit: AreaTimeUnit)
  extends Quantity[A, AreaTime.type] {
  override type Q[B] = AreaTime[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toSquareCentimeterSeconds: A = to(SquareCentimeterSeconds)
  def toSquareMeterSeconds: A = to(SquareMeterSeconds)
}

object AreaTime extends Dimension("Area Time") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = SquareMeterSeconds
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = SquareMeterSeconds
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(SquareCentimeterSeconds, SquareMeterSeconds)

  implicit class AreaTimeCons[A](a: A)(implicit num: Numeric[A]) {
    def squareCentimeterSeconds: AreaTime[A] = SquareCentimeterSeconds(a)
    def squareMeterSeconds: AreaTime[A] = SquareMeterSeconds(a)
  }

  lazy val squareCentimeterSeconds: AreaTime[Int] = SquareCentimeterSeconds(1)
  lazy val squareMeterSeconds: AreaTime[Int] = SquareMeterSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AreaTimeNumeric[A]()
  private case class AreaTimeNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, AreaTime.type], y: Quantity[A, AreaTime.type]): Quantity[A, AreaTime.this.type] =
      SquareMeterSeconds(x.to(SquareMeterSeconds) * y.to(SquareMeterSeconds))
  }
}

abstract class AreaTimeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AreaTime.type] {
  override lazy val dimension: AreaTime.type = AreaTime
  override def apply[A: Numeric](value: A): AreaTime[A] = AreaTime(value, this)
}

case object SquareCentimeterSeconds extends AreaTimeUnit("cm²‧s", 1.0E-4)
case object SquareMeterSeconds extends AreaTimeUnit("m²‧s", 1) with PrimaryUnit with SiUnit
