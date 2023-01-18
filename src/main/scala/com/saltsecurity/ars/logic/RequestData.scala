package com.saltsecurity.ars.logic

case class RequestData(path: String,
                       method: String,
                       queryParams: List[RequestDataParameter],
                       headers: List[RequestDataParameter],
                       body: List[RequestDataParameter]) {

  private lazy val queryMap: Map[String, String] = queryParams.map(p => (p.name,p.value)).toMap
  private lazy val headerMap: Map[String, String] = headers.map(p => (p.name,p.value)).toMap
  private lazy val bodyMap: Map[String, String] = body.map(p => (p.name,p.value)).toMap

  def getQueryParam(name: String) = queryMap.get(name)
  def getHeaderParam(name: String) = headerMap.get(name)
  def getBodyParam(name: String) = bodyMap.get(name)
}

case class RequestDataParameter(name: String, value: String)
