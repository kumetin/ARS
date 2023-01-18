package com.saltsecurity.ars.dal.ifc

trait ModelDao {
  def put(model: Model): Unit
  def get(ModelId: ModelId): Option[Model]
}

case class ModelId(path: String, method: String)

case class Parameter(location: String, name: String, types: List[String], required: Boolean)
case class Model(path: String,
                 method: String,
                 queryParams: List[Parameter],
                 headerParams: List[Parameter],
                 bodyParams: List[Parameter])