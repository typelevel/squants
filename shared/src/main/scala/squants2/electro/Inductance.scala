/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Inductance[A: Numeric] private [squants2]  (value: A, unit: InductanceUnit)
  extends Quantity[A, Inductance.type] {
  override type Q[B] = Inductance[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toPicohenry: A = to(Picohenry)
  def toNanohenry: A = to(Nanohenry)
  def toMicrohenry: A = to(Microhenry)
  def toMillihenry: A = to(Millihenry)
  def toHenry: A = to(Henry)
}

object Inductance extends Dimension("Inductance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Henry
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Henry
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Picohenry, Nanohenry, Microhenry, Millihenry, Henry)

  implicit class InductanceCons[A](a: A)(implicit num: Numeric[A]) {
    def picohenry: Inductance[A] = Picohenry(a)
    def nanohenry: Inductance[A] = Nanohenry(a)
    def microhenry: Inductance[A] = Microhenry(a)
    def millihenry: Inductance[A] = Millihenry(a)
    def henry: Inductance[A] = Henry(a)
  }

  lazy val picohenry: Inductance[Int] = Picohenry(1)
  lazy val nanohenry: Inductance[Int] = Nanohenry(1)
  lazy val microhenry: Inductance[Int] = Microhenry(1)
  lazy val millihenry: Inductance[Int] = Millihenry(1)
  lazy val henry: Inductance[Int] = Henry(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = InductanceNumeric[A]()
  private case class InductanceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Inductance.type], y: Quantity[A, Inductance.type]): Quantity[A, Inductance.this.type] =
      Henry(x.to(Henry) * y.to(Henry))
  }
}

abstract class InductanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Inductance.type] {
  override lazy val dimension: Inductance.type = Inductance
  override def apply[A: Numeric](value: A): Inductance[A] = Inductance(value, this)
}

case object Picohenry extends InductanceUnit("pH", 1.0E-12) with SiUnit
case object Nanohenry extends InductanceUnit("nH", 1.0E-9) with SiUnit
case object Microhenry extends InductanceUnit("μH", 1.0E-6) with SiUnit
case object Millihenry extends InductanceUnit("mH", 0.001) with SiUnit
case object Henry extends InductanceUnit("H", 1) with PrimaryUnit with SiUnit
