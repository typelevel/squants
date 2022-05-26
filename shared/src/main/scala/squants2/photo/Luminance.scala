/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.photo

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Luminance[A: Numeric] private[squants2] (value: A, unit: LuminanceUnit)
  extends Quantity[A, Luminance] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): LuminousIntensity[A] = ???
  // END CUSTOM OPS

  def toCandelasPerSquareMeter[B: Numeric](implicit f: A => B): B = toNum[B](CandelasPerSquareMeter)
}

object Luminance extends Dimension[Luminance]("Luminance") {

  override def primaryUnit: UnitOfMeasure[Luminance] with PrimaryUnit[Luminance] = CandelasPerSquareMeter
  override def siUnit: UnitOfMeasure[Luminance] with SiUnit[Luminance] = CandelasPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[Luminance]] = 
    Set(CandelasPerSquareMeter)

  implicit class LuminanceCons[A](a: A)(implicit num: Numeric[A]) {
    def candelasPerSquareMeter: Luminance[A] = CandelasPerSquareMeter(a)
  }

  lazy val candelasPerSquareMeter: Luminance[Int] = CandelasPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Luminance] = LuminanceNumeric[A]()
  private case class LuminanceNumeric[A: Numeric]() extends QuantityNumeric[A, Luminance](this) {
    override def times(x: Quantity[A, Luminance], y: Quantity[A, Luminance]): Quantity[A, Luminance] =
      CandelasPerSquareMeter(x.to(CandelasPerSquareMeter) * y.to(CandelasPerSquareMeter))
  }
}

abstract class LuminanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Luminance] {
  override def dimension: Dimension[Luminance] = Luminance
  override def apply[A: Numeric](value: A): Luminance[A] = Luminance(value, this)
}

case object CandelasPerSquareMeter extends LuminanceUnit("cd/m²", 1) with PrimaryUnit[Luminance] with SiUnit[Luminance]
