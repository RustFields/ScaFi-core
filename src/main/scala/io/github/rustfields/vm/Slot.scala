package io.github.rustfields.vm

trait Slot:
  /** This method is used to associate a value to a path.
    * @param value
    *   the value to be associated with the path
    * @return
    *   a tuple containing a path and the corresponding value
    */
  def ->(value: Any): (Path, Any) = (Path(this), value)

  /** Creates a path by concatenating this slot with the given slot.
    * @param slot
    *   the slot to be concatenated
    * @return
    *   a path
    */
  def /(slot: Slot): Path = Path(this, slot)

object Slot:
  final case class Nbr(index: Int) extends Slot
  final case class Rep(index: Int) extends Slot
  final case class FoldHood(index: Int) extends Slot
  final case class Branch(index: Int, tag: Boolean) extends Slot