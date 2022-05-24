/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class ParticleFlux[A: Numeric] private [squants2]  (value: A, unit: ParticleFluxUnit)
  extends Quantity[A, ParticleFlux.type] {
  override type Q[B] = ParticleFlux[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toBecquerelsPerSquareMeterSecond: A = to(BecquerelsPerSquareMeterSecond)
  def toBecquerelsPerSquareCentimeterSecond: A = to(BecquerelsPerSquareCentimeterSecond)
}

object ParticleFlux extends Dimension("ParticleFlux") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = BecquerelsPerSquareMeterSecond
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = BecquerelsPerSquareMeterSecond
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(BecquerelsPerSquareMeterSecond, BecquerelsPerSquareCentimeterSecond)

  implicit class ParticleFluxCons[A](a: A)(implicit num: Numeric[A]) {
    def becquerelsPerSquareMeterSecond: ParticleFlux[A] = BecquerelsPerSquareMeterSecond(a)
    def becquerelsPerSquareCentimeterSecond: ParticleFlux[A] = BecquerelsPerSquareCentimeterSecond(a)
  }

  lazy val becquerelsPerSquareMeterSecond: ParticleFlux[Int] = BecquerelsPerSquareMeterSecond(1)
  lazy val becquerelsPerSquareCentimeterSecond: ParticleFlux[Int] = BecquerelsPerSquareCentimeterSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ParticleFluxNumeric[A]()
  private case class ParticleFluxNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ParticleFlux.type], y: Quantity[A, ParticleFlux.type]): Quantity[A, ParticleFlux.this.type] =
      BecquerelsPerSquareMeterSecond(x.to(BecquerelsPerSquareMeterSecond) * y.to(BecquerelsPerSquareMeterSecond))
  }
}

abstract class ParticleFluxUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ParticleFlux.type] {
  override lazy val dimension: ParticleFlux.type = ParticleFlux
  override def apply[A: Numeric](value: A): ParticleFlux[A] = ParticleFlux(value, this)
}

case object BecquerelsPerSquareMeterSecond extends ParticleFluxUnit("Bq/m²‧s", 1.0) with PrimaryUnit with SiUnit
case object BecquerelsPerSquareCentimeterSecond extends ParticleFluxUnit("Bq/cm²‧s", 10000.0)
