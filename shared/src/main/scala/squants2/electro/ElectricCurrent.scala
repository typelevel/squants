/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import squants2.time._

final case class ElectricCurrent[A: Numeric] private[squants2] (value: A, unit: ElectricCurrentUnit)
  extends Quantity[A, ElectricCurrent] with TimeDerivative[A, ElectricCharge] {

  override protected[squants2] def timeIntegrated: ElectricCharge[A] = Coulombs(num.one)
  override protected[squants2] def derivativeTime: Time[A] = Seconds(num.one)

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

object ElectricCurrent extends BaseDimension[ElectricCurrent]("Electric Current", "I") {

  override def primaryUnit: UnitOfMeasure[ElectricCurrent] with PrimaryUnit[ElectricCurrent] = Amperes
  override def siUnit: UnitOfMeasure[ElectricCurrent] with SiBaseUnit[ElectricCurrent] = Amperes
  override lazy val units: Set[UnitOfMeasure[ElectricCurrent]] = 
    Set(Milliamperes, Amperes)

  implicit class ElectricCurrentCons[A](a: A)(implicit num: Numeric[A]) {
    def milliamperes: ElectricCurrent[A] = Milliamperes(a)
    def amperes: ElectricCurrent[A] = Amperes(a)
  }

  lazy val milliamperes: ElectricCurrent[Int] = Milliamperes(1)
  lazy val amperes: ElectricCurrent[Int] = Amperes(1)

}

abstract class ElectricCurrentUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricCurrent] {
  override def dimension: Dimension[ElectricCurrent] = ElectricCurrent
  override def apply[A: Numeric](value: A): ElectricCurrent[A] = ElectricCurrent(value, this)
}

case object Milliamperes extends ElectricCurrentUnit("mA", MetricSystem.Milli) with SiUnit[ElectricCurrent]
case object Amperes extends ElectricCurrentUnit("A", 1) with PrimaryUnit[ElectricCurrent] with SiBaseUnit[ElectricCurrent]
