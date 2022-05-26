/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class Activity[A: Numeric] private[squants2] (value: A, unit: ActivityUnit)
  extends Quantity[A, Activity] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: AreaTime[B])(implicit f: B => A): ParticleFlux[A] = ???
  // END CUSTOM OPS

  def toBecquerels[B: Numeric](implicit f: A => B): B = toNum[B](Becquerels)
  def toRutherfords[B: Numeric](implicit f: A => B): B = toNum[B](Rutherfords)
  def toCuries[B: Numeric](implicit f: A => B): B = toNum[B](Curies)
}

object Activity extends Dimension[Activity]("Activity") {

  override def primaryUnit: UnitOfMeasure[Activity] with PrimaryUnit[Activity] = Becquerels
  override def siUnit: UnitOfMeasure[Activity] with SiUnit[Activity] = Becquerels
  override lazy val units: Set[UnitOfMeasure[Activity]] = 
    Set(Becquerels, Rutherfords, Curies)

  implicit class ActivityCons[A](a: A)(implicit num: Numeric[A]) {
    def becquerels: Activity[A] = Becquerels(a)
    def rutherfords: Activity[A] = Rutherfords(a)
    def curies: Activity[A] = Curies(a)
  }

  lazy val becquerels: Activity[Int] = Becquerels(1)
  lazy val rutherfords: Activity[Int] = Rutherfords(1)
  lazy val curies: Activity[Int] = Curies(1)

}

abstract class ActivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Activity] {
  override def dimension: Dimension[Activity] = Activity
  override def apply[A: Numeric](value: A): Activity[A] = Activity(value, this)
}

case object Becquerels extends ActivityUnit("Bq", 1) with PrimaryUnit[Activity] with SiUnit[Activity]
case object Rutherfords extends ActivityUnit("Rd", MetricSystem.Mega)
case object Curies extends ActivityUnit("Ci", 3.7E10)
