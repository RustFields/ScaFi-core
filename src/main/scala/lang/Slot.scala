package lang

trait Slot

case class Rep(index: Int) extends Slot
case class Nbr(index: Int) extends Slot
case class Branch(index: Int, tag: Boolean) extends Slot