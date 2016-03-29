package org.ibpnh.core.fx.login;

import java.text.ParseException;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.caching.client.api.I_UserCacheManager;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.utils.HashUtils;
import org.ibpnh.core.utils.I_PasswordUtils;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.vo.user.ChangePasswordVo;
import org.ibpnh.core.vo.user.UserVo;
import org.ibpnh.core.web.WebContextHolder;

/**
 * Change Password Function
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_ChangePassword extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ChangePassword.class);

	/**
	 * User Dao.
	 */
	@Autowired
	private I_UserDao userDao;

	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * User cache manager.
	 */
	@Autowired
	private I_UserCacheManager userCacheManager;

	/**
	 * Password utils
	 */
	@Autowired
	private I_PasswordUtils passwordUtils;
	
	/**
	 * Response Strategy.
	 */
	private I_ChangePasswordResponseStrategy strategy;

	/**
	 * UserVo.
	 */
	private UserVo userVo;

	/**
	 * Current Check flag.
	 */
	private Boolean checkCurrentPassword;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_ChangePassword._validate()");

		if (this.getStrategy() == null) {
			// default strategy
			this.setStrategy(new WebChangePasswordResponseStrategy(this
					.getGson(), this.getRealMessageSolver()));
		}
		if (this.getUserVo() == null) {
			// default user
			this.setUserVo(this.getRealMessageSolver().getUserVo());
		}
		if (this.getCheckCurrentPassword() == null) {
			// default flag
			this.setCheckCurrentPassword(Boolean.TRUE);
		}

		if (this.getCheckCurrentPassword()
				&& !passwordUtils.checkPassword(this.getVo()
						.getCurrentPassword(), this.getUserVo(), this
						.getUserVo().getHashCost())) {
			this.logger.error("current password does not match");

			JsonResponse response = this.getStrategy().badCurrentPassword();

			return FxValidationResponse.error(response.getMessages()).withData(
					response.getData());
		} else if (this.getVo().getNewPassword()
				.equals(this.getVo().getCurrentPassword())) {
			this.logger.error("new password is equal as current password");

			JsonResponse response = this.getStrategy()
					.newPasswordEqualsCurrent();

			return FxValidationResponse.error(response.getMessages()).withData(
					response.getData());
		} else if (passwordUtils.formatCheck(this.getVo()
				.getNewPassword())) {
			
			ParameterVo errorMessageParameter = this.getParameterDao().
					getByName(this.getEm(), "fx.changePassword.validation.formatError");
			
			this.logger.error("password does not match against stored password regExps");

			JsonResponse response = this.getStrategy()
					.newPasswordAlphanumericError(errorMessageParameter.getValue());

			return FxValidationResponse.error(response.getMessages()).withData(
					response.getData());
		} else if (this.getUserVo().getUsername()
				.equals(this.getVo().getNewPassword())) {
			this.logger.error("the password is the same as the username");

			JsonResponse response = this.getStrategy()
					.newPasswordEqualsUsername();

			return FxValidationResponse.error(response.getMessages()).withData(
					response.getData());
		} else {
			try {
				Long minCharacters = this
						.getParameterDao()
						.getByName(this.getEm(),
								ParameterVo.PASSWORD_MIN_CHARACTERS)
						.getValue(Long.class);

				if (this.getVo().getNewPassword().length() < minCharacters) {
					this.logger
							.error("password does not have the min characters");

					JsonResponse response = this.getStrategy()
							.newPasswordMinimumCharactersError(minCharacters);

					return FxValidationResponse.error(response.getMessages())
							.withData(response.getData());
				}
			} catch (ParseException e) {
				this.logger.error("failed to get value of parameter {}",
						ParameterVo.LOGIN_MAX_ATTEMPTS);

				JsonResponse response = this.getStrategy().unexpectedError(
						ErrorCodes.ERROR_PARAMETER_PARSING);

				return FxValidationResponse.error(response.getMessages())
						.withData(response.getData());
			} catch (Exception e) {
				this.logger.error("failed to get parameter {}",
						ParameterVo.LOGIN_MAX_ATTEMPTS);

				JsonResponse response = this.getStrategy()
						.unexpectedError(null);

				return FxValidationResponse.error(response.getMessages())
						.withData(response.getData());
			}
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
		alertVo.setPriority(E_Priority.LOW);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.changePassword.alert",
				new String[] { this.getUserVo().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ChangePassword._execute()");

		try {
			this.getEm().getTransaction().begin();

			Long hashCost = this.getParameterDao()
					.getByName(this.getEm(), ParameterVo.HASH_COST)
					.getValue(Long.class);

			UserVo userVo = this.getUserDao().getByUsername(this.getEm(),
					this.getUserVo().getUsername());
			userVo.setPassword(HashUtils.hashPassword(this.getVo()
					.getNewPassword(), hashCost));
			userVo.setHashCost(hashCost);
			// we set the first login to false
			userVo.setFirstLogin(Boolean.FALSE);

			UserVo newUserVo = this.getUserDao().persist(this.getEm(), userVo);
			newUserVo.setToken(this.getUserVo().getToken());

			this.getEm().getTransaction().commit();

			this.getUserCacheManager().putUser(this.getUserVo().getToken(),
					newUserVo);
			this.getUserCacheManager().putUser(this.getUserVo().getUsername(),
					newUserVo);

			return this.getStrategy().passwordChanged(newUserVo);
		} catch (Exception e) {
			this.logger
					.error("error executing Fx_ChangePassword._execute()", e);
			try {
				this.getEm().getTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			return this.getStrategy().unexpectedError(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#getVo()
	 */
	@Override
	public ChangePasswordVo getVo() {
		return (ChangePasswordVo) super.getVo();
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

	/**
	 * @return the webContextHolder
	 */
	@Override
	public WebContextHolder getRealMessageSolver() {
		return this.webContextHolder;
	}

	/**
	 * @param webContextHolder
	 *            the webContextHolder to set
	 */
	@Override
	public void setWebContextHolder(WebContextHolder webContextHolder) {
		this.webContextHolder = webContextHolder;
	}

	/**
	 * @return the userCacheManager
	 */
	public I_UserCacheManager getUserCacheManager() {
		return this.userCacheManager;
	}

	/**
	 * @param userCacheManager
	 *            the userCacheManager to set
	 */
	public void setUserCacheManager(I_UserCacheManager userCacheManager) {
		this.userCacheManager = userCacheManager;
	}

	/**
	 * @return the strategy
	 */
	public I_ChangePasswordResponseStrategy getStrategy() {
		return this.strategy;
	}

	/**
	 * @param strategy
	 *            the strategy to set
	 */
	public void setStrategy(I_ChangePasswordResponseStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return this.userVo;
	}

	/**
	 * @param userVo
	 *            the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	/**
	 * @return the checkCurrentPassword
	 */
	public Boolean getCheckCurrentPassword() {
		return this.checkCurrentPassword;
	}

	/**
	 * @param checkCurrentPassword
	 *            the checkCurrentPassword to set
	 */
	public void setCheckCurrentPassword(Boolean checkCurrentPassword) {
		this.checkCurrentPassword = checkCurrentPassword;
	}

}
