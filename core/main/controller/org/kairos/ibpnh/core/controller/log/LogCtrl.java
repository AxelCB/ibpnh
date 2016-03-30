package org.kairos.ibpnh.core.controller.log;

import java.text.ParseException;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.dao.log.I_LogDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.vo.OrderItemVo;
import org.kairos.ibpnh.core.vo.OrderVo;
import org.kairos.ibpnh.core.vo.PaginatedListVo;
import org.kairos.ibpnh.core.vo.PaginatedRequestVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.log.LogVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Log Controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/log", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class LogCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(LogCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Log DAO.
	 */
	@Autowired
	private I_LogDao logDao;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * GSON.
	 */
	@Autowired
	private Gson gson;
	
	/**
	 * Web Context Holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * Lists existent license plates.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listLogs.json")
	public String listLogs(@RequestBody String paginationData) {
		this.logger.debug("calling LogCtrl.listLogs()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(
					paginationData, PaginatedRequestVo.class);
			PaginatedListVo<LogVo> paginatedListVo = this.getLogDao().listPage(
					em,
					paginatedRequestVo,
					this.getParameterDao()
							.getByName(em, ParameterVo.ITEMS_PER_PAGE)
							.getValue(Long.class),
					new OrderVo(new OrderItemVo("creationTimestamp", false)));

			String data = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(data);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = JsonResponse.error("", new String[] {});
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
	 * @return the logDao
	 */
	public I_LogDao getLogDao() {
		return this.logDao;
	}

	/**
	 * @param logDao
	 *            the logDao to set
	 */
	public void setLogDao(I_LogDao logDao) {
		this.logDao = logDao;
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

}
