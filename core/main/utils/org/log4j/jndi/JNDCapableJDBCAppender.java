package org.log4j.jndi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.persistence.SchemaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of the log4j's JDBCAppender that adds capability of JNDI connection
 * getting.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class JNDCapableJDBCAppender extends JDBCAppender {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(JNDCapableJDBCAppender.class);

	/**
	 * The JNDI name.
	 */
	public String jndiName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.jdbc.JDBCAppender#getConnection()
	 */
	@Override
	protected Connection getConnection() throws SQLException {
		if (this.getJndiName() == null) {
			throw new RuntimeException("JNDI Name not provided");
		} else {
			return this.lookupDataSource().getConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.jdbc.JDBCAppender#closeConnection(java.sql.Connection)
	 */
	@Override
	protected void closeConnection(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			// logs this in debug to avoid repeated errors
			this.logger
					.debug("ERROR Closing Connection on JDBC Appender, exception class {} and message {}",
							e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Looks up the data source in the naming context specified by the jndiName.
	 * 
	 * @return the data source.
	 */
	private DataSource lookupDataSource() {
		try {
			Context context = new InitialContext();
			return (DataSource) context.lookup(this.getJndiName());
		} catch (NamingException e) {
			throw new RuntimeException("Cannot find JNDI DataSource: "
					+ this.getJndiName(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.jdbc.JDBCAppender#getLogStatement(org.apache.log4j.spi
	 * .LoggingEvent)
	 */
	@Override
	protected String getLogStatement(LoggingEvent event) {
		String logStatement = super.getLogStatement(event);

		logStatement = logStatement.replace("[[schema]]",
				SchemaUtils.getSchema());

		return logStatement;
	}

	/**
	 * @return the jndiName
	 */
	public String getJndiName() {
		return this.jndiName;
	}

	/**
	 * @param jndiName
	 *            the jndiName to set
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

}
