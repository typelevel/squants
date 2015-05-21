/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.Grays]]
 */
final class SpecificEnergy private (val value: Double, val unit: SpecificEnergyUnit) extends Quantity[SpecificEnergy] {

  def dimension = SpecificEnergy

  def *(that: Mass): Energy = Joules(toGrays * that.toKilograms)
  def /(that: Time) = ??? // returns AbsorbedEnergyRate

  def toGrays = to(Grays)
}

object SpecificEnergy extends Dimension[SpecificEnergy] {
  private[energy] def apply[A](n: A, unit: SpecificEnergyUnit)(implicit num: Numeric[A]) = new SpecificEnergy(num.toDouble(n), unit)
  def apply = parse _
  def name = "SpecificEnergy"
  def primaryUnit = Grays
  def siUnit = Grays
  def units = Set(Grays)
}

trait SpecificEnergyUnit extends UnitOfMeasure[SpecificEnergy] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpecificEnergy(n, this)
}

object Grays extends SpecificEnergyUnit with PrimaryUnit with SiUnit {
  val symbol = "Gy"
}

object SpecificEnergyConversions {
  lazy val gray = Grays(1)

  implicit class SpecificEnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def grays = Grays(n)
  }

  implicit object SpecificEnergyNumeric extends AbstractQuantityNumeric[SpecificEnergy](SpecificEnergy.primaryUnit)
}

