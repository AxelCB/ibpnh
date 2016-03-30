package org.kairos.ibpnh.core.controller.login;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.controller.I_URIValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.login.Fx_ChangePassword;
import org.kairos.ibpnh.core.fx.login.Fx_Login;
import org.kairos.ibpnh.core.fx.login.Fx_Logout;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.user.ChangePasswordVo;
import org.kairos.ibpnh.core.vo.user.UserCredentialsVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Login Controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/login", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class LoginCtrl implements I_URIValidator {

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/login/logout",
					"/login/getLoggedUser", "/login/registrationEnabled",
					"/login/getBannerText" }));

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(LoginCtrl.class);

	/**
	 * Gson Holder
	 */
	@Autowired
	private Gson gson;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_URIValidator#validate(java.lang.String)
	 */
	@Override
	public Boolean validate(String uri) {
		return !EXCLUDED_URIS.contains(uri);
	}

	/**
	 * Gets the registration enabled parameter.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/registrationEnabled.json")
	public String registrationEnabled() {
		this.logger.debug("calling LoginCtrl.registrationEnabled()");
		EntityManager em =this.getEntityManagerHolder().getEntityManager();
		JsonResponse response = null;
		try {
			ParameterVo parameterVo = this.getParameterDao().getByName(em, ParameterVo.USER_REGISTRATION);

			response = JsonResponse.ok(this.getGson().toJson(
					parameterVo.getValue(Boolean.class)));
		} catch (ParseException pe) {
			this.logger.error("error parsing parameter", pe);

			response = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			response = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(response);
	}

	/**
	 * Tries to login the user.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login.json")
	public String login(@RequestBody String data) {
		this.logger.debug("calling LoginCtrl.login()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			UserCredentialsVo userCredentialsVo = this.getGson().fromJson(data,
					UserCredentialsVo.class);

			Fx_Login fx = this.getFxFactory().getNewFxInstance(Fx_Login.class);
			fx.setVo(userCredentialsVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_Login");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Logouts the user.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout.json")
	public String login() {
		this.logger.debug("calling LoginCtrl.logout()");

		Fx_Logout fx = this.getFxFactory().getNewFxInstance(Fx_Logout.class);

		this.logger.debug("executing Fx_Logout");

		return this.getGson().toJson(fx.execute());
	}

	/**
	 * Retrieves the logged user data.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLoggedUser.json")
	public String getLoggedUser() {
		this.logger.debug("calling LoginCtrl.getLoggedUser()");

		String data = this.getGson().toJson(
				this.getWebContextHolder().getUserVo());

		JsonResponse jsonResponse = JsonResponse.ok(data);

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Changes the user's password.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changePassword.json")
	public String changePassword(@RequestBody String data) {
		this.logger.debug("calling LoginCtrl.changePassword()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			ChangePasswordVo changePasswordVo = this.getGson().fromJson(data,
					ChangePasswordVo.class);
	
			Fx_ChangePassword fx = this.getFxFactory().getNewFxInstance(
					Fx_ChangePassword.class);
			fx.setVo(changePasswordVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_ChangePassword");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		

		return this.getGson().toJson(jsonResponse);
	}
	
	/**
	 * Get the banner text.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBannerText.json")
	public String getBannerText() {
		this.logger.debug("calling LoginCtrl.getBannerText()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			// 'banner text':
			ParameterVo credits = this.getParameterDao().getByName(em,
					ParameterVo.BANNER_TEXT);
	
			String json_info = this.getGson().toJson(credits);
			jsonResponse = JsonResponse.ok(json_info);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
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
	 * @return the fxFactory
	 */
	public I_FxFactory getFxFactory() {
		return this.fxFactory;
	}

	/**
	 * @param fxFactory
	 *            the fxFactory to set
	 */
	public void setFxFactory(I_FxFactory fxFactory) {
		this.fxFactory = fxFactory;
	}

	/**
	 * @return the entityManagerHolder
	 */
	public EntityManagerHolder getEntityManagerHolder() {
		return this.entityManagerHolder;
	}

	/**
	 * @param entityManagerHolder
	 *            the entityManagerHolder to set
	 */
	public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
		this.entityManagerHolder = entityManagerHolder;
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
	 * @return the parameterDao
	 */
	public I_ParameterDao getParameterDao() {
		return this.parameterDao;
	}

	/**
	 * @param parameterDao
	 *            the parameterDao to set
	 */
	public void setParameterDao(I_ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

}
