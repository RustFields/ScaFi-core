package field.monad

import field.Fields
import lang.AuxiliaryConstructs

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


trait MonadicFields extends Monads with Fields with AuxiliaryConstructs:
  /**
   * Field Monad instance
   */
  given Monad[Field] with
    def unit[A](a: => A): Field[A] =
      Field.lift(a)

    extension[A] (fa: Field[A])
      def flatMap[B](f: A => Field[B]): Field[B] =
        Field(fa.getMap.map { case (id, a) => id -> {
          val b = f(a)
          b.getMap.getOrElse(id, b.default)
        }
        }, f(fa.default).default)