/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */
package squants.photo

import squants._
import squants.space.{ SquareMeters, SolidAngle }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.Candelas]]
 */
final class LuminousIntensity private (val value: Double) extends Quantity[LuminousIntensity]
    with BaseQuantity {

  def valueUnit = Candelas
  def baseUnit = Candelas

  def *(that: SolidAngle): LuminousFlux = Lumens(toCandelas * that.toSquaredRadians)
  def /(that: Area): Luminance = Luminance(this, that)
  def /(that: Luminance): Area = SquareMeters(toCandelas / that.toCandelasPerSquareMeters)

  def toCandelas = to(Candelas)
}

object LuminousIntensity {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new LuminousIntensity(num.toDouble(n))
}

trait LuminousIntensityUnit extends BaseQuantityUnit[LuminousIntensity] with UnitMultiplier {
  val dimensionSymbol = "J"
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousIntensity(convertFrom(n))
  def unapply(l: LuminousIntensity) = Some(convertTo(l.value))
}

object Candelas extends LuminousIntensityUnit with ValueUnit with BaseUnit {
  val symbol = "cd"
}

object LuminousIntensityConversions {
  lazy val candela = Candelas(1)

  implicit class LuminousIntensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def candelas = Candelas(n)
  }

  implicit object LuminousIntensityNumeric extends AbstractQuantityNumeric[LuminousIntensity](Candelas)
}