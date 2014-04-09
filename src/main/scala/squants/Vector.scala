/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.motion.{ MetersPerSecondSquared, NewtonSeconds }

/**
 * Root trait for representing Vectors
 *
 * EXPERIMENTAL
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @tparam A Type for the vector's coordinate values
 */
trait Vector[A] {
  /**
   * The list values that makeup Vector's coordinates
   * @return
   */
  def coordinates: Seq[A]

  /**
   * The scalar value of the Vector
   * @return
   */
  def magnitude: A

  /**
   * Creates the Unit Vector which corresponds to this vector
   * @return
   */
  def normalize: Vector[A]

  /**
   * Add two Vectors
   * @param that Vector[A]
   * @return
   */
  def plus(that: Vector[A]): Vector[A]
  def + = plus _

  /**
   * Subtract two Vectors
   * @param that Vector[A]
   * @return
   */
  def minus(that: Vector[A]): Vector[A]
  def - = minus _

  /**
   * Scale a Vector
   * @param that Double
   * @return
   */
  def times(that: Double): Vector[A]
  def * = times _

  /**
   * Reduce a Vector
   *
   * @param that Double
   * @return
   */
  def divide(that: Double): Vector[A]
  def /(that: Double) = divide(that)

  /**
   * Reduce and "de-unit" a Vector.  Returns a DoubleVector
   * @param that Quantity[A]
   * @return
   */
  def divide(that: A): Vector[Double]
  def /(that: A) = divide(that)

  /**
   * Create the Dot Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def dotProduct(that: Vector[A]): A
  def *(that: Vector[A]) = dotProduct(that)

  /**
   * Create the Cross Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def crossProduct(that: Vector[A]): Vector[A]
}

/**
 * Double (Real Number) Vector
 *
 * EXPERIMENTAL
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param coordinates Double *
 */
case class DoubleVector(coordinates: Double*) extends Vector[Double] {
  def magnitude: Double = math.sqrt(coordinates.toTraversable.map(v ⇒ v * v).sum)
  def normalize: Vector[Double] = this.divide(magnitude)
  def plus(that: Vector[Double]): Vector[Double] =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)
  def minus(that: Vector[Double]): Vector[Double] =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)
  def times(that: Double): Vector[Double] = DoubleVector(coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  def divide(that: Double): Vector[Double] = DoubleVector(coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)

  def dotProduct(that: Vector[Double]) =
    coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 * v._2).sum

  def crossProduct(that: Vector[Double]) = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      DoubleVector(coordinates(1) * that.coordinates(2) - coordinates(2) * that.coordinates(1),
        coordinates(2) * that.coordinates(0) - coordinates(0) * that.coordinates(2),
        coordinates(0) * that.coordinates(1) - coordinates(1) * that.coordinates(0))
    case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional cross product is not currently supported")
    case _      ⇒ throw new UnsupportedOperationException("Cross product is not supported on vectors with an arbitrary number of dimensions")
  }
}

/**
 * Quantity Vector
 *
 * EXPERIMENTAL
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param coordinates Variable list of A
 * @tparam A QuantityType
 */
case class QuantityVector[A <: Quantity[A]](unit: UnitOfMeasure[A], coordinates: A*) extends Vector[A] {
  def magnitude: A = unit(math.sqrt(coordinates.toTraversable.map(v ⇒ v.to(unit) * v.to(unit)).sum))
  def normalize: Vector[A] = this / magnitude.to(unit)
  def plus(that: Vector[A]): Vector[A] =
    QuantityVector(unit, coordinates.toIterable.zipAll(that.coordinates.toIterable, unit(0), unit(0)).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)
  def minus(that: Vector[A]): Vector[A] =
    QuantityVector(unit, coordinates.toIterable.zipAll(that.coordinates.toIterable, unit(0), unit(0)).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)
  def times(that: Double): Vector[A] =
    QuantityVector(unit, coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  def divide(that: Double): Vector[A] =
    QuantityVector(unit, coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)
  def divide(that: A): Vector[Double] =
    DoubleVector(coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)

  def dotProduct(that: Vector[A]): A =
    unit(coordinates.toIterable.zipAll(that.coordinates.toIterable, unit(0), unit(0)).toTraversable.map(v ⇒ v._1.to(unit) * v._2.to(unit)).sum)

  def crossProduct(that: Vector[A]): Vector[A] = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      QuantityVector(unit,
        unit(coordinates(1).to(unit) * that.coordinates(2).to(unit) -
          coordinates(2).to(unit) * that.coordinates(1).to(unit)),
        unit(coordinates(2).to(unit) * that.coordinates(0).to(unit) -
          coordinates(0).to(unit) * that.coordinates(2).to(unit)),
        unit(coordinates(0).to(unit) * that.coordinates(1).to(unit) -
          coordinates(1).to(unit) * that.coordinates(0).to(unit)))
    case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional cross product is not currently supported")
    case _      ⇒ throw new UnsupportedOperationException("Cross product is not supported on vectors with an arbitrary number of dimensions")
  }
}

/**
 * Strategy #1 for implementations that support dimensional conversions
 *
 * Add implicit conversions for each Quantity type
 *
 */
object QuantityVectorConversion {

  implicit class VelocityVector(v: QuantityVector[Velocity]) {
    def *(that: Mass): QuantityVector[Momentum] =
      QuantityVector(NewtonSeconds, v.coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)

    def /(that: Time): QuantityVector[Acceleration] =
      QuantityVector(MetersPerSecondSquared, v.coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)

    def /(that: Acceleration): QuantityVector[Time] =
      QuantityVector(Seconds, v.coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)
  }
}

/**
 * Strategy #2 for implementations that support dimensional conversions
 *
 * Implement a class representing the specific Quantity Vector
 *
 */
//case class VelocityVector(override val unit: UnitOfMeasure[Velocity], velocities: Velocity*)
//    extends QuantityVector[Velocity](unit, velocities: _*) {
//
//  def *(that: Mass): QuantityVector[Momentum] =
//    QuantityVector(NewtonSeconds, velocities.toTraversable.map(v ⇒ v * that).toSeq: _*)
//
//  def /(that: Time): QuantityVector[Acceleration] =
//    QuantityVector(MetersPerSecondSquared, velocities.toTraversable.map(v ⇒ v / that).toSeq: _*)
//
//  def /(that: Acceleration): QuantityVector[Time] =
//    QuantityVector(Seconds, velocities.toTraversable.map(v ⇒ v / that).toSeq: _*)
//}
