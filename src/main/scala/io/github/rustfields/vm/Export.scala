package io.github.rustfields.vm

trait Export:
  /** Put a value into the export
    * @param path
    *   the path to the value
    * @param value
    *   the value
    * @tparam A
    *   the type of the value
    * @return
    *   the value
    */
  def put[A](path: Path, value: A): A

  /** Get the value corresponding to the given path
    * @param path
    *   the path to the value
    * @tparam A
    *   the type of the value
    * @return
    *   the value
    */
  def get[A](path: Path): Option[A]

  /** Get the value corresponding to the root path
    * @tparam A
    *   the type of the value
    * @return
    *   the value
    */
  def root[A](): A

  /** Get all the values in the export NB: the values are not typed
    * @return
    *   a map of all the values
    */
  def exports: Map[Path, Any]

  /** Get all the values in the export.
    * @tparam A
    *   the type of the values
    * @return
    *   a map of all the values
    */
  def getMap[A]: Map[Path, A] = exports.view.mapValues(_.asInstanceOf[A]).toMap

object Export:
  def empty(): Export = ExportImpl()
  def apply(exps: (Path, Any)*): Export = ExportImpl(exps.toMap)
  def apply(exps: Map[Path, Any]): Export = ExportImpl(exps)

  private case class ExportImpl(private var map: Map[Path, Any] = Map.empty) extends Export:

    override def exports: Map[Path, Any] = map

    override def put[A](path: Path, value: A): A = { map += (path -> value); value }

    override def root[A](): A = get(Path()).get

    override def get[A](path: Path): Option[A] = exports.get(path).map(_.asInstanceOf[A])