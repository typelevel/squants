/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.{ Seconds, _ }
import squants.space.{ CubicFeet, CubicMeters, Litres, Microlitres, Millilitres, Nanolitres, UsGallons }
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
  def toLitresPerDay = to(LitresPerDay)
  def toLitresPerHour = to(LitresPerHour)
  def toLitresPerMinute = to(LitresPerMinute)
  def toLitresPerSecond = to(LitresPerSecond)
  def toNanolitresPerDay = to(NanolitresPerDay)
  def toNanolitresPerHour = to(NanolitresPerHour)
  def toNanolitresPerMinute = to(NanolitresPerMinute)
  def toNanolitresPerSecond = to(NanolitresPerSecond)
  def toMicrolitresPerDay = to(MicrolitresPerDay)
  def toMicrolitresPerHour = to(MicrolitresPerHour)
  def toMicrolitresPerMinute = to(MicrolitresPerMinute)
  def toMicrolitresPerSecond = to(MicrolitresPerSecond)
  def toMillilitresPerDay = to(MillilitresPerDay)
  def toMillilitresPerHour = to(MillilitresPerHour)
  def toMillilitresPerMinute = to(MillilitresPerMinute)
  def toMillilitresPerSecond = to(MillilitresPerSecond)

  def toCubicFeetPerHour = to(CubicFeetPerHour)
  def toGallonsPerDay = to(GallonsPerDay)
  def toGallonsPerHour = to(GallonsPerHour)
  def toGallonsPerMinute = to(GallonsPerMinute)
  def toGallonsPerSecond = to(GallonsPerSecond)
}

object VolumeFlow extends Dimension[VolumeFlow] {
  private[motion] def apply[A](n: A, unit: VolumeFlowRateUnit)(implicit num: Numeric[A]) = new VolumeFlow(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "VolumeFlow"
  def primaryUnit = CubicMetersPerSecond
  def siUnit = CubicMetersPerSecond
  def units = Set(CubicMetersPerSecond, LitresPerSecond, LitresPerMinute, LitresPerHour, LitresPerDay,
    NanolitresPerSecond, NanolitresPerMinute, NanolitresPerHour, NanolitresPerDay,
    MicrolitresPerSecond, MicrolitresPerMinute, MicrolitresPerHour, MicrolitresPerDay,
    MillilitresPerSecond, MillilitresPerMinute, MillilitresPerHour, MillilitresPerDay,
    CubicFeetPerHour, GallonsPerDay, GallonsPerHour, GallonsPerMinute, GallonsPerSecond)
}

trait VolumeFlowRateUnit extends UnitOfMeasure[VolumeFlow] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = VolumeFlow(n, this)
}

object CubicMetersPerSecond extends VolumeFlowRateUnit with PrimaryUnit with SiUnit {
  val symbol = "m³/s"
}

object LitresPerSecond extends VolumeFlowRateUnit with SiUnit {
  val symbol = "L/s"
  val conversionFactor = Litres.conversionFactor / CubicMeters.conversionFactor
}

object LitresPerMinute extends VolumeFlowRateUnit with SiUnit {
  val symbol = "L/min"
  val conversionFactor = Litres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerMinute
}

object LitresPerHour extends VolumeFlowRateUnit with SiUnit {
  val symbol = "L/h"
  val conversionFactor = Litres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerHour
}

object LitresPerDay extends VolumeFlowRateUnit with SiUnit {
  val symbol = "L/d"
  val conversionFactor = Litres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerDay
}

object NanolitresPerSecond extends VolumeFlowRateUnit with SiUnit {
  val symbol = "nl/s"
  val conversionFactor = Nanolitres.conversionFactor / CubicMeters.conversionFactor
}

object NanolitresPerMinute extends VolumeFlowRateUnit with SiUnit {
  val symbol = "nl/min"
  val conversionFactor = (Nanolitres.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerMinute
}

object NanolitresPerHour extends VolumeFlowRateUnit with SiUnit {
  val symbol = "nl/h"
  val conversionFactor = Nanolitres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerHour
}

object NanolitresPerDay extends VolumeFlowRateUnit with SiUnit {
  val symbol = "nl/d"
  val conversionFactor = Nanolitres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerDay
}

object MicrolitresPerSecond extends VolumeFlowRateUnit with SiUnit {
  val symbol = "µl/s"
  val conversionFactor = Microlitres.conversionFactor / CubicMeters.conversionFactor
}

object MicrolitresPerMinute extends VolumeFlowRateUnit with SiUnit {
  val symbol = "µl/min"
  val conversionFactor = (Microlitres.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerMinute
}

object MicrolitresPerHour extends VolumeFlowRateUnit with SiUnit {
  val symbol = "µl/h"
  val conversionFactor = Microlitres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerHour
}

object MicrolitresPerDay extends VolumeFlowRateUnit with SiUnit {
  val symbol = "µl/d"
  val conversionFactor = Microlitres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerDay
}

object MillilitresPerSecond extends VolumeFlowRateUnit with SiUnit {
  val symbol = "ml/s"
  val conversionFactor = Millilitres.conversionFactor / CubicMeters.conversionFactor
}

object MillilitresPerMinute extends VolumeFlowRateUnit with SiUnit {
  val symbol = "ml/min"
  val conversionFactor = (Millilitres.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerMinute
}

object MillilitresPerHour extends VolumeFlowRateUnit with SiUnit {
  val symbol = "ml/h"
  val conversionFactor = Millilitres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerHour
}

object MillilitresPerDay extends VolumeFlowRateUnit with SiUnit {
  val symbol = "ml/d"
  val conversionFactor = Millilitres.conversionFactor / CubicMeters.conversionFactor / Time.SecondsPerDay
}

object CubicFeetPerHour extends VolumeFlowRateUnit {
  val symbol = "ft³/hr"
  val conversionFactor = (CubicFeet.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerHour
}

object GallonsPerDay extends VolumeFlowRateUnit {
  val symbol = "GPD"
  val conversionFactor = (UsGallons.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerDay
}

object GallonsPerHour extends VolumeFlowRateUnit {
  val symbol = "GPH"
  val conversionFactor = (UsGallons.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerHour
}

object GallonsPerMinute extends VolumeFlowRateUnit {
  val symbol = "GPM"
  val conversionFactor = (UsGallons.conversionFactor / CubicMeters.conversionFactor) / Time.SecondsPerMinute
}

object GallonsPerSecond extends VolumeFlowRateUnit {
  val symbol = "GPS"
  val conversionFactor = UsGallons.conversionFactor / CubicMeters.conversionFactor
}

object VolumeFlowConversions {
  lazy val cubicMeterPerSecond = CubicMetersPerSecond(1)
  lazy val litresPerSecond = LitresPerSecond(1)
  lazy val litersPerSecond = LitresPerSecond(1)
  lazy val litersPerMinute = LitresPerMinute(1)
  lazy val litresPerMinute = LitresPerMinute(1)
  lazy val litersPerHour = LitresPerHour(1)
  lazy val litresPerHour = LitresPerHour(1)
  lazy val litersPerDay = LitresPerDay(1)
  lazy val litresPerDay = LitresPerDay(1)
  lazy val nanolitresPerSecond = NanolitresPerSecond(1)
  lazy val nanolitersPerSecond = NanolitresPerSecond(1)
  lazy val nanolitersPerMinute = NanolitresPerMinute(1)
  lazy val nanolitresPerMinute = NanolitresPerMinute(1)
  lazy val nanolitersPerHour = NanolitresPerHour(1)
  lazy val nanolitresPerHour = NanolitresPerHour(1)
  lazy val nanolitersPerDay = NanolitresPerDay(1)
  lazy val nanolitresPerDay = NanolitresPerDay(1)
  lazy val microlitresPerSecond = MicrolitresPerSecond(1)
  lazy val microlitersPerSecond = MicrolitresPerSecond(1)
  lazy val microlitersPerMinute = MicrolitresPerMinute(1)
  lazy val microlitresPerMinute = MicrolitresPerMinute(1)
  lazy val microlitersPerHour = MicrolitresPerHour(1)
  lazy val microlitresPerHour = MicrolitresPerHour(1)
  lazy val microlitersPerDay = MicrolitresPerDay(1)
  lazy val microlitresPerDay = MicrolitresPerDay(1)
  lazy val millilitresPerSecond = MillilitresPerSecond(1)
  lazy val millilitersPerSecond = MillilitresPerSecond(1)
  lazy val millilitersPerMinute = MillilitresPerMinute(1)
  lazy val millilitresPerMinute = MillilitresPerMinute(1)
  lazy val millilitersPerHour = MillilitresPerHour(1)
  lazy val millilitresPerHour = MillilitresPerHour(1)
  lazy val millilitersPerDay = MillilitresPerDay(1)
  lazy val millilitresPerDay = MillilitresPerDay(1)
  lazy val cubicFeetPerHour = CubicFeetPerHour(1)
  lazy val gallonPerDay = GallonsPerDay(1)
  lazy val gallonPerHour = GallonsPerHour(1)
  lazy val gallonPerMinute = GallonsPerMinute(1)
  lazy val gallonPerSecond = GallonsPerSecond(1)

  implicit class VolumeFlowConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerSecond = CubicMetersPerSecond(n)
    def litresPerSecond = LitresPerSecond(n)
    def litersPerSecond = LitresPerSecond(n)
    def litersPerMinute = LitresPerMinute(n)
    def litresPerMinute = LitresPerMinute(n)
    def litersPerHour = LitresPerHour(n)
    def litresPerHour = LitresPerHour(n)
    def litersPerDay = LitresPerDay(n)
    def litresPerDay = LitresPerDay(n)
    def nanolitresPerSecond = NanolitresPerSecond(n)
    def nanolitersPerSecond = NanolitresPerSecond(n)
    def nanolitersPerMinute = NanolitresPerMinute(n)
    def nanolitresPerMinute = NanolitresPerMinute(n)
    def nanolitersPerHour = NanolitresPerHour(n)
    def nanolitresPerHour = NanolitresPerHour(n)
    def nanolitersPerDay = NanolitresPerDay(n)
    def nanolitresPerDay = NanolitresPerDay(n)
    def microlitresPerSecond = MicrolitresPerSecond(n)
    def microlitersPerSecond = MicrolitresPerSecond(n)
    def microlitersPerMinute = MicrolitresPerMinute(n)
    def microlitresPerMinute = MicrolitresPerMinute(n)
    def microlitersPerHour = MicrolitresPerHour(n)
    def microlitresPerHour = MicrolitresPerHour(n)
    def microlitersPerDay = MicrolitresPerDay(n)
    def microlitresPerDay = MicrolitresPerDay(n)
    def millilitresPerSecond = MillilitresPerSecond(n)
    def millilitersPerSecond = MillilitresPerSecond(n)
    def millilitersPerMinute = MillilitresPerMinute(n)
    def millilitresPerMinute = MillilitresPerMinute(n)
    def millilitersPerHour = MillilitresPerHour(n)
    def millilitresPerHour = MillilitresPerHour(n)
    def millilitersPerDay = MillilitresPerDay(n)
    def millilitresPerDay = MillilitresPerDay(n)
    def cubicFeetPerHour = CubicFeetPerHour(n)
    def gallonsPerDay = GallonsPerDay(n)
    def gallonsPerHour = GallonsPerHour(n)
    def gallonsPerMinute = GallonsPerMinute(n)
    def gallonsPerSecond = GallonsPerSecond(n)
  }

  implicit object VolumeFlowNumeric extends AbstractQuantityNumeric[VolumeFlow](CubicMetersPerSecond)
}
