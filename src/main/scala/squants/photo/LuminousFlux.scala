/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import squants._
import squants.time.{ Seconds, TimeDerivative }
import squants.space.{ SquaredRadians, SquareMeters }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.Lumens]]
 */
final class LuminousFlux private (val value: Double)
    extends Quantity[LuminousFlux]
    with TimeDerivative[LuminousEnergy] {

  def valueUnit = Lumens

  def /(that: Area): Illuminance = Lux(toLumens / that.toSquareMeters)
  def /(that: Illuminance): Area = SquareMeters(toLumens / that.toLux)
  def /(that: SolidAngle): LuminousIntensity = Candelas(toLumens / that.toSquaredRadians)
  def /(that: LuminousIntensity): SolidAngle = SquaredRadians(toLumens / that.toCandelas)

  def change = LumenSeconds(value)
  def time = Seconds(1)

  def toLumens = to(Lumens)
}

object LuminousFlux {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new LuminousFlux(num.toDouble(n))
}

trait LuminousFluxUnit extends UnitOfMeasure[LuminousFlux] with UnitMultiplier

object Lumens extends LuminousFluxUnit with ValueUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousFlux(n)
  val symbol = "lm"
}

object LuminousFluxConversions {
  lazy val lumen = Lumens(1)

  implicit class LuminousFluxConversions[A](n: A)(implicit num: Numeric[A]) {
    def lumens = Lumens(n)
  }

  implicit object LuminousFluxNumeric extends AbstractQuantityNumeric[LuminousFlux](Lumens)
}