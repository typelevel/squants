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
 * Represents a quantity of substance concentration (chemical amount per unit volume).
 * Also known as Molar Concentration or Molarity.
 * SI unit: mol/m³.
 *
 * @author  mithunbabbira
 * @since   2.0
 *
 * @param value value in [[squants.mass.MolesPerCubicMeter]]
 */
final class SubstanceConcentration private (val value: Double, val unit: SubstanceConcentrationUnit)
    extends Quantity[SubstanceConcentration] {

  def dimension = SubstanceConcentration

  def *(that: Volume): ChemicalAmount = Moles(this.toMolesPerCubicMeter * that.toCubicMeters)

  def toMolesPerCubicMeter = to(MolesPerCubicMeter)
}

object SubstanceConcentration extends Dimension[SubstanceConcentration] {
  private[mass] def apply[A](n: A, unit: SubstanceConcentrationUnit)(implicit num: Numeric[A]) = new SubstanceConcentration(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SubstanceConcentration"
  def primaryUnit = MolesPerCubicMeter
  def siUnit = MolesPerCubicMeter
  def units = Set(MolesPerCubicMeter)
}

trait SubstanceConcentrationUnit extends UnitOfMeasure[SubstanceConcentration] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SubstanceConcentration(n, this)
}

object MolesPerCubicMeter extends SubstanceConcentrationUnit with PrimaryUnit with SiUnit {
  val symbol = s"${Moles.symbol}/${CubicMeters.symbol}"
}

object SubstanceConcentrationConversions {
  lazy val molePerCubicMeter = MolesPerCubicMeter(1)

  implicit class SubstanceConcentrationConversions[A](n: A)(implicit num: Numeric[A]) {
    def molesPerCubicMeter = MolesPerCubicMeter(n)
  }

  implicit object SubstanceConcentrationNumeric extends AbstractQuantityNumeric[SubstanceConcentration](SubstanceConcentration.primaryUnit)
}
