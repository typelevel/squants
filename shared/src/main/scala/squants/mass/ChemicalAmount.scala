/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value in [[squants.mass.Moles]]
 */
final class ChemicalAmount private (val value: Double, val unit: ChemicalAmountUnit)
    extends Quantity[ChemicalAmount] {

  def dimension = ChemicalAmount

  def /(that: Volume) = ??? // returns SubstanceConcentration

  def toMoles = to(Moles)
  def toPoundMoles = to(PoundMoles)
}

object ChemicalAmount extends Dimension[ChemicalAmount] with BaseDimension {
  private[mass] def apply[A](n: A, unit: ChemicalAmountUnit)(implicit num: Numeric[A]) = new ChemicalAmount(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  val name = "ChemicalAmount"
  def primaryUnit = Moles
  def siUnit = Moles
  def units = Set(Moles, PoundMoles)
  def dimensionSymbol = "N"
}

trait ChemicalAmountUnit extends UnitOfMeasure[ChemicalAmount] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ChemicalAmount(n, this)
}

object Moles extends ChemicalAmountUnit with PrimaryUnit with SiBaseUnit {
  val symbol = "mol"
}

object PoundMoles extends ChemicalAmountUnit {
  val symbol = "lb-mol"
  val conversionFactor = 453.59237
}

object ChemicalAmountConversions {
  lazy val mole = Moles(1)
  lazy val poundMole = PoundMoles(1)

  implicit class ChemicalAmountConversions[A](n: A)(implicit num: Numeric[A]) {
    def moles = Moles(n)
    def poundMoles = PoundMoles(n)
  }

  implicit object ChemicalAmountNumeric extends AbstractQuantityNumeric[ChemicalAmount](ChemicalAmount.primaryUnit)
}