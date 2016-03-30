package org.kairos.ibpnh.core.services.mail.job;

import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * Mail Sender Task.
 * 
 * @author fgonzalez
 * 
 */
public class MailSenderTask extends AbstractMailSender implements Runnable {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(MailSenderTask.class);

	/**
	 * Mail to send.
	 */
	private MailVo mailVo;

	/**
	 * Mail FX.
	 */
	private I_MailFx mailFx;

	/**
	 * Entity Manager Holder.
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/*
	 * Mail DAO.
	 */
	@Autowired
	private I_MailDao mailDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		try {

			MimeMessage message = this.getMailSender().createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					message, false, "UTF-8");

			Map<String, String> fields = this.getFields(this.getMailVo(), em,
					this.getMailFx());

			this.prepareAndSendMessage(message, mimeMessageHelper, fields, this.getMailFx().getAttachments());
		} catch (CodedException ce) {
			this.logger.error(
					"at GenericMailSenderJob::execute: exception in the FX, code: "
							+ ce.getErrorCode(), ce);
		} catch (ScriptException se) {
			this.logger
					.error("at GenericMailSenderJob::execute: error evaluating JS Script",
							se);
		} catch (Exception e) {
			this.logger.error(
					"at GenericMailSenderJob::execute: unknown error", e);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
	}

	/**
	 * @return the mailVo
	 */
	public MailVo getMailVo() {
		return this.mailVo;
	}

	/**
	 * @param mailVo
	 *            the mailVo to set
	 */
	public void setMailVo(MailVo mailVo) {
		this.mailVo = mailVo;
	}

	/**
	 * @return the mailFx
	 */
	public I_MailFx getMailFx() {
		return this.mailFx;
	}

	/**
	 * @param mailFx
	 *            the mailFx to set
	 */
	public void setMailFx(I_MailFx mailFx) {
		this.mailFx = mailFx;
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
	 * @return the mailDao
	 */
	@Override
	public I_MailDao getMailDao() {
		return this.mailDao;
	}

	/**
	 * @param mailDao
	 *            the mailDao to set
	 */
	@Override
	public void setMailDao(I_MailDao mailDao) {
		this.mailDao = mailDao;
	}

}