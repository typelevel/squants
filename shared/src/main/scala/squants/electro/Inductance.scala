/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Henry]]
 */
final class Inductance private (val value: Double, val unit: InductanceUnit)
    extends Quantity[Inductance] {

  def dimension = Inductance

  def *(that: ElectricCurrent): MagneticFlux = Webers(this.toHenry * that.toAmperes)
  def /(that: Length): Permeability = HenriesPerMeter(this.toHenry / that.toMeters)

  def toHenry = to(Henry)
  def toMillihenry = to(Millihenry)
  def toMicrohenry = to(Microhenry)
  def toNanohenry = to(Nanohenry)
  def toPicohenry = to(Picohenry)
}

object Inductance extends Dimension[Inductance] {
  private[electro] def apply[A](n: A, unit: InductanceUnit)(implicit num: Numeric[A]) = new Inductance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Inductance"
  def primaryUnit = Henry
  def siUnit = Henry
  def units = Set(Henry, Millihenry, Microhenry, Nanohenry, Picohenry)
}

trait InductanceUnit extends UnitOfMeasure[Inductance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Inductance(n, this)
}

object Henry extends InductanceUnit with PrimaryUnit with SiUnit {
  val symbol = "H"
}

object Millihenry extends InductanceUnit with SiUnit {
  val symbol = "mH"
  val conversionFactor = MetricSystem.Milli
}

object Microhenry extends InductanceUnit with SiUnit {
  val symbol = "μH"
  val conversionFactor = MetricSystem.Micro
}

object Nanohenry extends InductanceUnit with SiUnit {
  val symbol = "nH"
  val conversionFactor = MetricSystem.Nano
}

object Picohenry extends InductanceUnit with SiUnit {
  val symbol = "pH"
  val conversionFactor = MetricSystem.Pico
}

object InductanceConversions {
  lazy val henry = Henry(1)
  lazy val millihenry = Millihenry(1)
  lazy val microhenry = Microhenry(1)
  lazy val nanohenry = Nanohenry(1)
  lazy val picohenry = Picohenry(1)

  implicit class InductanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def henry = Henry(n)
    def millihenry = Millihenry(n)
    def microhenry = Microhenry(n)
    def nanohenry = Nanohenry(n)
    def picohenry = Picohenry(n)
  }

  implicit object InductanceNumeric extends AbstractQuantityNumeric[Inductance](Inductance.primaryUnit)
}