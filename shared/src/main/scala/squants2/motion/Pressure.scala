/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import squants2.time._

final case class Pressure[A: Numeric] private[squants2] (value: A, unit: PressureUnit)
  extends Quantity[A, Pressure] with TimeIntegral[A, PressureChange] {

  override protected[squants2] def timeDerived: PressureChange[A] = PascalsPerSecond(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): Force[A] = ???
  //  def *[B](that: Time[B])(implicit f: B => A): Nothing$ = ???
  // END CUSTOM OPS

  def toPascals[B: Numeric](implicit f: A => B): B = toNum[B](Pascals)
  def toTorrs[B: Numeric](implicit f: A => B): B = toNum[B](Torrs)
  def toMillimetersOfMercury[B: Numeric](implicit f: A => B): B = toNum[B](MillimetersOfMercury)
  def toInchesOfMercury[B: Numeric](implicit f: A => B): B = toNum[B](InchesOfMercury)
  def toPoundsPerSquareInch[B: Numeric](implicit f: A => B): B = toNum[B](PoundsPerSquareInch)
  def toBars[B: Numeric](implicit f: A => B): B = toNum[B](Bars)
  def toStandardAtmospheres[B: Numeric](implicit f: A => B): B = toNum[B](StandardAtmospheres)
}

object Pressure extends Dimension[Pressure]("Pressure") {

  override def primaryUnit: UnitOfMeasure[Pressure] with PrimaryUnit[Pressure] = Pascals
  override def siUnit: UnitOfMeasure[Pressure] with SiUnit[Pressure] = Pascals
  override lazy val units: Set[UnitOfMeasure[Pressure]] = 
    Set(Pascals, Torrs, MillimetersOfMercury, InchesOfMercury, PoundsPerSquareInch, Bars, StandardAtmospheres)

  implicit class PressureCons[A](a: A)(implicit num: Numeric[A]) {
    def pascals: Pressure[A] = Pascals(a)
    def torrs: Pressure[A] = Torrs(a)
    def millimetersOfMercury: Pressure[A] = MillimetersOfMercury(a)
    def inchesOfMercury: Pressure[A] = InchesOfMercury(a)
    def poundsPerSquareInch: Pressure[A] = PoundsPerSquareInch(a)
    def bars: Pressure[A] = Bars(a)
    def standardAtmospheres: Pressure[A] = StandardAtmospheres(a)
  }

  lazy val pascal: Pressure[Int] = Pascals(1)
  lazy val torr: Pressure[Int] = Torrs(1)
  lazy val millimetersOfMercury: Pressure[Int] = MillimetersOfMercury(1)
  lazy val inchesOfMercury: Pressure[Int] = InchesOfMercury(1)
  lazy val poundPerSquareInch: Pressure[Int] = PoundsPerSquareInch(1)
  lazy val bar: Pressure[Int] = Bars(1)
  lazy val standardAtmosphere: Pressure[Int] = StandardAtmospheres(1)

}

abstract class PressureUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Pressure] {
  override def dimension: Dimension[Pressure] = Pressure
  override def apply[A: Numeric](value: A): Pressure[A] = Pressure(value, this)
}

case object Pascals extends PressureUnit("Pa", 1) with PrimaryUnit[Pressure] with SiUnit[Pressure]
case object Torrs extends PressureUnit("Torr", 133.32236842105263)
case object MillimetersOfMercury extends PressureUnit("mmHg", 133.322387415)
case object InchesOfMercury extends PressureUnit("inHg", 3386.389)
case object PoundsPerSquareInch extends PressureUnit("psi", 6894.757293168361)
case object Bars extends PressureUnit("bar", 100000)
case object StandardAtmospheres extends PressureUnit("atm", 101325)
