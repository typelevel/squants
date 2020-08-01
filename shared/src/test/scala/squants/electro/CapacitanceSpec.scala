/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.space.Meters
import squants.{MetricSystem, QuantityParseException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class CapacitanceSpec extends AnyFlatSpec with Matchers {

  behavior of "Capacitance and its Units of Measure"

  it should "create values using UOM factories" in {
    Farads(1).toFarads should be(1)
    Picofarads(1).toPicofarads should be(1)
    Nanofarads(1).toNanofarads should be(1)
    Microfarads(1).toMicrofarads should be(1)
    Millifarads(1).toMillifarads should be(1)
    Kilofarads(1).toKilofarads should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Capacitance("10.22 F").get should be(Farads(10.22))
    Capacitance("10.22 pF").get should be(Picofarads(10.22))
    Capacitance("10.22 nF").get should be(Nanofarads(10.22))
    Capacitance("10.22 μF").get should be(Microfarads(10.22))
    Capacitance("10.22 mF").get should be(Millifarads(10.22))
    Capacitance("10.22 kF").get should be(Kilofarads(10.22))
    Capacitance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Capacitance", "10.22 zz"))
    Capacitance("zz F").failed.get should be(QuantityParseException("Unable to parse Capacitance", "zz F"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Farads(1)
    x.toFarads should be(1.0)
    x.toPicofarads should be(1 / MetricSystem.Pico)
    x.toNanofarads should be(1 / MetricSystem.Nano)
    x.toMicrofarads should be(1 / MetricSystem.Micro)
    x.toMillifarads should be(1 / MetricSystem.Milli)
    x.toKilofarads should be(1 / MetricSystem.Kilo)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Farads(1).toString(Farads) should be("1.0 F")
    Picofarads(1).toString(Picofarads) should be("1.0 pF")
    Nanofarads(1).toString(Nanofarads) should be("1.0 nF")
    Microfarads(1).toString(Microfarads) should be("1.0 μF")
    Millifarads(1).toString(Millifarads) should be("1.0 mF")
    Kilofarads(1).toString(Kilofarads) should be("1.0 kF")
  }

  it should "return ElectricalCharge when multiplied by ElectricalPotential" in {
    Farads(1) * Volts(1) should be(Coulombs(1))
  }

  it should "return Permittivity when divided by Length" in {
    Farads(1) / Meters(1) should be(FaradsPerMeter(1))
  }

  behavior of "CapacitanceConversions"

  it should "provide aliases for single unit values" in {
    import CapacitanceConversions._

    farad should be(Farads(1))
    picofarad should be(Picofarads(1))
    nanofarad should be(Nanofarads(1))
    microfarad should be(Microfarads(1))
    millifarad should be(Millifarads(1))
    kilofarad should be(Kilofarads(1))
  }

  it should "provide implicit conversion from Double" in {
    import CapacitanceConversions._

    val d = 10d
    d.farads should be(Farads(d))
    d.picofarads should be(Picofarads(d))
    d.nanofarads should be(Nanofarads(d))
    d.microfarads should be(Microfarads(d))
    d.millifarads should be(Millifarads(d))
    d.kilofarads should be(Kilofarads(d))
  }

  it should "provide Numeric support" in {
    import CapacitanceConversions.CapacitanceNumeric

    val cs = List(Farads(100), Kilofarads(1))
    cs.sum should be(Kilofarads(1.1))
  }
}
