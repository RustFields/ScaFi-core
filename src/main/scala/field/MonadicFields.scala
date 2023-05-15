package field

import cats.kernel.Monoid
import functional.monad.Monads
import functional.monoid.Monoids.*
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


trait FieldOps extends MonadicFields:
  given[A, B]: Conversion[A => B, Field[A => B]] with
    override def apply(x: A => B): Field[A => B] = Field.lift(x)
    
  def combineFields[A, B, C](f1: Field[A], f2: Field[B])(func: (A, B) => C): Field[C] =
    for
      v1 <- f1
      v2 <- f2
    yield func(v1, v2)

  def applyFunctionField[A, B](f1: Field[A], f2: Field[A => B]): Field[B] =
    for
      v1 <- f1
      v2 <- f2
    yield v2(v1)

  def applyToAll[A, B](f: Field[A], func: A => B): Field[B] =
    applyFunctionField(f, func)

  def mappendFields[A: Monoid](f1: Field[A], f2: Field[A]): Field[A] =
    combineFields(f1, f2)(_ |+| _)