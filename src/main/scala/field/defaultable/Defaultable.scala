package field.defaultable

/**
 * Trait for types that have a default value.
 * @tparam A the Defaultable type
 */
trait Defaultable[A]:
  def default: A
