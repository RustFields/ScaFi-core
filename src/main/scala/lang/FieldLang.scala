package lang

import field.Fields

trait FieldLang extends Language with Fields:
  override type F[A] = Field[A]

  override def rep[A](init: => Field[A])(fun: Field[A] => Field[A]): Field[A]


