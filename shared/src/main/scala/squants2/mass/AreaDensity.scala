/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._

final case class AreaDensity[A: Numeric] private[squants2] (value: A, unit: AreaDensityUnit)
  extends Quantity[A, AreaDensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): Mass[A] = ???
  // END CUSTOM OPS

  def toKilogramsPerHectare[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerHectare)
  def toPoundsPerAcre[B: Numeric](implicit f: A => B): B = toNum[B](PoundsPerAcre)
  def toKilogramsPerSquareMeter[B: Numeric](implicit f: A => B): B = toNum[B](KilogramsPerSquareMeter)
  def toGramsPerSquareCentimeter[B: Numeric](implicit f: A => B): B = toNum[B](GramsPerSquareCentimeter)
}

object AreaDensity extends Dimension[AreaDensity]("Area Density") {

  override def primaryUnit: UnitOfMeasure[AreaDensity] with PrimaryUnit[AreaDensity] = KilogramsPerSquareMeter
  override def siUnit: UnitOfMeasure[AreaDensity] with SiUnit[AreaDensity] = KilogramsPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[AreaDensity]] = 
    Set(KilogramsPerHectare, PoundsPerAcre, KilogramsPerSquareMeter, GramsPerSquareCentimeter)

  implicit class AreaDensityCons[A](a: A)(implicit num: Numeric[A]) {
    def kilogramsPerHectare: AreaDensity[A] = KilogramsPerHectare(a)
    def poundsPerAcre: AreaDensity[A] = PoundsPerAcre(a)
    def kilogramsPerSquareMeter: AreaDensity[A] = KilogramsPerSquareMeter(a)
    def gramsPerSquareCentimeter: AreaDensity[A] = GramsPerSquareCentimeter(a)
  }

  lazy val kilogramPerHectare: AreaDensity[Int] = KilogramsPerHectare(1)
  lazy val poundPerAcre: AreaDensity[Int] = PoundsPerAcre(1)
  lazy val kilogramPerSquareMeter: AreaDensity[Int] = KilogramsPerSquareMeter(1)
  lazy val gramPerSquareCentimeter: AreaDensity[Int] = GramsPerSquareCentimeter(1)

}

abstract class AreaDensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[AreaDensity] {
  override def dimension: Dimension[AreaDensity] = AreaDensity
  override def apply[A: Numeric](value: A): AreaDensity[A] = AreaDensity(value, this)
}

case object KilogramsPerHectare extends AreaDensityUnit("kg/hectare", 1.0E-4)
case object PoundsPerAcre extends AreaDensityUnit("lb/acre", 1.1208511561944561E-4)
case object KilogramsPerSquareMeter extends AreaDensityUnit("kg/m²", 1) with PrimaryUnit[AreaDensity] with SiUnit[AreaDensity]
case object GramsPerSquareCentimeter extends AreaDensityUnit("g/cm²", MetricSystem.Deca) with SiUnit[AreaDensity]
