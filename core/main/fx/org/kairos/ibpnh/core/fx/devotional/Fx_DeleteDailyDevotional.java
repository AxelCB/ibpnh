package org.kairos.ibpnh.core.fx.devotional;

import org.kairos.ibpnh.core.dao.devotional.I_DailyDevotionalDao;
import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.utils.I_DateUtils;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.devotional.DailyDevotionalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FX for deleting a dailyDevotional.
 *
 * @author Axel Collard Bovy
 *
 * Created on 28/03/2016.
 */
public class Fx_DeleteDailyDevotional extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteDailyDevotional.class);

	/**
	 * Daily Devotional DAO.
	 */
	@Autowired
	private I_DailyDevotionalDao dao;

	/**
	 * Date utils DAO.
	 */
	@Autowired
	private I_DateUtils dateUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_DeleteFunction._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			this.getDao().delete(this.getEm(), this.getVo());

			this.commitTransaction();

			return JsonResponse.ok(
					"",
					this.getRealMessageSolver().getMessage(
							"default.entity.deleted.ok",
							new String[]{this.getRealMessageSolver()
									.getMessage("entity.dailyDevotional.date",
											null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteFunction._execute()", e);
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
		this.logger.debug("executing Fx_DeleteFunction.validate()");

		if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage("default.error.code", new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver().getMessage("default.entity.deleted.error", new String[] { this.getRealMessageSolver().getMessage("entity.dailyDevotional.name", null), errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			DailyDevotionalVo dailyDevotional = this.getDao().getById(this.getEm(),this.getVo().getId());

			if (dailyDevotional == null) {
				String jsonResponseMessage = this.getRealMessageSolver().getMessage("fx.dailyDevotional.validation.entityNotExists", new String[] { this.getRealMessageSolver().getMessage("default.delete", null) });

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
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.HIGH);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.dailyDevotional.alert.description.deleted",
				new String[] { this.getDateUtils().formateDate(this.getVo().getDate()) }));
	}

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

	public I_DateUtils getDateUtils() {
		return dateUtils;
	}

	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}
}
