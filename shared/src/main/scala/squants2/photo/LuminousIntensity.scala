/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._

final case class LuminousIntensity[A: Numeric] private[squants2] (value: A, unit: LuminousIntensityUnit)
  extends Quantity[A, LuminousIntensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: SolidAngle[B])(implicit f: B => A): LuminousFlux[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Luminance[A] = ???
  //  def /[B](that: Luminance[B])(implicit f: B => A): Area[A] = ???
  // END CUSTOM OPS

  def toCandelas[B: Numeric](implicit f: A => B): B = toNum[B](Candelas)
}

object LuminousIntensity extends BaseDimension[LuminousIntensity]("Luminous Intensity", "J") {

  override def primaryUnit: UnitOfMeasure[LuminousIntensity] with PrimaryUnit[LuminousIntensity] = Candelas
  override def siUnit: UnitOfMeasure[LuminousIntensity] with SiBaseUnit[LuminousIntensity] = Candelas
  override lazy val units: Set[UnitOfMeasure[LuminousIntensity]] = 
    Set(Candelas)

  implicit class LuminousIntensityCons[A](a: A)(implicit num: Numeric[A]) {
    def candelas: LuminousIntensity[A] = Candelas(a)
  }

  lazy val candela: LuminousIntensity[Int] = Candelas(1)

}

abstract class LuminousIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousIntensity] {
  override def dimension: Dimension[LuminousIntensity] = LuminousIntensity
  override def apply[A: Numeric](value: A): LuminousIntensity[A] = LuminousIntensity(value, this)
}

case object Candelas extends LuminousIntensityUnit("cd", 1) with PrimaryUnit[LuminousIntensity] with SiBaseUnit[LuminousIntensity]
