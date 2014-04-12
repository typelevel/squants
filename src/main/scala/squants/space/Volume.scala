/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.time.TimeIntegral
import squants.motion.VolumeFlowRate
import squants.mass.{ ChemicalAmount, Kilograms }
import squants.energy.{ Joules, EnergyDensity }

/**
 * Represents a quantity of Volume (three-dimensional space)
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.CubicMeters]]
 */
final class Volume private (val value: Double)
    extends Quantity[Volume]
    with TimeIntegral[VolumeFlowRate] {

  def valueUnit = CubicMeters

  def *(that: Density): Mass = Kilograms(toCubicMeters * that.toKilogramsPerCubicMeter)
  def *(that: EnergyDensity): Energy = Joules(toCubicMeters * that.toJoulesPerCubicMeter)
  def /(that: Area): Length = Meters(toCubicMeters / that.toSquareMeters)
  def /(that: Length): Area = SquareMeters(toCubicMeters / that.toMeters)
  def /(that: Time): VolumeFlowRate = VolumeFlowRate(this, that)
  def /(that: VolumeFlowRate): Time = that.time * (this / that.change)
  def /(that: Mass) = ??? // returns SpecificVolume (inverse of Density)
  def /(that: ChemicalAmount) = ??? // return MolarVolume

  def toCubicMeters = to(CubicMeters)
  def toLitres = to(Litres)
  def toNanolitres = to(Nanolitres)
  def toMillilitres = to(Millilitres)
  def toMicrolitres = to(Microlitres)
  def toCentilitres = to(Centilitres)
  def toDecilitres = to(Decilitres)
  def toHectolitres = to(Hectolitres)

  def toCubicMiles = to(CubicMiles)
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
}

object Volume {
  private[space] def apply(cubicMeters: Double) = new Volume(cubicMeters)
  def apply(area: Area, length: Length): Volume = apply(area.toSquareMeters * length.toMeters)
}

trait VolumeUnit extends UnitOfMeasure[Volume] with UnitMultiplier {
  def apply(n: Double) = Volume(convertFrom(n))
  def unapply(v: Volume) = Some(convertTo(v.value))
}

object CubicMeters extends VolumeUnit with ValueUnit {
  val symbol = "m³"
}

object Litres extends VolumeUnit {
  val symbol = "L"
  val multiplier = .001
}

object Nanolitres extends VolumeUnit {
  val symbol = "nl"
  val multiplier = Litres.multiplier * MetricSystem.Nano
}

object Microlitres extends VolumeUnit {
  val symbol = "µl"
  val multiplier = Litres.multiplier * MetricSystem.Micro
}

object Millilitres extends VolumeUnit {
  val symbol = "ml"
  val multiplier = Litres.multiplier * MetricSystem.Milli
}

object Centilitres extends VolumeUnit {
  val symbol = "cl"
  val multiplier = Litres.multiplier * MetricSystem.Centi
}

object Decilitres extends VolumeUnit {
  val symbol = "dl"
  val multiplier = Litres.multiplier * MetricSystem.Deci
}

object Hectolitres extends VolumeUnit {
  val symbol = "hl"
  val multiplier = Litres.multiplier * MetricSystem.Hecto
}

object CubicMiles extends VolumeUnit {
  val symbol = "mi³"
  val multiplier = math.pow(UsMiles.multiplier, 3)
}

object CubicYards extends VolumeUnit {
  val symbol = "yd³"
  val multiplier = BigDecimal(Yards.multiplier).pow(3).toDouble
}

object CubicFeet extends VolumeUnit {
  val symbol = "ft³"
  val multiplier = BigDecimal(Feet.multiplier).pow(3).toDouble
}

object CubicInches extends VolumeUnit {
  val symbol = "in³"
  val multiplier = math.pow(Inches.multiplier, 3)
}

object UsGallons extends VolumeUnit {
  val symbol = "gal"
  val multiplier = Millilitres.multiplier * 3785.411784
}

object UsQuarts extends VolumeUnit {
  val symbol = "qt"
  val multiplier = UsGallons.multiplier / 4d
}

object UsPints extends VolumeUnit {
  val symbol = "pt"
  val multiplier = UsGallons.multiplier / 8d
}

object UsCups extends VolumeUnit {
  val symbol = "c"
  val multiplier = UsGallons.multiplier / 16d
}

object FluidOunces extends VolumeUnit {
  val symbol = "oz"
  val multiplier = UsGallons.multiplier / 128d
}

object Tablespoons extends VolumeUnit {
  val symbol = "tbsp"
  val multiplier = FluidOunces.multiplier / 2d
}

object Teaspoons extends VolumeUnit {
  val symbol = "tsp"
  val multiplier = FluidOunces.multiplier / 6d
}

object UsDryGallons extends VolumeUnit {
  val symbol = "gal"
  val multiplier = Millilitres.multiplier * 4404.8837
}

object UsDryQuarts extends VolumeUnit {
  val symbol = "qt"
  val multiplier = UsDryGallons.multiplier / 4d
}

object UsDryPints extends VolumeUnit {
  val symbol = "pt"
  val multiplier = UsDryGallons.multiplier / 8d
}

object UsDryCups extends VolumeUnit {
  val symbol = "c"
  val multiplier = UsDryGallons.multiplier / 16d
}

object ImperialGallons extends VolumeUnit {
  val symbol = "gal"
  val multiplier = Millilitres.multiplier * 4546.09
}

object ImperialQuarts extends VolumeUnit {
  val symbol = "qt"
  val multiplier = ImperialGallons.multiplier / 4d
}

object ImperialPints extends VolumeUnit {
  val symbol = "pt"
  val multiplier = ImperialGallons.multiplier / 8d
}

object ImperialCups extends VolumeUnit {
  val symbol = "c"
  val multiplier = ImperialGallons.multiplier / 16d
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

  lazy val cubicMile = CubicMiles(1)
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

  implicit class VolumeConversions[A](d: A)(implicit num: Numeric[A]) {
    def cubicMeters = CubicMeters(num.toDouble(d))
    def cubicMetres = CubicMeters(num.toDouble(d))
    def litres = Litres(num.toDouble(d))
    def liters = Litres(num.toDouble(d))
    def nanolitres = Nanolitres(num.toDouble(d))
    def nanoliters = Nanolitres(num.toDouble(d))
    def microlitres = Microlitres(num.toDouble(d))
    def microliters = Microlitres(num.toDouble(d))
    def millilitres = Millilitres(num.toDouble(d))
    def milliliters = Millilitres(num.toDouble(d))
    def centilitres = Centilitres(num.toDouble(d))
    def centiliters = Centilitres(num.toDouble(d))
    def decilitres = Decilitres(num.toDouble(d))
    def deciliters = Decilitres(num.toDouble(d))
    def hectolitres = Hectolitres(num.toDouble(d))
    def hectoliters = Hectolitres(num.toDouble(d))

    def cubicMiles = CubicMiles(num.toDouble(d))
    def cubicYards = CubicYards(num.toDouble(d))
    def cubicFeet = CubicFeet(num.toDouble(d))
    def cubicInches = CubicInches(num.toDouble(d))

    def gallons = UsGallons(num.toDouble(d))
    def quarts = UsQuarts(num.toDouble(d))
    def pints = UsPints(num.toDouble(d))
    def cups = UsCups(num.toDouble(d))
    def fluidOunces = FluidOunces(num.toDouble(d))
    def tablespoons = Tablespoons(num.toDouble(d))
    def teaspoons = Teaspoons(num.toDouble(d))
  }

  implicit object VolumeNumeric extends AbstractQuantityNumeric[Volume](CubicMeters)
}
