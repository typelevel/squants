/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Teslas]]
 */
final class MagneticFluxDensity private (val value: Double) extends Quantity[MagneticFluxDensity]
    with PhysicalQuantity {

  def valueUnit = Teslas

  def *(that: Area): MagneticFlux = Webers(toTeslas * that.toSquareMeters)

  def toTeslas = to(Teslas)
  def toGuass = to(Gauss)
}

object MagneticFluxDensity {
  private[electro] def apply(value: Double) = new MagneticFluxDensity(value)
}

trait MagneticFluxDensityUnit extends UnitOfMeasure[MagneticFluxDensity] with UnitMultiplier {
  def apply(d: Double) = MagneticFluxDensity(convertFrom(d))
}

object Teslas extends MagneticFluxDensityUnit with ValueUnit {
  val symbol = "T"
}

object Gauss extends MagneticFluxDensityUnit {
  val multiplier = 100 * MetricSystem.Micro
  val symbol = "Gs"
}

object MagneticFluxDensityConversions {
  lazy val tesla = Teslas(1)
  lazy val gauss = Gauss(1)

  implicit class MagneticFluxDensistyConversion(d: Double) {
    def teslas = Teslas(d)
    def gauss = Gauss(d)
  }

  implicit object MagneticFluxDensistyNumeric extends AbstractQuantityNumeric[MagneticFluxDensity]((Teslas))
}
