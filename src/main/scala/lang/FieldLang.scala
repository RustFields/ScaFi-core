package lang

import field.Fields

trait FieldLang extends Language with Fields with AuxiliaryConstructs:
  override type F[A] = Field[A]

  override def rep[A](init: => Field[A])(fun: Field[A] => Field[A]): Field[A]

  override def nbr[A](expr: => Field[A]): Field[A]


