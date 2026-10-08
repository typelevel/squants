/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.mass.Kilograms

/**
 * Represents a quantity of specific volume (volume per unit mass).
 * Specific volume is the inverse of density.
 * SI unit: m³/kg.
 *
 * @author  mithunbabbira
 * @since   2.0
 *
 * @param value value in [[squants.space.CubicMetersPerKilogram]]
 */
final class SpecificVolume private (val value: Double, val unit: SpecificVolumeUnit)
    extends Quantity[SpecificVolume] {

  def dimension = SpecificVolume

  def *(that: Mass): Volume = CubicMeters(this.toCubicMetersPerKilogram * that.toKilograms)

  def toCubicMetersPerKilogram = to(CubicMetersPerKilogram)
}

object SpecificVolume extends Dimension[SpecificVolume] {
  private[space] def apply[A](n: A, unit: SpecificVolumeUnit)(implicit num: Numeric[A]) = new SpecificVolume(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SpecificVolume"
  def primaryUnit = CubicMetersPerKilogram
  def siUnit = CubicMetersPerKilogram
  def units = Set(CubicMetersPerKilogram)
}

trait SpecificVolumeUnit extends UnitOfMeasure[SpecificVolume] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpecificVolume(n, this)
}

object CubicMetersPerKilogram extends SpecificVolumeUnit with PrimaryUnit with SiUnit {
  val symbol = s"${CubicMeters.symbol}/${Kilograms.symbol}"
}

object SpecificVolumeConversions {
  lazy val cubicMeterPerKilogram = CubicMetersPerKilogram(1)

  implicit class SpecificVolumeConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerKilogram = CubicMetersPerKilogram(n)
  }

  implicit object SpecificVolumeNumeric extends AbstractQuantityNumeric[SpecificVolume](SpecificVolume.primaryUnit)
}
