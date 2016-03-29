package org.ibpnh.core.controller.user.roleType;

import java.text.ParseException;
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
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.dao.function.I_FunctionDao;
import org.ibpnh.core.dao.user.roleType.I_RoleTypeDao;
import org.ibpnh.core.fx.I_FxFactory;
import org.ibpnh.core.fx.user.roleType.Fx_CreateRoleType;
import org.ibpnh.core.fx.user.roleType.Fx_DeleteRoleType;
import org.ibpnh.core.fx.user.roleType.Fx_ModifyRoleType;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.user.E_RoleType;
import org.ibpnh.core.vo.PaginatedListVo;
import org.ibpnh.core.vo.PaginatedRequestVo;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.vo.user.RoleTypeVo;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;

/**
 * Controller for the Role Type entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/roleType", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class RoleTypeCtrl implements I_URIValidator {

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/roleType/listFunctions",
					"/roleType/listRoleTypes", "/roleType/listRoleTypeEnums" }));

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(RoleTypeCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/*
	 * Function DAO
	 */
	@Autowired
	private I_FunctionDao functionDao;

	/**
	 * RoleType DAO
	 */
	@Autowired
	private I_RoleTypeDao roleTypeDao;

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
	 * Lists role type enums.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listRoleTypeEnums.json")
	public String listTypes() {
		this.logger.debug("calling ParameterCtrl.listTypes()");

		String data = this.getGson().toJson(E_RoleType.values());

		JsonResponse jsonResponse = JsonResponse.ok(data);

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Lists functions.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listFunctions.json")
	public String listFunctions() {
		this.logger.debug("calling RoleTypeCtrl.listFunctions()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			String data = this.getGson().toJson(
					this.getFunctionDao().listAll(em));
			jsonResponse = JsonResponse.ok(data);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Lists all role types without pagination.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listRoleTypes.json")
	public String listRoleTypes() {
		this.logger.debug("calling RoleTypeCtrl.listRoleTypes()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getRoleTypeDao().setDeepMapping(Boolean.FALSE);

			String data = this.getGson().toJson(
					this.getRoleTypeDao().listAll(em));
			jsonResponse = JsonResponse.ok(data);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Lists all role types.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public String list(@RequestBody String paginationData) {
		this.logger.debug("calling RoleTypeCtrl.list()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getRoleTypeDao().setDeepMapping(Boolean.TRUE);

			PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(
					paginationData, PaginatedRequestVo.class);
			PaginatedListVo<RoleTypeVo> paginatedListVo;
			paginatedListVo = this.getRoleTypeDao().listPage(
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
	 * Creates a new role type
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create.json")
	public String create(@RequestBody String data) {
		this.logger.debug("calling RoleTypeCtrl.create()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getRoleTypeDao().setDeepMapping(Boolean.TRUE);

			RoleTypeVo roleTypeVo = this.getGson().fromJson(data,
					RoleTypeVo.class);

			Fx_CreateRoleType fx = this.getFxFactory().getNewFxInstance(
					Fx_CreateRoleType.class);

			fx.setVo(roleTypeVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_CreateRoleType");
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
	 * Modifies a role type
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify.json")
	public String modify(@RequestBody String data) {
		this.logger.debug("calling RoleTypeCtrl.modify()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getRoleTypeDao().setDeepMapping(Boolean.TRUE);

			RoleTypeVo roleTypeVo = this.getGson().fromJson(data,
					RoleTypeVo.class);

			Fx_ModifyRoleType fx = this.getFxFactory().getNewFxInstance(
					Fx_ModifyRoleType.class);

			fx.setVo(roleTypeVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_ModifyRoleType");
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
	 * Deletes a role type
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public String delete(@RequestBody String data) {
		this.logger.debug("calling RoleTypeCtrl.delete()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getRoleTypeDao().setDeepMapping(Boolean.TRUE);

			RoleTypeVo roleTypeVo = this.getGson().fromJson(data,
					RoleTypeVo.class);

			Fx_DeleteRoleType fx = this.getFxFactory().getNewFxInstance(
					Fx_DeleteRoleType.class);

			fx.setVo(roleTypeVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_DeleteRoleType");
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
	 * @return the roleTypeDao
	 */
	public I_RoleTypeDao getRoleTypeDao() {
		return this.roleTypeDao;
	}

	/**
	 * @param roleTypeDao
	 *            the roleTypeDao to set
	 */
	public void setRoleTypeDao(I_RoleTypeDao roleTypeDao) {
		this.roleTypeDao = roleTypeDao;
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
	 * @return the functionDao
	 */
	public I_FunctionDao getFunctionDao() {
		return this.functionDao;
	}

	/**
	 * @param functionDao
	 *            the functionDao to set
	 */
	public void setFunctionDao(I_FunctionDao functionDao) {
		this.functionDao = functionDao;
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
