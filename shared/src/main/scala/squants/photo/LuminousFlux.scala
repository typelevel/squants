/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import squants._
import squants.space.{ SquareMeters, SquaredRadians }
import squants.time.{ Seconds, TimeDerivative }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.Lumens]]
 */
final class LuminousFlux private (val value: Double, val unit: LuminousFluxUnit)
    extends Quantity[LuminousFlux]
    with TimeDerivative[LuminousEnergy] {

  def dimension = LuminousFlux

  protected[squants] def timeIntegrated = LumenSeconds(toLumens)
  protected[squants] def time = Seconds(1)

  def /(that: Area): Illuminance = Lux(this.toLumens / that.toSquareMeters)
  def /(that: Illuminance): Area = SquareMeters(this.toLumens / that.toLux)
  def /(that: SolidAngle): LuminousIntensity = Candelas(this.toLumens / that.toSquaredRadians)
  def /(that: LuminousIntensity): SolidAngle = SquaredRadians(this.toLumens / that.toCandelas)

  def toLumens = to(Lumens)
}

object LuminousFlux extends Dimension[LuminousFlux] {
  private[photo] def apply[A](n: A, unit: LuminousFluxUnit)(implicit num: Numeric[A]) = new LuminousFlux(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "LuminousFlux"
  def primaryUnit = Lumens
  def siUnit = Lumens
  def units = Set(Lumens)
}

trait LuminousFluxUnit extends UnitOfMeasure[LuminousFlux] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousFlux(n, this)
}

object Lumens extends LuminousFluxUnit with PrimaryUnit with SiUnit {
  val symbol = "lm"
}

object LuminousFluxConversions {
  lazy val lumen = Lumens(1)

  implicit class LuminousFluxConversions[A](n: A)(implicit num: Numeric[A]) {
    def lumens = Lumens(n)
  }

  implicit object LuminousFluxNumeric extends AbstractQuantityNumeric[LuminousFlux](LuminousFlux.primaryUnit)
}