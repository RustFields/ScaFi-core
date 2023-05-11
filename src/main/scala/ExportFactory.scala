trait ExportFactory:
  /**
   * Creates an empty export.
   * @return an empty export
   */
  def emptyExport(): Export

  /**
   * Creates an export from a sequence of paths and values.
   * @param exps a sequence of paths and values
   * @return
   */
  def exportFrom(exps: (Path, Any)*): Export

object ExportFactory:
  def apply(): ExportFactory = ExportFactoryImpl()
  
  private class ExportFactoryImpl extends ExportFactory:
    override def emptyExport(): Export = ExportImpl()
    override def exportFrom(exps: (Path, Any)*): Export = ExportImpl(exps.toMap)