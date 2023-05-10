package field.defaultable

/**
 * Trait for types that have a default value.
 * @tparam A the Defaultable type
 */
trait Defaultable[A]:
  def default: A

object DefaultableInstances:
  given Defaultable[Int] with
    def default = 0

  given Defaultable[String] with
    def default = ""

  given Defaultable[Boolean] with
    def default = false