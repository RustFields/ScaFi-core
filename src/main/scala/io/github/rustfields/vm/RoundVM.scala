package io.github.rustfields.vm

import RoundVM.*

import scala.util.control.Exception.handling

trait RoundVM:
  /**
   * The context of the current round
   * @return the context
   */
  def context: Context

  /**
   * The status of the current round
   * @return the status
   */
  def status: VMStatus

  /**
   * The exports of the context.
   * @return the list of the current exports.
   */
  def exports: List[Export]

  /**
   * The first export of the stack
   * @return the export
   */
  def exportData: Export = exports.head

  /**
   * If the computation is folding on a neighbor, get the id of the neighbor
   * @return the id
   */
  def neighbour: Option[Int] = status.neighbour

  /**
   * The index of the current computation
   * @return the index
   */
  def index: Int = status.index


  /**
   * The list of exports on the stack
   * @return a list of exports
   */
  def exportStack: List[Export]

  /**
   * The ID of the context device.
   * @return the ID
   */
  def self: Int =
    context.selfID

  /**
   * Register a value in the root of the export stack
   * @param v the value to register
   */
  def registerRoot(v: Any): Unit =
    exportData.put(Path.empty(), v)

  /**
   * Obtain the value corresponding to the actual path from the context export.
   * @tparam A
   * @return the value if it exists
   */
  def previousRoundVal[A]: Option[A] =
    context.readExportValue[A](self, status.path)

  /**
   * Obtain the value corresponding to the actual path from the neighbour.
   * or throw an exception if the neighbour is unknown.
   * @tparam A
   * @return
   */
  def neighbourVal[A]: A = context
    .readExportValue[A](neighbour.get, status.path)
    .getOrElse(throw OutOfDomainException(context.selfID, neighbour.get, status.path))

  /**
   * Get the value of a given sensor from the actual context.
   * Or throw an exception if the sensor is unknown.
   * @param name the name of the sensor
   * @tparam A
   * @return the value if it exists
   */
  def localSense[A](name: Sensor): A = context
    .localSense[A](name)
    .getOrElse(throw SensorUnknownException(self, name))

  /**
   * Get the value of a given sensor from the neighbour.
   * Or throw an exception if the sensor is unknown.
   * @param name the name of the sensor
   * @tparam A
   * @return the value if it exists
   */
  def neighbourSense[A](name: Sensor): A = {
    RoundVM.ensure(neighbour.isDefined, "Neighbouring sensor must be queried in a nbr-dependent context.")
    context
      .nbrSense(name)(neighbour.get)
      .getOrElse(throw NbrSensorUnknownException(self, name, neighbour.get))
  }

  /**
   * Perform a folded evaluation of the given expression in the given neighbor and return the result.
   * @param expr The expression to evaluate, which should return a value of type `A`.
   * @param id The id of the neighbor.. It is of type `i32`.
   * @tparam A
   * @return An `Option` containing the result of the expression.
   */
  def foldedEval[A](expr: => A)(id: Int): Option[A]

  /**
   * Nest the current status, execute the given expression, and return the result.
   *
   * This function updates the status by pushing a nested slot, and
   * evaluates the provided expression. The result of the expression is returned after restoring
   * the status to its previous state.
   *
   * @param slot The slot to nest in the current status.
   * @param write A boolean flag indicating whether to perform a write operation.
   * @param inc A boolean flag indicating whether to increment the index after nesting.
   * @param expr The expression to evaluate, which should return a value of type `A`.
   * @tparam A
   * @return the result of the expression
   */
  def nest[A](slot: Slot)(write: Boolean, inc: Boolean = true)(expr: => A): A


  /**
   * Evaluates the given expression locally and return the result.
   * @param a the expression to evaluate
   * @tparam A
   * @return the result of the closure
   */
  def locally[A](a: => A): A

  /**
   * The neighbours aligned to the actual context.
   * @return the list of IDs of the neighbours
   */
  def alignedNeighbours(): List[Int]

  /**
   * Isolate the evaluation of the expression from the current context
   * @param expr the expression to evaluate
   * @tparam A
   * @return the result of the expression
   */
  def isolate[A](expr: => A): A

  def newExportStack: Any

  def discardExport: Any

  def mergeExport: Any

  /**
   * Whether the device is contained in the neighbor list
   * @return true if the device is contained in the neighbor list, false otherwise
   */
  def onlyWhenFoldingOnSelf: Boolean = neighbour.forall(_ == self)

  /**
   * Whether the device is contained in the neighbor list
   * @return true if the device is contained in the neighbor list, false otherwise
   */
  def unlessFoldingOnOthers: Boolean = neighbour.contains(self)

object RoundVM:
  def apply(c: Context): RoundVM = new RoundVMImpl(c)
  def ensure(b: => Boolean, s: String): Unit = if (!b) throw new Exception(s)

  final case class OutOfDomainException(selfId: Int, nbr: Int, path: Path) extends Exception:
    override def toString: String = s"OutOfDomainException: $selfId , $nbr, $path"

  final case class SensorUnknownException(selfId: Int, name: Sensor) extends Exception:
    override def toString: String = s"SensorUnknownException: $selfId , $name"

  final case class NbrSensorUnknownException(selfId: Int, name: Sensor, nbr: Int) extends Exception:
    override def toString: String = s"NbrSensorUnknownException: $selfId , $name, $nbr"

  class RoundVMImpl(val context: Context) extends RoundVM:
    var exports: List[Export] = List(Export.empty())
    var status: VMStatus = VMStatus()
    var isolated = false // When true, neighbours are scoped out

    override def foldedEval[A](expr: => A)(id: Int): Option[A] =
      handling(classOf[OutOfDomainException]) by (_ => None) apply {
        try
          status = status.push()
          status = status.foldInto(Some(id))
          Some(expr)
        finally status = status.pop()
      }

    override def nest[A](slot: Slot)(write: Boolean, inc: Boolean = true)(expr: => A): A =
      try
        status = status.push().nest(slot) // prepare nested call
        if write then
          exportData.get(status.path).getOrElse(exportData.put(status.path, expr))
        else
          expr
      finally status = if (inc) status.pop().incIndex() else status.pop()

    override def locally[A](a: => A): A =
      val currentNeighbour = neighbour
      try
        status = status.foldOut()
        a
      finally status = status.foldInto(currentNeighbour)

    override def alignedNeighbours(): List[Int] =
      if isolated then
        List()
      else
        self ::
          context
            .exports
            .filter(_._1 != self)
            .filter(p => status.path.isRoot || p._2.get(status.path).isDefined)
            .map(_._1)
            .toList

    override def isolate[A](expr: => A): A =
      val wasIsolated = this.isolated
      try
        this.isolated = true
        expr
      finally this.isolated = wasIsolated

    override def newExportStack: Any = exports = Export.empty() :: exports

    override def discardExport: Any = exports = exports.tail

    override def mergeExport: Any =
      val toMerge = exportData
      exports = exports.tail
      toMerge.exports.foreach(tp => exportData.put(tp._1, tp._2))

trait VMFactory:
  def createVM(c: Context): RoundVM

trait StandardVMFactory extends VMFactory:
  override def createVM(c: Context): RoundVM = RoundVM(c)