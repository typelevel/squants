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
package object energy {

  object KineticEnergy {
    def apply(mass: Mass, velocity: squants.motion.Velocity): Energy =
      Joules(0.5 * mass.toKilograms * velocity.toMetersPerSecond * velocity.toMetersPerSecond)

    def apply(mass: Mass, momentum: squants.motion.Momentum): Energy =
      Joules(momentum.toNewtonSeconds / (mass.toKilograms * 2.0))

  }
}
