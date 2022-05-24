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
  override lazy val dimension: Frequency.type = Frequency
  override def apply[A: Numeric](value: A): Frequency[A] = Frequency(value, this)
}

case object RevolutionsPerMinute extends FrequencyUnit("rpm", 0.016666666666666666)
case object Hertz extends FrequencyUnit("Hz", 1.0) with PrimaryUnit with SiUnit
case object Kilohertz extends FrequencyUnit("kHz", 1000.0) with SiUnit
case object Megahertz extends FrequencyUnit("MHz", 1000000.0) with SiUnit
case object Gigahertz extends FrequencyUnit("GHz", 1.0E9) with SiUnit
case object Terahertz extends FrequencyUnit("THz", 1.0E12) with SiUnit
