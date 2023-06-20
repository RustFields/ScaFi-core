package io.github.rustfields.lang

import io.github.rustfields.vm.{Context, Export, RoundVM}
import io.github.rustfields.vm.RoundVM.{VMFactory, VMFactoryProvider}

trait FieldCalculusExecution extends (Context => Export) with LangImpl:
  factory: VMFactory =>

  type MainResult

  def main(): MainResult // where the code should be written

  var vm: RoundVM = _

  final def apply(c: Context): Export =
    round(c, main())

  final def round(c: Context, e: => Any = main()): Export =
    vm = factory.createVM(c)
    val result = e
    vm.registerRoot(result)
    vm.exportData

trait FieldCalculusInterpreter extends FieldCalculusExecution with FieldCalculusSyntax with VMFactoryProvider:
  type MainResult = Any
  final def main(): Any = ()

trait AggregateProgram extends FieldCalculusExecution with FieldCalculusSyntax:
  factory: VMFactory =>