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

final case class ElectricPotential[A: Numeric] private[squants2] (value: A, unit: ElectricPotentialUnit)
  extends Quantity[A, ElectricPotential] with TimeDerivative[A, MagneticFlux] {

  override protected[squants2] def timeIntegrated: MagneticFlux[A] = Webers(num.one)
  override protected[squants2] def derivativeTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: ElectricCurrent[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: Capacitance[B])(implicit f: B => A): ElectricCharge[A] = ???
  //  def *[B](that: ElectricCharge[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: ElectricCurrent[B])(implicit f: B => A): ElectricalResistance[A] = ???
  //  def /[B](that: ElectricalResistance[B])(implicit f: B => A): ElectricCurrent[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): ElectricFieldStrength[A] = ???
  // END CUSTOM OPS

  def toMicrovolts[B: Numeric](implicit f: A => B): B = toNum[B](Microvolts)
  def toMillivolts[B: Numeric](implicit f: A => B): B = toNum[B](Millivolts)
  def toVolts[B: Numeric](implicit f: A => B): B = toNum[B](Volts)
  def toKilovolts[B: Numeric](implicit f: A => B): B = toNum[B](Kilovolts)
  def toMegavolts[B: Numeric](implicit f: A => B): B = toNum[B](Megavolts)
}

object ElectricPotential extends Dimension[ElectricPotential]("Electric Potential") {

  override def primaryUnit: UnitOfMeasure[ElectricPotential] with PrimaryUnit[ElectricPotential] = Volts
  override def siUnit: UnitOfMeasure[ElectricPotential] with SiUnit[ElectricPotential] = Volts
  override lazy val units: Set[UnitOfMeasure[ElectricPotential]] = 
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

}

abstract class ElectricPotentialUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricPotential] {
  override def dimension: Dimension[ElectricPotential] = ElectricPotential
  override def apply[A: Numeric](value: A): ElectricPotential[A] = ElectricPotential(value, this)
}

case object Microvolts extends ElectricPotentialUnit("μV", MetricSystem.Micro) with SiUnit[ElectricPotential]
case object Millivolts extends ElectricPotentialUnit("mV", MetricSystem.Milli) with SiUnit[ElectricPotential]
case object Volts extends ElectricPotentialUnit("V", 1) with PrimaryUnit[ElectricPotential] with SiUnit[ElectricPotential]
case object Kilovolts extends ElectricPotentialUnit("kV", MetricSystem.Kilo) with SiUnit[ElectricPotential]
case object Megavolts extends ElectricPotentialUnit("MV", MetricSystem.Mega) with SiUnit[ElectricPotential]
