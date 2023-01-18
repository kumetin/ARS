package com.saltsecurity.ars.server

import org.json4s.jackson.JsonMethods
import org.json4s.{Formats, JValue}
import org.scalatra.ScalatraServlet

trait ValidationUtils { self: ScalatraServlet =>

  implicit class PimpOption[T](o: Option[T]) {
    def getOrNotFound(extra: String = "")(implicit mf: scala.reflect.Manifest[T]) = {
      o.getOrElse(halt(404, s"resource not found. $extra"))
    }
  }

  implicit class PimpJValue(jv: JValue) {
    def extractOrBadRequest[T](extra: String)(implicit formats: Formats, mf: scala.reflect.Manifest[T]) = {
      jv.extractOpt[T](formats, mf).getOrElse{
        halt(400, s"bad request - unexpected payload (${JsonMethods.pretty(jv)}) $extra")
      }
    }
  }
}