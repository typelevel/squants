/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */
package squants.photo

import squants._
import squants.time.TimeDerivative

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.Lux]]
 */
final class Illuminance private (val value: Double, val unit: IlluminanceUnit)
    extends Quantity[Illuminance]
    with TimeDerivative[LuminousExposure] {

  def dimension = Illuminance

  protected[squants] def timeIntegrated = LuxSeconds(toLux)
  protected[squants] def time = Seconds(1)

  def *(that: Area): LuminousFlux = Lumens(this.toLux * that.toSquareMeters)

  def toLux = to(Lux)
}

object Illuminance extends Dimension[Illuminance] {
  private[photo] def apply[A](n: A, unit: IlluminanceUnit)(implicit num: Numeric[A]) = new Illuminance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Illuminance"
  def primaryUnit = Lux
  def siUnit = Lux
  def units = Set(Lux)
}

trait IlluminanceUnit extends UnitOfMeasure[Illuminance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Illuminance(n, this)
}

object Lux extends IlluminanceUnit with PrimaryUnit with SiUnit {
  val symbol = "lx"
}

object IlluminanceConversions {
  lazy val lux = Lux(1)

  implicit class IlluminanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def lux = Lux(n)
  }

  implicit object IlluminanceNumeric extends AbstractQuantityNumeric[Illuminance](Illuminance.primaryUnit)
}
