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

final case class ElectricCurrent[A: Numeric] private [squants2]  (value: A, unit: ElectricCurrentUnit)
  extends Quantity[A, ElectricCurrent.type] {
  override type Q[B] = ElectricCurrent[B]

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: ElectricalResistance[B])(implicit f: B => A): ElectricPotential[A] = ???
  //  def *[B](that: ElectricPotential[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: Inductance[B])(implicit f: B => A): MagneticFlux[A] = ???
  //  def /[B](that: ElectricPotential[B])(implicit f: B => A): ElectricalConductance[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): MagneticFieldStrength[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): ElectricCurrentDensity[A] = ???
  // END CUSTOM OPS

  def toMilliamperes[B: Numeric](implicit f: A => B): B = toNum[B](Milliamperes)
  def toAmperes[B: Numeric](implicit f: A => B): B = toNum[B](Amperes)
}

object ElectricCurrent extends BaseDimension("Electric Current", "I") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Amperes
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Amperes
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Milliamperes, Amperes)

  implicit class ElectricCurrentCons[A](a: A)(implicit num: Numeric[A]) {
    def milliamperes: ElectricCurrent[A] = Milliamperes(a)
    def amperes: ElectricCurrent[A] = Amperes(a)
  }

  lazy val milliamperes: ElectricCurrent[Int] = Milliamperes(1)
  lazy val amperes: ElectricCurrent[Int] = Amperes(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricCurrentNumeric[A]()
  private case class ElectricCurrentNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricCurrent.type], y: Quantity[A, ElectricCurrent.type]): Quantity[A, ElectricCurrent.this.type] =
      Amperes(x.to(Amperes) * y.to(Amperes))
  }
}

abstract class ElectricCurrentUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricCurrent.type] {
  override def dimension: ElectricCurrent.type = ElectricCurrent
  override def apply[A: Numeric](value: A): ElectricCurrent[A] = ElectricCurrent(value, this)
}

case object Milliamperes extends ElectricCurrentUnit("mA", MetricSystem.Milli) with SiUnit
case object Amperes extends ElectricCurrentUnit("A", 1) with PrimaryUnit with SiBaseUnit
