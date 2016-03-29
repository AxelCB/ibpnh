package org.ibpnh.core.fx.configuration.mail;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.mail.I_MailJobScheduler;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.utils.exception.CodedException;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * FX for manually triggering the sent of a mail.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_TriggerMail extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_TriggerMail.class);

	/**
	 * Mail DAO.
	 */
	@Autowired
	private I_MailDao dao;

	/**
	 * Dynamic Mail Job Scheduler.
	 */
	@Autowired
	private I_MailJobScheduler mailJobScheduler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_TriggerMail._execute()");

		try {
			this.getMailJobScheduler().triggerMail(this.getVo());

			return JsonResponse
					.ok("",
							this.getRealMessageSolver().getMessage(
									"fx.mail.trigger.ok"));
		} catch (CodedException e) {
			this.logger.error("error executing Fx_CreateMail.execute()", e);

			return this.unexpectedErrorResponse(e.getErrorCode());
		} catch (Exception e) {
			this.logger.error("error executing Fx_TriggerMail._execute()", e);
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
		this.logger.debug("executing Fx_TriggerMail.validate()");

		if (this.getVo().getId() == null) {
			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });

			return FxValidationResponse.error(this.getRealMessageSolver()
					.getMessage("fx.mail.validation.triggering.noMailId",
							new String[] { errorCodeMessage }));
		} else {
			MailVo mailVo = this.getDao().getById(this.getEm(),
					this.getVo().getId());

			if (mailVo == null) {
				return FxValidationResponse.error(this.getRealMessageSolver()
						.getMessage(
								"fx.mail.validation.entityNotExists",
								new String[] { this.getRealMessageSolver()
										.getMessage("default.delete", null) }));
			} else {
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
				"fx.mail.alert.description.triggered",
				new String[] { this.getVo().getName() }));
	}

	/**
	 * Casted VO.
	 */
	@Override
	public MailVo getVo() {
		return (MailVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_MailDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_MailDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the mailJobScheduler
	 */
	public I_MailJobScheduler getMailJobScheduler() {
		return this.mailJobScheduler;
	}

	/**
	 * @param mailJobScheduler
	 *            the mailJobScheduler to set
	 */
	public void setMailJobScheduler(I_MailJobScheduler mailJobScheduler) {
		this.mailJobScheduler = mailJobScheduler;
	}

}
