/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants.electro.{ OhmMeters, Ohms, Siemens, SiemensPerMeter }
import squants.energy.Joules
import squants.motion.{ MetersPerSecond, Newtons }
import squants.time.Seconds
import squants.{ MetricSystem, QuantityParseException }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LengthSpec extends AnyFlatSpec with Matchers {

  behavior of "Length and its Units of Measure"

  it should "create values using UOM factories" in {

    Meters(1).toMeters should be(1)
    Angstroms(1).toAngstroms should be (1)
    Nanometers(1).toNanometers should be(1)
    Microns(1).toMicrons should be(1)
    Millimeters(1).toMillimeters should be(1)
    Centimeters(1).toCentimeters should be(1)
    Decimeters(1).toDecimeters should be(1)
    Decameters(1).toDecameters should be(1)
    Hectometers(1).toHectometers should be(1)
    Kilometers(1).toKilometers should be(1)

    Inches(1).toInches should be(1)
    Feet(1).toFeet should be(1)
    Yards(1).toYards should be(1)
    UsMiles(1).toUsMiles should be(1)
    InternationalMiles(1).toInternationalMiles should be(1)
    NauticalMiles(1).toNauticalMiles should be(1)
    AstronomicalUnits(1).toAstronomicalUnits should be(1)
    LightYears(1).toLightYears should be(1)
    Parsecs(1).toParsecs should be(1)
    KiloParsecs(1).toKiloParsecs should be(1)
    MegaParsecs(1).toMegaParsecs should be(1)
    GigaParsecs(1).toGigaParsecs should be(1)
    SolarRadii(1).toSolarRadii should be(1)
    NominalSolarRadii(1).toNominalSolarRadii should be(1)

    ElectronVoltLength(1).toeV should be(1)
    MilliElectronVoltLength(1).tomeV should be(1)
    KiloElectronVoltLength(1).tokeV should be(1)
    MegaElectronVoltLength(1).toMeV should be(1)
    GigaElectronVoltLength(1).toGeV should be(1)
    TeraElectronVoltLength(1).toTeV should be(1)
    PetaElectronVoltLength(1).toPeV should be(1)
    ExaElectronVoltLength(1).toEeV should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Length("10.33 Å").get should be(Angstroms(10.33))
    Length("10.33 nm").get should be(Nanometers(10.33))
    Length("10.33 µm").get should be(Microns(10.33))
    Length("10.33 mm").get should be(Millimeters(10.33))
    Length("10.33 cm").get should be(Centimeters(10.33))
    Length("10.33 dm").get should be(Decimeters(10.33))
    Length("10.33 m").get should be(Meters(10.33))
    Length("10.33 dam").get should be(Decameters(10.33))
    Length("10.33 hm").get should be(Hectometers(10.33))
    Length("10.33 km").get should be(Kilometers(10.33))
    Length("10.33 in").get should be(Inches(10.33))
    Length("10.33 ft").get should be(Feet(10.33))
    Length("10.33 yd").get should be(Yards(10.33))
    Length("10.33 mi").get should be(UsMiles(10.33))
    Length("10.33 mile").get should be(InternationalMiles(10.33))
    Length("10.33 nmi").get should be(NauticalMiles(10.33))
    Length("10.33 au").get should be(AstronomicalUnits(10.33))
    Length("10.33 ly").get should be(LightYears(10.33))
    Length("10.33 pc").get should be(Parsecs(10.33))
    Length("10.33 kpc").get should be(KiloParsecs(10.33))
    Length("10.33 Mpc").get should be(MegaParsecs(10.33))
    Length("10.33 Gpc").get should be(GigaParsecs(10.33))
    Length("10.33 R☉").get should be(SolarRadii(10.33))
    Length("10.33 RN☉").get should be(NominalSolarRadii(10.33))
    Length("10.22 ħc/eV").get should be(ElectronVoltLength(10.22))
    Length("10.22 mħc/eV").get should be(MilliElectronVoltLength(10.22))
    Length("10.22 kħc/eV").get should be(KiloElectronVoltLength(10.22))
    Length("10.22 Mħc/eV").get should be(MegaElectronVoltLength(10.22))
    Length("10.22 Għc/eV").get should be(GigaElectronVoltLength(10.22))
    Length("10.22 Tħc/eV").get should be(TeraElectronVoltLength(10.22))
    Length("10.22 Pħc/eV").get should be(PetaElectronVoltLength(10.22))
    Length("10.22 Eħc/eV").get should be(ExaElectronVoltLength(10.22))
    Length("10.33 zz").failed.get should be(QuantityParseException("Unable to parse Length", "10.33 zz"))
    Length("ZZ m").failed.get should be(QuantityParseException("Unable to parse Length", "ZZ m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Meters(1)
    x.toMeters should be(1)
    x.toAngstroms should be(1 / (100*MetricSystem.Pico))
    x.toNanometers should be(1 / MetricSystem.Nano)
    x.toMicrons should be(1 / MetricSystem.Micro)
    x.toMillimeters should be(1 / MetricSystem.Milli)
    x.toCentimeters should be(1 / MetricSystem.Centi)
    x.toDecimeters should be(1 / MetricSystem.Deci)
    x.toDecameters should be(1 / MetricSystem.Deca)
    x.toHectometers should be(1 / MetricSystem.Hecto)
    x.toKilometers should be(1 / MetricSystem.Kilo)

    val metersPerFoot = 0.3048006096
    x.toInches should be(1 / (metersPerFoot / 12))
    x.toFeet should be(1 / metersPerFoot)
    x.toYards should be(1 / (metersPerFoot * 3))
    x.toUsMiles should be(1 / (metersPerFoot * 5280))
    x.toInternationalMiles should be(1 / 1609.344)
    x.toNauticalMiles should be(1 / 1852d)
    x.toAstronomicalUnits should be(1 / 149597870700d)
    x.toLightYears should be(1 / 9460730472580800d)
    x.toParsecs should be(1 / 3.08567758149137e16)
    x.toKiloParsecs should be(1 / 3.08567758149137e19)
    x.toMegaParsecs should be(1 / 3.08567758149137e22)
    x.toGigaParsecs should be(1 / 3.08567758149137e25)
    x.toSolarRadii should be(1 / 6.957e8)
    x.toNominalSolarRadii should be(1 / 6.957e8)

    x.toeV should be(1 / ElectronVoltLength.conversionFactor)
    x.tomeV should be(1 / MilliElectronVoltLength.conversionFactor)
    x.tokeV should be(1 / KiloElectronVoltLength.conversionFactor)
    x.toMeV should be(1 / MegaElectronVoltLength.conversionFactor)
    x.toGeV should be(1 / GigaElectronVoltLength.conversionFactor)
    x.toTeV should be(1 / TeraElectronVoltLength.conversionFactor)
    x.toPeV should be(1 / PetaElectronVoltLength.conversionFactor)
    x.toEeV should be(1 / ExaElectronVoltLength.conversionFactor)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Meters(1).toString(Meters) should be("1.0 m")
    Angstroms(1).toString(Angstroms) should be ("1.0 Å")
    Nanometers(1).toString(Nanometers) should be("1.0 nm")
    Microns(1).toString(Microns) should be("1.0 µm")
    Millimeters(1).toString(Millimeters) should be("1.0 mm")
    Centimeters(1).toString(Centimeters) should be("1.0 cm")
    Decimeters(1).toString(Decimeters) should be("1.0 dm")
    Decameters(1).toString(Decameters) should be("1.0 dam")
    Hectometers(1).toString(Hectometers) should be("1.0 hm")
    Kilometers(1).toString(Kilometers) should be("1.0 km")

    Inches(1).toString(Inches) should be("1.0 in")
    Feet(1).toString(Feet) should be("1.0 ft")
    Yards(1).toString(Yards) should be("1.0 yd")
    UsMiles(1).toString(UsMiles) should be("1.0 mi")
    InternationalMiles(1).toString(InternationalMiles) should be("1.0 mile")
    NauticalMiles(1).toString(NauticalMiles) should be("1.0 nmi")
    AstronomicalUnits(1).toString(AstronomicalUnits) should be("1.0 au")
    LightYears(1).toString(LightYears) should be("1.0 ly")
    Parsecs(1).toString(Parsecs) should be("1.0 pc")
    KiloParsecs(1).toString(KiloParsecs) should be("1.0 kpc")
    MegaParsecs(1).toString(MegaParsecs) should be("1.0 Mpc")
    GigaParsecs(1).toString(GigaParsecs) should be("1.0 Gpc")
    SolarRadii(1).toString(SolarRadii) should be("1.0 R☉")
    NominalSolarRadii(1).toString(NominalSolarRadii) should be("1.0 RN☉")

    ElectronVoltLength(1).toString(ElectronVoltLength) should be("1.0 ħc/eV")
    MilliElectronVoltLength(1).toString(MilliElectronVoltLength) should be("1.0 mħc/eV")
    KiloElectronVoltLength(1).toString(KiloElectronVoltLength) should be("1.0 kħc/eV")
    MegaElectronVoltLength(1).toString(MegaElectronVoltLength) should be("1.0 Mħc/eV")
    GigaElectronVoltLength(1).toString(GigaElectronVoltLength) should be("1.0 Għc/eV")
    TeraElectronVoltLength(1).toString(TeraElectronVoltLength) should be("1.0 Tħc/eV")
    PetaElectronVoltLength(1).toString(PetaElectronVoltLength) should be("1.0 Pħc/eV")
    ExaElectronVoltLength(1).toString(ExaElectronVoltLength) should be("1.0 Eħc/eV")
  }

  it should "return Area when multiplied by Length" in {
    Meters(1) * Meters(1) should be(SquareMeters(1))
  }

  it should "return Volume when multiplied by Area" in {
    Meters(1) * SquareMeters(1) should be(CubicMeters(1))
  }

  it should "return Energy when multiplied by Force" in {
    Meters(1) * Newtons(1) should be(Joules(1))
  }

  it should "return ElectricalConductance when multiplied by Conductivity" in {
    Meters(1) * SiemensPerMeter(1) should be(Siemens(1))
  }

  it should "return Resistivity when multiplied by ElectricalResistance" in {
    Meters(1) * Ohms(1) should be(OhmMeters(1))
  }

  it should "return Velocity when divided by Time" in {
    Meters(1) / Seconds(1) should be(MetersPerSecond(1))
  }

  it should "return Time when divided by Velocity" in {
    Meters(1) / MetersPerSecond(1) should be(Seconds(1))
  }

  it should "return an Area when squared" in {
    Meters(4).squared should be(SquareMeters(16))
  }

  it should "return a Volume when cubed" in {
    Meters(3).cubed should be(CubicMeters(27))
  }

  behavior of "LengthConversions"

  it should "provide aliases for single unit values" in {
    import LengthConversions._

    angstrom should be(Angstroms(1))
    nanometer should be(Nanometers(1))
    nanometre should be(Nanometers(1))
    micron should be(Microns(1))
    micrometer should be(Microns(1))
    micrometre should be(Microns(1))
    millimeter should be(Millimeters(1))
    millimetre should be(Millimeters(1))
    centimeter should be(Centimeters(1))
    centimetre should be(Centimeters(1))
    decimeter should be(Decimeters(1))
    decimetre should be(Decimeters(1))
    meter should be(Meters(1))
    metre should be(Meters(1))
    decameter should be(Decameters(1))
    decametre should be(Decameters(1))
    hectometer should be(Hectometers(1))
    hectometre should be(Hectometers(1))
    kilometer should be(Kilometers(1))
    kilometre should be(Kilometers(1))
    inch should be(Inches(1))
    foot should be(Feet(1))
    yard should be(Yards(1))
    mile should be(UsMiles(1))
    nauticalMile should be(NauticalMiles(1))
    astronomicalUnit should be(AstronomicalUnits(1))
    lightYear should be(LightYears(1))
    parsec should be(Parsecs(1))
    kiloparsec should be(KiloParsecs(1))
    megaparsec should be(MegaParsecs(1))
    gigaparsec should be(GigaParsecs(1))
    solarRadius should be(SolarRadii(1))
    nominalSolarRadius should be(NominalSolarRadii(1))
    eV should be(ElectronVoltLength(1))
    meV should be(MilliElectronVoltLength(1))
    keV should be(KiloElectronVoltLength(1))
    MeV should be(MegaElectronVoltLength(1))
    GeV should be(GigaElectronVoltLength(1))
    TeV should be(TeraElectronVoltLength(1))
    PeV should be(PetaElectronVoltLength(1))
    EeV should be(ExaElectronVoltLength(1))
  }

  it should "provide implicit conversion from Double" in {
    import LengthConversions._

    val d = 10d
    d.Å should be(Angstroms(d))
    d.angstroms should be(Angstroms(d))
    d.nm should be(Nanometers(d))
    d.nanometers should be(Nanometers(d))
    d.nanometres should be(Nanometers(d))
    d.µm should be(Microns(d))
    d.microns should be(Microns(d))
    d.mm should be(Millimeters(d))
    d.millimeters should be(Millimeters(d))
    d.millimetres should be(Millimeters(d))
    d.cm should be(Centimeters(d))
    d.centimeters should be(Centimeters(d))
    d.centimetres should be(Centimeters(d))
    d.dm should be(Decimeters(d))
    d.meters should be(Meters(d))
    d.metres should be(Meters(d))
    d.dam should be(Decameters(d))
    d.hm should be(Hectometers(d))
    d.km should be(Kilometers(d))
    d.kilometers should be(Kilometers(d))
    d.kilometres should be(Kilometers(d))
    d.inches should be(Inches(d))
    d.ft should be(Feet(d))
    d.feet should be(Feet(d))
    d.yd should be(Yards(d))
    d.yards should be(Yards(d))
    d.miles should be(UsMiles(d))
    d.nmi should be(NauticalMiles(d))
    d.au should be(AstronomicalUnits(d))
    d.ly should be(LightYears(d))
    d.lightYears should be(LightYears(d))
    d.pc should be(Parsecs(d))
    d.kpc should be(KiloParsecs(d))
    d.Mpc should be(MegaParsecs(d))
    d.Gpc should be(GigaParsecs(d))
    d.parsecs should be(Parsecs(d))
    d.solarRadii should be(SolarRadii(d))
    d.nominalSolarRadii should be(NominalSolarRadii(d))
    d.eV should be(ElectronVoltLength(d))
    d.meV should be(MilliElectronVoltLength(d))
    d.keV should be(KiloElectronVoltLength(d))
    d.MeV should be(MegaElectronVoltLength(d))
    d.GeV should be(GigaElectronVoltLength(d))
    d.TeV should be(TeraElectronVoltLength(d))
    d.PeV should be(PetaElectronVoltLength(d))
    d.EeV should be(ExaElectronVoltLength(d))
  }

  it should "provide implicit conversion from String" in {
    import LengthConversions._

    "10.33 Å".toLength.get should be(Angstroms(10.33))
    "10.33 nm".toLength.get should be(Nanometers(10.33))
    "10.33 µm".toLength.get should be(Microns(10.33))
    "10.33 mm".toLength.get should be(Millimeters(10.33))
    "10.33 cm".toLength.get should be(Centimeters(10.33))
    "10.33 dm".toLength.get should be(Decimeters(10.33))
    "10.33 m".toLength.get should be(Meters(10.33))
    "10.33 dam".toLength.get should be(Decameters(10.33))
    "10.33 hm".toLength.get should be(Hectometers(10.33))
    "10.33 km".toLength.get should be(Kilometers(10.33))
    "10.33 in".toLength.get should be(Inches(10.33))
    "10.33 ft".toLength.get should be(Feet(10.33))
    "10.33 yd".toLength.get should be(Yards(10.33))
    "10.33 mi".toLength.get should be(UsMiles(10.33))
    "10.33 mile".toLength.get should be(InternationalMiles(10.33))
    "10.33 nmi".toLength.get should be(NauticalMiles(10.33))
    "10.33 au".toLength.get should be(AstronomicalUnits(10.33))
    "10.33 ly".toLength.get should be(LightYears(10.33))
    "10.33 pc".toLength.get should be(Parsecs(10.33))
    "10.33 kpc".toLength.get should be(KiloParsecs(10.33))
    "10.33 Mpc".toLength.get should be(MegaParsecs(10.33))
    "10.33 Gpc".toLength.get should be(GigaParsecs(10.33))
    "10.33 R☉".toLength.get should be(SolarRadii(10.33))
    "10.33 RN☉".toLength.get should be(NominalSolarRadii(10.33))
    "10.22 ħc/eV".toLength.get should be(ElectronVoltLength(10.22))
    "10.22 mħc/eV".toLength.get should be(MilliElectronVoltLength(10.22))
    "10.22 kħc/eV".toLength.get should be(KiloElectronVoltLength(10.22))
    "10.22 Mħc/eV".toLength.get should be(MegaElectronVoltLength(10.22))
    "10.22 Għc/eV".toLength.get should be(GigaElectronVoltLength(10.22))
    "10.22 Tħc/eV".toLength.get should be(TeraElectronVoltLength(10.22))
    "10.22 Pħc/eV".toLength.get should be(PetaElectronVoltLength(10.22))
    "10.22 Eħc/eV".toLength.get should be(ExaElectronVoltLength(10.22))
    "10.33 zz".toLength.failed.get should be(QuantityParseException("Unable to parse Length", "10.33 zz"))
    "ZZ m".toLength.failed.get should be(QuantityParseException("Unable to parse Length", "ZZ m"))
  }

  it should "provide Numeric support" in {
    import LengthConversions.LengthNumeric

    val ls = List(Meters(1000), Kilometers(1))
    ls.sum should be(Meters(2000))
  }
}
