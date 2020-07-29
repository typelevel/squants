/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.space.SquareMeters
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MagneticFluxDensitySpec extends AnyFlatSpec with Matchers {

  behavior of "MagneticFluxDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    Teslas(1).toTeslas should be(1)
    Gauss(1).toGuass should be(1)
  }

  it should "create values from properly formatted Strings" in {
    MagneticFluxDensity("10.22 T").get should be(Teslas(10.22))
    MagneticFluxDensity("10.22 Gs").get should be(Gauss(10.22))
    MagneticFluxDensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse MagneticFluxDensity", "10.22 zz"))
    MagneticFluxDensity("zz T").failed.get should be(QuantityParseException("Unable to parse MagneticFluxDensity", "zz T"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Teslas(1)
    x.toTeslas should be(1)
    x.toGuass should be(10000)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Teslas(1).toString(Teslas) should be("1.0 T")
    Gauss(1).toString(Gauss) should be("1.0 Gs")
  }

  it should "return MagneticFlux when multiplied by Area" in {
    Teslas(1) * SquareMeters(1) should be(Webers(1))
  }

  behavior of "MagneticFluxDensityConversions"

  it should "provide aliases for single unit values" in {
    import MagneticFluxDensityConversions._

    tesla should be(Teslas(1))
    gauss should be(Gauss(1))
  }

  it should "provide implicit conversion from Double" in {
    import MagneticFluxDensityConversions._

    val d = 10d
    d.teslas should be(Teslas(d))
    d.gauss should be(Gauss(d))
  }

  it should "provide Numeric support" in {
    import MagneticFluxDensityConversions.MagneticFluxDensistyNumeric

    val mfds = List(Teslas(100), Teslas(10))
    mfds.sum should be(Teslas(110))
  }
}
