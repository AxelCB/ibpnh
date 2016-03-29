package org.ibpnh.core.services.mail.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.ibpnh.core.fx.I_MailFx;
import org.ibpnh.core.fx.I_MailFx.AttachmentVo;
import org.ibpnh.core.utils.exception.CodedException;
import org.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * Abstract Class with commons methods for both sending mechanisms.
 * 
 * @author fgonzalez
 * 
 */
public abstract class AbstractMailSender {

	/**
	 * Mail Sender.
	 */
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Mail DAO.
	 */
	@Autowired
	private I_MailDao mailDao;

	/**
	 * Compiles the mail fields and returns them in a map.
	 * 
	 * @param mailVo
	 *            the mail to compile
	 * @param em
	 *            the entity manager
	 * @param fireTime
	 *            the fire time
	 * 
	 * @return map with fields
	 * 
	 * @throws ClassNotFoundException
	 * @throws CodedException
	 * @throws ScriptException
	 */
	protected Map<String, String> getFields(MailVo mailVo, EntityManager em,
			I_MailFx mailFx) throws CodedException, ScriptException {
		// executes and get the compile parameters
		Map<String, String> compileTimeParameters = mailFx
				.executeAndReturnParameters();

		// compile mail text
		String sender = this.getMailDao().compileField(em, mailVo.getSender(),
				compileTimeParameters);
		String subject = this.getMailDao().compileField(em,
				mailVo.getSubject(), compileTimeParameters);
		String toList = this.getMailDao().compileField(em, mailVo.getToList(),
				compileTimeParameters);
		String ccList = "";
		if (StringUtils.isNotBlank(mailVo.getCcList())) {
			ccList = this.getMailDao().compileField(em, mailVo.getCcList(),
					compileTimeParameters);
		}
		String ccoList = "";
		if (StringUtils.isNotBlank(mailVo.getCcoList())) {
			ccoList = this.getMailDao().compileField(em, mailVo.getCcoList(),
					compileTimeParameters);
		}
		String mailBody = this.getMailDao().compileField(em, mailVo.getBody(),
				compileTimeParameters);
		String mailFooter = this.getMailDao().compileField(em,
				mailVo.getFooter(), compileTimeParameters);

		// if the mail body contains javascript, we need to evaluate it and
		// get the HTML string
		if (mailVo.getBodyJavascript() != null && mailVo.getBodyJavascript()) {
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			ScriptEngine jsEngine = scriptEngineManager
					.getEngineByName("JavaScript");

			// we eval the whole string
			jsEngine.eval(mailBody);

			// we replace the mail body with the HTML variable that MUST be
			
			mailBody = jsEngine.get("html").toString();
		}

		Map<String, String> fields = new HashMap<>();
		fields.put("sender", sender);
		fields.put("subject", subject);
		fields.put("toList", toList);
		fields.put("ccList", ccList);
		fields.put("ccoList", ccoList);
		fields.put("mailBody", mailBody);
		fields.put("mailFooter", mailFooter);

		return fields;
	}

	/**
	 * Prepares the mail message and sents it.
	 * 
	 * @param message
	 *            the message
	 * @param mimeMessageHelper
	 *            the mime message
	 * @param fields
	 *            the fields
	 * @param attachments
	 *            the list of attachments
	 * 
	 * @throws MessagingException
	 */
	protected void prepareAndSendMessage(MimeMessage message,
			MimeMessageHelper mimeMessageHelper, Map<String, String> fields,
			List<AttachmentVo> attachments) throws MessagingException {
		mimeMessageHelper.setFrom(fields.get("sender"));
		mimeMessageHelper.setSubject(fields.get("subject"));
		mimeMessageHelper.setSentDate(new Date());
		mimeMessageHelper.setTo(fields.get("toList").split(","));
		if (StringUtils.isNotBlank(fields.get("ccList"))) {
			mimeMessageHelper.setCc(fields.get("ccList").split(","));
		}
		if (StringUtils.isNotBlank(fields.get("ccoList"))) {
			mimeMessageHelper.setBcc(fields.get("ccoList").split(","));
		}
		mimeMessageHelper
				.setText(fields.get("mailBody") + fields.get("mailFooter"),
						Boolean.TRUE);

		// add all of the attachments
		for (AttachmentVo attachmentVo : attachments) {
			if(attachmentVo.getInlineId()==null){
				mimeMessageHelper.addAttachment(attachmentVo.getFileName(),
						new ByteArrayResource(attachmentVo.getByteArray()),
						attachmentVo.getContentType());
			}else{
				try{
					mimeMessageHelper.addInline(attachmentVo.getInlineId(),
							new ByteArrayResource(attachmentVo.getByteArray()),
							attachmentVo.getContentType());
				}catch(MessagingException e){
					//TODO : POENR ERROR
				}
			}
			
		}

		this.getMailSender().send(message);
	}

	/**
	 * @return the mailDao
	 */
	public I_MailDao getMailDao() {
		return this.mailDao;
	}

	/**
	 * @param mailDao
	 *            the mailDao to set
	 */
	public void setMailDao(I_MailDao mailDao) {
		this.mailDao = mailDao;
	}

	/**
	 * @return the mailSender
	 */
	public JavaMailSender getMailSender() {
		return this.mailSender;
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
