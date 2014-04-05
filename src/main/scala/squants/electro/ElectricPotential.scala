/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.energy.{ Joules, Watts }
import squants.time.{ Seconds, TimeDerivative }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Volts]]
 */
final class ElectricPotential private (val value: Double) extends Quantity[ElectricPotential]
    with PhysicalQuantity with TimeDerivative[MagneticFlux] {

  def valueUnit = Volts
  def time = Seconds(1)
  def change = Webers(value)

  def *(that: ElectricCurrent): Power = Watts(toVolts * that.toAmperes)
  def *(that: Capacitance): ElectricCharge = Coulombs(toVolts * that.toFarads)
  def *(that: ElectricCharge): Energy = Joules(toVolts * that.toCoulombs)

  def /(that: ElectricCurrent): ElectricalResistance = Ohms(toVolts / that.toAmperes)
  def /(that: ElectricalResistance): ElectricCurrent = Amperes(toVolts / that.toOhms)
  def /(that: Length) = ??? // returns ElectricFieldStrength

  def toVolts = to(Volts)
  def toMircovolts = to(Microvolts)
  def toMillivolts = to(Millivolts)
  def toKilovolts = to(Kilovolts)
  def toMegavolts = to(Megavolts)
}

object ElectricPotential {
  private[electro] def apply(value: Double) = new ElectricPotential(value)
}

trait ElectricPotentialUnit extends UnitOfMeasure[ElectricPotential] with UnitMultiplier {
  def apply(d: Double) = ElectricPotential(convertFrom(d))
}

object Volts extends ElectricPotentialUnit with ValueUnit {
  val symbol = "V"
}

object Microvolts extends ElectricPotentialUnit {
  val symbol = "μV"
  val multiplier = MetricSystem.Micro
}

object Millivolts extends ElectricPotentialUnit {
  val symbol = "mV"
  val multiplier = MetricSystem.Milli
}

object Kilovolts extends ElectricPotentialUnit {
  val symbol = "kV"
  val multiplier = MetricSystem.Kilo
}

object Megavolts extends ElectricPotentialUnit {
  val symbol = "MV"
  val multiplier = MetricSystem.Mega
}

object ElectricPotentialConversions {
  lazy val volt = Volts(1)
  lazy val microvolt = Microvolts(1)
  lazy val millivolt = Millivolts(1)
  lazy val kilovolt = Kilovolts(1)
  lazy val megavolt = Megavolts(1)

  implicit class ElectricPotentialConversions(d: Double) {
    def V = Volts(d)
    def volts = Volts(d)
    def microvolts = Microvolts(d)
    def millivolts = Millivolts(d)
    def kilovolts = Kilovolts(d)
    def megavolts = Megavolts(d)
  }

  implicit object ElectricPotentialNumeric extends AbstractQuantityNumeric[ElectricPotential](Volts)
}

