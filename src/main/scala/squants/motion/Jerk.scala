/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ Seconds, TimeDerivative }
import squants.space.Feet

/**
 * Represents the third time derivative of position after Velocity and Acceleration
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Jerk private (val value: Double) extends Quantity[Jerk]
    with TimeDerivative[Acceleration] {

  def change = MetersPerSecondSquared(value)
  def time = Seconds(1)

  def valueUnit = Jerk.valueUnit

  def toMetersPerSecondCubed = change.toMetersPerSecondSquared / time.toSeconds
  def toFeetPerSecondCubed = change.toFeetPerSecondSquared / time.toSeconds
}

object Jerk extends QuantityCompanion[Jerk] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new Jerk(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "Jerk"
  def valueUnit = MetersPerSecondCubed
  def units = Set(MetersPerSecondCubed, FeetPerSecondCubed)
}

trait JerkUnit extends UnitOfMeasure[Jerk] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Jerk(convertFrom(n))
  def unapply(jerk: Jerk) = Some(jerk.to(this))
}

object MetersPerSecondCubed extends JerkUnit with ValueUnit {
  val symbol = "m/s³"
}
object FeetPerSecondCubed extends JerkUnit {
  val symbol = "ft/s³"
  val multiplier = Meters.multiplier * Feet.multiplier
}

object JerkConversions {
  lazy val meterPerSecondCubed = MetersPerSecondCubed(1)
  lazy val footPerSecondCubed = FeetPerSecondCubed(1)

  implicit class JerkConversions[A](n: A)(implicit num: Numeric[A]) {
    def metersPerSecondCubed = MetersPerSecondCubed(n)
    def feetPerSecondCubed = FeetPerSecondCubed(n)
  }

  implicit object JerkNumeric extends AbstractQuantityNumeric[Jerk](Jerk.valueUnit)
}
