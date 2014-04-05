/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{ UsGallons, VolumeUnit, CubicMeters }
import squants.time._
import squants.Time
import squants.Seconds
import scala.Some

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param volume Volume
 * @param time Time
 */
case class VolumeFlowRate(volume: Volume, time: Time) extends Quantity[VolumeFlowRate] with TimeDerivative[Volume] {

  def valueUnit = CubicMetersPerSecond
  def value = to(valueUnit)
  def change = volume

  def toString(unit: VolumeFlowRateUnit) = to(unit) + " " + unit.symbol

  def to(unit: VolumeFlowRateUnit) = volume / unit.volumeBase / time.to(unit.timeUnit)
  def toCubicMetersPerSecond = to(CubicMetersPerSecond)
  def toGallonsPerDay = to(GallonsPerDay)
  def toGallonsPerHour = to(GallonsPerHour)
  def toGallonsPerMinute = to(GallonsPerMinute)
}

trait VolumeFlowRateUnit extends UnitOfMeasure[VolumeFlowRate] {
  def volumeUnit: VolumeUnit
  def volumeBase: Volume
  def timeUnit: TimeUnit
  def time: Time
  def apply(d: Double): VolumeFlowRate = VolumeFlowRate(volumeUnit(d), time)
  def unapply(flow: VolumeFlowRate) = Some(flow.to(this))
}

object CubicMetersPerSecond extends VolumeFlowRateUnit with ValueUnit {
  val volumeUnit = CubicMeters
  val volumeBase = CubicMeters(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "m³/s"
}

object GallonsPerDay extends VolumeFlowRateUnit {
  val volumeUnit = UsGallons
  val volumeBase = UsGallons(1)
  val timeUnit = Days
  val time = Days(1)
  val symbol = "GPD"
}

object GallonsPerHour extends VolumeFlowRateUnit {
  val volumeUnit = UsGallons
  val volumeBase = UsGallons(1)
  val timeUnit = Hours
  val time = Hours(1)
  val symbol = "GPH"
}

object GallonsPerMinute extends VolumeFlowRateUnit {
  val volumeUnit = UsGallons
  val volumeBase = UsGallons(1)
  val timeUnit = Minutes
  val time = Minutes(1)
  val symbol = "GPM"
}

object VolumeFlowRateConversions {
  lazy val cubicMeterPerSecond = CubicMetersPerSecond(1)
  lazy val gallonPerDay = GallonsPerDay(1)
  lazy val gallonPerHour = GallonsPerHour(1)
  lazy val gallonPerMinute = GallonsPerMinute(1)

  implicit class VolumeFlowRateConversions(val d: Double) {
    def cubicMetersPerSecond = CubicMetersPerSecond(d)
    def gallonsPerDay = GallonsPerDay(d)
    def gallonsPerHour = GallonsPerHour(d)
    def gallonsPerMinute = GallonsPerMinute(d)
  }

  implicit object VolumeFlowRateNumeric extends AbstractQuantityNumeric[VolumeFlowRate](CubicMetersPerSecond)
}
