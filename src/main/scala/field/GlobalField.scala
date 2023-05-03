package field

type DeviceId = String

/**
 * A Global Field is a Field with a value for each device, not needing defaults.
 * @tparam A the type of the field
 */
trait GlobalField[A]:
  /**
   * Get the value of the field for each device.
   * @return a map from device id to field value
   */
  def getMap: Map[DeviceId, A]
  
object GlobalField:
  def apply[A](m: Map[DeviceId, A]): GlobalField[A] = new GlobalField[A]:
    override def getMap: Map[DeviceId, A] = m