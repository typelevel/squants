/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._

final case class AngularVelocity[A: Numeric] private[squants2] (value: A, unit: AngularVelocityUnit)
  extends Quantity[A, AngularVelocity] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def onRadius[B](radius: Length[B])(implicit f: B => A): Velocity[A] = ???
  // END CUSTOM OPS

  def toGradiansPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](GradiansPerSecond)
  def toDegreesPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](DegreesPerSecond)
  def toRadiansPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](RadiansPerSecond)
  def toTurnsPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](TurnsPerSecond)
}

object AngularVelocity extends Dimension[AngularVelocity]("Angular Velocity") {

  override def primaryUnit: UnitOfMeasure[AngularVelocity] with PrimaryUnit[AngularVelocity] = RadiansPerSecond
  override def siUnit: UnitOfMeasure[AngularVelocity] with SiUnit[AngularVelocity] = RadiansPerSecond
  override lazy val units: Set[UnitOfMeasure[AngularVelocity]] = 
    Set(GradiansPerSecond, DegreesPerSecond, RadiansPerSecond, TurnsPerSecond)

  implicit class AngularVelocityCons[A](a: A)(implicit num: Numeric[A]) {
    def gradiansPerSecond: AngularVelocity[A] = GradiansPerSecond(a)
    def degreesPerSecond: AngularVelocity[A] = DegreesPerSecond(a)
    def radiansPerSecond: AngularVelocity[A] = RadiansPerSecond(a)
    def turnsPerSecond: AngularVelocity[A] = TurnsPerSecond(a)
  }

  lazy val gradiansPerSecond: AngularVelocity[Int] = GradiansPerSecond(1)
  lazy val degreesPerSecond: AngularVelocity[Int] = DegreesPerSecond(1)
  lazy val radiansPerSecond: AngularVelocity[Int] = RadiansPerSecond(1)
  lazy val turnsPerSecond: AngularVelocity[Int] = TurnsPerSecond(1)

}

abstract class AngularVelocityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AngularVelocity] {
  override def dimension: Dimension[AngularVelocity] = AngularVelocity
  override def apply[A: Numeric](value: A): AngularVelocity[A] = AngularVelocity(value, this)
}

case object GradiansPerSecond extends AngularVelocityUnit("grad/s", 0.015707963267948967)
case object DegreesPerSecond extends AngularVelocityUnit("°/s", 0.017453292519943295)
case object RadiansPerSecond extends AngularVelocityUnit("rad/s", 1) with PrimaryUnit[AngularVelocity] with SiUnit[AngularVelocity]
case object TurnsPerSecond extends AngularVelocityUnit("turns/s", 6.283185307179586)
