package squants.experimental.constants

import squants._
import squants.motion._

trait Universal {
  // speed of light in vacuum
  val SpeedOfLight = new Constant[Velocity] {
    val value = MetersPerSecond(299792458)
    val symbol = "𝑐"
    val uncertainty = MetersPerSecond(0)
  }

}