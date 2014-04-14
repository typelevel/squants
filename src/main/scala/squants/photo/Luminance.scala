/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */
package squants.photo

import squants._
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param intensity LuminousIntensity
 * @param area Area
 */
case class Luminance(intensity: LuminousIntensity, area: Area) extends Quantity[Luminance] {
  def value = toCandelasPerSquareMeters
  def valueUnit = CandelasPerSquareMeter

  def *(that: Area): LuminousIntensity = intensity * (that / area)

  def toCandelasPerSquareMeters = intensity.toCandelas / area.toSquareMeters
}

trait LuminanceUnit extends UnitOfMeasure[Luminance]

object CandelasPerSquareMeter extends LuminanceUnit with ValueUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = new Luminance(Candelas(n), SquareMeters(1))
  val symbol = "cd/m²"
}

object LuminanceConversions {
  lazy val candelaPerSquareMeter = CandelasPerSquareMeter(1)

  implicit class LuminanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def candelasPerSquareMeter = CandelasPerSquareMeter(n)
  }

  implicit object LuminanceNumeric extends AbstractQuantityNumeric[Luminance](CandelasPerSquareMeter)
}

