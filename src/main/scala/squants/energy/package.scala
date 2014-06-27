/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.electro.Coulombs

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
package object energy {

  /* Known Quantities */
  lazy val ElementaryConstant = Coulombs(BigDecimal("1.602176565E-19"))
}
