/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental


/**
 * Represents a quantity of some thing for which there is no dimension.
 *
 * This may be used to represent counts or other discrete amounts of everyday life,
 * but may also represent ratios between like quantities where the units have cancelled out.
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double the amount
 */
final class Dimensionless[N : SquantsNumeric] private (val value: N, val unit: DimensionlessUnit)
    extends Quantity[Dimensionless[_], N] {

  import sqNum.mkSquantsNumericOps

  def dimension = Dimensionless

  def *(that: Dimensionless[N]) = Each(toEach * that.toEach)
  def *(that: Quantity[_, N]) = that * toEach

  def toPercent = to(Percent)
  def toEach = to(Each)
  def toDozen = to(Dozen)
  def toScore = to(Score)
  def toGross = to(Gross)
}

/**
 * Factory singleton for [[squants.Dimensionless]]
 */
object Dimensionless extends Dimension[Dimensionless[_]] {
  def apply[N : SquantsNumeric](n: N, unit: DimensionlessUnit) = new Dimensionless(n, unit)
  def apply(value: Any) = parse(value)
  def name = "Dimensionless"
  def primaryUnit = Each
  def siUnit = Each
  def units = Set(Each, Percent, Dozen, Score, Gross)
}

/**
 * Base trait for units of [[squants.Dimensionless]]
 *
 * The DimensionlessUnit is a useful paradox
 */
trait DimensionlessUnit extends UnitOfMeasure[Dimensionless[_]] with UnitConverter {
  def apply[A](n: A)(implicit num: SquantsNumeric[A]) = Dimensionless(n, this)
}

/**
 * Represents a unit of singles
 */
object Each extends DimensionlessUnit with PrimaryUnit with SiUnit {
  val symbol = "ea"
}

/**
 * Represents a number of hundredths (0.01)
 */
object Percent extends DimensionlessUnit {
  val conversionFactor = 1e-2
  val symbol = "%"
}

/**
 * Represents a unit of dozen (12)
 */
object Dozen extends DimensionlessUnit {
  val conversionFactor = 12d
  val symbol = "dz"
}

/**
 * Represents a unit of scores (20)
 */
object Score extends DimensionlessUnit {
  val conversionFactor = 20d
  val symbol = "score"
}

/**
 * Represents a unit of gross (144)
 */
object Gross extends DimensionlessUnit {
  val conversionFactor = 144d
  val symbol = "gr"
}

object DimensionlessConversions {
  lazy val percent = Percent(1d)
  lazy val each = Each(1d)
  lazy val dozen = Dozen(1d)
  lazy val score = Score(1d)
  lazy val gross = Gross(1d)
  lazy val hundred = Each(1e2)
  lazy val thousand = Each(1e3)
  lazy val million = Each(1e6)

  implicit class DimensionlessConversions[A](n: A)(implicit sqNum: SquantsNumeric[A]) {
    def percent = Percent(n)
    def each = Each(n)
    def ea = Each(n)
    def dozen = Dozen(n)
    def dz = Dozen(n)
    def score = Score(n)
    def gross = Gross(n)
    def gr = Gross(n)
    def hundred = Each(sqNum.times(n, sqNum.fromDouble(1e2)))
    def thousand = Each(sqNum.times(n, sqNum.fromDouble(1e3)))
    def million = Each(sqNum.times(n, sqNum.fromDouble(1e6)))
  }

//  implicit object DimensionlessNumeric extends AbstractQuantityNumeric[Dimensionless](Dimensionless.primaryUnit) {
//    /**
//     * Dimensionless quantities support the times operation.
//     * This method overrides the default [[squants.AbstractQuantityNumeric.times]] which thrown an exception
//     *
//     * @param x Dimensionless
//     * @param y Dimensionless
//     * @return
//     */
//    override def times(x: Dimensionless, y: Dimensionless) = x * y
//  }
}
