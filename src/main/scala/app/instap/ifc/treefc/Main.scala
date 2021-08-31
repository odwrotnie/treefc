package app.instap.ifc
package treefc

import java.nio.file.{ Files, Paths }
import java.util.stream.Stream

final case class IfcNode(name: String, children: Set[IfcNode])

object Main extends App {
  val filename = "/Users/rzepaw/google-drive/_promar/BIM Mostostal/ifc_new/UMS-Z1-PW-AA-KON-1202-M-ZZ_Model_Konstrukcji_Zadanie_1_wg_LUW.ifc"
  val stream: Stream[String] = Files.lines(Paths.get(filename))
  val numLines = stream.count
  println(s"Lines: $numLines")
  stream.close
}
