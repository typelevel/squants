/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
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
final class Resistivity private (val value: Double, val unit: ResistivityUnit)
    extends Quantity[Resistivity] {

  def dimension = Resistivity

  def /(that: Length): ElectricalResistance = Ohms(this.toOhmMeters / that.toMeters)
  def /(that: ElectricalResistance): Length = Meters(this.toOhmMeters / that.toOhms)

  def toOhmMeters = to(OhmMeters)
  def inSiemensPerMeter = SiemensPerMeter(1d / toOhmMeters)
}

object Resistivity extends Dimension[Resistivity] {
  private[electro] def apply[A](n: A, unit: ResistivityUnit)(implicit num: Numeric[A]) = new Resistivity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Resistivity"
  def primaryUnit = OhmMeters
  def siUnit = OhmMeters
  def units = Set(OhmMeters)
}

trait ResistivityUnit extends UnitOfMeasure[Resistivity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Resistivity(n, this)
}

object OhmMeters extends ResistivityUnit with PrimaryUnit with SiUnit {
  def symbol = "Ω⋅m"
}

object ResistivityConversions {
  lazy val ohmMeter = OhmMeters(1)

  implicit class ResistivityConversions[A](n: A)(implicit num: Numeric[A]) {
    def ohmMeters = OhmMeters(n)
  }

  implicit object ResistivityNumeric extends AbstractQuantityNumeric[Resistivity](Resistivity.primaryUnit)
}
