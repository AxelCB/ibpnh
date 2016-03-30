package org.kairos.ibpnh.core.fx.user.personalData;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.user.I_RegisteredUserDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.user.RegisteredUserVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * Gets the personal data for a driver user.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_GetPersonalDataDriverUser extends AbstractFxImpl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(Fx_GetPersonalDataDriverUser.class);

	/**
	 * Registered User DAO.
	 */
	@Autowired
	private I_RegisteredUserDao registeredUserDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		// we hard code the fire alert flag
		this.setFireAlert(Boolean.FALSE);
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
		// does nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("entering Fx_GetPersonalDataDriverUser._execute()");

		try {
			RegisteredUserVo user = this.getRegisteredUserVo(this.getEm());

			return JsonResponse.ok(this.getGson().toJson(user.getPerson()));
		} catch (ResponseException re) {
			this.logger.error("response exception", re);

			return re.getJsonResponse();
		} catch (Exception e) {
			this.logger.error("unexpected exception", e);

			return this.unexpectedErrorResponse();
		}
	}

	/**
	 * Gets the registered user of the logged user.
	 * 
	 * @param em
	 *            he entity manager
	 * 
	 * @return RegisteredUserVo
	 */
	protected RegisteredUserVo getRegisteredUserVo(EntityManager em)
			throws ResponseException {
		RegisteredUserVo registeredUserVo = this.getRegisteredUserDao()
				.findByUser(em, this.getWebContextHolder().getUserVo());

		if (registeredUserVo == null) {
			JsonResponse response = JsonResponse.error(
					"",
					this.getWebContextHolder().getMessage(
							"cellphoneUser.validation.NotRegisteredUser"));
			throw new ResponseException("registered user is null",
					ErrorCodes.ERROR_USER_NOT_REGISTERED, null, response);
		}
		return registeredUserVo;
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
}
