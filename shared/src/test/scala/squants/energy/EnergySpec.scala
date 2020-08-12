/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants.electro.{Coulombs, Volts}
import squants.mass.{Kilograms, Moles}
import squants.motion.{MetersPerSecond, NewtonSeconds, Newtons}
import squants.space.{CubicMeters, Meters}
import squants.thermal.{JoulesPerKelvin, Kelvin}
import squants.time.Hours
import squants.{MetricSystem, QuantityParseException}
import squants.radio.{WattsPerSquareMeter, BecquerelsPerSquareMeterSecond}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class EnergySpec extends AnyFlatSpec with Matchers {

  behavior of "Energy and its Units of Measure"

  it should "create values using UOM factories" in {
    WattHours(1).toWattHours should be(1)
    MilliwattHours(1).toMilliwattHours should be(1)
    KilowattHours(1).toKilowattHours should be(1)
    MegawattHours(1).toMegawattHours should be(1)
    GigawattHours(1).toGigawattHours should be(1)

    Joules(1).toJoules should be(1)
    Picojoules(1).toPicojoules should be(1)
    Nanojoules(1).toNanojoules should be(1)
    Microjoules(1).toMicrojoules should be(1)
    Millijoules(1).toMillijoules should be(1)
    Kilojoules(1).toKilojoules should be(1)
    Megajoules(1).toMegajoules should be(1)
    Gigajoules(1).toGigajoules should be(1)
    Terajoules(1).toTerajoules should be(1)

    BritishThermalUnits(1).toBtus should be(1)
    MBtus(1).toMBtus should be(1)
    MMBtus(1).toMMBtus should be(1)
    Ergs(1).toErgs should be(1)

    ElectronVolt(1).toeV should be(1)
    MilliElectronVolt(1).tomeV should be(1)
    KiloElectronVolt(1).tokeV should be(1)
    MegaElectronVolt(1).toMeV should be(1)
    GigaElectronVolt(1).toGeV should be(1)
    TeraElectronVolt(1).toTeV should be(1)
    PetaElectronVolt(1).toPeV should be(1)
    ExaElectronVolt(1).toEeV should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Energy("10.22 J").get should be(Joules(10.22))
    Energy("10.22 Wh").get should be(WattHours(10.22))
    Energy("10.22 mWh").get should be(MilliwattHours(10.22))
    Energy("10.22 kWh").get should be(KilowattHours(10.22))
    Energy("10.22 MWh").get should be(MegawattHours(10.22))
    Energy("10.22 GWh").get should be(GigawattHours(10.22))
    Energy("10.22 Btu").get should be(BritishThermalUnits(10.22))
    Energy("10.22 MBtu").get should be(MBtus(10.22))
    Energy("10.22 MMBtu").get should be(MMBtus(10.22))
    Energy("10.22 erg").get should be(Ergs(10.22))
    Energy("10.22 eV").get should be(ElectronVolt(10.22))
    Energy("10.22 meV").get should be(MilliElectronVolt(10.22))
    Energy("10.22 keV").get should be(KiloElectronVolt(10.22))
    Energy("10.22 MeV").get should be(MegaElectronVolt(10.22))
    Energy("10.22 GeV").get should be(GigaElectronVolt(10.22))
    Energy("10.22 TeV").get should be(TeraElectronVolt(10.22))
    Energy("10.22 PeV").get should be(PetaElectronVolt(10.22))
    Energy("10.22 EeV").get should be(ExaElectronVolt(10.22))
    Energy("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Energy", "10.22 zz"))
    Energy("ZZ J").failed.get should be(QuantityParseException("Unable to parse Energy", "ZZ J"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattHours(1)

    x.toWattHours should be(1)
    x.toMilliwattHours should be(1 / MetricSystem.Milli)
    x.toKilowattHours should be(1 / MetricSystem.Kilo)
    x.toMegawattHours should be(1 / MetricSystem.Mega)
    x.toGigawattHours should be(1 / MetricSystem.Giga)

    x.toJoules should be(1 / Joules.conversionFactor)
    x.toPicojoules should be(1 / Picojoules.conversionFactor)
    x.toNanojoules should be(1 / Nanojoules.conversionFactor)
    x.toMicrojoules should be(1 / Microjoules.conversionFactor)
    x.toMillijoules should be(1 / Millijoules.conversionFactor)
    x.toKilojoules should be(1 / Kilojoules.conversionFactor)
    x.toMegajoules should be(1 / Megajoules.conversionFactor)
    x.toGigajoules should be(1 / Gigajoules.conversionFactor)

    x.toBtus should be(1 / BritishThermalUnits.conversionFactor)
    x.toMBtus should be(1 / MBtus.conversionFactor)
    x.toMMBtus should be(1 / MMBtus.conversionFactor)
    x.toErgs should be(1 / Ergs.conversionFactor)

    x.toeV should be(1 / ElectronVolt.conversionFactor)
    x.tomeV should be(1 / MilliElectronVolt.conversionFactor)
    x.tokeV should be(1 / KiloElectronVolt.conversionFactor)
    x.toMeV should be(1 / MegaElectronVolt.conversionFactor)
    x.toGeV should be(1 / GigaElectronVolt.conversionFactor)
    x.toTeV should be(1 / TeraElectronVolt.conversionFactor)
    x.toPeV should be(1 / PetaElectronVolt.conversionFactor)
    x.toEeV should be(1 / ExaElectronVolt.conversionFactor)

    ElectronVolt(1).toJoules should be(1.602176565e-19)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattHours(1).toString(WattHours) should be("1.0 Wh")
    MilliwattHours(1).toString(MilliwattHours) should be("1.0 mWh")
    KilowattHours(1).toString(KilowattHours) should be("1.0 kWh")
    MegawattHours(1).toString(MegawattHours) should be("1.0 MWh")
    GigawattHours(1).toString(GigawattHours) should be("1.0 GWh")

    Joules(1).toString(Joules) should be("1.0 J")
    Picojoules(1).toString(Picojoules) should be("1.0 pJ")
    Nanojoules(1).toString(Nanojoules) should be("1.0 nJ")
    Microjoules(1).toString(Microjoules) should be("1.0 µJ")
    Millijoules(1).toString(Millijoules) should be("1.0 mJ")
    Kilojoules(1).toString(Kilojoules) should be("1.0 kJ")
    Megajoules(1).toString(Megajoules) should be("1.0 MJ")
    Gigajoules(1).toString(Gigajoules) should be("1.0 GJ")
    Terajoules(1).toString(Terajoules) should be("1.0 TJ")

    BritishThermalUnits(1).toString(BritishThermalUnits) should be("1.0 Btu")
    MBtus(1).toString(MBtus) should be("1.0 MBtu")
    MMBtus(1).toString(MMBtus) should be("1.0 MMBtu")
    Ergs(1).toString(Ergs) should be ("1.0 erg")

    ElectronVolt(1).toString(ElectronVolt) should be("1.0 eV")
    MilliElectronVolt(1).toString(MilliElectronVolt) should be("1.0 meV")
    KiloElectronVolt(1).toString(KiloElectronVolt) should be("1.0 keV")
    MegaElectronVolt(1).toString(MegaElectronVolt) should be("1.0 MeV")
    GigaElectronVolt(1).toString(GigaElectronVolt) should be("1.0 GeV")
    TeraElectronVolt(1).toString(TeraElectronVolt) should be("1.0 TeV")
    PetaElectronVolt(1).toString(PetaElectronVolt) should be("1.0 PeV")
    ExaElectronVolt(1).toString(ExaElectronVolt) should be("1.0 EeV")
  }

  it should "return Irradiance when multiplied by ParticleFlux" in {
    WattHours(1) * BecquerelsPerSquareMeterSecond(1) should be(WattsPerSquareMeter(Hours(1).toSeconds))
  }

  it should "return Power when divided by Time" in {
    WattHours(1) / Hours(1) should be(Watts(1))
  }

  it should "return Time when divided by Power" in {
    WattHours(1) / Watts(1) should be(Hours(1))
  }

  it should "return ElectricalPotential when divided by ElectricalCharge" in {
    Joules(1) / Coulombs(1) should be(Volts(1))
  }

  it should "return Force when divided by Length" in {
    Joules(1) / Meters(1) should be(Newtons(1))
  }

  it should "return Mass when divided by SpecificEnergy" in {
    Joules(1) / Grays(1) should be(Kilograms(1))
  }

  it should "return SpecificEnergy when divided by Mass" in {
    Joules(1) / Kilograms(1) should be(Grays(1))
  }

  it should "return Volume when divided by EnergyDensity" in {
    Joules(1) / JoulesPerCubicMeter(1) should be(CubicMeters(1))
  }

  it should "return EnergyDensity when divided by Volume" in {
    Joules(1) / CubicMeters(1) should be(JoulesPerCubicMeter(1))
  }

  it should "return ThermalCapacity when divided by Temperature" in {
    Joules(10) / JoulesPerKelvin(4) should be(Kelvin(2.5))
  }

  it should "return Temperature when divided by ThermalCapacity" in {
    Joules(10) / Kelvin(2) should be(JoulesPerKelvin(5))
  }

  it should "return MolarEnergy when divided by ChemicalAmount" in {
    Joules(10) / Moles(2) should be(JoulesPerMole(5))
  }

  behavior of "KineticEnergyCalculations"

  it should "calculate Kinetic Energy from Mass and Velocity" in {
    KineticEnergy(Kilograms(10), MetersPerSecond(5)) should be(Joules(125))
    KineticEnergy(Kilograms(5), MetersPerSecond(10)) should be(Joules(250))
  }

  it should "calculate Kinetic Energy from Mass and Momentum" in {
    KineticEnergy(Kilograms(10), NewtonSeconds(5)) should be(Joules(.25))
    KineticEnergy(Kilograms(5), NewtonSeconds(10)) should be(Joules(1))
  }

  behavior of "EnergyConversions"

  it should "provide aliases for single unit values" in {
    import EnergyConversions._

    wattHour should be(WattHours(1))
    Wh should be(WattHours(1))
    milliwattHour should be(MilliwattHours(1))
    mWh should be(MilliwattHours(1))
    kilowattHour should be(KilowattHours(1))
    kWh should be(KilowattHours(1))
    megawattHour should be(MegawattHours(1))
    MWh should be(MegawattHours(1))
    gigawattHour should be(GigawattHours(1))
    GWh should be(GigawattHours(1))

    joule should be(Joules(1))
    picojoule should be(Picojoules(1))
    nanojoule should be(Nanojoules(1))
    microjoule should be(Microjoules(1))
    millijoule should be(Millijoules(1))
    kilojoule should be(Kilojoules(1))
    megajoule should be(Megajoules(1))
    gigajoule should be(Gigajoules(1))
    terajoule should be(Terajoules(1))

    btu should be(BritishThermalUnits(1))
    btuMultiplier should be(0.2930710701722222)

    eV should be(ElectronVolt(1))
    meV should be(MilliElectronVolt(1))
    keV should be(KiloElectronVolt(1))
    MeV should be(MegaElectronVolt(1))
    GeV should be(GigaElectronVolt(1))
    TeV should be(TeraElectronVolt(1))
    PeV should be(PetaElectronVolt(1))
    EeV should be(ExaElectronVolt(1))
  }

  it should "provide implicit conversion from Double" in {
    import EnergyConversions._

    val d = 10D
    d.Wh should be(WattHours(d))
    d.mWh should be(MilliwattHours(d))
    d.kWh should be(KilowattHours(d))
    d.MWh should be(MegawattHours(d))
    d.GWh should be(GigawattHours(d))
    d.wattHours should be(WattHours(d))
    d.kilowattHours should be(KilowattHours(d))
    d.megawattHours should be(MegawattHours(d))
    d.gigawattHours should be(GigawattHours(d))

    d.J should be(Joules(d))
    d.joules should be(Joules(d))
    d.picojoules should be(Picojoules(d))
    d.nanojoules should be(Nanojoules(d))
    d.microjoules should be(Microjoules(d))
    d.milljoules should be(Millijoules(d))
    d.kilojoules should be(Kilojoules(d))
    d.megajoules should be(Megajoules(d))
    d.gigajoules should be(Gigajoules(d))

    d.Btu should be(BritishThermalUnits(d))
    d.MBtu should be(MBtus(d))
    d.MMBtu should be(MMBtus(d))
    d.ergs should be(Ergs(d))

    d.eV should be(ElectronVolt(d))
    d.meV should be(MilliElectronVolt(d))
    d.keV should be(KiloElectronVolt(d))
    d.MeV should be(MegaElectronVolt(d))
    d.GeV should be(GigaElectronVolt(d))
    d.TeV should be(TeraElectronVolt(d))
    d.PeV should be(PetaElectronVolt(d))
    d.EeV should be(ExaElectronVolt(d))
  }

  it should "provide implicit conversions from String" in {
    import EnergyConversions._

    "10.22 J".toEnergy.get should be(Joules(10.22))
    "10.22 Wh".toEnergy.get should be(WattHours(10.22))
    "10.22 mWh".toEnergy.get should be(MilliwattHours(10.22))
    "10.22 kWh".toEnergy.get should be(KilowattHours(10.22))
    "10.22 MWh".toEnergy.get should be(MegawattHours(10.22))
    "10.22 GWh".toEnergy.get should be(GigawattHours(10.22))
    "10.22 Btu".toEnergy.get should be(BritishThermalUnits(10.22))
    "10.22 MBtu".toEnergy.get should be(MBtus(10.22))
    "10.22 MMBtu".toEnergy.get should be(MMBtus(10.22))
    "10.22 erg".toEnergy.get should be(Ergs(10.22))
    "10.22 eV".toEnergy.get should be(ElectronVolt(10.22))
    "10.22 meV".toEnergy.get should be(MilliElectronVolt(10.22))
    "10.22 keV".toEnergy.get should be(KiloElectronVolt(10.22))
    "10.22 MeV".toEnergy.get should be(MegaElectronVolt(10.22))
    "10.22 GeV".toEnergy.get should be(GigaElectronVolt(10.22))
    "10.22 TeV".toEnergy.get should be(TeraElectronVolt(10.22))
    "10.22 PeV".toEnergy.get should be(PetaElectronVolt(10.22))
    "10.22 EeV".toEnergy.get should be(ExaElectronVolt(10.22))
    "10.22 zz".toEnergy.failed.get should be(QuantityParseException("Unable to parse Energy", "10.22 zz"))
    "ZZ J".toEnergy.failed.get should be(QuantityParseException("Unable to parse Energy", "ZZ J"))
  }

  it should "provide Numeric support" in {
    import EnergyConversions.EnergyNumeric

    val es = List(WattHours(100), KilowattHours(1))
    es.sum should be(KilowattHours(1.1))
  }
}
