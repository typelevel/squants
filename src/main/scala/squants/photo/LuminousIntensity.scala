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
final class LuminousIntensity private (val value: Double) extends Quantity[LuminousIntensity] {

  def valueUnit = LuminousIntensity.valueUnit

  def *(that: SolidAngle): LuminousFlux = Lumens(toCandelas * that.toSquaredRadians)
  def /(that: Area): Luminance = CandelasPerSquareMeter(toCandelas / that.toSquareMeters)
  def /(that: Luminance): Area = SquareMeters(toCandelas / that.toCandelasPerSquareMeters)

  def toCandelas = to(Candelas)
}

object LuminousIntensity extends QuantityCompanion[LuminousIntensity] with BaseQuantity {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new LuminousIntensity(num.toDouble(n))
  def apply = parseString _
  def name = "LuminousIntensity"
  def valueUnit = Candelas
  def units = Set(Candelas)
  def baseUnit = Candelas
  def dimensionSymbol = "J"
}

trait LuminousIntensityUnit extends UnitOfMeasure[LuminousIntensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousIntensity(convertFrom(n))
}

object Candelas extends LuminousIntensityUnit with ValueUnit with BaseUnit {
  val symbol = "cd"
}

object LuminousIntensityConversions {
  lazy val candela = Candelas(1)

  implicit class LuminousIntensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def candelas = Candelas(n)
  }

  implicit object LuminousIntensityNumeric extends AbstractQuantityNumeric[LuminousIntensity](LuminousIntensity.valueUnit)
}