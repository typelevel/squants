/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
package object thermal {

  lazy val AbsoluteZero = Kelvin(0)
  lazy val FreezingTemperatureWater = Celsius(0)
  lazy val BoilingTemperatureWater = Celsius(100)

}
