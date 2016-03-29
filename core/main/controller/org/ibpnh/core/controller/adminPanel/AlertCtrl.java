package org.ibpnh.core.controller.adminPanel;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import org.ibpnh.core.dao.I_Dao;
import org.ibpnh.core.dao.alert.I_AlertDao;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.vo.AbstractVo;
import org.ibpnh.core.vo.OrderItemVo;
import org.ibpnh.core.vo.OrderVo;
import org.ibpnh.core.vo.PaginatedListVo;
import org.ibpnh.core.vo.PaginatedRequestVo;
import org.ibpnh.core.vo.PaginatedSearchRequestVo;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Alert Controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/alert", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class AlertCtrl implements I_URIValidator {

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/alert/listUsers",
					"/alert/listPriorities" }));

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(AlertCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Alert DAO.
	 */
	@Autowired
	private I_AlertDao alertDao;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * user DAO.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * GSON.
	 */
	@Autowired
	private Gson gson;

	/**
	 * All DAO's.
	 */
	@Autowired
	private List<I_Dao<? extends AbstractVo>> daos;

	/**
	 * Web context holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

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
	 * Lists users.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listUsers.json")
	public String listUsers() {
		this.logger.debug("calling AlertCtrl.listUsers()");

		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		try {
			this.getUserDao().setDeepMapping(Boolean.FALSE);
			String data = this
					.getGson()
					.toJson(this.getUserDao().listAll(em, new OrderVo(new OrderItemVo("username", Boolean.TRUE))));
		
			return this.getGson().toJson(JsonResponse.ok(data));
		} catch(Exception e) {
			this.logger.debug("unexpected error", e);

			return this.getGson().toJson(this.getWebContextHolder().unexpectedErrorResponse());
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
	}

	/**
	 * Lists priorities.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listPriorities.json")
	public String listPriorities() {
		this.logger.debug("calling AlertCtrl.listPriorities()");

		String data = this.getGson().toJson(E_Priority.values());

		return this.getGson().toJson(JsonResponse.ok(data));
	}

	/**
	 * Lists alerts.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public String list(@RequestBody String paginationData) {
		this.logger.debug("calling AlertCtrl.list()");

		JsonResponse jsonResponse;

		PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(
				paginationData, PaginatedRequestVo.class);

		EntityManager em = this.getEntityManagerHolder().getEntityManager();

		try {
			this.getAlertDao().setDeepMapping(Boolean.FALSE);
			PaginatedListVo<AlertVo> paginatedListVo = this.getAlertDao()
					.listPage(
							em,
							paginatedRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class),
							new OrderVo(new OrderItemVo("timestamp", false)));

			String data = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(data);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = JsonResponse.error("", new String[] {});
		} catch(Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * 
	 * @param paginationData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search.json")
	public String search(@RequestBody String paginationData) {
		this.logger.debug("calling AlertCtrl.search()");

		JsonResponse jsonResponse;

		PaginatedSearchRequestVo<AlertVo> paginatedSearchRequestVo = this
				.getGson().fromJson(paginationData,
						new TypeToken<PaginatedSearchRequestVo<AlertVo>>() {
						}.getType());

		EntityManager em = this.getEntityManagerHolder().getEntityManager();

		try {
			this.getAlertDao().setDeepMapping(Boolean.FALSE);
			PaginatedListVo<AlertVo> paginatedListVo = this.getAlertDao()
					.searchPage(
							em,
							paginatedSearchRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class),
							new OrderVo(new OrderItemVo("timestamp", false)));

			String data = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(data);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = JsonResponse.error("", new String[] {});
		} catch(Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Gets the object referenced by the alert.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getObject.json")
	public String getObject(@RequestBody String alert) {
		JsonResponse jsonResponse = null;

		try {
			// deserializes the alert
			AlertVo alertVo = this.getGson().fromJson(alert, AlertVo.class);

			// finds the proper DAO
			I_Dao<? extends AbstractVo> dao = null;
			for (I_Dao<? extends AbstractVo> daoAux : this.getDaos()) {
				Class<? extends AbstractVo> daoVoClazz = daoAux.getVoClazz();
				Class<?> alertVoClazz = Class.forName(alertVo
						.getObjectClassName());

				if (alertVoClazz.equals(daoVoClazz)) {
					dao = daoAux;
					break;
				}
			}

			if (dao == null) {
				this.logger.error("DAO not found for VO Class {}",
						alertVo.getObjectClassName());

				jsonResponse = this.getWebContextHolder()
						.unexpectedErrorResponse(
								ErrorCodes.ERROR_DAO_FOR_VO_CLAZZ_NOT_FOUND);
			} else {
				AbstractVo vo = dao.getById(this.getEntityManagerHolder()
						.getEntityManager(), alertVo.getObjectId());

				jsonResponse = JsonResponse.ok(this.getGson().toJson(vo));
			}
		} catch (ClassNotFoundException cnfe) {
			this.logger.error("class not found", cnfe);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_VO_CLAZZ_NOT_FOUND);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
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
	 * @return the alertDao
	 */
	public I_AlertDao getAlertDao() {
		return this.alertDao;
	}

	/**
	 * @param alertDao
	 *            the alertDao to set
	 */
	public void setAlertDao(I_AlertDao alertDao) {
		this.alertDao = alertDao;
	}

	/**
	 * @return the daos
	 */
	public List<I_Dao<? extends AbstractVo>> getDaos() {
		return this.daos;
	}

	/**
	 * @param daos
	 *            the daos to set
	 */
	public void setDaos(List<I_Dao<? extends AbstractVo>> daos) {
		this.daos = daos;
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

}
