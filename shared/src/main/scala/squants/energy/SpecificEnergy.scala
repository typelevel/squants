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

  def *(that: Mass): Energy = Joules(this.toGrays * that.toKilograms)
  def /(that: Time) = ??? // returns AbsorbedEnergyRate

  def toGrays = to(Grays)
  def toRads = to(Rads)
  def toErgsPerGram = to(ErgsPerGram)
}

object SpecificEnergy extends Dimension[SpecificEnergy] {
  private[energy] def apply[A](n: A, unit: SpecificEnergyUnit)(implicit num: Numeric[A]) = new SpecificEnergy(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SpecificEnergy"
  def primaryUnit = Grays
  def siUnit = Grays
  def units = Set(Grays, Rads, ErgsPerGram)
}

trait SpecificEnergyUnit extends UnitOfMeasure[SpecificEnergy] {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpecificEnergy(n, this)
}

object Grays extends SpecificEnergyUnit with PrimaryUnit with SiUnit {
  val symbol = "Gy"
}

object Rads extends SpecificEnergyUnit with UnitConverter {
  val symbol = "rad"
  val conversionFactor = 0.01
}

object ErgsPerGram extends SpecificEnergyUnit with UnitConverter {
  val symbol = "erg/g"
  val conversionFactor = 0.0001
}

object SpecificEnergyConversions {
  lazy val gray = Grays(1)
  lazy val rad = Rads(1)
  lazy val ergsPerGram = ErgsPerGram(1)

  implicit class SpecificEnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def grays = Grays(n)
    def rads = Rads(n)
    def ergsPerGram = ErgsPerGram(n)
  }

  implicit object SpecificEnergyNumeric extends AbstractQuantityNumeric[SpecificEnergy](SpecificEnergy.primaryUnit)
}
