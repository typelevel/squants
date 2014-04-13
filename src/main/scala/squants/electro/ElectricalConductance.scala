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
 * @param value value in [[squants.electro.Siemens]]
 */
final class ElectricalConductance private (val value: Double)
    extends Quantity[ElectricalConductance] {

  def valueUnit = Siemens

  def /(that: Length): Conductivity = SiemensPerMeter(toSiemens / that.toMeters)
  def /(that: Conductivity): Length = Meters(toSiemens / that.toSiemensPerMeter)

  def toSiemens = to(Siemens)

  def inOhms = Ohms(1.0 / value)
}

object ElectricalConductance {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new ElectricalConductance(num.toDouble(n))
}

trait ElectricalConductanceUnit extends UnitOfMeasure[ElectricalConductance] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricalConductance(convertFrom(n))
}

object Siemens extends ElectricalConductanceUnit with ValueUnit {
  val symbol = "S"
}

object ElectricalConductanceConversions {
  lazy val siemen = Siemens(1)

  implicit class ElectricalConductanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def siemens = Siemens(n)
  }

  implicit object ElectricalConductanceNumeric extends AbstractQuantityNumeric[ElectricalConductance](Siemens)
}
