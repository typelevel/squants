/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.space.{Degrees, Kilometers, SquareKilometers, SquareMeters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.3.0
 *
 */
class SVectorSpec extends AnyFlatSpec with Matchers {

  // TODO Expand tests

  behavior of "DoubleVector"

  it should "create a Vector with expected values from Cartesian coordinates" in {
    val vector = SVector(1, 10, 5)
    vector.coordinates(0) should be(1)
    vector.coordinates(1) should be(10)
    vector.coordinates(2) should be(5)
  }

  it should "create a Vector with expected values from Polar coordinates" in {
    val vector = SVector(10, Degrees(45))
    vector.magnitude should be(10)
    vector.angle() should be(Degrees(45))
  }

  it should "equate to like Vectors" in {
    val x = 1d
    val y = 10d
    val z = 5d
    SVector(x, y, z).equals(SVector(x, y, z)) should be(right = true)
    SVector(x, y, z) == SVector(x, y, z) should be(right = true)
  }

  it should "not equate to dislike Vectors" in {
    val x = 1d
    val y = 10d
    val z = 5d
    SVector(x, y, z).equals(SVector(z, y, z)) should be(right = false)
    SVector(x, y, z) != SVector(z, y, z) should be(right = true)
  }

  it should "calculate the magnitude" in {
    val vector = SVector(3, 4, 5)
    vector.magnitude should be(math.sqrt(3 * 3 + 4 * 4 + 5 * 5))
  }

  it should "calculate the polar angle" in {
    val vector = SVector(3, 4)
    vector.angle().toRadians should be(math.atan2(4, 3))
  }

  it should "calculate polar coordinates" in {
    val (r, theta) = SVector(3, 4).polar()
    r should be(5)
    theta.toRadians should be(math.atan2(4, 3))
  }

  it should "normalize a Vector" in {
    val x = 3d
    val y = 4d
    val z = 5d
    val normalized = SVector(x, y, z).normalize
    normalized.magnitude should be(1.0 +- 0.0000000000000001)
  }

  it should "map to another DoubleVector" in {
    val v = SVector(1, 2, 3)
    val squared = v.map[Double](Math.pow(_, 2))
    squared.equals(SVector(1, 4, 9)) should be(right = true)
  }

  it should "map to QuantityVector" in {
    val v = SVector(1, 2, 3)
    val lengthVector = v.map[Length](Meters.apply)
    lengthVector.equals(SVector(Meters(1), Meters(2), Meters(3))) should be(right = true)
  }

  it should "add two Vectors" in {
    val x = 1d
    val y = 2d
    val z = 3d
    val a = 5d
    SVector(x, y, z).plus(SVector(a, a, a)) should be(SVector(x + a, y + a, z + a))
    SVector(x, y, z) + SVector(a, a, a) should be(SVector(x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = 1d
    val y = 2d
    val z = 3d
    val a = 5d
    SVector(x, y, z).minus(SVector(a, a, a)) should be(SVector(x - a, y - a, z - a))
    SVector(x, y, z) - SVector(a, a, a) should be(SVector(x - a, y - a, z - a))
  }

  it should "rescale a Vector" in {
    val x = 1d
    val y = 2d
    val z = 3d
    val r = 5d
    SVector(x, y, z).times(r) should be(SVector(x * r, y * r, z * r))
    SVector(x, y, z) * r should be(SVector(x * r, y * r, z * r))
    SVector(x, y, z).divide(r) should be(SVector(x / r, y / r, z / r))
    SVector(x, y, z) / r should be(SVector(x / r, y / r, z / r))
  }

  it should "dot product with another DoubleVector" in {
    val x = 1d
    val y = 2d
    val z = 3d
    val a = 5d
    val expRes = x * a + y * a + z * a
    SVector(x, y, z).dotProduct(SVector(a, a, a)) should be(expRes)
    SVector(x, y, z) * SVector(a, a, a) should be(expRes)
  }

  it should "dot product with a QuantityVector" in {
    val dVector = SVector(1, 2, 3)
    val qVector = SVector(Meters(1), Meters(2), Meters(3))
    dVector dotProduct qVector should be(qVector dotProduct dVector)
  }

  it should "cross product with a DoubleVector (with 3 coordinates each)" in {
    val x = 1d
    val y = 2d
    val z = 3d
    val a = 5d
    val expRes = SVector(y * a - z * a, z * a - x * a, x * a - y * a)
    SVector(x, y, z).crossProduct(SVector(a, a, a)) should be(expRes)
    SVector(x, y, z) #* SVector(a, a, a) should be(expRes)

    val up = SVector(1, 2, 3)
    val left = SVector(3, 2, 1)
    val forward = up crossProduct left
    val back = left crossProduct up

    forward should be (DoubleVector(-4.0, 8.0, -4.0))
    back should be (DoubleVector(4.0, -8.0, 4.0))
  }

  it should "crossProduct with a QuantityVector" in {
    val dVector = SVector(1, 2, 3)
    val qVector = SVector(Meters(1), Meters(2), Meters(3))
    dVector crossProduct qVector should be(qVector crossProduct dVector)
  }

  it should "throw an exception on crossProduct two Vectors with 7 dimensions" in {
    val v1 = SVector(1, 2, 3, 4, 5, 6, 7)
    val v2 = SVector(1, 2, 3, 4, 5, 6, 7)
    intercept[UnsupportedOperationException] {
      v1 crossProduct v2
    }
  }

  it should "throw an exception on crossProduct of arbitrary size" in {
    val vector3 = SVector(1, 2, 3)
    val vector4 = SVector(1, 2, 3, 4)
    val vector7 = SVector(1, 2, 3, 5, 6, 7)

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
    val vector = SVector(Kilometers(1), Kilometers(10), Kilometers(5))
    vector.coordinates(0) should be(Kilometers(1))
    vector.coordinates(1) should be(Kilometers(10))
    vector.coordinates(2) should be(Kilometers(5))
  }

  it should "create a Vector with expected values from Polar coordinates" in {
    val vector = SVector(Kilometers(10), Degrees(45))
    vector.magnitude should be(Kilometers(10))
    vector.angle() should be(Degrees(45))
  }

  it should "equate to like Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    SVector(x, y, z).equals(SVector(x, y, z)) should be(right = true)
    SVector(x, y, z) == SVector(x, y, z) should be(right = true)
  }

  it should "not equate to dislike Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    SVector(x, y, z).equals(SVector(z, y, z)) should be(right = false)
    SVector(x, y, z) != SVector(z, y, z) should be(right = true)
  }

  it should "determine a magnitude" in {
    val vector = SVector(Kilometers(.003), Kilometers(.004), Kilometers(.005))
    vector.magnitude should be(Meters(math.sqrt(3 * 3 + 4 * 4 + 5 * 5)))
  }

  it should "calculate the polar angle" in {
    val vector = SVector(Meters(3), Meters(4))
    vector.angle().toRadians should be(math.atan2(4, 3))
  }

  it should "calculate polar coordinates" in {
    val (r, theta) = SVector(Meters(3), Meters(4)).polar()
    r should be(Meters(5))
    theta.toRadians should be(math.atan2(4, 3))
  }

  it should "normalize a Vector" in {
    implicit val tol = Kilometers(1e-15)
    val x = Kilometers(3)
    val y = Kilometers(4)
    val z = Kilometers(5)
    val normalized = SVector(x, y, z).normalize(Kilometers)
    normalized.magnitude =~ Kilometers(1.0) should be(right = true)
  }

  it should "map to a DoubleVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = SVector(x, y, z)
    quantityVector.map[Double](_.to(Kilometers)).equals(SVector(1, 2, 3)) should be(right = true)
    quantityVector.to(Kilometers).equals(SVector(1, 2, 3)) should be(right = true)
    quantityVector.map[Double](_.to(Meters)).equals(SVector(1000, 2000, 3000)) should be(right = true)
  }

  it should "map to a QuantityVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = SVector(x, y, z)
    quantityVector.map[Area](l ⇒ l * l).equals(SVector(SquareKilometers(1), SquareKilometers(4), SquareKilometers(9))) should be(right = true)
    quantityVector.map[Length](_ * 2).equals(SVector(Kilometers(2), Kilometers(4), Kilometers(6))) should be(right = true)
  }

  it should "add two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    SVector(x, y, z).plus(SVector(a, a, a)) should be(SVector(x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    SVector(x, y, z).minus(SVector(a, a, a)) should be(SVector(x - a, y - a, z - a))
  }

  it should "rescale a Vector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val r = 5d
    SVector(x, y, z).times(r) should be(SVector(x * r, y * r, z * r))
    SVector(x, y, z) * r should be(SVector(x * r, y * r, z * r))
    SVector(x, y, z).divide(r) should be(SVector(x / r, y / r, z / r))
    SVector(x, y, z) / r should be(SVector(x / r, y / r, z / r))
  }

  it should "times and divide with another Quantity" in {
    val x = SquareMeters(1)
    val y = SquareMeters(2)
    val z = SquareMeters(3)
    val r = Meters(5)
    val rs = SquareMeters(.5)
    SVector(x, y, z).times(_ * r) should be(SVector[Volume](x * r, y * r, z * r))
    SVector(x, y, z).divide(_ / r) should be(SVector[Length](x / r, y / r, z / r))

    SVector(x, y, z).divide(rs) should be(SVector(x / rs, y / rs, z / rs))
    SVector(x, y, z) / rs should be(SVector(x / rs, y / rs, z / rs))
  }

  it should "dot product with a DoubleVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = 5d
    val expRes = x * a + y * a + z * a
    SVector(x, y, z).dotProduct(SVector(a, a, a)) should be(expRes)
    SVector(x, y, z) * SVector(a, a, a) should be(expRes)
  }

  it should "dot product with another QuantityVector" in {
    import space.AreaConversions._
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val expRes = x * x + y * y + z * z
    SVector(x, y, z).dotProduct[Length, Area](SVector(x, y, z), _ * _) should be(expRes)
  }

  it should "cross product with DoubleVector (3 coordinates each)" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = 5d
    val expRes = SVector(
      Kilometers(y.to(Kilometers) * a - z.to(Kilometers) * a),
      Kilometers(z.to(Kilometers) * a - x.to(Kilometers) * a),
      Kilometers(x.to(Kilometers) * a - y.to(Kilometers) * a))
    SVector(x, y, z).crossProduct(SVector(a, a, a)) should be(expRes)
    SVector(x, y, z) #* SVector(a, a, a) should be(expRes)

    val up = SVector(Kilometers(1), Kilometers(2), Kilometers(3))
    val left = SVector(3, 2, 1)
    val forward = up crossProduct left
    val back = left crossProduct up

    forward should be (QuantityVector(Kilometers(-4), Kilometers(8), Kilometers(-4)))
    back should be (QuantityVector(Kilometers(-4), Kilometers(8), Kilometers(-4)))
  }

  it should "cross product with another QuantityVector (3 coordinates each)" in {
    import space.AreaConversions._
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = Kilometers(5)
    val expRes = SVector(
      y * a - z * a,
      z * a - x * a,
      x * a - y * a)
    SVector(x, y, z).crossProduct[Length, Area](SVector(a, a, a), _ * _) should be(expRes)

    val up = SVector(Kilometers(1), Kilometers(2), Kilometers(3))
    val left = SVector(3, 2, 1)
    val forward = up crossProduct left
    val back = left crossProduct up

    forward should be (QuantityVector(Kilometers(-4), Kilometers(8), Kilometers(-4)))
    back should be (QuantityVector(Kilometers(-4), Kilometers(8), Kilometers(-4)))
  }

  it should "throw an exception on cross product two Vectors with 7 coordinates each" in {
    import scala.language.implicitConversions
    implicit def nToQ(d: Int) = Kilometers(d)
    val v1 = SVector[Length](1, 2, 3, 5, 6, 7)
    val v2 = SVector(1, 2, 3, 5, 6, 7)
    intercept[UnsupportedOperationException] {
      v1 crossProduct v2
    }
  }

  it should "throw an exception on crossProduct of arbitrary size" in {
    import scala.language.implicitConversions
    implicit def nToQ(d: Int) = Kilometers(d)
    val qv3 = SVector[Length](1, 2, 3)
    val qv4 = SVector[Length](1, 2, 3, 4)
    val qv7 = SVector[Length](1, 2, 3, 5, 6, 7)
    val dv3 = SVector(1, 2, 3)
    val dv4 = SVector(1, 2, 3, 4)
    val dv7 = SVector(1, 2, 3, 5, 6, 7)

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
    val quantityVector = SVector(x, y, z)
    quantityVector.to(Kilometers).equals(SVector(1, 2, 3)) should be(right = true)
    quantityVector.to(Meters).equals(SVector(1000, 2000, 3000)) should be(right = true)
  }

  it should "convert to a QuantityVector of the same dimension in the supplied Unit" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = SVector(x, y, z)
    quantityVector.in(Meters).equals(SVector(Meters(1000), Meters(2000), Meters(3000))) should be(right = true)
  }

}
