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

  def valueUnit = SiemensPerMeter

  def *(that: Length): ElectricalConductance = Siemens(toSiemensPerMeter * that.toMeters)

  def toSiemensPerMeter = to(SiemensPerMeter)
  def inOhmMeters = OhmMeters(1d / toSiemensPerMeter)
}

object Conductivity {
  private[electro] def apply(value: Double) = new Conductivity(value)
}

trait ConductivityUnit extends UnitOfMeasure[Conductivity] with UnitMultiplier {
  def apply(d: Double) = Conductivity(convertFrom(d))
}

object SiemensPerMeter extends ConductivityUnit with ValueUnit {
  val symbol = "S/m"
}

object ConductivityConversions {
  lazy val siemenPerMeter = SiemensPerMeter(1)

  implicit class ConductivityConversions(d: Double) {
    def siemensPerMeter = SiemensPerMeter(d)
  }

  implicit object ConductivityNumeric extends AbstractQuantityNumeric[Conductivity](SiemensPerMeter)
}
