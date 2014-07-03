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

  def valueUnit = Resistivity.valueUnit

  def /(that: Length): ElectricalResistance = Ohms(toOhmMeters / that.toMeters)
  def /(that: ElectricalResistance): Length = Meters(toOhmMeters / that.toOhms)

  def toOhmMeters = to(OhmMeters)
  def inSiemensPerMeter = SiemensPerMeter(1d / toOhmMeters)
}

object Resistivity extends QuantityCompanion[Resistivity] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new Resistivity(num.toDouble(n))
  def apply = parseString _
  def name = "Resistivity"
  def valueUnit = OhmMeters
  def units = Set(OhmMeters)
}

trait ResitivityUnit extends UnitOfMeasure[Resistivity] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Resistivity(convertFrom(n))
}

object OhmMeters extends ResitivityUnit with ValueUnit {
  def symbol = "Ω⋅m"
}

object ResistivityConversions {
  lazy val ohmMeter = OhmMeters(1)

  implicit class ResistivityConversions[A](n: A)(implicit num: Numeric[A]) {
    def ohmMeters = OhmMeters(n)
  }

  implicit object ResistivityNumeric extends AbstractQuantityNumeric[Resistivity](Resistivity.valueUnit)
}
