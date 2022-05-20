package squants2.space

import squants2.QNumeric.QNumericOps
import squants2._

final case class Length[A: QNumeric] private [space]  (value: A, unit: LengthUnit) extends Quantity[A, Length.type] {
  override type Q[B] = Length[B]

  def *[B](that: Length[B])(implicit f: B => A): Area[A] = SquareMeters(to(Meters) * that.asNum[A].to(Meters))
  def *[B](that: Area[B])(implicit f: B => A): Volume[A] = CubicMeters(to(Meters) * that.asNum[A].to(SquareMeters))
}

object Length extends BaseDimension("Length", "L") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Meters
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Meters
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Angstroms, Nanometers, Microns,
    Millimeters, Centimeters, Decimeters, Meters, Decameters, Hectometers, Kilometers,
    Inches, Feet, Yards, UsMiles, InternationalMiles, NauticalMiles,
    AstronomicalUnits, LightYears, Parsecs, KiloParsecs, MegaParsecs, GigaParsecs, SolarRadii, NominalSolarRadii,
    ElectronVoltLength, MilliElectronVoltLength, KiloElectronVoltLength, MegaElectronVoltLength,
    GigaElectronVoltLength, TeraElectronVoltLength, PetaElectronVoltLength, ExaElectronVoltLength)

  // Constructors from QNumeric values
  implicit class LengthCons[A: QNumeric](a: A) {
    def meters: Length[A] = Meters(a)
    def feet: Length[A] = Feet(a)
  }

  // Constants
  lazy val meter: Length[Int] = Meters(1)

}

abstract class LengthUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[Length.type] {
  override def dimension: Length.type = Length
  override def apply[A: QNumeric](value: A): Length[A] = Length(value, this)
}

case object Meters extends LengthUnit("m", 1) with PrimaryUnit with SiBaseUnit
case object Kilometers extends LengthUnit("km", MetricSystem.Kilo)
case object Hectometers extends LengthUnit("hm", MetricSystem.Hecto)
case object Decameters extends LengthUnit("dam", MetricSystem.Deca)
case object Decimeters extends LengthUnit("dm", MetricSystem.Deci)
case object Centimeters extends LengthUnit("cm", MetricSystem.Centi)
case object Millimeters extends LengthUnit("mm", MetricSystem.Milli)
case object Microns extends LengthUnit("µm", MetricSystem.Micro)
case object Nanometers extends LengthUnit("nm", MetricSystem.Nano)
case object Angstroms extends LengthUnit("Å", 1.0e-10)

case object Inches extends LengthUnit("in", Feet.conversionFactor / 12d)
case object Feet extends LengthUnit("ft", .3048006096)
case object Yards extends LengthUnit("yd", 3d * Feet.conversionFactor)
case object UsMiles extends LengthUnit("mi", Feet.conversionFactor * 5280d)

case object InternationalMiles extends LengthUnit("mile", 1609.344)
case object NauticalMiles extends LengthUnit("nmi", 1852.0)

case object ElectronVoltLength extends LengthUnit("ħc/eV", 1.97327e-7)
case object MilliElectronVoltLength extends LengthUnit("mħc/eV", MetricSystem.Milli * ElectronVoltLength.conversionFactor)
case object KiloElectronVoltLength extends LengthUnit("kħc/eV", MetricSystem.Kilo * ElectronVoltLength.conversionFactor)
case object MegaElectronVoltLength extends LengthUnit("Mħc/eV", MetricSystem.Mega * ElectronVoltLength.conversionFactor)
case object GigaElectronVoltLength extends LengthUnit("Għc/eV", MetricSystem.Giga * ElectronVoltLength.conversionFactor)
case object TeraElectronVoltLength extends LengthUnit("Tħc/eV", MetricSystem.Tera * ElectronVoltLength.conversionFactor)
case object PetaElectronVoltLength extends LengthUnit("Pħc/eV", MetricSystem.Peta * ElectronVoltLength.conversionFactor)
case object ExaElectronVoltLength extends LengthUnit("Eħc/eV", MetricSystem.Exa * ElectronVoltLength.conversionFactor)

case object Parsecs extends LengthUnit("pc", 3.08567758149137E16)
case object KiloParsecs extends LengthUnit("kpc", MetricSystem.Kilo * Parsecs.conversionFactor)
case object MegaParsecs extends LengthUnit("Mpc", MetricSystem.Mega * Parsecs.conversionFactor)
case object GigaParsecs extends LengthUnit("Gpc", MetricSystem.Giga * Parsecs.conversionFactor)
case object NominalSolarRadii extends LengthUnit("RN☉", 6.957e8)
case object AstronomicalUnits extends LengthUnit("au", 1.495978707e11)
case object LightYears extends LengthUnit("ly", 9.4607304725808e15)
case object SolarRadii extends LengthUnit("R☉", 6.957e8)
