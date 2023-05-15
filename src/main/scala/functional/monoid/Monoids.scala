package functional.monoid

import cats.kernel.Monoid

import scala.annotation.targetName

object Monoids:

  extension [A: Monoid](a1: A)
    @targetName("combineWith")
    def |+|(a2: A): A = summon[Monoid[A]].combine(a1, a2)

  given Monoid[Int] with
    def combine(a1: Int, a2: Int): Int = a1 + a2
    def empty: Int = 0

  given Monoid[String] with
    def combine(a1: String, a2: String): String = a1 + a2
    def empty: String = ""

  given[A]: Monoid[List[A]] with
    def combine(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
    def empty: List[A] = Nil

  given[A]: Monoid[Set[A]] with
    def combine(a1: Set[A], a2: Set[A]): Set[A] = a1 ++ a2
    def empty: Set[A] = Set.empty