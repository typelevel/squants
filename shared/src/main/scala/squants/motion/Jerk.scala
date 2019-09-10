/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.Feet
import squants.time.{ SecondTimeDerivative, Seconds, TimeDerivative, TimeSquared }

/**
 * Represents the third time derivative of position after Velocity and Acceleration
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Jerk private (val value: Double, val unit: JerkUnit)
    extends Quantity[Jerk]
    with TimeDerivative[Acceleration]
    with SecondTimeDerivative[Velocity] {

  def dimension = Jerk

  protected[squants] def timeIntegrated = MetersPerSecondSquared(toMetersPerSecondCubed)
  protected[squants] def time = Seconds(1)

  def *(that: TimeSquared): Velocity = this * that.time1 * that.time2

  def toMetersPerSecondCubed = to(MetersPerSecondCubed)
  def toFeetPerSecondCubed = to(FeetPerSecondCubed)
}

object Jerk extends Dimension[Jerk] {
  private[motion] def apply[A](n: A, unit: JerkUnit)(implicit num: Numeric[A]) = new Jerk(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Jerk"
  def primaryUnit = MetersPerSecondCubed
  def siUnit = MetersPerSecondCubed
  def units = Set(MetersPerSecondCubed, FeetPerSecondCubed)
}

trait JerkUnit extends UnitOfMeasure[Jerk] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Jerk(n, this)
}

object MetersPerSecondCubed extends JerkUnit with PrimaryUnit with SiUnit {
  val symbol = "m/s³"
}
object FeetPerSecondCubed extends JerkUnit {
  val symbol = "ft/s³"
  val conversionFactor = Feet.conversionFactor / Meters.conversionFactor
}

object JerkConversions {
  lazy val meterPerSecondCubed = MetersPerSecondCubed(1)
  lazy val footPerSecondCubed = FeetPerSecondCubed(1)

  implicit class JerkConversions[A](n: A)(implicit num: Numeric[A]) {
    def metersPerSecondCubed = MetersPerSecondCubed(n)
    def feetPerSecondCubed = FeetPerSecondCubed(n)
  }

  implicit object JerkNumeric extends AbstractQuantityNumeric[Jerk](Jerk.primaryUnit)
}
