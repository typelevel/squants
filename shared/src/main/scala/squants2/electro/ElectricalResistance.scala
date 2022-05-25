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

final case class ElectricalResistance[A: Numeric] private [squants2]  (value: A, unit: ElectricalResistanceUnit)
  extends Quantity[A, ElectricalResistance.type] {
  override type Q[B] = ElectricalResistance[B]

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

object ElectricalResistance extends Dimension("Electrical Resistance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Ohms
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Ohms
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  lazy val nanohms: ElectricalResistance[Int] = Nanohms(1)
  lazy val microohms: ElectricalResistance[Int] = Microohms(1)
  lazy val milliohms: ElectricalResistance[Int] = Milliohms(1)
  lazy val ohms: ElectricalResistance[Int] = Ohms(1)
  lazy val kilohms: ElectricalResistance[Int] = Kilohms(1)
  lazy val megohms: ElectricalResistance[Int] = Megohms(1)
  lazy val gigohms: ElectricalResistance[Int] = Gigohms(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricalResistanceNumeric[A]()
  private case class ElectricalResistanceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricalResistance.type], y: Quantity[A, ElectricalResistance.type]): Quantity[A, ElectricalResistance.this.type] =
      Ohms(x.to(Ohms) * y.to(Ohms))
  }
}

abstract class ElectricalResistanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricalResistance.type] {
  override def dimension: ElectricalResistance.type = ElectricalResistance
  override def apply[A: Numeric](value: A): ElectricalResistance[A] = ElectricalResistance(value, this)
}

case object Nanohms extends ElectricalResistanceUnit("nΩ", MetricSystem.Nano) with SiUnit
case object Microohms extends ElectricalResistanceUnit("µΩ", MetricSystem.Micro) with SiUnit
case object Milliohms extends ElectricalResistanceUnit("mΩ", MetricSystem.Milli) with SiUnit
case object Ohms extends ElectricalResistanceUnit("Ω", 1) with PrimaryUnit with SiUnit
case object Kilohms extends ElectricalResistanceUnit("kΩ", MetricSystem.Kilo) with SiUnit
case object Megohms extends ElectricalResistanceUnit("MΩ", MetricSystem.Mega) with SiUnit
case object Gigohms extends ElectricalResistanceUnit("GΩ", MetricSystem.Giga) with SiUnit
