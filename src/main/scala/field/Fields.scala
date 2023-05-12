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

    def lift[A](a: A): Field[A] = new Field[A]:
      def getMap: Map[DeviceId, A] = Map.empty

      def default: A = a

    def changeSelf[A](selfValue: A, field: Field[A]): Field[A] =
      Field[A](field.getMap ++ Map(mid -> selfValue), field.default)
 
    def changeDef[A](newDefault: A, field: Field[A]): Field[A] = Field(field.getMap, newDefault)

