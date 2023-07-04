package io.github.rustfields.functional

import CoreTestInterpreter.nbr
import CoreTestUtils.{assertEquivalence, fullyConnectedTopologyMap}
import io.github.rustfields.lang.FieldCalculusInterpreter
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

class TestByEquivalence extends AnyFlatSpec:

  given FieldCalculusInterpreter = CoreTestInterpreter
  import CoreTestInterpreter.*
  class Fixture:
    val random = new Random(0)
    val execSequence: LazyList[Int] = LazyList.continually(Random.nextInt(3)).take(100)
    val devicesAndNbrs: Map[Int, List[Int]] = fullyConnectedTopologyMap(List(0, 1, 2))

  "fold" should "work with multiple nbrs" in {
    val fixture = new Fixture
    assertEquivalence(fixture.devicesAndNbrs, fixture.execSequence) {
      foldhood(0)(_ + _)(nbr(1) + nbr(2) + nbr(mid()))
    } {
      foldhood(0)(_ + _)(nbr(1 + 2 + mid()))
    }
  }

  "rep.nbr" should "be ignored on first argument" in {
    val fixture = new Fixture
    assertEquivalence(fixture.devicesAndNbrs, fixture.execSequence) {
      foldhood(0)(_ + _)(rep(nbr(mid()))(old => old))
    } {
      foldhood(0)(_ + _)(rep(mid())(old => old))
    }
  }

  "rep.nbr" should "be ignored overall" in {
    val fixture = new Fixture
    assertEquivalence(fixture.devicesAndNbrs, fixture.execSequence){
      foldhood(0)(_ + _)(rep(nbr(mid()))(old => old + nbr(old) + nbr(mid())))
    } {
      foldhood(0)(_ + _)(1) * rep(mid())(old => old * 2 + mid())
    }
  }

  "fold.init nbr" should "be ignored" in {
    val fixture = new Fixture
    assertEquivalence(fixture.devicesAndNbrs, fixture.execSequence) {
      foldhood(0)(_ + _)(foldhood(nbr(mid()))(_ + _)(1))
    } {
      foldhood(0)(_ + _)(1) * foldhood(mid())(_ + _)(1)
    }
  }

  "fold.fold" should "work" in {
    val fixture = new Fixture
    assertEquivalence(fixture.devicesAndNbrs, fixture.execSequence) {
      foldhood(0)(_ + _)(foldhood(0)(_ + _)(1))
    } {
      Math.pow(foldhood(0)(_ + _)(1), 2)
    }
  }

  "Performance" should "not degrade when nesting foldhoods" in {
    val execSequence = LazyList.continually(Random.nextInt(100)).take(1000)
    val devicesAndNbrs = fullyConnectedTopologyMap(0 to 99)
    // fold.fold : performance
    // NOTE: pay attention to double overflow
    assertEquivalence(
      devicesAndNbrs,
      execSequence,
      (x: Double, y: Double) => Math.abs(x - y) / Math.max(Math.abs(x), Math.abs(y)) < 0.000001
    ) {
      foldhood(0.0)(_ + _) {
        foldhood(0.0)(_ + _) {
          foldhood(0.0)(_ + _) {
            foldhood(0.0)(_ + _) {
              foldhood(0.0)(_ + _) {
                foldhood(0.0)(_ + _) {
                  foldhood(0.0)(_ + _) {
                    foldhood(0.0)(_ + _) {
                      foldhood(0.0)(_ + _) {
                        foldhood(0.0)(_ + _)(1.0)
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    } {
      Math.pow(foldhood(0.0)(_ + _)(1.0), 10)
    }
  }