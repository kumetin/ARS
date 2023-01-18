package com.saltsecurity.ars.logic

import com.saltsecurity.ars.dal.ifc
import com.saltsecurity.ars.dal.ifc.{Model, ModelId}
import com.saltsecurity.ars.enums.ParamLocations
import com.saltsecurity.ars.enums.ParamLocations.ParamLocation

case class EndpointModel(path: String,
                         method: String,
                         query_params: List[Parameter],
                         headers: List[Parameter],
                         body: List[Parameter]) {

  private lazy val queryMap: Map[String, Parameter] = query_params.map(p => (p.name, p)).toMap
  private lazy val headerMap: Map[String, Parameter] = headers.map(p => (p.name, p)).toMap
  private lazy val bodyMap: Map[String, Parameter] = body.map(p => (p.name, p)).toMap

  private def paramToDataLayer(paramLocation: ParamLocation, p: Parameter): ifc.Parameter =
    ifc.Parameter(paramLocation.toString, p.name, p.types, p.required)
  def toDataLayer: Model =
    ifc.Model(path, method,
      query_params.map(paramToDataLayer(ParamLocations.Query,_)),
      headers.map(paramToDataLayer(ParamLocations.Header,_)),
      body.map(paramToDataLayer(ParamLocations.Body,_)))

  def getQueryParam(name: String): Option[Parameter] = queryMap.get(name)

  def getHeaderParam(name: String): Option[Parameter] = headerMap.get(name)

  def getBodyParam(name: String): Option[Parameter] = bodyMap.get(name)
}
object EndpointModel {
  def fromDataLayer(model: Model): EndpointModel = EndpointModel(model.path, model.method,
    model.queryParams.map(Parameter.fromDataLayer),
    model.headerParams.map(Parameter.fromDataLayer),
    model.bodyParams.map(Parameter.fromDataLayer))
}
case class EndpointId(path: String, method: String) {
  def toDataLayer: ModelId = ifc.ModelId(path, method)
}
object EndpointId {
  def fromRequestData(req: RequestData) = EndpointId(req.path, req.method)
}

case class Parameter(name: String, types: List[String], required: Boolean)
private object Parameter {
  def fromDataLayer(p: ifc.Parameter): Parameter = Parameter(p.name, p.types, p.required)
}