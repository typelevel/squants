/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.energy.Joules
import squants.mass.{ Kilograms, Pounds }
import squants.space.SquareMeters
import squants.time.{ Seconds, TimeDerivative, TimeIntegral }

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
  def *(that: Length): Energy = Joules(this.toNewtons * that.toMeters)
  def /(that: Length) = ??? // return SurfaceTension
  def /(that: Mass): Acceleration = MetersPerSecondSquared(this.toNewtons / that.toKilograms)
  def /(that: Acceleration): Mass = Kilograms(this.toNewtons / that.toMetersPerSecondSquared)
  def /(that: Area): Pressure = Pascals(this.toNewtons / that.toSquareMeters)
  def /(that: Pressure): Area = SquareMeters(this.toNewtons / that.toPascals)

  def toNewtons = to(Newtons)
  def toKilogramForce = to(KilogramForce)
  def toPoundForce = to(PoundForce)
  def toKiloElectronVoltsPerMicrometer = to(KiloElectronVoltsPerMicrometer)
  def toMegaElectronVoltsPerCentimeter = to(MegaElectronVoltsPerCentimeter)
}

object Force extends Dimension[Force] {
  private[motion] def apply[A](n: A, unit: ForceUnit)(implicit num: Numeric[A]) = new Force(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Force"
  def primaryUnit = Newtons
  def siUnit = Newtons
  def units = Set(
    Newtons, KilogramForce, PoundForce, 
    KiloElectronVoltsPerMicrometer, MegaElectronVoltsPerCentimeter)
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

object KiloElectronVoltsPerMicrometer extends ForceUnit {
  val symbol = "keV/μm"
  val conversionFactor = 1.602176565e-16 / MetricSystem.Micro
}

object MegaElectronVoltsPerCentimeter extends ForceUnit {
  val symbol = "MeV/cm"
  val conversionFactor = 1.602176565e-13 / MetricSystem.Centi
}

object ForceConversions {
  lazy val newton = Newtons(1)
  lazy val kilogramForce = KilogramForce(1)
  lazy val poundForce = PoundForce(1)
  lazy val kiloElectronVoltsPerMicrometer = KiloElectronVoltsPerMicrometer(1)
  lazy val megaElectronVoltsPerCentimeter = MegaElectronVoltsPerCentimeter(1)

  implicit class ForceConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtons = Newtons(n)
    def kilogramForce = KilogramForce(n)
    def poundForce = PoundForce(n)
    def lbf = PoundForce(n)
    def kiloElectronVoltsPerMicrometer = KiloElectronVoltsPerMicrometer(n)
    def megaElectronVoltsPerCentimeter = MegaElectronVoltsPerCentimeter(n)
  }

  implicit object ForceNumeric extends AbstractQuantityNumeric[Force](Force.primaryUnit)
}

