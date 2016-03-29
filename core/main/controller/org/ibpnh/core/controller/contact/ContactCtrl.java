package org.ibpnh.core.controller.contact;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ibpnh.core.dao.EntityManagerHolder;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Controller for the Parameter entities.
 * 
 * @author Axel Collard Bovy
 *
 */
@RequestMapping(value="/contact", produces="text/json;charset=utf-8", method= RequestMethod.POST)
public class ContactCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(ContactCtrl.class);
	
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
	@RequestMapping(value="/seeEmergencyContact.json")
	public String seeEmergencyContact() {
		this.logger.debug("calling ContactCtrl.seeEmergencyContact()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();		
		JsonResponse jsonResponse = null;
		
		try {
			//'systemProblemTelephone': 
			List<ParameterVo> systemProblemTelephones = this.getParameterDao().getsByName(em, "contact.systemProblemTelephone");
			
			//'emergencyTelephone': 
			List<ParameterVo> emergencyTelephones = this.getParameterDao().getsByName(em, "contact.emergencyTelephone");
			
			String json_info = this.getGson().toJson(new ContactInfo(emergencyTelephones, systemProblemTelephones));
			
			jsonResponse = JsonResponse.ok(json_info);
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(jsonResponse);
	}
	
	/**
	 * Lists all printer links.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/seePrinterLinks.json")
	public String seePrinterLinks() {
		this.logger.debug("calling ContactCtrl.seePrinterLinks()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();		
		JsonResponse jsonResponse = null;
		
		try {
			//'Drivers Printer Links': 
			List<ParameterVo> driverPrinterLinks = this.getParameterDao().getsByName(em, "contact.driverPrinterLink");
			
			//'Install Instructions Printer Links': 
			List<ParameterVo> printerInstalationInstructions = this.getParameterDao().getsByName(em, "contact.printerInstalationInstruction");
			
			String json_info = this.getGson().toJson(new PrinterLinks(driverPrinterLinks, printerInstalationInstructions));
			
			jsonResponse = JsonResponse.ok(json_info);
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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
	 * @param entityManagerHolder the entityManagerHolder to set
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
	 * @param contacDao the contacDao to set
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
	 * @param gson the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * Helper VO Class.
	 * 
	 * @author Axel Collard Bovy
	 *
	 */
	public class ContactInfo {
		public List<ParameterVo> emergencyTelephones;
		public List<ParameterVo> systemProblemTelephones;
		
		public ContactInfo(List<ParameterVo> emergencyTelephone,
				List<ParameterVo> systemProblemTelephone) {
			super();
			this.emergencyTelephones = emergencyTelephone;
			this.systemProblemTelephones = systemProblemTelephone;
		}
	}	
	
	/**
	 * Helper VO Class.
	 * 
	 * @author Axel Collard Bovy
	 *
	 */
	public class PrinterLinks {
		
		public List<ParameterVo> driverPrinterLinks;
		public List<ParameterVo> printerInstalationInstructions;
		
		public PrinterLinks(List<ParameterVo> driverPrinterLinks,
				List<ParameterVo> printerInstalationInstructions) {
			super();
			this.driverPrinterLinks = driverPrinterLinks;
			this.printerInstalationInstructions = printerInstalationInstructions;
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
