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
 * Represents specific volume — volume per unit mass (inverse of density).
 * SI unit: m³/kg
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.space.CubicMetersPerKilogram]]
 */
final class SpecificVolume private (val value: Double, val unit: SpecificVolumeUnit)
    extends Quantity[SpecificVolume] {

  def dimension = SpecificVolume

  def *(that: Mass): Volume = CubicMeters(this.toCubicMetersPerKilogram * that.toKilograms)

  def toCubicMetersPerKilogram = to(CubicMetersPerKilogram)
  def toCubicFeetPerPound = to(CubicFeetPerPound)
}

object SpecificVolume extends Dimension[SpecificVolume] {
  private[space] def apply[A](n: A, unit: SpecificVolumeUnit)(implicit num: Numeric[A]) = new SpecificVolume(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SpecificVolume"
  def primaryUnit = CubicMetersPerKilogram
  def siUnit = CubicMetersPerKilogram
  def units = Set(CubicMetersPerKilogram, CubicFeetPerPound)
}

trait SpecificVolumeUnit extends UnitOfMeasure[SpecificVolume] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpecificVolume(n, this)
}

object CubicMetersPerKilogram extends SpecificVolumeUnit with PrimaryUnit with SiUnit {
  val symbol = "m³/kg"
}

object CubicFeetPerPound extends SpecificVolumeUnit with SiUnit {
  val symbol = "ft³/lb"
  val conversionFactor = 0.062427960576144526
}

object SpecificVolumeConversions {
  lazy val cubicMeterPerKilogram = CubicMetersPerKilogram(1)
  lazy val cubicFeetPerPound = CubicFeetPerPound(1)

  implicit class SpecificVolumeConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerKilogram = CubicMetersPerKilogram(n)
    def cubicFeetPerPound = CubicFeetPerPound(n)
  }

  implicit object SpecificVolumeNumeric
    extends AbstractQuantityNumeric[SpecificVolume](SpecificVolume.primaryUnit)
}
