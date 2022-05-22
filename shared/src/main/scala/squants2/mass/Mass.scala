package squants2.mass

import squants2._

final case class Mass[A: Numeric : Converter] private [mass] (value: A, unit: MassUnit) extends Quantity[A, Mass.type] {
  override type Q[B] = Mass[B]
}

object Mass extends BaseDimension("Mass", "M") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Kilograms
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Kilograms
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Nanograms, Micrograms, Milligrams,
    Grams, Kilograms, Tonnes, Ounces, Pounds, Kilopounds, Megapounds, Stone,
    TroyGrains, Pennyweights, TroyOunces, TroyPounds, Tolas, Carats, SolarMasses, Dalton,
    ElectronVoltMass, MilliElectronVoltMass, KiloElectronVoltMass, MegaElectronVoltMass,
    GigaElectronVoltMass, TeraElectronVoltMass, PetaElectronVoltMass, ExaElectronVoltMass)

  // Constructors from Numeric values
  implicit class MassCons[A: Numeric : Converter](a: A) {
    def grams: Mass[A] = Grams(a)
    def kilograms: Mass[A] = Kilograms(a)
  }

  // Constants
  lazy val gram: Mass[Int] = Grams(1)
  lazy val kilogram: Mass[Int] = Kilograms(1)

}

abstract class MassUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Mass.type] {
  override def dimension: Mass.type = Mass
  override def apply[A: Numeric : Converter](value: A): Mass[A] = Mass(value, this)
}

case object Kilograms extends MassUnit("kg", MetricSystem.Kilo) with PrimaryUnit with SiBaseUnit
case object Grams extends MassUnit("g", 1)
case object Micrograms extends MassUnit("mcg", MetricSystem.Micro)
case object Milligrams extends MassUnit("mg", MetricSystem.Milli)
case object Nanograms extends MassUnit("ng", MetricSystem.Nano)
case object Tonnes extends MassUnit("t", MetricSystem.Mega)

case object Pounds extends MassUnit("lb", 453.59237)
case object Ounces extends MassUnit("oz", Pounds.conversionFactor / 16d)
case object Kilopounds extends MassUnit("klb", Pounds.conversionFactor * MetricSystem.Kilo)
case object Megapounds extends MassUnit("Mlb", Pounds.conversionFactor * MetricSystem.Mega)
case object Stone extends MassUnit("st", Pounds.conversionFactor * 14d)

case object TroyPounds extends MassUnit("lb t", 373.2417216)
case object TroyGrains extends MassUnit("gr", 64.79891 * Milligrams.conversionFactor)
case object TroyOunces extends MassUnit("oz t", 480d * TroyGrains.conversionFactor)
case object Pennyweights extends MassUnit("dwt", 24d * TroyGrains.conversionFactor)
case object Tolas extends MassUnit("tola", 180d * TroyGrains.conversionFactor)

case object ElectronVoltMass extends MassUnit("eV/c²", 1.782662E-36)
case object MilliElectronVoltMass extends MassUnit("meV/c²", MetricSystem.Milli * ElectronVoltMass.conversionFactor)
case object KiloElectronVoltMass extends MassUnit("keV/c²", MetricSystem.Kilo * ElectronVoltMass.conversionFactor)
case object MegaElectronVoltMass extends MassUnit("MeV/c²", MetricSystem.Mega * ElectronVoltMass.conversionFactor)
case object GigaElectronVoltMass extends MassUnit("GeV/c²", MetricSystem.Giga * ElectronVoltMass.conversionFactor)
case object TeraElectronVoltMass extends MassUnit("TeV/c²", MetricSystem.Tera * ElectronVoltMass.conversionFactor)
case object PetaElectronVoltMass extends MassUnit("PeV/c²", MetricSystem.Peta * ElectronVoltMass.conversionFactor)
case object ExaElectronVoltMass extends MassUnit("EeV/c²", MetricSystem.Exa * ElectronVoltMass.conversionFactor)

case object SolarMasses extends MassUnit("M☉", 1.98855e33)

case object Carats extends MassUnit("ct", .2)

case object Dalton extends MassUnit("Da", 1.6605390666E-24)
