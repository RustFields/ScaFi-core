package lang

import field.Fields.Field

trait FieldLang extends Language:
  override type F[A] = Field[A]

  override def rep[A](init: => Field[A])(fun: Field[A] => Field[A]): Field[A]

  override def nbr[A](expr: => Field[A]): Field[A]
