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

final case class Permittivity[A: Numeric] private[squants2] (value: A, unit: PermittivityUnit)
  extends Quantity[A, Permittivity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): Capacitance[A] = ???
  // END CUSTOM OPS

  def toFaradsPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](FaradsPerMeter)
}

object Permittivity extends Dimension[Permittivity]("Permittivity") {

  override def primaryUnit: UnitOfMeasure[Permittivity] with PrimaryUnit[Permittivity] = FaradsPerMeter
  override def siUnit: UnitOfMeasure[Permittivity] with SiUnit[Permittivity] = FaradsPerMeter
  override lazy val units: Set[UnitOfMeasure[Permittivity]] = 
    Set(FaradsPerMeter)

  implicit class PermittivityCons[A](a: A)(implicit num: Numeric[A]) {
    def faradsPerMeter: Permittivity[A] = FaradsPerMeter(a)
  }

  lazy val faradsPerMeter: Permittivity[Int] = FaradsPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Permittivity] = PermittivityNumeric[A]()
  private case class PermittivityNumeric[A: Numeric]() extends QuantityNumeric[A, Permittivity](this) {
    override def times(x: Quantity[A, Permittivity], y: Quantity[A, Permittivity]): Quantity[A, Permittivity] =
      FaradsPerMeter(x.to(FaradsPerMeter) * y.to(FaradsPerMeter))
  }
}

abstract class PermittivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Permittivity] {
  override def dimension: Dimension[Permittivity] = Permittivity
  override def apply[A: Numeric](value: A): Permittivity[A] = Permittivity(value, this)
}

case object FaradsPerMeter extends PermittivityUnit("F/m", 1) with PrimaryUnit[Permittivity] with SiUnit[Permittivity]
