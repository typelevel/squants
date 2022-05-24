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

final case class AngularAcceleration[A: Numeric] private [squants2]  (value: A, unit: AngularAccelerationUnit)
  extends Quantity[A, AngularAcceleration.type] {
  override type Q[B] = AngularAcceleration[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toArcsecondsPerSecondSquared: A = to(ArcsecondsPerSecondSquared)
  def toArcminutesPerSecondSquared: A = to(ArcminutesPerSecondSquared)
  def toGradiansPerSecondSquared: A = to(GradiansPerSecondSquared)
  def toDegreesPerSecondSquared: A = to(DegreesPerSecondSquared)
  def toRadiansPerSecondSquared: A = to(RadiansPerSecondSquared)
  def toTurnsPerSecondSquared: A = to(TurnsPerSecondSquared)
}

object AngularAcceleration extends Dimension("AngularAcceleration") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = RadiansPerSecondSquared
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = RadiansPerSecondSquared
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(ArcsecondsPerSecondSquared, ArcminutesPerSecondSquared, GradiansPerSecondSquared, DegreesPerSecondSquared, RadiansPerSecondSquared, TurnsPerSecondSquared)

  implicit class AngularAccelerationCons[A](a: A)(implicit num: Numeric[A]) {
    def arcsecondsPerSecondSquared: AngularAcceleration[A] = ArcsecondsPerSecondSquared(a)
    def arcminutesPerSecondSquared: AngularAcceleration[A] = ArcminutesPerSecondSquared(a)
    def gradiansPerSecondSquared: AngularAcceleration[A] = GradiansPerSecondSquared(a)
    def degreesPerSecondSquared: AngularAcceleration[A] = DegreesPerSecondSquared(a)
    def radiansPerSecondSquared: AngularAcceleration[A] = RadiansPerSecondSquared(a)
    def turnsPerSecondSquared: AngularAcceleration[A] = TurnsPerSecondSquared(a)
  }

  lazy val arcsecondsPerSecondSquared: AngularAcceleration[Int] = ArcsecondsPerSecondSquared(1)
  lazy val arcminutesPerSecondSquared: AngularAcceleration[Int] = ArcminutesPerSecondSquared(1)
  lazy val gradiansPerSecondSquared: AngularAcceleration[Int] = GradiansPerSecondSquared(1)
  lazy val degreesPerSecondSquared: AngularAcceleration[Int] = DegreesPerSecondSquared(1)
  lazy val radiansPerSecondSquared: AngularAcceleration[Int] = RadiansPerSecondSquared(1)
  lazy val turnsPerSecondSquared: AngularAcceleration[Int] = TurnsPerSecondSquared(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AngularAccelerationNumeric[A]()
  private case class AngularAccelerationNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, AngularAcceleration.type], y: Quantity[A, AngularAcceleration.type]): Quantity[A, AngularAcceleration.this.type] =
      RadiansPerSecondSquared(x.to(RadiansPerSecondSquared) * y.to(RadiansPerSecondSquared))
  }
}

abstract class AngularAccelerationUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AngularAcceleration.type] {
  override lazy val dimension: AngularAcceleration.type = AngularAcceleration
  override def apply[A: Numeric](value: A): AngularAcceleration[A] = AngularAcceleration(value, this)
}

case object ArcsecondsPerSecondSquared extends AngularAccelerationUnit("asec/s²", 4.84813681109536E-6)
case object ArcminutesPerSecondSquared extends AngularAccelerationUnit("amin/s²", 2.908882086657216E-4)
case object GradiansPerSecondSquared extends AngularAccelerationUnit("grad/s²", 0.015707963267948967)
case object DegreesPerSecondSquared extends AngularAccelerationUnit("°/s²", 0.017453292519943295)
case object RadiansPerSecondSquared extends AngularAccelerationUnit("rad/s²", 1.0) with PrimaryUnit with SiUnit
case object TurnsPerSecondSquared extends AngularAccelerationUnit("turns/s²", 6.283185307179586)
