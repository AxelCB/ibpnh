package org.kairos.ibpnh.core.controller.configuration.parameter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.kairos.ibpnh.core.fx.configuration.parameter.Fx_CreateParameter;
import org.kairos.ibpnh.core.fx.configuration.parameter.Fx_DeleteParameter;
import org.kairos.ibpnh.core.fx.configuration.parameter.Fx_ModifyParameter;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.core.utils.CollectionUtils;
import org.kairos.ibpnh.core.utils.CollectionUtils.CollectionToMapConverter;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.utils.I_DateUtils;
import org.kairos.ibpnh.core.vo.PaginatedListVo;
import org.kairos.ibpnh.core.vo.PaginatedRequestVo;
import org.kairos.ibpnh.core.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Controller for the Parameter entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/parameter", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class ParameterCtrl implements I_URIValidator {

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/parameter/listTypes",
					"/parameter/get", "/parameter/refreshDateUtils" }));

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(ParameterCtrl.class);

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
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateutils;

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
	 * Lists types.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listTypes.json")
	public String listTypes() {
		this.logger.debug("calling ParameterCtrl.listTypes()");

		String data = this.getGson().toJson(E_ParameterType.values());

		JsonResponse jsonResponse = JsonResponse.ok(data);

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Lists all parameters.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public String list(@RequestBody String paginationData) {
		this.logger.debug("calling ParameterCtrl.list()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(
					paginationData, PaginatedRequestVo.class);
			PaginatedListVo<ParameterVo> paginatedListVo = this
					.getParameterDao().listPage(
							em,
							paginatedRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class));

			String data = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(data);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Searches parameters.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search.json")
	public String search(@RequestBody String data) {
		this.logger.debug("calling ParameterCtrl.search()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			Type type = new TypeToken<PaginatedSearchRequestVo<ParameterVo>>() {
			}.getType();
			PaginatedSearchRequestVo<ParameterVo> paginatedSearchRequestVo = this
					.getGson().fromJson(data, type);
			PaginatedListVo<ParameterVo> paginatedListVo = this
					.getParameterDao().searchPage(
							em,
							paginatedSearchRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class));

			String responseData = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(responseData);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Creates a new parameter
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create.json")
	public String create(@RequestBody String data) {
		this.logger.debug("calling ParameterCtrl.create()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			ParameterVo parameterVo = this.getGson().fromJson(data,
					ParameterVo.class);

			Fx_CreateParameter fx = this.getFxFactory().getNewFxInstance(
					Fx_CreateParameter.class);

			fx.setVo(parameterVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_CreateParameter");
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
	 * Deletes a parameter
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public String delete(@RequestBody String data) {
		this.logger.debug("calling ParameterCtrl.delete()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			ParameterVo parameterVo = this.getGson().fromJson(data,
					ParameterVo.class);

			Fx_DeleteParameter fx = this.getFxFactory().getNewFxInstance(
					Fx_DeleteParameter.class);

			fx.setVo(parameterVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_DeleteParameter");
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
	 * Modifies a parameter
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify.json")
	public String modifiy(@RequestBody String data) {
		this.logger.debug("calling ParameterCtrl.modifiy()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			ParameterVo parameterVo = this.getGson().fromJson(data,
					ParameterVo.class);
			Fx_ModifyParameter fx = this.getFxFactory().getNewFxInstance(
					Fx_ModifyParameter.class);

			fx.setVo(parameterVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_ModifyParameter");
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
	 * Reloads the global parameters.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reloadGlobalParameters.json")
	public String reloadGlobalParameters() {
		this.logger.debug("calling ParameterCtrl.reloadGlobalParameters()");
		JsonResponse jsonResponse = null;
		EntityManager em = this.getEntityManagerHolder().getEntityManager();

		try {
			this.getParameterDao().loadGlobalParameters(em);

			jsonResponse = JsonResponse.ok("", this.getWebContextHolder()
					.getMessage("parameter.reloadGlobalParameters.success"));
		} catch (Exception e) {
			this.logger.error("unexpected error re-loading global parameters",
					e);
			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Get parameters.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get.json")
	public String get(@RequestBody String data) {
		this.logger.debug("calling ParameterCtrl.get()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			Type type = new TypeToken<List<String>>() {
			}.getType();
			List<String> parameterNames = this.getGson().fromJson(data, type);

			List<ParameterVo> parameters = this.getParameterDao().getByName(em,
					parameterNames);

			Map<String, String> parameterMap = CollectionUtils
					.collectionToMap(
							parameters,
							new CollectionToMapConverter<String, String, ParameterVo>() {

								@Override
								public String getKey(ParameterVo parameter) {
									return parameter.getName();
								}

								@Override
								public String getValue(ParameterVo parameter) {
									return parameter.getValue();
								}

							});

			jsonResponse = JsonResponse.ok(this.getGson().toJson(parameterMap));
		} catch (Exception e) {
			this.logger.error("unexpected error re-loading global parameters",
					e);
			this.logger.debug("Error with parameter: " + data);
			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}
	
	/**
	 * Refreshes the date utils.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refreshDateUtils.json")
	public String refreshDateUtils() {
		this.logger.debug("calling ParameterCtrl.refreshDateUtils()");
		JsonResponse jsonResponse = null;
		EntityManager em = this.getEntityManagerHolder().getEntityManager();

		try {
			this.getDateutils().refreshFormats();

			jsonResponse = JsonResponse.ok("", this.getWebContextHolder()
					.getMessage("parameter.refreshDateUtils.success"));
		} catch (Exception e) {
			this.logger.error("unexpected error refreshing the date utils bean",
					e);
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
	 * @return the dateutils
	 */
	public I_DateUtils getDateutils() {
		return this.dateutils;
	}

	/**
	 * @param dateutils
	 *            the dateutils to set
	 */
	public void setDateutils(I_DateUtils dateutils) {
		this.dateutils = dateutils;
	}

}
