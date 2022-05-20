package squants2.space

import squants2.QNumeric.QNumericOps
import squants2._

final case class Area[A: QNumeric] private [space]  (value: A, unit: AreaUnit) extends Quantity[A, Area.type] {
  override type Q[B] = Area[B]

  def /[B](that: Length[B])(implicit f: B => A): Length[A] = Meters(to(SquareMeters) * that.asNum[A].to(Meters))
  def *[B](that: Length[B])(implicit f: B => A): Volume[A] = CubicMeters(to(SquareMeters) * that.asNum[A].to(Meters))

}

object Area extends Dimension("Area") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = SquareMeters
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = SquareMeters
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(SquareMeters, SquareFeet)

  // Constructors from QNumeric values
  implicit class AreaCons[A: QNumeric](a: A) {
    def squareMeters: Area[A] = SquareMeters(a)
  }

  // Constants
  lazy val squareMeter: Area[Int] = SquareMeters(1)

}

abstract class AreaUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[Area.type] {
  override def dimension: Area.type = Area
  override def apply[A: QNumeric](value: A): Area[A] = Area(value, this)
}

case object SquareMeters extends AreaUnit("m²", 1) with PrimaryUnit with SiUnit
case object SquareFeet extends AreaUnit("ft²", 9.290304e-2)
