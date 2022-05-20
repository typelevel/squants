package squants2.space

import squants.space.{ Arcminutes, Arcseconds, Degrees, Gradians, Radians, Turns }
import squants.time.Time
import squants2.QNumeric.QNumericOps
import squants2.*

final case class Angle[A: QNumeric] private[squants2] (value: A, unit: AngleUnit) extends Quantity[A, Angle.type] {
  override type Q[B] = Angle[B]

  def toRadians = to(Radians)
  def toDegrees = to(Degrees)
  def toGradians = to(Gradians)
  def toTurns = to(Turns)
  def toArcminutes = to(Arcminutes)
  def toArcseconds = to(Arcseconds)

  // TODO - implement trig functions in QNumeric
  //  def cos = math.cos(toRadians)
  //  def tan = math.tan(toRadians)
  //  def asin = math.asin(toRadians)
  //  def acos = math.acos(toRadians)
  //  def sin = math.sin(toRadians)

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
case object Arcseconds extends AngleUnit("°", 1d / Time.SecondsPerMinute * Arcminutes.conversionFactor)
