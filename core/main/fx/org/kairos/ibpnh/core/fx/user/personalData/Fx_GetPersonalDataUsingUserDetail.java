package org.kairos.ibpnh.core.fx.user.personalData;

import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.user.I_UserDetailsDao;
import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.user.UserDetailsVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * Tries to get the personal data by using the user details.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_GetPersonalDataUsingUserDetail extends AbstractFxImpl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(Fx_GetPersonalDataUsingUserDetail.class);

	/**
	 * User details DAO.
	 */
	@Autowired
	private I_UserDetailsDao userDetailsDao;

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
		this.logger
				.debug("entering Fx_GetPersonalDataUsingUserDetail._execute()");

		try {
			UserDetailsVo userDetailsVo = this.getUserDetailsDao().findByUser(
					this.getEm(), this.getVo());

			return JsonResponse.ok(this.getGson().toJson(
					userDetailsVo.getPerson()));
		} catch (Exception e) {
			this.logger.error("unexpected exception", e);

			return this.unexpectedErrorResponse();
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
	 * @return the userDetailsDao
	 */
	public I_UserDetailsDao getUserDetailsDao() {
		return this.userDetailsDao;
	}

	/**
	 * @param userDetailsDao
	 *            the userDetailsDao to set
	 */
	public void setUserDetailsDao(I_UserDetailsDao userDetailsDao) {
		this.userDetailsDao = userDetailsDao;
	}

}
