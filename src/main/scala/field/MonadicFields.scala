package field

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
  given[A]: Conversion[A => A, Field[A => A]] with
    override def apply(x: A => A): Field[A => A] = Field.lift(x)

  def combineFields[A: Monoid](f1: Field[A], f2: Field[A]): Field[A] =
    for
      v1 <- f1
      v2 <- f2
    yield v1 |+| v2

  def combineFields[A](f1: Field[A], f2: Field[A => A]): Field[A] =
    for
      v1 <- f1
      v2 <- f2
    yield v2(v1)

  def applyToAll[A](f: Field[A], func: A => A): Field[A] =
    combineFields(f, func)