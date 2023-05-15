package field

import field.GlobalField
import functional.defaultable.Defaultable
import lang.AuxiliaryConstructs


trait Fields:
  self: AuxiliaryConstructs =>
  /**
   * A field is a map from device ids to values of type A.
   * When a device is not in the map, the default value is used.
   *
   * @tparam A the type of the field
   */
  case class Field[A](val getMap: Map[DeviceId, A], override val default: A) extends GlobalField[A] with Defaultable[A]

  object Field:
    def apply[A: Defaultable](m: Map[DeviceId, A]): Field[A] =
      val d = summon[Defaultable[A]].default
      Field(m, d)

    /**
     * Lifts a value to a field of values
     * @param a the value to lift
     * @tparam A the type of the value
     * @return the field with an empty map and the lifted value as a default
     */
    def lift[A](a: A): Field[A] = Field(Map.empty, a)