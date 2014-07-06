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

  def valueUnit = MagneticFluxDensity.valueUnit

  def *(that: Area): MagneticFlux = Webers(toTeslas * that.toSquareMeters)

  def toTeslas = to(Teslas)
  def toGuass = to(Gauss)
}

object MagneticFluxDensity extends QuantityCompanion[MagneticFluxDensity] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new MagneticFluxDensity(num.toDouble(n))
  def apply = parseString _
  def name = "MagneticFluxDensity"
  def valueUnit = Teslas
  def units = Set(Teslas, Gauss)
}

trait MagneticFluxDensityUnit extends UnitOfMeasure[MagneticFluxDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MagneticFluxDensity(convertFrom(n))
}

object Teslas extends MagneticFluxDensityUnit with ValueUnit {
  val symbol = "T"
}

object Gauss extends MagneticFluxDensityUnit {
  val conversionFactor = 100 * MetricSystem.Micro
  val symbol = "Gs"
}

object MagneticFluxDensityConversions {
  lazy val tesla = Teslas(1)
  lazy val gauss = Gauss(1)

  implicit class MagneticFluxDensistyConversions[A](n: A)(implicit num: Numeric[A]) {
    def teslas = Teslas(n)
    def gauss = Gauss(n)
  }

  implicit object MagneticFluxDensistyNumeric extends AbstractQuantityNumeric[MagneticFluxDensity](MagneticFluxDensity.valueUnit)
}
