/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.json

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
      new PowerSerializer(Kilowatts) +
      new EnergyPriceSerializer(MegawattHours) +
      new MassPriceSerializer(Pounds) +
      new MoneySerializer +
      new TimeSerializer(Seconds) +
      new MassSerializer(Pounds) +
      new TimePriceSerializer(Hours)
  }

  behavior of "QuantitySerializer"

  import QuantitySerializerMarshaller._

  val seedValue = 10.22
  val seedValueInt = 10
  val jsonkW = "{\"value\":10.22,\"unit\":\"kW\"}"
  val jsonMWin = "{\"value\":10.22,\"unit\":\"MW\"}"
  val jsonMWout = "{\"value\":10220.0,\"unit\":\"kW\"}"
  val jsonGWin = "{\"value\":10.22,\"unit\":\"GW\"}"
  val jsonGWout = "{\"value\":1.022E+7,\"unit\":\"kW\"}"
  val jsonPowerInt = "{\"value\":10,\"unit\":\"kW\"}"

  val jsonMillisIn = "{\"value\":10.22,\"unit\":\"ms\"}"
  val jsonMillisOut = "{\"value\":0.01022,\"unit\":\"s\"}"
  val jsonSecondsIn = "{\"value\":10.22,\"unit\":\"s\"}"
  val jsonSecondsOut = "{\"value\":10.22,\"unit\":\"s\"}"
  val jsonMinutesIn = "{\"value\":10.22,\"unit\":\"m\"}"
  val jsonMinutesOut = "{\"value\":613.2,\"unit\":\"s\"}"
  val jsonHoursIn = "{\"value\":10.22,\"unit\":\"h\"}"
  val jsonHoursOut = "{\"value\":36792.0,\"unit\":\"s\"}"
  val jsonTimeInt = "{\"value\":10,\"unit\":\"s\"}"

  it must "serialize a Power value" in {
    val json = write[Power](Kilowatts(seedValue))
    json must be(jsonkW)
    val json2 = write[Power](Megawatts(seedValue))
    json2 must be(jsonMWout)
    val json3 = write[Power](Gigawatts(seedValue))
    json3 must be(jsonGWout)
  }

  it must "deserialize a Power value" in {
    val power = read[Power](jsonkW)
    power must be(Kilowatts(seedValue))
    val powerMW = read[Power](jsonMWin)
    powerMW must be(Megawatts(seedValue))
    val powerGW = read[Power](jsonGWin)
    powerGW must be(Gigawatts(seedValue))
    val powerInt = read[Power](jsonPowerInt)
    powerInt must be(Kilowatts(seedValueInt))
  }

  it must "serialize a Time value" in {
    val json = write[Time](Milliseconds(seedValue))
    json must be(jsonMillisOut)
    val json2 = write[Time](Seconds(seedValue))
    json2 must be(jsonSecondsOut)
    val json3 = write[Time](Minutes(seedValue))
    json3 must be(jsonMinutesOut)
    val json4 = write[Time](Hours(seedValue))
    json4 must be(jsonHoursOut)
  }

  it must "deserialize a Time value" in {
    val time = read[Time](jsonMillisIn)
    time must be(Milliseconds(seedValue))
    val time2 = read[Time](jsonSecondsIn)
    time2 must be(Seconds(seedValue))
    val time3 = read[Time](jsonMinutesIn)
    time3 must be(Minutes(seedValue))
    val time4 = read[Time](jsonHoursIn)
    time4 must be(Hours(seedValue))
    val time5 = read[Time](jsonTimeInt)
    time5 must be(Seconds(seedValueInt))
  }

  it must "serialize a Mass value" in {
    val json = write[Mass](Pounds(seedValue))
    println(json)
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
  val jsonTimePrice = "{\"amount\":10.22,\"currency\":\"USD\",\"per\":\"0.5 h\"}"

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
    println(USD(seedValue) / Pounds(1))
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
