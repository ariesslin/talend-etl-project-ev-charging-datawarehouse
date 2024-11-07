
package ie6750_project1_grouph.timeload_0_1;

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
 * Job: TimeLoad Purpose: <br>
 * Description: <br>
 * 
 * @author lin.shan1@northeastern.edu
 * @version 8.0.1.20240920_1319-patch
 * @status
 */
public class TimeLoad implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "TimeLoad.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(TimeLoad.class);

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
	private final String jobName = "TimeLoad";
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
			"_gnHIIJvcEe-5PpigxqfS-w", "0.1");
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
					TimeLoad.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(TimeLoad.this, new Object[] { e, currentComponent, globalMap });
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

		status = "failure";

		tDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBConnection_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBCommit_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

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

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
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

		mdc("tDBConnection_1", "F30xnw_");

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
									"enc:routine.encryption.key.v1:6B0ZAQeY8hq2XEO1D3GHCKtVSFww8eA/mVYZ2MQpZZfbpQ==")
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
						"enc:routine.encryption.key.v1:oCns6R1qBsSZ37dPSAJO5V9sjgZB0ewnF8d6/FemXuzsvg==");
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

		mdc("tDBConnection_2", "LDzT4j_");

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
									"enc:routine.encryption.key.v1:e7TSOYeRJaDAEEb0MdQhMzgr+Z8VjgVBFCKBYLS9Os+ViA==")
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
						"enc:routine.encryption.key.v1:7vhHRIcKuqKOFrLVbkEntBrKZLqzJhGynpiTQpNS2IJ2og==");
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

			tFileInputDelimited_1Process(globalMap);

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

	public static class FoundStruct implements routines.system.IPersistableRow<FoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
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

				result = prime * result + (int) this.timekey;

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
			final FoundStruct other = (FoundStruct) obj;

			if (this.timekey != other.timekey)
				return false;

			return true;
		}

		public void copyDataTo(FoundStruct other) {

			other.timekey = this.timekey;
			other.year = this.year;
			other.month = this.month;
			other.date = this.date;
			other.hour = this.hour;
			other.daypart = this.daypart;
			other.weekdayorweekend = this.weekdayorweekend;

		}

		public void copyKeysDataTo(FoundStruct other) {

			other.timekey = this.timekey;

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.timekey = dis.readInt();

					this.year = readInteger(dis);

					this.month = readInteger(dis);

					this.date = readDate(dis);

					this.hour = readInteger(dis);

					this.daypart = readString(dis);

					this.weekdayorweekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.timekey = dis.readInt();

					this.year = readInteger(dis);

					this.month = readInteger(dis);

					this.date = readDate(dis);

					this.hour = readInteger(dis);

					this.daypart = readString(dis);

					this.weekdayorweekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.timekey);

				// Integer

				writeInteger(this.year, dos);

				// Integer

				writeInteger(this.month, dos);

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

				// String

				writeString(this.daypart, dos);

				// String

				writeString(this.weekdayorweekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.timekey);

				// Integer

				writeInteger(this.year, dos);

				// Integer

				writeInteger(this.month, dos);

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

				// String

				writeString(this.daypart, dos);

				// String

				writeString(this.weekdayorweekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

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
		public int compareTo(FoundStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.timekey, other.timekey);
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

	public static class NotFoundStruct implements routines.system.IPersistableRow<NotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
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

				result = prime * result + (int) this.timekey;

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
			final NotFoundStruct other = (NotFoundStruct) obj;

			if (this.timekey != other.timekey)
				return false;

			return true;
		}

		public void copyDataTo(NotFoundStruct other) {

			other.timekey = this.timekey;
			other.year = this.year;
			other.month = this.month;
			other.date = this.date;
			other.hour = this.hour;
			other.daypart = this.daypart;
			other.weekdayorweekend = this.weekdayorweekend;

		}

		public void copyKeysDataTo(NotFoundStruct other) {

			other.timekey = this.timekey;

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.timekey = dis.readInt();

					this.year = readInteger(dis);

					this.month = readInteger(dis);

					this.date = readDate(dis);

					this.hour = readInteger(dis);

					this.daypart = readString(dis);

					this.weekdayorweekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.timekey = dis.readInt();

					this.year = readInteger(dis);

					this.month = readInteger(dis);

					this.date = readDate(dis);

					this.hour = readInteger(dis);

					this.daypart = readString(dis);

					this.weekdayorweekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.timekey);

				// Integer

				writeInteger(this.year, dos);

				// Integer

				writeInteger(this.month, dos);

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

				// String

				writeString(this.daypart, dos);

				// String

				writeString(this.weekdayorweekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.timekey);

				// Integer

				writeInteger(this.year, dos);

				// Integer

				writeInteger(this.month, dos);

				// java.util.Date

				writeDate(this.date, dos);

				// Integer

				writeInteger(this.hour, dos);

				// String

				writeString(this.daypart, dos);

				// String

				writeString(this.weekdayorweekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

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
		public int compareTo(NotFoundStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.timekey, other.timekey);
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

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];

		public Integer TimeKey;

		public Integer getTimeKey() {
			return this.TimeKey;
		}

		public Boolean TimeKeyIsNullable() {
			return true;
		}

		public Boolean TimeKeyIsKey() {
			return false;
		}

		public Integer TimeKeyLength() {
			return 2;
		}

		public Integer TimeKeyPrecision() {
			return 0;
		}

		public String TimeKeyDefault() {

			return null;

		}

		public String TimeKeyComment() {

			return "";

		}

		public String TimeKeyPattern() {

			return "dd-MM-yyyy";

		}

		public String TimeKeyOriginalDbColumnName() {

			return "TimeKey";

		}

		public Integer Hour;

		public Integer getHour() {
			return this.Hour;
		}

		public Boolean HourIsNullable() {
			return true;
		}

		public Boolean HourIsKey() {
			return false;
		}

		public Integer HourLength() {
			return 2;
		}

		public Integer HourPrecision() {
			return 0;
		}

		public String HourDefault() {

			return null;

		}

		public String HourComment() {

			return "";

		}

		public String HourPattern() {

			return "dd-MM-yyyy";

		}

		public String HourOriginalDbColumnName() {

			return "Hour";

		}

		public java.util.Date Date;

		public java.util.Date getDate() {
			return this.Date;
		}

		public Boolean DateIsNullable() {
			return true;
		}

		public Boolean DateIsKey() {
			return false;
		}

		public Integer DateLength() {
			return 10;
		}

		public Integer DatePrecision() {
			return 0;
		}

		public String DateDefault() {

			return null;

		}

		public String DateComment() {

			return "";

		}

		public String DatePattern() {

			return "yyyy-MM-dd";

		}

		public String DateOriginalDbColumnName() {

			return "Date";

		}

		public Integer Month;

		public Integer getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 1;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "dd-MM-yyyy";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public Integer Year;

		public Integer getYear() {
			return this.Year;
		}

		public Boolean YearIsNullable() {
			return true;
		}

		public Boolean YearIsKey() {
			return false;
		}

		public Integer YearLength() {
			return 4;
		}

		public Integer YearPrecision() {
			return 0;
		}

		public String YearDefault() {

			return null;

		}

		public String YearComment() {

			return "";

		}

		public String YearPattern() {

			return "dd-MM-yyyy";

		}

		public String YearOriginalDbColumnName() {

			return "Year";

		}

		public String Daypart;

		public String getDaypart() {
			return this.Daypart;
		}

		public Boolean DaypartIsNullable() {
			return true;
		}

		public Boolean DaypartIsKey() {
			return false;
		}

		public Integer DaypartLength() {
			return 9;
		}

		public Integer DaypartPrecision() {
			return 0;
		}

		public String DaypartDefault() {

			return null;

		}

		public String DaypartComment() {

			return "";

		}

		public String DaypartPattern() {

			return "dd-MM-yyyy";

		}

		public String DaypartOriginalDbColumnName() {

			return "Daypart";

		}

		public String WeekdayOrWeekend;

		public String getWeekdayOrWeekend() {
			return this.WeekdayOrWeekend;
		}

		public Boolean WeekdayOrWeekendIsNullable() {
			return true;
		}

		public Boolean WeekdayOrWeekendIsKey() {
			return false;
		}

		public Integer WeekdayOrWeekendLength() {
			return 7;
		}

		public Integer WeekdayOrWeekendPrecision() {
			return 0;
		}

		public String WeekdayOrWeekendDefault() {

			return null;

		}

		public String WeekdayOrWeekendComment() {

			return "";

		}

		public String WeekdayOrWeekendPattern() {

			return "dd-MM-yyyy";

		}

		public String WeekdayOrWeekendOriginalDbColumnName() {

			return "WeekdayOrWeekend";

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.TimeKey = readInteger(dis);

					this.Hour = readInteger(dis);

					this.Date = readDate(dis);

					this.Month = readInteger(dis);

					this.Year = readInteger(dis);

					this.Daypart = readString(dis);

					this.WeekdayOrWeekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.TimeKey = readInteger(dis);

					this.Hour = readInteger(dis);

					this.Date = readDate(dis);

					this.Month = readInteger(dis);

					this.Year = readInteger(dis);

					this.Daypart = readString(dis);

					this.WeekdayOrWeekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.TimeKey, dos);

				// Integer

				writeInteger(this.Hour, dos);

				// java.util.Date

				writeDate(this.Date, dos);

				// Integer

				writeInteger(this.Month, dos);

				// Integer

				writeInteger(this.Year, dos);

				// String

				writeString(this.Daypart, dos);

				// String

				writeString(this.WeekdayOrWeekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.TimeKey, dos);

				// Integer

				writeInteger(this.Hour, dos);

				// java.util.Date

				writeDate(this.Date, dos);

				// Integer

				writeInteger(this.Month, dos);

				// Integer

				writeInteger(this.Year, dos);

				// String

				writeString(this.Daypart, dos);

				// String

				writeString(this.WeekdayOrWeekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("TimeKey=" + String.valueOf(TimeKey));
			sb.append(",Hour=" + String.valueOf(Hour));
			sb.append(",Date=" + String.valueOf(Date));
			sb.append(",Month=" + String.valueOf(Month));
			sb.append(",Year=" + String.valueOf(Year));
			sb.append(",Daypart=" + Daypart);
			sb.append(",WeekdayOrWeekend=" + WeekdayOrWeekend);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (TimeKey == null) {
				sb.append("<null>");
			} else {
				sb.append(TimeKey);
			}

			sb.append("|");

			if (Hour == null) {
				sb.append("<null>");
			} else {
				sb.append(Hour);
			}

			sb.append("|");

			if (Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Date);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Year);
			}

			sb.append("|");

			if (Daypart == null) {
				sb.append("<null>");
			} else {
				sb.append(Daypart);
			}

			sb.append("|");

			if (WeekdayOrWeekend == null) {
				sb.append("<null>");
			} else {
				sb.append(WeekdayOrWeekend);
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

	public static class after_tFileInputDelimited_1Struct
			implements routines.system.IPersistableRow<after_tFileInputDelimited_1Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];

		public Integer TimeKey;

		public Integer getTimeKey() {
			return this.TimeKey;
		}

		public Boolean TimeKeyIsNullable() {
			return true;
		}

		public Boolean TimeKeyIsKey() {
			return false;
		}

		public Integer TimeKeyLength() {
			return 2;
		}

		public Integer TimeKeyPrecision() {
			return 0;
		}

		public String TimeKeyDefault() {

			return null;

		}

		public String TimeKeyComment() {

			return "";

		}

		public String TimeKeyPattern() {

			return "dd-MM-yyyy";

		}

		public String TimeKeyOriginalDbColumnName() {

			return "TimeKey";

		}

		public Integer Hour;

		public Integer getHour() {
			return this.Hour;
		}

		public Boolean HourIsNullable() {
			return true;
		}

		public Boolean HourIsKey() {
			return false;
		}

		public Integer HourLength() {
			return 2;
		}

		public Integer HourPrecision() {
			return 0;
		}

		public String HourDefault() {

			return null;

		}

		public String HourComment() {

			return "";

		}

		public String HourPattern() {

			return "dd-MM-yyyy";

		}

		public String HourOriginalDbColumnName() {

			return "Hour";

		}

		public java.util.Date Date;

		public java.util.Date getDate() {
			return this.Date;
		}

		public Boolean DateIsNullable() {
			return true;
		}

		public Boolean DateIsKey() {
			return false;
		}

		public Integer DateLength() {
			return 10;
		}

		public Integer DatePrecision() {
			return 0;
		}

		public String DateDefault() {

			return null;

		}

		public String DateComment() {

			return "";

		}

		public String DatePattern() {

			return "yyyy-MM-dd";

		}

		public String DateOriginalDbColumnName() {

			return "Date";

		}

		public Integer Month;

		public Integer getMonth() {
			return this.Month;
		}

		public Boolean MonthIsNullable() {
			return true;
		}

		public Boolean MonthIsKey() {
			return false;
		}

		public Integer MonthLength() {
			return 1;
		}

		public Integer MonthPrecision() {
			return 0;
		}

		public String MonthDefault() {

			return null;

		}

		public String MonthComment() {

			return "";

		}

		public String MonthPattern() {

			return "dd-MM-yyyy";

		}

		public String MonthOriginalDbColumnName() {

			return "Month";

		}

		public Integer Year;

		public Integer getYear() {
			return this.Year;
		}

		public Boolean YearIsNullable() {
			return true;
		}

		public Boolean YearIsKey() {
			return false;
		}

		public Integer YearLength() {
			return 4;
		}

		public Integer YearPrecision() {
			return 0;
		}

		public String YearDefault() {

			return null;

		}

		public String YearComment() {

			return "";

		}

		public String YearPattern() {

			return "dd-MM-yyyy";

		}

		public String YearOriginalDbColumnName() {

			return "Year";

		}

		public String Daypart;

		public String getDaypart() {
			return this.Daypart;
		}

		public Boolean DaypartIsNullable() {
			return true;
		}

		public Boolean DaypartIsKey() {
			return false;
		}

		public Integer DaypartLength() {
			return 9;
		}

		public Integer DaypartPrecision() {
			return 0;
		}

		public String DaypartDefault() {

			return null;

		}

		public String DaypartComment() {

			return "";

		}

		public String DaypartPattern() {

			return "dd-MM-yyyy";

		}

		public String DaypartOriginalDbColumnName() {

			return "Daypart";

		}

		public String WeekdayOrWeekend;

		public String getWeekdayOrWeekend() {
			return this.WeekdayOrWeekend;
		}

		public Boolean WeekdayOrWeekendIsNullable() {
			return true;
		}

		public Boolean WeekdayOrWeekendIsKey() {
			return false;
		}

		public Integer WeekdayOrWeekendLength() {
			return 7;
		}

		public Integer WeekdayOrWeekendPrecision() {
			return 0;
		}

		public String WeekdayOrWeekendDefault() {

			return null;

		}

		public String WeekdayOrWeekendComment() {

			return "";

		}

		public String WeekdayOrWeekendPattern() {

			return "dd-MM-yyyy";

		}

		public String WeekdayOrWeekendOriginalDbColumnName() {

			return "WeekdayOrWeekend";

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.TimeKey = readInteger(dis);

					this.Hour = readInteger(dis);

					this.Date = readDate(dis);

					this.Month = readInteger(dis);

					this.Year = readInteger(dis);

					this.Daypart = readString(dis);

					this.WeekdayOrWeekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.TimeKey = readInteger(dis);

					this.Hour = readInteger(dis);

					this.Date = readDate(dis);

					this.Month = readInteger(dis);

					this.Year = readInteger(dis);

					this.Daypart = readString(dis);

					this.WeekdayOrWeekend = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.TimeKey, dos);

				// Integer

				writeInteger(this.Hour, dos);

				// java.util.Date

				writeDate(this.Date, dos);

				// Integer

				writeInteger(this.Month, dos);

				// Integer

				writeInteger(this.Year, dos);

				// String

				writeString(this.Daypart, dos);

				// String

				writeString(this.WeekdayOrWeekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.TimeKey, dos);

				// Integer

				writeInteger(this.Hour, dos);

				// java.util.Date

				writeDate(this.Date, dos);

				// Integer

				writeInteger(this.Month, dos);

				// Integer

				writeInteger(this.Year, dos);

				// String

				writeString(this.Daypart, dos);

				// String

				writeString(this.WeekdayOrWeekend, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("TimeKey=" + String.valueOf(TimeKey));
			sb.append(",Hour=" + String.valueOf(Hour));
			sb.append(",Date=" + String.valueOf(Date));
			sb.append(",Month=" + String.valueOf(Month));
			sb.append(",Year=" + String.valueOf(Year));
			sb.append(",Daypart=" + Daypart);
			sb.append(",WeekdayOrWeekend=" + WeekdayOrWeekend);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (TimeKey == null) {
				sb.append("<null>");
			} else {
				sb.append(TimeKey);
			}

			sb.append("|");

			if (Hour == null) {
				sb.append("<null>");
			} else {
				sb.append(Hour);
			}

			sb.append("|");

			if (Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Date);
			}

			sb.append("|");

			if (Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Month);
			}

			sb.append("|");

			if (Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Year);
			}

			sb.append("|");

			if (Daypart == null) {
				sb.append("<null>");
			} else {
				sb.append(Daypart);
			}

			sb.append("|");

			if (WeekdayOrWeekend == null) {
				sb.append("<null>");
			} else {
				sb.append(WeekdayOrWeekend);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tFileInputDelimited_1Struct other) {

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

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tFileInputDelimited_1", "rzid4P_");

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

				tDBInput_1Process(globalMap);

				row1Struct row1 = new row1Struct();
				FoundStruct Found = new FoundStruct();
				NotFoundStruct NotFound = new NotFoundStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				sh("tDBOutput_1");

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"timedimension\"";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "Found");

				int tos_count_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_1 = new StringBuilder();
							log4jParamters_tDBOutput_1.append("Parameters:");
							log4jParamters_tDBOutput_1.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"timedimension\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DATA_ACTION" + " = " + "UPDATE");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_ALTERNATE_SCHEMA" + " = " + "false");
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
					talendJobLog.addCM("tDBOutput_1", "\"timedimension\"", "tPostgresqlOutput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String dbschema_tDBOutput_1 = null;
				dbschema_tDBOutput_1 = (String) globalMap.get("schema_" + "tDBConnection_2");

				String tableName_tDBOutput_1 = null;
				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = ("timedimension");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("timedimension");
				}

				int updateKeyCount_tDBOutput_1 = 1;
				if (updateKeyCount_tDBOutput_1 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_1 == 7 && true) {
					throw new RuntimeException("For update, every Schema column can not be a key");
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

				conn_tDBOutput_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_1.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_1.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_1.getAutoCommit()) + ("'."));

				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;

				int count_tDBOutput_1 = 0;
				String update_tDBOutput_1 = "UPDATE \"" + tableName_tDBOutput_1
						+ "\" SET \"year\" = ?,\"month\" = ?,\"date\" = ?,\"hour\" = ?,\"daypart\" = ?,\"weekdayorweekend\" = ? WHERE \"timekey\" = ?";
				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(update_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tDBOutput_2 begin ] start
				 */

				sh("tDBOutput_2");

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"timedimension\"";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "NotFound");

				int tos_count_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_2 = new StringBuilder();
							log4jParamters_tDBOutput_2.append("Parameters:");
							log4jParamters_tDBOutput_2.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE" + " = " + "\"timedimension\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE_ACTION" + " = " + "NONE");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_ALTERNATE_SCHEMA" + " = " + "false");
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
					talendJobLog.addCM("tDBOutput_2", "\"timedimension\"", "tPostgresqlOutput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String dbschema_tDBOutput_2 = null;
				dbschema_tDBOutput_2 = (String) globalMap.get("schema_" + "tDBConnection_2");

				String tableName_tDBOutput_2 = null;
				if (dbschema_tDBOutput_2 == null || dbschema_tDBOutput_2.trim().length() == 0) {
					tableName_tDBOutput_2 = ("timedimension");
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "\".\"" + ("timedimension");
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

				conn_tDBOutput_2 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Uses an existing connection with username '")
							+ (conn_tDBOutput_2.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_tDBOutput_2.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_2.getAutoCommit()) + ("'."));

				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;
				java.lang.StringBuilder sb_tDBOutput_2 = new java.lang.StringBuilder();
				sb_tDBOutput_2.append("INSERT INTO \"").append(tableName_tDBOutput_2).append(
						"\" (\"timekey\",\"year\",\"month\",\"date\",\"hour\",\"daypart\",\"weekdayorweekend\") VALUES (?,?,?,?,?,?,?)");

				String insert_tDBOutput_2 = sb_tDBOutput_2.toString();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Executing '") + (insert_tDBOutput_2) + ("'."));

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
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
				int count_Found_tMap_1 = 0;

				FoundStruct Found_tmp = new FoundStruct();
				int count_NotFound_tMap_1 = 0;

				NotFoundStruct NotFound_tmp = new NotFoundStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				sh("tFileInputDelimited_1");

				s(currentComponent = "tFileInputDelimited_1");

				cLabel = "PrepopulatedTime";

				int tos_count_tFileInputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputDelimited_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputDelimited_1 = new StringBuilder();
							log4jParamters_tFileInputDelimited_1.append("Parameters:");
							log4jParamters_tFileInputDelimited_1.append("USE_EXISTING_DYNAMIC" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1
									.append("FILENAME" + " = " + "\"/Users/ariesslin/Downloads/Time.csv\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CSV_OPTION" + " = " + "true");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CSVROWSEPARATOR" + " = " + "\"\\n\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FIELDSEPARATOR" + " = " + "\",\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ESCAPE_CHAR" + " = " + "\"\"\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TEXT_ENCLOSURE" + " = " + "\"\"\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("HEADER" + " = " + "1");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FOOTER" + " = " + "0");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("LIMIT" + " = " + "");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("REMOVE_EMPTY_ROW" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("UNCOMPRESS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMALL" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMSELECT" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("TimeKey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Hour") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("Date") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("Month") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Year") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Daypart") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("WeekdayOrWeekend")
									+ "}]");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_FIELDS_NUM" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_DATE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENCODING" + " = " + "\"US-ASCII\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENABLE_DECODE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("USE_HEADER_AS_IS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputDelimited_1 - " + (log4jParamters_tFileInputDelimited_1));
						}
					}
					new BytesLimit65535_tFileInputDelimited_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputDelimited_1", "PrepopulatedTime", "tFileInputDelimited");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				int footer_tFileInputDelimited_1 = 0;
				int totalLinetFileInputDelimited_1 = 0;
				int limittFileInputDelimited_1 = -1;
				int lastLinetFileInputDelimited_1 = -1;

				char fieldSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ",").length() > 0) {
					fieldSeparator_tFileInputDelimited_1 = ((String) ",").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_1 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_1 = /** Start field tFileInputDelimited_1:FILENAME */
						"/Users/ariesslin/Downloads/Time.csv"/** End field tFileInputDelimited_1:FILENAME */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_1 = null;

				try {

					String[] rowtFileInputDelimited_1 = null;
					int currentLinetFileInputDelimited_1 = 0;
					int outputLinetFileInputDelimited_1 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_1 = 0;
							if (footer_value_tFileInputDelimited_1 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_1,
									fieldSeparator_tFileInputDelimited_1[0], "US-ASCII");
						} else {
							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_1),
									fieldSeparator_tFileInputDelimited_1[0], "US-ASCII");
						}

						csvReadertFileInputDelimited_1.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
							csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

						csvReadertFileInputDelimited_1.setQuoteChar('"');

						csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						if (footer_tFileInputDelimited_1 > 0) {
							for (totalLinetFileInputDelimited_1 = 0; totalLinetFileInputDelimited_1 < 1; totalLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
							csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_1.readNext()) {

								totalLinetFileInputDelimited_1++;

							}
							int lastLineTemptFileInputDelimited_1 = totalLinetFileInputDelimited_1
									- footer_tFileInputDelimited_1 < 0 ? 0
											: totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1;
							if (lastLinetFileInputDelimited_1 > 0) {
								lastLinetFileInputDelimited_1 = lastLinetFileInputDelimited_1 < lastLineTemptFileInputDelimited_1
										? lastLinetFileInputDelimited_1
										: lastLineTemptFileInputDelimited_1;
							} else {
								lastLinetFileInputDelimited_1 = lastLineTemptFileInputDelimited_1;
							}

							csvReadertFileInputDelimited_1.close();
							if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_1,
										fieldSeparator_tFileInputDelimited_1[0], "US-ASCII");
							} else {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_1),
										fieldSeparator_tFileInputDelimited_1[0], "US-ASCII");
							}
							csvReadertFileInputDelimited_1.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
								csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

							csvReadertFileInputDelimited_1.setQuoteChar('"');

							csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						}

						if (limittFileInputDelimited_1 != 0) {
							for (currentLinetFileInputDelimited_1 = 0; currentLinetFileInputDelimited_1 < 1; currentLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
						}
						csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						log.error("tFileInputDelimited_1 - " + e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					log.info("tFileInputDelimited_1 - Retrieving records from the datasource.");

					while (limittFileInputDelimited_1 != 0 && csvReadertFileInputDelimited_1 != null
							&& csvReadertFileInputDelimited_1.readNext()) {
						rowstate_tFileInputDelimited_1.reset();

						rowtFileInputDelimited_1 = csvReadertFileInputDelimited_1.getValues();

						currentLinetFileInputDelimited_1++;

						if (lastLinetFileInputDelimited_1 > -1
								&& currentLinetFileInputDelimited_1 > lastLinetFileInputDelimited_1) {
							break;
						}
						outputLinetFileInputDelimited_1++;
						if (limittFileInputDelimited_1 > 0
								&& outputLinetFileInputDelimited_1 > limittFileInputDelimited_1) {
							break;
						}

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ",").length() > 0) {
								fieldSeparator_tFileInputDelimited_1_ListType = ((String) ",").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row1.TimeKey = null;

								row1.Hour = null;

								row1.Date = null;

								row1.Month = null;

								row1.Year = null;

								row1.Daypart = null;

								row1.WeekdayOrWeekend = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_1 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_1 = 0;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.TimeKey = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"TimeKey", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.TimeKey = null;

									}

								} else {

									row1.TimeKey = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 1;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Hour = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Hour", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Hour = null;

									}

								} else {

									row1.Hour = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 2;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
													"yyyy-MM-dd");

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Date", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Date = null;

									}

								} else {

									row1.Date = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 3;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Month = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Month", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Month = null;

									}

								} else {

									row1.Month = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 4;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Year = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Year", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Year = null;

									}

								} else {

									row1.Year = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 5;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Daypart = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Daypart = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 6;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.WeekdayOrWeekend = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.WeekdayOrWeekend = null;

								}

							}

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							log.error("tFileInputDelimited_1 - " + e.getMessage());

							System.err.println(e.getMessage());
							row1 = null;

							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						}

						log.debug("tFileInputDelimited_1 - Retrieving the record " + (nb_line_tFileInputDelimited_1 + 1)
								+ ".");

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						s(currentComponent = "tFileInputDelimited_1");

						cLabel = "PrepopulatedTime";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						s(currentComponent = "tFileInputDelimited_1");

						cLabel = "PrepopulatedTime";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */

// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							s(currentComponent = "tMap_1");

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row1", "tFileInputDelimited_1", "PrepopulatedTime", "tFileInputDelimited",
									"tMap_1", "tMap_1", "tMap"

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

								Object exprKeyValue_row2__timekey = row1.TimeKey;
								if (exprKeyValue_row2__timekey == null) {
									hasCasePrimitiveKeyWithNull_tMap_1 = true;
								} else {
									row2HashKey.timekey = (int) (Integer) exprKeyValue_row2__timekey;
								}

								row2HashKey.hashCodeDirty = true;

								if (!hasCasePrimitiveKeyWithNull_tMap_1) { // G_TM_M_091

									tHash_Lookup_row2.lookup(row2HashKey);

								} // G_TM_M_091

								if (hasCasePrimitiveKeyWithNull_tMap_1 || !tHash_Lookup_row2.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2'
								// and it contains more one result from keys : row2.timekey = '" +
								// row2HashKey.timekey + "'");
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

								Found = null;
								NotFound = null;

								if (!rejectedInnerJoin_tMap_1) {

// # Output table : 'Found'
									count_Found_tMap_1++;

									Found_tmp.timekey = row2.timekey;
									Found_tmp.year = row1.Year;
									Found_tmp.month = row1.Month;
									Found_tmp.date = row1.Date;
									Found_tmp.hour = row1.Hour;
									Found_tmp.daypart = row1.Daypart;
									Found_tmp.weekdayorweekend = row1.WeekdayOrWeekend;
									Found = Found_tmp;
									log.debug("tMap_1 - Outputting the record " + count_Found_tMap_1
											+ " of the output table 'Found'.");

								} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'NotFound'
// # Filter conditions 
								if (rejectedInnerJoin_tMap_1) {
									count_NotFound_tMap_1++;

									NotFound_tmp.timekey = row1.TimeKey;
									NotFound_tmp.year = row1.Year;
									NotFound_tmp.month = row1.Month;
									NotFound_tmp.date = row1.Date;
									NotFound_tmp.hour = row1.Hour;
									NotFound_tmp.daypart = row1.Daypart;
									NotFound_tmp.weekdayorweekend = row1.WeekdayOrWeekend;
									NotFound = NotFound_tmp;
									log.debug("tMap_1 - Outputting the record " + count_NotFound_tMap_1
											+ " of the output table 'NotFound'.");

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

// Start of branch "Found"
							if (Found != null) {

								/**
								 * [tDBOutput_1 main ] start
								 */

								s(currentComponent = "tDBOutput_1");

								cLabel = "\"timedimension\"";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "Found", "tMap_1", "tMap_1", "tMap", "tDBOutput_1", "\"timedimension\"",
										"tPostgresqlOutput"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("Found - " + (Found == null ? "" : Found.toLogString()));
								}

								whetherReject_tDBOutput_1 = false;
								if (Found.year == null) {
									pstmt_tDBOutput_1.setNull(1, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(1, Found.year);
								}

								if (Found.month == null) {
									pstmt_tDBOutput_1.setNull(2, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(2, Found.month);
								}

								if (Found.date != null) {
									pstmt_tDBOutput_1.setTimestamp(3, new java.sql.Timestamp(Found.date.getTime()));
								} else {
									pstmt_tDBOutput_1.setNull(3, java.sql.Types.TIMESTAMP);
								}

								if (Found.hour == null) {
									pstmt_tDBOutput_1.setNull(4, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(4, Found.hour);
								}

								if (Found.daypart == null) {
									pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(5, Found.daypart);
								}

								if (Found.weekdayorweekend == null) {
									pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(6, Found.weekdayorweekend);
								}

								pstmt_tDBOutput_1.setInt(7 + count_tDBOutput_1, Found.timekey);

								pstmt_tDBOutput_1.addBatch();
								nb_line_tDBOutput_1++;

								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Adding the record ") + (nb_line_tDBOutput_1)
											+ (" to the ") + ("UPDATE") + (" batch."));
								batchSizeCounter_tDBOutput_1++;

								if ((batchSize_tDBOutput_1 > 0)
										&& (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1)) {
									try {
										int countSum_tDBOutput_1 = 0;

										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("Executing the ") + ("UPDATE") + (" batch."));
										for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
													: countEach_tDBOutput_1);
										}
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("The ") + ("UPDATE")
													+ (" batch execution has succeeded."));
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

										updatedCount_tDBOutput_1 += countSum_tDBOutput_1;

										batchSizeCounter_tDBOutput_1 = 0;
									} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
										globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
										java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
												sqle_tDBOutput_1 = null;
										String errormessage_tDBOutput_1;
										if (ne_tDBOutput_1 != null) {
											// build new exception to provide the original cause
											sqle_tDBOutput_1 = new java.sql.SQLException(
													e_tDBOutput_1.getMessage() + "\ncaused by: "
															+ ne_tDBOutput_1.getMessage(),
													ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
													ne_tDBOutput_1);
											errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
										} else {
											errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
										}

										int countSum_tDBOutput_1 = 0;
										for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
													: countEach_tDBOutput_1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

										updatedCount_tDBOutput_1 += countSum_tDBOutput_1;

										log.error("tDBOutput_1 - " + (errormessage_tDBOutput_1));
										System.err.println(errormessage_tDBOutput_1);

									}
								}

								tos_count_tDBOutput_1++;

								/**
								 * [tDBOutput_1 main ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_begin ] start
								 */

								s(currentComponent = "tDBOutput_1");

								cLabel = "\"timedimension\"";

								/**
								 * [tDBOutput_1 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_end ] start
								 */

								s(currentComponent = "tDBOutput_1");

								cLabel = "\"timedimension\"";

								/**
								 * [tDBOutput_1 process_data_end ] stop
								 */

							} // End of branch "Found"

// Start of branch "NotFound"
							if (NotFound != null) {

								/**
								 * [tDBOutput_2 main ] start
								 */

								s(currentComponent = "tDBOutput_2");

								cLabel = "\"timedimension\"";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "NotFound", "tMap_1", "tMap_1", "tMap", "tDBOutput_2", "\"timedimension\"",
										"tPostgresqlOutput"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("NotFound - " + (NotFound == null ? "" : NotFound.toLogString()));
								}

								whetherReject_tDBOutput_2 = false;
								pstmt_tDBOutput_2.setInt(1, NotFound.timekey);

								if (NotFound.year == null) {
									pstmt_tDBOutput_2.setNull(2, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setInt(2, NotFound.year);
								}

								if (NotFound.month == null) {
									pstmt_tDBOutput_2.setNull(3, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setInt(3, NotFound.month);
								}

								if (NotFound.date != null) {
									pstmt_tDBOutput_2.setTimestamp(4, new java.sql.Timestamp(NotFound.date.getTime()));
								} else {
									pstmt_tDBOutput_2.setNull(4, java.sql.Types.TIMESTAMP);
								}

								if (NotFound.hour == null) {
									pstmt_tDBOutput_2.setNull(5, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setInt(5, NotFound.hour);
								}

								if (NotFound.daypart == null) {
									pstmt_tDBOutput_2.setNull(6, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(6, NotFound.daypart);
								}

								if (NotFound.weekdayorweekend == null) {
									pstmt_tDBOutput_2.setNull(7, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(7, NotFound.weekdayorweekend);
								}

								pstmt_tDBOutput_2.addBatch();
								nb_line_tDBOutput_2++;

								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("Adding the record ") + (nb_line_tDBOutput_2)
											+ (" to the ") + ("INSERT") + (" batch."));
								batchSizeCounter_tDBOutput_2++;

								if ((batchSize_tDBOutput_2 > 0)
										&& (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2)) {
									try {
										int countSum_tDBOutput_2 = 0;

										if (log.isDebugEnabled())
											log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
										for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
											countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
													: countEach_tDBOutput_2);
										}
										if (log.isDebugEnabled())
											log.debug("tDBOutput_2 - " + ("The ") + ("INSERT")
													+ (" batch execution has succeeded."));
										rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

										insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

										batchSizeCounter_tDBOutput_2 = 0;
									} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
										globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
										java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(),
												sqle_tDBOutput_2 = null;
										String errormessage_tDBOutput_2;
										if (ne_tDBOutput_2 != null) {
											// build new exception to provide the original cause
											sqle_tDBOutput_2 = new java.sql.SQLException(
													e_tDBOutput_2.getMessage() + "\ncaused by: "
															+ ne_tDBOutput_2.getMessage(),
													ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(),
													ne_tDBOutput_2);
											errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
										} else {
											errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
										}

										int countSum_tDBOutput_2 = 0;
										for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
											countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
													: countEach_tDBOutput_2);
										}
										rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

										insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

										log.error("tDBOutput_2 - " + (errormessage_tDBOutput_2));
										System.err.println(errormessage_tDBOutput_2);

									}
								}

								tos_count_tDBOutput_2++;

								/**
								 * [tDBOutput_2 main ] stop
								 */

								/**
								 * [tDBOutput_2 process_data_begin ] start
								 */

								s(currentComponent = "tDBOutput_2");

								cLabel = "\"timedimension\"";

								/**
								 * [tDBOutput_2 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_2 process_data_end ] start
								 */

								s(currentComponent = "tDBOutput_2");

								cLabel = "\"timedimension\"";

								/**
								 * [tDBOutput_2 process_data_end ] stop
								 */

							} // End of branch "NotFound"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							s(currentComponent = "tMap_1");

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						s(currentComponent = "tFileInputDelimited_1");

						cLabel = "PrepopulatedTime";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						s(currentComponent = "tFileInputDelimited_1");

						cLabel = "PrepopulatedTime";

						nb_line_tFileInputDelimited_1++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_1 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_1 != null) {
							csvReadertFileInputDelimited_1.close();
						}
					}
					if (csvReadertFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", nb_line_tFileInputDelimited_1);
					}

					log.info("tFileInputDelimited_1 - Retrieved records count: " + nb_line_tFileInputDelimited_1 + ".");

				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
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
				log.debug("tMap_1 - Written records count in the table 'Found': " + count_Found_tMap_1 + ".");
				log.debug("tMap_1 - Written records count in the table 'NotFound': " + count_NotFound_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tFileInputDelimited_1", "PrepopulatedTime", "tFileInputDelimited", "tMap_1", "tMap_1", "tMap",
						"output")) {
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
				 * [tDBOutput_1 end ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"timedimension\"";

				try {
					int countSum_tDBOutput_1 = 0;
					if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("Executing the ") + ("UPDATE") + (" batch."));
						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("The ") + ("UPDATE") + (" batch execution has succeeded."));
					}

					updatedCount_tDBOutput_1 += countSum_tDBOutput_1;

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

					updatedCount_tDBOutput_1 += countSum_tDBOutput_1;

					log.error("tDBOutput_1 - " + (errormessage_tDBOutput_1));
					System.err.println(errormessage_tDBOutput_1);

				}

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");
				}
				resourceMap.put("statementClosed_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "Found", 2, 0, "tMap_1",
						"tMap_1", "tMap", "tDBOutput_1", "\"timedimension\"", "tPostgresqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Done."));

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"timedimension\"";

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

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "NotFound", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tDBOutput_2", "\"timedimension\"", "tPostgresqlOutput",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Done."));

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tFileInputDelimited_1:OnSubjobOk", "",
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

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row2");

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				s(currentComponent = "tFileInputDelimited_1");

				cLabel = "PrepopulatedTime";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				s(currentComponent = "tMap_1");

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				s(currentComponent = "tDBOutput_1");

				cLabel = "\"timedimension\"";

				if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
					if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_1")) != null) {
						pstmtToClose_tDBOutput_1.close();
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				s(currentComponent = "tDBOutput_2");

				cLabel = "\"timedimension\"";

				if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
					if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_2")) != null) {
						pstmtToClose_tDBOutput_2.close();
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

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBCommit_1", "icvQYt_");

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

	public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_TimeLoad = new byte[0];
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

			return "dd-MM-yyyy";

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

				result = prime * result + (int) this.timekey;

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

			if (this.timekey != other.timekey)
				return false;

			return true;
		}

		public void copyDataTo(row2Struct other) {

			other.timekey = this.timekey;
			other.year = this.year;
			other.month = this.month;
			other.date = this.date;
			other.hour = this.hour;
			other.daypart = this.daypart;
			other.weekdayorweekend = this.weekdayorweekend;

		}

		public void copyKeysDataTo(row2Struct other) {

			other.timekey = this.timekey;

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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.timekey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_TimeLoad) {

				try {

					int length = 0;

					this.timekey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.timekey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.timekey);

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

				this.year = readInteger(dis, ois);

				this.month = readInteger(dis, ois);

				this.date = readDate(dis, ois);

				this.hour = readInteger(dis, ois);

				this.daypart = readString(dis, ois);

				this.weekdayorweekend = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.year = readInteger(dis, objectIn);

				this.month = readInteger(dis, objectIn);

				this.date = readDate(dis, objectIn);

				this.hour = readInteger(dis, objectIn);

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

				writeInteger(this.year, dos, oos);

				writeInteger(this.month, dos, oos);

				writeDate(this.date, dos, oos);

				writeInteger(this.hour, dos, oos);

				writeString(this.daypart, dos, oos);

				writeString(this.weekdayorweekend, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeInteger(this.year, dos, objectOut);

				writeInteger(this.month, dos, objectOut);

				writeDate(this.date, dos, objectOut);

				writeInteger(this.hour, dos, objectOut);

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
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.timekey, other.timekey);
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

		mdc("tDBInput_1", "NCE9qW_");

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
				// source node:tDBInput_1 - inputs:(after_tFileInputDelimited_1)
				// outputs:(row2,row2) | target node:tAdvancedHash_row2 - inputs:(row2)
				// outputs:()
				// linked node: tMap_1 - inputs:(row1,row2) outputs:(Found,NotFound)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row2Struct>getLookup(matchingModeEnum_row2);

				globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);

				/**
				 * [tAdvancedHash_row2 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				sh("tDBInput_1");

				s(currentComponent = "tDBInput_1");

				cLabel = "\"timedimension\"";

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
							log4jParamters_tDBInput_1.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERY" + " = "
									+ "\"SELECT    \\\"timedimension\\\".\\\"timekey\\\",    \\\"timedimension\\\".\\\"year\\\",    \\\"timedimension\\\".\\\"month\\\",    \\\"timedimension\\\".\\\"date\\\",    \\\"timedimension\\\".\\\"hour\\\",    \\\"timedimension\\\".\\\"daypart\\\",    \\\"timedimension\\\".\\\"weekdayorweekend\\\"  FROM \\\"timedimension\\\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("timekey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("year") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("month") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("date") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("hour") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("daypart") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("weekdayorweekend")
									+ "}]");
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
					talendJobLog.addCM("tDBInput_1", "\"timedimension\"", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				conn_tDBInput_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBInput_1 != null) {
					if (conn_tDBInput_1.getMetaData() != null) {

						log.debug("tDBInput_1 - Uses an existing connection with username '"
								+ conn_tDBInput_1.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBInput_1.getMetaData().getURL() + ".");

					}
				}

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  \"timedimension\".\"timekey\", \n  \"timedimension\".\"year\", \n  \"timedimension\".\"month\", \n  \"timedimens"
						+ "ion\".\"date\", \n  \"timedimension\".\"hour\", \n  \"timedimension\".\"daypart\", \n  \"timedimension\".\"weekdayorweekend"
						+ "\"\n FROM \"timedimension\"";

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
							row2.timekey = 0;
						} else {

							row2.timekey = rs_tDBInput_1.getInt(1);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row2.year = null;
						} else {

							row2.year = rs_tDBInput_1.getInt(2);
							if (rs_tDBInput_1.wasNull()) {
								row2.year = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row2.month = null;
						} else {

							row2.month = rs_tDBInput_1.getInt(3);
							if (rs_tDBInput_1.wasNull()) {
								row2.month = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row2.date = null;
						} else {

							row2.date = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 4);
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row2.hour = null;
						} else {

							row2.hour = rs_tDBInput_1.getInt(5);
							if (rs_tDBInput_1.wasNull()) {
								row2.hour = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row2.daypart = null;
						} else {

							row2.daypart = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, false);
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row2.weekdayorweekend = null;
						} else {

							row2.weekdayorweekend = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
						}

						log.debug("tDBInput_1 - Retrieving the record " + nb_line_tDBInput_1 + ".");

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"timedimension\"";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"timedimension\"";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row2 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row2");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row2", "tDBInput_1", "\"timedimension\"", "tPostgresqlInput", "tAdvancedHash_row2",
								"tAdvancedHash_row2", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
						}

						row2Struct row2_HashRow = new row2Struct();

						row2_HashRow.timekey = row2.timekey;

						row2_HashRow.year = row2.year;

						row2_HashRow.month = row2.month;

						row2_HashRow.date = row2.date;

						row2_HashRow.hour = row2.hour;

						row2_HashRow.daypart = row2.daypart;

						row2_HashRow.weekdayorweekend = row2.weekdayorweekend;

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
						 * [tDBInput_1 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"timedimension\"";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"timedimension\"";

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
				 * [tAdvancedHash_row2 end ] start
				 */

				s(currentComponent = "tAdvancedHash_row2");

				tHash_Lookup_row2.endPut();

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tDBInput_1", "\"timedimension\"", "tPostgresqlInput", "tAdvancedHash_row2",
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
				 * [tDBInput_1 finally ] start
				 */

				s(currentComponent = "tDBInput_1");

				cLabel = "\"timedimension\"";

				/**
				 * [tDBInput_1 finally ] stop
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

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
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
		final TimeLoad TimeLoadClass = new TimeLoad();

		int exitCode = TimeLoadClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'TimeLoad' - Done.");
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
		log.info("TalendJob: 'TimeLoad' - Start.");

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
		org.slf4j.MDC.put("_jobRepositoryId", "_gnHIIJvcEe-5PpigxqfS-w");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-11-06T23:39:46.716461Z");

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
			java.io.InputStream inContext = TimeLoad.class.getClassLoader()
					.getResourceAsStream("ie6750_project1_grouph/timeload_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = TimeLoad.class.getClassLoader()
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
		log.info("TalendJob: 'TimeLoad' - Started.");
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
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : TimeLoad");
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
		log.info("TalendJob: 'TimeLoad' - Finished - status: " + status + " returnCode: " + returnCode);

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
 * 222989 characters generated by Talend Real-time Big Data Platform on the 7
 * November 2024 at 00:39:46 CET
 ************************************************************************************************/