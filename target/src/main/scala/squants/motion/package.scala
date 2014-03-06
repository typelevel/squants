/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
package object motion {

  type Distance = squants.space.Length
  type DistanceUnit = squants.space.LengthUnit

  case class Direction(x: Double, y: Double, z: Double)

  trait Vector {
    def magnitude: Quantity[_]
    def direction: Direction
  }
}
