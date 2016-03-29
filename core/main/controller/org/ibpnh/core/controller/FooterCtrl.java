package org.ibpnh.core.controller;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ibpnh.core.dao.EntityManagerHolder;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.fx.I_FxFactory;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Controller for the Parameter entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/footer", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class FooterCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(FooterCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Parameter DAO
	 */
	@Autowired
	private I_ParameterDao contacDao;

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
	 * Lists all contacs.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFooterInformation.json")
	public String getFooterInformation() {
		this.logger.debug("calling FooterCtrl.getFooterInformation()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			// 'credits':
			ParameterVo credits = this.getParameterDao().getByName(em,
					ParameterVo.FOOTER_CREDITS);
	
			// 'version':
			ParameterVo version = this.getParameterDao().getByName(em,
					ParameterVo.FOOTER_VERSION);
	
			// 'copyright':
			ParameterVo kickoff = this.getParameterDao().getByName(em,
					ParameterVo.FOOTER_KICKOFF_YEAR);
			
			// 'actual year':
			Calendar calendar = Calendar.getInstance();
			String actualYear = Integer.toString(calendar.get(Calendar.YEAR));
	
			String json_info = this.getGson().toJson(
					new FooterInfo(credits, version, kickoff.getValue(),actualYear));
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
	 * Get system name.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSystemName.json")
	public String getSystemName() {
		this.logger.debug("calling FooterCtrl.getSystemName()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse =  null;

		try {
			// 'credits':
			ParameterVo systemTitle = this.getParameterDao().getByName(em,
					ParameterVo.SYSTEM_TITLE);
	
			if (systemTitle == null) {
				jsonResponse = JsonResponse.error(null);
			} else {
				jsonResponse = JsonResponse.ok(this.getGson().toJson(systemTitle));
			}
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
	 * @return the contacDao
	 */
	public I_ParameterDao getParameterDao() {
		return this.contacDao;
	}

	/**
	 * @param contacDao
	 *            the contacDao to set
	 */
	public void setParameterDao(I_ParameterDao contacDao) {
		this.contacDao = contacDao;
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

	class FooterInfo {

		public ParameterVo credits;
		public ParameterVo version;
		public String copyright;
		public String actualYear;

		public FooterInfo(ParameterVo credits, ParameterVo version,
				String copyright, String actualYear) {
			super();
			this.credits = credits;
			this.version = version;
			this.copyright = copyright;
			this.actualYear = actualYear;
		}
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

}
