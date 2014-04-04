/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.time.{ Frequency, TimeIntegral }

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
final class Dimensionless private (val value: Double)
    extends Quantity[Dimensionless]
    with TimeIntegral[Frequency] {

  def valueUnit = Each

  def *(that: Dimensionless) = Each(toEach * that.toEach)
  def *(that: Quantity[_]) = that * toEach
  def /(that: Time): Frequency = Frequency(this, that)
  def /(that: Frequency): Time = that.time * (this / that.change)

  def toEach = to(Each)
  def toDozen = to(Dozen)
  def toScore = to(Score)
  def toGross = to(Gross)
}

/**
 * Factory singleton for [[squants.Dimensionless]]
 */
object Dimensionless {
  def apply(value: Double) = new Dimensionless(value)
}

/**
 * Base trait for units of [[squants.Dimensionless]]
 *
 * The DimensionlessUnit is a useful paradox
 */
trait DimensionlessUnit extends UnitOfMeasure[Dimensionless] with UnitMultiplier {
  def apply(n: Double) = Dimensionless(convertFrom(n))
  def unapply(c: Dimensionless) = Some(convertTo(c.value))
}

/**
 * Represents a unit of singles
 */
object Each extends DimensionlessUnit with ValueUnit {
  val symbol = "ea"
}

/**
 * Represents a unit of dozen (12)
 */
object Dozen extends DimensionlessUnit {
  val multiplier = 12D
  val symbol = "dz"
}

/**
 * Represents a unit of scores (20)
 */
object Score extends DimensionlessUnit {
  val multiplier = 20D
  val symbol = "score"
}

/**
 * Represents a unit of gross (144)
 */
object Gross extends DimensionlessUnit {
  val multiplier = 144D
  val symbol = "gr"
}

object DimensionlessConversions {
  lazy val each = Each(1)
  lazy val dozen = Dozen(1)
  lazy val score = Score(1)
  lazy val gross = Gross(1)
  lazy val hundred = Each(100)
  lazy val thousand = Each(1000)
  lazy val million = Each(1000000)

  implicit class DimensionlessConversions(d: Double) {
    def each = Each(d)
    def ea = Each(d)
    def dozen = Dozen(d)
    def dz = Dozen(d)
    def score = Score(d)
    def gross = Gross(d)
    def gr = Gross(d)
    def hundred = Each(d * 100)
    def thousand = Each(d * 1000)
    def million = Each(d * 1000000D)
  }

  implicit object DimensionlessNumeric extends AbstractQuantityNumeric[Dimensionless](Each) {
    /**
     * Dimensionless quantities support the times operation.
     * This method overrides the default [[squants.AbstractQuantityNumeric.times]] which thrown an exception
     *
     * @param x Dimensionless
     * @param y Dimensionless
     * @return
     */
    override def times(x: Dimensionless, y: Dimensionless) = x * y
  }
}
