/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * Root trait for representing Vectors
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @tparam A Type for the Vector's coordinate values
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
  /** plus */
  def + = plus _

  /**
   * Subtract two Vectors
   * @param that Vector[A]
   * @return
   */
  def minus(that: Vector[A]): Vector[A]
  /** minus */
  def - = minus _

  /**
   * Scale a Vector
   * @param that Double
   * @return
   */
  def times(that: Double): Vector[A]
  /** times */
  def * = times _

  /**
   * Reduce a Vector
   *
   * @param that Double
   * @return
   */
  def divide(that: Double): Vector[A]
  /** divide */
  def /(that: Double) = divide(that)

  /**
   * Create the Dot Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def dotProduct(that: Vector[Double]): A
  /** dotProduct */
  def *(that: Vector[Double]) = dotProduct(that)

  /**
   * Create the Cross Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def crossProduct(that: Vector[Double]): Vector[A]
  /** crossProduct */
  def #* = crossProduct _
}

/**
 * Double (Real Number) Vector
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param coordinates Double*
 */
case class DoubleVector(coordinates: Double*) extends Vector[Double] {
  def magnitude: Double = math.sqrt(coordinates.toTraversable.map(v ⇒ v * v).sum)
  def normalize: Vector[Double] = this.divide(magnitude)
  def plus(that: Vector[Double]): Vector[Double] =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)
  def minus(that: Vector[Double]): Vector[Double] =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)
  def times(that: Double): Vector[Double] = DoubleVector(coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  def times[B](that: B)(implicit num: Numeric[B]): Vector[Double] =
    DoubleVector(coordinates.toTraversable.map(v ⇒ v * num.toDouble(that)).toSeq: _*)

  /**
   * Scale and Squantify this Vector.  Returns a QuantityVector[A]
   *
   * val velVector: QuantityVector[Velocity] = DoubleVector(3.0, 4.0, 5.0) * MetersPerSecond(10.22)
   *
   * @param that Quantity[A]
   * @tparam A Quantity Type
   * @return
   */
  def times[A <: Quantity[A]](that: A) = QuantityVector(coordinates.toTraversable.map(v ⇒ that * v).toSeq: _*)

  def divide(that: Double): Vector[Double] = DoubleVector(coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)
  def divide[B](that: B)(implicit num: Fractional[B]): Vector[Double] =
    DoubleVector(coordinates.toTraversable.map(v ⇒ v * num.toDouble(that)).toSeq: _*)

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
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param coordinates Variable list of A
 * @tparam A QuantityType
 */
case class QuantityVector[A <: Quantity[A]](coordinates: A*) extends Vector[A] {
  def valueUnit = coordinates(0).unit
  def magnitude: A = valueUnit(math.sqrt(coordinates.toTraversable.map(v ⇒ v.to(valueUnit) * v.to(valueUnit)).sum))
  def normalize: Vector[A] = this / magnitude.to(valueUnit)

  /**
   * Creates the Unit Vector which corresponds to this vector using the given unit
   * @return
   */
  def normalize(unit: UnitOfMeasure[A]): Vector[A] = this / magnitude.to(unit)

  def plus(that: Vector[A]): Vector[A] =
    QuantityVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), valueUnit(0)).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)
  def minus(that: Vector[A]): Vector[A] =
    QuantityVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), valueUnit(0)).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)
  def times(that: Double): Vector[A] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  def divide(that: Double): Vector[A] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)

  /**
   * Reduce and "de-unit" a Vector.  Returns a DoubleVector
   * @param that Quantity[A]
   * @return
   */
  def divide(that: A): DoubleVector =
    DoubleVector(coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)
  /** divide */
  def /(that: A) = divide(that)

  def dotProduct(that: Vector[Double]): A =
    valueUnit(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), 0d).toTraversable.map(v ⇒ v._1.to(valueUnit) * v._2).sum)

  def crossProduct(that: Vector[Double]): Vector[A] = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      QuantityVector(
        valueUnit(coordinates(1).to(valueUnit) * that.coordinates(2) -
          coordinates(2).to(valueUnit) * that.coordinates(1)),
        valueUnit(coordinates(2).to(valueUnit) * that.coordinates(0) -
          coordinates(0).to(valueUnit) * that.coordinates(2)),
        valueUnit(coordinates(0).to(valueUnit) * that.coordinates(1) -
          coordinates(1).to(valueUnit) * that.coordinates(0)))
    case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional Cross Product is not currently supported")
    case _      ⇒ throw new UnsupportedOperationException("Cross Product is not supported on vectors with an arbitrary number of dimensions")
  }

  /**
   * Returns a DoubleVector representing the quantity values in terms of the supplied unit
   * @param unit UnitOfMeasure[A]
   * @return
   */
  def to(unit: UnitOfMeasure[A]): DoubleVector = this / unit(1)

  /**
   * EXPERIMENTAL
   *
   * Strategy #1 Prototype for implementation that support dimensional conversion
   *
   * Implement abstract methods here that use implicit mapTo function to perform the
   * dimensional operation
   *
   * Pros - requires no implicit classes or extended case classes,
   *        it will be much easier to implement divide, dotProduct and crossProduct
   *
   * Cons - requires implicit conversion to be in scope
   */
  def times[B <: Quantity[B], C <: Quantity[C]](that: B)(implicit mapTo: (A, B) ⇒ C): QuantityVector[C] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ mapTo(v, that)).toSeq: _*)
  def *[B <: Quantity[B], C <: Quantity[C]](that: B)(implicit mapTo: (A, B) ⇒ C) = times(that)

  def divide[B <: Quantity[B], C <: Quantity[C]](that: B)(implicit mapTo: (A, B) ⇒ C): QuantityVector[C] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ mapTo(v, that)).toSeq: _*)
  def /[B <: Quantity[B], C <: Quantity[C]](that: B)(implicit mapTo: (A, B) ⇒ C) = divide(that)

  def dotProduct[B <: Quantity[B], C <: Quantity[C]](that: Vector[B])(implicit mapTo: (A, B) ⇒ C, num: Numeric[C]): C =
    coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), that.coordinates(0).unit(0)).toTraversable.map(v ⇒ mapTo(v._1, v._2)).sum
}

/**
 * EXPERIMENTAL
 *
 * Strategy #2 Prototype for implementations that support dimensional conversions
 *
 * Add implicit conversions to each Quantity's Conversions object that define the operations
 *
 * Pros - can be imported automatically with other conversions
 *
 * Cons - requires an additional implicit conversion class and import for every quantity type
 */
object QuantityVectorConversion {

  // NOTE the def name start1Times is used as to not conflict with Strategy #1 implementation
  // during this evaluation

  implicit class LengthVector(v: QuantityVector[Length]) {
    def strat1Times(that: Length): QuantityVector[Area] =
      QuantityVector(v.coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
    def strat1Times(that: Area): QuantityVector[Volume] =
      QuantityVector(v.coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  }

  implicit class AreaVector(v: QuantityVector[Area]) {
    def strat1Times(that: Length): QuantityVector[Volume] =
      QuantityVector(v.coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  }
}

/**
 * EXPERIMENTAL
 *
 * Strategy #3 Prototype for implementations that support dimensional conversions
 *
 * Implement a class representing the specific Quantity Vector that define the operations
 *
 * Pros - requires no implicits
 *
 * Cons - requires a new class for every quantity, clunkier to use
 *
 */
class LengthVector(lengths: Length*)
    extends QuantityVector[Length](lengths: _*) {

  def *(that: Length): AreaVector =
    new AreaVector(coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
  def *(that: Area): QuantityVector[Volume] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
}

class AreaVector(areas: Area*)
    extends QuantityVector[Area](areas: _*) {

  def *(that: Length): QuantityVector[Volume] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ v * that).toSeq: _*)
}
