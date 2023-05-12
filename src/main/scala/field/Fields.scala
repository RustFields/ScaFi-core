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
  trait Field[A] extends GlobalField[A] with Defaultable[A]

  object Field:
    def apply[A](m: Map[DeviceId, A], d: A): Field[A] = new Field[A]:
      def getMap: Map[DeviceId, A] = m

      def default: A = d

    def apply[A: Defaultable](m: Map[DeviceId, A]): Field[A] = new Field[A]:
      def getMap: Map[DeviceId, A] = m

      def default: A = summon[Defaultable[A]].default

    /**
     * Lifts a value to a field of values
     * @param a the value to lift
     * @tparam A the type of the value
     * @return the field with an empty map and the lifted value as a default
     */
    def lift[A](a: A): Field[A] = new Field[A]:
      def getMap: Map[DeviceId, A] = Map.empty

      def default: A = a

    /**
     * Change the self value of the field
     * @param selfValue The new self value
     * @param field The field whose self value is to be changed
     * @tparam A The type of the value
     * @return A field with the new self value
     */
    def changeSelf[A](selfValue: A, field: Field[A]): Field[A] =
      Field[A](field.getMap ++ Map(mid -> selfValue), field.default)

    /**
     * Change the default value of the field
     * @param newDefault The new default value
     * @param field The field to modify
     * @tparam A The type of the field
     * @return A new field with the changed default value
     */
    def changeDef[A](newDefault: A, field: Field[A]): Field[A] = Field(field.getMap, newDefault)

