package org.kairos.ibpnh.fx.user;

import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FX for deleting a user.
 * 
 * @author acollard
 * 
 */
public class Fx_DeleteUser extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteUser.class);

	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_UserDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_DeleteFunction._execute()");

		try {
//			this.beginTransaction();

			// we persist the entity
			this.getDao().delete(this.getEntity());

//			this.commitTransaction();

			return JsonResponse.ok(
					"",
					this.getRealMessageSolver().getMessage(
							"default.entity.deleted.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.user.username",
											null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteFunction._execute()", e);
			try {
//				this.rollbackTransaction();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			return this.unexpectedErrorResponse();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_DeleteFunction.validate()");

		if (this.getEntity().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage("default.error.code", new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver().getMessage("default.entity.deleted.error", new String[] { this.getRealMessageSolver().getMessage("entity.user.name", null), errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			User user = this.getDao().getById(this.getEntity().getId());

			if (user == null) {
				String jsonResponseMessage = this.getRealMessageSolver().getMessage("fx.user.validation.entityNotExists", new String[] { this.getRealMessageSolver().getMessage("default.delete", null) });

				return FxValidationResponse.error(jsonResponseMessage);
			} else {
				return FxValidationResponse.ok();
			}
		}
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
//		alertVo.setPriority(E_Priority.HIGH);
//
//		alertVo.setDescription(this.getRealMessageSolver().getMessage(
//				"fx.user.alert.description.deleted",
//				new String[] { this.getEntity().getAcronym() }));
//	}

	/**
	 * Casted VO.
	 */
	@Override
	public User getEntity() {
		return (User) super.getEntity();
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
