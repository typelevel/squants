/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

import squants.market.MoneyConversions._
import squants.motion.{ GallonsPerDay, VolumeFlow }
import squants.space.UsGallons
import squants.space.VolumeConversions._
import squants.time.TimeConversions._

case class Brady(name: String, milkConsumption: VolumeFlow)

// Given
val theBradyBunch = List(
  Brady("Mike  ", 0.25.cups / day),
  Brady("Carol ", 0.5.cups / day),
  Brady("Greg  ", 1.gallons / day),
  Brady("Peter ", 3.5.quarts / day),
  Brady("Bobby ", 2.25.quarts / day),
  Brady("Marcia", 1.4.quarts / day),
  Brady("Jan   ", 1.cups / day),
  Brady("Cindy ", 0.quarts / day),
  Brady("Alice ", 0.5.cups / day),
  Brady("Sam   ", 0.cups / day))
val milkPrice = 1.50.USD / gallon

// Calculate Results
val totalMilkConsumption = theBradyBunch.map(_.milkConsumption).sum

val usageInOneWeek = totalMilkConsumption * 7.days
val weeklyMilkBudget = milkPrice * usageInOneWeek

// Display Results
println("Brady Bunch Milk Budget")
val format = "%.2f"
theBradyBunch.map(b => s"${b.name} - ${b.milkConsumption.toString(GallonsPerDay, format)}").foreach(println)
println(s"      Milk Price: ${milkPrice.toString(UsGallons)}")
println(s" Total Milk Flow: ${totalMilkConsumption.toString(GallonsPerDay, format)}")
println(s" Usage in 1 week: ${usageInOneWeek.toString(UsGallons, format)}")
println(s"1 wk Milk Budget: $weeklyMilkBudget")
