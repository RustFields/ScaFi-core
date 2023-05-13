/**
 * This trait models the context of a device.
 */
trait Context:
  /**
   * The ID of the device that this context is for.
   *
   * @return the ID
   */
  def selfID: Int

  /**
   * All the export that are available to the device.
   *
   * @return the exports
   */
  def exports: Iterable[(Int, Export)]

  /**
   * Add an export of a device to the context.
   *
   * @param id  the ID of the device
   * @param exp the export of the device
   * @return the new context
   */
  def put(id: Int, exp: Export): Context

  /**
   * Read the value corresponding to the given path from the export of a device.
   *
   * @param id   the ID of the device
   * @param path the path to the value
   * @tparam A the type of the value
   * @return the value if it exists
   */
  def readValue[A](id: Int, path: Path): Option[A]

  /**
   * The values perceived by the local sensors of the device.
   *
   * @return the values
   */
  def localSensor: Map[Sensor, Any]

  /**
   * TODO
   *
   * @return the values
   */
  def nbrSensors: Map[Sensor, Map[Int, Any]]

  /**
   * get the value of the given sensor.
   * @param name the name of the sensor
   * @tparam T the type of the value
   * @return the value if it exists
   */
  def sense[T](name: Sensor): Option[T]

object Context:
  def apply(selfID: Int,
            exports: Iterable[(Int, Export)] = Map.empty,
            localSensor: Map[Sensor, Any],
            nbrSensors: Map[Sensor, Map[Int, Any]]): Context = ContextImpl(selfID,
    exports.toMap,
    localSensor,
    nbrSensors)

  private case class ContextImpl(override val selfID: Int,
                                 override val exports: Map[Int, Export],
                                 override val localSensor: Map[Sensor, Any],
                                 override val nbrSensors: Map[Sensor, Map[Int, Any]]) extends Context:

    override def put(id: Int, exp: Export): Context = copy(exports = exports + (id -> exp))

    override def readValue[A](id: Int, path: Path): Option[A] = exports get id flatMap (_.get[A](path))

    override def toString: String = s"C[\n\tI:$selfID,\n\tE:$exports,\n\tS1:$localSensor,\n\tS2:$nbrSensors\n]"

    override def sense[T](name: Sensor): Option[T] = localSensor get name map (_.asInstanceOf[T])