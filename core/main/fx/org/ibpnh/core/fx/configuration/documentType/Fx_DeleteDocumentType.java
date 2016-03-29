package org.ibpnh.core.fx.configuration.documentType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.person.I_DocumentTypeDao;
import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.person.DocumentTypeVo;

/**
 * FX for deleting a documentType.
 * 
 * @author acollard
 * 
 */
public class Fx_DeleteDocumentType extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteDocumentType.class);

	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_DocumentTypeDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ibpnh.core.fx.AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_DeleteDocumentType._execute()");

		try {
			this.beginTransaction();

			// we persist the entity
			this.getDao().delete(this.getEm(), this.getVo());

			this.commitTransaction();

			return JsonResponse.ok(
					"",
					this.getRealMessageSolver().getMessage(
							"default.entity.deleted.ok",
							new String[] { this.getRealMessageSolver()
									.getMessage("entity.documentType.name",
											null) }));
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteDocumentType._execute()", e);
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
	 * @see org.ibpnh.core.fx.AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_DeleteDocumentType.validate()");

		if (this.getVo().getId() == null) {

			String errorCodeMessage = this.getRealMessageSolver().getMessage("default.error.code", new Object[] { ErrorCodes.ERROR_ENTITY_ID_UNDEFINED });
			String jsonResponseMessage = this.getRealMessageSolver().getMessage("default.entity.deleted.error", new String[] { this.getRealMessageSolver().getMessage("entity.documentType.name", null), errorCodeMessage });

			return FxValidationResponse.error(jsonResponseMessage);
		} else {
			DocumentTypeVo documentTypeVo = this.getDao().getById(this.getEm(),this.getVo().getId());

			if (documentTypeVo == null) {
				String jsonResponseMessage = this.getRealMessageSolver().getMessage("fx.documentType.validation.entityNotExists", new String[] { this.getRealMessageSolver().getMessage("default.delete", null) });

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
	 * org.ibpnh.core.fx.AbstractFxImpl#_completeAlert(org.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.HIGH);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.documentType.alert.description.deleted",
				new String[] { this.getVo().getAcronym() }));
	}

	/**
	 * Casted VO.
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
