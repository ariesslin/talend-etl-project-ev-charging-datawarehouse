
package ie6750_project1_grouph.sessionfactload_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.MDM;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.SQLike;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: SessionFactLoad Purpose: <br>
 * Description: <br>
 * 
 * @author lin.shan1@northeastern.edu
 * @version 8.0.1.20240920_1319-patch
 * @status
 */
public class SessionFactLoad implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "SessionFactLoad.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(SessionFactLoad.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	public static String taskExecutionId = null;

	public static String jobExecutionId = java.util.UUID.randomUUID().toString();;

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	protected java.util.Map<String, String> defaultProperties = new java.util.HashMap<String, String>();
	protected java.util.Map<String, String> additionalProperties = new java.util.HashMap<String, String>();

	public java.util.Map<String, String> getDefaultProperties() {
		return this.defaultProperties;
	}

	public java.util.Map<String, String> getAdditionalProperties() {
		return this.additionalProperties;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "SessionFactLoad";
	private final String projectName = "IE6750_PROJECT1_GROUPH";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");

	private void s(final String component) {
		try {
			org.talend.metrics.DataReadTracker.setCurrentComponent(jobName, component);
		} catch (Exception | NoClassDefFoundError e) {
			// ignore
		}
	}

	private void mdc(final String subJobName, final String subJobPidPrefix) {
		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", subJobName);
		org.slf4j.MDC.put("_subJobPid", subJobPidPrefix + subJobPidCounter.getAndIncrement());
	}

	private void sh(final String componentId) {
		ok_Hash.put(componentId, false);
		start_Hash.put(componentId, System.currentTimeMillis());
	}

	{
		s("none");
	}

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_z6ecsJyAEe-rSfekVieUmA", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;

		private String currentComponent = null;
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					SessionFactLoad.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(SessionFactLoad.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tDBCommit_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBConnection_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_6_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_7_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_8_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_9_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_10_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tWarn_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tWarn_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tWarn_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tWarn_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tWarn_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBOutput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBOutput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBCommit_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBConnection_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBConnection_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBOutput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBOutput_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBCommit_1", "8p3RAW_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBCommit_1 begin ] start
				 */

				sh("tDBCommit_1");

				s(currentComponent = "tDBCommit_1");

				int tos_count_tDBCommit_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBCommit_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBCommit_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBCommit_1 = new StringBuilder();
							log4jParamters_tDBCommit_1.append("Parameters:");
							log4jParamters_tDBCommit_1.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBCommit_1.append(" | ");
							log4jParamters_tDBCommit_1.append("CLOSE" + " = " + "true");
							log4jParamters_tDBCommit_1.append(" | ");
							log4jParamters_tDBCommit_1.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlCommit");
							log4jParamters_tDBCommit_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBCommit_1 - " + (log4jParamters_tDBCommit_1));
						}
					}
					new BytesLimit65535_tDBCommit_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBCommit_1", "tDBCommit_1", "tPostgresqlCommit");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tDBCommit_1 begin ] stop
				 */

				/**
				 * [tDBCommit_1 main ] start
				 */

				s(currentComponent = "tDBCommit_1");

				java.sql.Connection conn_tDBCommit_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");
				if (conn_tDBCommit_1 != null && !conn_tDBCommit_1.isClosed()) {

					try {

						log.debug("tDBCommit_1 - Connection 'tDBConnection_2' starting to commit.");

						conn_tDBCommit_1.commit();

						log.debug("tDBCommit_1 - Connection 'tDBConnection_2' commit has succeeded.");

					} finally {

						log.debug("tDBCommit_1 - Closing the connection 'tDBConnection_2' to the database.");

						conn_tDBCommit_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_tDBConnection_2"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug("tDBCommit_1 - Connection 'tDBConnection_2' to the database closed.");

					}

				}

				tos_count_tDBCommit_1++;

				/**
				 * [tDBCommit_1 main ] stop
				 */

				/**
				 * [tDBCommit_1 process_data_begin ] start
				 */

				s(currentComponent = "tDBCommit_1");

				/**
				 * [tDBCommit_1 process_data_begin ] stop
				 */

				/**
				 * [tDBCommit_1 process_data_end ] start
				 */

				s(currentComponent = "tDBCommit_1");

				/**
				 * [tDBCommit_1 process_data_end ] stop
				 */

				/**
				 * [tDBCommit_1 end ] start
				 */

				s(currentComponent = "tDBCommit_1");

				if (log.isDebugEnabled())
					log.debug("tDBCommit_1 - " + ("Done."));

				ok_Hash.put("tDBCommit_1", true);
				end_Hash.put("tDBCommit_1", System.currentTimeMillis());

				/**
				 * [tDBCommit_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBCommit_1 finally ] start
				 */

				s(currentComponent = "tDBCommit_1");

				/**
				 * [tDBCommit_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 1);
	}

	public void tDBConnection_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBConnection_1", "sC0SEX_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBConnection_1 begin ] start
				 */

				sh("tDBConnection_1");

				s(currentComponent = "tDBConnection_1");

				cLabel = "GcpPostgresOLTP";

				int tos_count_tDBConnection_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBConnection_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBConnection_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBConnection_1 = new StringBuilder();
							log4jParamters_tDBConnection_1.append("Parameters:");
							log4jParamters_tDBConnection_1.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("HOST" + " = " + "\"34.56.183.144\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("PORT" + " = " + "\"5432\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("DBNAME" + " = " + "\"ev_station_oltp\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("SCHEMA_DB" + " = " + "\"\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("USER" + " = " + "\"postgres\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:GDKoP204x7NKUSkv1kba0iEpUB2gBBl/5bWvDTwxcOl6SA==")
									.substring(0, 4) + "...");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("USE_SHARED_CONNECTION" + " = " + "true");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1
									.append("SHARED_CONNECTION_NAME" + " = " + "\"GcpPostgresOLTP\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1.append("AUTO_COMMIT" + " = " + "false");
							log4jParamters_tDBConnection_1.append(" | ");
							log4jParamters_tDBConnection_1
									.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlConnection");
							log4jParamters_tDBConnection_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBConnection_1 - " + (log4jParamters_tDBConnection_1));
						}
					}
					new BytesLimit65535_tDBConnection_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBConnection_1", "GcpPostgresOLTP", "tPostgresqlConnection");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String dbProperties_tDBConnection_1 = "";
				String url_tDBConnection_1 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/"
						+ "ev_station_oltp";

				if (dbProperties_tDBConnection_1 != null && !"".equals(dbProperties_tDBConnection_1.trim())) {
					url_tDBConnection_1 = url_tDBConnection_1 + "?" + dbProperties_tDBConnection_1;
				}
				String dbUser_tDBConnection_1 = "postgres";

				final String decryptedPassword_tDBConnection_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:FNtAhF9NnO8FtyrFvn43dKQDac0CEDzvmH07A0lYg5oYtg==");
				String dbPwd_tDBConnection_1 = decryptedPassword_tDBConnection_1;

				java.sql.Connection conn_tDBConnection_1 = null;

				java.util.Enumeration<java.sql.Driver> drivers_tDBConnection_1 = java.sql.DriverManager.getDrivers();
				java.util.Set<String> redShiftDriverNames_tDBConnection_1 = new java.util.HashSet<String>(
						java.util.Arrays.asList("com.amazon.redshift.jdbc.Driver", "com.amazon.redshift.jdbc41.Driver",
								"com.amazon.redshift.jdbc42.Driver"));
				while (drivers_tDBConnection_1.hasMoreElements()) {
					java.sql.Driver d_tDBConnection_1 = drivers_tDBConnection_1.nextElement();
					if (redShiftDriverNames_tDBConnection_1.contains(d_tDBConnection_1.getClass().getName())) {
						try {
							java.sql.DriverManager.deregisterDriver(d_tDBConnection_1);
							java.sql.DriverManager.registerDriver(d_tDBConnection_1);
						} catch (java.lang.Exception e_tDBConnection_1) {
							globalMap.put("tDBConnection_1_ERROR_MESSAGE", e_tDBConnection_1.getMessage());
							// do nothing
						}
					}
				}

				SharedDBConnectionLog4j.initLogger(log.getName(), "tDBConnection_1");
				String sharedConnectionName_tDBConnection_1 = "GcpPostgresOLTP";
				conn_tDBConnection_1 = SharedDBConnectionLog4j.getDBConnection("org.postgresql.Driver",
						url_tDBConnection_1, dbUser_tDBConnection_1, dbPwd_tDBConnection_1,
						sharedConnectionName_tDBConnection_1);
				globalMap.put("conn_tDBConnection_1", conn_tDBConnection_1);
				if (null != conn_tDBConnection_1) {

					log.debug("tDBConnection_1 - Connection is set auto commit to 'false'.");
					conn_tDBConnection_1.setAutoCommit(false);
				}

				globalMap.put("schema_" + "tDBConnection_1", "");

				/**
				 * [tDBConnection_1 begin ] stop
				 */

				/**
				 * [tDBConnection_1 main ] start
				 */

				s(currentComponent = "tDBConnection_1");

				cLabel = "GcpPostgresOLTP";

				tos_count_tDBConnection_1++;

				/**
				 * [tDBConnection_1 main ] stop
				 */

				/**
				 * [tDBConnection_1 process_data_begin ] start
				 */

				s(currentComponent = "tDBConnection_1");

				cLabel = "GcpPostgresOLTP";

				/**
				 * [tDBConnection_1 process_data_begin ] stop
				 */

				/**
				 * [tDBConnection_1 process_data_end ] start
				 */

				s(currentComponent = "tDBConnection_1");

				cLabel = "GcpPostgresOLTP";

				/**
				 * [tDBConnection_1 process_data_end ] stop
				 */

				/**
				 * [tDBConnection_1 end ] start
				 */

				s(currentComponent = "tDBConnection_1");

				cLabel = "GcpPostgresOLTP";

				if (log.isDebugEnabled())
					log.debug("tDBConnection_1 - " + ("Done."));

				ok_Hash.put("tDBConnection_1", true);
				end_Hash.put("tDBConnection_1", System.currentTimeMillis());

				/**
				 * [tDBConnection_1 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBConnection_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
			}

			tDBConnection_2Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBConnection_1 finally ] start
				 */

				s(currentComponent = "tDBConnection_1");

				cLabel = "GcpPostgresOLTP";

				/**
				 * [tDBConnection_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 1);
	}

	public void tDBConnection_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBConnection_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBConnection_2", "BuYaJa_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBConnection_2 begin ] start
				 */

				sh("tDBConnection_2");

				s(currentComponent = "tDBConnection_2");

				cLabel = "GcpPostgresDW";

				int tos_count_tDBConnection_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBConnection_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBConnection_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBConnection_2 = new StringBuilder();
							log4jParamters_tDBConnection_2.append("Parameters:");
							log4jParamters_tDBConnection_2.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("HOST" + " = " + "\"34.56.183.144\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("PORT" + " = " + "\"5432\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("DBNAME" + " = " + "\"ev_station_dw\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("SCHEMA_DB" + " = " + "\"\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("USER" + " = " + "\"postgres\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:iWuFui1dKEkhCARqT3YJ7a2ENeao+6ouIkm5vzoqFZ43Pw==")
									.substring(0, 4) + "...");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("USE_SHARED_CONNECTION" + " = " + "true");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2
									.append("SHARED_CONNECTION_NAME" + " = " + "\"GcpPostgresDW\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("AUTO_COMMIT" + " = " + "false");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2
									.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlConnection");
							log4jParamters_tDBConnection_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBConnection_2 - " + (log4jParamters_tDBConnection_2));
						}
					}
					new BytesLimit65535_tDBConnection_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBConnection_2", "GcpPostgresDW", "tPostgresqlConnection");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String dbProperties_tDBConnection_2 = "";
				String url_tDBConnection_2 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/"
						+ "ev_station_dw";

				if (dbProperties_tDBConnection_2 != null && !"".equals(dbProperties_tDBConnection_2.trim())) {
					url_tDBConnection_2 = url_tDBConnection_2 + "?" + dbProperties_tDBConnection_2;
				}
				String dbUser_tDBConnection_2 = "postgres";

				final String decryptedPassword_tDBConnection_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:3ctXk2nDmxgkBg47U2yVlA1dC4gLwjjuRpDR+uBoS6JOUA==");
				String dbPwd_tDBConnection_2 = decryptedPassword_tDBConnection_2;

				java.sql.Connection conn_tDBConnection_2 = null;

				java.util.Enumeration<java.sql.Driver> drivers_tDBConnection_2 = java.sql.DriverManager.getDrivers();
				java.util.Set<String> redShiftDriverNames_tDBConnection_2 = new java.util.HashSet<String>(
						java.util.Arrays.asList("com.amazon.redshift.jdbc.Driver", "com.amazon.redshift.jdbc41.Driver",
								"com.amazon.redshift.jdbc42.Driver"));
				while (drivers_tDBConnection_2.hasMoreElements()) {
					java.sql.Driver d_tDBConnection_2 = drivers_tDBConnection_2.nextElement();
					if (redShiftDriverNames_tDBConnection_2.contains(d_tDBConnection_2.getClass().getName())) {
						try {
							java.sql.DriverManager.deregisterDriver(d_tDBConnection_2);
							java.sql.DriverManager.registerDriver(d_tDBConnection_2);
						} catch (java.lang.Exception e_tDBConnection_2) {
							globalMap.put("tDBConnection_2_ERROR_MESSAGE", e_tDBConnection_2.getMessage());
							// do nothing
						}
					}
				}

				SharedDBConnectionLog4j.initLogger(log.getName(), "tDBConnection_2");
				String sharedConnectionName_tDBConnection_2 = "GcpPostgresDW";
				conn_tDBConnection_2 = SharedDBConnectionLog4j.getDBConnection("org.postgresql.Driver",
						url_tDBConnection_2, dbUser_tDBConnection_2, dbPwd_tDBConnection_2,
						sharedConnectionName_tDBConnection_2);
				globalMap.put("conn_tDBConnection_2", conn_tDBConnection_2);
				if (null != conn_tDBConnection_2) {

					log.debug("tDBConnection_2 - Connection is set auto commit to 'false'.");
					conn_tDBConnection_2.setAutoCommit(false);
				}

				globalMap.put("schema_" + "tDBConnection_2", "");

				/**
				 * [tDBConnection_2 begin ] stop
				 */

				/**
				 * [tDBConnection_2 main ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "GcpPostgresDW";

				tos_count_tDBConnection_2++;

				/**
				 * [tDBConnection_2 main ] stop
				 */

				/**
				 * [tDBConnection_2 process_data_begin ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "GcpPostgresDW";

				/**
				 * [tDBConnection_2 process_data_begin ] stop
				 */

				/**
				 * [tDBConnection_2 process_data_end ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "GcpPostgresDW";

				/**
				 * [tDBConnection_2 process_data_end ] stop
				 */

				/**
				 * [tDBConnection_2 end ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "GcpPostgresDW";

				if (log.isDebugEnabled())
					log.debug("tDBConnection_2 - " + ("Done."));

				ok_Hash.put("tDBConnection_2", true);
				end_Hash.put("tDBConnection_2", System.currentTimeMillis());

				/**
				 * [tDBConnection_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBConnection_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk2", 0, "ok");
			}

			tDBInput_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBConnection_2 finally ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "GcpPostgresDW";

				/**
				 * [tDBConnection_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBConnection_2_SUBPROCESS_STATE", 1);
	}

	public static class CalculateRevenueStruct implements routines.system.IPersistableRow<CalculateRevenueStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public BigDecimal totalkwhconsumed;

		public BigDecimal getTotalkwhconsumed() {
			return this.totalkwhconsumed;
		}

		public Boolean totalkwhconsumedIsNullable() {
			return true;
		}

		public Boolean totalkwhconsumedIsKey() {
			return false;
		}

		public Integer totalkwhconsumedLength() {
			return 10;
		}

		public Integer totalkwhconsumedPrecision() {
			return 2;
		}

		public String totalkwhconsumedDefault() {

			return null;

		}

		public String totalkwhconsumedComment() {

			return "";

		}

		public String totalkwhconsumedPattern() {

			return "";

		}

		public String totalkwhconsumedOriginalDbColumnName() {

			return "totalkwhconsumed";

		}

		public Long sessionduration;

		public Long getSessionduration() {
			return this.sessionduration;
		}

		public Boolean sessiondurationIsNullable() {
			return true;
		}

		public Boolean sessiondurationIsKey() {
			return false;
		}

		public Integer sessiondurationLength() {
			return null;
		}

		public Integer sessiondurationPrecision() {
			return null;
		}

		public String sessiondurationDefault() {

			return null;

		}

		public String sessiondurationComment() {

			return "";

		}

		public String sessiondurationPattern() {

			return "";

		}

		public String sessiondurationOriginalDbColumnName() {

			return "sessionduration";

		}

		public BigDecimal totalrevenue;

		public BigDecimal getTotalrevenue() {
			return this.totalrevenue;
		}

		public Boolean totalrevenueIsNullable() {
			return true;
		}

		public Boolean totalrevenueIsKey() {
			return false;
		}

		public Integer totalrevenueLength() {
			return 10;
		}

		public Integer totalrevenuePrecision() {
			return 2;
		}

		public String totalrevenueDefault() {

			return null;

		}

		public String totalrevenueComment() {

			return "";

		}

		public String totalrevenuePattern() {

			return "";

		}

		public String totalrevenueOriginalDbColumnName() {

			return "totalrevenue";

		}

		public BigDecimal paymentamount;

		public BigDecimal getPaymentamount() {
			return this.paymentamount;
		}

		public Boolean paymentamountIsNullable() {
			return true;
		}

		public Boolean paymentamountIsKey() {
			return false;
		}

		public Integer paymentamountLength() {
			return null;
		}

		public Integer paymentamountPrecision() {
			return null;
		}

		public String paymentamountDefault() {

			return null;

		}

		public String paymentamountComment() {

			return "";

		}

		public String paymentamountPattern() {

			return "";

		}

		public String paymentamountOriginalDbColumnName() {

			return "paymentamount";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public int enddatetimekey;

		public int getEnddatetimekey() {
			return this.enddatetimekey;
		}

		public Boolean enddatetimekeyIsNullable() {
			return false;
		}

		public Boolean enddatetimekeyIsKey() {
			return true;
		}

		public Integer enddatetimekeyLength() {
			return 10;
		}

		public Integer enddatetimekeyPrecision() {
			return 0;
		}

		public String enddatetimekeyDefault() {

			return null;

		}

		public String enddatetimekeyComment() {

			return "";

		}

		public String enddatetimekeyPattern() {

			return "";

		}

		public String enddatetimekeyOriginalDbColumnName() {

			return "enddatetimekey";

		}

		public Integer bookingkey;

		public Integer getBookingkey() {
			return this.bookingkey;
		}

		public Boolean bookingkeyIsNullable() {
			return true;
		}

		public Boolean bookingkeyIsKey() {
			return false;
		}

		public Integer bookingkeyLength() {
			return 10;
		}

		public Integer bookingkeyPrecision() {
			return 0;
		}

		public String bookingkeyDefault() {

			return null;

		}

		public String bookingkeyComment() {

			return "";

		}

		public String bookingkeyPattern() {

			return "";

		}

		public String bookingkeyOriginalDbColumnName() {

			return "bookingkey";

		}

		public Integer paymentkey;

		public Integer getPaymentkey() {
			return this.paymentkey;
		}

		public Boolean paymentkeyIsNullable() {
			return true;
		}

		public Boolean paymentkeyIsKey() {
			return false;
		}

		public Integer paymentkeyLength() {
			return 10;
		}

		public Integer paymentkeyPrecision() {
			return 0;
		}

		public String paymentkeyDefault() {

			return null;

		}

		public String paymentkeyComment() {

			return "";

		}

		public String paymentkeyPattern() {

			return "";

		}

		public String paymentkeyOriginalDbColumnName() {

			return "paymentkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return null;
		}

		public Integer powerconsumedPrecision() {
			return null;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		public Boolean isPeakHour;

		public Boolean getIsPeakHour() {
			return this.isPeakHour;
		}

		public Boolean isPeakHourIsNullable() {
			return true;
		}

		public Boolean isPeakHourIsKey() {
			return false;
		}

		public Integer isPeakHourLength() {
			return null;
		}

		public Integer isPeakHourPrecision() {
			return null;
		}

		public String isPeakHourDefault() {

			return null;

		}

		public String isPeakHourComment() {

			return "";

		}

		public String isPeakHourPattern() {

			return "";

		}

		public String isPeakHourOriginalDbColumnName() {

			return "isPeakHour";

		}

		public BigDecimal ActualCost;

		public BigDecimal getActualCost() {
			return this.ActualCost;
		}

		public Boolean ActualCostIsNullable() {
			return true;
		}

		public Boolean ActualCostIsKey() {
			return false;
		}

		public Integer ActualCostLength() {
			return null;
		}

		public Integer ActualCostPrecision() {
			return null;
		}

		public String ActualCostDefault() {

			return null;

		}

		public String ActualCostComment() {

			return "";

		}

		public String ActualCostPattern() {

			return "";

		}

		public String ActualCostOriginalDbColumnName() {

			return "ActualCost";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.userkey;

				result = prime * result + (int) this.vehiclekey;

				result = prime * result + (int) this.stationkey;

				result = prime * result + (int) this.chargepointkey;

				result = prime * result + (int) this.pricingmodelkey;

				result = prime * result + (int) this.startdatetimekey;

				result = prime * result + (int) this.enddatetimekey;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final CalculateRevenueStruct other = (CalculateRevenueStruct) obj;

			if (this.userkey != other.userkey)
				return false;

			if (this.vehiclekey != other.vehiclekey)
				return false;

			if (this.stationkey != other.stationkey)
				return false;

			if (this.chargepointkey != other.chargepointkey)
				return false;

			if (this.pricingmodelkey != other.pricingmodelkey)
				return false;

			if (this.startdatetimekey != other.startdatetimekey)
				return false;

			if (this.enddatetimekey != other.enddatetimekey)
				return false;

			return true;
		}

		public void copyDataTo(CalculateRevenueStruct other) {

			other.sessionid = this.sessionid;
			other.totalkwhconsumed = this.totalkwhconsumed;
			other.sessionduration = this.sessionduration;
			other.totalrevenue = this.totalrevenue;
			other.paymentamount = this.paymentamount;
			other.userkey = this.userkey;
			other.vehiclekey = this.vehiclekey;
			other.stationkey = this.stationkey;
			other.chargepointkey = this.chargepointkey;
			other.pricingmodelkey = this.pricingmodelkey;
			other.startdatetimekey = this.startdatetimekey;
			other.enddatetimekey = this.enddatetimekey;
			other.bookingkey = this.bookingkey;
			other.paymentkey = this.paymentkey;
			other.powerconsumed = this.powerconsumed;
			other.startdatetime = this.startdatetime;
			other.enddatetime = this.enddatetime;
			other.isinterrupted = this.isinterrupted;
			other.interruptedreasonid = this.interruptedreasonid;
			other.vehicleid = this.vehicleid;
			other.stationid = this.stationid;
			other.chargepointid = this.chargepointid;
			other.pricingmodelid = this.pricingmodelid;
			other.bookingid = this.bookingid;
			other.StartDate = this.StartDate;
			other.EndDate = this.EndDate;
			other.StartHour = this.StartHour;
			other.EndHour = this.EndHour;
			other.isPeakHour = this.isPeakHour;
			other.ActualCost = this.ActualCost;

		}

		public void copyKeysDataTo(CalculateRevenueStruct other) {

			other.userkey = this.userkey;
			other.vehiclekey = this.vehiclekey;
			other.stationkey = this.stationkey;
			other.chargepointkey = this.chargepointkey;
			other.pricingmodelkey = this.pricingmodelkey;
			other.startdatetimekey = this.startdatetimekey;
			other.enddatetimekey = this.enddatetimekey;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.totalkwhconsumed = (BigDecimal) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.sessionduration = null;
					} else {
						this.sessionduration = dis.readLong();
					}

					this.totalrevenue = (BigDecimal) dis.readObject();

					this.paymentamount = (BigDecimal) dis.readObject();

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isPeakHour = null;
					} else {
						this.isPeakHour = dis.readBoolean();
					}

					this.ActualCost = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.totalkwhconsumed = (BigDecimal) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.sessionduration = null;
					} else {
						this.sessionduration = dis.readLong();
					}

					this.totalrevenue = (BigDecimal) dis.readObject();

					this.paymentamount = (BigDecimal) dis.readObject();

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isPeakHour = null;
					} else {
						this.isPeakHour = dis.readBoolean();
					}

					this.ActualCost = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// BigDecimal

				dos.writeObject(this.totalkwhconsumed);

				// Long

				if (this.sessionduration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.sessionduration);
				}

				// BigDecimal

				dos.writeObject(this.totalrevenue);

				// BigDecimal

				dos.writeObject(this.paymentamount);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

				// Boolean

				if (this.isPeakHour == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isPeakHour);
				}

				// BigDecimal

				dos.writeObject(this.ActualCost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.totalkwhconsumed);

				// Long

				if (this.sessionduration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.sessionduration);
				}

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.totalrevenue);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.paymentamount);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

				// Boolean

				if (this.isPeakHour == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isPeakHour);
				}

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.ActualCost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",totalkwhconsumed=" + String.valueOf(totalkwhconsumed));
			sb.append(",sessionduration=" + String.valueOf(sessionduration));
			sb.append(",totalrevenue=" + String.valueOf(totalrevenue));
			sb.append(",paymentamount=" + String.valueOf(paymentamount));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",enddatetimekey=" + String.valueOf(enddatetimekey));
			sb.append(",bookingkey=" + String.valueOf(bookingkey));
			sb.append(",paymentkey=" + String.valueOf(paymentkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append(",isPeakHour=" + String.valueOf(isPeakHour));
			sb.append(",ActualCost=" + String.valueOf(ActualCost));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			if (totalkwhconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(totalkwhconsumed);
			}

			sb.append("|");

			if (sessionduration == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionduration);
			}

			sb.append("|");

			if (totalrevenue == null) {
				sb.append("<null>");
			} else {
				sb.append(totalrevenue);
			}

			sb.append("|");

			if (paymentamount == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentamount);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			sb.append(enddatetimekey);

			sb.append("|");

			if (bookingkey == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingkey);
			}

			sb.append("|");

			if (paymentkey == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentkey);
			}

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			if (isPeakHour == null) {
				sb.append("<null>");
			} else {
				sb.append(isPeakHour);
			}

			sb.append("|");

			if (ActualCost == null) {
				sb.append("<null>");
			} else {
				sb.append(ActualCost);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CalculateRevenueStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.userkey, other.userkey);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.vehiclekey, other.vehiclekey);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.stationkey, other.stationkey);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.chargepointkey, other.chargepointkey);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.pricingmodelkey, other.pricingmodelkey);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.startdatetimekey, other.startdatetimekey);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.enddatetimekey, other.enddatetimekey);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class CalculateCostStruct implements routines.system.IPersistableRow<CalculateCostStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public BigDecimal totalkwhconsumed;

		public BigDecimal getTotalkwhconsumed() {
			return this.totalkwhconsumed;
		}

		public Boolean totalkwhconsumedIsNullable() {
			return true;
		}

		public Boolean totalkwhconsumedIsKey() {
			return false;
		}

		public Integer totalkwhconsumedLength() {
			return 10;
		}

		public Integer totalkwhconsumedPrecision() {
			return 2;
		}

		public String totalkwhconsumedDefault() {

			return null;

		}

		public String totalkwhconsumedComment() {

			return "";

		}

		public String totalkwhconsumedPattern() {

			return "";

		}

		public String totalkwhconsumedOriginalDbColumnName() {

			return "totalkwhconsumed";

		}

		public Long sessionduration;

		public Long getSessionduration() {
			return this.sessionduration;
		}

		public Boolean sessiondurationIsNullable() {
			return true;
		}

		public Boolean sessiondurationIsKey() {
			return false;
		}

		public Integer sessiondurationLength() {
			return null;
		}

		public Integer sessiondurationPrecision() {
			return null;
		}

		public String sessiondurationDefault() {

			return null;

		}

		public String sessiondurationComment() {

			return "";

		}

		public String sessiondurationPattern() {

			return "";

		}

		public String sessiondurationOriginalDbColumnName() {

			return "sessionduration";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public int enddatetimekey;

		public int getEnddatetimekey() {
			return this.enddatetimekey;
		}

		public Boolean enddatetimekeyIsNullable() {
			return false;
		}

		public Boolean enddatetimekeyIsKey() {
			return true;
		}

		public Integer enddatetimekeyLength() {
			return 10;
		}

		public Integer enddatetimekeyPrecision() {
			return 0;
		}

		public String enddatetimekeyDefault() {

			return null;

		}

		public String enddatetimekeyComment() {

			return "";

		}

		public String enddatetimekeyPattern() {

			return "";

		}

		public String enddatetimekeyOriginalDbColumnName() {

			return "enddatetimekey";

		}

		public Integer bookingkey;

		public Integer getBookingkey() {
			return this.bookingkey;
		}

		public Boolean bookingkeyIsNullable() {
			return true;
		}

		public Boolean bookingkeyIsKey() {
			return false;
		}

		public Integer bookingkeyLength() {
			return 10;
		}

		public Integer bookingkeyPrecision() {
			return 0;
		}

		public String bookingkeyDefault() {

			return null;

		}

		public String bookingkeyComment() {

			return "";

		}

		public String bookingkeyPattern() {

			return "";

		}

		public String bookingkeyOriginalDbColumnName() {

			return "bookingkey";

		}

		public Integer paymentkey;

		public Integer getPaymentkey() {
			return this.paymentkey;
		}

		public Boolean paymentkeyIsNullable() {
			return true;
		}

		public Boolean paymentkeyIsKey() {
			return false;
		}

		public Integer paymentkeyLength() {
			return 10;
		}

		public Integer paymentkeyPrecision() {
			return 0;
		}

		public String paymentkeyDefault() {

			return null;

		}

		public String paymentkeyComment() {

			return "";

		}

		public String paymentkeyPattern() {

			return "";

		}

		public String paymentkeyOriginalDbColumnName() {

			return "paymentkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return null;
		}

		public Integer powerconsumedPrecision() {
			return null;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		public Boolean isPeakHour;

		public Boolean getIsPeakHour() {
			return this.isPeakHour;
		}

		public Boolean isPeakHourIsNullable() {
			return true;
		}

		public Boolean isPeakHourIsKey() {
			return false;
		}

		public Integer isPeakHourLength() {
			return null;
		}

		public Integer isPeakHourPrecision() {
			return null;
		}

		public String isPeakHourDefault() {

			return null;

		}

		public String isPeakHourComment() {

			return "";

		}

		public String isPeakHourPattern() {

			return "";

		}

		public String isPeakHourOriginalDbColumnName() {

			return "isPeakHour";

		}

		public BigDecimal ActualCost;

		public BigDecimal getActualCost() {
			return this.ActualCost;
		}

		public Boolean ActualCostIsNullable() {
			return true;
		}

		public Boolean ActualCostIsKey() {
			return false;
		}

		public Integer ActualCostLength() {
			return null;
		}

		public Integer ActualCostPrecision() {
			return null;
		}

		public String ActualCostDefault() {

			return null;

		}

		public String ActualCostComment() {

			return "";

		}

		public String ActualCostPattern() {

			return "";

		}

		public String ActualCostOriginalDbColumnName() {

			return "ActualCost";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.totalkwhconsumed = (BigDecimal) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.sessionduration = null;
					} else {
						this.sessionduration = dis.readLong();
					}

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isPeakHour = null;
					} else {
						this.isPeakHour = dis.readBoolean();
					}

					this.ActualCost = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.totalkwhconsumed = (BigDecimal) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.sessionduration = null;
					} else {
						this.sessionduration = dis.readLong();
					}

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isPeakHour = null;
					} else {
						this.isPeakHour = dis.readBoolean();
					}

					this.ActualCost = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// BigDecimal

				dos.writeObject(this.totalkwhconsumed);

				// Long

				if (this.sessionduration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.sessionduration);
				}

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

				// Boolean

				if (this.isPeakHour == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isPeakHour);
				}

				// BigDecimal

				dos.writeObject(this.ActualCost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.totalkwhconsumed);

				// Long

				if (this.sessionduration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.sessionduration);
				}

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

				// Boolean

				if (this.isPeakHour == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isPeakHour);
				}

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.ActualCost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",totalkwhconsumed=" + String.valueOf(totalkwhconsumed));
			sb.append(",sessionduration=" + String.valueOf(sessionduration));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",enddatetimekey=" + String.valueOf(enddatetimekey));
			sb.append(",bookingkey=" + String.valueOf(bookingkey));
			sb.append(",paymentkey=" + String.valueOf(paymentkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append(",isPeakHour=" + String.valueOf(isPeakHour));
			sb.append(",ActualCost=" + String.valueOf(ActualCost));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			if (totalkwhconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(totalkwhconsumed);
			}

			sb.append("|");

			if (sessionduration == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionduration);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			sb.append(enddatetimekey);

			sb.append("|");

			if (bookingkey == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingkey);
			}

			sb.append("|");

			if (paymentkey == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentkey);
			}

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			if (isPeakHour == null) {
				sb.append("<null>");
			} else {
				sb.append(isPeakHour);
			}

			sb.append("|");

			if (ActualCost == null) {
				sb.append("<null>");
			} else {
				sb.append(ActualCost);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CalculateCostStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class PrepareRevenueStruct implements routines.system.IPersistableRow<PrepareRevenueStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public BigDecimal totalkwhconsumed;

		public BigDecimal getTotalkwhconsumed() {
			return this.totalkwhconsumed;
		}

		public Boolean totalkwhconsumedIsNullable() {
			return true;
		}

		public Boolean totalkwhconsumedIsKey() {
			return false;
		}

		public Integer totalkwhconsumedLength() {
			return 10;
		}

		public Integer totalkwhconsumedPrecision() {
			return 2;
		}

		public String totalkwhconsumedDefault() {

			return null;

		}

		public String totalkwhconsumedComment() {

			return "";

		}

		public String totalkwhconsumedPattern() {

			return "";

		}

		public String totalkwhconsumedOriginalDbColumnName() {

			return "totalkwhconsumed";

		}

		public Long sessionduration;

		public Long getSessionduration() {
			return this.sessionduration;
		}

		public Boolean sessiondurationIsNullable() {
			return true;
		}

		public Boolean sessiondurationIsKey() {
			return false;
		}

		public Integer sessiondurationLength() {
			return null;
		}

		public Integer sessiondurationPrecision() {
			return null;
		}

		public String sessiondurationDefault() {

			return null;

		}

		public String sessiondurationComment() {

			return "";

		}

		public String sessiondurationPattern() {

			return "";

		}

		public String sessiondurationOriginalDbColumnName() {

			return "sessionduration";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public int enddatetimekey;

		public int getEnddatetimekey() {
			return this.enddatetimekey;
		}

		public Boolean enddatetimekeyIsNullable() {
			return false;
		}

		public Boolean enddatetimekeyIsKey() {
			return true;
		}

		public Integer enddatetimekeyLength() {
			return 10;
		}

		public Integer enddatetimekeyPrecision() {
			return 0;
		}

		public String enddatetimekeyDefault() {

			return null;

		}

		public String enddatetimekeyComment() {

			return "";

		}

		public String enddatetimekeyPattern() {

			return "";

		}

		public String enddatetimekeyOriginalDbColumnName() {

			return "enddatetimekey";

		}

		public Integer bookingkey;

		public Integer getBookingkey() {
			return this.bookingkey;
		}

		public Boolean bookingkeyIsNullable() {
			return true;
		}

		public Boolean bookingkeyIsKey() {
			return false;
		}

		public Integer bookingkeyLength() {
			return 10;
		}

		public Integer bookingkeyPrecision() {
			return 0;
		}

		public String bookingkeyDefault() {

			return null;

		}

		public String bookingkeyComment() {

			return "";

		}

		public String bookingkeyPattern() {

			return "";

		}

		public String bookingkeyOriginalDbColumnName() {

			return "bookingkey";

		}

		public Integer paymentkey;

		public Integer getPaymentkey() {
			return this.paymentkey;
		}

		public Boolean paymentkeyIsNullable() {
			return true;
		}

		public Boolean paymentkeyIsKey() {
			return false;
		}

		public Integer paymentkeyLength() {
			return 10;
		}

		public Integer paymentkeyPrecision() {
			return 0;
		}

		public String paymentkeyDefault() {

			return null;

		}

		public String paymentkeyComment() {

			return "";

		}

		public String paymentkeyPattern() {

			return "";

		}

		public String paymentkeyOriginalDbColumnName() {

			return "paymentkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return null;
		}

		public Integer powerconsumedPrecision() {
			return null;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		public Boolean isPeakHour;

		public Boolean getIsPeakHour() {
			return this.isPeakHour;
		}

		public Boolean isPeakHourIsNullable() {
			return true;
		}

		public Boolean isPeakHourIsKey() {
			return false;
		}

		public Integer isPeakHourLength() {
			return null;
		}

		public Integer isPeakHourPrecision() {
			return null;
		}

		public String isPeakHourDefault() {

			return null;

		}

		public String isPeakHourComment() {

			return "";

		}

		public String isPeakHourPattern() {

			return "";

		}

		public String isPeakHourOriginalDbColumnName() {

			return "isPeakHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.totalkwhconsumed = (BigDecimal) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.sessionduration = null;
					} else {
						this.sessionduration = dis.readLong();
					}

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isPeakHour = null;
					} else {
						this.isPeakHour = dis.readBoolean();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.totalkwhconsumed = (BigDecimal) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.sessionduration = null;
					} else {
						this.sessionduration = dis.readLong();
					}

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isPeakHour = null;
					} else {
						this.isPeakHour = dis.readBoolean();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// BigDecimal

				dos.writeObject(this.totalkwhconsumed);

				// Long

				if (this.sessionduration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.sessionduration);
				}

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

				// Boolean

				if (this.isPeakHour == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isPeakHour);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.totalkwhconsumed);

				// Long

				if (this.sessionduration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.sessionduration);
				}

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

				// Boolean

				if (this.isPeakHour == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isPeakHour);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",totalkwhconsumed=" + String.valueOf(totalkwhconsumed));
			sb.append(",sessionduration=" + String.valueOf(sessionduration));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",enddatetimekey=" + String.valueOf(enddatetimekey));
			sb.append(",bookingkey=" + String.valueOf(bookingkey));
			sb.append(",paymentkey=" + String.valueOf(paymentkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append(",isPeakHour=" + String.valueOf(isPeakHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			if (totalkwhconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(totalkwhconsumed);
			}

			sb.append("|");

			if (sessionduration == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionduration);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			sb.append(enddatetimekey);

			sb.append("|");

			if (bookingkey == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingkey);
			}

			sb.append("|");

			if (paymentkey == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentkey);
			}

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			if (isPeakHour == null) {
				sb.append("<null>");
			} else {
				sb.append(isPeakHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(PrepareRevenueStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class PaymentOptionalStruct implements routines.system.IPersistableRow<PaymentOptionalStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public int enddatetimekey;

		public int getEnddatetimekey() {
			return this.enddatetimekey;
		}

		public Boolean enddatetimekeyIsNullable() {
			return false;
		}

		public Boolean enddatetimekeyIsKey() {
			return true;
		}

		public Integer enddatetimekeyLength() {
			return 10;
		}

		public Integer enddatetimekeyPrecision() {
			return 0;
		}

		public String enddatetimekeyDefault() {

			return null;

		}

		public String enddatetimekeyComment() {

			return "";

		}

		public String enddatetimekeyPattern() {

			return "";

		}

		public String enddatetimekeyOriginalDbColumnName() {

			return "enddatetimekey";

		}

		public Integer bookingkey;

		public Integer getBookingkey() {
			return this.bookingkey;
		}

		public Boolean bookingkeyIsNullable() {
			return true;
		}

		public Boolean bookingkeyIsKey() {
			return false;
		}

		public Integer bookingkeyLength() {
			return 10;
		}

		public Integer bookingkeyPrecision() {
			return 0;
		}

		public String bookingkeyDefault() {

			return null;

		}

		public String bookingkeyComment() {

			return "";

		}

		public String bookingkeyPattern() {

			return "";

		}

		public String bookingkeyOriginalDbColumnName() {

			return "bookingkey";

		}

		public Integer paymentkey;

		public Integer getPaymentkey() {
			return this.paymentkey;
		}

		public Boolean paymentkeyIsNullable() {
			return true;
		}

		public Boolean paymentkeyIsKey() {
			return false;
		}

		public Integer paymentkeyLength() {
			return 10;
		}

		public Integer paymentkeyPrecision() {
			return 0;
		}

		public String paymentkeyDefault() {

			return null;

		}

		public String paymentkeyComment() {

			return "";

		}

		public String paymentkeyPattern() {

			return "";

		}

		public String paymentkeyOriginalDbColumnName() {

			return "paymentkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.paymentkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// Integer

				writeInteger(this.paymentkey, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",enddatetimekey=" + String.valueOf(enddatetimekey));
			sb.append(",bookingkey=" + String.valueOf(bookingkey));
			sb.append(",paymentkey=" + String.valueOf(paymentkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			sb.append(enddatetimekey);

			sb.append("|");

			if (bookingkey == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingkey);
			}

			sb.append("|");

			if (paymentkey == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentkey);
			}

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(PaymentOptionalStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class BookingOptionalStruct implements routines.system.IPersistableRow<BookingOptionalStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public int enddatetimekey;

		public int getEnddatetimekey() {
			return this.enddatetimekey;
		}

		public Boolean enddatetimekeyIsNullable() {
			return false;
		}

		public Boolean enddatetimekeyIsKey() {
			return true;
		}

		public Integer enddatetimekeyLength() {
			return 10;
		}

		public Integer enddatetimekeyPrecision() {
			return 0;
		}

		public String enddatetimekeyDefault() {

			return null;

		}

		public String enddatetimekeyComment() {

			return "";

		}

		public String enddatetimekeyPattern() {

			return "";

		}

		public String enddatetimekeyOriginalDbColumnName() {

			return "enddatetimekey";

		}

		public Integer bookingkey;

		public Integer getBookingkey() {
			return this.bookingkey;
		}

		public Boolean bookingkeyIsNullable() {
			return true;
		}

		public Boolean bookingkeyIsKey() {
			return false;
		}

		public Integer bookingkeyLength() {
			return 10;
		}

		public Integer bookingkeyPrecision() {
			return 0;
		}

		public String bookingkeyDefault() {

			return null;

		}

		public String bookingkeyComment() {

			return "";

		}

		public String bookingkeyPattern() {

			return "";

		}

		public String bookingkeyOriginalDbColumnName() {

			return "bookingkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.bookingkey = readInteger(dis);

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// Integer

				writeInteger(this.bookingkey, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",enddatetimekey=" + String.valueOf(enddatetimekey));
			sb.append(",bookingkey=" + String.valueOf(bookingkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			sb.append(enddatetimekey);

			sb.append("|");

			if (bookingkey == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingkey);
			}

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(BookingOptionalStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class EndTimeFoundStruct implements routines.system.IPersistableRow<EndTimeFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public int enddatetimekey;

		public int getEnddatetimekey() {
			return this.enddatetimekey;
		}

		public Boolean enddatetimekeyIsNullable() {
			return false;
		}

		public Boolean enddatetimekeyIsKey() {
			return true;
		}

		public Integer enddatetimekeyLength() {
			return 10;
		}

		public Integer enddatetimekeyPrecision() {
			return 0;
		}

		public String enddatetimekeyDefault() {

			return null;

		}

		public String enddatetimekeyComment() {

			return "";

		}

		public String enddatetimekeyPattern() {

			return "";

		}

		public String enddatetimekeyOriginalDbColumnName() {

			return "enddatetimekey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.enddatetimekey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// int

				dos.writeInt(this.enddatetimekey);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",enddatetimekey=" + String.valueOf(enddatetimekey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			sb.append(enddatetimekey);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(EndTimeFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class EndTimeNotFoundStruct implements routines.system.IPersistableRow<EndTimeNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(EndTimeNotFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class StartTimeFoundStruct implements routines.system.IPersistableRow<StartTimeFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public int startdatetimekey;

		public int getStartdatetimekey() {
			return this.startdatetimekey;
		}

		public Boolean startdatetimekeyIsNullable() {
			return false;
		}

		public Boolean startdatetimekeyIsKey() {
			return true;
		}

		public Integer startdatetimekeyLength() {
			return 10;
		}

		public Integer startdatetimekeyPrecision() {
			return 0;
		}

		public String startdatetimekeyDefault() {

			return null;

		}

		public String startdatetimekeyComment() {

			return "";

		}

		public String startdatetimekeyPattern() {

			return "";

		}

		public String startdatetimekeyOriginalDbColumnName() {

			return "startdatetimekey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.startdatetimekey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// int

				dos.writeInt(this.startdatetimekey);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",startdatetimekey=" + String.valueOf(startdatetimekey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			sb.append(startdatetimekey);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(StartTimeFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class StartTimeNotFoundStruct implements routines.system.IPersistableRow<StartTimeNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(StartTimeNotFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class VehicleFoundStruct implements routines.system.IPersistableRow<VehicleFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int userkey;

		public int getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return false;
		}

		public Boolean userkeyIsKey() {
			return true;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return null;

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.userkey = dis.readInt();

					this.vehiclekey = dis.readInt();

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.userkey);

				// int

				dos.writeInt(this.vehiclekey);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append(",vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(userkey);

			sb.append("|");

			sb.append(vehiclekey);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(VehicleFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class VehicleNotFoundStruct implements routines.system.IPersistableRow<VehicleNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(VehicleNotFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class PMFoundStruct implements routines.system.IPersistableRow<PMFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return null;

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public java.util.Date StartDate;

		public java.util.Date getStartDate() {
			return this.StartDate;
		}

		public Boolean StartDateIsNullable() {
			return true;
		}

		public Boolean StartDateIsKey() {
			return false;
		}

		public Integer StartDateLength() {
			return null;
		}

		public Integer StartDatePrecision() {
			return null;
		}

		public String StartDateDefault() {

			return null;

		}

		public String StartDateComment() {

			return "";

		}

		public String StartDatePattern() {

			return "yyyy-MM-dd";

		}

		public String StartDateOriginalDbColumnName() {

			return "StartDate";

		}

		public java.util.Date EndDate;

		public java.util.Date getEndDate() {
			return this.EndDate;
		}

		public Boolean EndDateIsNullable() {
			return true;
		}

		public Boolean EndDateIsKey() {
			return false;
		}

		public Integer EndDateLength() {
			return null;
		}

		public Integer EndDatePrecision() {
			return null;
		}

		public String EndDateDefault() {

			return null;

		}

		public String EndDateComment() {

			return "";

		}

		public String EndDatePattern() {

			return "yyyy-MM-dd";

		}

		public String EndDateOriginalDbColumnName() {

			return "EndDate";

		}

		public Integer StartHour;

		public Integer getStartHour() {
			return this.StartHour;
		}

		public Boolean StartHourIsNullable() {
			return true;
		}

		public Boolean StartHourIsKey() {
			return false;
		}

		public Integer StartHourLength() {
			return null;
		}

		public Integer StartHourPrecision() {
			return null;
		}

		public String StartHourDefault() {

			return null;

		}

		public String StartHourComment() {

			return "";

		}

		public String StartHourPattern() {

			return "dd-MM-yyyy";

		}

		public String StartHourOriginalDbColumnName() {

			return "StartHour";

		}

		public Integer EndHour;

		public Integer getEndHour() {
			return this.EndHour;
		}

		public Boolean EndHourIsNullable() {
			return true;
		}

		public Boolean EndHourIsKey() {
			return false;
		}

		public Integer EndHourLength() {
			return null;
		}

		public Integer EndHourPrecision() {
			return null;
		}

		public String EndHourDefault() {

			return null;

		}

		public String EndHourComment() {

			return "";

		}

		public String EndHourPattern() {

			return "";

		}

		public String EndHourOriginalDbColumnName() {

			return "EndHour";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.pricingmodelkey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

					this.StartDate = readDate(dis);

					this.EndDate = readDate(dis);

					this.StartHour = readInteger(dis);

					this.EndHour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// int

				dos.writeInt(this.pricingmodelkey);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

				// java.util.Date

				writeDate(this.StartDate, dos);

				// java.util.Date

				writeDate(this.EndDate, dos);

				// Integer

				writeInteger(this.StartHour, dos);

				// Integer

				writeInteger(this.EndHour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",StartDate=" + String.valueOf(StartDate));
			sb.append(",EndDate=" + String.valueOf(EndDate));
			sb.append(",StartHour=" + String.valueOf(StartHour));
			sb.append(",EndHour=" + String.valueOf(EndHour));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			sb.append(pricingmodelkey);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (StartDate == null) {
				sb.append("<null>");
			} else {
				sb.append(StartDate);
			}

			sb.append("|");

			if (EndDate == null) {
				sb.append("<null>");
			} else {
				sb.append(EndDate);
			}

			sb.append("|");

			if (StartHour == null) {
				sb.append("<null>");
			} else {
				sb.append(StartHour);
			}

			sb.append("|");

			if (EndHour == null) {
				sb.append("<null>");
			} else {
				sb.append(EndHour);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(PMFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class PMNotFoundStruct implements routines.system.IPersistableRow<PMNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(PMNotFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class CPFoundStruct implements routines.system.IPersistableRow<CPFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public int stationkey;

		public int getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return false;
		}

		public Boolean stationkeyIsKey() {
			return true;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return null;

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

					this.stationkey = dis.readInt();

					this.chargepointkey = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

				// int

				dos.writeInt(this.stationkey);

				// int

				dos.writeInt(this.chargepointkey);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append(",chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			sb.append(chargepointkey);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CPFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class CPNotFoundStruct implements routines.system.IPersistableRow<CPNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CPNotFoundStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];

		public int sessionid;

		public int getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return false;
		}

		public Boolean sessionidIsKey() {
			return true;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.sessionid);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.sessionid);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(sessionid);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tDBInput_1Struct implements routines.system.IPersistableRow<after_tDBInput_1Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int sessionid;

		public int getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return false;
		}

		public Boolean sessionidIsKey() {
			return true;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		public BigDecimal powerconsumed;

		public BigDecimal getPowerconsumed() {
			return this.powerconsumed;
		}

		public Boolean powerconsumedIsNullable() {
			return true;
		}

		public Boolean powerconsumedIsKey() {
			return false;
		}

		public Integer powerconsumedLength() {
			return 10;
		}

		public Integer powerconsumedPrecision() {
			return 2;
		}

		public String powerconsumedDefault() {

			return null;

		}

		public String powerconsumedComment() {

			return "";

		}

		public String powerconsumedPattern() {

			return "";

		}

		public String powerconsumedOriginalDbColumnName() {

			return "powerconsumed";

		}

		public java.util.Date startdatetime;

		public java.util.Date getStartdatetime() {
			return this.startdatetime;
		}

		public Boolean startdatetimeIsNullable() {
			return true;
		}

		public Boolean startdatetimeIsKey() {
			return false;
		}

		public Integer startdatetimeLength() {
			return 29;
		}

		public Integer startdatetimePrecision() {
			return 6;
		}

		public String startdatetimeDefault() {

			return null;

		}

		public String startdatetimeComment() {

			return "";

		}

		public String startdatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String startdatetimeOriginalDbColumnName() {

			return "startdatetime";

		}

		public java.util.Date enddatetime;

		public java.util.Date getEnddatetime() {
			return this.enddatetime;
		}

		public Boolean enddatetimeIsNullable() {
			return true;
		}

		public Boolean enddatetimeIsKey() {
			return false;
		}

		public Integer enddatetimeLength() {
			return 29;
		}

		public Integer enddatetimePrecision() {
			return 6;
		}

		public String enddatetimeDefault() {

			return null;

		}

		public String enddatetimeComment() {

			return "";

		}

		public String enddatetimePattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String enddatetimeOriginalDbColumnName() {

			return "enddatetime";

		}

		public Boolean isinterrupted;

		public Boolean getIsinterrupted() {
			return this.isinterrupted;
		}

		public Boolean isinterruptedIsNullable() {
			return true;
		}

		public Boolean isinterruptedIsKey() {
			return false;
		}

		public Integer isinterruptedLength() {
			return 1;
		}

		public Integer isinterruptedPrecision() {
			return 0;
		}

		public String isinterruptedDefault() {

			return null;

		}

		public String isinterruptedComment() {

			return "";

		}

		public String isinterruptedPattern() {

			return "";

		}

		public String isinterruptedOriginalDbColumnName() {

			return "isinterrupted";

		}

		public Integer interruptedreasonid;

		public Integer getInterruptedreasonid() {
			return this.interruptedreasonid;
		}

		public Boolean interruptedreasonidIsNullable() {
			return true;
		}

		public Boolean interruptedreasonidIsKey() {
			return false;
		}

		public Integer interruptedreasonidLength() {
			return 10;
		}

		public Integer interruptedreasonidPrecision() {
			return 0;
		}

		public String interruptedreasonidDefault() {

			return null;

		}

		public String interruptedreasonidComment() {

			return "";

		}

		public String interruptedreasonidPattern() {

			return "";

		}

		public String interruptedreasonidOriginalDbColumnName() {

			return "interruptedreasonid";

		}

		public int vehicleid;

		public int getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return false;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public int stationid;

		public int getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return false;
		}

		public Boolean stationidIsKey() {
			return false;
		}

		public Integer stationidLength() {
			return 10;
		}

		public Integer stationidPrecision() {
			return 0;
		}

		public String stationidDefault() {

			return null;

		}

		public String stationidComment() {

			return "";

		}

		public String stationidPattern() {

			return "";

		}

		public String stationidOriginalDbColumnName() {

			return "stationid";

		}

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public int pricingmodelid;

		public int getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return false;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.sessionid;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final after_tDBInput_1Struct other = (after_tDBInput_1Struct) obj;

			if (this.sessionid != other.sessionid)
				return false;

			return true;
		}

		public void copyDataTo(after_tDBInput_1Struct other) {

			other.sessionid = this.sessionid;
			other.powerconsumed = this.powerconsumed;
			other.startdatetime = this.startdatetime;
			other.enddatetime = this.enddatetime;
			other.isinterrupted = this.isinterrupted;
			other.interruptedreasonid = this.interruptedreasonid;
			other.vehicleid = this.vehicleid;
			other.stationid = this.stationid;
			other.chargepointid = this.chargepointid;
			other.pricingmodelid = this.pricingmodelid;
			other.bookingid = this.bookingid;

		}

		public void copyKeysDataTo(after_tDBInput_1Struct other) {

			other.sessionid = this.sessionid;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = dis.readInt();

					this.powerconsumed = (BigDecimal) dis.readObject();

					this.startdatetime = readDate(dis);

					this.enddatetime = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.isinterrupted = null;
					} else {
						this.isinterrupted = dis.readBoolean();
					}

					this.interruptedreasonid = readInteger(dis);

					this.vehicleid = dis.readInt();

					this.stationid = dis.readInt();

					this.chargepointid = dis.readInt();

					this.pricingmodelid = dis.readInt();

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.sessionid);

				// BigDecimal

				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.sessionid);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.powerconsumed);

				// java.util.Date

				writeDate(this.startdatetime, dos);

				// java.util.Date

				writeDate(this.enddatetime, dos);

				// Boolean

				if (this.isinterrupted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.isinterrupted);
				}

				// Integer

				writeInteger(this.interruptedreasonid, dos);

				// int

				dos.writeInt(this.vehicleid);

				// int

				dos.writeInt(this.stationid);

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.pricingmodelid);

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("sessionid=" + String.valueOf(sessionid));
			sb.append(",powerconsumed=" + String.valueOf(powerconsumed));
			sb.append(",startdatetime=" + String.valueOf(startdatetime));
			sb.append(",enddatetime=" + String.valueOf(enddatetime));
			sb.append(",isinterrupted=" + String.valueOf(isinterrupted));
			sb.append(",interruptedreasonid=" + String.valueOf(interruptedreasonid));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(sessionid);

			sb.append("|");

			if (powerconsumed == null) {
				sb.append("<null>");
			} else {
				sb.append(powerconsumed);
			}

			sb.append("|");

			if (startdatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(startdatetime);
			}

			sb.append("|");

			if (enddatetime == null) {
				sb.append("<null>");
			} else {
				sb.append(enddatetime);
			}

			sb.append("|");

			if (isinterrupted == null) {
				sb.append("<null>");
			} else {
				sb.append(isinterrupted);
			}

			sb.append("|");

			if (interruptedreasonid == null) {
				sb.append("<null>");
			} else {
				sb.append(interruptedreasonid);
			}

			sb.append("|");

			sb.append(vehicleid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			sb.append(chargepointid);

			sb.append("|");

			sb.append(pricingmodelid);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tDBInput_1Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.sessionid, other.sessionid);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_1", "DevE07_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				tDBInput_2Process(globalMap);
				tDBInput_3Process(globalMap);
				tDBInput_4Process(globalMap);
				tDBInput_5Process(globalMap);
				tDBInput_6Process(globalMap);
				tDBInput_7Process(globalMap);
				tDBInput_8Process(globalMap);
				tDBInput_9Process(globalMap);
				tDBInput_10Process(globalMap);
				tDBInput_11Process(globalMap);

				row1Struct row1 = new row1Struct();
				CPFoundStruct CPFound = new CPFoundStruct();
				PMFoundStruct PMFound = new PMFoundStruct();
				VehicleFoundStruct VehicleFound = new VehicleFoundStruct();
				StartTimeFoundStruct StartTimeFound = new StartTimeFoundStruct();
				EndTimeFoundStruct EndTimeFound = new EndTimeFoundStruct();
				BookingOptionalStruct BookingOptional = new BookingOptionalStruct();
				PaymentOptionalStruct PaymentOptional = new PaymentOptionalStruct();
				PrepareRevenueStruct PrepareRevenue = new PrepareRevenueStruct();
				CalculateCostStruct CalculateCost = new CalculateCostStruct();
				CalculateRevenueStruct CalculateRevenue = new CalculateRevenueStruct();
				EndTimeNotFoundStruct EndTimeNotFound = new EndTimeNotFoundStruct();
				StartTimeNotFoundStruct StartTimeNotFound = new StartTimeNotFoundStruct();
				VehicleNotFoundStruct VehicleNotFound = new VehicleNotFoundStruct();
				PMNotFoundStruct PMNotFound = new PMNotFoundStruct();
				CPNotFoundStruct CPNotFound = new CPNotFoundStruct();

				/**
				 * [tLogRow_1 begin ] start
				 */

				sh("tLogRow_1");

				s(currentComponent = "tLogRow_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "CalculateRevenue");

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
							log4jParamters_tLogRow_1.append("Parameters:");
							log4jParamters_tLogRow_1.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_1 - " + (log4jParamters_tLogRow_1));
						}
					}
					new BytesLimit65535_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_1", "tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[30];

					public void addRow(String[] row) {

						for (int i = 0; i < 30; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 29 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 29 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|%5$-");
							sbformat.append(colLengths[4]);
							sbformat.append("s");

							sbformat.append("|%6$-");
							sbformat.append(colLengths[5]);
							sbformat.append("s");

							sbformat.append("|%7$-");
							sbformat.append(colLengths[6]);
							sbformat.append("s");

							sbformat.append("|%8$-");
							sbformat.append(colLengths[7]);
							sbformat.append("s");

							sbformat.append("|%9$-");
							sbformat.append(colLengths[8]);
							sbformat.append("s");

							sbformat.append("|%10$-");
							sbformat.append(colLengths[9]);
							sbformat.append("s");

							sbformat.append("|%11$-");
							sbformat.append(colLengths[10]);
							sbformat.append("s");

							sbformat.append("|%12$-");
							sbformat.append(colLengths[11]);
							sbformat.append("s");

							sbformat.append("|%13$-");
							sbformat.append(colLengths[12]);
							sbformat.append("s");

							sbformat.append("|%14$-");
							sbformat.append(colLengths[13]);
							sbformat.append("s");

							sbformat.append("|%15$-");
							sbformat.append(colLengths[14]);
							sbformat.append("s");

							sbformat.append("|%16$-");
							sbformat.append(colLengths[15]);
							sbformat.append("s");

							sbformat.append("|%17$-");
							sbformat.append(colLengths[16]);
							sbformat.append("s");

							sbformat.append("|%18$-");
							sbformat.append(colLengths[17]);
							sbformat.append("s");

							sbformat.append("|%19$-");
							sbformat.append(colLengths[18]);
							sbformat.append("s");

							sbformat.append("|%20$-");
							sbformat.append(colLengths[19]);
							sbformat.append("s");

							sbformat.append("|%21$-");
							sbformat.append(colLengths[20]);
							sbformat.append("s");

							sbformat.append("|%22$-");
							sbformat.append(colLengths[21]);
							sbformat.append("s");

							sbformat.append("|%23$-");
							sbformat.append(colLengths[22]);
							sbformat.append("s");

							sbformat.append("|%24$-");
							sbformat.append(colLengths[23]);
							sbformat.append("s");

							sbformat.append("|%25$-");
							sbformat.append(colLengths[24]);
							sbformat.append("s");

							sbformat.append("|%26$-");
							sbformat.append(colLengths[25]);
							sbformat.append("s");

							sbformat.append("|%27$-");
							sbformat.append(colLengths[26]);
							sbformat.append("s");

							sbformat.append("|%28$-");
							sbformat.append(colLengths[27]);
							sbformat.append("s");

							sbformat.append("|%29$-");
							sbformat.append(colLengths[28]);
							sbformat.append("s");

							sbformat.append("|%30$-");
							sbformat.append(colLengths[29]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[3] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[4] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[5] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[6] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[7] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[8] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[9] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[10] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[11] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[12] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[13] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[14] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[15] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[16] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[17] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[18] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[19] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[20] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[21] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[22] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[23] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[24] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[25] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[26] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[27] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[28] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[29] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("tLogRow_1");
				util_tLogRow_1.addRow(new String[] { "sessionid", "totalkwhconsumed", "sessionduration", "totalrevenue",
						"paymentamount", "userkey", "vehiclekey", "stationkey", "chargepointkey", "pricingmodelkey",
						"startdatetimekey", "enddatetimekey", "bookingkey", "paymentkey", "powerconsumed",
						"startdatetime", "enddatetime", "isinterrupted", "interruptedreasonid", "vehicleid",
						"stationid", "chargepointid", "pricingmodelid", "bookingid", "StartDate", "EndDate",
						"StartHour", "EndHour", "isPeakHour", "ActualCost", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tMap_10 begin ] start
				 */

				sh("tMap_10");

				s(currentComponent = "tMap_10");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "CalculateCost");

				int tos_count_tMap_10 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_10 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_10 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_10 = new StringBuilder();
							log4jParamters_tMap_10.append("Parameters:");
							log4jParamters_tMap_10.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_10.append(" | ");
							log4jParamters_tMap_10.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_10.append(" | ");
							log4jParamters_tMap_10.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_10.append(" | ");
							log4jParamters_tMap_10.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_10.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_10 - " + (log4jParamters_tMap_10));
						}
					}
					new BytesLimit65535_tMap_10().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_10", "tMap_10", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_CalculateCost_tMap_10 = 0;

				int count_row11_tMap_10 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct> tHash_Lookup_row11 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct>) globalMap
						.get("tHash_Lookup_row11"));

				row11Struct row11HashKey = new row11Struct();
				row11Struct row11Default = new row11Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_10__Struct {
				}
				Var__tMap_10__Struct Var__tMap_10 = new Var__tMap_10__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_CalculateRevenue_tMap_10 = 0;

				CalculateRevenueStruct CalculateRevenue_tmp = new CalculateRevenueStruct();
// ###############################

				/**
				 * [tMap_10 begin ] stop
				 */

				/**
				 * [tMap_9 begin ] start
				 */

				sh("tMap_9");

				s(currentComponent = "tMap_9");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "PrepareRevenue");

				int tos_count_tMap_9 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_9 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_9 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_9 = new StringBuilder();
							log4jParamters_tMap_9.append("Parameters:");
							log4jParamters_tMap_9.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_9.append(" | ");
							log4jParamters_tMap_9.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_9.append(" | ");
							log4jParamters_tMap_9.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_9.append(" | ");
							log4jParamters_tMap_9.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_9.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_9 - " + (log4jParamters_tMap_9));
						}
					}
					new BytesLimit65535_tMap_9().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_9", "tMap_9", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_PrepareRevenue_tMap_9 = 0;

				int count_row10_tMap_9 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct> tHash_Lookup_row10 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct>) globalMap
						.get("tHash_Lookup_row10"));

				row10Struct row10HashKey = new row10Struct();
				row10Struct row10Default = new row10Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_9__Struct {
				}
				Var__tMap_9__Struct Var__tMap_9 = new Var__tMap_9__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_CalculateCost_tMap_9 = 0;

				CalculateCostStruct CalculateCost_tmp = new CalculateCostStruct();
// ###############################

				/**
				 * [tMap_9 begin ] stop
				 */

				/**
				 * [tMap_8 begin ] start
				 */

				sh("tMap_8");

				s(currentComponent = "tMap_8");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "PaymentOptional");

				int tos_count_tMap_8 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_8 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_8 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_8 = new StringBuilder();
							log4jParamters_tMap_8.append("Parameters:");
							log4jParamters_tMap_8.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_8.append(" | ");
							log4jParamters_tMap_8.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_8.append(" | ");
							log4jParamters_tMap_8.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_8.append(" | ");
							log4jParamters_tMap_8.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_8.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_8 - " + (log4jParamters_tMap_8));
						}
					}
					new BytesLimit65535_tMap_8().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_8", "tMap_8", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_PaymentOptional_tMap_8 = 0;

				int count_row9_tMap_8 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct> tHash_Lookup_row9 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct>) globalMap
						.get("tHash_Lookup_row9"));

				row9Struct row9HashKey = new row9Struct();
				row9Struct row9Default = new row9Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_8__Struct {
				}
				Var__tMap_8__Struct Var__tMap_8 = new Var__tMap_8__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_PrepareRevenue_tMap_8 = 0;

				PrepareRevenueStruct PrepareRevenue_tmp = new PrepareRevenueStruct();
// ###############################

				/**
				 * [tMap_8 begin ] stop
				 */

				/**
				 * [tMap_7 begin ] start
				 */

				sh("tMap_7");

				s(currentComponent = "tMap_7");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "BookingOptional");

				int tos_count_tMap_7 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_7 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_7 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_7 = new StringBuilder();
							log4jParamters_tMap_7.append("Parameters:");
							log4jParamters_tMap_7.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_7.append(" | ");
							log4jParamters_tMap_7.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_7.append(" | ");
							log4jParamters_tMap_7.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_7.append(" | ");
							log4jParamters_tMap_7.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_7.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_7 - " + (log4jParamters_tMap_7));
						}
					}
					new BytesLimit65535_tMap_7().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_7", "tMap_7", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_BookingOptional_tMap_7 = 0;

				int count_row8_tMap_7 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct> tHash_Lookup_row8 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct>) globalMap
						.get("tHash_Lookup_row8"));

				row8Struct row8HashKey = new row8Struct();
				row8Struct row8Default = new row8Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_7__Struct {
				}
				Var__tMap_7__Struct Var__tMap_7 = new Var__tMap_7__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_PaymentOptional_tMap_7 = 0;

				PaymentOptionalStruct PaymentOptional_tmp = new PaymentOptionalStruct();
// ###############################

				/**
				 * [tMap_7 begin ] stop
				 */

				/**
				 * [tMap_6 begin ] start
				 */

				sh("tMap_6");

				s(currentComponent = "tMap_6");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "EndTimeFound");

				int tos_count_tMap_6 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_6 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_6 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_6 = new StringBuilder();
							log4jParamters_tMap_6.append("Parameters:");
							log4jParamters_tMap_6.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_6.append(" | ");
							log4jParamters_tMap_6.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_6.append(" | ");
							log4jParamters_tMap_6.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_6.append(" | ");
							log4jParamters_tMap_6.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_6.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_6 - " + (log4jParamters_tMap_6));
						}
					}
					new BytesLimit65535_tMap_6().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_6", "tMap_6", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_EndTimeFound_tMap_6 = 0;

				int count_row7_tMap_6 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct> tHash_Lookup_row7 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct>) globalMap
						.get("tHash_Lookup_row7"));

				row7Struct row7HashKey = new row7Struct();
				row7Struct row7Default = new row7Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_6__Struct {
				}
				Var__tMap_6__Struct Var__tMap_6 = new Var__tMap_6__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_BookingOptional_tMap_6 = 0;

				BookingOptionalStruct BookingOptional_tmp = new BookingOptionalStruct();
// ###############################

				/**
				 * [tMap_6 begin ] stop
				 */

				/**
				 * [tWarn_5 begin ] start
				 */

				sh("tWarn_5");

				s(currentComponent = "tWarn_5");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "EndTimeNotFound");

				int tos_count_tWarn_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_5 = new StringBuilder();
							log4jParamters_tWarn_5.append("Parameters:");
							log4jParamters_tWarn_5.append("MESSAGE" + " = "
									+ "\"Warning: End Time not found for Session Fact '\" + EndTimeNotFound.sessionid + \"'. Please verify the session data.\"");
							log4jParamters_tWarn_5.append(" | ");
							log4jParamters_tWarn_5.append("CODE" + " = " + "42");
							log4jParamters_tWarn_5.append(" | ");
							log4jParamters_tWarn_5.append("PRIORITY" + " = " + "4");
							log4jParamters_tWarn_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tWarn_5 - " + (log4jParamters_tWarn_5));
						}
					}
					new BytesLimit65535_tWarn_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tWarn_5", "tWarn_5", "tWarn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tWarn_5 begin ] stop
				 */

				/**
				 * [tMap_5 begin ] start
				 */

				sh("tMap_5");

				s(currentComponent = "tMap_5");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "StartTimeFound");

				int tos_count_tMap_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_5 = new StringBuilder();
							log4jParamters_tMap_5.append("Parameters:");
							log4jParamters_tMap_5.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_5.append(" | ");
							log4jParamters_tMap_5.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_5.append(" | ");
							log4jParamters_tMap_5.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_5.append(" | ");
							log4jParamters_tMap_5.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_5 - " + (log4jParamters_tMap_5));
						}
					}
					new BytesLimit65535_tMap_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_5", "tMap_5", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_StartTimeFound_tMap_5 = 0;

				int count_row6_tMap_5 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct> tHash_Lookup_row6 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct>) globalMap
						.get("tHash_Lookup_row6"));

				row6Struct row6HashKey = new row6Struct();
				row6Struct row6Default = new row6Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_5__Struct {
				}
				Var__tMap_5__Struct Var__tMap_5 = new Var__tMap_5__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_EndTimeFound_tMap_5 = 0;

				EndTimeFoundStruct EndTimeFound_tmp = new EndTimeFoundStruct();
				int count_EndTimeNotFound_tMap_5 = 0;

				EndTimeNotFoundStruct EndTimeNotFound_tmp = new EndTimeNotFoundStruct();
// ###############################

				/**
				 * [tMap_5 begin ] stop
				 */

				/**
				 * [tWarn_4 begin ] start
				 */

				sh("tWarn_4");

				s(currentComponent = "tWarn_4");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "StartTimeNotFound");

				int tos_count_tWarn_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_4 = new StringBuilder();
							log4jParamters_tWarn_4.append("Parameters:");
							log4jParamters_tWarn_4.append("MESSAGE" + " = "
									+ "\"Warning: Start Time not found for Session Fact '\" + StartTimeNotFound.sessionid + \"'. Please verify the session data.\"");
							log4jParamters_tWarn_4.append(" | ");
							log4jParamters_tWarn_4.append("CODE" + " = " + "42");
							log4jParamters_tWarn_4.append(" | ");
							log4jParamters_tWarn_4.append("PRIORITY" + " = " + "4");
							log4jParamters_tWarn_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tWarn_4 - " + (log4jParamters_tWarn_4));
						}
					}
					new BytesLimit65535_tWarn_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tWarn_4", "tWarn_4", "tWarn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tWarn_4 begin ] stop
				 */

				/**
				 * [tMap_4 begin ] start
				 */

				sh("tMap_4");

				s(currentComponent = "tMap_4");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "VehicleFound");

				int tos_count_tMap_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_4 = new StringBuilder();
							log4jParamters_tMap_4.append("Parameters:");
							log4jParamters_tMap_4.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_4.append(" | ");
							log4jParamters_tMap_4.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_4.append(" | ");
							log4jParamters_tMap_4.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_4.append(" | ");
							log4jParamters_tMap_4.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_4 - " + (log4jParamters_tMap_4));
						}
					}
					new BytesLimit65535_tMap_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_4", "tMap_4", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_VehicleFound_tMap_4 = 0;

				int count_row5_tMap_4 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) globalMap
						.get("tHash_Lookup_row5"));

				row5Struct row5HashKey = new row5Struct();
				row5Struct row5Default = new row5Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_4__Struct {
				}
				Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_StartTimeFound_tMap_4 = 0;

				StartTimeFoundStruct StartTimeFound_tmp = new StartTimeFoundStruct();
				int count_StartTimeNotFound_tMap_4 = 0;

				StartTimeNotFoundStruct StartTimeNotFound_tmp = new StartTimeNotFoundStruct();
// ###############################

				/**
				 * [tMap_4 begin ] stop
				 */

				/**
				 * [tWarn_3 begin ] start
				 */

				sh("tWarn_3");

				s(currentComponent = "tWarn_3");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "VehicleNotFound");

				int tos_count_tWarn_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_3 = new StringBuilder();
							log4jParamters_tWarn_3.append("Parameters:");
							log4jParamters_tWarn_3.append("MESSAGE" + " = "
									+ "\"Warning: Vehicle not found for Session Fact '\" + VehicleNotFound.sessionid + \"'. Please verify the vehicle data.\"");
							log4jParamters_tWarn_3.append(" | ");
							log4jParamters_tWarn_3.append("CODE" + " = " + "42");
							log4jParamters_tWarn_3.append(" | ");
							log4jParamters_tWarn_3.append("PRIORITY" + " = " + "4");
							log4jParamters_tWarn_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tWarn_3 - " + (log4jParamters_tWarn_3));
						}
					}
					new BytesLimit65535_tWarn_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tWarn_3", "tWarn_3", "tWarn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tWarn_3 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				sh("tMap_3");

				s(currentComponent = "tMap_3");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "PMFound");

				int tos_count_tMap_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_3 = new StringBuilder();
							log4jParamters_tMap_3.append("Parameters:");
							log4jParamters_tMap_3.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_3.append(" | ");
							log4jParamters_tMap_3.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_3.append(" | ");
							log4jParamters_tMap_3.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_3.append(" | ");
							log4jParamters_tMap_3.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_3 - " + (log4jParamters_tMap_3));
						}
					}
					new BytesLimit65535_tMap_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_3", "tMap_3", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_PMFound_tMap_3 = 0;

				int count_row4_tMap_3 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) globalMap
						.get("tHash_Lookup_row4"));

				row4Struct row4HashKey = new row4Struct();
				row4Struct row4Default = new row4Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_VehicleFound_tMap_3 = 0;

				VehicleFoundStruct VehicleFound_tmp = new VehicleFoundStruct();
				int count_VehicleNotFound_tMap_3 = 0;

				VehicleNotFoundStruct VehicleNotFound_tmp = new VehicleNotFoundStruct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
				 */

				/**
				 * [tWarn_2 begin ] start
				 */

				sh("tWarn_2");

				s(currentComponent = "tWarn_2");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "PMNotFound");

				int tos_count_tWarn_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_2 = new StringBuilder();
							log4jParamters_tWarn_2.append("Parameters:");
							log4jParamters_tWarn_2.append("MESSAGE" + " = "
									+ "\"Warning: Pricing model not found for Session Fact '\" + PMNotFound.sessionid + \"'. Please verify the pricing model data.\"");
							log4jParamters_tWarn_2.append(" | ");
							log4jParamters_tWarn_2.append("CODE" + " = " + "42");
							log4jParamters_tWarn_2.append(" | ");
							log4jParamters_tWarn_2.append("PRIORITY" + " = " + "4");
							log4jParamters_tWarn_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tWarn_2 - " + (log4jParamters_tWarn_2));
						}
					}
					new BytesLimit65535_tWarn_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tWarn_2", "tWarn_2", "tWarn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tWarn_2 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				sh("tMap_2");

				s(currentComponent = "tMap_2");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "CPFound");

				int tos_count_tMap_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_2 = new StringBuilder();
							log4jParamters_tMap_2.append("Parameters:");
							log4jParamters_tMap_2.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_2.append(" | ");
							log4jParamters_tMap_2.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_2.append(" | ");
							log4jParamters_tMap_2.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_2.append(" | ");
							log4jParamters_tMap_2.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_2 - " + (log4jParamters_tMap_2));
						}
					}
					new BytesLimit65535_tMap_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_2", "tMap_2", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_CPFound_tMap_2 = 0;

				int count_row3_tMap_2 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) globalMap
						.get("tHash_Lookup_row3"));

				row3Struct row3HashKey = new row3Struct();
				row3Struct row3Default = new row3Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_PMFound_tMap_2 = 0;

				PMFoundStruct PMFound_tmp = new PMFoundStruct();
				int count_PMNotFound_tMap_2 = 0;

				PMNotFoundStruct PMNotFound_tmp = new PMNotFoundStruct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tWarn_1 begin ] start
				 */

				sh("tWarn_1");

				s(currentComponent = "tWarn_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "CPNotFound");

				int tos_count_tWarn_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_1 = new StringBuilder();
							log4jParamters_tWarn_1.append("Parameters:");
							log4jParamters_tWarn_1.append("MESSAGE" + " = "
									+ "\"Warning: Charging point not found for Session Fact '\" + CPNotFound.sessionid + \"'. Please verify the charging point data.\"");
							log4jParamters_tWarn_1.append(" | ");
							log4jParamters_tWarn_1.append("CODE" + " = " + "42");
							log4jParamters_tWarn_1.append(" | ");
							log4jParamters_tWarn_1.append("PRIORITY" + " = " + "4");
							log4jParamters_tWarn_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tWarn_1 - " + (log4jParamters_tWarn_1));
						}
					}
					new BytesLimit65535_tWarn_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tWarn_1", "tWarn_1", "tWarn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tWarn_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				sh("tMap_1");

				s(currentComponent = "tMap_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tMap_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_1 = new StringBuilder();
							log4jParamters_tMap_1.append("Parameters:");
							log4jParamters_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_1 - " + (log4jParamters_tMap_1));
						}
					}
					new BytesLimit65535_tMap_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_1", "tMap_1", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_row1_tMap_1 = 0;

				int count_row2_tMap_1 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) globalMap
						.get("tHash_Lookup_row2"));

				row2Struct row2HashKey = new row2Struct();
				row2Struct row2Default = new row2Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_CPFound_tMap_1 = 0;

				CPFoundStruct CPFound_tmp = new CPFoundStruct();
				int count_CPNotFound_tMap_1 = 0;

				CPNotFoundStruct CPNotFound_tmp = new CPNotFoundStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				sh("tDBInput_1");

				s(currentComponent = "tDBInput_1");

				cLabel = "\"session\"";

				int tos_count_tDBInput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_1 = new StringBuilder();
							log4jParamters_tDBInput_1.append("Parameters:");
							log4jParamters_tDBInput_1.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("CONNECTION" + " = " + "tDBConnection_1");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERY" + " = "
									+ "\"SELECT    \\\"session\\\".\\\"sessionid\\\",    \\\"session\\\".\\\"powerconsumed\\\",    \\\"session\\\".\\\"startdatetime\\\",    \\\"session\\\".\\\"enddatetime\\\",    \\\"session\\\".\\\"isinterrupted\\\",    \\\"session\\\".\\\"interruptedreasonid\\\",    \\\"session\\\".\\\"vehicleid\\\",    \\\"session\\\".\\\"stationid\\\",    \\\"session\\\".\\\"chargepointid\\\",    \\\"session\\\".\\\"pricingmodelid\\\",    \\\"session\\\".\\\"bookingid\\\"  FROM \\\"session\\\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("sessionid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("powerconsumed") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("startdatetime") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("enddatetime")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("isinterrupted") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("interruptedreasonid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("vehicleid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("stationid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("chargepointid")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("pricingmodelid") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("bookingid") + "}]");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_1 - " + (log4jParamters_tDBInput_1));
						}
					}
					new BytesLimit65535_tDBInput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_1", "\"session\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				conn_tDBInput_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_1");

				if (conn_tDBInput_1 != null) {
					if (conn_tDBInput_1.getMetaData() != null) {

						log.debug("tDBInput_1 - Uses an existing connection with username '"
								+ conn_tDBInput_1.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_1.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  \"session\".\"sessionid\", \n  \"session\".\"powerconsumed\", \n  \"session\".\"startdatetime\", \n  \"session\""
						+ ".\"enddatetime\", \n  \"session\".\"isinterrupted\", \n  \"session\".\"interruptedreasonid\", \n  \"session\".\"vehicleid\""
						+ ", \n  \"session\".\"stationid\", \n  \"session\".\"chargepointid\", \n  \"session\".\"pricingmodelid\", \n  \"session\".\"bo"
						+ "okingid\"\n FROM \"session\"";

				log.debug("tDBInput_1 - Executing the query: '" + dbquery_tDBInput_1 + "'.");

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);

				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					log.debug("tDBInput_1 - Retrieving records from the database.");

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.sessionid = 0;
						} else {

							row1.sessionid = rs_tDBInput_1.getInt(1);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.powerconsumed = null;
						} else {

							row1.powerconsumed = rs_tDBInput_1.getBigDecimal(2);
							if (rs_tDBInput_1.wasNull()) {
								row1.powerconsumed = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.startdatetime = null;
						} else {

							row1.startdatetime = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 3);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.enddatetime = null;
						} else {

							row1.enddatetime = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 4);
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.isinterrupted = null;
						} else {

							row1.isinterrupted = rs_tDBInput_1.getBoolean(5);
							if (rs_tDBInput_1.wasNull()) {
								row1.isinterrupted = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.interruptedreasonid = null;
						} else {

							row1.interruptedreasonid = rs_tDBInput_1.getInt(6);
							if (rs_tDBInput_1.wasNull()) {
								row1.interruptedreasonid = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.vehicleid = 0;
						} else {

							row1.vehicleid = rs_tDBInput_1.getInt(7);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row1.stationid = 0;
						} else {

							row1.stationid = rs_tDBInput_1.getInt(8);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row1.chargepointid = 0;
						} else {

							row1.chargepointid = rs_tDBInput_1.getInt(9);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row1.pricingmodelid = 0;
						} else {

							row1.pricingmodelid = rs_tDBInput_1.getInt(10);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 11) {
							row1.bookingid = null;
						} else {

							row1.bookingid = rs_tDBInput_1.getInt(11);
							if (rs_tDBInput_1.wasNull()) {
								row1.bookingid = null;
							}
						}

						log.debug("tDBInput_1 - Retrieving the record " + nb_line_tDBInput_1 + ".");

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"session\"";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"session\"";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tMap_1 main ] start
						 */

						s(currentComponent = "tMap_1");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row1", "tDBInput_1", "\"session\"", "tPostgresqlInput", "tMap_1", "tMap_1", "tMap"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

						row2Struct row2 = null;

						// ###############################
						// # Input tables (lookups)

						boolean rejectedInnerJoin_tMap_1 = false;
						boolean mainRowRejected_tMap_1 = false;

						///////////////////////////////////////////////
						// Starting Lookup Table "row2"
						///////////////////////////////////////////////

						boolean forceLooprow2 = false;

						row2Struct row2ObjectFromLookup = null;

						if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

							hasCasePrimitiveKeyWithNull_tMap_1 = false;

							row2HashKey.chargepointid = row1.chargepointid;

							row2HashKey.hashCodeDirty = true;

							tHash_Lookup_row2.lookup(row2HashKey);

							if (!tHash_Lookup_row2.hasNext()) { // G_TM_M_090

								rejectedInnerJoin_tMap_1 = true;

							} // G_TM_M_090

						} // G_TM_M_020

						if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G 071

							// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2'
							// and it contains more one result from keys : row2.chargepointid = '" +
							// row2HashKey.chargepointid + "'");
						} // G 071

						row2Struct fromLookup_row2 = null;
						row2 = row2Default;

						if (tHash_Lookup_row2 != null && tHash_Lookup_row2.hasNext()) { // G 099

							fromLookup_row2 = tHash_Lookup_row2.next();

						} // G 099

						if (fromLookup_row2 != null) {
							row2 = fromLookup_row2;
						}

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
							// ###############################
							// # Output tables

							CPFound = null;
							CPNotFound = null;

							if (!rejectedInnerJoin_tMap_1) {

// # Output table : 'CPFound'
								count_CPFound_tMap_1++;

								CPFound_tmp.sessionid = row1.sessionid;
								CPFound_tmp.stationkey = row2.stationkey;
								CPFound_tmp.chargepointkey = row2.chargepointkey;
								CPFound_tmp.powerconsumed = row1.powerconsumed;
								CPFound_tmp.startdatetime = row1.startdatetime;
								CPFound_tmp.enddatetime = row1.enddatetime;
								CPFound_tmp.isinterrupted = row1.isinterrupted;
								CPFound_tmp.interruptedreasonid = row1.interruptedreasonid;
								CPFound_tmp.vehicleid = row1.vehicleid;
								CPFound_tmp.stationid = row1.stationid;
								CPFound_tmp.chargepointid = row1.chargepointid;
								CPFound_tmp.pricingmodelid = row1.pricingmodelid;
								CPFound_tmp.bookingid = row1.bookingid;
								CPFound = CPFound_tmp;
								log.debug("tMap_1 - Outputting the record " + count_CPFound_tMap_1
										+ " of the output table 'CPFound'.");

							} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'CPNotFound'
// # Filter conditions 
							if (rejectedInnerJoin_tMap_1) {
								count_CPNotFound_tMap_1++;

								CPNotFound_tmp.sessionid = row1.sessionid;
								CPNotFound = CPNotFound_tmp;
								log.debug("tMap_1 - Outputting the record " + count_CPNotFound_tMap_1
										+ " of the output table 'CPNotFound'.");

							} // closing filter/reject
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_1 = false;

						tos_count_tMap_1++;

						/**
						 * [tMap_1 main ] stop
						 */

						/**
						 * [tMap_1 process_data_begin ] start
						 */

						s(currentComponent = "tMap_1");

						/**
						 * [tMap_1 process_data_begin ] stop
						 */

// Start of branch "CPFound"
						if (CPFound != null) {

							/**
							 * [tMap_2 main ] start
							 */

							s(currentComponent = "tMap_2");

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "CPFound", "tMap_1", "tMap_1", "tMap", "tMap_2", "tMap_2", "tMap"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("CPFound - " + (CPFound == null ? "" : CPFound.toLogString()));
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;

							row3Struct row3 = null;

							// ###############################
							// # Input tables (lookups)

							boolean rejectedInnerJoin_tMap_2 = false;
							boolean mainRowRejected_tMap_2 = false;

							///////////////////////////////////////////////
							// Starting Lookup Table "row3"
							///////////////////////////////////////////////

							boolean forceLooprow3 = false;

							row3Struct row3ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_2) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_2 = false;

								row3HashKey.pricingmodelid = CPFound.pricingmodelid;

								row3HashKey.hashCodeDirty = true;

								tHash_Lookup_row3.lookup(row3HashKey);

								if (!tHash_Lookup_row3.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_2 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3'
								// and it contains more one result from keys : row3.pricingmodelid = '" +
								// row3HashKey.pricingmodelid + "'");
							} // G 071

							row3Struct fromLookup_row3 = null;
							row3 = row3Default;

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.hasNext()) { // G 099

								fromLookup_row3 = tHash_Lookup_row3.next();

							} // G 099

							if (fromLookup_row3 != null) {
								row3 = fromLookup_row3;
							}

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
								// ###############################
								// # Output tables

								PMFound = null;
								PMNotFound = null;

								if (!rejectedInnerJoin_tMap_2) {

// # Output table : 'PMFound'
									count_PMFound_tMap_2++;

									PMFound_tmp.sessionid = CPFound.sessionid;
									PMFound_tmp.stationkey = CPFound.stationkey;
									PMFound_tmp.chargepointkey = CPFound.chargepointkey;
									PMFound_tmp.pricingmodelkey = row3.pricingmodelkey;
									PMFound_tmp.powerconsumed = CPFound.powerconsumed;
									PMFound_tmp.startdatetime = CPFound.startdatetime;
									PMFound_tmp.enddatetime = CPFound.enddatetime;
									PMFound_tmp.isinterrupted = CPFound.isinterrupted;
									PMFound_tmp.interruptedreasonid = CPFound.interruptedreasonid;
									PMFound_tmp.vehicleid = CPFound.vehicleid;
									PMFound_tmp.stationid = CPFound.stationid;
									PMFound_tmp.chargepointid = CPFound.chargepointid;
									PMFound_tmp.pricingmodelid = CPFound.pricingmodelid;
									PMFound_tmp.bookingid = CPFound.bookingid;
									PMFound_tmp.StartDate = TalendDate.parseDate("yyyy-MM-dd",
											TalendDate.formatDate("yyyy-MM-dd", CPFound.startdatetime));
									PMFound_tmp.EndDate = TalendDate.parseDate("yyyy-MM-dd",
											TalendDate.formatDate("yyyy-MM-dd", CPFound.enddatetime));
									PMFound_tmp.StartHour = TalendDate.getPartOfDate("HH", CPFound.startdatetime);
									PMFound_tmp.EndHour = TalendDate.getPartOfDate("HH", CPFound.enddatetime);
									;
									PMFound = PMFound_tmp;
									log.debug("tMap_2 - Outputting the record " + count_PMFound_tMap_2
											+ " of the output table 'PMFound'.");

								} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'PMNotFound'
// # Filter conditions 
								if (rejectedInnerJoin_tMap_2) {
									count_PMNotFound_tMap_2++;

									PMNotFound_tmp.sessionid = CPFound.sessionid;
									PMNotFound = PMNotFound_tmp;
									log.debug("tMap_2 - Outputting the record " + count_PMNotFound_tMap_2
											+ " of the output table 'PMNotFound'.");

								} // closing filter/reject
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_2 = false;

							tos_count_tMap_2++;

							/**
							 * [tMap_2 main ] stop
							 */

							/**
							 * [tMap_2 process_data_begin ] start
							 */

							s(currentComponent = "tMap_2");

							/**
							 * [tMap_2 process_data_begin ] stop
							 */

// Start of branch "PMFound"
							if (PMFound != null) {

								/**
								 * [tMap_3 main ] start
								 */

								s(currentComponent = "tMap_3");

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "PMFound", "tMap_2", "tMap_2", "tMap", "tMap_3", "tMap_3", "tMap"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("PMFound - " + (PMFound == null ? "" : PMFound.toLogString()));
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;

								row4Struct row4 = null;

								// ###############################
								// # Input tables (lookups)

								boolean rejectedInnerJoin_tMap_3 = false;
								boolean mainRowRejected_tMap_3 = false;

								///////////////////////////////////////////////
								// Starting Lookup Table "row4"
								///////////////////////////////////////////////

								boolean forceLooprow4 = false;

								row4Struct row4ObjectFromLookup = null;

								if (!rejectedInnerJoin_tMap_3) { // G_TM_M_020

									hasCasePrimitiveKeyWithNull_tMap_3 = false;

									row4HashKey.vehicleid = PMFound.vehicleid;

									row4HashKey.hashCodeDirty = true;

									tHash_Lookup_row4.lookup(row4HashKey);

									if (!tHash_Lookup_row4.hasNext()) { // G_TM_M_090

										rejectedInnerJoin_tMap_3 = true;

									} // G_TM_M_090

								} // G_TM_M_020

								if (tHash_Lookup_row4 != null && tHash_Lookup_row4.getCount(row4HashKey) > 1) { // G 071

									// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row4'
									// and it contains more one result from keys : row4.vehicleid = '" +
									// row4HashKey.vehicleid + "'");
								} // G 071

								row4Struct fromLookup_row4 = null;
								row4 = row4Default;

								if (tHash_Lookup_row4 != null && tHash_Lookup_row4.hasNext()) { // G 099

									fromLookup_row4 = tHash_Lookup_row4.next();

								} // G 099

								if (fromLookup_row4 != null) {
									row4 = fromLookup_row4;
								}

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
									// ###############################
									// # Output tables

									VehicleFound = null;
									VehicleNotFound = null;

									if (!rejectedInnerJoin_tMap_3) {

// # Output table : 'VehicleFound'
										count_VehicleFound_tMap_3++;

										VehicleFound_tmp.sessionid = PMFound.sessionid;
										VehicleFound_tmp.userkey = row4.userkey;
										VehicleFound_tmp.vehiclekey = row4.vehiclekey;
										VehicleFound_tmp.stationkey = PMFound.stationkey;
										VehicleFound_tmp.chargepointkey = PMFound.chargepointkey;
										VehicleFound_tmp.pricingmodelkey = PMFound.pricingmodelkey;
										VehicleFound_tmp.powerconsumed = PMFound.powerconsumed;
										VehicleFound_tmp.startdatetime = PMFound.startdatetime;
										VehicleFound_tmp.enddatetime = PMFound.enddatetime;
										VehicleFound_tmp.isinterrupted = PMFound.isinterrupted;
										VehicleFound_tmp.interruptedreasonid = PMFound.interruptedreasonid;
										VehicleFound_tmp.vehicleid = PMFound.vehicleid;
										VehicleFound_tmp.stationid = PMFound.stationid;
										VehicleFound_tmp.chargepointid = PMFound.chargepointid;
										VehicleFound_tmp.pricingmodelid = PMFound.pricingmodelid;
										VehicleFound_tmp.bookingid = PMFound.bookingid;
										VehicleFound_tmp.StartDate = PMFound.StartDate;
										VehicleFound_tmp.EndDate = PMFound.EndDate;
										VehicleFound_tmp.StartHour = PMFound.StartHour;
										VehicleFound_tmp.EndHour = PMFound.EndHour;
										VehicleFound = VehicleFound_tmp;
										log.debug("tMap_3 - Outputting the record " + count_VehicleFound_tMap_3
												+ " of the output table 'VehicleFound'.");

									} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'VehicleNotFound'
// # Filter conditions 
									if (rejectedInnerJoin_tMap_3) {
										count_VehicleNotFound_tMap_3++;

										VehicleNotFound_tmp.sessionid = PMFound.sessionid;
										VehicleNotFound = VehicleNotFound_tmp;
										log.debug("tMap_3 - Outputting the record " + count_VehicleNotFound_tMap_3
												+ " of the output table 'VehicleNotFound'.");

									} // closing filter/reject
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_3 = false;

								tos_count_tMap_3++;

								/**
								 * [tMap_3 main ] stop
								 */

								/**
								 * [tMap_3 process_data_begin ] start
								 */

								s(currentComponent = "tMap_3");

								/**
								 * [tMap_3 process_data_begin ] stop
								 */

// Start of branch "VehicleFound"
								if (VehicleFound != null) {

									/**
									 * [tMap_4 main ] start
									 */

									s(currentComponent = "tMap_4");

									if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

											, "VehicleFound", "tMap_3", "tMap_3", "tMap", "tMap_4", "tMap_4", "tMap"

									)) {
										talendJobLogProcess(globalMap);
									}

									if (log.isTraceEnabled()) {
										log.trace("VehicleFound - "
												+ (VehicleFound == null ? "" : VehicleFound.toLogString()));
									}

									boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;

									row5Struct row5 = null;

									// ###############################
									// # Input tables (lookups)

									boolean rejectedInnerJoin_tMap_4 = false;
									boolean mainRowRejected_tMap_4 = false;

									///////////////////////////////////////////////
									// Starting Lookup Table "row5"
									///////////////////////////////////////////////

									boolean forceLooprow5 = false;

									row5Struct row5ObjectFromLookup = null;

									if (!rejectedInnerJoin_tMap_4) { // G_TM_M_020

										hasCasePrimitiveKeyWithNull_tMap_4 = false;

										row5HashKey.date = VehicleFound.StartDate == null ? null
												: new java.util.Date((VehicleFound.StartDate).getTime());

										row5HashKey.hour = VehicleFound.StartHour;

										row5HashKey.hashCodeDirty = true;

										tHash_Lookup_row5.lookup(row5HashKey);

										if (!tHash_Lookup_row5.hasNext()) { // G_TM_M_090

											rejectedInnerJoin_tMap_4 = true;

										} // G_TM_M_090

									} // G_TM_M_020

									if (tHash_Lookup_row5 != null && tHash_Lookup_row5.getCount(row5HashKey) > 1) { // G
																													// 071

										// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row5'
										// and it contains more one result from keys : row5.date = '" + row5HashKey.date
										// + "', row5.hour = '" + row5HashKey.hour + "'");
									} // G 071

									row5Struct fromLookup_row5 = null;
									row5 = row5Default;

									if (tHash_Lookup_row5 != null && tHash_Lookup_row5.hasNext()) { // G 099

										fromLookup_row5 = tHash_Lookup_row5.next();

									} // G 099

									if (fromLookup_row5 != null) {
										row5 = fromLookup_row5;
									}

									// ###############################
									{ // start of Var scope

										// ###############################
										// # Vars tables

										Var__tMap_4__Struct Var = Var__tMap_4;// ###############################
										// ###############################
										// # Output tables

										StartTimeFound = null;
										StartTimeNotFound = null;

										if (!rejectedInnerJoin_tMap_4) {

// # Output table : 'StartTimeFound'
											count_StartTimeFound_tMap_4++;

											StartTimeFound_tmp.sessionid = VehicleFound.sessionid;
											StartTimeFound_tmp.userkey = VehicleFound.userkey;
											StartTimeFound_tmp.vehiclekey = VehicleFound.vehiclekey;
											StartTimeFound_tmp.stationkey = VehicleFound.stationkey;
											StartTimeFound_tmp.chargepointkey = VehicleFound.chargepointkey;
											StartTimeFound_tmp.pricingmodelkey = VehicleFound.pricingmodelkey;
											StartTimeFound_tmp.startdatetimekey = row5.timekey;
											StartTimeFound_tmp.powerconsumed = VehicleFound.powerconsumed;
											StartTimeFound_tmp.startdatetime = VehicleFound.startdatetime;
											StartTimeFound_tmp.enddatetime = VehicleFound.enddatetime;
											StartTimeFound_tmp.isinterrupted = VehicleFound.isinterrupted;
											StartTimeFound_tmp.interruptedreasonid = VehicleFound.interruptedreasonid;
											StartTimeFound_tmp.vehicleid = VehicleFound.vehicleid;
											StartTimeFound_tmp.stationid = VehicleFound.stationid;
											StartTimeFound_tmp.chargepointid = VehicleFound.chargepointid;
											StartTimeFound_tmp.pricingmodelid = VehicleFound.pricingmodelid;
											StartTimeFound_tmp.bookingid = VehicleFound.bookingid;
											StartTimeFound_tmp.StartDate = VehicleFound.StartDate;
											StartTimeFound_tmp.EndDate = VehicleFound.EndDate;
											StartTimeFound_tmp.StartHour = VehicleFound.StartHour;
											StartTimeFound_tmp.EndHour = VehicleFound.EndHour;
											StartTimeFound = StartTimeFound_tmp;
											log.debug("tMap_4 - Outputting the record " + count_StartTimeFound_tMap_4
													+ " of the output table 'StartTimeFound'.");

										} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'StartTimeNotFound'
// # Filter conditions 
										if (rejectedInnerJoin_tMap_4) {
											count_StartTimeNotFound_tMap_4++;

											StartTimeNotFound_tmp.sessionid = VehicleFound.sessionid;
											StartTimeNotFound = StartTimeNotFound_tmp;
											log.debug("tMap_4 - Outputting the record " + count_StartTimeNotFound_tMap_4
													+ " of the output table 'StartTimeNotFound'.");

										} // closing filter/reject
// ###############################

									} // end of Var scope

									rejectedInnerJoin_tMap_4 = false;

									tos_count_tMap_4++;

									/**
									 * [tMap_4 main ] stop
									 */

									/**
									 * [tMap_4 process_data_begin ] start
									 */

									s(currentComponent = "tMap_4");

									/**
									 * [tMap_4 process_data_begin ] stop
									 */

// Start of branch "StartTimeFound"
									if (StartTimeFound != null) {

										/**
										 * [tMap_5 main ] start
										 */

										s(currentComponent = "tMap_5");

										if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

												, "StartTimeFound", "tMap_4", "tMap_4", "tMap", "tMap_5", "tMap_5",
												"tMap"

										)) {
											talendJobLogProcess(globalMap);
										}

										if (log.isTraceEnabled()) {
											log.trace("StartTimeFound - "
													+ (StartTimeFound == null ? "" : StartTimeFound.toLogString()));
										}

										boolean hasCasePrimitiveKeyWithNull_tMap_5 = false;

										row6Struct row6 = null;

										// ###############################
										// # Input tables (lookups)

										boolean rejectedInnerJoin_tMap_5 = false;
										boolean mainRowRejected_tMap_5 = false;

										///////////////////////////////////////////////
										// Starting Lookup Table "row6"
										///////////////////////////////////////////////

										boolean forceLooprow6 = false;

										row6Struct row6ObjectFromLookup = null;

										if (!rejectedInnerJoin_tMap_5) { // G_TM_M_020

											hasCasePrimitiveKeyWithNull_tMap_5 = false;

											row6HashKey.date = StartTimeFound.EndDate == null ? null
													: new java.util.Date((StartTimeFound.EndDate).getTime());

											row6HashKey.hour = StartTimeFound.EndHour;

											row6HashKey.hashCodeDirty = true;

											tHash_Lookup_row6.lookup(row6HashKey);

											if (!tHash_Lookup_row6.hasNext()) { // G_TM_M_090

												rejectedInnerJoin_tMap_5 = true;

											} // G_TM_M_090

										} // G_TM_M_020

										if (tHash_Lookup_row6 != null && tHash_Lookup_row6.getCount(row6HashKey) > 1) { // G
																														// 071

											// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
											// 'row6' and it contains more one result from keys : row6.date = '" +
											// row6HashKey.date + "', row6.hour = '" + row6HashKey.hour + "'");
										} // G 071

										row6Struct fromLookup_row6 = null;
										row6 = row6Default;

										if (tHash_Lookup_row6 != null && tHash_Lookup_row6.hasNext()) { // G 099

											fromLookup_row6 = tHash_Lookup_row6.next();

										} // G 099

										if (fromLookup_row6 != null) {
											row6 = fromLookup_row6;
										}

										// ###############################
										{ // start of Var scope

											// ###############################
											// # Vars tables

											Var__tMap_5__Struct Var = Var__tMap_5;// ###############################
											// ###############################
											// # Output tables

											EndTimeFound = null;
											EndTimeNotFound = null;

											if (!rejectedInnerJoin_tMap_5) {

// # Output table : 'EndTimeFound'
												count_EndTimeFound_tMap_5++;

												EndTimeFound_tmp.sessionid = StartTimeFound.sessionid;
												EndTimeFound_tmp.userkey = StartTimeFound.userkey;
												EndTimeFound_tmp.vehiclekey = StartTimeFound.vehiclekey;
												EndTimeFound_tmp.stationkey = StartTimeFound.stationkey;
												EndTimeFound_tmp.chargepointkey = StartTimeFound.chargepointkey;
												EndTimeFound_tmp.pricingmodelkey = StartTimeFound.pricingmodelkey;
												EndTimeFound_tmp.startdatetimekey = StartTimeFound.startdatetimekey;
												EndTimeFound_tmp.enddatetimekey = row6.timekey;
												EndTimeFound_tmp.powerconsumed = StartTimeFound.powerconsumed;
												EndTimeFound_tmp.startdatetime = StartTimeFound.startdatetime;
												EndTimeFound_tmp.enddatetime = StartTimeFound.enddatetime;
												EndTimeFound_tmp.isinterrupted = StartTimeFound.isinterrupted;
												EndTimeFound_tmp.interruptedreasonid = StartTimeFound.interruptedreasonid;
												EndTimeFound_tmp.vehicleid = StartTimeFound.vehicleid;
												EndTimeFound_tmp.stationid = StartTimeFound.stationid;
												EndTimeFound_tmp.chargepointid = StartTimeFound.chargepointid;
												EndTimeFound_tmp.pricingmodelid = StartTimeFound.pricingmodelid;
												EndTimeFound_tmp.bookingid = StartTimeFound.bookingid;
												EndTimeFound_tmp.StartDate = StartTimeFound.StartDate;
												EndTimeFound_tmp.EndDate = StartTimeFound.EndDate;
												EndTimeFound_tmp.StartHour = StartTimeFound.StartHour;
												EndTimeFound_tmp.EndHour = StartTimeFound.EndHour;
												EndTimeFound = EndTimeFound_tmp;
												log.debug("tMap_5 - Outputting the record " + count_EndTimeFound_tMap_5
														+ " of the output table 'EndTimeFound'.");

											} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'EndTimeNotFound'
// # Filter conditions 
											if (rejectedInnerJoin_tMap_5) {
												count_EndTimeNotFound_tMap_5++;

												EndTimeNotFound_tmp.sessionid = StartTimeFound.sessionid;
												EndTimeNotFound = EndTimeNotFound_tmp;
												log.debug(
														"tMap_5 - Outputting the record " + count_EndTimeNotFound_tMap_5
																+ " of the output table 'EndTimeNotFound'.");

											} // closing filter/reject
// ###############################

										} // end of Var scope

										rejectedInnerJoin_tMap_5 = false;

										tos_count_tMap_5++;

										/**
										 * [tMap_5 main ] stop
										 */

										/**
										 * [tMap_5 process_data_begin ] start
										 */

										s(currentComponent = "tMap_5");

										/**
										 * [tMap_5 process_data_begin ] stop
										 */

// Start of branch "EndTimeFound"
										if (EndTimeFound != null) {

											/**
											 * [tMap_6 main ] start
											 */

											s(currentComponent = "tMap_6");

											if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

													, "EndTimeFound", "tMap_5", "tMap_5", "tMap", "tMap_6", "tMap_6",
													"tMap"

											)) {
												talendJobLogProcess(globalMap);
											}

											if (log.isTraceEnabled()) {
												log.trace("EndTimeFound - "
														+ (EndTimeFound == null ? "" : EndTimeFound.toLogString()));
											}

											boolean hasCasePrimitiveKeyWithNull_tMap_6 = false;

											row7Struct row7 = null;

											// ###############################
											// # Input tables (lookups)

											boolean rejectedInnerJoin_tMap_6 = false;
											boolean mainRowRejected_tMap_6 = false;

											///////////////////////////////////////////////
											// Starting Lookup Table "row7"
											///////////////////////////////////////////////

											boolean forceLooprow7 = false;

											row7Struct row7ObjectFromLookup = null;

											if (!rejectedInnerJoin_tMap_6) { // G_TM_M_020

												hasCasePrimitiveKeyWithNull_tMap_6 = false;

												row7HashKey.bookingid = EndTimeFound.bookingid;

												row7HashKey.hashCodeDirty = true;

												tHash_Lookup_row7.lookup(row7HashKey);

											} // G_TM_M_020

											if (tHash_Lookup_row7 != null
													&& tHash_Lookup_row7.getCount(row7HashKey) > 1) { // G 071

												// System.out.println("WARNING: UNIQUE MATCH is configured for the
												// lookup 'row7' and it contains more one result from keys :
												// row7.bookingid = '" + row7HashKey.bookingid + "'");
											} // G 071

											row7Struct fromLookup_row7 = null;
											row7 = row7Default;

											if (tHash_Lookup_row7 != null && tHash_Lookup_row7.hasNext()) { // G 099

												fromLookup_row7 = tHash_Lookup_row7.next();

											} // G 099

											if (fromLookup_row7 != null) {
												row7 = fromLookup_row7;
											}

											// ###############################
											{ // start of Var scope

												// ###############################
												// # Vars tables

												Var__tMap_6__Struct Var = Var__tMap_6;// ###############################
												// ###############################
												// # Output tables

												BookingOptional = null;

// # Output table : 'BookingOptional'
												count_BookingOptional_tMap_6++;

												BookingOptional_tmp.sessionid = EndTimeFound.sessionid;
												BookingOptional_tmp.userkey = EndTimeFound.userkey;
												BookingOptional_tmp.vehiclekey = EndTimeFound.vehiclekey;
												BookingOptional_tmp.stationkey = EndTimeFound.stationkey;
												BookingOptional_tmp.chargepointkey = EndTimeFound.chargepointkey;
												BookingOptional_tmp.pricingmodelkey = EndTimeFound.pricingmodelkey;
												BookingOptional_tmp.startdatetimekey = EndTimeFound.startdatetimekey;
												BookingOptional_tmp.enddatetimekey = EndTimeFound.enddatetimekey;
												BookingOptional_tmp.bookingkey = row7.bookingkey;
												BookingOptional_tmp.powerconsumed = EndTimeFound.powerconsumed;
												BookingOptional_tmp.startdatetime = EndTimeFound.startdatetime;
												BookingOptional_tmp.enddatetime = EndTimeFound.enddatetime;
												BookingOptional_tmp.isinterrupted = EndTimeFound.isinterrupted;
												BookingOptional_tmp.interruptedreasonid = EndTimeFound.interruptedreasonid;
												BookingOptional_tmp.vehicleid = EndTimeFound.vehicleid;
												BookingOptional_tmp.stationid = EndTimeFound.stationid;
												BookingOptional_tmp.chargepointid = EndTimeFound.chargepointid;
												BookingOptional_tmp.pricingmodelid = EndTimeFound.pricingmodelid;
												BookingOptional_tmp.bookingid = EndTimeFound.bookingid;
												BookingOptional_tmp.StartDate = EndTimeFound.StartDate;
												BookingOptional_tmp.EndDate = EndTimeFound.EndDate;
												BookingOptional_tmp.StartHour = EndTimeFound.StartHour;
												BookingOptional_tmp.EndHour = EndTimeFound.EndHour;
												BookingOptional = BookingOptional_tmp;
												log.debug(
														"tMap_6 - Outputting the record " + count_BookingOptional_tMap_6
																+ " of the output table 'BookingOptional'.");

// ###############################

											} // end of Var scope

											rejectedInnerJoin_tMap_6 = false;

											tos_count_tMap_6++;

											/**
											 * [tMap_6 main ] stop
											 */

											/**
											 * [tMap_6 process_data_begin ] start
											 */

											s(currentComponent = "tMap_6");

											/**
											 * [tMap_6 process_data_begin ] stop
											 */

// Start of branch "BookingOptional"
											if (BookingOptional != null) {

												/**
												 * [tMap_7 main ] start
												 */

												s(currentComponent = "tMap_7");

												if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

														, "BookingOptional", "tMap_6", "tMap_6", "tMap", "tMap_7",
														"tMap_7", "tMap"

												)) {
													talendJobLogProcess(globalMap);
												}

												if (log.isTraceEnabled()) {
													log.trace("BookingOptional - " + (BookingOptional == null ? ""
															: BookingOptional.toLogString()));
												}

												boolean hasCasePrimitiveKeyWithNull_tMap_7 = false;

												row8Struct row8 = null;

												// ###############################
												// # Input tables (lookups)

												boolean rejectedInnerJoin_tMap_7 = false;
												boolean mainRowRejected_tMap_7 = false;

												///////////////////////////////////////////////
												// Starting Lookup Table "row8"
												///////////////////////////////////////////////

												boolean forceLooprow8 = false;

												row8Struct row8ObjectFromLookup = null;

												if (!rejectedInnerJoin_tMap_7) { // G_TM_M_020

													hasCasePrimitiveKeyWithNull_tMap_7 = false;

													row8HashKey.sessionid = BookingOptional.sessionid;

													row8HashKey.hashCodeDirty = true;

													tHash_Lookup_row8.lookup(row8HashKey);

												} // G_TM_M_020

												if (tHash_Lookup_row8 != null
														&& tHash_Lookup_row8.getCount(row8HashKey) > 1) { // G 071

													// System.out.println("WARNING: UNIQUE MATCH is configured for the
													// lookup 'row8' and it contains more one result from keys :
													// row8.sessionid = '" + row8HashKey.sessionid + "'");
												} // G 071

												row8Struct fromLookup_row8 = null;
												row8 = row8Default;

												if (tHash_Lookup_row8 != null && tHash_Lookup_row8.hasNext()) { // G 099

													fromLookup_row8 = tHash_Lookup_row8.next();

												} // G 099

												if (fromLookup_row8 != null) {
													row8 = fromLookup_row8;
												}

												// ###############################
												{ // start of Var scope

													// ###############################
													// # Vars tables

													Var__tMap_7__Struct Var = Var__tMap_7;// ###############################
													// ###############################
													// # Output tables

													PaymentOptional = null;

// # Output table : 'PaymentOptional'
													count_PaymentOptional_tMap_7++;

													PaymentOptional_tmp.sessionid = BookingOptional.sessionid;
													PaymentOptional_tmp.userkey = BookingOptional.userkey;
													PaymentOptional_tmp.vehiclekey = BookingOptional.vehiclekey;
													PaymentOptional_tmp.stationkey = BookingOptional.stationkey;
													PaymentOptional_tmp.chargepointkey = BookingOptional.chargepointkey;
													PaymentOptional_tmp.pricingmodelkey = BookingOptional.pricingmodelkey;
													PaymentOptional_tmp.startdatetimekey = BookingOptional.startdatetimekey;
													PaymentOptional_tmp.enddatetimekey = BookingOptional.enddatetimekey;
													PaymentOptional_tmp.bookingkey = BookingOptional.bookingkey;
													PaymentOptional_tmp.paymentkey = row8.paymentkey;
													PaymentOptional_tmp.powerconsumed = BookingOptional.powerconsumed;
													PaymentOptional_tmp.startdatetime = BookingOptional.startdatetime;
													PaymentOptional_tmp.enddatetime = BookingOptional.enddatetime;
													PaymentOptional_tmp.isinterrupted = BookingOptional.isinterrupted;
													PaymentOptional_tmp.interruptedreasonid = BookingOptional.interruptedreasonid;
													PaymentOptional_tmp.vehicleid = BookingOptional.vehicleid;
													PaymentOptional_tmp.stationid = BookingOptional.stationid;
													PaymentOptional_tmp.chargepointid = BookingOptional.chargepointid;
													PaymentOptional_tmp.pricingmodelid = BookingOptional.pricingmodelid;
													PaymentOptional_tmp.bookingid = BookingOptional.bookingid;
													PaymentOptional_tmp.StartDate = BookingOptional.StartDate;
													PaymentOptional_tmp.EndDate = BookingOptional.EndDate;
													PaymentOptional_tmp.StartHour = BookingOptional.StartHour;
													PaymentOptional_tmp.EndHour = BookingOptional.EndHour;
													PaymentOptional = PaymentOptional_tmp;
													log.debug("tMap_7 - Outputting the record "
															+ count_PaymentOptional_tMap_7
															+ " of the output table 'PaymentOptional'.");

// ###############################

												} // end of Var scope

												rejectedInnerJoin_tMap_7 = false;

												tos_count_tMap_7++;

												/**
												 * [tMap_7 main ] stop
												 */

												/**
												 * [tMap_7 process_data_begin ] start
												 */

												s(currentComponent = "tMap_7");

												/**
												 * [tMap_7 process_data_begin ] stop
												 */

// Start of branch "PaymentOptional"
												if (PaymentOptional != null) {

													/**
													 * [tMap_8 main ] start
													 */

													s(currentComponent = "tMap_8");

													if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

															, "PaymentOptional", "tMap_7", "tMap_7", "tMap", "tMap_8",
															"tMap_8", "tMap"

													)) {
														talendJobLogProcess(globalMap);
													}

													if (log.isTraceEnabled()) {
														log.trace("PaymentOptional - " + (PaymentOptional == null ? ""
																: PaymentOptional.toLogString()));
													}

													boolean hasCasePrimitiveKeyWithNull_tMap_8 = false;

													row9Struct row9 = null;

													// ###############################
													// # Input tables (lookups)

													boolean rejectedInnerJoin_tMap_8 = false;
													boolean mainRowRejected_tMap_8 = false;

													///////////////////////////////////////////////
													// Starting Lookup Table "row9"
													///////////////////////////////////////////////

													boolean forceLooprow9 = false;

													row9Struct row9ObjectFromLookup = null;

													if (!rejectedInnerJoin_tMap_8) { // G_TM_M_020

														hasCasePrimitiveKeyWithNull_tMap_8 = false;

														Object exprKeyValue_row9__pricingmodelkey = PaymentOptional.pricingmodelkey;
														if (exprKeyValue_row9__pricingmodelkey == null) {
															hasCasePrimitiveKeyWithNull_tMap_8 = true;
														} else {
															row9HashKey.pricingmodelkey = (int) (Integer) exprKeyValue_row9__pricingmodelkey;
														}

														row9HashKey.hashCodeDirty = true;

														if (!hasCasePrimitiveKeyWithNull_tMap_8) { // G_TM_M_091

															tHash_Lookup_row9.lookup(row9HashKey);

														} // G_TM_M_091

														if (hasCasePrimitiveKeyWithNull_tMap_8
																|| !tHash_Lookup_row9.hasNext()) { // G_TM_M_090

															rejectedInnerJoin_tMap_8 = true;

														} // G_TM_M_090

													} // G_TM_M_020

													if (tHash_Lookup_row9 != null
															&& tHash_Lookup_row9.getCount(row9HashKey) > 1) { // G 071

														// System.out.println("WARNING: UNIQUE MATCH is configured for
														// the lookup 'row9' and it contains more one result from keys :
														// row9.pricingmodelkey = '" + row9HashKey.pricingmodelkey +
														// "'");
													} // G 071

													row9Struct fromLookup_row9 = null;
													row9 = row9Default;

													if (tHash_Lookup_row9 != null && tHash_Lookup_row9.hasNext()) { // G
																													// 099

														fromLookup_row9 = tHash_Lookup_row9.next();

													} // G 099

													if (fromLookup_row9 != null) {
														row9 = fromLookup_row9;
													}

													// ###############################
													{ // start of Var scope

														// ###############################
														// # Vars tables

														Var__tMap_8__Struct Var = Var__tMap_8;// ###############################
														// ###############################
														// # Output tables

														PrepareRevenue = null;

														if (!rejectedInnerJoin_tMap_8) {

// # Output table : 'PrepareRevenue'
															count_PrepareRevenue_tMap_8++;

															PrepareRevenue_tmp.sessionid = PaymentOptional.sessionid;
															PrepareRevenue_tmp.totalkwhconsumed = PaymentOptional.powerconsumed;
															PrepareRevenue_tmp.sessionduration = TalendDate.diffDate(
																	PaymentOptional.enddatetime,
																	PaymentOptional.startdatetime, "mm");
															PrepareRevenue_tmp.userkey = PaymentOptional.userkey;
															PrepareRevenue_tmp.vehiclekey = PaymentOptional.vehiclekey;
															PrepareRevenue_tmp.stationkey = PaymentOptional.stationkey;
															PrepareRevenue_tmp.chargepointkey = PaymentOptional.chargepointkey;
															PrepareRevenue_tmp.pricingmodelkey = PaymentOptional.pricingmodelkey;
															PrepareRevenue_tmp.startdatetimekey = PaymentOptional.startdatetimekey;
															PrepareRevenue_tmp.enddatetimekey = PaymentOptional.enddatetimekey;
															PrepareRevenue_tmp.bookingkey = PaymentOptional.bookingkey;
															PrepareRevenue_tmp.paymentkey = PaymentOptional.paymentkey;
															PrepareRevenue_tmp.powerconsumed = PaymentOptional.powerconsumed;
															PrepareRevenue_tmp.startdatetime = PaymentOptional.startdatetime;
															PrepareRevenue_tmp.enddatetime = PaymentOptional.enddatetime;
															PrepareRevenue_tmp.isinterrupted = PaymentOptional.isinterrupted;
															PrepareRevenue_tmp.interruptedreasonid = PaymentOptional.interruptedreasonid;
															PrepareRevenue_tmp.vehicleid = PaymentOptional.vehicleid;
															PrepareRevenue_tmp.stationid = PaymentOptional.stationid;
															PrepareRevenue_tmp.chargepointid = PaymentOptional.chargepointid;
															PrepareRevenue_tmp.pricingmodelid = PaymentOptional.pricingmodelid;
															PrepareRevenue_tmp.bookingid = PaymentOptional.bookingid;
															PrepareRevenue_tmp.StartDate = PaymentOptional.StartDate;
															PrepareRevenue_tmp.EndDate = PaymentOptional.EndDate;
															PrepareRevenue_tmp.StartHour = PaymentOptional.StartHour;
															PrepareRevenue_tmp.EndHour = PaymentOptional.EndHour;
															PrepareRevenue_tmp.isPeakHour = TalendDate
																	.parseDate("HH:mm:ss",
																			TalendDate.formatDate("HH:mm:ss",
																					PaymentOptional.startdatetime))
																	.compareTo(row9.peakstarttime) >= 0
																	&& TalendDate.parseDate("HH:mm:ss",
																			TalendDate.formatDate("HH:mm:ss",
																					PaymentOptional.startdatetime))
																			.compareTo(row9.peakendtime) <= 0;
															PrepareRevenue = PrepareRevenue_tmp;
															log.debug("tMap_8 - Outputting the record "
																	+ count_PrepareRevenue_tMap_8
																	+ " of the output table 'PrepareRevenue'.");

														} // closing inner join bracket (2)
// ###############################

													} // end of Var scope

													rejectedInnerJoin_tMap_8 = false;

													tos_count_tMap_8++;

													/**
													 * [tMap_8 main ] stop
													 */

													/**
													 * [tMap_8 process_data_begin ] start
													 */

													s(currentComponent = "tMap_8");

													/**
													 * [tMap_8 process_data_begin ] stop
													 */

// Start of branch "PrepareRevenue"
													if (PrepareRevenue != null) {

														/**
														 * [tMap_9 main ] start
														 */

														s(currentComponent = "tMap_9");

														if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

																, "PrepareRevenue", "tMap_8", "tMap_8", "tMap",
																"tMap_9", "tMap_9", "tMap"

														)) {
															talendJobLogProcess(globalMap);
														}

														if (log.isTraceEnabled()) {
															log.trace("PrepareRevenue - " + (PrepareRevenue == null ? ""
																	: PrepareRevenue.toLogString()));
														}

														boolean hasCasePrimitiveKeyWithNull_tMap_9 = false;

														row10Struct row10 = null;

														// ###############################
														// # Input tables (lookups)

														boolean rejectedInnerJoin_tMap_9 = false;
														boolean mainRowRejected_tMap_9 = false;

														///////////////////////////////////////////////
														// Starting Lookup Table "row10"
														///////////////////////////////////////////////

														boolean forceLooprow10 = false;

														row10Struct row10ObjectFromLookup = null;

														if (!rejectedInnerJoin_tMap_9) { // G_TM_M_020

															hasCasePrimitiveKeyWithNull_tMap_9 = false;

															Object exprKeyValue_row10__pricingmodelkey = PrepareRevenue.pricingmodelkey;
															if (exprKeyValue_row10__pricingmodelkey == null) {
																hasCasePrimitiveKeyWithNull_tMap_9 = true;
															} else {
																row10HashKey.pricingmodelkey = (int) (Integer) exprKeyValue_row10__pricingmodelkey;
															}

															row10HashKey.hashCodeDirty = true;

															if (!hasCasePrimitiveKeyWithNull_tMap_9) { // G_TM_M_091

																tHash_Lookup_row10.lookup(row10HashKey);

															} // G_TM_M_091

															if (hasCasePrimitiveKeyWithNull_tMap_9
																	|| !tHash_Lookup_row10.hasNext()) { // G_TM_M_090

																rejectedInnerJoin_tMap_9 = true;

															} // G_TM_M_090

														} // G_TM_M_020

														if (tHash_Lookup_row10 != null
																&& tHash_Lookup_row10.getCount(row10HashKey) > 1) { // G
																													// 071

															// System.out.println("WARNING: UNIQUE MATCH is configured
															// for the lookup 'row10' and it contains more one result
															// from keys : row10.pricingmodelkey = '" +
															// row10HashKey.pricingmodelkey + "'");
														} // G 071

														row10Struct fromLookup_row10 = null;
														row10 = row10Default;

														if (tHash_Lookup_row10 != null
																&& tHash_Lookup_row10.hasNext()) { // G 099

															fromLookup_row10 = tHash_Lookup_row10.next();

														} // G 099

														if (fromLookup_row10 != null) {
															row10 = fromLookup_row10;
														}

														// ###############################
														{ // start of Var scope

															// ###############################
															// # Vars tables

															Var__tMap_9__Struct Var = Var__tMap_9;// ###############################
															// ###############################
															// # Output tables

															CalculateCost = null;

															if (!rejectedInnerJoin_tMap_9) {

// # Output table : 'CalculateCost'
																count_CalculateCost_tMap_9++;

																CalculateCost_tmp.sessionid = PrepareRevenue.sessionid;
																CalculateCost_tmp.totalkwhconsumed = PrepareRevenue.totalkwhconsumed;
																CalculateCost_tmp.sessionduration = PrepareRevenue.sessionduration;
																CalculateCost_tmp.userkey = PrepareRevenue.userkey;
																CalculateCost_tmp.vehiclekey = PrepareRevenue.vehiclekey;
																CalculateCost_tmp.stationkey = PrepareRevenue.stationkey;
																CalculateCost_tmp.chargepointkey = PrepareRevenue.chargepointkey;
																CalculateCost_tmp.pricingmodelkey = PrepareRevenue.pricingmodelkey;
																CalculateCost_tmp.startdatetimekey = PrepareRevenue.startdatetimekey;
																CalculateCost_tmp.enddatetimekey = PrepareRevenue.enddatetimekey;
																CalculateCost_tmp.bookingkey = PrepareRevenue.bookingkey;
																CalculateCost_tmp.paymentkey = PrepareRevenue.paymentkey;
																CalculateCost_tmp.powerconsumed = PrepareRevenue.powerconsumed;
																CalculateCost_tmp.startdatetime = PrepareRevenue.startdatetime;
																CalculateCost_tmp.enddatetime = PrepareRevenue.enddatetime;
																CalculateCost_tmp.isinterrupted = PrepareRevenue.isinterrupted;
																CalculateCost_tmp.interruptedreasonid = PrepareRevenue.interruptedreasonid;
																CalculateCost_tmp.vehicleid = PrepareRevenue.vehicleid;
																CalculateCost_tmp.stationid = PrepareRevenue.stationid;
																CalculateCost_tmp.chargepointid = PrepareRevenue.chargepointid;
																CalculateCost_tmp.pricingmodelid = PrepareRevenue.pricingmodelid;
																CalculateCost_tmp.bookingid = PrepareRevenue.bookingid;
																CalculateCost_tmp.StartDate = PrepareRevenue.StartDate;
																CalculateCost_tmp.EndDate = PrepareRevenue.EndDate;
																CalculateCost_tmp.StartHour = PrepareRevenue.StartHour;
																CalculateCost_tmp.EndHour = PrepareRevenue.EndHour;
																CalculateCost_tmp.isPeakHour = PrepareRevenue.isPeakHour;
																CalculateCost_tmp.ActualCost = PrepareRevenue.powerconsumed
																		.multiply(row10.rateperkwh)
																		.multiply(PrepareRevenue.isPeakHour
																				? row10.peakmultiplier
																				: BigDecimal.ONE);
																CalculateCost = CalculateCost_tmp;
																log.debug("tMap_9 - Outputting the record "
																		+ count_CalculateCost_tMap_9
																		+ " of the output table 'CalculateCost'.");

															} // closing inner join bracket (2)
// ###############################

														} // end of Var scope

														rejectedInnerJoin_tMap_9 = false;

														tos_count_tMap_9++;

														/**
														 * [tMap_9 main ] stop
														 */

														/**
														 * [tMap_9 process_data_begin ] start
														 */

														s(currentComponent = "tMap_9");

														/**
														 * [tMap_9 process_data_begin ] stop
														 */

// Start of branch "CalculateCost"
														if (CalculateCost != null) {

															/**
															 * [tMap_10 main ] start
															 */

															s(currentComponent = "tMap_10");

															if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

																	, "CalculateCost", "tMap_9", "tMap_9", "tMap",
																	"tMap_10", "tMap_10", "tMap"

															)) {
																talendJobLogProcess(globalMap);
															}

															if (log.isTraceEnabled()) {
																log.trace(
																		"CalculateCost - " + (CalculateCost == null ? ""
																				: CalculateCost.toLogString()));
															}

															boolean hasCasePrimitiveKeyWithNull_tMap_10 = false;

															row11Struct row11 = null;

															// ###############################
															// # Input tables (lookups)

															boolean rejectedInnerJoin_tMap_10 = false;
															boolean mainRowRejected_tMap_10 = false;

															///////////////////////////////////////////////
															// Starting Lookup Table "row11"
															///////////////////////////////////////////////

															boolean forceLooprow11 = false;

															row11Struct row11ObjectFromLookup = null;

															if (!rejectedInnerJoin_tMap_10) { // G_TM_M_020

																hasCasePrimitiveKeyWithNull_tMap_10 = false;

																Object exprKeyValue_row11__paymentkey = CalculateCost.paymentkey;
																if (exprKeyValue_row11__paymentkey == null) {
																	hasCasePrimitiveKeyWithNull_tMap_10 = true;
																} else {
																	row11HashKey.paymentkey = (int) (Integer) exprKeyValue_row11__paymentkey;
																}

																row11HashKey.hashCodeDirty = true;

																if (!hasCasePrimitiveKeyWithNull_tMap_10) { // G_TM_M_091

																	tHash_Lookup_row11.lookup(row11HashKey);

																} // G_TM_M_091

																if (hasCasePrimitiveKeyWithNull_tMap_10
																		|| !tHash_Lookup_row11.hasNext()) { // G_TM_M_090

																	rejectedInnerJoin_tMap_10 = true;

																} // G_TM_M_090

															} // G_TM_M_020

															if (tHash_Lookup_row11 != null
																	&& tHash_Lookup_row11.getCount(row11HashKey) > 1) { // G
																														// 071

																// System.out.println("WARNING: UNIQUE MATCH is
																// configured for the lookup 'row11' and it contains
																// more one result from keys : row11.paymentkey = '" +
																// row11HashKey.paymentkey + "'");
															} // G 071

															row11Struct fromLookup_row11 = null;
															row11 = row11Default;

															if (tHash_Lookup_row11 != null
																	&& tHash_Lookup_row11.hasNext()) { // G 099

																fromLookup_row11 = tHash_Lookup_row11.next();

															} // G 099

															if (fromLookup_row11 != null) {
																row11 = fromLookup_row11;
															}

															// ###############################
															{ // start of Var scope

																// ###############################
																// # Vars tables

																Var__tMap_10__Struct Var = Var__tMap_10;// ###############################
																// ###############################
																// # Output tables

																CalculateRevenue = null;

																if (!rejectedInnerJoin_tMap_10) {

// # Output table : 'CalculateRevenue'
																	count_CalculateRevenue_tMap_10++;

																	CalculateRevenue_tmp.sessionid = CalculateCost.sessionid;
																	CalculateRevenue_tmp.totalkwhconsumed = CalculateCost.totalkwhconsumed;
																	CalculateRevenue_tmp.sessionduration = CalculateCost.sessionduration;
																	CalculateRevenue_tmp.totalrevenue = (CalculateCost.paymentkey != null
																			? row11.paymentamount
																					.subtract(CalculateCost.ActualCost)
																			: BigDecimal.ZERO);
																	CalculateRevenue_tmp.paymentamount = row11.paymentamount;
																	CalculateRevenue_tmp.userkey = CalculateCost.userkey;
																	CalculateRevenue_tmp.vehiclekey = CalculateCost.vehiclekey;
																	CalculateRevenue_tmp.stationkey = CalculateCost.stationkey;
																	CalculateRevenue_tmp.chargepointkey = CalculateCost.chargepointkey;
																	CalculateRevenue_tmp.pricingmodelkey = CalculateCost.pricingmodelkey;
																	CalculateRevenue_tmp.startdatetimekey = CalculateCost.startdatetimekey;
																	CalculateRevenue_tmp.enddatetimekey = CalculateCost.enddatetimekey;
																	CalculateRevenue_tmp.bookingkey = CalculateCost.bookingkey;
																	CalculateRevenue_tmp.paymentkey = CalculateCost.paymentkey;
																	CalculateRevenue_tmp.powerconsumed = CalculateCost.powerconsumed;
																	CalculateRevenue_tmp.startdatetime = CalculateCost.startdatetime;
																	CalculateRevenue_tmp.enddatetime = CalculateCost.enddatetime;
																	CalculateRevenue_tmp.isinterrupted = CalculateCost.isinterrupted;
																	CalculateRevenue_tmp.interruptedreasonid = CalculateCost.interruptedreasonid;
																	CalculateRevenue_tmp.vehicleid = CalculateCost.vehicleid;
																	CalculateRevenue_tmp.stationid = CalculateCost.stationid;
																	CalculateRevenue_tmp.chargepointid = CalculateCost.chargepointid;
																	CalculateRevenue_tmp.pricingmodelid = CalculateCost.pricingmodelid;
																	CalculateRevenue_tmp.bookingid = CalculateCost.bookingid;
																	CalculateRevenue_tmp.StartDate = CalculateCost.StartDate;
																	CalculateRevenue_tmp.EndDate = CalculateCost.EndDate;
																	CalculateRevenue_tmp.StartHour = CalculateCost.StartHour;
																	CalculateRevenue_tmp.EndHour = CalculateCost.EndHour;
																	CalculateRevenue_tmp.isPeakHour = CalculateCost.isPeakHour;
																	CalculateRevenue_tmp.ActualCost = CalculateCost.ActualCost;
																	CalculateRevenue = CalculateRevenue_tmp;
																	log.debug("tMap_10 - Outputting the record "
																			+ count_CalculateRevenue_tMap_10
																			+ " of the output table 'CalculateRevenue'.");

																} // closing inner join bracket (2)
// ###############################

															} // end of Var scope

															rejectedInnerJoin_tMap_10 = false;

															tos_count_tMap_10++;

															/**
															 * [tMap_10 main ] stop
															 */

															/**
															 * [tMap_10 process_data_begin ] start
															 */

															s(currentComponent = "tMap_10");

															/**
															 * [tMap_10 process_data_begin ] stop
															 */

// Start of branch "CalculateRevenue"
															if (CalculateRevenue != null) {

																/**
																 * [tLogRow_1 main ] start
																 */

																s(currentComponent = "tLogRow_1");

																if (runStat.update(execStat, enableLogStash, iterateId,
																		1, 1

																		, "CalculateRevenue", "tMap_10", "tMap_10",
																		"tMap", "tLogRow_1", "tLogRow_1", "tLogRow"

																)) {
																	talendJobLogProcess(globalMap);
																}

																if (log.isTraceEnabled()) {
																	log.trace("CalculateRevenue - "
																			+ (CalculateRevenue == null ? ""
																					: CalculateRevenue.toLogString()));
																}

///////////////////////		

																String[] row_tLogRow_1 = new String[30];

																if (CalculateRevenue.sessionid != null) { //
																	row_tLogRow_1[0] = String
																			.valueOf(CalculateRevenue.sessionid);

																} //

																if (CalculateRevenue.totalkwhconsumed != null) { //
																	row_tLogRow_1[1] = CalculateRevenue.totalkwhconsumed
																			.setScale(2, java.math.RoundingMode.HALF_UP)
																			.toPlainString();

																} //

																if (CalculateRevenue.sessionduration != null) { //
																	row_tLogRow_1[2] = String
																			.valueOf(CalculateRevenue.sessionduration);

																} //

																if (CalculateRevenue.totalrevenue != null) { //
																	row_tLogRow_1[3] = CalculateRevenue.totalrevenue
																			.setScale(2, java.math.RoundingMode.HALF_UP)
																			.toPlainString();

																} //

																if (CalculateRevenue.paymentamount != null) { //
																	row_tLogRow_1[4] = CalculateRevenue.paymentamount
																			.toPlainString();

																} //

																row_tLogRow_1[5] = String
																		.valueOf(CalculateRevenue.userkey);

																row_tLogRow_1[6] = String
																		.valueOf(CalculateRevenue.vehiclekey);

																row_tLogRow_1[7] = String
																		.valueOf(CalculateRevenue.stationkey);

																row_tLogRow_1[8] = String
																		.valueOf(CalculateRevenue.chargepointkey);

																row_tLogRow_1[9] = String
																		.valueOf(CalculateRevenue.pricingmodelkey);

																row_tLogRow_1[10] = String
																		.valueOf(CalculateRevenue.startdatetimekey);

																row_tLogRow_1[11] = String
																		.valueOf(CalculateRevenue.enddatetimekey);

																if (CalculateRevenue.bookingkey != null) { //
																	row_tLogRow_1[12] = String
																			.valueOf(CalculateRevenue.bookingkey);

																} //

																if (CalculateRevenue.paymentkey != null) { //
																	row_tLogRow_1[13] = String
																			.valueOf(CalculateRevenue.paymentkey);

																} //

																if (CalculateRevenue.powerconsumed != null) { //
																	row_tLogRow_1[14] = CalculateRevenue.powerconsumed
																			.toPlainString();

																} //

																if (CalculateRevenue.startdatetime != null) { //
																	row_tLogRow_1[15] = FormatterUtils.format_Date(
																			CalculateRevenue.startdatetime,
																			"yyyy-MM-dd HH:mm:ss");

																} //

																if (CalculateRevenue.enddatetime != null) { //
																	row_tLogRow_1[16] = FormatterUtils.format_Date(
																			CalculateRevenue.enddatetime,
																			"yyyy-MM-dd HH:mm:ss");

																} //

																if (CalculateRevenue.isinterrupted != null) { //
																	row_tLogRow_1[17] = String
																			.valueOf(CalculateRevenue.isinterrupted);

																} //

																if (CalculateRevenue.interruptedreasonid != null) { //
																	row_tLogRow_1[18] = String.valueOf(
																			CalculateRevenue.interruptedreasonid);

																} //

																row_tLogRow_1[19] = String
																		.valueOf(CalculateRevenue.vehicleid);

																row_tLogRow_1[20] = String
																		.valueOf(CalculateRevenue.stationid);

																row_tLogRow_1[21] = String
																		.valueOf(CalculateRevenue.chargepointid);

																row_tLogRow_1[22] = String
																		.valueOf(CalculateRevenue.pricingmodelid);

																if (CalculateRevenue.bookingid != null) { //
																	row_tLogRow_1[23] = String
																			.valueOf(CalculateRevenue.bookingid);

																} //

																if (CalculateRevenue.StartDate != null) { //
																	row_tLogRow_1[24] = FormatterUtils.format_Date(
																			CalculateRevenue.StartDate, "yyyy-MM-dd");

																} //

																if (CalculateRevenue.EndDate != null) { //
																	row_tLogRow_1[25] = FormatterUtils.format_Date(
																			CalculateRevenue.EndDate, "yyyy-MM-dd");

																} //

																if (CalculateRevenue.StartHour != null) { //
																	row_tLogRow_1[26] = String
																			.valueOf(CalculateRevenue.StartHour);

																} //

																if (CalculateRevenue.EndHour != null) { //
																	row_tLogRow_1[27] = String
																			.valueOf(CalculateRevenue.EndHour);

																} //

																if (CalculateRevenue.isPeakHour != null) { //
																	row_tLogRow_1[28] = String
																			.valueOf(CalculateRevenue.isPeakHour);

																} //

																if (CalculateRevenue.ActualCost != null) { //
																	row_tLogRow_1[29] = CalculateRevenue.ActualCost
																			.toPlainString();

																} //

																util_tLogRow_1.addRow(row_tLogRow_1);
																nb_line_tLogRow_1++;
																log.info("tLogRow_1 - Content of row "
																		+ nb_line_tLogRow_1 + ": "
																		+ TalendString.unionString("|", row_tLogRow_1));
//////

//////                    

///////////////////////    			

																tos_count_tLogRow_1++;

																/**
																 * [tLogRow_1 main ] stop
																 */

																/**
																 * [tLogRow_1 process_data_begin ] start
																 */

																s(currentComponent = "tLogRow_1");

																/**
																 * [tLogRow_1 process_data_begin ] stop
																 */

																/**
																 * [tLogRow_1 process_data_end ] start
																 */

																s(currentComponent = "tLogRow_1");

																/**
																 * [tLogRow_1 process_data_end ] stop
																 */

															} // End of branch "CalculateRevenue"

															/**
															 * [tMap_10 process_data_end ] start
															 */

															s(currentComponent = "tMap_10");

															/**
															 * [tMap_10 process_data_end ] stop
															 */

														} // End of branch "CalculateCost"

														/**
														 * [tMap_9 process_data_end ] start
														 */

														s(currentComponent = "tMap_9");

														/**
														 * [tMap_9 process_data_end ] stop
														 */

													} // End of branch "PrepareRevenue"

													/**
													 * [tMap_8 process_data_end ] start
													 */

													s(currentComponent = "tMap_8");

													/**
													 * [tMap_8 process_data_end ] stop
													 */

												} // End of branch "PaymentOptional"

												/**
												 * [tMap_7 process_data_end ] start
												 */

												s(currentComponent = "tMap_7");

												/**
												 * [tMap_7 process_data_end ] stop
												 */

											} // End of branch "BookingOptional"

											/**
											 * [tMap_6 process_data_end ] start
											 */

											s(currentComponent = "tMap_6");

											/**
											 * [tMap_6 process_data_end ] stop
											 */

										} // End of branch "EndTimeFound"

// Start of branch "EndTimeNotFound"
										if (EndTimeNotFound != null) {

											/**
											 * [tWarn_5 main ] start
											 */

											s(currentComponent = "tWarn_5");

											if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

													, "EndTimeNotFound", "tMap_5", "tMap_5", "tMap", "tWarn_5",
													"tWarn_5", "tWarn"

											)) {
												talendJobLogProcess(globalMap);
											}

											if (log.isTraceEnabled()) {
												log.trace("EndTimeNotFound - " + (EndTimeNotFound == null ? ""
														: EndTimeNotFound.toLogString()));
											}

											try {

												resumeUtil.addLog("USER_DEF_LOG", "NODE:tWarn_5", "",
														Thread.currentThread().getId() + "", "WARN", "",
														"Warning: End Time not found for Session Fact '"
																+ EndTimeNotFound.sessionid
																+ "'. Please verify the session data.",
														"", "");
												log.warn("tWarn_5 - " + ("Message: ")
														+ ("Warning: End Time not found for Session Fact '"
																+ EndTimeNotFound.sessionid
																+ "'. Please verify the session data.")
														+ (". Code: ") + (42));
												globalMap.put("tWarn_5_WARN_MESSAGES",
														"Warning: End Time not found for Session Fact '"
																+ EndTimeNotFound.sessionid
																+ "'. Please verify the session data.");
												globalMap.put("tWarn_5_WARN_PRIORITY", 4);
												globalMap.put("tWarn_5_WARN_CODE", 42);

											} catch (Exception e_tWarn_5) {
												globalMap.put("tWarn_5_ERROR_MESSAGE", e_tWarn_5.getMessage());
												logIgnoredError(String.format(
														"tWarn_5 - tWarn failed to log message due to internal error: %s",
														e_tWarn_5), e_tWarn_5);
											}

											tos_count_tWarn_5++;

											/**
											 * [tWarn_5 main ] stop
											 */

											/**
											 * [tWarn_5 process_data_begin ] start
											 */

											s(currentComponent = "tWarn_5");

											/**
											 * [tWarn_5 process_data_begin ] stop
											 */

											/**
											 * [tWarn_5 process_data_end ] start
											 */

											s(currentComponent = "tWarn_5");

											/**
											 * [tWarn_5 process_data_end ] stop
											 */

										} // End of branch "EndTimeNotFound"

										/**
										 * [tMap_5 process_data_end ] start
										 */

										s(currentComponent = "tMap_5");

										/**
										 * [tMap_5 process_data_end ] stop
										 */

									} // End of branch "StartTimeFound"

// Start of branch "StartTimeNotFound"
									if (StartTimeNotFound != null) {

										/**
										 * [tWarn_4 main ] start
										 */

										s(currentComponent = "tWarn_4");

										if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

												, "StartTimeNotFound", "tMap_4", "tMap_4", "tMap", "tWarn_4", "tWarn_4",
												"tWarn"

										)) {
											talendJobLogProcess(globalMap);
										}

										if (log.isTraceEnabled()) {
											log.trace("StartTimeNotFound - " + (StartTimeNotFound == null ? ""
													: StartTimeNotFound.toLogString()));
										}

										try {

											resumeUtil.addLog("USER_DEF_LOG", "NODE:tWarn_4", "",
													Thread.currentThread().getId() + "", "WARN", "",
													"Warning: Start Time not found for Session Fact '"
															+ StartTimeNotFound.sessionid
															+ "'. Please verify the session data.",
													"", "");
											log.warn("tWarn_4 - " + ("Message: ")
													+ ("Warning: Start Time not found for Session Fact '"
															+ StartTimeNotFound.sessionid
															+ "'. Please verify the session data.")
													+ (". Code: ") + (42));
											globalMap.put("tWarn_4_WARN_MESSAGES",
													"Warning: Start Time not found for Session Fact '"
															+ StartTimeNotFound.sessionid
															+ "'. Please verify the session data.");
											globalMap.put("tWarn_4_WARN_PRIORITY", 4);
											globalMap.put("tWarn_4_WARN_CODE", 42);

										} catch (Exception e_tWarn_4) {
											globalMap.put("tWarn_4_ERROR_MESSAGE", e_tWarn_4.getMessage());
											logIgnoredError(String.format(
													"tWarn_4 - tWarn failed to log message due to internal error: %s",
													e_tWarn_4), e_tWarn_4);
										}

										tos_count_tWarn_4++;

										/**
										 * [tWarn_4 main ] stop
										 */

										/**
										 * [tWarn_4 process_data_begin ] start
										 */

										s(currentComponent = "tWarn_4");

										/**
										 * [tWarn_4 process_data_begin ] stop
										 */

										/**
										 * [tWarn_4 process_data_end ] start
										 */

										s(currentComponent = "tWarn_4");

										/**
										 * [tWarn_4 process_data_end ] stop
										 */

									} // End of branch "StartTimeNotFound"

									/**
									 * [tMap_4 process_data_end ] start
									 */

									s(currentComponent = "tMap_4");

									/**
									 * [tMap_4 process_data_end ] stop
									 */

								} // End of branch "VehicleFound"

// Start of branch "VehicleNotFound"
								if (VehicleNotFound != null) {

									/**
									 * [tWarn_3 main ] start
									 */

									s(currentComponent = "tWarn_3");

									if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

											, "VehicleNotFound", "tMap_3", "tMap_3", "tMap", "tWarn_3", "tWarn_3",
											"tWarn"

									)) {
										talendJobLogProcess(globalMap);
									}

									if (log.isTraceEnabled()) {
										log.trace("VehicleNotFound - "
												+ (VehicleNotFound == null ? "" : VehicleNotFound.toLogString()));
									}

									try {

										resumeUtil.addLog("USER_DEF_LOG", "NODE:tWarn_3", "",
												Thread.currentThread().getId() + "", "WARN", "",
												"Warning: Vehicle not found for Session Fact '"
														+ VehicleNotFound.sessionid
														+ "'. Please verify the vehicle data.",
												"", "");
										log.warn("tWarn_3 - " + ("Message: ")
												+ ("Warning: Vehicle not found for Session Fact '"
														+ VehicleNotFound.sessionid
														+ "'. Please verify the vehicle data.")
												+ (". Code: ") + (42));
										globalMap.put("tWarn_3_WARN_MESSAGES",
												"Warning: Vehicle not found for Session Fact '"
														+ VehicleNotFound.sessionid
														+ "'. Please verify the vehicle data.");
										globalMap.put("tWarn_3_WARN_PRIORITY", 4);
										globalMap.put("tWarn_3_WARN_CODE", 42);

									} catch (Exception e_tWarn_3) {
										globalMap.put("tWarn_3_ERROR_MESSAGE", e_tWarn_3.getMessage());
										logIgnoredError(String.format(
												"tWarn_3 - tWarn failed to log message due to internal error: %s",
												e_tWarn_3), e_tWarn_3);
									}

									tos_count_tWarn_3++;

									/**
									 * [tWarn_3 main ] stop
									 */

									/**
									 * [tWarn_3 process_data_begin ] start
									 */

									s(currentComponent = "tWarn_3");

									/**
									 * [tWarn_3 process_data_begin ] stop
									 */

									/**
									 * [tWarn_3 process_data_end ] start
									 */

									s(currentComponent = "tWarn_3");

									/**
									 * [tWarn_3 process_data_end ] stop
									 */

								} // End of branch "VehicleNotFound"

								/**
								 * [tMap_3 process_data_end ] start
								 */

								s(currentComponent = "tMap_3");

								/**
								 * [tMap_3 process_data_end ] stop
								 */

							} // End of branch "PMFound"

// Start of branch "PMNotFound"
							if (PMNotFound != null) {

								/**
								 * [tWarn_2 main ] start
								 */

								s(currentComponent = "tWarn_2");

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "PMNotFound", "tMap_2", "tMap_2", "tMap", "tWarn_2", "tWarn_2", "tWarn"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("PMNotFound - " + (PMNotFound == null ? "" : PMNotFound.toLogString()));
								}

								try {

									resumeUtil.addLog(
											"USER_DEF_LOG", "NODE:tWarn_2", "", Thread.currentThread().getId() + "",
											"WARN", "", "Warning: Pricing model not found for Session Fact '"
													+ PMNotFound.sessionid + "'. Please verify the pricing model data.",
											"", "");
									log.warn("tWarn_2 - " + ("Message: ")
											+ ("Warning: Pricing model not found for Session Fact '"
													+ PMNotFound.sessionid + "'. Please verify the pricing model data.")
											+ (". Code: ") + (42));
									globalMap.put("tWarn_2_WARN_MESSAGES",
											"Warning: Pricing model not found for Session Fact '" + PMNotFound.sessionid
													+ "'. Please verify the pricing model data.");
									globalMap.put("tWarn_2_WARN_PRIORITY", 4);
									globalMap.put("tWarn_2_WARN_CODE", 42);

								} catch (Exception e_tWarn_2) {
									globalMap.put("tWarn_2_ERROR_MESSAGE", e_tWarn_2.getMessage());
									logIgnoredError(String.format(
											"tWarn_2 - tWarn failed to log message due to internal error: %s",
											e_tWarn_2), e_tWarn_2);
								}

								tos_count_tWarn_2++;

								/**
								 * [tWarn_2 main ] stop
								 */

								/**
								 * [tWarn_2 process_data_begin ] start
								 */

								s(currentComponent = "tWarn_2");

								/**
								 * [tWarn_2 process_data_begin ] stop
								 */

								/**
								 * [tWarn_2 process_data_end ] start
								 */

								s(currentComponent = "tWarn_2");

								/**
								 * [tWarn_2 process_data_end ] stop
								 */

							} // End of branch "PMNotFound"

							/**
							 * [tMap_2 process_data_end ] start
							 */

							s(currentComponent = "tMap_2");

							/**
							 * [tMap_2 process_data_end ] stop
							 */

						} // End of branch "CPFound"

// Start of branch "CPNotFound"
						if (CPNotFound != null) {

							/**
							 * [tWarn_1 main ] start
							 */

							s(currentComponent = "tWarn_1");

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "CPNotFound", "tMap_1", "tMap_1", "tMap", "tWarn_1", "tWarn_1", "tWarn"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("CPNotFound - " + (CPNotFound == null ? "" : CPNotFound.toLogString()));
							}

							try {

								resumeUtil.addLog(
										"USER_DEF_LOG", "NODE:tWarn_1", "", Thread.currentThread().getId() + "", "WARN",
										"", "Warning: Charging point not found for Session Fact '"
												+ CPNotFound.sessionid + "'. Please verify the charging point data.",
										"", "");
								log.warn("tWarn_1 - "
										+ ("Message: ") + ("Warning: Charging point not found for Session Fact '"
												+ CPNotFound.sessionid + "'. Please verify the charging point data.")
										+ (". Code: ") + (42));
								globalMap.put("tWarn_1_WARN_MESSAGES",
										"Warning: Charging point not found for Session Fact '" + CPNotFound.sessionid
												+ "'. Please verify the charging point data.");
								globalMap.put("tWarn_1_WARN_PRIORITY", 4);
								globalMap.put("tWarn_1_WARN_CODE", 42);

							} catch (Exception e_tWarn_1) {
								globalMap.put("tWarn_1_ERROR_MESSAGE", e_tWarn_1.getMessage());
								logIgnoredError(
										String.format("tWarn_1 - tWarn failed to log message due to internal error: %s",
												e_tWarn_1),
										e_tWarn_1);
							}

							tos_count_tWarn_1++;

							/**
							 * [tWarn_1 main ] stop
							 */

							/**
							 * [tWarn_1 process_data_begin ] start
							 */

							s(currentComponent = "tWarn_1");

							/**
							 * [tWarn_1 process_data_begin ] stop
							 */

							/**
							 * [tWarn_1 process_data_end ] start
							 */

							s(currentComponent = "tWarn_1");

							/**
							 * [tWarn_1 process_data_end ] stop
							 */

						} // End of branch "CPNotFound"

						/**
						 * [tMap_1 process_data_end ] start
						 */

						s(currentComponent = "tMap_1");

						/**
						 * [tMap_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"session\"";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"session\"";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);
				log.debug("tDBInput_1 - Retrieved records count: " + nb_line_tDBInput_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Done."));

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				s(currentComponent = "tMap_1");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row2 != null) {
					tHash_Lookup_row2.endGet();
				}
				globalMap.remove("tHash_Lookup_row2");

// ###############################      
				log.debug("tMap_1 - Written records count in the table 'CPFound': " + count_CPFound_tMap_1 + ".");
				log.debug("tMap_1 - Written records count in the table 'CPNotFound': " + count_CPNotFound_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tDBInput_1", "\"session\"", "tPostgresqlInput", "tMap_1", "tMap_1", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Done."));

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tMap_2 end ] start
				 */

				s(currentComponent = "tMap_2");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row3 != null) {
					tHash_Lookup_row3.endGet();
				}
				globalMap.remove("tHash_Lookup_row3");

// ###############################      
				log.debug("tMap_2 - Written records count in the table 'PMFound': " + count_PMFound_tMap_2 + ".");
				log.debug("tMap_2 - Written records count in the table 'PMNotFound': " + count_PMNotFound_tMap_2 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "CPFound", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tMap_2", "tMap_2", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_2 - " + ("Done."));

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tMap_3 end ] start
				 */

				s(currentComponent = "tMap_3");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row4 != null) {
					tHash_Lookup_row4.endGet();
				}
				globalMap.remove("tHash_Lookup_row4");

// ###############################      
				log.debug("tMap_3 - Written records count in the table 'VehicleFound': " + count_VehicleFound_tMap_3
						+ ".");
				log.debug("tMap_3 - Written records count in the table 'VehicleNotFound': "
						+ count_VehicleNotFound_tMap_3 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "PMFound", 2, 0,
						"tMap_2", "tMap_2", "tMap", "tMap_3", "tMap_3", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_3 - " + ("Done."));

				ok_Hash.put("tMap_3", true);
				end_Hash.put("tMap_3", System.currentTimeMillis());

				/**
				 * [tMap_3 end ] stop
				 */

				/**
				 * [tMap_4 end ] start
				 */

				s(currentComponent = "tMap_4");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row5 != null) {
					tHash_Lookup_row5.endGet();
				}
				globalMap.remove("tHash_Lookup_row5");

// ###############################      
				log.debug("tMap_4 - Written records count in the table 'StartTimeFound': " + count_StartTimeFound_tMap_4
						+ ".");
				log.debug("tMap_4 - Written records count in the table 'StartTimeNotFound': "
						+ count_StartTimeNotFound_tMap_4 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "VehicleFound", 2, 0,
						"tMap_3", "tMap_3", "tMap", "tMap_4", "tMap_4", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_4 - " + ("Done."));

				ok_Hash.put("tMap_4", true);
				end_Hash.put("tMap_4", System.currentTimeMillis());

				/**
				 * [tMap_4 end ] stop
				 */

				/**
				 * [tMap_5 end ] start
				 */

				s(currentComponent = "tMap_5");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row6 != null) {
					tHash_Lookup_row6.endGet();
				}
				globalMap.remove("tHash_Lookup_row6");

// ###############################      
				log.debug("tMap_5 - Written records count in the table 'EndTimeFound': " + count_EndTimeFound_tMap_5
						+ ".");
				log.debug("tMap_5 - Written records count in the table 'EndTimeNotFound': "
						+ count_EndTimeNotFound_tMap_5 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "StartTimeFound", 2, 0,
						"tMap_4", "tMap_4", "tMap", "tMap_5", "tMap_5", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_5 - " + ("Done."));

				ok_Hash.put("tMap_5", true);
				end_Hash.put("tMap_5", System.currentTimeMillis());

				/**
				 * [tMap_5 end ] stop
				 */

				/**
				 * [tMap_6 end ] start
				 */

				s(currentComponent = "tMap_6");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row7 != null) {
					tHash_Lookup_row7.endGet();
				}
				globalMap.remove("tHash_Lookup_row7");

// ###############################      
				log.debug("tMap_6 - Written records count in the table 'BookingOptional': "
						+ count_BookingOptional_tMap_6 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "EndTimeFound", 2, 0,
						"tMap_5", "tMap_5", "tMap", "tMap_6", "tMap_6", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_6 - " + ("Done."));

				ok_Hash.put("tMap_6", true);
				end_Hash.put("tMap_6", System.currentTimeMillis());

				/**
				 * [tMap_6 end ] stop
				 */

				/**
				 * [tMap_7 end ] start
				 */

				s(currentComponent = "tMap_7");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row8 != null) {
					tHash_Lookup_row8.endGet();
				}
				globalMap.remove("tHash_Lookup_row8");

// ###############################      
				log.debug("tMap_7 - Written records count in the table 'PaymentOptional': "
						+ count_PaymentOptional_tMap_7 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "BookingOptional", 2, 0,
						"tMap_6", "tMap_6", "tMap", "tMap_7", "tMap_7", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_7 - " + ("Done."));

				ok_Hash.put("tMap_7", true);
				end_Hash.put("tMap_7", System.currentTimeMillis());

				/**
				 * [tMap_7 end ] stop
				 */

				/**
				 * [tMap_8 end ] start
				 */

				s(currentComponent = "tMap_8");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row9 != null) {
					tHash_Lookup_row9.endGet();
				}
				globalMap.remove("tHash_Lookup_row9");

// ###############################      
				log.debug("tMap_8 - Written records count in the table 'PrepareRevenue': " + count_PrepareRevenue_tMap_8
						+ ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "PaymentOptional", 2, 0,
						"tMap_7", "tMap_7", "tMap", "tMap_8", "tMap_8", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_8 - " + ("Done."));

				ok_Hash.put("tMap_8", true);
				end_Hash.put("tMap_8", System.currentTimeMillis());

				/**
				 * [tMap_8 end ] stop
				 */

				/**
				 * [tMap_9 end ] start
				 */

				s(currentComponent = "tMap_9");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row10 != null) {
					tHash_Lookup_row10.endGet();
				}
				globalMap.remove("tHash_Lookup_row10");

// ###############################      
				log.debug("tMap_9 - Written records count in the table 'CalculateCost': " + count_CalculateCost_tMap_9
						+ ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "PrepareRevenue", 2, 0,
						"tMap_8", "tMap_8", "tMap", "tMap_9", "tMap_9", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_9 - " + ("Done."));

				ok_Hash.put("tMap_9", true);
				end_Hash.put("tMap_9", System.currentTimeMillis());

				/**
				 * [tMap_9 end ] stop
				 */

				/**
				 * [tMap_10 end ] start
				 */

				s(currentComponent = "tMap_10");

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row11 != null) {
					tHash_Lookup_row11.endGet();
				}
				globalMap.remove("tHash_Lookup_row11");

// ###############################      
				log.debug("tMap_10 - Written records count in the table 'CalculateRevenue': "
						+ count_CalculateRevenue_tMap_10 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "CalculateCost", 2, 0,
						"tMap_9", "tMap_9", "tMap", "tMap_10", "tMap_10", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_10 - " + ("Done."));

				ok_Hash.put("tMap_10", true);
				end_Hash.put("tMap_10", System.currentTimeMillis());

				/**
				 * [tMap_10 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				s(currentComponent = "tLogRow_1");

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ") + (nb_line_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "CalculateRevenue", 2, 0,
						"tMap_10", "tMap_10", "tMap", "tLogRow_1", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

				/**
				 * [tWarn_5 end ] start
				 */

				s(currentComponent = "tWarn_5");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "EndTimeNotFound", 2, 0,
						"tMap_5", "tMap_5", "tMap", "tWarn_5", "tWarn_5", "tWarn", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tWarn_5 - " + ("Done."));

				ok_Hash.put("tWarn_5", true);
				end_Hash.put("tWarn_5", System.currentTimeMillis());

				/**
				 * [tWarn_5 end ] stop
				 */

				/**
				 * [tWarn_4 end ] start
				 */

				s(currentComponent = "tWarn_4");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "StartTimeNotFound", 2,
						0, "tMap_4", "tMap_4", "tMap", "tWarn_4", "tWarn_4", "tWarn", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tWarn_4 - " + ("Done."));

				ok_Hash.put("tWarn_4", true);
				end_Hash.put("tWarn_4", System.currentTimeMillis());

				/**
				 * [tWarn_4 end ] stop
				 */

				/**
				 * [tWarn_3 end ] start
				 */

				s(currentComponent = "tWarn_3");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "VehicleNotFound", 2, 0,
						"tMap_3", "tMap_3", "tMap", "tWarn_3", "tWarn_3", "tWarn", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tWarn_3 - " + ("Done."));

				ok_Hash.put("tWarn_3", true);
				end_Hash.put("tWarn_3", System.currentTimeMillis());

				/**
				 * [tWarn_3 end ] stop
				 */

				/**
				 * [tWarn_2 end ] start
				 */

				s(currentComponent = "tWarn_2");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "PMNotFound", 2, 0,
						"tMap_2", "tMap_2", "tMap", "tWarn_2", "tWarn_2", "tWarn", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tWarn_2 - " + ("Done."));

				ok_Hash.put("tWarn_2", true);
				end_Hash.put("tWarn_2", System.currentTimeMillis());

				/**
				 * [tWarn_2 end ] stop
				 */

				/**
				 * [tWarn_1 end ] start
				 */

				s(currentComponent = "tWarn_1");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "CPNotFound", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tWarn_1", "tWarn_1", "tWarn", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tWarn_1 - " + ("Done."));

				ok_Hash.put("tWarn_1", true);
				end_Hash.put("tWarn_1", System.currentTimeMillis());

				/**
				 * [tWarn_1 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBInput_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk3", 0, "ok");
			}

			tDBCommit_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tMap_10"
			globalMap.remove("tHash_Lookup_row11");

			// free memory for "tMap_9"
			globalMap.remove("tHash_Lookup_row10");

			// free memory for "tMap_8"
			globalMap.remove("tHash_Lookup_row9");

			// free memory for "tMap_7"
			globalMap.remove("tHash_Lookup_row8");

			// free memory for "tMap_6"
			globalMap.remove("tHash_Lookup_row7");

			// free memory for "tMap_5"
			globalMap.remove("tHash_Lookup_row6");

			// free memory for "tMap_4"
			globalMap.remove("tHash_Lookup_row5");

			// free memory for "tMap_3"
			globalMap.remove("tHash_Lookup_row4");

			// free memory for "tMap_2"
			globalMap.remove("tHash_Lookup_row3");

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row2");

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				s(currentComponent = "tDBInput_1");

				cLabel = "\"session\"";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				s(currentComponent = "tMap_1");

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tMap_2 finally ] start
				 */

				s(currentComponent = "tMap_2");

				/**
				 * [tMap_2 finally ] stop
				 */

				/**
				 * [tMap_3 finally ] start
				 */

				s(currentComponent = "tMap_3");

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tMap_4 finally ] start
				 */

				s(currentComponent = "tMap_4");

				/**
				 * [tMap_4 finally ] stop
				 */

				/**
				 * [tMap_5 finally ] start
				 */

				s(currentComponent = "tMap_5");

				/**
				 * [tMap_5 finally ] stop
				 */

				/**
				 * [tMap_6 finally ] start
				 */

				s(currentComponent = "tMap_6");

				/**
				 * [tMap_6 finally ] stop
				 */

				/**
				 * [tMap_7 finally ] start
				 */

				s(currentComponent = "tMap_7");

				/**
				 * [tMap_7 finally ] stop
				 */

				/**
				 * [tMap_8 finally ] start
				 */

				s(currentComponent = "tMap_8");

				/**
				 * [tMap_8 finally ] stop
				 */

				/**
				 * [tMap_9 finally ] start
				 */

				s(currentComponent = "tMap_9");

				/**
				 * [tMap_9 finally ] stop
				 */

				/**
				 * [tMap_10 finally ] start
				 */

				s(currentComponent = "tMap_10");

				/**
				 * [tMap_10 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				s(currentComponent = "tLogRow_1");

				/**
				 * [tLogRow_1 finally ] stop
				 */

				/**
				 * [tWarn_5 finally ] start
				 */

				s(currentComponent = "tWarn_5");

				/**
				 * [tWarn_5 finally ] stop
				 */

				/**
				 * [tWarn_4 finally ] start
				 */

				s(currentComponent = "tWarn_4");

				/**
				 * [tWarn_4 finally ] stop
				 */

				/**
				 * [tWarn_3 finally ] start
				 */

				s(currentComponent = "tWarn_3");

				/**
				 * [tWarn_3 finally ] stop
				 */

				/**
				 * [tWarn_2 finally ] start
				 */

				s(currentComponent = "tWarn_2");

				/**
				 * [tWarn_2 finally ] stop
				 */

				/**
				 * [tWarn_1 finally ] start
				 */

				s(currentComponent = "tWarn_1");

				/**
				 * [tWarn_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int chargepointkey;

		public int getChargepointkey() {
			return this.chargepointkey;
		}

		public Boolean chargepointkeyIsNullable() {
			return false;
		}

		public Boolean chargepointkeyIsKey() {
			return true;
		}

		public Integer chargepointkeyLength() {
			return 10;
		}

		public Integer chargepointkeyPrecision() {
			return 0;
		}

		public String chargepointkeyDefault() {

			return "nextval('chargingpoint_chargepointkey_seq'::regclass)";

		}

		public String chargepointkeyComment() {

			return "";

		}

		public String chargepointkeyPattern() {

			return "";

		}

		public String chargepointkeyOriginalDbColumnName() {

			return "chargepointkey";

		}

		public Integer chargepointid;

		public Integer getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return true;
		}

		public Boolean chargepointidIsKey() {
			return false;
		}

		public Integer chargepointidLength() {
			return 10;
		}

		public Integer chargepointidPrecision() {
			return 0;
		}

		public String chargepointidDefault() {

			return null;

		}

		public String chargepointidComment() {

			return "";

		}

		public String chargepointidPattern() {

			return "";

		}

		public String chargepointidOriginalDbColumnName() {

			return "chargepointid";

		}

		public BigDecimal chargingcapacity;

		public BigDecimal getChargingcapacity() {
			return this.chargingcapacity;
		}

		public Boolean chargingcapacityIsNullable() {
			return true;
		}

		public Boolean chargingcapacityIsKey() {
			return false;
		}

		public Integer chargingcapacityLength() {
			return 10;
		}

		public Integer chargingcapacityPrecision() {
			return 2;
		}

		public String chargingcapacityDefault() {

			return null;

		}

		public String chargingcapacityComment() {

			return "";

		}

		public String chargingcapacityPattern() {

			return "";

		}

		public String chargingcapacityOriginalDbColumnName() {

			return "chargingcapacity";

		}

		public String chargertype;

		public String getChargertype() {
			return this.chargertype;
		}

		public Boolean chargertypeIsNullable() {
			return true;
		}

		public Boolean chargertypeIsKey() {
			return false;
		}

		public Integer chargertypeLength() {
			return 20;
		}

		public Integer chargertypePrecision() {
			return 0;
		}

		public String chargertypeDefault() {

			return null;

		}

		public String chargertypeComment() {

			return "";

		}

		public String chargertypePattern() {

			return "";

		}

		public String chargertypeOriginalDbColumnName() {

			return "chargertype";

		}

		public BigDecimal installationcost;

		public BigDecimal getInstallationcost() {
			return this.installationcost;
		}

		public Boolean installationcostIsNullable() {
			return true;
		}

		public Boolean installationcostIsKey() {
			return false;
		}

		public Integer installationcostLength() {
			return 10;
		}

		public Integer installationcostPrecision() {
			return 2;
		}

		public String installationcostDefault() {

			return null;

		}

		public String installationcostComment() {

			return "";

		}

		public String installationcostPattern() {

			return "";

		}

		public String installationcostOriginalDbColumnName() {

			return "installationcost";

		}

		public Integer chargingpointstatuskey;

		public Integer getChargingpointstatuskey() {
			return this.chargingpointstatuskey;
		}

		public Boolean chargingpointstatuskeyIsNullable() {
			return true;
		}

		public Boolean chargingpointstatuskeyIsKey() {
			return false;
		}

		public Integer chargingpointstatuskeyLength() {
			return 10;
		}

		public Integer chargingpointstatuskeyPrecision() {
			return 0;
		}

		public String chargingpointstatuskeyDefault() {

			return null;

		}

		public String chargingpointstatuskeyComment() {

			return "";

		}

		public String chargingpointstatuskeyPattern() {

			return "";

		}

		public String chargingpointstatuskeyOriginalDbColumnName() {

			return "chargingpointstatuskey";

		}

		public Integer stationkey;

		public Integer getStationkey() {
			return this.stationkey;
		}

		public Boolean stationkeyIsNullable() {
			return true;
		}

		public Boolean stationkeyIsKey() {
			return false;
		}

		public Integer stationkeyLength() {
			return 10;
		}

		public Integer stationkeyPrecision() {
			return 0;
		}

		public String stationkeyDefault() {

			return null;

		}

		public String stationkeyComment() {

			return "";

		}

		public String stationkeyPattern() {

			return "";

		}

		public String stationkeyOriginalDbColumnName() {

			return "stationkey";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.chargepointid == null) ? 0 : this.chargepointid.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row2Struct other = (row2Struct) obj;

			if (this.chargepointid == null) {
				if (other.chargepointid != null)
					return false;

			} else if (!this.chargepointid.equals(other.chargepointid))

				return false;

			return true;
		}

		public void copyDataTo(row2Struct other) {

			other.chargepointkey = this.chargepointkey;
			other.chargepointid = this.chargepointid;
			other.chargingcapacity = this.chargingcapacity;
			other.chargertype = this.chargertype;
			other.installationcost = this.installationcost;
			other.chargingpointstatuskey = this.chargingpointstatuskey;
			other.stationkey = this.stationkey;

		}

		public void copyKeysDataTo(row2Struct other) {

			other.chargepointid = this.chargepointid;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.chargepointid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.chargepointid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.chargepointkey = dis.readInt();

				this.chargingcapacity = (BigDecimal) ois.readObject();

				this.chargertype = readString(dis, ois);

				this.installationcost = (BigDecimal) ois.readObject();

				this.chargingpointstatuskey = readInteger(dis, ois);

				this.stationkey = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.chargepointkey = objectIn.readInt();

				this.chargingcapacity = (BigDecimal) objectIn.readObject();

				this.chargertype = readString(dis, objectIn);

				this.installationcost = (BigDecimal) objectIn.readObject();

				this.chargingpointstatuskey = readInteger(dis, objectIn);

				this.stationkey = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.chargepointkey);

				oos.writeObject(this.chargingcapacity);

				writeString(this.chargertype, dos, oos);

				oos.writeObject(this.installationcost);

				writeInteger(this.chargingpointstatuskey, dos, oos);

				writeInteger(this.stationkey, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.chargepointkey);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.chargingcapacity);

				writeString(this.chargertype, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.installationcost);

				writeInteger(this.chargingpointstatuskey, dos, objectOut);

				writeInteger(this.stationkey, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointkey=" + String.valueOf(chargepointkey));
			sb.append(",chargepointid=" + String.valueOf(chargepointid));
			sb.append(",chargingcapacity=" + String.valueOf(chargingcapacity));
			sb.append(",chargertype=" + chargertype);
			sb.append(",installationcost=" + String.valueOf(installationcost));
			sb.append(",chargingpointstatuskey=" + String.valueOf(chargingpointstatuskey));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(chargepointkey);

			sb.append("|");

			if (chargepointid == null) {
				sb.append("<null>");
			} else {
				sb.append(chargepointid);
			}

			sb.append("|");

			if (chargingcapacity == null) {
				sb.append("<null>");
			} else {
				sb.append(chargingcapacity);
			}

			sb.append("|");

			if (chargertype == null) {
				sb.append("<null>");
			} else {
				sb.append(chargertype);
			}

			sb.append("|");

			if (installationcost == null) {
				sb.append("<null>");
			} else {
				sb.append(installationcost);
			}

			sb.append("|");

			if (chargingpointstatuskey == null) {
				sb.append("<null>");
			} else {
				sb.append(chargingpointstatuskey);
			}

			sb.append("|");

			if (stationkey == null) {
				sb.append("<null>");
			} else {
				sb.append(stationkey);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.chargepointid, other.chargepointid);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_2", "FhF8gQ_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row2Struct row2 = new row2Struct();

				/**
				 * [tAdvancedHash_row2 begin ] start
				 */

				sh("tAdvancedHash_row2");

				s(currentComponent = "tAdvancedHash_row2");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tAdvancedHash_row2 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row2", "tAdvancedHash_row2", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row2
				// source node:tDBInput_2 - inputs:(after_tDBInput_1) outputs:(row2,row2) |
				// target node:tAdvancedHash_row2 - inputs:(row2) outputs:()
				// linked node: tMap_1 - inputs:(row1,row2) outputs:(CPFound,CPNotFound)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row2Struct>getLookup(matchingModeEnum_row2);

				globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);

				/**
				 * [tAdvancedHash_row2 begin ] stop
				 */

				/**
				 * [tDBInput_2 begin ] start
				 */

				sh("tDBInput_2");

				s(currentComponent = "tDBInput_2");

				cLabel = "\"chargingpoint\"";

				int tos_count_tDBInput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_2 = new StringBuilder();
							log4jParamters_tDBInput_2.append("Parameters:");
							log4jParamters_tDBInput_2.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("QUERY" + " = "
									+ "\"SELECT    \\\"chargingpoint\\\".\\\"chargepointkey\\\",    \\\"chargingpoint\\\".\\\"chargepointid\\\",    \\\"chargingpoint\\\".\\\"chargingcapacity\\\",    \\\"chargingpoint\\\".\\\"chargertype\\\",    \\\"chargingpoint\\\".\\\"installationcost\\\",    \\\"chargingpoint\\\".\\\"chargingpointstatuskey\\\",    \\\"chargingpoint\\\".\\\"stationkey\\\"  FROM \\\"chargingpoint\\\"\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargepointkey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargepointid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargingcapacity") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargertype") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("installationcost") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargingpointstatuskey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("stationkey") + "}]");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_2 - " + (log4jParamters_tDBInput_2));
						}
					}
					new BytesLimit65535_tDBInput_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_2", "\"chargingpoint\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				conn_tDBInput_2 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_2 != null) {
					if (conn_tDBInput_2.getMetaData() != null) {

						log.debug("tDBInput_2 - Uses an existing connection with username '"
								+ conn_tDBInput_2.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_2.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "SELECT \n  \"chargingpoint\".\"chargepointkey\", \n  \"chargingpoint\".\"chargepointid\", \n  \"chargingpoint\".\"charging"
						+ "capacity\", \n  \"chargingpoint\".\"chargertype\", \n  \"chargingpoint\".\"installationcost\", \n  \"chargingpoint\".\"char"
						+ "gingpointstatuskey\", \n  \"chargingpoint\".\"stationkey\"\n FROM \"chargingpoint\"";

				log.debug("tDBInput_2 - Executing the query: '" + dbquery_tDBInput_2 + "'.");

				globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);

				java.sql.ResultSet rs_tDBInput_2 = null;

				try {
					rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
					java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
					int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

					String tmpContent_tDBInput_2 = null;

					log.debug("tDBInput_2 - Retrieving records from the database.");

					while (rs_tDBInput_2.next()) {
						nb_line_tDBInput_2++;

						if (colQtyInRs_tDBInput_2 < 1) {
							row2.chargepointkey = 0;
						} else {

							row2.chargepointkey = rs_tDBInput_2.getInt(1);
							if (rs_tDBInput_2.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row2.chargepointid = null;
						} else {

							row2.chargepointid = rs_tDBInput_2.getInt(2);
							if (rs_tDBInput_2.wasNull()) {
								row2.chargepointid = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row2.chargingcapacity = null;
						} else {

							row2.chargingcapacity = rs_tDBInput_2.getBigDecimal(3);
							if (rs_tDBInput_2.wasNull()) {
								row2.chargingcapacity = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 4) {
							row2.chargertype = null;
						} else {

							row2.chargertype = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
						}
						if (colQtyInRs_tDBInput_2 < 5) {
							row2.installationcost = null;
						} else {

							row2.installationcost = rs_tDBInput_2.getBigDecimal(5);
							if (rs_tDBInput_2.wasNull()) {
								row2.installationcost = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 6) {
							row2.chargingpointstatuskey = null;
						} else {

							row2.chargingpointstatuskey = rs_tDBInput_2.getInt(6);
							if (rs_tDBInput_2.wasNull()) {
								row2.chargingpointstatuskey = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 7) {
							row2.stationkey = null;
						} else {

							row2.stationkey = rs_tDBInput_2.getInt(7);
							if (rs_tDBInput_2.wasNull()) {
								row2.stationkey = null;
							}
						}

						log.debug("tDBInput_2 - Retrieving the record " + nb_line_tDBInput_2 + ".");

						/**
						 * [tDBInput_2 begin ] stop
						 */

						/**
						 * [tDBInput_2 main ] start
						 */

						s(currentComponent = "tDBInput_2");

						cLabel = "\"chargingpoint\"";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_2");

						cLabel = "\"chargingpoint\"";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row2 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row2");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row2", "tDBInput_2", "\"chargingpoint\"", "tPostgresqlInput", "tAdvancedHash_row2",
								"tAdvancedHash_row2", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
						}

						row2Struct row2_HashRow = new row2Struct();

						row2_HashRow.chargepointkey = row2.chargepointkey;

						row2_HashRow.chargepointid = row2.chargepointid;

						row2_HashRow.chargingcapacity = row2.chargingcapacity;

						row2_HashRow.chargertype = row2.chargertype;

						row2_HashRow.installationcost = row2.installationcost;

						row2_HashRow.chargingpointstatuskey = row2.chargingpointstatuskey;

						row2_HashRow.stationkey = row2.stationkey;

						tHash_Lookup_row2.put(row2_HashRow);

						tos_count_tAdvancedHash_row2++;

						/**
						 * [tAdvancedHash_row2 main ] stop
						 */

						/**
						 * [tAdvancedHash_row2 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row2");

						/**
						 * [tAdvancedHash_row2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row2 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row2");

						/**
						 * [tAdvancedHash_row2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_2");

						cLabel = "\"chargingpoint\"";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						s(currentComponent = "tDBInput_2");

						cLabel = "\"chargingpoint\"";

					}
				} finally {
					if (rs_tDBInput_2 != null) {
						rs_tDBInput_2.close();
					}
					if (stmt_tDBInput_2 != null) {
						stmt_tDBInput_2.close();
					}
				}
				globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);
				log.debug("tDBInput_2 - Retrieved records count: " + nb_line_tDBInput_2 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_2 - " + ("Done."));

				ok_Hash.put("tDBInput_2", true);
				end_Hash.put("tDBInput_2", System.currentTimeMillis());

				/**
				 * [tDBInput_2 end ] stop
				 */

				/**
				 * [tAdvancedHash_row2 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row2");

				tHash_Lookup_row2.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tDBInput_2", "\"chargingpoint\"", "tPostgresqlInput", "tAdvancedHash_row2",
						"tAdvancedHash_row2", "tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row2", true);
				end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				s(currentComponent = "tDBInput_2");

				cLabel = "\"chargingpoint\"";

				/**
				 * [tDBInput_2 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row2 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row2");

				/**
				 * [tAdvancedHash_row2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class row3Struct implements routines.system.IPersistableComparableLookupRow<row3Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return "nextval('pricingmodel_pricingmodelkey_seq'::regclass)";

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public Integer pricingmodelid;

		public Integer getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return true;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public BigDecimal rateperkwh;

		public BigDecimal getRateperkwh() {
			return this.rateperkwh;
		}

		public Boolean rateperkwhIsNullable() {
			return true;
		}

		public Boolean rateperkwhIsKey() {
			return false;
		}

		public Integer rateperkwhLength() {
			return 10;
		}

		public Integer rateperkwhPrecision() {
			return 2;
		}

		public String rateperkwhDefault() {

			return null;

		}

		public String rateperkwhComment() {

			return "";

		}

		public String rateperkwhPattern() {

			return "";

		}

		public String rateperkwhOriginalDbColumnName() {

			return "rateperkwh";

		}

		public java.util.Date peakstarttime;

		public java.util.Date getPeakstarttime() {
			return this.peakstarttime;
		}

		public Boolean peakstarttimeIsNullable() {
			return true;
		}

		public Boolean peakstarttimeIsKey() {
			return false;
		}

		public Integer peakstarttimeLength() {
			return 15;
		}

		public Integer peakstarttimePrecision() {
			return 6;
		}

		public String peakstarttimeDefault() {

			return null;

		}

		public String peakstarttimeComment() {

			return "";

		}

		public String peakstarttimePattern() {

			return "HH:mm:ss";

		}

		public String peakstarttimeOriginalDbColumnName() {

			return "peakstarttime";

		}

		public java.util.Date peakendtime;

		public java.util.Date getPeakendtime() {
			return this.peakendtime;
		}

		public Boolean peakendtimeIsNullable() {
			return true;
		}

		public Boolean peakendtimeIsKey() {
			return false;
		}

		public Integer peakendtimeLength() {
			return 15;
		}

		public Integer peakendtimePrecision() {
			return 6;
		}

		public String peakendtimeDefault() {

			return null;

		}

		public String peakendtimeComment() {

			return "";

		}

		public String peakendtimePattern() {

			return "HH:mm:ss";

		}

		public String peakendtimeOriginalDbColumnName() {

			return "peakendtime";

		}

		public BigDecimal peakmultiplier;

		public BigDecimal getPeakmultiplier() {
			return this.peakmultiplier;
		}

		public Boolean peakmultiplierIsNullable() {
			return true;
		}

		public Boolean peakmultiplierIsKey() {
			return false;
		}

		public Integer peakmultiplierLength() {
			return 3;
		}

		public Integer peakmultiplierPrecision() {
			return 2;
		}

		public String peakmultiplierDefault() {

			return null;

		}

		public String peakmultiplierComment() {

			return "";

		}

		public String peakmultiplierPattern() {

			return "";

		}

		public String peakmultiplierOriginalDbColumnName() {

			return "peakmultiplier";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.pricingmodelid == null) ? 0 : this.pricingmodelid.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row3Struct other = (row3Struct) obj;

			if (this.pricingmodelid == null) {
				if (other.pricingmodelid != null)
					return false;

			} else if (!this.pricingmodelid.equals(other.pricingmodelid))

				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.pricingmodelkey = this.pricingmodelkey;
			other.pricingmodelid = this.pricingmodelid;
			other.rateperkwh = this.rateperkwh;
			other.peakstarttime = this.peakstarttime;
			other.peakendtime = this.peakendtime;
			other.peakmultiplier = this.peakmultiplier;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.pricingmodelid = this.pricingmodelid;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(DataInputStream dis, ObjectInputStream ois) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.pricingmodelid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.pricingmodelid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.pricingmodelid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.pricingmodelid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.pricingmodelkey = dis.readInt();

				this.rateperkwh = (BigDecimal) ois.readObject();

				this.peakstarttime = readDate(dis, ois);

				this.peakendtime = readDate(dis, ois);

				this.peakmultiplier = (BigDecimal) ois.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.pricingmodelkey = objectIn.readInt();

				this.rateperkwh = (BigDecimal) objectIn.readObject();

				this.peakstarttime = readDate(dis, objectIn);

				this.peakendtime = readDate(dis, objectIn);

				this.peakmultiplier = (BigDecimal) objectIn.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.pricingmodelkey);

				oos.writeObject(this.rateperkwh);

				writeDate(this.peakstarttime, dos, oos);

				writeDate(this.peakendtime, dos, oos);

				oos.writeObject(this.peakmultiplier);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.pricingmodelkey);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.rateperkwh);

				writeDate(this.peakstarttime, dos, objectOut);

				writeDate(this.peakendtime, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.peakmultiplier);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",rateperkwh=" + String.valueOf(rateperkwh));
			sb.append(",peakstarttime=" + String.valueOf(peakstarttime));
			sb.append(",peakendtime=" + String.valueOf(peakendtime));
			sb.append(",peakmultiplier=" + String.valueOf(peakmultiplier));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(pricingmodelkey);

			sb.append("|");

			if (pricingmodelid == null) {
				sb.append("<null>");
			} else {
				sb.append(pricingmodelid);
			}

			sb.append("|");

			if (rateperkwh == null) {
				sb.append("<null>");
			} else {
				sb.append(rateperkwh);
			}

			sb.append("|");

			if (peakstarttime == null) {
				sb.append("<null>");
			} else {
				sb.append(peakstarttime);
			}

			sb.append("|");

			if (peakendtime == null) {
				sb.append("<null>");
			} else {
				sb.append(peakendtime);
			}

			sb.append("|");

			if (peakmultiplier == null) {
				sb.append("<null>");
			} else {
				sb.append(peakmultiplier);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.pricingmodelid, other.pricingmodelid);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_3", "81XcMY_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();

				/**
				 * [tAdvancedHash_row3 begin ] start
				 */

				sh("tAdvancedHash_row3");

				s(currentComponent = "tAdvancedHash_row3");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row3");

				int tos_count_tAdvancedHash_row3 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row3", "tAdvancedHash_row3", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row3
				// source node:tDBInput_3 - inputs:(after_tDBInput_1) outputs:(row3,row3) |
				// target node:tAdvancedHash_row3 - inputs:(row3) outputs:()
				// linked node: tMap_2 - inputs:(CPFound,row3) outputs:(PMFound,PMNotFound)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row3Struct>getLookup(matchingModeEnum_row3);

				globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);

				/**
				 * [tAdvancedHash_row3 begin ] stop
				 */

				/**
				 * [tDBInput_3 begin ] start
				 */

				sh("tDBInput_3");

				s(currentComponent = "tDBInput_3");

				cLabel = "\"pricingmodel\"";

				int tos_count_tDBInput_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_3 = new StringBuilder();
							log4jParamters_tDBInput_3.append("Parameters:");
							log4jParamters_tDBInput_3.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("QUERY" + " = "
									+ "\"SELECT    \\\"pricingmodel\\\".\\\"pricingmodelkey\\\",    \\\"pricingmodel\\\".\\\"pricingmodelid\\\",    \\\"pricingmodel\\\".\\\"rateperkwh\\\",    \\\"pricingmodel\\\".\\\"peakstarttime\\\",    \\\"pricingmodel\\\".\\\"peakendtime\\\",    \\\"pricingmodel\\\".\\\"peakmultiplier\\\"  FROM \\\"pricingmodel\\\"\"");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("pricingmodelkey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("pricingmodelid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("rateperkwh") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("peakstarttime") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("peakendtime")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("peakmultiplier") + "}]");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_3 - " + (log4jParamters_tDBInput_3));
						}
					}
					new BytesLimit65535_tDBInput_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_3", "\"pricingmodel\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_3 = 0;
				java.sql.Connection conn_tDBInput_3 = null;
				conn_tDBInput_3 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_3 != null) {
					if (conn_tDBInput_3.getMetaData() != null) {

						log.debug("tDBInput_3 - Uses an existing connection with username '"
								+ conn_tDBInput_3.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_3.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

				String dbquery_tDBInput_3 = "SELECT \n  \"pricingmodel\".\"pricingmodelkey\", \n  \"pricingmodel\".\"pricingmodelid\", \n  \"pricingmodel\".\"rateperkw"
						+ "h\", \n  \"pricingmodel\".\"peakstarttime\", \n  \"pricingmodel\".\"peakendtime\", \n  \"pricingmodel\".\"peakmultiplier\"\n"
						+ " FROM \"pricingmodel\"";

				log.debug("tDBInput_3 - Executing the query: '" + dbquery_tDBInput_3 + "'.");

				globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);

				java.sql.ResultSet rs_tDBInput_3 = null;

				try {
					rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
					java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
					int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

					String tmpContent_tDBInput_3 = null;

					log.debug("tDBInput_3 - Retrieving records from the database.");

					while (rs_tDBInput_3.next()) {
						nb_line_tDBInput_3++;

						if (colQtyInRs_tDBInput_3 < 1) {
							row3.pricingmodelkey = 0;
						} else {

							row3.pricingmodelkey = rs_tDBInput_3.getInt(1);
							if (rs_tDBInput_3.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_3 < 2) {
							row3.pricingmodelid = null;
						} else {

							row3.pricingmodelid = rs_tDBInput_3.getInt(2);
							if (rs_tDBInput_3.wasNull()) {
								row3.pricingmodelid = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 3) {
							row3.rateperkwh = null;
						} else {

							row3.rateperkwh = rs_tDBInput_3.getBigDecimal(3);
							if (rs_tDBInput_3.wasNull()) {
								row3.rateperkwh = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 4) {
							row3.peakstarttime = null;
						} else {

							row3.peakstarttime = routines.system.JDBCUtil.getDate(rs_tDBInput_3, 4);
						}
						if (colQtyInRs_tDBInput_3 < 5) {
							row3.peakendtime = null;
						} else {

							row3.peakendtime = routines.system.JDBCUtil.getDate(rs_tDBInput_3, 5);
						}
						if (colQtyInRs_tDBInput_3 < 6) {
							row3.peakmultiplier = null;
						} else {

							row3.peakmultiplier = rs_tDBInput_3.getBigDecimal(6);
							if (rs_tDBInput_3.wasNull()) {
								row3.peakmultiplier = null;
							}
						}

						log.debug("tDBInput_3 - Retrieving the record " + nb_line_tDBInput_3 + ".");

						/**
						 * [tDBInput_3 begin ] stop
						 */

						/**
						 * [tDBInput_3 main ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"pricingmodel\"";

						tos_count_tDBInput_3++;

						/**
						 * [tDBInput_3 main ] stop
						 */

						/**
						 * [tDBInput_3 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"pricingmodel\"";

						/**
						 * [tDBInput_3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row3");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row3", "tDBInput_3", "\"pricingmodel\"", "tPostgresqlInput", "tAdvancedHash_row3",
								"tAdvancedHash_row3", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
						}

						row3Struct row3_HashRow = new row3Struct();

						row3_HashRow.pricingmodelkey = row3.pricingmodelkey;

						row3_HashRow.pricingmodelid = row3.pricingmodelid;

						row3_HashRow.rateperkwh = row3.rateperkwh;

						row3_HashRow.peakstarttime = row3.peakstarttime;

						row3_HashRow.peakendtime = row3.peakendtime;

						row3_HashRow.peakmultiplier = row3.peakmultiplier;

						tHash_Lookup_row3.put(row3_HashRow);

						tos_count_tAdvancedHash_row3++;

						/**
						 * [tAdvancedHash_row3 main ] stop
						 */

						/**
						 * [tAdvancedHash_row3 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row3");

						/**
						 * [tAdvancedHash_row3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row3");

						/**
						 * [tAdvancedHash_row3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"pricingmodel\"";

						/**
						 * [tDBInput_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 end ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"pricingmodel\"";

					}
				} finally {
					if (rs_tDBInput_3 != null) {
						rs_tDBInput_3.close();
					}
					if (stmt_tDBInput_3 != null) {
						stmt_tDBInput_3.close();
					}
				}
				globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);
				log.debug("tDBInput_3 - Retrieved records count: " + nb_line_tDBInput_3 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_3 - " + ("Done."));

				ok_Hash.put("tDBInput_3", true);
				end_Hash.put("tDBInput_3", System.currentTimeMillis());

				/**
				 * [tDBInput_3 end ] stop
				 */

				/**
				 * [tAdvancedHash_row3 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row3");

				tHash_Lookup_row3.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row3", 2, 0,
						"tDBInput_3", "\"pricingmodel\"", "tPostgresqlInput", "tAdvancedHash_row3",
						"tAdvancedHash_row3", "tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row3", true);
				end_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_3 finally ] start
				 */

				s(currentComponent = "tDBInput_3");

				cLabel = "\"pricingmodel\"";

				/**
				 * [tDBInput_3 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row3 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row3");

				/**
				 * [tAdvancedHash_row3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
	}

	public static class row4Struct implements routines.system.IPersistableComparableLookupRow<row4Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int vehiclekey;

		public int getVehiclekey() {
			return this.vehiclekey;
		}

		public Boolean vehiclekeyIsNullable() {
			return false;
		}

		public Boolean vehiclekeyIsKey() {
			return true;
		}

		public Integer vehiclekeyLength() {
			return 10;
		}

		public Integer vehiclekeyPrecision() {
			return 0;
		}

		public String vehiclekeyDefault() {

			return "nextval('vehicle_vehiclekey_seq'::regclass)";

		}

		public String vehiclekeyComment() {

			return "";

		}

		public String vehiclekeyPattern() {

			return "";

		}

		public String vehiclekeyOriginalDbColumnName() {

			return "vehiclekey";

		}

		public Integer vehicleid;

		public Integer getVehicleid() {
			return this.vehicleid;
		}

		public Boolean vehicleidIsNullable() {
			return true;
		}

		public Boolean vehicleidIsKey() {
			return false;
		}

		public Integer vehicleidLength() {
			return 10;
		}

		public Integer vehicleidPrecision() {
			return 0;
		}

		public String vehicleidDefault() {

			return null;

		}

		public String vehicleidComment() {

			return "";

		}

		public String vehicleidPattern() {

			return "";

		}

		public String vehicleidOriginalDbColumnName() {

			return "vehicleid";

		}

		public String make;

		public String getMake() {
			return this.make;
		}

		public Boolean makeIsNullable() {
			return true;
		}

		public Boolean makeIsKey() {
			return false;
		}

		public Integer makeLength() {
			return 50;
		}

		public Integer makePrecision() {
			return 0;
		}

		public String makeDefault() {

			return null;

		}

		public String makeComment() {

			return "";

		}

		public String makePattern() {

			return "";

		}

		public String makeOriginalDbColumnName() {

			return "make";

		}

		public String chargertype;

		public String getChargertype() {
			return this.chargertype;
		}

		public Boolean chargertypeIsNullable() {
			return true;
		}

		public Boolean chargertypeIsKey() {
			return false;
		}

		public Integer chargertypeLength() {
			return 20;
		}

		public Integer chargertypePrecision() {
			return 0;
		}

		public String chargertypeDefault() {

			return null;

		}

		public String chargertypeComment() {

			return "";

		}

		public String chargertypePattern() {

			return "";

		}

		public String chargertypeOriginalDbColumnName() {

			return "chargertype";

		}

		public BigDecimal batterycapacity;

		public BigDecimal getBatterycapacity() {
			return this.batterycapacity;
		}

		public Boolean batterycapacityIsNullable() {
			return true;
		}

		public Boolean batterycapacityIsKey() {
			return false;
		}

		public Integer batterycapacityLength() {
			return 10;
		}

		public Integer batterycapacityPrecision() {
			return 2;
		}

		public String batterycapacityDefault() {

			return null;

		}

		public String batterycapacityComment() {

			return "";

		}

		public String batterycapacityPattern() {

			return "";

		}

		public String batterycapacityOriginalDbColumnName() {

			return "batterycapacity";

		}

		public BigDecimal range;

		public BigDecimal getRange() {
			return this.range;
		}

		public Boolean rangeIsNullable() {
			return true;
		}

		public Boolean rangeIsKey() {
			return false;
		}

		public Integer rangeLength() {
			return 10;
		}

		public Integer rangePrecision() {
			return 2;
		}

		public String rangeDefault() {

			return null;

		}

		public String rangeComment() {

			return "";

		}

		public String rangePattern() {

			return "";

		}

		public String rangeOriginalDbColumnName() {

			return "range";

		}

		public Integer userkey;

		public Integer getUserkey() {
			return this.userkey;
		}

		public Boolean userkeyIsNullable() {
			return true;
		}

		public Boolean userkeyIsKey() {
			return false;
		}

		public Integer userkeyLength() {
			return 10;
		}

		public Integer userkeyPrecision() {
			return 0;
		}

		public String userkeyDefault() {

			return null;

		}

		public String userkeyComment() {

			return "";

		}

		public String userkeyPattern() {

			return "";

		}

		public String userkeyOriginalDbColumnName() {

			return "userkey";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.vehicleid == null) ? 0 : this.vehicleid.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row4Struct other = (row4Struct) obj;

			if (this.vehicleid == null) {
				if (other.vehicleid != null)
					return false;

			} else if (!this.vehicleid.equals(other.vehicleid))

				return false;

			return true;
		}

		public void copyDataTo(row4Struct other) {

			other.vehiclekey = this.vehiclekey;
			other.vehicleid = this.vehicleid;
			other.make = this.make;
			other.chargertype = this.chargertype;
			other.batterycapacity = this.batterycapacity;
			other.range = this.range;
			other.userkey = this.userkey;

		}

		public void copyKeysDataTo(row4Struct other) {

			other.vehicleid = this.vehicleid;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.vehicleid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.vehicleid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.vehicleid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.vehicleid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.vehiclekey = dis.readInt();

				this.make = readString(dis, ois);

				this.chargertype = readString(dis, ois);

				this.batterycapacity = (BigDecimal) ois.readObject();

				this.range = (BigDecimal) ois.readObject();

				this.userkey = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.vehiclekey = objectIn.readInt();

				this.make = readString(dis, objectIn);

				this.chargertype = readString(dis, objectIn);

				this.batterycapacity = (BigDecimal) objectIn.readObject();

				this.range = (BigDecimal) objectIn.readObject();

				this.userkey = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.vehiclekey);

				writeString(this.make, dos, oos);

				writeString(this.chargertype, dos, oos);

				oos.writeObject(this.batterycapacity);

				oos.writeObject(this.range);

				writeInteger(this.userkey, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.vehiclekey);

				writeString(this.make, dos, objectOut);

				writeString(this.chargertype, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.batterycapacity);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.range);

				writeInteger(this.userkey, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("vehiclekey=" + String.valueOf(vehiclekey));
			sb.append(",vehicleid=" + String.valueOf(vehicleid));
			sb.append(",make=" + make);
			sb.append(",chargertype=" + chargertype);
			sb.append(",batterycapacity=" + String.valueOf(batterycapacity));
			sb.append(",range=" + String.valueOf(range));
			sb.append(",userkey=" + String.valueOf(userkey));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(vehiclekey);

			sb.append("|");

			if (vehicleid == null) {
				sb.append("<null>");
			} else {
				sb.append(vehicleid);
			}

			sb.append("|");

			if (make == null) {
				sb.append("<null>");
			} else {
				sb.append(make);
			}

			sb.append("|");

			if (chargertype == null) {
				sb.append("<null>");
			} else {
				sb.append(chargertype);
			}

			sb.append("|");

			if (batterycapacity == null) {
				sb.append("<null>");
			} else {
				sb.append(batterycapacity);
			}

			sb.append("|");

			if (range == null) {
				sb.append("<null>");
			} else {
				sb.append(range);
			}

			sb.append("|");

			if (userkey == null) {
				sb.append("<null>");
			} else {
				sb.append(userkey);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.vehicleid, other.vehicleid);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_4", "soHpj0_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row4Struct row4 = new row4Struct();

				/**
				 * [tAdvancedHash_row4 begin ] start
				 */

				sh("tAdvancedHash_row4");

				s(currentComponent = "tAdvancedHash_row4");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row4");

				int tos_count_tAdvancedHash_row4 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row4", "tAdvancedHash_row4", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row4
				// source node:tDBInput_4 - inputs:(after_tDBInput_1) outputs:(row4,row4) |
				// target node:tAdvancedHash_row4 - inputs:(row4) outputs:()
				// linked node: tMap_3 - inputs:(PMFound,row4)
				// outputs:(VehicleFound,VehicleNotFound)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row4 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row4Struct>getLookup(matchingModeEnum_row4);

				globalMap.put("tHash_Lookup_row4", tHash_Lookup_row4);

				/**
				 * [tAdvancedHash_row4 begin ] stop
				 */

				/**
				 * [tDBInput_4 begin ] start
				 */

				sh("tDBInput_4");

				s(currentComponent = "tDBInput_4");

				cLabel = "\"vehicle\"";

				int tos_count_tDBInput_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_4 = new StringBuilder();
							log4jParamters_tDBInput_4.append("Parameters:");
							log4jParamters_tDBInput_4.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("QUERY" + " = "
									+ "\"SELECT    \\\"vehicle\\\".\\\"vehiclekey\\\",    \\\"vehicle\\\".\\\"vehicleid\\\",    \\\"vehicle\\\".\\\"make\\\",    \\\"vehicle\\\".\\\"chargertype\\\",    \\\"vehicle\\\".\\\"batterycapacity\\\",    \\\"vehicle\\\".\\\"range\\\",    \\\"vehicle\\\".\\\"userkey\\\"  FROM \\\"vehicle\\\"\"");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("vehiclekey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("vehicleid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("make")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("chargertype") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("batterycapacity") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("range") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("userkey") + "}]");
							log4jParamters_tDBInput_4.append(" | ");
							log4jParamters_tDBInput_4.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_4 - " + (log4jParamters_tDBInput_4));
						}
					}
					new BytesLimit65535_tDBInput_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_4", "\"vehicle\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_4 = 0;
				java.sql.Connection conn_tDBInput_4 = null;
				conn_tDBInput_4 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_4 != null) {
					if (conn_tDBInput_4.getMetaData() != null) {

						log.debug("tDBInput_4 - Uses an existing connection with username '"
								+ conn_tDBInput_4.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_4.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_4 = conn_tDBInput_4.createStatement();

				String dbquery_tDBInput_4 = "SELECT \n  \"vehicle\".\"vehiclekey\", \n  \"vehicle\".\"vehicleid\", \n  \"vehicle\".\"make\", \n  \"vehicle\".\"chargerty"
						+ "pe\", \n  \"vehicle\".\"batterycapacity\", \n  \"vehicle\".\"range\", \n  \"vehicle\".\"userkey\"\n FROM \"vehicle\"";

				log.debug("tDBInput_4 - Executing the query: '" + dbquery_tDBInput_4 + "'.");

				globalMap.put("tDBInput_4_QUERY", dbquery_tDBInput_4);

				java.sql.ResultSet rs_tDBInput_4 = null;

				try {
					rs_tDBInput_4 = stmt_tDBInput_4.executeQuery(dbquery_tDBInput_4);
					java.sql.ResultSetMetaData rsmd_tDBInput_4 = rs_tDBInput_4.getMetaData();
					int colQtyInRs_tDBInput_4 = rsmd_tDBInput_4.getColumnCount();

					String tmpContent_tDBInput_4 = null;

					log.debug("tDBInput_4 - Retrieving records from the database.");

					while (rs_tDBInput_4.next()) {
						nb_line_tDBInput_4++;

						if (colQtyInRs_tDBInput_4 < 1) {
							row4.vehiclekey = 0;
						} else {

							row4.vehiclekey = rs_tDBInput_4.getInt(1);
							if (rs_tDBInput_4.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_4 < 2) {
							row4.vehicleid = null;
						} else {

							row4.vehicleid = rs_tDBInput_4.getInt(2);
							if (rs_tDBInput_4.wasNull()) {
								row4.vehicleid = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 3) {
							row4.make = null;
						} else {

							row4.make = routines.system.JDBCUtil.getString(rs_tDBInput_4, 3, false);
						}
						if (colQtyInRs_tDBInput_4 < 4) {
							row4.chargertype = null;
						} else {

							row4.chargertype = routines.system.JDBCUtil.getString(rs_tDBInput_4, 4, false);
						}
						if (colQtyInRs_tDBInput_4 < 5) {
							row4.batterycapacity = null;
						} else {

							row4.batterycapacity = rs_tDBInput_4.getBigDecimal(5);
							if (rs_tDBInput_4.wasNull()) {
								row4.batterycapacity = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 6) {
							row4.range = null;
						} else {

							row4.range = rs_tDBInput_4.getBigDecimal(6);
							if (rs_tDBInput_4.wasNull()) {
								row4.range = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 7) {
							row4.userkey = null;
						} else {

							row4.userkey = rs_tDBInput_4.getInt(7);
							if (rs_tDBInput_4.wasNull()) {
								row4.userkey = null;
							}
						}

						log.debug("tDBInput_4 - Retrieving the record " + nb_line_tDBInput_4 + ".");

						/**
						 * [tDBInput_4 begin ] stop
						 */

						/**
						 * [tDBInput_4 main ] start
						 */

						s(currentComponent = "tDBInput_4");

						cLabel = "\"vehicle\"";

						tos_count_tDBInput_4++;

						/**
						 * [tDBInput_4 main ] stop
						 */

						/**
						 * [tDBInput_4 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_4");

						cLabel = "\"vehicle\"";

						/**
						 * [tDBInput_4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row4");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row4", "tDBInput_4", "\"vehicle\"", "tPostgresqlInput", "tAdvancedHash_row4",
								"tAdvancedHash_row4", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row4 - " + (row4 == null ? "" : row4.toLogString()));
						}

						row4Struct row4_HashRow = new row4Struct();

						row4_HashRow.vehiclekey = row4.vehiclekey;

						row4_HashRow.vehicleid = row4.vehicleid;

						row4_HashRow.make = row4.make;

						row4_HashRow.chargertype = row4.chargertype;

						row4_HashRow.batterycapacity = row4.batterycapacity;

						row4_HashRow.range = row4.range;

						row4_HashRow.userkey = row4.userkey;

						tHash_Lookup_row4.put(row4_HashRow);

						tos_count_tAdvancedHash_row4++;

						/**
						 * [tAdvancedHash_row4 main ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row4");

						/**
						 * [tAdvancedHash_row4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row4");

						/**
						 * [tAdvancedHash_row4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_4");

						cLabel = "\"vehicle\"";

						/**
						 * [tDBInput_4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 end ] start
						 */

						s(currentComponent = "tDBInput_4");

						cLabel = "\"vehicle\"";

					}
				} finally {
					if (rs_tDBInput_4 != null) {
						rs_tDBInput_4.close();
					}
					if (stmt_tDBInput_4 != null) {
						stmt_tDBInput_4.close();
					}
				}
				globalMap.put("tDBInput_4_NB_LINE", nb_line_tDBInput_4);
				log.debug("tDBInput_4 - Retrieved records count: " + nb_line_tDBInput_4 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_4 - " + ("Done."));

				ok_Hash.put("tDBInput_4", true);
				end_Hash.put("tDBInput_4", System.currentTimeMillis());

				/**
				 * [tDBInput_4 end ] stop
				 */

				/**
				 * [tAdvancedHash_row4 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row4");

				tHash_Lookup_row4.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row4", 2, 0,
						"tDBInput_4", "\"vehicle\"", "tPostgresqlInput", "tAdvancedHash_row4", "tAdvancedHash_row4",
						"tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row4", true);
				end_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row4 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_4 finally ] start
				 */

				s(currentComponent = "tDBInput_4");

				cLabel = "\"vehicle\"";

				/**
				 * [tDBInput_4 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row4 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row4");

				/**
				 * [tAdvancedHash_row4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 1);
	}

	public static class row5Struct implements routines.system.IPersistableComparableLookupRow<row5Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int timekey;

		public int getTimekey() {
			return this.timekey;
		}

		public Boolean timekeyIsNullable() {
			return false;
		}

		public Boolean timekeyIsKey() {
			return true;
		}

		public Integer timekeyLength() {
			return 10;
		}

		public Integer timekeyPrecision() {
			return 0;
		}

		public String timekeyDefault() {

			return "nextval('timedimension_timekey_seq'::regclass)";

		}

		public String timekeyComment() {

			return "";

		}

		public String timekeyPattern() {

			return "";

		}

		public String timekeyOriginalDbColumnName() {

			return "timekey";

		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		public Boolean yearIsNullable() {
			return true;
		}

		public Boolean yearIsKey() {
			return false;
		}

		public Integer yearLength() {
			return 10;
		}

		public Integer yearPrecision() {
			return 0;
		}

		public String yearDefault() {

			return null;

		}

		public String yearComment() {

			return "";

		}

		public String yearPattern() {

			return "";

		}

		public String yearOriginalDbColumnName() {

			return "year";

		}

		public Integer month;

		public Integer getMonth() {
			return this.month;
		}

		public Boolean monthIsNullable() {
			return true;
		}

		public Boolean monthIsKey() {
			return false;
		}

		public Integer monthLength() {
			return 10;
		}

		public Integer monthPrecision() {
			return 0;
		}

		public String monthDefault() {

			return null;

		}

		public String monthComment() {

			return "";

		}

		public String monthPattern() {

			return "";

		}

		public String monthOriginalDbColumnName() {

			return "month";

		}

		public java.util.Date date;

		public java.util.Date getDate() {
			return this.date;
		}

		public Boolean dateIsNullable() {
			return true;
		}

		public Boolean dateIsKey() {
			return false;
		}

		public Integer dateLength() {
			return 13;
		}

		public Integer datePrecision() {
			return 0;
		}

		public String dateDefault() {

			return null;

		}

		public String dateComment() {

			return "";

		}

		public String datePattern() {

			return "yyyy-MM-dd";

		}

		public String dateOriginalDbColumnName() {

			return "date";

		}

		public Integer hour;

		public Integer getHour() {
			return this.hour;
		}

		public Boolean hourIsNullable() {
			return true;
		}

		public Boolean hourIsKey() {
			return false;
		}

		public Integer hourLength() {
			return 10;
		}

		public Integer hourPrecision() {
			return 0;
		}

		public String hourDefault() {

			return null;

		}

		public String hourComment() {

			return "";

		}

		public String hourPattern() {

			return "";

		}

		public String hourOriginalDbColumnName() {

			return "hour";

		}

		public String daypart;

		public String getDaypart() {
			return this.daypart;
		}

		public Boolean daypartIsNullable() {
			return true;
		}

		public Boolean daypartIsKey() {
			return false;
		}

		public Integer daypartLength() {
			return 32;
		}

		public Integer daypartPrecision() {
			return 0;
		}

		public String daypartDefault() {

			return null;

		}

		public String daypartComment() {

			return "";

		}

		public String daypartPattern() {

			return "";

		}

		public String daypartOriginalDbColumnName() {

			return "daypart";

		}

		public String weekdayorweekend;

		public String getWeekdayorweekend() {
			return this.weekdayorweekend;
		}

		public Boolean weekdayorweekendIsNullable() {
			return true;
		}

		public Boolean weekdayorweekendIsKey() {
			return false;
		}

		public Integer weekdayorweekendLength() {
			return 10;
		}

		public Integer weekdayorweekendPrecision() {
			return 0;
		}

		public String weekdayorweekendDefault() {

			return null;

		}

		public String weekdayorweekendComment() {

			return "";

		}

		public String weekdayorweekendPattern() {

			return "";

		}

		public String weekdayorweekendOriginalDbColumnName() {

			return "weekdayorweekend";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());

				result = prime * result + ((this.hour == null) ? 0 : this.hour.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row5Struct other = (row5Struct) obj;

			if (this.date == null) {
				if (other.date != null)
					return false;

			} else if (!this.date.equals(other.date))

				return false;

			if (this.hour == null) {
				if (other.hour != null)
					return false;

			} else if (!this.hour.equals(other.hour))

				return false;

			return true;
		}

		public void copyDataTo(row5Struct other) {

			other.timekey = this.timekey;
			other.year = this.year;
			other.month = this.month;
			other.date = this.date;
			other.hour = this.hour;
			other.daypart = this.daypart;
			other.weekdayorweekend = this.weekdayorweekend;

		}

		public void copyKeysDataTo(row5Struct other) {

			other.date = this.date;
			other.hour = this.hour;

		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.hour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.hour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.timekey = dis.readInt();

				this.year = readInteger(dis, ois);

				this.month = readInteger(dis, ois);

				this.daypart = readString(dis, ois);

				this.weekdayorweekend = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.timekey = objectIn.readInt();

				this.year = readInteger(dis, objectIn);

				this.month = readInteger(dis, objectIn);

				this.daypart = readString(dis, objectIn);

				this.weekdayorweekend = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.timekey);

				writeInteger(this.year, dos, oos);

				writeInteger(this.month, dos, oos);

				writeString(this.daypart, dos, oos);

				writeString(this.weekdayorweekend, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.timekey);

				writeInteger(this.year, dos, objectOut);

				writeInteger(this.month, dos, objectOut);

				writeString(this.daypart, dos, objectOut);

				writeString(this.weekdayorweekend, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("timekey=" + String.valueOf(timekey));
			sb.append(",year=" + String.valueOf(year));
			sb.append(",month=" + String.valueOf(month));
			sb.append(",date=" + String.valueOf(date));
			sb.append(",hour=" + String.valueOf(hour));
			sb.append(",daypart=" + daypart);
			sb.append(",weekdayorweekend=" + weekdayorweekend);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(timekey);

			sb.append("|");

			if (year == null) {
				sb.append("<null>");
			} else {
				sb.append(year);
			}

			sb.append("|");

			if (month == null) {
				sb.append("<null>");
			} else {
				sb.append(month);
			}

			sb.append("|");

			if (date == null) {
				sb.append("<null>");
			} else {
				sb.append(date);
			}

			sb.append("|");

			if (hour == null) {
				sb.append("<null>");
			} else {
				sb.append(hour);
			}

			sb.append("|");

			if (daypart == null) {
				sb.append("<null>");
			} else {
				sb.append(daypart);
			}

			sb.append("|");

			if (weekdayorweekend == null) {
				sb.append("<null>");
			} else {
				sb.append(weekdayorweekend);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.date, other.date);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.hour, other.hour);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_5", "kd11Mv_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row5Struct row5 = new row5Struct();

				/**
				 * [tAdvancedHash_row5 begin ] start
				 */

				sh("tAdvancedHash_row5");

				s(currentComponent = "tAdvancedHash_row5");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row5");

				int tos_count_tAdvancedHash_row5 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row5", "tAdvancedHash_row5", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row5
				// source node:tDBInput_5 - inputs:(after_tDBInput_1) outputs:(row5,row5) |
				// target node:tAdvancedHash_row5 - inputs:(row5) outputs:()
				// linked node: tMap_4 - inputs:(VehicleFound,row5)
				// outputs:(StartTimeFound,StartTimeNotFound)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row5 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row5Struct>getLookup(matchingModeEnum_row5);

				globalMap.put("tHash_Lookup_row5", tHash_Lookup_row5);

				/**
				 * [tAdvancedHash_row5 begin ] stop
				 */

				/**
				 * [tDBInput_5 begin ] start
				 */

				sh("tDBInput_5");

				s(currentComponent = "tDBInput_5");

				cLabel = "\"timedimension\"";

				int tos_count_tDBInput_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_5 = new StringBuilder();
							log4jParamters_tDBInput_5.append("Parameters:");
							log4jParamters_tDBInput_5.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("QUERY" + " = "
									+ "\"SELECT    \\\"timedimension\\\".\\\"timekey\\\",    \\\"timedimension\\\".\\\"year\\\",    \\\"timedimension\\\".\\\"month\\\",    \\\"timedimension\\\".\\\"date\\\",    \\\"timedimension\\\".\\\"hour\\\",    \\\"timedimension\\\".\\\"daypart\\\",    \\\"timedimension\\\".\\\"weekdayorweekend\\\"  FROM \\\"timedimension\\\"\"");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("timekey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("year") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("month") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("date") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("hour") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("daypart") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("weekdayorweekend")
									+ "}]");
							log4jParamters_tDBInput_5.append(" | ");
							log4jParamters_tDBInput_5.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_5 - " + (log4jParamters_tDBInput_5));
						}
					}
					new BytesLimit65535_tDBInput_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_5", "\"timedimension\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_5 = 0;
				java.sql.Connection conn_tDBInput_5 = null;
				conn_tDBInput_5 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_5 != null) {
					if (conn_tDBInput_5.getMetaData() != null) {

						log.debug("tDBInput_5 - Uses an existing connection with username '"
								+ conn_tDBInput_5.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_5.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_5 = conn_tDBInput_5.createStatement();

				String dbquery_tDBInput_5 = "SELECT \n  \"timedimension\".\"timekey\", \n  \"timedimension\".\"year\", \n  \"timedimension\".\"month\", \n  \"timedimens"
						+ "ion\".\"date\", \n  \"timedimension\".\"hour\", \n  \"timedimension\".\"daypart\", \n  \"timedimension\".\"weekdayorweekend"
						+ "\"\n FROM \"timedimension\"";

				log.debug("tDBInput_5 - Executing the query: '" + dbquery_tDBInput_5 + "'.");

				globalMap.put("tDBInput_5_QUERY", dbquery_tDBInput_5);

				java.sql.ResultSet rs_tDBInput_5 = null;

				try {
					rs_tDBInput_5 = stmt_tDBInput_5.executeQuery(dbquery_tDBInput_5);
					java.sql.ResultSetMetaData rsmd_tDBInput_5 = rs_tDBInput_5.getMetaData();
					int colQtyInRs_tDBInput_5 = rsmd_tDBInput_5.getColumnCount();

					String tmpContent_tDBInput_5 = null;

					log.debug("tDBInput_5 - Retrieving records from the database.");

					while (rs_tDBInput_5.next()) {
						nb_line_tDBInput_5++;

						if (colQtyInRs_tDBInput_5 < 1) {
							row5.timekey = 0;
						} else {

							row5.timekey = rs_tDBInput_5.getInt(1);
							if (rs_tDBInput_5.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_5 < 2) {
							row5.year = null;
						} else {

							row5.year = rs_tDBInput_5.getInt(2);
							if (rs_tDBInput_5.wasNull()) {
								row5.year = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 3) {
							row5.month = null;
						} else {

							row5.month = rs_tDBInput_5.getInt(3);
							if (rs_tDBInput_5.wasNull()) {
								row5.month = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 4) {
							row5.date = null;
						} else {

							row5.date = routines.system.JDBCUtil.getDate(rs_tDBInput_5, 4);
						}
						if (colQtyInRs_tDBInput_5 < 5) {
							row5.hour = null;
						} else {

							row5.hour = rs_tDBInput_5.getInt(5);
							if (rs_tDBInput_5.wasNull()) {
								row5.hour = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 6) {
							row5.daypart = null;
						} else {

							row5.daypart = routines.system.JDBCUtil.getString(rs_tDBInput_5, 6, false);
						}
						if (colQtyInRs_tDBInput_5 < 7) {
							row5.weekdayorweekend = null;
						} else {

							row5.weekdayorweekend = routines.system.JDBCUtil.getString(rs_tDBInput_5, 7, false);
						}

						log.debug("tDBInput_5 - Retrieving the record " + nb_line_tDBInput_5 + ".");

						/**
						 * [tDBInput_5 begin ] stop
						 */

						/**
						 * [tDBInput_5 main ] start
						 */

						s(currentComponent = "tDBInput_5");

						cLabel = "\"timedimension\"";

						tos_count_tDBInput_5++;

						/**
						 * [tDBInput_5 main ] stop
						 */

						/**
						 * [tDBInput_5 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_5");

						cLabel = "\"timedimension\"";

						/**
						 * [tDBInput_5 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row5 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row5");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row5", "tDBInput_5", "\"timedimension\"", "tPostgresqlInput", "tAdvancedHash_row5",
								"tAdvancedHash_row5", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row5 - " + (row5 == null ? "" : row5.toLogString()));
						}

						row5Struct row5_HashRow = new row5Struct();

						row5_HashRow.timekey = row5.timekey;

						row5_HashRow.year = row5.year;

						row5_HashRow.month = row5.month;

						row5_HashRow.date = row5.date;

						row5_HashRow.hour = row5.hour;

						row5_HashRow.daypart = row5.daypart;

						row5_HashRow.weekdayorweekend = row5.weekdayorweekend;

						tHash_Lookup_row5.put(row5_HashRow);

						tos_count_tAdvancedHash_row5++;

						/**
						 * [tAdvancedHash_row5 main ] stop
						 */

						/**
						 * [tAdvancedHash_row5 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row5");

						/**
						 * [tAdvancedHash_row5 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row5 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row5");

						/**
						 * [tAdvancedHash_row5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_5");

						cLabel = "\"timedimension\"";

						/**
						 * [tDBInput_5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 end ] start
						 */

						s(currentComponent = "tDBInput_5");

						cLabel = "\"timedimension\"";

					}
				} finally {
					if (rs_tDBInput_5 != null) {
						rs_tDBInput_5.close();
					}
					if (stmt_tDBInput_5 != null) {
						stmt_tDBInput_5.close();
					}
				}
				globalMap.put("tDBInput_5_NB_LINE", nb_line_tDBInput_5);
				log.debug("tDBInput_5 - Retrieved records count: " + nb_line_tDBInput_5 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_5 - " + ("Done."));

				ok_Hash.put("tDBInput_5", true);
				end_Hash.put("tDBInput_5", System.currentTimeMillis());

				/**
				 * [tDBInput_5 end ] stop
				 */

				/**
				 * [tAdvancedHash_row5 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row5");

				tHash_Lookup_row5.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row5", 2, 0,
						"tDBInput_5", "\"timedimension\"", "tPostgresqlInput", "tAdvancedHash_row5",
						"tAdvancedHash_row5", "tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row5", true);
				end_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row5 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_5 finally ] start
				 */

				s(currentComponent = "tDBInput_5");

				cLabel = "\"timedimension\"";

				/**
				 * [tDBInput_5 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row5 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row5");

				/**
				 * [tAdvancedHash_row5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 1);
	}

	public static class row6Struct implements routines.system.IPersistableComparableLookupRow<row6Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int timekey;

		public int getTimekey() {
			return this.timekey;
		}

		public Boolean timekeyIsNullable() {
			return false;
		}

		public Boolean timekeyIsKey() {
			return true;
		}

		public Integer timekeyLength() {
			return 10;
		}

		public Integer timekeyPrecision() {
			return 0;
		}

		public String timekeyDefault() {

			return "nextval('timedimension_timekey_seq'::regclass)";

		}

		public String timekeyComment() {

			return "";

		}

		public String timekeyPattern() {

			return "";

		}

		public String timekeyOriginalDbColumnName() {

			return "timekey";

		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		public Boolean yearIsNullable() {
			return true;
		}

		public Boolean yearIsKey() {
			return false;
		}

		public Integer yearLength() {
			return 10;
		}

		public Integer yearPrecision() {
			return 0;
		}

		public String yearDefault() {

			return null;

		}

		public String yearComment() {

			return "";

		}

		public String yearPattern() {

			return "";

		}

		public String yearOriginalDbColumnName() {

			return "year";

		}

		public Integer month;

		public Integer getMonth() {
			return this.month;
		}

		public Boolean monthIsNullable() {
			return true;
		}

		public Boolean monthIsKey() {
			return false;
		}

		public Integer monthLength() {
			return 10;
		}

		public Integer monthPrecision() {
			return 0;
		}

		public String monthDefault() {

			return null;

		}

		public String monthComment() {

			return "";

		}

		public String monthPattern() {

			return "";

		}

		public String monthOriginalDbColumnName() {

			return "month";

		}

		public java.util.Date date;

		public java.util.Date getDate() {
			return this.date;
		}

		public Boolean dateIsNullable() {
			return true;
		}

		public Boolean dateIsKey() {
			return false;
		}

		public Integer dateLength() {
			return 13;
		}

		public Integer datePrecision() {
			return 0;
		}

		public String dateDefault() {

			return null;

		}

		public String dateComment() {

			return "";

		}

		public String datePattern() {

			return "yyyy-MM-dd";

		}

		public String dateOriginalDbColumnName() {

			return "date";

		}

		public Integer hour;

		public Integer getHour() {
			return this.hour;
		}

		public Boolean hourIsNullable() {
			return true;
		}

		public Boolean hourIsKey() {
			return false;
		}

		public Integer hourLength() {
			return 10;
		}

		public Integer hourPrecision() {
			return 0;
		}

		public String hourDefault() {

			return null;

		}

		public String hourComment() {

			return "";

		}

		public String hourPattern() {

			return "";

		}

		public String hourOriginalDbColumnName() {

			return "hour";

		}

		public String daypart;

		public String getDaypart() {
			return this.daypart;
		}

		public Boolean daypartIsNullable() {
			return true;
		}

		public Boolean daypartIsKey() {
			return false;
		}

		public Integer daypartLength() {
			return 32;
		}

		public Integer daypartPrecision() {
			return 0;
		}

		public String daypartDefault() {

			return null;

		}

		public String daypartComment() {

			return "";

		}

		public String daypartPattern() {

			return "";

		}

		public String daypartOriginalDbColumnName() {

			return "daypart";

		}

		public String weekdayorweekend;

		public String getWeekdayorweekend() {
			return this.weekdayorweekend;
		}

		public Boolean weekdayorweekendIsNullable() {
			return true;
		}

		public Boolean weekdayorweekendIsKey() {
			return false;
		}

		public Integer weekdayorweekendLength() {
			return 10;
		}

		public Integer weekdayorweekendPrecision() {
			return 0;
		}

		public String weekdayorweekendDefault() {

			return null;

		}

		public String weekdayorweekendComment() {

			return "";

		}

		public String weekdayorweekendPattern() {

			return "";

		}

		public String weekdayorweekendOriginalDbColumnName() {

			return "weekdayorweekend";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());

				result = prime * result + ((this.hour == null) ? 0 : this.hour.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row6Struct other = (row6Struct) obj;

			if (this.date == null) {
				if (other.date != null)
					return false;

			} else if (!this.date.equals(other.date))

				return false;

			if (this.hour == null) {
				if (other.hour != null)
					return false;

			} else if (!this.hour.equals(other.hour))

				return false;

			return true;
		}

		public void copyDataTo(row6Struct other) {

			other.timekey = this.timekey;
			other.year = this.year;
			other.month = this.month;
			other.date = this.date;
			other.hour = this.hour;
			other.daypart = this.daypart;
			other.weekdayorweekend = this.weekdayorweekend;

		}

		public void copyKeysDataTo(row6Struct other) {

			other.date = this.date;
			other.hour = this.hour;

		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.hour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.hour = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.timekey = dis.readInt();

				this.year = readInteger(dis, ois);

				this.month = readInteger(dis, ois);

				this.daypart = readString(dis, ois);

				this.weekdayorweekend = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.timekey = objectIn.readInt();

				this.year = readInteger(dis, objectIn);

				this.month = readInteger(dis, objectIn);

				this.daypart = readString(dis, objectIn);

				this.weekdayorweekend = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.timekey);

				writeInteger(this.year, dos, oos);

				writeInteger(this.month, dos, oos);

				writeString(this.daypart, dos, oos);

				writeString(this.weekdayorweekend, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.timekey);

				writeInteger(this.year, dos, objectOut);

				writeInteger(this.month, dos, objectOut);

				writeString(this.daypart, dos, objectOut);

				writeString(this.weekdayorweekend, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("timekey=" + String.valueOf(timekey));
			sb.append(",year=" + String.valueOf(year));
			sb.append(",month=" + String.valueOf(month));
			sb.append(",date=" + String.valueOf(date));
			sb.append(",hour=" + String.valueOf(hour));
			sb.append(",daypart=" + daypart);
			sb.append(",weekdayorweekend=" + weekdayorweekend);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(timekey);

			sb.append("|");

			if (year == null) {
				sb.append("<null>");
			} else {
				sb.append(year);
			}

			sb.append("|");

			if (month == null) {
				sb.append("<null>");
			} else {
				sb.append(month);
			}

			sb.append("|");

			if (date == null) {
				sb.append("<null>");
			} else {
				sb.append(date);
			}

			sb.append("|");

			if (hour == null) {
				sb.append("<null>");
			} else {
				sb.append(hour);
			}

			sb.append("|");

			if (daypart == null) {
				sb.append("<null>");
			} else {
				sb.append(daypart);
			}

			sb.append("|");

			if (weekdayorweekend == null) {
				sb.append("<null>");
			} else {
				sb.append(weekdayorweekend);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.date, other.date);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.hour, other.hour);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_6_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_6", "BtzOjp_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row6Struct row6 = new row6Struct();

				/**
				 * [tAdvancedHash_row6 begin ] start
				 */

				sh("tAdvancedHash_row6");

				s(currentComponent = "tAdvancedHash_row6");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row6");

				int tos_count_tAdvancedHash_row6 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row6", "tAdvancedHash_row6", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row6
				// source node:tDBInput_6 - inputs:(after_tDBInput_1) outputs:(row6,row6) |
				// target node:tAdvancedHash_row6 - inputs:(row6) outputs:()
				// linked node: tMap_5 - inputs:(StartTimeFound,row6)
				// outputs:(EndTimeFound,EndTimeNotFound)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row6 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct> tHash_Lookup_row6 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row6Struct>getLookup(matchingModeEnum_row6);

				globalMap.put("tHash_Lookup_row6", tHash_Lookup_row6);

				/**
				 * [tAdvancedHash_row6 begin ] stop
				 */

				/**
				 * [tDBInput_6 begin ] start
				 */

				sh("tDBInput_6");

				s(currentComponent = "tDBInput_6");

				cLabel = "\"timedimension\"";

				int tos_count_tDBInput_6 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_6 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_6 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_6 = new StringBuilder();
							log4jParamters_tDBInput_6.append("Parameters:");
							log4jParamters_tDBInput_6.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("QUERY" + " = "
									+ "\"SELECT    \\\"timedimension\\\".\\\"timekey\\\",    \\\"timedimension\\\".\\\"year\\\",    \\\"timedimension\\\".\\\"month\\\",    \\\"timedimension\\\".\\\"date\\\",    \\\"timedimension\\\".\\\"hour\\\",    \\\"timedimension\\\".\\\"daypart\\\",    \\\"timedimension\\\".\\\"weekdayorweekend\\\"  FROM \\\"timedimension\\\"\"");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("timekey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("year") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("month") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("date") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("hour") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("daypart") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("weekdayorweekend")
									+ "}]");
							log4jParamters_tDBInput_6.append(" | ");
							log4jParamters_tDBInput_6.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_6.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_6 - " + (log4jParamters_tDBInput_6));
						}
					}
					new BytesLimit65535_tDBInput_6().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_6", "\"timedimension\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_6 = 0;
				java.sql.Connection conn_tDBInput_6 = null;
				conn_tDBInput_6 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_6 != null) {
					if (conn_tDBInput_6.getMetaData() != null) {

						log.debug("tDBInput_6 - Uses an existing connection with username '"
								+ conn_tDBInput_6.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_6.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_6 = conn_tDBInput_6.createStatement();

				String dbquery_tDBInput_6 = "SELECT \n  \"timedimension\".\"timekey\", \n  \"timedimension\".\"year\", \n  \"timedimension\".\"month\", \n  \"timedimens"
						+ "ion\".\"date\", \n  \"timedimension\".\"hour\", \n  \"timedimension\".\"daypart\", \n  \"timedimension\".\"weekdayorweekend"
						+ "\"\n FROM \"timedimension\"";

				log.debug("tDBInput_6 - Executing the query: '" + dbquery_tDBInput_6 + "'.");

				globalMap.put("tDBInput_6_QUERY", dbquery_tDBInput_6);

				java.sql.ResultSet rs_tDBInput_6 = null;

				try {
					rs_tDBInput_6 = stmt_tDBInput_6.executeQuery(dbquery_tDBInput_6);
					java.sql.ResultSetMetaData rsmd_tDBInput_6 = rs_tDBInput_6.getMetaData();
					int colQtyInRs_tDBInput_6 = rsmd_tDBInput_6.getColumnCount();

					String tmpContent_tDBInput_6 = null;

					log.debug("tDBInput_6 - Retrieving records from the database.");

					while (rs_tDBInput_6.next()) {
						nb_line_tDBInput_6++;

						if (colQtyInRs_tDBInput_6 < 1) {
							row6.timekey = 0;
						} else {

							row6.timekey = rs_tDBInput_6.getInt(1);
							if (rs_tDBInput_6.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_6 < 2) {
							row6.year = null;
						} else {

							row6.year = rs_tDBInput_6.getInt(2);
							if (rs_tDBInput_6.wasNull()) {
								row6.year = null;
							}
						}
						if (colQtyInRs_tDBInput_6 < 3) {
							row6.month = null;
						} else {

							row6.month = rs_tDBInput_6.getInt(3);
							if (rs_tDBInput_6.wasNull()) {
								row6.month = null;
							}
						}
						if (colQtyInRs_tDBInput_6 < 4) {
							row6.date = null;
						} else {

							row6.date = routines.system.JDBCUtil.getDate(rs_tDBInput_6, 4);
						}
						if (colQtyInRs_tDBInput_6 < 5) {
							row6.hour = null;
						} else {

							row6.hour = rs_tDBInput_6.getInt(5);
							if (rs_tDBInput_6.wasNull()) {
								row6.hour = null;
							}
						}
						if (colQtyInRs_tDBInput_6 < 6) {
							row6.daypart = null;
						} else {

							row6.daypart = routines.system.JDBCUtil.getString(rs_tDBInput_6, 6, false);
						}
						if (colQtyInRs_tDBInput_6 < 7) {
							row6.weekdayorweekend = null;
						} else {

							row6.weekdayorweekend = routines.system.JDBCUtil.getString(rs_tDBInput_6, 7, false);
						}

						log.debug("tDBInput_6 - Retrieving the record " + nb_line_tDBInput_6 + ".");

						/**
						 * [tDBInput_6 begin ] stop
						 */

						/**
						 * [tDBInput_6 main ] start
						 */

						s(currentComponent = "tDBInput_6");

						cLabel = "\"timedimension\"";

						tos_count_tDBInput_6++;

						/**
						 * [tDBInput_6 main ] stop
						 */

						/**
						 * [tDBInput_6 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_6");

						cLabel = "\"timedimension\"";

						/**
						 * [tDBInput_6 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row6 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row6");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row6", "tDBInput_6", "\"timedimension\"", "tPostgresqlInput", "tAdvancedHash_row6",
								"tAdvancedHash_row6", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row6 - " + (row6 == null ? "" : row6.toLogString()));
						}

						row6Struct row6_HashRow = new row6Struct();

						row6_HashRow.timekey = row6.timekey;

						row6_HashRow.year = row6.year;

						row6_HashRow.month = row6.month;

						row6_HashRow.date = row6.date;

						row6_HashRow.hour = row6.hour;

						row6_HashRow.daypart = row6.daypart;

						row6_HashRow.weekdayorweekend = row6.weekdayorweekend;

						tHash_Lookup_row6.put(row6_HashRow);

						tos_count_tAdvancedHash_row6++;

						/**
						 * [tAdvancedHash_row6 main ] stop
						 */

						/**
						 * [tAdvancedHash_row6 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row6");

						/**
						 * [tAdvancedHash_row6 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row6 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row6");

						/**
						 * [tAdvancedHash_row6 process_data_end ] stop
						 */

						/**
						 * [tDBInput_6 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_6");

						cLabel = "\"timedimension\"";

						/**
						 * [tDBInput_6 process_data_end ] stop
						 */

						/**
						 * [tDBInput_6 end ] start
						 */

						s(currentComponent = "tDBInput_6");

						cLabel = "\"timedimension\"";

					}
				} finally {
					if (rs_tDBInput_6 != null) {
						rs_tDBInput_6.close();
					}
					if (stmt_tDBInput_6 != null) {
						stmt_tDBInput_6.close();
					}
				}
				globalMap.put("tDBInput_6_NB_LINE", nb_line_tDBInput_6);
				log.debug("tDBInput_6 - Retrieved records count: " + nb_line_tDBInput_6 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_6 - " + ("Done."));

				ok_Hash.put("tDBInput_6", true);
				end_Hash.put("tDBInput_6", System.currentTimeMillis());

				/**
				 * [tDBInput_6 end ] stop
				 */

				/**
				 * [tAdvancedHash_row6 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row6");

				tHash_Lookup_row6.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row6", 2, 0,
						"tDBInput_6", "\"timedimension\"", "tPostgresqlInput", "tAdvancedHash_row6",
						"tAdvancedHash_row6", "tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row6", true);
				end_Hash.put("tAdvancedHash_row6", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row6 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_6 finally ] start
				 */

				s(currentComponent = "tDBInput_6");

				cLabel = "\"timedimension\"";

				/**
				 * [tDBInput_6 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row6 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row6");

				/**
				 * [tAdvancedHash_row6 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_6_SUBPROCESS_STATE", 1);
	}

	public static class row7Struct implements routines.system.IPersistableComparableLookupRow<row7Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int bookingkey;

		public int getBookingkey() {
			return this.bookingkey;
		}

		public Boolean bookingkeyIsNullable() {
			return false;
		}

		public Boolean bookingkeyIsKey() {
			return true;
		}

		public Integer bookingkeyLength() {
			return 10;
		}

		public Integer bookingkeyPrecision() {
			return 0;
		}

		public String bookingkeyDefault() {

			return "nextval('booking_bookingkey_seq'::regclass)";

		}

		public String bookingkeyComment() {

			return "";

		}

		public String bookingkeyPattern() {

			return "";

		}

		public String bookingkeyOriginalDbColumnName() {

			return "bookingkey";

		}

		public Integer bookingid;

		public Integer getBookingid() {
			return this.bookingid;
		}

		public Boolean bookingidIsNullable() {
			return true;
		}

		public Boolean bookingidIsKey() {
			return false;
		}

		public Integer bookingidLength() {
			return 10;
		}

		public Integer bookingidPrecision() {
			return 0;
		}

		public String bookingidDefault() {

			return null;

		}

		public String bookingidComment() {

			return "";

		}

		public String bookingidPattern() {

			return "";

		}

		public String bookingidOriginalDbColumnName() {

			return "bookingid";

		}

		public String chargertype;

		public String getChargertype() {
			return this.chargertype;
		}

		public Boolean chargertypeIsNullable() {
			return true;
		}

		public Boolean chargertypeIsKey() {
			return false;
		}

		public Integer chargertypeLength() {
			return 20;
		}

		public Integer chargertypePrecision() {
			return 0;
		}

		public String chargertypeDefault() {

			return null;

		}

		public String chargertypeComment() {

			return "";

		}

		public String chargertypePattern() {

			return "";

		}

		public String chargertypeOriginalDbColumnName() {

			return "chargertype";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.bookingid == null) ? 0 : this.bookingid.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row7Struct other = (row7Struct) obj;

			if (this.bookingid == null) {
				if (other.bookingid != null)
					return false;

			} else if (!this.bookingid.equals(other.bookingid))

				return false;

			return true;
		}

		public void copyDataTo(row7Struct other) {

			other.bookingkey = this.bookingkey;
			other.bookingid = this.bookingid;
			other.chargertype = this.chargertype;

		}

		public void copyKeysDataTo(row7Struct other) {

			other.bookingid = this.bookingid;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.bookingid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.bookingid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.bookingkey = dis.readInt();

				this.chargertype = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.bookingkey = objectIn.readInt();

				this.chargertype = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.bookingkey);

				writeString(this.chargertype, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.bookingkey);

				writeString(this.chargertype, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("bookingkey=" + String.valueOf(bookingkey));
			sb.append(",bookingid=" + String.valueOf(bookingid));
			sb.append(",chargertype=" + chargertype);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(bookingkey);

			sb.append("|");

			if (bookingid == null) {
				sb.append("<null>");
			} else {
				sb.append(bookingid);
			}

			sb.append("|");

			if (chargertype == null) {
				sb.append("<null>");
			} else {
				sb.append(chargertype);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row7Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.bookingid, other.bookingid);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_7_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_7", "Nqpj8R_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row7Struct row7 = new row7Struct();

				/**
				 * [tAdvancedHash_row7 begin ] start
				 */

				sh("tAdvancedHash_row7");

				s(currentComponent = "tAdvancedHash_row7");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row7");

				int tos_count_tAdvancedHash_row7 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row7", "tAdvancedHash_row7", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row7
				// source node:tDBInput_7 - inputs:(after_tDBInput_1) outputs:(row7,row7) |
				// target node:tAdvancedHash_row7 - inputs:(row7) outputs:()
				// linked node: tMap_6 - inputs:(EndTimeFound,row7) outputs:(BookingOptional)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row7 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct> tHash_Lookup_row7 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row7Struct>getLookup(matchingModeEnum_row7);

				globalMap.put("tHash_Lookup_row7", tHash_Lookup_row7);

				/**
				 * [tAdvancedHash_row7 begin ] stop
				 */

				/**
				 * [tDBInput_7 begin ] start
				 */

				sh("tDBInput_7");

				s(currentComponent = "tDBInput_7");

				cLabel = "\"booking\"";

				int tos_count_tDBInput_7 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_7 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_7 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_7 = new StringBuilder();
							log4jParamters_tDBInput_7.append("Parameters:");
							log4jParamters_tDBInput_7.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append("QUERY" + " = "
									+ "\"SELECT    \\\"booking\\\".\\\"bookingkey\\\",    \\\"booking\\\".\\\"bookingid\\\",    \\\"booking\\\".\\\"chargertype\\\"  FROM \\\"booking\\\"\"");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append(
									"TRIM_COLUMN" + " = " + "[{TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("bookingkey")
											+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("bookingid") + "}, {TRIM="
											+ ("false") + ", SCHEMA_COLUMN=" + ("chargertype") + "}]");
							log4jParamters_tDBInput_7.append(" | ");
							log4jParamters_tDBInput_7.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_7.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_7 - " + (log4jParamters_tDBInput_7));
						}
					}
					new BytesLimit65535_tDBInput_7().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_7", "\"booking\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_7 = 0;
				java.sql.Connection conn_tDBInput_7 = null;
				conn_tDBInput_7 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_7 != null) {
					if (conn_tDBInput_7.getMetaData() != null) {

						log.debug("tDBInput_7 - Uses an existing connection with username '"
								+ conn_tDBInput_7.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_7.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_7 = conn_tDBInput_7.createStatement();

				String dbquery_tDBInput_7 = "SELECT \n  \"booking\".\"bookingkey\", \n  \"booking\".\"bookingid\", \n  \"booking\".\"chargertype\"\n FROM \"booking\"";

				log.debug("tDBInput_7 - Executing the query: '" + dbquery_tDBInput_7 + "'.");

				globalMap.put("tDBInput_7_QUERY", dbquery_tDBInput_7);

				java.sql.ResultSet rs_tDBInput_7 = null;

				try {
					rs_tDBInput_7 = stmt_tDBInput_7.executeQuery(dbquery_tDBInput_7);
					java.sql.ResultSetMetaData rsmd_tDBInput_7 = rs_tDBInput_7.getMetaData();
					int colQtyInRs_tDBInput_7 = rsmd_tDBInput_7.getColumnCount();

					String tmpContent_tDBInput_7 = null;

					log.debug("tDBInput_7 - Retrieving records from the database.");

					while (rs_tDBInput_7.next()) {
						nb_line_tDBInput_7++;

						if (colQtyInRs_tDBInput_7 < 1) {
							row7.bookingkey = 0;
						} else {

							row7.bookingkey = rs_tDBInput_7.getInt(1);
							if (rs_tDBInput_7.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_7 < 2) {
							row7.bookingid = null;
						} else {

							row7.bookingid = rs_tDBInput_7.getInt(2);
							if (rs_tDBInput_7.wasNull()) {
								row7.bookingid = null;
							}
						}
						if (colQtyInRs_tDBInput_7 < 3) {
							row7.chargertype = null;
						} else {

							row7.chargertype = routines.system.JDBCUtil.getString(rs_tDBInput_7, 3, false);
						}

						log.debug("tDBInput_7 - Retrieving the record " + nb_line_tDBInput_7 + ".");

						/**
						 * [tDBInput_7 begin ] stop
						 */

						/**
						 * [tDBInput_7 main ] start
						 */

						s(currentComponent = "tDBInput_7");

						cLabel = "\"booking\"";

						tos_count_tDBInput_7++;

						/**
						 * [tDBInput_7 main ] stop
						 */

						/**
						 * [tDBInput_7 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_7");

						cLabel = "\"booking\"";

						/**
						 * [tDBInput_7 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row7 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row7");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row7", "tDBInput_7", "\"booking\"", "tPostgresqlInput", "tAdvancedHash_row7",
								"tAdvancedHash_row7", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row7 - " + (row7 == null ? "" : row7.toLogString()));
						}

						row7Struct row7_HashRow = new row7Struct();

						row7_HashRow.bookingkey = row7.bookingkey;

						row7_HashRow.bookingid = row7.bookingid;

						row7_HashRow.chargertype = row7.chargertype;

						tHash_Lookup_row7.put(row7_HashRow);

						tos_count_tAdvancedHash_row7++;

						/**
						 * [tAdvancedHash_row7 main ] stop
						 */

						/**
						 * [tAdvancedHash_row7 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row7");

						/**
						 * [tAdvancedHash_row7 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row7 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row7");

						/**
						 * [tAdvancedHash_row7 process_data_end ] stop
						 */

						/**
						 * [tDBInput_7 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_7");

						cLabel = "\"booking\"";

						/**
						 * [tDBInput_7 process_data_end ] stop
						 */

						/**
						 * [tDBInput_7 end ] start
						 */

						s(currentComponent = "tDBInput_7");

						cLabel = "\"booking\"";

					}
				} finally {
					if (rs_tDBInput_7 != null) {
						rs_tDBInput_7.close();
					}
					if (stmt_tDBInput_7 != null) {
						stmt_tDBInput_7.close();
					}
				}
				globalMap.put("tDBInput_7_NB_LINE", nb_line_tDBInput_7);
				log.debug("tDBInput_7 - Retrieved records count: " + nb_line_tDBInput_7 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_7 - " + ("Done."));

				ok_Hash.put("tDBInput_7", true);
				end_Hash.put("tDBInput_7", System.currentTimeMillis());

				/**
				 * [tDBInput_7 end ] stop
				 */

				/**
				 * [tAdvancedHash_row7 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row7");

				tHash_Lookup_row7.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row7", 2, 0,
						"tDBInput_7", "\"booking\"", "tPostgresqlInput", "tAdvancedHash_row7", "tAdvancedHash_row7",
						"tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row7", true);
				end_Hash.put("tAdvancedHash_row7", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row7 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_7 finally ] start
				 */

				s(currentComponent = "tDBInput_7");

				cLabel = "\"booking\"";

				/**
				 * [tDBInput_7 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row7 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row7");

				/**
				 * [tAdvancedHash_row7 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_7_SUBPROCESS_STATE", 1);
	}

	public static class row8Struct implements routines.system.IPersistableComparableLookupRow<row8Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int paymentkey;

		public int getPaymentkey() {
			return this.paymentkey;
		}

		public Boolean paymentkeyIsNullable() {
			return false;
		}

		public Boolean paymentkeyIsKey() {
			return true;
		}

		public Integer paymentkeyLength() {
			return 10;
		}

		public Integer paymentkeyPrecision() {
			return 0;
		}

		public String paymentkeyDefault() {

			return "nextval('payment_paymentkey_seq'::regclass)";

		}

		public String paymentkeyComment() {

			return "";

		}

		public String paymentkeyPattern() {

			return "";

		}

		public String paymentkeyOriginalDbColumnName() {

			return "paymentkey";

		}

		public Integer paymentid;

		public Integer getPaymentid() {
			return this.paymentid;
		}

		public Boolean paymentidIsNullable() {
			return true;
		}

		public Boolean paymentidIsKey() {
			return false;
		}

		public Integer paymentidLength() {
			return 10;
		}

		public Integer paymentidPrecision() {
			return 0;
		}

		public String paymentidDefault() {

			return null;

		}

		public String paymentidComment() {

			return "";

		}

		public String paymentidPattern() {

			return "";

		}

		public String paymentidOriginalDbColumnName() {

			return "paymentid";

		}

		public String paymenttype;

		public String getPaymenttype() {
			return this.paymenttype;
		}

		public Boolean paymenttypeIsNullable() {
			return true;
		}

		public Boolean paymenttypeIsKey() {
			return false;
		}

		public Integer paymenttypeLength() {
			return 50;
		}

		public Integer paymenttypePrecision() {
			return 0;
		}

		public String paymenttypeDefault() {

			return null;

		}

		public String paymenttypeComment() {

			return "";

		}

		public String paymenttypePattern() {

			return "";

		}

		public String paymenttypeOriginalDbColumnName() {

			return "paymenttype";

		}

		public BigDecimal paymentamount;

		public BigDecimal getPaymentamount() {
			return this.paymentamount;
		}

		public Boolean paymentamountIsNullable() {
			return true;
		}

		public Boolean paymentamountIsKey() {
			return false;
		}

		public Integer paymentamountLength() {
			return 10;
		}

		public Integer paymentamountPrecision() {
			return 2;
		}

		public String paymentamountDefault() {

			return null;

		}

		public String paymentamountComment() {

			return "";

		}

		public String paymentamountPattern() {

			return "";

		}

		public String paymentamountOriginalDbColumnName() {

			return "paymentamount";

		}

		public Boolean iscompleted;

		public Boolean getIscompleted() {
			return this.iscompleted;
		}

		public Boolean iscompletedIsNullable() {
			return true;
		}

		public Boolean iscompletedIsKey() {
			return false;
		}

		public Integer iscompletedLength() {
			return 1;
		}

		public Integer iscompletedPrecision() {
			return 0;
		}

		public String iscompletedDefault() {

			return null;

		}

		public String iscompletedComment() {

			return "";

		}

		public String iscompletedPattern() {

			return "";

		}

		public String iscompletedOriginalDbColumnName() {

			return "iscompleted";

		}

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.sessionid == null) ? 0 : this.sessionid.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row8Struct other = (row8Struct) obj;

			if (this.sessionid == null) {
				if (other.sessionid != null)
					return false;

			} else if (!this.sessionid.equals(other.sessionid))

				return false;

			return true;
		}

		public void copyDataTo(row8Struct other) {

			other.paymentkey = this.paymentkey;
			other.paymentid = this.paymentid;
			other.paymenttype = this.paymenttype;
			other.paymentamount = this.paymentamount;
			other.iscompleted = this.iscompleted;
			other.sessionid = this.sessionid;

		}

		public void copyKeysDataTo(row8Struct other) {

			other.sessionid = this.sessionid;

		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.sessionid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.sessionid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.paymentkey = dis.readInt();

				this.paymentid = readInteger(dis, ois);

				this.paymenttype = readString(dis, ois);

				this.paymentamount = (BigDecimal) ois.readObject();

				length = dis.readByte();
				if (length == -1) {
					this.iscompleted = null;
				} else {
					this.iscompleted = dis.readBoolean();
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.paymentkey = objectIn.readInt();

				this.paymentid = readInteger(dis, objectIn);

				this.paymenttype = readString(dis, objectIn);

				this.paymentamount = (BigDecimal) objectIn.readObject();

				length = objectIn.readByte();
				if (length == -1) {
					this.iscompleted = null;
				} else {
					this.iscompleted = objectIn.readBoolean();
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.paymentkey);

				writeInteger(this.paymentid, dos, oos);

				writeString(this.paymenttype, dos, oos);

				oos.writeObject(this.paymentamount);

				if (this.iscompleted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.iscompleted);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.paymentkey);

				writeInteger(this.paymentid, dos, objectOut);

				writeString(this.paymenttype, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.paymentamount);

				if (this.iscompleted == null) {
					objectOut.writeByte(-1);
				} else {
					objectOut.writeByte(0);
					objectOut.writeBoolean(this.iscompleted);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("paymentkey=" + String.valueOf(paymentkey));
			sb.append(",paymentid=" + String.valueOf(paymentid));
			sb.append(",paymenttype=" + paymenttype);
			sb.append(",paymentamount=" + String.valueOf(paymentamount));
			sb.append(",iscompleted=" + String.valueOf(iscompleted));
			sb.append(",sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(paymentkey);

			sb.append("|");

			if (paymentid == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentid);
			}

			sb.append("|");

			if (paymenttype == null) {
				sb.append("<null>");
			} else {
				sb.append(paymenttype);
			}

			sb.append("|");

			if (paymentamount == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentamount);
			}

			sb.append("|");

			if (iscompleted == null) {
				sb.append("<null>");
			} else {
				sb.append(iscompleted);
			}

			sb.append("|");

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row8Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.sessionid, other.sessionid);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_8_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_8", "AFuxvC_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row8Struct row8 = new row8Struct();

				/**
				 * [tAdvancedHash_row8 begin ] start
				 */

				sh("tAdvancedHash_row8");

				s(currentComponent = "tAdvancedHash_row8");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row8");

				int tos_count_tAdvancedHash_row8 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row8", "tAdvancedHash_row8", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row8
				// source node:tDBInput_8 - inputs:(after_tDBInput_1) outputs:(row8,row8) |
				// target node:tAdvancedHash_row8 - inputs:(row8) outputs:()
				// linked node: tMap_7 - inputs:(BookingOptional,row8) outputs:(PaymentOptional)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row8 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct> tHash_Lookup_row8 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row8Struct>getLookup(matchingModeEnum_row8);

				globalMap.put("tHash_Lookup_row8", tHash_Lookup_row8);

				/**
				 * [tAdvancedHash_row8 begin ] stop
				 */

				/**
				 * [tDBInput_8 begin ] start
				 */

				sh("tDBInput_8");

				s(currentComponent = "tDBInput_8");

				cLabel = "\"payment\"";

				int tos_count_tDBInput_8 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_8 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_8 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_8 = new StringBuilder();
							log4jParamters_tDBInput_8.append("Parameters:");
							log4jParamters_tDBInput_8.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("QUERY" + " = "
									+ "\"SELECT    \\\"payment\\\".\\\"paymentkey\\\",    \\\"payment\\\".\\\"paymentid\\\",    \\\"payment\\\".\\\"paymenttype\\\",    \\\"payment\\\".\\\"paymentamount\\\",    \\\"payment\\\".\\\"iscompleted\\\",    \\\"payment\\\".\\\"sessionid\\\"  FROM \\\"payment\\\"\"");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("paymentkey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("paymentid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("paymenttype")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("paymentamount") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("iscompleted") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("sessionid") + "}]");
							log4jParamters_tDBInput_8.append(" | ");
							log4jParamters_tDBInput_8.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_8.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_8 - " + (log4jParamters_tDBInput_8));
						}
					}
					new BytesLimit65535_tDBInput_8().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_8", "\"payment\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_8 = 0;
				java.sql.Connection conn_tDBInput_8 = null;
				conn_tDBInput_8 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_8 != null) {
					if (conn_tDBInput_8.getMetaData() != null) {

						log.debug("tDBInput_8 - Uses an existing connection with username '"
								+ conn_tDBInput_8.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_8.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_8 = conn_tDBInput_8.createStatement();

				String dbquery_tDBInput_8 = "SELECT \n  \"payment\".\"paymentkey\", \n  \"payment\".\"paymentid\", \n  \"payment\".\"paymenttype\", \n  \"payment\".\"pa"
						+ "ymentamount\", \n  \"payment\".\"iscompleted\", \n  \"payment\".\"sessionid\"\n FROM \"payment\"";

				log.debug("tDBInput_8 - Executing the query: '" + dbquery_tDBInput_8 + "'.");

				globalMap.put("tDBInput_8_QUERY", dbquery_tDBInput_8);

				java.sql.ResultSet rs_tDBInput_8 = null;

				try {
					rs_tDBInput_8 = stmt_tDBInput_8.executeQuery(dbquery_tDBInput_8);
					java.sql.ResultSetMetaData rsmd_tDBInput_8 = rs_tDBInput_8.getMetaData();
					int colQtyInRs_tDBInput_8 = rsmd_tDBInput_8.getColumnCount();

					String tmpContent_tDBInput_8 = null;

					log.debug("tDBInput_8 - Retrieving records from the database.");

					while (rs_tDBInput_8.next()) {
						nb_line_tDBInput_8++;

						if (colQtyInRs_tDBInput_8 < 1) {
							row8.paymentkey = 0;
						} else {

							row8.paymentkey = rs_tDBInput_8.getInt(1);
							if (rs_tDBInput_8.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_8 < 2) {
							row8.paymentid = null;
						} else {

							row8.paymentid = rs_tDBInput_8.getInt(2);
							if (rs_tDBInput_8.wasNull()) {
								row8.paymentid = null;
							}
						}
						if (colQtyInRs_tDBInput_8 < 3) {
							row8.paymenttype = null;
						} else {

							row8.paymenttype = routines.system.JDBCUtil.getString(rs_tDBInput_8, 3, false);
						}
						if (colQtyInRs_tDBInput_8 < 4) {
							row8.paymentamount = null;
						} else {

							row8.paymentamount = rs_tDBInput_8.getBigDecimal(4);
							if (rs_tDBInput_8.wasNull()) {
								row8.paymentamount = null;
							}
						}
						if (colQtyInRs_tDBInput_8 < 5) {
							row8.iscompleted = null;
						} else {

							row8.iscompleted = rs_tDBInput_8.getBoolean(5);
							if (rs_tDBInput_8.wasNull()) {
								row8.iscompleted = null;
							}
						}
						if (colQtyInRs_tDBInput_8 < 6) {
							row8.sessionid = null;
						} else {

							row8.sessionid = rs_tDBInput_8.getInt(6);
							if (rs_tDBInput_8.wasNull()) {
								row8.sessionid = null;
							}
						}

						log.debug("tDBInput_8 - Retrieving the record " + nb_line_tDBInput_8 + ".");

						/**
						 * [tDBInput_8 begin ] stop
						 */

						/**
						 * [tDBInput_8 main ] start
						 */

						s(currentComponent = "tDBInput_8");

						cLabel = "\"payment\"";

						tos_count_tDBInput_8++;

						/**
						 * [tDBInput_8 main ] stop
						 */

						/**
						 * [tDBInput_8 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_8");

						cLabel = "\"payment\"";

						/**
						 * [tDBInput_8 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row8 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row8");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row8", "tDBInput_8", "\"payment\"", "tPostgresqlInput", "tAdvancedHash_row8",
								"tAdvancedHash_row8", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row8 - " + (row8 == null ? "" : row8.toLogString()));
						}

						row8Struct row8_HashRow = new row8Struct();

						row8_HashRow.paymentkey = row8.paymentkey;

						row8_HashRow.paymentid = row8.paymentid;

						row8_HashRow.paymenttype = row8.paymenttype;

						row8_HashRow.paymentamount = row8.paymentamount;

						row8_HashRow.iscompleted = row8.iscompleted;

						row8_HashRow.sessionid = row8.sessionid;

						tHash_Lookup_row8.put(row8_HashRow);

						tos_count_tAdvancedHash_row8++;

						/**
						 * [tAdvancedHash_row8 main ] stop
						 */

						/**
						 * [tAdvancedHash_row8 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row8");

						/**
						 * [tAdvancedHash_row8 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row8 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row8");

						/**
						 * [tAdvancedHash_row8 process_data_end ] stop
						 */

						/**
						 * [tDBInput_8 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_8");

						cLabel = "\"payment\"";

						/**
						 * [tDBInput_8 process_data_end ] stop
						 */

						/**
						 * [tDBInput_8 end ] start
						 */

						s(currentComponent = "tDBInput_8");

						cLabel = "\"payment\"";

					}
				} finally {
					if (rs_tDBInput_8 != null) {
						rs_tDBInput_8.close();
					}
					if (stmt_tDBInput_8 != null) {
						stmt_tDBInput_8.close();
					}
				}
				globalMap.put("tDBInput_8_NB_LINE", nb_line_tDBInput_8);
				log.debug("tDBInput_8 - Retrieved records count: " + nb_line_tDBInput_8 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_8 - " + ("Done."));

				ok_Hash.put("tDBInput_8", true);
				end_Hash.put("tDBInput_8", System.currentTimeMillis());

				/**
				 * [tDBInput_8 end ] stop
				 */

				/**
				 * [tAdvancedHash_row8 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row8");

				tHash_Lookup_row8.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row8", 2, 0,
						"tDBInput_8", "\"payment\"", "tPostgresqlInput", "tAdvancedHash_row8", "tAdvancedHash_row8",
						"tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row8", true);
				end_Hash.put("tAdvancedHash_row8", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row8 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_8 finally ] start
				 */

				s(currentComponent = "tDBInput_8");

				cLabel = "\"payment\"";

				/**
				 * [tDBInput_8 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row8 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row8");

				/**
				 * [tAdvancedHash_row8 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_8_SUBPROCESS_STATE", 1);
	}

	public static class row9Struct implements routines.system.IPersistableComparableLookupRow<row9Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return "nextval('pricingmodel_pricingmodelkey_seq'::regclass)";

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public Integer pricingmodelid;

		public Integer getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return true;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public BigDecimal rateperkwh;

		public BigDecimal getRateperkwh() {
			return this.rateperkwh;
		}

		public Boolean rateperkwhIsNullable() {
			return true;
		}

		public Boolean rateperkwhIsKey() {
			return false;
		}

		public Integer rateperkwhLength() {
			return 10;
		}

		public Integer rateperkwhPrecision() {
			return 2;
		}

		public String rateperkwhDefault() {

			return null;

		}

		public String rateperkwhComment() {

			return "";

		}

		public String rateperkwhPattern() {

			return "";

		}

		public String rateperkwhOriginalDbColumnName() {

			return "rateperkwh";

		}

		public java.util.Date peakstarttime;

		public java.util.Date getPeakstarttime() {
			return this.peakstarttime;
		}

		public Boolean peakstarttimeIsNullable() {
			return true;
		}

		public Boolean peakstarttimeIsKey() {
			return false;
		}

		public Integer peakstarttimeLength() {
			return 15;
		}

		public Integer peakstarttimePrecision() {
			return 6;
		}

		public String peakstarttimeDefault() {

			return null;

		}

		public String peakstarttimeComment() {

			return "";

		}

		public String peakstarttimePattern() {

			return "HH:mm:ss";

		}

		public String peakstarttimeOriginalDbColumnName() {

			return "peakstarttime";

		}

		public java.util.Date peakendtime;

		public java.util.Date getPeakendtime() {
			return this.peakendtime;
		}

		public Boolean peakendtimeIsNullable() {
			return true;
		}

		public Boolean peakendtimeIsKey() {
			return false;
		}

		public Integer peakendtimeLength() {
			return 15;
		}

		public Integer peakendtimePrecision() {
			return 6;
		}

		public String peakendtimeDefault() {

			return null;

		}

		public String peakendtimeComment() {

			return "";

		}

		public String peakendtimePattern() {

			return "HH:mm:ss";

		}

		public String peakendtimeOriginalDbColumnName() {

			return "peakendtime";

		}

		public BigDecimal peakmultiplier;

		public BigDecimal getPeakmultiplier() {
			return this.peakmultiplier;
		}

		public Boolean peakmultiplierIsNullable() {
			return true;
		}

		public Boolean peakmultiplierIsKey() {
			return false;
		}

		public Integer peakmultiplierLength() {
			return 3;
		}

		public Integer peakmultiplierPrecision() {
			return 2;
		}

		public String peakmultiplierDefault() {

			return null;

		}

		public String peakmultiplierComment() {

			return "";

		}

		public String peakmultiplierPattern() {

			return "";

		}

		public String peakmultiplierOriginalDbColumnName() {

			return "peakmultiplier";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.pricingmodelkey;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row9Struct other = (row9Struct) obj;

			if (this.pricingmodelkey != other.pricingmodelkey)
				return false;

			return true;
		}

		public void copyDataTo(row9Struct other) {

			other.pricingmodelkey = this.pricingmodelkey;
			other.pricingmodelid = this.pricingmodelid;
			other.rateperkwh = this.rateperkwh;
			other.peakstarttime = this.peakstarttime;
			other.peakendtime = this.peakendtime;
			other.peakmultiplier = this.peakmultiplier;

		}

		public void copyKeysDataTo(row9Struct other) {

			other.pricingmodelkey = this.pricingmodelkey;

		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(DataInputStream dis, ObjectInputStream ois) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.pricingmodelkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.pricingmodelkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.pricingmodelkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.pricingmodelkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.pricingmodelid = readInteger(dis, ois);

				this.rateperkwh = (BigDecimal) ois.readObject();

				this.peakstarttime = readDate(dis, ois);

				this.peakendtime = readDate(dis, ois);

				this.peakmultiplier = (BigDecimal) ois.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.pricingmodelid = readInteger(dis, objectIn);

				this.rateperkwh = (BigDecimal) objectIn.readObject();

				this.peakstarttime = readDate(dis, objectIn);

				this.peakendtime = readDate(dis, objectIn);

				this.peakmultiplier = (BigDecimal) objectIn.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeInteger(this.pricingmodelid, dos, oos);

				oos.writeObject(this.rateperkwh);

				writeDate(this.peakstarttime, dos, oos);

				writeDate(this.peakendtime, dos, oos);

				oos.writeObject(this.peakmultiplier);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeInteger(this.pricingmodelid, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.rateperkwh);

				writeDate(this.peakstarttime, dos, objectOut);

				writeDate(this.peakendtime, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.peakmultiplier);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",rateperkwh=" + String.valueOf(rateperkwh));
			sb.append(",peakstarttime=" + String.valueOf(peakstarttime));
			sb.append(",peakendtime=" + String.valueOf(peakendtime));
			sb.append(",peakmultiplier=" + String.valueOf(peakmultiplier));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(pricingmodelkey);

			sb.append("|");

			if (pricingmodelid == null) {
				sb.append("<null>");
			} else {
				sb.append(pricingmodelid);
			}

			sb.append("|");

			if (rateperkwh == null) {
				sb.append("<null>");
			} else {
				sb.append(rateperkwh);
			}

			sb.append("|");

			if (peakstarttime == null) {
				sb.append("<null>");
			} else {
				sb.append(peakstarttime);
			}

			sb.append("|");

			if (peakendtime == null) {
				sb.append("<null>");
			} else {
				sb.append(peakendtime);
			}

			sb.append("|");

			if (peakmultiplier == null) {
				sb.append("<null>");
			} else {
				sb.append(peakmultiplier);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row9Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.pricingmodelkey, other.pricingmodelkey);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_9Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_9_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_9", "RNOejb_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row9Struct row9 = new row9Struct();

				/**
				 * [tAdvancedHash_row9 begin ] start
				 */

				sh("tAdvancedHash_row9");

				s(currentComponent = "tAdvancedHash_row9");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row9");

				int tos_count_tAdvancedHash_row9 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row9", "tAdvancedHash_row9", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row9
				// source node:tDBInput_9 - inputs:(after_tDBInput_1) outputs:(row9,row9) |
				// target node:tAdvancedHash_row9 - inputs:(row9) outputs:()
				// linked node: tMap_8 - inputs:(PaymentOptional,row9) outputs:(PrepareRevenue)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row9 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct> tHash_Lookup_row9 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row9Struct>getLookup(matchingModeEnum_row9);

				globalMap.put("tHash_Lookup_row9", tHash_Lookup_row9);

				/**
				 * [tAdvancedHash_row9 begin ] stop
				 */

				/**
				 * [tDBInput_9 begin ] start
				 */

				sh("tDBInput_9");

				s(currentComponent = "tDBInput_9");

				cLabel = "\"pricingmodel\"";

				int tos_count_tDBInput_9 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_9 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_9 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_9 = new StringBuilder();
							log4jParamters_tDBInput_9.append("Parameters:");
							log4jParamters_tDBInput_9.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("HOST" + " = " + "\"34.56.183.144\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("PORT" + " = " + "\"5432\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("DBNAME" + " = " + "\"ev_station_dw\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("SCHEMA_DB" + " = " + "\"\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("USER" + " = " + "\"postgres\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:yvQ8Z0HLh8uJ3sYkOTiT2JeBzsboreQPy/h5HDy5S7MhMg==")
									.substring(0, 4) + "...");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("QUERY" + " = "
									+ "\"SELECT    \\\"pricingmodel\\\".\\\"pricingmodelkey\\\",    \\\"pricingmodel\\\".\\\"pricingmodelid\\\",    \\\"pricingmodel\\\".\\\"rateperkwh\\\",    \\\"pricingmodel\\\".\\\"peakstarttime\\\",    \\\"pricingmodel\\\".\\\"peakendtime\\\",    \\\"pricingmodel\\\".\\\"peakmultiplier\\\"  FROM \\\"pricingmodel\\\"\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("pricingmodelkey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("pricingmodelid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("rateperkwh") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("peakstarttime") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("peakendtime")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("peakmultiplier") + "}]");
							log4jParamters_tDBInput_9.append(" | ");
							log4jParamters_tDBInput_9.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_9.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_9 - " + (log4jParamters_tDBInput_9));
						}
					}
					new BytesLimit65535_tDBInput_9().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_9", "\"pricingmodel\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_9 = 0;
				java.sql.Connection conn_tDBInput_9 = null;
				String driverClass_tDBInput_9 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_9 = java.lang.Class.forName(driverClass_tDBInput_9);
				String dbUser_tDBInput_9 = "postgres";

				final String decryptedPassword_tDBInput_9 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:pSd9Qb1kQfyo0XGvW1ElPS0B7RwreF40d6iVYSIApdD5BA==");

				String dbPwd_tDBInput_9 = decryptedPassword_tDBInput_9;

				String url_tDBInput_9 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/" + "ev_station_dw";

				log.debug("tDBInput_9 - Driver ClassName: " + driverClass_tDBInput_9 + ".");

				log.debug("tDBInput_9 - Connection attempt to '" + url_tDBInput_9 + "' with the username '"
						+ dbUser_tDBInput_9 + "'.");

				conn_tDBInput_9 = java.sql.DriverManager.getConnection(url_tDBInput_9, dbUser_tDBInput_9,
						dbPwd_tDBInput_9);
				log.debug("tDBInput_9 - Connection to '" + url_tDBInput_9 + "' has succeeded.");

				log.debug("tDBInput_9 - Connection is set auto commit to 'false'.");

				conn_tDBInput_9.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_9 = conn_tDBInput_9.createStatement();

				String dbquery_tDBInput_9 = "SELECT \n  \"pricingmodel\".\"pricingmodelkey\", \n  \"pricingmodel\".\"pricingmodelid\", \n  \"pricingmodel\".\"rateperkw"
						+ "h\", \n  \"pricingmodel\".\"peakstarttime\", \n  \"pricingmodel\".\"peakendtime\", \n  \"pricingmodel\".\"peakmultiplier\"\n"
						+ " FROM \"pricingmodel\"";

				log.debug("tDBInput_9 - Executing the query: '" + dbquery_tDBInput_9 + "'.");

				globalMap.put("tDBInput_9_QUERY", dbquery_tDBInput_9);

				java.sql.ResultSet rs_tDBInput_9 = null;

				try {
					rs_tDBInput_9 = stmt_tDBInput_9.executeQuery(dbquery_tDBInput_9);
					java.sql.ResultSetMetaData rsmd_tDBInput_9 = rs_tDBInput_9.getMetaData();
					int colQtyInRs_tDBInput_9 = rsmd_tDBInput_9.getColumnCount();

					String tmpContent_tDBInput_9 = null;

					log.debug("tDBInput_9 - Retrieving records from the database.");

					while (rs_tDBInput_9.next()) {
						nb_line_tDBInput_9++;

						if (colQtyInRs_tDBInput_9 < 1) {
							row9.pricingmodelkey = 0;
						} else {

							row9.pricingmodelkey = rs_tDBInput_9.getInt(1);
							if (rs_tDBInput_9.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_9 < 2) {
							row9.pricingmodelid = null;
						} else {

							row9.pricingmodelid = rs_tDBInput_9.getInt(2);
							if (rs_tDBInput_9.wasNull()) {
								row9.pricingmodelid = null;
							}
						}
						if (colQtyInRs_tDBInput_9 < 3) {
							row9.rateperkwh = null;
						} else {

							row9.rateperkwh = rs_tDBInput_9.getBigDecimal(3);
							if (rs_tDBInput_9.wasNull()) {
								row9.rateperkwh = null;
							}
						}
						if (colQtyInRs_tDBInput_9 < 4) {
							row9.peakstarttime = null;
						} else {

							row9.peakstarttime = routines.system.JDBCUtil.getDate(rs_tDBInput_9, 4);
						}
						if (colQtyInRs_tDBInput_9 < 5) {
							row9.peakendtime = null;
						} else {

							row9.peakendtime = routines.system.JDBCUtil.getDate(rs_tDBInput_9, 5);
						}
						if (colQtyInRs_tDBInput_9 < 6) {
							row9.peakmultiplier = null;
						} else {

							row9.peakmultiplier = rs_tDBInput_9.getBigDecimal(6);
							if (rs_tDBInput_9.wasNull()) {
								row9.peakmultiplier = null;
							}
						}

						log.debug("tDBInput_9 - Retrieving the record " + nb_line_tDBInput_9 + ".");

						/**
						 * [tDBInput_9 begin ] stop
						 */

						/**
						 * [tDBInput_9 main ] start
						 */

						s(currentComponent = "tDBInput_9");

						cLabel = "\"pricingmodel\"";

						tos_count_tDBInput_9++;

						/**
						 * [tDBInput_9 main ] stop
						 */

						/**
						 * [tDBInput_9 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_9");

						cLabel = "\"pricingmodel\"";

						/**
						 * [tDBInput_9 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row9 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row9");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row9", "tDBInput_9", "\"pricingmodel\"", "tPostgresqlInput", "tAdvancedHash_row9",
								"tAdvancedHash_row9", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row9 - " + (row9 == null ? "" : row9.toLogString()));
						}

						row9Struct row9_HashRow = new row9Struct();

						row9_HashRow.pricingmodelkey = row9.pricingmodelkey;

						row9_HashRow.pricingmodelid = row9.pricingmodelid;

						row9_HashRow.rateperkwh = row9.rateperkwh;

						row9_HashRow.peakstarttime = row9.peakstarttime;

						row9_HashRow.peakendtime = row9.peakendtime;

						row9_HashRow.peakmultiplier = row9.peakmultiplier;

						tHash_Lookup_row9.put(row9_HashRow);

						tos_count_tAdvancedHash_row9++;

						/**
						 * [tAdvancedHash_row9 main ] stop
						 */

						/**
						 * [tAdvancedHash_row9 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row9");

						/**
						 * [tAdvancedHash_row9 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row9 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row9");

						/**
						 * [tAdvancedHash_row9 process_data_end ] stop
						 */

						/**
						 * [tDBInput_9 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_9");

						cLabel = "\"pricingmodel\"";

						/**
						 * [tDBInput_9 process_data_end ] stop
						 */

						/**
						 * [tDBInput_9 end ] start
						 */

						s(currentComponent = "tDBInput_9");

						cLabel = "\"pricingmodel\"";

					}
				} finally {
					if (rs_tDBInput_9 != null) {
						rs_tDBInput_9.close();
					}
					if (stmt_tDBInput_9 != null) {
						stmt_tDBInput_9.close();
					}
					if (conn_tDBInput_9 != null && !conn_tDBInput_9.isClosed()) {

						log.debug("tDBInput_9 - Closing the connection to the database.");

						conn_tDBInput_9.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug("tDBInput_9 - Connection to the database closed.");

					}

				}
				globalMap.put("tDBInput_9_NB_LINE", nb_line_tDBInput_9);
				log.debug("tDBInput_9 - Retrieved records count: " + nb_line_tDBInput_9 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_9 - " + ("Done."));

				ok_Hash.put("tDBInput_9", true);
				end_Hash.put("tDBInput_9", System.currentTimeMillis());

				/**
				 * [tDBInput_9 end ] stop
				 */

				/**
				 * [tAdvancedHash_row9 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row9");

				tHash_Lookup_row9.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row9", 2, 0,
						"tDBInput_9", "\"pricingmodel\"", "tPostgresqlInput", "tAdvancedHash_row9",
						"tAdvancedHash_row9", "tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row9", true);
				end_Hash.put("tAdvancedHash_row9", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row9 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_9 finally ] start
				 */

				s(currentComponent = "tDBInput_9");

				cLabel = "\"pricingmodel\"";

				/**
				 * [tDBInput_9 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row9 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row9");

				/**
				 * [tAdvancedHash_row9 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_9_SUBPROCESS_STATE", 1);
	}

	public static class row10Struct implements routines.system.IPersistableComparableLookupRow<row10Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int pricingmodelkey;

		public int getPricingmodelkey() {
			return this.pricingmodelkey;
		}

		public Boolean pricingmodelkeyIsNullable() {
			return false;
		}

		public Boolean pricingmodelkeyIsKey() {
			return true;
		}

		public Integer pricingmodelkeyLength() {
			return 10;
		}

		public Integer pricingmodelkeyPrecision() {
			return 0;
		}

		public String pricingmodelkeyDefault() {

			return "nextval('pricingmodel_pricingmodelkey_seq'::regclass)";

		}

		public String pricingmodelkeyComment() {

			return "";

		}

		public String pricingmodelkeyPattern() {

			return "";

		}

		public String pricingmodelkeyOriginalDbColumnName() {

			return "pricingmodelkey";

		}

		public Integer pricingmodelid;

		public Integer getPricingmodelid() {
			return this.pricingmodelid;
		}

		public Boolean pricingmodelidIsNullable() {
			return true;
		}

		public Boolean pricingmodelidIsKey() {
			return false;
		}

		public Integer pricingmodelidLength() {
			return 10;
		}

		public Integer pricingmodelidPrecision() {
			return 0;
		}

		public String pricingmodelidDefault() {

			return null;

		}

		public String pricingmodelidComment() {

			return "";

		}

		public String pricingmodelidPattern() {

			return "";

		}

		public String pricingmodelidOriginalDbColumnName() {

			return "pricingmodelid";

		}

		public BigDecimal rateperkwh;

		public BigDecimal getRateperkwh() {
			return this.rateperkwh;
		}

		public Boolean rateperkwhIsNullable() {
			return true;
		}

		public Boolean rateperkwhIsKey() {
			return false;
		}

		public Integer rateperkwhLength() {
			return 10;
		}

		public Integer rateperkwhPrecision() {
			return 2;
		}

		public String rateperkwhDefault() {

			return null;

		}

		public String rateperkwhComment() {

			return "";

		}

		public String rateperkwhPattern() {

			return "";

		}

		public String rateperkwhOriginalDbColumnName() {

			return "rateperkwh";

		}

		public java.util.Date peakstarttime;

		public java.util.Date getPeakstarttime() {
			return this.peakstarttime;
		}

		public Boolean peakstarttimeIsNullable() {
			return true;
		}

		public Boolean peakstarttimeIsKey() {
			return false;
		}

		public Integer peakstarttimeLength() {
			return 15;
		}

		public Integer peakstarttimePrecision() {
			return 6;
		}

		public String peakstarttimeDefault() {

			return null;

		}

		public String peakstarttimeComment() {

			return "";

		}

		public String peakstarttimePattern() {

			return "HH:mm:ss";

		}

		public String peakstarttimeOriginalDbColumnName() {

			return "peakstarttime";

		}

		public java.util.Date peakendtime;

		public java.util.Date getPeakendtime() {
			return this.peakendtime;
		}

		public Boolean peakendtimeIsNullable() {
			return true;
		}

		public Boolean peakendtimeIsKey() {
			return false;
		}

		public Integer peakendtimeLength() {
			return 15;
		}

		public Integer peakendtimePrecision() {
			return 6;
		}

		public String peakendtimeDefault() {

			return null;

		}

		public String peakendtimeComment() {

			return "";

		}

		public String peakendtimePattern() {

			return "HH:mm:ss";

		}

		public String peakendtimeOriginalDbColumnName() {

			return "peakendtime";

		}

		public BigDecimal peakmultiplier;

		public BigDecimal getPeakmultiplier() {
			return this.peakmultiplier;
		}

		public Boolean peakmultiplierIsNullable() {
			return true;
		}

		public Boolean peakmultiplierIsKey() {
			return false;
		}

		public Integer peakmultiplierLength() {
			return 3;
		}

		public Integer peakmultiplierPrecision() {
			return 2;
		}

		public String peakmultiplierDefault() {

			return null;

		}

		public String peakmultiplierComment() {

			return "";

		}

		public String peakmultiplierPattern() {

			return "";

		}

		public String peakmultiplierOriginalDbColumnName() {

			return "peakmultiplier";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.pricingmodelkey;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row10Struct other = (row10Struct) obj;

			if (this.pricingmodelkey != other.pricingmodelkey)
				return false;

			return true;
		}

		public void copyDataTo(row10Struct other) {

			other.pricingmodelkey = this.pricingmodelkey;
			other.pricingmodelid = this.pricingmodelid;
			other.rateperkwh = this.rateperkwh;
			other.peakstarttime = this.peakstarttime;
			other.peakendtime = this.peakendtime;
			other.peakmultiplier = this.peakmultiplier;

		}

		public void copyKeysDataTo(row10Struct other) {

			other.pricingmodelkey = this.pricingmodelkey;

		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(DataInputStream dis, ObjectInputStream ois) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.pricingmodelkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.pricingmodelkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.pricingmodelkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.pricingmodelkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.pricingmodelid = readInteger(dis, ois);

				this.rateperkwh = (BigDecimal) ois.readObject();

				this.peakstarttime = readDate(dis, ois);

				this.peakendtime = readDate(dis, ois);

				this.peakmultiplier = (BigDecimal) ois.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.pricingmodelid = readInteger(dis, objectIn);

				this.rateperkwh = (BigDecimal) objectIn.readObject();

				this.peakstarttime = readDate(dis, objectIn);

				this.peakendtime = readDate(dis, objectIn);

				this.peakmultiplier = (BigDecimal) objectIn.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeInteger(this.pricingmodelid, dos, oos);

				oos.writeObject(this.rateperkwh);

				writeDate(this.peakstarttime, dos, oos);

				writeDate(this.peakendtime, dos, oos);

				oos.writeObject(this.peakmultiplier);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeInteger(this.pricingmodelid, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.rateperkwh);

				writeDate(this.peakstarttime, dos, objectOut);

				writeDate(this.peakendtime, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.peakmultiplier);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("pricingmodelkey=" + String.valueOf(pricingmodelkey));
			sb.append(",pricingmodelid=" + String.valueOf(pricingmodelid));
			sb.append(",rateperkwh=" + String.valueOf(rateperkwh));
			sb.append(",peakstarttime=" + String.valueOf(peakstarttime));
			sb.append(",peakendtime=" + String.valueOf(peakendtime));
			sb.append(",peakmultiplier=" + String.valueOf(peakmultiplier));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(pricingmodelkey);

			sb.append("|");

			if (pricingmodelid == null) {
				sb.append("<null>");
			} else {
				sb.append(pricingmodelid);
			}

			sb.append("|");

			if (rateperkwh == null) {
				sb.append("<null>");
			} else {
				sb.append(rateperkwh);
			}

			sb.append("|");

			if (peakstarttime == null) {
				sb.append("<null>");
			} else {
				sb.append(peakstarttime);
			}

			sb.append("|");

			if (peakendtime == null) {
				sb.append("<null>");
			} else {
				sb.append(peakendtime);
			}

			sb.append("|");

			if (peakmultiplier == null) {
				sb.append("<null>");
			} else {
				sb.append(peakmultiplier);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row10Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.pricingmodelkey, other.pricingmodelkey);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_10Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_10_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_10", "0yywKC_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row10Struct row10 = new row10Struct();

				/**
				 * [tAdvancedHash_row10 begin ] start
				 */

				sh("tAdvancedHash_row10");

				s(currentComponent = "tAdvancedHash_row10");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row10");

				int tos_count_tAdvancedHash_row10 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row10", "tAdvancedHash_row10", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row10
				// source node:tDBInput_10 - inputs:(after_tDBInput_1) outputs:(row10,row10) |
				// target node:tAdvancedHash_row10 - inputs:(row10) outputs:()
				// linked node: tMap_9 - inputs:(PrepareRevenue,row10) outputs:(CalculateCost)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row10 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct> tHash_Lookup_row10 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row10Struct>getLookup(matchingModeEnum_row10);

				globalMap.put("tHash_Lookup_row10", tHash_Lookup_row10);

				/**
				 * [tAdvancedHash_row10 begin ] stop
				 */

				/**
				 * [tDBInput_10 begin ] start
				 */

				sh("tDBInput_10");

				s(currentComponent = "tDBInput_10");

				cLabel = "\"pricingmodel\"";

				int tos_count_tDBInput_10 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_10 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_10 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_10 = new StringBuilder();
							log4jParamters_tDBInput_10.append("Parameters:");
							log4jParamters_tDBInput_10.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("HOST" + " = " + "\"34.56.183.144\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("PORT" + " = " + "\"5432\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("DBNAME" + " = " + "\"ev_station_dw\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("SCHEMA_DB" + " = " + "\"\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("USER" + " = " + "\"postgres\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:TJq1Cuo1patqUtI/J2ZIw0juG5Ak/8ndOTq0upsyiJdYNA==")
									.substring(0, 4) + "...");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("QUERY" + " = "
									+ "\"SELECT    \\\"pricingmodel\\\".\\\"pricingmodelkey\\\",    \\\"pricingmodel\\\".\\\"pricingmodelid\\\",    \\\"pricingmodel\\\".\\\"rateperkwh\\\",    \\\"pricingmodel\\\".\\\"peakstarttime\\\",    \\\"pricingmodel\\\".\\\"peakendtime\\\",    \\\"pricingmodel\\\".\\\"peakmultiplier\\\"  FROM \\\"pricingmodel\\\"\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("pricingmodelkey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("pricingmodelid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("rateperkwh") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("peakstarttime") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("peakendtime")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("peakmultiplier") + "}]");
							log4jParamters_tDBInput_10.append(" | ");
							log4jParamters_tDBInput_10.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_10.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_10 - " + (log4jParamters_tDBInput_10));
						}
					}
					new BytesLimit65535_tDBInput_10().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_10", "\"pricingmodel\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_10 = 0;
				java.sql.Connection conn_tDBInput_10 = null;
				String driverClass_tDBInput_10 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_10 = java.lang.Class.forName(driverClass_tDBInput_10);
				String dbUser_tDBInput_10 = "postgres";

				final String decryptedPassword_tDBInput_10 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:kJhjWRmKXR3cj5qRYC6EOTTQa6lLA1qvJxXITdVFMPy/FQ==");

				String dbPwd_tDBInput_10 = decryptedPassword_tDBInput_10;

				String url_tDBInput_10 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/" + "ev_station_dw";

				log.debug("tDBInput_10 - Driver ClassName: " + driverClass_tDBInput_10 + ".");

				log.debug("tDBInput_10 - Connection attempt to '" + url_tDBInput_10 + "' with the username '"
						+ dbUser_tDBInput_10 + "'.");

				conn_tDBInput_10 = java.sql.DriverManager.getConnection(url_tDBInput_10, dbUser_tDBInput_10,
						dbPwd_tDBInput_10);
				log.debug("tDBInput_10 - Connection to '" + url_tDBInput_10 + "' has succeeded.");

				log.debug("tDBInput_10 - Connection is set auto commit to 'false'.");

				conn_tDBInput_10.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_10 = conn_tDBInput_10.createStatement();

				String dbquery_tDBInput_10 = "SELECT \n  \"pricingmodel\".\"pricingmodelkey\", \n  \"pricingmodel\".\"pricingmodelid\", \n  \"pricingmodel\".\"rateperkw"
						+ "h\", \n  \"pricingmodel\".\"peakstarttime\", \n  \"pricingmodel\".\"peakendtime\", \n  \"pricingmodel\".\"peakmultiplier\"\n"
						+ " FROM \"pricingmodel\"";

				log.debug("tDBInput_10 - Executing the query: '" + dbquery_tDBInput_10 + "'.");

				globalMap.put("tDBInput_10_QUERY", dbquery_tDBInput_10);

				java.sql.ResultSet rs_tDBInput_10 = null;

				try {
					rs_tDBInput_10 = stmt_tDBInput_10.executeQuery(dbquery_tDBInput_10);
					java.sql.ResultSetMetaData rsmd_tDBInput_10 = rs_tDBInput_10.getMetaData();
					int colQtyInRs_tDBInput_10 = rsmd_tDBInput_10.getColumnCount();

					String tmpContent_tDBInput_10 = null;

					log.debug("tDBInput_10 - Retrieving records from the database.");

					while (rs_tDBInput_10.next()) {
						nb_line_tDBInput_10++;

						if (colQtyInRs_tDBInput_10 < 1) {
							row10.pricingmodelkey = 0;
						} else {

							row10.pricingmodelkey = rs_tDBInput_10.getInt(1);
							if (rs_tDBInput_10.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_10 < 2) {
							row10.pricingmodelid = null;
						} else {

							row10.pricingmodelid = rs_tDBInput_10.getInt(2);
							if (rs_tDBInput_10.wasNull()) {
								row10.pricingmodelid = null;
							}
						}
						if (colQtyInRs_tDBInput_10 < 3) {
							row10.rateperkwh = null;
						} else {

							row10.rateperkwh = rs_tDBInput_10.getBigDecimal(3);
							if (rs_tDBInput_10.wasNull()) {
								row10.rateperkwh = null;
							}
						}
						if (colQtyInRs_tDBInput_10 < 4) {
							row10.peakstarttime = null;
						} else {

							row10.peakstarttime = routines.system.JDBCUtil.getDate(rs_tDBInput_10, 4);
						}
						if (colQtyInRs_tDBInput_10 < 5) {
							row10.peakendtime = null;
						} else {

							row10.peakendtime = routines.system.JDBCUtil.getDate(rs_tDBInput_10, 5);
						}
						if (colQtyInRs_tDBInput_10 < 6) {
							row10.peakmultiplier = null;
						} else {

							row10.peakmultiplier = rs_tDBInput_10.getBigDecimal(6);
							if (rs_tDBInput_10.wasNull()) {
								row10.peakmultiplier = null;
							}
						}

						log.debug("tDBInput_10 - Retrieving the record " + nb_line_tDBInput_10 + ".");

						/**
						 * [tDBInput_10 begin ] stop
						 */

						/**
						 * [tDBInput_10 main ] start
						 */

						s(currentComponent = "tDBInput_10");

						cLabel = "\"pricingmodel\"";

						tos_count_tDBInput_10++;

						/**
						 * [tDBInput_10 main ] stop
						 */

						/**
						 * [tDBInput_10 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_10");

						cLabel = "\"pricingmodel\"";

						/**
						 * [tDBInput_10 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row10 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row10");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row10", "tDBInput_10", "\"pricingmodel\"", "tPostgresqlInput", "tAdvancedHash_row10",
								"tAdvancedHash_row10", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row10 - " + (row10 == null ? "" : row10.toLogString()));
						}

						row10Struct row10_HashRow = new row10Struct();

						row10_HashRow.pricingmodelkey = row10.pricingmodelkey;

						row10_HashRow.pricingmodelid = row10.pricingmodelid;

						row10_HashRow.rateperkwh = row10.rateperkwh;

						row10_HashRow.peakstarttime = row10.peakstarttime;

						row10_HashRow.peakendtime = row10.peakendtime;

						row10_HashRow.peakmultiplier = row10.peakmultiplier;

						tHash_Lookup_row10.put(row10_HashRow);

						tos_count_tAdvancedHash_row10++;

						/**
						 * [tAdvancedHash_row10 main ] stop
						 */

						/**
						 * [tAdvancedHash_row10 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row10");

						/**
						 * [tAdvancedHash_row10 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row10 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row10");

						/**
						 * [tAdvancedHash_row10 process_data_end ] stop
						 */

						/**
						 * [tDBInput_10 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_10");

						cLabel = "\"pricingmodel\"";

						/**
						 * [tDBInput_10 process_data_end ] stop
						 */

						/**
						 * [tDBInput_10 end ] start
						 */

						s(currentComponent = "tDBInput_10");

						cLabel = "\"pricingmodel\"";

					}
				} finally {
					if (rs_tDBInput_10 != null) {
						rs_tDBInput_10.close();
					}
					if (stmt_tDBInput_10 != null) {
						stmt_tDBInput_10.close();
					}
					if (conn_tDBInput_10 != null && !conn_tDBInput_10.isClosed()) {

						log.debug("tDBInput_10 - Closing the connection to the database.");

						conn_tDBInput_10.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug("tDBInput_10 - Connection to the database closed.");

					}

				}
				globalMap.put("tDBInput_10_NB_LINE", nb_line_tDBInput_10);
				log.debug("tDBInput_10 - Retrieved records count: " + nb_line_tDBInput_10 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_10 - " + ("Done."));

				ok_Hash.put("tDBInput_10", true);
				end_Hash.put("tDBInput_10", System.currentTimeMillis());

				/**
				 * [tDBInput_10 end ] stop
				 */

				/**
				 * [tAdvancedHash_row10 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row10");

				tHash_Lookup_row10.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row10", 2, 0,
						"tDBInput_10", "\"pricingmodel\"", "tPostgresqlInput", "tAdvancedHash_row10",
						"tAdvancedHash_row10", "tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row10", true);
				end_Hash.put("tAdvancedHash_row10", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row10 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_10 finally ] start
				 */

				s(currentComponent = "tDBInput_10");

				cLabel = "\"pricingmodel\"";

				/**
				 * [tDBInput_10 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row10 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row10");

				/**
				 * [tAdvancedHash_row10 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_10_SUBPROCESS_STATE", 1);
	}

	public static class row11Struct implements routines.system.IPersistableComparableLookupRow<row11Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_SessionFactLoad = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int paymentkey;

		public int getPaymentkey() {
			return this.paymentkey;
		}

		public Boolean paymentkeyIsNullable() {
			return false;
		}

		public Boolean paymentkeyIsKey() {
			return true;
		}

		public Integer paymentkeyLength() {
			return 10;
		}

		public Integer paymentkeyPrecision() {
			return 0;
		}

		public String paymentkeyDefault() {

			return "nextval('payment_paymentkey_seq'::regclass)";

		}

		public String paymentkeyComment() {

			return "";

		}

		public String paymentkeyPattern() {

			return "";

		}

		public String paymentkeyOriginalDbColumnName() {

			return "paymentkey";

		}

		public Integer paymentid;

		public Integer getPaymentid() {
			return this.paymentid;
		}

		public Boolean paymentidIsNullable() {
			return true;
		}

		public Boolean paymentidIsKey() {
			return false;
		}

		public Integer paymentidLength() {
			return 10;
		}

		public Integer paymentidPrecision() {
			return 0;
		}

		public String paymentidDefault() {

			return null;

		}

		public String paymentidComment() {

			return "";

		}

		public String paymentidPattern() {

			return "";

		}

		public String paymentidOriginalDbColumnName() {

			return "paymentid";

		}

		public String paymenttype;

		public String getPaymenttype() {
			return this.paymenttype;
		}

		public Boolean paymenttypeIsNullable() {
			return true;
		}

		public Boolean paymenttypeIsKey() {
			return false;
		}

		public Integer paymenttypeLength() {
			return 50;
		}

		public Integer paymenttypePrecision() {
			return 0;
		}

		public String paymenttypeDefault() {

			return null;

		}

		public String paymenttypeComment() {

			return "";

		}

		public String paymenttypePattern() {

			return "";

		}

		public String paymenttypeOriginalDbColumnName() {

			return "paymenttype";

		}

		public BigDecimal paymentamount;

		public BigDecimal getPaymentamount() {
			return this.paymentamount;
		}

		public Boolean paymentamountIsNullable() {
			return true;
		}

		public Boolean paymentamountIsKey() {
			return false;
		}

		public Integer paymentamountLength() {
			return 10;
		}

		public Integer paymentamountPrecision() {
			return 2;
		}

		public String paymentamountDefault() {

			return null;

		}

		public String paymentamountComment() {

			return "";

		}

		public String paymentamountPattern() {

			return "";

		}

		public String paymentamountOriginalDbColumnName() {

			return "paymentamount";

		}

		public Boolean iscompleted;

		public Boolean getIscompleted() {
			return this.iscompleted;
		}

		public Boolean iscompletedIsNullable() {
			return true;
		}

		public Boolean iscompletedIsKey() {
			return false;
		}

		public Integer iscompletedLength() {
			return 1;
		}

		public Integer iscompletedPrecision() {
			return 0;
		}

		public String iscompletedDefault() {

			return null;

		}

		public String iscompletedComment() {

			return "";

		}

		public String iscompletedPattern() {

			return "";

		}

		public String iscompletedOriginalDbColumnName() {

			return "iscompleted";

		}

		public Integer sessionid;

		public Integer getSessionid() {
			return this.sessionid;
		}

		public Boolean sessionidIsNullable() {
			return true;
		}

		public Boolean sessionidIsKey() {
			return false;
		}

		public Integer sessionidLength() {
			return 10;
		}

		public Integer sessionidPrecision() {
			return 0;
		}

		public String sessionidDefault() {

			return null;

		}

		public String sessionidComment() {

			return "";

		}

		public String sessionidPattern() {

			return "";

		}

		public String sessionidOriginalDbColumnName() {

			return "sessionid";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.paymentkey;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row11Struct other = (row11Struct) obj;

			if (this.paymentkey != other.paymentkey)
				return false;

			return true;
		}

		public void copyDataTo(row11Struct other) {

			other.paymentkey = this.paymentkey;
			other.paymentid = this.paymentid;
			other.paymenttype = this.paymenttype;
			other.paymentamount = this.paymentamount;
			other.iscompleted = this.iscompleted;
			other.sessionid = this.sessionid;

		}

		public void copyKeysDataTo(row11Struct other) {

			other.paymentkey = this.paymentkey;

		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.paymentkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_SessionFactLoad) {

				try {

					int length = 0;

					this.paymentkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.paymentkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.paymentkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.paymentid = readInteger(dis, ois);

				this.paymenttype = readString(dis, ois);

				this.paymentamount = (BigDecimal) ois.readObject();

				length = dis.readByte();
				if (length == -1) {
					this.iscompleted = null;
				} else {
					this.iscompleted = dis.readBoolean();
				}

				this.sessionid = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.paymentid = readInteger(dis, objectIn);

				this.paymenttype = readString(dis, objectIn);

				this.paymentamount = (BigDecimal) objectIn.readObject();

				length = objectIn.readByte();
				if (length == -1) {
					this.iscompleted = null;
				} else {
					this.iscompleted = objectIn.readBoolean();
				}

				this.sessionid = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeInteger(this.paymentid, dos, oos);

				writeString(this.paymenttype, dos, oos);

				oos.writeObject(this.paymentamount);

				if (this.iscompleted == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.iscompleted);
				}

				writeInteger(this.sessionid, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeInteger(this.paymentid, dos, objectOut);

				writeString(this.paymenttype, dos, objectOut);

				objectOut.clearInstanceCache();
				objectOut.writeObject(this.paymentamount);

				if (this.iscompleted == null) {
					objectOut.writeByte(-1);
				} else {
					objectOut.writeByte(0);
					objectOut.writeBoolean(this.iscompleted);
				}

				writeInteger(this.sessionid, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("paymentkey=" + String.valueOf(paymentkey));
			sb.append(",paymentid=" + String.valueOf(paymentid));
			sb.append(",paymenttype=" + paymenttype);
			sb.append(",paymentamount=" + String.valueOf(paymentamount));
			sb.append(",iscompleted=" + String.valueOf(iscompleted));
			sb.append(",sessionid=" + String.valueOf(sessionid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(paymentkey);

			sb.append("|");

			if (paymentid == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentid);
			}

			sb.append("|");

			if (paymenttype == null) {
				sb.append("<null>");
			} else {
				sb.append(paymenttype);
			}

			sb.append("|");

			if (paymentamount == null) {
				sb.append("<null>");
			} else {
				sb.append(paymentamount);
			}

			sb.append("|");

			if (iscompleted == null) {
				sb.append("<null>");
			} else {
				sb.append(iscompleted);
			}

			sb.append("|");

			if (sessionid == null) {
				sb.append("<null>");
			} else {
				sb.append(sessionid);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row11Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.paymentkey, other.paymentkey);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_11Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_11_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_11", "fImpRu_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row11Struct row11 = new row11Struct();

				/**
				 * [tAdvancedHash_row11 begin ] start
				 */

				sh("tAdvancedHash_row11");

				s(currentComponent = "tAdvancedHash_row11");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row11");

				int tos_count_tAdvancedHash_row11 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tAdvancedHash_row11", "tAdvancedHash_row11", "tAdvancedHash");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				// connection name:row11
				// source node:tDBInput_11 - inputs:(after_tDBInput_1) outputs:(row11,row11) |
				// target node:tAdvancedHash_row11 - inputs:(row11) outputs:()
				// linked node: tMap_10 - inputs:(CalculateCost,row11)
				// outputs:(CalculateRevenue)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row11 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct> tHash_Lookup_row11 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row11Struct>getLookup(matchingModeEnum_row11);

				globalMap.put("tHash_Lookup_row11", tHash_Lookup_row11);

				/**
				 * [tAdvancedHash_row11 begin ] stop
				 */

				/**
				 * [tDBInput_11 begin ] start
				 */

				sh("tDBInput_11");

				s(currentComponent = "tDBInput_11");

				cLabel = "\"payment\"";

				int tos_count_tDBInput_11 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_11 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_11 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_11 = new StringBuilder();
							log4jParamters_tDBInput_11.append("Parameters:");
							log4jParamters_tDBInput_11.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("QUERY" + " = "
									+ "\"SELECT    \\\"payment\\\".\\\"paymentkey\\\",    \\\"payment\\\".\\\"paymentid\\\",    \\\"payment\\\".\\\"paymenttype\\\",    \\\"payment\\\".\\\"paymentamount\\\",    \\\"payment\\\".\\\"iscompleted\\\",    \\\"payment\\\".\\\"sessionid\\\"  FROM \\\"payment\\\"\"");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("paymentkey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("paymentid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("paymenttype")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("paymentamount") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("iscompleted") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("sessionid") + "}]");
							log4jParamters_tDBInput_11.append(" | ");
							log4jParamters_tDBInput_11.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlInput");
							log4jParamters_tDBInput_11.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_11 - " + (log4jParamters_tDBInput_11));
						}
					}
					new BytesLimit65535_tDBInput_11().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_11", "\"payment\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_11 = 0;
				java.sql.Connection conn_tDBInput_11 = null;
				conn_tDBInput_11 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_11 != null) {
					if (conn_tDBInput_11.getMetaData() != null) {

						log.debug("tDBInput_11 - Uses an existing connection with username '"
								+ conn_tDBInput_11.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_11.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_11 = conn_tDBInput_11.createStatement();

				String dbquery_tDBInput_11 = "SELECT \n  \"payment\".\"paymentkey\", \n  \"payment\".\"paymentid\", \n  \"payment\".\"paymenttype\", \n  \"payment\".\"pa"
						+ "ymentamount\", \n  \"payment\".\"iscompleted\", \n  \"payment\".\"sessionid\"\n FROM \"payment\"";

				log.debug("tDBInput_11 - Executing the query: '" + dbquery_tDBInput_11 + "'.");

				globalMap.put("tDBInput_11_QUERY", dbquery_tDBInput_11);

				java.sql.ResultSet rs_tDBInput_11 = null;

				try {
					rs_tDBInput_11 = stmt_tDBInput_11.executeQuery(dbquery_tDBInput_11);
					java.sql.ResultSetMetaData rsmd_tDBInput_11 = rs_tDBInput_11.getMetaData();
					int colQtyInRs_tDBInput_11 = rsmd_tDBInput_11.getColumnCount();

					String tmpContent_tDBInput_11 = null;

					log.debug("tDBInput_11 - Retrieving records from the database.");

					while (rs_tDBInput_11.next()) {
						nb_line_tDBInput_11++;

						if (colQtyInRs_tDBInput_11 < 1) {
							row11.paymentkey = 0;
						} else {

							row11.paymentkey = rs_tDBInput_11.getInt(1);
							if (rs_tDBInput_11.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_11 < 2) {
							row11.paymentid = null;
						} else {

							row11.paymentid = rs_tDBInput_11.getInt(2);
							if (rs_tDBInput_11.wasNull()) {
								row11.paymentid = null;
							}
						}
						if (colQtyInRs_tDBInput_11 < 3) {
							row11.paymenttype = null;
						} else {

							row11.paymenttype = routines.system.JDBCUtil.getString(rs_tDBInput_11, 3, false);
						}
						if (colQtyInRs_tDBInput_11 < 4) {
							row11.paymentamount = null;
						} else {

							row11.paymentamount = rs_tDBInput_11.getBigDecimal(4);
							if (rs_tDBInput_11.wasNull()) {
								row11.paymentamount = null;
							}
						}
						if (colQtyInRs_tDBInput_11 < 5) {
							row11.iscompleted = null;
						} else {

							row11.iscompleted = rs_tDBInput_11.getBoolean(5);
							if (rs_tDBInput_11.wasNull()) {
								row11.iscompleted = null;
							}
						}
						if (colQtyInRs_tDBInput_11 < 6) {
							row11.sessionid = null;
						} else {

							row11.sessionid = rs_tDBInput_11.getInt(6);
							if (rs_tDBInput_11.wasNull()) {
								row11.sessionid = null;
							}
						}

						log.debug("tDBInput_11 - Retrieving the record " + nb_line_tDBInput_11 + ".");

						/**
						 * [tDBInput_11 begin ] stop
						 */

						/**
						 * [tDBInput_11 main ] start
						 */

						s(currentComponent = "tDBInput_11");

						cLabel = "\"payment\"";

						tos_count_tDBInput_11++;

						/**
						 * [tDBInput_11 main ] stop
						 */

						/**
						 * [tDBInput_11 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_11");

						cLabel = "\"payment\"";

						/**
						 * [tDBInput_11 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row11 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row11");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row11", "tDBInput_11", "\"payment\"", "tPostgresqlInput", "tAdvancedHash_row11",
								"tAdvancedHash_row11", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row11 - " + (row11 == null ? "" : row11.toLogString()));
						}

						row11Struct row11_HashRow = new row11Struct();

						row11_HashRow.paymentkey = row11.paymentkey;

						row11_HashRow.paymentid = row11.paymentid;

						row11_HashRow.paymenttype = row11.paymenttype;

						row11_HashRow.paymentamount = row11.paymentamount;

						row11_HashRow.iscompleted = row11.iscompleted;

						row11_HashRow.sessionid = row11.sessionid;

						tHash_Lookup_row11.put(row11_HashRow);

						tos_count_tAdvancedHash_row11++;

						/**
						 * [tAdvancedHash_row11 main ] stop
						 */

						/**
						 * [tAdvancedHash_row11 process_data_begin ] start
						 */

						s(currentComponent = "tAdvancedHash_row11");

						/**
						 * [tAdvancedHash_row11 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row11 process_data_end ] start
						 */

						s(currentComponent = "tAdvancedHash_row11");

						/**
						 * [tAdvancedHash_row11 process_data_end ] stop
						 */

						/**
						 * [tDBInput_11 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_11");

						cLabel = "\"payment\"";

						/**
						 * [tDBInput_11 process_data_end ] stop
						 */

						/**
						 * [tDBInput_11 end ] start
						 */

						s(currentComponent = "tDBInput_11");

						cLabel = "\"payment\"";

					}
				} finally {
					if (rs_tDBInput_11 != null) {
						rs_tDBInput_11.close();
					}
					if (stmt_tDBInput_11 != null) {
						stmt_tDBInput_11.close();
					}
				}
				globalMap.put("tDBInput_11_NB_LINE", nb_line_tDBInput_11);
				log.debug("tDBInput_11 - Retrieved records count: " + nb_line_tDBInput_11 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_11 - " + ("Done."));

				ok_Hash.put("tDBInput_11", true);
				end_Hash.put("tDBInput_11", System.currentTimeMillis());

				/**
				 * [tDBInput_11 end ] stop
				 */

				/**
				 * [tAdvancedHash_row11 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row11");

				tHash_Lookup_row11.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row11", 2, 0,
						"tDBInput_11", "\"payment\"", "tPostgresqlInput", "tAdvancedHash_row11", "tAdvancedHash_row11",
						"tAdvancedHash", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tAdvancedHash_row11", true);
				end_Hash.put("tAdvancedHash_row11", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row11 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_11 finally ] start
				 */

				s(currentComponent = "tDBInput_11");

				cLabel = "\"payment\"";

				/**
				 * [tDBInput_11 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row11 finally ] start
				 */

				s(currentComponent = "tAdvancedHash_row11");

				/**
				 * [tAdvancedHash_row11 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_11_SUBPROCESS_STATE", 1);
	}

	public void tDBOutput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBOutput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBOutput_1", "o7Wwog_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBOutput_1 begin ] start
				 */

				sh("tDBOutput_1");

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"chargingsessionfact\"";

				int tos_count_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_1 = new StringBuilder();
							log4jParamters_tDBOutput_1.append("Parameters:");
							log4jParamters_tDBOutput_1.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("HOST" + " = " + "\"34.56.183.144\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("PORT" + " = " + "\"5432\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DBNAME" + " = " + "\"ev_station_dw\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("SCHEMA_DB" + " = " + "\"\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USER" + " = " + "\"postgres\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:y0YrFrkY4kdyexGvA2oFM+X7UCj64wVnCO8Og+9jlIVDMA==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"chargingsessionfact\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("COMMIT_EVERY" + " = " + "10000");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("SUPPORT_NULL_WHERE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("CONVERT_COLUMN_TABLE_TO_LOWERCASE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_BATCH_SIZE" + " = " + "true");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlOutput");
							log4jParamters_tDBOutput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_1 - " + (log4jParamters_tDBOutput_1));
						}
					}
					new BytesLimit65535_tDBOutput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_1", "\"chargingsessionfact\"", "tPostgresqlOutput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String dbschema_tDBOutput_1 = null;
				dbschema_tDBOutput_1 = "";

				String tableName_tDBOutput_1 = null;
				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = ("chargingsessionfact");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("chargingsessionfact");
				}

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				boolean whetherReject_tDBOutput_1 = false;

				java.sql.Connection conn_tDBOutput_1 = null;
				String dbUser_tDBOutput_1 = null;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Driver ClassName: ") + ("org.postgresql.Driver") + ("."));
				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_1 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/" + "ev_station_dw";
				dbUser_tDBOutput_1 = "postgres";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:DiuYsR0a1woZz2gr+acm8d5+k56Vt7h6/WhrpOkOZH3dBg==");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection attempts to '") + (url_tDBOutput_1)
							+ ("' with the username '") + (dbUser_tDBOutput_1) + ("'."));
				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);
				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection to '") + (url_tDBOutput_1) + ("' has succeeded."));

				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;
				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_1.getAutoCommit()) + ("'."));

				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;

				int count_tDBOutput_1 = 0;
				java.lang.StringBuilder sb_tDBOutput_1 = new java.lang.StringBuilder();
				sb_tDBOutput_1.append("INSERT INTO \"").append(tableName_tDBOutput_1).append(
						"\" (\"sessionid\",\"totalkwhconsumed\",\"totalrevenue\",\"userkey\",\"vehiclekey\",\"stationkey\",\"chargepointkey\",\"pricingmodelkey\",\"startdatetimekey\",\"enddatetimekey\",\"bookingkey\",\"paymentkey\",\"powerconsumed\",\"startdatetime\",\"enddatetime\",\"isinterrupted\",\"interruptedreasonid\",\"vehicleid\",\"stationid\",\"chargepointid\",\"pricingmodelid\",\"bookingid\",\"StartDate\",\"EndDate\",\"StartHour\",\"EndHour\",\"isPeakHour\",\"ActualCost\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				String insert_tDBOutput_1 = sb_tDBOutput_1.toString();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Executing '") + (insert_tDBOutput_1) + ("'."));

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tDBOutput_1 main ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"chargingsessionfact\"";

				tos_count_tDBOutput_1++;

				/**
				 * [tDBOutput_1 main ] stop
				 */

				/**
				 * [tDBOutput_1 process_data_begin ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"chargingsessionfact\"";

				/**
				 * [tDBOutput_1 process_data_begin ] stop
				 */

				/**
				 * [tDBOutput_1 process_data_end ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"chargingsessionfact\"";

				/**
				 * [tDBOutput_1 process_data_end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"chargingsessionfact\"";

				try {
					int countSum_tDBOutput_1 = 0;
					if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));
					}

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

				} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
					globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
					java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(), sqle_tDBOutput_1 = null;
					String errormessage_tDBOutput_1;
					if (ne_tDBOutput_1 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_1 = new java.sql.SQLException(
								e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(),
								ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
						errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
					} else {
						errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
					}

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					log.error("tDBOutput_1 - " + (errormessage_tDBOutput_1));
					System.err.println(errormessage_tDBOutput_1);

				}

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");
				}
				resourceMap.put("statementClosed_tDBOutput_1", true);
				if (rowsToCommitCount_tDBOutput_1 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Connection starting to commit ")
								+ (rowsToCommitCount_tDBOutput_1) + (" record(s)."));
				}
				conn_tDBOutput_1.commit();
				if (rowsToCommitCount_tDBOutput_1 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Connection commit has succeeded."));
					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Closing the connection to the database."));
				conn_tDBOutput_1.close();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection to the database has closed."));
				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Has ") + ("inserted") + (" ") + (nb_line_inserted_tDBOutput_1)
							+ (" record(s)."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Done."));

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBOutput_1 finally ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"chargingsessionfact\"";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Closing the connection to the database."));
								ctn_tDBOutput_1.close();
								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Connection to the database has closed."));
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								log.error("tDBOutput_1 - " + (errorMessage_tDBOutput_1));
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBOutput_1_SUBPROCESS_STATE", 1);
	}

	public void tDBOutput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBOutput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBOutput_2", "ECUWFe_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBOutput_2 begin ] start
				 */

				sh("tDBOutput_2");

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"chargingsessionfact\"";

				int tos_count_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_2 = new StringBuilder();
							log4jParamters_tDBOutput_2.append("Parameters:");
							log4jParamters_tDBOutput_2.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("HOST" + " = " + "\"34.56.183.144\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("PORT" + " = " + "\"5432\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DBNAME" + " = " + "\"ev_station_dw\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("SCHEMA_DB" + " = " + "\"\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USER" + " = " + "\"postgres\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:AxS8CnT3PBa7jfKaw6g8mRZMi2469agnGWgyhKWUnwPyig==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE" + " = " + "\"chargingsessionfact\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("COMMIT_EVERY" + " = " + "10000");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("SUPPORT_NULL_WHERE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("CONVERT_COLUMN_TABLE_TO_LOWERCASE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_BATCH_SIZE" + " = " + "true");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("UNIFIED_COMPONENTS" + " = " + "tPostgresqlOutput");
							log4jParamters_tDBOutput_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_2 - " + (log4jParamters_tDBOutput_2));
						}
					}
					new BytesLimit65535_tDBOutput_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_2", "\"chargingsessionfact\"", "tPostgresqlOutput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String dbschema_tDBOutput_2 = null;
				dbschema_tDBOutput_2 = "";

				String tableName_tDBOutput_2 = null;
				if (dbschema_tDBOutput_2 == null || dbschema_tDBOutput_2.trim().length() == 0) {
					tableName_tDBOutput_2 = ("chargingsessionfact");
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "\".\"" + ("chargingsessionfact");
				}

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				boolean whetherReject_tDBOutput_2 = false;

				java.sql.Connection conn_tDBOutput_2 = null;
				String dbUser_tDBOutput_2 = null;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Driver ClassName: ") + ("org.postgresql.Driver") + ("."));
				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_2 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/" + "ev_station_dw";
				dbUser_tDBOutput_2 = "postgres";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:SZVkgSRRJKiOHtRal8or45aqXGXXeScpCs95zIG70nS2Cg==");

				String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection attempts to '") + (url_tDBOutput_2)
							+ ("' with the username '") + (dbUser_tDBOutput_2) + ("'."));
				conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2,
						dbPwd_tDBOutput_2);
				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection to '") + (url_tDBOutput_2) + ("' has succeeded."));

				resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);
				conn_tDBOutput_2.setAutoCommit(false);
				int commitEvery_tDBOutput_2 = 10000;
				int commitCounter_tDBOutput_2 = 0;
				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_2.getAutoCommit()) + ("'."));

				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;
				java.lang.StringBuilder sb_tDBOutput_2 = new java.lang.StringBuilder();
				sb_tDBOutput_2.append("INSERT INTO \"").append(tableName_tDBOutput_2).append(
						"\" (\"sessionid\",\"totalkwhconsumed\",\"totalrevenue\",\"sessionduration\",\"peakhoursessionscount\",\"numberofinterruptions\",\"bookingtosessionconversionrate\",\"userkey\",\"vehiclekey\",\"stationkey\",\"chargepointkey\",\"pricingmodelkey\",\"startdatetimekey\",\"enddatetimekey\",\"bookingkey\",\"paymentkey\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				String insert_tDBOutput_2 = sb_tDBOutput_2.toString();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Executing '") + (insert_tDBOutput_2) + ("'."));

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tDBOutput_2 main ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"chargingsessionfact\"";

				tos_count_tDBOutput_2++;

				/**
				 * [tDBOutput_2 main ] stop
				 */

				/**
				 * [tDBOutput_2 process_data_begin ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"chargingsessionfact\"";

				/**
				 * [tDBOutput_2 process_data_begin ] stop
				 */

				/**
				 * [tDBOutput_2 process_data_end ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"chargingsessionfact\"";

				/**
				 * [tDBOutput_2 process_data_end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"chargingsessionfact\"";

				try {
					int countSum_tDBOutput_2 = 0;
					if (pstmt_tDBOutput_2 != null && batchSizeCounter_tDBOutput_2 > 0) {

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));
					}

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

				} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
					globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
					java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(), sqle_tDBOutput_2 = null;
					String errormessage_tDBOutput_2;
					if (ne_tDBOutput_2 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_2 = new java.sql.SQLException(
								e_tDBOutput_2.getMessage() + "\ncaused by: " + ne_tDBOutput_2.getMessage(),
								ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(), ne_tDBOutput_2);
						errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
					} else {
						errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
					}

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					log.error("tDBOutput_2 - " + (errormessage_tDBOutput_2));
					System.err.println(errormessage_tDBOutput_2);

				}

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");
				}
				resourceMap.put("statementClosed_tDBOutput_2", true);
				if (rowsToCommitCount_tDBOutput_2 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_2 - " + ("Connection starting to commit ")
								+ (rowsToCommitCount_tDBOutput_2) + (" record(s)."));
				}
				conn_tDBOutput_2.commit();
				if (rowsToCommitCount_tDBOutput_2 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_2 - " + ("Connection commit has succeeded."));
					rowsToCommitCount_tDBOutput_2 = 0;
				}
				commitCounter_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Closing the connection to the database."));
				conn_tDBOutput_2.close();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection to the database has closed."));
				resourceMap.put("finish_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Has ") + ("inserted") + (" ") + (nb_line_inserted_tDBOutput_2)
							+ (" record(s)."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Done."));

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBOutput_2 finally ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"chargingsessionfact\"";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
						if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_2")) != null) {
							pstmtToClose_tDBOutput_2.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_2") == null) {
						java.sql.Connection ctn_tDBOutput_2 = null;
						if ((ctn_tDBOutput_2 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_2")) != null) {
							try {
								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("Closing the connection to the database."));
								ctn_tDBOutput_2.close();
								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("Connection to the database has closed."));
							} catch (java.sql.SQLException sqlEx_tDBOutput_2) {
								String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :"
										+ sqlEx_tDBOutput_2.getMessage();
								log.error("tDBOutput_2 - " + (errorMessage_tDBOutput_2));
								System.err.println(errorMessage_tDBOutput_2);
							}
						}
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBOutput_2_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				sh("talendJobLog");

				s(currentComponent = "talendJobLog");

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				s(currentComponent = "talendJobLog");

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				s(currentComponent = "talendJobLog");

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final SessionFactLoad SessionFactLoadClass = new SessionFactLoad();

		int exitCode = SessionFactLoadClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'SessionFactLoad' - Done.");
		}

		System.exit(exitCode);
	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20240920_1319-patch"));

	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		final boolean enableCBP = false;
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (!inOSGi) {
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() == null) {
				try {
					org.talend.metrics.CBPClient.startListenIfNotStarted(enableCBP, true);
				} catch (java.lang.Exception e) {
					errorCode = 1;
					status = "failure";
					e.printStackTrace();
					return 1;
				}
			}
		}

		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}

		getjobInfo();
		log.info("TalendJob: 'SessionFactLoad' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_z6ecsJyAEe-rSfekVieUmA");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-11-07T02:21:28.542492Z");

		java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
		String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
		if (mxNameTable.length == 2) {
			org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
		} else {
			org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
		}

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}
		org.slf4j.MDC.put("_fatherPid", fatherPid);

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		try {
			java.util.Dictionary<String, Object> jobProperties = null;
			if (inOSGi) {
				jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

				if (jobProperties != null && jobProperties.get("context") != null) {
					contextStr = (String) jobProperties.get("context");
				}

				if (jobProperties != null && jobProperties.get("taskExecutionId") != null) {
					taskExecutionId = (String) jobProperties.get("taskExecutionId");
				}

				// extract ids from parent route
				if (null == taskExecutionId || taskExecutionId.isEmpty()) {
					for (String arg : args) {
						if (arg.startsWith("--context_param")
								&& (arg.contains("taskExecutionId") || arg.contains("jobExecutionId"))) {

							String keyValue = arg.replace("--context_param", "");
							String[] parts = keyValue.split("=");
							String[] cleanParts = java.util.Arrays.stream(parts).filter(s -> !s.isEmpty())
									.toArray(String[]::new);
							if (cleanParts.length == 2) {
								String key = cleanParts[0];
								String value = cleanParts[1];
								if ("taskExecutionId".equals(key.trim()) && null != value) {
									taskExecutionId = value.trim();
								} else if ("jobExecutionId".equals(key.trim()) && null != value) {
									jobExecutionId = value.trim();
								}
							}
						}
					}
				}
			}

			// first load default key-value pairs from application.properties
			if (isStandaloneMS) {
				context.putAll(this.getDefaultProperties());
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = SessionFactLoad.class.getClassLoader().getResourceAsStream(
					"ie6750_project1_grouph/sessionfactload_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = SessionFactLoad.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						if (inOSGi && jobProperties != null) {
							java.util.Enumeration<String> keys = jobProperties.keys();
							while (keys.hasMoreElements()) {
								String propKey = keys.nextElement();
								if (defaultProps.containsKey(propKey)) {
									defaultProps.put(propKey, (String) jobProperties.get(propKey));
								}
							}
						}
						context = new ContextProperties(defaultProps);
					}
					if (isStandaloneMS) {
						// override context key-value pairs if provided using --context=contextName
						defaultProps.load(inContext);
						context.putAll(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}
			// override key-value pairs if provided via --config.location=file1.file2 OR
			// --config.additional-location=file1,file2
			if (isStandaloneMS) {
				context.putAll(this.getAdditionalProperties());
			}

			// override key-value pairs if provide via command line like
			// --key1=value1,--key2=value2
			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'SessionFactLoad' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBConnection_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBConnection_1) {
			globalMap.put("tDBConnection_1_SUBPROCESS_STATE", -1);

			e_tDBConnection_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println(
					(endUsedMemory - startUsedMemory) + " bytes memory increase when running : SessionFactLoad");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		if (!inOSGi) {
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() != null) {
				s("none");
				org.talend.metrics.CBPClient.getInstanceForCurrentVM().sendData();
			}
		}

		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'SessionFactLoad' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

		closeSqlDbConnections();

	}

	private void closeSqlDbConnections() {
		try {
			Object obj_conn;
			obj_conn = globalMap.remove("conn_tDBConnection_1");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
			obj_conn = globalMap.remove("conn_tDBConnection_2");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
		} catch (java.lang.Exception e) {
		}
	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
		connections.put("conn_tDBConnection_1", globalMap.get("conn_tDBConnection_1"));
		connections.put("conn_tDBConnection_2", globalMap.get("conn_tDBConnection_2"));

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--context_file")) {
			String keyValue = arg.substring(15);
			String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
			java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
			try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					int index = -1;
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
							context_param.setContextType(line.substring(15, index), line.substring(index + 1));
						}
					}
				}
			} catch (java.io.IOException e) {
				System.err.println("Could not load the context file: " + filePath);
				e.printStackTrace();
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 952657 characters generated by Talend Real-time Big Data Platform on the 7
 * November 2024 at 03:21:28 CET
 ************************************************************************************************/