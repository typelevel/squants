/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.TypeLevelInt._
import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Density private (val value: Double, val unit: DensityUnit)
    extends Quantity[Density] with DimensionType {
  type Dimension = (Length, _M3) :: (Mass, _1) :: HNil

  def dimension = Density

  def *(that: Volume): Mass = Kilograms(value * that.toCubicMeters)

  def toKilogramsPerCubicMeter = to(KilogramsPerCubicMeter)
}

object Density extends Dimension[Density] {
  private[mass] def apply[A](n: A, unit: DensityUnit)(implicit num: Numeric[A]) = new Density(num.toDouble(n), unit)
  def apply(m: Mass, v: Volume): Density = KilogramsPerCubicMeter(m.toKilograms / v.toCubicMeters)
  def apply = parse _
  def name = "Density"
  def primaryUnit = KilogramsPerCubicMeter
  def siUnit = KilogramsPerCubicMeter
  def units = Set(KilogramsPerCubicMeter)
}

trait DensityUnit extends UnitOfMeasure[Density] with UnitConverter with SiUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = Density(n, this)
}

object KilogramsPerCubicMeter extends DensityUnit with PrimaryUnit {
  val symbol = "kg/m³"
}

object DensityConversions {
  lazy val kilogramPerCubicMeter = KilogramsPerCubicMeter(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerCubicMeter = KilogramsPerCubicMeter(n)
  }

  implicit object DensityNumeric extends AbstractQuantityNumeric[Density](KilogramsPerCubicMeter)
}