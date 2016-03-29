package org.ibpnh.core.fx.user;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * FX for deleting a user.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_DeleteUser extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteUser.class);

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
		this.logger.debug("executing Fx_DeleteUser.validate()");

		if (this.getVo().getId() == null) {
			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage(
							"default.entity.deleted.error",
							new String[] {
									this.getRealMessageSolver().getMessage(
											"entity.user.name", null),
									errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			UserVo userVo = this.getDao().getById(this.getEm(),
					this.getVo().getId());

			if (userVo == null) {
				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.deleteUser.validation.entityNotExists",
								new String[] { this.getRealMessageSolver()
										.getMessage("default.delete", null) });

				return FxValidationResponse.error(jsonResponseMessage);
			} else {
				return FxValidationResponse.ok();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_DeleteUser._execute()");

		try {
			this.getEm().getTransaction().begin();

			// we persist the entity
			this.getDao().delete(this.getEm(), this.getVo());

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					"",
					this.getRealMessageSolver().getMessage(
							"default.entity.deleted.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.user.name", null) }));
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteUser._execute()", e);
			try {
				this.getEm().getTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			return this.unexpectedErrorResponse();
		}
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
				"fx.deleteUser.alert.description.deleted",
				new String[] { this.getVo().getUsername() }));
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
