
package ie6750_project1_grouph.chargingpointscdtype3load_0_1;

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
 * Job: ChargingPointSCDType3Load Purpose: <br>
 * Description: <br>
 * 
 * @author lin.shan1@northeastern.edu
 * @version 8.0.1.20240920_1319-patch
 * @status
 */
public class ChargingPointSCDType3Load implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "ChargingPointSCDType3Load.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(ChargingPointSCDType3Load.class);

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
	private final String jobName = "ChargingPointSCDType3Load";
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
			"_DOjDIJ6DEe-roYUVHEF2JQ", "0.1");
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
					ChargingPointSCDType3Load.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(ChargingPointSCDType3Load.this, new Object[] { e, currentComponent, globalMap });
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

	public void tDBSCD_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

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

	public void tDBCommit_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tWarn_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tWarn_3_onSubJobError(exception, errorComponent, globalMap);
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

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBCommit_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tWarn_3_onSubJobError(Exception exception, String errorComponent,
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

		mdc("tDBConnection_1", "4QVO9i_");

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
									"enc:routine.encryption.key.v1:zuh+FHvAqRibUimXZv7iemJxmNvQmdB0JZ1TkvlGQ5KDcQ==")
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
						"enc:routine.encryption.key.v1:+eTvbBjdXPLevnd7zJKmAGeP66gTV8eq2hUWJTHpaCj/JA==");
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

		mdc("tDBConnection_2", "IcNnDR_");

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
									"enc:routine.encryption.key.v1:I9MS+SOKypxWBC/vi68BX+C3DksCRPhrwcCrFHAckRC4JA==")
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
						"enc:routine.encryption.key.v1:/H37Elar1TW9VtflMag48YnwYd0YeXM0sZW4w5fHPJM+2w==");
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

	public static class StatusFoundStruct implements routines.system.IPersistableRow<StatusFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.chargingpointstatuskey = readInteger(dis);

					this.stationkey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.chargingpointstatuskey = readInteger(dis);

					this.stationkey = readInteger(dis);

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

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.writeObject(this.installationcost);

				// Integer

				writeInteger(this.chargingpointstatuskey, dos);

				// Integer

				writeInteger(this.stationkey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.installationcost);

				// Integer

				writeInteger(this.chargingpointstatuskey, dos);

				// Integer

				writeInteger(this.stationkey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointid=" + String.valueOf(chargepointid));
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
		public int compareTo(StatusFoundStruct other) {

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

	public static class StatusNotFoundStruct implements routines.system.IPersistableRow<StatusNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

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

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.writeObject(this.installationcost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.installationcost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointid=" + String.valueOf(chargepointid));
			sb.append(",chargingcapacity=" + String.valueOf(chargingcapacity));
			sb.append(",chargertype=" + chargertype);
			sb.append(",installationcost=" + String.valueOf(installationcost));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

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

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(StatusNotFoundStruct other) {

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

	public static class StationFoundStruct implements routines.system.IPersistableRow<StationFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];

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

		public int statusid;

		public int getStatusid() {
			return this.statusid;
		}

		public Boolean statusidIsNullable() {
			return false;
		}

		public Boolean statusidIsKey() {
			return false;
		}

		public Integer statusidLength() {
			return 10;
		}

		public Integer statusidPrecision() {
			return 0;
		}

		public String statusidDefault() {

			return "1";

		}

		public String statusidComment() {

			return "";

		}

		public String statusidPattern() {

			return "";

		}

		public String statusidOriginalDbColumnName() {

			return "statusid";

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

			return "nextval('chargingstation_stationkey_seq'::regclass)";

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.statusid = dis.readInt();

					this.stationkey = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.statusid = dis.readInt();

					this.stationkey = dis.readInt();

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

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.writeObject(this.installationcost);

				// int

				dos.writeInt(this.statusid);

				// int

				dos.writeInt(this.stationkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.installationcost);

				// int

				dos.writeInt(this.statusid);

				// int

				dos.writeInt(this.stationkey);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointid=" + String.valueOf(chargepointid));
			sb.append(",chargingcapacity=" + String.valueOf(chargingcapacity));
			sb.append(",chargertype=" + chargertype);
			sb.append(",installationcost=" + String.valueOf(installationcost));
			sb.append(",statusid=" + String.valueOf(statusid));
			sb.append(",stationkey=" + String.valueOf(stationkey));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

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

			sb.append(statusid);

			sb.append("|");

			sb.append(stationkey);

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(StationFoundStruct other) {

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

	public static class StationNotFoundStruct implements routines.system.IPersistableRow<StationNotFoundStruct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = readInteger(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

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

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.writeObject(this.installationcost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.chargepointid, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.installationcost);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointid=" + String.valueOf(chargepointid));
			sb.append(",chargingcapacity=" + String.valueOf(chargingcapacity));
			sb.append(",chargertype=" + chargertype);
			sb.append(",installationcost=" + String.valueOf(installationcost));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

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

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(StationNotFoundStruct other) {

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
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return true;
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

		public String brand;

		public String getBrand() {
			return this.brand;
		}

		public Boolean brandIsNullable() {
			return true;
		}

		public Boolean brandIsKey() {
			return false;
		}

		public Integer brandLength() {
			return 128;
		}

		public Integer brandPrecision() {
			return 0;
		}

		public String brandDefault() {

			return null;

		}

		public String brandComment() {

			return "";

		}

		public String brandPattern() {

			return "";

		}

		public String brandOriginalDbColumnName() {

			return "brand";

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

		public int statusid;

		public int getStatusid() {
			return this.statusid;
		}

		public Boolean statusidIsNullable() {
			return false;
		}

		public Boolean statusidIsKey() {
			return false;
		}

		public Integer statusidLength() {
			return 10;
		}

		public Integer statusidPrecision() {
			return 0;
		}

		public String statusidDefault() {

			return "1";

		}

		public String statusidComment() {

			return "";

		}

		public String statusidPattern() {

			return "";

		}

		public String statusidOriginalDbColumnName() {

			return "statusid";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = dis.readInt();

					this.stationid = dis.readInt();

					this.brand = readString(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.statusid = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = dis.readInt();

					this.stationid = dis.readInt();

					this.brand = readString(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.statusid = dis.readInt();

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

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.stationid);

				// String

				writeString(this.brand, dos);

				// BigDecimal

				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.writeObject(this.installationcost);

				// int

				dos.writeInt(this.statusid);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.stationid);

				// String

				writeString(this.brand, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.installationcost);

				// int

				dos.writeInt(this.statusid);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointid=" + String.valueOf(chargepointid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",brand=" + brand);
			sb.append(",chargingcapacity=" + String.valueOf(chargingcapacity));
			sb.append(",chargertype=" + chargertype);
			sb.append(",installationcost=" + String.valueOf(installationcost));
			sb.append(",statusid=" + String.valueOf(statusid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(chargepointid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			if (brand == null) {
				sb.append("<null>");
			} else {
				sb.append(brand);
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

			sb.append(statusid);

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
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int chargepointid;

		public int getChargepointid() {
			return this.chargepointid;
		}

		public Boolean chargepointidIsNullable() {
			return false;
		}

		public Boolean chargepointidIsKey() {
			return true;
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

		public String brand;

		public String getBrand() {
			return this.brand;
		}

		public Boolean brandIsNullable() {
			return true;
		}

		public Boolean brandIsKey() {
			return false;
		}

		public Integer brandLength() {
			return 128;
		}

		public Integer brandPrecision() {
			return 0;
		}

		public String brandDefault() {

			return null;

		}

		public String brandComment() {

			return "";

		}

		public String brandPattern() {

			return "";

		}

		public String brandOriginalDbColumnName() {

			return "brand";

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

		public int statusid;

		public int getStatusid() {
			return this.statusid;
		}

		public Boolean statusidIsNullable() {
			return false;
		}

		public Boolean statusidIsKey() {
			return false;
		}

		public Integer statusidLength() {
			return 10;
		}

		public Integer statusidPrecision() {
			return 0;
		}

		public String statusidDefault() {

			return "1";

		}

		public String statusidComment() {

			return "";

		}

		public String statusidPattern() {

			return "";

		}

		public String statusidOriginalDbColumnName() {

			return "statusid";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.chargepointid;

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

			if (this.chargepointid != other.chargepointid)
				return false;

			return true;
		}

		public void copyDataTo(after_tDBInput_1Struct other) {

			other.chargepointid = this.chargepointid;
			other.stationid = this.stationid;
			other.brand = this.brand;
			other.chargingcapacity = this.chargingcapacity;
			other.chargertype = this.chargertype;
			other.installationcost = this.installationcost;
			other.statusid = this.statusid;

		}

		public void copyKeysDataTo(after_tDBInput_1Struct other) {

			other.chargepointid = this.chargepointid;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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
				if (length > commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length) {
					if (length < 1024 && commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load.length == 0) {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[1024];
					} else {
						commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length);
				strReturn = new String(commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load, 0, length,
						utf8Charset);
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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = dis.readInt();

					this.stationid = dis.readInt();

					this.brand = readString(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.statusid = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargepointid = dis.readInt();

					this.stationid = dis.readInt();

					this.brand = readString(dis);

					this.chargingcapacity = (BigDecimal) dis.readObject();

					this.chargertype = readString(dis);

					this.installationcost = (BigDecimal) dis.readObject();

					this.statusid = dis.readInt();

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

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.stationid);

				// String

				writeString(this.brand, dos);

				// BigDecimal

				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.writeObject(this.installationcost);

				// int

				dos.writeInt(this.statusid);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.chargepointid);

				// int

				dos.writeInt(this.stationid);

				// String

				writeString(this.brand, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.chargingcapacity);

				// String

				writeString(this.chargertype, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.installationcost);

				// int

				dos.writeInt(this.statusid);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("chargepointid=" + String.valueOf(chargepointid));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",brand=" + brand);
			sb.append(",chargingcapacity=" + String.valueOf(chargingcapacity));
			sb.append(",chargertype=" + chargertype);
			sb.append(",installationcost=" + String.valueOf(installationcost));
			sb.append(",statusid=" + String.valueOf(statusid));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(chargepointid);

			sb.append("|");

			sb.append(stationid);

			sb.append("|");

			if (brand == null) {
				sb.append("<null>");
			} else {
				sb.append(brand);
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

			sb.append(statusid);

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tDBInput_1Struct other) {

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

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_1", "X7gudw_");

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

				row1Struct row1 = new row1Struct();
				StationFoundStruct StationFound = new StationFoundStruct();
				StatusFoundStruct StatusFound = new StatusFoundStruct();
				StatusNotFoundStruct StatusNotFound = new StatusNotFoundStruct();
				StationNotFoundStruct StationNotFound = new StationNotFoundStruct();

				/**
				 * [tDBSCD_1 begin ] start
				 */

				sh("tDBSCD_1");

				s(currentComponent = "tDBSCD_1");

				cLabel = "\"chargingpoint\"";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "StatusFound");

				int tos_count_tDBSCD_1 = 0;

				if (enableLogStash) {
					talendJobLog.addCM("tDBSCD_1", "\"chargingpoint\"", "tPostgresqlSCD");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				class SCDSK_tDBSCD_1 {
					private int hashCode;
					public boolean hashCodeDirty = true;
					Integer chargepointid;

					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final SCDSK_tDBSCD_1 other = (SCDSK_tDBSCD_1) obj;
						if (this.chargepointid == null) {
							if (other.chargepointid != null)
								return false;
						} else if (!this.chargepointid.equals(other.chargepointid))
							return false;

						return true;
					}

					public int hashCode() {
						if (hashCodeDirty) {
							int prime = 31;
							hashCode = prime * hashCode + (chargepointid == null ? 0 : chargepointid.hashCode());
							hashCodeDirty = false;
						}
						return hashCode;
					}
				}

				class SCDStruct_tDBSCD_1 {
					private String chargertype;
					private BigDecimal chargingcapacity;
					private BigDecimal installationcost;
					private Integer stationkey;
					private Integer chargingpointstatuskey;
					private Integer previous_chargingpointstatuskey;
				}

				int nb_line_update_tDBSCD_1 = 0;
				int nb_line_inserted_tDBSCD_1 = 0;
				int nb_line_rejected_tDBSCD_1 = 0;
				String dbSchema_tDBSCD_1 = "";
				java.lang.Class.forName("org.postgresql.Driver");
				String connectionString_tDBSCD_1 = "jdbc:postgresql://" + "34.56.183.144" + ":" + "5432" + "/"
						+ "ev_station_dw";

				final String decryptedPassword_tDBSCD_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:QXyDbHrdhUogNJ9q95cBTia0CepKoSnGOqRqv282vOZ2gg==");

				java.sql.Connection connection_tDBSCD_1 = java.sql.DriverManager
						.getConnection(connectionString_tDBSCD_1, "postgres", decryptedPassword_tDBSCD_1);
				String tableName_tDBSCD_1 = null;
				if (dbSchema_tDBSCD_1 == null || dbSchema_tDBSCD_1.trim().length() == 0) {
					tableName_tDBSCD_1 = "chargingpoint";
				} else {
					tableName_tDBSCD_1 = dbSchema_tDBSCD_1 + "\".\"" + "chargingpoint";
				}
				java.sql.Timestamp timestamp_tDBSCD_1 = null;
				String tmpValue_tDBSCD_1 = null;
				String search_tDBSCD_1 = "SELECT \"chargepointid\", \"chargertype\", \"chargingcapacity\", \"installationcost\", \"stationkey\", \"chargingpointstatuskey\", \"previous_chargingpointstatuskey\" FROM \""
						+ tableName_tDBSCD_1 + "\"";
				java.sql.Statement statement_tDBSCD_1 = connection_tDBSCD_1.createStatement();
				java.sql.ResultSet resultSet_tDBSCD_1 = statement_tDBSCD_1.executeQuery(search_tDBSCD_1);
				java.util.Map<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1> cache_tDBSCD_1 = new java.util.HashMap<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1>();
				while (resultSet_tDBSCD_1.next()) {
					SCDSK_tDBSCD_1 sk_tDBSCD_1 = new SCDSK_tDBSCD_1();
					SCDStruct_tDBSCD_1 row_tDBSCD_1 = new SCDStruct_tDBSCD_1();
					if (resultSet_tDBSCD_1.getObject(1) != null) {
						sk_tDBSCD_1.chargepointid = resultSet_tDBSCD_1.getInt(1);
					}
					if (resultSet_tDBSCD_1.getObject(2) != null) {
						row_tDBSCD_1.chargertype = resultSet_tDBSCD_1.getString(2);
					}
					if (resultSet_tDBSCD_1.getObject(3) != null) {
						row_tDBSCD_1.chargingcapacity = resultSet_tDBSCD_1.getBigDecimal(3);
					}
					if (resultSet_tDBSCD_1.getObject(4) != null) {
						row_tDBSCD_1.installationcost = resultSet_tDBSCD_1.getBigDecimal(4);
					}
					if (resultSet_tDBSCD_1.getObject(5) != null) {
						row_tDBSCD_1.stationkey = resultSet_tDBSCD_1.getInt(5);
					}
					if (resultSet_tDBSCD_1.getObject(6) != null) {
						row_tDBSCD_1.chargingpointstatuskey = resultSet_tDBSCD_1.getInt(6);
					}
					if (resultSet_tDBSCD_1.getObject(7) != null) {
						row_tDBSCD_1.previous_chargingpointstatuskey = resultSet_tDBSCD_1.getInt(7);
					}
					cache_tDBSCD_1.put(sk_tDBSCD_1, row_tDBSCD_1);
				}
				resultSet_tDBSCD_1.close();
				statement_tDBSCD_1.close();
				String insertionSQL_tDBSCD_1 = "INSERT INTO \"" + tableName_tDBSCD_1
						+ "\"(\"chargepointkey\", \"chargepointid\", \"chargertype\", \"chargingcapacity\", \"installationcost\", \"stationkey\", \"chargingpointstatuskey\") VALUES("
						+ "nextval('"
						+ ((dbSchema_tDBSCD_1 != null && dbSchema_tDBSCD_1.trim().length() != 0)
								? dbSchema_tDBSCD_1 + "."
								: "")
						+ "chargingpoint_chargepointkey_seq" + "')" + ", ?, ?, ?, ?, ?, ?)";
				java.sql.PreparedStatement insertionStatement_tDBSCD_1 = connection_tDBSCD_1
						.prepareStatement(insertionSQL_tDBSCD_1);
				String updateSQLForType1_tDBSCD_1 = "UPDATE \"" + tableName_tDBSCD_1
						+ "\" SET \"stationkey\" = ? WHERE \"chargepointid\" = ?";
				java.sql.PreparedStatement updateForType1_tDBSCD_1 = connection_tDBSCD_1
						.prepareStatement(updateSQLForType1_tDBSCD_1);
				String updateSQLForType3_tDBSCD_1 = "UPDATE \"" + tableName_tDBSCD_1
						+ "\" SET \"chargingpointstatuskey\" = ?, \"previous_chargingpointstatuskey\" = ? WHERE \"chargepointid\" = ?";
				java.sql.PreparedStatement updateForType3_tDBSCD_1 = connection_tDBSCD_1
						.prepareStatement(updateSQLForType3_tDBSCD_1);

				SCDSK_tDBSCD_1 lookUpKey_tDBSCD_1 = null;
				SCDStruct_tDBSCD_1 lookUpValue_tDBSCD_1 = null;

				/**
				 * [tDBSCD_1 begin ] stop
				 */

				/**
				 * [tWarn_2 begin ] start
				 */

				sh("tWarn_2");

				s(currentComponent = "tWarn_2");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "StatusNotFound");

				int tos_count_tWarn_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_2 = new StringBuilder();
							log4jParamters_tWarn_2.append("Parameters:");
							log4jParamters_tWarn_2.append("MESSAGE" + " = "
									+ "\"Warning: Status not found for ChargingPoint '\" + StatusNotFound.chargepointid + \"'. Please verify the status data.\"");
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

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "StationFound");

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
				int count_StationFound_tMap_2 = 0;

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
				int count_StatusFound_tMap_2 = 0;

				StatusFoundStruct StatusFound_tmp = new StatusFoundStruct();
				int count_StatusNotFound_tMap_2 = 0;

				StatusNotFoundStruct StatusNotFound_tmp = new StatusNotFoundStruct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tWarn_1 begin ] start
				 */

				sh("tWarn_1");

				s(currentComponent = "tWarn_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "StationNotFound");

				int tos_count_tWarn_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_1 = new StringBuilder();
							log4jParamters_tWarn_1.append("Parameters:");
							log4jParamters_tWarn_1.append("MESSAGE" + " = "
									+ "\"Warning: Station not found for ChargingPoint '\" + StationNotFound.chargepointid + \"'. Please verify the station data.\"");
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
				int count_StationFound_tMap_1 = 0;

				StationFoundStruct StationFound_tmp = new StationFoundStruct();
				int count_StationNotFound_tMap_1 = 0;

				StationNotFoundStruct StationNotFound_tmp = new StationNotFoundStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				sh("tDBInput_1");

				s(currentComponent = "tDBInput_1");

				cLabel = "\"chargingpoint\"";

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
									+ "\"SELECT    \\\"chargingpoint\\\".\\\"chargepointid\\\",    \\\"chargingpoint\\\".\\\"stationid\\\",    \\\"chargingpoint\\\".\\\"brand\\\",    \\\"chargingpoint\\\".\\\"chargingcapacity\\\",    \\\"chargingpoint\\\".\\\"chargertype\\\",    \\\"chargingpoint\\\".\\\"installationcost\\\",    \\\"chargingpoint\\\".\\\"statusid\\\"  FROM \\\"chargingpoint\\\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargepointid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("stationid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("brand") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("chargingcapacity")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("chargertype") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("installationcost") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("statusid") + "}]");
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
					talendJobLog.addCM("tDBInput_1", "\"chargingpoint\"", "tPostgresqlInput");
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

				String dbquery_tDBInput_1 = "SELECT \n  \"chargingpoint\".\"chargepointid\", \n  \"chargingpoint\".\"stationid\", \n  \"chargingpoint\".\"brand\", \n  "
						+ "\"chargingpoint\".\"chargingcapacity\", \n  \"chargingpoint\".\"chargertype\", \n  \"chargingpoint\".\"installationcost\","
						+ " \n  \"chargingpoint\".\"statusid\"\n FROM \"chargingpoint\"";

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
							row1.chargepointid = 0;
						} else {

							row1.chargepointid = rs_tDBInput_1.getInt(1);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.stationid = 0;
						} else {

							row1.stationid = rs_tDBInput_1.getInt(2);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.brand = null;
						} else {

							row1.brand = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.chargingcapacity = null;
						} else {

							row1.chargingcapacity = rs_tDBInput_1.getBigDecimal(4);
							if (rs_tDBInput_1.wasNull()) {
								row1.chargingcapacity = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.chargertype = null;
						} else {

							row1.chargertype = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, false);
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.installationcost = null;
						} else {

							row1.installationcost = rs_tDBInput_1.getBigDecimal(6);
							if (rs_tDBInput_1.wasNull()) {
								row1.installationcost = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.statusid = 0;
						} else {

							row1.statusid = rs_tDBInput_1.getInt(7);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
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

						cLabel = "\"chargingpoint\"";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"chargingpoint\"";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tMap_1 main ] start
						 */

						s(currentComponent = "tMap_1");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row1", "tDBInput_1", "\"chargingpoint\"", "tPostgresqlInput", "tMap_1", "tMap_1",
								"tMap"

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

							row2HashKey.stationid = row1.stationid;

							row2HashKey.hashCodeDirty = true;

							tHash_Lookup_row2.lookup(row2HashKey);

							if (!tHash_Lookup_row2.hasNext()) { // G_TM_M_090

								rejectedInnerJoin_tMap_1 = true;

							} // G_TM_M_090

						} // G_TM_M_020

						if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G 071

							// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2'
							// and it contains more one result from keys : row2.stationid = '" +
							// row2HashKey.stationid + "'");
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

							StationFound = null;
							StationNotFound = null;

							if (!rejectedInnerJoin_tMap_1) {

// # Output table : 'StationFound'
								count_StationFound_tMap_1++;

								StationFound_tmp.chargepointid = row1.chargepointid;
								StationFound_tmp.chargingcapacity = row1.chargingcapacity;
								StationFound_tmp.chargertype = row1.chargertype;
								StationFound_tmp.installationcost = row1.installationcost;
								StationFound_tmp.statusid = row1.statusid;
								StationFound_tmp.stationkey = row2.stationkey;
								StationFound = StationFound_tmp;
								log.debug("tMap_1 - Outputting the record " + count_StationFound_tMap_1
										+ " of the output table 'StationFound'.");

							} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'StationNotFound'
// # Filter conditions 
							if (rejectedInnerJoin_tMap_1) {
								count_StationNotFound_tMap_1++;

								StationNotFound_tmp.chargepointid = row1.chargepointid;
								StationNotFound_tmp.chargingcapacity = row1.chargingcapacity;
								StationNotFound_tmp.chargertype = row1.chargertype;
								StationNotFound_tmp.installationcost = row1.installationcost;
								StationNotFound = StationNotFound_tmp;
								log.debug("tMap_1 - Outputting the record " + count_StationNotFound_tMap_1
										+ " of the output table 'StationNotFound'.");

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

// Start of branch "StationFound"
						if (StationFound != null) {

							/**
							 * [tMap_2 main ] start
							 */

							s(currentComponent = "tMap_2");

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "StationFound", "tMap_1", "tMap_1", "tMap", "tMap_2", "tMap_2", "tMap"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("StationFound - " + (StationFound == null ? "" : StationFound.toLogString()));
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

								row3HashKey.chargingpointstatusid = StationFound.statusid;

								row3HashKey.hashCodeDirty = true;

								tHash_Lookup_row3.lookup(row3HashKey);

								if (!tHash_Lookup_row3.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_2 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3'
								// and it contains more one result from keys : row3.chargingpointstatusid = '" +
								// row3HashKey.chargingpointstatusid + "'");
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

								StatusFound = null;
								StatusNotFound = null;

								if (!rejectedInnerJoin_tMap_2) {

// # Output table : 'StatusFound'
									count_StatusFound_tMap_2++;

									StatusFound_tmp.chargepointid = StationFound.chargepointid;
									StatusFound_tmp.chargingcapacity = StationFound.chargingcapacity;
									StatusFound_tmp.chargertype = StationFound.chargertype;
									StatusFound_tmp.installationcost = StationFound.installationcost;
									StatusFound_tmp.chargingpointstatuskey = row3.chargingpointstatuskey;
									StatusFound_tmp.stationkey = StationFound.stationkey;
									StatusFound = StatusFound_tmp;
									log.debug("tMap_2 - Outputting the record " + count_StatusFound_tMap_2
											+ " of the output table 'StatusFound'.");

								} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'StatusNotFound'
// # Filter conditions 
								if (rejectedInnerJoin_tMap_2) {
									count_StatusNotFound_tMap_2++;

									StatusNotFound_tmp.chargepointid = StationFound.chargepointid;
									StatusNotFound_tmp.chargingcapacity = StationFound.chargingcapacity;
									StatusNotFound_tmp.chargertype = StationFound.chargertype;
									StatusNotFound_tmp.installationcost = StationFound.installationcost;
									StatusNotFound = StatusNotFound_tmp;
									log.debug("tMap_2 - Outputting the record " + count_StatusNotFound_tMap_2
											+ " of the output table 'StatusNotFound'.");

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

// Start of branch "StatusFound"
							if (StatusFound != null) {

								/**
								 * [tDBSCD_1 main ] start
								 */

								s(currentComponent = "tDBSCD_1");

								cLabel = "\"chargingpoint\"";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "StatusFound", "tMap_2", "tMap_2", "tMap", "tDBSCD_1", "\"chargingpoint\"",
										"tPostgresqlSCD"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace(
											"StatusFound - " + (StatusFound == null ? "" : StatusFound.toLogString()));
								}

								try {
									lookUpKey_tDBSCD_1 = new SCDSK_tDBSCD_1();
									lookUpKey_tDBSCD_1.chargepointid = StatusFound.chargepointid;
									lookUpKey_tDBSCD_1.hashCodeDirty = true;
									lookUpValue_tDBSCD_1 = cache_tDBSCD_1.get(lookUpKey_tDBSCD_1);
									if (lookUpValue_tDBSCD_1 == null) {
										lookUpValue_tDBSCD_1 = new SCDStruct_tDBSCD_1();

										if (StatusFound.chargepointid == null) {
											insertionStatement_tDBSCD_1.setNull(1, java.sql.Types.INTEGER);
										} else {
											insertionStatement_tDBSCD_1.setInt(1, StatusFound.chargepointid);
										}

										if (StatusFound.chargertype == null) {
											insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.VARCHAR);
										} else {
											insertionStatement_tDBSCD_1.setString(2, StatusFound.chargertype);
										}

										insertionStatement_tDBSCD_1.setBigDecimal(3, StatusFound.chargingcapacity);

										insertionStatement_tDBSCD_1.setBigDecimal(4, StatusFound.installationcost);

										if (StatusFound.stationkey == null) {
											insertionStatement_tDBSCD_1.setNull(5, java.sql.Types.INTEGER);
										} else {
											insertionStatement_tDBSCD_1.setInt(5, StatusFound.stationkey);
										}

										if (StatusFound.chargingpointstatuskey == null) {
											insertionStatement_tDBSCD_1.setNull(6, java.sql.Types.INTEGER);
										} else {
											insertionStatement_tDBSCD_1.setInt(6, StatusFound.chargingpointstatuskey);
										}

										nb_line_inserted_tDBSCD_1 += insertionStatement_tDBSCD_1.executeUpdate();
									} else {
										if ((lookUpValue_tDBSCD_1.stationkey == null && StatusFound.stationkey != null)
												|| (lookUpValue_tDBSCD_1.stationkey != null
														&& !lookUpValue_tDBSCD_1.stationkey
																.equals(StatusFound.stationkey))) {
											if (StatusFound.stationkey == null) {
												updateForType1_tDBSCD_1.setNull(1, java.sql.Types.INTEGER);
											} else {
												updateForType1_tDBSCD_1.setInt(1, StatusFound.stationkey);
											}

											if (StatusFound.chargepointid == null) {
												updateForType1_tDBSCD_1.setNull(2, java.sql.Types.INTEGER);
											} else {
												updateForType1_tDBSCD_1.setInt(2, StatusFound.chargepointid);
											}

											nb_line_update_tDBSCD_1 += updateForType1_tDBSCD_1.executeUpdate();
										}
										if ((lookUpValue_tDBSCD_1.chargingpointstatuskey == null
												&& StatusFound.chargingpointstatuskey != null)
												|| (lookUpValue_tDBSCD_1.chargingpointstatuskey != null
														&& !lookUpValue_tDBSCD_1.chargingpointstatuskey
																.equals(StatusFound.chargingpointstatuskey))) {
											if (StatusFound.chargingpointstatuskey == null) {
												updateForType3_tDBSCD_1.setNull(1, java.sql.Types.INTEGER);
											} else {
												updateForType3_tDBSCD_1.setInt(1, StatusFound.chargingpointstatuskey);
											}

											if (lookUpValue_tDBSCD_1.chargingpointstatuskey == null) {
												updateForType3_tDBSCD_1.setNull(2, java.sql.Types.INTEGER);
											} else {
												updateForType3_tDBSCD_1.setInt(2,
														lookUpValue_tDBSCD_1.chargingpointstatuskey);
											}

											if (StatusFound.chargepointid == null) {
												updateForType3_tDBSCD_1.setNull(3, java.sql.Types.INTEGER);
											} else {
												updateForType3_tDBSCD_1.setInt(3, StatusFound.chargepointid);
											}

											nb_line_update_tDBSCD_1 += updateForType3_tDBSCD_1.executeUpdate();
										}
									}

								} catch (java.lang.Exception e) {// catch
									globalMap.put("tDBSCD_1_ERROR_MESSAGE", e.getMessage());

									System.err.print(e.getMessage());
								} // end catch

								lookUpValue_tDBSCD_1.stationkey = StatusFound.stationkey;
								lookUpValue_tDBSCD_1.chargingpointstatuskey = StatusFound.chargingpointstatuskey;
								cache_tDBSCD_1.put(lookUpKey_tDBSCD_1, lookUpValue_tDBSCD_1);

								tos_count_tDBSCD_1++;

								/**
								 * [tDBSCD_1 main ] stop
								 */

								/**
								 * [tDBSCD_1 process_data_begin ] start
								 */

								s(currentComponent = "tDBSCD_1");

								cLabel = "\"chargingpoint\"";

								/**
								 * [tDBSCD_1 process_data_begin ] stop
								 */

								/**
								 * [tDBSCD_1 process_data_end ] start
								 */

								s(currentComponent = "tDBSCD_1");

								cLabel = "\"chargingpoint\"";

								/**
								 * [tDBSCD_1 process_data_end ] stop
								 */

							} // End of branch "StatusFound"

// Start of branch "StatusNotFound"
							if (StatusNotFound != null) {

								/**
								 * [tWarn_2 main ] start
								 */

								s(currentComponent = "tWarn_2");

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "StatusNotFound", "tMap_2", "tMap_2", "tMap", "tWarn_2", "tWarn_2", "tWarn"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("StatusNotFound - "
											+ (StatusNotFound == null ? "" : StatusNotFound.toLogString()));
								}

								try {

									resumeUtil.addLog("USER_DEF_LOG", "NODE:tWarn_2", "",
											Thread.currentThread().getId() + "", "WARN", "",
											"Warning: Status not found for ChargingPoint '"
													+ StatusNotFound.chargepointid
													+ "'. Please verify the status data.",
											"", "");
									log.warn("tWarn_2 - " + ("Message: ")
											+ ("Warning: Status not found for ChargingPoint '"
													+ StatusNotFound.chargepointid
													+ "'. Please verify the status data.")
											+ (". Code: ") + (42));
									globalMap.put("tWarn_2_WARN_MESSAGES",
											"Warning: Status not found for ChargingPoint '"
													+ StatusNotFound.chargepointid
													+ "'. Please verify the status data.");
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

							} // End of branch "StatusNotFound"

							/**
							 * [tMap_2 process_data_end ] start
							 */

							s(currentComponent = "tMap_2");

							/**
							 * [tMap_2 process_data_end ] stop
							 */

						} // End of branch "StationFound"

// Start of branch "StationNotFound"
						if (StationNotFound != null) {

							/**
							 * [tWarn_1 main ] start
							 */

							s(currentComponent = "tWarn_1");

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "StationNotFound", "tMap_1", "tMap_1", "tMap", "tWarn_1", "tWarn_1", "tWarn"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("StationNotFound - "
										+ (StationNotFound == null ? "" : StationNotFound.toLogString()));
							}

							try {

								resumeUtil.addLog(
										"USER_DEF_LOG", "NODE:tWarn_1", "", Thread.currentThread().getId() + "", "WARN",
										"", "Warning: Station not found for ChargingPoint '"
												+ StationNotFound.chargepointid + "'. Please verify the station data.",
										"", "");
								log.warn("tWarn_1 - " + ("Message: ")
										+ ("Warning: Station not found for ChargingPoint '"
												+ StationNotFound.chargepointid + "'. Please verify the station data.")
										+ (". Code: ") + (42));
								globalMap.put("tWarn_1_WARN_MESSAGES", "Warning: Station not found for ChargingPoint '"
										+ StationNotFound.chargepointid + "'. Please verify the station data.");
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

						} // End of branch "StationNotFound"

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

						cLabel = "\"chargingpoint\"";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"chargingpoint\"";

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
				log.debug("tMap_1 - Written records count in the table 'StationFound': " + count_StationFound_tMap_1
						+ ".");
				log.debug("tMap_1 - Written records count in the table 'StationNotFound': "
						+ count_StationNotFound_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tDBInput_1", "\"chargingpoint\"", "tPostgresqlInput", "tMap_1", "tMap_1", "tMap", "output")) {
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
				log.debug(
						"tMap_2 - Written records count in the table 'StatusFound': " + count_StatusFound_tMap_2 + ".");
				log.debug("tMap_2 - Written records count in the table 'StatusNotFound': " + count_StatusNotFound_tMap_2
						+ ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "StationFound", 2, 0,
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
				 * [tDBSCD_1 end ] start
				 */

				s(currentComponent = "tDBSCD_1");

				cLabel = "\"chargingpoint\"";

				insertionStatement_tDBSCD_1.close();
				updateForType1_tDBSCD_1.close();
				updateForType3_tDBSCD_1.close();
				if (connection_tDBSCD_1 != null && !connection_tDBSCD_1.isClosed()) {
					connection_tDBSCD_1.close();
				}
				globalMap.put("tDBSCD_1_NB_LINE_UPDATED", nb_line_update_tDBSCD_1);
				globalMap.put("tDBSCD_1_NB_LINE_INSERTED", nb_line_inserted_tDBSCD_1);
				globalMap.put("tDBSCD_1_NB_LINE_REJECTED", nb_line_rejected_tDBSCD_1);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "StatusFound", 2, 0,
						"tMap_2", "tMap_2", "tMap", "tDBSCD_1", "\"chargingpoint\"", "tPostgresqlSCD", "output")) {
					talendJobLogProcess(globalMap);
				}

				ok_Hash.put("tDBSCD_1", true);
				end_Hash.put("tDBSCD_1", System.currentTimeMillis());

				/**
				 * [tDBSCD_1 end ] stop
				 */

				/**
				 * [tWarn_2 end ] start
				 */

				s(currentComponent = "tWarn_2");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "StatusNotFound", 2, 0,
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

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "StationNotFound", 2, 0,
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

			// free memory for "tMap_2"
			globalMap.remove("tHash_Lookup_row3");

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row2");

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				s(currentComponent = "tDBInput_1");

				cLabel = "\"chargingpoint\"";

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
				 * [tDBSCD_1 finally ] start
				 */

				s(currentComponent = "tDBSCD_1");

				cLabel = "\"chargingpoint\"";

				/**
				 * [tDBSCD_1 finally ] stop
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

	public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBCommit_1", "5Wf0EH_");

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
							log4jParamters_tDBCommit_1.append("CLOSE" + " = " + "false");
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

					log.debug("tDBCommit_1 - Connection 'tDBConnection_2' starting to commit.");

					conn_tDBCommit_1.commit();

					log.debug("tDBCommit_1 - Connection 'tDBConnection_2' commit has succeeded.");

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

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBCommit_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk4", 0, "ok");
			}

			tWarn_3Process(globalMap);

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

	public void tWarn_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tWarn_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tWarn_3", "R8txXU_");

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
				 * [tWarn_3 begin ] start
				 */

				sh("tWarn_3");

				s(currentComponent = "tWarn_3");

				int tos_count_tWarn_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tWarn_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tWarn_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tWarn_3 = new StringBuilder();
							log4jParamters_tWarn_3.append("Parameters:");
							log4jParamters_tWarn_3.append("MESSAGE" + " = " + "\"ChargingPoint Load is done!\"");
							log4jParamters_tWarn_3.append(" | ");
							log4jParamters_tWarn_3.append("CODE" + " = " + "200");
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
				 * [tWarn_3 main ] start
				 */

				s(currentComponent = "tWarn_3");

				try {

					resumeUtil.addLog("USER_DEF_LOG", "NODE:tWarn_3", "", Thread.currentThread().getId() + "", "WARN",
							"", "ChargingPoint Load is done!", "", "");
					log.warn("tWarn_3 - " + ("Message: ") + ("ChargingPoint Load is done!") + (". Code: ") + (200));
					globalMap.put("tWarn_3_WARN_MESSAGES", "ChargingPoint Load is done!");
					globalMap.put("tWarn_3_WARN_PRIORITY", 4);
					globalMap.put("tWarn_3_WARN_CODE", 200);

				} catch (Exception e_tWarn_3) {
					globalMap.put("tWarn_3_ERROR_MESSAGE", e_tWarn_3.getMessage());
					logIgnoredError(
							String.format("tWarn_3 - tWarn failed to log message due to internal error: %s", e_tWarn_3),
							e_tWarn_3);
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

				/**
				 * [tWarn_3 end ] start
				 */

				s(currentComponent = "tWarn_3");

				if (log.isDebugEnabled())
					log.debug("tWarn_3 - " + ("Done."));

				ok_Hash.put("tWarn_3", true);
				end_Hash.put("tWarn_3", System.currentTimeMillis());

				/**
				 * [tWarn_3 end ] stop
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
				 * [tWarn_3 finally ] start
				 */

				s(currentComponent = "tWarn_3");

				/**
				 * [tWarn_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tWarn_3_SUBPROCESS_STATE", 1);
	}

	public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

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

			return "nextval('chargingstation_stationkey_seq'::regclass)";

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

		public Integer stationid;

		public Integer getStationid() {
			return this.stationid;
		}

		public Boolean stationidIsNullable() {
			return true;
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

		public String stationname;

		public String getStationname() {
			return this.stationname;
		}

		public Boolean stationnameIsNullable() {
			return true;
		}

		public Boolean stationnameIsKey() {
			return false;
		}

		public Integer stationnameLength() {
			return 128;
		}

		public Integer stationnamePrecision() {
			return 0;
		}

		public String stationnameDefault() {

			return null;

		}

		public String stationnameComment() {

			return "";

		}

		public String stationnamePattern() {

			return "";

		}

		public String stationnameOriginalDbColumnName() {

			return "stationname";

		}

		public Integer citykey;

		public Integer getCitykey() {
			return this.citykey;
		}

		public Boolean citykeyIsNullable() {
			return true;
		}

		public Boolean citykeyIsKey() {
			return false;
		}

		public Integer citykeyLength() {
			return 10;
		}

		public Integer citykeyPrecision() {
			return 0;
		}

		public String citykeyDefault() {

			return null;

		}

		public String citykeyComment() {

			return "";

		}

		public String citykeyPattern() {

			return "";

		}

		public String citykeyOriginalDbColumnName() {

			return "citykey";

		}

		public Integer maxchargingpoints;

		public Integer getMaxchargingpoints() {
			return this.maxchargingpoints;
		}

		public Boolean maxchargingpointsIsNullable() {
			return true;
		}

		public Boolean maxchargingpointsIsKey() {
			return false;
		}

		public Integer maxchargingpointsLength() {
			return 10;
		}

		public Integer maxchargingpointsPrecision() {
			return 0;
		}

		public String maxchargingpointsDefault() {

			return null;

		}

		public String maxchargingpointsComment() {

			return "";

		}

		public String maxchargingpointsPattern() {

			return "";

		}

		public String maxchargingpointsOriginalDbColumnName() {

			return "maxchargingpoints";

		}

		public Integer stationstatuskey;

		public Integer getStationstatuskey() {
			return this.stationstatuskey;
		}

		public Boolean stationstatuskeyIsNullable() {
			return true;
		}

		public Boolean stationstatuskeyIsKey() {
			return false;
		}

		public Integer stationstatuskeyLength() {
			return 10;
		}

		public Integer stationstatuskeyPrecision() {
			return 0;
		}

		public String stationstatuskeyDefault() {

			return null;

		}

		public String stationstatuskeyComment() {

			return "";

		}

		public String stationstatuskeyPattern() {

			return "";

		}

		public String stationstatuskeyOriginalDbColumnName() {

			return "stationstatuskey";

		}

		public java.util.Date start_date;

		public java.util.Date getStart_date() {
			return this.start_date;
		}

		public Boolean start_dateIsNullable() {
			return false;
		}

		public Boolean start_dateIsKey() {
			return false;
		}

		public Integer start_dateLength() {
			return 13;
		}

		public Integer start_datePrecision() {
			return 0;
		}

		public String start_dateDefault() {

			return "'CURRENT_DATE'";

		}

		public String start_dateComment() {

			return "";

		}

		public String start_datePattern() {

			return "dd-MM-yyyy";

		}

		public String start_dateOriginalDbColumnName() {

			return "start_date";

		}

		public java.util.Date end_date;

		public java.util.Date getEnd_date() {
			return this.end_date;
		}

		public Boolean end_dateIsNullable() {
			return true;
		}

		public Boolean end_dateIsKey() {
			return false;
		}

		public Integer end_dateLength() {
			return 13;
		}

		public Integer end_datePrecision() {
			return 0;
		}

		public String end_dateDefault() {

			return null;

		}

		public String end_dateComment() {

			return "";

		}

		public String end_datePattern() {

			return "dd-MM-yyyy";

		}

		public String end_dateOriginalDbColumnName() {

			return "end_date";

		}

		public boolean is_current;

		public boolean getIs_current() {
			return this.is_current;
		}

		public Boolean is_currentIsNullable() {
			return false;
		}

		public Boolean is_currentIsKey() {
			return false;
		}

		public Integer is_currentLength() {
			return 1;
		}

		public Integer is_currentPrecision() {
			return 0;
		}

		public String is_currentDefault() {

			return "'true'";

		}

		public String is_currentComment() {

			return "";

		}

		public String is_currentPattern() {

			return "";

		}

		public String is_currentOriginalDbColumnName() {

			return "is_current";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.stationid == null) ? 0 : this.stationid.hashCode());

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

			if (this.stationid == null) {
				if (other.stationid != null)
					return false;

			} else if (!this.stationid.equals(other.stationid))

				return false;

			return true;
		}

		public void copyDataTo(row2Struct other) {

			other.stationkey = this.stationkey;
			other.stationid = this.stationid;
			other.stationname = this.stationname;
			other.citykey = this.citykey;
			other.maxchargingpoints = this.maxchargingpoints;
			other.stationstatuskey = this.stationstatuskey;
			other.start_date = this.start_date;
			other.end_date = this.end_date;
			other.is_current = this.is_current;

		}

		public void copyKeysDataTo(row2Struct other) {

			other.stationid = this.stationid;

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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.stationid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.stationid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.stationid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.stationid, dos);

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

				this.stationkey = dis.readInt();

				this.stationname = readString(dis, ois);

				this.citykey = readInteger(dis, ois);

				this.maxchargingpoints = readInteger(dis, ois);

				this.stationstatuskey = readInteger(dis, ois);

				this.start_date = readDate(dis, ois);

				this.end_date = readDate(dis, ois);

				this.is_current = dis.readBoolean();

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.stationkey = objectIn.readInt();

				this.stationname = readString(dis, objectIn);

				this.citykey = readInteger(dis, objectIn);

				this.maxchargingpoints = readInteger(dis, objectIn);

				this.stationstatuskey = readInteger(dis, objectIn);

				this.start_date = readDate(dis, objectIn);

				this.end_date = readDate(dis, objectIn);

				this.is_current = objectIn.readBoolean();

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.stationkey);

				writeString(this.stationname, dos, oos);

				writeInteger(this.citykey, dos, oos);

				writeInteger(this.maxchargingpoints, dos, oos);

				writeInteger(this.stationstatuskey, dos, oos);

				writeDate(this.start_date, dos, oos);

				writeDate(this.end_date, dos, oos);

				dos.writeBoolean(this.is_current);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.stationkey);

				writeString(this.stationname, dos, objectOut);

				writeInteger(this.citykey, dos, objectOut);

				writeInteger(this.maxchargingpoints, dos, objectOut);

				writeInteger(this.stationstatuskey, dos, objectOut);

				writeDate(this.start_date, dos, objectOut);

				writeDate(this.end_date, dos, objectOut);

				objectOut.writeBoolean(this.is_current);

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
			sb.append("stationkey=" + String.valueOf(stationkey));
			sb.append(",stationid=" + String.valueOf(stationid));
			sb.append(",stationname=" + stationname);
			sb.append(",citykey=" + String.valueOf(citykey));
			sb.append(",maxchargingpoints=" + String.valueOf(maxchargingpoints));
			sb.append(",stationstatuskey=" + String.valueOf(stationstatuskey));
			sb.append(",start_date=" + String.valueOf(start_date));
			sb.append(",end_date=" + String.valueOf(end_date));
			sb.append(",is_current=" + String.valueOf(is_current));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(stationkey);

			sb.append("|");

			if (stationid == null) {
				sb.append("<null>");
			} else {
				sb.append(stationid);
			}

			sb.append("|");

			if (stationname == null) {
				sb.append("<null>");
			} else {
				sb.append(stationname);
			}

			sb.append("|");

			if (citykey == null) {
				sb.append("<null>");
			} else {
				sb.append(citykey);
			}

			sb.append("|");

			if (maxchargingpoints == null) {
				sb.append("<null>");
			} else {
				sb.append(maxchargingpoints);
			}

			sb.append("|");

			if (stationstatuskey == null) {
				sb.append("<null>");
			} else {
				sb.append(stationstatuskey);
			}

			sb.append("|");

			if (start_date == null) {
				sb.append("<null>");
			} else {
				sb.append(start_date);
			}

			sb.append("|");

			if (end_date == null) {
				sb.append("<null>");
			} else {
				sb.append(end_date);
			}

			sb.append("|");

			sb.append(is_current);

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.stationid, other.stationid);
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

		mdc("tDBInput_2", "UR0XUh_");

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
				// linked node: tMap_1 - inputs:(row1,row2)
				// outputs:(StationFound,StationNotFound)

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

				cLabel = "\"chargingstation\"";

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
									+ "\"SELECT    \\\"chargingstation\\\".\\\"stationkey\\\",    \\\"chargingstation\\\".\\\"stationid\\\",    \\\"chargingstation\\\".\\\"stationname\\\",    \\\"chargingstation\\\".\\\"citykey\\\",    \\\"chargingstation\\\".\\\"maxchargingpoints\\\",    \\\"chargingstation\\\".\\\"stationstatuskey\\\",    \\\"chargingstation\\\".\\\"start_date\\\",    \\\"chargingstation\\\".\\\"end_date\\\",    \\\"chargingstation\\\".\\\"is_current\\\"  FROM \\\"chargingstation\\\"\"");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_2.append(" | ");
							log4jParamters_tDBInput_2.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("stationkey") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("stationid") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("stationname")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("citykey") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("maxchargingpoints") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("stationstatuskey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("start_date") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("end_date") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("is_current")
									+ "}]");
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
					talendJobLog.addCM("tDBInput_2", "\"chargingstation\"", "tPostgresqlInput");
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

				String dbquery_tDBInput_2 = "SELECT \n  \"chargingstation\".\"stationkey\", \n  \"chargingstation\".\"stationid\", \n  \"chargingstation\".\"stationnam"
						+ "e\", \n  \"chargingstation\".\"citykey\", \n  \"chargingstation\".\"maxchargingpoints\", \n  \"chargingstation\".\"stations"
						+ "tatuskey\", \n  \"chargingstation\".\"start_date\", \n  \"chargingstation\".\"end_date\", \n  \"chargingstation\".\"is_curr"
						+ "ent\"\n FROM \"chargingstation\"";

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
							row2.stationkey = 0;
						} else {

							row2.stationkey = rs_tDBInput_2.getInt(1);
							if (rs_tDBInput_2.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row2.stationid = null;
						} else {

							row2.stationid = rs_tDBInput_2.getInt(2);
							if (rs_tDBInput_2.wasNull()) {
								row2.stationid = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row2.stationname = null;
						} else {

							row2.stationname = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
						}
						if (colQtyInRs_tDBInput_2 < 4) {
							row2.citykey = null;
						} else {

							row2.citykey = rs_tDBInput_2.getInt(4);
							if (rs_tDBInput_2.wasNull()) {
								row2.citykey = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 5) {
							row2.maxchargingpoints = null;
						} else {

							row2.maxchargingpoints = rs_tDBInput_2.getInt(5);
							if (rs_tDBInput_2.wasNull()) {
								row2.maxchargingpoints = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 6) {
							row2.stationstatuskey = null;
						} else {

							row2.stationstatuskey = rs_tDBInput_2.getInt(6);
							if (rs_tDBInput_2.wasNull()) {
								row2.stationstatuskey = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 7) {
							row2.start_date = null;
						} else {

							row2.start_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 7);
						}
						if (colQtyInRs_tDBInput_2 < 8) {
							row2.end_date = null;
						} else {

							row2.end_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 8);
						}
						if (colQtyInRs_tDBInput_2 < 9) {
							row2.is_current = false;
						} else {

							row2.is_current = rs_tDBInput_2.getBoolean(9);
							if (rs_tDBInput_2.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
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

						cLabel = "\"chargingstation\"";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_2");

						cLabel = "\"chargingstation\"";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row2 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row2");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row2", "tDBInput_2", "\"chargingstation\"", "tPostgresqlInput", "tAdvancedHash_row2",
								"tAdvancedHash_row2", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
						}

						row2Struct row2_HashRow = new row2Struct();

						row2_HashRow.stationkey = row2.stationkey;

						row2_HashRow.stationid = row2.stationid;

						row2_HashRow.stationname = row2.stationname;

						row2_HashRow.citykey = row2.citykey;

						row2_HashRow.maxchargingpoints = row2.maxchargingpoints;

						row2_HashRow.stationstatuskey = row2.stationstatuskey;

						row2_HashRow.start_date = row2.start_date;

						row2_HashRow.end_date = row2.end_date;

						row2_HashRow.is_current = row2.is_current;

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

						cLabel = "\"chargingstation\"";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						s(currentComponent = "tDBInput_2");

						cLabel = "\"chargingstation\"";

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
						"tDBInput_2", "\"chargingstation\"", "tPostgresqlInput", "tAdvancedHash_row2",
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

				cLabel = "\"chargingstation\"";

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
		final static byte[] commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		static byte[] commonByteArray_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int chargingpointstatuskey;

		public int getChargingpointstatuskey() {
			return this.chargingpointstatuskey;
		}

		public Boolean chargingpointstatuskeyIsNullable() {
			return false;
		}

		public Boolean chargingpointstatuskeyIsKey() {
			return true;
		}

		public Integer chargingpointstatuskeyLength() {
			return 10;
		}

		public Integer chargingpointstatuskeyPrecision() {
			return 0;
		}

		public String chargingpointstatuskeyDefault() {

			return "nextval('chargingpointstatus_chargingpointstatuskey_seq'::regclass)";

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

		public Integer chargingpointstatusid;

		public Integer getChargingpointstatusid() {
			return this.chargingpointstatusid;
		}

		public Boolean chargingpointstatusidIsNullable() {
			return true;
		}

		public Boolean chargingpointstatusidIsKey() {
			return false;
		}

		public Integer chargingpointstatusidLength() {
			return 10;
		}

		public Integer chargingpointstatusidPrecision() {
			return 0;
		}

		public String chargingpointstatusidDefault() {

			return null;

		}

		public String chargingpointstatusidComment() {

			return "";

		}

		public String chargingpointstatusidPattern() {

			return "";

		}

		public String chargingpointstatusidOriginalDbColumnName() {

			return "chargingpointstatusid";

		}

		public String statusdescription;

		public String getStatusdescription() {
			return this.statusdescription;
		}

		public Boolean statusdescriptionIsNullable() {
			return true;
		}

		public Boolean statusdescriptionIsKey() {
			return false;
		}

		public Integer statusdescriptionLength() {
			return 100;
		}

		public Integer statusdescriptionPrecision() {
			return 0;
		}

		public String statusdescriptionDefault() {

			return null;

		}

		public String statusdescriptionComment() {

			return "";

		}

		public String statusdescriptionPattern() {

			return "";

		}

		public String statusdescriptionOriginalDbColumnName() {

			return "statusdescription";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result
						+ ((this.chargingpointstatusid == null) ? 0 : this.chargingpointstatusid.hashCode());

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

			if (this.chargingpointstatusid == null) {
				if (other.chargingpointstatusid != null)
					return false;

			} else if (!this.chargingpointstatusid.equals(other.chargingpointstatusid))

				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.chargingpointstatuskey = this.chargingpointstatuskey;
			other.chargingpointstatusid = this.chargingpointstatusid;
			other.statusdescription = this.statusdescription;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.chargingpointstatusid = this.chargingpointstatusid;

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

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargingpointstatusid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_IE6750_PROJECT1_GROUPH_ChargingPointSCDType3Load) {

				try {

					int length = 0;

					this.chargingpointstatusid = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.chargingpointstatusid, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.chargingpointstatusid, dos);

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

				this.chargingpointstatuskey = dis.readInt();

				this.statusdescription = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.chargingpointstatuskey = objectIn.readInt();

				this.statusdescription = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.chargingpointstatuskey);

				writeString(this.statusdescription, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.chargingpointstatuskey);

				writeString(this.statusdescription, dos, objectOut);

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
			sb.append("chargingpointstatuskey=" + String.valueOf(chargingpointstatuskey));
			sb.append(",chargingpointstatusid=" + String.valueOf(chargingpointstatusid));
			sb.append(",statusdescription=" + statusdescription);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(chargingpointstatuskey);

			sb.append("|");

			if (chargingpointstatusid == null) {
				sb.append("<null>");
			} else {
				sb.append(chargingpointstatusid);
			}

			sb.append("|");

			if (statusdescription == null) {
				sb.append("<null>");
			} else {
				sb.append(statusdescription);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.chargingpointstatusid, other.chargingpointstatusid);
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

		mdc("tDBInput_3", "y2IrKa_");

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
				// linked node: tMap_2 - inputs:(StationFound,row3)
				// outputs:(StatusFound,StatusNotFound)

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

				cLabel = "\"chargingpointstatus\"";

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
									+ "\"SELECT    \\\"chargingpointstatus\\\".\\\"chargingpointstatuskey\\\",    \\\"chargingpointstatus\\\".\\\"chargingpointstatusid\\\",    \\\"chargingpointstatus\\\".\\\"statusdescription\\\"  FROM \\\"chargingpointstatus\\\"\"");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("USE_CURSOR" + " = " + "false");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_3.append(" | ");
							log4jParamters_tDBInput_3.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargingpointstatuskey") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("chargingpointstatusid") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("statusdescription") + "}]");
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
					talendJobLog.addCM("tDBInput_3", "\"chargingpointstatus\"", "tPostgresqlInput");
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

				String dbquery_tDBInput_3 = "SELECT \n  \"chargingpointstatus\".\"chargingpointstatuskey\", \n  \"chargingpointstatus\".\"chargingpointstatusid\", \n  "
						+ "\"chargingpointstatus\".\"statusdescription\"\n FROM \"chargingpointstatus\"";

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
							row3.chargingpointstatuskey = 0;
						} else {

							row3.chargingpointstatuskey = rs_tDBInput_3.getInt(1);
							if (rs_tDBInput_3.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_3 < 2) {
							row3.chargingpointstatusid = null;
						} else {

							row3.chargingpointstatusid = rs_tDBInput_3.getInt(2);
							if (rs_tDBInput_3.wasNull()) {
								row3.chargingpointstatusid = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 3) {
							row3.statusdescription = null;
						} else {

							row3.statusdescription = routines.system.JDBCUtil.getString(rs_tDBInput_3, 3, false);
						}

						log.debug("tDBInput_3 - Retrieving the record " + nb_line_tDBInput_3 + ".");

						/**
						 * [tDBInput_3 begin ] stop
						 */

						/**
						 * [tDBInput_3 main ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"chargingpointstatus\"";

						tos_count_tDBInput_3++;

						/**
						 * [tDBInput_3 main ] stop
						 */

						/**
						 * [tDBInput_3 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"chargingpointstatus\"";

						/**
						 * [tDBInput_3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 main ] start
						 */

						s(currentComponent = "tAdvancedHash_row3");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row3", "tDBInput_3", "\"chargingpointstatus\"", "tPostgresqlInput",
								"tAdvancedHash_row3", "tAdvancedHash_row3", "tAdvancedHash"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
						}

						row3Struct row3_HashRow = new row3Struct();

						row3_HashRow.chargingpointstatuskey = row3.chargingpointstatuskey;

						row3_HashRow.chargingpointstatusid = row3.chargingpointstatusid;

						row3_HashRow.statusdescription = row3.statusdescription;

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

						cLabel = "\"chargingpointstatus\"";

						/**
						 * [tDBInput_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 end ] start
						 */

						s(currentComponent = "tDBInput_3");

						cLabel = "\"chargingpointstatus\"";

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
						"tDBInput_3", "\"chargingpointstatus\"", "tPostgresqlInput", "tAdvancedHash_row3",
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

				cLabel = "\"chargingpointstatus\"";

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
		final ChargingPointSCDType3Load ChargingPointSCDType3LoadClass = new ChargingPointSCDType3Load();

		int exitCode = ChargingPointSCDType3LoadClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'ChargingPointSCDType3Load' - Done.");
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
		log.info("TalendJob: 'ChargingPointSCDType3Load' - Start.");

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
		org.slf4j.MDC.put("_jobRepositoryId", "_DOjDIJ6DEe-roYUVHEF2JQ");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-11-09T20:08:15.993196Z");

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
			java.io.InputStream inContext = ChargingPointSCDType3Load.class.getClassLoader().getResourceAsStream(
					"ie6750_project1_grouph/chargingpointscdtype3load_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = ChargingPointSCDType3Load.class.getClassLoader()
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
		log.info("TalendJob: 'ChargingPointSCDType3Load' - Started.");
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
			System.out.println((endUsedMemory - startUsedMemory)
					+ " bytes memory increase when running : ChargingPointSCDType3Load");
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
		log.info(
				"TalendJob: 'ChargingPointSCDType3Load' - Finished - status: " + status + " returnCode: " + returnCode);

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
 * 261337 characters generated by Talend Real-time Big Data Platform on the 9
 * November 2024 at 21:08:15 CET
 ************************************************************************************************/