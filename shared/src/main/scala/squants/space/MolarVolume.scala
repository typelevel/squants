/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.mass.{ ChemicalAmount, Moles }

/**
 * Represents molar volume — volume per unit chemical amount.
 * SI unit: m³/mol
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.space.CubicMetersPerMole]]
 */
final class MolarVolume private (val value: Double, val unit: MolarVolumeUnit)
    extends Quantity[MolarVolume] {

  def dimension = MolarVolume

  def *(that: ChemicalAmount): Volume = CubicMeters(this.toCubicMetersPerMole * that.toMoles)

  def toCubicMetersPerMole = to(CubicMetersPerMole)
  def toLitresPerMole = to(LitresPerMole)
}

object MolarVolume extends Dimension[MolarVolume] {
  private[space] def apply[A](n: A, unit: MolarVolumeUnit)(implicit num: Numeric[A]) = new MolarVolume(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "MolarVolume"
  def primaryUnit = CubicMetersPerMole
  def siUnit = CubicMetersPerMole
  def units = Set(CubicMetersPerMole, LitresPerMole)
}

trait MolarVolumeUnit extends UnitOfMeasure[MolarVolume] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MolarVolume(n, this)
}

object CubicMetersPerMole extends MolarVolumeUnit with PrimaryUnit with SiUnit {
  val symbol = "m³/mol"
}

object LitresPerMole extends MolarVolumeUnit with SiUnit {
  val symbol = "L/mol"
  val conversionFactor = 0.001
}

object MolarVolumeConversions {
  lazy val cubicMeterPerMole = CubicMetersPerMole(1)
  lazy val litrePerMole = LitresPerMole(1)

  implicit class MolarVolumeConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerMole = CubicMetersPerMole(n)
    def litresPerMole = LitresPerMole(n)
  }

  implicit object MolarVolumeNumeric
    extends AbstractQuantityNumeric[MolarVolume](MolarVolume.primaryUnit)
}
