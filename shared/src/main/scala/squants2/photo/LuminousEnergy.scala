/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._
import squants2.time._

final case class LuminousEnergy[A: Numeric] private[squants2] (value: A, unit: LuminousEnergyUnit)
  extends Quantity[A, LuminousEnergy] with TimeIntegral[A, LuminousFlux] {

  override protected[squants2] def timeDerived: LuminousFlux[A] = Lumens(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)

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

  lazy val lumenSecond: LuminousEnergy[Int] = LumenSeconds(1)

}

abstract class LuminousEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousEnergy] {
  override def dimension: Dimension[LuminousEnergy] = LuminousEnergy
  override def apply[A: Numeric](value: A): LuminousEnergy[A] = LuminousEnergy(value, this)
}

case object LumenSeconds extends LuminousEnergyUnit("lm⋅s", 1) with PrimaryUnit[LuminousEnergy] with SiUnit[LuminousEnergy]
