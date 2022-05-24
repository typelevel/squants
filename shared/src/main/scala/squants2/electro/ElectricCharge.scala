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

final case class ElectricCharge[A: Numeric] private [squants2]  (value: A, unit: ElectricChargeUnit)
  extends Quantity[A, ElectricCharge.type] {
  override type Q[B] = ElectricCharge[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toPicocoulombs: A = to(Picocoulombs)
  def toNanocoulombs: A = to(Nanocoulombs)
  def toMicrocoulombs: A = to(Microcoulombs)
  def toMilliampereSeconds: A = to(MilliampereSeconds)
  def toMillicoulombs: A = to(Millicoulombs)
  def toCoulombs: A = to(Coulombs)
  def toMilliampereHours: A = to(MilliampereHours)
  def toAbcoulombs: A = to(Abcoulombs)
  def toAmpereHours: A = to(AmpereHours)
}

object ElectricCharge extends Dimension("ElectricCharge") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Coulombs
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Coulombs
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Picocoulombs, Nanocoulombs, Microcoulombs, MilliampereSeconds, Millicoulombs, Coulombs, MilliampereHours, Abcoulombs, AmpereHours)

  implicit class ElectricChargeCons[A](a: A)(implicit num: Numeric[A]) {
    def picocoulombs: ElectricCharge[A] = Picocoulombs(a)
    def nanocoulombs: ElectricCharge[A] = Nanocoulombs(a)
    def microcoulombs: ElectricCharge[A] = Microcoulombs(a)
    def milliampereSeconds: ElectricCharge[A] = MilliampereSeconds(a)
    def millicoulombs: ElectricCharge[A] = Millicoulombs(a)
    def coulombs: ElectricCharge[A] = Coulombs(a)
    def milliampereHours: ElectricCharge[A] = MilliampereHours(a)
    def abcoulombs: ElectricCharge[A] = Abcoulombs(a)
    def ampereHours: ElectricCharge[A] = AmpereHours(a)
  }

  lazy val picocoulombs: ElectricCharge[Int] = Picocoulombs(1)
  lazy val nanocoulombs: ElectricCharge[Int] = Nanocoulombs(1)
  lazy val microcoulombs: ElectricCharge[Int] = Microcoulombs(1)
  lazy val milliampereSeconds: ElectricCharge[Int] = MilliampereSeconds(1)
  lazy val millicoulombs: ElectricCharge[Int] = Millicoulombs(1)
  lazy val coulombs: ElectricCharge[Int] = Coulombs(1)
  lazy val milliampereHours: ElectricCharge[Int] = MilliampereHours(1)
  lazy val abcoulombs: ElectricCharge[Int] = Abcoulombs(1)
  lazy val ampereHours: ElectricCharge[Int] = AmpereHours(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricChargeNumeric[A]()
  private case class ElectricChargeNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricCharge.type], y: Quantity[A, ElectricCharge.type]): Quantity[A, ElectricCharge.this.type] =
      Coulombs(x.to(Coulombs) * y.to(Coulombs))
  }
}

abstract class ElectricChargeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricCharge.type] {
  override lazy val dimension: ElectricCharge.type = ElectricCharge
  override def apply[A: Numeric](value: A): ElectricCharge[A] = ElectricCharge(value, this)
}

case object Picocoulombs extends ElectricChargeUnit("pC", 1.0E-12) with SiUnit
case object Nanocoulombs extends ElectricChargeUnit("nC", 1.0E-9) with SiUnit
case object Microcoulombs extends ElectricChargeUnit("µC", 1.0E-6) with SiUnit
case object MilliampereSeconds extends ElectricChargeUnit("mAs", 0.001)
case object Millicoulombs extends ElectricChargeUnit("mC", 0.001) with SiUnit
case object Coulombs extends ElectricChargeUnit("C", 1.0) with PrimaryUnit with SiUnit
case object MilliampereHours extends ElectricChargeUnit("mAh", 3.6)
case object Abcoulombs extends ElectricChargeUnit("aC", 10.0)
case object AmpereHours extends ElectricChargeUnit("Ah", 3600.0)
