package org.kairos.ibpnh.fx.login;

import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.services.caching.client.api.I_UserCacheManager;
import org.kairos.ibpnh.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Logout function.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
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
	 * @see org.universe.core.fx.AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
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
//				"fx.logout.alert",
//				new String[] { this.getRealMessageSolver().getUsername() }));
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
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
