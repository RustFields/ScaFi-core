package lang

trait Language:
  type F[A]
  def rep[A](init: => F[A])(fun: F[A] => F[A]): F[A]

  def nbr[A](expr: => F[A]): F[A]
