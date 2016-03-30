package org.kairos.ibpnh.core.fx.configuration.parameter;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_HistoricOperationType;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * FX for creating a parameter.
 * 
 * @author Axel Collard Bovy
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
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_CreateParameter._execute()");

		try {
			this.getEm().getTransaction().begin();

			// we persist the entity
			this.getVo().setFixed(Boolean.FALSE); // a created parameter is a
													// non-fixed parameter
													// ipso facto
			ParameterVo parameterVo = this.getDao().persist(this.getEm(),
					this.getVo());
			this.setVo(parameterVo);

			// we persist the history
			this.getDao().persistHistory(this.getEm(), parameterVo,
					this.getWebContextHolder().getUsername(),
					E_HistoricOperationType.CREATION);

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					this.getGson().toJson(parameterVo),
					this.getRealMessageSolver()
							.getMessage(
									"default.entity.created.ok",
									new String[] { this.getRealMessageSolver()
											.getMessage(
													"entity.parameter.name",
													null) }));
		} catch (Exception e) {
			this.logger
					.error("error executing Fx_CreateParameter.execute()", e);
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
		this.logger.debug("executing Fx_CreateParameter._validate()");

		ParameterVo vo = this.getDao().getByName(this.getEm(),
				this.getVo().getName());

		if (vo != null) {

			String jsonResponseMessage = this.getRealMessageSolver().getMessage(
					"fx.parameter.validation.nonUniqueName",
					new String[] { vo.getName() });

			return FxValidationResponse.error(jsonResponseMessage);

		} else {
			return FxValidationResponse.ok();
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
				"fx.parameter.alert.description.created",
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
