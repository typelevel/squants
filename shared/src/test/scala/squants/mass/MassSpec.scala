/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.motion._
import squants.space.{CubicMeters, Meters, SquareMeters}
import squants.time.Seconds
import squants.{MetricSystem, QuantityParseException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassSpec extends AnyFlatSpec with Matchers {

  behavior of "Mass and its Units of Measure"

  it should "create values using UOM factories" in {
    Nanograms(10.22).toNanograms should be(10.22)
    Micrograms(10.22).toMicrograms should be(10.22)
    Milligrams(10.22).toMilligrams should be(10.22)
    Grams(10.22).toGrams should be(10.22)
    Kilograms(10.22).toKilograms should be(10.22)
    Tonnes(10.22).toTonnes should be(10.22)
    Ounces(10.22).toOunces should be(10.22)
    Pounds(10.22).toPounds should be(10.22)
    Kilopounds(10.22).toKilopounds should be(10.22)
    Megapounds(10.22).toMegapounds should be(10.22)
    Stone(10.22).toStone should be(10.22)
    TroyGrains(10.22).toTroyGrains should be(10.22)
    Pennyweights(10.22).toPennyweights should be(10.22)
    TroyOunces(10.22).toTroyOunces should be(10.22)
    TroyPounds(10.22).toTroyPounds should be(10.22)
    Tolas(10.22).toTolas should be(10.22)
    Carats(10.22).toCarats should be(10.22)
    SolarMasses(10.22).toSolarMasses should be(10.22)

    ElectronVoltMass(1).toeV should be(1)
    MilliElectronVoltMass(1).tomeV should be(1)
    KiloElectronVoltMass(1).tokeV should be(1)
    MegaElectronVoltMass(1).toMeV should be(1)
    GigaElectronVoltMass(1).toGeV should be(1)
    TeraElectronVoltMass(1).toTeV should be(1)
    PetaElectronVoltMass(1).toPeV should be(1)
    ExaElectronVoltMass(1).toEeV should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Mass("10.22 ng").get should be(Nanograms(10.22))
    Mass("10.22 mcg").get should be(Micrograms(10.22))
    Mass("10.22 mg").get should be(Milligrams(10.22))
    Mass("10.22 g").get should be(Grams(10.22))
    Mass("10.22 kg").get should be(Kilograms(10.22))
    Mass("10.22 t").get should be(Tonnes(10.22))
    Mass("10.22 oz").get should be(Ounces(10.22))
    Mass("10.22 lb").get should be(Pounds(10.22))
    Mass("10.22 klb").get should be(Kilopounds(10.22))
    Mass("10.22 Mlb").get should be(Megapounds(10.22))
    Mass("10.22 st").get should be(Stone(10.22))
    Mass("10.22 gr").get should be(TroyGrains(10.22))
    Mass("10.22 dwt").get should be(Pennyweights(10.22))
    Mass("10.22 oz t").get should be(TroyOunces(10.22))
    Mass("10.22 lb t").get should be(TroyPounds(10.22))
    Mass("10.22 tola").get should be(Tolas(10.22))
    Mass("10.22 ct").get should be(Carats(10.22))
    Mass("10.22 M☉").get should be(SolarMasses(10.22))
    Mass("10.22 eV/c²").get should be(ElectronVoltMass(10.22))
    Mass("10.22 meV/c²").get should be(MilliElectronVoltMass(10.22))
    Mass("10.22 keV/c²").get should be(KiloElectronVoltMass(10.22))
    Mass("10.22 MeV/c²").get should be(MegaElectronVoltMass(10.22))
    Mass("10.22 GeV/c²").get should be(GigaElectronVoltMass(10.22))
    Mass("10.22 TeV/c²").get should be(TeraElectronVoltMass(10.22))
    Mass("10.22 PeV/c²").get should be(PetaElectronVoltMass(10.22))
    Mass("10.22 EeV/c²").get should be(ExaElectronVoltMass(10.22))
    Mass("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Mass", "10.45 zz"))
    Mass("zz g").failed.get should be(QuantityParseException("Unable to parse Mass", "zz g"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Grams(1)
    x.toNanograms should be(1 / MetricSystem.Nano)
    x.toMicrograms should be(1 / MetricSystem.Micro)
    x.toMilligrams should be(1 / MetricSystem.Milli)
    x.toGrams should be(1)
    x.toKilograms should be(1 / MetricSystem.Kilo)
    x.toTonnes should be(1 / MetricSystem.Mega)
    x.toPounds should be(1 / Pounds.conversionFactor)
    x.toOunces should be(1 / Ounces.conversionFactor)
    x.toTroyGrains should be (1 / TroyGrains.conversionFactor)
    x.toPennyweights should be (1 / Pennyweights.conversionFactor)
    x.toTroyOunces should be (1 / TroyOunces.conversionFactor)
    x.toTroyPounds should be (1 / TroyPounds.conversionFactor)
    x.toCarats should be (1 / Carats.conversionFactor)
    x.toSolarMasses should be (1 / SolarMasses.conversionFactor)
    x.toeV should be (1 / ElectronVoltMass.conversionFactor)

    Grams(1000) should be(Kilograms(1))
    Kilograms(0.45359237) should be(Pounds(1))
    Ounces(16) should be(Pounds(1))
    Pounds(14) should be(Stone(1))

    TroyOunces(12) should be(TroyPounds(1))
    TroyGrains(180) should be(Tolas(1))
    TroyPounds(1) should be(TroyGrains(5760))
    TroyPounds(1).toGrams should be(373.2417216 +- 0.000000001)
    Carats(1) should be(Milligrams(200))
    TroyOunces(1).toGrams should be(31.1034768)
    Pennyweights(1).toGrams should be(1.55517384 +- 0.000000001)
    SolarMasses(1).toKilograms should be(1.98855e30 +- 0.00025)

    x.toeV should be(1 / ElectronVoltMass.conversionFactor)
    x.tomeV should be(1 / MilliElectronVoltMass.conversionFactor)
    x.tokeV should be(1 / KiloElectronVoltMass.conversionFactor)
    x.toMeV should be(1 / MegaElectronVoltMass.conversionFactor)
    x.toGeV should be(1 / GigaElectronVoltMass.conversionFactor)
    x.toTeV should be(1 / TeraElectronVoltMass.conversionFactor)
    x.toPeV should be(1 / PetaElectronVoltMass.conversionFactor)
    x.toEeV should be(1 / ExaElectronVoltMass.conversionFactor)

  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Nanograms(1).toString(Nanograms) should be("1.0 ng")
    Micrograms(1).toString(Micrograms) should be("1.0 mcg")
    Milligrams(1).toString(Milligrams) should be("1.0 mg")
    Grams(1).toString(Grams) should be("1.0 g")
    Kilograms(1).toString(Kilograms) should be("1.0 kg")
    Tonnes(1).toString(Tonnes) should be("1.0 t")
    Pounds(1).toString(Pounds) should be("1.0 lb")
    Ounces(1).toString(Ounces) should be("1.0 oz")
    Stone(1).toString(Stone) should be("1.0 st")

    TroyGrains(1).toString(TroyGrains) should be("1.0 gr")
    Pennyweights(1).toString(Pennyweights) should be("1.0 dwt")
    TroyOunces(1).toString(TroyOunces) should be("1.0 oz t")
    TroyPounds(1).toString(TroyPounds) should be("1.0 lb t")
    Tolas(1).toString(Tolas) should be("1.0 tola")
    Carats(1).toString(Carats) should be("1.0 ct")
    SolarMasses(1).toString(SolarMasses) should be("1.0 M☉")

    ElectronVoltMass(1).toString(ElectronVoltMass) should be("1.0 eV/c²")
    MilliElectronVoltMass(1).toString(MilliElectronVoltMass) should be("1.0 meV/c²")
    KiloElectronVoltMass(1).toString(KiloElectronVoltMass) should be("1.0 keV/c²")
    MegaElectronVoltMass(1).toString(MegaElectronVoltMass) should be("1.0 MeV/c²")
    GigaElectronVoltMass(1).toString(GigaElectronVoltMass) should be("1.0 GeV/c²")
    TeraElectronVoltMass(1).toString(TeraElectronVoltMass) should be("1.0 TeV/c²")
    PetaElectronVoltMass(1).toString(PetaElectronVoltMass) should be("1.0 PeV/c²")
    ExaElectronVoltMass(1).toString(ExaElectronVoltMass) should be("1.0 EeV/c²")
  }

  it should "return Momentum when multiplied by Velocity" in {
    Kilograms(1) * MetersPerSecond(1) should be(NewtonSeconds(1))
  }

  it should "return Force when multiplied by Acceleration" in {
    Kilograms(1) * MetersPerSecondSquared(1) should be(Newtons(1))
  }

  it should "return Volume when divided by Density" in {
    Kilograms(1) / KilogramsPerCubicMeter(1) should be(CubicMeters(1))
  }

  it should "return Time when divided by MassFlowRate" in {
    Kilograms(1) / KilogramsPerSecond(1) should be(Seconds(1))
  }

  it should "return MassFlowRate when divided by Time" in {
    Kilograms(1) / Seconds(1) should be(KilogramsPerSecond(1))
  }

  it should "return Density when divided by Volume" in {
    Kilograms(1) / CubicMeters(1) should be(KilogramsPerCubicMeter(1))
  }

  it should "return Area when divided by AreaDensity" in {
    Kilograms(1) / SquareMeters(1) should be(KilogramsPerSquareMeter(1))
  }

  it should "return AreaDensity when divided by Area" in {
    Kilograms(1) / KilogramsPerSquareMeter(1) should be(SquareMeters(1))
  }

  it should "return MomentOfInertia when onRadius of Length" in {
    Kilograms(1) onRadius Meters(1) should be(KilogramsMetersSquared(1))
  }

  behavior of "MassConversions"

  it should "provide aliases for single unit values" in {
    import MassConversions._

    nanogram should be(Nanograms(1))
    microgram should be(Micrograms(1))
    milligram should be(Milligrams(1))
    gram should be(Grams(1))
    kilogram should be(Kilograms(1))
    tonne should be(Tonnes(1))
    pound should be(Pounds(1))
    ounce should be(Ounces(1))
    stone should be(Stone(1))

    troyGrain should be(TroyGrains(1))
    pennyweight should be(Pennyweights(1))
    troyOunce should be(TroyOunces(1))
    troyPound should be(TroyPounds(1))
    tola should be(Tolas(1))
    carat should be(Carats(1))
    solarMass should be(SolarMasses(1))

    eV should be(ElectronVoltMass(1))
    meV should be(MilliElectronVoltMass(1))
    keV should be(KiloElectronVoltMass(1))
    MeV should be(MegaElectronVoltMass(1))
    GeV should be(GigaElectronVoltMass(1))
    TeV should be(TeraElectronVoltMass(1))
    PeV should be(PetaElectronVoltMass(1))
    EeV should be(ExaElectronVoltMass(1))
  }

  it should "provide implicit conversion from Double" in {
    import MassConversions._

    val d = 10.22
    d.ng should be(Nanograms(d))
    d.nanograms should be(Nanograms(d))
    d.mcg should be(Micrograms(d))
    d.micrograms should be(Micrograms(d))
    d.mg should be(Milligrams(d))
    d.milligrams should be(Milligrams(d))
    d.g should be(Grams(d))
    d.grams should be(Grams(d))
    d.kg should be(Kilograms(d))
    d.kilograms should be(Kilograms(d))
    d.tonnes should be(Tonnes(d))
    d.pounds should be(Pounds(d))
    d.ounces should be(Ounces(d))
    d.stone should be(Stone(d))

    d.troyGrains should be(TroyGrains(d))
    d.dwt should be(Pennyweights(d))
    d.pennyweights should be(Pennyweights(d))
    d.troyOunces should be(TroyOunces(d))
    d.troyPounds should be(TroyPounds(d))
    d.tolas should be(Tolas(d))
    d.ct should be(Carats(d))
    d.carats should be(Carats(d))
    d.solarMasses should be(SolarMasses(d))

    d.eV should be(ElectronVoltMass(d))
    d.meV should be(MilliElectronVoltMass(d))
    d.keV should be(KiloElectronVoltMass(d))
    d.MeV should be(MegaElectronVoltMass(d))
    d.GeV should be(GigaElectronVoltMass(d))
    d.TeV should be(TeraElectronVoltMass(d))
    d.PeV should be(PetaElectronVoltMass(d))
    d.EeV should be(ExaElectronVoltMass(d))
  }

  it should "provide implicit conversions from arbitrary Numeric types" in {
    import MassConversions._

    val i = 10
    i.grams should be(Grams(i))

    val bd = BigDecimal(10.22)
    bd.grams should be(Grams(bd))
  }

  it should "provide implicit conversions from String" in {
    import MassConversions._

    "10.45 ng".toMass.get should be(Nanograms(10.45))
    "10.45 mcg".toMass.get should be(Micrograms(10.45))
    "10.45 mg".toMass.get should be(Milligrams(10.45))
    "10.45 g".toMass.get should be(Grams(10.45))
    "10.45 kg".toMass.get should be(Kilograms(10.45))
    "10.45 t".toMass.get should be(Tonnes(10.45))
    "10.45 lb".toMass.get should be(Pounds(10.45))
    "10.45 oz".toMass.get should be(Ounces(10.45))
    "10.45 st".toMass.get should be(Stone(10.45))
    "10.45 gr".toMass.get should be(TroyGrains(10.45))
    "10.45 dwt".toMass.get should be(Pennyweights(10.45))
    "10.45 oz t".toMass.get should be(TroyOunces(10.45))
    "10.45 lb t".toMass.get should be(TroyPounds(10.45))
    "10.45 tola".toMass.get should be(Tolas(10.45))
    "10.45 ct".toMass.get should be(Carats(10.45))
    "10.45 M☉".toMass.get should be(SolarMasses(10.45))
    "10.22 eV/c²".toMass.get should be(ElectronVoltMass(10.22))
    "10.22 meV/c²".toMass.get should be(MilliElectronVoltMass(10.22))
    "10.22 keV/c²".toMass.get should be(KiloElectronVoltMass(10.22))
    "10.22 MeV/c²".toMass.get should be(MegaElectronVoltMass(10.22))
    "10.22 GeV/c²".toMass.get should be(GigaElectronVoltMass(10.22))
    "10.22 TeV/c²".toMass.get should be(TeraElectronVoltMass(10.22))
    "10.22 PeV/c²".toMass.get should be(PetaElectronVoltMass(10.22))
    "10.22 EeV/c²".toMass.get should be(ExaElectronVoltMass(10.22))
    "10.45 zz".toMass.failed.get should be(QuantityParseException("Unable to parse Mass", "10.45 zz"))
    "zz oz".toMass.failed.get should be(QuantityParseException("Unable to parse Mass", "zz oz"))
  }

  it should "provide Numeric support" in {
    import MassConversions._

    val ms = List(Grams(1000), Kilograms(10))
    ms.sum should be(Kilograms(11))
  }
}
