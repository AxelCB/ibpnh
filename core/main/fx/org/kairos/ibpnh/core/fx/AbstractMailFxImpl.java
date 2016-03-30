package org.kairos.ibpnh.core.fx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.vo.alert.AlertVo;

/**
 * Abstract FX implementation of the I_Fx interface
 * 
 * @author Axel Collard Bovy
 * 
 */
public abstract class AbstractMailFxImpl extends AbstractFxImpl implements
		I_MailFx {

	/**
	 * Logger.
	 */
	public Logger logger = LoggerFactory.getLogger(AbstractMailFxImpl.class);
	
	/**
	 * Fire Time of the Job.
	 */
	private Date fireTime;

	/**
	 * Checks if the function can be executed.
	 * 
	 * Always returns TRUE for MailFXs.
	 * 
	 * @return an FxValidationResponse
	 */
	@Override
	protected final FxValidationResponse validate() {
		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected final JsonResponse _execute() {
		// will never be called
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected final void _completeAlert(AlertVo alertVo) {
		// will never be called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Fx#execute()
	 */
	@Override
	public final JsonResponse execute() {
		this.logger.error("FATAL: execute called in a MailFx");
		throw new RuntimeException("FATAL: execute called in a MailFx");
	}
	
	/*
	 * (non-Javadoc)
	 * @see I_MailFx#getAttachments()
	 */
	@Override
	public List<AttachmentVo> getAttachments() {
		// by default, send an empty array 
		return new ArrayList<>();
	}

	/**
	 * Internal execution method.
	 * 
	 * @return
	 */
	protected abstract Map<String, String> _executeAndReturnParams()
			throws CodedException;

	/*
	 * (non-Javadoc)
	 * @see I_MailFx#executeAndReturnParameters()
	 */
	@Override
	public Map<String, String> executeAndReturnParameters() throws CodedException {
		this.logger.debug("executing mail fx");

		if (this.exposedParameters() == null) {
			this.logger.error("exposed parameters were null");
			throw new CodedException("exposed parameters were null",
					ErrorCodes.ERROR_MAIL_FX_EXPOSED_PARAMETERS_NULL, null);
		}

		this.logger.debug("executing mail fx - execution phase");
		Map<String, String> parameters = this._executeAndReturnParams();

		if (parameters == null) {
			this.logger.error("returned parameters were null");
			throw new CodedException("returned parameters were null",
					ErrorCodes.ERROR_MAIL_FX_RETURNED_PARAMETERS_NULL, null);
		}
		if (!this.validateParameters(parameters)) {
			this.logger.error("incomplete map of returned parameters");
			throw new CodedException("incomplete map of returned parameters",
					ErrorCodes.ERROR_MAIL_FX_RETURNED_PARAMETERS_INCOMPLETE,
					null);
		} else {
			return parameters;
		}
	}

	/**
	 * Validates the returned parameters of the FX.
	 * 
	 * @param parameters
	 *            returned parameters
	 * @return true iif all exposed parameters are present
	 */
	private Boolean validateParameters(Map<String, String> parameters) {
		// gets the set of keys returned by the mail FX
		Set<String> keySet = parameters.keySet();

		// key set is smaller than expected parameters, we can already return
		// false
		if (keySet.size() < this.exposedParameters().size()) {
			return Boolean.FALSE;
		}

		for (String parameter : this.exposedParameters()) {
			// one of the exposed parameters is missing, we return false
			if (!keySet.contains(parameter)) {
				return Boolean.FALSE;
			}
		}

		// everything works nice and smoooth
		return Boolean.TRUE;
	}

	/**
	 * @return the fireTime
	 */
	protected Date getFireTime() {
		return this.fireTime;
	}

	/*
	 * (non-Javadoc)
	 * @see I_MailFx#setFireTime(java.util.Date)
	 */
	@Override
	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}
}
