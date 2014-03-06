/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants.{ ValueUnit, UnitMultiplier, Quantity, UnitOfMeasure }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.Radians]]
 */
final class Angle private (val value: Double)
    extends Quantity[Angle] {

  def valueUnit = Radians

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
}

object Angle {
  private[space] def apply(value: Double) = new Angle(value)
}

trait AngleUnit extends UnitOfMeasure[Angle] with UnitMultiplier {
  def apply(d: Double): Angle = Angle(convertFrom(d))
}

object Radians extends AngleUnit with ValueUnit {
  val symbol = "rad"
}

object Degrees extends AngleUnit {
  val symbol = "°"
  val multiplier = math.Pi / 180d
}

object Gradians extends AngleUnit {
  val symbol = "grad"
  val multiplier = Turns.multiplier / 400d
}

object Turns extends AngleUnit {
  val symbol = "turns"
  val multiplier = 2 * math.Pi
}

object Arcminutes extends AngleUnit {
  val symbol = "amin"
  val multiplier = math.Pi / 10800d
}

object Arcseconds extends AngleUnit {
  val symbol = "asec"
  val multiplier = 1d / 60d * Arcminutes.multiplier
}

object AngleConversions {
  lazy val radian = Radians(1)
  lazy val degree = Degrees(1)
  lazy val gradian = Gradians(1)
  lazy val turn = Turns(1)
  lazy val arcminute = Arcminutes(1)
  lazy val arcsecond = Arcseconds(1)

  implicit class AngleConversions(d: Double) {
    def radians = Radians(d)
    def degrees = Degrees(d)
    def gradians = Gradians(d)
    def turns = Turns(d)
    def arcminutes = Arcminutes(d)
    def arcseconds = Arcseconds(d)
  }
}
