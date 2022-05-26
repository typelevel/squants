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

final case class ElectricFieldStrength[A: Numeric] private[squants2] (value: A, unit: ElectricFieldStrengthUnit)
  extends Quantity[A, ElectricFieldStrength] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): ElectricPotential[A] = ???
  // END CUSTOM OPS

  def toVoltsPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](VoltsPerMeter)
}

object ElectricFieldStrength extends Dimension[ElectricFieldStrength]("Electric Field Strength") {

  override def primaryUnit: UnitOfMeasure[ElectricFieldStrength] with PrimaryUnit[ElectricFieldStrength] = VoltsPerMeter
  override def siUnit: UnitOfMeasure[ElectricFieldStrength] with SiUnit[ElectricFieldStrength] = VoltsPerMeter
  override lazy val units: Set[UnitOfMeasure[ElectricFieldStrength]] = 
    Set(VoltsPerMeter)

  implicit class ElectricFieldStrengthCons[A](a: A)(implicit num: Numeric[A]) {
    def voltsPerMeter: ElectricFieldStrength[A] = VoltsPerMeter(a)
  }

  lazy val voltsPerMeter: ElectricFieldStrength[Int] = VoltsPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, ElectricFieldStrength] = ElectricFieldStrengthNumeric[A]()
  private case class ElectricFieldStrengthNumeric[A: Numeric]() extends QuantityNumeric[A, ElectricFieldStrength](this) {
    override def times(x: Quantity[A, ElectricFieldStrength], y: Quantity[A, ElectricFieldStrength]): Quantity[A, ElectricFieldStrength] =
      VoltsPerMeter(x.to(VoltsPerMeter) * y.to(VoltsPerMeter))
  }
}

abstract class ElectricFieldStrengthUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricFieldStrength] {
  override def dimension: Dimension[ElectricFieldStrength] = ElectricFieldStrength
  override def apply[A: Numeric](value: A): ElectricFieldStrength[A] = ElectricFieldStrength(value, this)
}

case object VoltsPerMeter extends ElectricFieldStrengthUnit("V/m", 1) with PrimaryUnit[ElectricFieldStrength] with SiUnit[ElectricFieldStrength]
