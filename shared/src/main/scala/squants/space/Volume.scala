/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.energy.{ EnergyDensity, Joules }
import squants.mass.{ ChemicalAmount, Kilograms }
import squants.motion.{ CubicMetersPerSecond, VolumeFlow }
import squants.time.TimeIntegral

/**
 * Represents a quantity of Volume (three-dimensional space)
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.CubicMeters]]
 */
final class Volume private (val value: Double, val unit: VolumeUnit)
    extends Quantity[Volume]
    with TimeIntegral[VolumeFlow] {

  def dimension = Volume

  protected def timeDerived = CubicMetersPerSecond(toCubicMeters)
  protected[squants] def time = Seconds(1)

  def *(that: Density): Mass = Kilograms(this.toCubicMeters * that.toKilogramsPerCubicMeter)
  def *(that: EnergyDensity): Energy = Joules(this.toCubicMeters * that.toJoulesPerCubicMeter)

  def /(that: Area): Length = unit match {
    case CubicUsMiles => UsMiles(this.value / that.toSquareUsMiles)
    case CubicYards   => Yards(this.value / that.toSquareYards)
    case CubicFeet    => Feet(this.value / that.toSquareFeet)
    case CubicInches  => Inches(this.value / that.toSquareInches)
    case _            => Meters(this.toCubicMeters / that.toSquareMeters)
  }

  def /(that: Length): Area = unit match {
    case CubicUsMiles => SquareUsMiles(this.value / that.toUsMiles)
    case CubicYards   => SquareYards(this.value / that.toYards)
    case CubicFeet    => SquareFeet(this.value / that.toFeet)
    case CubicInches  => SquareInches(this.value / that.toInches)
    case _            => SquareMeters(this.toCubicMeters / that.toMeters)
  }

  def /(that: Mass) = ??? // returns SpecificVolume (inverse of Density)
  def /(that: ChemicalAmount) = ??? // return MolarVolume

  def cubeRoot = Meters(math.cbrt(toCubicMeters))

  def toCubicMeters = to(CubicMeters)
  def toLitres = to(Litres)
  def toNanolitres = to(Nanolitres)
  def toMicrolitres = to(Microlitres)
  def toMillilitres = to(Millilitres)
  def toCentilitres = to(Centilitres)
  def toDecilitres = to(Decilitres)
  def toHectolitres = to(Hectolitres)

  def toCubicMiles = to(CubicUsMiles)
  def toCubicYards = to(CubicYards)
  def toCubicFeet = to(CubicFeet)
  def toCubicInches = to(CubicInches)

  def toUsGallons = to(UsGallons)
  def toUsQuarts = to(UsQuarts)
  def toUsPints = to(UsPints)
  def toUsCups = to(UsCups)
  def toFluidOunces = to(FluidOunces)
  def toTablespoons = to(Tablespoons)
  def toTeaspoons = to(Teaspoons)

  def toUsDryGallons = to(UsDryGallons)
  def toUsDryQuarts = to(UsDryQuarts)
  def toUsDryPints = to(UsDryPints)
  def toUsDryCups = to(UsDryCups)

  def toImperialGallons = to(ImperialGallons)
  def toImperialQuarts = to(ImperialQuarts)
  def toImperialPints = to(ImperialPints)
  def toImperialCups = to(ImperialCups)

  def toAcreFeet = to(AcreFeet)
}

object Volume extends Dimension[Volume] {
  private[space] def apply[A](n: A, unit: VolumeUnit)(implicit num: Numeric[A]) = new Volume(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Volume"
  def primaryUnit = CubicMeters
  def siUnit = CubicMeters
  def units = Set(CubicMeters, Litres, Nanolitres, Microlitres, Millilitres, Centilitres,
    Decilitres, Hectolitres,
    CubicUsMiles, CubicYards, CubicFeet, CubicInches,
    UsGallons, UsQuarts, UsPints, UsCups, FluidOunces, Tablespoons, Teaspoons,
    AcreFeet)
}

trait VolumeUnit extends UnitOfMeasure[Volume] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Volume(n, this)
}

object CubicMeters extends VolumeUnit with PrimaryUnit with SiUnit {
  val symbol = "m³"
}

object Litres extends VolumeUnit {
  val symbol = "L"
  val conversionFactor = .001
}

object Nanolitres extends VolumeUnit {
  val symbol = "nl"
  val conversionFactor = Litres.conversionFactor * MetricSystem.Nano
}

object Microlitres extends VolumeUnit {
  val symbol = "µl"
  val conversionFactor = Litres.conversionFactor * MetricSystem.Micro
}

object Millilitres extends VolumeUnit {
  val symbol = "ml"
  val conversionFactor = Litres.conversionFactor * MetricSystem.Milli
}

object Centilitres extends VolumeUnit {
  val symbol = "cl"
  val conversionFactor = Litres.conversionFactor * MetricSystem.Centi
}

object Decilitres extends VolumeUnit {
  val symbol = "dl"
  val conversionFactor = Litres.conversionFactor * MetricSystem.Deci
}

object Hectolitres extends VolumeUnit {
  val symbol = "hl"
  val conversionFactor = Litres.conversionFactor * MetricSystem.Hecto
}

object CubicUsMiles extends VolumeUnit {
  val symbol = "mi³"
  val conversionFactor = math.pow(UsMiles.conversionFactor, 3)
}

object CubicYards extends VolumeUnit {
  val symbol = "yd³"
  val conversionFactor = BigDecimal(Yards.conversionFactor).pow(3).toDouble
}

object CubicFeet extends VolumeUnit {
  val symbol = "ft³"
  val conversionFactor = BigDecimal(Feet.conversionFactor).pow(3).toDouble
}

object CubicInches extends VolumeUnit {
  val symbol = "in³"
  val conversionFactor = math.pow(Inches.conversionFactor, 3)
}

object UsGallons extends VolumeUnit {
  val symbol = "gal"
  val conversionFactor = Millilitres.conversionFactor * 3.785411784e3
}

object UsQuarts extends VolumeUnit {
  val symbol = "qt"
  val conversionFactor = UsGallons.conversionFactor / 4d
}

object UsPints extends VolumeUnit {
  val symbol = "pt"
  val conversionFactor = UsGallons.conversionFactor / 8d
}

object UsCups extends VolumeUnit {
  val symbol = "c"
  val conversionFactor = UsGallons.conversionFactor / 16d
}

object FluidOunces extends VolumeUnit {
  val symbol = "oz"
  val conversionFactor = UsGallons.conversionFactor / 128d
}

object Tablespoons extends VolumeUnit {
  val symbol = "tbsp"
  val conversionFactor = FluidOunces.conversionFactor / 2d
}

object Teaspoons extends VolumeUnit {
  val symbol = "tsp"
  val conversionFactor = FluidOunces.conversionFactor / 6d
}

object UsDryGallons extends VolumeUnit {
  val symbol = "gal"
  val conversionFactor = Millilitres.conversionFactor * 4.4048837e3
}

object UsDryQuarts extends VolumeUnit {
  val symbol = "qt"
  val conversionFactor = UsDryGallons.conversionFactor / 4d
}

object UsDryPints extends VolumeUnit {
  val symbol = "pt"
  val conversionFactor = UsDryGallons.conversionFactor / 8d
}

object UsDryCups extends VolumeUnit {
  val symbol = "c"
  val conversionFactor = UsDryGallons.conversionFactor / 16d
}

object ImperialGallons extends VolumeUnit {
  val symbol = "gal"
  val conversionFactor = Millilitres.conversionFactor * 4.54609e3
}

object ImperialQuarts extends VolumeUnit {
  val symbol = "qt"
  val conversionFactor = ImperialGallons.conversionFactor / 4d
}

object ImperialPints extends VolumeUnit {
  val symbol = "pt"
  val conversionFactor = ImperialGallons.conversionFactor / 8d
}

object ImperialCups extends VolumeUnit {
  val symbol = "c"
  val conversionFactor = ImperialGallons.conversionFactor / 16d
}

object AcreFeet extends VolumeUnit {
  val symbol = "acft"
  val conversionFactor = CubicFeet.conversionFactor * 43560d
}

object VolumeConversions {
  lazy val cubicMeter = CubicMeters(1)
  lazy val litre = Litres(1)
  lazy val liter = Litres(1)
  lazy val nanolitre = Nanolitres(1)
  lazy val nanoliter = Nanolitres(1)
  lazy val microlitre = Microlitres(1)
  lazy val microliter = Microlitres(1)
  lazy val millilitre = Millilitres(1)
  lazy val milliliter = Millilitres(1)
  lazy val centilitre = Centilitres(1)
  lazy val centiliter = Centilitres(1)
  lazy val decilitre = Decilitres(1)
  lazy val deciliter = Decilitres(1)
  lazy val hectolitre = Hectolitres(1)
  lazy val hectoliter = Hectolitres(1)

  lazy val cubicMile = CubicUsMiles(1)
  lazy val cubicYard = CubicYards(1)
  lazy val cubicFoot = CubicFeet(1)
  lazy val cubicInch = CubicInches(1)

  lazy val gallon = UsGallons(1)
  lazy val quart = UsQuarts(1)
  lazy val pint = UsPints(1)
  lazy val cup = UsCups(1)

  lazy val fluidOunce = FluidOunces(1)
  lazy val tablespoon = Tablespoons(1)
  lazy val teaspoon = Teaspoons(1)

  lazy val acreFoot = AcreFeet(1)

  implicit class VolumeConversions[A](n: A)(implicit num: Numeric[A]) {
    def cubicMeters = CubicMeters(n)
    def cubicMetres = CubicMeters(n)
    def litres = Litres(n)
    def liters = Litres(n)
    def nanolitres = Nanolitres(n)
    def nanoliters = Nanolitres(n)
    def microlitres = Microlitres(n)
    def microliters = Microlitres(n)
    def millilitres = Millilitres(n)
    def milliliters = Millilitres(n)
    def centilitres = Centilitres(n)
    def centiliters = Centilitres(n)
    def decilitres = Decilitres(n)
    def deciliters = Decilitres(n)
    def hectolitres = Hectolitres(n)
    def hectoliters = Hectolitres(n)

    def cubicMiles = CubicUsMiles(n)
    def cubicYards = CubicYards(n)
    def cubicFeet = CubicFeet(n)
    def cubicInches = CubicInches(n)

    def gallons = UsGallons(n)
    def quarts = UsQuarts(n)
    def pints = UsPints(n)
    def cups = UsCups(n)
    def fluidOunces = FluidOunces(n)
    def tablespoons = Tablespoons(n)
    def teaspoons = Teaspoons(n)

    def acreFeet = AcreFeet(n)
  }

  implicit object VolumeNumeric extends AbstractQuantityNumeric[Volume](Volume.primaryUnit)
}
