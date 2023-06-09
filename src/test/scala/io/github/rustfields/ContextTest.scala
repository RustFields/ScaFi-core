package io.github.rustfields

import io.github.rustfields.vm.Sensor.given
import io.github.rustfields.vm.Slot.{Nbr, Rep}
import io.github.rustfields.vm.{Context, Export, Sensor}
import org.scalatest.flatspec.AnyFlatSpec
import scala.language.implicitConversions

class ContextTest extends AnyFlatSpec:

  behavior of "Context"

  // Arrange
  val exp2: Export = Export(Map(Rep(0) -> "1", Nbr(1) -> "2"))
  val exp3: Export = Export(Map(Rep(0) -> "3", Rep(1) / Nbr(0) -> "4"))
  val exports: Map[Int, Export] = Map(2 -> exp2, 3 -> exp3)
  val localSensor: Map[Sensor, Any] =
    Map(Sensor("one") -> true, Sensor("two") -> 2)
  val nbrSensors: Map[Sensor, Map[Int, Any]] = Map.empty
  val context: Context = Context(1, exports, localSensor, nbrSensors)

  it should "add an export" in {
    // Arrange
    val expected: Export = Export(Map(Rep(0) -> "5", Nbr(0) -> "value"))
    // Act
    val ctx: Context = context.putExport(4, expected)
    // Assert
    ctx.exports.toMap get 4 foreach (v => assert(v == expected))
  }

  it should "read a value of an export" in {
    // Act
    val result: Option[String] = context.readExportValue(2, Rep(1) / Nbr(0))
    // Assert
    result foreach (v => assert(v == "4"))
  }

  it should "get the right value of a local sensor" in {
    // Act
    val result = context.localSense[Int]("two")
    // Assert
    result foreach (v => assert(v == 2))
  }

  it should "get the right value of a nbr sensor" in {
    // Arrange
    val nbrSensors = Map(
      Sensor("one") -> Map(0 -> 1, 1 -> 24),
      Sensor("two") -> Map(0 -> 66, 1 -> 23),
    )
    val context: Context = Context(1, exports, localSensor, nbrSensors)
    // Act
    val result = context.nbrSense[Int]("two")(1)
    // Assert
    result foreach (v => assert(v == 23))
  }
