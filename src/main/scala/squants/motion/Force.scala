/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.mass.{ Pounds, MassUnit, Kilograms }
import squants.time.{ TimeIntegral, Seconds, TimeDerivative }
import squants.energy.Joules
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param mass Mass
 * @param acc Acceleration
 */
case class Force(mass: Mass, acc: Acceleration) extends Quantity[Force]
    with TimeDerivative[Momentum] with TimeIntegral[Yank] {

  def change = Momentum(mass, acc * Seconds(1))
  def time = Seconds(1)

  def valueUnit = Newtons
  def value = toNewtons

  /* This could also be Torque, as Energy(Work) and Torque are dimensionally equivalent */
  def *(that: Length): Energy = Joules(toNewtons * that.toMeters)
  def /(that: Length) = ??? // return SurfaceTension
  def /(that: Mass): Acceleration = acc * (mass / that)
  def /(that: Acceleration): Mass = mass * (acc / that)
  def /(that: Area): Pressure = Pressure(this, that)
  def /(that: Pressure): Area = SquareMeters(toNewtons / that.toPascals)
  def /(that: Time): Yank = NewtonsPerSecond(toNewtons / that.toSeconds)
  def /(that: Yank): Time = Seconds(toNewtons / that.toNewtonsPerSecond)

  def toString(unit: ForceUnit) = to(unit) + " " + unit.symbol

  def to(unit: ForceUnit) = mass.to(unit.massUnit) / unit.massBase.to(unit.massUnit) * acc.to(unit.accUnit)
  def toNewtons = to(Newtons)
  def toKilogramForce = to(KilogramForce)
  def toPoundForce = to(PoundForce)
}

trait ForceUnit extends UnitOfMeasure[Force] {
  def massUnit: MassUnit
  def massBase: Mass
  def accUnit: AccelerationUnit
  def accBase: Acceleration
  def apply(value: Double) = Force(massBase * value, accBase)
}

object Newtons extends ForceUnit with ValueUnit {
  val massUnit = Kilograms
  val massBase = Kilograms(1)
  val accUnit = MetersPerSecondSquared
  val accBase = MetersPerSecondSquared(1)
  val symbol = "N"
}

object KilogramForce extends ForceUnit {
  val massUnit = Kilograms
  val massBase = Kilograms(1)
  val accUnit = EarthGravities
  val accBase = EarthGravities(1)
  val symbol = "kgf"
}

object PoundForce extends ForceUnit {
  val massUnit = Pounds
  val massBase = Pounds(1)
  val accUnit = EarthGravities
  val accBase = EarthGravities(1)
  val symbol = "lbf"
}

object ForceConversions {
  lazy val newton = Newtons(1)
  lazy val kilogramForce = KilogramForce(1)
  lazy val poundForce = PoundForce(1)

  implicit class ForceConversions(d: Double) {
    def newtons = Newtons(d)
    def kilogramForce = KilogramForce(d)
    def poundForce = PoundForce(1)
    def lbf = PoundForce(1)
  }

  implicit object ForceNumeric extends AbstractQuantityNumeric[Force](Newtons)
}

