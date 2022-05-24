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

final case class LuminousIntensity[A: Numeric] private [squants2]  (value: A, unit: LuminousIntensityUnit)
  extends Quantity[A, LuminousIntensity.type] {
  override type Q[B] = LuminousIntensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toCandelas: A = to(Candelas)
}

object LuminousIntensity extends BaseDimension("Luminous Intensity", "J") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Candelas
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Candelas
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Candelas)

  implicit class LuminousIntensityCons[A](a: A)(implicit num: Numeric[A]) {
    def candelas: LuminousIntensity[A] = Candelas(a)
  }

  lazy val candelas: LuminousIntensity[Int] = Candelas(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LuminousIntensityNumeric[A]()
  private case class LuminousIntensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, LuminousIntensity.type], y: Quantity[A, LuminousIntensity.type]): Quantity[A, LuminousIntensity.this.type] =
      Candelas(x.to(Candelas) * y.to(Candelas))
  }
}

abstract class LuminousIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousIntensity.type] {
  override def dimension: LuminousIntensity.type = LuminousIntensity
  override def apply[A: Numeric](value: A): LuminousIntensity[A] = LuminousIntensity(value, this)
}

case object Candelas extends LuminousIntensityUnit("cd", 1) with PrimaryUnit with SiBaseUnit
