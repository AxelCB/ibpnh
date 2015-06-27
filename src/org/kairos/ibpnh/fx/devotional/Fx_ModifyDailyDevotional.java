package org.kairos.ibpnh.fx.devotional;

import org.kairos.ibpnh.dao.devotional.I_DailyDevotionalDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FX for modifying a user.
 * 
 * @author acollard
 * 
 */
public class Fx_ModifyDailyDevotional extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyDailyDevotional.class);

	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_DailyDevotionalDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyDailyDevotional._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
//			this.getDao().setMapId("non-blob-"+this.getDao().getVoClazz());
			DailyDevotionalVo dailyDevotionalVo = this.getDao().persist(this.getPm(), this.getVo());

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(dailyDevotionalVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.modified.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.dailyDevotional.date", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyDailyDevotional._execute()", e);
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
		this.logger.debug("executing Fx_ModifyDailyDevotional.validate()");

//		String result = this.getVo().validate(this.getWebContextHolder());
//		if (result != null) {
//			return FxValidationResponse.error(result);
//		}

//		if (!this.getDao().checkDateUniqueness(this.getPm(),
//				this.getVo().getDate(), this.getVo().getId())) {
//			String jsonResponseMessage = this.getRealMessageSolver()
//					.getMessage("fx.dailyDevotional.validation.nonUniqueDate",
//							new String[] { this.getVo().getDate().toString() });
//
//			return FxValidationResponse.error(jsonResponseMessage);
//		} else
		if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage(
							"default.entity.modified.error",
							new String[] {
									this.getRealMessageSolver().getMessage(
											"entity.dailyDevotional.title", null),
									errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			DailyDevotionalVo dailyDevotionalVo = this.getDao().getById(this.getPm(),
					this.getVo().getId());

			if (dailyDevotionalVo == null) {

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.dailyDevotional.validation.entityNotExists",
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
//				"fx.user.alert.description.modified",
//				new String[] { this.getVo().getDescription() }));
//	}

	/**
	 * Class VO
	 */
	@Override
	public DailyDevotionalVo getVo() {
		return (DailyDevotionalVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_DailyDevotionalDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_DailyDevotionalDao dao) {
		this.dao = dao;
	}

}
