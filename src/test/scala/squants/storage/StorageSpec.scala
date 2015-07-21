/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.storage

import org.scalatest.{ Matchers, FlatSpec }
import squants.{ QuantityParseException, BinarySystem, MetricSystem }

/**
 * @author  Derek Morr
 * @since   XXX
 *
 */
class StorageSpec extends FlatSpec with Matchers {

  behavior of "Storage and its Units of Measure"

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
  }

  it should "create values form properly formatted Strings" in {
    Storage("100 B").get should be(Bytes(100))
    Storage("100 K").get should be(Kilobytes(100))
    Storage("100 M").get should be(Megabytes(100))
    Storage("100 G").get should be(Gigabytes(100))
    Storage("100 T").get should be(Terabytes(100))
    Storage("100 P").get should be(Petabytes(100))
    Storage("100 E").get should be(Exabytes(100))
    Storage("100 Z").get should be(Zettabytes(100))
    Storage("100 Y").get should be(Yottabytes(100))

    Storage("100 Ki").get should be(Kibibytes(100))
    Storage("100 Mi").get should be(Mebibytes(100))
    Storage("100 Gi").get should be(Gibibytes(100))
    Storage("100 Ti").get should be(Tebibytes(100))
    Storage("100 Pi").get should be(Pebibytes(100))
    Storage("100 Ei").get should be(Exbibytes(100))
    Storage("100 Zi").get should be(Zebibytes(100))
    Storage("100 Yi").get should be(Yobibytes(100))

    Storage("100 zz").failed.get should be(QuantityParseException("Unable to parse Storage", "100 zz"))
    Storage("ZZ B").failed.get should be(QuantityParseException("Unable to parse Storage", "ZZ B"))
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
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Bytes(1).toString(Bytes) should be("1.0 B")
    Kilobytes(1).toString(Kilobytes) should be("1.0 K")
    Megabytes(1).toString(Megabytes) should be("1.0 M")
    Gigabytes(1).toString(Gigabytes) should be("1.0 G")
    Terabytes(1).toString(Terabytes) should be("1.0 T")
    Petabytes(1).toString(Petabytes) should be("1.0 P")
    Exabytes(1).toString(Exabytes) should be("1.0 E")
    Zettabytes(1).toString(Zettabytes) should be("1.0 Z")
    Yottabytes(1).toString(Yottabytes) should be("1.0 Y")

    Kibibytes(1).toString(Kibibytes) should be("1.0 Ki")
    Mebibytes(1).toString(Mebibytes) should be("1.0 Mi")
    Gibibytes(1).toString(Gibibytes) should be("1.0 Gi")
    Tebibytes(1).toString(Tebibytes) should be("1.0 Ti")
    Pebibytes(1).toString(Pebibytes) should be("1.0 Pi")
    Exbibytes(1).toString(Exbibytes) should be("1.0 Ei")
    Zebibytes(1).toString(Zebibytes) should be("1.0 Zi")
    Yobibytes(1).toString(Yobibytes) should be("1.0 Yi")
  }

  it should "provide aliases for single unit values" in {
    import StorageConversions._

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
  }

  it should "provide implicit conversion from Double" in {
    import StorageConversions._

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

  }

  it should "provide implicit conversion from String" in {
    import StorageConversions._

    "100 B".toStorage.get should be(Bytes(100))
    "100 K".toStorage.get should be(Kilobytes(100))
    "100 M".toStorage.get should be(Megabytes(100))
    "100 G".toStorage.get should be(Gigabytes(100))
    "100 T".toStorage.get should be(Terabytes(100))
    "100 P".toStorage.get should be(Petabytes(100))
    "100 E".toStorage.get should be(Exabytes(100))
    "100 Z".toStorage.get should be(Zettabytes(100))
    "100 Y".toStorage.get should be(Yottabytes(100))

    "100 Ki".toStorage.get should be(Kibibytes(100))
    "100 Mi".toStorage.get should be(Mebibytes(100))
    "100 Gi".toStorage.get should be(Gibibytes(100))
    "100 Ti".toStorage.get should be(Tebibytes(100))
    "100 Pi".toStorage.get should be(Pebibytes(100))
    "100 Ei".toStorage.get should be(Exbibytes(100))
    "100 Zi".toStorage.get should be(Zebibytes(100))
    "100 Yi".toStorage.get should be(Yobibytes(100))

    "100 zz".toStorage.failed.get should be(QuantityParseException("Unable to parse Storage", "100 zz"))
    "ZZ B".toStorage.failed.get should be(QuantityParseException("Unable to parse Storage", "ZZ B"))
  }

  it should "provide Numeric support" in {
    import StorageConversions.StorageNumeric

    val ss = List(Bytes(1000), Kilobytes(1))
    ss.sum should be(Bytes(2000))
  }

}
