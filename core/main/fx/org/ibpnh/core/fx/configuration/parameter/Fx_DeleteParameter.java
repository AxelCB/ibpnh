package org.ibpnh.core.fx.configuration.parameter;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_HistoricOperationType;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * FX for deleting a parameter.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_DeleteParameter extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteParameter.class);

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
		this.logger.debug("executing Fx_DeleteParameter._execute()");

		try {
			this.getEm().getTransaction().begin();

			// we persist the entity
			this.getDao().delete(this.getEm(), this.getVo());

			// we persist the history
			this.getDao().persistHistory(this.getEm(), this.getVo(),
					this.getWebContextHolder().getUsername(),
					E_HistoricOperationType.DELETION);

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					"",
					this.getRealMessageSolver()
							.getMessage(
									"default.entity.created.ok",
									new String[] { this.getRealMessageSolver()
											.getMessage(
													"entity.parameter.name",
													null) }));
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteParameter._execute()",
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
		this.logger.debug("executing Fx_DeleteParameter.validate()");

		if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver().getMessage(
					"default.entity.deleted.error",
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
										.getMessage("default.delete", null) });

				return FxValidationResponse.error(jsonResponseMessage);
			} else {
				if (parameterVo.getFixed() != null && parameterVo.getFixed()) {
					String jsonResponseMessage = this
							.getRealMessageSolver()
							.getMessage(
									"fx.parameter.validation.fixedParameterDeleting",
									new String[] { parameterVo.getName() });

					return FxValidationResponse.error(jsonResponseMessage);
				}

				return FxValidationResponse.ok();
			}
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
				"fx.parameter.alert.description.deleted",
				new String[] { this.getVo().getName() }));
	}

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