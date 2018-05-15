/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.energy.Watts
import squants.photo.{ Lumens, LuminousFlux, LuminousIntensity }
import squants.radio.RadiantIntensity

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.SquaredRadians]]
 */
final class SolidAngle private (val value: Double, val unit: SolidAngleUnit)
    extends Quantity[SolidAngle] {

  def dimension = SolidAngle

  def *(that: LuminousIntensity): LuminousFlux = Lumens(this.toSquaredRadians * that.toCandelas)
  def *(that: RadiantIntensity): Power = Watts(this.toSquaredRadians * that.toWattsPerSteradian)

  def toSquaredRadians = value
  def toSteradians = value
}

object SolidAngle extends Dimension[SolidAngle] {
  private[space] def apply[A](n: A, unit: SolidAngleUnit)(implicit num: Numeric[A]) = new SolidAngle(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SolidAngle"
  def primaryUnit = SquareRadians
  def siUnit = SquareRadians
  def units = Set(SquareRadians)
}

trait SolidAngleUnit extends UnitOfMeasure[SolidAngle] {
  def apply[A](n: A)(implicit num: Numeric[A]) = SolidAngle(n, this)
}

object SquaredRadians extends SolidAngleUnit with PrimaryUnit with SiUnit {
  val symbol = "sr"
}

object SolidAngleConversions {
  lazy val squaredRadian = SquaredRadians(1)
  lazy val steradian = SquaredRadians(1)

  implicit class SolidAngleConversions[A](n: A)(implicit num: Numeric[A]) {
    def squaredRadians = SquaredRadians(n)
    def steradians = SquaredRadians(n)
  }

  implicit object SolidAngleNumeric extends AbstractQuantityNumeric[SolidAngle](SolidAngle.primaryUnit)
}

