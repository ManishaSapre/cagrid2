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
 * 2014-04-10T21:59:25.573-04:00
 * Generated source version: 2.6.3
 * 
 */
@WebService(targetNamespace = "http://mds.globus.org/bigindex/2008/11/24", name = "BigIndexEntryPortType")
@XmlSeeAlso({org.cagrid.index.metrics.types.ObjectFactory.class, org.cagrid.index.aggregator.types.ObjectFactory.class, ObjectFactory.class, org.oasis_open.docs.wsn._2004._06.wsn_ws_basenotification_1_2_draft_01.ObjectFactory.class, org.xmlsoap.schemas.ws._2004._03.addressing.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_servicegroup_1_2_draft_01.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_basefaults_1_2_draft_01.ObjectFactory.class, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourcelifetime_1_2_draft_01.ObjectFactory.class})
public interface BigIndexEntryPortType {

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "GetMultipleResourcePropertiesResponse", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", partName = "GetMultipleResourcePropertiesResponse")
    @WebMethod(operationName = "GetMultipleResourceProperties", action = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties/GetMultipleResourceProperties")
    public org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
        @WebParam(partName = "GetMultipleResourcePropertiesRequest", name = "GetMultipleResourceProperties", targetNamespace = "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd")
        org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01.GetMultipleResourceProperties getMultipleResourcePropertiesRequest
    ) throws org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.ResourceUnknownFault, org.oasis_open.docs.wsrf._2004._06.wsrf_ws_resourceproperties_1_2_draft_01_wsdl.InvalidResourcePropertyQNameFault;

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
}