package org.kairos.ibpnh.core.fx.login;

import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.vo.user.UserVo;
import org.kairos.ibpnh.core.web.I_MessageSolver;

import com.google.gson.Gson;

/**
 * Strategy for the web.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class WebChangePasswordResponseStrategy implements
		I_ChangePasswordResponseStrategy {

	/**
	 * GSON Parser.
	 */
	private Gson gson;

	/**
	 * Message Solver.
	 */
	private I_MessageSolver messageSolver;

	/**
	 * Constructor with fields.
	 * 
	 * @param gson
	 * @param messageSolver
	 */
	public WebChangePasswordResponseStrategy(Gson gson,
			I_MessageSolver messageSolver) {
		super();
		this.gson = gson;
		this.messageSolver = messageSolver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_ChangePasswordResponseStrategy#
	 * badCurrentPassword()
	 */
	@Override
	public JsonResponse badCurrentPassword() {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage(
						"fx.changePassword.validation.currentPasswordError"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_ChangePasswordResponseStrategy#
	 * newPasswordEqualsCurrent()
	 */
	@Override
	public JsonResponse newPasswordEqualsCurrent() {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage(
						"fx.changePassword.validation.newEqualsCurrent"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_ChangePasswordResponseStrategy#
	 * newPasswordAlphanumericError(java.lang.String)
	 */
	@Override
	public JsonResponse newPasswordAlphanumericError(String errorMessage) {
		return JsonResponse.error(
				"",errorMessage
//				this.getMessageSolver().getMessage(
//						"fx.changePassword.validation.alphanumericError")
						);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_ChangePasswordResponseStrategy#
	 * newPasswordEqualsUsername()
	 */
	@Override
	public JsonResponse newPasswordEqualsUsername() {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage(
						"fx.changePassword.validation.sameAsUsername")
						);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_ChangePasswordResponseStrategy#
	 * newPasswordMinimumCharactersError(java.lang.Long)
	 */
	@Override
	public JsonResponse newPasswordMinimumCharactersError(Long minCharacters) {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage(
						"fx.changePassword.validation.minCharactersError",
						new Long[] { minCharacters }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_ChangePasswordResponseStrategy#unexpectedError
	 * (java.lang.String)
	 */
	@Override
	public JsonResponse unexpectedError(String errorCode) {
		if (errorCode != null) {
			return this.getMessageSolver().unexpectedErrorResponse(errorCode);
		} else {
			return this.getMessageSolver().unexpectedErrorResponse();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_ChangePasswordResponseStrategy#passwordChanged
	 * (UserVo)
	 */
	@Override
	public JsonResponse passwordChanged(UserVo userVo) {
		return JsonResponse.ok(this.getGson().toJson(userVo), this
				.getMessageSolver().getMessage("fx.changePassword.changed"));
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
	 * @return the messageSolver
	 */
	public I_MessageSolver getMessageSolver() {
		return this.messageSolver;
	}

	/**
	 * @param messageSolver
	 *            the messageSolver to set
	 */
	public void setMessageSolver(I_MessageSolver messageSolver) {
		this.messageSolver = messageSolver;
	}

}
