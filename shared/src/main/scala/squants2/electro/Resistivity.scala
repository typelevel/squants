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

final case class Resistivity[A: Numeric] private[squants2] (value: A, unit: ResistivityUnit)
  extends Quantity[A, Resistivity] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Length[B])(implicit f: B => A): ElectricalResistance[A] = ???
  //  def /[B](that: ElectricalResistance[B])(implicit f: B => A): Length[A] = ???
  //  def inSiemensPerMeter[B]()(implicit f: B => A): Conductivity[A] = ???
  // END CUSTOM OPS

  def toOhmMeters[B: Numeric](implicit f: A => B): B = toNum[B](OhmMeters)
}

object Resistivity extends Dimension[Resistivity]("Resistivity") {

  override def primaryUnit: UnitOfMeasure[Resistivity] with PrimaryUnit[Resistivity] = OhmMeters
  override def siUnit: UnitOfMeasure[Resistivity] with SiUnit[Resistivity] = OhmMeters
  override lazy val units: Set[UnitOfMeasure[Resistivity]] = 
    Set(OhmMeters)

  implicit class ResistivityCons[A](a: A)(implicit num: Numeric[A]) {
    def ohmMeters: Resistivity[A] = OhmMeters(a)
  }

  lazy val ohmMeters: Resistivity[Int] = OhmMeters(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Resistivity] = ResistivityNumeric[A]()
  private case class ResistivityNumeric[A: Numeric]() extends QuantityNumeric[A, Resistivity](this) {
    override def times(x: Quantity[A, Resistivity], y: Quantity[A, Resistivity]): Quantity[A, Resistivity] =
      OhmMeters(x.to(OhmMeters) * y.to(OhmMeters))
  }
}

abstract class ResistivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Resistivity] {
  override def dimension: Dimension[Resistivity] = Resistivity
  override def apply[A: Numeric](value: A): Resistivity[A] = Resistivity(value, this)
}

case object OhmMeters extends ResistivityUnit("Ω⋅m", 1) with PrimaryUnit[Resistivity] with SiUnit[Resistivity]
