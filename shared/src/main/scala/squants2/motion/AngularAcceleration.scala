/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import squants2.time._

final case class AngularAcceleration[A: Numeric] private[squants2] (value: A, unit: AngularAccelerationUnit)
  extends Quantity[A, AngularAcceleration] with TimeDerivative[A, AngularVelocity] {

  override protected[squants2] def timeIntegrated: AngularVelocity[A] = RadiansPerSecond(num.one)
  override protected[squants2] def derivativeTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def onRadius[B](radius: Length[B])(implicit f: B => A): Acceleration[A] = ???
  //  def *[B](that: MomentOfInertia[B])(implicit f: B => A): Torque[A] = ???
  // END CUSTOM OPS

  def toArcsecondsPerSecondSquared[B: Numeric](implicit f: A => B): B = toNum[B](ArcsecondsPerSecondSquared)
  def toArcminutesPerSecondSquared[B: Numeric](implicit f: A => B): B = toNum[B](ArcminutesPerSecondSquared)
  def toGradiansPerSecondSquared[B: Numeric](implicit f: A => B): B = toNum[B](GradiansPerSecondSquared)
  def toDegreesPerSecondSquared[B: Numeric](implicit f: A => B): B = toNum[B](DegreesPerSecondSquared)
  def toRadiansPerSecondSquared[B: Numeric](implicit f: A => B): B = toNum[B](RadiansPerSecondSquared)
  def toTurnsPerSecondSquared[B: Numeric](implicit f: A => B): B = toNum[B](TurnsPerSecondSquared)
}

object AngularAcceleration extends Dimension[AngularAcceleration]("Angular Acceleration") {

  override def primaryUnit: UnitOfMeasure[AngularAcceleration] with PrimaryUnit[AngularAcceleration] = RadiansPerSecondSquared
  override def siUnit: UnitOfMeasure[AngularAcceleration] with SiUnit[AngularAcceleration] = RadiansPerSecondSquared
  override lazy val units: Set[UnitOfMeasure[AngularAcceleration]] = 
    Set(ArcsecondsPerSecondSquared, ArcminutesPerSecondSquared, GradiansPerSecondSquared, DegreesPerSecondSquared, RadiansPerSecondSquared, TurnsPerSecondSquared)

  implicit class AngularAccelerationCons[A](a: A)(implicit num: Numeric[A]) {
    def arcsecondsPerSecondSquared: AngularAcceleration[A] = ArcsecondsPerSecondSquared(a)
    def arcminutesPerSecondSquared: AngularAcceleration[A] = ArcminutesPerSecondSquared(a)
    def gradiansPerSecondSquared: AngularAcceleration[A] = GradiansPerSecondSquared(a)
    def degreesPerSecondSquared: AngularAcceleration[A] = DegreesPerSecondSquared(a)
    def radiansPerSecondSquared: AngularAcceleration[A] = RadiansPerSecondSquared(a)
    def turnsPerSecondSquared: AngularAcceleration[A] = TurnsPerSecondSquared(a)
  }

  lazy val arcsecondPerSecondSquared: AngularAcceleration[Int] = ArcsecondsPerSecondSquared(1)
  lazy val arcminutePerSecondSquared: AngularAcceleration[Int] = ArcminutesPerSecondSquared(1)
  lazy val gradianPerSecondSquared: AngularAcceleration[Int] = GradiansPerSecondSquared(1)
  lazy val degreePerSecondSquared: AngularAcceleration[Int] = DegreesPerSecondSquared(1)
  lazy val radianPerSecondSquared: AngularAcceleration[Int] = RadiansPerSecondSquared(1)
  lazy val turnPerSecondSquared: AngularAcceleration[Int] = TurnsPerSecondSquared(1)

}

abstract class AngularAccelerationUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AngularAcceleration] {
  override def dimension: Dimension[AngularAcceleration] = AngularAcceleration
  override def apply[A: Numeric](value: A): AngularAcceleration[A] = AngularAcceleration(value, this)
}

case object ArcsecondsPerSecondSquared extends AngularAccelerationUnit("asec/s²", 4.84813681109536E-6)
case object ArcminutesPerSecondSquared extends AngularAccelerationUnit("amin/s²", 2.908882086657216E-4)
case object GradiansPerSecondSquared extends AngularAccelerationUnit("grad/s²", 0.015707963267948967)
case object DegreesPerSecondSquared extends AngularAccelerationUnit("°/s²", 0.017453292519943295)
case object RadiansPerSecondSquared extends AngularAccelerationUnit("rad/s²", 1) with PrimaryUnit[AngularAcceleration] with SiUnit[AngularAcceleration]
case object TurnsPerSecondSquared extends AngularAccelerationUnit("turns/s²", 6.283185307179586)
