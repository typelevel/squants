package squants

// like Nat, but for integers
// Shamelessly borrowed from non https://gist.github.com/non/8396462
trait TypeLevelInt

// Pos means positive-or-zero, and Neg means negative-or-zero
trait Pos extends TypeLevelInt { type P <: Pos }
trait Neg extends TypeLevelInt { type N <: Neg }

class Zero extends Pos with Neg { type N = Zero }

case class PlusOne[P <: Pos]() extends Pos { type X = PlusOne[P] }
case class MinusOne[N <: Neg]() extends Neg { type X = MinusOne[N] }

object TypeLevelInt {

  type PlusTwo[A <: Pos] = PlusOne[PlusOne[A]]

  type _0 = Zero

  type _1 = PlusOne[_0]
  type _2 = PlusOne[_1]
  type _3 = PlusOne[_2]
  type _4 = PlusOne[_3]
  type _5 = PlusOne[_4]
  type _6 = PlusOne[_5]
  type _7 = PlusOne[_6]
  type _8 = PlusOne[_7]
  type _9 = PlusOne[_8]

  type _M1 = MinusOne[_0]
  type _M2 = MinusOne[_M1]
  type _M3 = MinusOne[_M2]
  type _M4 = MinusOne[_M3]
  type _M5 = MinusOne[_M4]
  type _M6 = MinusOne[_M5]
  type _M7 = MinusOne[_M6]
  type _M8 = MinusOne[_M7]
  type _M9 = MinusOne[_M8]

  // C is the sum of A and B
  trait Sum[A <: TypeLevelInt, B <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Sum {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit sum: Sum[A, B]): Aux[A, B, sum.Out] = sum
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt] = Sum[A, B] { type Out = C }

    //implicit def plusZero[A <: Z]: Aux[A, Zero, A] = new Sum[A, Zero] { type Out = A }
    implicit def zeroPlus[B <: TypeLevelInt]: Aux[Zero, B, B] = new Sum[Zero, B] { type Out = B }

    implicit def posPlusPos[A <: Pos, B <: Pos]
    (implicit sum: Sum[A, PlusOne[B]]): Aux[PlusOne[A], B, sum.Out] = new Sum[PlusOne[A], B] { type Out = sum.Out }
    implicit def negPlusNeg[A <: Neg, B <: Neg]
    (implicit sum: Sum[A, MinusOne[B]]): Aux[MinusOne[A], B, sum.Out] = new Sum[MinusOne[A], B] { type Out = sum.Out }
    implicit def posPlusNeg[A <: Pos, B <: Neg]
    (implicit sum: Sum[A, B]): Aux[PlusOne[A], MinusOne[B], sum.Out] = new Sum[PlusOne[A], MinusOne[B]] { type Out = sum.Out }
    implicit def negPlusPos[A <: Neg, B <: Pos]
    (implicit sum: Sum[A, B]): Aux[MinusOne[A], PlusOne[B], sum.Out] = new Sum[MinusOne[A], PlusOne[B]] { type Out = sum.Out }
  }

  // C is the difference of A and B
  trait Diff[A <: TypeLevelInt, B <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Diff {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit diff: Diff[A, B]): Aux[A, B, diff.Out] = diff
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt] = Diff[A, B] { type Out = C }

    implicit def minusZero[A <: TypeLevelInt]: Aux[A, Zero, A] = new Diff[A, Zero] { type Out = A }

    implicit def posMinusPos[A <: Pos, B <: Pos](implicit diff: Diff[A, B]): Aux[PlusOne[A], PlusOne[B], diff.Out] =
      new Diff[PlusOne[A], PlusOne[B]] { type Out = diff.Out }
    implicit def negMinusNeg[A <: Neg, B <: Neg](implicit diff: Diff[A, B]): Aux[MinusOne[A], MinusOne[B], diff.Out] =
      new Diff[MinusOne[A], MinusOne[B]] { type Out = diff.Out }
    implicit def posMinusNeg[A <: Pos, B <: Neg](implicit diff: Diff[PlusOne[A], B]): Aux[A, MinusOne[B], diff.Out] =
      new Diff[A, MinusOne[B]] { type Out = diff.Out }
    implicit def negMinusPos[A <: Neg, B <: Pos](implicit diff: Diff[MinusOne[A], B]): Aux[A, PlusOne[B], diff.Out] =
      new Diff[A, PlusOne[B]] { type Out = diff.Out }
  }

  // B is the negation of A
  trait Negate[A <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Negate {
    def apply[A <: TypeLevelInt](implicit n: Negate[A]): Aux[A, n.Out] = n
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt] = Negate[A] { type Out = B }
    implicit def int[A <: TypeLevelInt](implicit diff: Diff[Zero, A]): Aux[A, diff.Out] =
      new Negate[A] { type Out = diff.Out }
  }

  // C is the product of A and B
  trait Prod[A <: TypeLevelInt, B <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Prod {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit prod: Prod[A, B]): Aux[A, B, prod.Out] = prod
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt] = Prod[A, B] { type Out = C }

    implicit def timesZero[A <: TypeLevelInt]: Aux[A, Zero, Zero] = new Prod[A, Zero] { type Out = Zero }
    implicit def zeroTimes[B <: TypeLevelInt]: Aux[Zero, B, Zero] = new Prod[Zero, B] { type Out = Zero }

    implicit def posTimes[A <: Pos, B <: TypeLevelInt, C <: TypeLevelInt]
    (implicit prod: Prod.Aux[A, B, C], sum: Sum[C, B]): Aux[PlusOne[A], B, sum.Out] =
      new Prod[PlusOne[A], B] { type Out = sum.Out }

    implicit def negTimes[A <: Neg, B <: TypeLevelInt, C <: TypeLevelInt]
    (implicit prod: Prod.Aux[A, B, C], diff: Diff[C, B]): Aux[MinusOne[A], B, diff.Out] =
      new Prod[MinusOne[A], B] { type Out = diff.Out }
  }

  trait Quot[A <: TypeLevelInt, B <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Quot {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit div: Quot[A, B]): Aux[A, B, div.Out] = div
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt] = Quot[A, B] { type Out = C }

    // zero / pos,neg
    implicit def zeroDivPos[A <: Pos]: Aux[Zero, PlusOne[A], Zero] =
      new Quot[Zero, PlusOne[A]] { type Out = Zero }
    implicit def zeroDivNeg[A <: Neg]: Aux[Zero, MinusOne[A], Zero] =
      new Quot[Zero, MinusOne[A]] { type Out = Zero }

    // pos / pos
    implicit def posDivPos1[A <: Pos, B <: Pos]
    (implicit lt: LT[A, B]): Aux[PlusOne[A], PlusOne[B], Zero] =
      new Quot[PlusOne[A], PlusOne[B]] { type Out = Zero }
    implicit def posDivPosN[A <: Pos, B <: Pos, C <: Pos, D <: Pos]
    (implicit diff: Diff.Aux[PlusOne[A], B, C], div: Quot.Aux[C, B, D]): Aux[PlusOne[A], B, PlusOne[D]] =
      new Quot[PlusOne[A], B] { type Out = PlusOne[D] }

    // neg / pos
    implicit def negDivPos1[A <: Neg, B <: Pos, C <: Pos]
    (implicit na: Negate.Aux[A, C], lt: LT[C, B]): Aux[MinusOne[A], PlusOne[B], Zero] =
      new Quot[MinusOne[A], PlusOne[B]] { type Out = Zero }
    implicit def negDivPosN[A <: Neg, B <: Pos, C <: Neg, D <: Neg]
    (implicit sum: Sum.Aux[MinusOne[A], B, C], div: Quot.Aux[C, B, D]): Aux[MinusOne[A], B, MinusOne[D]] =
      new Quot[MinusOne[A], B] { type Out = MinusOne[D] }

    // pos / neg
    implicit def posDivNeg1[A <: Pos, B <: Neg, C <: Pos]
    (implicit nb: Negate.Aux[B, C], lt: LT[A, C]): Aux[PlusOne[A], MinusOne[B], Zero] =
      new Quot[PlusOne[A], MinusOne[B]] { type Out = Zero }
    implicit def posDivNegN[A <: Pos, B <: Neg, C <: Pos, D <: Neg]
    (implicit diff: Sum.Aux[PlusOne[A], B, C], div: Quot.Aux[C, B, D]): Aux[PlusOne[A], B, MinusOne[D]] =
      new Quot[PlusOne[A], B] { type Out = MinusOne[D] }

    // neg / neg
    implicit def negDivNeg1[A <: Neg, B <: Neg]
    (implicit lt: LT[B, A]): Aux[MinusOne[A], MinusOne[B], Zero] =
      new Quot[MinusOne[A], MinusOne[B]] { type Out = Zero }
    implicit def negDivNegN[A <: Neg, B <: Neg, C <: Neg, D <: Pos]
    (implicit diff: Diff.Aux[MinusOne[A], B, C], div: Quot.Aux[C, B, D]): Aux[MinusOne[A], B, PlusOne[D]] =
      new Quot[MinusOne[A], B] { type Out = PlusOne[D] }
  }

  // Out is the remainder of A divided by B
  trait Mod[A <: TypeLevelInt, B <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Mod {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit mod: Mod[A, B]): Aux[A, B, mod.Out] = mod
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt] = Mod[A, B] { type Out = C }
    implicit def modAux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt, D <: TypeLevelInt, E <: TypeLevelInt]
    (implicit div: Quot.Aux[A, B, C], prod: Prod.Aux[C, B, D], diff: Diff.Aux[A, D, E]): Aux[A, B, E] =
      new Mod[A, B] { type Out = E }
  }

  // Out is the GCD of A and B (currently broken when there is no gcd)
  trait Gcd[A <: TypeLevelInt, B <: TypeLevelInt] { type Out <: TypeLevelInt }
  object Gcd {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit gcd: Gcd[A, B]): Aux[A, B, gcd.Out] = gcd
    type Aux[A <: TypeLevelInt, B <: TypeLevelInt, C <: TypeLevelInt] = Gcd[A, B] { type Out = C }

    implicit def gcdZero[A <: Pos]: Aux[PlusOne[A], Zero, PlusOne[A]] =
      new Gcd[PlusOne[A], Zero] { type Out = PlusOne[A] }

    implicit def gcdN[A <: Pos, B <: Pos, C <: Pos, D <: Pos]
    (implicit gcd: Aux[PlusOne[B], D, C], mod: Mod.Aux[PlusOne[A], PlusOne[B], D]): Aux[PlusOne[A], PlusOne[B], C] =
      new Gcd[PlusOne[A], PlusOne[B]] { type Out = C }
  }

  // A is less than B
  trait LT[A <: TypeLevelInt, B <: TypeLevelInt]
  object LT {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit lt: A < B) = lt
    type <[A <: TypeLevelInt, B <: TypeLevelInt] = LT[A, B]

    implicit def lt1[B <: Pos] = new <[Zero, PlusOne[B]] {}
    implicit def lt2[A <: Neg] = new <[MinusOne[A], Zero] {}
    implicit def lt3[A <: Neg, B <: Pos] = new <[MinusOne[A], PlusOne[B]] {}
    implicit def lt4[A <: Pos, B <: Pos](implicit lt : A < B) = new <[PlusOne[A], PlusOne[B]] {}
    implicit def lt5[A <: Neg, B <: Neg](implicit lt : A < B) = new <[MinusOne[A], MinusOne[B]] {}

  }

  // A is less-than-or-equal-to B
  trait LTEq[A <: TypeLevelInt, B <: TypeLevelInt]
  object LTEq {
    def apply[A <: TypeLevelInt, B <: TypeLevelInt](implicit lteq: A <= B) = lteq
    type <=[A <: TypeLevelInt, B <: TypeLevelInt] = LTEq[A, B]

    implicit def ltEq1 = new <=[Zero, Zero] {}
    implicit def ltEq2[B <: Pos] = new <=[Zero, PlusOne[B]] {}
    implicit def ltEq3[A <: Neg] = new <=[MinusOne[A], Zero] {}
    implicit def ltEq4[A <: Pos, B <: Pos](implicit lteq : A <= B) = new <=[PlusOne[A], PlusOne[B]] {}
    implicit def ltEq5[A <: Neg, B <: Neg](implicit lteq : A <= B) = new <=[MinusOne[A], MinusOne[B]] {}
  }

  // B is the absolute value of A
  trait Abs[A <: TypeLevelInt] { type Out <: Pos }
  object Abs {
    def apply[A <: TypeLevelInt](implicit abs: Abs[A]): Aux[A, abs.Out] = abs
    type Aux[A <: TypeLevelInt, B <: Pos] = Abs[A] { type Out = B }

    implicit def posAbs[A <: Pos]: Aux[A, A] = new Abs[A] { type Out = A }

    implicit def negAbs[A <: Neg, B <: Pos](implicit abs: Abs.Aux[A, B]): Aux[MinusOne[A], PlusOne[B]] =
      new Abs[MinusOne[A]] { type Out = PlusOne[abs.Out] }
  }

  trait ToInt[N <: TypeLevelInt] { def apply(): Int }
  object ToInt {
    def apply[N <: TypeLevelInt](implicit toInt: ToInt[N]): ToInt[N] = toInt

    implicit val toIntZero = new ToInt[Zero] { def apply() = 0 }

    implicit def toIntPos[N <: Pos](implicit toIntN: ToInt[N]) =
      new ToInt[PlusOne[N]] { def apply() = toIntN() + 1 }

    implicit def toIntNeg[N <: Neg](implicit toIntN: ToInt[N]) =
      new ToInt[MinusOne[N]] { def apply() = toIntN() - 1 }
  }

  val Zero: Zero = new Zero
  val One: PlusOne[Zero] = new PlusOne[Zero]
  val MinusOne: MinusOne[Zero] = new MinusOne[Zero]
}
