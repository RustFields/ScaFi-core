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
   * TODO
   * @param expr
   * @param id
   * @tparam A
   * @return
   */
  def foldedEval[A](expr: => A)(id: Int): Option[A]

  /**
   * TODO
   * @param slot the slot to use
   * @param write whether to write the result in the export
   * @param inc whether to increment the index
   * @param expr the expression to evaluate
   * @tparam A
   * @return the result of the expression
   */
  def nest[A](slot: Slot)(write: Boolean, inc: Boolean = true)(expr: => A): A

  /**
   * TODO
   * @param a the expression to evaluate
   * @tparam A
   * @return the result of the expression
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