trait Path:
  def push(slot: Slot): Path
  def pull(): Path
  def isEqualTo(path: Path): Boolean
  def isRoot: Boolean
  def head: Slot
  def /(slot: Slot): Path = push(slot)

object Path:
  def apply(slots: Slot*): Path = PathImpl(slots.toList.reverse)

  private case class PathImpl(path: List[Slot]) extends Path:

    override def push(slot: Slot): Path = PathImpl(slot :: path)

    def pull(): Path = path match
      case _ :: p => PathImpl(p)
      case _ => throw new Exception()

    override def isEqualTo(path: Path): Boolean = this == path

    override def isRoot: Boolean = path.isEmpty

    override def head: Slot = path.head

    override def toString: String = path.reverse.mkString("/")
