/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }
import squants.space.{SquareMeters, SquareKilometers, Kilometers}

/**
 * @author  garyKeorkunian
 * @since   0.3.0
 *
 */
class VectorSpec extends FlatSpec with Matchers {

  // TODO Expand tests

  behavior of "DoubleVector"

  it should "create a Vector with expected values" in {
    val vector = DoubleVector(1, 10, 5)
    vector.coordinates(0) should be(1)
    vector.coordinates(1) should be(10)
    vector.coordinates(2) should be(5)
  }

  it should "equate to like Vectors" in {
    val x = 1
    val y = 10
    val z = 5
    DoubleVector(x, y, z).equals(DoubleVector(x, y, z)) should be(right = true)
    DoubleVector(x, y, z) == DoubleVector(x, y, z) should be(right = true)
  }

  it should "not equate to dislike Vectors" in {
    val x = 1
    val y = 10
    val z = 5
    DoubleVector(x, y, z).equals(DoubleVector(z, y, z)) should be(right = false)
    DoubleVector(x, y, z) != DoubleVector(z, y, z) should be(right = true)
  }

  it should "determine a magnitude" in {
    val vector = DoubleVector(3, 4, 5)
    vector.magnitude should be(math.sqrt(3 * 3 + 4 * 4 + 5 * 5))
  }

  it should "normalize a Vector" in {
    val x = 3
    val y = 4
    val z = 5
    val normalized = DoubleVector(x, y, z).normalize
    normalized.magnitude should be(1.0 +- 0.0000000000000001)
  }

  it should "map to another DoubleVector" in {
    val v = DoubleVector(1, 2, 3)
    val squared = v.map[Double](Math.pow(_, 2))
    squared.equals(DoubleVector(1, 4, 9)) should be(right = true)
  }

  it should "map to QuantityVector" in {
    val v = DoubleVector(1, 2, 3)
    val lengthVector = v.map[Length](Meters.apply)
    lengthVector.equals(QuantityVector[Length](Meters(1), Meters(2), Meters(3))) should be(right = true)
  }

  it should "add two Vectors" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5
    DoubleVector(x, y, z).plus(DoubleVector(a, a, a)) should be(DoubleVector(x + a, y + a, z + a))
    DoubleVector(x, y, z) + DoubleVector(a, a, a) should be(DoubleVector(x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5
    DoubleVector(x, y, z).minus(DoubleVector(a, a, a)) should be(DoubleVector(x - a, y - a, z - a))
    DoubleVector(x, y, z) - DoubleVector(a, a, a) should be(DoubleVector(x - a, y - a, z - a))
  }

  it should "rescale a Vector" in {
    val x = 1
    val y = 2
    val z = 3
    val r = 5d
    DoubleVector(x, y, z).times(r) should be(DoubleVector(x * r, y * r, z * r))
    DoubleVector(x, y, z) * r should be(DoubleVector(x * r, y * r, z * r))
    DoubleVector(x, y, z).divide(r) should be(DoubleVector(x / r, y / r, z / r))
    DoubleVector(x, y, z) / r should be(DoubleVector(x / r, y / r, z / r))
  }

  it should "dot product with another DoubleVector" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5d
    val expRes = x * a + y * a + z * a
    DoubleVector(x, y, z).dotProduct(DoubleVector(a, a, a)) should be(expRes)
    DoubleVector(x, y, z) * DoubleVector(a, a, a) should be(expRes)
  }

  it should "dot product with a QuantityVector" in {
    import squants.space.LengthConversions._
    val dVector = DoubleVector(1, 2, 3)
    val qVector = QuantityVector(Meters(1), Meters(2), Meters(3))
    dVector dotProduct qVector should be(qVector dotProduct dVector)
  }

  it should "cross product with a DoubleVector (with 3 coordinates each)" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5d
    val expRes = DoubleVector(y * a - z * a, z * a - x * a, x * a - y * a)
    DoubleVector(x, y, z).crossProduct(DoubleVector(a, a, a)) should be(expRes)
    DoubleVector(x, y, z) #* DoubleVector(a, a, a) should be(expRes)

    val up = DoubleVector(1, 2, 3)
    val left = DoubleVector(3, 2, 1)
    val forward = up crossProduct left
    val back = left crossProduct up
  }

  it should "crossProduct with a QuantityVector" in {
    val dVector = DoubleVector(1, 2, 3)
    val qVector = QuantityVector(Meters(1), Meters(2), Meters(3))
    dVector crossProduct qVector should be(qVector crossProduct dVector)
  }

  it should "throw an exception on crossProduct two Vectors with 7 dimensions" in {
    val v1 = DoubleVector(1, 2, 3, 4, 5, 6, 7)
    val v2 = DoubleVector(1, 2, 3, 4, 5, 6, 7)
    intercept[UnsupportedOperationException] {
      v1 crossProduct v2
    }
  }

  it should "throw an exception on crossProduct of arbitrary size" in {
    val vector3 = DoubleVector(1, 2, 3)
    val vector4 = DoubleVector(1, 2, 3, 4)
    val vector7 = DoubleVector(1, 2, 3, 5, 6, 7)

    intercept[UnsupportedOperationException] {
      vector3 crossProduct vector4
    }
    intercept[UnsupportedOperationException] {
      vector4 crossProduct vector3
    }

    intercept[UnsupportedOperationException] {
      vector4 crossProduct vector4
    }

    intercept[UnsupportedOperationException] {
      vector7 crossProduct vector4
    }
    intercept[UnsupportedOperationException] {
      vector4 crossProduct vector7
    }
  }

  behavior of "QuantityVector"

  it should "create a Vector with expected values" in {
    val vector = QuantityVector(Kilometers(1), Kilometers(10), Kilometers(5))
    vector.coordinates(0) should be(Kilometers(1))
    vector.coordinates(1) should be(Kilometers(10))
    vector.coordinates(2) should be(Kilometers(5))
  }

  it should "equate to like Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    QuantityVector(x, y, z).equals(QuantityVector(x, y, z)) should be(right = true)
    QuantityVector(x, y, z) == QuantityVector(x, y, z) should be(right = true)
  }

  it should "not equate to dislike Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    QuantityVector(x, y, z).equals(QuantityVector(z, y, z)) should be(right = false)
    QuantityVector(x, y, z) != QuantityVector(z, y, z) should be(right = true)
  }

  it should "determine a magnitude" in {
    val vector = QuantityVector(Kilometers(.003), Kilometers(.004), Kilometers(.005))
    vector.magnitude should be(Meters(math.sqrt(3 * 3 + 4 * 4 + 5 * 5)))
  }

  it should "normalize a Vector" in {
    implicit val tol = Kilometers(1e-15)
    val x = Kilometers(3)
    val y = Kilometers(4)
    val z = Kilometers(5)
    val normalized = QuantityVector(x, y, z).normalize(Kilometers)
    normalized.magnitude =~ Kilometers(1.0) should be(right = true)
  }

  it should "map to a DoubleVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = QuantityVector(x, y, z)
    quantityVector.map[Double](_.to(Kilometers)).equals(DoubleVector(1,2,3)) should be(right = true)
    quantityVector.to(Kilometers).equals(DoubleVector(1, 2, 3)) should be(right = true)
    quantityVector.map[Double](_.to(Meters)).equals(DoubleVector(1000, 2000, 3000)) should be(right = true)
  }

  it should "map to a QuantityVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = QuantityVector(x, y, z)
    quantityVector.map[Area](l => l * l).equals(QuantityVector[Area](SquareKilometers(1),SquareKilometers(4), SquareKilometers(9))) should be(right = true)
    quantityVector.map[Length](_ * 2).equals(QuantityVector[Length](Kilometers(2), Kilometers(4), Kilometers(6))) should be(right = true)
  }

  it should "add two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    QuantityVector(x, y, z).plus(QuantityVector(a, a, a)) should be(QuantityVector(x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    QuantityVector(x, y, z).minus(QuantityVector(a, a, a)) should be(QuantityVector(x - a, y - a, z - a))
  }

  it should "rescale a Vector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val r = 5
    QuantityVector(x, y, z).times(r) should be(QuantityVector(x * r, y * r, z * r))
    QuantityVector(x, y, z) * r should be(QuantityVector(x * r, y * r, z * r))
    QuantityVector(x, y, z).divide(r) should be(QuantityVector(x / r, y / r, z / r))
    QuantityVector(x, y, z) / r should be(QuantityVector(x / r, y / r, z / r))
  }

  it should "times and divide with another Quantity" in {
    val x = SquareMeters(1)
    val y = SquareMeters(2)
    val z = SquareMeters(3)
    val r = Meters(5)
    val rs = SquareMeters(.5)
    QuantityVector(x, y, z).times(_ * r) should be(QuantityVector[Volume](x * r, y * r, z * r))
    QuantityVector(x, y, z).divide(_ / r) should be(QuantityVector[Length](x / r, y / r, z / r))

    QuantityVector(x, y, z).divide(rs) should be(DoubleVector(x / rs, y / rs, z / rs))
    QuantityVector(x, y, z) / rs should be(DoubleVector(x / rs, y / rs, z / rs))
  }

  it should "dot product with a DoubleVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = 5d
    val expRes = x * a + y * a + z * a
    QuantityVector(x, y, z).dotProduct(DoubleVector(a, a, a)) should be(expRes)
    QuantityVector(x, y, z) * DoubleVector(a, a, a) should be(expRes)
  }

  it should "dot product with another QuantityVector" ignore {
    import space.AreaConversions._
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val expRes = x * x + y * y + z * z
    QuantityVector(x, y, z).dotProduct[Length, Area](QuantityVector(x, y, z), _ * _) should be(expRes)
  }

  it should "cross product with DoubleVector (3 coordinates each)" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = 5d
    val expRes = QuantityVector(
      Kilometers(y.to(Kilometers) * a - z.to(Kilometers) * a),
      Kilometers(z.to(Kilometers) * a - x.to(Kilometers) * a),
      Kilometers(x.to(Kilometers) * a - y.to(Kilometers) * a))
    QuantityVector(x, y, z).crossProduct(DoubleVector(a, a, a)) should be(expRes)
    QuantityVector(x, y, z) #* DoubleVector(a, a, a) should be(expRes)

    val up = QuantityVector(Kilometers(1), Kilometers(2), Kilometers(3))
    val left = DoubleVector(3, 2, 1)
    val forward = up crossProduct left
    val back = left crossProduct up
    // TODO Enhance these tests
  }

  it should "cross product with another QuantityVectory (3 coordinates each)" ignore {
    import space.AreaConversions._
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    val expRes = QuantityVector(
      y * a - z * a,
      z * a - x * a,
      x * a - y * a)
    QuantityVector(x, y, z).crossProduct[Length, Area](QuantityVector(a, a, a), _ * _) should be(expRes)

    val up = QuantityVector(Kilometers(1), Kilometers(2), Kilometers(3))
    val left = DoubleVector(3, 2, 1)
    val forward = up crossProduct left
    val back = left crossProduct up
  }

  it should "throw an exception on cross product two Vectors with 7 coordinates each" in {
    import scala.language.implicitConversions
    implicit def nToQ(d: Int) = Kilometers(d)
    val v1 = QuantityVector[Length](1, 2, 3, 5, 6, 7)
    val v2 = DoubleVector(1, 2, 3, 5, 6, 7)
    intercept[UnsupportedOperationException] {
      v1 crossProduct v2
    }
  }

  it should "throw an exception on crossProduct of arbitrary size" in {
    import scala.language.implicitConversions
    implicit def nToQ(d: Int) = Kilometers(d)
    val qv3 = QuantityVector[Length](1, 2, 3)
    val qv4 = QuantityVector[Length](1, 2, 3, 4)
    val qv7 = QuantityVector[Length](1, 2, 3, 5, 6, 7)
    val dv3 = DoubleVector(1, 2, 3)
    val dv4 = DoubleVector(1, 2, 3, 4)
    val dv7 = DoubleVector(1, 2, 3, 5, 6, 7)

    // No crossProduct 3D with other sized vectors
    intercept[UnsupportedOperationException] {
      qv3 crossProduct dv7
    }
    intercept[UnsupportedOperationException] {
      qv3 crossProduct dv4
    }
    intercept[UnsupportedOperationException] {
      dv7 crossProduct qv3
    }
    intercept[UnsupportedOperationException] {
      dv4 crossProduct qv3
    }

    // NO crossProduct 7D with other sized vectors
    intercept[UnsupportedOperationException] {
      qv7 crossProduct dv3
    }
    intercept[UnsupportedOperationException] {
      qv7 crossProduct dv4
    }
    intercept[UnsupportedOperationException] {
      dv3 crossProduct qv7
    }
    intercept[UnsupportedOperationException] {
      dv4 crossProduct qv7
    }

    // No crossProduct with other matching size vectors
    intercept[UnsupportedOperationException] {
      dv4 crossProduct qv4
    }
    intercept[UnsupportedOperationException] {
      qv4 crossProduct dv4
    }
  }

  it should "convert to a DoubleVector based on a Unit" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = QuantityVector(x, y, z)
    quantityVector.to(Kilometers).equals(DoubleVector(1, 2, 3)) should be(right = true)
    quantityVector.to(Meters).equals(DoubleVector(1000, 2000, 3000)) should be(right = true)
  }

  it should "convert to a QuantityVector of the same dimension in the supplied Unit" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = QuantityVector(x, y, z)
    quantityVector.in(Meters).equals(QuantityVector(Meters(1000), Meters(2000), Meters(3000))) should be(right = true)
  }

}
