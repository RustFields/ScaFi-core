trait Slot

object Slot:
  final case class Nbr(index: Int) extends Slot
  final case class Rep(index: Int) extends Slot
  final case class FoldHood(index: Int) extends Slot
  final case class Branch(index: Int, tag: Boolean) extends Slot
