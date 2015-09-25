package org.kairos.ibpnh.fx.user;

import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FX for modifying a user.
 * 
 * @author acollard
 * 
 */
public class Fx_ModifyUser extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyUser.class);

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
		this.logger.debug("executing Fx_ModifyUser._execute()");

		try {
//			this.beginTransaction();

			// we persist the entity
			this.getEntity().setPassword(HashUtils.hashPassword(this.getEntity().getPassword(), this.getEntity().getHashCost()));
			User user = this.getDao().persist(this.getEntity());

//			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(user),
					this.getRealMessageSolver().getMessage(
							"default.entity.modified.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.user.username", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyUser._execute()", e);
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
		this.logger.debug("executing Fx_ModifyUser.validate()");

//		String result = this.getEntity().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

		if (!this.getDao().checkUsernameUniqueness(this.getEntity().getUsername(), this.getEntity().getId())) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.user.validation.nonUniqueCode",
							new String[] { this.getEntity().getUsername() });

			return FxValidationResponse.error(jsonResponseMessage);
		} else if (this.getEntity().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage(
							"default.entity.modified.error",
							new String[] {
									this.getRealMessageSolver().getMessage(
											"entity.user.name", null),
									errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			User user = this.getDao().getById(this.getEntity().getId());

			if (user == null) {

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.user.validation.entityNotExists",
								new String[] { this.getRealMessageSolver()
										.getMessage("default.modify", null) });

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
//		alertVo.setPriority(E_Priority.LOW);
//
//		alertVo.setDescription(this.getRealMessageSolver().getMessage(
//				"fx.user.alert.description.modified",
//				new String[] { this.getEntity().getDescription() }));
//	}

	/**
	 * Class VO
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
