package field

import cats.implicits.*
import field.FieldTest
import field.Fields.Field
import functional.defaultable.DefaultableInstances.given
import functional.monad.Monads.{*, given}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe

class FieldsOpsTest extends AnyFlatSpec with FieldTest with FieldOps:

  "Mapping a field" should "yield a new Field" in {
    val f = Field(Map(mid -> 1, "d2" -> 1))
    f.map(_ + 1).getMap shouldBe Map(mid -> 2, "d2" -> 2)
  }

  "Fields" should "be able to be used inside comprehensions" in {
    val f = Field(Map(mid -> 1, "d2" -> 1))
    val g = Field(Map(mid -> 2, "d2" -> 2))
    val h = for {
      x <- f
      y <- g
    } yield x + y
    h.getMap shouldBe Map(mid -> 3, "d2" -> 3)
  }

  "Fields" should "be composed in a functional way" in {
    val plusOne = (x: Int) => x + 1
    val plusTwo = (x: Int) => x + 2

    val f = Field(Map(mid -> 1, "d2" -> 2, "d3" -> 1, "d4" -> 2, "d5" -> 1))
    val g: Field[Int => Int] = Field(
      Map(
        mid -> plusOne,
        "d2" -> plusTwo,
        "d3" -> plusOne,
        "d4" -> plusTwo,
        "d5" -> plusOne,
      ),
      (_: Int) => 0
    )
    val h = for
      x <- f
      y <- g
    yield y.apply(x)
    h.getMap shouldBe Map(mid -> 2, "d2" -> 4, "d3" -> 2, "d4" -> 4, "d5" -> 2)

    val h1 = applyToAll(f, plusOne)
    h1.getMap shouldBe Map(mid -> 2, "d2" -> 3, "d3" -> 2, "d4" -> 3, "d5" -> 2)
    h1.default shouldBe 1

    val h2 = applyToAll(Field.lift(1), plusTwo)
    h2.default shouldBe 3
  }

  "Fields" should "be able to be applied to functions" in {
    val field = Field.lift(1)
    val plusOne = (x: Int) => x + 1

    val result = field >> plusOne >> plusOne >> plusOne
    result.default shouldBe 4
  }

  "Fields of monoids" should "be able to be combined together" in {
    val f1 = Field(Map(mid -> 1, "d2" -> 2, "d3" -> 1, "d4" -> 2, "d5" -> 1))
    val g1 = Field(Map(mid -> 2, "d2" -> 2, "d3" -> 2, "d4" -> 2, "d5" -> 2))
    val h1 = mappendFields(f1, g1)
    h1.getMap shouldBe Map(mid -> 3, "d2" -> 4, "d3" -> 3, "d4" -> 4, "d5" -> 3)

    val f2 = Field(
      Map(
        mid -> List(1),
        "d2" -> List(2),
        "d3" -> List(1),
        "d4" -> List(2),
        "d5" -> List(1),
      )
    )
    val g2 = Field(
      Map(
        mid -> List(2),
        "d2" -> List(2),
        "d3" -> List(2),
        "d4" -> List(2),
        "d5" -> List(2),
      )
    )
    val h2 = mappendFields(f2, g2)
    h2.getMap shouldBe Map(
      mid -> List(1, 2),
      "d2" -> List(2, 2),
      "d3" -> List(1, 2),
      "d4" -> List(2, 2),
      "d5" -> List(1, 2),
    )
  }
