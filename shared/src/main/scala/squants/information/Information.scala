/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.information

import squants._
import squants.time.TimeIntegral

/**
 * Represents information.
 *
 * @author Derek Morr
 * @since 0.6.0
 * @param value value in [[squants.information.Bytes]]
 */
final class Information private(val value: Double, val unit: InformationUnit)
    extends Quantity[Information]
    with TimeIntegral[DataRate] {

  def dimension = Information

  protected def timeDerived = BytesPerSecond(toBytes)
  protected[squants] def time = Seconds(1)

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

trait InformationUnit extends UnitOfMeasure[Information] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Information(n, this)
}

/**
 * Factory singleton for information
 */
object Information extends Dimension[Information] with BaseDimension {
  private[information] def apply[A](n: A, unit: InformationUnit)(implicit num: Numeric[A]) = new Information(num.toDouble(n), unit)
  def apply = parse _
  def name = "Information"
  def primaryUnit = Bytes
  def siUnit = Bytes
  def units = Set(Bytes, Kilobytes, Kibibytes, Megabytes, Mebibytes,
    Gigabytes, Gibibytes, Terabytes, Tebibytes, Petabytes, Pebibytes,
    Exabytes, Exbibytes, Zettabytes, Zebibytes, Yottabytes, Yobibytes)
  def dimensionSymbol = "B"
}

object Bytes extends InformationUnit with PrimaryUnit with SiBaseUnit {
  val symbol = "B"
}

object Octets extends InformationUnit {
  val conversionFactor = 1.0d
  val symbol = "o"
}

object Kilobytes extends InformationUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "KB"
}

object Kibibytes extends InformationUnit {
  val conversionFactor = BinarySystem.Kilo
  val symbol = "KiB"
}

object Megabytes extends InformationUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "MB"
}

object Mebibytes extends InformationUnit {
  val conversionFactor = BinarySystem.Mega
  val symbol = "MiB"
}

object Gigabytes extends InformationUnit {
  val conversionFactor = MetricSystem.Giga
  val symbol = "GB"
}

object Gibibytes extends InformationUnit {
  val conversionFactor = BinarySystem.Giga
  val symbol = "GiB"
}

object Terabytes extends InformationUnit {
  val conversionFactor = MetricSystem.Tera
  val symbol = "TB"
}

object Tebibytes extends InformationUnit {
  val conversionFactor = BinarySystem.Tera
  val symbol = "TiB"
}

object Petabytes extends InformationUnit {
  val conversionFactor = MetricSystem.Peta
  val symbol = "PB"
}

object Pebibytes extends InformationUnit {
  val conversionFactor = BinarySystem.Peta
  val symbol = "PiB"
}

object Exabytes extends InformationUnit {
  val conversionFactor = MetricSystem.Exa
  val symbol = "EB"
}

object Exbibytes extends InformationUnit {
  val conversionFactor = BinarySystem.Exa
  val symbol = "EiB"
}

object Zettabytes extends InformationUnit {
  def conversionFactor = MetricSystem.Zetta
  def symbol = "ZB"
}

object Zebibytes extends InformationUnit {
  def conversionFactor = BinarySystem.Zetta
  def symbol = "ZiB"
}

object Yottabytes extends InformationUnit {
  def conversionFactor = MetricSystem.Yotta
  def symbol = "YB"
}

object Yobibytes extends InformationUnit {
  def conversionFactor = BinarySystem.Yotta
  def symbol = "YiB"
}

object InformationConversions {
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

  implicit class InformationConversions[A](n: A)(implicit num: Numeric[A]) {
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

  implicit class InformationStringConversions(s: String) {
    def toInformation = Information(s)
  }

  implicit object InformationNumeric extends AbstractQuantityNumeric[Information](Information.primaryUnit)
}
