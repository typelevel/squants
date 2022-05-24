/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class LuminousExposure[A: Numeric] private [squants2]  (value: A, unit: LuminousExposureUnit)
  extends Quantity[A, LuminousExposure.type] {
  override type Q[B] = LuminousExposure[B]

  // BEGIN CUSTOM OPS
  //  def /[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: Frequency[B])(implicit f: B => A): Quantity[A] = ???
  // END CUSTOM OPS

  def toLuxSeconds: A = to(LuxSeconds)
}

object LuminousExposure extends Dimension("Luminous Exposure") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = LuxSeconds
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = LuxSeconds
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(LuxSeconds)

  implicit class LuminousExposureCons[A](a: A)(implicit num: Numeric[A]) {
    def luxSeconds: LuminousExposure[A] = LuxSeconds(a)
  }

  lazy val luxSeconds: LuminousExposure[Int] = LuxSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LuminousExposureNumeric[A]()
  private case class LuminousExposureNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, LuminousExposure.type], y: Quantity[A, LuminousExposure.type]): Quantity[A, LuminousExposure.this.type] =
      LuxSeconds(x.to(LuxSeconds) * y.to(LuxSeconds))
  }
}

abstract class LuminousExposureUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousExposure.type] {
  override def dimension: LuminousExposure.type = LuminousExposure
  override def apply[A: Numeric](value: A): LuminousExposure[A] = LuminousExposure(value, this)
}

case object LuxSeconds extends LuminousExposureUnit("lx⋅s", 1) with PrimaryUnit with SiUnit
