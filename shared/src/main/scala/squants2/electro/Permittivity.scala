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

final case class Permittivity[A: Numeric] private [squants2]  (value: A, unit: PermittivityUnit)
  extends Quantity[A, Permittivity.type] {
  override type Q[B] = Permittivity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toFaradsPerMeter: A = to(FaradsPerMeter)
}

object Permittivity extends Dimension("Permittivity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = FaradsPerMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = FaradsPerMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(FaradsPerMeter)

  implicit class PermittivityCons[A](a: A)(implicit num: Numeric[A]) {
    def faradsPerMeter: Permittivity[A] = FaradsPerMeter(a)
  }

  lazy val faradsPerMeter: Permittivity[Int] = FaradsPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PermittivityNumeric[A]()
  private case class PermittivityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Permittivity.type], y: Quantity[A, Permittivity.type]): Quantity[A, Permittivity.this.type] =
      FaradsPerMeter(x.to(FaradsPerMeter) * y.to(FaradsPerMeter))
  }
}

abstract class PermittivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Permittivity.type] {
  override lazy val dimension: Permittivity.type = Permittivity
  override def apply[A: Numeric](value: A): Permittivity[A] = Permittivity(value, this)
}

case object FaradsPerMeter extends PermittivityUnit("F/m", 1) with PrimaryUnit with SiUnit
