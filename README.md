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
[![Scaladocs](https://www.javadoc.io/badge/org.typelevel/squants_2.13.svg?label=scaladoc)](https://static.javadoc.io/org.typelevel/squants_2.13/1.6.0/squants/index.html)
|
[![Build Status](https://travis-ci.org/typelevel/squants.png?branch=master)](https://travis-ci.org/typelevel/squants)


### Current Versions
Current Release: **1.6.0**
([API Docs](https://oss.sonatype.org/service/local/repositories/releases/archive/org/typelevel/squants_2.13/1.6.0/squants_2.13-1.6.0-javadoc.jar/!/index.html#squants.package))

Development Build: **1.7.0-SNAPSHOT**
([API Docs](https://oss.sonatype.org/service/local/repositories/snapshots/archive/org/typelevel/squants_2.13/1.6.0-SNAPSHOT/squants_2.13-1.7.0-SNAPSHOT-javadoc.jar/!/index.html#squants.package))

[Release History](https://github.com/typelevel/squants/wiki/Release-History)

Build services provided by [Travis CI](https://travis-ci.com/)

NOTE - This README reflects the feature set in the branch it can be found.
For more information on feature availability of a specific version see the Release History or the README for a that version

## Installation
Repository hosting for Squants is provided by [Sonatype](https://oss.sonatype.org/).
To use Squants in your SBT project add the following dependency to your build.

    "org.typelevel"  %% "squants"  % "1.6.0"
or

    "org.typelevel"  %% "squants"  % "1.7.0-SNAPSHOT"


To use Squants in your Maven project add the following dependency

```xml
<dependency>
    <groupId>org.typelevel</groupId>
    <artifactId>squants_2.11</artifactId>
    <version>1.6.0</version>
</dependency>
```

Beginning with Squants 0.4.x series, both Scala 2.10 and 2.11 builds are available.
Beginning with Squants 1.x series, Scala 2.11, 2.12 and 2.13 builds are available.
Scala.js is supported on version 0.6.31 and 1.0.0-RC1

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

```scala
scala> val loadKw = 1.2
loadKw: Double = 1.2

scala> val energyMwh = 24.2
energyMwh: Double = 24.2

scala> val sumKw = loadKw + energyMwh
sumKw: Double = 25.4
```

This example not only adds quantities of different dimensions (Power vs Energy),
it also fails to convert the scales implied in the val names (Mega vs Kilo).
Because this code compiles, detection of these errors is pushed further into the development cycle.

### Dimensional Type Safety

_Only quantities with the same dimensions may be compared, equated, added, or subtracted._

Squants helps prevent errors like these by type checking operations at compile time and
automatically applying scale and type conversions at run-time.  For example:

```scala
scala> import squants.energy.{Kilowatts, Megawatts, Power}
import squants.energy.{Kilowatts, Megawatts, Power}

scala> val load1: Power = Kilowatts(12)
load1: squants.energy.Power = 12.0 kW

scala> val load2: Power = Megawatts(0.023)
load2: squants.energy.Power = 0.023 MW

scala> val sum = load1 + load2
sum: squants.energy.Power = 35.0 kW

scala> sum == Kilowatts(35)
res0: Boolean = true

scala> sum == Megawatts(0.035) // comparisons automatically convert scale
res1: Boolean = true
```

The above sample works because Kilowatts and Megawatts are both units of Power.  Only the scale is
different and the library applies an appropriate conversion.  Also, notice that keeping track of
the scale within the value name is no longer needed:

```scala
scala> import squants.energy.{Energy, Power, Kilowatts, KilowattHours}
import squants.energy.{Energy, Power, Kilowatts, KilowattHours}

scala> val load: Power = Kilowatts(1.2)
load: squants.energy.Power = 1.2 kW

scala> val energy: Energy = KilowattHours(23.0)
energy: squants.energy.Energy = 23.0 kWh
```

Invalid operations, like adding power and energy, no longer compile:
```scala
scala> val sum = load + energy
<console>:16: error: type mismatch;
 found   : squants.energy.Energy
 required: squants.energy.Power
       val sum = load + energy
                        ^
```
By using stronger types, we catch the error earlier in the development cycle, preventing the error made when using Double in the example above.

### Dimensionally Correct Type Conversions

_One may take quantities with different dimensions, and multiply or divide them._

Dimensionally correct type conversions are a key feature of Squants.
Conversions are implemented by defining relationships between Quantity types using the `*` and `/` operators.

Code samples in this section assume these imports:
```scala
import squants.energy.{Kilowatts, Power}
import squants.time.{Hours, Days}
```

The following code demonstrates creating ratio between two quantities of the same dimension,
resulting in a dimensionless value:

```scala
scala> val ratio = Days(1) / Hours(3)
ratio: Double = 8.0
```

This code demonstrates use of the `Power.*` method that takes a `Time` and returns an `Energy`:

```scala
scala> val load = Kilowatts(1.2)
load: squants.energy.Power = 1.2 kW

scala> val time = Hours(2)
time: squants.time.Time = 2.0 h

scala> val energyUsed = load * time
energyUsed: squants.energy.Energy = 2400.0 Wh
```

This code demonstrates use of the `Energy./` method that takes a `Time` and returns a `Power`:

```scala
scala> val aveLoad: Power = energyUsed / time
aveLoad: squants.energy.Power = 1200.0 W
```

### Unit Conversions

Code samples in this section assume these imports:
```scala
import scala.language.postfixOps
import squants.energy.{Gigawatts, Kilowatts, Power, Megawatts}
import squants.mass.MassConversions._
import squants.mass.{Kilograms, Pounds}
import squants.thermal.TemperatureConversions._
import squants.thermal.Fahrenheit
```

Quantity values are based in the units used to create them.

```scala
scala> val loadA: Power = Kilowatts(1200)
loadA: squants.energy.Power = 1200.0 kW

scala> val loadB: Power = Megawatts(1200)
loadB: squants.energy.Power = 1200.0 MW
```

Since Squants properly equates values of a like dimension, regardless of the unit,
there is usually no reason to explicitly convert from one to the other.
This is especially true if the user code is primarily performing dimensional analysis.

However, there are times when you may need to set a Quantity value to a specific unit (eg, for proper JSON encoding).

When necessary, a quantity can be converted to another unit using the `in` method.

```scala
scala> val loadA = Kilowatts(1200)
loadA: squants.energy.Power = 1200.0 kW

scala> val loadB = loadA in Megawatts
loadB: squants.energy.Power = 1.2 MW

scala> val loadC = loadA in Gigawatts
loadC: squants.energy.Power = 0.0012 GW
```

Sometimes you need to get the numeric value of the quantity in a specific unit
(eg, for submission to an external service that requires a numeric in a specified unit
or to perform analysis beyond Squant's domain)

When necessary, the value can be extracted in the desired unit with the `to` method.

```scala
scala> val load: Power = Kilowatts(1200)
load: squants.energy.Power = 1200.0 kW

scala> val kw: Double = load to Kilowatts
kw: Double = 1200.0

scala> val mw: Double = load to Megawatts
mw: Double = 1.2

scala> val gw: Double = load to Gigawatts
gw: Double = 0.0012
```

Most types include methods with convenient aliases for the `to` methods.

```scala
scala> val kw: Double = load toKilowatts
kw: Double = 1200.0

scala> val mw: Double = load toMegawatts
mw: Double = 1.2

scala> val gw: Double = load toGigawatts
gw: Double = 0.0012
```

NOTE - It is important to use the `to` method for extracting the numeric value,
as this ensures you will be getting the numeric value for the desired unit.
`Quantity.value` should not be accessed directly.
To prevent improper usage, direct access to the `Quantity.value` field may be deprecated in a future version.

Creating strings formatted in the desired unit:

```scala
scala> val kw: String = load toString Kilowatts
kw: String = 1200.0 kW

scala> val mw: String = load toString Megawatts
mw: String = 1.2 MW

scala> val gw: String = load toString Gigawatts
gw: String = 0.0012 GW
```

Creating `Tuple2[Double, String]` that includes a numeric value and unit symbol:

```scala
scala> val load: Power = Kilowatts(1200)
load: squants.energy.Power = 1200.0 kW

scala> val kw = load toTuple
kw: (Double, String) = (1200.0,kW)

scala> val mw = load toTuple Megawatts
mw: (Double, String) = (1.2,MW)

scala> val gw = load toTuple Gigawatts
gw: (Double, String) = (0.0012,GW)
```

This can be useful for passing properly scaled quantities to other processes
that do not use Squants, or require use of more basic types (Double, String)

Simple console based conversions (using DSL described below)

```scala
scala> 1.kilograms to Pounds
res0: Double = 2.2046226218487757

scala> kilogram / pound
res1: Double = 2.2046226218487757

scala> 2.1.pounds to Kilograms
res2: Double = 0.952543977

scala> 2.1.pounds / kilogram
res3: Double = 0.9525439770000002

scala> 100.C to Fahrenheit
res4: Double = 212.0
```

### Mapping over Quantity values
Apply a `Double => Double` operation to the underlying value of a quantity, while preserving its type and unit.

```scala
scala> import squants.energy.Kilowatts
import squants.energy.Kilowatts

scala> val load = Kilowatts(2.0)
load: squants.energy.Power = 2.0 kW

scala> val newLoad = load.map(v => v * 2 + 10)
newLoad: squants.energy.Power = 14.0 kW
```

The `q.map(f)` method effectively expands to `q.unit(f(q.to(q.unit))`

NOTE - For Money objects, use the `mapAmount` method as this will retain the BigDecimal precision used there.

### Approximations
Create an implicit Quantity value to be used as a tolerance in approximations.
Then use the `approx` method (or `=~`, `~=`, `≈` operators) like you would use the `equals` method (`==` operator).

```scala
scala> import squants.energy.{Kilowatts, Watts}
import squants.energy.{Kilowatts, Watts}

scala> val load = Kilowatts(2.0)
load: squants.energy.Power = 2.0 kW

scala> val reading = Kilowatts(1.9999)
reading: squants.energy.Power = 1.9999 kW
```

Calls to `approx` (and its symbolic aliases) use an implicit tolerance:

```scala
scala> implicit val tolerance = Watts(.1)
tolerance: squants.energy.Power = 0.1 W

scala> load =~ reading
res0: Boolean = true

scala> load ≈ reading
res1: Boolean = true

scala> load approx reading
res2: Boolean = true
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

```scala
scala> import squants.{QuantityVector, SVector}
import squants.{QuantityVector, SVector}

scala> import squants.space.{Kilometers, Length}
import squants.space.{Kilometers, Length}

scala> import squants.space.LengthConversions._
import squants.space.LengthConversions._

scala> val vector: QuantityVector[Length] = SVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
vector: squants.QuantityVector[squants.space.Length] = QuantityVector(WrappedArray(1.2 km, 4.3 km, 2.3 km))

scala> val magnitude: Length = vector.magnitude        // returns the scalar value of the vector
magnitude: squants.space.Length = 5.021951811795888 km

scala> val normalized = vector.normalize(Kilometers)   // returns a corresponding vector scaled to 1 of the given unit
normalized: vector.SVectorType = QuantityVector(ArrayBuffer(0.2389509188800581 km, 0.8562407926535415 km, 0.45798926118677796 km))

scala> val vector2: QuantityVector[Length] = SVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
vector2: squants.QuantityVector[squants.space.Length] = QuantityVector(WrappedArray(1.2 km, 4.3 km, 2.3 km))

scala> val vectorSum = vector + vector2        // returns the sum of two vectors
vectorSum: vector.SVectorType = QuantityVector(ArrayBuffer(2.4 km, 8.6 km, 4.6 km))

scala> val vectorDiff = vector - vector2       // return the difference of two vectors
vectorDiff: vector.SVectorType = QuantityVector(ArrayBuffer(0.0 km, 0.0 km, 0.0 km))

scala> val vectorScaled = vector * 5           // returns vector scaled 5 times
vectorScaled: vector.SVectorType = QuantityVector(ArrayBuffer(6.0 km, 21.5 km, 11.5 km))

scala> val vectorReduced = vector / 5          // returns vector reduced 5 time
vectorReduced: vector.SVectorType = QuantityVector(ArrayBuffer(0.24 km, 0.86 km, 0.45999999999999996 km))

scala> val vectorDouble = vector / 5.meters    // returns vector reduced and converted to DoubleVector
vectorDouble: squants.DoubleVector = DoubleVector(ArrayBuffer(240.0, 860.0, 459.99999999999994))

scala> val dotProduct = vector * vectorDouble  // returns the Dot Product of vector and vectorDouble
dotProduct: squants.space.Length = 5044.0 km

scala> val crossProduct = vector crossProduct vectorDouble  // currently only supported for 3-dimensional vectors
crossProduct: vector.SVectorType = QuantityVector(WrappedArray(0.0 km, 1.1368683772161603E-13 km, 0.0 km))
```

Simple non-quantity (Double based) vectors are also supported.

```scala
import squants.DoubleVector

val vector: DoubleVector = SVector(1.2, 4.3, 2.3, 5.4)   // a Four-dimensional vector
```

#### Dimensional conversions within Vector operations.

Currently dimensional conversions are supported by using the slightly verbose, but flexible map method.

```scala
scala> import squants.{DoubleVector, QuantityVector}
import squants.{DoubleVector, QuantityVector}

scala> import squants.motion.Velocity
import squants.motion.Velocity

scala> import squants.space.{Area, Kilometers, Length, Meters}
import squants.space.{Area, Kilometers, Length, Meters}

scala> import squants.time.Seconds
import squants.time.Seconds

scala> val vectorLength = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
vectorLength: squants.QuantityVector[squants.space.Length] = QuantityVector(WrappedArray(1.2 km, 4.3 km, 2.3 km))

scala> val vectorArea = vectorLength.map[Area](_ * Kilometers(2))      // QuantityVector(2.4 km², 8.6 km², 4.6 km²)
vectorArea: squants.QuantityVector[squants.space.Area] = QuantityVector(ArrayBuffer(2.4 km², 8.6 km², 4.6 km²))

scala> val vectorVelocity = vectorLength.map[Velocity](_ / Seconds(1)) // QuantityVector(1200.0 m/s, 4300.0 m/s, 2300.0 m/s)
vectorVelocity: squants.QuantityVector[squants.motion.Velocity] = QuantityVector(ArrayBuffer(1200.0 m/s, 4300.0 m/s, 2300.0 m/s))

scala> val vectorDouble = DoubleVector(1.2, 4.3, 2.3)
vectorDouble: squants.DoubleVector = DoubleVector(WrappedArray(1.2, 4.3, 2.3))

scala> val vectorLength = vectorDouble.map[Length](Kilometers(_))      // QuantityVector(1.2 km, 4.3 km, 2.3 km)
vectorLength: squants.QuantityVector[squants.space.Length] = QuantityVector(ArrayBuffer(1.2 km, 4.3 km, 2.3 km))
```

Convert QuantityVectors to specific units using the `to` or `in` method - much like Quantities.

```scala
scala> val vectorLength = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3))
vectorLength: squants.QuantityVector[squants.space.Length] = QuantityVector(WrappedArray(1.2 km, 4.3 km, 2.3 km))

scala> val vectorMetersNum = vectorLength.to(Meters)   // DoubleVector(1200.0, 4300.0, 2300.0)
vectorMetersNum: squants.DoubleVector = DoubleVector(ArrayBuffer(1200.0, 4300.0, 2300.0))

scala> val vectorMeters = vectorLength.in(Meters)      // QuantityVector(1200.0 m, 4300.0 m, 2300.0 m)
vectorMeters: squants.QuantityVector[squants.space.Length] = QuantityVector(ArrayBuffer(1200.0 m, 4300.0 m, 2300.0 m))
```

## Market Package
Market Types are similar but not quite the same as other quantities in the library.
The primary type, Money, is a Dimensional Quantity, and its Units of Measure are Currencies.
However, because the conversion multipliers between currency units can not be predefined,
many of the behaviors have been overridden and augmented to realize correct behavior.

### Money
A Quantity of purchasing power measured in Currency units.
Like other quantities, the Unit of Measures are used to create Money values.

```scala
scala> import squants.market.{BTC, JPY, USD, XAU}
import squants.market.{BTC, JPY, USD, XAU}

scala> val tenBucks = USD(10)      // Money: 10 USD
tenBucks: squants.market.Money = 1E+1 USD

scala> val someYen = JPY(1200)     // Money: 1200 JPY
someYen: squants.market.Money = 1.2E+3 JPY

scala> val goldStash = XAU(50)     // Money: 50 XAU
goldStash: squants.market.Money = 5E+1 XAU

scala> val digitalCache = BTC(50)  // Money: 50 BTC
digitalCache: squants.market.Money = 5E+1 BTC
```

### Price
A Ratio between Money and another Quantity.
A Price value is typed on a Quantity and can be denominated in any defined Currency.

*Price = Money / Quantity*

Assuming these imports:
```scala
import squants.{Dozen, Each}
import squants.energy.MegawattHours
import squants.market.USD
import squants.space.UsGallons
```

You can compute the following:
```scala
scala> val threeForADollar = USD(1) / Each(3)
threeForADollar: squants.market.Price[squants.Dimensionless] = 1 USD/3.0 ea

scala> val energyPrice = USD(102.20) / MegawattHours(1)
energyPrice: squants.market.Price[squants.energy.Energy] = 102.2 USD/1.0 MWh

scala> val milkPrice = USD(4) / UsGallons(1)
milkPrice: squants.market.Price[squants.space.Volume] = 4 USD/1.0 gal

scala> val costForABunch = threeForADollar * Dozen(10)
costForABunch: squants.market.Money = 4E+1 USD

scala> val energyCost = energyPrice * MegawattHours(4)
energyCost: squants.market.Money = 408.8 USD

scala> val milkQuota = USD(20) / milkPrice
milkQuota: squants.space.Volume = 5.0 gal
```

Conversions to Strings
```scala
scala> val money = USD(123.456)
money: squants.market.Money = 123.456 USD

scala> val s = money.toString  // returns full precision amount with currency code
s: String = 123.456 USD

scala> val s = money.toFormattedString // returns currency symbol and amount rounded based on currency rules
s: String = $123.46
```

### FX Support
Currency Exchange Rates are used to define the conversion factors between currencies

```scala
scala> import squants.market.{CurrencyExchangeRate, JPY, Money, USD}
import squants.market.{CurrencyExchangeRate, JPY, Money, USD}

scala> // create an exchange rate
     | val rate1 = CurrencyExchangeRate(USD(1), JPY(100))
rate1: squants.market.CurrencyExchangeRate = USD/JPY 100.0

scala> // OR
     | val rate2 = USD / JPY(100)
rate2: squants.market.CurrencyExchangeRate = USD/JPY 100.0

scala> // OR
     | val rate3 = JPY(100) -> USD(1)
rate3: squants.market.CurrencyExchangeRate = USD/JPY 100.0

scala> // OR
     | val rate4 = JPY(100) toThe USD(1)
rate4: squants.market.CurrencyExchangeRate = USD/JPY 100.0

scala> val someYen: Money = JPY(350)
someYen: squants.market.Money = 3.5E+2 JPY

scala> val someBucks: Money = USD(23.50)
someBucks: squants.market.Money = 23.5 USD
```

Use the `convert` method which automatically converts the money to the 'other' currency:

```scala
scala> val dollarAmount: Money = rate1.convert(someYen)
dollarAmount: squants.market.Money = 3.5 USD

scala> val yenAmount: Money = rate1.convert(someBucks)
yenAmount: squants.market.Money = 2.35E+3 JPY
```

Or just use the `*` operator in either direction (money * rate, or rate * money):
```scala
scala> val dollarAmount2: Money = rate1 * someYen
dollarAmount2: squants.market.Money = 3.5 USD

scala> val yenAmount2: Money = someBucks * rate1
yenAmount2: squants.market.Money = 2.35E+3 JPY
```

### Money Context
A MoneyContext can be implicitly declared to define default settings and applicable exchange rates within its scope.
This allows your application to work with a default currency based on an application configuration or other dynamic source.
It also provides support for updating exchange rates and using those rates for automatic conversions between currencies.
The technique and frequency chosen for exchange rate updates is completely in control of the application.

Assuming these imports:
```scala
import squants.energy.MegawattHours
import squants.market.{CAD, JPY, MXN, USD}
import squants.market.defaultMoneyContext
```

You can compute:
```scala
scala> val exchangeRates = List(USD / CAD(1.05), USD / MXN(12.50), USD / JPY(100))
exchangeRates: List[squants.market.CurrencyExchangeRate] = List(USD/CAD 1.05, USD/MXN 12.5, USD/JPY 100.0)

scala> implicit val moneyContext = defaultMoneyContext withExchangeRates exchangeRates
moneyContext: squants.market.MoneyContext = MoneyContext(DefaultCurrency(USD),Currencies(ARS,AUD,BRL,BTC,CAD,CHF,CLP,CNY,CZK,DKK,ETH,EUR,GBP,HKD,INR,JPY,KRW,LTC,MXN,MYR,NAD,NOK,NZD,RUB,SEK,USD,XAG,XAU,ZAR),ExchangeRates(USD/CAD 1.05,USD/JPY 100.0,USD/MXN 12.5),AllowIndirectConversions(true))

scala> val energyPrice = USD(102.20) / MegawattHours(1)
energyPrice: squants.market.Price[squants.energy.Energy] = 102.2 USD/1.0 MWh

scala> val someMoney = Money(350) // 350 in the default Cur
someMoney: squants.market.Money = 3.5E+2 USD

scala> val usdMoney: Money = someMoney in USD
usdMoney: squants.market.Money = 3.5E+2 USD

scala> val usdBigDecimal: BigDecimal = someMoney to USD
usdBigDecimal: BigDecimal = 350.0

scala> val yenCost: Money = (energyPrice * MegawattHours(5)) in JPY
yenCost: squants.market.Money = 5.11E+4 JPY

scala> val northAmericanSales: Money = (CAD(275) + USD(350) + MXN(290)) in USD
northAmericanSales: squants.market.Money = 635.1047619047619047619047619047619 USD
```

## Quantity Ranges
A `QuantityRange` is used to represent a range of Quantity values between an upper and lower bound:

```scala
import squants.QuantityRange
import squants.energy.{Kilowatts, Megawatts, Power}
```
```scala
val load1: Power = Kilowatts(1000)
// load1: squants.energy.Power = 1000.0 kW

val load2: Power = Kilowatts(5000)
// load2: squants.energy.Power = 5000.0 kW

val range: QuantityRange[Power] = QuantityRange(load1, load2)
// range: squants.QuantityRange[squants.energy.Power] = QuantityRange(1000.0 kW,5000.0 kW)
```

### Inclusivity and Exclusivitiy

The `QuantityRange` constructor requires that `upper` is strictly greater than `lower`:

```scala
import squants.space.LengthConversions._
// import squants.space.LengthConversions._

// this will work b/c upper > lower
QuantityRange(1.km, 5.km)
// res1: squants.QuantityRange[squants.space.Length] = QuantityRange(1.0 km,5.0 km)
```

This will fail because `lower` = `upper`:

```scala
scala> QuantityRange(1.km, 1.km)
java.lang.IllegalArgumentException: QuantityRange upper bound must be strictly greater than to the lower bound
  at squants.QuantityRange.<init>(QuantityRange.scala:25)
  ... 43 elided
```

`QuantityRange` contains two functions that check if an element is part of the range, `contains` and `includes`.
These differ in how they treat the range's upper bound: `contains()` _excludes_ it but `includes()` _includes_ it.

```scala
scala> val distances = QuantityRange(1.km, 5.km)
distances: squants.QuantityRange[squants.space.Length] = QuantityRange(1.0 km,5.0 km)

scala> distances.contains(5.km) // this is false b/c contains() doesn't include the upper range
res3: Boolean = false

scala> distances.includes(5.km) // this is true b/c includes() does include the upper range
res4: Boolean = true
```

### QuantityRange transformation

The multiplication and division operators create a `Seq` of ranges from the original.

For example:

Create a Seq of 10 sequential ranges starting with the original and each the same size as the original:
```scala
val rs1 = range * 10
// rs1: squants.QuantitySeries[squants.energy.Power] = Vector(QuantityRange(1000.0 kW,5000.0 kW), QuantityRange(5000.0 kW,9000.0 kW), QuantityRange(9000.0 kW,13000.0 kW), QuantityRange(13000.0 kW,17000.0 kW), QuantityRange(17000.0 kW,21000.0 kW), QuantityRange(21000.0 kW,25000.0 kW), QuantityRange(25000.0 kW,29000.0 kW), QuantityRange(29000.0 kW,33000.0 kW), QuantityRange(33000.0 kW,37000.0 kW), QuantityRange(37000.0 kW,41000.0 kW))
```
Create a Seq of 10 sequential ranges each 1/10th of the original size:

```scala
val rs2 = range / 10
// rs2: squants.QuantitySeries[squants.energy.Power] = Vector(QuantityRange(1000.0 kW,1400.0 kW), QuantityRange(1400.0 kW,1800.0 kW), QuantityRange(1800.0 kW,2200.0 kW), QuantityRange(2200.0 kW,2600.0 kW), QuantityRange(2600.0 kW,3000.0 kW), QuantityRange(3000.0 kW,3400.0 kW), QuantityRange(3400.0 kW,3800.0 kW), QuantityRange(3800.0 kW,4200.0 kW), QuantityRange(4200.0 kW,4600.0 kW), QuantityRange(4600.0 kW,5000.0 kW))
```

Create a Seq of 10 sequential ranges each with a size of 400 kilowatts:
```scala
val rs3 = range / Kilowatts(400)
// rs3: squants.QuantitySeries[squants.energy.Power] = Vector(QuantityRange(1000.0 kW,1400.0 kW), QuantityRange(1400.0 kW,1800.0 kW), QuantityRange(1800.0 kW,2200.0 kW), QuantityRange(2200.0 kW,2600.0 kW), QuantityRange(2600.0 kW,3000.0 kW), QuantityRange(3000.0 kW,3400.0 kW), QuantityRange(3400.0 kW,3800.0 kW), QuantityRange(3800.0 kW,4200.0 kW), QuantityRange(4200.0 kW,4600.0 kW), QuantityRange(4600.0 kW,5000.0 kW))
```

### QuantityRange operations

`QuantityRange` supports foreach, map, and foldLeft/foldRight. These vary slightly from the versions
in the Scala standard library in that they take a divisior as the first parameter. The examples below
illustrate their use.

Subdivide range into 1-Megawatt "slices", and foreach over each of slices:
```scala
range.foreach(Megawatts(1)) { r => println(s"lower = ${r.lower}, upper = ${r.upper}") }
// lower = 1000.0 kW, upper = 2000.0 kW
// lower = 2000.0 kW, upper = 3000.0 kW
// lower = 3000.0 kW, upper = 4000.0 kW
// lower = 4000.0 kW, upper = 5000.0 kW
```

Subdivide range into 10 slices and map over each slice:
```scala
range.map(10) { r => r.upper }
// res6: Seq[squants.energy.Power] = Vector(1400.0 kW, 1800.0 kW, 2200.0 kW, 2600.0 kW, 3000.0 kW, 3400.0 kW, 3800.0 kW, 4200.0 kW, 4600.0 kW, 5000.0 kW)
```

Subdivide range into 10 slices and fold over them, using 0 Megawatts as a starting value:
```scala
range.foldLeft(10, Megawatts(0)) { (z, r) => z + r.upper }
// res7: squants.energy.Power = 32.0 MW
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
```scala
import squants.energy.{Kilowatts, MegawattHours, Power}
import squants.market.{Price, USD}
import squants.time.Hours
```

Create Quantities using Unit Of Measure Factory objects (no implicits required):

```scala
scala> val load = Kilowatts(100)
load: squants.energy.Power = 100.0 kW

scala> val time = Hours(3.75)
time: squants.time.Time = 3.75 h

scala> val money = USD(112.50)
money: squants.market.Money = 112.5 USD

scala> val price = Price(money, MegawattHours(1))
price: squants.market.Price[squants.energy.Energy] = 112.5 USD/1.0 MWh
```

Create Quantities using Unit of Measure names and/or symbols (uses implicits):

```scala
import scala.language.postfixOps
import squants.energy.EnergyConversions._
import squants.energy.PowerConversions._
import squants.information.InformationConversions._
import squants.market.MoneyConversions._
import squants.space.LengthConversions._
import squants.time.TimeConversions._
```
```scala
scala> val load1 = 100 kW 			        // Simple expressions don’t need dots
load1: squants.energy.Power = 100.0 kW

scala> val load2 = 100 megawatts
load2: squants.energy.Power = 100.0 MW

scala> val time = 3.hours + 45.minutes     // Compound expressions may need dots
time: squants.time.Time = 3.75 h
```

Create Quantities using operations between other Quantities:

```scala
scala> val energyUsed = 100.kilowatts * (3.hours + 45.minutes)
energyUsed: squants.energy.Energy = 375000.0 Wh

scala> val price = 112.50.USD / 1.megawattHours
price: squants.market.Price[squants.energy.Energy] = 112.5 USD/1.0 MWh

scala> val speed = 55.miles / 1.hours
speed: squants.motion.Velocity = 24.587249174399997 m/s
```

Create Quantities using formatted Strings:

```scala
scala> val load = Power("40 MW")
load: scala.util.Try[squants.energy.Power] = Success(40.0 MW)
```

Create Quantities using Tuples:

```scala
scala> val load = Power((40.5, "MW"))
load: scala.util.Try[squants.energy.Power] = Success(40.5 MW)
```

Use single unit values to simplify expressions:

```scala
scala> // Hours(1) == 1.hours == hour
     | val ramp = 100.kilowatts / hour
ramp: squants.energy.PowerRamp = 100000.0 W/h

scala> val speed = 100.kilometers / hour
speed: squants.motion.Velocity = 27.77777777777778 m/s

scala> // MegawattHours(1) == 1.megawattHours == megawattHour == MWh
     | val hi = 100.dollars / MWh
hi: squants.market.Price[squants.energy.Energy] = 1E+2 USD/1.0 MWh

scala> val low = 40.dollars / megawattHour
low: squants.market.Price[squants.energy.Energy] = 4E+1 USD/1.0 MWh
```

Implicit conversion support for using Doubles, Longs and BigDecimals on the left side of multiply and divide operations:

```scala
scala> val load = 10.22 * 4.MW
load: squants.energy.Power = 40.88 MW

scala> val driveArrayCapacity = 12 * 600.gb
driveArrayCapacity: squants.information.Information = 7200.0 GB

scala> val freq = 60 / second
freq: squants.time.Frequency = 60.0 Hz

scala> val freq2 = BigDecimal(36000000) / hour
freq2: squants.time.Frequency = 10000.0 Hz
```

Create Quantity Ranges using `to` or `plusOrMinus` (`+-`) operators:

```scala
val range1 = 1000.kW to 5000.kW	             // 1000.kW to 5000.kW
val range2 = 5000.kW plusOrMinus 1000.kW     // 4000.kW to 6000.kW
val range2 = 5000.kW +- 1000.kW              // 4000.kW to 6000.kW
```

### Numeric Support
Most Quantities that support implicit conversions also include an implicit Numeric object that can be imported
to your code where Numeric support is required.  These follow the following pattern:

```scala
scala> import squants.mass.{Grams, Kilograms}
import squants.mass.{Grams, Kilograms}

scala> import squants.mass.MassConversions.MassNumeric
import squants.mass.MassConversions.MassNumeric

scala> val sum = List(Kilograms(100), Grams(34510)).sum
sum: squants.mass.Mass = 134510.0 g
```

NOTE - Because a quantity can not be multiplied by a like quantity and return a like quantity, the `Numeric.times`
operation of numeric is implemented to throw an UnsupportedOperationException for all types except `Dimensionless`.

The MoneyNumeric implementation is a bit different than the implementations for other quantity types
in a few important ways.

1. MoneyNumeric is a class, not an object like the others.
2. To create a MoneyNumeric value there must be an implicit MoneyContext in scope.
3. The MoneyContext must contain applicable exchange rates if you will be applying cross-currency Numeric ops.

The following code provides a basic example for creating a MoneyNumeric:

```scala
import squants.market.defaultMoneyContext
import squants.market.MoneyConversions._
import squants.market.USD
implicit val moneyContext = defaultMoneyContext
```

```scala
scala> implicit val moneyNum = new MoneyNumeric()
moneyNum: squants.market.MoneyConversions.MoneyNumeric = MoneyNumeric(MoneyContext(DefaultCurrency(USD),Currencies(ARS,AUD,BRL,BTC,CAD,CHF,CLP,CNY,CZK,DKK,ETH,EUR,GBP,HKD,INR,JPY,KRW,LTC,MXN,MYR,NAD,NOK,NZD,RUB,SEK,USD,XAG,XAU,ZAR),ExchangeRates(),AllowIndirectConversions(true)))

scala> val sum = List(USD(100), USD(10)).sum
sum: squants.market.Money = 1.1E+2 USD
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

```scala
import squants.space.Length
// import squants.space.Length

import squants.experimental.unitgroups.ImplicitDimensions.space._
// import squants.experimental.unitgroups.ImplicitDimensions.space._

import squants.experimental.unitgroups.UnitGroup
// import squants.experimental.unitgroups.UnitGroup

import squants.experimental.unitgroups.si.strict.implicits._
// import squants.experimental.unitgroups.si.strict.implicits._

val siLengths: UnitGroup[Length] = implicitly[UnitGroup[Length]]
// siLengths: squants.experimental.unitgroups.UnitGroup[squants.space.Length] = squants.experimental.unitgroups.si.strict.package$implicits$$anon$1@f52ca1b
```

To print out units and their conversion factors to the primary SI unit, you could use this code:

```scala
import squants.{Quantity, UnitOfMeasure}
// import squants.{Quantity, UnitOfMeasure}

def mkConversionFactor[A <: Quantity[A]](uom: UnitOfMeasure[A]): Double = {
  val one = uom(1)
  one.to(one.dimension.siUnit)
}
// mkConversionFactor: [A <: squants.Quantity[A]](uom: squants.UnitOfMeasure[A])Double

def mkTuple[A <: Quantity[A]](uom: UnitOfMeasure[A]): (String, Double) = {
  (uom.symbol, mkConversionFactor(uom))
}
// mkTuple: [A <: squants.Quantity[A]](uom: squants.UnitOfMeasure[A])(String, Double)

siLengths.sortedUnits.toList.map(mkTuple).foreach(println)
// (nm,1.0E-9)
// (µm,1.0E-6)
// (mm,0.001)
// (cm,0.01)
// (dm,0.1)
// (m,1.0)
// (dam,10.0)
// (hm,100.0)
// (km,1000.0)
```

Note that `UnitGroup`'s `sortedUnits` field is a `SortedSet`, so before mapping over it,
you will probably want to convert it to a List, otherwise the output may be resorted.

### Non-SI UnitGroups

Other `UnitGroup` definitions don't use implicits. For example, `squants.experimental.unitgroups.uscustomary.space.UsCustomaryLiquidVolumes` or `squants.experimental.unitgroups.misc.TroyMasses` can be imported and used directly.

### Creating an ad-hoc UnitGroup

To create an ad-hoc `UnitGroup` just implement the trait. For example, to make a US cooking measure `UnitGroup`:

```scala
import squants.{Quantity, Dimension}
// import squants.{Quantity, Dimension}

import squants.space._
// import squants.space._

import squants.experimental.unitgroups.UnitGroup
// import squants.experimental.unitgroups.UnitGroup

val usCookingUnitGroup = new UnitGroup[Volume] {
  // units don't have to be specified in-order.
  val units: Set[UnitOfMeasure[Volume]] = Set(UsPints, UsGallons, Teaspoons, Tablespoons, UsQuarts, FluidOunces)
}
// usCookingUnitGroup: squants.experimental.unitgroups.UnitGroup[squants.space.Volume]{val units: Set[squants.UnitOfMeasure[squants.space.Volume]]} = $anon$1@495c28e0

// squants automatically sorts units
usCookingUnitGroup.sortedUnits.foreach(println)
// squants.space.Teaspoons$@1f2aa9fd
// squants.space.Tablespoons$@20a4318e
// squants.space.FluidOunces$@3f31c22
// squants.space.UsPints$@6b5332c3
// squants.space.UsQuarts$@5293da73
// squants.space.UsGallons$@25de9dee
```

The `UnitGroup` values provided with Squants are only samples and aren't intended to be exhaustive.
We encourage users to make their own `UnitGroup` defintitions and submit them as PRs if they're generally
applicable.

## Formatters

Squants provides an experimental API for formatting Quantities in the "best unit." For example,
convert Inches(12) to Feet(1). This is useful for producing human-friendly output.

To use a formatter, you must implement the `squants.formatters.Formatter` trait:

```scala
trait Formatter[A <: Quantity[A]] {
  def inBestUnit(quantity: Quantity[A]): A
}
```

### Default Formatter implementation

There is a default formatter implementation in `squants.experimental.formatter.DefaultFormatter`. This builds on the `UnitGroup`
API discussed above to choose the best `UnitOfMeasure` for a `Quantity`. The `DefaultFormatter` algorithm will probably
work for most use-cases, but users can create their own `Formatters` if they have custom needs.

To use `DefaultFormatter` import it, and a unit group:

```scala
import squants.experimental.formatter.DefaultFormatter
import squants.experimental.unitgroups.misc.AstronomicalLengthUnitGroup
```

Then create the formatter by passing in a unit group:
```scala
val astroFormatter = new DefaultFormatter(AstronomicalLengthUnitGroup)
// astroFormatter: squants.experimental.formatter.DefaultFormatter[squants.space.Length] = squants.experimental.formatter.DefaultFormatter@790fe346
```

Now, we create some values using human-unfriendly numbers:

```scala
import squants.space.LengthConversions._
// import squants.space.LengthConversions._

val earthToJupiter = 588000000.km
// earthToJupiter: squants.space.Length = 588000000.0 km

val earthToVoyager1 = 2.06e10.km
// earthToVoyager1: squants.space.Length = 20600000000.0 km

val earthToAlphaCentauri = 4.1315e+13.km
// earthToAlphaCentauri: squants.space.Length = 41315000000000.0 km
```

And format them into appropriate units (AUs and Parsecs, in this case):
```scala
astroFormatter.inBestUnit(earthToJupiter)
// res3: squants.space.Length = 3.9305372278938457 au

astroFormatter.inBestUnit(earthToVoyager1)
// res4: squants.space.Length = 137.70249471872998 au

astroFormatter.inBestUnit(earthToAlphaCentauri)
// res5: squants.space.Length = 1.3389279634339382 pc
```


### Implicit formatters

There is a nicer syntax for formatters available via implicits.
This lets you write expressions such as `12.inches.inBestUnit`. This syntax is added per-`Dimension`.

To use this syntax, first import `squants.experimental.formatter.syntax._`.
Then, for each `Dimension` you wish to use, place a Formatter for the Dimension in implicit scope. In this example,
we're adding support for `Length`.

```scala
import squants.experimental.formatter.DefaultFormatter
import squants.experimental.formatter.syntax._
import squants.mass.MassConversions._
import squants.space.Length
import squants.space.LengthConversions._
import squants.experimental.unitgroups.misc.AstronomicalLengthUnitGroup
```

```scala
implicit val astroFormatter = new DefaultFormatter(AstronomicalLengthUnitGroup)
// astroFormatter: squants.experimental.formatter.DefaultFormatter[squants.space.Length] = squants.experimental.formatter.DefaultFormatter@135301a1

val earthToJupiter = 588000000.km
// earthToJupiter: squants.space.Length = 588000000.0 km

val earthToVoyager1 = 2.06e10.km
// earthToVoyager1: squants.space.Length = 20600000000.0 km

val earthToAlphaCentauri = 4.1315e+13.km
// earthToAlphaCentauri: squants.space.Length = 41315000000000.0 km

earthToJupiter.inBestUnit
// res0: squants.Quantity[squants.space.Length] = 3.9305372278938457 au

earthToVoyager1.inBestUnit
// res1: squants.Quantity[squants.space.Length] = 137.70249471872998 au

earthToAlphaCentauri.inBestUnit
// res2: squants.Quantity[squants.space.Length] = 1.3389279634339382 pc
```

This example won't compile because there is no `Formatter[Mass]` in implicit scope:

```scala
scala> 5000.grams.inBestUnit
<console>:26: error: could not find implicit value for parameter formatter: squants.experimental.formatter.Formatter[squants.mass.Mass]
       5000.grams.inBestUnit
                  ^
```

### SI Formatters and implicit syntax

When using SI units, and the default formatter algorithm, you don't have to declare a `Formatter` and place it in
implicit scope. The compiler can do that for you. This creates a very human-friendly API by using the appropriate
imports.

First, import the SI unit groups and their implicits:
```scala
import squants.experimental.unitgroups.ImplicitDimensions.space._
import squants.experimental.unitgroups.si.strict.implicits._
```

Next, import the formatter syntax described above:

```scala
import squants.experimental.formatter.syntax._
```

Finally, add imports for implicitly deriving formatters:
```scala
scala> import squants.experimental.formatter.implicits._
import squants.experimental.formatter.implicits._
```

Now we can create quantities and format them by calling `.inBestUnit` directly:

```scala
import squants.space.LengthConversions._
```

```scala
5.cm.inBestUnit
// res0: squants.Quantity[squants.space.Length] = 5.0 cm

500.cm.inBestUnit
// res1: squants.Quantity[squants.space.Length] = 5.0 m

3000.meters.inBestUnit
// res2: squants.Quantity[squants.space.Length] = 3.0 km
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
```scala
import squants.energy.Kilowatts
import squants.motion.{Acceleration, Velocity}
import squants.space.{Kilometers, Length}
import squants.space.LengthConversions._
import squants.time.{Hours, Seconds, Time}
import squants.time.TimeConversions._
```

You can code the following:
```scala
scala> val distance: Length = Kilometers(100)
distance: squants.space.Length = 100.0 km

scala> val time: Time = Hours(2)
time: squants.time.Time = 2.0 h

scala> val velocity: Velocity = distance / time
velocity: squants.motion.Velocity = 13.88888888888889 m/s

scala> val acc: Acceleration = velocity / Seconds(1)
acc: squants.motion.Acceleration = 13.88888888888889 m/s²

scala> val gravity = 32.feet / second.squared
gravity: squants.Acceleration = 9.7536195072 m/s²
```

Power is the 1st Time Derivative of Energy, PowerRamp is the 2nd.
```scala
scala> val power = Kilowatts(100)
power: squants.energy.Power = 100.0 kW

scala> val time: Time = Hours(2)
time: squants.time.Time = 2.0 h

scala> val energy = power * time
energy: squants.energy.Energy = 200000.0 Wh

scala> val ramp = Kilowatts(50) / Hours(1)
ramp: squants.energy.PowerRamp = 50000.0 W/h
```

## Use Cases

### Dimensional Analysis

The primary use case for Squants, as described above, is to produce code that is typesafe within domains
that perform dimensional analysis.

This code samples in this section use these imports:
```scala
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

```scala
scala> implicit val moneyContext = defaultMoneyContext
moneyContext: squants.market.MoneyContext = MoneyContext(DefaultCurrency(USD),Currencies(ARS,AUD,BRL,BTC,CAD,CHF,CLP,CNY,CZK,DKK,ETH,EUR,GBP,HKD,INR,JPY,KRW,LTC,MXN,MYR,NAD,NOK,NZD,RUB,SEK,USD,XAG,XAU,ZAR),ExchangeRates(),AllowIndirectConversions(true))

scala> val energyPrice: Price[Energy] = 45.25.money / megawattHour
energyPrice: squants.market.Price[squants.energy.Energy] = 45.25 USD/1.0 MWh

scala> val energyUsage: Energy = 345.kilowatts * 5.4.hours
energyUsage: squants.energy.Energy = 1863000.0000000002 Wh

scala> val energyCost: Money = energyPrice * energyUsage
energyCost: squants.market.Money = 84.30075000000000905 USD

scala> val dodgeViper: Acceleration = 60.miles / hour / 3.9.seconds
dodgeViper: squants.motion.Acceleration = 6.877552216615386 m/s²

scala> val speedAfter5Seconds: Velocity = dodgeViper * 5.seconds
speedAfter5Seconds: squants.motion.Velocity = 34.38776108307693 m/s

scala> val timeTo100MPH: Time = 100.miles / hour / dodgeViper
timeTo100MPH: squants.time.Time = 6.499999999999999 s

scala> val density: Density = 1200.kilograms / cubicMeter
density: squants.mass.Density = 1200.0 kg/m³

scala> val volFlowRate: VolumeFlow = 10.gallons / minute
volFlowRate: squants.motion.VolumeFlow = 6.30901964E-4 m³/s

scala> val flowTime: Time = 30.minutes
flowTime: squants.time.Time = 30.0 m

scala> val totalMassFlow: Mass = volFlowRate * flowTime * density
totalMassFlow: squants.mass.Mass = 1362.7482422399999 kg
```

### Domain Modeling
Another excellent use case for Squants is stronger typing for fields in your domain model.

Code samples in this section use these imports:
```scala
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

```scala
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

```scala
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

* Repeat for scala.js 1.0.0-RC1
```
  SCALAJS_VERSION=1.0.0-RC1 sbt +squantsJS/publishSigned
```

* Then make a release (Note: after this step the release cannot be replaced)
```
  sbt sonatypeRelease
```
