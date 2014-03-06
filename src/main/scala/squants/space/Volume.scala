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
  def toMillilitres = to(Milliliters)

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

object Milliliters extends VolumeUnit {
  val symbol = "ml"
  val multiplier = Litres.multiplier * MetricSystem.Milli
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
  val multiplier = Milliliters.multiplier * 3785.411784
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
  val multiplier = Milliliters.multiplier * 4404.8837
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
  val multiplier = Milliliters.multiplier * 4546.09
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
  lazy val millilitre = Milliliters(1)

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

  implicit class VolumeConversions(val d: Double) {
    def cubicMeters = CubicMeters(d)
    def cubicMetres = CubicMeters(d)
    def litres = Litres(d)
    def millilitres = Milliliters(d)

    def cubicMiles = CubicMiles(d)
    def cubicYards = CubicYards(d)
    def cubicFeet = CubicFeet(d)
    def cubicInches = CubicInches(d)

    def gallons = UsGallons(d)
    def quarts = UsQuarts(d)
    def pints = UsPints(d)
    def cups = UsCups(d)
    def fluidOunces = FluidOunces(d)
    def tablespoons = Tablespoons(d)
    def teaspoons = Teaspoons(d)
  }
}
