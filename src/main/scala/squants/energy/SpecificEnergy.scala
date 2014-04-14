/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
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
final class SpecificEnergy private (val value: Double) extends Quantity[SpecificEnergy] {

  def valueUnit = Grays

  def *(that: Mass): Energy = Joules(toGrays * that.toKilograms)
  def /(that: Time) = ??? // returns AbsorbedEnergyRate

  def toGrays = to(Grays)
}

object SpecificEnergy {
  private[energy] def apply[A](n: A)(implicit num: Numeric[A]) = new SpecificEnergy(num.toDouble(n))
}

trait SpecificEnergyUnit extends UnitOfMeasure[SpecificEnergy] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpecificEnergy(convertFrom(n))
}

object Grays extends SpecificEnergyUnit with ValueUnit {
  val symbol = "Gy"
}

object SpecificEnergyConversions {
  lazy val gray = Grays(1)

  implicit class SpecificEnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def grays = Grays(n)
  }

  implicit object SpecificEnergyNumeric extends AbstractQuantityNumeric[SpecificEnergy](Grays)
}

