/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.energy

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class SpecificEnergy[A: Numeric] private [squants2]  (value: A, unit: SpecificEnergyUnit)
  extends Quantity[A, SpecificEnergy.type] {
  override type Q[B] = SpecificEnergy[B]

  // BEGIN CUSTOM OPS

  //  def *[B](that: Mass[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: Time[B])(implicit f: B => A): Nothing$ = ???
  // END CUSTOM OPS

  def toErgsPerGram[B: Numeric](implicit f: A => B): B = toNum[B](ErgsPerGram)
  def toRads[B: Numeric](implicit f: A => B): B = toNum[B](Rads)
  def toGrays[B: Numeric](implicit f: A => B): B = toNum[B](Grays)
}

object SpecificEnergy extends Dimension("Specific Energy") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Grays
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Grays
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(ErgsPerGram, Rads, Grays)

  implicit class SpecificEnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def ergsPerGram: SpecificEnergy[A] = ErgsPerGram(a)
    def rads: SpecificEnergy[A] = Rads(a)
    def grays: SpecificEnergy[A] = Grays(a)
  }

  lazy val ergsPerGram: SpecificEnergy[Int] = ErgsPerGram(1)
  lazy val rads: SpecificEnergy[Int] = Rads(1)
  lazy val grays: SpecificEnergy[Int] = Grays(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = SpecificEnergyNumeric[A]()
  private case class SpecificEnergyNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, SpecificEnergy.type], y: Quantity[A, SpecificEnergy.type]): Quantity[A, SpecificEnergy.this.type] =
      Grays(x.to(Grays) * y.to(Grays))
  }
}

abstract class SpecificEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpecificEnergy.type] {
  override def dimension: SpecificEnergy.type = SpecificEnergy
  override def apply[A: Numeric](value: A): SpecificEnergy[A] = SpecificEnergy(value, this)
}

case object ErgsPerGram extends SpecificEnergyUnit("erg/g", 1.0E-4)
case object Rads extends SpecificEnergyUnit("rad", MetricSystem.Centi)
case object Grays extends SpecificEnergyUnit("Gy", 1) with PrimaryUnit with SiUnit
