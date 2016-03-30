package org.kairos.ibpnh.core.fx.configuration.mail;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.services.mail.I_MailJobScheduler;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * FX for deleting a mail.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_DeleteMail extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteMail.class);

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
		this.logger.debug("executing Fx_DeleteMail._execute()");

		try {
			this.getEm().getTransaction().begin();

			// we persist the entity
			this.getDao().delete(this.getEm(), this.getVo());

			if (this.getVo().getCron() != null && this.getVo().getCron()) {
				this.getMailJobScheduler().unscheduleMail(this.getVo());
			}

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					"",
					this.getRealMessageSolver().getMessage(
							"default.entity.deleted.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.mail.name", null) }));
		} catch (CodedException e) {
			this.logger.error("error executing Fx_CreateMail.execute()", e);

			return this.unexpectedErrorResponse(e.getErrorCode());
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteMail._execute()", e);
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
		this.logger.debug("executing Fx_DeleteMail.validate()");

		if (this.getVo().getId() == null) {
			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });

			return FxValidationResponse.error(this.getRealMessageSolver()
					.getMessage(
							"default.entity.deleted.error",
							new String[] {
									this.getRealMessageSolver().getMessage(
											"entity.mail.name", null),
									errorCodeMessage }));
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
	 * AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.HIGH);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.mail.alert.description.deleted",
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
