/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
case class Density(value: Double) extends Quantity[Density] {

  def valueUnit = KilogramsPerCubicMeter

  def *(that: Volume): Mass = Kilograms(value * that.toCubicMeters)

  def toKilogramsPerCubicMeter = to(KilogramsPerCubicMeter)
}

object Density extends QuantityCompanion[Density] {
  private[mass] def apply[A](n: A)(implicit num: Numeric[A]) = new Density(num.toDouble(n))
  def apply(m: Mass, v: Volume): Density = KilogramsPerCubicMeter(m.toKilograms / v.toCubicMeters)
  def apply = parseString _
  def name = "Density"
  def valueUnit = KilogramsPerCubicMeter
  def units = Set(KilogramsPerCubicMeter)
}

trait DensityUnit extends UnitOfMeasure[Density] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Density(convertFrom(n))
}

object KilogramsPerCubicMeter extends DensityUnit with ValueUnit {
  val symbol = "kg/m³"
}

object DensityConversions {
  lazy val kilogramPerCubicMeter = KilogramsPerCubicMeter(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerCubicMeter = KilogramsPerCubicMeter(n)
  }

  implicit object DensityNumeric extends AbstractQuantityNumeric[Density](KilogramsPerCubicMeter)
}