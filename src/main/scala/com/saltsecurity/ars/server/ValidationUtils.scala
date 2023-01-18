package com.saltsecurity.ars.server

import org.json4s.jackson.JsonMethods
import org.json4s.{Formats, JValue}
import org.scalatra.ScalatraServlet

trait ValidationUtils { self: ScalatraServlet =>

  implicit class PimpOption[T](o: Option[T]) {
    def getOrNotFound(implicit mf: scala.reflect.Manifest[T]) = {
      o.getOrElse(halt(404, s"resource not found"))
    }
  }

  implicit class PimpJValue(jv: JValue) {
    def extractOrBadRequest[T](implicit formats: Formats, mf: scala.reflect.Manifest[T]) = {
      jv.extractOpt[T](formats, mf).getOrElse{
        halt(400, s"Bad request - Unexpected payload (${JsonMethods.pretty(jv)})")
      }
    }
  }
}