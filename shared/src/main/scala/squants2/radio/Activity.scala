/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Activity[A: Numeric] private [squants2]  (value: A, unit: ActivityUnit)
  extends Quantity[A, Activity.type] {
  override type Q[B] = Activity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toBecquerels: A = to(Becquerels)
  def toRutherfords: A = to(Rutherfords)
  def toCuries: A = to(Curies)
}

object Activity extends Dimension("Activity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Becquerels
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Becquerels
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Becquerels, Rutherfords, Curies)

  implicit class ActivityCons[A](a: A)(implicit num: Numeric[A]) {
    def becquerels: Activity[A] = Becquerels(a)
    def rutherfords: Activity[A] = Rutherfords(a)
    def curies: Activity[A] = Curies(a)
  }

  lazy val becquerels: Activity[Int] = Becquerels(1)
  lazy val rutherfords: Activity[Int] = Rutherfords(1)
  lazy val curies: Activity[Int] = Curies(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ActivityNumeric[A]()
  private case class ActivityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Activity.type], y: Quantity[A, Activity.type]): Quantity[A, Activity.this.type] =
      Becquerels(x.to(Becquerels) * y.to(Becquerels))
  }
}

abstract class ActivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Activity.type] {
  override lazy val dimension: Activity.type = Activity
  override def apply[A: Numeric](value: A): Activity[A] = Activity(value, this)
}

case object Becquerels extends ActivityUnit("Bq", 1.0) with PrimaryUnit with SiUnit
case object Rutherfords extends ActivityUnit("Rd", 1000000.0)
case object Curies extends ActivityUnit("Ci", 3.7E10)
