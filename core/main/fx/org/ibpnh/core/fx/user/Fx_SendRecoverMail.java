package org.ibpnh.core.fx.user;

import java.util.Date;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.fx.I_FxFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.I_RegisteredUserDao;
import org.ibpnh.core.dao.user.I_PasswordRecoverDao;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.fx.configuration.mail.parameters.Fx_Mail_PasswordRecover;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.mail.I_MailJobScheduler;
import org.ibpnh.core.utils.HashUtils;
import org.ibpnh.core.utils.I_DateUtils;
import org.ibpnh.core.utils.StringUtils;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.RegisteredUserVo;
import org.ibpnh.core.vo.configuration.mail.MailVo;
import org.ibpnh.core.vo.user.PasswordRecoverVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * @author Axel Collard Bovy
 * 
 */
public class Fx_SendRecoverMail extends AbstractFxImpl implements I_Fx {

	/**
	 * Password recover DAO.
	 */
	@Autowired
	private I_PasswordRecoverDao passwordRecoverDao;

	/**
	 * Registered User DAO.
	 */
	@Autowired
	private I_RegisteredUserDao registeredUserDao;

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/**
	 * Mail Job Scheduler.
	 */
	@Autowired
	private I_MailJobScheduler mailJobScheduler;
	
	/**
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateUtils;

	/**
	 * The registered user.
	 */
	private RegisteredUserVo registeredUserVo;

	/**
	 * Email to send the recover info.
	 */
	private String email;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		if (StringUtils.isBlank(this.getEmail())
				|| !StringUtils.validMail(this.getEmail())) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.sendRecoverMail.validation.email"));
		}

		this.setRegisteredUserVo(this.getRegisteredUserDao().findByEmail(
				this.getEm(), this.getEmail()));
		if (this.getRegisteredUserVo() == null) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.sendRecoverMail.validation.emailUser"));
		}

		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractFxImpl#_completeAlert(org.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getWebContextHolder().getMessage(
				"fx.sendRecoverMail.alert.description.charged",
				new String[] { this.getEmail(),
						this.getRegisteredUserVo().getUserId().toString() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("entering Fx_SendRecoverMail._execute()");

		try {
			this.beginTransaction();

			// retreive the user
			UserVo userVo = this.getUserDao().getById(this.getEm(),
					this.getRegisteredUserVo().getUserId());

			PasswordRecoverVo passwordRecover = new PasswordRecoverVo();
			passwordRecover.setUser(userVo);
			passwordRecover.setRecovered(Boolean.FALSE);
			passwordRecover.setPublicHash(HashUtils
					.hashPasswordSHA512(this.getDateUtils().formateDateTime(new Date())
							+ HashUtils.generateRandomPassword(10, Boolean.FALSE, Boolean.FALSE)));
			passwordRecover.setPrivateHash(HashUtils
					.hashPasswordSHA512(this.getDateUtils().formateDateTime(new Date())
							+ HashUtils.generateRandomPassword(10, Boolean.FALSE, Boolean.FALSE)));
			this.getPasswordRecoverDao().persist(this.getEm(), passwordRecover);

			// send email
			Fx_Mail_PasswordRecover fx = this.getFxFactory().getNewFxInstance(
					Fx_Mail_PasswordRecover.class);
			fx.setPublicHash(passwordRecover.getPublicHash());
			fx.setEmail(this.getEmail());
			this.getMailJobScheduler().triggerMail(
					MailVo.PASSWORD_RECOVER_NAME, fx);

			this.commitTransaction();

			return JsonResponse.ok(
					passwordRecover.getPrivateHash(),
					this.getWebContextHolder().getMessage(
							"fx.sendRecoverMail.description.sent"));
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			this.rollbackTransaction();

			return this.getWebContextHolder().unexpectedErrorResponse();
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the registeredUserVo
	 */
	public RegisteredUserVo getRegisteredUserVo() {
		return this.registeredUserVo;
	}

	/**
	 * @param registeredUserVo
	 *            the registeredUserVo to set
	 */
	public void setRegisteredUserVo(RegisteredUserVo registeredUserVo) {
		this.registeredUserVo = registeredUserVo;
	}

	/**
	 * @return the registeredUserDao
	 */
	public I_RegisteredUserDao getRegisteredUserDao() {
		return this.registeredUserDao;
	}

	/**
	 * @param registeredUserDao
	 *            the registeredUserDao to set
	 */
	public void setRegisteredUserDao(I_RegisteredUserDao registeredUserDao) {
		this.registeredUserDao = registeredUserDao;
	}

	/**
	 * @return the userDao
	 */
	public I_UserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(I_UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the passwordRecoverDao
	 */
	public I_PasswordRecoverDao getPasswordRecoverDao() {
		return this.passwordRecoverDao;
	}

	/**
	 * @param passwordRecoverDao
	 *            the passwordRecoverDao to set
	 */
	public void setPasswordRecoverDao(I_PasswordRecoverDao passwordRecoverDao) {
		this.passwordRecoverDao = passwordRecoverDao;
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
	 * @return the mailJobScheduler
	 */
	public I_MailJobScheduler getMailJobScheduler() {
		return this.mailJobScheduler;
	}

	/**
	 * @param mailJobScheduler
	 *            the mailJobScheduler to set
	 */
	public void setMailJobScheduler(I_MailJobScheduler mailJobScheduler) {
		this.mailJobScheduler = mailJobScheduler;
	}

	/**
	 * @return the dateUtils
	 */
	public I_DateUtils getDateUtils() {
		return this.dateUtils;
	}

	/**
	 * @param dateUtils the dateUtils to set
	 */
	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}

}
