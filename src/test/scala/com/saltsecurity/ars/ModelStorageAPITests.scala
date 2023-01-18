package com.saltsecurity.ars

import com.saltsecurity.ars.server.APIServlet
import com.saltsecurity.ars.testing.EndpointModels
import org.scalatra.test.scalatest._

class ModelStorageAPITests extends ScalatraFunSuite {

  addServlet(classOf[APIServlet], "/api/*")

  test("PUT model/update with no payload returns 400") {
    put("/api/model/update", body = "{}".getBytes) {
      status should equal(400)
    }
  }

  test("PUT model/update with correct body returns 200") {
    put("/api/model/update", body = EndpointModels.GetUsersInfo.getBytes) {
      status should equal(200)
    }
  }

}
