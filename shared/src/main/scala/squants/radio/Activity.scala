/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2018, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants._

/**
 * @author  Hunter Payne
 *
 * @param value Double
 */
final class Activity private (
  val value: Double, val unit: ActivityUnit)
    extends Quantity[Activity] {

  def dimension = Activity

  def /(that: AreaTime): ParticleFlux = BecquerelsPerSquareMeterSecond(
    this.toBecquerels / that.toSquareMeterSeconds)

  def toCuries = to(Curies)
  def toBecquerels = to(Becquerels)
  def toRutherfords = to(Rutherfords)
}

object Activity extends Dimension[Activity] {
  private[radio] def apply[A](n: A, unit: ActivityUnit)(
    implicit num: Numeric[A]) = 
    new Activity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Activity"
  def primaryUnit = Becquerels
  def siUnit = Becquerels
  def units = Set(Becquerels, Curies, Rutherfords)
}

trait ActivityUnit 
    extends UnitOfMeasure[Activity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Activity(n, this)
}

object Curies extends ActivityUnit {
  val conversionFactor = 3.7 * Math.pow(10, 10)
  val symbol = "Ci"
}

object Rutherfords extends ActivityUnit {
  val conversionFactor = 1000000.0
  val symbol = "Rd"
}

object Becquerels extends ActivityUnit with PrimaryUnit with SiUnit {
  val symbol = "Bq"
}

object ActivityConversions {
  lazy val curie = Curies(1)
  lazy val rutherford = Rutherfords(1)
  lazy val becquerel = Becquerels(1)

  implicit class ActivityConversions[A](n: A)(
    implicit num: Numeric[A]) {
    def curies = Curies(n)
    def rutherfords = Rutherfords(n)
    def becquerels = Becquerels(n)
  }

  implicit object ActivityNumeric 
      extends AbstractQuantityNumeric[Activity](Activity.primaryUnit)
}
