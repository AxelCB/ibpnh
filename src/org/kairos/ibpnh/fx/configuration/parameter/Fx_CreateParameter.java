package org.kairos.ibpnh.fx.configuration.parameter;

import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FX for creating a parameter.
 * 
 * @author acollard
 * 
 */
public class Fx_CreateParameter extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateParameter.class);

	/**
	 * Parameter DAO.
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
		this.logger.debug("executing Fx_CreateFunction._execute()");

		try {
//			this.beginTransaction();

			// we persist the entity
			ParameterVo parameter = this.getDao().persist(this.getVo());
			this.setVo(parameter);

//			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(parameter),
					this.getRealMessageSolver().getMessage(
							"default.entity.created.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.parameter.name", null) }));
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

//		String result = this.getVo().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

		if (!this.getDao().checkNameUniqueness(this.getVo().getName(), null)) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.parameter.validation.nonUniqueName",
							new String[] { this.getVo().getName() });

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
//				"fx.parameter.alert.description.created",
//				new String[] { this.getVo().getDescription() }));
//	}

	/**
	 * Casted VO.
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
