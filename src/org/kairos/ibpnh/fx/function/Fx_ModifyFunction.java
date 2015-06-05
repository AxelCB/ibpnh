package org.kairos.ibpnh.fx.function;

import org.kairos.ibpnh.dao.user.I_FunctionDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.vo.user.FunctionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FX for modifying a Function.
 * 
 * @author acollard
 * 
 */
public class Fx_ModifyFunction extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyFunction.class);

	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_FunctionDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyFunction._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			FunctionVo FunctionVo = this.getDao().persist(this.getPm(), this.getVo());

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(FunctionVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.modified.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.Function.Functionname", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyFunction._execute()", e);
			try {
				this.rollbackTransaction();
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
		this.logger.debug("executing Fx_ModifyFunction.validate()");

//		String result = this.getVo().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

		if (!this.getDao().checkActionnameUniqueness(this.getPm(),
				this.getVo().getActionName(), this.getVo().getId())) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.Function.validation.nonUniqueCode",
							new String[] { this.getVo().getActionName() });

			return FxValidationResponse.error(jsonResponseMessage);
		} else if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage(
							"default.entity.modified.error",
							new String[] {
									this.getRealMessageSolver().getMessage(
											"entity.Function.name", null),
									errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			FunctionVo FunctionVo = this.getDao().getById(this.getPm(),
					this.getVo().getId());

			if (FunctionVo == null) {

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.Function.validation.entityNotExists",
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
//	protected void _completeAlert(AlertVo alertVo) {
//		alertVo.setPriority(E_Priority.LOW);
//
//		alertVo.setDescription(this.getRealMessageSolver().getMessage(
//				"fx.Function.alert.description.modified",
//				new String[] { this.getVo().getDescription() }));
//	}

	/**
	 * Class VO
	 */
	@Override
	public FunctionVo getVo() {
		return (FunctionVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_FunctionDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_FunctionDao dao) {
		this.dao = dao;
	}

}
