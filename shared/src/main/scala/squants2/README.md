# Generic Number support in Squants 2.0

## Motivation

Squants currently uses `Double` for a Quantity value.
This limits the level of precision and accuracy in results from Squants operations, making unsuitable for some use cases.
Allowing the user to choose the numeric type for a Quantity will remove this limitation, and broaden adoption.

## Goals

* Support for `Numeric` as Quantity values with Interoperability between types ✓ 
* Refactor Core Model to Support Generics ✓
* Refactor SI Base Dimensions to validate model ✓
* Refactor all Dimensions - #TODO
* Update Docs - #TODO

## Generic Numerics and Interoperability

This refactoring supports using a `Numeric` type for `Quantity.value`.

```scala
import squants2.mass._

val massN = Kilograms(1) // Mass[Int]
val massD = Kilograms(10.22) // Mass[Double]
val massBD = Kilograms(BigDecimal(10.22)) // Mass[BigDecimal]

// convert numbers to others types (requires implicit conversion to Numeric in scope)
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

Unit conversion factors are still defined using `Double`. 
However, they are converted to the `Numeric` type used in the `Quantity` before conversions are applied.
This should help preserve precision, however, we may want to consider using `BigDecimal` to hold conversion factors.

In either case, the conversionFactor is converted to the target Numeric using the `Numeric.parseString` method.
This is only available in Scala 2.13 and above, therefore Scala 2.12 has been removed from the build.
Alternatives ideas to this are welcome.

## Refactor Model to Support Generics

The core model has been significantly refactored.

`Dimension` is now the "top-level" concept and `UnitOfMeasure` and `Quantity` are based on that.

An abbreviated view ...
```scala
import squants2._

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
  def apply[A: Numeric](a: A): Quantity[A, D]
  /* ... */
}

abstract class Quantity[A: Numeric, D <: Dimension] {
  type Q[B] <: Quantity[B, D]
  def value: A
  def unit: UnitOfMeasure[D]
  /* ... */
}


```

Most of the complexity at this point, particularly around the type system, is encapsulated within the `Quantity` and other core classes.

However, each quantity type must be refactored to use `Numeric`, and provide numeric interoperability for dimensional operations.

`UnitOfMeasure` definitions are much easier to create, read and maintain by using an abstract class instead of a trait for the base unit type.
(*A refactoring that is possible in the current 1.x model, as well*)

```scala
import squants2._

final case class Length[A: Numeric] private (value: A, unit: LengthUnit) extends Quantity[A, Length.type] {
 
  override type Q[B] = Length[B]

  def *[B](that: Length[B])(implicit f: B => A): Area[A] = SquareMeters(to(Meters) * that.asNum[A].to(Meters))
  def *[B](that: Area[B])(implicit f: B => A): Volume[A] = CubicMeters(to(Meters) * that.asNum[A].to(SquareMeters))
}

object Length extends BaseDimension("Length", "L") {

  override lazy val primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Meters
  override lazy val siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Meters
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Meters, Feet)

  // Constructors from Numeric values
  implicit class LengthCons[A: Numeric](a: A) {
    def meters: Length[A] = Meters(a)
    def feet: Length[A] = Feet(a)
  }

  // Constants
  lazy val meter: Length[Int] = Meters(1)

}

abstract class LengthUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[Length.type] {
  override def dimension: Length.type = Length
  override def apply[A: Numeric](value: A): Length[A] = Length(value, this)
}

case object Meters extends LengthUnit("m", 1) with PrimaryUnit with SiBaseUnit
case object Feet extends LengthUnit("ft", .3048006096)
```

## Status and Approach

### Generic Value and Core Model 

All components of the core model has been refactored to use `Numeric` types for values.

`SVector` has been reduced to only working with `Quantity`s.  The DoubleVector has been deprecated in favor of just using `SVector[Dimensionless]`

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

The following derived dimensions are functioning:

* Angle
* Area (only 2 units)
* Volume (only 2 units)

### Approach to Complete Refactoring

1. Refactor `TimeDerivative` and `TimeIntegral` traits
2. Refactor `market` package
3. Migrate remaining derived dimensions
4. Refactor and Migrate Tests
5. Update Docs

The refactored code can continue being added to the `squants2` package within this development branch.
Once things are fully migrated, `squants` can be removed and `squants2` renamed to `squants`.
When all looks good, we can create a PR for Squants 2.0-SNAPSHOT.

#### Note on 1.x Compatibility

Of course, this will be a breaking change in many respects.
How dimensions, units and quantities are defined and implemented is very different.
User code that defines new dimensions or units will require refactoring.

However, we may be able to provide a "shadow" package that provides backward compatibility for more basic usage of 1.x.
It could do this by providing type aliases that use `Numeric[Double]`

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


## Alternative Solutions

[Terra](https://github.com/hunterpayne/terra) is another library, based on Squants, that has taken a different approach to implementing support for generic values.
Checkout that project to see if it fits your use case better.
