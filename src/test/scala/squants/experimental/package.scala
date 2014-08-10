/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
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

  type QuantitySeriesX[A <: QuantityX[A]] = IndexedSeq[QuantityRangeX[A]]

}
