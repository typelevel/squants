/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.energy.Joules
import squants.time.{ Time, TimeIntegral }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Coulombs]]
 */
final class ElectricCharge private (val value: Double, val unit: ElectricChargeUnit)
    extends Quantity[ElectricCharge]
    with TimeIntegral[ElectricCurrent] {

  def dimension = ElectricCharge

  protected def timeDerived = Amperes(toCoulombs)
  protected def time = Seconds(1)

  def *(that: ElectricPotential): Energy = Joules(this.toCoulombs * that.toVolts)
  def /(that: ElectricPotential): Capacitance = Farads(this.toCoulombs / that.toVolts)
  def /(that: Capacitance): ElectricPotential = Volts(this.toCoulombs / that.toFarads)
  def /(that: Length): LinearElectricChargeDensity = CoulombsPerMeter(this.toCoulombs / that.toMeters)
  def /(that: Area): AreaElectricChargeDensity = CoulombsPerSquareMeter(this.toCoulombs / that.toSquareMeters)
  def /(that: Volume): ElectricChargeDensity = CoulombsPerCubicMeter(this.toCoulombs / that.toCubicMeters)
  def /(that: Mass): ElectricChargeMassRatio = CoulombsPerKilogram(this.toCoulombs / that.toKilograms)

  def toCoulombs = to(Coulombs)
  def toPicocoulombs = to(Picocoulombs)
  def toNanocoulombs = to(Nanocoulombs)
  def toMicrocoulombs = to(Microcoulombs)
  def toMillcoulombs = to(Millicoulombs)
  def toAbcoulombs = to(Abcoulombs)
  def toAmpereHours = to(AmpereHours)
  def toMilliampereHours = to(MilliampereHours)
  def toMilliampereSeconds = to(MilliampereSeconds)
}

object ElectricCharge extends Dimension[ElectricCharge] {
  private[electro] def apply[A](n: A, unit: ElectricChargeUnit)(implicit num: Numeric[A]) = new ElectricCharge(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricCharge"
  def primaryUnit = Coulombs
  def siUnit = Coulombs
  def units = Set(Coulombs, Picocoulombs, Nanocoulombs, Microcoulombs, Millicoulombs, Abcoulombs,
    AmpereHours, MilliampereHours, MilliampereSeconds)
}

trait ElectricChargeUnit extends UnitOfMeasure[ElectricCharge] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricCharge(n, this)
}

object Coulombs extends ElectricChargeUnit with PrimaryUnit with SiUnit {
  val symbol = "C"
}

object Picocoulombs extends ElectricChargeUnit with SiUnit {
  val symbol = "pC"
  val conversionFactor = MetricSystem.Pico
}

object Nanocoulombs extends ElectricChargeUnit with SiUnit {
  val symbol = "nC"
  val conversionFactor = MetricSystem.Nano
}

object Microcoulombs extends ElectricChargeUnit with SiUnit {
  val symbol = "µC"
  val conversionFactor = MetricSystem.Micro
}

object Millicoulombs extends ElectricChargeUnit with SiUnit {
  val symbol = "mC"
  val conversionFactor = MetricSystem.Milli
}

object Abcoulombs extends ElectricChargeUnit {
  val symbol = "aC"
  val conversionFactor = 10d
}

object AmpereHours extends ElectricChargeUnit {
  val symbol = "Ah"
  val conversionFactor = Time.SecondsPerHour
}

object MilliampereHours extends ElectricChargeUnit {
  val symbol = "mAh"
  val conversionFactor = AmpereHours.conversionFactor * MetricSystem.Milli
}

object MilliampereSeconds extends ElectricChargeUnit {
  val symbol = "mAs"
  val conversionFactor = Coulombs.conversionFactor * MetricSystem.Milli
}

object ElectricChargeConversions {
  lazy val coulomb = Coulombs(1)
  lazy val picocoulomb = Picocoulombs(1)
  lazy val nanocoulomb = Nanocoulombs(1)
  lazy val microcoulomb = Microcoulombs(1)
  lazy val millicoulomb = Millicoulombs(1)
  lazy val abcoulomb = Abcoulombs(1)
  lazy val ampereHour = AmpereHours(1)
  lazy val milliampereHour = MilliampereHours(1)
  lazy val milliampereSecond = MilliampereSeconds(1)

  implicit class ElectricalChargeConversions[A](n: A)(implicit num: Numeric[A]) {
    def coulombs = Coulombs(n)
    def picocoulombs = Picocoulombs(n)
    def nanocoulombs = Nanocoulombs(n)
    def microcoulombs = Microcoulombs(n)
    def millicoulombs = Millicoulombs(n)
    def abcoulombs = Abcoulombs(n)
    def ampereHours = AmpereHours(n)
    def milliampereHours = MilliampereHours(n)
    def milliampereSeconds = MilliampereSeconds(n)
  }

  implicit object ElectricalChargeNumeric
    extends AbstractQuantityNumeric[ElectricCharge](ElectricCharge.primaryUnit)
}
