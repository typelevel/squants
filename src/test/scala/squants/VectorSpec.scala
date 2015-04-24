/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }
import squants.space.{ SquareKilometers, Kilometers }
import squants.motion.Newtons
import squants.energy.Joules

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

  it should "add two Vectors" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5
    DoubleVector(x, y, z).plus(DoubleVector(a, a, a)) should be(DoubleVector(x + a, y + a, z + a))
  }

  it should "subtract two Vectors" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5
    DoubleVector(x, y, z).minus(DoubleVector(a, a, a)) should be(DoubleVector(x - a, y - a, z - a))
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

  it should "dot product two Vectors" in {
    val x = 1
    val y = 2
    val z = 3
    val a = 5d
    val expRes = x * a + y * a + z * a
    DoubleVector(x, y, z).dotProduct(DoubleVector(a, a, a)) should be(expRes)
    DoubleVector(x, y, z) * DoubleVector(a, a, a) should be(expRes)
  }

  it should "cross product two Vectors with 3 coordinates each" in {
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
    // TODO Enhance these tests
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

  it should "dot product two Vectors" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val a = 5d
    val expRes = x * a + y * a + z * a
    QuantityVector(x, y, z).dotProduct(DoubleVector(a, a, a)) should be(expRes)
    QuantityVector(x, y, z) * DoubleVector(a, a, a) should be(expRes)
  }

  it should "cross product two Vectors with 3 coordinates each" in {
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

  it should "convert to a DoubleVector" in {
    val x = Kilometers(1)
    val y = Kilometers(2)
    val z = Kilometers(3)
    val quantityVector = QuantityVector(x, y, z)
    quantityVector.to(Kilometers).equals(DoubleVector(1, 2, 3)) should be(right = true)
    quantityVector.to(Meters).equals(DoubleVector(1000, 2000, 3000)) should be(right = true)
  }

  behavior of "SquantifiedDoubleVector"

  it should "multiply by a Vector[Quantity] and return the Dot Product as like Quantity" in {
    val dVector = DoubleVector(1, 2, 3)
    val qVector = QuantityVector(Meters(1), Meters(2), Meters(3))
    dVector dotProduct qVector should be(qVector dotProduct dVector)
  }

  it should "crossProduct by a Vector[Quantity] and return the Cross Product as like Vector[Quantity]" in {
    val dVector = DoubleVector(1, 2, 3)
    val qVector = QuantityVector(Meters(1), Meters(2), Meters(3))
    dVector crossProduct qVector should be(qVector crossProduct dVector)
  }

  behavior of "Dimensional Conversion Strategies"

  /**
   * Pros - clean usage once implicits are in scope
   * Cons - requires implicit conversions to be in scope
   *        too many implicits creates conflicts, resolved with explicit typing
   *
   * Note - These implicit defs could be moved to each type's conversion object
   */
  it should "Strategy #1 - Overloaded times operator that uses implicit dimensional conversion" in {
    implicit def lenTimesLen: (Length, Length) ⇒ Area = (a, b) ⇒ a * b
    implicit def lenTimesArea: (Length, Area) ⇒ Volume = (a, b) ⇒ a * b
    implicit def areaTimesLen: (Area, Length) ⇒ Volume = (a, b) ⇒ a * b
    implicit def areaDivideLen: (Area, Length) ⇒ Length = (a, b) ⇒ a / b
    implicit def volumeDivideArea: (Volume, Area) ⇒ Length = (a, b) ⇒ a / b
    implicit def volumeDivideLength: (Volume, Length) ⇒ Area = (a, b) ⇒ a / b

    val x = Kilometers(3)
    val y = Kilometers(4)
    val z = Kilometers(5)
    val l = Kilometers(1)
    val a = SquareKilometers(1)
    val vectorL = QuantityVector(x, y, z)
    val vectorA = vectorL times l
    vectorA should be(QuantityVector(SquareKilometers(3), SquareKilometers(4), SquareKilometers(5)))

    val vectorV1: QuantityVector[Volume] = vectorA * l // Con - explicit typing required to resolve (Area, Length) conflict
    val vectorV2 = vectorL * a
    vectorV1 should be(vectorV2)

    val vectorL1 = vectorV1 / (l * l)
    val vectorL2 = (vectorV2 / l).divide[Length, Length](l) // Con - explicit typing required
    vectorL1 should be(vectorL2)

    implicit def lenTimesForce: (Length, Force) ⇒ Energy = (l, f) ⇒ l * f
    import squants.energy.EnergyConversions._

    val displacement = QuantityVector(Meters(2), Meters(2))
    val force = QuantityVector(Newtons(10), Newtons(10))
    val work = displacement dotProduct force
    work should be(Joules(40))
  }

  /**
   * Pros - clean usage once implicits are in scope
   * Cons - requires implicits to be in scope
   */
  it should "Strategy #2 - Implicit conversion hosting supported operations" in {
    import QuantityVectorConversion._

    val x = Kilometers(3)
    val y = Kilometers(4)
    val z = Kilometers(5)
    val l = Kilometers(1)
    val a = SquareKilometers(1)
    val vectorL = QuantityVector(x, y, z)
    val vectorA = vectorL.strat1Times(l)
    vectorA should be(QuantityVector(SquareKilometers(3), SquareKilometers(4), SquareKilometers(5)))

    val vectorV1 = vectorA strat1Times l
    val vectorV2 = vectorL strat1Times a
    vectorV1 should be(vectorV2)
  }

  /**
   * Pros - Requires no implicits
   * Cons - Requires use of new on a whole new setup of classes
   */
  it should "Strategy #3 - different class for each QuantityVector" in {
    val x = Kilometers(3)
    val y = Kilometers(4)
    val z = Kilometers(5)
    val l = Kilometers(1)
    val a = SquareKilometers(1)
    val vectorL = new LengthVector(x, y, z)
    val vectorA = vectorL * l
    vectorA should be(QuantityVector(SquareKilometers(3), SquareKilometers(4), SquareKilometers(5)))

    val vectorV1 = vectorA * l
    val vectorV2 = vectorL * a
    vectorV1 should be(vectorV2)
  }
}
