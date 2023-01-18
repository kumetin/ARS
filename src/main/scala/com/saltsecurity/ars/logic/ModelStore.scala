package com.saltsecurity.ars.logic

import com.saltsecurity.ars.dal.ifc.ModelDao
import org.slf4j.LoggerFactory

class ModelStore (modelDao: ModelDao) {

  private val logger =  LoggerFactory.getLogger(getClass)

  def put(model: EndpointModel): Unit = {
    logger.info(s"Putting endpoint model $model")
    modelDao.put(model.toDataLayer)
  }
  def get(id: EndpointId): Option[EndpointModel] = {
    modelDao.get(id.toDataLayer).map(EndpointModel.fromDataLayer)
  }
}
