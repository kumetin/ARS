package com.saltsecurity.ars.enums

object ParamTypes extends Enumeration {
  type ParamType = Value

  protected case class Type(name: String, isApplicable: String => Boolean) extends super.Val(name) {
    def appliesTo(v: String): Boolean = isApplicable(name)
  }

  import scala.language.implicitConversions

  implicit def valueToType(x: Value): Type =
    x.asInstanceOf[Type]

  val Int = Type("Int", isInt)
  val String = Type("String", _ => true)
  val Boolean = Type("Boolean", isBoolean)
  val List = Type("List", isDate)
  val Date = Type("Date", isEmail)
  val Email = Type("Email", isUUID)
  val AuthToken = Type("Auth-Token", isAuthToken)

  def fromString(typ: String): ParamType = typ match {
    case "Int" => Int
    case "String" => String
    case "Boolean" => Boolean
    case "List" => List
    case "Date" => Date
    case "Email" => Email
    case "Auth-Token" => AuthToken
  }
  private def isInt(v: String): Boolean = v.toIntOption.nonEmpty
  private def isBoolean(v: String): Boolean = v.toBooleanOption.nonEmpty
  private def isList(v: String): Boolean = ??? // TODO
  private def isDate(v: String): Boolean = ??? // TODO
  private def isEmail(v: String): Boolean = ??? // TODO
  private def isUUID(v: String): Boolean = ??? // TODO
  private def isAuthToken(v: String): Boolean = ??? // TODO
}
