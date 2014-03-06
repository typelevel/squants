/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import squants._

/**
 * Represents a quantity of frequency, which is the number cycles (count) over time
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param cycles Count the number of cycles per `time`
 * @param time Time
 */
case class Frequency(cycles: Dimensionless, time: Time) extends Quantity[Frequency] with TimeDerivative[Dimensionless] {

  def valueUnit = Hertz
  def value = toHertz
  def change = cycles

  def toHertz = cycles.value / time.toSeconds
  def toKilohertz = cycles.value / MetricSystem.Kilo / time.toSeconds
  def toMegahertz = cycles.value / MetricSystem.Mega / time.toSeconds
  def toGigahertz = cycles.value / MetricSystem.Giga / time.toSeconds
  def toTerahertz = cycles.value / MetricSystem.Tera / time.toSeconds
  def toRevolutionsPerMinute = cycles.value / time.toMinutes
}

trait FrequencyUnit extends UnitOfMeasure[Frequency] with UnitMultiplier

object Hertz extends FrequencyUnit with ValueUnit {
  val symbol = "Hz"
  def apply(cycles: Double) = Frequency(Dimensionless(cycles), Seconds(1))
}

object Kilohertz extends FrequencyUnit {
  val multiplier = MetricSystem.Kilo
  val symbol = "kHz"
  def apply(cycles: Double) = Frequency(Dimensionless(cycles * MetricSystem.Kilo), Seconds(1))
}

object Megahertz extends FrequencyUnit {
  val multiplier = MetricSystem.Mega
  val symbol = "MHz"
  def apply(cycles: Double) = Frequency(Dimensionless(cycles * MetricSystem.Mega), Seconds(1))
}

object Gigahertz extends FrequencyUnit {
  val multiplier = MetricSystem.Giga
  val symbol = "GHz"
  def apply(cycles: Double) = Frequency(Dimensionless(cycles * MetricSystem.Giga), Seconds(1))
}

object Terahertz extends FrequencyUnit {
  val multiplier = MetricSystem.Tera
  val symbol = "THz"
  def apply(cycles: Double) = Frequency(Dimensionless(cycles * MetricSystem.Tera), Seconds(1))
}

object RevolutionsPerMinute extends FrequencyUnit {
  val multiplier = 1d / 60
  val symbol = "rpm"
  def apply(revolutions: Double) = Frequency(Dimensionless(revolutions), Minutes(1))
}

object FrequencyConversions {
  implicit class FrequencyConversions(d: Double) {
    def hertz = Hertz(d)
    def kilohertz = Kilohertz(d)
    def megahertz = Megahertz(d)
    def gigahertz = Gigahertz(d)
    def terahertz = Terahertz(d)
    def rpm = RevolutionsPerMinute(d)
  }
}
