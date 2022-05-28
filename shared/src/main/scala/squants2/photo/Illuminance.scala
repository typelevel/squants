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

final case class Illuminance[A: Numeric] private[squants2] (value: A, unit: IlluminanceUnit)
  extends Quantity[A, Illuminance] with TimeDerivative[A, LuminousExposure] {

  override protected[squants2] def timeIntegrated: LuminousExposure[A] = LuxSeconds(num.one)
  override protected[squants2] def derivativeTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: Area[B])(implicit f: B => A): LuminousFlux[A] = ???
  // END CUSTOM OPS

  def toLux[B: Numeric](implicit f: A => B): B = toNum[B](Lux)
}

object Illuminance extends Dimension[Illuminance]("Illuminance") {

  override def primaryUnit: UnitOfMeasure[Illuminance] with PrimaryUnit[Illuminance] = Lux
  override def siUnit: UnitOfMeasure[Illuminance] with SiUnit[Illuminance] = Lux
  override lazy val units: Set[UnitOfMeasure[Illuminance]] = 
    Set(Lux)

  implicit class IlluminanceCons[A](a: A)(implicit num: Numeric[A]) {
    def lux: Illuminance[A] = Lux(a)
  }

  lazy val lux: Illuminance[Int] = Lux(1)

}

abstract class IlluminanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Illuminance] {
  override def dimension: Dimension[Illuminance] = Illuminance
  override def apply[A: Numeric](value: A): Illuminance[A] = Illuminance(value, this)
}

case object Lux extends IlluminanceUnit("lx", 1) with PrimaryUnit[Illuminance] with SiUnit[Illuminance]
