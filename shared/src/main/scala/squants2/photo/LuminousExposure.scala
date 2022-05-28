/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._
import squants2.time._

final case class LuminousExposure[A: Numeric] private[squants2] (value: A, unit: LuminousExposureUnit)
  extends Quantity[A, LuminousExposure] with TimeIntegral[A, Illuminance] {

  override protected[squants2] def timeDerived: Illuminance[A] = Lux(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  // END CUSTOM OPS

  def toLuxSeconds[B: Numeric](implicit f: A => B): B = toNum[B](LuxSeconds)
}

object LuminousExposure extends Dimension[LuminousExposure]("Luminous Exposure") {

  override def primaryUnit: UnitOfMeasure[LuminousExposure] with PrimaryUnit[LuminousExposure] = LuxSeconds
  override def siUnit: UnitOfMeasure[LuminousExposure] with SiUnit[LuminousExposure] = LuxSeconds
  override lazy val units: Set[UnitOfMeasure[LuminousExposure]] = 
    Set(LuxSeconds)

  implicit class LuminousExposureCons[A](a: A)(implicit num: Numeric[A]) {
    def luxSeconds: LuminousExposure[A] = LuxSeconds(a)
  }

  lazy val luxSeconds: LuminousExposure[Int] = LuxSeconds(1)

}

abstract class LuminousExposureUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousExposure] {
  override def dimension: Dimension[LuminousExposure] = LuminousExposure
  override def apply[A: Numeric](value: A): LuminousExposure[A] = LuminousExposure(value, this)
}

case object LuxSeconds extends LuminousExposureUnit("lx⋅s", 1) with PrimaryUnit[LuminousExposure] with SiUnit[LuminousExposure]
