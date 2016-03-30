package org.kairos.ibpnh.core.fx.login;

import org.apache.commons.lang.StringUtils;
import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.user.I_UserDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * FX for User hash Enabling.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_EnableUserByHash extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_EnableUserByHash.class);

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * The enabling hash.
	 */
	private String enablingHash;

	/**
	 * Only for the alert.
	 */
	private String username;

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
	 * AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getWebContextHolder().getMessage(
				"fx.register.alert.enabled",
				new String[] { this.getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("entering Fx_Register._execute()");

		JsonResponse jsonResponse = null;

		if (StringUtils.isBlank(this.getEnablingHash())) {
			this.logger.error("the enabling hash was blank");

			return this.getWebContextHolder().unexpectedErrorResponse();
		}

		try {
			this.getEm().getTransaction().begin();

			UserVo userVo = this.getUserDao().getUserByEnablingHash(
					this.getEm(), this.getEnablingHash());

			if (userVo == null) {
				jsonResponse = JsonResponse.error(
						"",
						this.getWebContextHolder().getMessage(
								"fx.register.error.notFound"));
			} else {
				userVo.setEnabled(Boolean.TRUE);
				userVo.setDisabledCause(null);
				userVo.setFirstLogin(Boolean.FALSE);
				userVo.setEnablingHash(null);

				this.getUserDao().setDeepMapping(Boolean.FALSE);
				this.getUserDao().persist(this.getEm(), userVo);

				jsonResponse = JsonResponse.ok("", this.getWebContextHolder()
						.getMessage("fx.register.enabled.description"));
			}

			this.getEm().getTransaction().commit();
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			if (this.getEm().getTransaction().isActive()) {
				try {
					this.getEm().getTransaction().rollback();
				} catch (Exception e2) {
					this.logger.error("error rollbacking transaction", e2);
					// does nothing
				}
			}
		}

		return jsonResponse;
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
	 * @return the enablingHash
	 */
	public String getEnablingHash() {
		return this.enablingHash;
	}

	/**
	 * @param enablingHash
	 *            the enablingHash to set
	 */
	public void setEnablingHash(String enablingHash) {
		this.enablingHash = enablingHash;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
