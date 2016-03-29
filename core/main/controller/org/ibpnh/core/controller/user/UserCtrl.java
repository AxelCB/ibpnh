package org.ibpnh.core.controller.user;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.fx.I_FxFactory;
import org.ibpnh.core.fx.user.Fx_CreateUser;
import org.ibpnh.core.fx.user.Fx_DeleteUser;
import org.ibpnh.core.fx.user.Fx_ModifyUserFunctions;
import org.ibpnh.core.fx.user.Fx_ResetAnyUserPassword;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.user.E_RoleType;
import org.ibpnh.core.vo.OrderItemVo;
import org.ibpnh.core.vo.OrderVo;
import org.ibpnh.core.vo.PaginatedListVo;
import org.ibpnh.core.vo.PaginatedRequestVo;
import org.ibpnh.core.vo.PaginatedSearchRequestVo;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.vo.user.UserVo;
import org.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Controller for the User entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/user", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class UserCtrl implements I_URIValidator {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(UserCtrl.class);

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/user/listRoleTypeForCreation",
					"/user/getUserRoleFunctions" }));

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

	/*
	 * Function DAO
	 */
	@Autowired
	private I_UserDao userDao;

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
	 * Gets the list of role types available for creation.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listRoleTypeForCreation.json")
	public String listRoleTypeForCreation() {
		this.logger.debug("calling UserCtrl.listRoleTypeForCreation()");
		JsonResponse jsonResponse = null;

		try {
			E_RoleType[] roleTypes = E_RoleType.values();

			List<E_RoleType> finalRoleTypes = new ArrayList<>();

			for (int i = 0; i < roleTypes.length; i++) {
				if (roleTypes[i].getCanBeCreatedByAdmin()) {
					finalRoleTypes.add(roleTypes[i]);
				}
			}

			jsonResponse = JsonResponse.ok(this.getGson()
					.toJson(finalRoleTypes));
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Lists all users.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public String list(@RequestBody String paginationData) {
		this.logger.debug("calling UserCtrl.list()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getUserDao().setDeepMapping(Boolean.FALSE);

			PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(
					paginationData, PaginatedRequestVo.class);
			PaginatedListVo<UserVo> paginatedListVo;
			paginatedListVo = this.getUserDao().listPage(
					em,
					paginatedRequestVo,
					this.getParameterDao()
							.getByName(em, ParameterVo.ITEMS_PER_PAGE)
							.getValue(Long.class),
					new OrderVo(new OrderItemVo("username", Boolean.TRUE)));

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
	 * Searches users.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search.json")
	public String search(@RequestBody String paginationData) {
		this.logger.debug("calling UserCtrl.search()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			this.getUserDao().setDeepMapping(Boolean.FALSE);

			Type type = new TypeToken<PaginatedSearchRequestVo<UserVo>>() {
			}.getType();
			PaginatedSearchRequestVo<UserVo> paginatedSearchRequestVo = this
					.getGson().fromJson(paginationData, type);
			PaginatedListVo<UserVo> paginatedListVo = this.getUserDao()
					.searchPage(
							em,
							paginatedSearchRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class),
							new OrderVo(new OrderItemVo("username",
									Boolean.TRUE)));

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
	 * Resets the password of any user
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetAnyUserPassword.json")
	public String modifiy(@RequestBody String data) {
		this.logger.debug("calling UserCtrl.resetAnyUserPassword()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			UserVo userVo = this.getGson().fromJson(data, UserVo.class);

			Fx_ResetAnyUserPassword fx = this.getFxFactory().getNewFxInstance(
					Fx_ResetAnyUserPassword.class);

			fx.setVo(userVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_ResetAnyUserPassword");
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
	 * Creates a new user
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create.json")
	public String create(@RequestBody String data) {
		this.logger.debug("calling UserCtrl.create()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			UserVo userVo = this.getGson().fromJson(data, UserVo.class);

			Fx_CreateUser fx = this.getFxFactory().getNewFxInstance(
					Fx_CreateUser.class);

			fx.setVo(userVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_CreateUser");
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
	 * Deletes a user.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public String delete(@RequestBody String data) {
		this.logger.debug("calling UserCtrl.delete()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			UserVo userVo = this.getGson().fromJson(data, UserVo.class);

			Fx_DeleteUser fx = this.getFxFactory().getNewFxInstance(
					Fx_DeleteUser.class);
			fx.setVo(userVo);
			fx.setEm(em);

			this.logger.debug("executing Fx_DeleteUser");
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
	 * Returns the user role's functions.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserRoleFunctions.json")
	public String getUserRoleFunctions(@RequestBody String data) {
		this.logger.debug("calling UserCtrl.getUserRoleFunctions()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			UserVo userVo = this.getGson().fromJson(data, UserVo.class);
			this.getUserDao().setCustomMap("only-role-functions-UserVo");
			userVo = this.getUserDao().getById(em, userVo.getId());

			jsonResponse = JsonResponse.ok(this.getGson().toJson(
					userVo.getRole().getRoleFunctions()));
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}
	
	/**
	 * Modifies the User's functions.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyUserFunctions.json")
	public String modifyUserFunctions(@RequestBody String data) {
		this.logger.debug("calling UserCtrl.modifyUserFunctions()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			UserVo userVo = this.getGson().fromJson(data, UserVo.class);
			
			Fx_ModifyUserFunctions fx = this.getFxFactory().getNewFxInstance(Fx_ModifyUserFunctions.class);
			fx.setVo(userVo);
			fx.setEm(em);

			this.logger.debug("executing Fx_ModifyUser");
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
	 * @param webContextHolder
	 *            the webContextHolder to set
	 */
	public void setWebContextHolder(WebContextHolder webContextHolder) {
		this.webContextHolder = webContextHolder;
	}
}
