package lang

import lang.Builtins.{Bounded, PartialOrderingWithGLB}

/**
 * This trait defines a component that extends LanguageStructure and requires to be "attached" to Core
 * It defines a trait with additional language mechanisms, in the form of certain builtins, and Ordering implicits
 */
trait Builtins:
  self: Language =>
  import Builtins.given

  def mux[A](cond: Boolean)(th: A)(el: A): A = if (cond) th else el

  def minHoodLoc[A](default: A)(expr: => A)(using partial: PartialOrderingWithGLB[A]): A =
   foldhood(default)((x,y) => if (partial.equiv(x, y)) partial.gle(x, y) else if (partial.lt(x, y)) x else y)(expr)

  def minHood[A](expr: => A)(using of: Bounded[A]): A = foldhood[A](of.top)((x, y) => of.min(x, y))(expr)

  def maxHood[A](expr: => A)(using of: Bounded[A]): A = foldhood[A](of.bottom)((x, y) => of.max(x, y))(expr)

  def foldhoodPlus[A](init: => A)(aggr: (A, A) => A)(expr: => A): A = foldhood(init)(aggr)(mux(mid() == nbr(mid()))(init)(expr))

  def minHoodPlus[A](expr: => A)(using of: Bounded[A]): A = foldhoodPlus[A](of.top)((x, y) => of.min(x, y)){expr}

  def maxHoodPlus[A](expr: => A)(using of: Bounded[A]): A = foldhoodPlus[A](of.bottom)((x, y) => of.max(x, y)){expr}

object Builtins extends Serializable:
  trait Ordered[A]:
    def compare(a: A, b: A): Int
    def same(a: A, b: A): Boolean = compare(a, b) == 0
    def min(a: A, b: A): A = if (compare(a, b) <= 0) a else b
    def max(a: A, b: A): A = if (compare(a, b) > 0) a else b

  trait LowerBounded[A] extends Ordered[A]:
    def bottom: A

  trait UpperBounded[A] extends Ordered[A]:
    def top: A

  trait Bounded[A] extends LowerBounded[A] with UpperBounded[A]

  object Bounded extends Serializable:
    given Bounded[Int] with
      override def top: Int = Int.MaxValue
      override def bottom: Int = Int.MinValue
      override def compare(a: Int, b: Int): Int = a.compareTo(b)

    given Bounded[Double] with
      override def top: Double = Double.PositiveInfinity
      override def bottom: Double = Double.NegativeInfinity
      override def compare(a: Double, b: Double): Int = (a - b).toInt

    given Bounded[String] with
      override def top: String = "Z"
      override def bottom: String = "A"
      override def compare(a: String, b: String): Int = if (a > b) 1 else if (b < a) -1 else 0

    given [T: Bounded]: Bounded[() => T] with
      private val oft: Bounded[T] = implicitly[Bounded[T]]
      override def top: () => T = () => oft.top
      override def bottom: () => T = () => oft.bottom
      override def compare(a: () => T, b: () => T): Int = oft.compare(a(), b())

    given tupleBounded[T1, T2](using
                   of1: Bounded[T1],
                   of2: Bounded[T2]
    ): Bounded[(T1, T2)] with
      override def top: (T1, T2) = (of1.top, of2.top)
      override def bottom: (T1, T2) = (of1.bottom, of2.bottom)
      override def compare(a: (T1, T2), b: (T1, T2)): Int = if (of1.compare(a._1, b._1) == 0) of2.compare(a._2, b._2) else of1.compare(a._1, b._1)

    given [T1, T2, T3, T4](using
                           of1: Bounded[T1],
                           of2: Bounded[T2],
                           of3: Bounded[T3],
                           of4: Bounded[T4]
    ): Bounded[(T1, T2, T3, T4)] with
      override def top: (T1, T2, T3, T4) = (of1.top, of2.top, of3.top, of4.top)
      override def bottom: (T1, T2, T3, T4) = (of1.bottom, of2.bottom, of3.top, of4.top)
      override def compare(a: (T1, T2, T3, T4), b: (T1, T2, T3, T4)): Int =
        List(of1.compare(a._1, b._1), of2.compare(a._2, b._2), of3.compare(a._3, b._3), of4.compare(a._4, b._4))
          .filter(_ != 0)
          .lift(0)
          .getOrElse(0)

    given tupleOnFirstBounded[T1, T2](using
                   of1: Bounded[T1],
                   of2: Defaultable[T2]
    ): Bounded[(T1, T2)] with
      override def top: (T1, T2) = (of1.top, of2.default)
      override def bottom: (T1, T2) = (of1.bottom, of2.default)
      override def compare(a: (T1, T2), b: (T1, T2)): Int = of1.compare(a._1, b._1)

    given [T](using ev: Bounded[T]): Bounded[List[T]] with
      override def top: List[T] = List()
      override def bottom: List[T] = List()
      override def compare(a: List[T], b: List[T]): Int =
        a.zip(b)
          .dropWhile { case (x, y) => ev.compare(x, y) == 0 }
          .headOption
          .map { case (x, y) => ev.compare(x, y) }
          .getOrElse(-1)


  trait  PartialOrderingWithGLB[T] extends PartialOrdering[T]:
    def gle(x: T, y: T): T

  object PartialOrderingWithGLB:
    given [T](using b: Bounded[T]): PartialOrderingWithGLB[T] with
      override def gle(x: T, y: T): T = b.min(x, y)
      override def tryCompare(x: T, y: T): Option[Int] = Some(b.compare(x, y))
      override def lteq(x: T, y: T): Boolean = b.compare(x, y) <= 0

    given PartialOrderingWithGLB[Double] with
      override def gle(x: Double, y: Double): Double = Math.min(x, y)
      override def tryCompare(x: Double, y: Double): Option[Int] = None
      override def lteq(x: Double, y: Double): Boolean = x <= y

    given [T1, T2](using
                   p1: PartialOrderingWithGLB[T1],
                   p2: PartialOrderingWithGLB[T2]
    ): PartialOrderingWithGLB[(T1, T2)] with
      override def gle(x: (T1, T2), y: (T1, T2)): (T1, T2) = (p1.gle(x._1, y._1), p2.gle(x._2, y._2))
      override def tryCompare(x: (T1, T2), y: (T1, T2)): Option[Int] = None
      override def lteq(x: (T1, T2), y: (T1, T2)): Boolean = p1.lteq(x._1, y._1) && p2.lteq(x._2, y._2)

  trait Defaultable[T]:
    def default: T

  object Defaultable:
    def apply[T](defaultVal: T): Defaultable[T] = new Defaultable[T]:
      override def default: T = defaultVal