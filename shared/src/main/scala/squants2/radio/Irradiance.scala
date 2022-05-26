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

final case class Irradiance[A: Numeric] private[squants2] (value: A, unit: IrradianceUnit)
  extends Quantity[A, Irradiance] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Area[B])(implicit f: B => A): Power[A] = ???
  //  def *[B](that: AreaTime[B])(implicit f: B => A): Energy[A] = ???
  //  def /[B](that: Energy[B])(implicit f: B => A): ParticleFlux[A] = ???
  //  def /[B](that: ParticleFlux[B])(implicit f: B => A): Energy[A] = ???
  // END CUSTOM OPS

  def toErgsPerSecondPerSquareCentimeter[B: Numeric](implicit f: A => B): B = toNum[B](ErgsPerSecondPerSquareCentimeter)
  def toWattsPerSquareMeter[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerSquareMeter)
}

object Irradiance extends Dimension[Irradiance]("Irradiance") {

  override def primaryUnit: UnitOfMeasure[Irradiance] with PrimaryUnit[Irradiance] = WattsPerSquareMeter
  override def siUnit: UnitOfMeasure[Irradiance] with SiUnit[Irradiance] = WattsPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[Irradiance]] = 
    Set(ErgsPerSecondPerSquareCentimeter, WattsPerSquareMeter)

  implicit class IrradianceCons[A](a: A)(implicit num: Numeric[A]) {
    def ergsPerSecondPerSquareCentimeter: Irradiance[A] = ErgsPerSecondPerSquareCentimeter(a)
    def wattsPerSquareMeter: Irradiance[A] = WattsPerSquareMeter(a)
  }

  lazy val ergsPerSecondPerSquareCentimeter: Irradiance[Int] = ErgsPerSecondPerSquareCentimeter(1)
  lazy val wattsPerSquareMeter: Irradiance[Int] = WattsPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, Irradiance] = IrradianceNumeric[A]()
  private case class IrradianceNumeric[A: Numeric]() extends QuantityNumeric[A, Irradiance](this) {
    override def times(x: Quantity[A, Irradiance], y: Quantity[A, Irradiance]): Quantity[A, Irradiance] =
      WattsPerSquareMeter(x.to(WattsPerSquareMeter) * y.to(WattsPerSquareMeter))
  }
}

abstract class IrradianceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Irradiance] {
  override def dimension: Dimension[Irradiance] = Irradiance
  override def apply[A: Numeric](value: A): Irradiance[A] = Irradiance(value, this)
}

case object ErgsPerSecondPerSquareCentimeter extends IrradianceUnit("erg/s/cm²", 9.999999999999998E-4)
case object WattsPerSquareMeter extends IrradianceUnit("W/m²", 1) with PrimaryUnit[Irradiance] with SiUnit[Irradiance]
