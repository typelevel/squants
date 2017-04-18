/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.TypeLevelInt._
import squants.space.{ Feet, UsMiles }
import squants.time.{ Seconds, _ }

/**
 * Represents a quantity of acceleration
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Acceleration private (val value: Double, val unit: AccelerationUnit)
    extends Quantity[Acceleration]
    with DimensionType
    with TimeDerivative[Velocity]
    with SecondTimeDerivative[Length]
    with TimeIntegral[Jerk] {

  type Dimension = (Length, _1) :: (Time, _M2) :: HNil
  def dimension = Acceleration

  protected[squants] def timeIntegrated = MetersPerSecond(toMetersPerSecondSquared)
  protected[squants] def timeDerived = MetersPerSecondCubed(toMetersPerSecondSquared)
  protected[squants] def time = Seconds(1)

  def *(that: Mass): Force = Newtons(toMetersPerSecondSquared * that.toKilograms)
  def *(that: TimeSquared): Length = this * that.time1 * that.time2

  def toFeetPerSecondSquared = to(FeetPerSecondSquared)
  def toMetersPerSecondSquared = to(MetersPerSecondSquared)
  def toUsMilesPerHourSquared = to(UsMilesPerHourSquared)
  def toEarthGravities = to(EarthGravities)

  def analyze(distance: Length): (Time, Velocity) = {
    val timeToDistance = (distance * 2 / this).squareRoot
    val finalVelocity = this * timeToDistance
    (timeToDistance, finalVelocity)
  }
  def analyze(accelerationTime: Time): (Length, Velocity) = {
    val finalVelocity = this * accelerationTime
    val distance = this * accelerationTime.squared * 0.5
    (distance, finalVelocity)
  }
  def analyze(velocity: Velocity): (Time, Length) = {
    val timeToVelocity = velocity / this
    val distance = this * timeToVelocity.squared * 0.5
    (timeToVelocity, distance)
  }
}

object Acceleration extends Dimension[Acceleration] {
  private[motion] def apply[A](n: A, unit: AccelerationUnit)(implicit num: Numeric[A]) = new Acceleration(num.toDouble(n), unit)
  def apply = parse _
  def name = "Acceleration"
  def primaryUnit = MetersPerSecondSquared
  def siUnit = MetersPerSecondSquared
  def units = Set(FeetPerSecondSquared, MetersPerSecondSquared, UsMilesPerHourSquared, EarthGravities)
}

/**
 * Base trait for units of [[squants.motion.Acceleration]]
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
trait AccelerationUnit extends UnitOfMeasure[Acceleration] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Acceleration(n, this)
}

object MetersPerSecondSquared extends AccelerationUnit with PrimaryUnit with SiUnit {
  val symbol = "m/s²"
}

object FeetPerSecondSquared extends AccelerationUnit {
  val symbol = "ft/s²"
  val conversionFactor = Feet.conversionFactor * Meters.conversionFactor
}

object UsMilesPerHourSquared extends AccelerationUnit {
  val symbol = "mph²"
  val conversionFactor = (UsMiles.conversionFactor * Meters.conversionFactor) / math.pow(Time.SecondsPerHour, 2)
}

/**
 * Represents acceleration in Earth gravities also knows as G-Force, or g's
 */
object EarthGravities extends AccelerationUnit {
  val symbol = "g"
  val conversionFactor = 9.80665 * Meters.conversionFactor
}

object AccelerationConversions {

  implicit class AccelerationConversions[A](n: A)(implicit num: Numeric[A]) {
    def mpss = MetersPerSecondSquared(n)
    def fpss = FeetPerSecondSquared(n)
  }

  implicit object AccelerationNumeric extends AbstractQuantityNumeric[Acceleration](Acceleration.primaryUnit)
}