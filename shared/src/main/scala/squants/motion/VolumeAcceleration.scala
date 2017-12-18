/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2017, Transcriptic, Inc.                                         **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.space._
import squants.{ AbstractQuantityNumeric, Dimension, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure }
import squants.time.{ SecondTimeDerivative, Seconds, TimeDerivative, TimeSquared }

/**
  * Represents the time rate of change of VolumeFlow i.e. volume per time per time.
  *
  * @author  Phil Sung
  * @since   1.4
  *
  * @param value Double
  */
final class VolumeAcceleration private (val value: Double, val unit: VolumeAccelerationUnit)
    extends Quantity[VolumeAcceleration]
    with TimeDerivative[VolumeFlow]
    with SecondTimeDerivative[Volume] {

  def dimension = VolumeAcceleration

  protected[squants] def timeIntegrated = CubicMetersPerSecond(toCubicMetersPerSecondSquared)
  protected[squants] def time = Seconds(1)

  def *(that: TimeSquared): Volume = this * that.time1 * that.time2

  def toCubicMetersPerSecondSquared = to(CubicMetersPerSecondSquared)
  def toLitresPerSecondSquared = to(LitresPerSecondSquared)
  def toMillilitresPerSecondSquared = to(MillilitresPerSecondSquared)
  def toMicrolitresPerSecondSquared = to(MicrolitresPerSecondSquared)
}

object VolumeAcceleration extends Dimension[VolumeAcceleration] {
  private[motion] def apply[A](n: A, unit: VolumeAccelerationUnit)(implicit num: Numeric[A]) = {
    new VolumeAcceleration(num.toDouble(n), unit)
  }
  def apply = parse _
  def name = "VolumeAcceleration"
  def primaryUnit = CubicMetersPerSecondSquared
  def siUnit = CubicMetersPerSecondSquared
  def units = Set(
    CubicMetersPerSecondSquared,
    LitresPerSecondSquared,
    MillilitresPerSecondSquared,
    MicrolitresPerSecondSquared
  )
}

/**
  * Base trait for units of [[squants.motion.VolumeAcceleration]]
  *
  * @author  Phil Sung
  * @since   1.4
  */
trait VolumeAccelerationUnit extends UnitOfMeasure[VolumeAcceleration] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = VolumeAcceleration(n, this)
}

object CubicMetersPerSecondSquared extends VolumeAccelerationUnit with PrimaryUnit with SiUnit {
  val symbol = "m³/s²"
}

object LitresPerSecondSquared extends VolumeAccelerationUnit {
  val symbol = "L/s²"
  val conversionFactor = Litres.conversionFactor / CubicMeters.conversionFactor
}

object MillilitresPerSecondSquared extends VolumeAccelerationUnit {
  val symbol = "mL/s²"
  val conversionFactor = Millilitres.conversionFactor / CubicMeters.conversionFactor
}

object MicrolitresPerSecondSquared extends VolumeAccelerationUnit {
  val symbol = "µL/s²"
  val conversionFactor = Microlitres.conversionFactor / CubicMeters.conversionFactor
}

object VolumeAccelerationConversions {
  lazy val cubicMetersPerSecondSquared = CubicMetersPerSecondSquared(1)
  lazy val litresPerSecondSquared = LitresPerSecondSquared(1)
  lazy val millilitresPerSecondSquared = MillilitresPerSecondSquared(1)
  lazy val microlitresPerSecondSquared = MicrolitresPerSecondSquared(1)

  implicit class VolumeFlowConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMetersPerSecondSquared = CubicMetersPerSecondSquared(n)
    def litresPerSecondSquared = LitresPerSecondSquared(n)
    def millilitresPerSecondSquared = MillilitresPerSecondSquared(n)
    def microlitresPerSecondSquared = MicrolitresPerSecondSquared(n)
  }

  implicit object VolumeAccelerationNumeric extends AbstractQuantityNumeric[VolumeAcceleration](
    VolumeAcceleration.primaryUnit
  )
}
