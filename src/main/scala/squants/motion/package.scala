/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
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

  lazy val SpeedOfLight = Velocity(Meters(2.99792458e8), Seconds(1))

  lazy val EquatorGravity = MetersPerSecondSquared(9.7903)
  lazy val StandardEarthGravity = MetersPerSecondSquared(9.80665)
  lazy val PoleGravity = MetersPerSecondSquared(9.8322)
}
