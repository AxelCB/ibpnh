package org.kairos.ibpnh.core.controller.contact;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Controller for the about.
 * 
 * @author Axel Collard Bovy
 *
 */
@RequestMapping(value="/about", produces="text/json;charset=utf-8", method= RequestMethod.POST)
public class AboutCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(AboutCtrl.class);
	
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
	 * Lists all about.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dataAbout.json")
	public String dataAbout() {
		this.logger.debug("calling AboutCtrl.dataAbout()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();		
		JsonResponse jsonResponse = null;
		
		try {
			// 'kickoff':
			ParameterVo kickoff = this.getParameterDao().getByName(em,ParameterVo.FOOTER_KICKOFF_YEAR);
						
			// 'actual year':
			Calendar calendar = Calendar.getInstance();
			String actualYear = Integer.toString(calendar.get(Calendar.YEAR));
			
			//valueYearCopyright
			ParameterVo aboutVersion = this.getParameterDao().getByName(em, ParameterVo.FOOTER_VERSION);
			String valueYearCopyright = aboutVersion.getValue() + ' ' + kickoff.getValue();
					
					
			if(!kickoff.getValue().equals(actualYear))
			{
				valueYearCopyright = valueYearCopyright + " - " + actualYear;
			}
			ParameterVo aboutTitle = this.getParameterDao().getByName(em, ParameterVo.SYSTEM_NAME);
			ParameterVo aboutUrl = this.getParameterDao().getByName(em, ParameterVo.COMPANY_URL);
			
			List<String> dataAboutResponse = new ArrayList<String>();
			ParameterVo generalDataAbout = this.getParameterDao().getByName(em, ParameterVo.ABOUT_GENERAL);
			String[] aboutParameterNames = generalDataAbout.getValue().split(",");
			
			for (String string : aboutParameterNames) {
				ParameterVo dataAbout = this.getParameterDao().getByName(em, string);
				dataAboutResponse.add(dataAbout.getValue());
			}
			AboutVO aboutVO = new AboutVO(valueYearCopyright, aboutUrl.getValue(), aboutTitle.getValue(),dataAboutResponse);			
			jsonResponse = JsonResponse.ok(this.getGson().toJson(aboutVO));
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
		return this.parameterDao;
	}

	/**
	 * @param contacDao the contacDao to set
	 */
	public void setParameterDao(I_ParameterDao contacDao) {
		this.parameterDao = contacDao;
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
	

	public class AboutVO{
		public String version;
		public String url;
		public String title;
		public List<String> dataAboutResponse;
		public AboutVO(String version, String url, String title,
				List<String> dataAboutResponse) {
			super();
			this.version = version;
			this.url = url;
			this.title = title;
			this.dataAboutResponse = dataAboutResponse;
		}
		
		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<String> getDataAboutResponse() {
			return dataAboutResponse;
		}
		public void setDataAboutResponse(List<String> dataAboutResponse) {
			this.dataAboutResponse = dataAboutResponse;
		}
		
	}
}
