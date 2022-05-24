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

final case class Length[A: Numeric] private [squants2]  (value: A, unit: LengthUnit)
  extends Quantity[A, Length.type] {
  override type Q[B] = Length[B]

  // BEGIN CUSTOM OPS
  //  def /[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  //  def *[B](that: Frequency[B])(implicit f: B => A): Quantity[A] = ???
  //  def *[B](that: Length[B])(implicit f: B => A): Area[A] = ???
  //  def *[B](that: Area[B])(implicit f: B => A): Volume[A] = ???
  //  def *[B](that: Force[B])(implicit f: B => A): Energy[A] = ???
  //  def *[B](that: SpectralIntensity[B])(implicit f: B => A): RadiantIntensity[A] = ???
  //  def *[B](that: SpectralPower[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: Conductivity[B])(implicit f: B => A): ElectricalConductance[A] = ???
  //  def *[B](that: ElectricalResistance[B])(implicit f: B => A): Resistivity[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): Acceleration[A] = ???
  //  def /[B](that: SecondTimeDerivative[B])(implicit f: B => A): TimeSquared[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  // END CUSTOM OPS

  def toAngstroms: A = to(Angstroms)
  def toMilliElectronVoltLength: A = to(MilliElectronVoltLength)
  def toNanometers: A = to(Nanometers)
  def toElectronVoltLength: A = to(ElectronVoltLength)
  def toMicrons: A = to(Microns)
  def toKiloElectronVoltLength: A = to(KiloElectronVoltLength)
  def toMillimeters: A = to(Millimeters)
  def toCentimeters: A = to(Centimeters)
  def toInches: A = to(Inches)
  def toDecimeters: A = to(Decimeters)
  def toMegaElectronVoltLength: A = to(MegaElectronVoltLength)
  def toFeet: A = to(Feet)
  def toYards: A = to(Yards)
  def toMeters: A = to(Meters)
  def toDecameters: A = to(Decameters)
  def toHectometers: A = to(Hectometers)
  def toGigaElectronVoltLength: A = to(GigaElectronVoltLength)
  def toKilometers: A = to(Kilometers)
  def toInternationalMiles: A = to(InternationalMiles)
  def toUsMiles: A = to(UsMiles)
  def toNauticalMiles: A = to(NauticalMiles)
  def toTeraElectronVoltLength: A = to(TeraElectronVoltLength)
  def toPetaElectronVoltLength: A = to(PetaElectronVoltLength)
  def toNominalSolarRadii: A = to(NominalSolarRadii)
  def toSolarRadii: A = to(SolarRadii)
  def toAstronomicalUnits: A = to(AstronomicalUnits)
  def toExaElectronVoltLength: A = to(ExaElectronVoltLength)
  def toLightYears: A = to(LightYears)
  def toParsecs: A = to(Parsecs)
  def toKiloParsecs: A = to(KiloParsecs)
  def toMegaParsecs: A = to(MegaParsecs)
  def toGigaParsecs: A = to(GigaParsecs)
}

object Length extends BaseDimension("Length", "L") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Meters
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Meters
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  lazy val angstroms: Length[Int] = Angstroms(1)
  lazy val milliElectronVoltLength: Length[Int] = MilliElectronVoltLength(1)
  lazy val nanometers: Length[Int] = Nanometers(1)
  lazy val electronVoltLength: Length[Int] = ElectronVoltLength(1)
  lazy val microns: Length[Int] = Microns(1)
  lazy val kiloElectronVoltLength: Length[Int] = KiloElectronVoltLength(1)
  lazy val millimeters: Length[Int] = Millimeters(1)
  lazy val centimeters: Length[Int] = Centimeters(1)
  lazy val inches: Length[Int] = Inches(1)
  lazy val decimeters: Length[Int] = Decimeters(1)
  lazy val megaElectronVoltLength: Length[Int] = MegaElectronVoltLength(1)
  lazy val feet: Length[Int] = Feet(1)
  lazy val yards: Length[Int] = Yards(1)
  lazy val meters: Length[Int] = Meters(1)
  lazy val decameters: Length[Int] = Decameters(1)
  lazy val hectometers: Length[Int] = Hectometers(1)
  lazy val gigaElectronVoltLength: Length[Int] = GigaElectronVoltLength(1)
  lazy val kilometers: Length[Int] = Kilometers(1)
  lazy val internationalMiles: Length[Int] = InternationalMiles(1)
  lazy val usMiles: Length[Int] = UsMiles(1)
  lazy val nauticalMiles: Length[Int] = NauticalMiles(1)
  lazy val teraElectronVoltLength: Length[Int] = TeraElectronVoltLength(1)
  lazy val petaElectronVoltLength: Length[Int] = PetaElectronVoltLength(1)
  lazy val nominalSolarRadii: Length[Int] = NominalSolarRadii(1)
  lazy val solarRadii: Length[Int] = SolarRadii(1)
  lazy val astronomicalUnits: Length[Int] = AstronomicalUnits(1)
  lazy val exaElectronVoltLength: Length[Int] = ExaElectronVoltLength(1)
  lazy val lightYears: Length[Int] = LightYears(1)
  lazy val parsecs: Length[Int] = Parsecs(1)
  lazy val kiloParsecs: Length[Int] = KiloParsecs(1)
  lazy val megaParsecs: Length[Int] = MegaParsecs(1)
  lazy val gigaParsecs: Length[Int] = GigaParsecs(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = LengthNumeric[A]()
  private case class LengthNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Length.type], y: Quantity[A, Length.type]): Quantity[A, Length.this.type] =
      Meters(x.to(Meters) * y.to(Meters))
  }
}

abstract class LengthUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Length.type] {
  override def dimension: Length.type = Length
  override def apply[A: Numeric](value: A): Length[A] = Length(value, this)
}

case object Angstroms extends LengthUnit("Å", 1.0E-10)
case object MilliElectronVoltLength extends LengthUnit("mħc/eV", 1.97327E-10)
case object Nanometers extends LengthUnit("nm", MetricSystem.Nano) with SiUnit
case object ElectronVoltLength extends LengthUnit("ħc/eV", 1.97327E-7)
case object Microns extends LengthUnit("µm", MetricSystem.Micro) with SiUnit
case object KiloElectronVoltLength extends LengthUnit("kħc/eV", 1.9732700000000002E-4)
case object Millimeters extends LengthUnit("mm", MetricSystem.Milli) with SiUnit
case object Centimeters extends LengthUnit("cm", MetricSystem.Centi) with SiUnit
case object Inches extends LengthUnit("in", 0.0254000508)
case object Decimeters extends LengthUnit("dm", MetricSystem.Deci) with SiUnit
case object MegaElectronVoltLength extends LengthUnit("Mħc/eV", 0.197327)
case object Feet extends LengthUnit("ft", 0.3048006096)
case object Yards extends LengthUnit("yd", 0.9144018288)
case object Meters extends LengthUnit("m", 1) with PrimaryUnit with SiBaseUnit
case object Decameters extends LengthUnit("dam", MetricSystem.Deca) with SiUnit
case object Hectometers extends LengthUnit("hm", MetricSystem.Hecto) with SiUnit
case object GigaElectronVoltLength extends LengthUnit("Għc/eV", 197.327)
case object Kilometers extends LengthUnit("km", MetricSystem.Kilo) with SiUnit
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
