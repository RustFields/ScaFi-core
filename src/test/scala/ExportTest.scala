import Slot.{Rep, Nbr}
import org.scalatest.flatspec.AnyFlatSpec

class ExportTest extends AnyFlatSpec:

  behavior of "Export"

  it should "push a path/value pair" in {
    val initialExport = ExportImpl(Map(Rep(0) -> "test"))
    val finalExport = initialExport.put(Path(Nbr(1)), "test")
    val expectedExport = ExportImpl(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    assert(finalExport == expectedExport)
  }

  it should "get the correct value" in {
    val testExport = ExportImpl(Map(Rep(0) -> "1", Nbr(1) -> "2", Nbr(2) -> "3"))
    val result = testExport.get(Path(Nbr(1))).get
    val expected = "2"
    assert(result == expected)
  }

  it should "be different from another export (values)" in {
    val export1 = ExportImpl(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    val export2 = ExportImpl(Map(Rep(0) -> "value", Nbr(1) -> "test"))
    assert(export1 != export2)
  }

  it should "be different from another export (paths)" in {
    val export1 = ExportImpl(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    val export2 = ExportImpl(Map(Rep(1) -> "test", Nbr(1) -> "test"))
    assert(export1 != export2)
  }

  it should "be equal to another export correctly" in {
    val export1 = ExportImpl(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    val export2 = ExportImpl(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    assert(export1 == export2)
  }