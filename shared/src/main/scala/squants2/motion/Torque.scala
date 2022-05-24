/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Torque[A: Numeric] private [squants2]  (value: A, unit: TorqueUnit)
  extends Quantity[A, Torque.type] {
  override type Q[B] = Torque[B]

  // BEGIN CUSTOM OPS
  //  def /[B](that: MomentOfInertia[B])(implicit f: B => A): AngularAcceleration[A] = ???
  // END CUSTOM OPS

  def toNewtonMeters: A = to(NewtonMeters)
  def toPoundFeet: A = to(PoundFeet)
}

object Torque extends Dimension("Torque") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = NewtonMeters
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = NewtonMeters
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(NewtonMeters, PoundFeet)

  implicit class TorqueCons[A](a: A)(implicit num: Numeric[A]) {
    def newtonMeters: Torque[A] = NewtonMeters(a)
    def poundFeet: Torque[A] = PoundFeet(a)
  }

  lazy val newtonMeters: Torque[Int] = NewtonMeters(1)
  lazy val poundFeet: Torque[Int] = PoundFeet(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = TorqueNumeric[A]()
  private case class TorqueNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Torque.type], y: Quantity[A, Torque.type]): Quantity[A, Torque.this.type] =
      NewtonMeters(x.to(NewtonMeters) * y.to(NewtonMeters))
  }
}

abstract class TorqueUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Torque.type] {
  override def dimension: Torque.type = Torque
  override def apply[A: Numeric](value: A): Torque[A] = Torque(value, this)
}

case object NewtonMeters extends TorqueUnit("N‧m", 1) with PrimaryUnit with SiBaseUnit
case object PoundFeet extends TorqueUnit("lb‧ft", 1.3558206599672968)
