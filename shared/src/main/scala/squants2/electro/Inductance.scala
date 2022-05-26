/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class Inductance[A: Numeric] private[squants2] (value: A, unit: InductanceUnit)
  extends Quantity[A, Inductance] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: ElectricCurrent[B])(implicit f: B => A): MagneticFlux[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): Permeability[A] = ???
  // END CUSTOM OPS

  def toPicohenry[B: Numeric](implicit f: A => B): B = toNum[B](Picohenry)
  def toNanohenry[B: Numeric](implicit f: A => B): B = toNum[B](Nanohenry)
  def toMicrohenry[B: Numeric](implicit f: A => B): B = toNum[B](Microhenry)
  def toMillihenry[B: Numeric](implicit f: A => B): B = toNum[B](Millihenry)
  def toHenry[B: Numeric](implicit f: A => B): B = toNum[B](Henry)
}

object Inductance extends Dimension[Inductance]("Inductance") {

  override def primaryUnit: UnitOfMeasure[Inductance] with PrimaryUnit[Inductance] = Henry
  override def siUnit: UnitOfMeasure[Inductance] with SiUnit[Inductance] = Henry
  override lazy val units: Set[UnitOfMeasure[Inductance]] = 
    Set(Picohenry, Nanohenry, Microhenry, Millihenry, Henry)

  implicit class InductanceCons[A](a: A)(implicit num: Numeric[A]) {
    def picohenry: Inductance[A] = Picohenry(a)
    def nanohenry: Inductance[A] = Nanohenry(a)
    def microhenry: Inductance[A] = Microhenry(a)
    def millihenry: Inductance[A] = Millihenry(a)
    def henry: Inductance[A] = Henry(a)
  }

  lazy val picohenry: Inductance[Int] = Picohenry(1)
  lazy val nanohenry: Inductance[Int] = Nanohenry(1)
  lazy val microhenry: Inductance[Int] = Microhenry(1)
  lazy val millihenry: Inductance[Int] = Millihenry(1)
  lazy val henry: Inductance[Int] = Henry(1)

}

abstract class InductanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Inductance] {
  override def dimension: Dimension[Inductance] = Inductance
  override def apply[A: Numeric](value: A): Inductance[A] = Inductance(value, this)
}

case object Picohenry extends InductanceUnit("pH", MetricSystem.Pico) with SiUnit[Inductance]
case object Nanohenry extends InductanceUnit("nH", MetricSystem.Nano) with SiUnit[Inductance]
case object Microhenry extends InductanceUnit("μH", MetricSystem.Micro) with SiUnit[Inductance]
case object Millihenry extends InductanceUnit("mH", MetricSystem.Milli) with SiUnit[Inductance]
case object Henry extends InductanceUnit("H", 1) with PrimaryUnit[Inductance] with SiUnit[Inductance]
