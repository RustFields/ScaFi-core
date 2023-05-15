given Conversion[String, Sensor] = Sensor(_)

trait Sensor

object Sensor:
  def apply(id: String): Sensor = SensorImpl(id)

  private case class SensorImpl(id: String) extends Sensor
