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

final case class ElectricCharge[A: Numeric] private[squants2] (value: A, unit: ElectricChargeUnit)
  extends Quantity[A, ElectricCharge] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: ElectricPotential[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: ElectricPotential[B])(implicit f: B => A): Capacitance[A] = ???
  //  def /[B](that: Capacitance[B])(implicit f: B => A): ElectricPotential[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): LinearElectricChargeDensity[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): AreaElectricChargeDensity[A] = ???
  //  def /[B](that: Volume[B])(implicit f: B => A): ElectricChargeDensity[A] = ???
  //  def /[B](that: Mass[B])(implicit f: B => A): ElectricChargeMassRatio[A] = ???
  // END CUSTOM OPS

  def toPicocoulombs[B: Numeric](implicit f: A => B): B = toNum[B](Picocoulombs)
  def toNanocoulombs[B: Numeric](implicit f: A => B): B = toNum[B](Nanocoulombs)
  def toMicrocoulombs[B: Numeric](implicit f: A => B): B = toNum[B](Microcoulombs)
  def toMilliampereSeconds[B: Numeric](implicit f: A => B): B = toNum[B](MilliampereSeconds)
  def toMillicoulombs[B: Numeric](implicit f: A => B): B = toNum[B](Millicoulombs)
  def toCoulombs[B: Numeric](implicit f: A => B): B = toNum[B](Coulombs)
  def toMilliampereHours[B: Numeric](implicit f: A => B): B = toNum[B](MilliampereHours)
  def toAbcoulombs[B: Numeric](implicit f: A => B): B = toNum[B](Abcoulombs)
  def toAmpereHours[B: Numeric](implicit f: A => B): B = toNum[B](AmpereHours)
}

object ElectricCharge extends Dimension[ElectricCharge]("Electric Charge") {

  override def primaryUnit: UnitOfMeasure[ElectricCharge] with PrimaryUnit[ElectricCharge] = Coulombs
  override def siUnit: UnitOfMeasure[ElectricCharge] with SiUnit[ElectricCharge] = Coulombs
  override lazy val units: Set[UnitOfMeasure[ElectricCharge]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, ElectricCharge] = ElectricChargeNumeric[A]()
  private case class ElectricChargeNumeric[A: Numeric]() extends QuantityNumeric[A, ElectricCharge](this) {
    override def times(x: Quantity[A, ElectricCharge], y: Quantity[A, ElectricCharge]): Quantity[A, ElectricCharge] =
      Coulombs(x.to(Coulombs) * y.to(Coulombs))
  }
}

abstract class ElectricChargeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricCharge] {
  override def dimension: Dimension[ElectricCharge] = ElectricCharge
  override def apply[A: Numeric](value: A): ElectricCharge[A] = ElectricCharge(value, this)
}

case object Picocoulombs extends ElectricChargeUnit("pC", MetricSystem.Pico) with SiUnit[ElectricCharge]
case object Nanocoulombs extends ElectricChargeUnit("nC", MetricSystem.Nano) with SiUnit[ElectricCharge]
case object Microcoulombs extends ElectricChargeUnit("µC", MetricSystem.Micro) with SiUnit[ElectricCharge]
case object MilliampereSeconds extends ElectricChargeUnit("mAs", MetricSystem.Milli)
case object Millicoulombs extends ElectricChargeUnit("mC", MetricSystem.Milli) with SiUnit[ElectricCharge]
case object Coulombs extends ElectricChargeUnit("C", 1) with PrimaryUnit[ElectricCharge] with SiUnit[ElectricCharge]
case object MilliampereHours extends ElectricChargeUnit("mAh", 3.6)
case object Abcoulombs extends ElectricChargeUnit("aC", MetricSystem.Deca)
case object AmpereHours extends ElectricChargeUnit("Ah", 3600)
