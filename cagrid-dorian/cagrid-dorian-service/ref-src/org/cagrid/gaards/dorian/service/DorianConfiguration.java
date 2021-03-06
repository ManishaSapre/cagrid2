package org.cagrid.gaards.dorian.service;

import gov.nih.nci.cagrid.introduce.servicetools.ServiceConfiguration;

import java.io.File;

import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;
import org.globus.wsrf.config.ContainerConfig;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 * 
 * This class holds all service properties which were defined for the service to have
 * access to.
 * 
 * @created by Introduce Toolkit version 1.4
 * 
 */
public class DorianConfiguration implements ServiceConfiguration {

	public static DorianConfiguration  configuration = null;
    public String etcDirectoryPath;
    	
	public static DorianConfiguration getConfiguration() throws Exception {
		if (DorianConfiguration.configuration != null) {
			return DorianConfiguration.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/serviceconfiguration";
		try {
			javax.naming.Context initialContext = new InitialContext();
			DorianConfiguration.configuration = (DorianConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate service configuration.", e);
		}

		return DorianConfiguration.configuration;
	}
	

	
	private String dorianConfiguration;
	
	private String dorianProperties;
	
	
    public String getEtcDirectoryPath() {
		return ContainerConfig.getBaseDirectory() + File.separator + etcDirectoryPath;
	}
	
	public void setEtcDirectoryPath(String etcDirectoryPath) {
		this.etcDirectoryPath = etcDirectoryPath;
	}


	
	public String getDorianConfiguration() {
		return ContainerConfig.getBaseDirectory() + File.separator + dorianConfiguration;
	}
	
	
	public void setDorianConfiguration(String dorianConfiguration) {
		this.dorianConfiguration = dorianConfiguration;
	}

	
	public String getDorianProperties() {
		return ContainerConfig.getBaseDirectory() + File.separator + dorianProperties;
	}
	
	
	public void setDorianProperties(String dorianProperties) {
		this.dorianProperties = dorianProperties;
	}

	
}
