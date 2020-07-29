/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.{ QuantityParseException, MetricSystem }
import squants.energy.Watts
import squants.time.Seconds
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricPotentialSpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricPotential and its Units of Measure"

  it should "create values using UOM factories" in {

    Volts(1).toVolts should be(1)
    Microvolts(1).toMicrovolts should be(1)
    Millivolts(1).toMillivolts should be(1)
    Kilovolts(1).toKilovolts should be(1)
    Megavolts(1).toMegavolts should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricPotential("10.22 V").get should be(Volts(10.22))
    ElectricPotential("10.22 μV").get should be(Microvolts(10.22))
    ElectricPotential("10.22 mV").get should be(Millivolts(10.22))
    ElectricPotential("10.22 kV").get should be(Kilovolts(10.22))
    ElectricPotential("10.22 MV").get should be(Megavolts(10.22))
    ElectricPotential("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricPotential", "10.22 zz"))
    ElectricPotential("zz V").failed.get should be(QuantityParseException("Unable to parse ElectricPotential", "zz V"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Volts(1)
    x.toVolts should be(1.0)
    x.toMicrovolts should be(1 / MetricSystem.Micro)
    x.toMillivolts should be(1 / MetricSystem.Milli)
    x.toKilovolts should be(1 / MetricSystem.Kilo)
    x.toMegavolts should be(1 / MetricSystem.Mega)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Volts(1).toString(Volts) should be("1.0 V")
    Microvolts(1).toString(Microvolts) should be("1.0 μV")
    Millivolts(1).toString(Millivolts) should be("1.0 mV")
    Kilovolts(1).toString(Kilovolts) should be("1.0 kV")
    Megavolts(1).toString(Megavolts) should be("1.0 MV")
  }

  it should "return ElectricalResistance when multiplied by ElectricalCurrent" in {
    Volts(1) / Amperes(1) should be(Ohms(1))
  }

  it should "return ElectricalCurrent when multiplied by ElectricalResistance" in {
    Volts(1) / Ohms(1) should be(Amperes(1))
  }

  it should "return Power when multiplied by ElectricalCurrent" in {
    Volts(1) * Amperes(1) should be(Watts(1))
  }

  it should "return ElectricalCharge when multiplied by Capacitance" in {
    Volts(1) * Farads(1) should be(Coulombs(1))
  }

  it should "return MagneticFlux when multiplied by Time" in {
    Volts(1) * Seconds(1) should be(Webers(1))
  }

  behavior of "ElectricalPotentialConversions"

  it should "provide aliases for single unit values" in {
    import ElectricPotentialConversions._

    volt should be(Volts(1))
    microvolt should be(Microvolts(1))
    millivolt should be(Millivolts(1))
    kilovolt should be(Kilovolts(1))
    megavolt should be(Megavolts(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricPotentialConversions._

    val d = 10d
    d.V should be(Volts(d))
    d.volts should be(Volts(d))
    d.microvolts should be(Microvolts(d))
    d.millivolts should be(Millivolts(d))
    d.kilovolts should be(Kilovolts(d))
    d.megavolts should be(Megavolts(d))
  }

  it should "provide Numeric support" in {
    import ElectricPotentialConversions.ElectricPotentialNumeric

    val eps = List(Volts(100), Kilovolts(1))
    eps.sum should be(Volts(1100))
  }
}
