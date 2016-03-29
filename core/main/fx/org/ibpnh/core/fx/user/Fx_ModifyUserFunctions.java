package org.ibpnh.core.fx.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * FX for Modifying a USer
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_ModifyUserFunctions extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyUserFunctions.class);

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
		this.logger.debug("executing Fx_ModifyUserFunctions.validate()");

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
				"fx.modifyUser.alert.modified",
				new String[] { this.getVo().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyUserFunctions._execute()");

		try {
			this.getEm().getTransaction().begin();

			// persist it
			this.getDao().setDeepMapping(Boolean.TRUE);
			UserVo userVo = this.getDao().getById(this.getEm(), this.getVo().getId());
			userVo.getRole().setRoleFunctions(this.getVo().getRole().getRoleFunctions());
			this.setVo(this.getDao().persist(this.getEm(), userVo));

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					this.getGson().toJson(this.getVo()),
					this.getRealMessageSolver().getMessage(
							"fx.modifyUser.alert.modified",
							new String[] { this.getVo().getUsername() }));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyUserFunctions._execute()", e);
			try {
				this.getEm().getTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e1);
			}

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
