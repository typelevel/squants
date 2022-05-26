/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._

final case class Torque[A: Numeric] private[squants2] (value: A, unit: TorqueUnit)
  extends Quantity[A, Torque] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: MomentOfInertia[B])(implicit f: B => A): AngularAcceleration[A] = ???
  // END CUSTOM OPS

  def toNewtonMeters[B: Numeric](implicit f: A => B): B = toNum[B](NewtonMeters)
  def toPoundFeet[B: Numeric](implicit f: A => B): B = toNum[B](PoundFeet)
}

object Torque extends Dimension[Torque]("Torque") {

  override def primaryUnit: UnitOfMeasure[Torque] with PrimaryUnit[Torque] = NewtonMeters
  override def siUnit: UnitOfMeasure[Torque] with SiUnit[Torque] = NewtonMeters
  override lazy val units: Set[UnitOfMeasure[Torque]] = 
    Set(NewtonMeters, PoundFeet)

  implicit class TorqueCons[A](a: A)(implicit num: Numeric[A]) {
    def newtonMeters: Torque[A] = NewtonMeters(a)
    def poundFeet: Torque[A] = PoundFeet(a)
  }

  lazy val newtonMeters: Torque[Int] = NewtonMeters(1)
  lazy val poundFeet: Torque[Int] = PoundFeet(1)

}

abstract class TorqueUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Torque] {
  override def dimension: Dimension[Torque] = Torque
  override def apply[A: Numeric](value: A): Torque[A] = Torque(value, this)
}

case object NewtonMeters extends TorqueUnit("N‧m", 1) with PrimaryUnit[Torque] with SiBaseUnit[Torque]
case object PoundFeet extends TorqueUnit("lb‧ft", 1.3558206599672968)
