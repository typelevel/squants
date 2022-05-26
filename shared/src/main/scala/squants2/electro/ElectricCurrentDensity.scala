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

final case class ElectricCurrentDensity[A: Numeric] private[squants2] (value: A, unit: ElectricCurrentDensityUnit)
  extends Quantity[A, ElectricCurrentDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): ElectricCurrent[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): MagneticFieldStrength[A] = ???
  // END CUSTOM OPS

  def toAmperesPerSquareMeter[B: Numeric](implicit f: A => B): B = toNum[B](AmperesPerSquareMeter)
}

object ElectricCurrentDensity extends Dimension[ElectricCurrentDensity]("Electric Current Density") {

  override def primaryUnit: UnitOfMeasure[ElectricCurrentDensity] with PrimaryUnit[ElectricCurrentDensity] = AmperesPerSquareMeter
  override def siUnit: UnitOfMeasure[ElectricCurrentDensity] with SiUnit[ElectricCurrentDensity] = AmperesPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[ElectricCurrentDensity]] = 
    Set(AmperesPerSquareMeter)

  implicit class ElectricCurrentDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def amperesPerSquareMeter: ElectricCurrentDensity[A] = AmperesPerSquareMeter(a)
  }

  lazy val amperesPerSquareMeter: ElectricCurrentDensity[Int] = AmperesPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, ElectricCurrentDensity] = ElectricCurrentDensityNumeric[A]()
  private case class ElectricCurrentDensityNumeric[A: Numeric]() extends QuantityNumeric[A, ElectricCurrentDensity](this) {
    override def times(x: Quantity[A, ElectricCurrentDensity], y: Quantity[A, ElectricCurrentDensity]): Quantity[A, ElectricCurrentDensity] =
      AmperesPerSquareMeter(x.to(AmperesPerSquareMeter) * y.to(AmperesPerSquareMeter))
  }
}

abstract class ElectricCurrentDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricCurrentDensity] {
  override def dimension: Dimension[ElectricCurrentDensity] = ElectricCurrentDensity
  override def apply[A: Numeric](value: A): ElectricCurrentDensity[A] = ElectricCurrentDensity(value, this)
}

case object AmperesPerSquareMeter extends ElectricCurrentDensityUnit("A/m²", 1) with PrimaryUnit[ElectricCurrentDensity] with SiUnit[ElectricCurrentDensity]
