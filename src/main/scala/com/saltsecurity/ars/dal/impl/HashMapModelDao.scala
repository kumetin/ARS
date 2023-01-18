package com.saltsecurity.ars.dal.impl

import com.saltsecurity.ars.dal.ifc.{Model, ModelDao, ModelId}

import scala.collection.mutable

class HashMapModelDao extends ModelDao {

  private val map: mutable.Map[ModelId, Model] = mutable.HashMap[ModelId, Model]()
  private def extractId(model: Model) = ModelId(model.path, model.method)

  override def put(model: Model): Unit = {
    map.put(extractId(model), model)
  }

  override def get(id: ModelId): Option[Model] = {
    map.get(id)
  }
}
