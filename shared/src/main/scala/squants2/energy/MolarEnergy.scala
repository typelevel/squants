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

final case class MolarEnergy[A: Numeric] private [squants2]  (value: A, unit: MolarEnergyUnit)
  extends Quantity[A, MolarEnergy.type] {
  override type Q[B] = MolarEnergy[B]

  def toJoulesPerMole: A = to(JoulesPerMole)
}

object MolarEnergy extends Dimension("MolarEnergy") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = JoulesPerMole
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = JoulesPerMole
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(JoulesPerMole)

  implicit class MolarEnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def joulesPerMole: MolarEnergy[A] = JoulesPerMole(a)
  }

  lazy val joulesPerMole: MolarEnergy[Int] = JoulesPerMole(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = MolarEnergyNumeric[A]()
  private case class MolarEnergyNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, MolarEnergy.type], y: Quantity[A, MolarEnergy.type]): Quantity[A, MolarEnergy.this.type] =
      JoulesPerMole(x.to(JoulesPerMole) * y.to(JoulesPerMole))
  }
}

abstract class MolarEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MolarEnergy.type] {
  override lazy val dimension: MolarEnergy.type = MolarEnergy
  override def apply[A: Numeric](value: A): MolarEnergy[A] = MolarEnergy(value, this)
}

case object JoulesPerMole extends MolarEnergyUnit("J/mol", 1.0) with PrimaryUnit with SiUnit
