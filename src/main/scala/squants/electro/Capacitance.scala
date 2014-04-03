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
 * @param value value in [[squants.electro.Farads]]
 */
final class Capacitance private (val value: Double)
    extends Quantity[Capacitance] {

  def valueUnit = Farads

  def *(that: ElectricPotential): ElectricCharge = Coulombs(this.toFarads * that.toVolts)
  def /(that: Length) = ??? // returns Permittivity

  def toFarads = value
  def toPicofarads = to(Picofarads)
  def toNanofarads = to(Nanofarads)
  def toMicrofarads = to(Microfarads)
  def toMillifarads = to(Millifarads)
  def toKilofarads = to(Kilofarads)
}

object Capacitance {
  private[electro] def apply(value: Double) = new Capacitance(value)
}

trait CapacitanceUnit extends UnitOfMeasure[Capacitance] with UnitMultiplier {
  def apply(d: Double) = Capacitance(convertFrom(d))
}

object Farads extends CapacitanceUnit with ValueUnit {
  val symbol = "F"
}

object Picofarads extends CapacitanceUnit {
  val symbol = "pF"
  val multiplier = MetricSystem.Pico
}

object Nanofarads extends CapacitanceUnit {
  val symbol = "nF"
  val multiplier = MetricSystem.Nano
}

object Microfarads extends CapacitanceUnit {
  val symbol = "μF"
  val multiplier = MetricSystem.Micro
}

object Millifarads extends CapacitanceUnit {
  val symbol = "mF"
  val multiplier = MetricSystem.Milli
}

object Kilofarads extends CapacitanceUnit {
  val symbol = "kF"
  val multiplier = MetricSystem.Kilo
}

object CapacitanceConversions {
  lazy val farad = Farads(1)
  lazy val picofarad = Picofarads(1)
  lazy val nanofarad = Nanofarads(1)
  lazy val microfarad = Microfarads(1)
  lazy val millifarad = Millifarads(1)
  lazy val kilofarad = Kilofarads(1)

  implicit class CapacitanceConversions(d: Double) {
    def farads = Farads(d)
    def picofarads = Picofarads(d)
    def nanofarads = Nanofarads(d)
    def microfarads = Microfarads(d)
    def millifarads = Millifarads(d)
    def kilofarads = Kilofarads(d)
  }

  implicit object CapacitanceNumeric extends AbstractQuantityNumeric[Capacitance](Farads)
}
