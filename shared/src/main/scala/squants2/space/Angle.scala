/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.space

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Angle[A: Numeric] private [squants2]  (value: A, unit: AngleUnit)
  extends Quantity[A, Angle.type] {
  override type Q[B] = Angle[B]

  // BEGIN CUSTOM OPS
  private val num = implicitly[Numeric[A]]
  def cos: Double = math.cos(num.toDouble(toRadians))
  def tan: Double = math.tan(num.toDouble(toRadians))
  def sin: Double = math.sin(num.toDouble(toRadians))
  def asin: Double = math.asin(num.toDouble(toRadians))
  def acos: Double = math.acos(num.toDouble(toRadians))
  def atan: Double = math.atan(num.toDouble(toRadians))

  def onRadius[B](radius: Length[B])(implicit f: B => A): Length[A] = radius.asNum[A] * to(Radians)

  def toArcseconds: A = to(Arcseconds)
  def toArcminutes: A = to(Arcminutes)
  def toGradians: A = to(Gradians)
  def toDegrees: A = to(Degrees)
  def toRadians: A = to(Radians)
  def toTurns: A = to(Turns)
  // END CUSTOM OPS

}

object Angle extends Dimension("Angle") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Radians
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Radians
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AngleNumeric[A]()
  private case class AngleNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Angle.type], y: Quantity[A, Angle.type]): Quantity[A, Angle.this.type] =
      Radians(x.to(Radians) * y.to(Radians))
  }
}

abstract class AngleUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Angle.type] {
  override lazy val dimension: Angle.type = Angle
  override def apply[A: Numeric](value: A): Angle[A] = Angle(value, this)
}

case object Arcseconds extends AngleUnit("asec", 4.84813681109536E-6)
case object Arcminutes extends AngleUnit("amin", 2.908882086657216E-4)
case object Gradians extends AngleUnit("grad", 0.015707963267948967)
case object Degrees extends AngleUnit("°", 0.017453292519943295)
case object Radians extends AngleUnit("rad", 1) with PrimaryUnit with SiUnit
case object Turns extends AngleUnit("turns", 6.283185307179586)
