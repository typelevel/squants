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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class CapacitanceSpec extends FlatSpec with Matchers {

  behavior of "Capacitance and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Farads(1).toFarads == 1)
    assert(Picofarads(1).toPicofarads == 1)
    assert(Nanofarads(1).toNanofarads == 1)
    assert(Microfarads(1).toMicrofarads == 1)
    assert(Millifarads(1).toMillifarads == 1)
    assert(Kilofarads(1).toKilofarads == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Farads(1)
    assert(x.toFarads == 1.0)
    assert(x.toPicofarads == 1 / MetricSystem.Pico)
    assert(x.toNanofarads == 1 / MetricSystem.Nano)
    assert(x.toMicrofarads == 1 / MetricSystem.Micro)
    assert(x.toMillifarads == 1 / MetricSystem.Milli)
    assert(x.toKilofarads == 1 / MetricSystem.Kilo)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Farads(1).toString(Farads) == "1.0 F")
    assert(Picofarads(1).toString(Picofarads) == "1.0 pF")
    assert(Nanofarads(1).toString(Nanofarads) == "1.0 nF")
    assert(Microfarads(1).toString(Microfarads) == "1.0 μF")
    assert(Millifarads(1).toString(Millifarads) == "1.0 mF")
    assert(Kilofarads(1).toString(Kilofarads) == "1.0 kF")
  }

  it should "return ElectricalCharge when multiplied by ElectricalPotential" in {
    assert(Farads(1) * Volts(1) == Coulombs(1))
  }

  behavior of "CapacitanceConversions"

  it should "provide aliases for single unit values" in {
    import CapacitanceConversions._

    assert(farad == Farads(1))
    assert(picofarad == Picofarads(1))
    assert(nanofarad == Nanofarads(1))
    assert(microfarad == Microfarads(1))
    assert(millifarad == Millifarads(1))
    assert(kilofarad == Kilofarads(1))
  }

  it should "provide implicit conversion from Double" in {
    import CapacitanceConversions._

    val d = 10d
    assert(d.farads == Farads(d))
    assert(d.picofarads == Picofarads(d))
    assert(d.nanofarads == Nanofarads(d))
    assert(d.microfarads == Microfarads(d))
    assert(d.millifarads == Millifarads(d))
    assert(d.kilofarads == Kilofarads(d))
  }
}
