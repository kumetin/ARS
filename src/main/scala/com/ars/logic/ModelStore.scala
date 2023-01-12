package com.ars.logic

import com.ars.dal
import com.ars.dal.ifc
import com.ars.dal.ifc.ModelDao
import com.ars.enums.{ParamLocations, ParamTypes}
import com.ars.enums.ParamLocations.ParamLocation
import com.ars.enums.ParamTypes.ParamType

case class EndpointId(path: String, method: String) {
  def toDataLayer: dal.ifc.ModelId = dal.ifc.ModelId(path, method)
}

case class Parameter(name: String, types: List[ParamType], required: Boolean)

object Parameter {
  def fromDataLayer(p: ifc.Parameter): Parameter = Parameter(p.name, p.types.map(ParamTypes.fromString), p.required)
}

case class EndpointModel(path: String,
                         method: String,
                         queryParams: List[Parameter],
                         headerParams: List[Parameter],
                         bodyParams: List[Parameter]) {
  private def paramToDataLayer(paramLocation: ParamLocation, p: Parameter): dal.ifc.Parameter =
    dal.ifc.Parameter(paramLocation.toString, p.name, p.types.map(_.name), p.required)
  def toDataLayer: dal.ifc.Model =
    dal.ifc.Model(path, method,
      queryParams.map(paramToDataLayer(ParamLocations.Query,_)),
      headerParams.map(paramToDataLayer(ParamLocations.Header,_)),
      bodyParams.map(paramToDataLayer(ParamLocations.Body,_)))

  def getQueryParam(name: String): Option[Parameter] = queryParams.find(_.name == name)
  def getHeaderParam(name: String): Option[Parameter] = headerParams.find(_.name == name)
  def getBodyParam(name: String): Option[Parameter] = bodyParams.find(_.name == name)
}

case class Header(name: String, value: String)
case class RequestData(path: String, method: String, queryParams: List[Header], headers: List[Header], body: List[Header]) {
  def getQueryParam(name: String) = queryParams.find(_.name == name).map(_.value)
  def getHeaderParam(name: String) = headers.find(_.name == name).map(_.value)
  def getBodyParam(name: String) = body.find(_.name == name).map(_.value)
}

object EndpointModel {
  def fromDataLayer(model: dal.ifc.Model): EndpointModel = EndpointModel(model.path, model.method,
    model.queryParams.map(Parameter.fromDataLayer),
    model.headerParams.map(Parameter.fromDataLayer),
    model.bodyParams.map(Parameter.fromDataLayer))
}

class ModelStore (modelDao: ModelDao){
  def put(model: EndpointModel): Unit = {
    modelDao.put(model.toDataLayer)
  }
  def get(id: EndpointId): Option[EndpointModel] = {
    modelDao.get(id.toDataLayer).map(EndpointModel.fromDataLayer)
  }
}
