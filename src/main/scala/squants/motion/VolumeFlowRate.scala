/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.space.{ Volume, UsGallons, CubicMeters }
import squants._
import squants.time._
import squants.Seconds

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class VolumeFlowRate private (val value: Double, val unit: VolumeFlowRateUnit)
    extends Quantity[VolumeFlowRate]
    with TimeDerivative[Volume] {

  def dimension = VolumeFlowRate

  protected def timeIntegrated = CubicMeters(toCubicMetersPerSecond)
  protected[squants] def time = Seconds(1)

  def toCubicMetersPerSecond = to(CubicMetersPerSecond)
  def toGallonsPerDay = to(GallonsPerDay)
  def toGallonsPerHour = to(GallonsPerHour)
  def toGallonsPerMinute = to(GallonsPerMinute)
  def toGallonsPerSecond = to(GallonsPerSecond)
}

object VolumeFlowRate extends Dimension[VolumeFlowRate] {
  private[motion] def apply[A](n: A, unit: VolumeFlowRateUnit)(implicit num: Numeric[A]) = new VolumeFlowRate(num.toDouble(n), unit)
  def apply = parseString _
  def name = "VolumeFlowRate"
  def primaryUnit = CubicMetersPerSecond
  def siUnit = CubicMetersPerSecond
  def units = Set(CubicMetersPerSecond, GallonsPerDay, GallonsPerHour, GallonsPerMinute, GallonsPerSecond)
}

trait VolumeFlowRateUnit extends UnitOfMeasure[VolumeFlowRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = VolumeFlowRate(n, this)
}

object CubicMetersPerSecond extends VolumeFlowRateUnit with PrimaryUnit with SiUnit {
  val symbol = "m³/s"
}

object GallonsPerDay extends VolumeFlowRateUnit {
  val symbol = "GPD"
  val conversionFactor = (CubicMeters.conversionFactor * UsGallons.conversionFactor) / (Days.conversionFactor / Seconds.conversionFactor)
}

object GallonsPerHour extends VolumeFlowRateUnit {
  val symbol = "GPH"
  val conversionFactor = (CubicMeters.conversionFactor * UsGallons.conversionFactor) / (Hours.conversionFactor / Seconds.conversionFactor)
}

object GallonsPerMinute extends VolumeFlowRateUnit {
  val symbol = "GPM"
  val conversionFactor = (CubicMeters.conversionFactor * UsGallons.conversionFactor) / (Minutes.conversionFactor / Seconds.conversionFactor)
}

object GallonsPerSecond extends VolumeFlowRateUnit {
  val symbol = "GPS"
  val conversionFactor = CubicMeters.conversionFactor * UsGallons.conversionFactor
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
