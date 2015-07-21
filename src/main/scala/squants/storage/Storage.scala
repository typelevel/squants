/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.storage

import squants._

/**
 * Represents computer storage.
 *
 * @author Derek Morr
 * @since XXX
 *
 * @param value value in [[squants.storage.Bytes]]
 */
final class Storage private (val value: Double, val unit: StorageUnit)
    extends Quantity[Storage] {

  def dimension = Storage

  def toBytes = to(Bytes)
  def toKilobytes = to(Kilobytes)
  def toKibibytes = to(Kibibytes)
  def toMegabytes = to(Megabytes)
  def toMebibytes = to(Mebibytes)
  def toGigabytes = to(Gigabytes)
  def toGibibytes = to(Gibibytes)
  def toTerabytes = to(Terabytes)
  def toTebibytes = to(Tebibytes)
  def toPetabytes = to(Petabytes)
  def toPebibytes = to(Pebibytes)
  def toExabytes = to(Exabytes)
  def toExbibytes = to(Exbibytes)
  def toZettabytes = to(Zettabytes)
  def toZebibytes = to(Zebibytes)
  def toYottabytes = to(Yottabytes)
  def toYobibytes = to(Yobibytes)
}

trait StorageUnit extends UnitOfMeasure[Storage] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Storage(n, this)
}

/**
 * Factory singleton for storage
 */
object Storage extends Dimension[Storage] with BaseDimension {
  private[storage] def apply[A](n: A, unit: StorageUnit)(implicit num: Numeric[A]) = new Storage(num.toDouble(n), unit)
  def apply = parse _
  def name = "Storage"
  def primaryUnit = Bytes
  def siUnit = Bytes
  def units = Set(Bytes, Kilobytes, Kibibytes, Megabytes, Mebibytes,
    Gigabytes, Gibibytes, Terabytes, Tebibytes, Petabytes, Pebibytes,
    Exabytes, Exbibytes, Zettabytes, Zebibytes, Yottabytes, Yobibytes)
  def dimensionSymbol = "B"
}

object Bytes extends StorageUnit with PrimaryUnit with SiBaseUnit {
  val symbol = "B"
}

object Octets extends StorageUnit {
  val conversionFactor = 1.0d
  val symbol = "o"
}

object Kilobytes extends StorageUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "K"
}

object Kibibytes extends StorageUnit {
  val conversionFactor = BinarySystem.Kilo
  val symbol = "Ki"
}

object Megabytes extends StorageUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "M"
}

object Mebibytes extends StorageUnit {
  val conversionFactor = BinarySystem.Mega
  val symbol = "Mi"
}

object Gigabytes extends StorageUnit {
  val conversionFactor = MetricSystem.Giga
  val symbol = "G"
}

object Gibibytes extends StorageUnit {
  val conversionFactor = BinarySystem.Giga
  val symbol = "Gi"
}

object Terabytes extends StorageUnit {
  val conversionFactor = MetricSystem.Tera
  val symbol = "T"
}

object Tebibytes extends StorageUnit {
  val conversionFactor = BinarySystem.Tera
  val symbol = "Ti"
}

object Petabytes extends StorageUnit {
  val conversionFactor = MetricSystem.Peta
  val symbol = "P"
}

object Pebibytes extends StorageUnit {
  val conversionFactor = BinarySystem.Peta
  val symbol = "Pi"
}

object Exabytes extends StorageUnit {
  val conversionFactor = MetricSystem.Exa
  val symbol = "E"
}

object Exbibytes extends StorageUnit {
  val conversionFactor = BinarySystem.Exa
  val symbol = "Ei"
}

object Zettabytes extends StorageUnit {
  def conversionFactor = MetricSystem.Zetta
  def symbol = "Z"
}

object Zebibytes extends StorageUnit {
  def conversionFactor = BinarySystem.Zetta
  def symbol = "Zi"
}

object Yottabytes extends StorageUnit {
  def conversionFactor = MetricSystem.Yotta
  def symbol = "Y"
}

object Yobibytes extends StorageUnit {
  def conversionFactor = BinarySystem.Yotta
  def symbol = "Yi"
}

object StorageConversions {
  lazy val byte = Bytes(1)
  lazy val kilobyte = Kilobytes(1)
  lazy val kibibyte = Kibibytes(1)
  lazy val megabyte = Megabytes(1)
  lazy val mebibyte = Mebibytes(1)
  lazy val gigabyte = Gigabytes(1)
  lazy val gibibyte = Gibibytes(1)
  lazy val terabyte = Terabytes(1)
  lazy val tebibyte = Tebibytes(1)
  lazy val petabyte = Petabytes(1)
  lazy val pebibyte = Pebibytes(1)
  lazy val exabyte = Exabytes(1)
  lazy val exbibyte = Exbibytes(1)
  lazy val zettabyte = Zettabytes(1)
  lazy val zebibyte = Zebibytes(1)
  lazy val yottabyte = Yottabytes(1)
  lazy val yobibyte = Yobibytes(1)

  implicit class StorageConversions[A](n: A)(implicit num: Numeric[A]) {
    def bytes = Bytes(n)
    def kb = Kilobytes(n)
    def kilobytes = Kilobytes(n)
    def mb = Megabytes(n)
    def megabytes = Megabytes(n)
    def gb = Gigabytes(n)
    def gigabytes = Gigabytes(n)
    def tb = Terabytes(n)
    def terabytes = Terabytes(n)
    def pb = Petabytes(n)
    def petabytes = Petabytes(n)
    def eb = Exabytes(n)
    def exabytes = Exabytes(n)
    def zb = Zettabytes(n)
    def zettabytes = Zettabytes(n)
    def yb = Yottabytes(n)
    def yottabytes = Yottabytes(n)

    def kib = Kibibytes(n)
    def kibibytes = Kibibytes(n)
    def mib = Mebibytes(n)
    def mebibytes = Mebibytes(n)
    def gib = Gibibytes(n)
    def gibibytes = Gibibytes(n)
    def tib = Tebibytes(n)
    def tebibytes = Tebibytes(n)
    def pib = Pebibytes(n)
    def pebibytes = Pebibytes(n)
    def eib = Exbibytes(n)
    def exbibytes = Exbibytes(n)
    def zib = Zebibytes(n)
    def zebibytes = Zebibytes(n)
    def yib = Yobibytes(n)
    def yobibytes = Yobibytes(n)
  }

  implicit class StorageStringConversions(s: String) {
    def toStorage = Storage(s)
  }

  implicit object StorageNumeric extends AbstractQuantityNumeric[Storage](Storage.primaryUnit)
}
