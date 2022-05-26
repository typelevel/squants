/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class ParticleFlux[A: Numeric] private[squants2] (value: A, unit: ParticleFluxUnit)
  extends Quantity[A, ParticleFlux] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: AreaTime[B])(implicit f: B => A): Activity[A] = ???
  //  def *[B](that: Energy[B])(implicit f: B => A): Irradiance[A] = ???
  // END CUSTOM OPS

  def toBecquerelsPerSquareMeterSecond[B: Numeric](implicit f: A => B): B = toNum[B](BecquerelsPerSquareMeterSecond)
  def toBecquerelsPerSquareCentimeterSecond[B: Numeric](implicit f: A => B): B = toNum[B](BecquerelsPerSquareCentimeterSecond)
}

object ParticleFlux extends Dimension[ParticleFlux]("Particle Flux") {

  override def primaryUnit: UnitOfMeasure[ParticleFlux] with PrimaryUnit[ParticleFlux] = BecquerelsPerSquareMeterSecond
  override def siUnit: UnitOfMeasure[ParticleFlux] with SiUnit[ParticleFlux] = BecquerelsPerSquareMeterSecond
  override lazy val units: Set[UnitOfMeasure[ParticleFlux]] = 
    Set(BecquerelsPerSquareMeterSecond, BecquerelsPerSquareCentimeterSecond)

  implicit class ParticleFluxCons[A](a: A)(implicit num: Numeric[A]) {
    def becquerelsPerSquareMeterSecond: ParticleFlux[A] = BecquerelsPerSquareMeterSecond(a)
    def becquerelsPerSquareCentimeterSecond: ParticleFlux[A] = BecquerelsPerSquareCentimeterSecond(a)
  }

  lazy val becquerelsPerSquareMeterSecond: ParticleFlux[Int] = BecquerelsPerSquareMeterSecond(1)
  lazy val becquerelsPerSquareCentimeterSecond: ParticleFlux[Int] = BecquerelsPerSquareCentimeterSecond(1)

}

abstract class ParticleFluxUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ParticleFlux] {
  override def dimension: Dimension[ParticleFlux] = ParticleFlux
  override def apply[A: Numeric](value: A): ParticleFlux[A] = ParticleFlux(value, this)
}

case object BecquerelsPerSquareMeterSecond extends ParticleFluxUnit("Bq/m²‧s", 1) with PrimaryUnit[ParticleFlux] with SiUnit[ParticleFlux]
case object BecquerelsPerSquareCentimeterSecond extends ParticleFluxUnit("Bq/cm²‧s", 10000)
