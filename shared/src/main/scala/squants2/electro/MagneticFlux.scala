/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class MagneticFlux[A: Numeric] private [squants2]  (value: A, unit: MagneticFluxUnit)
  extends Quantity[A, MagneticFlux.type] {
  override type Q[B] = MagneticFlux[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWebers: A = to(Webers)
}

object MagneticFlux extends Dimension("Magnetic Flux") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Webers
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Webers
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Webers)

  implicit class MagneticFluxCons[A](a: A)(implicit num: Numeric[A]) {
    def webers: MagneticFlux[A] = Webers(a)
  }

  lazy val webers: MagneticFlux[Int] = Webers(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = MagneticFluxNumeric[A]()
  private case class MagneticFluxNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, MagneticFlux.type], y: Quantity[A, MagneticFlux.type]): Quantity[A, MagneticFlux.this.type] =
      Webers(x.to(Webers) * y.to(Webers))
  }
}

abstract class MagneticFluxUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MagneticFlux.type] {
  override lazy val dimension: MagneticFlux.type = MagneticFlux
  override def apply[A: Numeric](value: A): MagneticFlux[A] = MagneticFlux(value, this)
}

case object Webers extends MagneticFluxUnit("Wb", 1) with PrimaryUnit with SiUnit
