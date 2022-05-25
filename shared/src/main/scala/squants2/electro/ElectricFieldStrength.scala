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

final case class ElectricFieldStrength[A: Numeric] private [squants2]  (value: A, unit: ElectricFieldStrengthUnit)
  extends Quantity[A, ElectricFieldStrength.type] {
  override type Q[B] = ElectricFieldStrength[B]

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): ElectricPotential[A] = ???
  // END CUSTOM OPS

  def toVoltsPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](VoltsPerMeter)
}

object ElectricFieldStrength extends Dimension("Electric Field Strength") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = VoltsPerMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = VoltsPerMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(VoltsPerMeter)

  implicit class ElectricFieldStrengthCons[A](a: A)(implicit num: Numeric[A]) {
    def voltsPerMeter: ElectricFieldStrength[A] = VoltsPerMeter(a)
  }

  lazy val voltsPerMeter: ElectricFieldStrength[Int] = VoltsPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricFieldStrengthNumeric[A]()
  private case class ElectricFieldStrengthNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricFieldStrength.type], y: Quantity[A, ElectricFieldStrength.type]): Quantity[A, ElectricFieldStrength.this.type] =
      VoltsPerMeter(x.to(VoltsPerMeter) * y.to(VoltsPerMeter))
  }
}

abstract class ElectricFieldStrengthUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricFieldStrength.type] {
  override def dimension: ElectricFieldStrength.type = ElectricFieldStrength
  override def apply[A: Numeric](value: A): ElectricFieldStrength[A] = ElectricFieldStrength(value, this)
}

case object VoltsPerMeter extends ElectricFieldStrengthUnit("V/m", 1) with PrimaryUnit with SiUnit
