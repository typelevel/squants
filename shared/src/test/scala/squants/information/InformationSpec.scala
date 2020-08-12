/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.information

import squants.time.Seconds
import squants.{BinarySystem, MetricSystem, QuantityParseException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  Derek Morr
 * @since   0.6.0
 *
 */
class InformationSpec extends AnyFlatSpec with Matchers {

  behavior of "Information and its Units of Measure"

  it should "create values using UOM factories" in {
    Bytes(1).toBytes should be(1)
    Kilobytes(1).toKilobytes should be(1)
    Megabytes(1).toMegabytes should be(1)
    Gigabytes(1).toGigabytes should be(1)
    Terabytes(1).toTerabytes should be(1)
    Petabytes(1).toPetabytes should be(1)
    Exabytes(1).toExabytes should be(1)
    Zettabytes(1).toZettabytes should be(1)
    Yottabytes(1).toYottabytes should be(1)

    Kibibytes(1).toKibibytes should be(1)
    Mebibytes(1).toMebibytes should be(1)
    Gibibytes(1).toGibibytes should be(1)
    Tebibytes(1).toTebibytes should be(1)
    Pebibytes(1).toPebibytes should be(1)
    Exbibytes(1).toExbibytes should be(1)
    Zebibytes(1).toZebibytes should be(1)
    Yobibytes(1).toYobibytes should be(1)

    Bits(1).toBits should be(1)
    Kilobits(1).toKilobits should be(1)
    Megabits(1).toMegabits should be(1)
    Gigabits(1).toGigabits should be(1)
    Terabits(1).toTerabits should be(1)
    Petabits(1).toPetabits should be(1)
    Exabits(1).toExabits should be(1)
    Zettabits(1).toZettabits should be(1)
    Yottabits(1).toYottabits should be(1)

    Kibibits(1).toKibibits should be(1)
    Mebibits(1).toMebibits should be(1)
    Gibibits(1).toGibibits should be(1)
    Tebibits(1).toTebibits should be(1)
    Pebibits(1).toPebibits should be(1)
    Exbibits(1).toExbibits should be(1)
    Zebibits(1).toZebibits should be(1)
    Yobibits(1).toYobibits should be(1)
  }

  it should "create values form properly formatted Strings" in {
    Information("100 B").get should be(Bytes(100))
    Information("100 KB").get should be(Kilobytes(100))
    Information("100 MB").get should be(Megabytes(100))
    Information("100 GB").get should be(Gigabytes(100))
    Information("100 TB").get should be(Terabytes(100))
    Information("100 PB").get should be(Petabytes(100))
    Information("100 EB").get should be(Exabytes(100))
    Information("100 ZB").get should be(Zettabytes(100))
    Information("100 YB").get should be(Yottabytes(100))

    Information("100 KiB").get should be(Kibibytes(100))
    Information("100 MiB").get should be(Mebibytes(100))
    Information("100 GiB").get should be(Gibibytes(100))
    Information("100 TiB").get should be(Tebibytes(100))
    Information("100 PiB").get should be(Pebibytes(100))
    Information("100 EiB").get should be(Exbibytes(100))
    Information("100 ZiB").get should be(Zebibytes(100))
    Information("100 YiB").get should be(Yobibytes(100))

    Information("100 bit").get should be(Bits(100))
    Information("100 Kbit").get should be(Kilobits(100))
    Information("100 Mbit").get should be(Megabits(100))
    Information("100 Gbit").get should be(Gigabits(100))
    Information("100 Tbit").get should be(Terabits(100))
    Information("100 Pbit").get should be(Petabits(100))
    Information("100 Ebit").get should be(Exabits(100))
    Information("100 Zbit").get should be(Zettabits(100))
    Information("100 Ybit").get should be(Yottabits(100))

    Information("100 Kibit").get should be(Kibibits(100))
    Information("100 Mibit").get should be(Mebibits(100))
    Information("100 Gibit").get should be(Gibibits(100))
    Information("100 Tibit").get should be(Tebibits(100))
    Information("100 Pibit").get should be(Pebibits(100))
    Information("100 Eibit").get should be(Exbibits(100))
    Information("100 Zibit").get should be(Zebibits(100))
    Information("100 Yibit").get should be(Yobibits(100))

    Information("100 zz").failed.get should be(QuantityParseException("Unable to parse Information", "100 zz"))
    Information("ZZ B").failed.get should be(QuantityParseException("Unable to parse Information", "ZZ B"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Bytes(1)
    x.toBytes should be(1)
    x.toKilobytes should be(1 / MetricSystem.Kilo)
    x.toMegabytes should be(1 / MetricSystem.Mega)
    x.toGigabytes should be(1 / MetricSystem.Giga)
    x.toTerabytes should be(1 / MetricSystem.Tera)
    x.toPetabytes should be(1 / MetricSystem.Peta)
    x.toExabytes should be(1 / MetricSystem.Exa)
    x.toZettabytes should be(1 / MetricSystem.Zetta)
    x.toYottabytes should be(1 / MetricSystem.Yotta)

    x.toKibibytes should be(1 / BinarySystem.Kilo)
    x.toMebibytes should be(1 / BinarySystem.Mega)
    x.toGibibytes should be(1 / BinarySystem.Giga)
    x.toTebibytes should be(1 / BinarySystem.Tera)
    x.toPebibytes should be(1 / BinarySystem.Peta)
    x.toExbibytes should be(1 / BinarySystem.Exa)
    x.toZebibytes should be(1 / BinarySystem.Zetta)
    x.toYobibytes should be(1 / BinarySystem.Yotta)

    x.toBits should be(1 / Bits.conversionFactor)
    x.toKilobits should be(1 / MetricSystem.Kilo / Bits.conversionFactor)
    x.toMegabits should be(1 / MetricSystem.Mega / Bits.conversionFactor)
    x.toGigabits should be(1 / MetricSystem.Giga / Bits.conversionFactor)
    x.toTerabits should be(1 / MetricSystem.Tera / Bits.conversionFactor)
    x.toPetabits should be(1 / MetricSystem.Peta / Bits.conversionFactor)
    x.toExabits should be(1 / MetricSystem.Exa / Bits.conversionFactor)
    x.toZettabits should be(1 / MetricSystem.Zetta / Bits.conversionFactor)
    x.toYottabits should be(1 / MetricSystem.Yotta / Bits.conversionFactor)

    x.toKibibits should be(1 / BinarySystem.Kilo / Bits.conversionFactor)
    x.toMebibits should be(1 / BinarySystem.Mega / Bits.conversionFactor)
    x.toGibibits should be(1 / BinarySystem.Giga / Bits.conversionFactor)
    x.toTebibits should be(1 / BinarySystem.Tera / Bits.conversionFactor)
    x.toPebibits should be(1 / BinarySystem.Peta / Bits.conversionFactor)
    x.toExbibits should be(1 / BinarySystem.Exa / Bits.conversionFactor)
    x.toZebibits should be(1 / BinarySystem.Zetta / Bits.conversionFactor)
    x.toYobibits should be(1 / BinarySystem.Yotta / Bits.conversionFactor)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Bytes(1).toString(Bytes) should be("1.0 B")
    Kilobytes(1).toString(Kilobytes) should be("1.0 KB")
    Megabytes(1).toString(Megabytes) should be("1.0 MB")
    Gigabytes(1).toString(Gigabytes) should be("1.0 GB")
    Terabytes(1).toString(Terabytes) should be("1.0 TB")
    Petabytes(1).toString(Petabytes) should be("1.0 PB")
    Exabytes(1).toString(Exabytes) should be("1.0 EB")
    Zettabytes(1).toString(Zettabytes) should be("1.0 ZB")
    Yottabytes(1).toString(Yottabytes) should be("1.0 YB")

    Kibibytes(1).toString(Kibibytes) should be("1.0 KiB")
    Mebibytes(1).toString(Mebibytes) should be("1.0 MiB")
    Gibibytes(1).toString(Gibibytes) should be("1.0 GiB")
    Tebibytes(1).toString(Tebibytes) should be("1.0 TiB")
    Pebibytes(1).toString(Pebibytes) should be("1.0 PiB")
    Exbibytes(1).toString(Exbibytes) should be("1.0 EiB")
    Zebibytes(1).toString(Zebibytes) should be("1.0 ZiB")
    Yobibytes(1).toString(Yobibytes) should be("1.0 YiB")

    Bits(1).toString(Bits) should be("1.0 bit")
    Kilobits(1).toString(Kilobits) should be("1.0 Kbit")
    Megabits(1).toString(Megabits) should be("1.0 Mbit")
    Gigabits(1).toString(Gigabits) should be("1.0 Gbit")
    Terabits(1).toString(Terabits) should be("1.0 Tbit")
    Petabits(1).toString(Petabits) should be("1.0 Pbit")
    Exabits(1).toString(Exabits) should be("1.0 Ebit")
    Zettabits(1).toString(Zettabits) should be("1.0 Zbit")
    Yottabits(1).toString(Yottabits) should be("1.0 Ybit")

    Kibibits(1).toString(Kibibits) should be("1.0 Kibit")
    Mebibits(1).toString(Mebibits) should be("1.0 Mibit")
    Gibibits(1).toString(Gibibits) should be("1.0 Gibit")
    Tebibits(1).toString(Tebibits) should be("1.0 Tibit")
    Pebibits(1).toString(Pebibits) should be("1.0 Pibit")
    Exbibits(1).toString(Exbibits) should be("1.0 Eibit")
    Zebibits(1).toString(Zebibits) should be("1.0 Zibit")
    Yobibits(1).toString(Yobibits) should be("1.0 Yibit")
  }

  it should "return Time when divided by DataRate" in {
    Bytes(1) / BytesPerSecond(1) should be(Seconds(1))
  }

  behavior of "InformationConversions"

  it should "provide aliases for single unit values" in {
    import InformationConversions._

    byte should be(Bytes(1))
    kilobyte should be(Kilobytes(1))
    megabyte should be(Megabytes(1))
    gigabyte should be(Gigabytes(1))
    terabyte should be(Terabytes(1))
    petabyte should be(Petabytes(1))
    exabyte should be(Exabytes(1))
    zettabyte should be(Zettabytes(1))
    yottabyte should be(Yottabytes(1))

    kibibyte should be(Kibibytes(1))
    mebibyte should be(Mebibytes(1))
    gibibyte should be(Gibibytes(1))
    tebibyte should be(Tebibytes(1))
    pebibyte should be(Pebibytes(1))
    exbibyte should be(Exbibytes(1))
    zebibyte should be(Zebibytes(1))
    yobibyte should be(Yobibytes(1))

    bit should be(Bits(1))
    kilobit should be(Kilobits(1))
    megabit should be(Megabits(1))
    gigabit should be(Gigabits(1))
    terabit should be(Terabits(1))
    petabit should be(Petabits(1))
    exabit should be(Exabits(1))
    zettabit should be(Zettabits(1))
    yottabit should be(Yottabits(1))

    kibibit should be(Kibibits(1))
    mebibit should be(Mebibits(1))
    gibibit should be(Gibibits(1))
    tebibit should be(Tebibits(1))
    pebibit should be(Pebibits(1))
    exbibit should be(Exbibits(1))
    zebibit should be(Zebibits(1))
    yobibit should be(Yobibits(1))
  }

  it should "provide implicit conversion from Double" in {
    import InformationConversions._

    val d = 10d
    d.bytes should be(Bytes(d))
    d.kb should be(Kilobytes(d))
    d.kilobytes should be(Kilobytes(d))
    d.mb should be(Megabytes(d))
    d.megabytes should be(Megabytes(d))
    d.gb should be(Gigabytes(d))
    d.gigabytes should be(Gigabytes(d))
    d.tb should be(Terabytes(d))
    d.terabytes should be(Terabytes(d))
    d.pb should be(Petabytes(d))
    d.petabytes should be(Petabytes(d))
    d.eb should be(Exabytes(d))
    d.exabytes should be(Exabytes(d))
    d.zb should be(Zettabytes(d))
    d.zettabytes should be(Zettabytes(d))
    d.yb should be(Yottabytes(d))
    d.yottabytes should be(Yottabytes(d))

    d.kib should be(Kibibytes(d))
    d.kibibytes should be(Kibibytes(d))
    d.mib should be(Mebibytes(d))
    d.mebibytes should be(Mebibytes(d))
    d.gib should be(Gibibytes(d))
    d.gibibytes should be(Gibibytes(d))
    d.tib should be(Tebibytes(d))
    d.tebibytes should be(Tebibytes(d))
    d.pib should be(Pebibytes(d))
    d.pebibytes should be(Pebibytes(d))
    d.eib should be(Exbibytes(d))
    d.exbibytes should be(Exbibytes(d))
    d.zib should be(Zebibytes(d))
    d.zebibytes should be(Zebibytes(d))
    d.yib should be(Yobibytes(d))
    d.yobibytes should be(Yobibytes(d))

    d.bits should be(Bits(d))
    d.kilobits should be(Kilobits(d))
    d.megabits should be(Megabits(d))
    d.gigabits should be(Gigabits(d))
    d.terabits should be(Terabits(d))
    d.petabits should be(Petabits(d))
    d.exabits should be(Exabits(d))
    d.zettabits should be(Zettabits(d))
    d.yottabits should be(Yottabits(d))

    d.kibibits should be(Kibibits(d))
    d.mebibits should be(Mebibits(d))
    d.gibibits should be(Gibibits(d))
    d.tebibits should be(Tebibits(d))
    d.pebibits should be(Pebibits(d))
    d.exbibits should be(Exbibits(d))
    d.zebibits should be(Zebibits(d))
    d.yobibits should be(Yobibits(d))
  }

  it should "provide implicit conversion from String" in {
    import InformationConversions._

    "100 B".toInformation.get should be(Bytes(100))
    "100 KB".toInformation.get should be(Kilobytes(100))
    "100 MB".toInformation.get should be(Megabytes(100))
    "100 GB".toInformation.get should be(Gigabytes(100))
    "100 TB".toInformation.get should be(Terabytes(100))
    "100 PB".toInformation.get should be(Petabytes(100))
    "100 EB".toInformation.get should be(Exabytes(100))
    "100 ZB".toInformation.get should be(Zettabytes(100))
    "100 YB".toInformation.get should be(Yottabytes(100))

    "100 KiB".toInformation.get should be(Kibibytes(100))
    "100 MiB".toInformation.get should be(Mebibytes(100))
    "100 GiB".toInformation.get should be(Gibibytes(100))
    "100 TiB".toInformation.get should be(Tebibytes(100))
    "100 PiB".toInformation.get should be(Pebibytes(100))
    "100 EiB".toInformation.get should be(Exbibytes(100))
    "100 ZiB".toInformation.get should be(Zebibytes(100))
    "100 YiB".toInformation.get should be(Yobibytes(100))

    "100 bit".toInformation.get should be(Bits(100))
    "100 Kbit".toInformation.get should be(Kilobits(100))
    "100 Mbit".toInformation.get should be(Megabits(100))
    "100 Gbit".toInformation.get should be(Gigabits(100))
    "100 Tbit".toInformation.get should be(Terabits(100))
    "100 Pbit".toInformation.get should be(Petabits(100))
    "100 Ebit".toInformation.get should be(Exabits(100))
    "100 Zbit".toInformation.get should be(Zettabits(100))
    "100 Ybit".toInformation.get should be(Yottabits(100))

    "100 Kibit".toInformation.get should be(Kibibits(100))
    "100 Mibit".toInformation.get should be(Mebibits(100))
    "100 Gibit".toInformation.get should be(Gibibits(100))
    "100 Tibit".toInformation.get should be(Tebibits(100))
    "100 Pibit".toInformation.get should be(Pebibits(100))
    "100 Eibit".toInformation.get should be(Exbibits(100))
    "100 Zibit".toInformation.get should be(Zebibits(100))
    "100 Yibit".toInformation.get should be(Yobibits(100))


    "100 zz".toInformation.failed.get should be(QuantityParseException("Unable to parse Information", "100 zz"))
    "ZZ B".toInformation.failed.get should be(QuantityParseException("Unable to parse Information", "ZZ B"))
  }

  it should "provide Numeric support" in {
    import InformationConversions.InformationNumeric

    val ss = List(Bytes(1000), Kilobytes(1))
    ss.sum should be(Bytes(2000))
  }

}
