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
    assert(Teslas(1).toTeslas == 1)
    assert(Gauss(1).toGuass == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Teslas(1)
    assert(x.toTeslas == 1)
    assert(x.toGuass == 10000)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Teslas(1).toString(Teslas) == "1.0 T")
    assert(Gauss(1).toString(Gauss) == "1.0 Gs")
  }

  it should "return MagneticFlux when multiplied by Area" in {
    assert(Teslas(1) * SquareMeters(1) == Webers(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Teslas(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[MagneticFluxDensity](ser)
    assert(x == des)
  }

  behavior of "MagneticFluxDensityConversions"

  it should "provide aliases for single unit values" in {
    import MagneticFluxDensityConversions._

    assert(tesla == Teslas(1))
    assert(gauss == Gauss(1))
  }

  it should "provide implicit conversion from Double" in {
    import MagneticFluxDensityConversions._

    val d = 10d
    assert(d.teslas == Teslas(d))
    assert(d.gauss == Gauss(d))
  }
}
