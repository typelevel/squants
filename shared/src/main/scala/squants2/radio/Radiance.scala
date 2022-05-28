/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class Radiance[A: Numeric] private[squants2] (value: A, unit: RadianceUnit)
  extends Quantity[A, Radiance] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): RadiantIntensity[A] = ???
  //  def /[B](that: RadiantIntensity[B])(implicit f: B => A): Area[A] = ???
  // END CUSTOM OPS

  def toWattsPerSteradianPerSquareMeter[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerSteradianPerSquareMeter)
}

object Radiance extends Dimension[Radiance]("Radiance") {

  override def primaryUnit: UnitOfMeasure[Radiance] with PrimaryUnit[Radiance] = WattsPerSteradianPerSquareMeter
  override def siUnit: UnitOfMeasure[Radiance] with SiUnit[Radiance] = WattsPerSteradianPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[Radiance]] = 
    Set(WattsPerSteradianPerSquareMeter)

  implicit class RadianceCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerSquareMeter: Radiance[A] = WattsPerSteradianPerSquareMeter(a)
  }

  lazy val wattPerSteradianPerSquareMeter: Radiance[Int] = WattsPerSteradianPerSquareMeter(1)

}

abstract class RadianceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Radiance] {
  override def dimension: Dimension[Radiance] = Radiance
  override def apply[A: Numeric](value: A): Radiance[A] = Radiance(value, this)
}

case object WattsPerSteradianPerSquareMeter extends RadianceUnit("W/sr/m²", 1) with PrimaryUnit[Radiance] with SiUnit[Radiance]
