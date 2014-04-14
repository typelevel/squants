/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.time.TimeIntegral
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Webers]]
 */
final class MagneticFlux private (val value: Double) extends Quantity[MagneticFlux]
    with PhysicalQuantity with TimeIntegral[ElectricPotential] {

  def valueUnit = Webers

  def /(that: ElectricPotential): Time = that.time * (this / that.change)
  def /(that: Time): ElectricPotential = Volts(toWebers / that.toSeconds)
  def /(that: Area): MagneticFluxDensity = Teslas(toWebers / that.toSquareMeters)
  def /(that: MagneticFluxDensity): Area = SquareMeters(toWebers / that.toTeslas)
  def /(that: ElectricCurrent): Inductance = Henry(toWebers / that.toAmperes)
  def /(that: Inductance): ElectricCurrent = Amperes(toWebers / that.toHenry)

  def toWebers = to(Webers)
}

object MagneticFlux {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new MagneticFlux(num.toDouble(n))
}

trait MagneticFluxUnit extends UnitOfMeasure[MagneticFlux] with UnitMultiplier

object Webers extends MagneticFluxUnit with ValueUnit {
  val symbol = "Wb"
  def apply[A](n: A)(implicit num: Numeric[A]) = MagneticFlux(n)
}

object MagneticFluxConversions {
  lazy val weber = Webers(1)

  implicit class MagneticFluxConversions[A](n: A)(implicit num: Numeric[A]) {
    def webers = Webers(n)
  }

  implicit object MagneticFluxNumeric extends AbstractQuantityNumeric[MagneticFlux](Webers)
}