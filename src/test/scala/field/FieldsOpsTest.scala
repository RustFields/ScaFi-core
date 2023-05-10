package field

import field.monad.MonadicFields
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe
import field.defaultable.DefaultableInstances.given

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
