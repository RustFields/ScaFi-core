package vm

/**
 * This trait models the status of the virtual machine.
 */
trait VMStatus:
  /**
   * The path of the computation.
   *
   * @return the current path
   */
  def path: Path

  /**
   * The index of the current slot.
   *
   * @return the index
   */
  def index: Int

  /**
   * The id of the current neighbour. If the current slot is not a folding slot, this value is None.
   *
   * @return the id
   */
  def neighbour: Option[Int]

  /**
   * Whether the VM is folding or not.
   *
   * @return true if the VM is folding, false otherwise
   */
  def isFolding: Boolean

  /**
   * Fold the current slot into the given neighbour.
   *
   * @param id the id of the neighbour
   * @return the new status
   */
  def foldInto(id: Option[Int]): VMStatus

  /**
   * Fold out of the current slot.
   *
   * @return the new status
   */
  def foldOut(): VMStatus

  /**
   * Nest the given slot.
   *
   * @param s the slot to nest
   * @return the new status
   */
  def nest(s: Slot): VMStatus

  /**
   * Increment the index of the current slot.
   *
   * @return the new status
   */
  def incIndex(): VMStatus

  /**
   * Push the current status on the stack.
   *
   * @return the new status
   */
  def push(): VMStatus

  /**
   * Pop the current status from the stack.
   *
   * @return the new status
   */
  def pop(): VMStatus

object VMStatus:
  def apply(path: Path): VMStatus = VMStatusImpl(path)

  def apply(): VMStatus = VMStatusImpl()

  final private case class VMStatusImpl(override val path: Path = Path(),
                                        override val index: Int = 0,
                                        override val neighbour: Option[Int] = None,
                                        private val stack: List[(Path, Int, Option[Int])] = List()
                                       ) extends VMStatus:

    override def isFolding: Boolean = neighbour.isDefined

    override def foldInto(id: Option[Int]): VMStatus = VMStatusImpl(path, index, id, stack)

    override def foldOut(): VMStatus = VMStatusImpl(path, index, None, stack)

    override def nest(s: Slot): VMStatus = VMStatusImpl(path.push(s), 0, neighbour, stack)

    override def incIndex(): VMStatus = VMStatusImpl(path, index + 1, neighbour, stack)

    override def push(): VMStatus = VMStatusImpl(path, index, neighbour, (path, index, neighbour) :: stack)

    override def pop(): VMStatus = stack match
      case (p, i, n) :: s => VMStatusImpl(p, i, n, s)
      case _ => throw new Exception()
