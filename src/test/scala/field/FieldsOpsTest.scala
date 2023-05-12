package field

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe
import field.defaultable.DefaultableInstances.given
import field.MonadicFields

class FieldsOpsTest extends AnyFlatSpec with FieldTest with MonadicFields:

  "Mapping a field" should "yield a new Field" in {
    val f = Field(Map(mid -> 1, "d2" ->1))
    f.map(_ + 1).getMap shouldBe Map(mid -> 2, "d2" ->2)
  }

  "Fields" should "be able to be used inside comprehensions" in {
    val f = Field(Map(mid -> 1, "d2" ->1))
    val g = Field(Map(mid -> 2, "d2" ->2))
    val h = for {
      x <- f
      y <- g
    } yield x + y
    h.getMap shouldBe Map(mid -> 3, "d2" ->3)
  }

  "Fields" should "be composed in a functional way" in {
    val plusOne = (x: Int) => x + 1
    val plusTwo = (x: Int) => x + 2

    val f = Field(Map(mid -> 1, "d2" ->2, "d3" -> 1, "d4" -> 2, "d5" -> 1))
    val g: Field[Int => Int] = Field(Map(mid -> plusOne, "d2" -> plusTwo, "d3" -> plusOne, "d4" -> plusTwo, "d5" -> plusOne), (_:Int) => 0)
    val h = for
      x <- f
      y <- g
    yield y.apply(x)
    h.getMap shouldBe Map(mid -> 2, "d2" -> 4, "d3" -> 2, "d4" -> 4, "d5" -> 2)
  }