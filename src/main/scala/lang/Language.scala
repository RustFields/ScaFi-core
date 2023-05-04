package lang

import field.Field

trait Language:
  type F[A]
  def rep[A](init: => F[A])(fun: F[A] => F[A]): F[A]