/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Permeability[A: Numeric] private [squants2]  (value: A, unit: PermeabilityUnit)
  extends Quantity[A, Permeability.type] {
  override type Q[B] = Permeability[B]

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): Inductance[A] = ???
  // END CUSTOM OPS

  def toHenriesPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](HenriesPerMeter)
  def toNewtonsPerAmperesSquared[B: Numeric](implicit f: A => B): B = toNum[B](NewtonsPerAmperesSquared)
}

object Permeability extends Dimension("Permeability") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = HenriesPerMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = HenriesPerMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(HenriesPerMeter, NewtonsPerAmperesSquared)

  implicit class PermeabilityCons[A](a: A)(implicit num: Numeric[A]) {
    def henriesPerMeter: Permeability[A] = HenriesPerMeter(a)
    def newtonsPerAmperesSquared: Permeability[A] = NewtonsPerAmperesSquared(a)
  }

  lazy val henriesPerMeter: Permeability[Int] = HenriesPerMeter(1)
  lazy val newtonsPerAmperesSquared: Permeability[Int] = NewtonsPerAmperesSquared(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PermeabilityNumeric[A]()
  private case class PermeabilityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Permeability.type], y: Quantity[A, Permeability.type]): Quantity[A, Permeability.this.type] =
      HenriesPerMeter(x.to(HenriesPerMeter) * y.to(HenriesPerMeter))
  }
}

abstract class PermeabilityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Permeability.type] {
  override def dimension: Permeability.type = Permeability
  override def apply[A: Numeric](value: A): Permeability[A] = Permeability(value, this)
}

case object HenriesPerMeter extends PermeabilityUnit("H/m", 1) with PrimaryUnit with SiUnit
case object NewtonsPerAmperesSquared extends PermeabilityUnit("N/A²", 1) with PrimaryUnit with SiUnit
