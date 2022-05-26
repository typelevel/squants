/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class SpectralPower[A: Numeric] private[squants2] (value: A, unit: SpectralPowerUnit)
  extends Quantity[A, SpectralPower] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): Power[A] = ???
  //  def /[B](that: Power[B])(implicit f: B => A): Length[A] = ???
  // END CUSTOM OPS

  def toWattsPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerMeter)
}

object SpectralPower extends Dimension[SpectralPower]("Spectral Power") {

  override def primaryUnit: UnitOfMeasure[SpectralPower] with PrimaryUnit[SpectralPower] = WattsPerMeter
  override def siUnit: UnitOfMeasure[SpectralPower] with SiUnit[SpectralPower] = WattsPerMeter
  override lazy val units: Set[UnitOfMeasure[SpectralPower]] = 
    Set(WattsPerMeter)

  implicit class SpectralPowerCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerMeter: SpectralPower[A] = WattsPerMeter(a)
  }

  lazy val wattsPerMeter: SpectralPower[Int] = WattsPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, SpectralPower] = SpectralPowerNumeric[A]()
  private case class SpectralPowerNumeric[A: Numeric]() extends QuantityNumeric[A, SpectralPower](this) {
    override def times(x: Quantity[A, SpectralPower], y: Quantity[A, SpectralPower]): Quantity[A, SpectralPower] =
      WattsPerMeter(x.to(WattsPerMeter) * y.to(WattsPerMeter))
  }
}

abstract class SpectralPowerUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpectralPower] {
  override def dimension: Dimension[SpectralPower] = SpectralPower
  override def apply[A: Numeric](value: A): SpectralPower[A] = SpectralPower(value, this)
}

case object WattsPerMeter extends SpectralPowerUnit("W/m", 1) with PrimaryUnit[SpectralPower] with SiUnit[SpectralPower]
