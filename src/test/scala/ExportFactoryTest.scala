import Slot.{Rep, Nbr}
import org.scalatest.flatspec.AnyFlatSpec

class ExportFactoryTest extends AnyFlatSpec:

  behavior of "ExportFactory"

  // Arrange
  val factory: ExportFactory = ExportFactory()

  it should "create an empty export" in {
    // Arrange
    val expected = ExportImpl(Map.empty)
    // Act
    val result = factory.emptyExport()
    // Assert
    assert(result == expected)
  }

  it should "create an export" in {
    // Arrange
    val map = Map(Rep(0) -> "1", Nbr(1) -> "3")
    val expected = ExportImpl(map)
    // Act
    val result = factory.exportFrom(Rep(0) -> "1", Nbr(1) -> "3")
    // Assert
    assert(result == expected)
  }


