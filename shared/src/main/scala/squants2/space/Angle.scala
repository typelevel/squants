/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.space

import squants2._
import squants2.motion._
import squants2.time._

final case class Angle[A: Numeric] private[squants2] (value: A, unit: AngleUnit)
  extends Quantity[A, Angle] with TimeIntegral[A, AngularVelocity] {

  override protected[squants2] def timeDerived: AngularVelocity[A] = RadiansPerSecond(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def sin[B]()(implicit f: B => A): A = ???
  //  def cos[B]()(implicit f: B => A): A = ???
  //  def tan[B]()(implicit f: B => A): A = ???
  //  def asin[B]()(implicit f: B => A): A = ???
  //  def acos[B]()(implicit f: B => A): A = ???
  //  def onRadius[B](radius: Length[B])(implicit f: B => A): Length[A] = ???
  // END CUSTOM OPS

  def toArcseconds[B: Numeric](implicit f: A => B): B = toNum[B](Arcseconds)
  def toArcminutes[B: Numeric](implicit f: A => B): B = toNum[B](Arcminutes)
  def toGradians[B: Numeric](implicit f: A => B): B = toNum[B](Gradians)
  def toDegrees[B: Numeric](implicit f: A => B): B = toNum[B](Degrees)
  def toRadians[B: Numeric](implicit f: A => B): B = toNum[B](Radians)
  def toTurns[B: Numeric](implicit f: A => B): B = toNum[B](Turns)
}

object Angle extends Dimension[Angle]("Angle") {

  override def primaryUnit: UnitOfMeasure[Angle] with PrimaryUnit[Angle] = Radians
  override def siUnit: UnitOfMeasure[Angle] with SiUnit[Angle] = Radians
  override lazy val units: Set[UnitOfMeasure[Angle]] = 
    Set(Arcseconds, Arcminutes, Gradians, Degrees, Radians, Turns)

  implicit class AngleCons[A](a: A)(implicit num: Numeric[A]) {
    def arcseconds: Angle[A] = Arcseconds(a)
    def arcminutes: Angle[A] = Arcminutes(a)
    def gradians: Angle[A] = Gradians(a)
    def degrees: Angle[A] = Degrees(a)
    def radians: Angle[A] = Radians(a)
    def turns: Angle[A] = Turns(a)
  }

  lazy val arcseconds: Angle[Int] = Arcseconds(1)
  lazy val arcminutes: Angle[Int] = Arcminutes(1)
  lazy val gradians: Angle[Int] = Gradians(1)
  lazy val degrees: Angle[Int] = Degrees(1)
  lazy val radians: Angle[Int] = Radians(1)
  lazy val turns: Angle[Int] = Turns(1)

}

abstract class AngleUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Angle] {
  override def dimension: Dimension[Angle] = Angle
  override def apply[A: Numeric](value: A): Angle[A] = Angle(value, this)
}

case object Arcseconds extends AngleUnit("asec", 4.84813681109536E-6)
case object Arcminutes extends AngleUnit("amin", 2.908882086657216E-4)
case object Gradians extends AngleUnit("grad", 0.015707963267948967)
case object Degrees extends AngleUnit("°", 0.017453292519943295)
case object Radians extends AngleUnit("rad", 1) with PrimaryUnit[Angle] with SiUnit[Angle]
case object Turns extends AngleUnit("turns", 6.283185307179586)
