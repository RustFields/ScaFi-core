package field

import field.GlobalField
import field.defaultable.Defaultable


trait Fields:
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

    def apply[A](m: Map[DeviceId, A])(using defaultable: Defaultable[A]): Field[A] = new Field[A]:
      def getMap: Map[DeviceId, A] = m

      def default: A = defaultable.default

    def lift[A](a: A): Field[A] = new Field[A]:
      def getMap: Map[DeviceId, A] = Map.empty

      def default: A = a

    def changeDef[A](field: Field[A], newDefault: A): Field[A] = Field(field.getMap, newDefault)

