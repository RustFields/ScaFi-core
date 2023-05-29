package lang

import field.Fields.Field
import vm.RoundVM
import vm.RoundVM.*

trait FieldLang extends LangImpl:
  override type F[A] = Field[A]