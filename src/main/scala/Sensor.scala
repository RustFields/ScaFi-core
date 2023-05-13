trait Sensor

object Sensor:
  def apply(id: String): Sensor = SensorImpl(id)

  private case class SensorImpl(id: String) extends Sensor

given Conversion[String, Sensor] with
  def apply(name: String): Sensor = Sensor(name)