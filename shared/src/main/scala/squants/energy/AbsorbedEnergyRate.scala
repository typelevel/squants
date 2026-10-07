/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.time.{ Seconds, Time, TimeDerivative, TimeIntegral }

/**
 * Represents absorbed energy rate — energy per unit time (rate of energy deposition).
 * SI unit: Gy/s (Grays per second), same dimension as Gray/s.
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.energy.GraysPerSecond]]
 */
final class AbsorbedEnergyRate private (val value: Double, val unit: AbsorbedEnergyRateUnit)
    extends Quantity[AbsorbedEnergyRate]
    with TimeDerivative[SpecificEnergy] {

  def dimension = AbsorbedEnergyRate

  protected[squants] def timeIntegrated = Grays(toGraysPerSecond)

  def toGraysPerSecond = to(GraysPerSecond)
}

object AbsorbedEnergyRate extends Dimension[AbsorbedEnergyRate] {
  private[energy] def apply[A](n: A, unit: AbsorbedEnergyRateUnit)(implicit num: Numeric[A]) = new AbsorbedEnergyRate(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "AbsorbedEnergyRate"
  def primaryUnit = GraysPerSecond
  def siUnit = GraysPerSecond
  def units = Set(GraysPerSecond)
}

trait AbsorbedEnergyRateUnit extends UnitOfMeasure[AbsorbedEnergyRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = AbsorbedEnergyRate(n, this)
}

object GraysPerSecond extends AbsorbedEnergyRateUnit with PrimaryUnit with SiUnit {
  val symbol = "Gy/s"
}

object AbsorbedEnergyRateConversions {
  lazy val grayPerSecond = GraysPerSecond(1)

  implicit class AbsorbedEnergyRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def graysPerSecond = GraysPerSecond(n)
  }

  implicit object AbsorbedEnergyRateNumeric
    extends AbstractQuantityNumeric[AbsorbedEnergyRate](AbsorbedEnergyRate.primaryUnit)
}
