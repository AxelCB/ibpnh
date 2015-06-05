package org.kairos.ibpnh.fx.login;

import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.services.caching.client.api.I_UserCacheManager;
import org.kairos.ibpnh.utils.HashUtils;
import org.kairos.ibpnh.utils.I_PasswordUtils;
import org.kairos.ibpnh.vo.user.UserCredentialsVo;
import org.kairos.ibpnh.vo.user.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Login Function.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
public class Fx_Login extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_Login.class);

	/**
	 * User Dao.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * Parameter Dao.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * User Cache Manager.
	 */
	@Autowired
	private I_UserCacheManager userCacheManager;
	
	/**
	 * Password utils
	 */
	@Autowired
	private I_PasswordUtils passwordUtils;

	/**
	 * Login Response Strategy.
	 */
	private I_LoginResponseStrategy loginResponseStrategy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		if (this.getLoginResponseStrategy() == null) {
			// default strategy
			this.setLoginResponseStrategy(new WebLoginResponseStrategy(this
					.getWebContextHolder(), this.getGson()));
		}
		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.fx.AbstractFxImpl#_completeAlert(org.universe.core.
	 * vo.alert.AlertVo)
	 */
//	@Override
//	protected void _completeAlert(AlertVo alertVo) {
//		alertVo.setPriority(E_Priority.LOW);
//
//		alertVo.setDescription(this.getRealMessageSolver().getMessage(
//				"fx.login.alert", new String[] { this.getVo().getUsername() }));
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_Login._execute() (username: {})", this
				.getVo().getUsername());

		UserVo userVo = null;

		String token = null;

		try {
			// response to send
			JsonResponse response = null;

			this.getPm().currentTransaction().begin();

			Long maxAttempts = 5l;
//					this.getParameterDao()
//					.getByName(this.getPm(), ParameterVo.LOGIN_MAX_ATTEMPTS)
//					.getValue(Long.class);
			Long hashCost = 5l;
//					this.getParameterDao()
//					.getByName(this.getPm(), ParameterVo.HASH_COST)
//					.getValue(Long.class);
			if (userVo == null) {
				userVo = this.getUserDao().getByUsername(this.getPm(),
						this.getVo().getUsername());
			}

			if (userVo == null) {
				this.logger.debug("failed to find user with username {}", this
						.getVo().getUsername());

				response = this.getLoginResponseStrategy().userNotFound();
			} else if (!userVo.getEnabled()) {
				this.logger.debug("the user is disabled");

				response = this.getLoginResponseStrategy().disabledUser(
						userVo.getDisabledCause());
			} else if (userVo.getLoginAttempts() >= maxAttempts) {
				// block the user
				this.logger
						.debug("not allowing the user to log in, max failed attempts reached");

				response = this.getLoginResponseStrategy().maxAttempts();
			} else if (!passwordUtils.checkPassword(this.getVo().getPassword(),
					userVo, hashCost)) {
				// adds the failed attempt
				userVo.setLoginAttempts(userVo.getLoginAttempts() + 1);

				this.logger.debug(
						"failed to login user {}, total failed attempts: {}",
						this.getVo().getUsername(), userVo.getLoginAttempts());

				if (userVo.getLoginAttempts() < maxAttempts) {
					// we return the remaining attempts messages
					response = this.getLoginResponseStrategy().badPassword(
							maxAttempts - userVo.getLoginAttempts());

					this.getUserDao().persist(this.getPm(), userVo);
				} else {
					// block the user
					this.logger
							.debug("not allowing the user to log in, max failed attempts reached");

					response = this.getLoginResponseStrategy().maxAttempts();

					this.getUserDao().persist(this.getPm(), userVo);
				}
			} else {
				this.logger.debug("user properly logged");
				// properly logged, reset the attempts
				userVo.setLoginAttempts(0);

				UserVo newUserVo = this.getUserDao().persist(this.getPm(),
						userVo);

				// order the menu
				this.getUserDao().orderMenu(newUserVo);

				// try to get the existing user from the cache
				UserVo cacheUserVo = this.getUserCacheManager().getUser(
						this.getVo().getUsername());
				if (cacheUserVo == null) {
					// there was none, we generate a new token and put it on the
					// cache
					token = HashUtils.generateUserToken(newUserVo);
					newUserVo.setToken(token);

					this.logger.debug("storing the user to the cache");
					this.getUserCacheManager().putUser(token, newUserVo);
					this.getUserCacheManager().putUser(newUserVo.getUsername(),
							newUserVo);
				} else {
					// we return the same user
					newUserVo = cacheUserVo;
				}

				response = this.getLoginResponseStrategy()
						.userLogged(newUserVo);
			}

			this.getPm().currentTransaction().commit();

			return response;
//		} catch (ParseException pe) {
//			this.logger.error("failed to value of parameter {}",
//					ParameterVo.LOGIN_MAX_ATTEMPTS, pe);
//
//			return this.getLoginResponseStrategy().unexpectedError(
//					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.error("error executing Fx_Login._execute()", e);
			try {
				this.getPm().currentTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			if (token != null) {
				this.getUserCacheManager().putUser(token, null);
			}
			if (userVo != null) {
				this.getUserCacheManager().putUser(userVo.getUsername(), null);
			}

			return this.getLoginResponseStrategy().unexpectedError(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#getVo()
	 */
	@Override
	public UserCredentialsVo getVo() {
		return (UserCredentialsVo) super.getVo();
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
	 * @return the loginResponseStrategy
	 */
	public I_LoginResponseStrategy getLoginResponseStrategy() {
		return this.loginResponseStrategy;
	}

	/**
	 * @param loginResponseStrategy
	 *            the loginResponseStrategy to set
	 */
	public void setLoginResponseStrategy(
			I_LoginResponseStrategy loginResponseStrategy) {
		this.loginResponseStrategy = loginResponseStrategy;
	}

}
