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

An abbreviated view ...
```scala
import squants2._

abstract class Quantity[A: Numeric, Q[_] <: Quantity[_, Q]] {
  def value: A
  def unit: UnitOfMeasure[Q]
  
  // new generic number methods
  def asNum[B: Numeric](implicit f: A => B): Quantity[B, Q]
  def toNum[B: Numeric](uom: UnitOfMeasure[Q])(implicit f: A => B): B
  /* ... */
}

abstract class Dimension[Q[_] <: Quantity[_, Q]](val name: String) {
  def primaryUnit: UnitOfMeasure[Q] with PrimaryUnit[Q]
  def siUnit: UnitOfMeasure[Q] with SiUnit[Q]
  def units: Set[UnitOfMeasure[Q]]
  def apply[A: Numeric](a: A, uom: UnitOfMeasure[Q]): Quantity[A, Q]
  /* ... */
}

trait UnitOfMeasure[Q[_] <: Quantity[_, Q]] {
  def dimension: Dimension[Q]
  def symbol: String
  def conversionFactor: BigDecimal  
  def apply[A: Numeric](a: A): Quantity[A, Q]
  // Default uses conversionFactor, but may be overridden for specific Dimensions of Units
  def convertTo[A](quantity: Quantity[A, Q], uom: UnitOfMeasure[Q])(implicit num: Numeric[A]): Quantity[A, Q]
  /* ... */
}



```

Most of the complexity at this point, particularly around the type system, is encapsulated within the `Quantity` and other core classes.

However, each quantity type must be refactored to use `Numeric`, and provide numeric interoperability for dimensional operations.

`UnitOfMeasure` definitions are much easier to create, read and maintain by using an abstract class instead of a trait for the base unit type.
(*A refactoring that is possible in the current 1.x model, as well*)

```scala
import squants2._

final case class Dimensionless[A: Numeric] private[squants2] (value: A, unit: DimensionlessUnit)
  extends Quantity[A, Dimensionless] {

  def *[B](that: Dimensionless[B])(implicit f: B => A): Dimensionless[A] = Each(to(Each) * that.asNum[A].to(Each))
  def *[B, Q[_] <: Quantity[B, Q]](that: Q[B])(implicit f: B => A): Quantity[A, Q] = that.asNum[A] * to(Each)
  def +[B](that: B)(implicit f: B => A): Dimensionless[A] = Each(to(Each) + that)

  def toPercent[B: Numeric](implicit f: A => B): B = toNum[B](Percent)
  def toEach[B: Numeric](implicit f: A => B): B = toNum[B](Each)
  def toDozen[B: Numeric](implicit f: A => B): B = toNum[B](Dozen)
  def toScore[B: Numeric](implicit f: A => B): B = toNum[B](Score)
  def toGross[B: Numeric](implicit f: A => B): B = toNum[B](Gross)
}

object Dimensionless extends Dimension[Dimensionless]("Dimensionless") {

  override def primaryUnit: UnitOfMeasure[Dimensionless] with PrimaryUnit[Dimensionless] = Each
  override def siUnit: UnitOfMeasure[Dimensionless] with SiUnit[Dimensionless] = Each
  override lazy val units: Set[UnitOfMeasure[Dimensionless]] =
    Set(Percent, Each, Dozen, Score, Gross)

  implicit class DimensionlessCons[A](a: A)(implicit num: Numeric[A]) {
    def percent: Dimensionless[A] = Percent(a)
    def each: Dimensionless[A] = Each(a)
    def dozen: Dimensionless[A] = Dozen(a)
    def score: Dimensionless[A] = Score(a)
    def gross: Dimensionless[A] = Gross(a)
    def hundred: Dimensionless[A] = Each(a * num.fromInt(100))
    def thousand: Dimensionless[A] = Each(a * num.fromInt(1000))
    def million: Dimensionless[A] = Each(a * num.fromInt(1000000))
  }

  lazy val percent: Dimensionless[Int] = Percent(1)
  lazy val each: Dimensionless[Int] = Each(1)
  lazy val dozen: Dimensionless[Int] = Dozen(1)
  lazy val score: Dimensionless[Int] = Score(1)
  lazy val gross: Dimensionless[Int] = Gross(1)
  lazy val hundred: Dimensionless[Int] = Each(100)
  lazy val thousand: Dimensionless[Int] = Each(1000)
  lazy val million: Dimensionless[Int] = Each(1000000)

  override def numeric[A: Numeric]: QuantityNumeric[A, Dimensionless] = DimensionlessNumeric[A]()
  private case class DimensionlessNumeric[A: Numeric]() extends QuantityNumeric[A, Dimensionless](this) {
    override def times(x: Quantity[A, Dimensionless], y: Quantity[A, Dimensionless]): Quantity[A, Dimensionless] =
      Each(x.to(Each) * y.to(Each))
  }
}

abstract class DimensionlessUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Dimensionless] {
  override def dimension: Dimension[Dimensionless] = Dimensionless
  override def apply[A: Numeric](value: A): Dimensionless[A] = Dimensionless(value, this)
}

case object Percent extends DimensionlessUnit("%", MetricSystem.Centi)
case object Each extends DimensionlessUnit("ea", 1) with PrimaryUnit[Dimensionless] with SiUnit[Dimensionless]
case object Dozen extends DimensionlessUnit("dz", 12)
case object Score extends DimensionlessUnit("score", 20)
case object Gross extends DimensionlessUnit("gr", 144)

```

## Status and Approach

### Generic Value and Core Model 

All components of the core model has been refactored to use `Numeric` types for values.

`SVector` has been reduced to only working with `Quantity`s.  The DoubleVector has been deprecated in favor of just using `SVector[Dimensionless]`

This refactored code has been added to this new `squants2` package within the `shared [squants-sources]` project,
where it can live during refactoring.

### Converting existing Dimensions

* All Dimensions and Units from Squants 1 have been ported using a conversion utility.
* Custom operations have been stubbed as commented code within each Dimension

### Approach to Complete Refactoring

1. Refactor `TimeDerivative` and `TimeIntegral` traits
2. Refactor `market` package
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
  
  type Length = squants2.space.Length[Double]
  /* ... */
}
```

User code would then need to replace `import squants.*` with `import squants.double.*`,
which should minimize required code changes.

This could be validated by applying the existing 1.x test suite (or some subset of it) to that shadow package.


## Alternative Solutions

[Terra](https://github.com/hunterpayne/terra) is another library, based on Squants, that has taken a different approach to implementing support for generic values.
Checkout that project to see if it fits your use case better.
