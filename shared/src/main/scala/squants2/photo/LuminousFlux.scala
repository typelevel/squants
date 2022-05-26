/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._

final case class LuminousFlux[A: Numeric] private[squants2] (value: A, unit: LuminousFluxUnit)
  extends Quantity[A, LuminousFlux] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Illuminance[A] = ???
  //  def /[B](that: Illuminance[B])(implicit f: B => A): Area[A] = ???
  //  def /[B](that: SolidAngle[B])(implicit f: B => A): LuminousIntensity[A] = ???
  //  def /[B](that: LuminousIntensity[B])(implicit f: B => A): SolidAngle[A] = ???
  // END CUSTOM OPS

  def toLumens[B: Numeric](implicit f: A => B): B = toNum[B](Lumens)
}

object LuminousFlux extends Dimension[LuminousFlux]("Luminous Flux") {

  override def primaryUnit: UnitOfMeasure[LuminousFlux] with PrimaryUnit[LuminousFlux] = Lumens
  override def siUnit: UnitOfMeasure[LuminousFlux] with SiUnit[LuminousFlux] = Lumens
  override lazy val units: Set[UnitOfMeasure[LuminousFlux]] = 
    Set(Lumens)

  implicit class LuminousFluxCons[A](a: A)(implicit num: Numeric[A]) {
    def lumens: LuminousFlux[A] = Lumens(a)
  }

  lazy val lumens: LuminousFlux[Int] = Lumens(1)

}

abstract class LuminousFluxUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousFlux] {
  override def dimension: Dimension[LuminousFlux] = LuminousFlux
  override def apply[A: Numeric](value: A): LuminousFlux[A] = LuminousFlux(value, this)
}

case object Lumens extends LuminousFluxUnit("lm", 1) with PrimaryUnit[LuminousFlux] with SiUnit[LuminousFlux]
