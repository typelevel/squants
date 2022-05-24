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

final case class LuminousFlux[A: Numeric] private [squants2]  (value: A, unit: LuminousFluxUnit)
  extends Quantity[A, LuminousFlux.type] {
  override type Q[B] = LuminousFlux[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toLumens: A = to(Lumens)
}

object LuminousFlux extends Dimension("Luminous Flux") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Lumens
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Lumens
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Lumens)

  implicit class LuminousFluxCons[A](a: A)(implicit num: Numeric[A]) {
    def lumens: LuminousFlux[A] = Lumens(a)
  }

  lazy val lumens: LuminousFlux[Int] = Lumens(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LuminousFluxNumeric[A]()
  private case class LuminousFluxNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, LuminousFlux.type], y: Quantity[A, LuminousFlux.type]): Quantity[A, LuminousFlux.this.type] =
      Lumens(x.to(Lumens) * y.to(Lumens))
  }
}

abstract class LuminousFluxUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousFlux.type] {
  override def dimension: LuminousFlux.type = LuminousFlux
  override def apply[A: Numeric](value: A): LuminousFlux[A] = LuminousFlux(value, this)
}

case object Lumens extends LuminousFluxUnit("lm", 1) with PrimaryUnit with SiUnit
