/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class Permeability[A: Numeric] private[squants2] (value: A, unit: PermeabilityUnit)
  extends Quantity[A, Permeability] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): Inductance[A] = ???
  // END CUSTOM OPS

  def toHenriesPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](HenriesPerMeter)
  def toNewtonsPerAmperesSquared[B: Numeric](implicit f: A => B): B = toNum[B](NewtonsPerAmperesSquared)
}

object Permeability extends Dimension[Permeability]("Permeability") {

  override def primaryUnit: UnitOfMeasure[Permeability] with PrimaryUnit[Permeability] = HenriesPerMeter
  override def siUnit: UnitOfMeasure[Permeability] with SiUnit[Permeability] = HenriesPerMeter
  override lazy val units: Set[UnitOfMeasure[Permeability]] = 
    Set(HenriesPerMeter, NewtonsPerAmperesSquared)

  implicit class PermeabilityCons[A](a: A)(implicit num: Numeric[A]) {
    def henriesPerMeter: Permeability[A] = HenriesPerMeter(a)
    def newtonsPerAmperesSquared: Permeability[A] = NewtonsPerAmperesSquared(a)
  }

  lazy val henriePerMeter: Permeability[Int] = HenriesPerMeter(1)
  lazy val newtonPerAmperesSquared: Permeability[Int] = NewtonsPerAmperesSquared(1)

}

abstract class PermeabilityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Permeability] {
  override def dimension: Dimension[Permeability] = Permeability
  override def apply[A: Numeric](value: A): Permeability[A] = Permeability(value, this)
}

case object HenriesPerMeter extends PermeabilityUnit("H/m", 1) with PrimaryUnit[Permeability] with SiUnit[Permeability]
case object NewtonsPerAmperesSquared extends PermeabilityUnit("N/A²", 1) with PrimaryUnit[Permeability] with SiUnit[Permeability]
