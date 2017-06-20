/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2015-2017, Carlos Quiroz                                         **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.motion.Newtons

/**
 * Quantity and units for Magnetic Permeability
 *
 * https://en.wikipedia.org/wiki/Permeability_(electromagnetism)
 *
 * @author  cquiroz
 * @since   1.4
 *
 * @param value value in [[squants.electro.Webers]]
 */
final class MagneticPermeability private (val value: Double, val unit: MagneticPermeabilityUnit)
    extends Quantity[MagneticPermeability] {

  def dimension = MagneticPermeability

}

object MagneticPermeability extends Dimension[MagneticPermeability] {
  private[electro] def apply[A](n: A, unit: MagneticPermeabilityUnit)(implicit num: Numeric[A]) = new MagneticPermeability(num.toDouble(n), unit)
  def apply = parse _
  def name = "MagneticPermeability"
  def primaryUnit = HenriesPerMeter
  def siUnit = HenriesPerMeter
  def units = Set(HenriesPerMeter, NewtonsPerAmperesSquared)
}

trait MagneticPermeabilityUnit extends UnitOfMeasure[MagneticPermeability] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MagneticPermeability(n, this)
}

object HenriesPerMeter extends MagneticPermeabilityUnit with PrimaryUnit with SiUnit {
  def symbol = s"${Henry.symbol}/${Meters.symbol}"
}

object NewtonsPerAmperesSquared extends MagneticPermeabilityUnit with PrimaryUnit with SiUnit {
  def symbol = s"${Newtons.symbol}/${Amperes.symbol}²"
}

object MagneticPermeabilityConversions {
  lazy val henryPerMeter = HenriesPerMeter(1)

  implicit class MagneticPermeabilityConversions[A](n: A)(implicit num: Numeric[A]) {
    def henriesPerMeter = HenriesPerMeter(n)
    def newtonsPerAmperesSquared = NewtonsPerAmperesSquared(n)
  }

  implicit object MagneticPermeabilityNumeric extends AbstractQuantityNumeric[MagneticPermeability](MagneticPermeability.primaryUnit)
}
