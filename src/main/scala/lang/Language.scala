package lang

import vm.{RoundVM, Sensor}
import vm.Slot.{Branch, FoldHood, Nbr, Rep}

trait Language:
  type F[A]
  def rep[A](init: => F[A])(fun: F[A] => F[A]): F[A]

  def nbr[A](expr: => F[A]): F[A]

  def mid(): Int

  def foldhood[A](init: => A)(aggr: (A, A) => A)(expr: => A): A

  def branch[A](cond: => Boolean)(thn: => A)(els: => A): A

  def sense[A](name: Sensor): A

  def nbrVar[A](name: Sensor): A


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

  override def foldhood[A](init: => A)(aggr: (A, A) => A)(expr: => A): A =
    vm.nest(FoldHood(vm.index))(write = true){
      val nbrField: List[A] = vm
        .alignedNeighbours()
        .map(id => vm.foldedEval(expr)(id).getOrElse(vm.locally(init)))
      vm.isolate(nbrField.fold(vm.locally(init))((x,y) => aggr(x,y)))
    }

  override def branch[A](cond: => Boolean)(thn: => A)(els: => A): A =
    val tag: Boolean = vm.locally(cond)
    vm.nest(Branch(vm.index, tag))(write = vm.unlessFoldingOnOthers) {
      vm.neighbour match {
        case Some(nbr) if nbr != vm.self => vm.neighbourVal
        case _ => if (tag) vm.locally(thn) else vm.locally(els)
      }
    }

  override def sense[A](name: Sensor): A = vm.localSense(name)

  override def nbrVar[A](name: Sensor): A = vm.neighbourSense(name)
