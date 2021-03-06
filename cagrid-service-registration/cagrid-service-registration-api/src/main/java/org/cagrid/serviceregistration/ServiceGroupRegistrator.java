package org.cagrid.serviceregistration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.helpers.XMLUtils;
import org.cagrid.serviceregistration.model.ServiceGroupRegistrationParameters;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xmlsoap.schemas.ws._2004._03.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._03.addressing.EndpointReferenceType;

public class ServiceGroupRegistrator {

	private int initialDelay = 10000;

	private final static Logger logger = LoggerFactory
			.getLogger(ServiceGroupRegistrator.class);


	public boolean isDebug = false;
	private Scheduler scheduler;

	/** Creates a new instance of ServiceGroupRegistrationClient */
	public ServiceGroupRegistrator() {
		this.isDebug = logger.isDebugEnabled();
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public ServiceGroupRegistrator(String performRegistration, String paramsFile, String registrantURL,
			String indexServiceURL) {
		this.isDebug = logger.isDebugEnabled();
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		boolean perform = new Boolean(performRegistration).booleanValue();
		if(perform)
			this.register(paramsFile, registrantURL, indexServiceURL);
	}

	public ServiceGroupRegistrator(String paramsFile, String registrantURL,
			String indexServiceURL, long initialDelay) {
		this.isDebug = logger.isDebugEnabled();
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		this.register(paramsFile, registrantURL, indexServiceURL, initialDelay);
	}

	public void setInitialDelay(int d) {
		this.initialDelay = d;
	}

	public JobDetail register(String paramsFile, String registrantURL,
			String indexServiceURL, long delayMillis) {
		ServiceGroupRegistrationParameters params = null;
		try {
			params = readParams(paramsFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return register(params, registrantURL, indexServiceURL, delayMillis);
	}

	public JobDetail register(String paramsFile, String registrantURL,
			String indexServiceURL) {
		ServiceGroupRegistrationParameters params = null;
		try {
			params = readParams(paramsFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return register(params, registrantURL, indexServiceURL, initialDelay);
	}

	/**
	 * Create new managed registration using the supplied registration
	 * parameters.
	 * 
	 * @param params
	 *            registration parameters
	 * @param delayMillis
	 *            the delay in milliseconds before executing the request.
	 */
	public JobDetail register(ServiceGroupRegistrationParameters params,
			String registrantURL, String indexServiceURL, long delayMillis) {

		EndpointReferenceType epr = new EndpointReferenceType();
		AttributedURI uri = new AttributedURI();
		uri.setValue(registrantURL);
		epr.setAddress(uri);

		params.setRegistrantEPR(epr);

		EndpointReferenceType epr2 = new EndpointReferenceType();
		AttributedURI uri2 = new AttributedURI();
		uri2.setValue(indexServiceURL);
		epr2.setAddress(uri2);

		params.setServiceGroupEPR(epr2);

		JobDetail detail = new JobDetail(params.getRegistrantEPR() + "Job",
				"cagrid", RegistrationJob.class);
		JobDataMap data = new JobDataMap();
		data.put("params", params);
		data.put("epr", null);
		detail.setJobDataMap(data);
		long startTime = System.currentTimeMillis() + (delayMillis);
		SimpleTrigger trigger = new SimpleTrigger(params.getRegistrantEPR()
				+ "-Trigger", "cagrid", new Date(startTime), null,
				SimpleTrigger.REPEAT_INDEFINITELY,
				params.getRefreshIntervalSecs() * 1000);

		try {
			scheduler.scheduleJob(detail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return detail;
	}

	/**
	 * Create new managed registration using the supplied registration
	 * parameters. The registration will be made immediately (but
	 * asynchronously.
	 * 
	 * @param params
	 *            registration parameters
	 */
	public JobDetail register(ServiceGroupRegistrationParameters params,
			String registrantURL, String indexServiceURL) {
		return this.register(params, registrantURL, indexServiceURL,
				initialDelay);
	}

	/**
	 * 
	 * Use caution when calling the terminate method, as this method will cause
	 * ALL registration timer events to be canceled. In particular, excercise
	 * caution when calling terminate on the ServiceGroupRegistrationClient
	 * instance returned from the static getContainerClient method.
	 * 
	 */
	public void terminate() {
		this.reset();
		this.status("ServiceGroupRegistrationClient shut down");
	}

	/*
	 * Cancels all timer events and removes registrant lists
	 */
	private void reset() {

		try {
			System.out.println("Shutting down the scheduler");
			this.scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	private static final int LOG_I = 0;
	private static final int LOG_E = 1;
	private static final int LOG_W = 2;
	private static final int LOG_D = 3;

	/** status output at default level of Info */
	private void status(Object obj) {
		this.status(LOG_I, obj);
	}

	/** Internal method for handling console or log4j based output. */
	private void status(int msgType, Object obj) {
		// if (this.outputToConsole) {
		if ((msgType == LOG_E) || (msgType == LOG_W)) {
			System.err.println(obj);
		} else if (msgType == LOG_D) {
			// skip outputting debug messages to console unless
			// in debug mode
			if (this.isDebug)
				System.out.println(obj);
		} else {
			System.out.println(obj);
		}
		// } else if (this.outputToLog) {
		// switch (msgType) {
		// case LOG_E:
		// logger.error(obj);
		// break;
		// case LOG_W:
		// logger.warn(obj);
		// break;
		// case LOG_D:
		// logger.debug(obj);
		// break;
		// case LOG_I:
		// default:
		// logger.info(obj);
		// break;
		// }
		// }
	}

	// static public EndpointReferenceType getIndexEPR()
	// throws java.net.MalformedURLException, java.io.IOException {
	//
	// EndpointReferenceType indexEPR = new EndpointReferenceType();
	//
	// URL baseURL = ServiceHost.getBaseURL();
	// URL indexURL = new URL(baseURL, "DefaultIndexService");
	// if (logger.isDebugEnabled()) {
	// logger.debug("Container registration client will register to local index at "
	// + indexURL);
	// }
	//
	// indexEPR.setAddress(new Address(indexURL.toString()));
	//
	// return indexEPR;
	// }

	/**
	 * A helper method for reading a params block from a file. This is intended
	 * to be used to pull in a skeleton registration block by a service prior to
	 * populating any run-time defined fields.
	 * 
	 * @since GT3.9.5
	 */

	static public ServiceGroupRegistrationParameters readParams(String filename)
			throws Exception {
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(filename);
			Document doc = XMLUtils.parse(inputStream);

			ServiceGroupRegistrationParameters params = null;

			JAXBContext jc = JAXBContext
					.newInstance(ServiceGroupRegistrationParameters.class);
			Unmarshaller u = jc.createUnmarshaller();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();

			params = (ServiceGroupRegistrationParameters) u.unmarshal(doc);
			return params;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	// static public Timer register(EndpointReferenceType epr, String regFile)
	// throws Exception {
	// return register(ResourceContext.getResourceContext(), epr, regFile);
	// }

	// static public Timer register(ResourceContext ctx,
	// EndpointReferenceType epr, String regFile) throws Exception {
	// String regPath = ctx
	// .getProperty(org.apache.axis.Constants.MC_CONFIGPATH) + regFile;
	//
	// ServiceGroupRegistrationParameters params =
	// ServiceGroupRegistrationClient
	// .readParams(regPath);
	//
	// params.setRegistrantEPR(epr);
	//
	// ServiceGroupRegistrationClient client = ServiceGroupRegistrationClient
	// .getContainerClient();
	//
	// return client.register(params);
	// }

}
