package org.ibpnh.core.fx.user;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.fx.I_FxFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * Reset Any Users Password Function
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_ResetAnyUserPassword extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory
			.getLogger(Fx_ResetAnyUserPassword.class);

	/**
	 * User Dao.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_ResetAnyUserPassword._validate()");

		if (!this.getVo()
				.getRole()
				.getRoleType()
				.getPasswordReseterId()
				.equals(this.getWebContextHolder().getUserVo().getRole()
						.getRoleType().getId())) {
			

			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.user.validation.cannotResetPassword", null);
			
			return FxValidationResponse.error(jsonResponseMessage);
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
		alertVo.setPriority(E_Priority.HIGH);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.resetAnyUserPassword.alert",
				new String[] { this.getVo().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ResetAnyUserPassword._execute()");

		Fx_ResetPassword fx_reset = this.getFxFactory().getNewFxInstance(
				Fx_ResetPassword.class);

		fx_reset.setVo(this.getVo());
		fx_reset.setEm(this.getEm());

		JsonResponse response = fx_reset.execute();

		if (response.getOk()) {
			return JsonResponse.ok(
					this.getGson().toJson(this.getVo()),
					this.getRealMessageSolver().getMessage(
							"fx.resetAnyUserPassword.reseted",
							new String[] { this.getVo().getUsername() }));
		} else {
			return response;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#getVo()
	 */
	@Override
	public UserVo getVo() {
		return (UserVo) super.getVo();
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
	 * @return the logger
	 */
	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
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

}
