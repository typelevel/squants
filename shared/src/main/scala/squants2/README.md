# Generic Number support in Squants 2.0

## Motivation

Squants currently uses `Double` for a Quantity value.
This limits the level of precision and accuracy in results from Squants operations, making unsuitable for some use cases.
Allowing the user to choose the numeric type for a Quantity will remove this limitation, and broaden adoption.

## Goals

* Generic Numeric type for Quantity values with Interoperability ✓ 
* Refactor Core Model to Support Generics ✓
* Refactor SI Base Dimensions to validate model ✓
* Refactor all Dimensions - #TODO
* Update Docs - #TODO
* Companion project for Spire - #TODO

## Generic Numerics and Interoperability

This refactoring supports using a generic type for `Quantity.value`.

It is based on the new `QNumeric` type class.

```scala
trait QNumeric[A] {
  def zero: A
  def one: A
  def plus[B](a: A, b: B)(implicit f: B => A): A
  def minus[B](a: A, b: B)(implicit f: B => A): A
  def times[B](a: A, b: B)(implicit f: B => A): A
  def divide[B](a: A, b: B)(implicit f: B => A): A
  def mod[B](a: A, b: B)(implicit f: B => A): A
  def negate(a: A): A
  def abs(a: A): A
  def ceil(a: A): A
  def floor(a: A): A
  def rint(a: A): A
  def rounded(a: A, scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): A

  def compare[B](a: A, b: B)(implicit f: B => A): Int
  def lt[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) < 0
  def lteq[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) <= 0
  def gt[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) > 0
  def gteq[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) >= 0
  def equiv[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) == 0

  def toInt(a: A): Int
  def toLong(a: A): Long
  def toFloat(a: A): Float
  def toDouble(a: A): Double
  def fromDouble(a: Double): A
  def fromString(str: String): Option[A]
}
```
Unlike the `Numeric` trait in the standard library `QNumeric` implements its binary operations to accept
any value for the second operand, along with an implicit conversion from that type to the `QNumeric` type parameter.

This allows for more readable operations between different numeric types.

```scala
val massN = Kilograms(1) // Mass[Int]
val massD = Kilograms(10.22) // Mass[Double]
val massBD = Kilograms(BigDecimal(10.22)) // Mass[BigDecimal]

// convert numbers to others types (requires implicit conversion to QNumeric in scope)
val massN2D: Mass[Double] = massN.asNum[Double]
val massD2B: Mass[BigDecimal] = massD.asNum[BigDecimal]

// the right-side operand is automatically converted before operations are applied
// For standard types, built-in implicit conversions are used.

val massSum = massBD + massD + massN // implicitly converted to Mass[BigDecimal]
val massSum2 = massN.asNum[BigDecimal] + massD + massBD // a bit more explicit

// No default implicit conversions from Double to Int (and none provided as it creates precision loss)
//  val massSum3 = massN + massD // No implicit prevents this code from compiling - GOOD!

// But you can be explicit
val massSumN = massN + massD.map(_.toInt)  // Mass[Int]
val massSumD = massN.asNum[Double] + massD // Mass[Double]
```

Of course, this ability is provided by the standard library to some degree, but this type class
allows us to support conversions for other, 3rd party types.

Any type can be used as `Quantity.value` for which there is a `QNumeric` type class implementation.
This library provides type classes for `Double`, `Float`, `Int`, `Long` and `BigDecimal`.

A companion project is planned for Spire types, which can also be used as an example for creating companions to other numeric type libraries.

Unit conversion factors are still defined using `Double`. 
However, they are converted to the `QNumeric` type used in the `Quantity` before conversions are applied.
This should help preserve precision, however, we may want to consider using `BigDecimal` to hold conversion factors.

## Refactor Model to Support Generics

The core model has been significantly refactored.

`Dimension` is now the "top-level" concept and `UnitOfMeasure` and `Quantity` are based on that.

An abbreviated view ...
```scala
abstract class Dimension(val name: String) {
  type D = this.type
  def units: Set[UnitOfMeasure[D]]
  def primaryUnit: UnitOfMeasure[D] with PrimaryUnit
  def siUnit: UnitOfMeasure[D] with SiUnit
  /* ... */
}

trait UnitOfMeasure[D <: Dimension] {
  def dimension: D
  def symbol: String
  def conversionFactor: Double  // maybe BigDecimal instead
  def apply[A: QNumeric](a: A): Quantity[A, D]
  /* ... */
}

abstract class Quantity[A: QNumeric, D <: Dimension] {
  type Q[B] <: Quantity[B, D]
  def value: A
  def unit: UnitOfMeasure[D]
  /* ... */
}


```

Most of the complexity at this point, particularly around the type system, is encapsulated within the `Quantity` and other core classes.

However, each quantity type must be refactored to use `QNumeric`, and provide numeric interoperability for dimensional operations.

`UnitOfMeasure` definitions are much easier to create, read and maintain by using an abstract class instead of a trait for the base unit type.
(*A refactoring that is possible in the current 1.x model, as well*)

```scala

final case class Length[A: QNumeric] private (value: A, unit: LengthUnit) extends Quantity[A, Length.type] {
 
  override type Q[B] = Length[B]

  def *[B](that: Length[B])(implicit f: B => A): Area[A] = SquareMeters(to(Meters) * that.asNum[A].to(Meters))
  def *[B](that: Area[B])(implicit f: B => A): Volume[A] = CubicMeters(to(Meters) * that.asNum[A].to(SquareMeters))
}

object Length extends BaseDimension("Length", "L") {

  override lazy val primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Meters
  override lazy val siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Meters
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Meters, Feet)

  // Constructors from QNumeric values
  implicit class LengthCons[A: QNumeric](a: A) {
    def meters: Length[A] = Meters(a)
    def feet: Length[A] = Feet(a)
  }

  // Constants
  lazy val meter: Length[Int] = Meters(1)

}

abstract class LengthUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[Length.type] {
  override def dimension: Length.type = Length
  override def apply[A: QNumeric](value: A): Length[A] = Length(value, this)
}

case object Meters extends LengthUnit("m", 1) with PrimaryUnit with SiBaseUnit
case object Feet extends LengthUnit("ft", .3048006096)
```

## Status and Approach

### Generic Value and Core Model 

The core model has been refactored to use `QNumeric` types for values.
This includes all classes except `SVector`

This refactored code has been added to this new `squants2` package within the `shared [squants-sources]` project,
where it can live during refactoring.

### SI Base Dimensions

The 7 SI base dimensions and `Dimensionless` have been refactored.

* ChemicalAmount
* Length
* LuminousIntensity
* Mass
* Temperature
* Time

*Only a few dimensional operations have been implemented*

### Derived Dimensions

2 Derived Dimensions (`Area` and `Volume`) have been refactored, but with only a couple units each.

### Approach to Complete Refactoring

1. Refactor `TimeDerivative` and `TimeIntegral` traits
2. Refactor `SVector`
3. Refactor `market` package
4. Migrate remaining derived dimensions
5. Refactor and Migrate Tests
6. Update Docs
7. Create a `squants-spire` companion project

The refactored code can continue being added to the `squants2` package within this development branch.
Once things are fully migrated, `squants` can be removed and `squants2` renamed to `squants`.
When all looks good, we can create a PR for Squants 2.0-SNAPSHOT.

#### Note on 1.x Compatibility

Of course, this will be a breaking change in many respects.
How dimensions, units and quantities are defined and implemented is very different.
User code that defines new dimensions or units will require refactoring.

However, we may be able to provide a "shadow" package that provides backward compatibility for more basic usage of 1.x.
It could do this by providing type aliases that use `QNumeric[Double]`

```scala
package squants

package object double {
  
  type Length = squants.space.Length[Double]
  /* ... */
}
```

User code would then need to replace `import squants.*` with `import squants.double.*`,
which should minimize required code changes.

This could be validated by applying the existing 1.x test suite (or some subset of it) to that shadow package.
