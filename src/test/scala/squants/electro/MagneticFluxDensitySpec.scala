/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.space.SquareMeters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MagneticFluxDensitySpec extends FlatSpec with Matchers {

  behavior of "MagneticFluxDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    Teslas(1).toTeslas should be(1)
    Gauss(1).toGuass should be(1)
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

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Teslas(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[MagneticFluxDensity](ser)
    x should be(des)
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
