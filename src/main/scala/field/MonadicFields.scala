package field

import functional.monad.Monads
import lang.AuxiliaryConstructs

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


