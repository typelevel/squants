package squants.time

import org.scalatest.{ FlatSpec, Matchers }
import squants.CustomMatchers

/**
 * @author  garyKeorkunian
 * @since   0.5.1
 *
 */
class TimeSquaredSpec extends FlatSpec with Matchers with CustomMatchers {

  behavior of "Time Squared as an intermediate operand in time derivative calculations"

  it should "create values in the correct units" in {
    SecondsSquared(10) should be(TimeSquared(Seconds(10), Seconds(10)))
    MinutesSquared(10) should be(TimeSquared(Minutes(10), Minutes(10)))
    HoursSquared(10) should be(TimeSquared(Hours(10), Hours(10)))
    TimeSquared(Seconds(5)) should be(TimeSquared(Seconds(5), Seconds(5)))
  }
}
