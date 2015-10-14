/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * The experimental package is a replica of the main framework.
 *
 * All class, traits and objects names have an X appended.
 *
 * The purpose of this package is to experiment with various design changes
 * before implementing in the main framework.
 *
 */
package object experimental {

//  type QuantitySeries[A <: Quantity[A, _]] = IndexedSeq[QuantityRange[A, _]]

  /**
   * Helper function to achieve uniform Double formatting over JVM and JS platforms.
   * Simple Double.toString will format 1.0 as "1.0" on JVM and as "1" on JS
   * @param d Double number to be formatted
   * @return
   */
  private[squants] def crossFormat[N](d: N)(implicit sqNum: SquantsNumeric[N]): String = if (d == sqNum.toLong(d)) { "%.1f".format(sqNum.toDouble(d)) } else { d.toString }

}
