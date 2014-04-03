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
  private[energy] def apply(value: Double) = new SpecificEnergy(value)
}

trait SpecificEnergyUnit extends UnitOfMeasure[SpecificEnergy] with UnitMultiplier {
  def apply(d: Double): SpecificEnergy = SpecificEnergy(convertFrom(d))
}

object Grays extends SpecificEnergyUnit with ValueUnit {
  val symbol = "Gy"
}

object SpecificEnergyConversions {
  lazy val gray = Grays(1)

  implicit class SpecificEnergyConversions(d: Double) {
    def grays = Grays(d)
  }

  implicit object SpecificEnergyNumeric extends AbstractQuantityNumeric[SpecificEnergy](Grays)
}

