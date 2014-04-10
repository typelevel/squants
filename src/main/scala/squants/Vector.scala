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
 * EXPERIMENTAL
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
  def dotProduct(that: Vector[Double]): A
  def *(that: Vector[Double]) = dotProduct(that)

  /**
   * Create the Cross Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def crossProduct(that: Vector[Double]): Vector[A]
  def #* = crossProduct _
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
case class QuantityVector[A <: Quantity[A]](coordinates: A*) extends Vector[A] {
  def valueUnit = coordinates(0).valueUnit
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
  def divide(that: A): Vector[Double] =
    DoubleVector(coordinates.toTraversable.map(v ⇒ v / that).toSeq: _*)

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
   * Strategy #1 Prototype for implementation that support dimensional conversion
   *
   * Pros - requires no implicit classes of extended case classes,
   *        it will be much easier to implement divide, dotProduct and crossProduct
   * Cons - requires implicit conversion to be in scope
   */
  def times[B <: Quantity[B], C <: Quantity[C]](that: B)(implicit convert: (A, B) ⇒ C): QuantityVector[C] =
    QuantityVector(coordinates.toTraversable.map(v ⇒ convert(v, that)).toSeq: _*)
  def *[B <: Quantity[B], C <: Quantity[C]](that: B)(implicit convert: (A, B) ⇒ C) = times(that)
}

/**
 * Strategy #2 Prototype for implementations that support dimensional conversions
 *
 * Add implicit conversions to each Quantity's Conversions object
 *
 * Pros - can be imported automatically with other conversions
 * Cons - requires an additional implicit conversion class for every type
 */
object QuantityVectorConversion {

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
 * Strategy #3 Prototype for implementations that support dimensional conversions
 *
 * Implement a class representing the specific Quantity Vector
 *
 * Pros - requires no implicits
 * Cons - requires a new class for every quantity
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
