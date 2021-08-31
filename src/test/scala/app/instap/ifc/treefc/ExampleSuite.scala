package app.instap.ifc
package treefc

import scala.util.matching.Regex

final class ExampleSuite extends TestSuite {

  val TEST_LINE = """#9554= IFCFACEOUTERBOUND(#9552,.T.);"""
  val r: Regex = raw"""(#\d+)= (\w+)\(.+\);""".r

  test("Match") {
    TEST_LINE match {
      case r(id, name, fields) =>
        println(s"$id = $name ($fields)")
      case r(g1, g2) =>
        println(s"G1: $g1, G2: $g2")
      case r(g, _*) =>
        println(s"G: $g")
      case r(_*) =>
        println(s"G")
      case x =>
        println(s"No match: $x")
    }
    1 shouldBe 1
  }
}
