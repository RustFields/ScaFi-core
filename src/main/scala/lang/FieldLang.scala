package lang

import field.Fields.Field
import vm.RoundVM
import vm.RoundVM.*

trait FieldLang extends Language:
  override type F[A] = Field[A]

  override def rep[A](init: => Field[A])(fun: Field[A] => Field[A]): Field[A]

  override def nbr[A](expr: => Field[A]): Field[A]

trait FieldLangImpl extends FieldLang with AuxiliaryConstructs:
  val vm: RoundVM
  override def rep[A](init: => Field[A])(fun: Field[A] => Field[A]): Field[A] = ???
