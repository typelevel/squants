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
import squants.time.Seconds
import squants.energy.Watts
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricCurrentSpec extends FlatSpec with Matchers {

  behavior of "ElectricalCurrent and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Amperes(1).toAmperes == 1)
    assert(Milliamperes(1).toMilliamperes == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Amperes(1)
    assert(x.toAmperes == 1.0)
    assert(x.toMilliamperes == 1 / MetricSystem.Milli)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Amperes(1).toString(Amperes) == "1.0 A")
    assert(Milliamperes(1).toString(Milliamperes) == "1.0 mA")
  }

  it should "return ElectricalPotential when multiplied by ElectricalResistance" in {
    assert(Amperes(1) * Ohms(1) == Volts(1))
  }

  it should "return Energy when multiplied by ElectricalPotential" in {
    assert(Amperes(1) * Volts(1) == Watts(1))
  }

  it should "return ElectricalConductance when divided by ElectricalPotential" in {
    assert(Amperes(1) / Volts(1) == Siemens(1))
  }

  it should "return ElectricalCharge when multiplied by Time" in {
    assert(Amperes(1) * Seconds(1) == Coulombs(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Amperes(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricCurrent](ser)
    assert(x == des)
  }

  behavior of "ElectricalCurrentConversions"

  it should "provide aliases for single unit values" in {
    import ElectricCurrentConversions._

    assert(ampere == Amperes(1))
    assert(amp == Amperes(1))
    assert(milliampere == Milliamperes(1))
    assert(milliamp == Milliamperes(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricCurrentConversions._

    val d = 10d
    assert(d.amperes == Amperes(d))
    assert(d.amps == Amperes(d))
    assert(d.A == Amperes(d))
    assert(d.milliampers == Milliamperes(d))
    assert(d.milliamps == Milliamperes(d))
    assert(d.mA == Milliamperes(d))
  }
}
