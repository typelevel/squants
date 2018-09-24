package squants.information

import org.scalatest.{Matchers, FlatSpec}
import squants.QuantityParseException
import squants.time.Seconds

/**
  * @author Derek Morr
  * @since xxx
  */
class DataRateSpec extends FlatSpec with Matchers {

  behavior of "DataRate and its Units of Measure"

  it should "create values using UOM factories" in {
    BytesPerSecond(1).toBytesPerSecond should be(1)

    KilobytesPerSecond(1).toKilobytesPerSecond  should be(1)
    MegabytesPerSecond(1).toMegabytesPerSecond  should be(1)
    GigabytesPerSecond(1).toGigabytesPerSecond  should be(1)
    TerabytesPerSecond(1).toTerabytesPerSecond  should be(1)
    PetabytesPerSecond(1).toPetabytesPerSecond  should be(1)
    ExabytesPerSecond(1).toExabytesPerSecond    should be(1)
    ZettabytesPerSecond(1).toZettabytesPerSecond should be(1)
    YottabytesPerSecond(1).toYottabytesPerSecond should be(1)

    KibibytesPerSecond(1).toKibibytesPerSecond should be(1)
    MebibytesPerSecond(1).toMebibytesPerSecond should be(1)
    GibibytesPerSecond(1).toGibibytesPerSecond should be(1)
    TebibytesPerSecond(1).toTebibytesPerSecond should be(1)
    PebibytesPerSecond(1).toPebibytesPerSecond should be(1)
    ExbibytesPerSecond(1).toExbibytesPerSecond should be(1)
    ZebibytesPerSecond(1).toZebibytesPerSecond should be(1)
    YobibytesPerSecond(1).toYobibytesPerSecond should be(1)

    BitsPerSecond(1).toBitsPerSecond should be(1)

    KilobitsPerSecond(1).toKilobitsPerSecond   should be(1)
    MegabitsPerSecond(1).toMegabitsPerSecond   should be(1)
    GigabitsPerSecond(1).toGigabitsPerSecond   should be(1)
    TerabitsPerSecond(1).toTerabitsPerSecond   should be(1)
    PetabitsPerSecond(1).toPetabitsPerSecond   should be(1)
    ExabitsPerSecond(1).toExabitsPerSecond     should be(1)
    ZettabitsPerSecond(1).toZettabitsPerSecond should be(1)
    YottabitsPerSecond(1).toYottabitsPerSecond should be(1)

    KibibitsPerSecond(1).toKibibitsPerSecond should be(1)
    MebibitsPerSecond(1).toMebibitsPerSecond should be(1)
    GibibitsPerSecond(1).toGibibitsPerSecond should be(1)
    TebibitsPerSecond(1).toTebibitsPerSecond should be(1)
    PebibitsPerSecond(1).toPebibitsPerSecond should be(1)
    ExbibitsPerSecond(1).toExbibitsPerSecond should be(1)
    ZebibitsPerSecond(1).toZebibitsPerSecond should be(1)
    YobibitsPerSecond(1).toYobibitsPerSecond should be(1)
  }

  it should "create values from properly formatted Strings" in {
    val rate = 10.22
    DataRate(s"$rate B/s").get should be(BytesPerSecond(rate))

    DataRate(s"$rate KB/s").get should be(KilobytesPerSecond(rate))
    DataRate(s"$rate MB/s").get should be(MegabytesPerSecond(rate))
    DataRate(s"$rate GB/s").get should be(GigabytesPerSecond(rate))
    DataRate(s"$rate TB/s").get should be(TerabytesPerSecond(rate))
    DataRate(s"$rate PB/s").get should be(PetabytesPerSecond(rate))
    DataRate(s"$rate EB/s").get should be(ExabytesPerSecond(rate))
    DataRate(s"$rate ZB/s").get should be(ZettabytesPerSecond(rate))
    DataRate(s"$rate YB/s").get should be(YottabytesPerSecond(rate))

    DataRate(s"$rate KiB/s").get should be(KibibytesPerSecond(rate))
    DataRate(s"$rate MiB/s").get should be(MebibytesPerSecond(rate))
    DataRate(s"$rate GiB/s").get should be(GibibytesPerSecond(rate))
    DataRate(s"$rate TiB/s").get should be(TebibytesPerSecond(rate))
    DataRate(s"$rate PiB/s").get should be(PebibytesPerSecond(rate))
    DataRate(s"$rate EiB/s").get should be(ExbibytesPerSecond(rate))
    DataRate(s"$rate ZiB/s").get should be(ZebibytesPerSecond(rate))
    DataRate(s"$rate YiB/s").get should be(YobibytesPerSecond(rate))

    DataRate(s"$rate bps").get  should be(BitsPerSecond(rate))
    DataRate(s"$rate Kbps").get should be(KilobitsPerSecond(rate))
    DataRate(s"$rate Mbps").get should be(MegabitsPerSecond(rate))
    DataRate(s"$rate Gbps").get should be(GigabitsPerSecond(rate))
    DataRate(s"$rate Tbps").get should be(TerabitsPerSecond(rate))
    DataRate(s"$rate Pbps").get should be(PetabitsPerSecond(rate))
    DataRate(s"$rate Ebps").get should be(ExabitsPerSecond(rate))
    DataRate(s"$rate Zbps").get should be(ZettabitsPerSecond(rate))
    DataRate(s"$rate Ybps").get should be(YottabitsPerSecond(rate))

    DataRate(s"$rate Kibps").get should be(KibibitsPerSecond(rate))
    DataRate(s"$rate Mibps").get should be(MebibitsPerSecond(rate))
    DataRate(s"$rate Gibps").get should be(GibibitsPerSecond(rate))
    DataRate(s"$rate Tibps").get should be(TebibitsPerSecond(rate))
    DataRate(s"$rate Pibps").get should be(PebibitsPerSecond(rate))
    DataRate(s"$rate Eibps").get should be(ExbibitsPerSecond(rate))
    DataRate(s"$rate Zibps").get should be(ZebibitsPerSecond(rate))
    DataRate(s"$rate Yibps").get should be(YobibitsPerSecond(rate))

    DataRate(s"$rate zz").failed.get should be(QuantityParseException("Unable to parse DataRate", s"$rate zz"))
    DataRate("zz B/s").failed.get should be(QuantityParseException("Unable to parse DataRate", "zz B/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val tolerance: Double = 0.000000000000001
    val x = BytesPerSecond(1)
    x.toBytesPerSecond should be(1)

    x.toKilobytesPerSecond  should be(Bytes(1).toKilobytes  / Seconds(1).toSeconds +- tolerance)
    x.toMegabytesPerSecond  should be(Bytes(1).toMegabytes  / Seconds(1).toSeconds +- tolerance)
    x.toGigabytesPerSecond  should be(Bytes(1).toGigabytes  / Seconds(1).toSeconds +- tolerance)
    x.toTerabytesPerSecond  should be(Bytes(1).toTerabytes  / Seconds(1).toSeconds +- tolerance)
    x.toPetabytesPerSecond  should be(Bytes(1).toPetabytes  / Seconds(1).toSeconds +- tolerance)
    x.toExabytesPerSecond   should be(Bytes(1).toExabytes   / Seconds(1).toSeconds +- tolerance)
    x.toZettabytesPerSecond should be(Bytes(1).toZettabytes / Seconds(1).toSeconds +- tolerance)
    x.toYottabytesPerSecond should be(Bytes(1).toYottabytes / Seconds(1).toSeconds +- tolerance)

    x.toKibibytesPerSecond should be(Bytes(1).toKibibytes / Seconds(1).toSeconds +- tolerance)
    x.toMebibytesPerSecond should be(Bytes(1).toMebibytes / Seconds(1).toSeconds +- tolerance)
    x.toGibibytesPerSecond should be(Bytes(1).toGibibytes / Seconds(1).toSeconds +- tolerance)
    x.toTebibytesPerSecond should be(Bytes(1).toTebibytes / Seconds(1).toSeconds +- tolerance)
    x.toPebibytesPerSecond should be(Bytes(1).toPebibytes / Seconds(1).toSeconds +- tolerance)
    x.toExbibytesPerSecond should be(Bytes(1).toExbibytes / Seconds(1).toSeconds +- tolerance)
    x.toZebibytesPerSecond should be(Bytes(1).toZebibytes / Seconds(1).toSeconds +- tolerance)
    x.toYobibytesPerSecond should be(Bytes(1).toYobibytes / Seconds(1).toSeconds +- tolerance)

    x.toBitsPerSecond      should be(Bytes(1).toBits  / Seconds(1).toSeconds +- tolerance)
    x.toKilobitsPerSecond  should be(Bytes(1).toKilobits  / Seconds(1).toSeconds +- tolerance)
    x.toMegabitsPerSecond  should be(Bytes(1).toMegabits  / Seconds(1).toSeconds +- tolerance)
    x.toGigabitsPerSecond  should be(Bytes(1).toGigabits  / Seconds(1).toSeconds +- tolerance)
    x.toTerabitsPerSecond  should be(Bytes(1).toTerabits  / Seconds(1).toSeconds +- tolerance)
    x.toPetabitsPerSecond  should be(Bytes(1).toPetabits  / Seconds(1).toSeconds +- tolerance)
    x.toExabitsPerSecond   should be(Bytes(1).toExabits   / Seconds(1).toSeconds +- tolerance)
    x.toZettabitsPerSecond should be(Bytes(1).toZettabits / Seconds(1).toSeconds +- tolerance)
    x.toYottabitsPerSecond should be(Bytes(1).toYottabits / Seconds(1).toSeconds +- tolerance)

    x.toKibibitsPerSecond should be(Bytes(1).toKibibits / Seconds(1).toSeconds +- tolerance)
    x.toMebibitsPerSecond should be(Bytes(1).toMebibits / Seconds(1).toSeconds +- tolerance)
    x.toGibibitsPerSecond should be(Bytes(1).toGibibits / Seconds(1).toSeconds +- tolerance)
    x.toTebibitsPerSecond should be(Bytes(1).toTebibits / Seconds(1).toSeconds +- tolerance)
    x.toPebibitsPerSecond should be(Bytes(1).toPebibits / Seconds(1).toSeconds +- tolerance)
    x.toExbibitsPerSecond should be(Bytes(1).toExbibits / Seconds(1).toSeconds +- tolerance)
    x.toZebibitsPerSecond should be(Bytes(1).toZebibits / Seconds(1).toSeconds +- tolerance)
    x.toYobibitsPerSecond should be(Bytes(1).toYobibits / Seconds(1).toSeconds +- tolerance)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    BytesPerSecond(1).toString(BytesPerSecond) should be("1.0 B/s")

    KilobytesPerSecond(1).toString(KilobytesPerSecond)   should be("1.0 KB/s")
    MegabytesPerSecond(1).toString(MegabytesPerSecond)   should be("1.0 MB/s")
    GigabytesPerSecond(1).toString(GigabytesPerSecond)   should be("1.0 GB/s")
    TerabytesPerSecond(1).toString(TerabytesPerSecond)   should be("1.0 TB/s")
    PetabytesPerSecond(1).toString(PetabytesPerSecond)   should be("1.0 PB/s")
    ExabytesPerSecond(1).toString(ExabytesPerSecond)     should be("1.0 EB/s")
    ZettabytesPerSecond(1).toString(ZettabytesPerSecond) should be("1.0 ZB/s")
    YottabytesPerSecond(1).toString(YottabytesPerSecond) should be("1.0 YB/s")

    KibibytesPerSecond(1).toString(KibibytesPerSecond) should be("1.0 KiB/s")
    MebibytesPerSecond(1).toString(MebibytesPerSecond) should be("1.0 MiB/s")
    GibibytesPerSecond(1).toString(GibibytesPerSecond) should be("1.0 GiB/s")
    TebibytesPerSecond(1).toString(TebibytesPerSecond) should be("1.0 TiB/s")
    PebibytesPerSecond(1).toString(PebibytesPerSecond) should be("1.0 PiB/s")
    ExbibytesPerSecond(1).toString(ExbibytesPerSecond) should be("1.0 EiB/s")
    ZebibytesPerSecond(1).toString(ZebibytesPerSecond) should be("1.0 ZiB/s")
    YobibytesPerSecond(1).toString(YobibytesPerSecond) should be("1.0 YiB/s")

    BitsPerSecond(1).toString(BitsPerSecond)           should be("1.0 bps")
    KilobitsPerSecond(1).toString(KilobitsPerSecond)   should be("1.0 Kbps")
    MegabitsPerSecond(1).toString(MegabitsPerSecond)   should be("1.0 Mbps")
    GigabitsPerSecond(1).toString(GigabitsPerSecond)   should be("1.0 Gbps")
    TerabitsPerSecond(1).toString(TerabitsPerSecond)   should be("1.0 Tbps")
    PetabitsPerSecond(1).toString(PetabitsPerSecond)   should be("1.0 Pbps")
    ExabitsPerSecond(1).toString(ExabitsPerSecond)     should be("1.0 Ebps")
    ZettabitsPerSecond(1).toString(ZettabitsPerSecond) should be("1.0 Zbps")
    YottabitsPerSecond(1).toString(YottabitsPerSecond) should be("1.0 Ybps")

    KibibitsPerSecond(1).toString(KibibitsPerSecond) should be("1.0 Kibps")
    MebibitsPerSecond(1).toString(MebibitsPerSecond) should be("1.0 Mibps")
    GibibitsPerSecond(1).toString(GibibitsPerSecond) should be("1.0 Gibps")
    TebibitsPerSecond(1).toString(TebibitsPerSecond) should be("1.0 Tibps")
    PebibitsPerSecond(1).toString(PebibitsPerSecond) should be("1.0 Pibps")
    ExbibitsPerSecond(1).toString(ExbibitsPerSecond) should be("1.0 Eibps")
    ZebibitsPerSecond(1).toString(ZebibitsPerSecond) should be("1.0 Zibps")
    YobibitsPerSecond(1).toString(YobibitsPerSecond) should be("1.0 Yibps")
  }

  it should "return Information when multiplied by Time" in {
    BytesPerSecond(1) * Seconds(1) should be(Bytes(1))
  }

  it should "return a dimensionless ration when divided by a DataRate" in {
    BytesPerSecond(10) / BytesPerSecond(4) should be(2.5)
  }

  behavior of "DataRateConversions"

  it should "provide aliases for single unit values" in {
    import DataRateConversions._

    bytesPerSecond should be(BytesPerSecond(1))

    kilobytesPerSecond  should be(KilobytesPerSecond(1))
    megabytesPerSecond  should be(MegabytesPerSecond(1))
    gigabytesPerSecond  should be(GigabytesPerSecond(1))
    terabytesPerSecond  should be(TerabytesPerSecond(1))
    petabytesPerSecond  should be(PetabytesPerSecond(1))
    exabytesPerSecond   should be(ExabytesPerSecond(1))
    zettabytesPerSecond should be(ZettabytesPerSecond(1))
    yottabytesPerSecond should be(YottabytesPerSecond(1))

    kibibytesPerSecond should be(KibibytesPerSecond(1))
    mebibytesPerSecond should be(MebibytesPerSecond(1))
    gibibytesPerSecond should be(GibibytesPerSecond(1))
    tebibytesPerSecond should be(TebibytesPerSecond(1))
    pebibytesPerSecond should be(PebibytesPerSecond(1))
    exbibytesPerSecond should be(ExbibytesPerSecond(1))
    zebibytesPerSecond should be(ZebibytesPerSecond(1))
    yobibytesPerSecond should be(YobibytesPerSecond(1))

    bitsPerSecond      should be(BitsPerSecond(1))
    kilobitsPerSecond  should be(KilobitsPerSecond(1))
    megabitsPerSecond  should be(MegabitsPerSecond(1))
    gigabitsPerSecond  should be(GigabitsPerSecond(1))
    terabitsPerSecond  should be(TerabitsPerSecond(1))
    petabitsPerSecond  should be(PetabitsPerSecond(1))
    exabitsPerSecond   should be(ExabitsPerSecond(1))
    zettabitsPerSecond should be(ZettabitsPerSecond(1))
    yottabitsPerSecond should be(YottabitsPerSecond(1))

    kibibitsPerSecond should be(KibibitsPerSecond(1))
    mebibitsPerSecond should be(MebibitsPerSecond(1))
    gibibitsPerSecond should be(GibibitsPerSecond(1))
    tebibitsPerSecond should be(TebibitsPerSecond(1))
    pebibitsPerSecond should be(PebibitsPerSecond(1))
    exbibitsPerSecond should be(ExbibitsPerSecond(1))
    zebibitsPerSecond should be(ZebibitsPerSecond(1))
    yobibitsPerSecond should be(YobibitsPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import DataRateConversions._
    
    val dr = 10d
    dr.bytesPerSecond should be(BytesPerSecond(10))

    dr.kilobytesPerSecond  should be(KilobytesPerSecond(10))
    dr.megabytesPerSecond  should be(MegabytesPerSecond(10))
    dr.gigabytesPerSecond  should be(GigabytesPerSecond(10))
    dr.terabytesPerSecond  should be(TerabytesPerSecond(10))
    dr.petabytesPerSecond  should be(PetabytesPerSecond(10))
    dr.exabytesPerSecond   should be(ExabytesPerSecond(10))
    dr.zettabytesPerSecond should be(ZettabytesPerSecond(10))
    dr.yottabytesPerSecond should be(YottabytesPerSecond(10))

    dr.kibibytesPerSecond should be(KibibytesPerSecond(10))
    dr.mebibytesPerSecond should be(MebibytesPerSecond(10))
    dr.gibibytesPerSecond should be(GibibytesPerSecond(10))
    dr.tebibytesPerSecond should be(TebibytesPerSecond(10))
    dr.pebibytesPerSecond should be(PebibytesPerSecond(10))
    dr.exbibytesPerSecond should be(ExbibytesPerSecond(10))
    dr.zebibytesPerSecond should be(ZebibytesPerSecond(10))
    dr.yobibytesPerSecond should be(YobibytesPerSecond(10))

    dr.bitsPerSecond      should be(BitsPerSecond(10))
    dr.kilobitsPerSecond  should be(KilobitsPerSecond(10))
    dr.megabitsPerSecond  should be(MegabitsPerSecond(10))
    dr.gigabitsPerSecond  should be(GigabitsPerSecond(10))
    dr.terabitsPerSecond  should be(TerabitsPerSecond(10))
    dr.petabitsPerSecond  should be(PetabitsPerSecond(10))
    dr.exabitsPerSecond   should be(ExabitsPerSecond(10))
    dr.zettabitsPerSecond should be(ZettabitsPerSecond(10))
    dr.yottabitsPerSecond should be(YottabitsPerSecond(10))

    dr.kibibitsPerSecond should be(KibibitsPerSecond(10))
    dr.mebibitsPerSecond should be(MebibitsPerSecond(10))
    dr.gibibitsPerSecond should be(GibibitsPerSecond(10))
    dr.tebibitsPerSecond should be(TebibitsPerSecond(10))
    dr.pebibitsPerSecond should be(PebibitsPerSecond(10))
    dr.exbibitsPerSecond should be(ExbibitsPerSecond(10))
    dr.zebibitsPerSecond should be(ZebibitsPerSecond(10))
    dr.yobibitsPerSecond should be(YobibitsPerSecond(10))
  }

  it should "provide Numeric support" in {
    import DataRateConversions.DataRateNumeric

    val dr = List(BytesPerSecond(100), BytesPerSecond(1))
    dr.sum should be(BytesPerSecond(101))
  }
}
