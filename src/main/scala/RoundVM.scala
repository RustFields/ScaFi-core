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
  def neighbor: Option[Int] = status.neighbour

  /**
   * The index of the current computation
   *
   * @return the index
   */
  def index: Int = status.index

  /**
   * Whether the device is contained in the neighbor list
   *
   * @return true if the device is contained in the neighbor list, false otherwise
   */
  def onlyWhenFoldingOnSelf: Boolean = neighbor.contains(selfID)

  /**
   * Whether the device is contained in the neighbor list
   *
   * @return true if the device is contained in the neighbor list, false otherwise
   */
  def unlessFoldingOnOthers: Boolean = neighbor.contains(selfID)

object RoundVM:
  def apply(context: Context,
            exportsStack: List[Export] = List(Export.empty()),
            status: VMStatus = VMStatus()): RoundVM = RoundVMImpl(context, status, exportsStack)

  private case class RoundVMImpl(override val context: Context,
                                 override val status: VMStatus,
                                 override val exports: List[Export]) extends RoundVM