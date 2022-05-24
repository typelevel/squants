/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class MomentOfInertia[A: Numeric] private [squants2]  (value: A, unit: MomentOfInertiaUnit)
  extends Quantity[A, MomentOfInertia.type] {
  override type Q[B] = MomentOfInertia[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toKilogramsMetersSquared: A = to(KilogramsMetersSquared)
  def toPoundsSquareFeet: A = to(PoundsSquareFeet)
}

object MomentOfInertia extends Dimension("MomentOfInertia") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = KilogramsMetersSquared
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = KilogramsMetersSquared
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(KilogramsMetersSquared, PoundsSquareFeet)

  implicit class MomentOfInertiaCons[A](a: A)(implicit num: Numeric[A]) {
    def kilogramsMetersSquared: MomentOfInertia[A] = KilogramsMetersSquared(a)
    def poundsSquareFeet: MomentOfInertia[A] = PoundsSquareFeet(a)
  }

  lazy val kilogramsMetersSquared: MomentOfInertia[Int] = KilogramsMetersSquared(1)
  lazy val poundsSquareFeet: MomentOfInertia[Int] = PoundsSquareFeet(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = MomentOfInertiaNumeric[A]()
  private case class MomentOfInertiaNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, MomentOfInertia.type], y: Quantity[A, MomentOfInertia.type]): Quantity[A, MomentOfInertia.this.type] =
      KilogramsMetersSquared(x.to(KilogramsMetersSquared) * y.to(KilogramsMetersSquared))
  }
}

abstract class MomentOfInertiaUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MomentOfInertia.type] {
  override lazy val dimension: MomentOfInertia.type = MomentOfInertia
  override def apply[A: Numeric](value: A): MomentOfInertia[A] = MomentOfInertia(value, this)
}

case object KilogramsMetersSquared extends MomentOfInertiaUnit("kg‧m²", 1.0) with PrimaryUnit with SiUnit
case object PoundsSquareFeet extends MomentOfInertiaUnit("lb‧ft²", 42.14027865441374)
