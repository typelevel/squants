/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._

final case class MomentOfInertia[A: Numeric] private[squants2] (value: A, unit: MomentOfInertiaUnit)
  extends Quantity[A, MomentOfInertia] {

  // BEGIN CUSTOM OPS

  //  def *[B](angularAcceleration: AngularAcceleration[B])(implicit f: B => A): Torque[A] = ???
  //  def atCenter[B](radius: Length[B])(implicit f: B => A): Mass[A] = ???
  // END CUSTOM OPS

  def toKilogramsMetersSquared[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsMetersSquared)
  def toPoundsSquareFeet[B: Numeric](implicit f: A => B): B = toNum[B](PoundsSquareFeet)
}

object MomentOfInertia extends Dimension[MomentOfInertia]("Moment Of Inertia") {

  override def primaryUnit: UnitOfMeasure[MomentOfInertia] with PrimaryUnit[MomentOfInertia] = KilogramsMetersSquared
  override def siUnit: UnitOfMeasure[MomentOfInertia] with SiUnit[MomentOfInertia] = KilogramsMetersSquared
  override lazy val units: Set[UnitOfMeasure[MomentOfInertia]] = 
    Set(KilogramsMetersSquared, PoundsSquareFeet)

  implicit class MomentOfInertiaCons[A](a: A)(implicit num: Numeric[A]) {
    def kilogramsMetersSquared: MomentOfInertia[A] = KilogramsMetersSquared(a)
    def poundsSquareFeet: MomentOfInertia[A] = PoundsSquareFeet(a)
  }

  lazy val kilogramsMeterSquared: MomentOfInertia[Int] = KilogramsMetersSquared(1)
  lazy val poundsSquareFeet: MomentOfInertia[Int] = PoundsSquareFeet(1)

}

abstract class MomentOfInertiaUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[MomentOfInertia] {
  override def dimension: Dimension[MomentOfInertia] = MomentOfInertia
  override def apply[A: Numeric](value: A): MomentOfInertia[A] = MomentOfInertia(value, this)
}

case object KilogramsMetersSquared extends MomentOfInertiaUnit("kg‧m²", 1) with PrimaryUnit[MomentOfInertia] with SiBaseUnit[MomentOfInertia]
case object PoundsSquareFeet extends MomentOfInertiaUnit("lb‧ft²", 42.14027865441374)
