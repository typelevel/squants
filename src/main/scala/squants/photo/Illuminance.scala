/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
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
final class Illuminance private (val value: Double) extends Quantity[Illuminance]
    with PhysicalQuantity
    with TimeDerivative[LuminousExposure] {

  def valueUnit = Lux
  def change = LuxSeconds(value)
  def time = Seconds(1)

  def *(that: Area): LuminousFlux = Lumens(toLux * that.toSquareMeters)

  def toLux = to(Lux)
}

object Illuminance {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new Illuminance(num.toDouble(n))
}

trait IlluminanceUnit extends UnitOfMeasure[Illuminance] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = Illuminance(convertFrom(n))
}

object Lux extends IlluminanceUnit with ValueUnit {
  val symbol = "lx"
}

object IlluminanceConversions {
  lazy val lux = Lux(1)

  implicit class IlluminanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def lux = Lux(n)
  }

  implicit object IlluminanceNumeric extends AbstractQuantityNumeric[Illuminance](Lux)
}
