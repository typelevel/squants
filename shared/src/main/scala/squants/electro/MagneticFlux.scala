/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.space.SquareMeters
import squants.time.TimeIntegral

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Webers]]
 */
final class MagneticFlux private (val value: Double, val unit: MagneticFluxUnit)
    extends Quantity[MagneticFlux]
    with TimeIntegral[ElectricPotential] {

  def dimension = MagneticFlux

  protected def timeDerived = Volts(toWebers)
  protected def time = Seconds(1)

  def /(that: Area): MagneticFluxDensity = Teslas(this.toWebers / that.toSquareMeters)
  def /(that: MagneticFluxDensity): Area = SquareMeters(this.toWebers / that.toTeslas)
  def /(that: ElectricCurrent): Inductance = Henry(this.toWebers / that.toAmperes)
  def /(that: Inductance): ElectricCurrent = Amperes(this.toWebers / that.toHenry)

  def toWebers = to(Webers)
}

object MagneticFlux extends Dimension[MagneticFlux] {
  private[electro] def apply[A](n: A, unit: MagneticFluxUnit)(implicit num: Numeric[A]) = new MagneticFlux(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "MagneticFlux"
  def primaryUnit = Webers
  def siUnit = Webers
  def units = Set(Webers)
}

trait MagneticFluxUnit extends UnitOfMeasure[MagneticFlux] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MagneticFlux(n, this)
}

object Webers extends MagneticFluxUnit with PrimaryUnit with SiUnit {
  val symbol = "Wb"
}

object MagneticFluxConversions {
  lazy val weber = Webers(1)

  implicit class MagneticFluxConversions[A](n: A)(implicit num: Numeric[A]) {
    def webers = Webers(n)
  }

  implicit object MagneticFluxNumeric extends AbstractQuantityNumeric[MagneticFlux](MagneticFlux.primaryUnit)
}