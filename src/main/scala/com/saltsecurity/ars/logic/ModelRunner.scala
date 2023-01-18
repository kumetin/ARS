package com.saltsecurity.ars.logic

import com.saltsecurity.ars.enums.ParamLocations.ParamLocation
import com.saltsecurity.ars.enums.ParamTypes.ParamType
import ModelRunner.Abnormalities.{Abnormality, MissingRequiredParam, TypeMismatch}
import com.saltsecurity.ars.enums.{ParamLocations, ParamTypes}

object ModelRunner {

  object Abnormalities {
    sealed trait Abnormality {
      def endpointId: EndpointId;

      def paramLocation: ParamLocation
    }

    case class MissingRequiredParam(override val endpointId: EndpointId,
                                    override val paramLocation: ParamLocation,
                                    name: String) extends Abnormality

    case class TypeMismatch(override val endpointId: EndpointId,
                            override val paramLocation: ParamLocation,
                            name: String) extends Abnormality

  }

  def findAbnormalities(endpointModel: EndpointModel, requestData: RequestData): List[Abnormality] = {
      rules.flatMap(_.findAbnormalities(endpointModel, requestData))
  }

  private val rules: List[Rule] = List(new RequiredParametersRule, new IncorrectParameterTypeRule)
}

private abstract class Rule {
  def findAbnormalities(endpointModel: EndpointModel, requestData: RequestData): List[Abnormality]
}

private [logic] class RequiredParametersRule extends Rule {
  override def findAbnormalities(endpointModel: EndpointModel, requestData: RequestData): List[Abnormality] = {

    val abnormalities: Iterable[Abnormality] =
      endpointModel.queryParams
      .find{case p => p.required && requestData.getQueryParam(p.name).isEmpty}
      .map(p => MissingRequiredParam(EndpointId(endpointModel.path, endpointModel.method), ParamLocations.Query, p.name)) ++
    endpointModel.headers
      .find { case p => p.required && requestData.getHeaderParam(p.name).isEmpty }
      .map(p => MissingRequiredParam(EndpointId(endpointModel.path, endpointModel.method), ParamLocations.Header, p.name)) ++
    endpointModel.body
      .find { case p => p.required && requestData.getBodyParam(p.name).isEmpty }
      .map(p => MissingRequiredParam(EndpointId(endpointModel.path, endpointModel.method), ParamLocations.Body, p.name))

    abnormalities.toList
  }
}

private [logic] class IncorrectParameterTypeRule extends Rule {
  override def findAbnormalities(endpointModel: EndpointModel, requestData: RequestData): List[Abnormality] = {
    endpointModel.queryParams.filter { case p =>
      val applicableTypes: Seq[ParamType] = p.types.map(ParamTypes.fromString)
      val value: Option[String] = requestData.getQueryParam(p.name)
      value.exists(v => applicableTypes.forall(!_.isApplicable(v)))
    }.map { case p => TypeMismatch(EndpointId(endpointModel.path, endpointModel.method), ParamLocations.Query, p.name) } ++
      endpointModel.headers.filter { case p =>
        val applicableTypes: Seq[ParamType] = p.types.map(ParamTypes.fromString)
        val value: Option[String] = requestData.getHeaderParam(p.name)
        value.exists(v => applicableTypes.forall(!_.isApplicable(v)))
      }.map { case p => TypeMismatch(EndpointId(endpointModel.path, endpointModel.method), ParamLocations.Header, p.name) } ++
      endpointModel.body.filter { case p =>
        val applicableTypes: Seq[ParamType] = p.types.map(ParamTypes.fromString)
        val value: Option[String] = requestData.getBodyParam(p.name)
        value.exists(v => applicableTypes.forall(!_.isApplicable(v)))
      }.map { case p => TypeMismatch(EndpointId(endpointModel.path, endpointModel.method), ParamLocations.Body, p.name) }
  }}









