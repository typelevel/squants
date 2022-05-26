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

final case class Force[A: Numeric] private[squants2] (value: A, unit: ForceUnit)
  extends Quantity[A, Force] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): Nothing$ = ???
  //  def /[B](that: Mass[B])(implicit f: B => A): Acceleration[A] = ???
  //  def /[B](that: Acceleration[B])(implicit f: B => A): Mass[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Pressure[A] = ???
  //  def /[B](that: Pressure[B])(implicit f: B => A): Area[A] = ???
  // END CUSTOM OPS

  def toMegaElectronVoltsPerCentimeter[B: Numeric](implicit f: A => B): B = toNum[B](MegaElectronVoltsPerCentimeter)
  def toKiloElectronVoltsPerMicrometer[B: Numeric](implicit f: A => B): B = toNum[B](KiloElectronVoltsPerMicrometer)
  def toNewtons[B: Numeric](implicit f: A => B): B = toNum[B](Newtons)
  def toPoundForce[B: Numeric](implicit f: A => B): B = toNum[B](PoundForce)
  def toKilogramForce[B: Numeric](implicit f: A => B): B = toNum[B](KilogramForce)
}

object Force extends Dimension[Force]("Force") {

  override def primaryUnit: UnitOfMeasure[Force] with PrimaryUnit[Force] = Newtons
  override def siUnit: UnitOfMeasure[Force] with SiUnit[Force] = Newtons
  override lazy val units: Set[UnitOfMeasure[Force]] = 
    Set(MegaElectronVoltsPerCentimeter, KiloElectronVoltsPerMicrometer, Newtons, PoundForce, KilogramForce)

  implicit class ForceCons[A](a: A)(implicit num: Numeric[A]) {
    def megaElectronVoltsPerCentimeter: Force[A] = MegaElectronVoltsPerCentimeter(a)
    def kiloElectronVoltsPerMicrometer: Force[A] = KiloElectronVoltsPerMicrometer(a)
    def newtons: Force[A] = Newtons(a)
    def poundForce: Force[A] = PoundForce(a)
    def kilogramForce: Force[A] = KilogramForce(a)
  }

  lazy val megaElectronVoltsPerCentimeter: Force[Int] = MegaElectronVoltsPerCentimeter(1)
  lazy val kiloElectronVoltsPerMicrometer: Force[Int] = KiloElectronVoltsPerMicrometer(1)
  lazy val newtons: Force[Int] = Newtons(1)
  lazy val poundForce: Force[Int] = PoundForce(1)
  lazy val kilogramForce: Force[Int] = KilogramForce(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Force] = ForceNumeric[A]()
  private case class ForceNumeric[A: Numeric]() extends QuantityNumeric[A, Force](this) {
    override def times(x: Quantity[A, Force], y: Quantity[A, Force]): Quantity[A, Force] =
      Newtons(x.to(Newtons) * y.to(Newtons))
  }
}

abstract class ForceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Force] {
  override def dimension: Dimension[Force] = Force
  override def apply[A: Numeric](value: A): Force[A] = Force(value, this)
}

case object MegaElectronVoltsPerCentimeter extends ForceUnit("MeV/cm", 1.602176565E-11)
case object KiloElectronVoltsPerMicrometer extends ForceUnit("keV/μm", 1.602176565E-10)
case object Newtons extends ForceUnit("N", 1) with PrimaryUnit[Force] with SiUnit[Force]
case object PoundForce extends ForceUnit("lbf", 4.4482216152605)
case object KilogramForce extends ForceUnit("kgf", 9.80665)
