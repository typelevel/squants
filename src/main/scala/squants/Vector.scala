/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * Experimental implementation of 3-Dimensional Vector Quantity
 *
 * @author garyKeorkunian
 * @since 0.3.0
 *
 * @param x A
 * @param y A
 * @param z A
 * @tparam A Quantity Type
 */
case class Vector[A <: Quantity[A]](x: A, y: A, z: A) {

  def length()(implicit create: Double ⇒ A): A =
    create(math.sqrt(x.value * x.value + y.value * y.value + z.value * z.value))

  def plus(that: Vector[A]): Vector[A] = Vector(x + that.x, y + that.y, z + that.z)
  def +(that: Vector[A]) = plus(that)

  def minus(that: Vector[A]): Vector[A] = Vector(x - that.x, y - that.y, z - that.z)
  def -(that: Vector[A]) = minus(that)

  def times(that: Double): Vector[A] = Vector(x * that, y * that, z * that)
  def *(that: Double) = times(that)

  def divide(that: Double): Vector[A] = Vector(x / that, y / that, z / that)
  def /(that: Double) = divide(that)

  def dotProduct(that: Vector[A])(implicit create: Double ⇒ A): A =
    create(x.value * that.x.value + y.value * that.y.value + z.value * that.z.value)

  def crossProduct(that: Vector[A])(implicit create: Double ⇒ A): Vector[A] =
    Vector(
      create(y.value * that.z.value - z.value * that.y.value),
      create(z.value * that.x.value - x.value * that.z.value),
      create(x.value * that.y.value - y.value * that.x.value))

  def normalize(implicit create: Double ⇒ A): Vector[A] = this divide length().value
}
