/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental.json

import org.scalatest._
import org.json4s.DefaultFormats
import squants.energy._
import squants.time._
import org.json4s.native.Serialization._
import squants.market._
import squants.market.Price
import squants.mass.{ Mass, Pounds }

class QuantitySerializerSpec extends FlatSpec with MustMatchers {

  object QuantitySerializerMarshaller {
    implicit val formats = DefaultFormats.withBigDecimal +
      new PowerSerializer +
      new EnergyPriceSerializer(defaultMoneyContext) +
      new MassPriceSerializer(defaultMoneyContext) +
      new MoneySerializer(defaultMoneyContext) +
      new TimeSerializer +
      new MassSerializer +
      new TimePriceSerializer(defaultMoneyContext)
  }

  behavior of "QuantitySerializer"

  import QuantitySerializerMarshaller._

  val seedValue = 10.22
  val seedValueInt = 10
  val jsonkW = "{\"value\":10.22,\"unit\":\"kW\"}"
  val jsonMW = "{\"value\":10.22,\"unit\":\"MW\"}"
  val jsonGW = "{\"value\":10.22,\"unit\":\"GW\"}"
  val jsonPowerInt = "{\"value\":10,\"unit\":\"kW\"}"

  val jsonMillis = "{\"value\":10.22,\"unit\":\"ms\"}"
  val jsonSeconds = "{\"value\":10.22,\"unit\":\"s\"}"
  val jsonMinutes = "{\"value\":10.22,\"unit\":\"m\"}"
  val jsonHours = "{\"value\":10.22,\"unit\":\"h\"}"
  val jsonTimeInt = "{\"value\":10,\"unit\":\"s\"}"

  it must "serialize a Power value" in {
    val json = write[Power](Kilowatts(seedValue))
    json must be(jsonkW)
    val json2 = write[Power](Megawatts(seedValue))
    json2 must be(jsonMW)
    val json3 = write[Power](Gigawatts(seedValue))
    json3 must be(jsonGW)
  }

  it must "deserialize a Power value" in {
    val power = read[Power](jsonkW)
    power must be(Kilowatts(seedValue))
    val powerMW = read[Power](jsonMW)
    powerMW must be(Megawatts(seedValue))
    val powerGW = read[Power](jsonGW)
    powerGW must be(Gigawatts(seedValue))
    val powerInt = read[Power](jsonPowerInt)
    powerInt must be(Kilowatts(seedValueInt))
  }

  it must "serialize a Time value" in {
    val json = write[Time](Milliseconds(seedValue))
    json must be(jsonMillis)
    val json2 = write[Time](Seconds(seedValue))
    json2 must be(jsonSeconds)
    val json3 = write[Time](Minutes(seedValue))
    json3 must be(jsonMinutes)
    val json4 = write[Time](Hours(seedValue))
    json4 must be(jsonHours)
  }

  it must "deserialize a Time value" in {
    val time = read[Time](jsonMillis)
    time must be(Milliseconds(seedValue))
    val time2 = read[Time](jsonSeconds)
    time2 must be(Seconds(seedValue))
    val time3 = read[Time](jsonMinutes)
    time3 must be(Minutes(seedValue))
    val time4 = read[Time](jsonHours)
    time4 must be(Hours(seedValue))
    val time5 = read[Time](jsonTimeInt)
    time5 must be(Seconds(seedValueInt))
  }

  it must "serialize a Mass value" in {
    val json = write[Mass](Pounds(seedValue))
    json must be("{\"value\":10.22,\"unit\":\"lb\"}")
  }

  behavior of "MoneySerializer"

  val jsonMoney = "{\"amount\":10.22,\"currency\":\"USD\"}"

  it must "serialize a Money value" in {
    val json = write[Money](USD(seedValue))
    json must be(jsonMoney)
  }

  it must "deserialize a Money value" in {
    val money = read[Money](jsonMoney)
    money must be(USD(seedValue))
  }

  behavior of "PriceSerializer"

  val jsonEnergyPrice = "{\"amount\":10.22,\"currency\":\"USD\",\"per\":\"1.0 MWh\"}"
  val jsonMassPrice = "{\"amount\":10.22,\"currency\":\"USD\",\"per\":\"1.0 lb\"}"
  val jsonTimePrice = "{\"amount\":10.22,\"currency\":\"USD\",\"per\":\"30.0 m\"}"

  it must "serialize an Energy Price" in {
    val json = write[Price[Energy]](USD(seedValue) / MegawattHours(1))
    json must be(jsonEnergyPrice)
  }

  it must "deserialize an Energy Price" in {
    val price = read[Price[Energy]](jsonEnergyPrice)
    price must be(USD(seedValue) / MegawattHours(1))
  }

  it must "serialize an Mass Price" in {
    val json = write[Price[Mass]](USD(seedValue) / Pounds(1))
    json must be(jsonMassPrice)
  }

  it must "deserialize an Mass Price" in {
    val price = read[Price[Mass]](jsonMassPrice)
    price must be(USD(seedValue) / Pounds(1))
  }

  it must "serialize a Time Price" in {
    val json = write[Price[Time]](USD(seedValue) / Minutes(30))
    json must be(jsonTimePrice)
  }

  it must "deserialize a Time Price" in {
    val price = read[Price[Time]](jsonTimePrice)
    price must be(USD(seedValue) / Minutes(30))
  }
}
