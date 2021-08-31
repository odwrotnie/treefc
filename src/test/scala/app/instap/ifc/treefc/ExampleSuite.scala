package app.instap.ifc
package treefc

import scala.util.matching.Regex

final class ExampleSuite extends TestSuite {
  val TEST_LINE =
    """#9554= IFCFACEOUTERBOUND(#9552,.T.,asdfl,alskjdf,alsdkfjie,alsdkfj,laksjdfie);"""
  val r: Regex = raw"""#(\d+)= (\w+)\((.+)\);""".r

  test("Match") {
    TEST_LINE match {
      case r(id, name, fields) =>
        println(s"$id = $name, fields: $fields")
      case x =>
        println(s"No match for $r in $x")
    }
    1 shouldBe 1
  }
}
