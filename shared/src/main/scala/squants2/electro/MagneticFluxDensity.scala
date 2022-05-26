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

final case class MagneticFluxDensity[A: Numeric] private[squants2] (value: A, unit: MagneticFluxDensityUnit)
  extends Quantity[A, MagneticFluxDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): MagneticFlux[A] = ???
  // END CUSTOM OPS

  def toGauss[B: Numeric](implicit f: A => B): B = toNum[B](Gauss)
  def toTeslas[B: Numeric](implicit f: A => B): B = toNum[B](Teslas)
}

object MagneticFluxDensity extends Dimension[MagneticFluxDensity]("Magnetic Flux Density") {

  override def primaryUnit: UnitOfMeasure[MagneticFluxDensity] with PrimaryUnit[MagneticFluxDensity] = Teslas
  override def siUnit: UnitOfMeasure[MagneticFluxDensity] with SiUnit[MagneticFluxDensity] = Teslas
  override lazy val units: Set[UnitOfMeasure[MagneticFluxDensity]] = 
    Set(Gauss, Teslas)

  implicit class MagneticFluxDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def gauss: MagneticFluxDensity[A] = Gauss(a)
    def teslas: MagneticFluxDensity[A] = Teslas(a)
  }

  lazy val gauss: MagneticFluxDensity[Int] = Gauss(1)
  lazy val teslas: MagneticFluxDensity[Int] = Teslas(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, MagneticFluxDensity] = MagneticFluxDensityNumeric[A]()
  private case class MagneticFluxDensityNumeric[A: Numeric]() extends QuantityNumeric[A, MagneticFluxDensity](this) {
    override def times(x: Quantity[A, MagneticFluxDensity], y: Quantity[A, MagneticFluxDensity]): Quantity[A, MagneticFluxDensity] =
      Teslas(x.to(Teslas) * y.to(Teslas))
  }
}

abstract class MagneticFluxDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MagneticFluxDensity] {
  override def dimension: Dimension[MagneticFluxDensity] = MagneticFluxDensity
  override def apply[A: Numeric](value: A): MagneticFluxDensity[A] = MagneticFluxDensity(value, this)
}

case object Gauss extends MagneticFluxDensityUnit("Gs", 9.999999999999999E-5)
case object Teslas extends MagneticFluxDensityUnit("T", 1) with PrimaryUnit[MagneticFluxDensity] with SiUnit[MagneticFluxDensity]
