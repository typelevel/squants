/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.time.{ Seconds, TimeDerivative }
import squants.energy.Watts

/**
 * Represents a quantity of electric current
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the amount of charge in [[squants.electro.Amperes]]'s
 */
final class ElectricCurrent private (val value: Double) extends Quantity[ElectricCurrent]
    with PhysicalQuantity with BaseQuantity with TimeDerivative[ElectricCharge] {

  def valueUnit = ElectricCurrent.valueUnit
  def baseUnit = Amperes

  def change = Coulombs(value)
  def time = Seconds(1)

  def *(that: ElectricalResistance): ElectricPotential = Volts(toAmperes * that.toOhms)
  def *(that: ElectricPotential): Power = Watts(toAmperes * that.toVolts)
  def *(that: Inductance): MagneticFlux = Webers(toAmperes * that.toHenry)
  def /(that: ElectricPotential): ElectricalConductance = Siemens(toAmperes / that.toVolts)
  def /(that: Length) = ??? // returns MagneticFieldStrength
  def /(that: Area) = ??? // returns ElectricCurrentDensity

  def toAmperes = to(Amperes)
  def toMilliamperes = to(Milliamperes)
}

object ElectricCurrent extends QuantityCompanion[ElectricCurrent] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new ElectricCurrent(num.toDouble(n))
  def apply = parseString _
  def name = "ElectricCurrent"
  def valueUnit = Amperes
  def units = Set(Amperes, Milliamperes)
}

/**
 * Base trait for units of [[squants.electro.ElectricCurrent]]
 */
trait ElectricCurrentUnit extends BaseQuantityUnit[ElectricCurrent] with UnitMultiplier {
  def dimensionSymbol = "I"
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricCurrent(convertFrom(n))
}

/**
 * Amperes
 */
object Amperes extends ElectricCurrentUnit with ValueUnit with BaseUnit {
  val symbol = "A"
}

/**
 * Milliamperes
 */
object Milliamperes extends ElectricCurrentUnit {
  val symbol = "mA"
  val multiplier = MetricSystem.Milli
}

object ElectricCurrentConversions {
  lazy val ampere = Amperes(1)
  lazy val amp = Amperes(1)
  lazy val milliampere = Milliamperes(1)
  lazy val milliamp = Milliamperes(1)

  implicit class ElectricCurrentConversions[A](n: A)(implicit num: Numeric[A]) {
    def amperes = Amperes(n)
    def amps = Amperes(n)
    def A = Amperes(n)
    def milliampers = Milliamperes(n)
    def milliamps = Milliamperes(n)
    def mA = Milliamperes(n)
  }

  implicit object ElectricCurrentNumeric
    extends AbstractQuantityNumeric[ElectricCurrent](ElectricCurrent.valueUnit)
}
