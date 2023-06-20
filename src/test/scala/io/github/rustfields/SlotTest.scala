package io.github.rustfields

import io.github.rustfields.vm.Path
import io.github.rustfields.vm.Slot.{Nbr, Rep}
import org.scalatest.flatspec.AnyFlatSpec

class SlotTest extends AnyFlatSpec:

  behavior of "Slot"

  it should "create a Path" in {
    val path = Rep(0) / Nbr(1)
    assert(path.path == List(Rep(0), Nbr(1)).reverse)
  }

  it should "associate a value to a path" in {
    assert(Rep(0) -> "test" == (Path(Rep(0)), "test"))
  }
