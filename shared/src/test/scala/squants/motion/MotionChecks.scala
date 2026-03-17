/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.space.{ SquareMeters, CubicMeters, Meters }
import squants.time.Seconds
import squants.mass.Kilograms
import org.scalacheck.Properties
import squants.QuantityChecks
import org.scalacheck.Prop._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object MotionChecks extends Properties("Motion") with QuantityChecks {

  implicit val tolVel: Velocity = MetersPerSecond(tol)
  implicit val tolAcc: Acceleration = MetersPerSecondSquared(tol)
  implicit val tolJerk: Jerk = MetersPerSecondCubed(tol)
  implicit val tolMfr: MassFlow = KilogramsPerSecond(tol)
  implicit val tolVfr: VolumeFlow = CubicMetersPerSecond(tol)

  property("Distance = Velocity * Time") = forAll(posNum, posNum) { (velocity: TestData, time: TestData) =>
    Meters(velocity * time) == MetersPerSecond(velocity) * Seconds(time) &&
      Meters(velocity * time) == Seconds(time) * MetersPerSecond(velocity) &&
      Seconds(time) =~ Meters(velocity * time) / MetersPerSecond(velocity) &&
      MetersPerSecond(velocity) =~ Meters(velocity * time) / Seconds(time)
  }

  property("Velocity = Acceleration * Time") = forAll(posNum, posNum) { (acc: TestData, time: TestData) =>
    MetersPerSecond(acc * time) == MetersPerSecondSquared(acc) * Seconds(time) &&
      MetersPerSecond(acc * time) == Seconds(time) * MetersPerSecondSquared(acc) &&
      Seconds(time) =~ MetersPerSecond(acc * time) / MetersPerSecondSquared(acc) &&
      MetersPerSecondSquared(acc) =~ MetersPerSecond(acc * time) / Seconds(time)
  }

  property("Acceleration = Jerk * Time") = forAll(posNum, posNum) { (jerk: TestData, time: TestData) =>
    MetersPerSecondSquared(jerk * time) == MetersPerSecondCubed(jerk) * Seconds(time) &&
      MetersPerSecondSquared(jerk * time) == Seconds(time) * MetersPerSecondCubed(jerk) &&
      Seconds(time) =~ MetersPerSecondSquared(jerk * time) / MetersPerSecondCubed(jerk) &&
      MetersPerSecondCubed(jerk) =~ MetersPerSecondSquared(jerk * time) / Seconds(time)
  }

  property("Momentum = Velocity * Mass") = forAll(posNum, posNum) { (velocity: TestData, mass: TestData) =>
    NewtonSeconds(velocity * mass) == MetersPerSecond(velocity) * Kilograms(mass) &&
      NewtonSeconds(velocity * mass) == Kilograms(mass) * MetersPerSecond(velocity) &&
      Kilograms(mass) == NewtonSeconds(velocity * mass) / MetersPerSecond(velocity) &&
      MetersPerSecond(velocity).plusOrMinus(MetersPerSecond(tol)).contains(NewtonSeconds(velocity * mass) / Kilograms(mass))
  }

  property("Force = Acceleration * Mass") = forAll(posNum, posNum) { (acc: TestData, mass: TestData) =>
    Newtons(acc * mass) == MetersPerSecondSquared(acc) * Kilograms(mass) &&
      Newtons(acc * mass) == Kilograms(mass) * MetersPerSecondSquared(acc) &&
      MetersPerSecondSquared(acc) == Newtons(acc * mass) / Kilograms(mass) &&
      Kilograms(mass).plusOrMinus(Kilograms(tol)).contains(Newtons(acc * mass) / MetersPerSecondSquared(acc))
  }

  property("Momentum = Force * Time") = forAll(posNum, posNum) { (force: TestData, time: TestData) =>
    NewtonSeconds(force * time) == Newtons(force) * Seconds(time) &&
      NewtonSeconds(force * time) == Seconds(time) * Newtons(force) &&
      Seconds(time) =~ NewtonSeconds(force * time) / Newtons(force) &&
      Newtons(force).plusOrMinus(Newtons(tol)).contains(NewtonSeconds(force * time) / Seconds(time))
  }

  property("Force = Pressure * Area") = forAll(posNum, posNum) { (pressure: TestData, area: TestData) =>
    Newtons(pressure * area) == Pascals(pressure) * SquareMeters(area) &&
      Newtons(pressure * area) == SquareMeters(area) * Pascals(pressure) &&
      Pascals(pressure) == Newtons(pressure * area) / SquareMeters(area) &&
      SquareMeters(area) == Newtons(pressure * area) / Pascals(pressure)
  }

  property("Mass = MassFlowRate * Time") = forAll(posNum, posNum) { (flowRate: TestData, time: TestData) =>
    Kilograms(flowRate * time) == KilogramsPerSecond(flowRate) * Seconds(time) &&
      Kilograms(flowRate * time) == Seconds(time) * KilogramsPerSecond(flowRate) &&
      Seconds(time) =~ Kilograms(flowRate * time) / KilogramsPerSecond(flowRate) &&
      KilogramsPerSecond(flowRate) =~ Kilograms(flowRate * time) / Seconds(time)
  }

  property("Volume = VolumeFlowRate * Time") = forAll(posNum, posNum) { (flowRate: TestData, time: TestData) =>
    CubicMeters(flowRate * time) == CubicMetersPerSecond(flowRate) * Seconds(time) &&
      CubicMeters(flowRate * time) == Seconds(time) * CubicMetersPerSecond(flowRate) &&
      Seconds(time) =~ CubicMeters(flowRate * time) / CubicMetersPerSecond(flowRate) &&
      CubicMetersPerSecond(flowRate) =~ CubicMeters(flowRate * time) / Seconds(time)
  }
}
