/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants.TypeLevelInt._2
import squants._
import squants.electro.{ MagneticFlux, MagneticFluxDensity, Webers }
import squants.energy.Watts
import squants.mass.AreaDensity
import squants.motion.{ Newtons, Pressure }
import squants.photo.{ Candelas, _ }
import squants.radio._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.SquareMeters]]
 */
final class Area private (val value: Double, val unit: AreaUnit)
    extends Quantity[Area] with DimensionType[Length, _2]{

  def dimension = Area

  def *(that: Length): Volume = unit match {
    case SquareUsMiles ⇒ CubicUsMiles(value * that.toUsMiles)
    case SquareYards   ⇒ CubicYards(value * that.toYards)
    case SquareFeet    ⇒ CubicFeet(value * that.toFeet)
    case SquareInches  ⇒ CubicInches(value * that.toInches)
    case _             ⇒ CubicMeters(toSquareMeters * that.toMeters)
  }

  def *(that: AreaDensity): Mass = Kilograms(toSquareMeters * that.toKilogramsPerSquareMeter)
  def *(that: Pressure): Force = Newtons(toSquareMeters * that.toPascals)
  def *(that: Illuminance): LuminousFlux = Lumens(toSquareMeters * that.toLux)
  def *(that: Luminance): LuminousIntensity = Candelas(toSquareMeters * that.toCandelasPerSquareMeters)
  def *(that: MagneticFluxDensity): MagneticFlux = Webers(toSquareMeters * that.toTeslas)
  def *(that: Irradiance): Power = Watts(toSquareMeters * that.toWattsPerSquareMeter)
  def *(that: Radiance): RadiantIntensity = WattsPerSteradian(toSquareMeters * that.toWattsPerSteradianPerSquareMeter)

  def /(that: Length): Length = unit match {
    case SquareUsMiles ⇒ UsMiles(value / that.toUsMiles)
    case SquareYards   ⇒ Yards(value / that.toYards)
    case SquareFeet    ⇒ Feet(value / that.toFeet)
    case SquareInches  ⇒ Inches(value / that.toInches)
    case _             ⇒ Meters(toSquareMeters / that.toMeters)
  }

  def squareRoot = Meters(math.sqrt(toSquareMeters))

  def toSquareMeters = to(SquareMeters)
  def toSquareCentimeters = to(SquareCentimeters)
  def toSquareKilometers = to(SquareKilometers)
  def toSquareUsMiles = to(SquareUsMiles)
  def toSquareYards = to(SquareYards)
  def toSquareFeet = to(SquareFeet)
  def toSquareInches = to(SquareInches)
  def toHectares = to(Hectares)
  def toAcres = to(Acres)
  def toBarnes = to(Barnes)
}

object Area extends Dimension[Area] {
  private[space] def apply[A](n: A, unit: AreaUnit)(implicit num: Numeric[A]) = new Area(num.toDouble(n), unit)
  def apply = parse _
  def name = "Area"
  def primaryUnit = SquareMeters
  def siUnit = SquareMeters
  def units = Set(SquareMeters, SquareCentimeters, SquareKilometers,
    SquareUsMiles, SquareYards, SquareFeet, SquareInches,
    Hectares, Acres, Barnes)
}

trait AreaUnit extends UnitOfMeasure[Area] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Area(n, this)
}

object SquareMeters extends AreaUnit with PrimaryUnit with SiUnit {
  val symbol = "m²"
}

object SquareCentimeters extends AreaUnit {
  val symbol = "cm²"
  val conversionFactor = MetricSystem.Centi * MetricSystem.Centi
}

object SquareKilometers extends AreaUnit {
  val symbol = "km²"
  val conversionFactor = MetricSystem.Kilo * MetricSystem.Kilo
}

object SquareUsMiles extends AreaUnit {
  val symbol = "mi²"
  val conversionFactor = 2.589988110336 * SquareKilometers.conversionFactor
}

object SquareYards extends AreaUnit {
  val symbol = "yd²"
  val conversionFactor = 8.3612736e-1
}

object SquareFeet extends AreaUnit {
  val symbol = "ft²"
  val conversionFactor = 9.290304e-2
}

object SquareInches extends AreaUnit {
  val symbol = "in²"
  val conversionFactor = 6.4516 * SquareCentimeters.conversionFactor
}

object Hectares extends AreaUnit {
  val symbol = "ha"
  val conversionFactor = 10000d
}

object Acres extends AreaUnit {
  val symbol = "acre"
  val conversionFactor = 43560d * SquareFeet.conversionFactor
}

object Barnes extends AreaUnit {
  val symbol = "b"
  val conversionFactor = scala.math.pow(10, -28)
}

object AreaConversions {
  lazy val squareMeter = SquareMeters(1)
  lazy val squareCentimeter = SquareCentimeters(1)
  lazy val squareKilometer = SquareKilometers(1)
  lazy val squareMile = SquareUsMiles(1)
  lazy val squareYard = SquareYards(1)
  lazy val squareFoot = SquareFeet(1)
  lazy val squareInch = SquareInches(1)
  lazy val hectare = Hectares(1)
  lazy val acre = Acres(1)
  lazy val barne = Barnes(1)

  implicit class AreaConversions[A](n: A)(implicit num: Numeric[A]) {
    def squareMeters = SquareMeters(n)
    def squareCentimeters = SquareCentimeters(n)
    def squareKilometers = SquareKilometers(n)
    def squareMiles = SquareUsMiles(n)
    def squareYards = SquareYards(n)
    def squareFeet = SquareFeet(n)
    def squareInches = SquareInches(n)
    def hectares = Hectares(n)
    def acres = Acres(n)
    def barnes = Barnes(n)
  }

  implicit object AreaNumeric extends AbstractQuantityNumeric[Area](Area.primaryUnit)
}
