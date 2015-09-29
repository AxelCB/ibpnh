package org.kairos.ibpnh.fx.user;

import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FX for creating a user.
 * 
 * @author acollard
 * 
 */
public class Fx_CreateUser extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateUser.class);

	/**
	 * User DAO.
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
		this.logger.debug("executing Fx_CreateFunction._execute()");

		try {
//			this.beginTransaction();

			// we persist the entity
			this.getEntity().setHashCost(10L);
			this.getEntity().setEnabled(Boolean.TRUE);
			this.getEntity().setLoginAttempts(0);
			this.getEntity().setPassword(HashUtils.hashPassword(this.getEntity().getPassword(), this.getEntity().getHashCost()));
			this.getEntity().setFirstLogin(Boolean.FALSE);
			User user = this.getDao().persist(this.getEntity());
			this.setEntity(user);

//			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(user),
					this.getRealMessageSolver().getMessage(
							"default.entity.created.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.user.username", null) }));
		} catch (Exception e) {
			this.logger.error(
					"error executing Fx_CreateFunction.execute()", e);
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
		this.logger.debug("executing Fx_CreateFunction._validate()");

//		String result = this.getEntity().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

		if (!this.getDao().checkUsernameUniqueness(this.getEntity().getUsername(), null)) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.user.validation.nonUniqueUsername",
							new String[] { this.getEntity().getUsername() });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			return FxValidationResponse.ok();
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
//				"fx.user.alert.description.created",
//				new String[] { this.getEntity().getDescription() }));
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
