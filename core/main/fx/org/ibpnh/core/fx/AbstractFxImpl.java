package org.ibpnh.core.fx;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.services.alert.I_AlertSrv;
import org.ibpnh.core.vo.AbstractVo;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.web.I_MessageSolver;
import org.ibpnh.core.web.MessageSolver;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Abstract FX implementation of the I_Fx interface
 * 
 * @author Axel Collard Bovy
 * 
 */
public abstract class AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	public Logger logger = LoggerFactory.getLogger(AbstractFxImpl.class);

	/**
	 * Entity manager.
	 */
	private EntityManager em;

	/**
	 * Gson formatter
	 */
	@Autowired
	private Gson gson;

	/**
	 * Generic VO.
	 */
	private AbstractVo vo;

	/**
	 * Alert Service.
	 */
	@Autowired
	private I_AlertSrv alertSrv;

	/**
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * Message solver (to use if the web context holder will not be available).
	 */
	@Autowired
	private MessageSolver messageSolver;

	/**
	 * Fire alert flag.
	 */
	private Boolean fireAlert = Boolean.TRUE;

	/**
	 * Active entity transaction flag.
	 */
	private Boolean wasActive = Boolean.FALSE;

	/**
	 * Checks if the function can be executed.
	 * 
	 * @return an FxValidationResponse
	 */
	protected abstract FxValidationResponse validate();

	/**
	 * Completes the alert VO.
	 * 
	 * @param alertVo
	 *            alert VO
	 */
	protected abstract void _completeAlert(AlertVo alertVo);

	/**
	 * Generates an alert to sent to the Alert Server.
	 * 
	 * @return alertVo
	 */
	protected AlertVo generateAlert() {
		this.logger.debug("generating alert for the FX execution");

		AlertVo alertVo = new AlertVo();
		Calendar calendar = Calendar.getInstance();

		alertVo.setObjectId(this.getVo() == null ? null : this.getVo().getId());
		alertVo.setObjectClassName(this.getVo() == null ? null : this.getVo()
				.getClass().getCanonicalName());
		alertVo.setTimestamp(calendar.getTime());

		try {
			alertVo.setUser(this.getWebContextHolder().getUserIdReferenceVo());
		} catch (BeanCreationException e) {
			if (e.getBeanName().equals(
					WebContextHolder.WEB_CONTEXT_HOLDER_PROXY_BEAN_NAME)) {
				// it is just a non-request thread
				this.logger.debug("non request thread, cannot get a user");
			} else {
				// we don't know what has happened, throw this away
				throw e;
			}
		}

		this._completeAlert(alertVo);

		return alertVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Fx#disableAlert()
	 */
	@Override
	public void disableAlert() {
		this.setFireAlert(Boolean.FALSE);
	}

	/**
	 * Internal execution method.
	 * 
	 * @return
	 */
	protected abstract JsonResponse _execute();

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Fx#execute()
	 */
	@Override
	public JsonResponse execute() {
		this.logger.debug("executing fx");

		this.logger.debug("executing fx - validation phase");
		// template method
		FxValidationResponse fxValidationResponse = this.validate();

		if (fxValidationResponse.getOk()) {
			this.logger.debug("executing fx - execution phase");
			JsonResponse response = this._execute();

			// assumes O(1) order
			if (response.getOk()) {
				this.logger.debug("executing fx - alert generation phase");

				if (this.getFireAlert()) {
					AlertVo alertVo = this.generateAlert();

					if (alertVo.getSendAlert()) {
						this.getAlertSrv().saveAlert(alertVo);
					}
				} else {
					this.logger.debug("executing fx - alert disabled");
				}
			}

			return response;
		} else {
			this.logger.debug("executing fx - validation failed");
			return JsonResponse.error(fxValidationResponse.getData(),
					fxValidationResponse.getMessages());
		}
	}

	/**
	 * Starts transaction if it was not active.
	 */
	public void beginTransaction() {
		this.setWasActive(this.getEm().getTransaction().isActive());
		if (!this.getWasActive()) {
			this.getEm().getTransaction().begin();
		}
	}

	/**
	 * Commits transaction if it was not active when fx was called.
	 */
	public void commitTransaction() {
		if (!this.getWasActive()) {
			this.getEm().getTransaction().commit();
		}
	}

	/**
	 * Rollback transaction if it was active.
	 */
	public void rollbackTransaction() {
		if (this.getEm().getTransaction().isActive()) {
			try {
				this.getEm().getTransaction().rollback();
			} catch (Exception e) {
				this.logger.error("error rollbacking transaction", e);
			}
		}
	}

	/**
	 * Gets a new error message with the specified code.
	 * 
	 * @param errorCode
	 *            the error code
	 * 
	 * @return string
	 */
	protected String errorMessage(String errorCode) {
		return this.getWebContextHolder().errorMessage(errorCode);
	}

	/**
	 * Generates a standard error response with a generic message.
	 * 
	 * @return jsonResponse
	 */
	protected JsonResponse unexpectedErrorResponse() {
		return this.getRealMessageSolver().unexpectedErrorResponse();
	}

	/**
	 * 
	 * Generates a standard error response with a generic message.
	 * 
	 * @param errorCode
	 *            the error code to set
	 * 
	 * @return jsonResponse
	 */
	protected JsonResponse unexpectedErrorResponse(String errorCode) {
		return this.getRealMessageSolver().unexpectedErrorResponse(errorCode);
	}

	/**
	 * Gets the real message solver.
	 * 
	 * @return message solver
	 */
	protected I_MessageSolver getRealMessageSolver() {
		try {
			// we try to get something from the context holder (to use the
			// proxy)
			this.getWebContextHolder().getLocale();

			// we got up to here, we can use this as the message solver
			return this.getWebContextHolder();
		} catch (BeanCreationException e) {
			if (e.getBeanName().equals(
					WebContextHolder.WEB_CONTEXT_HOLDER_PROXY_BEAN_NAME)) {
				// the exception was thrown, we use the non threat-bound message
				// solver
				return this.getMessageSolver();
			} else {
				// this is another kind of error, move along!
				throw e;
			}
		}
	}

	/**
	 * @return the em
	 */
	@Override
	public EntityManager getEm() {
		return this.em;
	}

	/**
	 * @param em
	 *            the em to set
	 */
	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * @return the gson
	 */
	public Gson getGson() {
		return this.gson;
	}

	/**
	 * @param gson
	 *            the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * @return the alertSrv
	 */
	public I_AlertSrv getAlertSrv() {
		return this.alertSrv;
	}

	/**
	 * @param alertSrv
	 *            the alertSrv to set
	 */
	public void setAlertSrv(I_AlertSrv alertSrv) {
		this.alertSrv = alertSrv;
	}

	/**
	 * @return the vo
	 */
	public AbstractVo getVo() {
		return this.vo;
	}

	/**
	 * @param vo
	 *            the vo to set
	 */
	@Override
	public void setVo(AbstractVo vo) {
		this.vo = vo;
	}

	/**
	 * @return the webContextHolder
	 */
	public WebContextHolder getWebContextHolder() {
		return this.webContextHolder;
	}

	/**
	 * @param webContextHolder
	 *            the webContextHolder to set
	 */
	public void setWebContextHolder(WebContextHolder webContextHolder) {
		this.webContextHolder = webContextHolder;
	}

	/**
	 * @return the fireAlert
	 */
	protected Boolean getFireAlert() {
		return this.fireAlert;
	}

	/**
	 * @param fireAlert
	 *            the fireAlert to set
	 */
	protected void setFireAlert(Boolean fireAlert) {
		this.fireAlert = fireAlert;
	}

	/**
	 * @return the messageSolver
	 */
	public MessageSolver getMessageSolver() {
		return this.messageSolver;
	}

	/**
	 * @param messageSolver
	 *            the messageSolver to set
	 */
	public void setMessageSolver(MessageSolver messageSolver) {
		this.messageSolver = messageSolver;
	}

	/**
	 * @return the wasActive
	 */
	public Boolean getWasActive() {
		return this.wasActive;
	}

	/**
	 * @param wasActive the wasActive to set
	 */
	public void setWasActive(Boolean wasActive) {
		this.wasActive = wasActive;
	}

}
