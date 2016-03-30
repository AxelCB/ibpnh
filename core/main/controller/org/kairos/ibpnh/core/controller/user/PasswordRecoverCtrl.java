package org.kairos.ibpnh.core.controller.user;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.user.I_RegisteredUserDao;
import org.kairos.ibpnh.core.vo.user.RegisteredUserVo;
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
import org.kairos.ibpnh.core.dao.person.I_PersonDao;
import org.kairos.ibpnh.core.dao.user.I_UserDao;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.configuration.mail.parameters.Fx_Mail_Activation;
import org.kairos.ibpnh.core.fx.user.Fx_PasswordRecover;
import org.kairos.ibpnh.core.fx.user.Fx_SendRecoverMail;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.services.mail.I_MailJobScheduler;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.user.UserVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Password Recover controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/passwordRecover", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class PasswordRecoverCtrl implements I_URIValidator {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(PasswordRecoverCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Parameter DAO
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * User DAO
	 */
	@Autowired
	private I_UserDao userDao;
	
	/**
	 * Person DAO
	 */
	@Autowired
	private I_PersonDao personDao;

	/**
	 * UserDetail DAO
	 */
	@Autowired
	private I_RegisteredUserDao registeredUserDao;

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
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;
	
	/**
	 * Mail Job Scheduler.
	 */
	@Autowired
	private I_MailJobScheduler mailJobScheduler;
	
	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/passwordRecover/forwardActivationCode" }));
	
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
	 * Expiration time in minutes.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/expirationTime.json")
	public String expirationTime() {
		this.logger.debug("entering PasswordRecoverCtrl.expirationTime()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse response = null;
		
		try {
			Long expiration = this.getParameterDao().getByName(em, ParameterVo.USER_REGISTRATION_COOKIE_EXPIRATION).getValue(Long.class);
			
			response = JsonResponse.ok(this.getGson().toJson(expiration));
		} catch (ParseException pe) {
			this.logger.error("parameter parsing error", pe);
			
			response = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.error("parameter missing error", e);
			
			response = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_PARAMETER_MISSING);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(response);
	}
	
	/**
	 * Sends the recover password mail.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMail.json")
	public String sendMail(@RequestBody String email) {
		this.logger.debug("entering PasswordRecoverCtrl.sendMail()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;		
		
		try {
			Fx_SendRecoverMail fx = this.getFxFactory().getNewFxInstance(Fx_SendRecoverMail.class);
			fx.setEm(em);
			fx.setEmail(email);
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(jsonResponse);
	}
	
	/**
	 * Sends the recover password mail.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forwardActivationCode.json")
	public String forwardActivationCode(@RequestBody String email) {
		this.logger.debug("entering PasswordRecoverCtrl.forwardActivationCode()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;		
		
		try {
			RegisteredUserVo registeredUserVo = registeredUserDao.findByEmail(em, email);
			
			if (registeredUserVo != null) {
				UserVo userVo = userDao.getById(em, registeredUserVo.getUserId());
				
				Fx_Mail_Activation fx = this.getFxFactory().getNewFxInstance(
						Fx_Mail_Activation.class);
				fx.setActivationHash(userVo.getEnablingHash());
				fx.setUsername(userVo.getUsername());
				fx.setName(registeredUserVo.getPerson().getName());
				fx.setSurname(registeredUserVo.getPerson().getSurname());
				fx.setEmail(registeredUserVo.getPerson().getEmail());
				this.getMailJobScheduler().triggerMail(
						MailVo.ACTIVATION_EMAIL_NAME, fx);
			}
			
			jsonResponse = JsonResponse.ok("", this.getWebContextHolder()
					.getMessage("ctrl.password.recover.forward.activation.code"));
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(jsonResponse);
	}
	
	/**
	 * Recovers the password for a user.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recoverPassword.json")
	public String recoverPassword(@RequestBody String data) {
		this.logger.debug("entering PasswordRecoverCtrl.recoverPassword()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;
		
		try {
			JsonObject jsonData = this.getGson().fromJson(data, JsonObject.class);
			
			Fx_PasswordRecover fx = this.getFxFactory().getNewFxInstance(Fx_PasswordRecover.class);
			fx.setEm(em);
			fx.setNewPassword(jsonData.get("newPassword").getAsString());
			fx.setPrivateHash(jsonData.get("privateHash").getAsString());
			fx.setPublicHash(jsonData.get("publicHash").getAsString());
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(jsonResponse);
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
	 * @return the userDao
	 */
	public I_UserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(I_UserDao userDao) {
		this.userDao = userDao;
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
	 * @param webContextHolder the webContextHolder to set
	 */
	public void setWebContextHolder(WebContextHolder webContextHolder) {
		this.webContextHolder = webContextHolder;
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
