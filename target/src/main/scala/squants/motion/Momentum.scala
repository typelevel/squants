/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ Seconds, TimeIntegral }
import squants.mass.Kilograms

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param mass Mass
 * @param velocity Velocity
 */
case class Momentum(mass: Mass, velocity: Velocity) extends Quantity[Momentum] with TimeIntegral[Force] {

  def valueUnit = NewtonSeconds
  def value = toNewtonSeconds

  def /(that: Time): Force = Force(mass, velocity / that)
  def /(that: Force): Time = Seconds(toNewtonSeconds / that.toNewtons)
  def /(that: Velocity): Mass = mass / (that / velocity)
  def /(that: Mass): Velocity = velocity / (that / mass)

  def toNewtonSeconds = mass.toKilograms * velocity.toMetersPerSeconds
}

trait MomentumUnit extends UnitOfMeasure[Momentum] {
}

object NewtonSeconds extends MomentumUnit {
  def apply(d: Double): Momentum = Momentum(Kilograms(d), MetersPerSecond(1))
  val symbol = "Ns"
}

object MomentumConversions {
  lazy val newtonSecond = NewtonSeconds(1)

  implicit class MomentumConversions(d: Double) {
    def newtonSeconds = NewtonSeconds(d)
  }
}