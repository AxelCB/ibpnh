package org.kairos.ibpnh.core.services.alert.consumer;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.alert.I_AlertDao;
import org.kairos.ibpnh.core.vo.alert.AlertVo;

/**
 * Asynchronous listener of the Messages
 * 
 * @author Axel Collard Bovy
 * 
 */
public class AlertAsyncConsumerMq implements MessageListener, ExceptionListener {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(AlertAsyncConsumerMq.class);

	/**
	 * Entity Manager.
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * The alert DAO.
	 */
	@Autowired
	private I_AlertDao alertDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			// downcast the message
			EntityManager em = null;			
			try {
				ObjectMessage objectMessage = (ObjectMessage) message;
				// get the alert object
				AlertVo alertVo = (AlertVo) objectMessage.getObject();

				// we get the entity manager
				em = this.getEntityManagerHolder().getEntityManager();

				em.getTransaction().begin();
				alertVo = this.getAlertDao().persist(em, alertVo);
				em.getTransaction().commit();
				em.close();

				this.logger.debug("alert persisted - {}", alertVo.getId());
			} catch (Exception e) {
				this.logger.error(
						"Error Executing Alert OnMessage MQ Listener.", e);
				if (em != null) {
					em.getTransaction().setRollbackOnly();
				}
			} finally {
				if (em != null && em.getTransaction().isActive()) {
					try {
						em.getTransaction().commit();
					} catch (Exception e) {
						// do nothing
					}
				}
				this.getEntityManagerHolder().closeEntityManager(em);
			}
		} else {
			this.logger
					.error("FATAL Error Executing Alert OnMessage MQ Listener: message was not an object message");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.ExceptionListener#onException(javax.jms.JMSException)
	 */
	@Override
	public void onException(JMSException ex) {
		this.logger.error("Error Exception on Listener.", ex);
	}

	/**
	 * @return the entityManagerHolder
	 */
	public EntityManagerHolder getEntityManagerHolder() {
		return this.entityManagerHolder;
	}

	/**
	 * @param entityManagerHolder
	 *            the entityManagerHolder to set
	 */
	public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
		this.entityManagerHolder = entityManagerHolder;
	}

	/**
	 * @return the alertDao
	 */
	public I_AlertDao getAlertDao() {
		return this.alertDao;
	}

	/**
	 * @param alertDao
	 *            the alertDao to set
	 */
	public void setAlertDao(I_AlertDao alertDao) {
		this.alertDao = alertDao;
	}

}
