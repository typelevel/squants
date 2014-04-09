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
    val vector = Vector(Kilometers(1), Kilometers(10), Kilometers(5))
    vector.x should be(Kilometers(1))
    vector.y should be(Kilometers(10))
    vector.z should be(Kilometers(5))
  }

  it should "equate to like Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    Vector(x, y, z).equals(Vector(x, y, z)) should be(right = true)
    Vector(x, y, z) == Vector(x, y, z) should be(right = true)
  }

  it should "not equate to dislike Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    Vector(x, y, z).equals(Vector(z, y, z)) should be(right = false)
    Vector(x, y, z) != Vector(z, y, z) should be(right = true)
  }

  it should "determine a length" in {
    implicit val create: Double ⇒ Length = value ⇒ Meters(value)
    val vector = Vector(Meters(3), Meters(4), Meters(5))
    vector.length should be(Meters(math.sqrt(3 * 3 + 4 * 4 + 5 * 5)))
  }

  it should "add two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    Vector(x, y, z).plus(Vector(a, a, a)) should be(Vector(x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    Vector(x, y, z).minus(Vector(a, a, a)) should be(Vector(x - a, y - a, z - a))
  }

  it should "rescale a Vector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val r = 5
    Vector(x, y, z).times(r) should be(Vector(x * r, y * r, z * r))
    Vector(x, y, z) * r should be(Vector(x * r, y * r, z * r))
    Vector(x, y, z).divide(r) should be(Vector(x / r, y / r, z / r))
    Vector(x, y, z) / r should be(Vector(x / r, y / r, z / r))
  }

  it should "dot product two Vectors" in {
    implicit val create: Double ⇒ Length = value ⇒ Meters(value)
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    Vector(x, y, z).dotProduct(Vector(a, a, a)) should be(Meters(x.value * a.value + y.value * a.value + z.value * a.value))
  }

  it should "cross product two Vectors" in {
    implicit val create: Double ⇒ Length = value ⇒ Meters(value)
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    Vector(x, y, z).crossProduct(Vector(a, a, a)) should be(
      Vector(Meters(y.value * a.value - z.value * a.value),
        Meters(z.value * a.value - x.value * a.value),
        Meters(x.value * a.value - y.value * a.value)))
  }

  it should "normalize a Vector" in {
    implicit val create: Double ⇒ Length = value ⇒ Meters(value)
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    Vector(x, y, z).normalize.length() should be(Meters(1.0))
  }
}
