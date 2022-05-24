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

final case class DataRate[A: Numeric] private [squants2]  (value: A, unit: DataRateUnit)
  extends Quantity[A, DataRate.type] {
  override type Q[B] = DataRate[B]

  // BEGIN CUSTOM OPS
  //  def *[B](that: Time[B])(implicit f: B => A): Quantity[A] = ???
  //  def /[B, E <: Dimension](that: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???
  // END CUSTOM OPS

  def toBitsPerSecond: A = to(BitsPerSecond)
  def toBytesPerSecond: A = to(BytesPerSecond)
  def toKilobitsPerSecond: A = to(KilobitsPerSecond)
  def toKibibitsPerSecond: A = to(KibibitsPerSecond)
  def toKilobytesPerSecond: A = to(KilobytesPerSecond)
  def toKibibytesPerSecond: A = to(KibibytesPerSecond)
  def toMegabitsPerSecond: A = to(MegabitsPerSecond)
  def toMebibitsPerSecond: A = to(MebibitsPerSecond)
  def toMegabytesPerSecond: A = to(MegabytesPerSecond)
  def toMebibytesPerSecond: A = to(MebibytesPerSecond)
  def toGigabitsPerSecond: A = to(GigabitsPerSecond)
  def toGibibitsPerSecond: A = to(GibibitsPerSecond)
  def toGigabytesPerSecond: A = to(GigabytesPerSecond)
  def toGibibytesPerSecond: A = to(GibibytesPerSecond)
  def toTerabitsPerSecond: A = to(TerabitsPerSecond)
  def toTebibitsPerSecond: A = to(TebibitsPerSecond)
  def toTerabytesPerSecond: A = to(TerabytesPerSecond)
  def toTebibytesPerSecond: A = to(TebibytesPerSecond)
  def toPetabitsPerSecond: A = to(PetabitsPerSecond)
  def toPebibitsPerSecond: A = to(PebibitsPerSecond)
  def toPetabytesPerSecond: A = to(PetabytesPerSecond)
  def toPebibytesPerSecond: A = to(PebibytesPerSecond)
  def toExabitsPerSecond: A = to(ExabitsPerSecond)
  def toExbibitsPerSecond: A = to(ExbibitsPerSecond)
  def toExabytesPerSecond: A = to(ExabytesPerSecond)
  def toExbibytesPerSecond: A = to(ExbibytesPerSecond)
  def toZettabitsPerSecond: A = to(ZettabitsPerSecond)
  def toZebibitsPerSecond: A = to(ZebibitsPerSecond)
  def toZettabytesPerSecond: A = to(ZettabytesPerSecond)
  def toZebibytesPerSecond: A = to(ZebibytesPerSecond)
  def toYottabitsPerSecond: A = to(YottabitsPerSecond)
  def toYobibitsPerSecond: A = to(YobibitsPerSecond)
  def toYottabytesPerSecond: A = to(YottabytesPerSecond)
  def toYobibytesPerSecond: A = to(YobibytesPerSecond)
}

object DataRate extends Dimension("Data Rate") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = BytesPerSecond
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = BytesPerSecond
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(BitsPerSecond, BytesPerSecond, KilobitsPerSecond, KibibitsPerSecond, KilobytesPerSecond, KibibytesPerSecond, MegabitsPerSecond, MebibitsPerSecond, MegabytesPerSecond, MebibytesPerSecond, GigabitsPerSecond, GibibitsPerSecond, GigabytesPerSecond, GibibytesPerSecond, TerabitsPerSecond, TebibitsPerSecond, TerabytesPerSecond, TebibytesPerSecond, PetabitsPerSecond, PebibitsPerSecond, PetabytesPerSecond, PebibytesPerSecond, ExabitsPerSecond, ExbibitsPerSecond, ExabytesPerSecond, ExbibytesPerSecond, ZettabitsPerSecond, ZebibitsPerSecond, ZettabytesPerSecond, ZebibytesPerSecond, YottabitsPerSecond, YobibitsPerSecond, YottabytesPerSecond, YobibytesPerSecond)

  implicit class DataRateCons[A](a: A)(implicit num: Numeric[A]) {
    def bitsPerSecond: DataRate[A] = BitsPerSecond(a)
    def bytesPerSecond: DataRate[A] = BytesPerSecond(a)
    def kilobitsPerSecond: DataRate[A] = KilobitsPerSecond(a)
    def kibibitsPerSecond: DataRate[A] = KibibitsPerSecond(a)
    def kilobytesPerSecond: DataRate[A] = KilobytesPerSecond(a)
    def kibibytesPerSecond: DataRate[A] = KibibytesPerSecond(a)
    def megabitsPerSecond: DataRate[A] = MegabitsPerSecond(a)
    def mebibitsPerSecond: DataRate[A] = MebibitsPerSecond(a)
    def megabytesPerSecond: DataRate[A] = MegabytesPerSecond(a)
    def mebibytesPerSecond: DataRate[A] = MebibytesPerSecond(a)
    def gigabitsPerSecond: DataRate[A] = GigabitsPerSecond(a)
    def gibibitsPerSecond: DataRate[A] = GibibitsPerSecond(a)
    def gigabytesPerSecond: DataRate[A] = GigabytesPerSecond(a)
    def gibibytesPerSecond: DataRate[A] = GibibytesPerSecond(a)
    def terabitsPerSecond: DataRate[A] = TerabitsPerSecond(a)
    def tebibitsPerSecond: DataRate[A] = TebibitsPerSecond(a)
    def terabytesPerSecond: DataRate[A] = TerabytesPerSecond(a)
    def tebibytesPerSecond: DataRate[A] = TebibytesPerSecond(a)
    def petabitsPerSecond: DataRate[A] = PetabitsPerSecond(a)
    def pebibitsPerSecond: DataRate[A] = PebibitsPerSecond(a)
    def petabytesPerSecond: DataRate[A] = PetabytesPerSecond(a)
    def pebibytesPerSecond: DataRate[A] = PebibytesPerSecond(a)
    def exabitsPerSecond: DataRate[A] = ExabitsPerSecond(a)
    def exbibitsPerSecond: DataRate[A] = ExbibitsPerSecond(a)
    def exabytesPerSecond: DataRate[A] = ExabytesPerSecond(a)
    def exbibytesPerSecond: DataRate[A] = ExbibytesPerSecond(a)
    def zettabitsPerSecond: DataRate[A] = ZettabitsPerSecond(a)
    def zebibitsPerSecond: DataRate[A] = ZebibitsPerSecond(a)
    def zettabytesPerSecond: DataRate[A] = ZettabytesPerSecond(a)
    def zebibytesPerSecond: DataRate[A] = ZebibytesPerSecond(a)
    def yottabitsPerSecond: DataRate[A] = YottabitsPerSecond(a)
    def yobibitsPerSecond: DataRate[A] = YobibitsPerSecond(a)
    def yottabytesPerSecond: DataRate[A] = YottabytesPerSecond(a)
    def yobibytesPerSecond: DataRate[A] = YobibytesPerSecond(a)
  }

  lazy val bitsPerSecond: DataRate[Int] = BitsPerSecond(1)
  lazy val bytesPerSecond: DataRate[Int] = BytesPerSecond(1)
  lazy val kilobitsPerSecond: DataRate[Int] = KilobitsPerSecond(1)
  lazy val kibibitsPerSecond: DataRate[Int] = KibibitsPerSecond(1)
  lazy val kilobytesPerSecond: DataRate[Int] = KilobytesPerSecond(1)
  lazy val kibibytesPerSecond: DataRate[Int] = KibibytesPerSecond(1)
  lazy val megabitsPerSecond: DataRate[Int] = MegabitsPerSecond(1)
  lazy val mebibitsPerSecond: DataRate[Int] = MebibitsPerSecond(1)
  lazy val megabytesPerSecond: DataRate[Int] = MegabytesPerSecond(1)
  lazy val mebibytesPerSecond: DataRate[Int] = MebibytesPerSecond(1)
  lazy val gigabitsPerSecond: DataRate[Int] = GigabitsPerSecond(1)
  lazy val gibibitsPerSecond: DataRate[Int] = GibibitsPerSecond(1)
  lazy val gigabytesPerSecond: DataRate[Int] = GigabytesPerSecond(1)
  lazy val gibibytesPerSecond: DataRate[Int] = GibibytesPerSecond(1)
  lazy val terabitsPerSecond: DataRate[Int] = TerabitsPerSecond(1)
  lazy val tebibitsPerSecond: DataRate[Int] = TebibitsPerSecond(1)
  lazy val terabytesPerSecond: DataRate[Int] = TerabytesPerSecond(1)
  lazy val tebibytesPerSecond: DataRate[Int] = TebibytesPerSecond(1)
  lazy val petabitsPerSecond: DataRate[Int] = PetabitsPerSecond(1)
  lazy val pebibitsPerSecond: DataRate[Int] = PebibitsPerSecond(1)
  lazy val petabytesPerSecond: DataRate[Int] = PetabytesPerSecond(1)
  lazy val pebibytesPerSecond: DataRate[Int] = PebibytesPerSecond(1)
  lazy val exabitsPerSecond: DataRate[Int] = ExabitsPerSecond(1)
  lazy val exbibitsPerSecond: DataRate[Int] = ExbibitsPerSecond(1)
  lazy val exabytesPerSecond: DataRate[Int] = ExabytesPerSecond(1)
  lazy val exbibytesPerSecond: DataRate[Int] = ExbibytesPerSecond(1)
  lazy val zettabitsPerSecond: DataRate[Int] = ZettabitsPerSecond(1)
  lazy val zebibitsPerSecond: DataRate[Int] = ZebibitsPerSecond(1)
  lazy val zettabytesPerSecond: DataRate[Int] = ZettabytesPerSecond(1)
  lazy val zebibytesPerSecond: DataRate[Int] = ZebibytesPerSecond(1)
  lazy val yottabitsPerSecond: DataRate[Int] = YottabitsPerSecond(1)
  lazy val yobibitsPerSecond: DataRate[Int] = YobibitsPerSecond(1)
  lazy val yottabytesPerSecond: DataRate[Int] = YottabytesPerSecond(1)
  lazy val yobibytesPerSecond: DataRate[Int] = YobibytesPerSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = DataRateNumeric[A]()
  private case class DataRateNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, DataRate.type], y: Quantity[A, DataRate.type]): Quantity[A, DataRate.this.type] =
      BytesPerSecond(x.to(BytesPerSecond) * y.to(BytesPerSecond))
  }
}

abstract class DataRateUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[DataRate.type] {
  override def dimension: DataRate.type = DataRate
  override def apply[A: Numeric](value: A): DataRate[A] = DataRate(value, this)
}

case object BitsPerSecond extends DataRateUnit("bps", 0.125)
case object BytesPerSecond extends DataRateUnit("B/s", 1) with PrimaryUnit with SiUnit
case object KilobitsPerSecond extends DataRateUnit("Kbps", 125)
case object KibibitsPerSecond extends DataRateUnit("Kibps", 128)
case object KilobytesPerSecond extends DataRateUnit("KB/s", MetricSystem.Kilo)
case object KibibytesPerSecond extends DataRateUnit("KiB/s", BinarySystem.Kilo)
case object MegabitsPerSecond extends DataRateUnit("Mbps", 125000)
case object MebibitsPerSecond extends DataRateUnit("Mibps", 131072)
case object MegabytesPerSecond extends DataRateUnit("MB/s", MetricSystem.Mega)
case object MebibytesPerSecond extends DataRateUnit("MiB/s", BinarySystem.Mega)
case object GigabitsPerSecond extends DataRateUnit("Gbps", 125000000)
case object GibibitsPerSecond extends DataRateUnit("Gibps", 134217728)
case object GigabytesPerSecond extends DataRateUnit("GB/s", MetricSystem.Giga)
case object GibibytesPerSecond extends DataRateUnit("GiB/s", BinarySystem.Giga)
case object TerabitsPerSecond extends DataRateUnit("Tbps", 1.25E11)
case object TebibitsPerSecond extends DataRateUnit("Tibps", 1.37438953472E11)
case object TerabytesPerSecond extends DataRateUnit("TB/s", MetricSystem.Tera)
case object TebibytesPerSecond extends DataRateUnit("TiB/s", BinarySystem.Tera)
case object PetabitsPerSecond extends DataRateUnit("Pbps", 1.25E14)
case object PebibitsPerSecond extends DataRateUnit("Pibps", 1.40737488355328E14)
case object PetabytesPerSecond extends DataRateUnit("PB/s", MetricSystem.Peta)
case object PebibytesPerSecond extends DataRateUnit("PiB/s", BinarySystem.Peta)
case object ExabitsPerSecond extends DataRateUnit("Ebps", 1.25E17)
case object ExbibitsPerSecond extends DataRateUnit("Eibps", 1.44115188075855872E17)
case object ExabytesPerSecond extends DataRateUnit("EB/s", MetricSystem.Exa)
case object ExbibytesPerSecond extends DataRateUnit("EiB/s", BinarySystem.Exa)
case object ZettabitsPerSecond extends DataRateUnit("Zbps", 1.25E20)
case object ZebibitsPerSecond extends DataRateUnit("Zibps", 1.4757395258967641E20)
case object ZettabytesPerSecond extends DataRateUnit("ZB/s", MetricSystem.Zetta)
case object ZebibytesPerSecond extends DataRateUnit("ZiB/s", BinarySystem.Zetta)
case object YottabitsPerSecond extends DataRateUnit("Ybps", 1.25E23)
case object YobibitsPerSecond extends DataRateUnit("Yibps", 1.5111572745182865E23)
case object YottabytesPerSecond extends DataRateUnit("YB/s", MetricSystem.Yotta)
case object YobibytesPerSecond extends DataRateUnit("YiB/s", BinarySystem.Yotta)
