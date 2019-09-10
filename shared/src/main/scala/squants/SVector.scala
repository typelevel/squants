/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.space.AngleUnit

/**
 * Root trait for representing Vectors
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @tparam A Type for the Vector's coordinate values
 */
trait SVector[A] {

  type SVectorType <: SVector[A]

  /**
   * The list of values that makeup the Vector's Cartesian coordinates
   * @return
   */
  def coordinates: Seq[A]

  /**
   * The scalar value of the Vector
   * @return
   */
  def magnitude: A

  /**
   * The angle between the two Cartesian coordinates at the supplied indices
   * @param coordinateX index of the abscissa coordinate (defaults to 0)
   * @param coordinateY index of the ordinate coordinate (defaults to 1)
   * @param unit unit for the angle (theta) component (defaults to Radians)
   * @return Angle
   */
  def angle(coordinateX: Int = 0, coordinateY: Int = 1, unit: AngleUnit = Radians): Angle

  /**
   * The polar coordinates (r, theta) of the two Cartesian coordinates at the supplied indices
   * @param coordinateX index of the abscissa coordinate (defaults to 0)
   * @param coordinateY index of the ordinate coordinate (defaults to 1)
   * @param unit unit for the angle (theta) component (defaults to Radians)
   * @return (A, Angle)
   */
  def polar(coordinateX: Int = 0, coordinateY: Int = 1, unit: AngleUnit = Radians): (A, Angle) = (magnitude, angle(coordinateX, coordinateY, unit))

  /**
   * Creates the Unit Vector which corresponds to this vector
   * @return
   */
  def normalize: SVectorType

  /**
   * Add two Vectors
   * @param that Vector[A]
   * @return
   */
  def plus(that: SVectorType): SVectorType
  def + = plus _

  /**
   * Subtract two Vectors
   * @param that Vector[A]
   * @return
   */
  def minus(that: SVectorType): SVectorType
  def - = minus _

  /**
   * Scale a Vector
   * @param that Double
   * @return
   */
  def times(that: Double): SVectorType
  def * = times _

  /**
   * Reduce a Vector
   *
   * @param that Double
   * @return
   */
  def divide(that: Double): SVectorType
  def /(that: Double) = divide(that)

  /**
   * Create the Dot Product of two Vectors
   * @param that Double
   * @return
   */
  def dotProduct(that: DoubleVector): A
  def *(that: DoubleVector) = dotProduct(that)

  /**
   * Create the Cross Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def crossProduct(that: DoubleVector): SVector[A]
  def #* = crossProduct _

}

object SVector {

  def apply(coordinates: Double*): DoubleVector = DoubleVector(coordinates: _*)
  def apply[A <: Quantity[A]](coordinates: A*): QuantityVector[A] = QuantityVector[A](coordinates: _*)

  /**
   * Create a 2-dimensional DoubleVector from Polar Coordinates
   * @param radius the magnitude of the vector
   * @param theta the angle from the polar axis
   * @return
   */
  def apply(radius: Double, theta: Angle): DoubleVector = apply(radius * theta.cos, radius * theta.sin)

  /**
   * Create a 2-dimensional QuantityVector[A] from Polar Coordinates
   * @param radius the magnitude of the vector
   * @param theta the angle from the polar axis
   * @tparam A Quantity type
   * @return
   */
  def apply[A <: Quantity[A]](radius: A, theta: Angle): QuantityVector[A] = apply(radius * theta.cos, radius * theta.sin)
}

/**
 * Double (Real Number) Vector
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param coordinates Double*
 */
case class DoubleVector(coordinates: Double*) extends SVector[Double] {

  type SVectorType = DoubleVector

  def magnitude: Double = math.sqrt(coordinates.toTraversable.map(v ⇒ v * v).sum)
  def angle(coordinateX: Int = 0, coordinateY: Int = 1, unit: AngleUnit = Radians): Angle =
    Radians(math.atan2(coordinates(coordinateY), coordinates(coordinateX))) in unit

  def normalize: SVectorType = this.divide(magnitude)

  /**
   * Creates a DoubleVector by mapping over each coordinate with the supplied function
   * @param f A => Double map function
   * @return
   */
  def map[A <: Double](f: Double ⇒ Double): DoubleVector = DoubleVector(coordinates.toTraversable.map(f).toSeq: _*)

  /**
   * Creates a QuantityVector by mapping over each coordinate with the supplied function
   * @param f Double => B
   * @tparam A <: Quantity
   * @return
   */
  def map[A <: Quantity[A]](f: Double ⇒ A): QuantityVector[A] = QuantityVector(coordinates.toTraversable.map(f).toSeq: _*)

  def plus(that: SVectorType): SVectorType =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)

  def minus(that: SVectorType): SVectorType =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)

  def times(that: Double): SVectorType = map(_ * that)
  def times[A <: Quantity[A]](that: A): QuantityVector[A] = map(_ * that)

  def divide(that: Double): SVectorType = map(_ / that)

  def dotProduct(that: SVectorType): Double =
    coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 * v._2).sum

  def dotProduct[B <: Quantity[B]](that: QuantityVector[B]) = that dotProduct this

  def crossProduct(that: SVectorType) = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      DoubleVector(this.coordinates(1) * that.coordinates(2) - this.coordinates(2) * that.coordinates(1),
        coordinates(2) * that.coordinates(0) - this.coordinates(0) * that.coordinates(2),
        coordinates(0) * that.coordinates(1) - this.coordinates(1) * that.coordinates(0))
    case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional cross product is not currently supported")
    case _      ⇒ throw new UnsupportedOperationException("Cross product is not supported on vectors with an arbitrary number of dimensions")
  }

  def crossProduct[B <: Quantity[B]](that: QuantityVector[B]) = that crossProduct this

}

/**
 * Quantity Vector
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param coordinates Variable list of A
 * @tparam A QuantityType
 */
case class QuantityVector[A <: Quantity[A]](coordinates: A*) extends SVector[A] {
  type SVectorType = QuantityVector[A]

  def valueUnit = coordinates(0).unit
  def magnitude: A = valueUnit(math.sqrt(coordinates.toTraversable.map(v ⇒ v.to(valueUnit) * v.to(valueUnit)).sum))
  def angle(coordinateX: Int = 0, coordinateY: Int = 1, unit: AngleUnit = Radians): Angle =
    Radians(math.atan(coordinates(coordinateY) / coordinates(coordinateX))) in unit

  def normalize: SVectorType = this / magnitude.to(valueUnit)

  /**
   * Creates the Unit Vector which corresponds to this vector using the given unit
   * @return
   */
  def normalize(unit: UnitOfMeasure[A]): SVectorType = this / magnitude.to(unit)

  /**
   * Creates a DoubleVector by mapping over each coordinate with the supplied function
   * @param f A => Double map function
   * @return
   */
  def map[B <: Double](f: A ⇒ Double): DoubleVector = DoubleVector(coordinates.toTraversable.map(f).toSeq: _*)

  /**
   * Creates a QuantityVector by mapping over each coordinate with the supplied function
   * @param f A => B
   * @tparam B <: Quantity
   * @return
   */
  def map[B <: Quantity[B]](f: A ⇒ B): QuantityVector[B] = QuantityVector(coordinates.toTraversable.map(f).toSeq: _*)

  def plus(that: SVectorType): SVectorType =
    QuantityVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), valueUnit(0)).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)
  def minus(that: SVectorType): SVectorType =
    QuantityVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), valueUnit(0)).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)

  def times(that: Double): SVectorType = map(_ * that)
  def *(that: Double): SVectorType = times(that)

  def times[B <: Quantity[B], C <: Quantity[C]](quantTimes: A ⇒ C): QuantityVector[C] = map(quantTimes)

  def divide(that: Double): SVectorType = map(_ / that)

  def divide(that: A): DoubleVector = map(_ / that)
  def /(that: A) = divide(that)

  def divide[B <: Quantity[B], C <: Quantity[C]](quantDiv: A ⇒ C): QuantityVector[C] = map(quantDiv(_))

  def dotProduct(that: DoubleVector): A =
    valueUnit(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), 0d).toTraversable.map(v ⇒ v._1.to(valueUnit) * v._2).sum)

  def dotProduct[B <: Quantity[B], C <: Quantity[C]](that: SVector[B], quantTimes: (A, B) ⇒ C)(implicit num: Numeric[C]): C =
    coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), that.coordinates.head.unit(0)).toTraversable.map(v ⇒ quantTimes(v._1, v._2)).sum

  def crossProduct(that: DoubleVector): SVectorType = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      QuantityVector(
        (this.coordinates(1) * that.coordinates(2)) - (this.coordinates(2) * that.coordinates(1)),
        (this.coordinates(2) * that.coordinates(0)) - (this.coordinates(0) * that.coordinates(2)),
        (this.coordinates(0) * that.coordinates(1)) - (this.coordinates(1) * that.coordinates(0)))
    case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional Cross Product is not currently supported")
    case _      ⇒ throw new UnsupportedOperationException("Cross Product is not supported on vectors with an arbitrary number of dimensions")
  }

  def crossProduct[B <: Quantity[B], C <: Quantity[C]: Numeric](that: SVector[B], quantTimes: (A, B) ⇒ C): QuantityVector[C] = {
    (this.coordinates.length, that.coordinates.length) match {
      case (3, 3) ⇒
        QuantityVector(
          quantTimes(this.coordinates(1), that.coordinates(2)) - quantTimes(coordinates(2), that.coordinates(1)),
          quantTimes(this.coordinates(2), that.coordinates(0)) - quantTimes(coordinates(0), that.coordinates(2)),
          quantTimes(this.coordinates(0), that.coordinates(1)) - quantTimes(coordinates(1), that.coordinates(0)))
      case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional Cross Product is not currently supported")
      case _      ⇒ throw new UnsupportedOperationException("Cross Product is not supported on vectors with an arbitrary number of dimensions")
    }
  }

  /**
   * Returns a DoubleVector representing the quantity values in terms of the supplied unit
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def to(uom: UnitOfMeasure[A]): DoubleVector = this / uom(1)

  /**
   * Returns a QuantityVector with all coordinates set to the supplied unit
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def in(uom: UnitOfMeasure[A]): QuantityVector[A] = map[A](_.in(uom))

}
