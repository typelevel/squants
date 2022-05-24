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

final case class ElectricPotential[A: Numeric] private [squants2]  (value: A, unit: ElectricPotentialUnit)
  extends Quantity[A, ElectricPotential.type] {
  override type Q[B] = ElectricPotential[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toMicrovolts: A = to(Microvolts)
  def toMillivolts: A = to(Millivolts)
  def toVolts: A = to(Volts)
  def toKilovolts: A = to(Kilovolts)
  def toMegavolts: A = to(Megavolts)
}

object ElectricPotential extends Dimension("Electric Potential") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Volts
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Volts
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Microvolts, Millivolts, Volts, Kilovolts, Megavolts)

  implicit class ElectricPotentialCons[A](a: A)(implicit num: Numeric[A]) {
    def microvolts: ElectricPotential[A] = Microvolts(a)
    def millivolts: ElectricPotential[A] = Millivolts(a)
    def volts: ElectricPotential[A] = Volts(a)
    def kilovolts: ElectricPotential[A] = Kilovolts(a)
    def megavolts: ElectricPotential[A] = Megavolts(a)
  }

  lazy val microvolts: ElectricPotential[Int] = Microvolts(1)
  lazy val millivolts: ElectricPotential[Int] = Millivolts(1)
  lazy val volts: ElectricPotential[Int] = Volts(1)
  lazy val kilovolts: ElectricPotential[Int] = Kilovolts(1)
  lazy val megavolts: ElectricPotential[Int] = Megavolts(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricPotentialNumeric[A]()
  private case class ElectricPotentialNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricPotential.type], y: Quantity[A, ElectricPotential.type]): Quantity[A, ElectricPotential.this.type] =
      Volts(x.to(Volts) * y.to(Volts))
  }
}

abstract class ElectricPotentialUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricPotential.type] {
  override def dimension: ElectricPotential.type = ElectricPotential
  override def apply[A: Numeric](value: A): ElectricPotential[A] = ElectricPotential(value, this)
}

case object Microvolts extends ElectricPotentialUnit("μV", MetricSystem.Micro) with SiUnit
case object Millivolts extends ElectricPotentialUnit("mV", MetricSystem.Milli) with SiUnit
case object Volts extends ElectricPotentialUnit("V", 1) with PrimaryUnit with SiUnit
case object Kilovolts extends ElectricPotentialUnit("kV", MetricSystem.Kilo) with SiUnit
case object Megavolts extends ElectricPotentialUnit("MV", MetricSystem.Mega) with SiUnit
