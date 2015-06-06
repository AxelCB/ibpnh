package org.kairos.ibpnh.fx.configuration.parameter;

import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FX for modifying a parameter.
 * 
 * @author acollard
 * 
 */
public class Fx_ModifyParameter extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyParameter.class);

	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_ParameterDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyParameter._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			ParameterVo parameterVo = this.getDao().persist(this.getPm(), this.getVo());

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(parameterVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.modified.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.parameter.name", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyParameter._execute()", e);
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
		this.logger.debug("executing Fx_ModifyParameter.validate()");

//		String result = this.getVo().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

		if (!this.getDao().checkNameUniqueness(this.getPm(),
				this.getVo().getName(), this.getVo().getId())) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.parameter.validation.nonUniqueCode",
							new String[] { this.getVo().getName() });

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
											"entity.parameter.name", null),
									errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			ParameterVo parameterVo = this.getDao().getById(this.getPm(),
					this.getVo().getId());

			if (parameterVo == null) {

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.parameter.validation.entityNotExists",
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
//				"fx.parameter.alert.description.modified",
//				new String[] { this.getVo().getDescription() }));
//	}

	/**
	 * Class VO
	 */
	@Override
	public ParameterVo getVo() {
		return (ParameterVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_ParameterDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_ParameterDao dao) {
		this.dao = dao;
	}

}
