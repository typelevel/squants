/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.information

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Information[A: Numeric] private [squants2]  (value: A, unit: InformationUnit)
  extends Quantity[A, Information.type] {
  override type Q[B] = Information[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toBits: A = to(Bits)
  def toBytes: A = to(Bytes)
  def toKilobits: A = to(Kilobits)
  def toKibibits: A = to(Kibibits)
  def toKilobytes: A = to(Kilobytes)
  def toKibibytes: A = to(Kibibytes)
  def toMegabits: A = to(Megabits)
  def toMebibits: A = to(Mebibits)
  def toMegabytes: A = to(Megabytes)
  def toMebibytes: A = to(Mebibytes)
  def toGigabits: A = to(Gigabits)
  def toGibibits: A = to(Gibibits)
  def toGigabytes: A = to(Gigabytes)
  def toGibibytes: A = to(Gibibytes)
  def toTerabits: A = to(Terabits)
  def toTebibits: A = to(Tebibits)
  def toTerabytes: A = to(Terabytes)
  def toTebibytes: A = to(Tebibytes)
  def toPetabits: A = to(Petabits)
  def toPebibits: A = to(Pebibits)
  def toPetabytes: A = to(Petabytes)
  def toPebibytes: A = to(Pebibytes)
  def toExabits: A = to(Exabits)
  def toExbibits: A = to(Exbibits)
  def toExabytes: A = to(Exabytes)
  def toExbibytes: A = to(Exbibytes)
  def toZettabits: A = to(Zettabits)
  def toZebibits: A = to(Zebibits)
  def toZettabytes: A = to(Zettabytes)
  def toZebibytes: A = to(Zebibytes)
  def toYottabits: A = to(Yottabits)
  def toYobibits: A = to(Yobibits)
  def toYottabytes: A = to(Yottabytes)
  def toYobibytes: A = to(Yobibytes)
}

object Information extends BaseDimension("Information", "B") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Bytes
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Bytes
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Bits, Bytes, Kilobits, Kibibits, Kilobytes, Kibibytes, Megabits, Mebibits, Megabytes, Mebibytes, Gigabits, Gibibits, Gigabytes, Gibibytes, Terabits, Tebibits, Terabytes, Tebibytes, Petabits, Pebibits, Petabytes, Pebibytes, Exabits, Exbibits, Exabytes, Exbibytes, Zettabits, Zebibits, Zettabytes, Zebibytes, Yottabits, Yobibits, Yottabytes, Yobibytes)

  implicit class InformationCons[A](a: A)(implicit num: Numeric[A]) {
    def bits: Information[A] = Bits(a)
    def bytes: Information[A] = Bytes(a)
    def kilobits: Information[A] = Kilobits(a)
    def kibibits: Information[A] = Kibibits(a)
    def kilobytes: Information[A] = Kilobytes(a)
    def kibibytes: Information[A] = Kibibytes(a)
    def megabits: Information[A] = Megabits(a)
    def mebibits: Information[A] = Mebibits(a)
    def megabytes: Information[A] = Megabytes(a)
    def mebibytes: Information[A] = Mebibytes(a)
    def gigabits: Information[A] = Gigabits(a)
    def gibibits: Information[A] = Gibibits(a)
    def gigabytes: Information[A] = Gigabytes(a)
    def gibibytes: Information[A] = Gibibytes(a)
    def terabits: Information[A] = Terabits(a)
    def tebibits: Information[A] = Tebibits(a)
    def terabytes: Information[A] = Terabytes(a)
    def tebibytes: Information[A] = Tebibytes(a)
    def petabits: Information[A] = Petabits(a)
    def pebibits: Information[A] = Pebibits(a)
    def petabytes: Information[A] = Petabytes(a)
    def pebibytes: Information[A] = Pebibytes(a)
    def exabits: Information[A] = Exabits(a)
    def exbibits: Information[A] = Exbibits(a)
    def exabytes: Information[A] = Exabytes(a)
    def exbibytes: Information[A] = Exbibytes(a)
    def zettabits: Information[A] = Zettabits(a)
    def zebibits: Information[A] = Zebibits(a)
    def zettabytes: Information[A] = Zettabytes(a)
    def zebibytes: Information[A] = Zebibytes(a)
    def yottabits: Information[A] = Yottabits(a)
    def yobibits: Information[A] = Yobibits(a)
    def yottabytes: Information[A] = Yottabytes(a)
    def yobibytes: Information[A] = Yobibytes(a)
  }

  lazy val bits: Information[Int] = Bits(1)
  lazy val bytes: Information[Int] = Bytes(1)
  lazy val kilobits: Information[Int] = Kilobits(1)
  lazy val kibibits: Information[Int] = Kibibits(1)
  lazy val kilobytes: Information[Int] = Kilobytes(1)
  lazy val kibibytes: Information[Int] = Kibibytes(1)
  lazy val megabits: Information[Int] = Megabits(1)
  lazy val mebibits: Information[Int] = Mebibits(1)
  lazy val megabytes: Information[Int] = Megabytes(1)
  lazy val mebibytes: Information[Int] = Mebibytes(1)
  lazy val gigabits: Information[Int] = Gigabits(1)
  lazy val gibibits: Information[Int] = Gibibits(1)
  lazy val gigabytes: Information[Int] = Gigabytes(1)
  lazy val gibibytes: Information[Int] = Gibibytes(1)
  lazy val terabits: Information[Int] = Terabits(1)
  lazy val tebibits: Information[Int] = Tebibits(1)
  lazy val terabytes: Information[Int] = Terabytes(1)
  lazy val tebibytes: Information[Int] = Tebibytes(1)
  lazy val petabits: Information[Int] = Petabits(1)
  lazy val pebibits: Information[Int] = Pebibits(1)
  lazy val petabytes: Information[Int] = Petabytes(1)
  lazy val pebibytes: Information[Int] = Pebibytes(1)
  lazy val exabits: Information[Int] = Exabits(1)
  lazy val exbibits: Information[Int] = Exbibits(1)
  lazy val exabytes: Information[Int] = Exabytes(1)
  lazy val exbibytes: Information[Int] = Exbibytes(1)
  lazy val zettabits: Information[Int] = Zettabits(1)
  lazy val zebibits: Information[Int] = Zebibits(1)
  lazy val zettabytes: Information[Int] = Zettabytes(1)
  lazy val zebibytes: Information[Int] = Zebibytes(1)
  lazy val yottabits: Information[Int] = Yottabits(1)
  lazy val yobibits: Information[Int] = Yobibits(1)
  lazy val yottabytes: Information[Int] = Yottabytes(1)
  lazy val yobibytes: Information[Int] = Yobibytes(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = InformationNumeric[A]()
  private case class InformationNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Information.type], y: Quantity[A, Information.type]): Quantity[A, Information.this.type] =
      Bytes(x.to(Bytes) * y.to(Bytes))
  }
}

abstract class InformationUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Information.type] {
  override def dimension: Information.type = Information
  override def apply[A: Numeric](value: A): Information[A] = Information(value, this)
}

case object Bits extends InformationUnit("bit", 0.125)
case object Bytes extends InformationUnit("B", 1) with PrimaryUnit with SiBaseUnit
case object Kilobits extends InformationUnit("Kbit", 125)
case object Kibibits extends InformationUnit("Kibit", 128)
case object Kilobytes extends InformationUnit("KB", MetricSystem.Kilo)
case object Kibibytes extends InformationUnit("KiB", BinarySystem.Kilo)
case object Megabits extends InformationUnit("Mbit", 125000)
case object Mebibits extends InformationUnit("Mibit", 131072)
case object Megabytes extends InformationUnit("MB", MetricSystem.Mega)
case object Mebibytes extends InformationUnit("MiB", BinarySystem.Mega)
case object Gigabits extends InformationUnit("Gbit", 125000000)
case object Gibibits extends InformationUnit("Gibit", 134217728)
case object Gigabytes extends InformationUnit("GB", MetricSystem.Giga)
case object Gibibytes extends InformationUnit("GiB", BinarySystem.Giga)
case object Terabits extends InformationUnit("Tbit", 1.25E11)
case object Tebibits extends InformationUnit("Tibit", 1.37438953472E11)
case object Terabytes extends InformationUnit("TB", MetricSystem.Tera)
case object Tebibytes extends InformationUnit("TiB", BinarySystem.Tera)
case object Petabits extends InformationUnit("Pbit", 1.25E14)
case object Pebibits extends InformationUnit("Pibit", 1.40737488355328E14)
case object Petabytes extends InformationUnit("PB", MetricSystem.Peta)
case object Pebibytes extends InformationUnit("PiB", BinarySystem.Peta)
case object Exabits extends InformationUnit("Ebit", 1.25E17)
case object Exbibits extends InformationUnit("Eibit", 1.44115188075855872E17)
case object Exabytes extends InformationUnit("EB", MetricSystem.Exa)
case object Exbibytes extends InformationUnit("EiB", BinarySystem.Exa)
case object Zettabits extends InformationUnit("Zbit", 1.25E20)
case object Zebibits extends InformationUnit("Zibit", 1.4757395258967641E20)
case object Zettabytes extends InformationUnit("ZB", MetricSystem.Zetta)
case object Zebibytes extends InformationUnit("ZiB", BinarySystem.Zetta)
case object Yottabits extends InformationUnit("Ybit", 1.25E23)
case object Yobibits extends InformationUnit("Yibit", 1.5111572745182865E23)
case object Yottabytes extends InformationUnit("YB", MetricSystem.Yotta)
case object Yobibytes extends InformationUnit("YiB", BinarySystem.Yotta)
