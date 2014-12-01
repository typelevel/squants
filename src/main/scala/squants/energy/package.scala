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
package object energy {

  object KineticEnergy {
    def apply(mass: Mass, velocity: Velocity): Energy =
      Joules(0.5 * mass.toKilograms * velocity.toMetersPerSecond * velocity.toMetersPerSecond)
  }
}
