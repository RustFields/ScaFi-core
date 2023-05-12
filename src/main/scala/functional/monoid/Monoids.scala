package functional.monoid

object Monoids:
  trait Monoid[A]:
    def op(a1: A, a2: A): A
    def zero: A

  extension [A: Monoid](a1: A)
    def |+|(a2: A): A = summon[Monoid[A]].op(a1, a2)

  given Monoid[Int] with
    def op(a1: Int, a2: Int): Int = a1 + a2
    def zero: Int = 0

  given Monoid[String] with
    def op(a1: String, a2: String): String = a1 + a2
    def zero: String = ""

  given[A]: Monoid[List[A]] with
    def op(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
    def zero: List[A] = Nil

  given[A]: Monoid[Set[A]] with
    def op(a1: Set[A], a2: Set[A]): Set[A] = a1 ++ a2
    def zero: Set[A] = Set.empty