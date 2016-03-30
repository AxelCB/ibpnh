package org.kairos.ibpnh.core.fx.devotional;

import org.kairos.ibpnh.core.dao.devotional.I_DailyDevotionalDao;
import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.utils.I_DateUtils;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.devotional.DailyDevotionalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FX for creating a Daily Devotional.
 *
 * @author Axel Collard Bovy
 *
 * Created on 28/03/2016.
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

	/**
	 * Date utils.
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
		this.logger.debug("executing Fx_CreateFunction._execute()");

		try {
			this.beginTransaction();

			this.getVo().setCreator(this.getWebContextHolder().getUserVo());

			// we persist the entity
			this.getVo().setDate(this.getDateUtils().zeroHour(this.getVo().getDate()));
			DailyDevotionalVo dailyDevotional = this.getDao().persist(this.getEm(),this.getVo());
			this.setVo(dailyDevotional);

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(dailyDevotional),
					this.getRealMessageSolver().getMessage(
							"default.entity.created.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.dailyDevotional.username", null) }));
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

		if (!this.getDao().checkDateUniqueness(this.getEm(),this.getVo().getDate(), null)) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.dailyDevotional.validation.nonUniqueDate",
							new String[] { this.getVo().getDate().toString() });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			return FxValidationResponse.ok();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.kairos.ibpnh.core.fx.AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.dailyDevotional.alert.description.created",
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
