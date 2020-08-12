/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.{MetricSystem, QuantityParseException}
import squants.space.Meters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class InductanceSpec extends AnyFlatSpec with Matchers {

  behavior of "Inductance and its Units of Measure"

  it should "create values using UOM factories" in {
    Henry(1).toHenry should be(1)
    Millihenry(1).toMillihenry should be(1)
    Microhenry(1).toMicrohenry should be(1)
    Nanohenry(1).toNanohenry should be(1)
    Picohenry(1).toPicohenry should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Inductance("10.22 H").get should be(Henry(10.22))
    Inductance("10.22 mH").get should be(Millihenry(10.22))
    Inductance("10.22 μH").get should be(Microhenry(10.22))
    Inductance("10.22 nH").get should be(Nanohenry(10.22))
    Inductance("10.22 pH").get should be(Picohenry(10.22))
    Inductance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Inductance", "10.22 zz"))
    Inductance("zz H").failed.get should be(QuantityParseException("Unable to parse Inductance", "zz H"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Henry(1)
    x.toHenry should be(1.0)
    x.toMillihenry should be(1 / MetricSystem.Milli)
    x.toMicrohenry should be(1 / MetricSystem.Micro)
    x.toNanohenry should be(1 / MetricSystem.Nano)
    x.toPicohenry should be(1 / MetricSystem.Pico)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Henry(1).toString(Henry) should be("1.0 H")
    Millihenry(1).toString(Millihenry) should be("1.0 mH")
    Microhenry(1).toString(Microhenry) should be("1.0 μH")
    Nanohenry(1).toString(Nanohenry) should be("1.0 nH")
    Picohenry(1).toString(Picohenry) should be("1.0 pH")
  }

  it should "return MagneticFlux when multiplied by ElectricCurrent" in {
    Henry(1) * Amperes(1) should be(Webers(1))
  }

  it should "return Permeability when divided by Length" in {
    Henry(1) / Meters(1) should be(HenriesPerMeter(1))
  }

  behavior of "InductanceConversions"

  it should "provide aliases for single unit values" in {
    import InductanceConversions._

    henry should be(Henry(1))
    millihenry should be(Millihenry(1))
    microhenry should be(Microhenry(1))
    nanohenry should be(Nanohenry(1))
    picohenry should be(Picohenry(1))
  }

  it should "provide implicit conversion from Double" in {
    import InductanceConversions._

    val d = 10d
    d.henry should be(Henry(d))
    d.millihenry should be(Millihenry(d))
    d.microhenry should be(Microhenry(d))
    d.nanohenry should be(Nanohenry(d))
    d.picohenry should be(Picohenry(d))
  }

  it should "provide Numeric support" in {
    import InductanceConversions.InductanceNumeric

    val is = List(Henry(100), Henry(10))
    is.sum should be(Henry(110))
  }
}
