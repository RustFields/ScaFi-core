/** A path is a list of slots that represent a path in a tree. The path works
  * like a stack.
  */
trait Path:

  /** Pushes a slot to the path
    * @param slot
    *   the slot to push
    * @return
    *   the new path
    */
  def push(slot: Slot): Path

  /** Pulls the last slot from the top of the path
    * @return
    *   the new path
    */
  def pull(): Path

  /** Checks if the path is the root path
    * @return
    *   true if the path is the root path, false otherwise
    */
  def isRoot: Boolean

  /** Returns the head of the path (the last slot pushed)
    * @return
    *   the head of the path
    */
  def head: Slot

  /** @return
    *   the path as a list of slots
    */
  def path: List[Slot]

  /** Pushes a slot to the path
    * @param slot
    *   the slot to push
    * @return
    *   the new path
    */
  def /(slot: Slot): Path = push(slot)

object Path:
  def apply(slots: Slot*): Path = PathImpl(slots.toList.reverse)

  def empty(): Path = PathImpl(List())

  private case class PathImpl(path: List[Slot]) extends Path:

    override def push(slot: Slot): Path = PathImpl(slot :: path)

    override def pull(): Path = path match
      case _ :: p => PathImpl(p)
      case _      => throw new Exception()

    override def isRoot: Boolean = path.isEmpty

    override def head: Slot = path.head

    override def toString: String = "P:/" + path.reverse.mkString("/")
