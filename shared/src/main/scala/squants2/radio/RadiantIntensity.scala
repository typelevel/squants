/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class RadiantIntensity[A: Numeric] private[squants2] (value: A, unit: RadiantIntensityUnit)
  extends Quantity[A, RadiantIntensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: SolidAngle[B])(implicit f: B => A): Power[A] = ???
  //  def /[B](that: Power[B])(implicit f: B => A): SolidAngle[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): SpectralIntensity[A] = ???
  //  def /[B](that: SpectralIntensity[B])(implicit f: B => A): Length[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Radiance[A] = ???
  //  def /[B](that: Radiance[B])(implicit f: B => A): Area[A] = ???
  // END CUSTOM OPS

  def toWattsPerSteradian[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerSteradian)
}

object RadiantIntensity extends Dimension[RadiantIntensity]("Radiant Intensity") {

  override def primaryUnit: UnitOfMeasure[RadiantIntensity] with PrimaryUnit[RadiantIntensity] = WattsPerSteradian
  override def siUnit: UnitOfMeasure[RadiantIntensity] with SiUnit[RadiantIntensity] = WattsPerSteradian
  override lazy val units: Set[UnitOfMeasure[RadiantIntensity]] = 
    Set(WattsPerSteradian)

  implicit class RadiantIntensityCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerSteradian: RadiantIntensity[A] = WattsPerSteradian(a)
  }

  lazy val wattsPerSteradian: RadiantIntensity[Int] = WattsPerSteradian(1)

}

abstract class RadiantIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[RadiantIntensity] {
  override def dimension: Dimension[RadiantIntensity] = RadiantIntensity
  override def apply[A: Numeric](value: A): RadiantIntensity[A] = RadiantIntensity(value, this)
}

case object WattsPerSteradian extends RadiantIntensityUnit("W/sr", 1) with PrimaryUnit[RadiantIntensity] with SiUnit[RadiantIntensity]
