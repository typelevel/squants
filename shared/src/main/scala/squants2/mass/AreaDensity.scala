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

final case class AreaDensity[A: Numeric] private [squants2]  (value: A, unit: AreaDensityUnit)
  extends Quantity[A, AreaDensity.type] {
  override type Q[B] = AreaDensity[B]

  def toKilogramsPerHectare: A = to(KilogramsPerHectare)
  def toPoundsPerAcre: A = to(PoundsPerAcre)
  def toKilogramsPerSquareMeter: A = to(KilogramsPerSquareMeter)
  def toGramsPerSquareCentimeter: A = to(GramsPerSquareCentimeter)
}

object AreaDensity extends Dimension("AreaDensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = KilogramsPerSquareMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = KilogramsPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(KilogramsPerHectare, PoundsPerAcre, KilogramsPerSquareMeter, GramsPerSquareCentimeter)

  implicit class AreaDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def kilogramsPerHectare: AreaDensity[A] = KilogramsPerHectare(a)
    def poundsPerAcre: AreaDensity[A] = PoundsPerAcre(a)
    def kilogramsPerSquareMeter: AreaDensity[A] = KilogramsPerSquareMeter(a)
    def gramsPerSquareCentimeter: AreaDensity[A] = GramsPerSquareCentimeter(a)
  }

  lazy val kilogramsPerHectare: AreaDensity[Int] = KilogramsPerHectare(1)
  lazy val poundsPerAcre: AreaDensity[Int] = PoundsPerAcre(1)
  lazy val kilogramsPerSquareMeter: AreaDensity[Int] = KilogramsPerSquareMeter(1)
  lazy val gramsPerSquareCentimeter: AreaDensity[Int] = GramsPerSquareCentimeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AreaDensityNumeric[A]()
  private case class AreaDensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, AreaDensity.type], y: Quantity[A, AreaDensity.type]): Quantity[A, AreaDensity.this.type] =
      KilogramsPerSquareMeter(x.to(KilogramsPerSquareMeter) * y.to(KilogramsPerSquareMeter))
  }
}

abstract class AreaDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AreaDensity.type] {
  override lazy val dimension: AreaDensity.type = AreaDensity
  override def apply[A: Numeric](value: A): AreaDensity[A] = AreaDensity(value, this)
}

case object KilogramsPerHectare extends AreaDensityUnit("kg/hectare", 1.0E-4)
case object PoundsPerAcre extends AreaDensityUnit("lb/acre", 1.1208511561944561E-4)
case object KilogramsPerSquareMeter extends AreaDensityUnit("kg/m²", 1.0) with PrimaryUnit with SiUnit
case object GramsPerSquareCentimeter extends AreaDensityUnit("g/cm²", 10.0) with SiUnit
