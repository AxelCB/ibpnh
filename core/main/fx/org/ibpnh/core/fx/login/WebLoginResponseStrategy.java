package org.ibpnh.core.fx.login;

import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.vo.user.UserVo;
import org.ibpnh.core.web.I_MessageSolver;

import com.google.gson.Gson;

/**
 * Login Response Strategy for web access.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class WebLoginResponseStrategy implements I_LoginResponseStrategy {

	/**
	 * The message solver.
	 */
	private I_MessageSolver messageSolver;

	/**
	 * GSON.
	 */
	private Gson gson;

	/**
	 * Constructor with fields.
	 * 
	 * @param messageSolver
	 *            the message solver
	 * @param gson
	 *            the GSON parser
	 */
	public WebLoginResponseStrategy(I_MessageSolver messageSolver, Gson gson) {
		super();
		this.messageSolver = messageSolver;
		this.gson = gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_LoginResponseStrategy#userNotFound()
	 */
	@Override
	public JsonResponse userNotFound() {
		return JsonResponse.error("",
				this.getMessageSolver().getMessage("fx.login.userNotFound"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_LoginResponseStrategy#disabledUser(java.
	 * lang.String)
	 */
	@Override
	public JsonResponse disabledUser(String disabledCause) {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage("fx.login.userDisabled",
						new String[] { disabledCause }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_LoginResponseStrategy#maxAttempts()
	 */
	@Override
	public JsonResponse maxAttempts() {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage(
						"fx.login.disabledCause.maxLoginAttemps", null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_LoginResponseStrategy#badPassword(java.lang
	 * .Long)
	 */
	@Override
	public JsonResponse badPassword(Long totalAttempts) {
		return JsonResponse.error(
				"",
				this.getMessageSolver().getMessage("fx.login.attemptFailed",
						new Object[] { totalAttempts }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_LoginResponseStrategy#userLogged(org.ibpnh
	 * .core.vo.user.UserVo)
	 */
	@Override
	public JsonResponse userLogged(UserVo userVo) {
		return JsonResponse.ok(this.getGson().toJson(userVo));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_LoginResponseStrategy#unexpectedError(java
	 * .lang.String)
	 */
	@Override
	public JsonResponse unexpectedError(String errorCode) {
		if (errorCode != null) {
			return this.getMessageSolver().unexpectedErrorResponse(errorCode);
		} else {
			return this.getMessageSolver().unexpectedErrorResponse();
		}
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
