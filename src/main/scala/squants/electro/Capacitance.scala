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

  def valueUnit = Capacitance.valueUnit

  def *(that: ElectricPotential): ElectricCharge = Coulombs(this.toFarads * that.toVolts)
  def /(that: Length) = ??? // returns Permittivity

  def toFarads = to(Farads)
  def toPicofarads = to(Picofarads)
  def toNanofarads = to(Nanofarads)
  def toMicrofarads = to(Microfarads)
  def toMillifarads = to(Millifarads)
  def toKilofarads = to(Kilofarads)
}

object Capacitance extends QuantityCompanion[Capacitance] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new Capacitance(num.toDouble(n))
  def apply = parseString _
  def name = "Capacitance"
  def valueUnit = Farads
  def units = Set(Farads, Picofarads, Nanofarads, Microfarads, Millifarads, Kilofarads)
}

trait CapacitanceUnit extends UnitOfMeasure[Capacitance] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Capacitance(convertFrom(n))
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

  implicit class CapacitanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def farads = Farads(n)
    def picofarads = Picofarads(n)
    def nanofarads = Nanofarads(n)
    def microfarads = Microfarads(n)
    def millifarads = Millifarads(n)
    def kilofarads = Kilofarads(n)
  }

  implicit object CapacitanceNumeric extends AbstractQuantityNumeric[Capacitance](Capacitance.valueUnit)
}
