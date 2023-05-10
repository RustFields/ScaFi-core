package fields

import org.scalatest.funsuite.AnyFunSuite
import fields.FieldTest

class FieldsTest extends AnyFunSuite with FieldTest:
  test("Field creation") {
    val f: Field[Int] = Field(Map(mid -> 1), 0)
    assert(f.getMap.get(mid).contains(1))
    assert(f.default == 0)
  }

  test("Lift a value to a field") {
    val f = Field.lift(1)
    assert(f.default == 1)
  }

  test("Change self value") {
    val f = Field(Map(mid -> 0), 0)
    val newField = Field.changeSelf(1, f)
    assert(newField.getMap.get(mid).contains(1))
  }

  test("Change default value") {
    val f = Field(Map(mid -> 0), 0)
    val newField = Field.changeDef(1, f)
    assert(newField.default == 1)
  }