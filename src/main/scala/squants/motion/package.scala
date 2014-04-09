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

  lazy val SpeedOfLight = Velocity(Meters(299792458), Seconds(1))

  lazy val EquatorGravity = Acceleration(Velocity(Meters(9.7803), Seconds(1)), Seconds(1))
  lazy val StandardEarthGravity = Acceleration(Velocity(Meters(9.80665), Seconds(1)), Seconds(1))
  lazy val PoleGravity = Acceleration(Velocity(Meters(9.8322), Seconds(1)), Seconds(1))
}
