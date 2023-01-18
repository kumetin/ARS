package com.saltsecurity.ars.utils

import org.json4s.JsonAST.JValue
import org.json4s.jackson.{JsonMethods, compactJson, parseJson}
import org.json4s.{Formats, JsonInput}

object JsonImplicits {

  implicit class FromString(s: String) {
    implicit def asJson: JValue = JsonMethods.parse(s)
    implicit def as[T](implicit formats: Formats, mf: Manifest[T]): T = asJson.extract[T]
  }

  implicit class FromJson(jv: JValue) {
    def asString: String = compactJson(jv)
    def as[T](implicit formats: Formats, mf: Manifest[T]): T = jv.extract[T]
  }

  implicit class FromInstance[T](o: JsonInput) {
    def asJson = parseJson(o)
    def asJsonString: String = compactJson(o.asJson)
  }
}
