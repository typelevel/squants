/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import org.scalacheck.Prop._
import org.scalacheck.Properties
import squants.QuantityChecks
import squants.energy.Watts
import squants.space.{ Meters, SquareMeters, SquaredRadians }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object RadioChecks extends Properties("Radio") with QuantityChecks {

  property("Power = Irradiance * Area") = forAll(posNum, posNum) { (irr: TestData, area: TestData) =>
    Watts(irr * area) == WattsPerSquareMeter(irr) * SquareMeters(area) &&
      Watts(irr * area) == SquareMeters(area) * WattsPerSquareMeter(irr) &&
      SquareMeters(area) == Watts(irr * area) / WattsPerSquareMeter(irr) &&
      WattsPerSquareMeter(irr) == Watts(irr * area) / SquareMeters(area)
  }

  property("RadiantIntensity = Radiance * Area") = forAll(posNum, posNum) { (rad: TestData, area: TestData) =>
    WattsPerSteradian(rad * area) == WattsPerSteradianPerSquareMeter(rad) * SquareMeters(area) &&
      WattsPerSteradian(rad * area) == SquareMeters(area) * WattsPerSteradianPerSquareMeter(rad) &&
      SquareMeters(area) == WattsPerSteradian(rad * area) / WattsPerSteradianPerSquareMeter(rad) &&
      WattsPerSteradianPerSquareMeter(rad) == WattsPerSteradian(rad * area) / SquareMeters(area)
  }

  property("Power = RadiantIntensity * SolidAngle") = forAll(posNum, posNum) { (rad: TestData, solidAngle: TestData) =>
    Watts(rad * solidAngle) == WattsPerSteradian(rad) * SquaredRadians(solidAngle) &&
      Watts(rad * solidAngle) == SquaredRadians(solidAngle) * WattsPerSteradian(rad) &&
      SquaredRadians(solidAngle) == Watts(rad * solidAngle) / WattsPerSteradian(rad) &&
      WattsPerSteradian(rad) == Watts(rad * solidAngle) / SquaredRadians(solidAngle)
  }

  property("RadiantIntensity = SpectralIntensity * Length") = forAll(posNum, posNum) { (specInt: TestData, length: TestData) =>
    WattsPerSteradian(specInt * length) == WattsPerSteradianPerMeter(specInt) * Meters(length) &&
      WattsPerSteradian(specInt * length) == Meters(length) * WattsPerSteradianPerMeter(specInt) &&
      Meters(length) == WattsPerSteradian(specInt * length) / WattsPerSteradianPerMeter(specInt) &&
      WattsPerSteradianPerMeter(specInt) == WattsPerSteradian(specInt * length) / Meters(length)
  }

  property("Power = SpectralPower * Length") = forAll(posNum, posNum) { (specPower: TestData, length: TestData) =>
    Watts(specPower * length) == WattsPerMeter(specPower) * Meters(length) &&
      Watts(specPower * length) == Meters(length) * WattsPerMeter(specPower) &&
      Meters(length).plusOrMinus(Meters(tol)).contains(Watts(specPower * length) / WattsPerMeter(specPower)) &&
      WattsPerMeter(specPower).plusOrMinus(WattsPerMeter(tol)).contains(Watts(specPower * length) / Meters(length))
  }
}
