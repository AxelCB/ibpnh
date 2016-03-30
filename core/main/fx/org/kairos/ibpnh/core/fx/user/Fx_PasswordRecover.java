package org.kairos.ibpnh.core.fx.user;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.dao.user.I_PasswordRecoverDao;
import org.kairos.ibpnh.core.dao.user.I_UserDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.utils.HashUtils;
import org.kairos.ibpnh.core.utils.I_PasswordUtils;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.user.PasswordRecoverVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * @author Axel Collard Bovy
 * 
 */
public class Fx_PasswordRecover extends AbstractFxImpl implements I_Fx {

	/**
	 * Password recover DAO.
	 */
	@Autowired
	private I_PasswordRecoverDao passwordRecoverDao;

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Password utils
	 */
	@Autowired
	private I_PasswordUtils passwordUtils;
	
	/**
	 * The private hash.
	 */
	private String privateHash;

	/**
	 * The public hash.
	 */
	private String publicHash;

	/**
	 * The new password to set.
	 */
	private String newPassword;

	/**
	 * The password recover VO.
	 */
	private PasswordRecoverVo passwordRecoverVo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		if (StringUtils.isBlank(this.getPrivateHash())) {
			return FxValidationResponse.error(this
					.getWebContextHolder()
					.unexpectedErrorResponse(
							ErrorCodes.ERROR_PRIVATE_HASH_MISSING)
					.getMessages().get(0));
		}

		if (StringUtils.isBlank(this.getPublicHash())) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.passwordRecover.validation.publicHash"));
		}

		// we try to get the user that is trying to change it's password
		this.setPasswordRecoverVo(this.getPasswordRecoverDao().getByHashes(
				this.getEm(), this.getPrivateHash(), this.getPublicHash()));

		if (this.getPasswordRecoverVo() == null) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.passwordRecover.validation.userNotFound"));
		}

		if (StringUtils.isBlank(this.getNewPassword())) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.passwordRecover.validation.blankPassword"));
		}

		if (passwordUtils.formatCheck(this.getNewPassword())) {
			this.logger.error("password does not match against stored password regExps");
			
			ParameterVo errorMessageParameter = this.getParameterDao().
					getByName(this.getEm(), "fx.changePassword.validation.formatError");
			
			String jsonResponseMessage = errorMessageParameter.getValue();
//					this.getRealMessageSolver()
//					.getMessage(
//							"fx.changePassword.validation.alphanumericError",
//							new String[] {});

			return FxValidationResponse.error(jsonResponseMessage);
		}

		if (this.getPasswordRecoverVo().getUser().getUsername()
				.equals(this.getNewPassword())) {
			this.logger.error("the password is the same as the username");

			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.changePassword.validation.sameAsUsername",
							new String[] {});

			return FxValidationResponse.error(jsonResponseMessage);
		}

		try {
			Long minCharacters = this
					.getParameterDao()
					.getByName(this.getEm(),
							ParameterVo.PASSWORD_MIN_CHARACTERS)
					.getValue(Long.class);

			if (this.getNewPassword().length() < minCharacters) {
				this.logger.error("password does not have the min characters");

				String jsonResponseMessage = this
						.getRealMessageSolver()
						.getMessage(
								"fx.changePassword.validation.minCharactersError",
								new Long[] { minCharacters });

				return FxValidationResponse.error(jsonResponseMessage);
			}
		} catch (ParseException e) {
			this.logger.error("failed to value of parameter {}",
					ParameterVo.LOGIN_MAX_ATTEMPTS);

			return FxValidationResponse
					.error(this
							.unexpectedErrorResponse(
									ErrorCodes.ERROR_PARAMETER_PARSING)
							.getMessages().toArray(new String[] {}));
		}

		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getWebContextHolder().getMessage(
				"fx.passwordRecover.alert.description.recovered",
				new String[] { this.getPasswordRecoverVo().getUser()
						.getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("entering Fx_PasswordRecover._execute()");

		try {
			this.beginTransaction();
			
			Long hashCost = this.getParameterDao()
					.getByName(this.getEm(), ParameterVo.HASH_COST)
					.getValue(Long.class);

			// retrieve the user
			this.getPasswordRecoverVo().setRecovered(Boolean.TRUE);
			this.getPasswordRecoverDao().persist(this.getEm(),
					this.getPasswordRecoverVo());

			UserVo userVo = this.getPasswordRecoverVo().getUser();
			userVo.setLoginAttempts(0);
			userVo.setHashCost(hashCost);
			userVo.setPassword(HashUtils.hashPassword(this.getNewPassword(),
					hashCost));
			this.getUserDao().setDeepMapping(Boolean.FALSE);
			this.getUserDao().persist(this.getEm(), userVo);

			this.commitTransaction();

			return JsonResponse.ok(
					"",
					this.getWebContextHolder().getMessage(
							"fx.passwordRecover.description.recovered"));
		} catch (ParseException pe) {
			this.logger.error("parameter parse error", pe);

			this.rollbackTransaction();

			return this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			this.rollbackTransaction();

			return this.getWebContextHolder().unexpectedErrorResponse();
		}
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
	 * @return the privateHash
	 */
	public String getPrivateHash() {
		return this.privateHash;
	}

	/**
	 * @param privateHash
	 *            the privateHash to set
	 */
	public void setPrivateHash(String privateHash) {
		this.privateHash = privateHash;
	}

	/**
	 * @return the publicHash
	 */
	public String getPublicHash() {
		return this.publicHash;
	}

	/**
	 * @param publicHash
	 *            the publicHash to set
	 */
	public void setPublicHash(String publicHash) {
		this.publicHash = publicHash;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return this.newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the passwordRecoverVo
	 */
	public PasswordRecoverVo getPasswordRecoverVo() {
		return this.passwordRecoverVo;
	}

	/**
	 * @param passwordRecoverVo
	 *            the passwordRecoverVo to set
	 */
	public void setPasswordRecoverVo(PasswordRecoverVo passwordRecoverVo) {
		this.passwordRecoverVo = passwordRecoverVo;
	}

	/**
	 * @return the parameterDao
	 */
	public I_ParameterDao getParameterDao() {
		return this.parameterDao;
	}

	/**
	 * @param parameterDao
	 *            the parameterDao to set
	 */
	public void setParameterDao(I_ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

}
