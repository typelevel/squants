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

  def valueUnit = LuminousFlux.valueUnit
  protected def timeIntegrated = LumenSeconds(toLumens)
  protected[squants] def time = Seconds(1)

  def /(that: Area): Illuminance = Lux(toLumens / that.toSquareMeters)
  def /(that: Illuminance): Area = SquareMeters(toLumens / that.toLux)
  def /(that: SolidAngle): LuminousIntensity = Candelas(toLumens / that.toSquaredRadians)
  def /(that: LuminousIntensity): SolidAngle = SquaredRadians(toLumens / that.toCandelas)

  def toLumens = to(Lumens)
}

object LuminousFlux extends QuantityCompanion[LuminousFlux] {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new LuminousFlux(num.toDouble(n))
  def apply = parseString _
  def name = "LuminousFlux"
  def valueUnit = Lumens
  def units = Set(Lumens)
}

trait LuminousFluxUnit extends UnitOfMeasure[LuminousFlux] with UnitConverter

object Lumens extends LuminousFluxUnit with ValueUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousFlux(n)
  val symbol = "lm"
}

object LuminousFluxConversions {
  lazy val lumen = Lumens(1)

  implicit class LuminousFluxConversions[A](n: A)(implicit num: Numeric[A]) {
    def lumens = Lumens(n)
  }

  implicit object LuminousFluxNumeric extends AbstractQuantityNumeric[LuminousFlux](LuminousFlux.valueUnit)
}