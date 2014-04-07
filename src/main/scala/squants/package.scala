/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

/**
 * ==Squants==
 * The Scala API for Quantities, Units of Measure and Dimensional Analysis
 *
 * ==Overview==
 * Squants is a framework of data types and a domain specific language (DSL) for representing Quantities,
 * their Units of Measure, and their Dimensional relationships.
 * The API supports typesafe dimensional analysis, improved domain models and more.
 * All types are immutable and thread-safe.
 *
 * The motivating driver for the framework was to ensure developers of VPower, Viridity Energy's
 * flagship energy management product, would have a type safe way to define energy
 * related expressions, operations, domain models and API's.
 *
 * For example, when using a Double to describe Energy (kWh) and Power/Load (kW), it is possible
 * to compile a program that adds these two values together.  This is not appropriate as kW and kWh
 * measure two different quantities.  The unit kWh is used to measure an amount of Energy used
 * or produced.  The unit kW is used to measure Power/Load, the rate at which Energy is being used
 * or produced, that is, Power/Load is the first time derivative of Energy, Energy / Time.
 *
 * Consider the following code
 * {{{
 *   val loadKw: Double = 1.2
 *   val energyMwh: Double = 24.2
 *   val sumKw = loadKw + energyMwh
 * }}}
 * which not only adds different quantity types (load vs energy), it also fails to convert the scales (Mega vs Kilo).
 * Because this code compiles, detection of these errors is pushed further into the development cycle.
 *
 * The Squants Type Library and DSL helps prevent errors like these by type checking operations at compile time and
 * automatically applying scale and type conversions (see below) at run-time.  For example,
 * {{{
 *   val load1: Power = Kilowatts(1.2)
 *   val load2: Power = Megawatts(0.023)
 *   val loadSum = load1 + load2
 *   loadSum should be(Kilowatts(24.2))
 * }}}
 * is a valid assertion because Kilowatts and Megawatts are both measures of load.  Only the scale is
 * different and the framework applies an appropriate conversion.  Also, notice that keeping track of
 * the scale within the value name is no longer needed.  If necessary, the value can be converted to a specific
 * scale using the methods provided by the quantity class.
 * {{{
 *   val load: Power == Kilowatts(1200)
 *   val kw: Double = load1.toKilowatts  // returns 1200.0
 *   val mw: Double = load1.toMegawatts  // returns 1.2
 *   val gw: Double = load1.toGigawatts  // returns 0.0012
 * }}}
 *
 * The following code highlights the type safety features.
 * {{{
 *   val load: Power = Kilowatts(1.2)
 *   val energy: Energy = MegawattsHours(0.023)
 *   val sum = load + energy // <--- INVALID OPERATION, DOES NOT COMPILE
 *   sumKw should be(Kilowatts(24.2))
 * }}}
 * The invalid expression prevents the code from compiling, catching the error made when using Double much earlier.
 *
 * Domain smart type conversions are supported.  Most conversions are implemented by defining relationships between
 * Quantity types using infix operations.
 * {{{
 *   val load: Power = Kilowatts(1.2)
 *   val time: Time = Hours(2)
 *   val energyUsed: Energy = load * time
 *   energyUsed should be(KilowattHours(2.4))
 * }}}
 * This code demonstrates use of the Power.* method, defined as an infix operator that takes a Time
 * value and returns an Energy value, conversely
 * {{{
 *   val aveLoad: Power = energyUsed / time
 *   aveLoad should be(Kilowatts(1.2)
 * }}}
 * demonstrates use of the Energy./ method that takes a Time and returns a Power
 *
 * Other conversions are done using specialized versions of a Quantity's factory methods
 * {{{
 *   val aveLoad = Power(energyUsed, time)  // equates to energyUsed / time
 *   val energyUsed = Energy(aveLoad, time) // equates to aveLoad * time
 * }}}
 *
 * ===Natural Language Features===
 * Implicit conversions give the DSL some features that allows client code to express quantities in a more natural way.
 * {{{
 *   val load = 1.2 kW
 *   val time = 2 hours
 *   val energyUsed = load * time
 *   energyUsed should be((2.4 kWh))
 * }}}
 *
 * Many Quantity classes also provide a factory method that takes a quantity expression
 * {{{
 *   val load = Power("1.2 kW")
 *   val time = Time("2h")
 *   val energy = Energy("23 MWh")
 * }}}
 * Useful for automatically interpreting strings from user input, json marshaller and other sources
 *
 * ===Type Hierarchy===
 * The type hierarchy includes two root base traits:  [[squants.Quantity]] and [[squants.UnitOfMeasure]]
 *
 * ==Quantity==
 * Quantity measures the magnitude or multitude of some thing.  Classes extending Quantity represent the
 * various types of quantities that can be measured.  These are our alternative to just using Double.
 * Common 'Base' Quantities include Mass, Temperature, Length, Time, Energy, etc.
 *
 * Derived Quantities are based on one or more other quantities.  Typical examples include Time Derivatives.
 *
 * Speed is the 1st Time Derivative of Length (Distance), Acceleration is the 2nd Time Derivative.
 * {{{
 *   val distance: Length = Kilometers(100)
 *   val time: Time = Hours(2)
 *   val speed: Speed = distance / time
 *   speed.toKilometersPerHour should be(50.0)
 *
 *   val acc: Acceleration = Meters(50) / Second(1) / Second(1)
 *   acc.toMetersPerSecondSquared should be(50)
 * }}}
 *
 * Power is the 1st Time Derivative of Energy, PowerRamp is the 2nd
 *
 * ==Unit of Measure==
 * UnitOfMeasure is the scale or multiplier in which the Quantity is being measured.
 *
 * For each Quantity a series of UOM objects implement a base UOM trait typed to that Quantity.
 * The UOM objects define the unit symbols and conversion settings.
 * Factory methods in each UOM object create instances of the corresponding Quantity.
 *
 * For example UOM objects extending [[squants.space.LengthUnit]] can be used to create Length quantities
 * {{{
 *   val len1: Length = Inches(5)
 *   val len2: Length = Meters(4.3)
 *   val len3: Length = UsMiles(345.2)
 * }}}
 *
 * Units of Measure for Time include Milliseconds, Seconds, Minutes, Hours, and Days
 *
 * Units of Measure for Temperature include Celsius, Kelvin, and Fahrenheit
 *
 * Units of Measure for Mass include Grams, Kilograms, etc.
 *
 * ==Domain Modeling==
 * An excellent use case for Squants is stronger types for fields in your domain model.
 * {{{
 *   case class Generator(name: String, maxLoad: Power, rampUpRate: PowerRamp,
 *   aveOperatingCost:  Price[Energy], minDailyDowntime: Time)
 * }}}
 *
 * Another use case is performing domain specific computations.
 * {{{
 *   val gen = Generators("My Gen", Kilowatt(100), KilowattsPerHour(200), Price(USD(51.2), megawatt), Minutes(45))
 *    // How long until gen is running at full capacity
 *   val timeToMaxLoad = gen.rampUpRate / gen.maxLoad
 * }}}
 *
 * @author  garyKeorkunian
 * @version 0.1
 * @since   0.1
 *
 */
package object squants {

  type QuantitySeries[A <: Quantity[A]] = IndexedSeq[QuantityRange[A]]

  /* Quantity Types brought into scope with just squants._ */

  /* SI Base Quantities and their Base Units */
  type Length = squants.space.Length
  val Meters = squants.space.Meters
  type Mass = squants.mass.Mass
  val Kilograms = squants.mass.Kilograms
  type Time = squants.time.Time
  val Seconds = squants.time.Seconds
  type ElectricCurrent = squants.electro.ElectricCurrent
  val Amperes = squants.electro.Amperes
  type Temperature = squants.thermal.Temperature
  val Kelvin = squants.thermal.Kelvin
  type ChemicalAmount = squants.mass.ChemicalAmount
  val Moles = squants.mass.Moles
  type LuminousIntensity = squants.photo.LuminousIntensity
  val Candelas = squants.photo.Candelas

  /* Common Derived Quantities */
  type Angle = squants.space.Angle
  val Radians = squants.space.Radians
  type SolidAngle = squants.space.SolidAngle
  val SquareRadians = squants.space.SquaredRadians

  type Area = squants.space.Area
  type Volume = squants.space.Volume

  type Density = squants.mass.Density

  type Velocity = squants.motion.Velocity
  type Acceleration = squants.motion.Acceleration
  type Jerk = squants.motion.Jerk
  type Momentum = squants.motion.Momentum
  type Force = squants.motion.Force
  type MassFlowRate = squants.motion.MassFlowRate
  type VolumeFlowRate = squants.motion.VolumeFlowRate

  type Energy = squants.energy.Energy
  type Power = squants.energy.Power
  type PowerRamp = squants.energy.PowerRamp

  /* Market Quantities */
  type Money = squants.market.Money
  type Price[A <: Quantity[A]] = squants.market.Price[A]

  /**
   * Provides implicit conversions that allow 'primitive' numbers to lead in * operations
   * {{{
   *    1.5 * Kilometers(10) should be(Kilometers(15))
   * }}}
   *
   * @param d Double
   */
  implicit class SquantifiedDouble(d: Double) {
    def *[A <: Quantity[A]](that: A): A = that * d
  }
}
