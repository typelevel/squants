/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2018, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants._

/**
 * @author  Hunter Payne
 *
 * @param value Double
 */
final class ParticleFlux private (
  val value: Double, val unit: ParticleFluxUnit)
    extends Quantity[ParticleFlux] {

  def dimension = ParticleFlux

  def *(that: AreaTime): Activity = 
    Becquerels(
      this.toBecquerelsPerSquareMeterSecond * that.toSquareMeterSeconds)
  // the 3600.0 is to convert watt hours to watt second which isn't a normal
  // supported type in Squants
  def *(that: Energy): Irradiance = WattsPerSquareMeter(
    3600.0 * that.toWattHours * this.toBecquerelsPerSquareMeterSecond)

  def toBecquerelsPerSquareMeterSecond = to(BecquerelsPerSquareMeterSecond)
  def toBecquerelsPerSquareCentimeterSecond = 
    to(BecquerelsPerSquareCentimeterSecond)
}

object ParticleFlux extends Dimension[ParticleFlux] {
  private[radio] def apply[A](n: A, unit: ParticleFluxUnit)(
    implicit num: Numeric[A]) = 
    new ParticleFlux(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ParticleFlux"
  def primaryUnit = BecquerelsPerSquareMeterSecond
  def siUnit = BecquerelsPerSquareMeterSecond
  def units = 
    Set(BecquerelsPerSquareMeterSecond, BecquerelsPerSquareCentimeterSecond)
}

trait ParticleFluxUnit 
    extends UnitOfMeasure[ParticleFlux] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ParticleFlux(n, this)
}

object BecquerelsPerSquareCentimeterSecond extends ParticleFluxUnit {
  val conversionFactor = 10000.0 //0.0001
  val symbol = Becquerels.symbol + "/cm²‧s"
}

object BecquerelsPerSquareMeterSecond
    extends ParticleFluxUnit with PrimaryUnit with SiUnit {
  val symbol = Becquerels.symbol + "/m²‧s"
}

object ParticleFluxConversions {
  lazy val becquerelPerSquareMeterSecond = BecquerelsPerSquareMeterSecond(1)
  lazy val becquerelPerSquareCentimeterSecond = 
    BecquerelsPerSquareCentimeterSecond(1)

  implicit class ParticleFluxConversions[A](n: A)(
    implicit num: Numeric[A]) {
    def becquerelsPerSquareMeterSecond = BecquerelsPerSquareMeterSecond(n)
    def becquerelsPerSquareCentimeterSecond = 
      BecquerelsPerSquareCentimeterSecond(n)
  }

  implicit object ParticleFluxNumeric 
      extends AbstractQuantityNumeric[ParticleFlux](ParticleFlux.primaryUnit)
}
