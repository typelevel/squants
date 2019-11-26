/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants._
import squants.mass.Mass
import squants.energy.{ Energy, Joules }
import squants.time.Time

/**
 * Its important to note that while Dose and SpecificEnergy are simliar
 * measures (both are energy/mass dimensions), they are decidedly different
 * and no conversions between the two dimensions should be directly possible
 * through implicit conversions EVER.  This is because SpecificEnergy is used to
 * measure absorbed dose which just measures energy deposited into a mass of
 * material while Dose is used to measure equivalent/effective/committed doses
 * which are measures of the damage done to biological tissues.  Since this
 * is an easy and disastrous mistake to make, it is critical that Squants
 * doesn't allow any sort of magic conversions that allow this mistake.
 * @author  Hunter Payne
 *
 */
final class Dose private (val value: Double, val unit: DoseUnit) extends Quantity[Dose] {

  def dimension = Dose

  def *(that: Mass): Energy = Joules(this.toSieverts * that.toKilograms)
  def /(that: Time) = ??? // returns AbsorbedEnergyRate

  def toSieverts = to(Sieverts)
  def toRems = to(Rems)
}

object Dose extends Dimension[Dose] {
  private[radio] def apply[A](n: A, unit: DoseUnit)(implicit num: Numeric[A]) = new Dose(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Dose"
  def primaryUnit = Sieverts
  def siUnit = Sieverts
  def units = Set(Sieverts, Rems)
}

trait DoseUnit extends UnitOfMeasure[Dose] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Dose(n, this)
}

object Rems extends DoseUnit with UnitConverter {
  val symbol = "rem"
  val conversionFactor = 0.01
}

object Sieverts extends DoseUnit with PrimaryUnit with SiUnit {
  val symbol = "Sv"
}

object DoseConversions {
  lazy val sievert = Sieverts(1)
  lazy val rem = Rems(1)

  implicit class DoseConversions[A](n: A)(implicit num: Numeric[A]) {
    def sieverts = Sieverts(n)
    def rems = Rems(n)
  }

  implicit object DoseNumeric extends AbstractQuantityNumeric[Dose](Dose.primaryUnit)
}
