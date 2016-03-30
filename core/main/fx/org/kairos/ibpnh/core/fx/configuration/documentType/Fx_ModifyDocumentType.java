package org.kairos.ibpnh.core.fx.configuration.documentType;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.person.I_DocumentTypeDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.person.DocumentTypeVo;

/**
 * FX for modifying a documentType.
 * 
 * @author acollard
 * 
 */
public class Fx_ModifyDocumentType extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyDocumentType.class);

	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_DocumentTypeDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyDocumentType._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			DocumentTypeVo documentTypeVo = this.getDao().persist(this.getEm(), this.getVo());

			this.commitTransaction();

			return JsonResponse.ok(
					this.getGson().toJson(documentTypeVo),
					this.getRealMessageSolver().getMessage(
							"default.entity.modified.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.documentType.name", null) }));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyDocumentType._execute()", e);
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
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_ModifyDocumentType.validate()");

		String result = this.getVo().validate(this.getWebContextHolder());
		if (result != null) {
			return FxValidationResponse.error(result);
		}

		if (!this.getDao().checkAcronymUniqueness(this.getEm(),
				this.getVo().getAcronym(), this.getVo().getId())) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.documentType.validation.nonUniqueCode",
							new String[] { this.getVo().getAcronym() });

			return FxValidationResponse.error(jsonResponseMessage);
		} else if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage(
					"default.error.code",
					new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage(
							"default.entity.modified.error",
							new String[] {
									this.getRealMessageSolver().getMessage(
											"entity.documentType.name", null),
									errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			DocumentTypeVo documentTypeVo = this.getDao().getById(this.getEm(),
					this.getVo().getId());

			if (documentTypeVo == null) {

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage(
								"fx.documentType.validation.entityNotExists",
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
	 * AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.LOW);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.documentType.alert.description.modified",
				new String[] { this.getVo().getDescription() }));
	}

	/**
	 * Class VO
	 */
	@Override
	public DocumentTypeVo getVo() {
		return (DocumentTypeVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_DocumentTypeDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_DocumentTypeDao dao) {
		this.dao = dao;
	}

}
