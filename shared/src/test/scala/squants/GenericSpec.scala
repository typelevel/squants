package squants

import scala.util.Try

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalacheck.Gen.{alphaStr, nonEmptyListOf, posNum}
import org.scalatest.prop.{PropertyChecks, TableFor2}
import org.scalatest.{FlatSpec, Matchers, TryValues}

/**
 * Generic tests for quantities
 */
abstract class GenericSpec[A <: Quantity[A]](dimension: Dimension[A])
  extends FlatSpec
  with Matchers
  with TryValues
  with PropertyChecks {

  // tests must implement these
  def unitConversionsTable: TableFor2[Double, Double]
  def singleUnitValues: TableFor2[A, A]
  def doubleImplicitConversionValues: TableFor2[A, A]
  def implicitStringConversion: String => Try[A]

  behavior of s"${dimension.name} and its Units of Measure"

  it should "create values using UOM factories" in {
    dimension.units.foreach { unit: UnitOfMeasure[A] =>
      forAll { d: Double =>
        unit(d).to(unit) should be (d)
      }
    }
  }

  it should "create values from properly formatted strings" in {
    dimension.units.foreach { unit: UnitOfMeasure[A] =>
      forAll { d: Double =>
        dimension(s"$d ${unit.symbol}").success.value should be (unit(d))
      }
    }
  }


  it should "properly convert to all supported Units of Measure" in {
    forAll(unitConversionsTable) { (actual: Double, expected: Double) =>
      actual should be (expected)
    }
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    dimension.units.foreach { unit: UnitOfMeasure[A] =>
      forAll { d: Double =>

        // avoid inconsistent string double formatting
        val Array(qty, unit2) = unit(d).toString.split(" ")
        qty.toDouble should be (d)
        unit2 should be (unit.symbol)
      }
    }
  }

  it should "provide aliases for single unit values" in {
    forAll(singleUnitValues) { (unitValue, expected) =>
      unitValue should be(expected)
    }
  }

  behavior of s"${dimension.name}Conversions"

  it should "provide implicit conversions from Double" in {
    forAll(doubleImplicitConversionValues) { (actual, expected) =>
      actual should be (expected)
    }
  }

  it should "provide implicit conversion from String when the string is correctly formed" in {
    dimension.units.foreach { unit =>
      forAll { d: Double =>
        implicitStringConversion(s"$d ${unit.symbol}").success.value should be(unit(d))
      }
    }
  }

  it should "fail to implicitly convert a String when the unit is invalid" in {
    val symbols = dimension.units.map(_.symbol)
    forAll(posNum[Double], alphaStr) { (d, notASymbol) =>
      whenever(!symbols.contains(notASymbol)) {
        val str = s"$d $notASymbol"
        implicitStringConversion(str).failure.exception should be
          QuantityParseException(s"Unable to parse ${dimension.name}", str)
      }
    }
  }

  it should "fail to implicitly convert a String when the value is invalid" in {
    dimension.units.foreach { unit =>
      forAll(alphaStr) { notANumber =>
        val str = s"$notANumber $unit"
        implicitStringConversion(str).failure.exception should be
          QuantityParseException(s"Unable to parse ${dimension.name}", str)
      }
    }
  }

  def checkNumeric(implicit numeric: Numeric[A]): Unit = {
    it should "provide numeric support" in {
      val qtyGen: Gen[A] = for {
        value <- arbitrary[Double]
        unit <- Gen.oneOf(dimension.units.toSeq)
      } yield unit(value)

      // check for overflow
      def safeValue(q: Quantity[A]): Boolean = {
        val x = q.to(dimension.primaryUnit)
        !x.isInfinite && !x.isNaN
      }

      forAll(nonEmptyListOf(qtyGen)) { qtys: List[A] =>
        whenever(qtys.forall(safeValue)) {
          val rawTotal: Double = qtys.map(_.to(dimension.primaryUnit)).sum
          val expected: Quantity[A] = dimension.primaryUnit(rawTotal)
          qtys.sum should be(expected)
        }
      }
    }

    it should "provide numeric support when a List is empty" in {
      List.empty[A].sum should be(dimension.primaryUnit(0))
    }
  }
}
