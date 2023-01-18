package com.saltsecurity.ars

import com.saltsecurity.ars.logic.EndpointModel
import com.saltsecurity.ars.server.APIServlet
import com.saltsecurity.ars.testing.JsonImplicits._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.test.scalatest._

import java.nio.charset.Charset


class ModelRunAPITests extends ScalatraFunSuite {

  addServlet(classOf[APIServlet], "/api/*")

  test("POST model/run with no payload returns 400") {
    put("/api/model/run", body = "{}".getBytes) {
      status should equal(400)
    }
  }

  test("PUT model/run with correct request data payload but no matching schema in model store, returns 404") {
    put("/api/model/run", body = testing.RequestData.GetUsers.getBytes(Charset.forName("UTF-8"))) {
      status should equal(404)
    }
  }

  test("PUT model/run with correct request data payload and matching schema existing in model store, returns 200") {
    implicit val jsonFormats: Formats = DefaultFormats
    Services.modelStore.put(testing.EndpointModels.GetUsersInfo.as[EndpointModel])
    put("/api/model/run", body = testing.RequestData.GetUsers) {
      status should equal(200)
    }
  }
}
