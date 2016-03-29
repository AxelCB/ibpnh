package org.ibpnh.core.fx.configuration.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.mail.I_MailJobScheduler;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.utils.exception.CodedException;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * FX for modifying a mail.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_ModifyMail extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyMail.class);

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
		this.logger.debug("executing Fx_ModifyMail._execute()");

		try {
			this.getEm().getTransaction().begin();
			
			//we get the previous name, for unscheduling the previous task
			String oldName = this.getDao().getById(this.getEm(), this.getVo().getId()).getName();
			
			if (!oldName.equals(this.getVo().getName())) {
				this.getMailJobScheduler().unscheduleMail(oldName);
			}
			
			// we persist the entity
			MailVo mailVo = this.getDao().persist(this.getEm(), this.getVo());

			this.getEm().flush();

			if (mailVo.getCron() != null && mailVo.getCron()) {
				if (mailVo.getEnabled() != null && mailVo.getEnabled()) {
					this.getMailJobScheduler().scheduleMail(mailVo);
				} else {
					if (oldName.equals(this.getVo().getName())) {
						this.getMailJobScheduler().unscheduleMail(mailVo);
					}
				}
			}

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					this.getGson().toJson(mailVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.modified.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.mail.name", null) }));
		} catch (CodedException e) {
			this.logger.error("error executing Fx_CreateMail.execute()", e);

			return this.unexpectedErrorResponse(e.getErrorCode());
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyMail._execute()", e);
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
		this.logger.debug("executing Fx_ModifyMail.validate()");

		if (this.getVo().getId() == null) {
			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });

			return FxValidationResponse.error(this.getRealMessageSolver()
					.getMessage(
							"default.entity.modified.error",
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
										.getMessage("default.modify", null) }));
			} else {
				String jsonResponseMessage = this.getVo().validate(
						this.getRealMessageSolver());

				if (jsonResponseMessage == null) {

					if (!this.getDao().checkNameUniqueness(this.getEm(),
							this.getVo().getName(), this.getVo().getId())) {
						jsonResponseMessage = this
								.getRealMessageSolver()
								.getMessage("fx.mail.validation.nonUniqueName",
										new String[] { this.getVo().getName() });

						return FxValidationResponse.error(jsonResponseMessage);
					} else {
						return FxValidationResponse.ok();
					}
				} else {
					return FxValidationResponse.error(jsonResponseMessage);
				}
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
		alertVo.setPriority(E_Priority.LOW);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.mail.alert.description.modified",
				new String[] { this.getVo().getName() }));
	}

	/**
	 * Class VO
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
