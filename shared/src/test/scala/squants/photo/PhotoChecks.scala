/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import org.scalacheck.Prop._
import org.scalacheck.Properties
import squants.QuantityChecks
import squants.space.{ SquareMeters, SquaredRadians }
import squants.time.Seconds

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object PhotoChecks extends Properties("Photo") with QuantityChecks {

  implicit val tolLumFlux: LuminousFlux = Lumens(tol)
  implicit val tolIllum: Illuminance = Lux(tol)

  property("Lumens = Lux * SquareMeters") = forAll(posNum, posNum) { (lux: TestData, sm: TestData) =>
    Lumens(lux * sm) == Lux(lux) * SquareMeters(sm) &&
      Lumens(lux * sm) == SquareMeters(sm) * Lux(lux) &&
      Lux(lux) == Lumens(lux * sm) / SquareMeters(sm) &&
      SquareMeters(sm) == Lumens(lux * sm) / Lux(lux)
  }

  property("LuxSeconds = Lux * Seconds") = forAll(posNum, posNum) { (lux: TestData, seconds: TestData) =>
    LuxSeconds(lux * seconds) == Lux(lux) * Seconds(seconds) &&
      LuxSeconds(lux * seconds) == Seconds(seconds) * Lux(lux) &&
      Lux(lux) =~ LuxSeconds(lux * seconds) / Seconds(seconds) &&
      Seconds(seconds) =~ LuxSeconds(lux * seconds) / Lux(lux)
  }

  property("Candelas = CandelasPerSquareMeter * SquareMeters") = forAll(posNum, posNum) { (cpsm: TestData, sm: TestData) =>
    Candelas(cpsm * sm) == CandelasPerSquareMeter(cpsm) * SquareMeters(sm) &&
      Candelas(cpsm * sm) == SquareMeters(sm) * CandelasPerSquareMeter(cpsm) &&
      CandelasPerSquareMeter(cpsm) == Candelas(cpsm * sm) / SquareMeters(sm) &&
      SquareMeters(sm) == Candelas(cpsm * sm) / CandelasPerSquareMeter(cpsm)
  }

  property("LumenSeconds = Lumens * Seconds") = forAll(posNum, posNum) { (lumens: TestData, seconds: TestData) =>
    LumenSeconds(lumens * seconds) == Lumens(lumens) * Seconds(seconds) &&
      LumenSeconds(lumens * seconds) == Seconds(seconds) * Lumens(lumens) &&
      Lumens(lumens) =~ LumenSeconds(lumens * seconds) / Seconds(seconds) &&
      Seconds(seconds) =~ LumenSeconds(lumens * seconds) / Lumens(lumens)
  }

  property("Lumens = Candelas * SquaredRadians") = forAll(posNum, posNum) { (candelas: TestData, sqRadians: TestData) =>
    Lumens(candelas * sqRadians) == Candelas(candelas) * SquaredRadians(sqRadians) &&
      Lumens(candelas * sqRadians) == SquaredRadians(sqRadians) * Candelas(candelas) &&
      Candelas(candelas) == Lumens(candelas * sqRadians) / SquaredRadians(sqRadians) &&
      SquaredRadians(sqRadians) == Lumens(candelas * sqRadians) / Candelas(candelas)
  }
}
