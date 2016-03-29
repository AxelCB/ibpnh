package org.ibpnh.core.fx.user;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.fx.I_FxFactory;
import org.ibpnh.core.fx.configuration.mail.parameters.Fx_Mail_Activation;
import org.ibpnh.core.fx.login.Fx_Logout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.person.I_PersonDao;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.mail.I_MailJobScheduler;
import org.ibpnh.core.utils.HashUtils;
import org.ibpnh.core.utils.I_DateUtils;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.mail.MailVo;
import org.ibpnh.core.vo.person.PersonVo;

/**
 * Updates user's personal data.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_UpdatePersonalData extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory
			.getLogger(Fx_UpdatePersonalData.class);

	/**
	 * Person DAO.
	 */
	@Autowired
	private I_PersonDao personDao;

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * Mail Job Scheduler.
	 */
	@Autowired
	private I_MailJobScheduler mailJobScheduler;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;
	
	/**
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		String result = this.getVo().validate(this.getWebContextHolder());
		
		if (StringUtils.isNotBlank(this.getVo().getEmail())) {
			if (!this.getPersonDao().checkEmailUniqueness(this.getEm(),
					this.getVo().getEmail(), this.getVo().getId())) {
				return FxValidationResponse.error(this.getWebContextHolder()
						.getMessage("fx.nonUniqueEmail"));
			}
		}

		if (result != null) {
			return FxValidationResponse.error(result);
		} else {
			return FxValidationResponse.ok();
		}
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
		alertVo.setPriority(E_Priority.LOW);

		alertVo.setDescription(this.getWebContextHolder().getMessage(
				"fx.updatePersonalData.alert.updated"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("entering Fx_UpdatePersonalData._execute()");

		try {
			this.beginTransaction();

			PersonVo personVo = this.getPersonDao().getById(this.getEm(),
					this.getVo().getId());

			// we persist the data
			this.setVo(this.getPersonDao().persist(this.getEm(), this.getVo()));

			JsonResponse response = null;
			if (StringUtils.isNotBlank(this.getVo().getEmail())
					&& (StringUtils.isBlank(personVo.getEmail()) || (StringUtils
							.isNotBlank(personVo.getEmail()) && !personVo
							.getEmail().equals(this.getVo().getEmail())))) {

				// we hash the current date as string concatenated with a random
				// sequence of 10 characters, and use that hash to enable the
				// user
				this.getWebContextHolder()
						.getUserVo()
						.setEnablingHash(
								HashUtils.hashPasswordSHA512(this.getDateUtils().formateDateTime(new Date())
										+ HashUtils.generateRandomPassword(10, Boolean.FALSE, Boolean.FALSE)));
				this.getWebContextHolder()
						.getUserVo()
						.disable(
								this.getWebContextHolder().getMessage(
										"fx.register.disabled"));

				this.getUserDao().persist(this.getEm(),
						this.getWebContextHolder().getUserVo());

				// send email
				Fx_Mail_Activation fx = this.getFxFactory().getNewFxInstance(
						Fx_Mail_Activation.class);
				fx.setActivationHash(this.getWebContextHolder().getUserVo()
						.getEnablingHash());
				fx.setUsername(this.getWebContextHolder().getUserVo()
						.getUsername());
				fx.setName(this.getVo().getName());
				fx.setSurname(this.getVo().getSurname());
				fx.setEmail(this.getVo().getEmail());
				this.getMailJobScheduler().triggerMail(
						MailVo.ACTIVATION_EMAIL_NAME, fx);

				Fx_Logout fxLogout = this.getFxFactory().getNewFxInstance(
						Fx_Logout.class);
				fxLogout.execute();

				response = JsonResponse.ok(
						this.getGson().toJson(this.getVo()),
						this.getWebContextHolder().getMessage(
								"fx.updatePersonalData.description.updated"),
						this.getWebContextHolder().getMessage(
								"fx.updatePersonalData.description.mailSent"));
				response.setAction(JsonResponse.ACTION_RELOGIN);
			} else {
				response = JsonResponse.ok(
						this.getGson().toJson(this.getVo()),
						this.getWebContextHolder().getMessage(
								"fx.updatePersonalData.description.updated"));
			}

			this.commitTransaction();

			return response;
		} catch (Exception e) {
			this.logger.error(
					"error executing Fx_UpdatePersonalData._execute()", e);
			
			this.rollbackTransaction();

			return this.unexpectedErrorResponse();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#getVo()
	 */
	@Override
	public PersonVo getVo() {
		return (PersonVo) super.getVo();
	}

	/**
	 * @return the personDao
	 */
	public I_PersonDao getPersonDao() {
		return this.personDao;
	}

	/**
	 * @param personDao
	 *            the personDao to set
	 */
	public void setPersonDao(I_PersonDao personDao) {
		this.personDao = personDao;
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
