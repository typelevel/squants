/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import squants._
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
final class ThermalCapacity private (val value: Double, val unit: ThermalCapacityUnit)
    extends Quantity[ThermalCapacity] {

  def dimension = ThermalCapacity

  def *(that: Temperature): Energy = Joules(this.toJoulesPerKelvin * that.toKelvinScale)

  def toJoulesPerKelvin = to(JoulesPerKelvin)
}

object ThermalCapacity extends Dimension[ThermalCapacity] {
  private[thermal] def apply[A](n: A, unit: ThermalCapacityUnit)(implicit num: Numeric[A]) = new ThermalCapacity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ThermalCapacity"
  def primaryUnit = JoulesPerKelvin
  def siUnit = JoulesPerKelvin
  def units = Set(JoulesPerKelvin)
}

trait ThermalCapacityUnit extends UnitOfMeasure[ThermalCapacity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ThermalCapacity(n, this)
}

object JoulesPerKelvin extends ThermalCapacityUnit with PrimaryUnit with SiUnit {
  def symbol = "J/K"
}

object ThermalCapacityConversions {
  lazy val joulePerKelvin = JoulesPerKelvin(1)

  implicit class ThermalCapacityConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerKelvin = JoulesPerKelvin(n)
  }

  implicit object ThermalCapacityNumeric extends AbstractQuantityNumeric[ThermalCapacity](ThermalCapacity.primaryUnit)
}
