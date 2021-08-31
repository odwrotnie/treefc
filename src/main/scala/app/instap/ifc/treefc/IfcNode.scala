package app.instap.ifc
package treefc

final case class IfcNode(
    id: Long,
    name: String,
    children: List[IfcTag],
  )

object IfcNode {
  private val LINE_REGEX = raw"""#(\d+)= (\w+)\((.+)\);""".r
  private val SPLITTER = ","

  def apply(line: String)(implicit fetchNode: Long => Option[IfcNode]): Option[IfcNode] =
    line match {
      case LINE_REGEX(id, name, values) =>
        val tags = values.split(SPLITTER).toList.map(v => IfcTag(v))
        Some(IfcNode(id.toLong, name, tags))
      case _ =>
        None
    }
}
