package org.ibpnh.core.fx.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.caching.client.api.I_UserCacheManager;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.web.WebContextHolder;

/**
 * Logout function.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_Logout extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_Logout.class);

	/**
	 * User Cache manager.
	 */
	@Autowired
	private I_UserCacheManager userCacheManager;

	/**
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
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
				"fx.logout.alert",
				new String[] { this.getRealMessageSolver().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("logging out user {}", this.getRealMessageSolver()
				.getUsername());

		this.getUserCacheManager().removeUser(
				this.getRealMessageSolver().getToken());
		this.getUserCacheManager().removeUser(
				this.getRealMessageSolver().getUsername());

		return JsonResponse.ok("");
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

}
