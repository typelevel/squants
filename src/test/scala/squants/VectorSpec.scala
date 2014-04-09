/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }
import squants.space.Kilometers

/**
 * @author  garyKeorkunian
 * @since   0.3.0
 *
 */
class VectorSpec extends FlatSpec with Matchers {

  // TODO Expand tests

  behavior of "Vector"

  it should "create a Vector with expected values" in {
    val vector = QuantityVector(Kilometers, Kilometers(1), Kilometers(10), Kilometers(5))
    vector.coordinates(0) should be(Kilometers(1))
    vector.coordinates(1) should be(Kilometers(10))
    vector.coordinates(2) should be(Kilometers(5))
  }

  it should "equate to like Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    QuantityVector(Kilometers, x, y, z).equals(QuantityVector(Kilometers, x, y, z)) should be(right = true)
    QuantityVector(Kilometers, x, y, z) == QuantityVector(Kilometers, x, y, z) should be(right = true)
  }

  it should "not equate to dislike Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    QuantityVector(Kilometers, x, y, z).equals(QuantityVector(Kilometers, z, y, z)) should be(right = false)
    QuantityVector(Kilometers, x, y, z) != QuantityVector(Kilometers, z, y, z) should be(right = true)
  }

  it should "determine a length" in {
    val vector = QuantityVector(Meters, Meters(3), Meters(4), Meters(5))
    vector.magnitude should be(Meters(math.sqrt(3 * 3 + 4 * 4 + 5 * 5)))
  }

  it should "add two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    QuantityVector(Kilometers, x, y, z).plus(QuantityVector(Kilometers, a, a, a)) should be(QuantityVector(Kilometers, x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    QuantityVector(Kilometers, x, y, z).minus(QuantityVector(Kilometers, a, a, a)) should be(QuantityVector(Kilometers, x - a, y - a, z - a))
  }

  it should "rescale a Vector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val r = 5
    QuantityVector(Kilometers, x, y, z).times(r) should be(QuantityVector(Kilometers, x * r, y * r, z * r))
    QuantityVector(Kilometers, x, y, z) * r should be(QuantityVector(Kilometers, x * r, y * r, z * r))
    QuantityVector(Kilometers, x, y, z).divide(r) should be(QuantityVector(Kilometers, x / r, y / r, z / r))
    QuantityVector(Kilometers, x, y, z) / r should be(QuantityVector(Kilometers, x / r, y / r, z / r))
  }

  it should "dot product two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    val expRes = Kilometers(x.to(Kilometers) * a.to(Kilometers) + y.to(Kilometers) * a.to(Kilometers) + z.to(Kilometers) * a.to(Kilometers))
    QuantityVector(Kilometers, x, y, z).dotProduct(QuantityVector(Kilometers, a, a, a)) should be(expRes)
    QuantityVector(Kilometers, x, y, z) * QuantityVector(Kilometers, a, a, a) should be(expRes)
  }

  it should "cross product two Vectors with 3 coordinates each" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    val expRes = QuantityVector(Kilometers,
      Kilometers(y.to(Kilometers) * a.to(Kilometers) - z.to(Kilometers) * a.to(Kilometers)),
      Kilometers(z.to(Kilometers) * a.to(Kilometers) - x.to(Kilometers) * a.to(Kilometers)),
      Kilometers(x.to(Kilometers) * a.to(Kilometers) - y.to(Kilometers) * a.to(Kilometers)))
    QuantityVector(Kilometers, x, y, z).crossProduct(QuantityVector(Kilometers, a, a, a)) should be(expRes)
  }

  it should "cross product two Vectors with 7 coordinates each" is pending

  it should "normalize a Vector" in {
    val x = Kilometers(3)
    val y = Kilometers(3)
    val z = Kilometers(3)
    val normalized = QuantityVector(Kilometers, x, y, z).normalize
    normalized.magnitude should be(Kilometers(1.0))
    println(normalized)
  }
}
