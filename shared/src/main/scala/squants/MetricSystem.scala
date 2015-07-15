/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * Singleton defining Metric System multipliers
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object MetricSystem {
  lazy val Yocto = 1e-24
  lazy val Zepto = 1e-21
  lazy val Atto = 1e-18
  lazy val Femto = 1e-15
  lazy val Pico = 1e-12
  lazy val Nano = 1e-9
  lazy val Micro = 1e-6
  lazy val Milli = 1e-3
  lazy val Centi = 1e-2
  lazy val Deci = 1e-1

  lazy val Deca = 1e1
  lazy val Hecto = 1e2
  lazy val Kilo = 1e3
  lazy val Mega = 1e6
  lazy val Giga = 1e9
  lazy val Tera = 1e12
  lazy val Peta = 1e15
  lazy val Exa = 1e18
  lazy val Zetta = 1e21
  lazy val Yotta = 1e24
}
