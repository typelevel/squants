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

final case class Capacitance[A: Numeric] private [squants2]  (value: A, unit: CapacitanceUnit)
  extends Quantity[A, Capacitance.type] {
  override type Q[B] = Capacitance[B]

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

object Capacitance extends Dimension("Capacitance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Farads
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Farads
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = CapacitanceNumeric[A]()
  private case class CapacitanceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Capacitance.type], y: Quantity[A, Capacitance.type]): Quantity[A, Capacitance.this.type] =
      Farads(x.to(Farads) * y.to(Farads))
  }
}

abstract class CapacitanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Capacitance.type] {
  override def dimension: Capacitance.type = Capacitance
  override def apply[A: Numeric](value: A): Capacitance[A] = Capacitance(value, this)
}

case object Picofarads extends CapacitanceUnit("pF", MetricSystem.Pico) with SiUnit
case object Nanofarads extends CapacitanceUnit("nF", MetricSystem.Nano) with SiUnit
case object Microfarads extends CapacitanceUnit("μF", MetricSystem.Micro) with SiUnit
case object Millifarads extends CapacitanceUnit("mF", MetricSystem.Milli) with SiUnit
case object Farads extends CapacitanceUnit("F", 1) with PrimaryUnit with SiUnit
case object Kilofarads extends CapacitanceUnit("kF", MetricSystem.Kilo) with SiUnit
