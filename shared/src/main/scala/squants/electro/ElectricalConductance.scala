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
 * @param value value in [[squants.electro.Siemens]]
 */
final class ElectricalConductance private (val value: Double, val unit: ElectricalConductanceUnit)
    extends Quantity[ElectricalConductance] {

  def dimension = ElectricalConductance

  def /(that: Length): Conductivity = SiemensPerMeter(this.toSiemens / that.toMeters)
  def /(that: Conductivity): Length = Meters(this.toSiemens / that.toSiemensPerMeter)

  def toSiemens = to(Siemens)

  def inOhms = Ohms(1.0 / value)
}

object ElectricalConductance extends Dimension[ElectricalConductance] {
  private[electro] def apply[A](n: A, unit: ElectricalConductanceUnit)(implicit num: Numeric[A]) = new ElectricalConductance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricalConductance"
  def primaryUnit = Siemens
  def siUnit = Siemens
  def units = Set(Siemens)
}

trait ElectricalConductanceUnit extends UnitOfMeasure[ElectricalConductance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricalConductance(n, this)
}

object Siemens extends ElectricalConductanceUnit with PrimaryUnit with SiUnit {
  val symbol = "S"
}

object ElectricalConductanceConversions {
  lazy val siemen = Siemens(1)

  implicit class ElectricalConductanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def siemens = Siemens(n)
  }

  implicit object ElectricalConductanceNumeric extends AbstractQuantityNumeric[ElectricalConductance](ElectricalConductance.primaryUnit)
}
