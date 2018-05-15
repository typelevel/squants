/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
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
final class Luminance private (val value: Double, val unit: LuminanceUnit)
    extends Quantity[Luminance] {

  def dimension = Luminance

  def *(that: Area): LuminousIntensity = Candelas(this.value * that.toSquareMeters)

  def toCandelasPerSquareMeters = to(CandelasPerSquareMeter)
}

object Luminance extends Dimension[Luminance] {
  private[photo] def apply[A](n: A, unit: LuminanceUnit)(implicit num: Numeric[A]) = new Luminance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)

  def name = "Luminance"
  def primaryUnit = CandelasPerSquareMeter
  def siUnit = CandelasPerSquareMeter
  def units = Set(CandelasPerSquareMeter)
}

trait LuminanceUnit extends UnitOfMeasure[Luminance] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Luminance(num.toDouble(n), this)
}

object CandelasPerSquareMeter extends LuminanceUnit with PrimaryUnit with SiUnit {
  val symbol = "cd/m²"
}

object LuminanceConversions {
  lazy val candelaPerSquareMeter = CandelasPerSquareMeter(1)

  implicit class LuminanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def candelasPerSquareMeter = CandelasPerSquareMeter(n)
  }

  implicit object LuminanceNumeric extends AbstractQuantityNumeric[Luminance](Luminance.primaryUnit)
}

