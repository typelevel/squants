/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._
import squants.space.{CubicMeters, Volume}

/**
 * Represents a quantity of molar volume (volume per mole of substance).
 * SI unit: m³/mol.
 *
 * @author  mithunbabbira
 * @since   2.0
 *
 * @param value value in [[squants.mass.CubicMetersPerMole]]
 */
final class MolarVolume private (val value: Double, val unit: MolarVolumeUnit)
    extends Quantity[MolarVolume] {

  def dimension = MolarVolume

  def *(that: ChemicalAmount): Volume = CubicMeters(this.toCubicMetersPerMole * that.toMoles)

  def toCubicMetersPerMole = to(CubicMetersPerMole)
}

object MolarVolume extends Dimension[MolarVolume] {
  private[mass] def apply[A](n: A, unit: MolarVolumeUnit)(implicit num: Numeric[A]) = new MolarVolume(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "MolarVolume"
  def primaryUnit = CubicMetersPerMole
  def siUnit = CubicMetersPerMole
  def units = Set(CubicMetersPerMole)
}

trait MolarVolumeUnit extends UnitOfMeasure[MolarVolume] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MolarVolume(n, this)
}

object CubicMetersPerMole extends MolarVolumeUnit with PrimaryUnit with SiUnit {
  val symbol = s"${CubicMeters.symbol}/${Moles.symbol}"
}

object MolarVolumeConversions {
  lazy val cubicMeterPerMole = CubicMetersPerMole(1)

  implicit class MolarVolumeConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerMole = CubicMetersPerMole(n)
  }

  implicit object MolarVolumeNumeric extends AbstractQuantityNumeric[MolarVolume](MolarVolume.primaryUnit)
}
