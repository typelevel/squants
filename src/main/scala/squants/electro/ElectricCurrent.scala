/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import scala.Some
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

  def valueUnit = Amperes
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

object ElectricCurrent {
  private[electro] def apply(value: Double) = new ElectricCurrent(value)
}

/**
 * Base trait for units of [[squants.electro.ElectricCurrent]]
 */
trait ElectricCurrentUnit extends BaseQuantityUnit[ElectricCurrent] with UnitMultiplier {
  def dimensionSymbol = "I"
  def apply(n: Double) = ElectricCurrent(convertFrom(n))
  def unapply(c: ElectricCurrent) = Some(convertTo(c.value))
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

  implicit class ElectricCurrentConversion(d: Double) {
    def amperes = Amperes(d)
    def amps = Amperes(d)
    def A = Amperes(d)
    def milliampers = Milliamperes(d)
    def milliamps = Milliamperes(d)
    def mA = Milliamperes(d)
  }

  implicit object ElectricCurrentNumeric
    extends AbstractQuantityNumeric[ElectricCurrent]((Amperes))
}
