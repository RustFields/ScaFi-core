package io.github.rustfields.functional

import io.github.rustfields.lang.FieldCalculusInterpreter
import io.github.rustfields.vm.{Context, Export, Sensor}

import scala.collection.mutable

trait CoreTestUtils:
  def ctx(
           selfId: Int,
           exports: Map[Int, Export] = Map(),
           lsens: Map[String, Any] = Map(),
           nbsens: Map[String, Map[Int, Any]] = Map(),
         ): Context =
    val localSensorsWithId = lsens.map((k, v) => Sensor(k) -> v)
    val neighborhoodSensorWithId = nbsens.map((k, v) => Sensor(k) -> v)
    Context(selfId, exports, localSensorsWithId, neighborhoodSensorWithId)

  def assertEquivalence[T](
                            nbrs: Map[Int, List[Int]], 
                            execOrder: Iterable[Int], 
                            comparer: (T, T) => Boolean = (_: Any) == (_: Any),
                          )(program1: => Any)(program2: => Any)(using interpreter: FieldCalculusInterpreter): Boolean =
    val states = mutable.Map[Int, (Export, Export)]()
    execOrder.foreach { curr =>
      val nbrExports = states.view.filterKeys(nbrs(curr).contains(_))
      val currCtx1 = ctx(curr, exports = nbrExports.mapValues(_._1).toMap)
      val currCtx2 = ctx(curr, exports = nbrExports.mapValues(_._2).toMap)
      val exp1 = interpreter.round(currCtx1, program1)
      val exp2 = interpreter.round(currCtx2, program2)
      if (!comparer(exp1.root(), exp2.root()))
        throw new Exception(s"Not equivalent: \n$exp1\n$currCtx1\n--------\n$exp2\n$currCtx2")
      states.put(curr, (exp1, exp2))
    }
    true

  def fullyConnectedTopologyMap(elems: Iterable[Int]): Map[Int, List[Int]] =
    elems.map(elem => elem -> elems.toList).toMap

object CoreTestUtils extends CoreTestUtils