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

  trait Vector {
    def magnitude: Quantity[_]
    def direction: Direction
  }

  /* Known Quantities */
  lazy val Up = Direction(0, 0, 1)
  lazy val Down = Direction(0, 0, -1)
  lazy val North = Direction(0, 1, 0)
  lazy val South = Direction(0, -1, 0)
  lazy val East = Direction(1, 0, 0)
  lazy val West = Direction(-1, 0, 0)

  lazy val SpeedOfLight = Velocity(Meters(299792458), Seconds(1))

  lazy val EquatorGravity = Acceleration(Velocity(Meters(9.7803), Seconds(1), Some(Down)), Seconds(1))
  lazy val StandardEarthGravity = Acceleration(Velocity(Meters(9.80665), Seconds(1), Some(Down)), Seconds(1))
  lazy val PoleGravity = Acceleration(Velocity(Meters(9.8322), Seconds(1), Some(Down)), Seconds(1))
}
