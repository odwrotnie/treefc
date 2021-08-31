package app.instap.ifc.treefc

sealed trait IfcTag

object IfcTag {
  private val IFC_REF_REGEX = """#(\d+)""".r
  private val IFC_STRING_REGEX = """'([^,]+)'""".r
  private val IFC_EMPTY_STRING_REGEX = """('')""".r
  private val IFC_DOLLAR_REGEX = """(\$)""".r
  private val IFC_DOUBLE_REGEX = """(\d+\.?\d?)""".r

  def apply(value: String)(implicit fetchNode: Long => Option[IfcNode]): IfcTag = value match {
    case IFC_REF_REGEX(index) => IfcRef(index.toLong)
    case IFC_STRING_REGEX(s) => IfcString(s)
    case IFC_EMPTY_STRING_REGEX(_) => IfcEmptyString
    case IFC_DOLLAR_REGEX(_) => IfcDollar
    case IFC_DOUBLE_REGEX(d) => IfcDouble(d.toDouble)
    case v => IfcUnknown(v)
  }
}

final case class IfcRef(index: Long)(implicit fetchNode: Long => Option[IfcNode]) extends IfcTag {
  lazy val node: Option[IfcNode] = fetchNode(index)
  override def toString() =
    s"($index): ${node.getOrElse("🤬")}"
}

final case class IfcString(index: String) extends IfcTag

final case class IfcDouble(value: Double) extends IfcTag

final case object IfcEmptyString extends IfcTag

final case object IfcDollar extends IfcTag

final case class IfcUnknown(value: String) extends IfcTag
