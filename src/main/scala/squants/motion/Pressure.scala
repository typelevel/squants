/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{ SquareInches, SquareMeters }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Pressure private (val value: Double) extends Quantity[Pressure] {

  def valueUnit = Pressure.valueUnit

  def *(that: Area): Force = Newtons(toPascals * that.toSquareMeters)
  def *(that: Time) = ??? // returns DynamicViscosity

  def toPascals = to(Pascals)
  def toBars = to(Bars)
  def toPoundsPerSquareInch = to(PoundsPerSquareInch)
  def toStandardAtmospheres = to(StandardAtmospheres)
}

object Pressure extends QuantityCompanion[Pressure] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new Pressure(num.toDouble(n))
  def apply = parseString _
  def name = "Pressure"
  def valueUnit = Pascals
  def units = Set(Pascals, Bars, PoundsPerSquareInch, StandardAtmospheres)
}

trait PressureUnit extends UnitOfMeasure[Pressure] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Pressure(convertFrom(n))
}

object Pascals extends PressureUnit with ValueUnit {
  val symbol = "Pa"
}

object Bars extends PressureUnit {
  val symbol = "bar"
  val conversionFactor = 100d
}

object PoundsPerSquareInch extends PressureUnit {
  val symbol = "psi"
  val conversionFactor = (Newtons.conversionFactor * PoundForce.conversionFactor) / (SquareInches.conversionFactor / SquareMeters.conversionFactor)
}

object StandardAtmospheres extends PressureUnit {
  val symbol = "atm"
  val conversionFactor = Newtons.conversionFactor * 101325d
}

object PressureConversions {
  lazy val pascal = Pascals(1)
  lazy val bar = Bars(1)
  lazy val psi = PoundsPerSquareInch(1)
  lazy val atm = StandardAtmospheres(1)

  implicit class PressureConversions[A](n: A)(implicit num: Numeric[A]) {
    def pascals = Pascals(n)
    def bars = Bars(n)
    def psi = PoundsPerSquareInch(n)
    def atm = StandardAtmospheres(n)
  }

  implicit object PressureNumeric extends AbstractQuantityNumeric[Pressure](Pressure.valueUnit)
}