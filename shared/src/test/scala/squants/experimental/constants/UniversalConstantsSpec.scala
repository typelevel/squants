package squants.experimental.constants

import org.scalatest.{FlatSpec, Matchers}
import squants._
import squants.experimental.constants._
import squants.experimental.constants.ConstantsConversions._
import squants.motion.VelocityConversions._
import squants.time.TimeConversions._
import squants.space.LengthConversions._

class UniversalConstantsSpec extends FlatSpec with Matchers {
  import universal._

  behavior of "Universal Constants"

  it should "have a value" in {
    SpeedOfLight.value should be(299792458.mps)
  }
  it should "have a symbol" in {
    SpeedOfLight.symbol should be("𝑐")
  }
  it should "have an error range" in {
    SpeedOfLight.uncertainty should be(0.mps)
  }
  it should "support scalar mulitplication" in {
    (SpeedOfLight * 2) should be(599584916.0.mps)
  }
  it should "support operations with other units" in {
    (1.seconds * SpeedOfLight.value) should be(299792458.meters)
  }

}
