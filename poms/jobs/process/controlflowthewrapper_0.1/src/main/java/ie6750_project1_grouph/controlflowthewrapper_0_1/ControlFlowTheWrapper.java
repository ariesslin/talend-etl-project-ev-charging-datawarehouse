
package ie6750_project1_grouph.controlflowthewrapper_0_1;

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
 * Job: ControlFlowTheWrapper Purpose: <br>
 * Description: <br>
 * 
 * @author lin.shan1@northeastern.edu
 * @version 8.0.1.20240920_1319-patch
 * @status
 */
public class ControlFlowTheWrapper implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "ControlFlowTheWrapper.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(ControlFlowTheWrapper.class);

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

	private Object[] multiThreadLockWrite = new Object[0];

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
	private final String jobName = "ControlFlowTheWrapper";
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

	private final java.util.Map<String, Object> globalMap = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Object>());

	private final java.util.Map<String, Long> start_Hash = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Long>());
	private final java.util.Map<String, Long> end_Hash = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Long>());
	private final java.util.Map<String, Boolean> ok_Hash = java.util.Collections
			.synchronizedMap(new java.util.HashMap<String, Boolean>());
	public final java.util.List<String[]> globalBuffer = java.util.Collections
			.synchronizedList(new java.util.ArrayList<String[]>());

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_tLKekJvcEe-5PpigxqfS-w", "0.1");
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
					ControlFlowTheWrapper.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(ControlFlowTheWrapper.this, new Object[] { e, currentComponent, globalMap });
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

	public void tDBConnection_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tDBConnection_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_9_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_6_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_13_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_13_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_11_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_14_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_14_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tParallelize_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tParallelize_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_12_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_12_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_7_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tRunJob_10_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBCommit_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		((java.util.Map) threadLocal.get()).put("status", "failure");

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
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

	public void tRunJob_9_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_5_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_6_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_13_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_11_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_14_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tParallelize_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_12_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_8_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_7_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_10_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBCommit_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBConnection_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBConnection_1", "wiF88Y_");

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
									"enc:routine.encryption.key.v1:ht8ICMac3aCRA15VdG5qgPsJaYITtf5DkcnpfRDMEY3XaQ==")
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
						"enc:routine.encryption.key.v1:IOMtF9IMjDBD+kAgKmeDOzEbWy/KRRRV1pfT7yM2X3ZyZQ==");
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

		mdc("tDBConnection_2", "yGLvdA_");

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
									"enc:routine.encryption.key.v1:gSXRw9NfxZXEdMU39elliNCM4pSa6Y3rJ/YTaKLLuHrP3A==")
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
						"enc:routine.encryption.key.v1:h1N7SJ6KYzmxGEraLvcMqLbP/UZcOtXae/zPs/Cdutl0Dg==");
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

			tRunJob_9Process(globalMap);

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

	public void tRunJob_9Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_9_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_9", "rImPmF_");

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
				 * [tRunJob_9 begin ] start
				 */

				sh("tRunJob_9");

				s(currentComponent = "tRunJob_9");

				cLabel = "RegionLoad";

				int tos_count_tRunJob_9 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_9 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_9 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_9 = new StringBuilder();
							log4jParamters_tRunJob_9.append("Parameters:");
							log4jParamters_tRunJob_9.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("PROCESS" + " = " + "RegionLoad");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_9.append(" | ");
							log4jParamters_tRunJob_9.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_9.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_9 - " + (log4jParamters_tRunJob_9));
						}
					}
					new BytesLimit65535_tRunJob_9().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_9", "RegionLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_9 begin ] stop
				 */

				/**
				 * [tRunJob_9 main ] start
				 */

				s(currentComponent = "tRunJob_9");

				cLabel = "RegionLoad";

				java.util.List<String> paraList_tRunJob_9 = new java.util.ArrayList<String>();

				paraList_tRunJob_9.add("--father_pid=" + pid);

				paraList_tRunJob_9.add("--root_pid=" + rootPid);

				paraList_tRunJob_9.add("--father_node=tRunJob_9");

				paraList_tRunJob_9.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_9.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_9.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_9.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_9.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_9 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_9 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_9".equals(tRunJobName_tRunJob_9) && childResumePath_tRunJob_9 != null) {
					paraList_tRunJob_9.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_9.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_9");

				java.util.Map<String, Object> parentContextMap_tRunJob_9 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_9 = null;

				ie6750_project1_grouph.regionload_0_1.RegionLoad childJob_tRunJob_9 = new ie6750_project1_grouph.regionload_0_1.RegionLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_9 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_9) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_9 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_9 : talendDataSources_tRunJob_9
							.entrySet()) {
						dataSources_tRunJob_9.put(talendDataSourceEntry_tRunJob_9.getKey(),
								talendDataSourceEntry_tRunJob_9.getValue().getRawDataSource());
					}
					childJob_tRunJob_9.setDataSources(dataSources_tRunJob_9);
				}

				childJob_tRunJob_9.parentContextMap = parentContextMap_tRunJob_9;

				log.info(
						"tRunJob_9 - The child job 'ie6750_project1_grouph.regionload_0_1.RegionLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_9 = childJob_tRunJob_9
						.runJob((String[]) paraList_tRunJob_9.toArray(new String[paraList_tRunJob_9.size()]));

				log.info("tRunJob_9 - The child job 'ie6750_project1_grouph.regionload_0_1.RegionLoad' is done.");

				if (childJob_tRunJob_9.getErrorCode() == null) {
					globalMap.put("tRunJob_9_CHILD_RETURN_CODE",
							childJob_tRunJob_9.getStatus() != null && ("failure").equals(childJob_tRunJob_9.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_9_CHILD_RETURN_CODE", childJob_tRunJob_9.getErrorCode());
				}
				if (childJob_tRunJob_9.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_9_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_9.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_9.getErrorCode());
				if (childJob_tRunJob_9.getErrorCode() != null || ("failure").equals(childJob_tRunJob_9.getStatus())) {
					java.lang.Exception ce_tRunJob_9 = childJob_tRunJob_9.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_9 != null)
							? (ce_tRunJob_9.getClass().getName() + ": " + ce_tRunJob_9.getMessage())
							: ""));
				}

				tos_count_tRunJob_9++;

				/**
				 * [tRunJob_9 main ] stop
				 */

				/**
				 * [tRunJob_9 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_9");

				cLabel = "RegionLoad";

				/**
				 * [tRunJob_9 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_9 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_9");

				cLabel = "RegionLoad";

				/**
				 * [tRunJob_9 process_data_end ] stop
				 */

				/**
				 * [tRunJob_9 end ] start
				 */

				s(currentComponent = "tRunJob_9");

				cLabel = "RegionLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_9 - " + ("Done."));

				ok_Hash.put("tRunJob_9", true);
				end_Hash.put("tRunJob_9", System.currentTimeMillis());

				/**
				 * [tRunJob_9 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_9:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk4", 0, "ok");
			}

			tRunJob_5Process(globalMap);

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
				 * [tRunJob_9 finally ] start
				 */

				s(currentComponent = "tRunJob_9");

				cLabel = "RegionLoad";

				/**
				 * [tRunJob_9 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_9_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_5", "rh9N57_");

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
				 * [tRunJob_5 begin ] start
				 */

				sh("tRunJob_5");

				s(currentComponent = "tRunJob_5");

				cLabel = "CityLoad";

				int tos_count_tRunJob_5 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_5 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_5 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_5 = new StringBuilder();
							log4jParamters_tRunJob_5.append("Parameters:");
							log4jParamters_tRunJob_5.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("PROCESS" + " = " + "CityLoad");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_5.append(" | ");
							log4jParamters_tRunJob_5.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_5.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_5 - " + (log4jParamters_tRunJob_5));
						}
					}
					new BytesLimit65535_tRunJob_5().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_5", "CityLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_5 begin ] stop
				 */

				/**
				 * [tRunJob_5 main ] start
				 */

				s(currentComponent = "tRunJob_5");

				cLabel = "CityLoad";

				java.util.List<String> paraList_tRunJob_5 = new java.util.ArrayList<String>();

				paraList_tRunJob_5.add("--father_pid=" + pid);

				paraList_tRunJob_5.add("--root_pid=" + rootPid);

				paraList_tRunJob_5.add("--father_node=tRunJob_5");

				paraList_tRunJob_5.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_5.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_5.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_5.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_5.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_5 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_5 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_5".equals(tRunJobName_tRunJob_5) && childResumePath_tRunJob_5 != null) {
					paraList_tRunJob_5.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_5.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_5");

				java.util.Map<String, Object> parentContextMap_tRunJob_5 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_5 = null;

				ie6750_project1_grouph.cityload_0_1.CityLoad childJob_tRunJob_5 = new ie6750_project1_grouph.cityload_0_1.CityLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_5 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_5) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_5 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_5 : talendDataSources_tRunJob_5
							.entrySet()) {
						dataSources_tRunJob_5.put(talendDataSourceEntry_tRunJob_5.getKey(),
								talendDataSourceEntry_tRunJob_5.getValue().getRawDataSource());
					}
					childJob_tRunJob_5.setDataSources(dataSources_tRunJob_5);
				}

				childJob_tRunJob_5.parentContextMap = parentContextMap_tRunJob_5;

				log.info(
						"tRunJob_5 - The child job 'ie6750_project1_grouph.cityload_0_1.CityLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_5 = childJob_tRunJob_5
						.runJob((String[]) paraList_tRunJob_5.toArray(new String[paraList_tRunJob_5.size()]));

				log.info("tRunJob_5 - The child job 'ie6750_project1_grouph.cityload_0_1.CityLoad' is done.");

				if (childJob_tRunJob_5.getErrorCode() == null) {
					globalMap.put("tRunJob_5_CHILD_RETURN_CODE",
							childJob_tRunJob_5.getStatus() != null && ("failure").equals(childJob_tRunJob_5.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_5_CHILD_RETURN_CODE", childJob_tRunJob_5.getErrorCode());
				}
				if (childJob_tRunJob_5.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_5_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_5.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_5.getErrorCode());
				if (childJob_tRunJob_5.getErrorCode() != null || ("failure").equals(childJob_tRunJob_5.getStatus())) {
					java.lang.Exception ce_tRunJob_5 = childJob_tRunJob_5.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_5 != null)
							? (ce_tRunJob_5.getClass().getName() + ": " + ce_tRunJob_5.getMessage())
							: ""));
				}

				tos_count_tRunJob_5++;

				/**
				 * [tRunJob_5 main ] stop
				 */

				/**
				 * [tRunJob_5 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_5");

				cLabel = "CityLoad";

				/**
				 * [tRunJob_5 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_5 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_5");

				cLabel = "CityLoad";

				/**
				 * [tRunJob_5 process_data_end ] stop
				 */

				/**
				 * [tRunJob_5 end ] start
				 */

				s(currentComponent = "tRunJob_5");

				cLabel = "CityLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_5 - " + ("Done."));

				ok_Hash.put("tRunJob_5", true);
				end_Hash.put("tRunJob_5", System.currentTimeMillis());

				/**
				 * [tRunJob_5 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_5:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk5", 0, "ok");
			}

			tRunJob_6Process(globalMap);

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
				 * [tRunJob_5 finally ] start
				 */

				s(currentComponent = "tRunJob_5");

				cLabel = "CityLoad";

				/**
				 * [tRunJob_5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_5_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_6_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_6", "NMi6Ps_");

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
				 * [tRunJob_6 begin ] start
				 */

				sh("tRunJob_6");

				s(currentComponent = "tRunJob_6");

				cLabel = "EVUserLoad";

				int tos_count_tRunJob_6 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_6 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_6 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_6 = new StringBuilder();
							log4jParamters_tRunJob_6.append("Parameters:");
							log4jParamters_tRunJob_6.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("PROCESS" + " = " + "EVUserLoad");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_6.append(" | ");
							log4jParamters_tRunJob_6.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_6.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_6 - " + (log4jParamters_tRunJob_6));
						}
					}
					new BytesLimit65535_tRunJob_6().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_6", "EVUserLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_6 begin ] stop
				 */

				/**
				 * [tRunJob_6 main ] start
				 */

				s(currentComponent = "tRunJob_6");

				cLabel = "EVUserLoad";

				java.util.List<String> paraList_tRunJob_6 = new java.util.ArrayList<String>();

				paraList_tRunJob_6.add("--father_pid=" + pid);

				paraList_tRunJob_6.add("--root_pid=" + rootPid);

				paraList_tRunJob_6.add("--father_node=tRunJob_6");

				paraList_tRunJob_6.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_6.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_6.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_6.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_6.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_6 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_6 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_6".equals(tRunJobName_tRunJob_6) && childResumePath_tRunJob_6 != null) {
					paraList_tRunJob_6.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_6.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_6");

				java.util.Map<String, Object> parentContextMap_tRunJob_6 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_6 = null;

				ie6750_project1_grouph.evuserload_0_1.EVUserLoad childJob_tRunJob_6 = new ie6750_project1_grouph.evuserload_0_1.EVUserLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_6 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_6) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_6 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_6 : talendDataSources_tRunJob_6
							.entrySet()) {
						dataSources_tRunJob_6.put(talendDataSourceEntry_tRunJob_6.getKey(),
								talendDataSourceEntry_tRunJob_6.getValue().getRawDataSource());
					}
					childJob_tRunJob_6.setDataSources(dataSources_tRunJob_6);
				}

				childJob_tRunJob_6.parentContextMap = parentContextMap_tRunJob_6;

				log.info(
						"tRunJob_6 - The child job 'ie6750_project1_grouph.evuserload_0_1.EVUserLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_6 = childJob_tRunJob_6
						.runJob((String[]) paraList_tRunJob_6.toArray(new String[paraList_tRunJob_6.size()]));

				log.info("tRunJob_6 - The child job 'ie6750_project1_grouph.evuserload_0_1.EVUserLoad' is done.");

				if (childJob_tRunJob_6.getErrorCode() == null) {
					globalMap.put("tRunJob_6_CHILD_RETURN_CODE",
							childJob_tRunJob_6.getStatus() != null && ("failure").equals(childJob_tRunJob_6.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_6_CHILD_RETURN_CODE", childJob_tRunJob_6.getErrorCode());
				}
				if (childJob_tRunJob_6.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_6_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_6.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_6.getErrorCode());
				if (childJob_tRunJob_6.getErrorCode() != null || ("failure").equals(childJob_tRunJob_6.getStatus())) {
					java.lang.Exception ce_tRunJob_6 = childJob_tRunJob_6.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_6 != null)
							? (ce_tRunJob_6.getClass().getName() + ": " + ce_tRunJob_6.getMessage())
							: ""));
				}

				tos_count_tRunJob_6++;

				/**
				 * [tRunJob_6 main ] stop
				 */

				/**
				 * [tRunJob_6 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_6");

				cLabel = "EVUserLoad";

				/**
				 * [tRunJob_6 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_6 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_6");

				cLabel = "EVUserLoad";

				/**
				 * [tRunJob_6 process_data_end ] stop
				 */

				/**
				 * [tRunJob_6 end ] start
				 */

				s(currentComponent = "tRunJob_6");

				cLabel = "EVUserLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_6 - " + ("Done."));

				ok_Hash.put("tRunJob_6", true);
				end_Hash.put("tRunJob_6", System.currentTimeMillis());

				/**
				 * [tRunJob_6 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_6:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk6", 0, "ok");
			}

			tRunJob_13Process(globalMap);

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
				 * [tRunJob_6 finally ] start
				 */

				s(currentComponent = "tRunJob_6");

				cLabel = "EVUserLoad";

				/**
				 * [tRunJob_6 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_6_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_13Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_13_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_13", "MWhDFN_");

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
				 * [tRunJob_13 begin ] start
				 */

				sh("tRunJob_13");

				s(currentComponent = "tRunJob_13");

				cLabel = "VechicleLoad";

				int tos_count_tRunJob_13 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_13 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_13 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_13 = new StringBuilder();
							log4jParamters_tRunJob_13.append("Parameters:");
							log4jParamters_tRunJob_13.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("PROCESS" + " = " + "VechicleLoad");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_13.append(" | ");
							log4jParamters_tRunJob_13.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_13.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_13 - " + (log4jParamters_tRunJob_13));
						}
					}
					new BytesLimit65535_tRunJob_13().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_13", "VechicleLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_13 begin ] stop
				 */

				/**
				 * [tRunJob_13 main ] start
				 */

				s(currentComponent = "tRunJob_13");

				cLabel = "VechicleLoad";

				java.util.List<String> paraList_tRunJob_13 = new java.util.ArrayList<String>();

				paraList_tRunJob_13.add("--father_pid=" + pid);

				paraList_tRunJob_13.add("--root_pid=" + rootPid);

				paraList_tRunJob_13.add("--father_node=tRunJob_13");

				paraList_tRunJob_13.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_13.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_13.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_13.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_13.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_13 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_13 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_13".equals(tRunJobName_tRunJob_13) && childResumePath_tRunJob_13 != null) {
					paraList_tRunJob_13.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_13.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_13");

				java.util.Map<String, Object> parentContextMap_tRunJob_13 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_13 = null;

				ie6750_project1_grouph.vechicleload_0_1.VechicleLoad childJob_tRunJob_13 = new ie6750_project1_grouph.vechicleload_0_1.VechicleLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_13 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_13) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_13 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_13 : talendDataSources_tRunJob_13
							.entrySet()) {
						dataSources_tRunJob_13.put(talendDataSourceEntry_tRunJob_13.getKey(),
								talendDataSourceEntry_tRunJob_13.getValue().getRawDataSource());
					}
					childJob_tRunJob_13.setDataSources(dataSources_tRunJob_13);
				}

				childJob_tRunJob_13.parentContextMap = parentContextMap_tRunJob_13;

				log.info(
						"tRunJob_13 - The child job 'ie6750_project1_grouph.vechicleload_0_1.VechicleLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_13 = childJob_tRunJob_13
						.runJob((String[]) paraList_tRunJob_13.toArray(new String[paraList_tRunJob_13.size()]));

				log.info("tRunJob_13 - The child job 'ie6750_project1_grouph.vechicleload_0_1.VechicleLoad' is done.");

				if (childJob_tRunJob_13.getErrorCode() == null) {
					globalMap.put("tRunJob_13_CHILD_RETURN_CODE", childJob_tRunJob_13.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_13.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_13_CHILD_RETURN_CODE", childJob_tRunJob_13.getErrorCode());
				}
				if (childJob_tRunJob_13.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_13_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_13.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_13.getErrorCode());
				if (childJob_tRunJob_13.getErrorCode() != null || ("failure").equals(childJob_tRunJob_13.getStatus())) {
					java.lang.Exception ce_tRunJob_13 = childJob_tRunJob_13.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_13 != null)
							? (ce_tRunJob_13.getClass().getName() + ": " + ce_tRunJob_13.getMessage())
							: ""));
				}

				tos_count_tRunJob_13++;

				/**
				 * [tRunJob_13 main ] stop
				 */

				/**
				 * [tRunJob_13 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_13");

				cLabel = "VechicleLoad";

				/**
				 * [tRunJob_13 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_13 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_13");

				cLabel = "VechicleLoad";

				/**
				 * [tRunJob_13 process_data_end ] stop
				 */

				/**
				 * [tRunJob_13 end ] start
				 */

				s(currentComponent = "tRunJob_13");

				cLabel = "VechicleLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_13 - " + ("Done."));

				ok_Hash.put("tRunJob_13", true);
				end_Hash.put("tRunJob_13", System.currentTimeMillis());

				/**
				 * [tRunJob_13 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_13:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk7", 0, "ok");
			}

			tRunJob_11Process(globalMap);

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
				 * [tRunJob_13 finally ] start
				 */

				s(currentComponent = "tRunJob_13");

				cLabel = "VechicleLoad";

				/**
				 * [tRunJob_13 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_13_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_11Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_11_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_11", "mGJp9E_");

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
				 * [tRunJob_11 begin ] start
				 */

				sh("tRunJob_11");

				s(currentComponent = "tRunJob_11");

				cLabel = "StationStatusLoad";

				int tos_count_tRunJob_11 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_11 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_11 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_11 = new StringBuilder();
							log4jParamters_tRunJob_11.append("Parameters:");
							log4jParamters_tRunJob_11.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("PROCESS" + " = " + "StationStatusLoad");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_11.append(" | ");
							log4jParamters_tRunJob_11.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_11.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_11 - " + (log4jParamters_tRunJob_11));
						}
					}
					new BytesLimit65535_tRunJob_11().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_11", "StationStatusLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_11 begin ] stop
				 */

				/**
				 * [tRunJob_11 main ] start
				 */

				s(currentComponent = "tRunJob_11");

				cLabel = "StationStatusLoad";

				java.util.List<String> paraList_tRunJob_11 = new java.util.ArrayList<String>();

				paraList_tRunJob_11.add("--father_pid=" + pid);

				paraList_tRunJob_11.add("--root_pid=" + rootPid);

				paraList_tRunJob_11.add("--father_node=tRunJob_11");

				paraList_tRunJob_11.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_11.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_11.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_11.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_11.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_11 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_11 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_11".equals(tRunJobName_tRunJob_11) && childResumePath_tRunJob_11 != null) {
					paraList_tRunJob_11.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_11.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_11");

				java.util.Map<String, Object> parentContextMap_tRunJob_11 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_11 = null;

				ie6750_project1_grouph.stationstatusload_0_1.StationStatusLoad childJob_tRunJob_11 = new ie6750_project1_grouph.stationstatusload_0_1.StationStatusLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_11 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_11) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_11 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_11 : talendDataSources_tRunJob_11
							.entrySet()) {
						dataSources_tRunJob_11.put(talendDataSourceEntry_tRunJob_11.getKey(),
								talendDataSourceEntry_tRunJob_11.getValue().getRawDataSource());
					}
					childJob_tRunJob_11.setDataSources(dataSources_tRunJob_11);
				}

				childJob_tRunJob_11.parentContextMap = parentContextMap_tRunJob_11;

				log.info(
						"tRunJob_11 - The child job 'ie6750_project1_grouph.stationstatusload_0_1.StationStatusLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_11 = childJob_tRunJob_11
						.runJob((String[]) paraList_tRunJob_11.toArray(new String[paraList_tRunJob_11.size()]));

				log.info(
						"tRunJob_11 - The child job 'ie6750_project1_grouph.stationstatusload_0_1.StationStatusLoad' is done.");

				if (childJob_tRunJob_11.getErrorCode() == null) {
					globalMap.put("tRunJob_11_CHILD_RETURN_CODE", childJob_tRunJob_11.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_11.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_11_CHILD_RETURN_CODE", childJob_tRunJob_11.getErrorCode());
				}
				if (childJob_tRunJob_11.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_11_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_11.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_11.getErrorCode());
				if (childJob_tRunJob_11.getErrorCode() != null || ("failure").equals(childJob_tRunJob_11.getStatus())) {
					java.lang.Exception ce_tRunJob_11 = childJob_tRunJob_11.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_11 != null)
							? (ce_tRunJob_11.getClass().getName() + ": " + ce_tRunJob_11.getMessage())
							: ""));
				}

				tos_count_tRunJob_11++;

				/**
				 * [tRunJob_11 main ] stop
				 */

				/**
				 * [tRunJob_11 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_11");

				cLabel = "StationStatusLoad";

				/**
				 * [tRunJob_11 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_11 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_11");

				cLabel = "StationStatusLoad";

				/**
				 * [tRunJob_11 process_data_end ] stop
				 */

				/**
				 * [tRunJob_11 end ] start
				 */

				s(currentComponent = "tRunJob_11");

				cLabel = "StationStatusLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_11 - " + ("Done."));

				ok_Hash.put("tRunJob_11", true);
				end_Hash.put("tRunJob_11", System.currentTimeMillis());

				/**
				 * [tRunJob_11 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_11:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk8", 0, "ok");
			}

			tRunJob_14Process(globalMap);

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
				 * [tRunJob_11 finally ] start
				 */

				s(currentComponent = "tRunJob_11");

				cLabel = "StationStatusLoad";

				/**
				 * [tRunJob_11 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_11_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_14Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_14_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_14", "nyhqAh_");

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
				 * [tRunJob_14 begin ] start
				 */

				sh("tRunJob_14");

				s(currentComponent = "tRunJob_14");

				cLabel = "ChargingStationSCDType2Load";

				int tos_count_tRunJob_14 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_14 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_14 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_14 = new StringBuilder();
							log4jParamters_tRunJob_14.append("Parameters:");
							log4jParamters_tRunJob_14.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("PROCESS" + " = " + "ChargingStationSCDType2Load");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_14.append(" | ");
							log4jParamters_tRunJob_14.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_14.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_14 - " + (log4jParamters_tRunJob_14));
						}
					}
					new BytesLimit65535_tRunJob_14().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_14", "ChargingStationSCDType2Load", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_14 begin ] stop
				 */

				/**
				 * [tRunJob_14 main ] start
				 */

				s(currentComponent = "tRunJob_14");

				cLabel = "ChargingStationSCDType2Load";

				java.util.List<String> paraList_tRunJob_14 = new java.util.ArrayList<String>();

				paraList_tRunJob_14.add("--father_pid=" + pid);

				paraList_tRunJob_14.add("--root_pid=" + rootPid);

				paraList_tRunJob_14.add("--father_node=tRunJob_14");

				paraList_tRunJob_14.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_14.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_14.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_14.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_14.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_14 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_14 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_14".equals(tRunJobName_tRunJob_14) && childResumePath_tRunJob_14 != null) {
					paraList_tRunJob_14.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_14.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_14");

				java.util.Map<String, Object> parentContextMap_tRunJob_14 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_14 = null;

				ie6750_project1_grouph.chargingstationscdtype2load_0_1.ChargingStationSCDType2Load childJob_tRunJob_14 = new ie6750_project1_grouph.chargingstationscdtype2load_0_1.ChargingStationSCDType2Load();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_14 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_14) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_14 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_14 : talendDataSources_tRunJob_14
							.entrySet()) {
						dataSources_tRunJob_14.put(talendDataSourceEntry_tRunJob_14.getKey(),
								talendDataSourceEntry_tRunJob_14.getValue().getRawDataSource());
					}
					childJob_tRunJob_14.setDataSources(dataSources_tRunJob_14);
				}

				childJob_tRunJob_14.parentContextMap = parentContextMap_tRunJob_14;

				log.info(
						"tRunJob_14 - The child job 'ie6750_project1_grouph.chargingstationscdtype2load_0_1.ChargingStationSCDType2Load' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_14 = childJob_tRunJob_14
						.runJob((String[]) paraList_tRunJob_14.toArray(new String[paraList_tRunJob_14.size()]));

				log.info(
						"tRunJob_14 - The child job 'ie6750_project1_grouph.chargingstationscdtype2load_0_1.ChargingStationSCDType2Load' is done.");

				if (childJob_tRunJob_14.getErrorCode() == null) {
					globalMap.put("tRunJob_14_CHILD_RETURN_CODE", childJob_tRunJob_14.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_14.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_14_CHILD_RETURN_CODE", childJob_tRunJob_14.getErrorCode());
				}
				if (childJob_tRunJob_14.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_14_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_14.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_14.getErrorCode());
				if (childJob_tRunJob_14.getErrorCode() != null || ("failure").equals(childJob_tRunJob_14.getStatus())) {
					java.lang.Exception ce_tRunJob_14 = childJob_tRunJob_14.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_14 != null)
							? (ce_tRunJob_14.getClass().getName() + ": " + ce_tRunJob_14.getMessage())
							: ""));
				}

				tos_count_tRunJob_14++;

				/**
				 * [tRunJob_14 main ] stop
				 */

				/**
				 * [tRunJob_14 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_14");

				cLabel = "ChargingStationSCDType2Load";

				/**
				 * [tRunJob_14 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_14 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_14");

				cLabel = "ChargingStationSCDType2Load";

				/**
				 * [tRunJob_14 process_data_end ] stop
				 */

				/**
				 * [tRunJob_14 end ] start
				 */

				s(currentComponent = "tRunJob_14");

				cLabel = "ChargingStationSCDType2Load";

				if (log.isDebugEnabled())
					log.debug("tRunJob_14 - " + ("Done."));

				ok_Hash.put("tRunJob_14", true);
				end_Hash.put("tRunJob_14", System.currentTimeMillis());

				/**
				 * [tRunJob_14 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_14:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk9", 0, "ok");
			}

			tRunJob_2Process(globalMap);

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
				 * [tRunJob_14 finally ] start
				 */

				s(currentComponent = "tRunJob_14");

				cLabel = "ChargingStationSCDType2Load";

				/**
				 * [tRunJob_14 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_14_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_2", "H9LVGO_");

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
				 * [tRunJob_2 begin ] start
				 */

				sh("tRunJob_2");

				s(currentComponent = "tRunJob_2");

				cLabel = "ChargingPointStatusLoad";

				int tos_count_tRunJob_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_2 = new StringBuilder();
							log4jParamters_tRunJob_2.append("Parameters:");
							log4jParamters_tRunJob_2.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("PROCESS" + " = " + "ChargingPointStatusLoad");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_2.append(" | ");
							log4jParamters_tRunJob_2.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_2 - " + (log4jParamters_tRunJob_2));
						}
					}
					new BytesLimit65535_tRunJob_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_2", "ChargingPointStatusLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_2 begin ] stop
				 */

				/**
				 * [tRunJob_2 main ] start
				 */

				s(currentComponent = "tRunJob_2");

				cLabel = "ChargingPointStatusLoad";

				java.util.List<String> paraList_tRunJob_2 = new java.util.ArrayList<String>();

				paraList_tRunJob_2.add("--father_pid=" + pid);

				paraList_tRunJob_2.add("--root_pid=" + rootPid);

				paraList_tRunJob_2.add("--father_node=tRunJob_2");

				paraList_tRunJob_2.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_2.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_2.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_2.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_2.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_2 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_2 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_2".equals(tRunJobName_tRunJob_2) && childResumePath_tRunJob_2 != null) {
					paraList_tRunJob_2.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_2.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_2");

				java.util.Map<String, Object> parentContextMap_tRunJob_2 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_2 = null;

				ie6750_project1_grouph.chargingpointstatusload_0_1.ChargingPointStatusLoad childJob_tRunJob_2 = new ie6750_project1_grouph.chargingpointstatusload_0_1.ChargingPointStatusLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_2 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_2) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_2 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_2 : talendDataSources_tRunJob_2
							.entrySet()) {
						dataSources_tRunJob_2.put(talendDataSourceEntry_tRunJob_2.getKey(),
								talendDataSourceEntry_tRunJob_2.getValue().getRawDataSource());
					}
					childJob_tRunJob_2.setDataSources(dataSources_tRunJob_2);
				}

				childJob_tRunJob_2.parentContextMap = parentContextMap_tRunJob_2;

				log.info(
						"tRunJob_2 - The child job 'ie6750_project1_grouph.chargingpointstatusload_0_1.ChargingPointStatusLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_2 = childJob_tRunJob_2
						.runJob((String[]) paraList_tRunJob_2.toArray(new String[paraList_tRunJob_2.size()]));

				log.info(
						"tRunJob_2 - The child job 'ie6750_project1_grouph.chargingpointstatusload_0_1.ChargingPointStatusLoad' is done.");

				if (childJob_tRunJob_2.getErrorCode() == null) {
					globalMap.put("tRunJob_2_CHILD_RETURN_CODE",
							childJob_tRunJob_2.getStatus() != null && ("failure").equals(childJob_tRunJob_2.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_2_CHILD_RETURN_CODE", childJob_tRunJob_2.getErrorCode());
				}
				if (childJob_tRunJob_2.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_2_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_2.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_2.getErrorCode());
				if (childJob_tRunJob_2.getErrorCode() != null || ("failure").equals(childJob_tRunJob_2.getStatus())) {
					java.lang.Exception ce_tRunJob_2 = childJob_tRunJob_2.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_2 != null)
							? (ce_tRunJob_2.getClass().getName() + ": " + ce_tRunJob_2.getMessage())
							: ""));
				}

				tos_count_tRunJob_2++;

				/**
				 * [tRunJob_2 main ] stop
				 */

				/**
				 * [tRunJob_2 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_2");

				cLabel = "ChargingPointStatusLoad";

				/**
				 * [tRunJob_2 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_2 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_2");

				cLabel = "ChargingPointStatusLoad";

				/**
				 * [tRunJob_2 process_data_end ] stop
				 */

				/**
				 * [tRunJob_2 end ] start
				 */

				s(currentComponent = "tRunJob_2");

				cLabel = "ChargingPointStatusLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_2 - " + ("Done."));

				ok_Hash.put("tRunJob_2", true);
				end_Hash.put("tRunJob_2", System.currentTimeMillis());

				/**
				 * [tRunJob_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk10", 0, "ok");
			}

			tRunJob_3Process(globalMap);

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
				 * [tRunJob_2 finally ] start
				 */

				s(currentComponent = "tRunJob_2");

				cLabel = "ChargingPointStatusLoad";

				/**
				 * [tRunJob_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_2_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_3", "EWXzyX_");

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
				 * [tRunJob_3 begin ] start
				 */

				sh("tRunJob_3");

				s(currentComponent = "tRunJob_3");

				cLabel = "ChargingPointSCDType3Load";

				int tos_count_tRunJob_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_3 = new StringBuilder();
							log4jParamters_tRunJob_3.append("Parameters:");
							log4jParamters_tRunJob_3.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("PROCESS" + " = " + "ChargingPointSCDType3Load");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_3 - " + (log4jParamters_tRunJob_3));
						}
					}
					new BytesLimit65535_tRunJob_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_3", "ChargingPointSCDType3Load", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_3 begin ] stop
				 */

				/**
				 * [tRunJob_3 main ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "ChargingPointSCDType3Load";

				java.util.List<String> paraList_tRunJob_3 = new java.util.ArrayList<String>();

				paraList_tRunJob_3.add("--father_pid=" + pid);

				paraList_tRunJob_3.add("--root_pid=" + rootPid);

				paraList_tRunJob_3.add("--father_node=tRunJob_3");

				paraList_tRunJob_3.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_3.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_3.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_3.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_3.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_3 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_3 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_3".equals(tRunJobName_tRunJob_3) && childResumePath_tRunJob_3 != null) {
					paraList_tRunJob_3.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_3.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_3");

				java.util.Map<String, Object> parentContextMap_tRunJob_3 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_3 = null;

				ie6750_project1_grouph.chargingpointscdtype3load_0_1.ChargingPointSCDType3Load childJob_tRunJob_3 = new ie6750_project1_grouph.chargingpointscdtype3load_0_1.ChargingPointSCDType3Load();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_3 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_3) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_3 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_3 : talendDataSources_tRunJob_3
							.entrySet()) {
						dataSources_tRunJob_3.put(talendDataSourceEntry_tRunJob_3.getKey(),
								talendDataSourceEntry_tRunJob_3.getValue().getRawDataSource());
					}
					childJob_tRunJob_3.setDataSources(dataSources_tRunJob_3);
				}

				childJob_tRunJob_3.parentContextMap = parentContextMap_tRunJob_3;

				log.info(
						"tRunJob_3 - The child job 'ie6750_project1_grouph.chargingpointscdtype3load_0_1.ChargingPointSCDType3Load' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_3 = childJob_tRunJob_3
						.runJob((String[]) paraList_tRunJob_3.toArray(new String[paraList_tRunJob_3.size()]));

				log.info(
						"tRunJob_3 - The child job 'ie6750_project1_grouph.chargingpointscdtype3load_0_1.ChargingPointSCDType3Load' is done.");

				if (childJob_tRunJob_3.getErrorCode() == null) {
					globalMap.put("tRunJob_3_CHILD_RETURN_CODE",
							childJob_tRunJob_3.getStatus() != null && ("failure").equals(childJob_tRunJob_3.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_3_CHILD_RETURN_CODE", childJob_tRunJob_3.getErrorCode());
				}
				if (childJob_tRunJob_3.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_3_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_3.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_3.getErrorCode());
				if (childJob_tRunJob_3.getErrorCode() != null || ("failure").equals(childJob_tRunJob_3.getStatus())) {
					java.lang.Exception ce_tRunJob_3 = childJob_tRunJob_3.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_3 != null)
							? (ce_tRunJob_3.getClass().getName() + ": " + ce_tRunJob_3.getMessage())
							: ""));
				}

				tos_count_tRunJob_3++;

				/**
				 * [tRunJob_3 main ] stop
				 */

				/**
				 * [tRunJob_3 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "ChargingPointSCDType3Load";

				/**
				 * [tRunJob_3 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_3 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "ChargingPointSCDType3Load";

				/**
				 * [tRunJob_3 process_data_end ] stop
				 */

				/**
				 * [tRunJob_3 end ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "ChargingPointSCDType3Load";

				if (log.isDebugEnabled())
					log.debug("tRunJob_3 - " + ("Done."));

				ok_Hash.put("tRunJob_3", true);
				end_Hash.put("tRunJob_3", System.currentTimeMillis());

				/**
				 * [tRunJob_3 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_3:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk11", 0, "ok");
			}

			tParallelize_1Process(globalMap);

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
				 * [tRunJob_3 finally ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "ChargingPointSCDType3Load";

				/**
				 * [tRunJob_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_3_SUBPROCESS_STATE", 1);
	}

	public void tParallelize_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tParallelize_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tParallelize_1", "A5P0Or_");

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
				 * [tParallelize_1 begin ] start
				 */

				sh("tParallelize_1");

				s(currentComponent = "tParallelize_1");

				int tos_count_tParallelize_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tParallelize_1", "tParallelize_1", "tParallelize");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// call parallelized subjobs
				java.util.Map<String, Thread> mt_tParallelize_1 = new java.util.HashMap<String, Thread>();

// clear the temporary values in the globalMap
				globalMap.remove("tRunJob_12_SUBPROCESS_STATE");
				globalMap.remove("tRunJob_8_SUBPROCESS_STATE");
				globalMap.remove("tRunJob_1_SUBPROCESS_STATE");
				globalMap.remove("tRunJob_7_SUBPROCESS_STATE");

				java.util.Map concurrentMap_temp_tParallelize_1;
				if (globalMap instanceof java.util.HashMap) {
					concurrentMap_temp_tParallelize_1 = java.util.Collections.synchronizedMap(globalMap);
				} else {
					concurrentMap_temp_tParallelize_1 = globalMap;
				}
				final java.util.Map concurrentMap_tParallelize_1 = concurrentMap_temp_tParallelize_1;

				runningThreadCount.add(1);
				String name_tRunJob_12 = "tParallelize_1_" + java.util.UUID.randomUUID().toString();
				Thread thread_tRunJob_12 = new Thread(new ThreadGroup(name_tRunJob_12), name_tRunJob_12) {
					public void run() {
						java.util.Map threadRunResultMap = new java.util.HashMap();
						threadRunResultMap.put("errorCode", null);
						threadRunResultMap.put("status", "");
						threadLocal.set(threadRunResultMap);

						try {

							mdcInfo.forEach(org.slf4j.MDC::put);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_12' starts.");

							tRunJob_12Process(concurrentMap_tParallelize_1);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_12' is done.");

						} catch (TalendException e) {

							log.error("tParallelize_1 - " + e.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_12_SUBPROCESS_STATE", -1);
							e.printStackTrace();
						} catch (java.lang.Error error) {

							log.error("tParallelize_1 - " + error.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_12_SUBPROCESS_STATE", -1);
							error.printStackTrace();
						} finally {
							runningThreadCount.add(-1);
						}
					}
				};
				thread_tRunJob_12.start();
				mt_tParallelize_1.put("tRunJob_12", thread_tRunJob_12);
				runningThreadCount.add(1);
				String name_tRunJob_8 = "tParallelize_1_" + java.util.UUID.randomUUID().toString();
				Thread thread_tRunJob_8 = new Thread(new ThreadGroup(name_tRunJob_8), name_tRunJob_8) {
					public void run() {
						java.util.Map threadRunResultMap = new java.util.HashMap();
						threadRunResultMap.put("errorCode", null);
						threadRunResultMap.put("status", "");
						threadLocal.set(threadRunResultMap);

						try {

							mdcInfo.forEach(org.slf4j.MDC::put);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_8' starts.");

							tRunJob_8Process(concurrentMap_tParallelize_1);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_8' is done.");

						} catch (TalendException e) {

							log.error("tParallelize_1 - " + e.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_8_SUBPROCESS_STATE", -1);
							e.printStackTrace();
						} catch (java.lang.Error error) {

							log.error("tParallelize_1 - " + error.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_8_SUBPROCESS_STATE", -1);
							error.printStackTrace();
						} finally {
							runningThreadCount.add(-1);
						}
					}
				};
				thread_tRunJob_8.start();
				mt_tParallelize_1.put("tRunJob_8", thread_tRunJob_8);
				runningThreadCount.add(1);
				String name_tRunJob_1 = "tParallelize_1_" + java.util.UUID.randomUUID().toString();
				Thread thread_tRunJob_1 = new Thread(new ThreadGroup(name_tRunJob_1), name_tRunJob_1) {
					public void run() {
						java.util.Map threadRunResultMap = new java.util.HashMap();
						threadRunResultMap.put("errorCode", null);
						threadRunResultMap.put("status", "");
						threadLocal.set(threadRunResultMap);

						try {

							mdcInfo.forEach(org.slf4j.MDC::put);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_1' starts.");

							tRunJob_1Process(concurrentMap_tParallelize_1);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_1' is done.");

						} catch (TalendException e) {

							log.error("tParallelize_1 - " + e.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_1_SUBPROCESS_STATE", -1);
							e.printStackTrace();
						} catch (java.lang.Error error) {

							log.error("tParallelize_1 - " + error.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_1_SUBPROCESS_STATE", -1);
							error.printStackTrace();
						} finally {
							runningThreadCount.add(-1);
						}
					}
				};
				thread_tRunJob_1.start();
				mt_tParallelize_1.put("tRunJob_1", thread_tRunJob_1);
				runningThreadCount.add(1);
				String name_tRunJob_7 = "tParallelize_1_" + java.util.UUID.randomUUID().toString();
				Thread thread_tRunJob_7 = new Thread(new ThreadGroup(name_tRunJob_7), name_tRunJob_7) {
					public void run() {
						java.util.Map threadRunResultMap = new java.util.HashMap();
						threadRunResultMap.put("errorCode", null);
						threadRunResultMap.put("status", "");
						threadLocal.set(threadRunResultMap);

						try {

							mdcInfo.forEach(org.slf4j.MDC::put);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_7' starts.");

							tRunJob_7Process(concurrentMap_tParallelize_1);

							log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_7' is done.");

						} catch (TalendException e) {

							log.error("tParallelize_1 - " + e.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_7_SUBPROCESS_STATE", -1);
							e.printStackTrace();
						} catch (java.lang.Error error) {

							log.error("tParallelize_1 - " + error.getMessage());

							concurrentMap_tParallelize_1.put("tRunJob_7_SUBPROCESS_STATE", -1);
							error.printStackTrace();
						} finally {
							runningThreadCount.add(-1);
						}
					}
				};
				thread_tRunJob_7.start();
				mt_tParallelize_1.put("tRunJob_7", thread_tRunJob_7);
				while ((((globalMap.get("tRunJob_12_SUBPROCESS_STATE") == null) ? true
						: ((Integer) globalMap.get("tRunJob_12_SUBPROCESS_STATE") == 0))
						|| ((globalMap.get("tRunJob_8_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_8_SUBPROCESS_STATE") == 0))
						|| ((globalMap.get("tRunJob_1_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_1_SUBPROCESS_STATE") == 0))
						|| ((globalMap.get("tRunJob_7_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_7_SUBPROCESS_STATE") == 0))
						|| false)
						&& (((globalMap.get("tRunJob_12_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_12_SUBPROCESS_STATE") != -1))
								&& ((globalMap.get("tRunJob_8_SUBPROCESS_STATE") == null) ? true
										: ((Integer) globalMap.get("tRunJob_8_SUBPROCESS_STATE") != -1))
								&& ((globalMap.get("tRunJob_1_SUBPROCESS_STATE") == null) ? true
										: ((Integer) globalMap.get("tRunJob_1_SUBPROCESS_STATE") != -1))
								&& ((globalMap.get("tRunJob_7_SUBPROCESS_STATE") == null) ? true
										: ((Integer) globalMap.get("tRunJob_7_SUBPROCESS_STATE") != -1))
								&& true)) {
					Thread.sleep(100);
				}
// Die on error
// if one subjob fails,all other subjob will be shut down.
				if (((globalMap.get("tRunJob_12_SUBPROCESS_STATE") == null) ? false
						: ((Integer) globalMap.get("tRunJob_12_SUBPROCESS_STATE") == -1))
						|| ((globalMap.get("tRunJob_8_SUBPROCESS_STATE") == null) ? false
								: ((Integer) globalMap.get("tRunJob_8_SUBPROCESS_STATE") == -1))
						|| ((globalMap.get("tRunJob_1_SUBPROCESS_STATE") == null) ? false
								: ((Integer) globalMap.get("tRunJob_1_SUBPROCESS_STATE") == -1))
						|| ((globalMap.get("tRunJob_7_SUBPROCESS_STATE") == null) ? false
								: ((Integer) globalMap.get("tRunJob_7_SUBPROCESS_STATE") == -1))
						|| false) {
					// before you stop all the task threads, you need to make sure every task thread
					// have run already as : we have some block code which depend the
					// status("runningThreadCount" field) in task thread,
					// if the thread don't have run, the value of "runningThreadCount" may be wrong.
					while (((globalMap.get("tRunJob_12_SUBPROCESS_STATE") == null)
							|| (globalMap.get("tRunJob_8_SUBPROCESS_STATE") == null)
							|| (globalMap.get("tRunJob_1_SUBPROCESS_STATE") == null)
							|| (globalMap.get("tRunJob_7_SUBPROCESS_STATE") == null) || false)) {
						Thread.sleep(100);
					}

					// stop all task threads
					// sometimes the one ThreadDeath error is not enough (TDI-44798) --OZ
					while (thread_tRunJob_12.isAlive() || thread_tRunJob_8.isAlive() || thread_tRunJob_1.isAlive()
							|| thread_tRunJob_7.isAlive() || false) {
						for (Thread thread_tParallelize_1 : mt_tParallelize_1.values()) {
							if (thread_tParallelize_1.isAlive()) {
								thread_tParallelize_1.stop();
							}
						}

						Thread.sleep(100);
					}
					throw new Exception("At least one of the subjobs in tParallelize fails");
				}
// wait for all
// the following code will break only when all subjobs are done.
				while (((globalMap.get("tRunJob_12_SUBPROCESS_STATE") == null) ? true
						: ((Integer) globalMap.get("tRunJob_12_SUBPROCESS_STATE") == 0))
						|| ((globalMap.get("tRunJob_8_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_8_SUBPROCESS_STATE") == 0))
						|| ((globalMap.get("tRunJob_1_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_1_SUBPROCESS_STATE") == 0))
						|| ((globalMap.get("tRunJob_7_SUBPROCESS_STATE") == null) ? true
								: ((Integer) globalMap.get("tRunJob_7_SUBPROCESS_STATE") == 0))
						|| false) {
					Thread.sleep(100);
				}

// call next subprocesses
				log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_10' starts.");
				tRunJob_10Process(globalMap);
				log.debug("tParallelize_1 - The subjob starting with the component 'tRunJob_10' is done.");

				/**
				 * [tParallelize_1 begin ] stop
				 */

				/**
				 * [tParallelize_1 main ] start
				 */

				s(currentComponent = "tParallelize_1");

				tos_count_tParallelize_1++;

				/**
				 * [tParallelize_1 main ] stop
				 */

				/**
				 * [tParallelize_1 process_data_begin ] start
				 */

				s(currentComponent = "tParallelize_1");

				/**
				 * [tParallelize_1 process_data_begin ] stop
				 */

				/**
				 * [tParallelize_1 process_data_end ] start
				 */

				s(currentComponent = "tParallelize_1");

				/**
				 * [tParallelize_1 process_data_end ] stop
				 */

				/**
				 * [tParallelize_1 end ] start
				 */

				s(currentComponent = "tParallelize_1");

				ok_Hash.put("tParallelize_1", true);
				end_Hash.put("tParallelize_1", System.currentTimeMillis());

				/**
				 * [tParallelize_1 end ] stop
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
				 * [tParallelize_1 finally ] start
				 */

				s(currentComponent = "tParallelize_1");

				/**
				 * [tParallelize_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tParallelize_1_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_12Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_12_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_12", "0cdMLa_");

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
				 * [tRunJob_12 begin ] start
				 */

				sh("tRunJob_12");

				s(currentComponent = "tRunJob_12");

				cLabel = "TimeLoad";

				int tos_count_tRunJob_12 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_12 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_12 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_12 = new StringBuilder();
							log4jParamters_tRunJob_12.append("Parameters:");
							log4jParamters_tRunJob_12.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("PROCESS" + " = " + "TimeLoad");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_12.append(" | ");
							log4jParamters_tRunJob_12.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_12.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_12 - " + (log4jParamters_tRunJob_12));
						}
					}
					new BytesLimit65535_tRunJob_12().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_12", "TimeLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_12 begin ] stop
				 */

				/**
				 * [tRunJob_12 main ] start
				 */

				s(currentComponent = "tRunJob_12");

				cLabel = "TimeLoad";

				java.util.List<String> paraList_tRunJob_12 = new java.util.ArrayList<String>();

				paraList_tRunJob_12.add("--father_pid=" + pid);

				paraList_tRunJob_12.add("--root_pid=" + rootPid);

				paraList_tRunJob_12.add("--father_node=tRunJob_12");

				paraList_tRunJob_12.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_12.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_12.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_12.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_12.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_12 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_12 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_12".equals(tRunJobName_tRunJob_12) && childResumePath_tRunJob_12 != null) {
					paraList_tRunJob_12.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_12.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_12");

				java.util.Map<String, Object> parentContextMap_tRunJob_12 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_12 = null;

				ie6750_project1_grouph.timeload_0_1.TimeLoad childJob_tRunJob_12 = new ie6750_project1_grouph.timeload_0_1.TimeLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_12 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_12) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_12 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_12 : talendDataSources_tRunJob_12
							.entrySet()) {
						dataSources_tRunJob_12.put(talendDataSourceEntry_tRunJob_12.getKey(),
								talendDataSourceEntry_tRunJob_12.getValue().getRawDataSource());
					}
					childJob_tRunJob_12.setDataSources(dataSources_tRunJob_12);
				}

				childJob_tRunJob_12.parentContextMap = parentContextMap_tRunJob_12;

				log.info(
						"tRunJob_12 - The child job 'ie6750_project1_grouph.timeload_0_1.TimeLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_12 = childJob_tRunJob_12
						.runJob((String[]) paraList_tRunJob_12.toArray(new String[paraList_tRunJob_12.size()]));

				log.info("tRunJob_12 - The child job 'ie6750_project1_grouph.timeload_0_1.TimeLoad' is done.");

				if (childJob_tRunJob_12.getErrorCode() == null) {
					globalMap.put("tRunJob_12_CHILD_RETURN_CODE", childJob_tRunJob_12.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_12.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_12_CHILD_RETURN_CODE", childJob_tRunJob_12.getErrorCode());
				}
				if (childJob_tRunJob_12.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_12_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_12.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_12.getErrorCode());
				if (childJob_tRunJob_12.getErrorCode() != null || ("failure").equals(childJob_tRunJob_12.getStatus())) {
					java.lang.Exception ce_tRunJob_12 = childJob_tRunJob_12.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_12 != null)
							? (ce_tRunJob_12.getClass().getName() + ": " + ce_tRunJob_12.getMessage())
							: ""));
				}

				tos_count_tRunJob_12++;

				/**
				 * [tRunJob_12 main ] stop
				 */

				/**
				 * [tRunJob_12 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_12");

				cLabel = "TimeLoad";

				/**
				 * [tRunJob_12 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_12 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_12");

				cLabel = "TimeLoad";

				/**
				 * [tRunJob_12 process_data_end ] stop
				 */

				/**
				 * [tRunJob_12 end ] start
				 */

				s(currentComponent = "tRunJob_12");

				cLabel = "TimeLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_12 - " + ("Done."));

				ok_Hash.put("tRunJob_12", true);
				end_Hash.put("tRunJob_12", System.currentTimeMillis());

				/**
				 * [tRunJob_12 end ] stop
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
				 * [tRunJob_12 finally ] start
				 */

				s(currentComponent = "tRunJob_12");

				cLabel = "TimeLoad";

				/**
				 * [tRunJob_12 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_12_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_8_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_8", "Hsakoz_");

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
				 * [tRunJob_8 begin ] start
				 */

				sh("tRunJob_8");

				s(currentComponent = "tRunJob_8");

				cLabel = "PricingModelLoad";

				int tos_count_tRunJob_8 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_8 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_8 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_8 = new StringBuilder();
							log4jParamters_tRunJob_8.append("Parameters:");
							log4jParamters_tRunJob_8.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("PROCESS" + " = " + "PricingModelLoad");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_8.append(" | ");
							log4jParamters_tRunJob_8.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_8.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_8 - " + (log4jParamters_tRunJob_8));
						}
					}
					new BytesLimit65535_tRunJob_8().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_8", "PricingModelLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_8 begin ] stop
				 */

				/**
				 * [tRunJob_8 main ] start
				 */

				s(currentComponent = "tRunJob_8");

				cLabel = "PricingModelLoad";

				java.util.List<String> paraList_tRunJob_8 = new java.util.ArrayList<String>();

				paraList_tRunJob_8.add("--father_pid=" + pid);

				paraList_tRunJob_8.add("--root_pid=" + rootPid);

				paraList_tRunJob_8.add("--father_node=tRunJob_8");

				paraList_tRunJob_8.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_8.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_8.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_8.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_8.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_8 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_8 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_8".equals(tRunJobName_tRunJob_8) && childResumePath_tRunJob_8 != null) {
					paraList_tRunJob_8.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_8.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_8");

				java.util.Map<String, Object> parentContextMap_tRunJob_8 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_8 = null;

				ie6750_project1_grouph.pricingmodelload_0_1.PricingModelLoad childJob_tRunJob_8 = new ie6750_project1_grouph.pricingmodelload_0_1.PricingModelLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_8 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_8) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_8 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_8 : talendDataSources_tRunJob_8
							.entrySet()) {
						dataSources_tRunJob_8.put(talendDataSourceEntry_tRunJob_8.getKey(),
								talendDataSourceEntry_tRunJob_8.getValue().getRawDataSource());
					}
					childJob_tRunJob_8.setDataSources(dataSources_tRunJob_8);
				}

				childJob_tRunJob_8.parentContextMap = parentContextMap_tRunJob_8;

				log.info(
						"tRunJob_8 - The child job 'ie6750_project1_grouph.pricingmodelload_0_1.PricingModelLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_8 = childJob_tRunJob_8
						.runJob((String[]) paraList_tRunJob_8.toArray(new String[paraList_tRunJob_8.size()]));

				log.info(
						"tRunJob_8 - The child job 'ie6750_project1_grouph.pricingmodelload_0_1.PricingModelLoad' is done.");

				if (childJob_tRunJob_8.getErrorCode() == null) {
					globalMap.put("tRunJob_8_CHILD_RETURN_CODE",
							childJob_tRunJob_8.getStatus() != null && ("failure").equals(childJob_tRunJob_8.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_8_CHILD_RETURN_CODE", childJob_tRunJob_8.getErrorCode());
				}
				if (childJob_tRunJob_8.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_8_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_8.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_8.getErrorCode());
				if (childJob_tRunJob_8.getErrorCode() != null || ("failure").equals(childJob_tRunJob_8.getStatus())) {
					java.lang.Exception ce_tRunJob_8 = childJob_tRunJob_8.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_8 != null)
							? (ce_tRunJob_8.getClass().getName() + ": " + ce_tRunJob_8.getMessage())
							: ""));
				}

				tos_count_tRunJob_8++;

				/**
				 * [tRunJob_8 main ] stop
				 */

				/**
				 * [tRunJob_8 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_8");

				cLabel = "PricingModelLoad";

				/**
				 * [tRunJob_8 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_8 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_8");

				cLabel = "PricingModelLoad";

				/**
				 * [tRunJob_8 process_data_end ] stop
				 */

				/**
				 * [tRunJob_8 end ] start
				 */

				s(currentComponent = "tRunJob_8");

				cLabel = "PricingModelLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_8 - " + ("Done."));

				ok_Hash.put("tRunJob_8", true);
				end_Hash.put("tRunJob_8", System.currentTimeMillis());

				/**
				 * [tRunJob_8 end ] stop
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
				 * [tRunJob_8 finally ] start
				 */

				s(currentComponent = "tRunJob_8");

				cLabel = "PricingModelLoad";

				/**
				 * [tRunJob_8 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_8_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_1", "dXz8b1_");

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
				 * [tRunJob_1 begin ] start
				 */

				sh("tRunJob_1");

				s(currentComponent = "tRunJob_1");

				cLabel = "BookingLoad";

				int tos_count_tRunJob_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_1 = new StringBuilder();
							log4jParamters_tRunJob_1.append("Parameters:");
							log4jParamters_tRunJob_1.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("PROCESS" + " = " + "BookingLoad");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_1 - " + (log4jParamters_tRunJob_1));
						}
					}
					new BytesLimit65535_tRunJob_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_1", "BookingLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_1 begin ] stop
				 */

				/**
				 * [tRunJob_1 main ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "BookingLoad";

				java.util.List<String> paraList_tRunJob_1 = new java.util.ArrayList<String>();

				paraList_tRunJob_1.add("--father_pid=" + pid);

				paraList_tRunJob_1.add("--root_pid=" + rootPid);

				paraList_tRunJob_1.add("--father_node=tRunJob_1");

				paraList_tRunJob_1.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_1.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_1.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_1.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_1.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_1 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_1 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_1".equals(tRunJobName_tRunJob_1) && childResumePath_tRunJob_1 != null) {
					paraList_tRunJob_1.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_1.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_1");

				java.util.Map<String, Object> parentContextMap_tRunJob_1 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_1 = null;

				ie6750_project1_grouph.bookingload_0_1.BookingLoad childJob_tRunJob_1 = new ie6750_project1_grouph.bookingload_0_1.BookingLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_1 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_1) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_1 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_1 : talendDataSources_tRunJob_1
							.entrySet()) {
						dataSources_tRunJob_1.put(talendDataSourceEntry_tRunJob_1.getKey(),
								talendDataSourceEntry_tRunJob_1.getValue().getRawDataSource());
					}
					childJob_tRunJob_1.setDataSources(dataSources_tRunJob_1);
				}

				childJob_tRunJob_1.parentContextMap = parentContextMap_tRunJob_1;

				log.info(
						"tRunJob_1 - The child job 'ie6750_project1_grouph.bookingload_0_1.BookingLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_1 = childJob_tRunJob_1
						.runJob((String[]) paraList_tRunJob_1.toArray(new String[paraList_tRunJob_1.size()]));

				log.info("tRunJob_1 - The child job 'ie6750_project1_grouph.bookingload_0_1.BookingLoad' is done.");

				if (childJob_tRunJob_1.getErrorCode() == null) {
					globalMap.put("tRunJob_1_CHILD_RETURN_CODE",
							childJob_tRunJob_1.getStatus() != null && ("failure").equals(childJob_tRunJob_1.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_1_CHILD_RETURN_CODE", childJob_tRunJob_1.getErrorCode());
				}
				if (childJob_tRunJob_1.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_1_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_1.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_1.getErrorCode());
				if (childJob_tRunJob_1.getErrorCode() != null || ("failure").equals(childJob_tRunJob_1.getStatus())) {
					java.lang.Exception ce_tRunJob_1 = childJob_tRunJob_1.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_1 != null)
							? (ce_tRunJob_1.getClass().getName() + ": " + ce_tRunJob_1.getMessage())
							: ""));
				}

				tos_count_tRunJob_1++;

				/**
				 * [tRunJob_1 main ] stop
				 */

				/**
				 * [tRunJob_1 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "BookingLoad";

				/**
				 * [tRunJob_1 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_1 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "BookingLoad";

				/**
				 * [tRunJob_1 process_data_end ] stop
				 */

				/**
				 * [tRunJob_1 end ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "BookingLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_1 - " + ("Done."));

				ok_Hash.put("tRunJob_1", true);
				end_Hash.put("tRunJob_1", System.currentTimeMillis());

				/**
				 * [tRunJob_1 end ] stop
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
				 * [tRunJob_1 finally ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "BookingLoad";

				/**
				 * [tRunJob_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_1_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_7_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_7", "BCmkdx_");

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
				 * [tRunJob_7 begin ] start
				 */

				sh("tRunJob_7");

				s(currentComponent = "tRunJob_7");

				cLabel = "PaymentLoad";

				int tos_count_tRunJob_7 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_7 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_7 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_7 = new StringBuilder();
							log4jParamters_tRunJob_7.append("Parameters:");
							log4jParamters_tRunJob_7.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("PROCESS" + " = " + "PaymentLoad");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_7.append(" | ");
							log4jParamters_tRunJob_7.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_7.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_7 - " + (log4jParamters_tRunJob_7));
						}
					}
					new BytesLimit65535_tRunJob_7().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_7", "PaymentLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_7 begin ] stop
				 */

				/**
				 * [tRunJob_7 main ] start
				 */

				s(currentComponent = "tRunJob_7");

				cLabel = "PaymentLoad";

				java.util.List<String> paraList_tRunJob_7 = new java.util.ArrayList<String>();

				paraList_tRunJob_7.add("--father_pid=" + pid);

				paraList_tRunJob_7.add("--root_pid=" + rootPid);

				paraList_tRunJob_7.add("--father_node=tRunJob_7");

				paraList_tRunJob_7.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_7.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_7.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_7.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_7.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_7 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_7 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_7".equals(tRunJobName_tRunJob_7) && childResumePath_tRunJob_7 != null) {
					paraList_tRunJob_7.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_7.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_7");

				java.util.Map<String, Object> parentContextMap_tRunJob_7 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_7 = null;

				ie6750_project1_grouph.paymentload_0_1.PaymentLoad childJob_tRunJob_7 = new ie6750_project1_grouph.paymentload_0_1.PaymentLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_7 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_7) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_7 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_7 : talendDataSources_tRunJob_7
							.entrySet()) {
						dataSources_tRunJob_7.put(talendDataSourceEntry_tRunJob_7.getKey(),
								talendDataSourceEntry_tRunJob_7.getValue().getRawDataSource());
					}
					childJob_tRunJob_7.setDataSources(dataSources_tRunJob_7);
				}

				childJob_tRunJob_7.parentContextMap = parentContextMap_tRunJob_7;

				log.info(
						"tRunJob_7 - The child job 'ie6750_project1_grouph.paymentload_0_1.PaymentLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_7 = childJob_tRunJob_7
						.runJob((String[]) paraList_tRunJob_7.toArray(new String[paraList_tRunJob_7.size()]));

				log.info("tRunJob_7 - The child job 'ie6750_project1_grouph.paymentload_0_1.PaymentLoad' is done.");

				if (childJob_tRunJob_7.getErrorCode() == null) {
					globalMap.put("tRunJob_7_CHILD_RETURN_CODE",
							childJob_tRunJob_7.getStatus() != null && ("failure").equals(childJob_tRunJob_7.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_7_CHILD_RETURN_CODE", childJob_tRunJob_7.getErrorCode());
				}
				if (childJob_tRunJob_7.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_7_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_7.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_7.getErrorCode());
				if (childJob_tRunJob_7.getErrorCode() != null || ("failure").equals(childJob_tRunJob_7.getStatus())) {
					java.lang.Exception ce_tRunJob_7 = childJob_tRunJob_7.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_7 != null)
							? (ce_tRunJob_7.getClass().getName() + ": " + ce_tRunJob_7.getMessage())
							: ""));
				}

				tos_count_tRunJob_7++;

				/**
				 * [tRunJob_7 main ] stop
				 */

				/**
				 * [tRunJob_7 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_7");

				cLabel = "PaymentLoad";

				/**
				 * [tRunJob_7 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_7 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_7");

				cLabel = "PaymentLoad";

				/**
				 * [tRunJob_7 process_data_end ] stop
				 */

				/**
				 * [tRunJob_7 end ] start
				 */

				s(currentComponent = "tRunJob_7");

				cLabel = "PaymentLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_7 - " + ("Done."));

				ok_Hash.put("tRunJob_7", true);
				end_Hash.put("tRunJob_7", System.currentTimeMillis());

				/**
				 * [tRunJob_7 end ] stop
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
				 * [tRunJob_7 finally ] start
				 */

				s(currentComponent = "tRunJob_7");

				cLabel = "PaymentLoad";

				/**
				 * [tRunJob_7 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_7_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_10Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_10_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tRunJob_10", "g1LApq_");

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
				 * [tRunJob_10 begin ] start
				 */

				sh("tRunJob_10");

				s(currentComponent = "tRunJob_10");

				cLabel = "SessionFactLoad";

				int tos_count_tRunJob_10 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_10 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_10 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_10 = new StringBuilder();
							log4jParamters_tRunJob_10.append("Parameters:");
							log4jParamters_tRunJob_10.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("PROCESS" + " = " + "SessionFactLoad");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_10.append(" | ");
							log4jParamters_tRunJob_10.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_10.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_10 - " + (log4jParamters_tRunJob_10));
						}
					}
					new BytesLimit65535_tRunJob_10().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_10", "SessionFactLoad", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_10 begin ] stop
				 */

				/**
				 * [tRunJob_10 main ] start
				 */

				s(currentComponent = "tRunJob_10");

				cLabel = "SessionFactLoad";

				java.util.List<String> paraList_tRunJob_10 = new java.util.ArrayList<String>();

				paraList_tRunJob_10.add("--father_pid=" + pid);

				paraList_tRunJob_10.add("--root_pid=" + rootPid);

				paraList_tRunJob_10.add("--father_node=tRunJob_10");

				paraList_tRunJob_10.add("--context=Default");

				if (!"".equals(log4jLevel)) {
					paraList_tRunJob_10.add("--log4jLevel=" + log4jLevel);
				}

				if (enableLogStash) {
					paraList_tRunJob_10.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_10.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_10.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_10 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_10 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_10".equals(tRunJobName_tRunJob_10) && childResumePath_tRunJob_10 != null) {
					paraList_tRunJob_10.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_10.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_10");

				java.util.Map<String, Object> parentContextMap_tRunJob_10 = new java.util.HashMap<String, Object>();

				Object obj_tRunJob_10 = null;

				ie6750_project1_grouph.sessionfactload_0_1.SessionFactLoad childJob_tRunJob_10 = new ie6750_project1_grouph.sessionfactload_0_1.SessionFactLoad();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_10 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_10) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_10 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_10 : talendDataSources_tRunJob_10
							.entrySet()) {
						dataSources_tRunJob_10.put(talendDataSourceEntry_tRunJob_10.getKey(),
								talendDataSourceEntry_tRunJob_10.getValue().getRawDataSource());
					}
					childJob_tRunJob_10.setDataSources(dataSources_tRunJob_10);
				}

				childJob_tRunJob_10.parentContextMap = parentContextMap_tRunJob_10;

				log.info(
						"tRunJob_10 - The child job 'ie6750_project1_grouph.sessionfactload_0_1.SessionFactLoad' starts on the version '0.1' with the context 'Default'.");

				String[][] childReturn_tRunJob_10 = childJob_tRunJob_10
						.runJob((String[]) paraList_tRunJob_10.toArray(new String[paraList_tRunJob_10.size()]));

				log.info(
						"tRunJob_10 - The child job 'ie6750_project1_grouph.sessionfactload_0_1.SessionFactLoad' is done.");

				if (childJob_tRunJob_10.getErrorCode() == null) {
					globalMap.put("tRunJob_10_CHILD_RETURN_CODE", childJob_tRunJob_10.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_10.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_10_CHILD_RETURN_CODE", childJob_tRunJob_10.getErrorCode());
				}
				if (childJob_tRunJob_10.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_10_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_10.getExceptionStackTrace());
				}
				((java.util.Map) threadLocal.get()).put("errorCode", childJob_tRunJob_10.getErrorCode());
				if (childJob_tRunJob_10.getErrorCode() != null || ("failure").equals(childJob_tRunJob_10.getStatus())) {
					java.lang.Exception ce_tRunJob_10 = childJob_tRunJob_10.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_10 != null)
							? (ce_tRunJob_10.getClass().getName() + ": " + ce_tRunJob_10.getMessage())
							: ""));
				}

				tos_count_tRunJob_10++;

				/**
				 * [tRunJob_10 main ] stop
				 */

				/**
				 * [tRunJob_10 process_data_begin ] start
				 */

				s(currentComponent = "tRunJob_10");

				cLabel = "SessionFactLoad";

				/**
				 * [tRunJob_10 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_10 process_data_end ] start
				 */

				s(currentComponent = "tRunJob_10");

				cLabel = "SessionFactLoad";

				/**
				 * [tRunJob_10 process_data_end ] stop
				 */

				/**
				 * [tRunJob_10 end ] start
				 */

				s(currentComponent = "tRunJob_10");

				cLabel = "SessionFactLoad";

				if (log.isDebugEnabled())
					log.debug("tRunJob_10 - " + ("Done."));

				ok_Hash.put("tRunJob_10", true);
				end_Hash.put("tRunJob_10", System.currentTimeMillis());

				/**
				 * [tRunJob_10 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_10:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk18", 0, "ok");
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

			try {

				/**
				 * [tRunJob_10 finally ] start
				 */

				s(currentComponent = "tRunJob_10");

				cLabel = "SessionFactLoad";

				/**
				 * [tRunJob_10 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_10_SUBPROCESS_STATE", 1);
	}

	public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBCommit_1", "ZqWtKL_");

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

	private SyncInt runningThreadCount = new SyncInt();

	private class SyncInt {
		private int count = 0;

		public synchronized void add(int i) {
			count += i;
		}

		public synchronized int getCount() {
			return count;
		}
	}

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final ControlFlowTheWrapper ControlFlowTheWrapperClass = new ControlFlowTheWrapper();

		int exitCode = ControlFlowTheWrapperClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'ControlFlowTheWrapper' - Done.");
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
		log.info("TalendJob: 'ControlFlowTheWrapper' - Start.");

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
		org.slf4j.MDC.put("_jobRepositoryId", "_tLKekJvcEe-5PpigxqfS-w");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-11-09T19:56:05.550526Z");

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
			java.io.InputStream inContext = ControlFlowTheWrapper.class.getClassLoader().getResourceAsStream(
					"ie6750_project1_grouph/controlflowthewrapper_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = ControlFlowTheWrapper.class.getClassLoader()
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
		log.info("TalendJob: 'ControlFlowTheWrapper' - Started.");
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
		final Thread launchingThread = Thread.currentThread();
		runningThreadCount.add(1);
		new Thread() {
			public void run() {
				mdcInfo.forEach(org.slf4j.MDC::put);

				java.util.Map threadRunResultMap = new java.util.HashMap();
				threadRunResultMap.put("errorCode", null);
				threadRunResultMap.put("status", "");
				threadLocal.set(threadRunResultMap);

				try {
					((java.util.Map) threadLocal.get()).put("errorCode", null);
					tDBConnection_1Process(globalMap);
					if (!"failure".equals(((java.util.Map) threadLocal.get()).get("status"))) {
						((java.util.Map) threadLocal.get()).put("status", "end");
					}
				} catch (TalendException e_tDBConnection_1) {
					globalMap.put("tDBConnection_1_SUBPROCESS_STATE", -1);

					e_tDBConnection_1.printStackTrace();

				} catch (java.lang.Error e_tDBConnection_1) {
					globalMap.put("tDBConnection_1_SUBPROCESS_STATE", -1);
					((java.util.Map) threadLocal.get()).put("status", "failure");
					throw e_tDBConnection_1;

				} finally {
					Integer localErrorCode = (Integer) (((java.util.Map) threadLocal.get()).get("errorCode"));
					String localStatus = (String) (((java.util.Map) threadLocal.get()).get("status"));
					if (localErrorCode != null) {
						if (errorCode == null || localErrorCode.compareTo(errorCode) > 0) {
							errorCode = localErrorCode;
						}
					}
					if (!status.equals("failure")) {
						status = localStatus;
					}

					if ("true".equals(((java.util.Map) threadLocal.get()).get("JobInterrupted"))) {
						launchingThread.interrupt();
					}

					runningThreadCount.add(-1);
				}
			}
		}.start();

		boolean interrupted = false;
		while (runningThreadCount.getCount() > 0) {
			try {
				Thread.sleep(10);
			} catch (java.lang.InterruptedException e) {
				interrupted = true;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (interrupted) {
			Thread.currentThread().interrupt();
		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println(
					(endUsedMemory - startUsedMemory) + " bytes memory increase when running : ControlFlowTheWrapper");
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

		Integer localErrorCode = (Integer) (((java.util.Map) threadLocal.get()).get("errorCode"));
		String localStatus = (String) (((java.util.Map) threadLocal.get()).get("status"));
		if (localErrorCode != null) {
			if (errorCode == null || localErrorCode.compareTo(errorCode) > 0) {
				errorCode = localErrorCode;
			}
		}
		if (localStatus != null && !status.equals("failure")) {
			status = localStatus;
		}

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
		log.info("TalendJob: 'ControlFlowTheWrapper' - Finished - status: " + status + " returnCode: " + returnCode);

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
 * 217340 characters generated by Talend Real-time Big Data Platform on the 9
 * November 2024 at 20:56:05 CET
 ************************************************************************************************/