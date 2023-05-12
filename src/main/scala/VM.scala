trait VM:

  def status: VMStatus

  /**
   * The current index of the VM.
   *
   * @return the index
   */
  def index: Int = status.index
