package app.instap.ifc
package treefc

final case class IfcNode(
    id: Long,
    name: String,
    children: List[IfcTag],
  ) {
  override def toString = s"""#$id / $name
    |${children.mkString(" - ", "\n - ", "\n")}
    """.stripMargin
}

object IfcNode {
  private val LINE_REGEX = raw"""#(\d+)= (\w+)\((.+)\);""".r
  private val SPLITTER = ","

  def apply(line: String): Option[IfcNode] =
    line match {
      case LINE_REGEX(id, name, values) =>
        val tags = values.split(SPLITTER).toList.map(v => IfcTag(v))
        Some(IfcNode(id.toLong, name, tags))
      case _ =>
        None
    }
}
