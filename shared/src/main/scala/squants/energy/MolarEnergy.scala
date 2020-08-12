package squants.energy

import squants.mass.{ChemicalAmount, Moles}
import squants.{AbstractQuantityNumeric, Dimension, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  * @param value Double
  */
final class MolarEnergy private (val value: Double, val unit: MolarEnergyUnit)
  extends Quantity[MolarEnergy] {

  def dimension = MolarEnergy

  def *(that: ChemicalAmount): Energy = Joules(this.toJoulesPerMole * that.toMoles)

  def toJoulesPerMole = to(JoulesPerMole)
}

object MolarEnergy extends Dimension[MolarEnergy] {
  private[energy] def apply[A](n: A, unit: MolarEnergyUnit)(implicit num: Numeric[A]) = new MolarEnergy(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "MolarEnergy"
  def primaryUnit = JoulesPerMole
  def siUnit = JoulesPerMole
  def units = Set(JoulesPerMole)
}

trait MolarEnergyUnit extends UnitOfMeasure[MolarEnergy] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MolarEnergy(n, this)
}

object JoulesPerMole extends MolarEnergyUnit with PrimaryUnit with SiUnit {
  val symbol = Joules.symbol + "/" + Moles.symbol
}

object MolarEnergyConversions {
  lazy val joulePerMole = JoulesPerMole(1)

  implicit class MolarEnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerMole = JoulesPerMole(n)
  }

  implicit object MolarEnergyNumeric extends AbstractQuantityNumeric[MolarEnergy](MolarEnergy.primaryUnit)
}
