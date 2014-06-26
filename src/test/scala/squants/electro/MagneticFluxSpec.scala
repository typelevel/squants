/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.time.Seconds
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MagneticFluxSpec extends FlatSpec with Matchers {

  behavior of "MagneticFlux and its Units of Measure"

  it should "create values using UOM factories" in {
    Webers(1).toWebers should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Webers(1)
    x.toWebers should be(1.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Webers(1).toString(Webers) should be("1.0 Wb")
  }

  it should "return Time when divided by ElectricPotential" in {
    Webers(1) / Volts(1) should be(Seconds(1))
  }

  it should "return ElectricPotential when divided by Time" in {
    Webers(1) / Seconds(1) should be(Volts(1))
  }

  it should "return MagneticFluxDensity when divided by Area" in {
    Webers(1) / SquareMeters(1) should be(Teslas(1))
  }

  it should "return Inductance when divided by ElectricCurrent" in {
    Webers(1) / Amperes(1) should be(Henry(1))
  }

  behavior of "MagneticFluxConversions"

  it should "provide aliases for single unit values" in {
    import MagneticFluxConversions._

    weber should be(Webers(1))
  }

  it should "provide implicit conversion from Double" in {
    import MagneticFluxConversions._

    val d = 10d
    d.webers should be(Webers(d))
  }

  it should "provide Numeric support" in {
    import MagneticFluxConversions.MagneticFluxNumeric

    val mfs = List(Webers(100), Webers(10))
    mfs.sum should be(Webers(110))
  }
}
