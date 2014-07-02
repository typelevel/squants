/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{ UsGallons, CubicMeters }
import squants.time._
import squants.Seconds
import scala.Some

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class VolumeFlowRate private (val value: Double) extends Quantity[VolumeFlowRate]
    with TimeDerivative[Volume] {

  def valueUnit = CubicMetersPerSecond
  def change = CubicMeters(value)
  def time = Seconds(1)

  def toCubicMetersPerSecond = to(CubicMetersPerSecond)
  def toGallonsPerDay = to(GallonsPerDay)
  def toGallonsPerHour = to(GallonsPerHour)
  def toGallonsPerMinute = to(GallonsPerMinute)
  def toGallonsPerSecond = to(GallonsPerSecond)
}

object VolumeFlowRate extends QuantityCompanion[VolumeFlowRate] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new VolumeFlowRate(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "VolumeFlowRate"
  def valueUnit = CubicMetersPerSecond
  def units = Set(CubicMetersPerSecond, GallonsPerDay, GallonsPerHour, GallonsPerMinute, GallonsPerSecond)
}

trait VolumeFlowRateUnit extends UnitOfMeasure[VolumeFlowRate] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = VolumeFlowRate(convertFrom(n))
}

object CubicMetersPerSecond extends VolumeFlowRateUnit with ValueUnit {
  val symbol = "m³/s"
}

object GallonsPerDay extends VolumeFlowRateUnit {
  val symbol = "GPD"
  val multiplier = (CubicMeters.multiplier * UsGallons.multiplier) / (Days.multiplier / Seconds.multiplier)
}

object GallonsPerHour extends VolumeFlowRateUnit {
  val symbol = "GPH"
  val multiplier = (CubicMeters.multiplier * UsGallons.multiplier) / (Hours.multiplier / Seconds.multiplier)
}

object GallonsPerMinute extends VolumeFlowRateUnit {
  val symbol = "GPM"
  val multiplier = (CubicMeters.multiplier * UsGallons.multiplier) / (Minutes.multiplier / Seconds.multiplier)
}

object GallonsPerSecond extends VolumeFlowRateUnit {
  val symbol = "GPS"
  val multiplier = CubicMeters.multiplier * UsGallons.multiplier
}

object VolumeFlowRateConversions {
  lazy val cubicMeterPerSecond = CubicMetersPerSecond(1)
  lazy val gallonPerDay = GallonsPerDay(1)
  lazy val gallonPerHour = GallonsPerHour(1)
  lazy val gallonPerMinute = GallonsPerMinute(1)
  lazy val gallonPerSecond = GallonsPerSecond(1)

  implicit class VolumeFlowRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerSecond = CubicMetersPerSecond(n)
    def gallonsPerDay = GallonsPerDay(n)
    def gallonsPerHour = GallonsPerHour(n)
    def gallonsPerMinute = GallonsPerMinute(n)
    def gallonsPerSecond = GallonsPerSecond(n)
  }

  implicit object VolumeFlowRateNumeric extends AbstractQuantityNumeric[VolumeFlowRate](CubicMetersPerSecond)
}
