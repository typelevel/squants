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
 * @param value value in [[squants.electro.Farads]]
 */
final class Capacitance private (val value: Double, val unit: CapacitanceUnit)
    extends Quantity[Capacitance] {

  def dimension = Capacitance

  def *(that: ElectricPotential): ElectricCharge = Coulombs(this.toFarads * that.toVolts)
  def /(that: Length): Permittivity = FaradsPerMeter(this.toFarads / that.toMeters)

  def toFarads = to(Farads)
  def toPicofarads = to(Picofarads)
  def toNanofarads = to(Nanofarads)
  def toMicrofarads = to(Microfarads)
  def toMillifarads = to(Millifarads)
  def toKilofarads = to(Kilofarads)
}

object Capacitance extends Dimension[Capacitance] {
  private[electro] def apply[A](n: A, unit: CapacitanceUnit)(implicit num: Numeric[A]) = new Capacitance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Capacitance"
  def primaryUnit = Farads
  def siUnit = Farads
  def units = Set(Farads, Picofarads, Nanofarads, Microfarads, Millifarads, Kilofarads)
}

trait CapacitanceUnit extends UnitOfMeasure[Capacitance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Capacitance(n, this)
}

object Farads extends CapacitanceUnit with PrimaryUnit with SiUnit {
  val symbol = "F"
}

object Picofarads extends CapacitanceUnit with SiUnit {
  val symbol = "pF"
  val conversionFactor = MetricSystem.Pico
}

object Nanofarads extends CapacitanceUnit with SiUnit {
  val symbol = "nF"
  val conversionFactor = MetricSystem.Nano
}

object Microfarads extends CapacitanceUnit with SiUnit {
  val symbol = "μF"
  val conversionFactor = MetricSystem.Micro
}

object Millifarads extends CapacitanceUnit with SiUnit {
  val symbol = "mF"
  val conversionFactor = MetricSystem.Milli
}

object Kilofarads extends CapacitanceUnit with SiUnit {
  val symbol = "kF"
  val conversionFactor = MetricSystem.Kilo
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

  implicit object CapacitanceNumeric extends AbstractQuantityNumeric[Capacitance](Capacitance.primaryUnit)
}
