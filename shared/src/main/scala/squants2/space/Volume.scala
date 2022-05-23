package squants2.space

import squants2._

final case class Volume[A: Numeric] private [space] (value: A, unit: VolumeUnit) extends Quantity[A, Volume.type] {
  override type Q[B] = Volume[B]

  def /[B](that: Length[B])(implicit f: B => A): Area[A] = SquareMeters(to(CubicMeters) / that.asNum[A].to(Meters))
  def /[B](that: Area[B])(implicit f: B => A): Length[A] = Meters(to(CubicMeters) / that.asNum[A].to(SquareMeters))

}

object Volume extends Dimension("Volume") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = CubicMeters
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = CubicMeters
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(CubicMeters, CubicFeet)

  // Constructors from Numeric values
  implicit class VolumeCons[A: Numeric](a: A) {
    def cubicMeters: Volume[A] = CubicMeters(a)
  }

  // Constants
  lazy val cubicMeter: Volume[Int] = CubicMeters(1)

}

abstract class VolumeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Volume.type] {
  override def dimension: Volume.type = Volume
  override def apply[A: Numeric](value: A): Volume[A] = Volume(value, this)
}

case object CubicMeters extends VolumeUnit("m³", 1) with PrimaryUnit with SiBaseUnit
case object CubicFeet extends VolumeUnit("ft³", 9.290304e-2)
