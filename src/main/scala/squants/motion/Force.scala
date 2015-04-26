/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.mass.{ Pounds, Kilograms }
import squants.time.{ TimeIntegral, Seconds, TimeDerivative }
import squants.energy.Joules
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Force private (val value: Double, val unit: ForceUnit)
    extends Quantity[Force]
    with TimeDerivative[Momentum] with TimeIntegral[Yank] {

  def dimension = Force

  protected[squants] def timeIntegrated = NewtonSeconds(toNewtons)
  protected def timeDerived = NewtonsPerSecond(toNewtons)
  override def time = Seconds(1)

  /* This could also be Torque, as Energy(Work) and Torque are dimensionally equivalent */
  def *(that: Length): Energy = Joules(toNewtons * that.toMeters)
  def /(that: Length) = ??? // return SurfaceTension
  def /(that: Mass): Acceleration = MetersPerSecondSquared(toNewtons / that.toKilograms)
  def /(that: Acceleration): Mass = Kilograms(toNewtons / that.toMetersPerSecondSquared)
  def /(that: Area): Pressure = Pascals(toNewtons / that.toSquareMeters)
  def /(that: Pressure): Area = SquareMeters(toNewtons / that.toPascals)

  def toNewtons = to(Newtons)
  def toKilogramForce = to(KilogramForce)
  def toPoundForce = to(PoundForce)
}

object Force extends Dimension[Force] {
  private[motion] def apply[A](n: A, unit: ForceUnit)(implicit num: Numeric[A]) = new Force(num.toDouble(n), unit)
  def apply = parseString _
  def name = "Force"
  def primaryUnit = Newtons
  def siUnit = Newtons
  def units = Set(Newtons, KilogramForce, PoundForce)
}

trait ForceUnit extends UnitOfMeasure[Force] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Force(n, this)
}

object Newtons extends ForceUnit with PrimaryUnit with SiUnit {
  val symbol = "N"
}

object KilogramForce extends ForceUnit {
  val symbol = "kgf"
  val conversionFactor = MetersPerSecondSquared.conversionFactor * EarthGravities.conversionFactor
}

object PoundForce extends ForceUnit {
  val symbol = "lbf"
  val conversionFactor = Pounds.conversionFactor * KilogramForce.conversionFactor / Kilograms.conversionFactor
}

object ForceConversions {
  lazy val newton = Newtons(1)
  lazy val kilogramForce = KilogramForce(1)
  lazy val poundForce = PoundForce(1)

  implicit class ForceConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtons = Newtons(n)
    def kilogramForce = KilogramForce(n)
    def poundForce = PoundForce(1)
    def lbf = PoundForce(1)
  }

  implicit object ForceNumeric extends AbstractQuantityNumeric[Force](Force.primaryUnit)
}

