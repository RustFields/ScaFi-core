package lang

import field.Fields.DeviceId
import vm.RoundVM
import vm.Slot.{Nbr, Rep}

trait Language:
  type F[A]
  def rep[A](init: => F[A])(fun: F[A] => F[A]): F[A]

  def nbr[A](expr: => F[A]): F[A]

  def mid(): Int


trait LangImpl extends Language:
  def vm: RoundVM

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

  override def mid(): Int = vm.self