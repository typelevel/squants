/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.motion.{AngularVelocity, RadiansPerSecond}
import squants.time.{Time, TimeIntegral}

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.Radians]]
 */
final class Angle private (val value: Double, val unit: AngleUnit)
    extends Quantity[Angle] with TimeIntegral[AngularVelocity] {

  def dimension = Angle

  def toRadians = to(Radians)
  def toDegrees = to(Degrees)
  def toGradians = to(Gradians)
  def toTurns = to(Turns)
  def toArcminutes = to(Arcminutes)
  def toArcseconds = to(Arcseconds)

  def sin = math.sin(toRadians)
  def cos = math.cos(toRadians)
  def tan = math.tan(toRadians)
  def asin = math.asin(toRadians)
  def acos = math.acos(toRadians)

  /**
    * length of the arc traveled by a point on the rim of a circle with this
    * angle traveled and the given (constant) radius from the center of
    * rotation
    * @param radius the distance from the center of rotation
    * @return arc length with given arc measure and radius
    */
  def onRadius(radius: Length): Length = toRadians * radius


  protected def timeDerived: AngularVelocity = RadiansPerSecond(toRadians)

  override protected def time: Time = Seconds(1)
}

object Angle extends Dimension[Angle] {
  private[space] def apply[A](n: A, unit: AngleUnit)(implicit num: Numeric[A]) = new Angle(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Angle"
  def primaryUnit = Radians
  def siUnit = Radians
  def units = Set(Radians, Degrees, Gradians, Turns, Arcminutes, Arcseconds)
}

trait AngleUnit extends UnitOfMeasure[Angle] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Angle(n, this)
}

object Radians extends AngleUnit with PrimaryUnit with SiUnit {
  val symbol = "rad"
}

object Degrees extends AngleUnit {
  val symbol = "°"
  val conversionFactor = math.Pi / 180d
}

object Gradians extends AngleUnit {
  val symbol = "grad"
  val conversionFactor = Turns.conversionFactor / 400d
}

object Turns extends AngleUnit {
  val symbol = "turns"
  val conversionFactor = 2 * math.Pi
}

object Arcminutes extends AngleUnit {
  val symbol = "amin"
  val conversionFactor = math.Pi / 10800d
}

object Arcseconds extends AngleUnit {
  val symbol = "asec"
  val conversionFactor = 1d / Time.SecondsPerMinute * Arcminutes.conversionFactor
}

object AngleConversions {
  lazy val radian = Radians(1)
  lazy val degree = Degrees(1)
  lazy val gradian = Gradians(1)
  lazy val turn = Turns(1)
  lazy val arcminute = Arcminutes(1)
  lazy val arcsecond = Arcseconds(1)

  implicit class AngleConversions[A](n: A)(implicit num: Numeric[A]) {
    def radians = Radians(n)
    def degrees = Degrees(n)
    def gradians = Gradians(n)
    def turns = Turns(n)
    def arcminutes = Arcminutes(n)
    def arcseconds = Arcseconds(n)
  }

  implicit object AngleNumeric extends AbstractQuantityNumeric[Angle](Angle.primaryUnit)
}
