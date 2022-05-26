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

final case class LuminousEnergy[A: Numeric] private[squants2] (value: A, unit: LuminousEnergyUnit)
  extends Quantity[A, LuminousEnergy] {

  // BEGIN CUSTOM OPS

  // END CUSTOM OPS

  def toLumenSeconds[B: Numeric](implicit f: A => B): B = toNum[B](LumenSeconds)
}

object LuminousEnergy extends Dimension[LuminousEnergy]("Luminous Energy") {

  override def primaryUnit: UnitOfMeasure[LuminousEnergy] with PrimaryUnit[LuminousEnergy] = LumenSeconds
  override def siUnit: UnitOfMeasure[LuminousEnergy] with SiUnit[LuminousEnergy] = LumenSeconds
  override lazy val units: Set[UnitOfMeasure[LuminousEnergy]] = 
    Set(LumenSeconds)

  implicit class LuminousEnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def lumenSeconds: LuminousEnergy[A] = LumenSeconds(a)
  }

  lazy val lumenSeconds: LuminousEnergy[Int] = LumenSeconds(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, LuminousEnergy] = LuminousEnergyNumeric[A]()
  private case class LuminousEnergyNumeric[A: Numeric]() extends QuantityNumeric[A, LuminousEnergy](this) {
    override def times(x: Quantity[A, LuminousEnergy], y: Quantity[A, LuminousEnergy]): Quantity[A, LuminousEnergy] =
      LumenSeconds(x.to(LumenSeconds) * y.to(LumenSeconds))
  }
}

abstract class LuminousEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousEnergy] {
  override def dimension: Dimension[LuminousEnergy] = LuminousEnergy
  override def apply[A: Numeric](value: A): LuminousEnergy[A] = LuminousEnergy(value, this)
}

case object LumenSeconds extends LuminousEnergyUnit("lm⋅s", 1) with PrimaryUnit[LuminousEnergy] with SiUnit[LuminousEnergy]
