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

    assert(Volts(1).toVolts == 1)
    assert(Microvolts(1).toMircovolts == 1)
    assert(Millivolts(1).toMillivolts == 1)
    assert(Kilovolts(1).toKilovolts == 1)
    assert(Megavolts(1).toMegavolts == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Volts(1)
    assert(x.toVolts == 1.0)
    assert(x.toMircovolts == 1 / MetricSystem.Micro)
    assert(x.toMillivolts == 1 / MetricSystem.Milli)
    assert(x.toKilovolts == 1 / MetricSystem.Kilo)
    assert(x.toMegavolts == 1 / MetricSystem.Mega)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Volts(1).toString(Volts) == "1.0 V")
    assert(Microvolts(1).toString(Microvolts) == "1.0 μV")
    assert(Millivolts(1).toString(Millivolts) == "1.0 mV")
    assert(Kilovolts(1).toString(Kilovolts) == "1.0 kV")
    assert(Megavolts(1).toString(Megavolts) == "1.0 MV")
  }

  it should "return ElectricalResistance when multiplied by ElectricalCurrent" in {
    assert(Volts(1) / Amperes(1) == Ohms(1))
  }

  it should "return ElectricalCurrent when multiplied by ElectricalResistance" in {
    assert(Volts(1) / Ohms(1) == Amperes(1))
  }

  it should "return Power when multiplied by ElectricalCurrent" in {
    assert(Volts(1) * Amperes(1) == Watts(1))
  }

  it should "return ElectricalCharge when multiplied by Capacitance" in {
    assert(Volts(1) * Farads(1) == Coulombs(1))
  }

  it should "return MagneticFlux when multiplied by Time" in {
    assert(Volts(1) * Seconds(1) == Webers(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Volts(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricPotential](ser)
    assert(x == des)
  }

  behavior of "ElectricalPotentialConversions"

  it should "provide aliases for single unit values" in {
    import ElectricPotentialConversions._

    assert(volt == Volts(1))
    assert(microvolt == Microvolts(1))
    assert(millivolt == Millivolts(1))
    assert(kilovolt == Kilovolts(1))
    assert(megavolt == Megavolts(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricPotentialConversions._

    val d = 10d
    assert(d.V == Volts(d))
    assert(d.volts == Volts(d))
    assert(d.microvolts == Microvolts(d))
    assert(d.millivolts == Millivolts(d))
    assert(d.kilovolts == Kilovolts(d))
    assert(d.megavolts == Megavolts(d))
  }
}
