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

  def *(that: Area): Force = Newtons(this.toPascals * that.toSquareMeters)
  def *(that: Time) = ??? // returns DynamicViscosity

  def toPascals: Double              = to(Pascals)
  def toBars: Double                 = to(Bars)
  def toPoundsPerSquareInch: Double  = to(PoundsPerSquareInch)
  def toStandardAtmospheres: Double  = to(StandardAtmospheres)
  def toMillimetersOfMercury: Double = to(MillimetersOfMercury)
  def toInchesOfMercury: Double       = to(InchesOfMercury)
  def toTorr: Double                 = to(Torrs)
}

object Pressure extends Dimension[Pressure] {
  private[motion] def apply[A](n: A, unit: PressureUnit)(implicit num: Numeric[A]) = new Pressure(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Pressure"
  def primaryUnit = Pascals
  def siUnit = Pascals
  def units = Set(Pascals, Bars, PoundsPerSquareInch, StandardAtmospheres, MillimetersOfMercury, InchesOfMercury, Torrs)
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
  val conversionFactor = (PoundForce.conversionFactor / Newtons.conversionFactor) / (SquareInches.conversionFactor / SquareMeters.conversionFactor)
}

object StandardAtmospheres extends PressureUnit {
  val symbol = "atm"
  val conversionFactor = Newtons.conversionFactor * 1.01325e5
}

object MillimetersOfMercury extends PressureUnit {
  val symbol = "mmHg"
  val conversionFactor = Newtons.conversionFactor * 133.322387415
}

object InchesOfMercury extends PressureUnit {
  val symbol = "inHg"
  val conversionFactor = Newtons.conversionFactor * 3386.389
}

object Torrs extends PressureUnit {
  val symbol = "Torr"
  val conversionFactor = StandardAtmospheres.conversionFactor / 760d
}

object PressureConversions {
  lazy val pascal = Pascals(1)
  lazy val bar    = Bars(1)
  lazy val psi    = PoundsPerSquareInch(1)
  lazy val atm    = StandardAtmospheres(1)
  lazy val mmHg   = MillimetersOfMercury(1)
  lazy val inHg   = InchesOfMercury(1)
  lazy val torr   = Torrs(1)

  implicit class PressureConversions[A](n: A)(implicit num: Numeric[A]) {
    def pascals = Pascals(n)
    def bars    = Bars(n)
    def psi     = PoundsPerSquareInch(n)
    def atm     = StandardAtmospheres(n)
    def mmHg    = MillimetersOfMercury(n)
    def inHg    = InchesOfMercury(n)
    def torr    = Torrs(n)
  }

  implicit object PressureNumeric extends AbstractQuantityNumeric[Pressure](Pressure.primaryUnit)
}
