trait Export:
  /**
   * Put a value into the export
   * @param path the path to the value
   * @param value the value
   * @tparam A the type of the value
   * @return a new export with the value added
   */
  def put[A](path: Path, value: A): Export

  /**
   * Get the value corresponding to the given path
   * @param path the path to the value
   * @tparam A the type of the value
   * @return the value
   */
  def get[A](path: Path): Option[A]

  /**
   * Get the value corresponding to the root path
   * @tparam A the type of the value
   * @return the value
   */
  def root[A](): A

  /**
   * Get all the values in the export
   * NB: the values are not typed
   * @return a map of all the values
   */
  def paths: Map[Path, Any]

  /**
   * Get all the values in the export.
   * @tparam A the type of the values
   * @return a map of all the values
   */
  def getMap[A]: Map[Path, A] = paths.view.mapValues(_.asInstanceOf[A]).toMap
