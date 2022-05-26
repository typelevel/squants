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

final case class ElectricChargeMassRatio[A: Numeric] private[squants2] (value: A, unit: ElectricChargeMassRatioUnit)
  extends Quantity[A, ElectricChargeMassRatio] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Mass[B])(implicit f: B => A): ElectricCharge[A] = ???
  // END CUSTOM OPS

  def toCoulombsPerKilogram[B: Numeric](implicit f: A => B): B = toNum[B](CoulombsPerKilogram)
}

object ElectricChargeMassRatio extends Dimension[ElectricChargeMassRatio]("Electric Charge Mass Ratio") {

  override def primaryUnit: UnitOfMeasure[ElectricChargeMassRatio] with PrimaryUnit[ElectricChargeMassRatio] = CoulombsPerKilogram
  override def siUnit: UnitOfMeasure[ElectricChargeMassRatio] with SiUnit[ElectricChargeMassRatio] = CoulombsPerKilogram
  override lazy val units: Set[UnitOfMeasure[ElectricChargeMassRatio]] = 
    Set(CoulombsPerKilogram)

  implicit class ElectricChargeMassRatioCons[A](a: A)(implicit num: Numeric[A]) {
    def coulombsPerKilogram: ElectricChargeMassRatio[A] = CoulombsPerKilogram(a)
  }

  lazy val coulombsPerKilogram: ElectricChargeMassRatio[Int] = CoulombsPerKilogram(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, ElectricChargeMassRatio] = ElectricChargeMassRatioNumeric[A]()
  private case class ElectricChargeMassRatioNumeric[A: Numeric]() extends QuantityNumeric[A, ElectricChargeMassRatio](this) {
    override def times(x: Quantity[A, ElectricChargeMassRatio], y: Quantity[A, ElectricChargeMassRatio]): Quantity[A, ElectricChargeMassRatio] =
      CoulombsPerKilogram(x.to(CoulombsPerKilogram) * y.to(CoulombsPerKilogram))
  }
}

abstract class ElectricChargeMassRatioUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricChargeMassRatio] {
  override def dimension: Dimension[ElectricChargeMassRatio] = ElectricChargeMassRatio
  override def apply[A: Numeric](value: A): ElectricChargeMassRatio[A] = ElectricChargeMassRatio(value, this)
}

case object CoulombsPerKilogram extends ElectricChargeMassRatioUnit("C/kg", 1) with PrimaryUnit[ElectricChargeMassRatio] with SiUnit[ElectricChargeMassRatio]
