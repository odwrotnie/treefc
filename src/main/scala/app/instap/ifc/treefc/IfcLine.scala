package app.instap.ifc
package treefc

sealed trait IfcLine {
  def id: Long
}

object IfcLine {
  // #1824= IFCPROPERTYSET('1TSTJNin2fQ27MjK9Itwgr',#12,'01 MW_KLASYFIKACJA',$,(#1814,#1815,#1816,#1817,#1818,#1819,#1820,#1821,#1822,#1823));
  private val IFCPROPERTYSET_REGEX = """#(\d+)=.?IFCPROPERTYSET\('([^']+)',#(\d+),'([^']+)',\$,\(([^\(\)]+)\)\);""".r
  private val IFCRELDEFINESBYPROPERTIES = """#(\d+)=.?IFCRELDEFINESBYPROPERTIES\('([^']+)',#(\d+),\$,\$,\(([^\(\)]+)\),(#\d+)\);""".r
  private val IFCPROPERTYSINGLEVALUE = """#(\d+)=.?IFCPROPERTYSINGLEVALUE\('([^']+)',\$,([^,]+),\$\);""".r
  private val LINE_REGEX = raw"""#(\d+)= (\w+)\((.+)\);""".r
  private val SPLITTER = ","

  private def parseRefs(str: String)(implicit find: Long => Option[IfcLine]): List[IfcRef] = str
    .split(SPLITTER)
    .toList
    .map(v => IfcTag(v))
    .collect {
      case r: IfcRef =>
        Some(r)
      case _ =>
        None
    }
    .flatten

      def apply(line: String)(implicit find: Long => Option[IfcLine]): Option[IfcLine] =
    line match {
      case s if s.contains("IFCPOLYLOOP") => None
      case s if s.contains("IFCFACE") => None
      case s if s.contains("IFCFACEOUTERBOUND") => None
      case s if s.contains("IFCFACETEDBREP") => None
      case s if s.contains("IFCSTYLEDITEM") => None
      case s if s.contains("IFCSHAPEREPRESENTATION") => None
      case s if s.contains("IFCCARTESIANPOINT") => None
      case s if s.contains("IFCPOLYLINE") => None
      case s if s.contains("IFCPRODUCTDEFINITIONSHAPE") => None
      case s if s.contains("IFCSHAPEASPECT") => None
      case s if s.contains("IFCDIRECTION") => None
      case s if s.contains("IFCAXIS2PLACEMENT3D") => None
      case s if s.contains("IFCLOCALPLACEMENT") => None
      case s if s.contains("IFCCLOSEDSHELL") => None
      case IFCPROPERTYSET_REGEX(id, propertyId, _, name, refs) =>
        val r = parseRefs(refs)
        Some(IfcPropertySet(id.toLong, propertyId, 123, name, r))
      case IFCRELDEFINESBYPROPERTIES(id, propertyId, _, refs, ref) =>
        val r = parseRefs(refs)
        Some(IfcRelDefines(id.toLong, propertyId, r, IfcTag(ref)))
      case IFCPROPERTYSINGLEVALUE(id, name, value) =>
        val t = IfcTag.apply(value)
        Some(IfcSingleValue(id.toLong, name, t))
      case LINE_REGEX(id, name, values) =>
        val tags = values.split(SPLITTER).toList.map(v => IfcTag(v))
        Some(IfcNode(id.toLong, name, tags))
      case LINE_REGEX(id, name, values) =>
        val tags = values.split(SPLITTER).toList.map(v => IfcTag(v))
        Some(IfcNode(id.toLong, name, tags))
      case s =>
        if (s.startsWith("#")) // All interesting shit starts with #
          throw new Exception(s"Can't parse $s")
        else
          None
    }
}

final case class IfcRelDefines(
    id: Long,
    propertyId: String,
    children: List[IfcRef],
    ref: IfcTag
  ) extends IfcLine

final case class IfcSingleValue(
    id: Long,
    name: String,
    value: IfcTag,
  ) extends IfcLine

final case class IfcPropertySet(
    id: Long,
    propertyId: String,
    owner: Long,
    name: String,
    children: List[IfcRef],
  ) extends IfcLine

final case class IfcPropertyValue(
    id: Long,
    name: String,
    value: String,
    children: List[IfcTag],
  ) extends IfcLine

final case class IfcNode(
    id: Long,
    name: String,
    children: List[IfcTag],
  ) extends IfcLine
