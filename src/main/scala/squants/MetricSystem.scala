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
  lazy val Yocto = .001 * Zepto
  lazy val Zepto = .001 * Atto
  lazy val Atto = .001 * Femto
  lazy val Femto = .001 * Pico
  lazy val Pico = .001 * Nano
  lazy val Nano = .001 * Micro
  lazy val Micro = .001 * Milli
  lazy val Milli = .001
  lazy val Centi = .01
  lazy val Deci = .1

  lazy val Deca = 10d
  lazy val Hecto = 100d
  lazy val Kilo = 1000d
  lazy val Mega = 1000d * Kilo
  lazy val Giga = 1000d * Mega
  lazy val Tera = 1000d * Giga
  lazy val Peta = 1000d * Tera
  lazy val Exa = 1000d * Peta
  lazy val Zetta = 1000d * Exa
  lazy val Yotta = 1000d * Zetta
}
