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
  * Represents a quantity of power density
  *
  * @author  Nicolas Vinuesa
  * @since   1.4
  *
  * @param value value in [[squants.energy.WattsPerCubicMeter]]
  */
final class PowerDensity private (val value: Double, val unit: PowerDensityUnit)
  extends Quantity[PowerDensity] {

  def dimension = PowerDensity

  def *(that: Volume): Power = Watts(this.toWattsPerCubicMeter * that.toCubicMeters)

  def toWattsPerCubicMeter = to(WattsPerCubicMeter)
}

object PowerDensity extends Dimension[PowerDensity] {
  private[energy] def apply[A](n: A, unit: PowerDensityUnit)(implicit num: Numeric[A]) = new PowerDensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "PowerDensity"
  def primaryUnit = WattsPerCubicMeter
  def siUnit = WattsPerCubicMeter
  def units = Set(WattsPerCubicMeter)
}

trait PowerDensityUnit extends UnitOfMeasure[PowerDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = PowerDensity(n, this)
}

object WattsPerCubicMeter extends PowerDensityUnit with PrimaryUnit with SiUnit {
  val symbol = "W/m³"
}

object PowerDensityConversions {
  lazy val wattPerCubicMeter = WattsPerCubicMeter(1)

  implicit class PowerDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerCubicMeter = WattsPerCubicMeter(n)
  }

  implicit object PowerDensityNumeric extends AbstractQuantityNumeric[PowerDensity](PowerDensity.primaryUnit)
}
