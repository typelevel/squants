/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class MagneticFlux[A: Numeric] private[squants2] (value: A, unit: MagneticFluxUnit)
  extends Quantity[A, MagneticFlux] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Area[B])(implicit f: B => A): MagneticFluxDensity[A] = ???
  //  def /[B](that: MagneticFluxDensity[B])(implicit f: B => A): Area[A] = ???
  //  def /[B](that: ElectricCurrent[B])(implicit f: B => A): Inductance[A] = ???
  //  def /[B](that: Inductance[B])(implicit f: B => A): ElectricCurrent[A] = ???
  // END CUSTOM OPS

  def toWebers[B: Numeric](implicit f: A => B): B = toNum[B](Webers)
}

object MagneticFlux extends Dimension[MagneticFlux]("Magnetic Flux") {

  override def primaryUnit: UnitOfMeasure[MagneticFlux] with PrimaryUnit[MagneticFlux] = Webers
  override def siUnit: UnitOfMeasure[MagneticFlux] with SiUnit[MagneticFlux] = Webers
  override lazy val units: Set[UnitOfMeasure[MagneticFlux]] = 
    Set(Webers)

  implicit class MagneticFluxCons[A](a: A)(implicit num: Numeric[A]) {
    def webers: MagneticFlux[A] = Webers(a)
  }

  lazy val webers: MagneticFlux[Int] = Webers(1)

}

abstract class MagneticFluxUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MagneticFlux] {
  override def dimension: Dimension[MagneticFlux] = MagneticFlux
  override def apply[A: Numeric](value: A): MagneticFlux[A] = MagneticFlux(value, this)
}

case object Webers extends MagneticFluxUnit("Wb", 1) with PrimaryUnit[MagneticFlux] with SiUnit[MagneticFlux]
