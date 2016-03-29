package org.eclipse.persistence.logging;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * <p>
 * This is a wrapper class for SLF4J. It is used when messages need to be logged
 * through SLF4J.
 * </p>
 * <p>
 * Para usar SLF4j para los logs de EclipseLink configuramos la propiedad
 * <code>eclipselink.logging.logger</code> con el valor
 * <code>org.eclipse.persistence.logging.Slf4jSessionLogger</code>
 * </p>
 * <p>
 * La configuración del nivel de los logs no se realiza en EclipseLink (con la
 * propiedad eclipselink.logging.level), sino en la implementación de SLF4J.
 * <p>
 * Se puede usar el resto de las propiedades de logging de EclipseLink 
 * (eclipselink.logging.timestamp, eclipselink.logging.thread,	
 * eclipselink.logging.session, eclipselink.logging.connection 
 * y eclipselink.logging.parameters) para configurar el formato de salida.
 * <p>
 * Se usan las siguientes categorias de log:
 * <p>
 * <ul>
 * <li>org.eclipse.persistence.logging.default
 * <li>org.eclipse.persistence.logging.sql
 * <li>org.eclipse.persistence.logging.transaction
 * <li>org.eclipse.persistence.logging.event
 * <li>org.eclipse.persistence.logging.connection
 * <li>org.eclipse.persistence.logging.query
 * <li>org.eclipse.persistence.logging.cache
 * <li>org.eclipse.persistence.logging.propagation
 * <li>org.eclipse.persistence.logging.sequencing
 * <li>org.eclipse.persistence.logging.ejb
 * <li>org.eclipse.persistence.logging.ejb_or_metadata
 * <li>org.eclipse.persistence.logging.weaver
 * <li>org.eclipse.persistence.logging.properties
 * <li>org.eclipse.persistence.logging.server
 * </ul>
 * </p>
 * <p>
 * Los niveles de log de EclipseLink y SLF4J son distintos, se realiza la
 * siguiente correspondencia:
 * </p>
 * <ul>
 * <li>ALL,FINER,FINEST -> TRACE
 * <li>FINE -> DEBUG
 * <li>CONFIG,INFO -> INFO
 * <li>WARNING -> WARN
 * <li>SEVERE -> ERROR
 * </ul>
 * </p>
 * <p>
 * 
 * @author Miguel Angel Sosvilla Luis.
 * 
 */
public class Slf4jSessionLogger extends AbstractSessionLog {

	public static final String ECLIPSELINK_NAMESPACE = "org.eclipse.persistence.logging";
	public static final String DEFAULT_CATEGORY = "default";

	public static final String DEFAULT_ECLIPSELINK_NAMESPACE = ECLIPSELINK_NAMESPACE
			+ "." + DEFAULT_CATEGORY;

	private Map<Integer, LogLevel> mapLevels;
	private Map<String, Logger> categoryLoggers = new HashMap<String, Logger>();

	public Slf4jSessionLogger() {
		super();
		this.createCategoryLoggers();
		this.initMapLevels();
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void log(SessionLogEntry entry) {
		if (!this.shouldLog(entry.getLevel(), entry.getNameSpace())) {
			return;
		}

		Logger logger = this.getLogger(entry.getNameSpace());
		LogLevel logLevel = this.getLogLevel(entry.getLevel());

		StringBuilder message = new StringBuilder();

		message.append(this.getSupplementDetailString(entry));
		message.append(this.formatMessage(entry));

		switch (logLevel) {
		case TRACE:
			logger.trace(message.toString());
			break;
		case DEBUG:
			logger.debug(message.toString());
			break;
		case INFO:
			logger.info(message.toString());
			break;
		case WARN:
			logger.warn(message.toString());
			break;
		case ERROR:
			logger.error(message.toString());
			break;
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean shouldLog(int level, String category) {
		Logger logger = this.getLogger(category);
		boolean resp = false;

		LogLevel logLevel = this.getLogLevel(level);
		
		switch (logLevel) {
		case TRACE:
			resp = logger.isTraceEnabled();
			break;
		case DEBUG:
			resp = logger.isDebugEnabled();
			break;
		case INFO:
			resp = logger.isInfoEnabled();
			break;
		case WARN:
			resp = logger.isWarnEnabled();
			break;
		case ERROR:
			resp = logger.isErrorEnabled();
			break;
		}

		return resp;
	}

	@Override
	public boolean shouldLog(int level) {
		return this.shouldLog(level, "default");
	}

	/**
	 * Return true if SQL logging should log visible bind parameters. If the
	 * shouldDisplayData is not set, return false.
	 */
	@Override
	public boolean shouldDisplayData() {
		if (this.shouldDisplayData != null) {
			return this.shouldDisplayData.booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * Initialize loggers eagerly
	 */
	private void createCategoryLoggers() {
		for (String category : SessionLog.loggerCatagories) {
			this.addLogger(category, ECLIPSELINK_NAMESPACE + "." + category);
		}
		// Logger default para cuando no hay categoría.
		this.addLogger(DEFAULT_CATEGORY, DEFAULT_ECLIPSELINK_NAMESPACE);
	}

	/**
	 * INTERNAL: Add Logger to the categoryLoggers.
	 */
	private void addLogger(String loggerCategory, String loggerNameSpace) {
		this.categoryLoggers.put(loggerCategory,
				LoggerFactory.getLogger(loggerNameSpace));
	}

	/**
	 * INTERNAL: Return the Logger for the given category
	 */
	private Logger getLogger(String category) {

		if (!StringUtils.hasText(category)
				|| !this.categoryLoggers.containsKey(category)) {
			category = DEFAULT_CATEGORY;
		}

		return this.categoryLoggers.get(category);

	}

	/**
	 * Return the corresponding Slf4j Level for a given EclipseLink level.
	 */
	private LogLevel getLogLevel(Integer level) {
		LogLevel logLevel = this.mapLevels.get(level);

		if (logLevel == null) {
			logLevel = LogLevel.OFF;
		}

		return logLevel;
	}

	/**
	 * SLF4J log levels.
	 * 
	 * @author Miguel Angel Sosvilla Luis
	 * 
	 */
	enum LogLevel {
		TRACE, DEBUG, INFO, WARN, ERROR, OFF
	}

	/**
	 * Relación de los niveles de log de EclipseLink y los de SLF4J
	 */
	private void initMapLevels() {
		this.mapLevels = new HashMap<Integer, LogLevel>();

		this.mapLevels.put(SessionLog.ALL, LogLevel.TRACE);
		this.mapLevels.put(SessionLog.FINEST, LogLevel.TRACE);
		this.mapLevels.put(SessionLog.FINER, LogLevel.TRACE);
		this.mapLevels.put(SessionLog.FINE, LogLevel.TRACE);
		this.mapLevels.put(SessionLog.CONFIG, LogLevel.TRACE);
		this.mapLevels.put(SessionLog.INFO, LogLevel.INFO);
		this.mapLevels.put(SessionLog.WARNING, LogLevel.WARN);
		this.mapLevels.put(SessionLog.SEVERE, LogLevel.ERROR);
	}

}
