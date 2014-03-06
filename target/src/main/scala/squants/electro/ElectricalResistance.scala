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

  def valueUnit = Ohms

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

object ElectricalResistance {
  private[electro] def apply(value: Double) = new ElectricalResistance(value)
}

trait ElectricalResistanceUnit extends UnitOfMeasure[ElectricalResistance] with UnitMultiplier {
  def apply(d: Double) = ElectricalResistance(convertFrom(d))
}

object Ohms extends ElectricalResistanceUnit with ValueUnit {
  val symbol = "Ω"
}

object Nanohms extends ElectricalResistanceUnit {
  val symbol = "nΩ"
  val multiplier = MetricSystem.Nano
}

object Microohms extends ElectricalResistanceUnit {
  val symbol = "µΩ"
  val multiplier = MetricSystem.Micro
}

object Milliohms extends ElectricalResistanceUnit {
  val symbol = "mΩ"
  val multiplier = MetricSystem.Milli
}

object Kilohms extends ElectricalResistanceUnit {
  val symbol = "kΩ"
  val multiplier = MetricSystem.Kilo
}

object Megohms extends ElectricalResistanceUnit {
  val symbol = "MΩ"
  val multiplier = MetricSystem.Mega
}

object Gigohms extends ElectricalResistanceUnit {
  val symbol = "GΩ"
  val multiplier = MetricSystem.Giga
}

object ElectricalResistanceConversions {
  lazy val ohm = Ohms(1)
  lazy val nanohm = Nanohms(1)
  lazy val microohm = Microohms(1)
  lazy val milliohm = Milliohms(1)
  lazy val kilohm = Kilohms(1)
  lazy val megohm = Megohms(1)
  lazy val gigohm = Gigohms(1)

  implicit class ElectricalResistanceConversions(d: Double) {
    def ohms = Ohms(d)
    def nanohms = Nanohms(d)
    def microohms = Microohms(d)
    def milliohms = Milliohms(d)
    def kilohms = Kilohms(d)
    def megohms = Megohms(d)
    def gigohms = Gigohms(d)
  }
}
