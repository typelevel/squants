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

final case class Volume[A: Numeric] private[squants2] (value: A, unit: VolumeUnit)
  extends Quantity[A, Volume] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Density[B])(implicit f: B => A): Mass[A] = ???
  //  def *[B](that: EnergyDensity[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: Area[B])(implicit f: B => A): Length[A] = ???
  //  def /[B](that: Length[B])(implicit f: B => A): Area[A] = ???
  //  def /[B](that: Mass[B])(implicit f: B => A): Nothing$ = ???
  //  def /[B](that: ChemicalAmount[B])(implicit f: B => A): Nothing$ = ???
  //  def cubeRoot[B]()(implicit f: B => A): Length[A] = ???
  // END CUSTOM OPS

  def toNanolitres[B: Numeric](implicit f: A => B): B = toNum[B](Nanolitres)
  def toMicrolitres[B: Numeric](implicit f: A => B): B = toNum[B](Microlitres)
  def toMillilitres[B: Numeric](implicit f: A => B): B = toNum[B](Millilitres)
  def toTeaspoons[B: Numeric](implicit f: A => B): B = toNum[B](Teaspoons)
  def toCentilitres[B: Numeric](implicit f: A => B): B = toNum[B](Centilitres)
  def toTablespoons[B: Numeric](implicit f: A => B): B = toNum[B](Tablespoons)
  def toCubicInches[B: Numeric](implicit f: A => B): B = toNum[B](CubicInches)
  def toFluidOunces[B: Numeric](implicit f: A => B): B = toNum[B](FluidOunces)
  def toDecilitres[B: Numeric](implicit f: A => B): B = toNum[B](Decilitres)
  def toUsCups[B: Numeric](implicit f: A => B): B = toNum[B](UsCups)
  def toUsPints[B: Numeric](implicit f: A => B): B = toNum[B](UsPints)
  def toUsQuarts[B: Numeric](implicit f: A => B): B = toNum[B](UsQuarts)
  def toLitres[B: Numeric](implicit f: A => B): B = toNum[B](Litres)
  def toUsGallons[B: Numeric](implicit f: A => B): B = toNum[B](UsGallons)
  def toCubicFeet[B: Numeric](implicit f: A => B): B = toNum[B](CubicFeet)
  def toHectolitres[B: Numeric](implicit f: A => B): B = toNum[B](Hectolitres)
  def toCubicYards[B: Numeric](implicit f: A => B): B = toNum[B](CubicYards)
  def toCubicMeters[B: Numeric](implicit f: A => B): B = toNum[B](CubicMeters)
  def toAcreFeet[B: Numeric](implicit f: A => B): B = toNum[B](AcreFeet)
  def toCubicUsMiles[B: Numeric](implicit f: A => B): B = toNum[B](CubicUsMiles)
}

object Volume extends Dimension[Volume]("Volume") {

  override def primaryUnit: UnitOfMeasure[Volume] with PrimaryUnit[Volume] = CubicMeters
  override def siUnit: UnitOfMeasure[Volume] with SiUnit[Volume] = CubicMeters
  override lazy val units: Set[UnitOfMeasure[Volume]] = 
    Set(Nanolitres, Microlitres, Millilitres, Teaspoons, Centilitres, Tablespoons, CubicInches, FluidOunces, Decilitres, UsCups, UsPints, UsQuarts, Litres, UsGallons, CubicFeet, Hectolitres, CubicYards, CubicMeters, AcreFeet, CubicUsMiles)

  implicit class VolumeCons[A](a: A)(implicit num: Numeric[A]) {
    def nanolitres: Volume[A] = Nanolitres(a)
    def microlitres: Volume[A] = Microlitres(a)
    def millilitres: Volume[A] = Millilitres(a)
    def teaspoons: Volume[A] = Teaspoons(a)
    def centilitres: Volume[A] = Centilitres(a)
    def tablespoons: Volume[A] = Tablespoons(a)
    def cubicInches: Volume[A] = CubicInches(a)
    def fluidOunces: Volume[A] = FluidOunces(a)
    def decilitres: Volume[A] = Decilitres(a)
    def usCups: Volume[A] = UsCups(a)
    def usPints: Volume[A] = UsPints(a)
    def usQuarts: Volume[A] = UsQuarts(a)
    def litres: Volume[A] = Litres(a)
    def usGallons: Volume[A] = UsGallons(a)
    def cubicFeet: Volume[A] = CubicFeet(a)
    def hectolitres: Volume[A] = Hectolitres(a)
    def cubicYards: Volume[A] = CubicYards(a)
    def cubicMeters: Volume[A] = CubicMeters(a)
    def acreFeet: Volume[A] = AcreFeet(a)
    def cubicUsMiles: Volume[A] = CubicUsMiles(a)
  }

  lazy val nanolitres: Volume[Int] = Nanolitres(1)
  lazy val microlitres: Volume[Int] = Microlitres(1)
  lazy val millilitres: Volume[Int] = Millilitres(1)
  lazy val teaspoons: Volume[Int] = Teaspoons(1)
  lazy val centilitres: Volume[Int] = Centilitres(1)
  lazy val tablespoons: Volume[Int] = Tablespoons(1)
  lazy val cubicInches: Volume[Int] = CubicInches(1)
  lazy val fluidOunces: Volume[Int] = FluidOunces(1)
  lazy val decilitres: Volume[Int] = Decilitres(1)
  lazy val usCups: Volume[Int] = UsCups(1)
  lazy val usPints: Volume[Int] = UsPints(1)
  lazy val usQuarts: Volume[Int] = UsQuarts(1)
  lazy val litres: Volume[Int] = Litres(1)
  lazy val usGallons: Volume[Int] = UsGallons(1)
  lazy val cubicFeet: Volume[Int] = CubicFeet(1)
  lazy val hectolitres: Volume[Int] = Hectolitres(1)
  lazy val cubicYards: Volume[Int] = CubicYards(1)
  lazy val cubicMeters: Volume[Int] = CubicMeters(1)
  lazy val acreFeet: Volume[Int] = AcreFeet(1)
  lazy val cubicUsMiles: Volume[Int] = CubicUsMiles(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Volume] = VolumeNumeric[A]()
  private case class VolumeNumeric[A: Numeric]() extends QuantityNumeric[A, Volume](this) {
    override def times(x: Quantity[A, Volume], y: Quantity[A, Volume]): Quantity[A, Volume] =
      CubicMeters(x.to(CubicMeters) * y.to(CubicMeters))
  }
}

abstract class VolumeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Volume] {
  override def dimension: Dimension[Volume] = Volume
  override def apply[A: Numeric](value: A): Volume[A] = Volume(value, this)
}

case object Nanolitres extends VolumeUnit("nl", 1.0000000000000002E-12)
case object Microlitres extends VolumeUnit("µl", MetricSystem.Nano)
case object Millilitres extends VolumeUnit("ml", MetricSystem.Micro)
case object Teaspoons extends VolumeUnit("tsp", 4.92892159375E-6)
case object Centilitres extends VolumeUnit("cl", 1.0E-5)
case object Tablespoons extends VolumeUnit("tbsp", 1.4786764781249999E-5)
case object CubicInches extends VolumeUnit("in³", 1.6387162322580647E-5)
case object FluidOunces extends VolumeUnit("oz", 2.9573529562499998E-5)
case object Decilitres extends VolumeUnit("dl", 1.0E-4)
case object UsCups extends VolumeUnit("c", 2.3658823649999998E-4)
case object UsPints extends VolumeUnit("pt", 4.7317647299999996E-4)
case object UsQuarts extends VolumeUnit("qt", 9.463529459999999E-4)
case object Litres extends VolumeUnit("L", MetricSystem.Milli)
case object UsGallons extends VolumeUnit("gal", 0.0037854117839999997)
case object CubicFeet extends VolumeUnit("ft³", 0.028317016493419354)
case object Hectolitres extends VolumeUnit("hl", MetricSystem.Deci)
case object CubicYards extends VolumeUnit("yd³", 0.7645594453223226)
case object CubicMeters extends VolumeUnit("m³", 1) with PrimaryUnit[Volume] with SiUnit[Volume]
case object AcreFeet extends VolumeUnit("acft", 1233.489238453347)
case object CubicUsMiles extends VolumeUnit("mi³", 4.1682068345815496E9)
