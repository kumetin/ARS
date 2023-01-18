package com.saltsecurity.ars.server

import com.saltsecurity.ars.Services
import com.saltsecurity.ars.logic.{EndpointId, EndpointModel, ModelRunner, RequestData}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

class APIServlet extends ScalatraServlet with JacksonJsonSupport with ValidationUtils {

  override implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  // Update endpoints model store
  put("/model/update") {
    val endpointModel = parsedBody.extractOrBadRequest[EndpointModel]("Expected a json that can be parsed as `EndpointModel`")
    Services.modelStore.put(endpointModel)
  }

  // Check for abnormalities given in a request data
  put("/model/run") {
    val requestData = parsedBody.extractOrBadRequest[RequestData]("Expected a json that can be parsed as `RequestData`")
    val endpointId = EndpointId.fromRequestData(requestData)
    val model = Services.modelStore
      .get(endpointId)
      .getOrNotFound(s"Could not find a corresponding model (by endpoint id $endpointId) for the given request data")
    ModelRunner.findAbnormalities(model, requestData)
  }

}
