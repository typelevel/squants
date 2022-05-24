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

final case class Volume[A: Numeric] private [squants2]  (value: A, unit: VolumeUnit)
  extends Quantity[A, Volume.type] {
  override type Q[B] = Volume[B]

  // BEGIN CUSTOM OPS
  def /[B](that: Length[B])(implicit f: B => A): Area[A] = SquareMeters(to(CubicMeters) / that.asNum[A].to(Meters))
  def /[B](that: Area[B])(implicit f: B => A): Length[A] = Meters(to(CubicMeters) / that.asNum[A].to(SquareMeters))
  // END CUSTOM OPS

  def toNanolitres: A = to(Nanolitres)
  def toMicrolitres: A = to(Microlitres)
  def toMillilitres: A = to(Millilitres)
  def toTeaspoons: A = to(Teaspoons)
  def toCentilitres: A = to(Centilitres)
  def toTablespoons: A = to(Tablespoons)
  def toCubicInches: A = to(CubicInches)
  def toFluidOunces: A = to(FluidOunces)
  def toDecilitres: A = to(Decilitres)
  def toUsCups: A = to(UsCups)
  def toUsPints: A = to(UsPints)
  def toUsQuarts: A = to(UsQuarts)
  def toLitres: A = to(Litres)
  def toUsGallons: A = to(UsGallons)
  def toCubicFeet: A = to(CubicFeet)
  def toHectolitres: A = to(Hectolitres)
  def toCubicYards: A = to(CubicYards)
  def toCubicMeters: A = to(CubicMeters)
  def toAcreFeet: A = to(AcreFeet)
  def toCubicUsMiles: A = to(CubicUsMiles)
}

object Volume extends Dimension("Volume") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CubicMeters
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CubicMeters
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = VolumeNumeric[A]()
  private case class VolumeNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Volume.type], y: Quantity[A, Volume.type]): Quantity[A, Volume.this.type] =
      CubicMeters(x.to(CubicMeters) * y.to(CubicMeters))
  }
}

abstract class VolumeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Volume.type] {
  override def dimension: Volume.type = Volume
  override def apply[A: Numeric](value: A): Volume[A] = Volume(value, this)
}

case object Nanolitres extends VolumeUnit("nl", 1.0000000000000002E-12)
case object Microlitres extends VolumeUnit("µl", 1.0E-9)
case object Millilitres extends VolumeUnit("ml", 1.0E-6)
case object Teaspoons extends VolumeUnit("tsp", 4.92892159375E-6)
case object Centilitres extends VolumeUnit("cl", 1.0E-5)
case object Tablespoons extends VolumeUnit("tbsp", 1.4786764781249999E-5)
case object CubicInches extends VolumeUnit("in³", 1.6387162322580647E-5)
case object FluidOunces extends VolumeUnit("oz", 2.9573529562499998E-5)
case object Decilitres extends VolumeUnit("dl", 1.0E-4)
case object UsCups extends VolumeUnit("c", 2.3658823649999998E-4)
case object UsPints extends VolumeUnit("pt", 4.7317647299999996E-4)
case object UsQuarts extends VolumeUnit("qt", 9.463529459999999E-4)
case object Litres extends VolumeUnit("L", 0.001)
case object UsGallons extends VolumeUnit("gal", 0.0037854117839999997)
case object CubicFeet extends VolumeUnit("ft³", 0.028317016493419354)
case object Hectolitres extends VolumeUnit("hl", 0.1)
case object CubicYards extends VolumeUnit("yd³", 0.7645594453223226)
case object CubicMeters extends VolumeUnit("m³", 1) with PrimaryUnit with SiUnit
case object AcreFeet extends VolumeUnit("acft", 1233.489238453347)
case object CubicUsMiles extends VolumeUnit("mi³", 4.1682068345815496E9)
