package lang

import vm.RoundVM
import vm.Slot.{Nbr, Rep}

trait Language:
  type F[A]
  def rep[A](init: => F[A])(fun: F[A] => F[A]): F[A]

  def nbr[A](expr: => F[A]): F[A]


trait LangImpl extends Language:
  val vm: RoundVM

  override def rep[A](init: => F[A])(fun: F[A] => F[A]): F[A] =
    vm.nest(Rep(vm.index))(write = vm.unlessFoldingOnOthers) {
      vm.locally {
        fun(vm.previousRoundVal.getOrElse(init))
      }
    }

  override def nbr[A](expr: => F[A]): F[A] =
    vm.nest(Nbr(vm.index))(write = vm.onlyWhenFoldingOnSelf) {
      vm.neighbour match {
        case Some(nbr) if nbr != vm.self => vm.neighbourVal
        case _ => expr
      }
    }