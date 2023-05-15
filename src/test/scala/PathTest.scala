import Slot.{Branch, FoldHood, Nbr, Rep}
import org.scalatest.flatspec.AnyFlatSpec

class PathTest extends AnyFlatSpec:

  behavior of "Path"

  it should "be empty" in {
    val path = Path()
    assert(path.path.isEmpty)
  }

  it should "have size 3" in {
    val path = Path(Rep(0), Nbr(1), Nbr(2))
    assert(path.path.size == 3)
  }

  it should "correctly push a Slot" in {
    val path = Path(Rep(0), Nbr(1))
    val pathAfterPush = path.push(Nbr(2))
    assert(pathAfterPush.path == List(Rep(0), Nbr(1), Nbr(2)).reverse)
  }

  it should "correctly pop a Slot" in {
    val path = Path(Rep(0), Nbr(1), Nbr(2))
    assert(path.pull().path == List(Rep(0), Nbr(1)).reverse)
  }

  it should "be root" in {
    val path = Path()
    assert(path.isRoot)
  }

  it should "NOT be root" in {
    val path = Path(Rep(0), Nbr(1), Nbr(2))
    assert(!path.isRoot)
  }

  it should "return the head Slot" in {
    val path = Path(Rep(0), Nbr(1), Nbr(2))
    assert(path.head == Nbr(2))
  }

  it should "add a slot correctly using / method" in {
    val path = Path(Rep(0))
    val pathAfterAdd = path / FoldHood(1) / Branch(1, false)
    assert(
      pathAfterAdd.path == List(Rep(0), FoldHood(1), Branch(1, false)).reverse
    )
  }

  it should "print correctly" in {
    val path = Path(Rep(0), Nbr(1), Nbr(2))
    assert(path.toString == "P:/Rep(0)/Nbr(1)/Nbr(2)")
  }
