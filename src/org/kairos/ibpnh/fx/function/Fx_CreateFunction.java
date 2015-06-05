package org.kairos.ibpnh.fx.function;

import org.kairos.ibpnh.dao.user.I_FunctionDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.vo.user.FunctionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FX for creating a function.
 * 
 * @author acollard
 * 
 */
public class Fx_CreateFunction extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateFunction.class);

	/**
	 * Function DAO.
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
		this.logger.debug("executing Fx_CreateFunction._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			FunctionVo functionVo = this.getDao().persist(this.getPm(), this.getVo());
			this.setVo(functionVo);

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(functionVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.created.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.function.actionname", null)}));
		} catch (Exception e) {
			this.logger.error(
					"error executing Fx_CreateFunction.execute()", e);
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
		this.logger.debug("executing Fx_CreateFunction._validate()");

//		String result = this.getVo().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

		if (!this.getDao().checkActionnameUniqueness(this.getPm(),
				this.getVo().getActionName(), null)) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.function.validation.nonUniqueAcronym",
							new String[] { this.getVo().getActionName() });

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
//	protected void _completeAlert(AlertVo alertVo) {
//		alertVo.setPriority(E_Priority.HIGH);
//
//		alertVo.setDescription(this.getRealMessageSolver().getMessage(
//				"fx.function.alert.description.created",
//				new String[] { this.getVo().getDescription() }));
//	}

	/**
	 * Casted VO.
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
