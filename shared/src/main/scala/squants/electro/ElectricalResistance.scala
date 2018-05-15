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
 * @param value value in [[squants.electro.Ohms]]
 */
final class ElectricalResistance private (val value: Double, val unit: ElectricalResistanceUnit)
    extends Quantity[ElectricalResistance] {

  def dimension = ElectricalResistance

  def *(that: ElectricCurrent): ElectricPotential = Volts(this.toOhms * that.toAmperes)
  def *(that: Length): Resistivity = OhmMeters(this.toOhms * that.toMeters)

  def toOhms = to(Ohms)
  def toNanohms = to(Nanohms)
  def toMicrohms = to(Microohms)
  def toMillohms = to(Milliohms)
  def toKilohms = to(Kilohms)
  def toMegohms = to(Megohms)
  def toGigohms = to(Gigohms)

  def inSiemens = Siemens(1.0 / to(Ohms))
}

object ElectricalResistance extends Dimension[ElectricalResistance] {
  private[electro] def apply[A](n: A, unit: ElectricalResistanceUnit)(implicit num: Numeric[A]) = new ElectricalResistance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricalResistance"
  def primaryUnit = Ohms
  def siUnit = Ohms
  def units = Set(Ohms, Nanohms, Microohms, Milliohms, Kilohms, Megohms, Gigohms)
}

trait ElectricalResistanceUnit extends UnitOfMeasure[ElectricalResistance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricalResistance(n, this)
}

object Ohms extends ElectricalResistanceUnit with PrimaryUnit with SiUnit {
  val symbol = "Ω"
}

object Nanohms extends ElectricalResistanceUnit with SiUnit {
  val symbol = "nΩ"
  val conversionFactor = MetricSystem.Nano
}

object Microohms extends ElectricalResistanceUnit with SiUnit {
  val symbol = "µΩ"
  val conversionFactor = MetricSystem.Micro
}

object Milliohms extends ElectricalResistanceUnit with SiUnit {
  val symbol = "mΩ"
  val conversionFactor = MetricSystem.Milli
}

object Kilohms extends ElectricalResistanceUnit with SiUnit {
  val symbol = "kΩ"
  val conversionFactor = MetricSystem.Kilo
}

object Megohms extends ElectricalResistanceUnit with SiUnit {
  val symbol = "MΩ"
  val conversionFactor = MetricSystem.Mega
}

object Gigohms extends ElectricalResistanceUnit with SiUnit {
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
    extends AbstractQuantityNumeric[ElectricalResistance](ElectricalResistance.primaryUnit)
}
