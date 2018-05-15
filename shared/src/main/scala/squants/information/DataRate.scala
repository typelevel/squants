package squants.information

import squants._
import squants.time.TimeDerivative

/**
  * Represnets a rate of transfer of information
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
    ZettabytesPerSecond, ZebibytesPerSecond, YottabytesPerSecond, YobibytesPerSecond)
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
  }
  
  implicit object DataRateNumeric extends AbstractQuantityNumeric[DataRate](DataRate.primaryUnit)
}
