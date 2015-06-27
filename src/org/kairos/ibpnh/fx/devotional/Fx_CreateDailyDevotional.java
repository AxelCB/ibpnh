package org.kairos.ibpnh.fx.devotional;

import org.kairos.ibpnh.dao.devotional.I_DailyDevotionalDao;
import org.kairos.ibpnh.fx.AbstractFxImpl;
import org.kairos.ibpnh.fx.FxValidationResponse;
import org.kairos.ibpnh.fx.I_Fx;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FX for creating a user.
 *
 * @author acollard
 *
 */
public class Fx_CreateDailyDevotional extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateDailyDevotional.class);

	/**
	 * DailyDevotional DAO.
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
		this.logger.debug("executing Fx_CreateFunction._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			DailyDevotionalVo dailyDevotionalVo = this.getDao().persist(this.getPm(), this.getVo());
			this.setVo(dailyDevotionalVo);

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(dailyDevotionalVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.created.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.user.username", null) }));
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

//		if (!this.getDao().checkDateUniqueness(this.getPm(),
//				this.getVo().getDate(), null)) {
//			String jsonResponseMessage = this.getRealMessageSolver()
//					.getMessage("fx.user.validation.nonUniqueDate",
//							new String[] { this.getVo().getDate().toString() });
//
//			return FxValidationResponse.error(jsonResponseMessage);
//		} else {
			return FxValidationResponse.ok();
//		}
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
//				"fx.user.alert.description.created",
//				new String[] { this.getVo().getDescription() }));
//	}

	/**
	 * Casted VO.
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
