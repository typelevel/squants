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

final case class Conductivity[A: Numeric] private[squants2] (value: A, unit: ConductivityUnit)
  extends Quantity[A, Conductivity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): ElectricalConductance[A] = ???
  //  def inOhmMeters[B]()(implicit f: B => A): Resistivity[A] = ???
  // END CUSTOM OPS

  def toSiemensPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](SiemensPerMeter)
}

object Conductivity extends Dimension[Conductivity]("Conductivity") {

  override def primaryUnit: UnitOfMeasure[Conductivity] with PrimaryUnit[Conductivity] = SiemensPerMeter
  override def siUnit: UnitOfMeasure[Conductivity] with SiUnit[Conductivity] = SiemensPerMeter
  override lazy val units: Set[UnitOfMeasure[Conductivity]] = 
    Set(SiemensPerMeter)

  implicit class ConductivityCons[A](a: A)(implicit num: Numeric[A]) {
    def siemensPerMeter: Conductivity[A] = SiemensPerMeter(a)
  }

  lazy val siemensPerMeter: Conductivity[Int] = SiemensPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Conductivity] = ConductivityNumeric[A]()
  private case class ConductivityNumeric[A: Numeric]() extends QuantityNumeric[A, Conductivity](this) {
    override def times(x: Quantity[A, Conductivity], y: Quantity[A, Conductivity]): Quantity[A, Conductivity] =
      SiemensPerMeter(x.to(SiemensPerMeter) * y.to(SiemensPerMeter))
  }
}

abstract class ConductivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Conductivity] {
  override def dimension: Dimension[Conductivity] = Conductivity
  override def apply[A: Numeric](value: A): Conductivity[A] = Conductivity(value, this)
}

case object SiemensPerMeter extends ConductivityUnit("S/m", 1) with PrimaryUnit[Conductivity] with SiUnit[Conductivity]
