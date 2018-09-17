package squants.information

import squants._
import squants.time.TimeDerivative

/**
  * Represents a rate of transfer of information
  */
final class DataRate private(val value: Double, val unit: DataRateUnit)
    extends Quantity[DataRate]
    with TimeDerivative[Information] {

  def dimension = DataRate
  protected[squants] def timeIntegrated = Bytes(toBytesPerSecond)
  protected[squants] def time = Seconds(1)

  def toBytesPerSecond = to(BytesPerSecond)
  def toKilobytesPerSecond = to(KilobytesPerSecond)
  def toMegabytesPerSecond = to(MegabytesPerSecond)
  def toGigabytesPerSecond = to(GigabytesPerSecond)
  def toTerabytesPerSecond = to(TerabytesPerSecond)
  def toPetabytesPerSecond = to(PetabytesPerSecond)
  def toExabytesPerSecond = to(ExabytesPerSecond)
  def toZettabytesPerSecond = to(ZettabytesPerSecond)
  def toYottabytesPerSecond = to(YottabytesPerSecond)

  def toKibibytesPerSecond = to(KibibytesPerSecond)
  def toMebibytesPerSecond = to(MebibytesPerSecond)
  def toGibibytesPerSecond = to(GibibytesPerSecond)
  def toTebibytesPerSecond = to(TebibytesPerSecond)
  def toPebibytesPerSecond = to(PebibytesPerSecond)
  def toExbibytesPerSecond = to(ExbibytesPerSecond)
  def toZebibytesPerSecond = to(ZebibytesPerSecond)
  def toYobibytesPerSecond = to(YobibytesPerSecond)

  def toBitsPerSecond = to(BitsPerSecond)
  def toKilobitsPerSecond = to(KilobitsPerSecond)
  def toMegabitsPerSecond = to(MegabitsPerSecond)
  def toGigabitsPerSecond = to(GigabitsPerSecond)
  def toTerabitsPerSecond = to(TerabitsPerSecond)
  def toPetabitsPerSecond = to(PetabitsPerSecond)
  def toExabitsPerSecond = to(ExabitsPerSecond)
  def toZettabitsPerSecond = to(ZettabitsPerSecond)
  def toYottabitsPerSecond = to(YottabitsPerSecond)

  def toKibibitsPerSecond = to(KibibitsPerSecond)
  def toMebibitsPerSecond = to(MebibitsPerSecond)
  def toGibibitsPerSecond = to(GibibitsPerSecond)
  def toTebibitsPerSecond = to(TebibitsPerSecond)
  def toPebibitsPerSecond = to(PebibitsPerSecond)
  def toExbibitsPerSecond = to(ExbibitsPerSecond)
  def toZebibitsPerSecond = to(ZebibitsPerSecond)
  def toYobibitsPerSecond = to(YobibitsPerSecond)
}


object DataRate extends Dimension[DataRate] {
  private[information] def apply[A](n: A, unit: DataRateUnit)(implicit num: Numeric[A]) =
    new DataRate(num.toDouble(n), unit)

  def apply(i: Information, t: Time) = BytesPerSecond(i.toBytes / t.toSeconds)
  def apply(value: Any) = parse(value)
  def name = "DataRate"
  def primaryUnit = BytesPerSecond
  def siUnit = BytesPerSecond
  def units = Set(BytesPerSecond, KilobytesPerSecond, KibibytesPerSecond, MegabytesPerSecond, MebibytesPerSecond,
    GigabytesPerSecond, GibibytesPerSecond, TerabytesPerSecond, TebibytesPerSecond,
    PetabytesPerSecond, PebibytesPerSecond, ExabytesPerSecond, ExbibytesPerSecond,
    ZettabytesPerSecond, ZebibytesPerSecond, YottabytesPerSecond, YobibytesPerSecond,
    BitsPerSecond, KilobitsPerSecond, KibibitsPerSecond, MegabitsPerSecond, MebibitsPerSecond,
    GigabitsPerSecond, GibibitsPerSecond, TerabitsPerSecond, TebibitsPerSecond,
    PetabitsPerSecond, PebibitsPerSecond, ExabitsPerSecond, ExbibitsPerSecond,
    ZettabitsPerSecond, ZebibitsPerSecond, YottabitsPerSecond, YobibitsPerSecond)
}

trait DataRateUnit extends UnitOfMeasure[DataRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]): DataRate = DataRate(n, this)
}

object BytesPerSecond extends DataRateUnit with PrimaryUnit with SiUnit {
  val symbol = "B/s"
}

object KilobytesPerSecond extends DataRateUnit {
  val symbol = "KB/s"
  val conversionFactor = Kilobytes.conversionFactor
}

object KibibytesPerSecond extends DataRateUnit {
  val symbol = "KiB/s"
  val conversionFactor = Kibibytes.conversionFactor
}

object MegabytesPerSecond extends DataRateUnit {
  val symbol = "MB/s"
  val conversionFactor = Megabytes.conversionFactor
}

object MebibytesPerSecond extends DataRateUnit {
  val symbol = "MiB/s"
  val conversionFactor = Mebibytes.conversionFactor
}

object GigabytesPerSecond extends DataRateUnit {
  val symbol = "GB/s"
  val conversionFactor = Gigabytes.conversionFactor
}

object GibibytesPerSecond extends DataRateUnit {
  val symbol = "GiB/s"
  val conversionFactor = Gibibytes.conversionFactor
}

object TerabytesPerSecond extends DataRateUnit {
  val symbol = "TB/s"
  val conversionFactor = Terabytes.conversionFactor
}

object TebibytesPerSecond extends DataRateUnit {
  val symbol = "TiB/s"
  val conversionFactor = Tebibytes.conversionFactor
}

object PetabytesPerSecond extends DataRateUnit {
  val symbol = "PB/s"
  val conversionFactor = Petabytes.conversionFactor
}

object PebibytesPerSecond extends DataRateUnit {
  val symbol = "PiB/s"
  val conversionFactor = Pebibytes.conversionFactor
}

object ExabytesPerSecond extends DataRateUnit {
  val symbol = "EB/s"
  val conversionFactor = Exabytes.conversionFactor
}

object ExbibytesPerSecond extends DataRateUnit {
  val symbol = "EiB/s"
  val conversionFactor = Exbibytes.conversionFactor
}

object ZettabytesPerSecond extends DataRateUnit {
  val symbol = "ZB/s"
  val conversionFactor = Zettabytes.conversionFactor
}

object ZebibytesPerSecond extends DataRateUnit {
  val symbol = "ZiB/s"
  val conversionFactor = Zebibytes.conversionFactor
}

object YottabytesPerSecond extends DataRateUnit {
  val symbol = "YB/s"
  val conversionFactor = Yottabytes.conversionFactor
}

object YobibytesPerSecond extends DataRateUnit {
  val symbol = "YiB/s"
  val conversionFactor = Yobibytes.conversionFactor
}

object BitsPerSecond extends DataRateUnit {
  val symbol = "bps"
  val conversionFactor = Bits.conversionFactor
}

object KilobitsPerSecond extends DataRateUnit {
  val symbol = "Kbps"
  val conversionFactor = BitsPerSecond.conversionFactor * Kilobytes.conversionFactor
}

object KibibitsPerSecond extends DataRateUnit {
  val symbol = "Kibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Kibibytes.conversionFactor
}

object MegabitsPerSecond extends DataRateUnit {
  val symbol = "Mbps"
  val conversionFactor = BitsPerSecond.conversionFactor * Megabytes.conversionFactor
}

object MebibitsPerSecond extends DataRateUnit {
  val symbol = "Mibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Mebibytes.conversionFactor
}

object GigabitsPerSecond extends DataRateUnit {
  val symbol = "Gbps"
  val conversionFactor = BitsPerSecond.conversionFactor * Gigabytes.conversionFactor
}

object GibibitsPerSecond extends DataRateUnit {
  val symbol = "Gibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Gibibytes.conversionFactor
}

object TerabitsPerSecond extends DataRateUnit {
  val symbol = "Tbps"
  val conversionFactor = BitsPerSecond.conversionFactor * Terabytes.conversionFactor
}

object TebibitsPerSecond extends DataRateUnit {
  val symbol = "Tibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Tebibytes.conversionFactor
}

object PetabitsPerSecond extends DataRateUnit {
  val symbol = "Pbps"
  val conversionFactor = BitsPerSecond.conversionFactor * Petabytes.conversionFactor
}

object PebibitsPerSecond extends DataRateUnit {
  val symbol = "Pibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Pebibytes.conversionFactor
}

object ExabitsPerSecond extends DataRateUnit {
  val symbol = "Ebps"
  val conversionFactor = BitsPerSecond.conversionFactor * Exabytes.conversionFactor
}

object ExbibitsPerSecond extends DataRateUnit {
  val symbol = "Eibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Exbibytes.conversionFactor
}

object ZettabitsPerSecond extends DataRateUnit {
  val symbol = "Zbps"
  val conversionFactor = BitsPerSecond.conversionFactor * Zettabytes.conversionFactor
}

object ZebibitsPerSecond extends DataRateUnit {
  val symbol = "Zibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Zebibytes.conversionFactor
}

object YottabitsPerSecond extends DataRateUnit {
  val symbol = "Ybps"
  val conversionFactor = BitsPerSecond.conversionFactor * Yottabytes.conversionFactor
}

object YobibitsPerSecond extends DataRateUnit {
  val symbol = "Yibps"
  val conversionFactor = BitsPerSecond.conversionFactor * Yobibytes.conversionFactor
}


object DataRateConversions {
  lazy val bytesPerSecond = BytesPerSecond(1)
  lazy val kilobytesPerSecond = KilobytesPerSecond(1)
  lazy val kibibytesPerSecond = KibibytesPerSecond(1)
  lazy val megabytesPerSecond = MegabytesPerSecond(1)
  lazy val mebibytesPerSecond = MebibytesPerSecond(1)
  lazy val gigabytesPerSecond = GigabytesPerSecond(1)
  lazy val gibibytesPerSecond = GibibytesPerSecond(1)
  lazy val terabytesPerSecond = TerabytesPerSecond(1)
  lazy val tebibytesPerSecond = TebibytesPerSecond(1)
  lazy val petabytesPerSecond = PetabytesPerSecond(1)
  lazy val pebibytesPerSecond = PebibytesPerSecond(1)
  lazy val exabytesPerSecond = ExabytesPerSecond(1)
  lazy val exbibytesPerSecond = ExbibytesPerSecond(1)
  lazy val zettabytesPerSecond = ZettabytesPerSecond(1)
  lazy val zebibytesPerSecond = ZebibytesPerSecond(1)
  lazy val yottabytesPerSecond = YottabytesPerSecond(1)
  lazy val yobibytesPerSecond = YobibytesPerSecond(1)

  lazy val bitsPerSecond = BitsPerSecond(1)
  lazy val kilobitsPerSecond = KilobitsPerSecond(1)
  lazy val kibibitsPerSecond = KibibitsPerSecond(1)
  lazy val megabitsPerSecond = MegabitsPerSecond(1)
  lazy val mebibitsPerSecond = MebibitsPerSecond(1)
  lazy val gigabitsPerSecond = GigabitsPerSecond(1)
  lazy val gibibitsPerSecond = GibibitsPerSecond(1)
  lazy val terabitsPerSecond = TerabitsPerSecond(1)
  lazy val tebibitsPerSecond = TebibitsPerSecond(1)
  lazy val petabitsPerSecond = PetabitsPerSecond(1)
  lazy val pebibitsPerSecond = PebibitsPerSecond(1)
  lazy val exabitsPerSecond = ExabitsPerSecond(1)
  lazy val exbibitsPerSecond = ExbibitsPerSecond(1)
  lazy val zettabitsPerSecond = ZettabitsPerSecond(1)
  lazy val zebibitsPerSecond = ZebibitsPerSecond(1)
  lazy val yottabitsPerSecond = YottabitsPerSecond(1)
  lazy val yobibitsPerSecond = YobibitsPerSecond(1)

  implicit class DataRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def bytesPerSecond = BytesPerSecond(n)
    def kilobytesPerSecond = KilobytesPerSecond(n)
    def kibibytesPerSecond = KibibytesPerSecond(n)
    def megabytesPerSecond = MegabytesPerSecond(n)
    def mebibytesPerSecond = MebibytesPerSecond(n)
    def gigabytesPerSecond = GigabytesPerSecond(n)
    def gibibytesPerSecond = GibibytesPerSecond(n)
    def terabytesPerSecond = TerabytesPerSecond(n)
    def tebibytesPerSecond = TebibytesPerSecond(n)
    def petabytesPerSecond = PetabytesPerSecond(n)
    def pebibytesPerSecond = PebibytesPerSecond(n)
    def exabytesPerSecond = ExabytesPerSecond(n)
    def exbibytesPerSecond = ExbibytesPerSecond(n)
    def zettabytesPerSecond = ZettabytesPerSecond(n)
    def zebibytesPerSecond = ZebibytesPerSecond(n)
    def yottabytesPerSecond = YottabytesPerSecond(n)
    def yobibytesPerSecond = YobibytesPerSecond(n)

    def bitsPerSecond = BitsPerSecond(n)
    def kilobitsPerSecond = KilobitsPerSecond(n)
    def kibibitsPerSecond = KibibitsPerSecond(n)
    def megabitsPerSecond = MegabitsPerSecond(n)
    def mebibitsPerSecond = MebibitsPerSecond(n)
    def gigabitsPerSecond = GigabitsPerSecond(n)
    def gibibitsPerSecond = GibibitsPerSecond(n)
    def terabitsPerSecond = TerabitsPerSecond(n)
    def tebibitsPerSecond = TebibitsPerSecond(n)
    def petabitsPerSecond = PetabitsPerSecond(n)
    def pebibitsPerSecond = PebibitsPerSecond(n)
    def exabitsPerSecond = ExabitsPerSecond(n)
    def exbibitsPerSecond = ExbibitsPerSecond(n)
    def zettabitsPerSecond = ZettabitsPerSecond(n)
    def zebibitsPerSecond = ZebibitsPerSecond(n)
    def yottabitsPerSecond = YottabitsPerSecond(n)
    def yobibitsPerSecond = YobibitsPerSecond(n)
  }
  
  implicit object DataRateNumeric extends AbstractQuantityNumeric[DataRate](DataRate.primaryUnit)
}
