package squants.electro

import squants.space.{Length, Meters}
import squants.{AbstractQuantityNumeric, Dimension, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  * @param value Double
  */
final class Permittivity private (val value: Double, val unit: PermittivityUnit)
    extends Quantity[Permittivity] {

  def dimension = Permittivity

  def *(that: Length): Capacitance = Farads(this.toFaradsMeters * that.toMeters)

  def toFaradsMeters = to(FaradsMeters)
}

object Permittivity extends Dimension[Permittivity] {
  private[electro] def apply[A](n: A, unit: PermittivityUnit)(implicit num: Numeric[A]) = new Permittivity(num.toDouble(n), unit)
  def apply = parse _
  def name = "Permittivity"
  def primaryUnit = FaradsMeters
  def siUnit = FaradsMeters
  def units = Set(FaradsMeters)
}

trait PermittivityUnit extends UnitOfMeasure[Permittivity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Permittivity(n, this)
}

object FaradsMeters extends PermittivityUnit with PrimaryUnit with SiUnit {
  def symbol = Farads.symbol + "/" + Meters.symbol
}

object PermittivityConversions {
  lazy val faradMeter = FaradsMeters(1)

  implicit class PermittivityConversions[A](n: A)(implicit num: Numeric[A]) {
    def faradsMeters = FaradsMeters(n)
  }

  implicit object PermittivityNumeric extends AbstractQuantityNumeric[Permittivity](Permittivity.primaryUnit)
}
