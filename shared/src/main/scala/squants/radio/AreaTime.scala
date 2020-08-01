/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants._
import squants.space.SquareMeters

/**
 * @author  Hunter Payne
 *
 * @param value Double
 */
final class AreaTime private (val value: Double, val unit: AreaTimeUnit)
    extends Quantity[AreaTime] {

  def dimension = AreaTime

  def /(that: Area): Time = 
    Seconds(this.toSquareMeterSeconds / that.toSquareMeters)
  def /(that: Time): Area = 
    SquareMeters(this.toSquareMeterSeconds / that.toSeconds)

  def toSquareMeterSeconds = to(SquareMeterSeconds)
  def toSquareCentimeterSeconds = to(SquareCentimeterSeconds)
}

/**
 * Factory singleton for [[squants.radio.AreaTime]] values
 */
object AreaTime extends Dimension[AreaTime] {
  private[radio] def apply[A](n: A, unit: AreaTimeUnit)(
    implicit num: Numeric[A]) = 
    new AreaTime(num.toDouble(n), unit)
  def apply(area: Area, time: Time): AreaTime = 
    SquareMeterSeconds(area.toSquareMeters * time.toSeconds)
  def apply(value: Any) = parse(value)
  def name = "AreaTime"
  def primaryUnit = SquareMeterSeconds
  def siUnit = SquareMeterSeconds
  def units = Set(SquareMeterSeconds, SquareCentimeterSeconds)
}

trait AreaTimeUnit extends UnitOfMeasure[AreaTime] {
  def apply[A](n: A)(implicit num: Numeric[A]) = AreaTime(n, this)
}

object SquareMeterSeconds extends AreaTimeUnit with PrimaryUnit with SiUnit {
  val symbol = "m²‧s"
}

object SquareCentimeterSeconds extends AreaTimeUnit with UnitConverter {
  val symbol = "cm²‧s"
  val conversionFactor = 0.0001
}

object AreaTimeConversions {
  lazy val squareMeterSeconds = SquareMeterSeconds(1)
  lazy val squareCentimeterSeconds = SquareCentimeterSeconds(1)

  implicit class AreaTimeConversions[A](n: A)(implicit num: Numeric[A]) {
    def squareMeterSeconds = SquareMeterSeconds(n)
    def squareCentimeterSeconds = SquareCentimeterSeconds(n)
  }

  implicit object AreaTimeNumeric extends AbstractQuantityNumeric[AreaTime](AreaTime.primaryUnit)
}
