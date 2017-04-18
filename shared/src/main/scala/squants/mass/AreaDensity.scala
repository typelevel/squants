/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.TypeLevelInt._
import squants._

/**
 * @author  garyKeorkunian
 * @since   0.2.3
 *
 * @param value Double
 */
final class AreaDensity private (val value: Double, val unit: AreaDensityUnit)
    extends Quantity[AreaDensity] with DimensionType {
  type Dimension = (Length, _M2) :: (Mass, _1) :: HNil

  def dimension = AreaDensity

  def *(that: Area): Mass = Kilograms(toKilogramsPerSquareMeter * that.toSquareMeters)

  def toKilogramsPerSquareMeter = to(KilogramsPerSquareMeter)
  def toGramsPerSquareCentimeter = to(GramsPerSquareCentimeter)
  def toKilogramsPerHectare = to(KilogramsPerHectare)
}

/**
 * Factory singleton for [[squants.mass.AreaDensity]] values
 */
object AreaDensity extends Dimension[AreaDensity] {
  private[mass] def apply[A](n: A, unit: AreaDensityUnit)(implicit num: Numeric[A]) = new AreaDensity(num.toDouble(n), unit)
  def apply(mass: Mass, area: Area): AreaDensity = KilogramsPerSquareMeter(mass.toKilograms / area.toSquareMeters)
  def apply = parse _
  def name = "AreaDensity"
  def primaryUnit = KilogramsPerSquareMeter
  def siUnit = KilogramsPerSquareMeter
  def units = Set(KilogramsPerSquareMeter, KilogramsPerHectare, GramsPerSquareCentimeter)
}

trait AreaDensityUnit extends UnitOfMeasure[AreaDensity] {
  def apply[A](n: A)(implicit num: Numeric[A]) = AreaDensity(n, this)
}

object KilogramsPerSquareMeter extends AreaDensityUnit with PrimaryUnit with SiUnit {
  val symbol = "kg/m²"
}

object KilogramsPerHectare extends AreaDensityUnit with UnitConverter {
  val symbol = "kg/hectare"
  val conversionFactor = 1/(100*100d)
}

object GramsPerSquareCentimeter extends AreaDensityUnit with UnitConverter {
  val symbol = "g/cm²"
  val conversionFactor = (100*100d)/1000d
}

object AreaDensityConversions {
  lazy val kilogramPerSquareMeter = KilogramsPerSquareMeter(1)
  lazy val kilogramPerHectare = KilogramsPerHectare(1)
  lazy val gramPerSquareCentimeter = GramsPerSquareCentimeter(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSquareMeter = KilogramsPerSquareMeter(n)
    def kilogramsPerHectare = KilogramsPerHectare(n)
    def gramsPerSquareCentimeter = GramsPerSquareCentimeter(n)
  }

  implicit object AreaDensityNumeric extends AbstractQuantityNumeric[AreaDensity](AreaDensity.primaryUnit)
}