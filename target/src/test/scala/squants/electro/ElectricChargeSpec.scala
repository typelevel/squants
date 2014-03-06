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
import squants.time.{ Seconds, Time }
import squants.energy.Joules

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricChargeSpec extends FlatSpec with Matchers {

  behavior of "ElectricalCharge and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Coulombs(1).toCoulombs == 1)
    assert(Picocoulombs(1).toPicocoulombs == 1)
    assert(Nanocoulombs(1).toNanocoulombs == 1)
    assert(Microcoulombs(1).toMicrocoulombs == 1)
    assert(Millicoulombs(1).toMillcoulombs == 1)
    assert(Abcoulombs(1).toAbcoulombs == 1)
    assert(AmpereHours(1).toAmpereHours == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Coulombs(1)
    assert(x.toCoulombs == 1.0)
    assert(x.toNanocoulombs == 1 / MetricSystem.Nano)
    assert(x.toMicrocoulombs == 1 / MetricSystem.Micro)
    assert(x.toMillcoulombs == 1 / MetricSystem.Milli)
    assert(x.toAbcoulombs == 1 / MetricSystem.Deca)
    assert(x.toAmpereHours == 1 / Time.SecondsPerHour)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Coulombs(1).toString(Coulombs) == "1.0 C")
    assert(Nanocoulombs(1).toString(Nanocoulombs) == "1.0 nC")
    assert(Microcoulombs(1).toString(Microcoulombs) == "1.0 µC")
    assert(Millicoulombs(1).toString(Millicoulombs) == "1.0 mC")
    assert(Abcoulombs(1).toString(Abcoulombs) == "1.0 aC")
    assert(AmpereHours(1).toString(AmpereHours) == "1.0 Ah")
  }

  it should "return Energy when multiplied by ElectricalPotential" in {
    assert(Coulombs(1) * Volts(1) == Joules(1))
  }

  it should "return ElectricalCurrent when divided by Time" in {
    assert(Coulombs(1) / Seconds(1) == Amperes(1))
  }

  it should "return Capacitance when divided by ElectricalPotential" in {
    assert(Coulombs(1) / Volts(1) == Farads(1))
  }

  it should "return ElectricalPotential when divided by Capacitance" in {
    assert(Coulombs(1) / Farads(1) == Volts(1))
  }

  it should "return Time when divided by ElectricalCurrent" in {
    assert(Coulombs(1) / Amperes(1) == Seconds(1))
  }

  behavior of "ElectricalChargeConversions"

  it should "provide aliases for single unit values" in {
    import ElectricChargeConversions._

    assert(coulomb == Coulombs(1))
    assert(picocoulomb == Picocoulombs(1))
    assert(nanocoulomb == Nanocoulombs(1))
    assert(microcoulomb == Microcoulombs(1))
    assert(millicoulomb == Millicoulombs(1))
    assert(abcoulomb == Abcoulombs(1))
    assert(ampereHour == AmpereHours(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricChargeConversions._

    val d = 10d
    assert(d.coulombs == Coulombs(d))
    assert(d.picocoulombs == Picocoulombs(d))
    assert(d.nanocoulombs == Nanocoulombs(d))
    assert(d.microcoulombs == Microcoulombs(d))
    assert(d.millicoulombs == Millicoulombs(d))
    assert(d.abcoulombs == Abcoulombs(d))
    assert(d.ampereHours == AmpereHours(d))
  }
}
