/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.{ QuantityParseException, MetricSystem }
import squants.space.Meters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricalResistanceSpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricalResistance and its Units of Measure"

  it should "create values using UOM factories" in {
    Ohms(1).toOhms should be(1)
    Nanohms(1).toNanohms should be(1)
    Microohms(1).toMicrohms should be(1)
    Milliohms(1).toMillohms should be(1)
    Kilohms(1).toKilohms should be(1)
    Megohms(1).toMegohms should be(1)
    Gigohms(1).toGigohms should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricalResistance("10.22 Ω").get should be(Ohms(10.22))
    ElectricalResistance("10.22 nΩ").get should be(Nanohms(10.22))
    ElectricalResistance("10.22 µΩ").get should be(Microohms(10.22))
    ElectricalResistance("10.22 mΩ").get should be(Milliohms(10.22))
    ElectricalResistance("10.22 kΩ").get should be(Kilohms(10.22))
    ElectricalResistance("10.22 MΩ").get should be(Megohms(10.22))
    ElectricalResistance("10.22 GΩ").get should be(Gigohms(10.22))
    ElectricalResistance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricalResistance", "10.22 zz"))
    ElectricalResistance("zz Ω").failed.get should be(QuantityParseException("Unable to parse ElectricalResistance", "zz Ω"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Ohms(1)
    x.toOhms should be(1.0)
    x.toNanohms should be(1 / MetricSystem.Nano)
    x.toMicrohms should be(1 / MetricSystem.Micro)
    x.toMillohms should be(1 / MetricSystem.Milli)
    x.toKilohms should be(1 / MetricSystem.Kilo)
    x.toMegohms should be(1 / MetricSystem.Mega)
    x.toGigohms should be(1 / MetricSystem.Giga)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Ohms(1).toString(Ohms) should be("1.0 Ω")
    Nanohms(1).toString(Nanohms) should be("1.0 nΩ")
    Microohms(1).toString(Microohms) should be("1.0 µΩ")
    Milliohms(1).toString(Milliohms) should be("1.0 mΩ")
    Kilohms(1).toString(Kilohms) should be("1.0 kΩ")
    Megohms(1).toString(Megohms) should be("1.0 MΩ")
    Gigohms(1).toString(Gigohms) should be("1.0 GΩ")
  }

  it should "return ElectricalPotential when multiplied by ElectricCurrent" in {
    Ohms(1) * Amperes(1) should be(Volts(1))
  }

  it should "return Resistivity when multiplied by Length" in {
    Ohms(1) * Meters(1) should be(OhmMeters(1))
  }

  it should "return the inverse value as Siemens" in {
    Ohms(5).inSiemens should be(Siemens(.2))
  }

  behavior of "ElectricalResistanceConversions"

  it should "provide aliases for single unit values" in {
    import ElectricalResistanceConversions._

    ohm should be(Ohms(1))
    nanohm should be(Nanohms(1))
    microohm should be(Microohms(1))
    milliohm should be(Milliohms(1))
    kilohm should be(Kilohms(1))
    megohm should be(Megohms(1))
    gigohm should be(Gigohms(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricalResistanceConversions._

    val d = 10d
    d.ohms should be(Ohms(d))
    d.nanohms should be(Nanohms(d))
    d.microohms should be(Microohms(d))
    d.milliohms should be(Milliohms(d))
    d.kilohms should be(Kilohms(d))
    d.megohms should be(Megohms(d))
    d.gigohms should be(Gigohms(d))
  }

  it should "provide Numeric support" in {
    import ElectricalResistanceConversions.ElectricalResistanceNumeric

    val ers = List(Ohms(100), Kilohms(1))
    ers.sum should be(Ohms(1100))
  }
}
