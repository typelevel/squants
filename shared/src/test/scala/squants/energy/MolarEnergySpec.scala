package squants.energy

import squants.QuantityParseException
import squants.mass.Moles
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class MolarEnergySpec extends AnyFlatSpec with Matchers {

  behavior of "MolarEnergy and its Units of Measure"

  it should "create values using UOM factories" in {
    JoulesPerMole(1).toJoulesPerMole should be(1)
  }

  it should "create values from properly formatted Strings" in {
    MolarEnergy("10.22 J/mol").get should be(JoulesPerMole(10.22))
    MolarEnergy("10.22 zz").failed.get should be(QuantityParseException("Unable to parse MolarEnergy", "10.22 zz"))
    MolarEnergy("zz J/mol").failed.get should be(QuantityParseException("Unable to parse MolarEnergy", "zz J/mol"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = JoulesPerMole(10)
    x.toJoulesPerMole should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    JoulesPerMole(1).toString(JoulesPerMole) should be("1.0 J/mol")
  }

  it should "return Energy when multiplied by ChemicalAmount" in {
    JoulesPerMole(1) * Moles(1) should be(Joules(1))
  }

  behavior of "MolarEnergyConversions"

  it should "provide aliases for single unit values" in {
    import MolarEnergyConversions._

    joulePerMole should be(JoulesPerMole(1))
  }

  it should "provide implicit conversion from Double" in {
    import MolarEnergyConversions._

    val d = 10.22
    d.joulesPerMole should be(JoulesPerMole(d))
  }

  it should "provide Numeric support" in {
    import MolarEnergyConversions.MolarEnergyNumeric

    val rs = List(JoulesPerMole(100), JoulesPerMole(10))
    rs.sum should be(JoulesPerMole(110))
  }
}
