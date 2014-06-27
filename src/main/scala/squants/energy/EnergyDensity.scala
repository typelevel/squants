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
 * Represents a quantity of energy
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.WattHours]]
 */
final class EnergyDensity private (val value: Double) extends Quantity[EnergyDensity]
    with PhysicalQuantity {

  def valueUnit = EnergyDensity.valueUnit

  def *(that: Volume): Energy = Joules(toJoulesPerCubicMeter * that.toCubicMeters)

  def toJoulesPerCubicMeter = to(JoulesPerCubicMeter)
}

object EnergyDensity extends QuantityCompanion[EnergyDensity] {
  private[energy] def apply[A](n: A)(implicit num: Numeric[A]) = new EnergyDensity(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "EnergyDensity"
  def valueUnit = JoulesPerCubicMeter
  def units = Set(JoulesPerCubicMeter)
}

trait EnergyDensityUnit extends UnitOfMeasure[EnergyDensity] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = EnergyDensity(convertFrom(n))
}

object JoulesPerCubicMeter extends EnergyDensityUnit with ValueUnit {
  val symbol = "j/m³"
}

object EnergyDensityConversions {
  lazy val joulePerCubicMeter = JoulesPerCubicMeter(1)

  implicit class EnergyDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerCubicMeter = JoulesPerCubicMeter(n)
  }

  implicit object EnergyDensityNumeric extends AbstractQuantityNumeric[EnergyDensity](EnergyDensity.valueUnit)
}
