package org.cagrid.index.wsrf.stubs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.3
 * 2014-04-10T21:59:24.857-04:00
 * Generated source version: 2.6.3
 * 
 */
@WebService(targetNamespace = "http://mds.globus.org/bigindex/2008/11/24", name = "BigIndexPortType")
@XmlSeeAlso({org.cagrid.index.metrics.types.ObjectFactory.class, org.cagrid.index.aggregator.types.ObjectFactory.class, ObjectFactory.class, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.ObjectFactory.class, org.cagrid.index.types.ObjectFactory.class, org.xmlsoap.schemas.ws._2004._03.addressing.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_servicegroup_1_2_draft_01.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_basefaults_1_2_draft_01.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01.ObjectFactory.class})
public interface BigIndexPortType {

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "GetMultipleResourcePropertiesResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", partName = "GetMultipleResourcePropertiesResponse")
    @WebMethod(operationName = "GetMultipleResourceProperties", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties/GetMultipleResourceProperties")
    public org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
        @WebParam(partName = "GetMultipleResourcePropertiesRequest", name = "GetMultipleResourceProperties", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd")
        org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.GetMultipleResourceProperties getMultipleResourcePropertiesRequest
    ) throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.InvalidResourcePropertyQNameFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "GetContentResponse", targetNamespace = "http://mds.globus.org/bigindex/2008/11/24", partName = "getContentResponse")
    @WebMethod(operationName = "GetContent", action = "http://mds.globus.org/bigindex/2008/11/24/BigIndexPortType/GetContentRequest")
    public GetContentResponse getContent(
        @WebParam(partName = "getContentRequest", name = "GetContentRequest", targetNamespace = "http://mds.globus.org/bigindex/2008/11/24")
        org.cagrid.index.types.BigIndexContentIDList getContentRequest
    );

    @WebResult(name = "SubscriptionReference", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
    @RequestWrapper(localName = "Subscribe", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", className = "org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.Subscribe")
    @WebMethod(operationName = "Subscribe", action = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification/Subscribe")
    @ResponseWrapper(localName = "SubscribeResponse", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", className = "org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.SubscribeResponse")
    public org.xmlsoap.schemas.ws._2004._03.addressing.EndpointReferenceType subscribe(
        @WebParam(name = "ConsumerReference", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        org.xmlsoap.schemas.ws._2004._03.addressing.EndpointReferenceType consumerReference,
        @WebParam(name = "TopicExpression", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.TopicExpressionType topicExpression,
        @WebParam(name = "UseNotify", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        java.lang.Boolean useNotify,
        @WebParam(name = "Precondition", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.QueryExpressionType precondition,
        @WebParam(name = "Selector", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.QueryExpressionType selector,
        @WebParam(name = "SubscriptionPolicy", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        java.lang.Object subscriptionPolicy,
        @WebParam(name = "InitialTerminationTime", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        java.util.Calendar initialTerminationTime
    ) throws org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.SubscribeCreationFailedFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.TopicPathDialectUnknownFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.InvalidTopicExpressionFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.TopicNotSupportedFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "GetCurrentMessageResponse", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", partName = "GetCurrentMessageResponse")
    @WebMethod(operationName = "GetCurrentMessage", action = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification/GetCurrentMessage")
    public org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.GetCurrentMessageResponse getCurrentMessage(
        @WebParam(partName = "GetCurrentMessageRequest", name = "GetCurrentMessage", targetNamespace = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd")
        org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.GetCurrentMessage getCurrentMessageRequest
    ) throws org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.InvalidTopicExpressionFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.NoCurrentMessageOnTopicFault, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01_wsdl.TopicNotSupportedFault;

    @RequestWrapper(localName = "SetTerminationTime", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", className = "org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01.SetTerminationTime")
    @WebMethod(operationName = "SetTerminationTime", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime/SetTerminationTime")
    @ResponseWrapper(localName = "SetTerminationTimeResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", className = "org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01.SetTerminationTimeResponse")
    public void setTerminationTime(
        @WebParam(name = "RequestedTerminationTime", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd")
        java.util.Calendar requestedTerminationTime,
        @WebParam(mode = WebParam.Mode.OUT, name = "NewTerminationTime", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd")
        javax.xml.ws.Holder<java.util.Calendar> newTerminationTime,
        @WebParam(mode = WebParam.Mode.OUT, name = "CurrentTime", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd")
        javax.xml.ws.Holder<java.util.Calendar> currentTime
    ) throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01_wsdl.TerminationTimeChangeRejectedFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01_wsdl.UnableToSetTerminationTimeFault;

    @RequestWrapper(localName = "Destroy", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", className = "org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01.Destroy")
    @WebMethod(operationName = "Destroy", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime/Destroy")
    @ResponseWrapper(localName = "DestroyResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", className = "org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01.DestroyResponse")
    public void destroy() throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01_wsdl.ResourceNotDestroyedFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01_wsdl.ResourceUnknownFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "AddResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", partName = "AddResponse")
    @WebMethod(operationName = "Add", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2/Add")
    public org.xmlsoap.schemas.ws._2004._03.addressing.EndpointReferenceType add(
        @WebParam(partName = "AddRequest", name = "Add", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd")
        org.oasis_open.docs.wsrf._2004._06.wsrf_ws_servicegroup_1_2_draft_01.Add addRequest
    ) throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_servicegroup_1_2_draft_01_wsdl.ContentCreationFailedFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_servicegroup_1_2_draft_01_wsdl.UnsupportedMemberInterfaceFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_servicegroup_1_2_draft_01_wsdl.AddRefusedFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "QueryResponse", targetNamespace = "http://mds.globus.org/bigindex/2008/11/24", partName = "queryResponse")
    @WebMethod(operationName = "Query", action = "http://mds.globus.org/bigindex/2008/11/24/BigIndexPortType/QueryRequest")
    public QueryResponse query(
        @WebParam(partName = "queryRequest", name = "QueryRequest", targetNamespace = "http://mds.globus.org/bigindex/2008/11/24")
        QueryType queryRequest
    );

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "GetResourcePropertyResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", partName = "GetResourcePropertyResponse")
    @WebMethod(operationName = "GetResourceProperty", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties/GetResourceProperty")
    public org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.GetResourcePropertyResponse getResourceProperty(
        @WebParam(partName = "GetResourcePropertyRequest", name = "GetResourceProperty", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd")
        javax.xml.namespace.QName getResourcePropertyRequest
    ) throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.InvalidResourcePropertyQNameFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "QueryResourcePropertiesResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", partName = "QueryResourcePropertiesResponse")
    @WebMethod(operationName = "QueryResourceProperties", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties/QueryResourceProperties")
    public org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.QueryResourcePropertiesResponse queryResourceProperties(
        @WebParam(partName = "QueryResourcePropertiesRequest", name = "QueryResourceProperties", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd")
        org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.QueryResourceProperties queryResourcePropertiesRequest
    ) throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.UnknownQueryExpressionDialectFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.InvalidQueryExpressionFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.QueryEvaluationErrorFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.InvalidResourcePropertyQNameFault;
}
