/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{ SquareInches, SquareMeters }
import squants.time.TimeIntegral

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Pressure private (val value: Double, val unit: PressureUnit)
    extends Quantity[Pressure]
    with TimeIntegral[PressureChange] {

  def dimension = Pressure

  override protected def timeDerived: PressureChange = PascalsPerSecond(toPascals)
  override protected def time: Time = Seconds(1)

  def *(that: Area): Force = Newtons(toPascals * that.toSquareMeters)
  def *(that: Time) = ??? // returns DynamicViscosity

  def toPascals = to(Pascals)
  def toBars = to(Bars)
  def toPoundsPerSquareInch = to(PoundsPerSquareInch)
  def toStandardAtmospheres = to(StandardAtmospheres)
  def toMillimeterOfMercury = to(MillimeterOfMercury)
}

object Pressure extends Dimension[Pressure] {
  private[motion] def apply[A](n: A, unit: PressureUnit)(implicit num: Numeric[A]) = new Pressure(num.toDouble(n), unit)
  def apply = parse _
  def name = "Pressure"
  def primaryUnit = Pascals
  def siUnit = Pascals
  def units = Set(Pascals, Bars, PoundsPerSquareInch, StandardAtmospheres, MillimeterOfMercury)
}

trait PressureUnit extends UnitOfMeasure[Pressure] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Pressure(n, this)
}

object Pascals extends PressureUnit with PrimaryUnit with SiUnit {
  val symbol = "Pa"
}

object Bars extends PressureUnit {
  val symbol = "bar"
  val conversionFactor = 100000d
}

object PoundsPerSquareInch extends PressureUnit {
  val symbol = "psi"
  val conversionFactor = (Newtons.conversionFactor * PoundForce.conversionFactor) / (SquareInches.conversionFactor / SquareMeters.conversionFactor)
}

object StandardAtmospheres extends PressureUnit {
  val symbol = "atm"
  val conversionFactor = Newtons.conversionFactor * 1.01325e5
}

object MillimeterOfMercury extends PressureUnit {
  val symbol = "mmHg"
  val conversionFactor = Newtons.conversionFactor * 133.322387415
}

object PressureConversions {
  lazy val pascal = Pascals(1)
  lazy val bar  = Bars(1)
  lazy val psi  = PoundsPerSquareInch(1)
  lazy val atm  = StandardAtmospheres(1)
  lazy val mmHg = MillimeterOfMercury(1)

  implicit class PressureConversions[A](n: A)(implicit num: Numeric[A]) {
    def pascals = Pascals(n)
    def bars    = Bars(n)
    def psi     = PoundsPerSquareInch(n)
    def atm     = StandardAtmospheres(n)
    def mmHg    = MillimeterOfMercury(n)
  }

  implicit object PressureNumeric extends AbstractQuantityNumeric[Pressure](Pressure.primaryUnit)
}
