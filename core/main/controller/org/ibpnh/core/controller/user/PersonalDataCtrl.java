package org.ibpnh.core.controller.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ibpnh.core.controller.I_URIValidator;
import org.ibpnh.core.dao.EntityManagerHolder;
import org.ibpnh.core.dao.person.I_DocumentTypeDao;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.fx.I_FxFactory;
import org.ibpnh.core.fx.user.Fx_UpdatePersonalData;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.vo.person.PersonVo;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Personal Data Controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/personalData", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class PersonalDataCtrl implements I_URIValidator {

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/personalData/getPersonalData",
					"/personalData/listDocumentTypes" }));

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(PersonalDataCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;
	
	/**
	 * Document Type DAO.
	 */
	@Autowired
	private I_DocumentTypeDao documentTypeDao;

	/**
	 * Gson.
	 */
	@Autowired
	private Gson gson;

	/*
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

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
	 * Updates the personal data.
	 * 
	 * @param data
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPersonalData.json")
	public String getPersonalData() {
		this.logger.debug("calling CellphoneUserCtrl.getPersonalData()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse response = null;

		try {
			Class<? extends I_Fx> fxClass = this.getWebContextHolder().getUserVo().getRole().getRoleType()
					.getRoleTypeEnum().getGetPersonalDataFx();
			if (fxClass == null) {
				response = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_GET_PERSONAL_DATA_FX_MISSING);
			} else {
				I_Fx fx = this.getFxFactory().getNewFxInstance(fxClass);
				fx.setEm(em);
				fx.setVo(this.getWebContextHolder().getUserVo());
			
				response = fx.execute();
			}
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			response = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(response);
	}

	/**
	 * Lists existent document types.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listDocumentTypes.json")
	public String listDocumentTypes() {
		this.logger.debug("calling CellphoneUserCtrl.listDocumentTypes()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		
		JsonResponse jsonResponse;
		try {
			String data = this.getGson().toJson(documentTypeDao.listAll(em));

			jsonResponse = JsonResponse.ok(data);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);
			jsonResponse = JsonResponse.error("Unexpected error listing document types");			
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Updates the personal data.
	 * 
	 * @param data
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePersonalData.json")
	public String updatePersonalData(@RequestBody String data) {
		this.logger.debug("calling CellphoneUserCtrl.updatePersonalData()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			PersonVo personVo = this.getGson().fromJson(data, PersonVo.class);
	
			Fx_UpdatePersonalData fx = this.getFxFactory().getNewFxInstance(
					Fx_UpdatePersonalData.class);
			fx.setVo(personVo);
			fx.setEm(em);
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
}
