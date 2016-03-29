package org.eclipse.persistence;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Schema Utils.
 * 
 * @author Axel Collard Bovy
 *
 */
public class SchemaUtils {
	
	/**
	 * Logger
	 */
	private static Logger logger = LoggerFactory.getLogger(SchemaUtils.class);
	
	/**
	 * The schema.
	 */
	private static String schema; 
	
	static {
		try {
			// we try to read the schema from the configured one in schema.xml
			InputStream is = SchemaUtils.class.getResourceAsStream("/jpa/schema.xml");
			String schemaFile = IOUtils.toString(is);
			
			Pattern pattern = Pattern.compile("<schema>(.*)<");
			Matcher matcher = pattern.matcher(schemaFile);
			
			String parsedSchema = null;
			while (matcher.find()) {
				parsedSchema = matcher.group(1);
			}
			
			schema = parsedSchema;
			
			logger.debug("schema found: " + ((schema == null) ? "NULL" : schema));
		} catch (Exception e) {
			logger.error("error getting schema", e);
		}	
	}
	
	/**
	 * Returns the schema.
	 * 
	 * @return the schema string
	 */
	public static String getSchema() {
		return schema;
	}
}
