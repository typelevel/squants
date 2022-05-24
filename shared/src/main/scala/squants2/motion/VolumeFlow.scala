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

final case class VolumeFlow[A: Numeric] private [squants2]  (value: A, unit: VolumeFlowUnit)
  extends Quantity[A, VolumeFlow.type] {
  override type Q[B] = VolumeFlow[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toNanolitresPerDay: A = to(NanolitresPerDay)
  def toNanolitresPerHour: A = to(NanolitresPerHour)
  def toMicrolitresPerDay: A = to(MicrolitresPerDay)
  def toNanolitresPerMinute: A = to(NanolitresPerMinute)
  def toMicrolitresPerHour: A = to(MicrolitresPerHour)
  def toNanolitresPerSecond: A = to(NanolitresPerSecond)
  def toMillilitresPerDay: A = to(MillilitresPerDay)
  def toMicrolitresPerMinute: A = to(MicrolitresPerMinute)
  def toMillilitresPerHour: A = to(MillilitresPerHour)
  def toMicrolitresPerSecond: A = to(MicrolitresPerSecond)
  def toLitresPerDay: A = to(LitresPerDay)
  def toMillilitresPerMinute: A = to(MillilitresPerMinute)
  def toGallonsPerDay: A = to(GallonsPerDay)
  def toLitresPerHour: A = to(LitresPerHour)
  def toMillilitresPerSecond: A = to(MillilitresPerSecond)
  def toGallonsPerHour: A = to(GallonsPerHour)
  def toCubicFeetPerHour: A = to(CubicFeetPerHour)
  def toLitresPerMinute: A = to(LitresPerMinute)
  def toGallonsPerMinute: A = to(GallonsPerMinute)
  def toLitresPerSecond: A = to(LitresPerSecond)
  def toGallonsPerSecond: A = to(GallonsPerSecond)
  def toCubicMetersPerSecond: A = to(CubicMetersPerSecond)
}

object VolumeFlow extends Dimension("VolumeFlow") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CubicMetersPerSecond
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CubicMetersPerSecond
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(NanolitresPerDay, NanolitresPerHour, MicrolitresPerDay, NanolitresPerMinute, MicrolitresPerHour, NanolitresPerSecond, MillilitresPerDay, MicrolitresPerMinute, MillilitresPerHour, MicrolitresPerSecond, LitresPerDay, MillilitresPerMinute, GallonsPerDay, LitresPerHour, MillilitresPerSecond, GallonsPerHour, CubicFeetPerHour, LitresPerMinute, GallonsPerMinute, LitresPerSecond, GallonsPerSecond, CubicMetersPerSecond)

  implicit class VolumeFlowCons[A](a: A)(implicit num: Numeric[A]) {
    def nanolitresPerDay: VolumeFlow[A] = NanolitresPerDay(a)
    def nanolitresPerHour: VolumeFlow[A] = NanolitresPerHour(a)
    def microlitresPerDay: VolumeFlow[A] = MicrolitresPerDay(a)
    def nanolitresPerMinute: VolumeFlow[A] = NanolitresPerMinute(a)
    def microlitresPerHour: VolumeFlow[A] = MicrolitresPerHour(a)
    def nanolitresPerSecond: VolumeFlow[A] = NanolitresPerSecond(a)
    def millilitresPerDay: VolumeFlow[A] = MillilitresPerDay(a)
    def microlitresPerMinute: VolumeFlow[A] = MicrolitresPerMinute(a)
    def millilitresPerHour: VolumeFlow[A] = MillilitresPerHour(a)
    def microlitresPerSecond: VolumeFlow[A] = MicrolitresPerSecond(a)
    def litresPerDay: VolumeFlow[A] = LitresPerDay(a)
    def millilitresPerMinute: VolumeFlow[A] = MillilitresPerMinute(a)
    def gallonsPerDay: VolumeFlow[A] = GallonsPerDay(a)
    def litresPerHour: VolumeFlow[A] = LitresPerHour(a)
    def millilitresPerSecond: VolumeFlow[A] = MillilitresPerSecond(a)
    def gallonsPerHour: VolumeFlow[A] = GallonsPerHour(a)
    def cubicFeetPerHour: VolumeFlow[A] = CubicFeetPerHour(a)
    def litresPerMinute: VolumeFlow[A] = LitresPerMinute(a)
    def gallonsPerMinute: VolumeFlow[A] = GallonsPerMinute(a)
    def litresPerSecond: VolumeFlow[A] = LitresPerSecond(a)
    def gallonsPerSecond: VolumeFlow[A] = GallonsPerSecond(a)
    def cubicMetersPerSecond: VolumeFlow[A] = CubicMetersPerSecond(a)
  }

  lazy val nanolitresPerDay: VolumeFlow[Int] = NanolitresPerDay(1)
  lazy val nanolitresPerHour: VolumeFlow[Int] = NanolitresPerHour(1)
  lazy val microlitresPerDay: VolumeFlow[Int] = MicrolitresPerDay(1)
  lazy val nanolitresPerMinute: VolumeFlow[Int] = NanolitresPerMinute(1)
  lazy val microlitresPerHour: VolumeFlow[Int] = MicrolitresPerHour(1)
  lazy val nanolitresPerSecond: VolumeFlow[Int] = NanolitresPerSecond(1)
  lazy val millilitresPerDay: VolumeFlow[Int] = MillilitresPerDay(1)
  lazy val microlitresPerMinute: VolumeFlow[Int] = MicrolitresPerMinute(1)
  lazy val millilitresPerHour: VolumeFlow[Int] = MillilitresPerHour(1)
  lazy val microlitresPerSecond: VolumeFlow[Int] = MicrolitresPerSecond(1)
  lazy val litresPerDay: VolumeFlow[Int] = LitresPerDay(1)
  lazy val millilitresPerMinute: VolumeFlow[Int] = MillilitresPerMinute(1)
  lazy val gallonsPerDay: VolumeFlow[Int] = GallonsPerDay(1)
  lazy val litresPerHour: VolumeFlow[Int] = LitresPerHour(1)
  lazy val millilitresPerSecond: VolumeFlow[Int] = MillilitresPerSecond(1)
  lazy val gallonsPerHour: VolumeFlow[Int] = GallonsPerHour(1)
  lazy val cubicFeetPerHour: VolumeFlow[Int] = CubicFeetPerHour(1)
  lazy val litresPerMinute: VolumeFlow[Int] = LitresPerMinute(1)
  lazy val gallonsPerMinute: VolumeFlow[Int] = GallonsPerMinute(1)
  lazy val litresPerSecond: VolumeFlow[Int] = LitresPerSecond(1)
  lazy val gallonsPerSecond: VolumeFlow[Int] = GallonsPerSecond(1)
  lazy val cubicMetersPerSecond: VolumeFlow[Int] = CubicMetersPerSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = VolumeFlowNumeric[A]()
  private case class VolumeFlowNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, VolumeFlow.type], y: Quantity[A, VolumeFlow.type]): Quantity[A, VolumeFlow.this.type] =
      CubicMetersPerSecond(x.to(CubicMetersPerSecond) * y.to(CubicMetersPerSecond))
  }
}

abstract class VolumeFlowUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[VolumeFlow.type] {
  override lazy val dimension: VolumeFlow.type = VolumeFlow
  override def apply[A: Numeric](value: A): VolumeFlow[A] = VolumeFlow(value, this)
}

case object NanolitresPerDay extends VolumeFlowUnit("nl/d", 1.1574074074074075E-17) with SiUnit
case object NanolitresPerHour extends VolumeFlowUnit("nl/h", 2.7777777777777785E-16) with SiUnit
case object MicrolitresPerDay extends VolumeFlowUnit("µl/d", 1.1574074074074075E-14) with SiUnit
case object NanolitresPerMinute extends VolumeFlowUnit("nl/min", 1.666666666666667E-14) with SiUnit
case object MicrolitresPerHour extends VolumeFlowUnit("µl/h", 2.777777777777778E-13) with SiUnit
case object NanolitresPerSecond extends VolumeFlowUnit("nl/s", 1.0000000000000002E-12) with SiUnit
case object MillilitresPerDay extends VolumeFlowUnit("ml/d", 1.1574074074074074E-11) with SiUnit
case object MicrolitresPerMinute extends VolumeFlowUnit("µl/min", 1.6666666666666667E-11) with SiUnit
case object MillilitresPerHour extends VolumeFlowUnit("ml/h", 2.7777777777777777E-10) with SiUnit
case object MicrolitresPerSecond extends VolumeFlowUnit("µl/s", 1.0E-9) with SiUnit
case object LitresPerDay extends VolumeFlowUnit("L/d", 1.1574074074074074E-8) with SiUnit
case object MillilitresPerMinute extends VolumeFlowUnit("ml/min", 1.6666666666666667E-8) with SiUnit
case object GallonsPerDay extends VolumeFlowUnit("GPD", 4.3812636388888886E-8)
case object LitresPerHour extends VolumeFlowUnit("L/h", 2.7777777777777776E-7) with SiUnit
case object MillilitresPerSecond extends VolumeFlowUnit("ml/s", 1.0E-6) with SiUnit
case object GallonsPerHour extends VolumeFlowUnit("GPH", 1.0515032733333332E-6)
case object CubicFeetPerHour extends VolumeFlowUnit("ft³/hr", 7.86583791483871E-6)
case object LitresPerMinute extends VolumeFlowUnit("L/min", 1.6666666666666667E-5) with SiUnit
case object GallonsPerMinute extends VolumeFlowUnit("GPM", 6.309019639999999E-5)
case object LitresPerSecond extends VolumeFlowUnit("L/s", 0.001) with SiUnit
case object GallonsPerSecond extends VolumeFlowUnit("GPS", 0.0037854117839999997)
case object CubicMetersPerSecond extends VolumeFlowUnit("m³/s", 1.0) with PrimaryUnit with SiUnit
