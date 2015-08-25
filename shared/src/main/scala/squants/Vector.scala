/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
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

  type VectorType <: Vector[A]

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
  def normalize: VectorType

  /**
   * Add two Vectors
   * @param that Vector[A]
   * @return
   */
  def plus(that: VectorType): VectorType
  def + = plus _

  /**
   * Subtract two Vectors
   * @param that Vector[A]
   * @return
   */
  def minus(that: VectorType): VectorType
  def - = minus _

  /**
   * Scale a Vector
   * @param that Double
   * @return
   */
  def times(that: Double): VectorType
  def * = times _

  /**
   * Reduce a Vector
   *
   * @param that Double
   * @return
   */
  def divide(that: Double): VectorType
  def /(that: Double) = divide(that)

  /**
   * Create the Dot Product of two Vectors
   * @param that Double
   * @return
   */
  def dotProduct(that: DoubleVector): A
  def *(that:DoubleVector) = dotProduct(that)

  /**
   * Create the Cross Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def crossProduct(that: DoubleVector): Vector[A]
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

  type VectorType = DoubleVector

  def magnitude: Double = math.sqrt(coordinates.toTraversable.map(v ⇒ v * v).sum)
  def normalize: VectorType = this.divide(magnitude)

  /**
   * Creates a DoubleVector by mapping over each coordinate with the supplied function
   * @param f A => Double map function
   * @return
   */
  def map[A <: Double](f: Double => Double): DoubleVector = DoubleVector(coordinates.toTraversable.map(f).toSeq: _*)

  /**
   * Creates a QuantityVector by mapping over each coordinate with the supplied function
   * @param f Double => B
   * @tparam A <: Quantity
   * @return
   */
  def map[A <: Quantity[A]](f: Double => A): QuantityVector[A] = QuantityVector(coordinates.toTraversable.map(f).toSeq: _*)

  def plus(that: VectorType): VectorType =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)

  def minus(that: VectorType): VectorType =
    DoubleVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)

  def times(that: Double): VectorType = map(_ * that)
  def times[A <: Quantity[A]](that: A): QuantityVector[A] = map(_ * that)

  def divide(that: Double): VectorType = map(_ / that)

  def dotProduct(that: VectorType): Double =
    coordinates.toIterable.zipAll(that.coordinates.toIterable, 0d, 0d).toTraversable.map(v ⇒ v._1 * v._2).sum

  def dotProduct[B <: Quantity[B]](that: QuantityVector[B]) = that dotProduct this

  def crossProduct(that: VectorType) = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      DoubleVector(coordinates(1) * that.coordinates(2) - coordinates(2) * that.coordinates(1),
        coordinates(2) * that.coordinates(0) - coordinates(0) * that.coordinates(2),
        coordinates(0) * that.coordinates(1) - coordinates(1) * that.coordinates(0))
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
case class QuantityVector[A <: Quantity[A]](coordinates: A*) extends Vector[A] {
  type VectorType = QuantityVector[A]

  def valueUnit = coordinates(0).unit
  def magnitude: A = valueUnit(math.sqrt(coordinates.toTraversable.map(v ⇒ v.to(valueUnit) * v.to(valueUnit)).sum))
  def normalize:VectorType = this / magnitude.to(valueUnit)

  /**
   * Creates the Unit Vector which corresponds to this vector using the given unit
   * @return
   */
  def normalize(unit: UnitOfMeasure[A]):VectorType = this / magnitude.to(unit)

  /**
   * Creates a DoubleVector by mapping over each coordinate with the supplied function
   * @param f A => Double map function
   * @return
   */
  def map[B <: Double](f: A => Double): DoubleVector = DoubleVector(coordinates.toTraversable.map(f).toSeq: _*)

  /**
   * Creates a QuantityVector by mapping over each coordinate with the supplied function
   * @param f A => B
   * @tparam B <: Quantity
   * @return
   */
  def map[B <: Quantity[B]](f: A => B): QuantityVector[B] = QuantityVector(coordinates.toTraversable.map(f).toSeq: _*)

  def plus(that: VectorType): VectorType =
    QuantityVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), valueUnit(0)).toTraversable.map(v ⇒ v._1 + v._2).toSeq: _*)
  def minus(that: VectorType):VectorType =
    QuantityVector(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), valueUnit(0)).toTraversable.map(v ⇒ v._1 - v._2).toSeq: _*)

  def times(that: Double): VectorType = map(_ * that)
  def *(that: Double): VectorType = times(that)

  def times[B <: Quantity[B], C <: Quantity[C]](quantTimes: A => C): QuantityVector[C] = map(quantTimes)

  def divide(that: Double): VectorType = map(_ / that)

  def divide(that: A): DoubleVector = map(_ / that)
  def /(that: A) = divide(that)

  def divide[B <: Quantity[B], C <: Quantity[C]](quantDiv: A => C): QuantityVector[C] = map(quantDiv(_))

  def dotProduct(that: DoubleVector): A =
    valueUnit(coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), 0d).toTraversable.map(v ⇒ v._1.to(valueUnit) * v._2).sum)

  def dotProduct[B <: Quantity[B], C <: Quantity[C]](that: Vector[B], quantTimes: (A, B) ⇒ C)(implicit num: Numeric[C]): C =
    coordinates.toIterable.zipAll(that.coordinates.toIterable, valueUnit(0), that.coordinates.head.unit(0)).toTraversable.map(v ⇒ quantTimes(v._1, v._2)).sum

  def crossProduct(that: DoubleVector): VectorType = (this.coordinates.length, that.coordinates.length) match {
    case (3, 3) ⇒
      QuantityVector(
        (coordinates(1) * that.coordinates(2)) - (coordinates(2) * that.coordinates(1)),
        (coordinates(2) * that.coordinates(0)) - (coordinates(0) * that.coordinates(2)),
        (coordinates(0) * that.coordinates(1)) - (coordinates(1) * that.coordinates(0)))
    case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional Cross Product is not currently supported")
    case _      ⇒ throw new UnsupportedOperationException("Cross Product is not supported on vectors with an arbitrary number of dimensions")
  }

  def crossProduct[B <: Quantity[B], C <: Quantity[C]](that: Vector[B], quantTimes: (A, B) ⇒ C)(implicit num: Numeric[C]): QuantityVector[C] = {
    (this.coordinates.length, that.coordinates.length) match {
      case (3, 3) ⇒
        QuantityVector(
          quantTimes(coordinates(1), that.coordinates(2)) - quantTimes(coordinates(2), that.coordinates(1)),
          quantTimes(coordinates(2), that.coordinates(0)) - quantTimes(coordinates(0), that.coordinates(2)),
          quantTimes(coordinates(0), that.coordinates(1)) - quantTimes(coordinates(1), that.coordinates(0)))
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
   * Returns a QuantityVector with all coordinates set the supplied unit
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def in(uom: UnitOfMeasure[A]): QuantityVector[A] = this.map[A](_.in(uom))

 }
