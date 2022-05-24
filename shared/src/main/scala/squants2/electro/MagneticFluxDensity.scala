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

final case class MagneticFluxDensity[A: Numeric] private [squants2]  (value: A, unit: MagneticFluxDensityUnit)
  extends Quantity[A, MagneticFluxDensity.type] {
  override type Q[B] = MagneticFluxDensity[B]

  def toGauss: A = to(Gauss)
  def toTeslas: A = to(Teslas)
}

object MagneticFluxDensity extends Dimension("MagneticFluxDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Teslas
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Teslas
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Gauss, Teslas)

  implicit class MagneticFluxDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def gauss: MagneticFluxDensity[A] = Gauss(a)
    def teslas: MagneticFluxDensity[A] = Teslas(a)
  }

  lazy val gauss: MagneticFluxDensity[Int] = Gauss(1)
  lazy val teslas: MagneticFluxDensity[Int] = Teslas(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = MagneticFluxDensityNumeric[A]()
  private case class MagneticFluxDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, MagneticFluxDensity.type], y: Quantity[A, MagneticFluxDensity.type]): Quantity[A, MagneticFluxDensity.this.type] =
      Teslas(x.to(Teslas) * y.to(Teslas))
  }
}

abstract class MagneticFluxDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MagneticFluxDensity.type] {
  override lazy val dimension: MagneticFluxDensity.type = MagneticFluxDensity
  override def apply[A: Numeric](value: A): MagneticFluxDensity[A] = MagneticFluxDensity(value, this)
}

case object Gauss extends MagneticFluxDensityUnit("Gs", 9.999999999999999E-5)
case object Teslas extends MagneticFluxDensityUnit("T", 1.0) with PrimaryUnit with SiUnit
