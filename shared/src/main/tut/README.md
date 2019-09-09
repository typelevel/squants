# Squants

**The Scala API for Quantities, Units of Measure and Dimensional Analysis**

Squants is a framework of data types and a domain specific language (DSL) for representing Quantities,
their Units of Measure, and their Dimensional relationships.
The API supports typesafe dimensional analysis, improved domain models and more.
All types are immutable and thread-safe.

[Website](http:/www.squants.com/)
|
[GitHub](https://github.com/typelevel/squants)
|
[Wiki](https://github.com/typelevel/squants/wiki)
|
[![Join the chat at https://gitter.im/typelevel/squants](https://badges.gitter.im/typelevel/squants.svg)](https://gitter.im/typelevel/squants?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
|
[![Scaladocs](https://www.javadoc.io/badge/org.typelevel/squants_2.12.svg?label=scaladoc)](https://static.javadoc.io/org.typelevel/squants_2.12/1.5.0/squants/index.html)
|
[![Build Status](https://travis-ci.org/typelevel/squants.png?branch=master)](https://travis-ci.org/typelevel/squants)


### Current Versions
Current Release: **1.5.0**
([API Docs](https://oss.sonatype.org/service/local/repositories/releases/archive/org/typelevel/squants_2.12/1.5.0/squants_2.12-1.5.0-javadoc.jar/!/index.html#squants.package))

Development Build: **1.6.0-SNAPSHOT**
([API Docs](https://oss.sonatype.org/service/local/repositories/snapshots/archive/org/typelevel/squants_2.12/1.6.0-SNAPSHOT/squants_2.12-1.6.0-SNAPSHOT-javadoc.jar/!/index.html#squants.package))

[Release History](https://github.com/typelevel/squants/wiki/Release-History)

Build services provided by [Travis CI](https://travis-ci.com/)

NOTE - This README reflects the feature set in the branch it can be found.
For more information on feature availability of a specific version see the Release History or the README for a that version

## Installation
Repository hosting for Squants is provided by [Sonatype](https://oss.sonatype.org/).
To use Squants in your SBT project add the following dependency to your build.

    "org.typelevel"  %% "squants"  % "1.5.0"
or

    "org.typelevel"  %% "squants"  % "1.6.0-SNAPSHOT"


To use Squants in your Maven project add the following dependency

```xml
<dependency>
    <groupId>org.typelevel</groupId>
    <artifactId>squants_2.11</artifactId>
    <version>1.5.0</version>
</dependency>
```

Beginning with Squants 0.4.x series, both Scala 2.10 and 2.11 builds are available.
Beginning with Squants 1.x series, Scala 2.10, 2.11 and 2.12 builds are available.
Scala.js is supported on version 0.6.28 and 1.0.0-M8

To use Squants interactively in the Scala REPL, clone the git repo and run `sbt squantsJVM/console`

    git clone https://github.com/typelevel/squants
    cd squants
    sbt squantsJVM/console

## Third-party integrations

This is an incomplete list of third-party libraries that support squants:

* [PureConfig](https://github.com/melrief/pureconfig/)
* [Ciris](https://cir.is/)

If your library isn't listed here, please open a PR to add it!

## Type Safe Dimensional Analysis
*The Trouble with Doubles*

When building programs that perform dimensional analysis, developers are quick to declare
quantities using a basic numeric type, usually Double.  While this may be satisfactory in some situations,
it can often lead to semantic and other logic issues.

For example, when using a Double to describe quantities of Energy (kWh) and Power (kW), it is possible
to compile a program that adds these two values together.  This is not appropriate as kW and kWh
measure quantities of two different dimensions.  The unit kWh is used to measure an amount of Energy used
or produced.  The unit kW is used to measure Power/Load, the rate at which Energy is being used
or produced, that is, Power is the first time derivative of Energy.

*Power = Energy / Time*

Consider the following code:

```tut
val loadKw = 1.2
val energyMwh = 24.2
val sumKw = loadKw + energyMwh
```

This example not only adds quantities of different dimensions (Power vs Energy),
it also fails to convert the scales implied in the val names (Mega vs Kilo).
Because this code compiles, detection of these errors is pushed further into the development cycle.

### Dimensional Type Safety

_Only quantities with the same dimensions may be compared, equated, added, or subtracted._

Squants helps prevent errors like these by type checking operations at compile time and
automatically applying scale and type conversions at run-time.  For example:

```tut:reset
import squants.energy.{Kilowatts, Megawatts, Power}
val load1: Power = Kilowatts(12)
val load2: Power = Megawatts(0.023)
val sum = load1 + load2
sum == Kilowatts(35)
sum == Megawatts(0.035) // comparisions automatically convert scale
```

The above sample works because Kilowatts and Megawatts are both units of Power.  Only the scale is
different and the library applies an appropriate conversion.  Also, notice that keeping track of
the scale within the value name is no longer needed:

```tut
import squants.energy.{Energy, Power, Kilowatts, KilowattHours}
val load: Power = Kilowatts(1.2)
val energy: Energy = KilowattHours(23.0)
```

Invalid operations, like adding power and energy, no longer compile:
```tut:fail
val sum = load + energy
```
By using stronger types, we catch the error earlier in the development cycle, preventing the error made when using Double in the example above.

### Dimensionally Correct Type Conversions

_One may take quantities with different dimensions, and multiply or divide them._

Dimensionally correct type conversions are a key feature of Squants.
Conversions are implemented by defining relationships between Quantity types using the `*` and `/` operators.

Code samples in this section assume these imports:
```tut:silent:reset
import squants.energy.{Kilowatts, Power}
import squants.time.{Hours, Days}
```

The following code demonstrates creating ratio between two quantities of the same dimension,
resulting in a dimensionless value:

```tut
val ratio = Days(1) / Hours(3)
```

This code demonstrates use of the `Power.*` method that takes a `Time` and returns an `Energy`:

```tut
val load = Kilowatts(1.2)
val time = Hours(2)
val energyUsed = load * time
```

This code demonstrates use of the `Energy./` method that takes a `Time` and returns a `Power`:

```tut
val aveLoad: Power = energyUsed / time
```

### Unit Conversions

Code samples in this section assume these imports:
```tut:silent:reset
import scala.language.postfixOps
import squants.energy.{Gigawatts, Kilowatts, Power, Megawatts}
import squants.mass.MassConversions._
import squants.mass.{Kilograms, Pounds}
import squants.thermal.TemperatureConversions._
import squants.thermal.Fahrenheit
```

Quantity values are based in the units used to create them.

```tut
val loadA: Power = Kilowatts(1200)
val loadB: Power = Megawatts(1200)
```

Since Squants properly equates values of a like dimension, regardless of the unit,
there is usually no reason to explicitly convert from one to the other.
This is especially true if the user code is primarily performing dimensional analysis.

However, there are times when you may need to set a Quantity value to a specific unit (eg, for proper JSON encoding).

When necessary, a quantity can be converted to another unit using the `in` method.

```tut
val loadA = Kilowatts(1200)
val loadB = loadA in Megawatts
val loadC = loadA in Gigawatts
```

Sometimes you need to get the numeric value of the quantity in a specific unit
(eg, for submission to an external service that requires a numeric in a specified unit
or to perform analysis beyond Squant's domain)

When necessary, the value can be extracted in the desired unit with the `to` method.

```tut
val load: Power = Kilowatts(1200)
val kw: Double = load to Kilowatts
val mw: Double = load to Megawatts
val gw: Double = load to Gigawatts
```

Most types include methods with convenient aliases for the `to` methods.

```tut
val kw: Double = load toKilowatts
val mw: Double = load toMegawatts
val gw: Double = load toGigawatts
```

NOTE - It is important to use the `to` method for extracting the numeric value,
as this ensures you will be getting the numeric value for the desired unit.
`Quantity.value` should not be accessed directly.
To prevent improper usage, direct access to the `Quantity.value` field may be deprecated in a future version.

Creating strings formatted in the desired unit:

```tut
val kw: String = load toString Kilowatts
val mw: String = load toString Megawatts
val gw: String = load toString Gigawatts
```

Creating `Tuple2[Double, String]` that includes a numeric value and unit symbol:

```tut
val load: Power = Kilowatts(1200)
val kw = load toTuple
val mw = load toTuple Megawatts
val gw = load toTuple Gigawatts
```

This can be useful for passing properly scaled quantities to other processes
that do not use Squants, or require use of more basic types (Double, String)

Simple console based conversions (using DSL described below)

```tut
1.kilograms to Pounds
kilogram / pound

2.1.pounds to Kilograms
2.1.pounds / kilogram

100.C to Fahrenheit
```

### Mapping over Quantity values
Apply a `Double => Double` operation to the underlying value of a quantity, while preserving its type and unit.

```tut:reset
import squants.energy.Kilowatts

val load = Kilowatts(2.0)
val newLoad = load.map(v => v * 2 + 10)
```

The `q.map(f)` method effectively expands to `q.unit(f(q.to(q.unit))`

NOTE - For Money objects, use the `mapAmount` method as this will retain the BigDecimal precision used there.

### Approximations
Create an implicit Quantity value to be used as a tolerance in approximations.
Then use the `approx` method (or `=~`, `~=`, `≈` operators) like you would use the `equals` method (`==` operator).

```tut:reset
import squants.energy.{Kilowatts, Watts}

val load = Kilowatts(2.0)
val reading = Kilowatts(1.9999)
```

Calls to `approx` (and its symbolic aliases) use an implicit tolerance:

```tut
implicit val tolerance = Watts(.1)
load =~ reading
load ≈ reading
load approx reading
```

The `=~` and `≈` are the preferred operators as they have the correct precedence for equality operations.
The `~=` is provided for those who wish to use a more natural looking approx operator using standard characters.
However, because of its lower precedence, user code may require parenthesis around these comparisons.

### Vectors

All `Quantity` types in Squants represent the scalar value of a quantity.
That is, there is no direction information encoded in any of the Quantity types.
This is true even for Quantities which are normally vector quantities (ie. Velocity, Acceleration, etc).

Vector quantities in Squants are implemented as case classes that takes a variable parameter list of like quantities
representing a set of point coordinates in Cartesian space.
The SVector object is a factory for creating DoubleVectors and QuantityVectors.
The dimensionality of the vector is determined by the number of arguments.
Most basic vector operations are currently supported (addition, subtraction, scaling, cross and dot products)

```tut:reset
import squants.{QuantityVector, SVector}
import squants.space.{Kilometers, Length}
import squants.space.LengthConversions._

val vector: QuantityVector[Length] = SVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
val magnitude: Length = vector.magnitude        // returns the scalar value of the vector
val normalized = vector.normalize(Kilometers)   // returns a corresponding vector scaled to 1 of the given unit

val vector2: QuantityVector[Length] = SVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
val vectorSum = vector + vector2        // returns the sum of two vectors
val vectorDiff = vector - vector2       // return the difference of two vectors
val vectorScaled = vector * 5           // returns vector scaled 5 times
val vectorReduced = vector / 5          // returns vector reduced 5 time
val vectorDouble = vector / 5.meters    // returns vector reduced and converted to DoubleVector
val dotProduct = vector * vectorDouble  // returns the Dot Product of vector and vectorDouble

val crossProduct = vector crossProduct vectorDouble  // currently only supported for 3-dimensional vectors
```

Simple non-quantity (Double based) vectors are also supported.

```tut:silent
import squants.DoubleVector

val vector: DoubleVector = SVector(1.2, 4.3, 2.3, 5.4)   // a Four-dimensional vector
```

#### Dimensional conversions within Vector operations.

Currently dimensional conversions are supported by using the slightly verbose, but flexible map method.

```tut:reset
import squants.{DoubleVector, QuantityVector}
import squants.motion.Velocity
import squants.space.{Area, Kilometers, Length, Meters}
import squants.time.Seconds

val vectorLength = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
val vectorArea = vectorLength.map[Area](_ * Kilometers(2))      // QuantityVector(2.4 km², 8.6 km², 4.6 km²)
val vectorVelocity = vectorLength.map[Velocity](_ / Seconds(1)) // QuantityVector(1200.0 m/s, 4300.0 m/s, 2300.0 m/s)

val vectorDouble = DoubleVector(1.2, 4.3, 2.3)
val vectorLength = vectorDouble.map[Length](Kilometers(_))      // QuantityVector(1.2 km, 4.3 km, 2.3 km)
```

Convert QuantityVectors to specific units using the `to` or `in` method - much like Quantities.

```tut
val vectorLength = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
val vectorMetersNum = vectorLength.to(Meters)   // DoubleVector(1200.0, 4300.0, 2300.0)
val vectorMeters = vectorLength.in(Meters)      // QuantityVector(1200.0 m, 4300.0 m, 2300.0 m)
```

## Market Package
Market Types are similar but not quite the same as other quantities in the library.
The primary type, Money, is a Dimensional Quantity, and its Units of Measure are Currencies.
However, because the conversion multipliers between currency units can not be predefined,
many of the behaviors have been overridden and augmented to realize correct behavior.

### Money
A Quantity of purchasing power measured in Currency units.
Like other quantities, the Unit of Measures are used to create Money values.

```tut:reset
import squants.market.{BTC, JPY, USD, XAU}

val tenBucks = USD(10)      // Money: 10 USD
val someYen = JPY(1200)     // Money: 1200 JPY
val goldStash = XAU(50)     // Money: 50 XAU
val digitalCache = BTC(50)  // Money: 50 BTC
```

### Price
A Ratio between Money and another Quantity.
A Price value is typed on a Quantity and can be denominated in any defined Currency.

*Price = Money / Quantity*

Assuming these imports:
```tut:reset:silent
import squants.{Dozen, Each}
import squants.energy.MegawattHours
import squants.market.USD
import squants.space.UsGallons
```

You can compute the following:
```tut
val threeForADollar = USD(1) / Each(3)
val energyPrice = USD(102.20) / MegawattHours(1)
val milkPrice = USD(4) / UsGallons(1)

val costForABunch = threeForADollar * Dozen(10)
val energyCost = energyPrice * MegawattHours(4)
val milkQuota = USD(20) / milkPrice
```

Conversions to Strings
```tut
val money = USD(123.456)
val s = money.toString  // returns full precision amount with currency code
val s = money.toFormattedString // returns currency symbol and amount rounded based on currency rules
```

### FX Support
Currency Exchange Rates are used to define the conversion factors between currencies

```tut:reset
import squants.market.{CurrencyExchangeRate, JPY, Money, USD}

// create an exchange rate
val rate1 = CurrencyExchangeRate(USD(1), JPY(100))
// OR
val rate2 = USD / JPY(100)
// OR
val rate3 = JPY(100) -> USD(1)
// OR
val rate4 = JPY(100) toThe USD(1)

val someYen: Money = JPY(350)
val someBucks: Money = USD(23.50)
```

Use the `convert` method which automatically converts the money to the 'other' currency:

```tut
val dollarAmount: Money = rate1.convert(someYen)
val yenAmount: Money = rate1.convert(someBucks)
```

Or just use the `*` operator in either direction (money * rate, or rate * money):
```tut`
val dollarAmount2: Money = rate1 * someYen
val yenAmount2: Money = someBucks * rate1
```

### Money Context
A MoneyContext can be implicitly declared to define default settings and applicable exchange rates within its scope.
This allows your application to work with a default currency based on an application configuration or other dynamic source.
It also provides support for updating exchange rates and using those rates for automatic conversions between currencies.
The technique and frequency chosen for exchange rate updates is completely in control of the application.

Assuming these imports:
```tut:silent
import squants.energy.MegawattHours
import squants.market.{CAD, JPY, MXN, USD}
import squants.market.defaultMoneyContext
```

You can compute:
```tut
val exchangeRates = List(USD / CAD(1.05), USD / MXN(12.50), USD / JPY(100))
implicit val moneyContext = defaultMoneyContext withExchangeRates exchangeRates

val energyPrice = USD(102.20) / MegawattHours(1)
val someMoney = Money(350) // 350 in the default Cur
val usdMoney: Money = someMoney in USD
val usdBigDecimal: BigDecimal = someMoney to USD
val yenCost: Money = (energyPrice * MegawattHours(5)) in JPY
val northAmericanSales: Money = (CAD(275) + USD(350) + MXN(290)) in USD
```

## Quantity Ranges
A `QuantityRange` is used to represent a range of Quantity values between an upper and lower bound:

```tut:reset:silent
import squants.QuantityRange
import squants.energy.{Kilowatts, Megawatts, Power}
```
```tut:book
val load1: Power = Kilowatts(1000)
val load2: Power = Kilowatts(5000)
val range: QuantityRange[Power] = QuantityRange(load1, load2)
```

### Inclusivity and Exclusivitiy

The `QuantityRange` constructor requires that `upper` is strictly greater than `lower`:

```tut:book
import squants.space.LengthConversions._

// this will work b/c upper > lower
QuantityRange(1.km, 5.km)
```

This will fail because `lower` = `upper`:

```tut:fail
QuantityRange(1.km, 1.km)
```

`QuantityRange` contains two functions that check if an element is part of the range, `contains` and `includes`.
These differ in how they treat the range's upper bound: `contains()` _excludes_ it but `includes()` _includes_ it.

```tut
val distances = QuantityRange(1.km, 5.km)
distances.contains(5.km) // this is false b/c contains() doesn't include the upper range
distances.includes(5.km) // this is true b/c includes() does include the upper range
```

### QuantityRange transformation

The multiplication and division operators create a `Seq` of ranges from the original.

For example:

Create a Seq of 10 sequential ranges starting with the original and each the same size as the original:
```tut:book
val rs1 = range * 10
```
Create a Seq of 10 sequential ranges each 1/10th of the original size:

```tut:book
val rs2 = range / 10
```

Create a Seq of 10 sequential ranges each with a size of 400 kilowatts:
```tut:book
val rs3 = range / Kilowatts(400)
```

### QuantityRange operations

`QuantityRange` supports foreach, map, and foldLeft/foldRight. These vary slightly from the versions
in the Scala standard library in that they take a divisior as the first parameter. The examples below
illustrate their use.

Subdivide range into 1-Megawatt "slices", and foreach over each of slices:
```tut:book
range.foreach(Megawatts(1)) { r => println(s"lower = ${r.lower}, upper = ${r.upper}") }
```

Subdivide range into 10 slices and map over each slice:
```tut:book
range.map(10) { r => r.upper }
```

Subdivide range into 10 slices and fold over them, using 0 Megawatts as a starting value:
```tut:book
range.foldLeft(10, Megawatts(0)) { (z, r) => z + r.upper }
```

NOTE - Because these implementations of foreach, map and fold* take a parameter (the divisor), these methods
are not directly compatible with Scala's for comprehensions.
To use in a for comprehension, apply the * or / operators as described above to create a Seq from the Range.

```scala
for {
    interval <- (0.seconds to 1.seconds) * 60  // 60 time ranges, 0s to 1s, 1s to 2s, ...., 59s to 60s
    ...
} yield ...
```

## Natural Language DSL
Implicit conversions give the DSL some features that allows user code to express quantities in a
more naturally expressive and readable way.

Code samples in this section assume these imports
```tut:silent:reset
import squants.energy.{Kilowatts, MegawattHours, Power}
import squants.market.{Price, USD}
import squants.time.Hours
```

Create Quantities using Unit Of Measure Factory objects (no implicits required):

```tut
val load = Kilowatts(100)
val time = Hours(3.75)
val money = USD(112.50)
val price = Price(money, MegawattHours(1))
```

Create Quantities using Unit of Measure names and/or symbols (uses implicits):

```tut:silent
import scala.language.postfixOps
import squants.energy.EnergyConversions._
import squants.energy.PowerConversions._
import squants.information.InformationConversions._
import squants.market.MoneyConversions._
import squants.space.LengthConversions._
import squants.time.TimeConversions._
```
```tut
val load1 = 100 kW 			        // Simple expressions don’t need dots
val load2 = 100 megawatts
val time = 3.hours + 45.minutes     // Compound expressions may need dots
```

Create Quantities using operations between other Quantities:

```tut
val energyUsed = 100.kilowatts * (3.hours + 45.minutes)
val price = 112.50.USD / 1.megawattHours
val speed = 55.miles / 1.hours
```

Create Quantities using formatted Strings:

```tut
val load = Power("40 MW")
```

Create Quantities using Tuples:

```tut
val load = Power((40.5, "MW"))
```

Use single unit values to simplify expressions:

```tut
// Hours(1) == 1.hours == hour
val ramp = 100.kilowatts / hour
val speed = 100.kilometers / hour

// MegawattHours(1) == 1.megawattHours == megawattHour == MWh
val hi = 100.dollars / MWh
val low = 40.dollars / megawattHour
```

Implicit conversion support for using Doubles, Longs and BigDecimals on the left side of multiply and divide operations:

```tut
val load = 10.22 * 4.MW
val driveArrayCapacity = 12 * 600.gb
val freq = 60 / second
val freq2 = BigDecimal(36000000) / hour
```

Create Quantity Ranges using `to` or `plusOrMinus` (`+-`) operators:

```tut:silent
val range1 = 1000.kW to 5000.kW	             // 1000.kW to 5000.kW
val range2 = 5000.kW plusOrMinus 1000.kW     // 4000.kW to 6000.kW
val range2 = 5000.kW +- 1000.kW              // 4000.kW to 6000.kW
```

### Numeric Support
Most Quantities that support implicit conversions also include an implicit Numeric object that can be imported
to your code where Numeric support is required.  These follow the following pattern:

```tut:reset
import squants.mass.{Grams, Kilograms}
import squants.mass.MassConversions.MassNumeric

val sum = List(Kilograms(100), Grams(34510)).sum
```

NOTE - Because a quantity can not be multiplied by a like quantity and return a like quantity, the `Numeric.times`
operation of numeric is implemented to throw an UnsupportedOperationException for all types except `Dimensionless`.

The MoneyNumeric implementation is a bit different than the implementations for other quantity types
in a few important ways.

1. MoneyNumeric is a class, not an object like the others.
2. To create a MoneyNumeric value there must be an implicit MoneyContext in scope.
3. The MoneyContext must contain applicable exchange rates if you will be applying cross-currency Numeric ops.

The following code provides a basic example for creating a MoneyNumeric:

```tut:reset:silent
import squants.market.defaultMoneyContext
import squants.market.MoneyConversions._
import squants.market.USD
implicit val moneyContext = defaultMoneyContext
```

```tut
implicit val moneyNum = new MoneyNumeric()

val sum = List(USD(100), USD(10)).sum
```

## Unit groups

Squants provides an experimental API for grouping related `UnitOfMeasure` values together.
This are called `UnitGroup`s. Squants provides `UnitGroup` implementations for the SI, the US Customary system, and various other systems. End-users can create their own ad-hoc `UnitGroup`s for `UnitOfMeasure`s in a related dimension.

The `UnitGroup` trait defines two public fields: `units`, a `Set[UnitOfMeasure]`, and `sortedUnits`, which contains `units` sorted in ascending order.

### SI UnitGroups

Almost every `Dimension` in Squants has SI Units (with the exception of `Information`
and `Money`). To avoid boilerplate, Squants generates `UnitGroup`s for SI using implicits.

There are two `UnitGroup`s provided for SI: "strict" and "expanded." Strict only includes SI
UnitOfMeasure defined in the SI; "expanded" includes [non-SI units that are commonly used in](http://www.bipm.org/en/publications/si-brochure/table6.html)
SI, such as litre, hectare, hour, minute, etc). See the linked document for a detailed list.

To summon the strict SI `UnitGroup` for `Length`, you would use this code:

```tut:reset:book
import squants.space.Length
import squants.experimental.unitgroups.ImplicitDimensions.space._
import squants.experimental.unitgroups.UnitGroup
import squants.experimental.unitgroups.si.strict.implicits._
val siLengths: UnitGroup[Length] = implicitly[UnitGroup[Length]]
```

To print out units and their conversion factors to the primary SI unit, you could use this code:

```tut:book
import squants.{Quantity, UnitOfMeasure}

def mkConversionFactor[A <: Quantity[A]](uom: UnitOfMeasure[A]): Double = {
  val one = uom(1)
  one.to(one.dimension.siUnit)
}

def mkTuple[A <: Quantity[A]](uom: UnitOfMeasure[A]): (String, Double) = {
  (uom.symbol, mkConversionFactor(uom))
}

siLengths.sortedUnits.toList.map(mkTuple).foreach(println)
```

Note that `UnitGroup`'s `sortedUnits` field is a `SortedSet`, so before mapping over it,
you will probably want to convert it to a List, otherwise the output may be resorted.

### Non-SI UnitGroups

Other `UnitGroup` definitions don't use implicits. For example, `squants.experimental.unitgroups.uscustomary.space.UsCustomaryLiquidVolumes` or `squants.experimental.unitgroups.misc.TroyMasses` can be imported and used directly.

### Creating an ad-hoc UnitGroup

To create an ad-hoc `UnitGroup` just implement the trait. For example, to make a US cooking measure `UnitGroup`:

```tut:book
import squants.{Quantity, Dimension}
import squants.space._
import squants.experimental.unitgroups.UnitGroup

val usCookingUnitGroup = new UnitGroup[Volume] {
  // units don't have to be specified in-order.
  val units: Set[UnitOfMeasure[Volume]] = Set(UsPints, UsGallons, Teaspoons, Tablespoons, UsQuarts, FluidOunces)
}

// squants automatically sorts units
usCookingUnitGroup.sortedUnits.foreach(println)
```

The `UnitGroup` values provided with Squants are only samples and aren't intended to be exhaustive.
We encourage users to make their own `UnitGroup` defintitions and submit them as PRs if they're generally
applicable.

## Formatters

Squants provides an experimental API for formatting Quantities in the "best unit." For example,
convert Inches(12) to Feet(1). This is useful for producing human-friendly output.

To use a formatter, you must implement the `squants.formatters.Formatter` trait:

```tut:silent
trait Formatter[A <: Quantity[A]] {
  def inBestUnit(quantity: Quantity[A]): A
}
```

### Default Formatter implementation

There is a default formatter implementation in `squants.experimental.formatter.DefaultFormatter`. This builds on the `UnitGroup`
API discussed above to choose the best `UnitOfMeasure` for a `Quantity`. The `DefaultFormatter` algorithm will probably
work for most use-cases, but users can create their own `Formatters` if they have custom needs.

To use `DefaultFormatter` import it, and a unit group:

```tut:silent
import squants.experimental.formatter.DefaultFormatter
import squants.experimental.unitgroups.misc.AstronomicalLengthUnitGroup
```

Then create the formatter by passing in a unit group:
```tut:book
val astroFormatter = new DefaultFormatter(AstronomicalLengthUnitGroup)
```

Now, we create some values using human-unfriendly numbers:

```tut:book
import squants.space.LengthConversions._
val earthToJupiter = 588000000.km
val earthToVoyager1 = 2.06e10.km
val earthToAlphaCentauri = 4.1315e+13.km
```

And format them into appropriate units (AUs and Parsecs, in this case):
```tut:book
astroFormatter.inBestUnit(earthToJupiter)
astroFormatter.inBestUnit(earthToVoyager1)
astroFormatter.inBestUnit(earthToAlphaCentauri)
```


### Implicit formatters

There is a nicer syntax for formatters available via implicits.
This lets you write expressions such as `12.inches.inBestUnit`. This syntax is added per-`Dimension`.

To use this syntax, first import `squants.experimental.formatter.syntax._`.
Then, for each `Dimension` you wish to use, place a Formatter for the Dimension in implicit scope. In this example,
we're adding support for `Length`.

```tut:reset:silent
import squants.experimental.formatter.DefaultFormatter
import squants.experimental.formatter.syntax._
import squants.mass.MassConversions._
import squants.space.Length
import squants.space.LengthConversions._
import squants.experimental.unitgroups.misc.AstronomicalLengthUnitGroup
```

```tut:book
implicit val astroFormatter = new DefaultFormatter(AstronomicalLengthUnitGroup)

val earthToJupiter = 588000000.km
val earthToVoyager1 = 2.06e10.km
val earthToAlphaCentauri = 4.1315e+13.km

earthToJupiter.inBestUnit
earthToVoyager1.inBestUnit
earthToAlphaCentauri.inBestUnit
```

This example won't compile because there is no `Formatter[Mass]` in implicit scope:

```tut:fail
5000.grams.inBestUnit
```

### SI Formatters and implicit syntax

When using SI units, and the default formatter algorithm, you don't have to declare a `Formatter` and place it in
implicit scope. The compiler can do that for you. This creates a very human-friendly API by using the appropriate
imports.

First, import the SI unit groups and their implicits:
```tut:reset:silent
import squants.experimental.unitgroups.ImplicitDimensions.space._
import squants.experimental.unitgroups.si.strict.implicits._
```

Next, import the formatter syntax described above:

```tut:silent
import squants.experimental.formatter.syntax._
```

Finally, add imports for implicitly deriving formatters:
```tut
import squants.experimental.formatter.implicits._
```

Now we can create quantities and format them by calling `.inBestUnit` directly:

```tut:silent
import squants.space.LengthConversions._
```

```tut:book
5.cm.inBestUnit
500.cm.inBestUnit
3000.meters.inBestUnit
```

## Type Hierarchy
The type hierarchy includes the following core types:  Quantity, Dimension, and UnitOfMeasure

### Quantity and Dimension

A Dimension represents a type of Quantity. For example: Mass, Length, Time, etc.

A Quantity represents a dimensional value or measurement.  A Quantity is a combination of a numeric value and a unit.
For example:  2 lb, 10 km, 3.4 hr.

Squants has built in support for 54 quantity dimensions.

### Unit of Measure
UnitOfMeasure is the scale or multiplier in which the Quantity is being measured.
Squants has built in support for over 257 units of measure

For each Dimension a set of UOM objects implement a primary UOM trait typed to that Quantity.
The UOM objects define the unit symbols, conversion factors, and factory methods for creating Quantities in that unit.

### Quantity Implementations

The code for specific implementations include

* A class representing the Quantity including cross-dimensional operations
* A companion object representing the Dimension and set of available units
* A base trait for its Units
* A set of objects defining specific units, their symbols and conversion factors

This is an abbreviated example of how a Quantity type is constructed:

```scala
class Length(val value: Double, val unit: LengthUnit) extends Quantity[Length]  { ... }
object Length extends Dimension[Length]  { ... }
trait LengthUnit extends UnitOfMeasure[Length]  { ... }
object Meters extends LengthUnit { ... }
object Yards extends LengthUnit { ... }
```

The apply method of the UOM objects are implemented as factories for creating Quantity values.

```scala
val len1: Length = Meters(4.3)
val len2: Length = Yards(5)
```

Squants currently supports 257 units of measure

### Time Derivatives

Special traits are used to establish a time derivative relationship between quantities.

For example Velocity is the 1st Time Derivative of Length (Distance), Acceleration is the 2nd Time Derivative.

```scala
class Length( ... ) extends Quantity[Length] with TimeIntegral[Velocity]
...
class Velocity( ... ) extends Quantity[Velocity] with TimeDerivative[Length] with TimeIntegral[Acceleration]
...
class Acceleration( ... ) extends Quantity[Acceleration] with TimeDerivative[Velocity]
```

These traits provide operations with time operands which result in correct dimensional transformations.


Using these imports:
```tut:reset:silent
import squants.energy.Kilowatts
import squants.motion.{Acceleration, Velocity}
import squants.space.{Kilometers, Length}
import squants.space.LengthConversions._
import squants.time.{Hours, Seconds, Time}
import squants.time.TimeConversions._
```

You can code the following:
```tut
val distance: Length = Kilometers(100)
val time: Time = Hours(2)
val velocity: Velocity = distance / time
val acc: Acceleration = velocity / Seconds(1)

val gravity = 32.feet / second.squared
```

Power is the 1st Time Derivative of Energy, PowerRamp is the 2nd.
```tut
val power = Kilowatts(100)
val time: Time = Hours(2)
val energy = power * time
val ramp = Kilowatts(50) / Hours(1)
```

## Use Cases

### Dimensional Analysis

The primary use case for Squants, as described above, is to produce code that is typesafe within domains
that perform dimensional analysis.

This code samples in this section use these imports:
```tut:reset:silent
import squants.energy.Energy
import squants.energy.EnergyConversions._
import squants.energy.PowerConversions._
import squants.market.{Money, Price}
import squants.market.MoneyConversions._
import squants.market.defaultMoneyContext
import squants.mass.{Density, Mass}
import squants.mass.MassConversions._
import squants.motion.{Acceleration, Velocity, VolumeFlow}
import squants.motion.AccelerationConversions._
import squants.space.LengthConversions._
import squants.space.VolumeConversions._
import squants.time.Time
import squants.time.TimeConversions._
```

```tut
implicit val moneyContext = defaultMoneyContext
val energyPrice: Price[Energy] = 45.25.money / megawattHour
val energyUsage: Energy = 345.kilowatts * 5.4.hours
val energyCost: Money = energyPrice * energyUsage

val dodgeViper: Acceleration = 60.miles / hour / 3.9.seconds
val speedAfter5Seconds: Velocity = dodgeViper * 5.seconds
val timeTo100MPH: Time = 100.miles / hour / dodgeViper

val density: Density = 1200.kilograms / cubicMeter
val volFlowRate: VolumeFlow = 10.gallons / minute
val flowTime: Time = 30.minutes
val totalMassFlow: Mass = volFlowRate * flowTime * density
```

### Domain Modeling
Another excellent use case for Squants is stronger typing for fields in your domain model.

Code samples in this section use these imports:
```tut:silent:reset
import scala.language.postfixOps

import squants.energy.{Energy, Power, PowerRamp}
import squants.energy.EnergyConversions._
import squants.energy.PowerConversions._
import squants.energy.PowerRampConversions._
import squants.market.Price
import squants.market.MoneyConversions._
import squants.time.Time
import squants.time.TimeConversions._
```

This is OK ...

```tut:silent
case class Generator(
  id: String,
  maxLoadKW: Double,
  rampRateKWph: Double,
  operatingCostPerMWh: Double,
  currency: String,
  maintenanceTimeHours: Double)

val gen1 = Generator("Gen1", 5000, 7500, 75.4, "USD", 1.5)
val gen2 = Generator("Gen2", 100, 250, 2944.5, "JPY", 0.5)
```

... but this is much better

```tut:silent
case class Generator(
  id: String,
  maxLoad: Power,
  rampRate: PowerRamp,
  operatingCost: Price[Energy],
  maintenanceTime: Time)

val gen1 = Generator("Gen1", 5 MW, 7.5.MW/hour, 75.4.USD/MWh, 1.5 hours)
val gen2 = Generator("Gen2", 100 kW, 250 kWph, 2944.5.JPY/MWh, 30 minutes)
```

### Anticorruption Layers

Create wrappers around external services that use basic types to represent quantities.
Your application code then uses the ACL to communicate with that system thus eliminating the need to deal
with type and scale conversions in multiple places throughout your application logic.

```scala
class ScadaServiceAnticorruption(val service: ScadaService) {
  // ScadaService returns meter load as Double representing Megawatts
  def getLoad: Power = Megawatts(service.getLoad(meterId))
  }
  // ScadaService.sendTempBias requires a Double representing Fahrenheit
  def sendTempBias(temp: Temperature) =
    service.sendTempBias(temp.to(Fahrenheit))
}
```

Implement the ACL as a trait and mix in to the application's services where needed.

```scala
import squants.radio.{Irradiance, WattsPerSquareMeter}
import squants.thermal.{Celsius, Temperature}

trait WeatherServiceAntiCorruption {
  val service: WeatherService
  def getTemperature: Temperature = Celsius(service.getTemperature)
  def getIrradiance: Irradiance = WattsPerSquareMeter(service.getIrradiance)
}
```

Extend the pattern to provide multi-currency support

```scala
class MarketServiceAnticorruption(val service: MarketService)
     (implicit val moneyContext: = MoneyContext) {

  // MarketService.getPrice returns a Double representing $/MegawattHour
  def getPrice: Price[Energy] =
    (USD(service.getPrice) in moneyContext.defaultCurrency) / megawattHour

  // MarketService.sendBid requires a Double representing $/MegawattHour
  // and another Double representing the max amount of energy in MegawattHours
  def sendBid(bid: Price[Energy], limit: Energy) =
    service.sendBid((bid * megawattHour) to USD, limit to MegawattHours)
}
```

Build Anticorruption into Akka routers

```scala
// LoadReading message used within a Squants enabled application context
case class LoadReading(meterId: String, time: Long, load: Power)
class ScadaLoadListener(router: Router) extends Actor {
  def receive = {
   // ScadaLoadReading - from an external service - sends load as a string
   // eg, “10.3 MW”, “345 kW”
   case msg @ ScadaLoadReading(meterId, time, loadString) ⇒
    // Parse the string and on success emit the Squants enabled event to routees
    Power(loadString) match {
      case Success(p) => router.route(LoadReading(meterId, time, p), sender())
      case Failure(e) => // react to QuantityStringParseException
    }
  }
}
```

... and REST API's with contracts that require basic types

```scala
trait LoadRoute extends HttpService {
  def repo: LoadRepository
  val loadRoute = {
    path("meter-reading") {
      // REST API contract requires load value and units in different fields
      // Units are string values that may be 'kW' or 'MW'
      post {
        parameters(meterId, time, loadDouble, unit) { (meterId, time, loadDouble, unit) =>
          complete {
            val load = unit match {
              case "kW" => Kilowatts(loadDouble)
              case "MW" => Megawatts(loadDouble)
            }
            repo.saveLoad(meterId, time, load)
          }
        }
      } ~
      // REST API contract requires load returned as a number representing megawatts
      get {
        parameters(meterId, time) { (meterId, time) =>
          complete {
            repo.getLoad(meterId, time) to Megawatts
          }
        }
      }
    }
  }
}
```

## Contributors

* Gary Keorkunian ([garyKeorkunian](https://github.com/garyKeorkunian))
* Jeremy Apthorp ([nornagon](https://github.com/nornagon))
* Steve Barham ([stevebarham](https://github.com/stevebarham))
* Derek Morr ([derekmorr](https://github.com/derekmorr))
* Michael Korbakov ([rmihael](https://github.com/rmihael))
* Florian Nussberger ([fnussber](https://github.com/fnussber))
* Ajay Chandran ([ajaychandran](https://github.com/ajaychandran))
* Gia Bảo ([giabao](https://github.com/giabao))
* Josh Lemer ([joshlemer](https://github.com/joshlemer))
* Dave DeCarpio ([DaveDeCaprio](https://github.com/DaveDeCaprio))
* Carlos Quiroz ([cquiroz](https://github.com/cquiroz))
* Szabolcs Berecz ([khernyo](https://github.com/khernyo))
* Matt Hicks ([darkfrog26](https://github.com/darkfrog26))
* golem131 ([golem131](https://github.com/golem131))
* Ian O'Hara ([ianohara](https://github.com/ianohara))
* Shadaj Laddad ([shadaj](https://github.com/shadaj))
* Ian McIntosh ([cranst0n](https://github.com/cranst0n))
* Doug Hurst ([robotsnowfall](https://github.com/robotsnowfall))
* Philip Axelrod ([Paxelord](https://github.com/paxelord))

## Code of Conduct

Squants is a [Typelevel](http://typelevel.org/) Incubator Project and, as such, supports the [Typelevel Code of Conduct](http://typelevel.org/conduct).

## Caveats

Code is offered as-is, with no implied warranty of any kind.
Comments, criticisms, and/or praise are welcome, especially from scientists, engineers and the like.

# Release procedure

Making a release requires permission to publish to sonatype, and a properly setup [signing key](http://www.scala-sbt.org/sbt-pgp/usage.html):

To make a release do the following:

* Ensure the version is not set to `SNAPSHOT`

* Build the README using tut

```
  sbt tut
```

* Publish a cross-version signed package (no cross-version available for Scala Native)
```
  sbt +squantsJVM/publishSigned
  sbt +squantsJS/publishSigned
  sbt squantsNative/publishSigned
```

* Repeat for scala.js 1.0.0-M8
```
  SCALAJS_VERSION=1.0.0-M8 sbt +squantsJS/publishSigned
```

* Then make a release (Note: after this step the release cannot be replaced)
```
  sbt sonatypeRelease
```
