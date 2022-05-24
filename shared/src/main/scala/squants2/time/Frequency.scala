/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.time

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Frequency[A: Numeric] private [squants2]  (value: A, unit: FrequencyUnit)
  extends Quantity[A, Frequency.type] {
  override type Q[B] = Frequency[B]

  // BEGIN CUSTOM OPS
  //  def *[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: Acceleration[B])(implicit f: B => A): Jerk[A] = ???
  //  def *[B](that: Angle[B])(implicit f: B => A): AngularVelocity[A] = ???
  //  def *[B](that: Dimensionless[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: ElectricCharge[B])(implicit f: B => A): ElectricCurrent[A] = ???
  //  def *[B](that: Energy[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: Force[B])(implicit f: B => A): Yank[A] = ???
  //  def *[B](that: Information[B])(implicit f: B => A): DataRate[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): Velocity[A] = ???
  //  def *[B](that: LuminousEnergy[B])(implicit f: B => A): LuminousFlux[A] = ???
  //  def *[B](that: LuminousExposure[B])(implicit f: B => A): Illuminance[A] = ???
  //  def *[B](that: MagneticFlux[B])(implicit f: B => A): ElectricPotential[A] = ???
  //  def *[B](that: Mass[B])(implicit f: B => A): MassFlow[A] = ???
  //  def *[B](that: Momentum[B])(implicit f: B => A): Force[A] = ???
  //  def *[B](that: Power[B])(implicit f: B => A): PowerRamp[A] = ???
  //  def *[B](that: Pressure[B])(implicit f: B => A): PressureChange[A] = ???
  //  def *[B](that: Velocity[B])(implicit f: B => A): Acceleration[A] = ???
  //  def *[B](that: Volume[B])(implicit f: B => A): VolumeFlow[A] = ???
  // END CUSTOM OPS

  def toRevolutionsPerMinute: A = to(RevolutionsPerMinute)
  def toHertz: A = to(Hertz)
  def toKilohertz: A = to(Kilohertz)
  def toMegahertz: A = to(Megahertz)
  def toGigahertz: A = to(Gigahertz)
  def toTerahertz: A = to(Terahertz)
}

object Frequency extends Dimension("Frequency") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Hertz
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Hertz
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(RevolutionsPerMinute, Hertz, Kilohertz, Megahertz, Gigahertz, Terahertz)

  implicit class FrequencyCons[A](a: A)(implicit num: Numeric[A]) {
    def revolutionsPerMinute: Frequency[A] = RevolutionsPerMinute(a)
    def hertz: Frequency[A] = Hertz(a)
    def kilohertz: Frequency[A] = Kilohertz(a)
    def megahertz: Frequency[A] = Megahertz(a)
    def gigahertz: Frequency[A] = Gigahertz(a)
    def terahertz: Frequency[A] = Terahertz(a)
  }

  lazy val revolutionsPerMinute: Frequency[Int] = RevolutionsPerMinute(1)
  lazy val hertz: Frequency[Int] = Hertz(1)
  lazy val kilohertz: Frequency[Int] = Kilohertz(1)
  lazy val megahertz: Frequency[Int] = Megahertz(1)
  lazy val gigahertz: Frequency[Int] = Gigahertz(1)
  lazy val terahertz: Frequency[Int] = Terahertz(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = FrequencyNumeric[A]()
  private case class FrequencyNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Frequency.type], y: Quantity[A, Frequency.type]): Quantity[A, Frequency.this.type] =
      Hertz(x.to(Hertz) * y.to(Hertz))
  }
}

abstract class FrequencyUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Frequency.type] {
  override def dimension: Frequency.type = Frequency
  override def apply[A: Numeric](value: A): Frequency[A] = Frequency(value, this)
}

case object RevolutionsPerMinute extends FrequencyUnit("rpm", 0.016666666666666666)
case object Hertz extends FrequencyUnit("Hz", 1) with PrimaryUnit with SiUnit
case object Kilohertz extends FrequencyUnit("kHz", MetricSystem.Kilo) with SiUnit
case object Megahertz extends FrequencyUnit("MHz", MetricSystem.Mega) with SiUnit
case object Gigahertz extends FrequencyUnit("GHz", MetricSystem.Giga) with SiUnit
case object Terahertz extends FrequencyUnit("THz", MetricSystem.Tera) with SiUnit
