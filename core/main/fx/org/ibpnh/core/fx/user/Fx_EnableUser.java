package org.ibpnh.core.fx.user;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.fx.user.roleType.Fx_CreateRoleType;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * FX for Creating a RoleType
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_EnableUser extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateRoleType.class);

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_EnableUser.validate()");

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
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.enableUser.alert.description",
				new String[] { this.getVo().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_EnableUser._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			this.getDao().setDeepMapping(Boolean.FALSE);
			this.setVo(this.getDao()
					.getById(this.getEm(), this.getVo().getId()));
			this.getVo().enable();
			UserVo userVo = this.getDao().persist(this.getEm(), this.getVo());

			this.commitTransaction();

			this.setVo(userVo);

			return JsonResponse.ok(
					this.getGson().toJson(userVo),
					this.getRealMessageSolver().getMessage("fx.disableUser.ok",
							new String[] { this.getVo().getUsername() }));

		} catch (Exception e) {
			this.logger.error("error executing Fx_EnableUser._execute()", e);
			
			this.rollbackTransaction();

			return this.unexpectedErrorResponse();
		}
	}

	/**
	 * Casted VO.
	 */
	@Override
	public UserVo getVo() {
		return (UserVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_UserDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_UserDao dao) {
		this.dao = dao;
	}

}
