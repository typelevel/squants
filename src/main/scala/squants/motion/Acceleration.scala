/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time._
import squants.Time
import squants.time.Seconds
import squants.space.{ UsMiles, Feet }

/**
 * Represents a quantity of acceleration
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Acceleration private (val value: Double)
    extends Quantity[Acceleration]
    with TimeDerivative[Velocity]
    with SecondTimeDerivative[Length]
    with TimeIntegral[Jerk] {

  def valueUnit = Acceleration.valueUnit
  protected def timeIntegrated = MetersPerSecond(toMetersPerSecondSquared)
  protected def timeDerived = MetersPerSecondCubed(toMetersPerSecondSquared)
  protected[squants] def time = Seconds(1)

  def *(that: Mass): Force = Newtons(toMetersPerSecondSquared * that.toKilograms)

  def *(that: TimeSquared): Length = this * that.time1 * that.time2

  def toFeetPerSecondSquared = to(FeetPerSecondSquared)
  def toMetersPerSecondSquared = to(MetersPerSecondSquared)
  def toUsMilesPerHourSquared = to(UsMilesPerHourSquared)
  def toEarthGravities = to(EarthGravities)
}

object Acceleration extends QuantityCompanion[Acceleration] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new Acceleration(num.toDouble(n))
  def apply = parseString _
  def name = "Acceleration"
  def valueUnit = MetersPerSecondSquared
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
  def apply[A](n: A)(implicit num: Numeric[A]) = Acceleration(convertFrom(n))
}

object MetersPerSecondSquared extends AccelerationUnit with ValueUnit {
  val symbol = "m/s²"
}

object FeetPerSecondSquared extends AccelerationUnit {
  val symbol = "ft/s²"
  val conversionFactor = Feet.conversionFactor * Meters.conversionFactor
}

object UsMilesPerHourSquared extends AccelerationUnit {
  val symbol = "mph²"
  val conversionFactor = (UsMiles.conversionFactor * Meters.conversionFactor) / 3600d / 3600d
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

  implicit object AccelerationNumeric extends AbstractQuantityNumeric[Acceleration](Acceleration.valueUnit)
}