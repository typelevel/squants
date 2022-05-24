/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class LuminousEnergy[A: Numeric] private [squants2]  (value: A, unit: LuminousEnergyUnit)
  extends Quantity[A, LuminousEnergy.type] {
  override type Q[B] = LuminousEnergy[B]

  // BEGIN CUSTOM OPS
  //  def /[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: Frequency[B])(implicit f: B => A): Quantity[A] = ???
  // END CUSTOM OPS

  def toLumenSeconds: A = to(LumenSeconds)
}

object LuminousEnergy extends Dimension("Luminous Energy") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = LumenSeconds
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = LumenSeconds
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(LumenSeconds)

  implicit class LuminousEnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def lumenSeconds: LuminousEnergy[A] = LumenSeconds(a)
  }

  lazy val lumenSeconds: LuminousEnergy[Int] = LumenSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LuminousEnergyNumeric[A]()
  private case class LuminousEnergyNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, LuminousEnergy.type], y: Quantity[A, LuminousEnergy.type]): Quantity[A, LuminousEnergy.this.type] =
      LumenSeconds(x.to(LumenSeconds) * y.to(LumenSeconds))
  }
}

abstract class LuminousEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousEnergy.type] {
  override def dimension: LuminousEnergy.type = LuminousEnergy
  override def apply[A: Numeric](value: A): LuminousEnergy[A] = LuminousEnergy(value, this)
}

case object LumenSeconds extends LuminousEnergyUnit("lm⋅s", 1) with PrimaryUnit with SiUnit
