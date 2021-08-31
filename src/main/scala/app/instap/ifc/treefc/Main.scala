package app.instap.ifc
package treefc

import java.nio.charset.StandardCharsets
import java.nio.file._

import scala.jdk.StreamConverters._

object Main extends App {
  val fileName =
    "/Users/rzepaw/google-drive/_promar/BIM Mostostal/ifc_new/UMS-Z1-PW-AA-KON-1202-M-ZZ_Model_Konstrukcji_Zadanie_1_wg_LUW.ifc"
  val stream: LazyList[String] =
    Files.lines(Paths.get(fileName), StandardCharsets.US_ASCII).toScala(LazyList)
  val nodesStream: LazyList[IfcNode] = stream.flatMap(line => IfcNode.apply(line))
  nodesStream.take(10000).filter(_.id == 1824).foreach { node =>
    println(s"IFC node: $node")
  }
  // val numLines = stream.size
  // println(s"Lines: $numLines")
}
