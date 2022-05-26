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

final case class MolarEnergy[A: Numeric] private[squants2] (value: A, unit: MolarEnergyUnit)
  extends Quantity[A, MolarEnergy] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: ChemicalAmount[B])(implicit f: B => A): Energy[A] = ???
  // END CUSTOM OPS

  def toJoulesPerMole[B: Numeric](implicit f: A => B): B = toNum[B](JoulesPerMole)
}

object MolarEnergy extends Dimension[MolarEnergy]("Molar Energy") {

  override def primaryUnit: UnitOfMeasure[MolarEnergy] with PrimaryUnit[MolarEnergy] = JoulesPerMole
  override def siUnit: UnitOfMeasure[MolarEnergy] with SiUnit[MolarEnergy] = JoulesPerMole
  override lazy val units: Set[UnitOfMeasure[MolarEnergy]] = 
    Set(JoulesPerMole)

  implicit class MolarEnergyCons[A](a: A)(implicit num: Numeric[A]) {
    def joulesPerMole: MolarEnergy[A] = JoulesPerMole(a)
  }

  lazy val joulesPerMole: MolarEnergy[Int] = JoulesPerMole(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, MolarEnergy] = MolarEnergyNumeric[A]()
  private case class MolarEnergyNumeric[A: Numeric]() extends QuantityNumeric[A, MolarEnergy](this) {
    override def times(x: Quantity[A, MolarEnergy], y: Quantity[A, MolarEnergy]): Quantity[A, MolarEnergy] =
      JoulesPerMole(x.to(JoulesPerMole) * y.to(JoulesPerMole))
  }
}

abstract class MolarEnergyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MolarEnergy] {
  override def dimension: Dimension[MolarEnergy] = MolarEnergy
  override def apply[A: Numeric](value: A): MolarEnergy[A] = MolarEnergy(value, this)
}

case object JoulesPerMole extends MolarEnergyUnit("J/mol", 1) with PrimaryUnit[MolarEnergy] with SiUnit[MolarEnergy]
