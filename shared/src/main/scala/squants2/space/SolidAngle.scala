/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.space

import squants2._

final case class SolidAngle[A: Numeric] private[squants2] (value: A, unit: SolidAngleUnit)
  extends Quantity[A, SolidAngle] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: LuminousIntensity[B])(implicit f: B => A): LuminousFlux[A] = ???
  //  def *[B](that: RadiantIntensity[B])(implicit f: B => A): Power[A] = ???
  // END CUSTOM OPS

  def toSquaredRadians[B: Numeric](implicit f: A => B): B = toNum[B](SquaredRadians)
}

object SolidAngle extends Dimension[SolidAngle]("Solid Angle") {

  override def primaryUnit: UnitOfMeasure[SolidAngle] with PrimaryUnit[SolidAngle] = SquaredRadians
  override def siUnit: UnitOfMeasure[SolidAngle] with SiUnit[SolidAngle] = SquaredRadians
  override lazy val units: Set[UnitOfMeasure[SolidAngle]] = 
    Set(SquaredRadians)

  implicit class SolidAngleCons[A](a: A)(implicit num: Numeric[A]) {
    def squaredRadians: SolidAngle[A] = SquaredRadians(a)
  }

  lazy val squaredRadian: SolidAngle[Int] = SquaredRadians(1)

}

abstract class SolidAngleUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SolidAngle] {
  override def dimension: Dimension[SolidAngle] = SolidAngle
  override def apply[A: Numeric](value: A): SolidAngle[A] = SolidAngle(value, this)
}

case object SquaredRadians extends SolidAngleUnit("sr", 1) with PrimaryUnit[SolidAngle] with SiUnit[SolidAngle]
