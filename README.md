# Squants

**The Scala API for Quantities, Units of Measure and Dimensional Analysis**

Squants is a framework of data types and a domain specific language (DSL) for representing Quantities,
their Units of Measure, and their Dimensional relationships.
The API supports typesafe dimensional analysis, improved domain models and more.
All types are immutable and thread-safe.

[Website](http:/www.squants.com/)
|
[GitHub](https://github.com/garyKeorkunian/squants)
|
[User Forum](https://groups.google.com/forum/#!forum/squants)
|
[Wiki](https://github.com/garyKeorkunian/squants/wiki)

### Current Versions
Current Release: **0.3.3**
([API Docs](https://oss.sonatype.org/service/local/repositories/releases/archive/com/squants/squants_2.10/0.3.3/squants_2.10-0.3.3-javadoc.jar/!/index.html#squants.package))

Development Build: **0.4.0-SNAPSHOT**
([API Docs](https://oss.sonatype.org/service/local/repositories/snapshots/archive/com/squants/squants_2.10/0.4.0-SNAPSHOT/squants_2.10-0.4.0-SNAPSHOT-javadoc.jar/!/index.html#squants.package))

[Release History](https://github.com/garyKeorkunian/squants/wiki/Release-History)

[![Build Status](https://travis-ci.org/garyKeorkunian/squants.png?branch=master)](https://travis-ci.org/garyKeorkunian/squants)

## Installation
Repository hosting for Squants is provided by Sonatype.
To use Squants in your SBT project you will need to add the following dependency to your build.

    "com.squants"  %% "squants"  % "0.3.3"

To use Squants interactively in the Scala REPL, clone the git repo and run `sbt console`

    git clone https://github.com/garyKeorkunian/squants
    cd squants
    sbt console

## Better Dimensional Analysis
*The Trouble with Doubles*

When building programs that perform some type of dimensional analysis, developers are quick to declare
quantities using a basic numeric type, usually Double.  While this may be satisfactory in some situations,
it can often lead to semantic and other logic issues.

For example, when using a Double to describe a quantity of Energy (kWh) and Power (kW), it is possible
to compile a program that adds these two values together.  This is not appropriate as kW and kWh
measure two different quantities.  The unit kWh is used to measure an amount of Energy used
or produced.  The unit kW is used to measure Power/Load, the rate at which Energy is being used
or produced, that is, Power is the first time derivative of Energy.

*Power = Energy / Time*

Consider the following code

```scala
val loadKw: Double = 1.2
val energyMwh: Double = 24.2
val sumKw = loadKw + energyMwh
```

which not only adds different quantity types (load vs energy), it also fails to convert the scales (Mega vs Kilo).
Because this code compiles, detection of these errors is pushed further into the development cycle.

### Dimensional Types
The Squants Type Library and DSL helps prevent errors like these by type checking operations at compile time and
automatically applying scale and type conversions (see below) at run-time.  For example,

```scala
val load1: Power = Kilowatts(12)
val load2: Power = Megawatts(0.023)
val sum = load1 + load2
sum should be(Kilowatts(35))
sum should be(Megawatts(0.035))
```

is a valid assertion because Kilowatts and Megawatts are both measures of load.  Only the scale is
different and the framework applies an appropriate conversion.  Also, notice that keeping track of
the scale within the value name is no longer needed.

_Note - Quantities can be initialized using any type T with an available implicit Numeric[T].
In the current iteration, those values are converted to Double, which is the current type of the
underlying value.
The goal of the project is to refactor the underlying value to a Generic so that the initializing
type is retained as the underlying value type.
This allows user code to use high precision types for the underlying value.

### Dimensional Type Safety
The following code highlights the type safety features.

```scala
val load: Power = Kilowatts(1.2)
val energy: Energy = KilowattHours(23.0)
val sum = load + energy // Invalid operation - does not compile
```
The invalid expression prevents the code from compiling, catching the error made when using Double above.

### Smart Type Conversions
Dimensionally smart type conversions are a key feature of Squants.
Most conversions are implemented by defining relationships between Quantity types using infix operations.

```scala
val load: Power = Kilowatts(1.2)
val time: Time = Hours(2)
val energyUsed: Energy = load * time
energyUsed should be(KilowattHours(2.4))
```
This code demonstrates use of the Power.* method, defined as an infix operator that takes a Time
and returns an Energy, conversely

```scala
val aveLoad: Power = energyUsed / time
aveLoad should be(Kilowatts(1.2)
```
demonstrates use of the Energy./ method that takes a Time and returns a Power

### Unit Conversions
If necessary, the value in the desired unit can be extracted with the `to` method.

```scala
val load: Power = Kilowatts(1200)
val mw: Double = load to Kilowatts // returns 1200.0 (default method)
val gw: Double = load to Gigawatts // returns 0.0012
val my: Double = load to MyPowerUnit // returns ??? Whatever you want
val kw: Double = load toKilowatts // returns 1200.0 (convenience method)
```
Strings formatted in the desired unit is also supported

```scala
val kw: String = load toString Kilowatts // returns “1200.0 kW”
val mw: String = load toString Megawatts // returns “1.2 MW”
val gw: String = load toString Gigawatts // returns “0.0012 GW”
```

### Approximations
Create an implicit Quantity value to be used as a tolerance in approximate equality comparisons.
Use the `approx` method (`=~` and `~=` operator) like the `equals` method (`==` operator)

```scala
implicit val tolerance = Watts(.1)
val load = Kilowatts(2.0)
val reading = Kilowatts(1.9999)

load =~ reading should be(true) // uses implicit tolerance
load approx reading should be(true) // uses implicit tolerance

// use instead of, or override implicit
load.=~(reading)(Watts(.01)) should be(false)
load.approx(reading)(Watts(.01)) should be(false)
```

### Vectors
** EXPERIMENTAL **

Vectors are implemented as a case class that takes a variable list of like quantities
representing a set of point coordinates in Cartesian space.
The dimensionality of the vector is determined by the number of arguments.

```scala
val vector = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3)
val magnitude: Length = vector.magnitude        // returns the scalar value of the vector
val normalized = vector.normalize(Kilometers)   // returns a corresponding vector scaled to 1 of the given unit

val vector2 = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3)
val vectorSum = vector + vector2        // returns the sum of two vectors
val vectorDiff = vector - vector2       // return the difference of two vectors
val vectorScaled = vector * 5           // returns vector scaled 5 times
val vectorReduced = vector / 5          // returns vector reduced 5 time
val vectorDouble = vector / 5.meters    // returns vector reduced and converted to DoubleVector
val dotProduct = vector * vectorDouble  // returns the Dot Product of vector and vectorDouble

val crossProduct = vector crossProduct vectorDouble  // currently only supported for 3-dimensional vectors
```

Dimensional conversions within Vector operations.
This feature is currently and development and the final implementation being evaluated.
This following type of operation is the goal.

```scala
val vectorLength = QuantityVector(Kilometers(1.2), Kilometers(4.3), Kilometers(2.3)
val vectorArea: QuantityVector[Area] = vectorLength * Kilometers(10)
val vectorVelocity: QuantityVector[Velocity] = vectorLength / Seconds(1)
```

Simple non-quantity (Double based) vectors are also supported

```scala
val vector = DoubleVector(1.2, 4.3, 2.3, 5.4)   // a Four-dimensional vector
```

These features are experimental in the current version and the API may undergo significant change before final release.

## Market Package
Market Types are similar but not quite the same as other quantities in the library.
The primary type, Money, is derived from Quantity, and its Units of Measure are Currencies.
However, because the conversion multipliers between units can not be predefined, many of the behaviors have been
overridden and augmented to realize correct behavior.

### Money
A Quantity of purchasing power measured in units we call Currencies.
Create Money values using standard Currency codes.

```scala
val tenBucks: Money = USD(10)
val someYen: Money = JPY(1200)
val goldStash: Money = XAU(50)
val digitalStash: Money = BTC(50)
```

### Price
A Ratio between Money and another Quantity.
A Price value is typed on a Quantity and can be denominated in any defined Currency.

*Price = Money / Quantity*

```scala
val threeForADollar: Price[Dimensionless] = USD(1) / Each(3)
val energyPrice: Price[Energy] = USD(102.20) / MegawattHours(1)
val milkPrice: Price[Volume] = USD(4) / UsGallons(1)

val costForABunch: Money = threeForADollar * Dozen(10) // returns USD(40)
val energyCost: Money = energyPrice * MegawattHours(4) // returns USD(408.80)
val milkQuota: Volume = milkPrice * USD(20) // returns UsGallons(5)
```

### FX Support
Currency Exchange Rates

```scala
// create an exchange rate
val rate = CurrencyExchangeRate(USD(1), JPY(100))

val someYen: Money = JPY(350)
val someBucks: Money = USD(23.50)

// Use the convert method which automatically converts the money to the 'other' currency
val dollarAmount: Money = rate.convert(someYen) // returns USD(3.5)
val yenAmount: Money = rate.convert(someBucks)  // returns JPY(2350)

// or just use the * operator in either direction (money * rate, or rate * money)
val dollarAmount2: Money = rate * someYen       // returns USD(3.5)
val yenAmount2: Money = someBucks * rate		// returns JPY(2350)
```

### Money Context
A MoneyContext can be implicitly declared to define default settings and applicable exchange rates within a context.
This allows your application to work with a default currency based on an application configuration or other dynamic source.
It also provides support for updating exchange rates and using those rates for automatic conversions between currencies.
The technique and frequency chosen for exchange rate updates is completely in control of the application.

```scala
implicit val moneyContext = MoneyContext(defCur, curList, exchangeRates)
val someMoney = Money(350) // 350 in the default Cur
val usdMoney: Money = someMoney in USD
val usdDouble: Double = someMoney to USD
val yenCost: Money = (energyPrice * MegawattHours(5)) in JPY
val northAmericanSales: Money = (CAD(275) + USD(350) + MXN(290)) in USD
```

## Quantity Ranges
Used to represent a range of Quantity values between an upper and lower bound

```scala
val load1: Power = Kilowatts(1000)
val load2: Power = Kilowatts(5000)
val range: QuantityRange[Power] = QuantityRange(load1, load2)
```
Use multiplication and division to create a Seq of ranges from the original

```scala
// Create a Seq of 10 sequential ranges starting with the original and each the same size as the original
val rs1 = range * 10
// Create a Seq of 10 sequential ranges each 1/10th of the original size
val rs2 = range / 10
// Create a Seq of 10 sequential ranges each with a size of 400 kilowatts
val rs3 = range / Kilowatts(400)
```
Apply foreach, map and foldLeft/foldRight directly to QuantityRanges with a divisor

```scala
// foreach over each of the 400 kilometer ranges within the range
range.foreach(Kilometers(400)) {r => ???}
// map over each of 10 even parts of the range
range.map(10) {r => ???}
// fold over each 10 even parts of the range
range.foldLeft(10)(0) {(z, r) => ???}
```

## Natural Language Features
Implicit conversions give the DSL some features that allows client code to express quantities in a more natural way.

Create Quantities using Unit Of Measure Factory objects (no implicits required)

```scala
val load = Kilowatts(100)
val time = Hours(3.75)
val money = USD(112.50)
val price = Price(money, MegawattHours(1))
```

Create Quantities using Unit of Measure names and/or symbols (uses implicits)

```scala
val load1 = 100 kW 			        // Simple expressions don’t need dots
val load2 = 100 megaWatts
val time = 3.hours + 45.minutes     // Compound expressions may need dots
```

Create Quantities using operations between other Quantities

```scala
val energyUsed = 100.kilowatts * (3.hours + 45.minutes)
val price = 112.50.USD / 1.megawattHours
val speed = 55.miles / 1.hours
```

Create Quantities using formatted Strings

```scala
val load = Power("40 MW")		// 40 MW
```

Use single unit values to simplify expressions

```scala
// Hours(1) == 1.hours == hour
val ramp = 100.kilowatts / hour
val speed = 100.kilometers / hour

// MegawattHours(1) == 1.megawattHours == megawattHour == MWh
val hi = 100.dollars / MWh
val low = 40.dollars / megawattHour
```

Implicit conversion support for using Double on the left side of operations

```scala
val price = 10 / dollar	    // 1 USD / 10 ea
val freq = 60 / second	    // 60 Hz
val load = 10 * 4.MW		// 40 MW
```

Create Quantity Ranges using `to` or `plusOrMinus` (`+-`) operators

```scala
val range1 = 1000.kW to 5000.kW	    // 1000.kW to 5000.kW
val range2 = 5000.kW +- 1000.kW     // 4000.kW to 6000.kW
```

### Numeric Support
Most Quantities that support implicit conversions also include an implicit Numeric object that can be imported
to your code where Numeric support is required.  These follow the following pattern:

```scala
import squants.mass.MassConversions.MassNumeric

val sum = List(Kilograms(100), Grams(34510)).sum
```

## Type Hierarchy
The type hierarchy includes two root base traits:  Quantity and UnitOfMeasure

### Quantity
Quantity measures the magnitude or multitude of some thing.  Classes extending Quantity represent the
various types of quantities that can be measured.  These are our alternatives to just using Double.
Common 'Base' Quantities include Mass, Temperature, Length, Time, Energy, etc.

Derived Quantities are based on one or more other quantities.  Typical examples include Time Derivatives.

Speed is the 1st Time Derivative of Length (Distance), Acceleration is the 2nd Time Derivative.

```scala
val distance: Length = Kilometers(100)
val time: Time = Hours(2)
val speed: Speed = distance / time
speed.toKilometersPerHour should be(50.0)
val acc: Acceleration = Meters(50) / Second(1) / Second(1)
acc.toMetersPerSecondSquared should be(50)
```
Power is the 1st Time Derivative of Energy, PowerRamp is the 2nd

```scala
val energy: Energy = KilowattHours(100)
val time: Time = Hours(2)
val power: Power = energy / time
power.toKilowatts should be(50.0)
val ramp: PowerRamp = KilowattHours(50) / Hours(1) / Hours(1)
ramp.toKilowattsPerHour should be(50)
```

Squants currently supports over 50 quantity types.

### Unit of Measure
UnitOfMeasure is the scale or multiplier in which the Quantity is being measured.

For each Quantity a series of UOM objects implement a base UOM trait typed to that Quantity.
The UOM objects define the unit symbols and conversion settings.
Factory methods in each UOM object create instances of the corresponding Quantity.

For example UOM objects extending LengthUnit can be used to create Length quantities

```scala
val len1: Length = Inches(5)
val len2: Length = Meters(4.3)
val len3: Length = UsMiles(345.2)
```
Units of Measure for Time include Milliseconds, Seconds, Minutes, Hours, and Days

Units of Measure for Temperature include Celsius, Kelvin, and Fahrenheit

Units of Measure for Mass include Grams, Kilograms, etc.

Squants currently supports over 150 units of measure

## Use Cases

### Dimensional Analysis

The primary use case for Squants, as described above, is to produce code that is typesafe with in domains
that perform dimensional analysis.

```scala
val energyPrice: Price[Energy] = 45.25.money / megawattHour
val energyUsage: Energy = 345.kilowatts * 5.4.hours
val energyCost: Money = energyPrice * energyUsage

val dodgeViper: Acceleration = 60.miles / hour / 3.9.seconds
val speedAfter5Seconds: Velocity = dodgeViper * 5.seconds
val TimeTo100MPH: Time = 100.miles / hour / dodgeViper

val density: Density = 1200.kilograms / cubicMeter
val volFlowRate: VolumeFlowRate = 10.gallons / minute
val flowTime: Time = 30.minutes
val totalMassFlow: Mass = volFlowRate * flowTime * density
```

### Domain Modeling
Another excellent use case for Squants is stronger types for fields in your domain model.
This is OK ...

```scala
case class Generator(id: String, maxLoadKW: Double, rampRateKWph: Double,
operatingCostPerMWh: Double, currency: String, maintenanceTimeHours: Double)
...
val gen1 = Generator("Gen1", 5000, 7500, 75.4, "USD", 1.5)
val gen2 = Generator("Gen2", 100, 250, 2944.5, "JPY", 0.5)
assetManagementActor ! ManageGenerator(gen1)
```
… but this is much better

```scala
case class Generator(id: String, maxLoad: Power, rampRate: PowerRamp,
operatingCost: Price[Energy], maintenanceTime: Time)
...
val gen1 = Generator("Gen1", 5 MW, 7.5.MW/hour, 75.4.USD/MWh, 1.5 hours)
val gen2 = Generator("Gen2", 100 kW, 250 kWph, 2944.5.JPY/MWh, 30 minutes)
assetManagementActor ! ManageGenerator(gen1)
```

### Anticorruption Layers

Create wrappers around external services that use basic types to represent quantities.
Your application code then uses the ACL to communicate with that system thus eliminating the need to deal
with type and scale conversions in your application logic.

```scala
class ScadaServiceAnticorruption(val service: ScadaService) {
  // ScadaService returns load as Double representing Megawatts
  def getLoad: Power = Megawatts(service.getLoad(meterId))
  }
  // ScadaService.sendTempBias requires a Double representing Fahrenheit
  def sendTempBias(temp: Temperature) =
    service.sendTempBias(temp.to(Fahrenheit))
}
```

Implement the ACL as a trait and mix in to the application's services where needed.

```scala
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
// LoadReading message used within the Squant’s enabled application context
case class LoadReading(meterId: String, time: Long, load: Power)
class ScadaLoadListener(router: Router) extends Actor {
  def receive = {
   // ScadaLoadReading, from an external service, types load as a string
   // eg, “10.3 MW”, “345 kW”
   case msg @ ScadaLoadReading(meterId, time, loadString) ⇒
    // This handler converts the string to a Power value and emits the events
    // to the routees which are actors within an app context that uses Squants
    router.route(LoadReading(meterId, time, Power(loadString)), sender())
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
## Roadmap

The following features and improvements are planned for the 1.0 release

* JSON Marshalling Support perhaps with a companion project or two for specific bindings (ie, squants-json4s)
* Validate and improve Money and FX Support
* Implement improved Vector Quantity support (Velocity, Acceleration, Force, etc)
* Additional Quantity Types, Units and Dimensional Conversions
* Optimize Performance and / or Conversion Precisions
  * Using a Double as the underlying value is likely providing the best performance
  * Better precision will likely take the form of replacing the underlying Double value with a Generic Type
  so that user code can choose a more precise type such as those provided by the
  [Spire](https://github.com/non/spire) project
* Enhance documentation and support presence
* Typesafe Activator Sample Project /  Template

## Caveats

Code is offered as-is, with no implied warranty of any kind.
Comments, criticisms, and/or praise are welcome, especially from scientists, engineers and the like.
