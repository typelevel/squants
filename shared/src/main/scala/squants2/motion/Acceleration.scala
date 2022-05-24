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

final case class Acceleration[A: Numeric] private [squants2]  (value: A, unit: AccelerationUnit)
  extends Quantity[A, Acceleration.type] {
  override type Q[B] = Acceleration[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toUsMilesPerHourSquared: A = to(UsMilesPerHourSquared)
  def toMillimetersPerSecondSquared: A = to(MillimetersPerSecondSquared)
  def toFeetPerSecondSquared: A = to(FeetPerSecondSquared)
  def toMetersPerSecondSquared: A = to(MetersPerSecondSquared)
  def toEarthGravities: A = to(EarthGravities)
}

object Acceleration extends Dimension("Acceleration") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = MetersPerSecondSquared
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = MetersPerSecondSquared
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(UsMilesPerHourSquared, MillimetersPerSecondSquared, FeetPerSecondSquared, MetersPerSecondSquared, EarthGravities)

  implicit class AccelerationCons[A](a: A)(implicit num: Numeric[A]) {
    def usMilesPerHourSquared: Acceleration[A] = UsMilesPerHourSquared(a)
    def millimetersPerSecondSquared: Acceleration[A] = MillimetersPerSecondSquared(a)
    def feetPerSecondSquared: Acceleration[A] = FeetPerSecondSquared(a)
    def metersPerSecondSquared: Acceleration[A] = MetersPerSecondSquared(a)
    def earthGravities: Acceleration[A] = EarthGravities(a)
  }

  lazy val usMilesPerHourSquared: Acceleration[Int] = UsMilesPerHourSquared(1)
  lazy val millimetersPerSecondSquared: Acceleration[Int] = MillimetersPerSecondSquared(1)
  lazy val feetPerSecondSquared: Acceleration[Int] = FeetPerSecondSquared(1)
  lazy val metersPerSecondSquared: Acceleration[Int] = MetersPerSecondSquared(1)
  lazy val earthGravities: Acceleration[Int] = EarthGravities(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AccelerationNumeric[A]()
  private case class AccelerationNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Acceleration.type], y: Quantity[A, Acceleration.type]): Quantity[A, Acceleration.this.type] =
      MetersPerSecondSquared(x.to(MetersPerSecondSquared) * y.to(MetersPerSecondSquared))
  }
}

abstract class AccelerationUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Acceleration.type] {
  override lazy val dimension: Acceleration.type = Acceleration
  override def apply[A: Numeric](value: A): Acceleration[A] = Acceleration(value, this)
}

case object UsMilesPerHourSquared extends AccelerationUnit("mph²", 1.2417802613333333E-4)
case object MillimetersPerSecondSquared extends AccelerationUnit("mm/s²", 0.001) with SiUnit
case object FeetPerSecondSquared extends AccelerationUnit("ft/s²", 0.3048006096)
case object MetersPerSecondSquared extends AccelerationUnit("m/s²", 1.0) with PrimaryUnit with SiUnit
case object EarthGravities extends AccelerationUnit("g", 9.80665)
