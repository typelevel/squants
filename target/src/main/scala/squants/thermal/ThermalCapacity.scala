/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import squants.{ ValueUnit, UnitMultiplier, UnitOfMeasure, Quantity }
import squants.energy.{ Energy, Joules }

/**
 * Represents the capacity of some substance or system to hold thermal energy.
 *
 * Also a representation of Entropy
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in [[squants.thermal.JoulesPerKelvin]]
 */
final class ThermalCapacity private (val value: Double) extends Quantity[ThermalCapacity] {

  def valueUnit = JoulesPerKelvin

  def *(that: Temperature): Energy = Joules(toJoulesPerKelvin * that.toKelvinScale)

  def toJoulesPerKelvin = to(JoulesPerKelvin)
}

object ThermalCapacity {
  private[thermal] def apply(value: Double) = new ThermalCapacity(value)
}

trait ThermalCapacityUnit extends UnitOfMeasure[ThermalCapacity] with UnitMultiplier {
  def apply(d: Double) = ThermalCapacity(convertFrom(d))
}

object JoulesPerKelvin extends ThermalCapacityUnit with ValueUnit {
  def symbol = "J/K"
}

object ThermalCapacityConversions {
  lazy val joulePerKelvin = JoulesPerKelvin(1)

  implicit class ThermalCapacityConversions(d: Double) {
    def joulesPerKelvin = JoulesPerKelvin(d)
  }
}
