package org.kairos.ibpnh.core.cometd;

/**
 * Utility class with CometD channel constants.
 * 
 * @author Axel Collard Bovy
 *
 */
public class Channels {	

	/**
	 * Service channels.
	 */
	public static final String SERVICE_ECHO = "/service/echo";
	
	/**
	 * Broadcast channels.
	 */
	public static final String CHANNEL_ECHO = "/echo";
	public static final String CHANNEL_DASHBOARD = "/dashboard";
	public static final String CHANNEL_FINE_DASHBOARD = "/fineDashboard";
	public static final String CHANNEL_BLOCKS_CONTROL = "/blocksControl";
	public static final String CHANNEL_PROGRESS_INFO = "/progressInfo";
	
}
