package org.kairos.ibpnh.core.fx.configuration.parameter;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.pojomatic.Pojomatic;
import org.pojomatic.diff.Difference;
import org.pojomatic.diff.Differences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_HistoricOperationType;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * FX for modifying a parameter.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_ModifyParameter extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyParameter.class);

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyParameter._execute()");

		try {
			this.getEm().getTransaction().begin();

			// we persist the entity
			ParameterVo parameterVo = this.getDao().persist(this.getEm(),
					this.getVo());

			// we persist the history
			this.getDao().persistHistory(this.getEm(), parameterVo,
					this.getWebContextHolder().getUsername(),
					E_HistoricOperationType.MODIFICATION);

			this.getEm().flush();
			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					this.getGson().toJson(parameterVo),
					this.getRealMessageSolver()
							.getMessage(
									"default.entity.modified.ok",
									new String[] { this.getRealMessageSolver()
											.getMessage(
													"entity.parameter.name",
													null) }));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyParameter._execute()",
					e);
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
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_ModifyParameter.validate()");

		if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver().getMessage(
					"default.entity.modified.error",
					new String[] {
							this.getRealMessageSolver().getMessage(
									"entity.parameter.name", null),
							errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			ParameterVo parameterVo = this.getDao().getById(this.getEm(),
					this.getVo().getId());

			if (parameterVo == null) {

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.parameter.validation.entityNotExists",
								new String[] { this.getRealMessageSolver()
										.getMessage("default.modify", null) });

				return FxValidationResponse.error(jsonResponseMessage);
			} else {
				if (parameterVo.getFixed() != null && parameterVo.getFixed()) {
					Differences differences = Pojomatic.diff(parameterVo,
							this.getVo());
					for (Difference difference : differences.differences()) {
						if (!(difference.propertyName().equals("value") || difference.propertyName().equals("tags") || difference.propertyName().equals("viewed"))) {
							String jsonResponseMessage = this
									.getRealMessageSolver()
									.getMessage(
											"fx.parameter.validation.fixedParameterModifying",
											new String[] { parameterVo
													.getName() });

							return FxValidationResponse
									.error(jsonResponseMessage);
						}
					}
				}

				return FxValidationResponse.ok();
			}
		}
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
		alertVo.setPriority(E_Priority.HIGH);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.parameter.alert.description.modified",
				new String[] { this.getVo().getName() }));
	}

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
