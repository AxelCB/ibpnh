package org.kairos.ibpnh.fx.login;

import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.*;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.services.caching.client.api.I_UserCacheManager;
import org.kairos.ibpnh.utils.HashUtils;
import org.kairos.ibpnh.utils.I_PasswordUtils;
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
//	protected void _completeAlert(Alert alertVo) {
//		alertVo.setPriority(E_Priority.LOW);
//
//		alertVo.setDescription(this.getRealMessageSolver().getMessage(
//				"fx.login.alert", new String[] { this.getEntity().getUsername() }));
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_Login._execute() (username: {})", this
				.getEntity().getUsername());

		User user = null;

		String token = null;

		try {
			// response to send
			JsonResponse response = null;

//			this.getOfy().currentTransaction().begin();

			Long maxAttempts = 5l;
//					this.getParameterDao()
//					.getByName(this.getOfy(), ParameterVo.LOGIN_MAX_ATTEMPTS)
//					.getValue(Long.class);
			Long hashCost = 5l;
//					this.getParameterDao()
//					.getByName(this.getOfy(), ParameterVo.HASH_COST)
//					.getValue(Long.class);
			if (user == null) {
				user = this.getUserDao().getByUsername(this.getEntity().getUsername());
			}

			if (user == null) {
				this.logger.debug("failed to find user with username {}", this
						.getEntity().getUsername());

				response = this.getLoginResponseStrategy().userNotFound();
			} else if (!user.getEnabled()) {
				this.logger.debug("the user is disabled");

				response = this.getLoginResponseStrategy().disabledUser(
						user.getDisabledCause());
			} else if (user.getLoginAttempts() >= maxAttempts) {
				// block the user
				this.logger
						.debug("not allowing the user to log in, max failed attempts reached");

				response = this.getLoginResponseStrategy().maxAttempts();
			} else if (!passwordUtils.checkPassword(this.getEntity().getPassword(),
					user, hashCost)) {
				// adds the failed attempt
				user.setLoginAttempts(user.getLoginAttempts() + 1);

				this.logger.debug(
						"failed to login user {}, total failed attempts: {}",
						this.getEntity().getUsername(), user.getLoginAttempts());

				if (user.getLoginAttempts() < maxAttempts) {
					// we return the remaining attempts messages
					response = this.getLoginResponseStrategy().badPassword(
							maxAttempts - user.getLoginAttempts());

					this.getUserDao().persist(user);
				} else {
					// block the user
					this.logger
							.debug("not allowing the user to log in, max failed attempts reached");

					response = this.getLoginResponseStrategy().maxAttempts();

					this.getUserDao().persist(user);
				}
			} else {
				this.logger.debug("user properly logged");
				// properly logged, reset the attempts
				user.setLoginAttempts(0);

				User newUser = this.getUserDao().persist(user);

				// try to get the existing user from the cache
				User cacheUser = this.getUserCacheManager().getUser(
						this.getEntity().getUsername());
				if (cacheUser == null) {
					// there was none, we generate a new token and put it on the
					// cache
					token = HashUtils.generateUserToken(newUser);
					newUser.setToken(token);

					this.logger.debug("storing the user to the cache");
					this.getUserCacheManager().putUser(token, newUser);
					this.getUserCacheManager().putUser(newUser.getUsername(),
							newUser);
				} else {
					// we return the same user
					newUser = cacheUser;
				}

				response = this.getLoginResponseStrategy()
						.userLogged(newUser);
			}

//			this.getOfy().currentTransaction().commit();

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
//				this.getOfy().currentTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			if (token != null) {
				this.getUserCacheManager().putUser(token, null);
			}
			if (user != null) {
				this.getUserCacheManager().putUser(user.getUsername(), null);
			}

			return this.getLoginResponseStrategy().unexpectedError(null);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universe.core.fx.AbstractFxImpl#getEntity()
	 */
	@Override
	public User getEntity() {
		return (User) super.getEntity();
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
