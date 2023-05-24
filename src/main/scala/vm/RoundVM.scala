package vm

import vm.RoundVM.*
import vm.{Path, Slot, Context, Export, VMStatus}

trait RoundVM:
  /**
   * The context of the current round
   *
   * @return the context
   */
  def context: Context

  /**
   * The status of the current round
   *
   * @return the status
   */
  def status: VMStatus

  /**
   * TODO
   *
   * @return
   */
  def exports: List[Export]

  /**
   * The first export of the stack
   *
   * @return the export
   */
  def exportData: Export = exports.head

  /**
   * The id of the device
   *
   * @return the id
   */
  def selfID: Int = context.selfID

  /**
   * If the computation is folding on a neighbor, get the id of the neighbor
   *
   * @return the id
   */
  def neighbour: Option[Int] = status.neighbour

  /**
   * The index of the current computation
   *
   * @return the index
   */
  def index: Int = status.index


  def exportStack: List[Export]

  def self: Int =
    context.selfID

  def registerRoot(v: Any): Unit =
    exportData.put(Path.empty(), v)

  def previousRoundVal[A]: Option[A] =
    context.readExportValue[A](self, status.path)

  def neighbourVal[A]: A = context
    .readExportValue[A](neighbour.get, status.path)
    .getOrElse(throw OutOfDomainException(context.selfID, neighbour.get, status.path))

  def localSense[A](name: Sensor): A = context
    .localSense[A](name)
    .getOrElse(throw SensorUnknownException(self, name))

  def neighbourSense[A](name: Sensor): A = {
    RoundVM.ensure(neighbour.isDefined, "Neighbouring sensor must be queried in a nbr-dependent context.")
    context
      .nbrSense(name)(neighbour.get)
      .getOrElse(throw NbrSensorUnknownException(self, name, neighbour.get))
  }

  def foldedEval[A](expr: => A)(id: Int): Option[A]

  def nest[A](slot: Slot)(write: Boolean, inc: Boolean = true)(expr: => A): A

  def locally[A](a: => A): A

  def alignedNeighbours(): List[Int]

  def isolate[A](expr: => A): A

  def newExportStack: Any

  def discardExport: Any

  def mergeExport: Any

  /**
   * Whether the device is contained in the neighbor list
   *
   * @return true if the device is contained in the neighbor list, false otherwise
   */
  def onlyWhenFoldingOnSelf: Boolean = neighbour.contains(selfID)

  /**
   * Whether the device is contained in the neighbor list
   *
   * @return true if the device is contained in the neighbor list, false otherwise
   */
  def unlessFoldingOnOthers: Boolean = neighbour.contains(selfID)

object RoundVM:
  def ensure(b: => Boolean, s: String): Unit = if (!b) throw new Exception(s)

  final case class OutOfDomainException(selfId: Int, nbr: Int, path: Path) extends Exception() {
    override def toString: String = s"OutOfDomainException: $selfId , $nbr, $path"
  }

  final case class SensorUnknownException(selfId: Int, name: Sensor) extends Exception() {
    override def toString: String = s"SensorUnknownException: $selfId , $name"
  }

  final case class NbrSensorUnknownException(selfId: Int, name: Sensor, nbr: Int) extends Exception() {
    override def toString: String = s"NbrSensorUnknownException: $selfId , $name, $nbr"
  }
