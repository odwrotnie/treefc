package app.instap.ifc
package treefc

import java.nio.charset.StandardCharsets
import java.nio.file._

import scala.jdk.StreamConverters._

object Main extends App {
  lazy val fileName =
    "/Users/rzepaw/google-drive/_promar/BIM Mostostal/ifc_new/UMS-Z1-PW-AA-KON-1202-M-ZZ_Model_Konstrukcji_Zadanie_1_wg_LUW.ifc"
  lazy val stream: LazyList[String] =
    Files.lines(Paths.get(fileName), StandardCharsets.US_ASCII).toScala(LazyList)
  implicit val fetchNode: Long => Option[IfcLine] = id => nodesStream.find(_.id == id)
  lazy val nodesStream: LazyList[IfcLine] = stream.flatMap(line => IfcLine.apply(line))
  nodesStream.foreach {
    case node: IfcRelDefines =>
      println(s" * $node")
    case node =>
      // println(s" * $node")
  }
}
