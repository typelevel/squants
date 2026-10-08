/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.energy.{Grays, Joules}
import squants.radio.Sieverts
import squants.time.Seconds

/**
 * Represents a quantity of dose rate (dose or specific energy per unit time).
 * SI unit: Gy/s (for specific energy) or Sv/s (for dose).
 *
 * @author  mithunbabbira
 * @since   2.0
 *
 * @param value value in [[squants.energy.GraysPerSecond]]
 */
final class AbsorbedDoseRate private (val value: Double, val unit: AbsorbedDoseRateUnit)
    extends Quantity[AbsorbedDoseRate] {

  def dimension = AbsorbedDoseRate

  def *(that: Time): SpecificEnergy = Grays(this.toGraysPerSecond * that.toSeconds)
  def *(that: Time): Dose = Sieverts(this.toSievertsPerSecond * that.toSeconds)

  def toGraysPerSecond = to(GraysPerSecond)
  def toSievertsPerSecond = to(SievertsPerSecond)
}

object AbsorbedDoseRate extends Dimension[AbsorbedDoseRate] {
  private[energy] def apply[A](n: A, unit: AbsorbedDoseRateUnit)(implicit num: Numeric[A]) = new AbsorbedDoseRate(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "AbsorbedDoseRate"
  def primaryUnit = GraysPerSecond
  def siUnit = GraysPerSecond
  def units = Set(GraysPerSecond, SievertsPerSecond)
}

trait AbsorbedDoseRateUnit extends UnitOfMeasure[AbsorbedDoseRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = AbsorbedDoseRate(n, this)
}

object GraysPerSecond extends AbsorbedDoseRateUnit with PrimaryUnit with SiUnit {
  val symbol = s"${Grays.symbol}/${Seconds.symbol}"
}

object SievertsPerSecond extends AbsorbedDoseRateUnit with SiUnit {
  val symbol = s"${Sieverts.symbol}/${Seconds.symbol}"
}

object AbsorbedDoseRateConversions {
  lazy val grayPerSecond = GraysPerSecond(1)
  lazy val sievertPerSecond = SievertsPerSecond(1)

  implicit class AbsorbedDoseRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def graysPerSecond = GraysPerSecond(n)
    def sievertsPerSecond = SievertsPerSecond(n)
  }

  implicit object AbsorbedDoseRateNumeric extends AbstractQuantityNumeric[AbsorbedDoseRate](AbsorbedDoseRate.primaryUnit)
}
