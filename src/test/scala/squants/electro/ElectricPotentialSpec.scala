/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.MetricSystem
import squants.energy.Watts
import squants.time.Seconds
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricPotentialSpec extends FlatSpec with Matchers {

  behavior of "ElectricalPotential and its Units of Measure"

  it should "create values using UOM factories" in {

    Volts(1).toVolts should be(1)
    Microvolts(1).toMircovolts should be(1)
    Millivolts(1).toMillivolts should be(1)
    Kilovolts(1).toKilovolts should be(1)
    Megavolts(1).toMegavolts should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Volts(1)
    x.toVolts should be(1.0)
    x.toMircovolts should be(1 / MetricSystem.Micro)
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

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Volts(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricPotential](ser)
    x should be(des)
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
}
