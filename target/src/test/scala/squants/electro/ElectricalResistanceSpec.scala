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
import squants.space.Meters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricalResistanceSpec extends FlatSpec with Matchers {

  behavior of "ElectricalResistance and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Ohms(1).toOhms == 1)
    assert(Nanohms(1).toNanohms == 1)
    assert(Microohms(1).toMicrohms == 1)
    assert(Milliohms(1).toMillohms == 1)
    assert(Kilohms(1).toKilohms == 1)
    assert(Megohms(1).toMegohms == 1)
    assert(Gigohms(1).toGigohms == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Ohms(1)
    assert(x.toOhms == 1.0)
    assert(x.toNanohms == 1 / MetricSystem.Nano)
    assert(x.toMicrohms == 1 / MetricSystem.Micro)
    assert(x.toMillohms == 1 / MetricSystem.Milli)
    assert(x.toKilohms == 1 / MetricSystem.Kilo)
    assert(x.toMegohms == 1 / MetricSystem.Mega)
    assert(x.toGigohms == 1 / MetricSystem.Giga)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Ohms(1).toString(Ohms) == "1.0 Ω")
    assert(Nanohms(1).toString(Nanohms) == "1.0 nΩ")
    assert(Microohms(1).toString(Microohms) == "1.0 µΩ")
    assert(Milliohms(1).toString(Milliohms) == "1.0 mΩ")
    assert(Kilohms(1).toString(Kilohms) == "1.0 kΩ")
    assert(Megohms(1).toString(Megohms) == "1.0 MΩ")
    assert(Gigohms(1).toString(Gigohms) == "1.0 GΩ")
  }

  it should "return ElectricalPotential when multiplied by ElectricCurrent" in {
    assert(Ohms(1) * Amperes(1) == Volts(1))
  }

  it should "return Resistivity when multiplied by Length" in {
    assert(Ohms(1) * Meters(1) == OhmMeters(1))
  }

  it should "return the inverse value as Siemens" in {
    assert(Ohms(5).inSiemens == Siemens(.2))
  }

  behavior of "ElectricalResistanceConversions"

  it should "provide aliases for single unit values" in {
    import ElectricalResistanceConversions._

    assert(ohm == Ohms(1))
    assert(nanohm == Nanohms(1))
    assert(microohm == Microohms(1))
    assert(milliohm == Milliohms(1))
    assert(kilohm == Kilohms(1))
    assert(megohm == Megohms(1))
    assert(gigohm == Gigohms(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricalResistanceConversions._

    val d = 10d
    assert(d.ohms == Ohms(d))
    assert(d.nanohms == Nanohms(d))
    assert(d.microohms == Microohms(d))
    assert(d.milliohms == Milliohms(d))
    assert(d.kilohms == Kilohms(d))
    assert(d.megohms == Megohms(d))
    assert(d.gigohms == Gigohms(d))
  }
}
