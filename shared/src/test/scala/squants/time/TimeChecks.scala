/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import org.scalacheck.Prop._
import org.scalacheck.Properties
import squants.{ Each, QuantityChecks }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object TimeChecks extends Properties("Time") with QuantityChecks {

  implicit val tolFreq: Frequency = Hertz(tol)

  property("Milliseconds = Seconds * 1000") = forAll(posNum) { (seconds: TestData) =>
    Milliseconds(seconds * 1000) == Seconds(seconds)
  }

  property("Seconds = Minutes * 60") = forAll(posNum) { (minutes: TestData) =>
    Seconds(minutes * 60) == Minutes(minutes)
  }

  property("Minutes = Hours * 60") = forAll(posNum) { (hours: TestData) =>
    Minutes(hours * 60) == Hours(hours)
  }

  property("Hours = Days * 24") = forAll(posNum) { (days: TestData) =>
    Hours(days * 24) == Days(days)
  }

  property("Count = Frequency * Time") = forAll(posNum, posNum) { (freq: TestData, time: TestData) =>
    Each(freq * time) == Hertz(freq) * Seconds(time)
    Each(freq * time) == Seconds(time) * Hertz(freq)
    Seconds(time) =~ Each(freq * time) / Hertz(freq)
    Hertz(freq) =~ Each(freq * time) / Seconds(time)
  }
}
