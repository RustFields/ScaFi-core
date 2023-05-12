trait RoundVM:
  def status: VMStatus
  def index: Int = status.index
