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

final case class Capacitance[A: Numeric] private[squants2] (value: A, unit: CapacitanceUnit)
  extends Quantity[A, Capacitance] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: ElectricPotential[B])(implicit f: B => A): ElectricCharge[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): Permittivity[A] = ???
  // END CUSTOM OPS

  def toPicofarads[B: Numeric](implicit f: A => B): B = toNum[B](Picofarads)
  def toNanofarads[B: Numeric](implicit f: A => B): B = toNum[B](Nanofarads)
  def toMicrofarads[B: Numeric](implicit f: A => B): B = toNum[B](Microfarads)
  def toMillifarads[B: Numeric](implicit f: A => B): B = toNum[B](Millifarads)
  def toFarads[B: Numeric](implicit f: A => B): B = toNum[B](Farads)
  def toKilofarads[B: Numeric](implicit f: A => B): B = toNum[B](Kilofarads)
}

object Capacitance extends Dimension[Capacitance]("Capacitance") {

  override def primaryUnit: UnitOfMeasure[Capacitance] with PrimaryUnit[Capacitance] = Farads
  override def siUnit: UnitOfMeasure[Capacitance] with SiUnit[Capacitance] = Farads
  override lazy val units: Set[UnitOfMeasure[Capacitance]] = 
    Set(Picofarads, Nanofarads, Microfarads, Millifarads, Farads, Kilofarads)

  implicit class CapacitanceCons[A](a: A)(implicit num: Numeric[A]) {
    def picofarads: Capacitance[A] = Picofarads(a)
    def nanofarads: Capacitance[A] = Nanofarads(a)
    def microfarads: Capacitance[A] = Microfarads(a)
    def millifarads: Capacitance[A] = Millifarads(a)
    def farads: Capacitance[A] = Farads(a)
    def kilofarads: Capacitance[A] = Kilofarads(a)
  }

  lazy val picofarads: Capacitance[Int] = Picofarads(1)
  lazy val nanofarads: Capacitance[Int] = Nanofarads(1)
  lazy val microfarads: Capacitance[Int] = Microfarads(1)
  lazy val millifarads: Capacitance[Int] = Millifarads(1)
  lazy val farads: Capacitance[Int] = Farads(1)
  lazy val kilofarads: Capacitance[Int] = Kilofarads(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Capacitance] = CapacitanceNumeric[A]()
  private case class CapacitanceNumeric[A: Numeric]() extends QuantityNumeric[A, Capacitance](this) {
    override def times(x: Quantity[A, Capacitance], y: Quantity[A, Capacitance]): Quantity[A, Capacitance] =
      Farads(x.to(Farads) * y.to(Farads))
  }
}

abstract class CapacitanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Capacitance] {
  override def dimension: Dimension[Capacitance] = Capacitance
  override def apply[A: Numeric](value: A): Capacitance[A] = Capacitance(value, this)
}

case object Picofarads extends CapacitanceUnit("pF", MetricSystem.Pico) with SiUnit[Capacitance]
case object Nanofarads extends CapacitanceUnit("nF", MetricSystem.Nano) with SiUnit[Capacitance]
case object Microfarads extends CapacitanceUnit("μF", MetricSystem.Micro) with SiUnit[Capacitance]
case object Millifarads extends CapacitanceUnit("mF", MetricSystem.Milli) with SiUnit[Capacitance]
case object Farads extends CapacitanceUnit("F", 1) with PrimaryUnit[Capacitance] with SiUnit[Capacitance]
case object Kilofarads extends CapacitanceUnit("kF", MetricSystem.Kilo) with SiUnit[Capacitance]
