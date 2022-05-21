package squants2.space

import squants2.time.Minutes
import squants2._

final case class Angle[A] private[squants2] (value: A, unit: AngleUnit)(implicit qNum: QNumeric[A]) extends Quantity[A, Angle.type] {
  override type Q[B] = Angle[B]

  def toRadians: A = to(Radians)
  def toDegrees: A = to(Degrees)
  def toGradians: A = to(Gradians)
  def toTurns: A = to(Turns)
  def toArcminutes: A = to(Arcminutes)
  def toArcseconds: A = to(Arcseconds)

  def cos: A = qNum.cos(toRadians)
  def tan: A = qNum.tan(toRadians)
  def sin: A = qNum.sin(toRadians)
  def asin: A = qNum.asin(toRadians)
  def acos: A = qNum.acos(toRadians)
  def atan: A = qNum.atan(toRadians)

  def onRadius[B](radius: Length[B])(implicit f: B => A): Length[A] = radius.asNum[A] * to(Radians)

}

object Angle extends Dimension("Angle") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Radians
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Radians
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Radians, Degrees, Gradians, Turns, Arcminutes, Arcseconds)

  // Constructors from QNumeric values
  implicit class AngleCons[A](a: A)(implicit num: QNumeric[A]) {
    def radians: Angle[A] = Radians(a)
    def degrees: Angle[A] = Degrees(a)
    def gradians: Angle[A] = Gradians(a)
    def turns: Angle[A] = Turns(a)
    def arcminutes: Angle[A] = Arcminutes(a)
    def arcseconds: Angle[A] = Arcseconds(a)
  }

  // Constants
  lazy val radian: Angle[Int] = Radians(1)
  lazy val degree: Angle[Int] = Degrees(1)
  lazy val gradian: Angle[Int] = Gradians(1)
  lazy val turn: Angle[Int] = Turns(1)
  lazy val arcminute: Angle[Int] = Arcminutes(1)
  lazy val arcsecond: Angle[Int] = Arcseconds(1)

}

abstract class AngleUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[Angle.type] {
  override lazy val dimension: Angle.type = Angle
  override def apply[A: QNumeric](value: A): Angle[A] = Angle(value, this)
}

case object Radians extends AngleUnit("rad", 1) with PrimaryUnit with SiUnit
case object Degrees extends AngleUnit("°", math.Pi / 180d)
case object Gradians extends AngleUnit("grad", Turns.conversionFactor / 400d)
case object Turns extends AngleUnit("°", 2d * math.Pi)
case object Arcminutes extends AngleUnit("°", math.Pi / 10800d)
case object Arcseconds extends AngleUnit("°", 1d / Minutes.conversionFactor * Arcminutes.conversionFactor)
