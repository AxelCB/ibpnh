package org.kairos.ibpnh.core.controller.login;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.dao.person.I_DocumentTypeDao;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.login.Fx_EnableUserByHash;
import org.kairos.ibpnh.core.fx.login.Fx_Register;
import org.kairos.ibpnh.core.fx.login.Fx_Register.RegistrationVo;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Login Controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/register", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class RegistrationCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(RegistrationCtrl.class);

	/**
	 * Gson.
	 */
	@Autowired
	private Gson gson;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/**
	 * Entity Manager Holder.
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;
	
	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_DocumentTypeDao documentTypeDao;

	/**
	 * Web context holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * Gets the document types.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/documentTypes.json")
	public String documentTypes() {
		this.logger.debug("entering RegistrationCtrl.documentTypes()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		try{
			return this.getGson()
					.toJson(JsonResponse.ok(this.getGson().toJson(documentTypeDao.listAll(em))));
		}catch(Exception e){
			this.logger.error("unexpected error", e);
			return this.getGson()
					.toJson(JsonResponse.error("Unexpected error listing document types"));
		}finally{
			this.getEntityManagerHolder().closeEntityManager(em);
		}
	}

	/**
	 * Gets the uses captcha flag.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/usesCaptcha.json")
	public String usesCaptcha() {
		this.logger.debug("entering RegistrationCtrl.usesCaptcha()");

		JsonResponse response = null;
		try {
			response = JsonResponse.ok(this.getGson().toJson(
					this.getParameterDao()
							.getByName(
									this.getEntityManagerHolder()
											.getEntityManager(),
									ParameterVo.USER_REGISTRATION_CAPTCHA)
							.getValue(Boolean.class)));
		} catch (ParseException pe) {
			this.logger.error("error parsing parameter", pe);

			response = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (NullPointerException npe) {
			this.logger.error("error parameter missing", npe);

			response = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_MISSING);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			response = this.getWebContextHolder().unexpectedErrorResponse();
		}

		return this.getGson().toJson(response);
	}

	/**
	 * Registers a user.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register.json")
	public String register(@RequestBody String data, HttpServletRequest request) {
		this.logger.debug("entering RegistrationCtrl.register()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			Fx_Register fx = this.getFxFactory()
					.getNewFxInstance(Fx_Register.class);
			fx.setVo(this.getGson().fromJson(data, RegistrationVo.class));
			fx.setEm(em);
			fx.setRemoteIp(request.getRemoteAddr());
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
	 * Enables a user.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enable.json")
	public String enable(@RequestBody String hash) {
		this.logger.debug("entering RegistrationCtrl.enable()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;
			
		try {
			Fx_EnableUserByHash fx = this.getFxFactory().getNewFxInstance(
					Fx_EnableUserByHash.class);
			fx.setEnablingHash(hash);
			fx.setEm(em);
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

}
