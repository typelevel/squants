/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._

/**
 * Represents a quantity of surface tension — force per unit length.
 * SI unit: N/m (same as N/ m = Force / Length)
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.motion.NewtonsPerMeter]]
 */
final class SurfaceTension private (val value: Double, val unit: SurfaceTensionUnit)
    extends Quantity[SurfaceTension] {

  def dimension = SurfaceTension

  def /(that: Time) = PowerPerLength(this.toNewtonsPerMeter / that.toSeconds)

  def toNewtonsPerMeter = to(NewtonsPerMeter)
}

object SurfaceTension extends Dimension[SurfaceTension] {
  private[motion] def apply[A](n: A, unit: SurfaceTensionUnit)(implicit num: Numeric[A]) = new SurfaceTension(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SurfaceTension"
  def primaryUnit = NewtonsPerMeter
  def siUnit = NewtonsPerMeter
  def units = Set(NewtonsPerMeter)
}

trait SurfaceTensionUnit extends UnitOfMeasure[SurfaceTension] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SurfaceTension(n, this)
}

object NewtonsPerMeter extends SurfaceTensionUnit with PrimaryUnit with SiUnit {
  val symbol = "N/m"
}

object SurfaceTensionConversions {
  lazy val newtonPerMeter = NewtonsPerMeter(1)

  implicit class SurfaceTensionConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtonsPerMeter = NewtonsPerMeter(n)
  }

  implicit object SurfaceTensionNumeric
    extends AbstractQuantityNumeric[SurfaceTension](SurfaceTension.primaryUnit)
}
