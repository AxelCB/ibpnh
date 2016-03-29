package org.ibpnh.core.services.alert.producer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.ibpnh.core.vo.alert.AlertVo;

/**
 * Active MQ Message Consumer.
 * 
 * @author Axel Collard Bovy
 *
 */
public class AlertProducerMq {

	/*
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(AlertProducerMq.class);
	
	/**
	 * Connection factory.
	 */
	@Autowired
	private CachingConnectionFactory mqCachedConnectionFactory;
	
	/*
	 * Queue name. (setted by the spring configuration)
	 */
	private String jmsDestination;
	
	/**
	 * Sends an alert to the queue.
	 * 
	 * @param alertVo
	 */
	public void send(AlertVo alertVo) {
		// Creates a Connection
		Connection connection;
		
		try {
			this.logger.debug("Trying to create a MQ connection from Factory");
			connection = this.getMqCachedConnectionFactory().createConnection();

			connection.start();
			
			this.logger.debug("Create a MQ Session.");
			//TODO: change to auto acknowledge
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue(this.getJmsDestination());

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);			

			//create command message to transport AlertVo send from parameter
			ObjectMessage commandMessage = session.createObjectMessage();
			commandMessage.setObject(alertVo);
			
			this.logger.debug("Sending the Command Message.");
			producer.send(commandMessage);

			// Clean up connection
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			this.logger.error("Error sending AlertVo message to Queue",e);
		}
	}

	/**
	 * @return the mqCachedConnectionFactory
	 */
	public CachingConnectionFactory getMqCachedConnectionFactory() {
		return this.mqCachedConnectionFactory;
	}

	/**
	 * @param mqCachedConnectionFactory the mqCachedConnectionFactory to set
	 */
	public void setMqCachedConnectionFactory(
			CachingConnectionFactory mqCachedConnectionFactory) {
		this.mqCachedConnectionFactory = mqCachedConnectionFactory;
	}

	/**
	 * @return the jmsDestination
	 */
	public String getJmsDestination() {
		return this.jmsDestination;
	}

	/**
	 * @param jmsDestination the jmsDestination to set
	 */
	public void setJmsDestination(String jmsDestination) {
		this.jmsDestination = jmsDestination;
	}
	
}
