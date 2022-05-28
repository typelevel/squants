/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.space

import squants2._
import squants2.motion._
import squants2.time._

final case class Length[A: Numeric] private[squants2] (value: A, unit: LengthUnit)
  extends Quantity[A, Length] with TimeIntegral[A, Velocity] {

  override protected[squants2] def timeDerived: Velocity[A] = MetersPerSecond(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def per[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): Area[A] = ???
  //  def *[B](that: Area[B])(implicit f: B => A): Volume[A] = ???
  //  def *[B](that: Force[B])(implicit f: B => A): Energy[A] = ???
  //  def *[B](that: SpectralIntensity[B])(implicit f: B => A): RadiantIntensity[A] = ???
  //  def *[B](that: SpectralPower[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: Conductivity[B])(implicit f: B => A): ElectricalConductance[A] = ???
  //  def *[B](that: ElectricalResistance[B])(implicit f: B => A): Resistivity[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): Acceleration[A] = ???
  //  def squared[B]()(implicit f: B => A): Area[A] = ???
  //  def cubed[B]()(implicit f: B => A): Volume[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  // END CUSTOM OPS

  def toAngstroms[B: Numeric](implicit f: A => B): B = toNum[B](Angstroms)
  def toMilliElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](MilliElectronVoltLength)
  def toNanometers[B: Numeric](implicit f: A => B): B = toNum[B](Nanometers)
  def toElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](ElectronVoltLength)
  def toMicrons[B: Numeric](implicit f: A => B): B = toNum[B](Microns)
  def toKiloElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](KiloElectronVoltLength)
  def toMillimeters[B: Numeric](implicit f: A => B): B = toNum[B](Millimeters)
  def toCentimeters[B: Numeric](implicit f: A => B): B = toNum[B](Centimeters)
  def toInches[B: Numeric](implicit f: A => B): B = toNum[B](Inches)
  def toDecimeters[B: Numeric](implicit f: A => B): B = toNum[B](Decimeters)
  def toMegaElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](MegaElectronVoltLength)
  def toFeet[B: Numeric](implicit f: A => B): B = toNum[B](Feet)
  def toYards[B: Numeric](implicit f: A => B): B = toNum[B](Yards)
  def toMeters[B: Numeric](implicit f: A => B): B = toNum[B](Meters)
  def toDecameters[B: Numeric](implicit f: A => B): B = toNum[B](Decameters)
  def toHectometers[B: Numeric](implicit f: A => B): B = toNum[B](Hectometers)
  def toGigaElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](GigaElectronVoltLength)
  def toKilometers[B: Numeric](implicit f: A => B): B = toNum[B](Kilometers)
  def toInternationalMiles[B: Numeric](implicit f: A => B): B = toNum[B](InternationalMiles)
  def toUsMiles[B: Numeric](implicit f: A => B): B = toNum[B](UsMiles)
  def toNauticalMiles[B: Numeric](implicit f: A => B): B = toNum[B](NauticalMiles)
  def toTeraElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](TeraElectronVoltLength)
  def toPetaElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](PetaElectronVoltLength)
  def toNominalSolarRadii[B: Numeric](implicit f: A => B): B = toNum[B](NominalSolarRadii)
  def toSolarRadii[B: Numeric](implicit f: A => B): B = toNum[B](SolarRadii)
  def toAstronomicalUnits[B: Numeric](implicit f: A => B): B = toNum[B](AstronomicalUnits)
  def toExaElectronVoltLength[B: Numeric](implicit f: A => B): B = toNum[B](ExaElectronVoltLength)
  def toLightYears[B: Numeric](implicit f: A => B): B = toNum[B](LightYears)
  def toParsecs[B: Numeric](implicit f: A => B): B = toNum[B](Parsecs)
  def toKiloParsecs[B: Numeric](implicit f: A => B): B = toNum[B](KiloParsecs)
  def toMegaParsecs[B: Numeric](implicit f: A => B): B = toNum[B](MegaParsecs)
  def toGigaParsecs[B: Numeric](implicit f: A => B): B = toNum[B](GigaParsecs)
}

object Length extends BaseDimension[Length]("Length", "L") {

  override def primaryUnit: UnitOfMeasure[Length] with PrimaryUnit[Length] = Meters
  override def siUnit: UnitOfMeasure[Length] with SiBaseUnit[Length] = Meters
  override lazy val units: Set[UnitOfMeasure[Length]] = 
    Set(Angstroms, MilliElectronVoltLength, Nanometers, ElectronVoltLength, Microns, KiloElectronVoltLength, Millimeters, Centimeters, Inches, Decimeters, MegaElectronVoltLength, Feet, Yards, Meters, Decameters, Hectometers, GigaElectronVoltLength, Kilometers, InternationalMiles, UsMiles, NauticalMiles, TeraElectronVoltLength, PetaElectronVoltLength, NominalSolarRadii, SolarRadii, AstronomicalUnits, ExaElectronVoltLength, LightYears, Parsecs, KiloParsecs, MegaParsecs, GigaParsecs)

  implicit class LengthCons[A](a: A)(implicit num: Numeric[A]) {
    def angstroms: Length[A] = Angstroms(a)
    def milliElectronVoltLength: Length[A] = MilliElectronVoltLength(a)
    def nanometers: Length[A] = Nanometers(a)
    def electronVoltLength: Length[A] = ElectronVoltLength(a)
    def microns: Length[A] = Microns(a)
    def kiloElectronVoltLength: Length[A] = KiloElectronVoltLength(a)
    def millimeters: Length[A] = Millimeters(a)
    def centimeters: Length[A] = Centimeters(a)
    def inches: Length[A] = Inches(a)
    def decimeters: Length[A] = Decimeters(a)
    def megaElectronVoltLength: Length[A] = MegaElectronVoltLength(a)
    def feet: Length[A] = Feet(a)
    def yards: Length[A] = Yards(a)
    def meters: Length[A] = Meters(a)
    def decameters: Length[A] = Decameters(a)
    def hectometers: Length[A] = Hectometers(a)
    def gigaElectronVoltLength: Length[A] = GigaElectronVoltLength(a)
    def kilometers: Length[A] = Kilometers(a)
    def internationalMiles: Length[A] = InternationalMiles(a)
    def usMiles: Length[A] = UsMiles(a)
    def nauticalMiles: Length[A] = NauticalMiles(a)
    def teraElectronVoltLength: Length[A] = TeraElectronVoltLength(a)
    def petaElectronVoltLength: Length[A] = PetaElectronVoltLength(a)
    def nominalSolarRadii: Length[A] = NominalSolarRadii(a)
    def solarRadii: Length[A] = SolarRadii(a)
    def astronomicalUnits: Length[A] = AstronomicalUnits(a)
    def exaElectronVoltLength: Length[A] = ExaElectronVoltLength(a)
    def lightYears: Length[A] = LightYears(a)
    def parsecs: Length[A] = Parsecs(a)
    def kiloParsecs: Length[A] = KiloParsecs(a)
    def megaParsecs: Length[A] = MegaParsecs(a)
    def gigaParsecs: Length[A] = GigaParsecs(a)
  }

  lazy val angstrom: Length[Int] = Angstroms(1)
  lazy val milliElectronVoltLength: Length[Int] = MilliElectronVoltLength(1)
  lazy val nanometer: Length[Int] = Nanometers(1)
  lazy val electronVoltLength: Length[Int] = ElectronVoltLength(1)
  lazy val micron: Length[Int] = Microns(1)
  lazy val kiloElectronVoltLength: Length[Int] = KiloElectronVoltLength(1)
  lazy val millimeter: Length[Int] = Millimeters(1)
  lazy val centimeter: Length[Int] = Centimeters(1)
  lazy val inche: Length[Int] = Inches(1)
  lazy val decimeter: Length[Int] = Decimeters(1)
  lazy val megaElectronVoltLength: Length[Int] = MegaElectronVoltLength(1)
  lazy val feet: Length[Int] = Feet(1)
  lazy val yard: Length[Int] = Yards(1)
  lazy val meter: Length[Int] = Meters(1)
  lazy val decameter: Length[Int] = Decameters(1)
  lazy val hectometer: Length[Int] = Hectometers(1)
  lazy val gigaElectronVoltLength: Length[Int] = GigaElectronVoltLength(1)
  lazy val kilometer: Length[Int] = Kilometers(1)
  lazy val internationalMiles: Length[Int] = InternationalMiles(1)
  lazy val uMiles: Length[Int] = UsMiles(1)
  lazy val nauticalMiles: Length[Int] = NauticalMiles(1)
  lazy val teraElectronVoltLength: Length[Int] = TeraElectronVoltLength(1)
  lazy val petaElectronVoltLength: Length[Int] = PetaElectronVoltLength(1)
  lazy val nominalSolarRadii: Length[Int] = NominalSolarRadii(1)
  lazy val solarRadii: Length[Int] = SolarRadii(1)
  lazy val astronomicalUnits: Length[Int] = AstronomicalUnits(1)
  lazy val exaElectronVoltLength: Length[Int] = ExaElectronVoltLength(1)
  lazy val lightYears: Length[Int] = LightYears(1)
  lazy val parsec: Length[Int] = Parsecs(1)
  lazy val kiloParsecs: Length[Int] = KiloParsecs(1)
  lazy val megaParsecs: Length[Int] = MegaParsecs(1)
  lazy val gigaParsecs: Length[Int] = GigaParsecs(1)

}

abstract class LengthUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Length] {
  override def dimension: Dimension[Length] = Length
  override def apply[A: Numeric](value: A): Length[A] = Length(value, this)
}

case object Angstroms extends LengthUnit("Å", 1.0E-10)
case object MilliElectronVoltLength extends LengthUnit("mħc/eV", 1.97327E-10)
case object Nanometers extends LengthUnit("nm", MetricSystem.Nano) with SiUnit[Length]
case object ElectronVoltLength extends LengthUnit("ħc/eV", 1.97327E-7)
case object Microns extends LengthUnit("µm", MetricSystem.Micro) with SiUnit[Length]
case object KiloElectronVoltLength extends LengthUnit("kħc/eV", 1.9732700000000002E-4)
case object Millimeters extends LengthUnit("mm", MetricSystem.Milli) with SiUnit[Length]
case object Centimeters extends LengthUnit("cm", MetricSystem.Centi) with SiUnit[Length]
case object Inches extends LengthUnit("in", 0.0254000508)
case object Decimeters extends LengthUnit("dm", MetricSystem.Deci) with SiUnit[Length]
case object MegaElectronVoltLength extends LengthUnit("Mħc/eV", 0.197327)
case object Feet extends LengthUnit("ft", 0.3048006096)
case object Yards extends LengthUnit("yd", 0.9144018288)
case object Meters extends LengthUnit("m", 1) with PrimaryUnit[Length] with SiBaseUnit[Length]
case object Decameters extends LengthUnit("dam", MetricSystem.Deca) with SiUnit[Length]
case object Hectometers extends LengthUnit("hm", MetricSystem.Hecto) with SiUnit[Length]
case object GigaElectronVoltLength extends LengthUnit("Għc/eV", 197.327)
case object Kilometers extends LengthUnit("km", MetricSystem.Kilo) with SiUnit[Length]
case object InternationalMiles extends LengthUnit("mile", 1609.344)
case object UsMiles extends LengthUnit("mi", 1609.3472186879999)
case object NauticalMiles extends LengthUnit("nmi", 1852)
case object TeraElectronVoltLength extends LengthUnit("Tħc/eV", 197327)
case object PetaElectronVoltLength extends LengthUnit("Pħc/eV", 197327000)
case object NominalSolarRadii extends LengthUnit("RN☉", 695700000)
case object SolarRadii extends LengthUnit("R☉", 695700000)
case object AstronomicalUnits extends LengthUnit("au", 1.495978707E11)
case object ExaElectronVoltLength extends LengthUnit("Eħc/eV", 1.97327E11)
case object LightYears extends LengthUnit("ly", 9.4607304725808E15)
case object Parsecs extends LengthUnit("pc", 3.08567758149137E16)
case object KiloParsecs extends LengthUnit("kpc", 3.08567758149137E19)
case object MegaParsecs extends LengthUnit("Mpc", 3.08567758149137E22)
case object GigaParsecs extends LengthUnit("Gpc", 3.08567758149137E25)
