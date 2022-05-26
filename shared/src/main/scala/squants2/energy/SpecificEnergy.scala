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

final case class SpecificEnergy[A: Numeric] private[squants2] (value: A, unit: SpecificEnergyUnit)
  extends Quantity[A, SpecificEnergy] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Mass[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: Time[B])(implicit f: B => A): Nothing$ = ???
  // END CUSTOM OPS

  def toErgsPerGram[B: Numeric](implicit f: A => B): B = toNum[B](ErgsPerGram)
  def toRads[B: Numeric](implicit f: A => B): B = toNum[B](Rads)
  def toGrays[B: Numeric](implicit f: A => B): B = toNum[B](Grays)
}

object SpecificEnergy extends Dimension[SpecificEnergy]("Specific Energy") {

  override def primaryUnit: UnitOfMeasure[SpecificEnergy] with PrimaryUnit[SpecificEnergy] = Grays
  override def siUnit: UnitOfMeasure[SpecificEnergy] with SiUnit[SpecificEnergy] = Grays
  override lazy val units: Set[UnitOfMeasure[SpecificEnergy]] = 
    Set(ErgsPerGram, Rads, Grays)

  implicit class SpecificEnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def ergsPerGram: SpecificEnergy[A] = ErgsPerGram(a)
    def rads: SpecificEnergy[A] = Rads(a)
    def grays: SpecificEnergy[A] = Grays(a)
  }

  lazy val ergsPerGram: SpecificEnergy[Int] = ErgsPerGram(1)
  lazy val rads: SpecificEnergy[Int] = Rads(1)
  lazy val grays: SpecificEnergy[Int] = Grays(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, SpecificEnergy] = SpecificEnergyNumeric[A]()
  private case class SpecificEnergyNumeric[A: Numeric]() extends QuantityNumeric[A, SpecificEnergy](this) {
    override def times(x: Quantity[A, SpecificEnergy], y: Quantity[A, SpecificEnergy]): Quantity[A, SpecificEnergy] =
      Grays(x.to(Grays) * y.to(Grays))
  }
}

abstract class SpecificEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpecificEnergy] {
  override def dimension: Dimension[SpecificEnergy] = SpecificEnergy
  override def apply[A: Numeric](value: A): SpecificEnergy[A] = SpecificEnergy(value, this)
}

case object ErgsPerGram extends SpecificEnergyUnit("erg/g", 1.0E-4)
case object Rads extends SpecificEnergyUnit("rad", MetricSystem.Centi)
case object Grays extends SpecificEnergyUnit("Gy", 1) with PrimaryUnit[SpecificEnergy] with SiUnit[SpecificEnergy]
