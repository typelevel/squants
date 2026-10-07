/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._
import squants.space.{ CubicMeters, Volume }

/**
 * Represents chemical concentration — chemical amount per unit volume.
 * SI unit: mol/m³
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.mass.MolesPerCubicMeter]]
 */
final class SubstanceConcentration private (val value: Double, val unit: SubstanceConcentrationUnit)
    extends Quantity[SubstanceConcentration] {

  def dimension = SubstanceConcentration

  def *(that: Volume): ChemicalAmount = Moles(this.toMolesPerCubicMeter * that.toCubicMeters)

  def toMolesPerCubicMeter = to(MolesPerCubicMeter)
  def toMolarity = to(Molarity)
  def toMillimolarity = to(Millimolarity)
}

object SubstanceConcentration extends Dimension[SubstanceConcentration] {
  private[mass] def apply[A](n: A, unit: SubstanceConcentrationUnit)(implicit num: Numeric[A]) = new SubstanceConcentration(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SubstanceConcentration"
  def primaryUnit = MolesPerCubicMeter
  def siUnit = MolesPerCubicMeter
  def units = Set(MolesPerCubicMeter, Molarity, Millimolarity)
}

trait SubstanceConcentrationUnit extends UnitOfMeasure[SubstanceConcentration] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SubstanceConcentration(n, this)
}

object MolesPerCubicMeter extends SubstanceConcentrationUnit with PrimaryUnit with SiUnit {
  val symbol = "mol/m³"
}

object Molarity extends SubstanceConcentrationUnit with SiUnit {
  val symbol = "M"
  val conversionFactor = 1000d
}

object Millimolarity extends SubstanceConcentrationUnit with SiUnit {
  val symbol = "mM"
  val conversionFactor = 1d
}

object SubstanceConcentrationConversions {
  lazy val molePerCubicMeter = MolesPerCubicMeter(1)
  lazy val molarity = Molarity(1)
  lazy val millimolarity = Millimolarity(1)

  implicit class SubstanceConcentrationConversions[A](n: A)(implicit num: Numeric[A]) {
    def molesPerCubicMeter = MolesPerCubicMeter(n)
    def molarities = Molarity(n)
    def millimolarities = Millimolarity(n)
  }

  implicit object SubstanceConcentrationNumeric
    extends AbstractQuantityNumeric[SubstanceConcentration](SubstanceConcentration.primaryUnit)
}
