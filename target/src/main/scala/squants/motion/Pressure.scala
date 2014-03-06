/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{ SquareInches, AreaUnit, SquareMeters }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param force Force
 * @param area Area
 */
case class Pressure(force: Force, area: Area) extends Quantity[Pressure] {
  def value = toPascals
  def valueUnit = Pascals

  def *(that: Area): Force = Newtons(toPascals * that.toSquareMeters)
  def *(that: Time) = ??? // returns DynamicViscosity

  def toString(unit: PressureUnit) = to(unit) + " " + unit.symbol

  def to(unit: PressureUnit) = (force.to(unit.forceUnit) / unit.forceValue.to(unit.forceUnit)) / area.to(unit.areaUnit)
  def toPascals = to(Pascals)
  def toBars = to(Bars)
  def toPoundsPerSquareInch = to(PoundsPerSquareInch)
  def toStandardAtmospheres = to(StandardAtmospheres)
}

trait PressureUnit extends UnitOfMeasure[Pressure] {
  def forceUnit: ForceUnit
  def forceValue: Force
  def areaUnit: AreaUnit
  def areaValue: Area
  def apply(value: Double) = Pressure(forceValue * value, areaValue)
}

object Pascals extends PressureUnit with ValueUnit {
  val forceUnit = Newtons
  val forceValue = Newtons(1)
  val areaUnit = SquareMeters
  val areaValue = SquareMeters(1)
  val symbol = "Pa"
}

object Bars extends PressureUnit {
  val forceUnit = Newtons
  val forceValue = Newtons(100)
  val areaUnit = SquareMeters
  val areaValue = SquareMeters(1)
  val symbol = "bar"
}

object PoundsPerSquareInch extends PressureUnit {
  val forceUnit = PoundForce
  val forceValue = PoundForce(1)
  val areaUnit = SquareInches
  val areaValue = SquareInches(1)
  val symbol = "psi"
}

object StandardAtmospheres extends PressureUnit {
  val forceUnit = Newtons
  val forceValue = Newtons(101325)
  val areaUnit = SquareMeters
  val areaValue = SquareMeters(1)
  val symbol = "atm"
}

object PressureConversions {
  lazy val pascal = Pascals(1)
  lazy val bar = Bars(1)
  lazy val psi = PoundsPerSquareInch(1)
  lazy val atm = StandardAtmospheres(1)

  implicit class PressureConversion(d: Double) {
    def pascals = Pascals(d)
    def bars = Bars(d)
    def psi = PoundsPerSquareInch(d)
    def atm = StandardAtmospheres(d)
  }
}