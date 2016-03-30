package org.kairos.ibpnh.core.services.mail.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.script.ScriptException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * Generic Mail Sender Job.
 * 
 * @author fgonzalez
 * 
 */
public class GenericMailSenderJob extends AbstractMailSender implements Job {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(GenericMailSenderJob.class);

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/**
	 * Entity Manager Holder.
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Mail Sender.
	 */
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Test the mail execution without sending the actual mail. Returns the
	 * fields solved as a map.
	 * 
	 * @param mailVo
	 *            the mail to test
	 * @param em
	 * @return
	 */
	public Map<String, String> testAndGetFields(MailVo mailVo) {
		Map<String, String> map = new HashMap<>();

		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		try {
			I_MailFx mailFx = this.getMailFx(mailVo, new Date(), em);
			return this.getFields(mailVo, em, mailFx);
		} catch (ClassNotFoundException cnfe) {
			this.logger
					.error("at GenericMailSenderJob::testAndGetFields: Mail FX Class not found at loading",
							cnfe);

			StringWriter errors = new StringWriter();
			cnfe.printStackTrace(new PrintWriter(errors));
			map.put("error",
					"at GenericMailSenderJob::testAndGetFields: Mail FX Class not found at loading: "
							+ errors.toString());
		} catch (ClassCastException cce) {
			this.logger
					.error("at GenericMailSenderJob::testAndGetFields: Mail FX Class cast exception",
							cce);

			StringWriter errors = new StringWriter();
			cce.printStackTrace(new PrintWriter(errors));
			map.put("error",
					"at GenericMailSenderJob::testAndGetFields: Mail FX Class cast exception: "
							+ errors.toString());
		} catch (CodedException ce) {
			this.logger.error(
					"at GenericMailSenderJob::testAndGetFields: exception in the FX, code: "
							+ ce.getErrorCode(), ce);

			StringWriter errors = new StringWriter();
			ce.printStackTrace(new PrintWriter(errors));
			map.put("error",
					"at GenericMailSenderJob::testAndGetFields: exception in the FX, code: "
							+ errors.toString());
		} catch (ScriptException se) {
			this.logger
					.error("at GenericMailSenderJob::testAndGetFields: error evaluating JS Script",
							se);

			StringWriter errors = new StringWriter();
			se.printStackTrace(new PrintWriter(errors));
			map.put("error",
					"at GenericMailSenderJob::testAndGetFields: error evaluating JS Script: "
							+ errors.toString());
		} catch (Exception e) {
			this.logger.error(
					"at GenericMailSenderJob::testAndGetFields: unknown error",
					e);

			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			map.put("error",
					"at GenericMailSenderJob::testAndGetFields: unknown error: "
							+ errors.toString());
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		try {
			this.logger.debug("start to send mail");
			

			// retrieves the necessary DAOs and Services
			JobDataMap jdm = context.getJobDetail().getJobDataMap();

			// get the mail VO
			MailVo mailVo = (MailVo) jdm.get("mailVo");
			mailVo = this.getMailDao().getById(em, mailVo.getId());

			this.logger.debug("mail retreived: {}", mailVo.getName());

			// prepares the mail message
			MimeMessage message = this.getMailSender().createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					message, true, "UTF-8");

			I_MailFx mailFx = this.getMailFx(mailVo, context.getFireTime(), em);

			Map<String, String> fields = this.getFields(mailVo, em, mailFx);

			this.prepareAndSendMessage(message, mimeMessageHelper, fields, mailFx.getAttachments());
		} catch (ClassNotFoundException cnfe) {
			this.logger
					.error("at GenericMailSenderJob::execute: Mail FX Class not found at loading",
							cnfe);
		} catch (ClassCastException cce) {
			this.logger
					.error("at GenericMailSenderJob::execute: Mail FX Class cast exception",
							cce);
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
	 * Gets the mail FX for this mail JOB.
	 * 
	 * @param mailVo
	 *            the mail VO
	 * @param fireTime
	 *            the fire time
	 * @param em
	 *            the entity manager
	 * 
	 * @return mail FX instance
	 * 
	 * @throws ClassNotFoundException
	 */
	private I_MailFx getMailFx(MailVo mailVo, Date fireTime, EntityManager em)
			throws ClassNotFoundException {
		// loads the FX class
		@SuppressWarnings("unchecked")
		Class<I_MailFx> mailFxClazz = (Class<I_MailFx>) Class.forName(mailVo
				.getMailFxClass());

		// creates a new FX of that class and sets its fire time and the
		// entity manager
		I_MailFx mailFx = this.getFxFactory().getNewFxInstance(mailFxClazz);
		mailFx.setFireTime(fireTime);
		mailFx.setEm(em);

		return mailFx;
	}

	/**
	 * @return the fxFactory
	 */
	public I_FxFactory getFxFactory() {
		return this.fxFactory;
	}

	/**
	 * @param fxFactory
	 *            the fxFactory to set
	 */
	public void setFxFactory(I_FxFactory fxFactory) {
		this.fxFactory = fxFactory;
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
	 * @return the mailSender
	 */
	@Override
	public JavaMailSender getMailSender() {
		return this.mailSender;
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	@Override
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
