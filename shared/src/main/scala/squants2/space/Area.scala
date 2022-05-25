/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.space

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Area[A: Numeric] private [squants2]  (value: A, unit: AreaUnit)
  extends Quantity[A, Area.type] {
  override type Q[B] = Area[B]

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): Volume[A] = ???
  //  def *[B](that: AreaDensity[B])(implicit f: B => A): Mass[A] = ???
  //  def *[B](that: Pressure[B])(implicit f: B => A): Force[A] = ???
  //  def *[B](that: Illuminance[B])(implicit f: B => A): LuminousFlux[A] = ???
  //  def *[B](that: Luminance[B])(implicit f: B => A): LuminousIntensity[A] = ???
  //  def *[B](that: MagneticFluxDensity[B])(implicit f: B => A): MagneticFlux[A] = ???
  //  def *[B](that: Irradiance[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: Radiance[B])(implicit f: B => A): RadiantIntensity[A] = ???
  //  def *[B](that: Time[B])(implicit f: B => A): AreaTime[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): Length[A] = ???
  //  def squareRoot[B]()(implicit f: B => A): Length[A] = ???
  // END CUSTOM OPS

  def toBarnes[B: Numeric](implicit f: A => B): B = toNum[B](Barnes)
  def toSquareCentimeters[B: Numeric](implicit f: A => B): B = toNum[B](SquareCentimeters)
  def toSquareInches[B: Numeric](implicit f: A => B): B = toNum[B](SquareInches)
  def toSquareFeet[B: Numeric](implicit f: A => B): B = toNum[B](SquareFeet)
  def toSquareYards[B: Numeric](implicit f: A => B): B = toNum[B](SquareYards)
  def toSquareMeters[B: Numeric](implicit f: A => B): B = toNum[B](SquareMeters)
  def toAcres[B: Numeric](implicit f: A => B): B = toNum[B](Acres)
  def toHectares[B: Numeric](implicit f: A => B): B = toNum[B](Hectares)
  def toSquareKilometers[B: Numeric](implicit f: A => B): B = toNum[B](SquareKilometers)
  def toSquareUsMiles[B: Numeric](implicit f: A => B): B = toNum[B](SquareUsMiles)
}

object Area extends Dimension("Area") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = SquareMeters
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = SquareMeters
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Barnes, SquareCentimeters, SquareInches, SquareFeet, SquareYards, SquareMeters, Acres, Hectares, SquareKilometers, SquareUsMiles)

  implicit class AreaCons[A](a: A)(implicit num: Numeric[A]) {
    def barnes: Area[A] = Barnes(a)
    def squareCentimeters: Area[A] = SquareCentimeters(a)
    def squareInches: Area[A] = SquareInches(a)
    def squareFeet: Area[A] = SquareFeet(a)
    def squareYards: Area[A] = SquareYards(a)
    def squareMeters: Area[A] = SquareMeters(a)
    def acres: Area[A] = Acres(a)
    def hectares: Area[A] = Hectares(a)
    def squareKilometers: Area[A] = SquareKilometers(a)
    def squareUsMiles: Area[A] = SquareUsMiles(a)
  }

  lazy val barnes: Area[Int] = Barnes(1)
  lazy val squareCentimeters: Area[Int] = SquareCentimeters(1)
  lazy val squareInches: Area[Int] = SquareInches(1)
  lazy val squareFeet: Area[Int] = SquareFeet(1)
  lazy val squareYards: Area[Int] = SquareYards(1)
  lazy val squareMeters: Area[Int] = SquareMeters(1)
  lazy val acres: Area[Int] = Acres(1)
  lazy val hectares: Area[Int] = Hectares(1)
  lazy val squareKilometers: Area[Int] = SquareKilometers(1)
  lazy val squareUsMiles: Area[Int] = SquareUsMiles(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = AreaNumeric[A]()
  private case class AreaNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Area.type], y: Quantity[A, Area.type]): Quantity[A, Area.this.type] =
      SquareMeters(x.to(SquareMeters) * y.to(SquareMeters))
  }
}

abstract class AreaUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Area.type] {
  override def dimension: Area.type = Area
  override def apply[A: Numeric](value: A): Area[A] = Area(value, this)
}

case object Barnes extends AreaUnit("b", 1.0E-28)
case object SquareCentimeters extends AreaUnit("cm²", 1.0E-4) with SiUnit
case object SquareInches extends AreaUnit("in²", 6.4516E-4)
case object SquareFeet extends AreaUnit("ft²", 0.09290304)
case object SquareYards extends AreaUnit("yd²", 0.83612736)
case object SquareMeters extends AreaUnit("m²", 1) with PrimaryUnit with SiUnit
case object Acres extends AreaUnit("acre", 4046.8564224)
case object Hectares extends AreaUnit("ha", 10000)
case object SquareKilometers extends AreaUnit("km²", MetricSystem.Mega) with SiUnit
case object SquareUsMiles extends AreaUnit("mi²", 2589988.1103359996)
