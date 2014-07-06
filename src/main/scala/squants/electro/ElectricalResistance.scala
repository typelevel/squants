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
 * @param value value in [[squants.electro.Ohms]]
 */
final class ElectricalResistance private (val value: Double)
    extends Quantity[ElectricalResistance] {

  def valueUnit = ElectricalResistance.valueUnit

  def *(that: ElectricCurrent): ElectricPotential = Volts(toOhms * that.toAmperes)
  def *(that: Length): Resistivity = OhmMeters(toOhms * that.toMeters)

  def toOhms = to(Ohms)
  def toNanohms = to(Nanohms)
  def toMicrohms = to(Microohms)
  def toMillohms = to(Milliohms)
  def toKilohms = to(Kilohms)
  def toMegohms = to(Megohms)
  def toGigohms = to(Gigohms)

  def inSiemens = Siemens(1.0 / to(Ohms))
}

object ElectricalResistance extends QuantityCompanion[ElectricalResistance] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new ElectricalResistance(num.toDouble(n))
  def apply = parseString _
  def name = "ElectricalResistance"
  def valueUnit = Ohms
  def units = Set(Ohms, Nanohms, Microohms, Milliohms, Kilohms, Megohms, Gigohms)
}

trait ElectricalResistanceUnit extends UnitOfMeasure[ElectricalResistance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricalResistance(convertFrom(n))
}

object Ohms extends ElectricalResistanceUnit with ValueUnit {
  val symbol = "Ω"
}

object Nanohms extends ElectricalResistanceUnit {
  val symbol = "nΩ"
  val conversionFactor = MetricSystem.Nano
}

object Microohms extends ElectricalResistanceUnit {
  val symbol = "µΩ"
  val conversionFactor = MetricSystem.Micro
}

object Milliohms extends ElectricalResistanceUnit {
  val symbol = "mΩ"
  val conversionFactor = MetricSystem.Milli
}

object Kilohms extends ElectricalResistanceUnit {
  val symbol = "kΩ"
  val conversionFactor = MetricSystem.Kilo
}

object Megohms extends ElectricalResistanceUnit {
  val symbol = "MΩ"
  val conversionFactor = MetricSystem.Mega
}

object Gigohms extends ElectricalResistanceUnit {
  val symbol = "GΩ"
  val conversionFactor = MetricSystem.Giga
}

object ElectricalResistanceConversions {
  lazy val ohm = Ohms(1)
  lazy val nanohm = Nanohms(1)
  lazy val microohm = Microohms(1)
  lazy val milliohm = Milliohms(1)
  lazy val kilohm = Kilohms(1)
  lazy val megohm = Megohms(1)
  lazy val gigohm = Gigohms(1)

  implicit class ElectricalResistanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def ohms = Ohms(n)
    def nanohms = Nanohms(n)
    def microohms = Microohms(n)
    def milliohms = Milliohms(n)
    def kilohms = Kilohms(n)
    def megohms = Megohms(n)
    def gigohms = Gigohms(n)
  }

  implicit object ElectricalResistanceNumeric
    extends AbstractQuantityNumeric[ElectricalResistance](ElectricalResistance.valueUnit)
}
