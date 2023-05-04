package lang.vm

import lang.{Sensor, Slot}

trait RoundVM:

  def nest[A](slot: Slot)(write: Boolean, increment: Boolean = true)(expr: => A): A
  def localSense[A](sensorName: Sensor): A
  def index: Int
  def unlessFoldingOnOthers: Boolean
  def onlyWhenFoldingOnSelf: Boolean
  def locally[A](a: => A): A
  def previousRoundVal[A]: Option[A]
  def self: Int
  def neighbourVal[A]: A
  def neighbour: Option[Int]