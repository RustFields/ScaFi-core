package functional.monad

import field.Fields
import lang.AuxiliaryConstructs

import scala.annotation.targetName

trait Monads:
  /**
   * Monad Type class
   *
   * @tparam F the type of the monad
   */
  trait Monad[F[_]]:
    def unit[A](a: => A): F[A]

    extension[A] (fa: F[A])
      def flatMap[B](f: A => F[B]): F[B]

      def map[B](f: A => B): F[B] =
        fa.flatMap(a => unit(f(a)))

      @targetName("mapWith")
      def >>[B](f: A => B): F[B] =
        fa.map(f)

      @targetName("flatMapWith")
      def >>=[B](f: A => F[B]): F[B] =
        fa.flatMap(f)