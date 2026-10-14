/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Measure Library and DSL                         **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      */
/*                                                                      */

package squants.energy

import squants._
import squants.space.CubicMeters
import squants.mass.Kilograms
import squants.time._

/**
 * Represents a quantity of specific volume (inverse of density)
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.CubicMetersPerKilogram]]
 */
final class SpecificVolume private (val value: Double, val unit: SpecificVolumeUnit)
    extends Quantity[SpecificVolume] {

  def dimension = SpecificVolume

  def *(that: Mass): Volume = CubicMeters(this.toCubicMetersPerKilogram * that.toKilograms)

  def toCubicMetersPerKilogram = to(CubicMetersPerKilogram)
}

object SpecificVolume extends Dimension[SpecificVolume] {
  private[energy] def apply[A](n: A, unit: SpecificVolumeUnit)(implicit num: Numeric[A]) = new SpecificVolume(num.toDouble(n), unit)
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
