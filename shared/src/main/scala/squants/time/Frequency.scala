/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import squants._
import squants.electro.{ ElectricCharge, ElectricPotential, MagneticFlux }
import squants.information.{ DataRate, Information }
import squants.motion.{ AngularVelocity, Pressure, PressureChange, Yank }
import squants.photo.{ Illuminance, LuminousEnergy, LuminousExposure, LuminousFlux }

/**
 * Represents a quantity of frequency, which is the number cycles (count) over time
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Frequency private (val value: Double, val unit: FrequencyUnit)
    extends Quantity[Frequency] with TimeDerivative[Dimensionless] {

  def dimension = Frequency

  protected[squants] def timeIntegrated = Each(toHertz)
  protected[squants] def time = Seconds(1)

  def *(that: Acceleration): Jerk = that * this
  def *(that: Angle): AngularVelocity = that * this
  def *(that: Dimensionless): Frequency = this * that.toEach
  def *(that: ElectricCharge): ElectricCurrent = that * this
  def *(that: Energy): Power = that * this
  def *(that: Force): Yank = that * this
  def *(that: Information): DataRate = that * this
  def *(that: Length): Velocity = that * this
  def *(that: LuminousEnergy): LuminousFlux = that * this
  def *(that: LuminousExposure): Illuminance = that * this
  def *(that: MagneticFlux): ElectricPotential = that * this
  def *(that: Mass): MassFlow = that * this
  def *(that: Momentum): Force = that * this
  def *(that: Power): PowerRamp = that * this
  def *(that: Pressure): PressureChange = that * this
  def *(that: Velocity): Acceleration = that * this
  def *(that: Volume): VolumeFlow = that * this

  def toHertz = to(Hertz)
  def toKilohertz = to(Kilohertz)
  def toMegahertz = to(Megahertz)
  def toGigahertz = to(Gigahertz)
  def toTerahertz = to(Terahertz)
  def toRevolutionsPerMinute = to(RevolutionsPerMinute)
}

object Frequency extends Dimension[Frequency] {
  private[time] def apply[A](n: A, unit: FrequencyUnit)(implicit num: Numeric[A]) = new Frequency(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Frequency"
  def primaryUnit = Hertz
  def siUnit = Hertz
  def units = Set(Hertz, Kilohertz, Megahertz, Gigahertz, Terahertz, RevolutionsPerMinute)
}

trait FrequencyUnit extends UnitOfMeasure[Frequency] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Frequency(n, this)
}

object Hertz extends FrequencyUnit with PrimaryUnit with SiUnit {
  val symbol = "Hz"
}

object Kilohertz extends FrequencyUnit with SiUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kHz"
}

object Megahertz extends FrequencyUnit with SiUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "MHz"
}

object Gigahertz extends FrequencyUnit with SiUnit {
  val conversionFactor = MetricSystem.Giga
  val symbol = "GHz"
}

object Terahertz extends FrequencyUnit with SiUnit {
  val conversionFactor = MetricSystem.Tera
  val symbol = "THz"
}

object RevolutionsPerMinute extends FrequencyUnit {
  val conversionFactor = 1d / 60d
  val symbol = "rpm"
}

object FrequencyConversions {
  implicit class FrequencyConversions[A](n: A)(implicit num: Numeric[A]) {
    def hertz = Hertz(n)
    def kilohertz = Kilohertz(n)
    def megahertz = Megahertz(n)
    def gigahertz = Gigahertz(n)
    def terahertz = Terahertz(n)
    def rpm = RevolutionsPerMinute(n)
  }

  implicit object FrequencyNumeric extends AbstractQuantityNumeric[Frequency](Frequency.primaryUnit)
}
