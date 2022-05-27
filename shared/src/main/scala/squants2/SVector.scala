/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants2

import squants2.space._

import scala.math.Numeric.Implicits.infixNumericOps

/**
 * Root trait for representing Vectors
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @tparam A The numeric type for the Vector's coordinate values
 */
abstract class SVector[A, Q[N] <: Quantity[N, Q]] {

  type SVectorType = SVector[A, Q]

  /**
   * The list of values that makeup the Vector's Cartesian coordinates
   * @return
   */
  def coordinates: Seq[Q[A]]

  /**
   * The scalar value of the Vector
   * @return
   */
  def magnitude: Q[A]

  /**
   * The angle between the two Cartesian coordinates at the supplied indices
   * @param coordinateX index of the abscissa coordinate (defaults to 0)
   * @param coordinateY index of the ordinate coordinate (defaults to 1)
   * @param unit unit for the angle (theta) component (defaults to Radians)
   * @return Angle
   */
  def angle(coordinateX: Int = 0, coordinateY: Int = 1, unit: AngleUnit = Radians): Angle[A]

  /**
   * The polar coordinates (r, theta) of the two Cartesian coordinates at the supplied indices
   * @param coordinateX index of the abscissa coordinate (defaults to 0)
   * @param coordinateY index of the ordinate coordinate (defaults to 1)
   * @param unit unit for the angle (theta) component (defaults to Radians)
   * @return (A, Angle)
   */
  def polar(coordinateX: Int = 0, coordinateY: Int = 1, unit: AngleUnit = Radians): (Q[A], Angle[A]) = (magnitude, angle(coordinateX, coordinateY, unit))

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
  def + : SVectorType => SVectorType = plus

  /**
   * Subtract two Vectors
   * @param that Vector[A]
   * @return
   */
  def minus(that: SVectorType): SVectorType
  def - : SVectorType => SVectorType = minus

  /**
   * Scale a Vector
   * @param that Double
   * @return
   */
  def times[B](that: B)(implicit f: B => A): SVectorType
  def *[B](that: B)(implicit f: B => A): SVectorType  = times(that)

  /**
   * Reduce a Vector
   *
   * @param that Double
   * @return
   */
  def divide[B](that: B)(implicit f: B => A): SVectorType
  def /[B](that: B)(implicit f: B => A): SVectorType = divide(that)

  /**
   * Create the Dot Product of two Vectors
   * @param that Double
   * @return
   */
  def dotProduct[B](that: SVector[B, Dimensionless])(implicit f: B => A): A
  def *[B](that: SVector[B, Dimensionless])(implicit f: B => A): A = dotProduct(that)

  /**
   * Create the Cross Product of two Vectors
   * @param that Vector[A]
   * @return
   */
  def crossProduct[B](that: SVector[B, Dimensionless])(implicit f: B => A): SVector[A, Q]
  def #*[B](that: SVector[B, Dimensionless])(implicit f: B => A): SVector[A, Q] = crossProduct(that)

}

object SVector {

  //  def apply(coordinates: Double*): DoubleVector = DoubleVector(coordinates: _*)
  //  def apply[A <: Quantity[A]](coordinates: A*): QuantityVector[A] = QuantityVector[A](coordinates: _*)

//  /**
//   * Create a 2-dimensional DoubleVector from Polar Coordinates
//   * @param radius the magnitude of the vector
//   * @param theta the angle from the polar axis
//   * @return
//   */
  //  def apply[B: Numeric](radius: Double, theta: Angle[B]): DoubleVector = apply(radius * theta.cos, radius * theta.sin)

//  /**
//   * Create a 2-dimensional QuantityVector[A] from Polar Coordinates
//   * @param radius the magnitude of the vector
//   * @param theta the angle from the polar axis
//   * @tparam A Quantity type
//   * @return
//   */
  //  def apply[A <: Quantity[A]](radius: A, theta: Angle): QuantityVector[A] = apply(radius * theta.cos, radius * theta.sin)
}

/**
 *
 * @param coordinates - The list of values that makeup the Vector's Cartesian coordinates
 * @tparam A The numeric type for the Vector's coordinate values
 * @tparam Q The Dimension of the Vector's coordinate values
 */
case class SVectorImpl[A, Q[N] <: Quantity[N, Q]](coordinates: Seq[Q[A]])(implicit num: Numeric[A]) extends SVector[A, Q] {

  private val valueUnit = coordinates.head.unit
  /**
   * The scalar value of the Vector
   *
   * @return
   */
  override def magnitude: Q[A] = ???
//    valueUnit(num.sqrt(coordinates.map(v ⇒ v.to(valueUnit) * v.to(valueUnit)).foldLeft(num.zero)(_ + _)))

  /**
   * The angle between the two Cartesian coordinates at the supplied indices
   *
   * @param coordinateX index of the abscissa coordinate (defaults to 0)
   * @param coordinateY index of the ordinate coordinate (defaults to 1)
   * @param unit        unit for the angle (theta) component (defaults to Radians)
   * @return Angle
   */
  override def angle(coordinateX: Int, coordinateY: Int, unit: AngleUnit): Angle[A] = ???
//    Radians(num.atan(coordinates(coordinateY) / coordinates(coordinateX))) in unit

  /**
   * Creates the Unit Vector which corresponds to this vector
   *
   * @return
   */
  override def normalize: SVectorType = this / magnitude.to(valueUnit)

  /**
   * Creates the Unit Vector which corresponds to this vector, using the given unit
   *
   * @param unit UnitofMeasure to as unit(1) of vector
   * @return
   */
  def normalize(unit: UnitOfMeasure[Q]): SVectorType = this / magnitude.to(unit)

  /**
   * Creates a new SVector by applying the function to each coordinate
   *
   * @param f - the function to apply to each coordinate
   * @tparam B - the numeric type of the new SVector
   * @tparam R - the Dimension of the new SVector
   * @return
   */
  def map[B: Numeric, R[RN] <: Quantity[RN, R]](f: Q[A] => R[B]): SVector[B, R] = SVectorImpl(coordinates.map(q => f(q)))

  /**
   * Add two Vectors
   *
   * @param that Vector[A]
   * @return
   */
  override def plus(that: SVectorType): SVectorType =
    SVectorImpl(coordinates.zipAll(that.coordinates, valueUnit(num.zero), valueUnit(num.zero)).map(v ⇒ (v._1 + v._2))).asInstanceOf[SVectorType]

  /**
   * Subtract two Vectors
   *
   * @param that Vector[A]
   * @return
   */
  override def minus(that: SVectorType): SVectorType =
    SVectorImpl(coordinates.zipAll(that.coordinates, valueUnit(num.zero), valueUnit(num.zero)).map(v ⇒ (v._1 - v._2))).asInstanceOf[SVectorType]


  /**
   * Scale a Vector
   *
   * @param that Double
   * @return
   */
  override def times[B](that: B)(implicit f: B => A): SVectorType = map(_ * that).asInstanceOf[SVectorType]

  /**
   * Reduce a Vector
   *
   * @param that Double
   * @return
   */
  override def divide[B](that: B)(implicit f: B => A): SVectorType = map(_ * that).asInstanceOf[SVectorType]

  /**
   * Create the Dot Product of two Vectors
   *
   * @param that Double
   * @return
   */
  override def dotProduct[B](that: SVector[B, Dimensionless])(implicit f: B => A): A =
    valueUnit(coordinates.zipAll(that.coordinates.map(_.asNum[A]), valueUnit(num.zero), Each(num.zero))
      .map(v ⇒ v._1.to(valueUnit) * v._2.to(Each)).foldLeft(num.zero)(_ + _)).to(valueUnit)
  // TODO dotProduct against other quantity types

  /**
   * Create the Cross Product of two Vectors
   *
   * @param that Vector[A]
   * @return
   */
  override def crossProduct[B](that: SVector[B, Dimensionless])(implicit f: B => A): SVector[A, Q] = {
    (this.coordinates.length, that.coordinates.length) match {
      case (3, 3) ⇒
        SVectorImpl(
          Seq((this.coordinates(1) * that.coordinates(2).to(Each)) - (this.coordinates(2) * that.coordinates(1).to(Each)),
          (this.coordinates(2) * that.coordinates(0).to(Each)) - (this.coordinates(0) * that.coordinates(2).to(Each)),
          (this.coordinates(0) * that.coordinates(1).to(Each)) - (this.coordinates(1) * that.coordinates(0).to(Each))))
      case (7, 7) ⇒ throw new UnsupportedOperationException("Seven-dimensional Cross Product is not currently supported")
      case _      ⇒ throw new UnsupportedOperationException("Cross Product is not supported on vectors with an arbitrary number of dimensions")
    }
    // TODO crossProduct against other quantity types
  }

  /**
   * Returns a DoubleVector representing the quantity values in terms of the supplied unit
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def to(uom: UnitOfMeasure[Q]): SVector[A, Dimensionless] = map(q => Each(q.to(uom)))

  /**
   * Returns a QuantityVector with all coordinates set to the supplied unit
   *
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def in(uom: UnitOfMeasure[Q]): SVector[A, Q] = map(_.in(uom))

}
