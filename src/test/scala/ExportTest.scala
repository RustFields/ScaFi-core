import Slot.{Nbr, Rep}
import org.scalatest.flatspec.AnyFlatSpec

class ExportTest extends AnyFlatSpec:

  behavior of "Export"

  it should "create an empty export" in {
    // Act
    val result = Export.empty()
    // Assert
    assert(result.exports.isEmpty)
  }

  it should "create an export from a map" in {
    // Arrange
    val expected = Map(Rep(0) -> "1", Nbr(1) -> "2")
    // Act
    val result = Export(Map(Rep(0) -> "1", Nbr(1) -> "2"))
    // Assert
    assert(result.exports == expected)
  }

  it should "create an export from a sequence of paths and values" in {
    // Arrange
    val expected = Map(Rep(0) -> "1", Nbr(1) -> "2")
    // Act
    val result = Export(Rep(0) -> "1", Nbr(1) -> "2")
    // Assert
    assert(result.exports == expected)
  }

  it should "push a path/value pair" in {
    // Arrange
    val initialExport = Export(Map(Rep(0) -> "test"))
    val expectedExport = Export(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    // Act
    val finalExport = initialExport.put(Path(Nbr(1)), "test")
    // Assert
    assert(finalExport == expectedExport)
  }

  it should "get the correct value" in {
    // Arrange
    val testExport = Export(Map(Rep(0) -> "1", Nbr(1) -> "2", Nbr(2) -> "3"))
    val expected = "2"
    // Act
    val result = testExport.get[String](Path(Nbr(1))).get
    // Assert
    assert(result == expected)
  }

  it should "be different from another export (values)" in {
    // Arrange
    val export1 = Export(Map(Rep(0) -> "1", Nbr(1) -> "2"))
    val export2 = Export(Map(Rep(0) -> "test", Nbr(1) -> "2"))
    // Act & Assert
    assert(export1 != export2)
  }

  it should "be different from another export (paths)" in {
    // Arrange
    val export1 = Export(Map(Rep(0) -> "test", Nbr(1) -> "test"))
    val export2 = Export(Map(Rep(1) -> "test", Nbr(1) -> "test"))
    // Act & Assert
    assert(export1 != export2)
  }

  it should "be equal to another export correctly" in {
    // Arrange
    val export1 = Export(Map(Rep(0) -> "1", Nbr(1) -> "test"))
    val export2 = Export(Map(Rep(0) -> "1", Nbr(1) -> "test"))
    // Act & Assert
    assert(export1 == export2)
  }

  it should "return the value corresponding to the root path" in {
    // Arrange
    val testExport = Export(Map(Rep(0) -> "1", Nbr(1) -> "2", Path() -> "test"))
    val expected = "test"
    // Act
    val result = testExport.root[String]()
    // Assert
    assert(result == expected)
  }

  it should "return the map with all the values" in {
    // Arrange
    val testExport = Export(Map(Rep(0) -> "1", Nbr(1) -> "2", Path() -> "test"))
    val expected = Map(Rep(0) -> "1", Nbr(1) -> "2", Path() -> "test")
    // Act
    val result = testExport.exports
    // Assert
    assert(result == expected)
  }

  it should "return the map with all the values of a given type" in {
    // Arrange
    val testExport = Export(Map(Rep(0) -> "1", Nbr(1) -> "2", Path() -> "test"))
    val expected = Map(Rep(0) -> "1", Nbr(1) -> "2", Path() -> "test")
    // Act
    val result = testExport.getMap[String]
    // Assert
    assert(result == expected)
  }
