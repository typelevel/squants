/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.energy.Joules
import squants.time.{ TimeIntegral, Time }
import squants.Time

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Coulombs]]
 */
final class ElectricCharge private (val value: Double)
    extends Quantity[ElectricCharge]
    with TimeIntegral[ElectricCurrent] {

  def valueUnit = Coulombs

  def *(that: ElectricPotential): Energy = Joules(this.toCoulombs * that.toVolts)
  def /(that: Time): ElectricCurrent = Amperes(toCoulombs / that.toSeconds)
  def /(that: ElectricPotential): Capacitance = Farads(toCoulombs / that.toVolts)
  def /(that: Capacitance): ElectricPotential = Volts(toCoulombs / that.toFarads)
  def /(that: ElectricCurrent): Time = that.time * (this / that.change)
  def /(that: Length) = ??? // returns LinearElectricChargeDensity
  def /(that: Area) = ??? // returns AreaElectricChargeDensity
  def /(that: Volume) = ??? // returns ElectricChargeDensity
  def /(that: Mass) = ??? // returns ElectricChargeMassRatio

  def toCoulombs = to(Coulombs)
  def toPicocoulombs = to(Picocoulombs)
  def toNanocoulombs = to(Nanocoulombs)
  def toMicrocoulombs = to(Microcoulombs)
  def toMillcoulombs = to(Millicoulombs)
  def toAbcoulombs = to(Abcoulombs)
  def toAmpereHours = to(AmpereHours)
}

object ElectricCharge {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new ElectricCharge(num.toDouble(n))
}

trait ElectricChargeUnit extends UnitOfMeasure[ElectricCharge] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricCharge(convertFrom(n))
}

object Coulombs extends ElectricChargeUnit with ValueUnit {
  val symbol = "C"
}

object Picocoulombs extends ElectricChargeUnit {
  val symbol = "pC"
  val multiplier = MetricSystem.Pico
}

object Nanocoulombs extends ElectricChargeUnit {
  val symbol = "nC"
  val multiplier = MetricSystem.Nano
}

object Microcoulombs extends ElectricChargeUnit {
  val symbol = "µC"
  val multiplier = MetricSystem.Micro
}

object Millicoulombs extends ElectricChargeUnit {
  val symbol = "mC"
  val multiplier = MetricSystem.Milli
}

object Abcoulombs extends ElectricChargeUnit {
  val symbol = "aC"
  val multiplier = 10d
}

object AmpereHours extends ElectricChargeUnit {
  val symbol = "Ah"
  val multiplier = Time.SecondsPerHour
}

object ElectricChargeConversions {
  lazy val coulomb = Coulombs(1)
  lazy val picocoulomb = Picocoulombs(1)
  lazy val nanocoulomb = Nanocoulombs(1)
  lazy val microcoulomb = Microcoulombs(1)
  lazy val millicoulomb = Millicoulombs(1)
  lazy val abcoulomb = Abcoulombs(1)
  lazy val ampereHour = AmpereHours(1)

  implicit class ElectricalChargeConversions[A](n: A)(implicit num: Numeric[A]) {
    def coulombs = Coulombs(n)
    def picocoulombs = Picocoulombs(n)
    def nanocoulombs = Nanocoulombs(n)
    def microcoulombs = Microcoulombs(n)
    def millicoulombs = Millicoulombs(n)
    def abcoulombs = Abcoulombs(n)
    def ampereHours = AmpereHours(n)
  }

  implicit object ElectricalChargeNumeric
    extends AbstractQuantityNumeric[ElectricCharge](Coulombs)
}
