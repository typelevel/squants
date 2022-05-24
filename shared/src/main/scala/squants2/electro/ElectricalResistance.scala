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
  // END CUSTOM OPS

  def toNanohms: A = to(Nanohms)
  def toMicroohms: A = to(Microohms)
  def toMilliohms: A = to(Milliohms)
  def toOhms: A = to(Ohms)
  def toKilohms: A = to(Kilohms)
  def toMegohms: A = to(Megohms)
  def toGigohms: A = to(Gigohms)
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

case object Nanohms extends ElectricalResistanceUnit("nΩ", 1.0E-9) with SiUnit
case object Microohms extends ElectricalResistanceUnit("µΩ", 1.0E-6) with SiUnit
case object Milliohms extends ElectricalResistanceUnit("mΩ", 0.001) with SiUnit
case object Ohms extends ElectricalResistanceUnit("Ω", 1) with PrimaryUnit with SiUnit
case object Kilohms extends ElectricalResistanceUnit("kΩ", 1000.0) with SiUnit
case object Megohms extends ElectricalResistanceUnit("MΩ", 1000000.0) with SiUnit
case object Gigohms extends ElectricalResistanceUnit("GΩ", 1.0E9) with SiUnit
