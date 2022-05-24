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

final case class Force[A: Numeric] private [squants2]  (value: A, unit: ForceUnit)
  extends Quantity[A, Force.type] {
  override type Q[B] = Force[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toMegaElectronVoltsPerCentimeter: A = to(MegaElectronVoltsPerCentimeter)
  def toKiloElectronVoltsPerMicrometer: A = to(KiloElectronVoltsPerMicrometer)
  def toNewtons: A = to(Newtons)
  def toPoundForce: A = to(PoundForce)
  def toKilogramForce: A = to(KilogramForce)
}

object Force extends Dimension("Force") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Newtons
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Newtons
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ForceNumeric[A]()
  private case class ForceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Force.type], y: Quantity[A, Force.type]): Quantity[A, Force.this.type] =
      Newtons(x.to(Newtons) * y.to(Newtons))
  }
}

abstract class ForceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Force.type] {
  override lazy val dimension: Force.type = Force
  override def apply[A: Numeric](value: A): Force[A] = Force(value, this)
}

case object MegaElectronVoltsPerCentimeter extends ForceUnit("MeV/cm", 1.602176565E-11)
case object KiloElectronVoltsPerMicrometer extends ForceUnit("keV/μm", 1.602176565E-10)
case object Newtons extends ForceUnit("N", 1.0) with PrimaryUnit with SiUnit
case object PoundForce extends ForceUnit("lbf", 4.4482216152605)
case object KilogramForce extends ForceUnit("kgf", 9.80665)
