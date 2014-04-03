/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.OhmMeters]]
 */
final class Resistivity private (val value: Double) extends Quantity[Resistivity] {

  def valueUnit = OhmMeters

  def /(that: Length): ElectricalResistance = Ohms(toOhmMeters / that.toMeters)
  def /(that: ElectricalResistance): Length = Meters(toOhmMeters / that.toOhms)

  def toOhmMeters = to(OhmMeters)
  def inSiemensPerMeter = SiemensPerMeter(1d / toOhmMeters)
}

object Resistivity {
  private[electro] def apply(value: Double) = new Resistivity(value)
}

trait ResitivityUnit extends UnitOfMeasure[Resistivity] with UnitMultiplier {
  def apply(d: Double) = Resistivity(convertFrom(d))
}

object OhmMeters extends ResitivityUnit with ValueUnit {
  def symbol = "Ω⋅m"
}

object ResistivityConversions {
  lazy val ohmMeter = OhmMeters(1)

  implicit class ResistivityConversions(d: Double) {
    def ohmMeters = OhmMeters(d)
  }

  implicit object ResistivityNumeric extends AbstractQuantityNumeric[Resistivity]((OhmMeters))
}
