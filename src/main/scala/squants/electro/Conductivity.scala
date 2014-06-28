/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.space.Length

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.SiemensPerMeter]]
 */
final class Conductivity private (val value: Double) extends Quantity[Conductivity] {

  def valueUnit = Conductivity.valueUnit

  def *(that: Length): ElectricalConductance = Siemens(toSiemensPerMeter * that.toMeters)

  def toSiemensPerMeter = to(SiemensPerMeter)
  def inOhmMeters = OhmMeters(1d / toSiemensPerMeter)
}

object Conductivity extends QuantityCompanion[Conductivity] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new Conductivity(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "Conductivity"
  def valueUnit = SiemensPerMeter
  def units = Set(SiemensPerMeter)
}

trait ConductivityUnit extends UnitOfMeasure[Conductivity] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Conductivity(convertFrom(n))
}

object SiemensPerMeter extends ConductivityUnit with ValueUnit {
  val symbol = "S/m"
}

object ConductivityConversions {
  lazy val siemenPerMeter = SiemensPerMeter(1)

  implicit class ConductivityConversions[A](n: A)(implicit num: Numeric[A]) {
    def siemensPerMeter = SiemensPerMeter(n)
  }

  implicit object ConductivityNumeric extends AbstractQuantityNumeric[Conductivity](Conductivity.valueUnit)
}
