
import squants.market._
import squants.mass.MassConversions._
import squants.mass.Pounds
import squants.time.TimeConversions._
import squants.market.MoneyConversions._

implicit val moneyContext =
  MoneyContext(
    defaultCurrency = USD,
    currencies = defaultCurrencySet,
    rates = List(
      JPY(102.59) toThe USD(1),
      CAD(1.1034) toThe USD(1),
      USD(1.6828) toThe GBP(1))
  )

val usageRate = 10.kilograms / hour
val duration = 3.hours + 15.minutes
val price = JPY(234.45) / kilogram
val usPrice = price in USD
val cadPrice = price in CAD
val cost = price * (usageRate * duration)
val usdCost = cost in USD
val cadCost = cost in CAD
val gbpCost = cost in GBP
val usage = usageRate * duration
val lbUsage = usage toString Pounds

val p = 10.dollars / pound

