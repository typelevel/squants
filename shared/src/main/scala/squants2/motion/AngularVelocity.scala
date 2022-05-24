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

final case class AngularVelocity[A: Numeric] private [squants2]  (value: A, unit: AngularVelocityUnit)
  extends Quantity[A, AngularVelocity.type] {
  override type Q[B] = AngularVelocity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toGradiansPerSecond: A = to(GradiansPerSecond)
  def toDegreesPerSecond: A = to(DegreesPerSecond)
  def toRadiansPerSecond: A = to(RadiansPerSecond)
  def toTurnsPerSecond: A = to(TurnsPerSecond)
}

object AngularVelocity extends Dimension("AngularVelocity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = RadiansPerSecond
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = RadiansPerSecond
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(GradiansPerSecond, DegreesPerSecond, RadiansPerSecond, TurnsPerSecond)

  implicit class AngularVelocityCons[A](a: A)(implicit num: Numeric[A]) {
    def gradiansPerSecond: AngularVelocity[A] = GradiansPerSecond(a)
    def degreesPerSecond: AngularVelocity[A] = DegreesPerSecond(a)
    def radiansPerSecond: AngularVelocity[A] = RadiansPerSecond(a)
    def turnsPerSecond: AngularVelocity[A] = TurnsPerSecond(a)
  }

  lazy val gradiansPerSecond: AngularVelocity[Int] = GradiansPerSecond(1)
  lazy val degreesPerSecond: AngularVelocity[Int] = DegreesPerSecond(1)
  lazy val radiansPerSecond: AngularVelocity[Int] = RadiansPerSecond(1)
  lazy val turnsPerSecond: AngularVelocity[Int] = TurnsPerSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AngularVelocityNumeric[A]()
  private case class AngularVelocityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, AngularVelocity.type], y: Quantity[A, AngularVelocity.type]): Quantity[A, AngularVelocity.this.type] =
      RadiansPerSecond(x.to(RadiansPerSecond) * y.to(RadiansPerSecond))
  }
}

abstract class AngularVelocityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AngularVelocity.type] {
  override lazy val dimension: AngularVelocity.type = AngularVelocity
  override def apply[A: Numeric](value: A): AngularVelocity[A] = AngularVelocity(value, this)
}

case object GradiansPerSecond extends AngularVelocityUnit("grad/s", 0.015707963267948967)
case object DegreesPerSecond extends AngularVelocityUnit("°/s", 0.017453292519943295)
case object RadiansPerSecond extends AngularVelocityUnit("rad/s", 1.0) with PrimaryUnit with SiUnit
case object TurnsPerSecond extends AngularVelocityUnit("turns/s", 6.283185307179586)
