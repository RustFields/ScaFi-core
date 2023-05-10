package fields

import field.{DeviceId, Fields}
import lang.AuxiliaryConstructs

trait FieldTest extends Fields with AuxiliaryConstructs:
  override def mid: DeviceId = "d1"
