/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */
package squants.photo

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Luminance private (val value: Double) extends Quantity[Luminance] {

  def valueUnit = Luminance.valueUnit

  def *(that: Area): LuminousIntensity = Candelas(value * that.toSquareMeters)

  def toCandelasPerSquareMeters = to(CandelasPerSquareMeter)
}

object Luminance extends QuantityCompanion[Luminance] {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new Luminance(num.toDouble(n))
  def apply(s: String) = parseString(s)

  def name = "Luminance"
  def valueUnit = CandelasPerSquareMeter
  def units = Set(CandelasPerSquareMeter)
}

trait LuminanceUnit extends UnitOfMeasure[Luminance] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Luminance(num.toDouble(n))
}

object CandelasPerSquareMeter extends LuminanceUnit with ValueUnit {
  val symbol = "cd/m²"
}

object LuminanceConversions {
  lazy val candelaPerSquareMeter = CandelasPerSquareMeter(1)

  implicit class LuminanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def candelasPerSquareMeter = CandelasPerSquareMeter(n)
  }

  implicit object LuminanceNumeric extends AbstractQuantityNumeric[Luminance](Luminance.valueUnit)
}

