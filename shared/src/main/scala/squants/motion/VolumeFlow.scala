/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.{ Seconds, _ }
import squants.space.{ CubicFeet, CubicMeters, UsGallons }
import squants.time._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class VolumeFlow private (val value: Double, val unit: VolumeFlowRateUnit)
    extends Quantity[VolumeFlow]
    with TimeDerivative[Volume] {

  def dimension = VolumeFlow

  protected[squants] def timeIntegrated = CubicMeters(toCubicMetersPerSecond)
  protected[squants] def time = Seconds(1)

  def toCubicMetersPerSecond = to(CubicMetersPerSecond)
  def toCubicFeetPerHour = to(CubicFeetPerHour)
  def toGallonsPerDay = to(GallonsPerDay)
  def toGallonsPerHour = to(GallonsPerHour)
  def toGallonsPerMinute = to(GallonsPerMinute)
  def toGallonsPerSecond = to(GallonsPerSecond)
}

object VolumeFlow extends Dimension[VolumeFlow] {
  private[motion] def apply[A](n: A, unit: VolumeFlowRateUnit)(implicit num: Numeric[A]) = new VolumeFlow(num.toDouble(n), unit)
  def apply = parse _
  def name = "VolumeFlow"
  def primaryUnit = CubicMetersPerSecond
  def siUnit = CubicMetersPerSecond
  def units = Set(CubicMetersPerSecond, CubicFeetPerHour, GallonsPerDay, GallonsPerHour, GallonsPerMinute, GallonsPerSecond)
}

trait VolumeFlowRateUnit extends UnitOfMeasure[VolumeFlow] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = VolumeFlow(n, this)
}

object CubicMetersPerSecond extends VolumeFlowRateUnit with PrimaryUnit with SiUnit {
  val symbol = "m³/s"
}

object CubicFeetPerHour extends VolumeFlowRateUnit {
  val symbol = "ft³/hr"
  val conversionFactor = (CubicMeters.conversionFactor * CubicFeet.conversionFactor) / Time.SecondsPerHour
}

object GallonsPerDay extends VolumeFlowRateUnit {
  val symbol = "GPD"
  val conversionFactor = (CubicMeters.conversionFactor * UsGallons.conversionFactor) / Time.SecondsPerDay
}

object GallonsPerHour extends VolumeFlowRateUnit {
  val symbol = "GPH"
  val conversionFactor = (CubicMeters.conversionFactor * UsGallons.conversionFactor) / Time.SecondsPerHour
}

object GallonsPerMinute extends VolumeFlowRateUnit {
  val symbol = "GPM"
  val conversionFactor = (CubicMeters.conversionFactor * UsGallons.conversionFactor) / Time.SecondsPerMinute
}

object GallonsPerSecond extends VolumeFlowRateUnit {
  val symbol = "GPS"
  val conversionFactor = CubicMeters.conversionFactor * UsGallons.conversionFactor
}

object VolumeFlowConversions {
  lazy val cubicMeterPerSecond = CubicMetersPerSecond(1)
  lazy val cubicFeetPerHour = CubicFeetPerHour(1)
  lazy val gallonPerDay = GallonsPerDay(1)
  lazy val gallonPerHour = GallonsPerHour(1)
  lazy val gallonPerMinute = GallonsPerMinute(1)
  lazy val gallonPerSecond = GallonsPerSecond(1)

  implicit class VolumeFlowConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerSecond = CubicMetersPerSecond(n)
    def cubicFeetPerHour = CubicFeetPerHour(n)
    def gallonsPerDay = GallonsPerDay(n)
    def gallonsPerHour = GallonsPerHour(n)
    def gallonsPerMinute = GallonsPerMinute(n)
    def gallonsPerSecond = GallonsPerSecond(n)
  }

  implicit object VolumeFlowNumeric extends AbstractQuantityNumeric[VolumeFlow](CubicMetersPerSecond)
}
