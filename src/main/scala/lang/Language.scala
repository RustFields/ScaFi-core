package lang

import vm.{RoundVM, Sensor}
import vm.Slot.{Branch, FoldHood, Nbr, Rep}

trait Language:
  /**
   * Iteratively updates the value of the input expression at each device using the last computed value.
   * @param init the initial value
   * @param fun the function to apply to the value
   * @tparam A
   * @return the updated value
   */
  def rep[A](init: => A)(fun: A => A): A

  /**
   * Observes the value of an expression across neighbors, producing a “field of fields”.
   * @param expr the expression to evaluate
   * @tparam A
   * @return the value of the expression
   */
  def nbr[A](expr: => A): A

  /**
   * The id of the local device
   * @return the ID as Int
   */
  def mid(): Int

  /**
   * Aggregates the results of the neighbor computation.
   * @param init The initial value for the aggregation.
   * @param aggr The aggregation function to combine values.
   * @param expr The expression to evaluate for each neighbor.
   * @tparam A
   * @return The final result of the aggregation.
   */
  def foldhood[A](init: => A)(aggr: (A, A) => A)(expr: => A): A

  /**
   * Partition the domain into two subspaces that do not interact with each others.
   * @param cond the condition for the partition
   * @param thn the function to apply if the condition is satisfied
   * @param els the function to apply if the condition is *not* satisfied
   * @tparam A
   * @return the value of the function executed based on the condition
   */
  def branch[A](cond: => Boolean)(thn: => A)(els: => A): A

  /**
   * Obtain the value of a local sensor.
   * @param name the name of the sensor
   * @tparam A
   * @return the value of the local sensor
   */
  def sense[A](name: Sensor): A

  /**
   * Obtain the value of the neighbor's sensor.
   * @param name the name of the sensor
   * @tparam A
   * @return the value of the neighbor's sensor
   */
  def nbrVar[A](name: Sensor): A


trait LangImpl extends Language:
  def vm: RoundVM

  override def rep[A](init: => A)(fun: A => A): A =
    vm.nest(Rep(vm.index))(write = vm.unlessFoldingOnOthers) {
      vm.locally {
        fun(vm.previousRoundVal.getOrElse(init))
      }
    }

  override def nbr[A](expr: => A): A =
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
