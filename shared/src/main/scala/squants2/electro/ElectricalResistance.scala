/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class ElectricalResistance[A: Numeric] private[squants2] (value: A, unit: ElectricalResistanceUnit)
  extends Quantity[A, ElectricalResistance] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: ElectricCurrent[B])(implicit f: B => A): ElectricPotential[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): Resistivity[A] = ???
  //  def inSiemens[B]()(implicit f: B => A): ElectricalConductance[A] = ???
  // END CUSTOM OPS

  def toNanohms[B: Numeric](implicit f: A => B): B = toNum[B](Nanohms)
  def toMicroohms[B: Numeric](implicit f: A => B): B = toNum[B](Microohms)
  def toMilliohms[B: Numeric](implicit f: A => B): B = toNum[B](Milliohms)
  def toOhms[B: Numeric](implicit f: A => B): B = toNum[B](Ohms)
  def toKilohms[B: Numeric](implicit f: A => B): B = toNum[B](Kilohms)
  def toMegohms[B: Numeric](implicit f: A => B): B = toNum[B](Megohms)
  def toGigohms[B: Numeric](implicit f: A => B): B = toNum[B](Gigohms)
}

object ElectricalResistance extends Dimension[ElectricalResistance]("Electrical Resistance") {

  override def primaryUnit: UnitOfMeasure[ElectricalResistance] with PrimaryUnit[ElectricalResistance] = Ohms
  override def siUnit: UnitOfMeasure[ElectricalResistance] with SiUnit[ElectricalResistance] = Ohms
  override lazy val units: Set[UnitOfMeasure[ElectricalResistance]] = 
    Set(Nanohms, Microohms, Milliohms, Ohms, Kilohms, Megohms, Gigohms)

  implicit class ElectricalResistanceCons[A](a: A)(implicit num: Numeric[A]) {
    def nanohms: ElectricalResistance[A] = Nanohms(a)
    def microohms: ElectricalResistance[A] = Microohms(a)
    def milliohms: ElectricalResistance[A] = Milliohms(a)
    def ohms: ElectricalResistance[A] = Ohms(a)
    def kilohms: ElectricalResistance[A] = Kilohms(a)
    def megohms: ElectricalResistance[A] = Megohms(a)
    def gigohms: ElectricalResistance[A] = Gigohms(a)
  }

  lazy val nanohm: ElectricalResistance[Int] = Nanohms(1)
  lazy val microohm: ElectricalResistance[Int] = Microohms(1)
  lazy val milliohm: ElectricalResistance[Int] = Milliohms(1)
  lazy val ohm: ElectricalResistance[Int] = Ohms(1)
  lazy val kilohm: ElectricalResistance[Int] = Kilohms(1)
  lazy val megohm: ElectricalResistance[Int] = Megohms(1)
  lazy val gigohm: ElectricalResistance[Int] = Gigohms(1)

}

abstract class ElectricalResistanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricalResistance] {
  override def dimension: Dimension[ElectricalResistance] = ElectricalResistance
  override def apply[A: Numeric](value: A): ElectricalResistance[A] = ElectricalResistance(value, this)
}

case object Nanohms extends ElectricalResistanceUnit("nΩ", MetricSystem.Nano) with SiUnit[ElectricalResistance]
case object Microohms extends ElectricalResistanceUnit("µΩ", MetricSystem.Micro) with SiUnit[ElectricalResistance]
case object Milliohms extends ElectricalResistanceUnit("mΩ", MetricSystem.Milli) with SiUnit[ElectricalResistance]
case object Ohms extends ElectricalResistanceUnit("Ω", 1) with PrimaryUnit[ElectricalResistance] with SiUnit[ElectricalResistance]
case object Kilohms extends ElectricalResistanceUnit("kΩ", MetricSystem.Kilo) with SiUnit[ElectricalResistance]
case object Megohms extends ElectricalResistanceUnit("MΩ", MetricSystem.Mega) with SiUnit[ElectricalResistance]
case object Gigohms extends ElectricalResistanceUnit("GΩ", MetricSystem.Giga) with SiUnit[ElectricalResistance]
