package org.kairos.ibpnh.controller.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.dao.PersistenceManagerHolder;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.user.I_FunctionDao;
import org.kairos.ibpnh.dao.user.I_RoleDao;
import org.kairos.ibpnh.dao.user.I_RoleTypeDao;
import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.user.Fx_CreateUser;
import org.kairos.ibpnh.fx.user.Fx_DeleteUser;
import org.kairos.ibpnh.fx.user.Fx_ModifyUser;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.utils.HashUtils;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.vo.user.*;
import org.kairos.ibpnh.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;

/**
 * User Controller.
 *
 * @author AxelCollardBovy ,created on 09/03/2015.
 */
@Controller
@RequestMapping(value = "/user", produces = "text/json;charset=utf-8")
public class UserCtrl implements I_URIValidator {

    /**
     * List of excluded URIs
     */
    private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
            Arrays.asList(new String[]{"/user/init","/user/listRoleTypeForCreation"}));

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(UserCtrl.class);

    /**
     * Gson Holder
     */
    @Autowired
    private Gson gson;

    /**
     * User Dao
     */
    @Autowired
    private I_UserDao userDao;

    /**
     * User Dao
     */
    @Autowired
    private I_RoleTypeDao roleTypeDao;

    /**
     * User Dao
     */
    @Autowired
    private I_RoleDao roleDao;

//    /**
//     * Function Dao
//     */
//    @Autowired
//    private I_FunctionDao functionDao;

    /**
     * Parameter Dao
     */
    @Autowired
    private I_ParameterDao parameterDao;

    /**
     * Fx Factory Dao
     */
    @Autowired
    private I_FxFactory fxFactory;

    /**
     * Entity Manager Holder
     */
    @Autowired
    private PersistenceManagerHolder persistenceManagerHolder;

    /**
     * Web Context Holder.
     */
    @Autowired
    private WebContextHolder webContextHolder;

    /*
 * (non-Javadoc)
 *
 * @see
 * org.kairos.ibpnh.core.controller.I_URIValidator#validate(java.lang.String)
 */
    @Override
    public Boolean validate(String uri) {
        return !EXCLUDED_URIS.contains(uri);
    }

    /**
     * Inits the app.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/public/init", method = RequestMethod.GET)
    public String init() {
        this.logger.debug("calling UserCtrl.init()");
        JDOPersistenceManager pm = null;
        JsonResponse jsonResponse = null;

        try {
            pm = this.getPersistenceManagerHolder().getPersistenceManager();
//            if(this.getUserDao().countByRoleType(pm,Boolean.FALSE,E_RoleType.ADMIN)==0){
            if(this.getUserDao().listAll(pm).size()==0){

                //We create the PASTOR role type
                RoleTypeVo pastorRoleTypeVo = new RoleTypeVo();
                pastorRoleTypeVo.setDescription("Pastor");
                pastorRoleTypeVo.setName("Pastor");
                pastorRoleTypeVo.setRoleTypeEnum(E_RoleType.PASTOR);

                //We create the USER role type
                RoleTypeVo userRoleTypeVo = new RoleTypeVo();
                userRoleTypeVo.setDescription("Usuario");
                userRoleTypeVo.setName("Usuario");
                userRoleTypeVo.setRoleTypeEnum(E_RoleType.USER);

                this.getRoleTypeDao().persist(pm,pastorRoleTypeVo);
                this.getRoleTypeDao().persist(pm,userRoleTypeVo);

                //We create the ADMIN role type
                RoleTypeVo adminRoleTypeVo = new RoleTypeVo();
                adminRoleTypeVo.setDescription("Administrador");
                adminRoleTypeVo.setName("Administrador");
                adminRoleTypeVo.setRoleTypeEnum(E_RoleType.ADMIN);

                this.getRoleTypeDao().persist(pm,adminRoleTypeVo);

                //We create the necessary functions for the admin role type
//                List<FunctionVo> functions = new ArrayList<FunctionVo>();

//                FunctionVo functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("CreateUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("createUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/create");
//                functions.add(functionVo);
//
//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("DeleteUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("deleteUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/delete");
//                functions.add(functionVo);
//
//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("ModifyUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("modifyUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/modify");
//                functions.add(functionVo);

//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("ListUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("listUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/list");
//                functions.add(functionVo);

//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("SearchUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("searchUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/search");
//                functions.add(functionVo);

//                for (FunctionVo function : functions) {
//                    RoleTypeFunctionVo roleTypeFunctionVo = new RoleTypeFunctionVo();
//                    roleTypeFunctionVo.setEnabled(Boolean.TRUE);
//                    roleTypeFunctionVo.setFunction(function);
//                    roleTypeFunctionVo.setRoleType(adminRoleTypeVo);
//
//                    adminRoleTypeVo.getRoleTypeFunctions().add(roleTypeFunctionVo);
//                }

                adminRoleTypeVo=this.getRoleTypeDao().getByRoleTypeEnum(pm,E_RoleType.ADMIN);

                //We create the Admin role
                RoleVo adminRoleVo = new RoleVo();
                adminRoleVo.setRoleType(adminRoleTypeVo);
//                adminRoleVo.copyOrUpdateFromRoleType(adminRoleTypeVo);

                //We create the Admin user
                UserVo adminVo = new UserVo();
                adminVo.setEnabled(Boolean.TRUE);
                adminVo.setFirstLogin(false);
                adminVo.setLoginAttempts(Integer.valueOf(0));
                adminVo.setUsername("admin");
                adminVo.setHashCost(Long.valueOf("5"));
                adminVo.setPassword(HashUtils.hashPassword("krwlng", adminVo.getHashCost()));
                adminVo.setRole(adminRoleVo);

                adminVo = this.getUserDao().persist(pm,adminVo);

                jsonResponse = JsonResponse.ok(this.getGson().toJson(adminVo));
            }

            this.logger.debug("executing Init_User(Admin)");
        } catch (Exception e) {
            this.logger.error("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
                    ErrorCodes.ERROR_UNEXPECTED);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Lists all users.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    public String list(@RequestBody String paginationData) {
        this.logger.debug("calling UserCtrl.list()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
            PaginatedListVo<UserVo> paginatedListVo = this.getUserDao()
                    .listPage(
                            pm,
                            paginatedRequestVo,
                            10l);
//            this.getParameterDao()
//                    .getByName(pm, ParameterVo.ITEMS_PER_PAGE)
//                    .getValue(Long.class);

            String data = this.getGson().toJson(paginatedListVo);

            jsonResponse = JsonResponse.ok(data);
//        } catch (ParseException e) {
//            this.logger.error("error trying to read items.per.page parameter",
//                    e);
//
//            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
//                    ErrorCodes.ERROR_PARAMETER_PARSING);
        } catch (Exception e) {
            this.logger.error("error trying to read items.per.page parameter",
                    e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
                    ErrorCodes.ERROR_PARAMETER_MISSING);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Searches users.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    public String search(@RequestBody String data) {
        this.logger.debug("calling UserCtrl.search()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            Type type = new TypeToken<PaginatedSearchRequestVo<UserVo>>() {}.getType();
            PaginatedSearchRequestVo<UserVo> paginatedSearchRequestVo = this.getGson().fromJson(data, type);
            PaginatedListVo<UserVo> paginatedListVo = this.getUserDao()
                    .searchPage(
                            pm,
                            paginatedSearchRequestVo,
                            this.getParameterDao()
                                    .getByName(pm, ParameterVo.ITEMS_PER_PAGE)
                                    .getValue(Long.class));

            String responseData = this.getGson().toJson(paginatedListVo);

            jsonResponse = JsonResponse.ok(responseData);
        } catch (ParseException e) {
            this.logger.error("error trying to read items.per.page parameter",
                    e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
                    ErrorCodes.ERROR_PARAMETER_PARSING);
        } catch (Exception e) {
            this.logger.error("error trying to read items.per.page parameter",
                    e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
                    ErrorCodes.ERROR_PARAMETER_MISSING);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Add Functions to admin.
     *
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/addFunctions.json", method = RequestMethod.GET)
//    public String addFunctions() {
//        this.logger.debug("calling UserCtrl.addFunctions()");
//        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
//        JsonResponse jsonResponse = null;
//
//        try {
//            UserVo userVo = this.getUserDao().getByUsername(pm, "admin");
//            String responseData = this.getGson().toJson(Boolean.FALSE);
//            if(userVo.getRole().getRoleFunctions().size()<=1){
//                pm.currentTransaction().begin();
//
//                List<FunctionVo> functions = new ArrayList<FunctionVo>();
//
//                FunctionVo functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("CreateUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("createUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/create");
//                functions.add(functionVo);
//
//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("DeleteUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("deleteUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/delete");
//                functions.add(functionVo);
//
//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("ModifyUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("modifyUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/modify");
//                functions.add(functionVo);
//
//                functionVo = new FunctionVo();
//                functionVo.setActionName("user");
//                functionVo.setDescription("SearchUser");
//                functionVo.setMenuName("configuration");
//                functionVo.setName("searchUser");
//                functionVo.setSubmenuName(null);
//                functionVo.setUri("/user/search");
//                functions.add(functionVo);
//
//                for (FunctionVo function : functions){
//                    function = this.getFunctionDao().persist(pm,function);
//                    pm.flush();
//
//                    RoleFunctionVo roleFunctionVo = new RoleFunctionVo();
//                    roleFunctionVo.setFunction(function);
//                    roleFunctionVo.setRole(userVo.getRole());
//                    userVo.getRole().getRoleFunctions().add(roleFunctionVo);
////                    this.getUserDao().persist(pm,userVo);
//                    this.getRoleDao().persist(pm,userVo.getRole());
//                    userVo = this.getUserDao().getByUsername(pm, "admin");
//
//                    pm.flush();
//                }
//
//                pm.currentTransaction().commit();
//
//                responseData = this.getGson().toJson(Boolean.TRUE);
//            }
//
//            jsonResponse = JsonResponse.ok(responseData);
////        } catch (ParseException e) {
////            this.logger.error("error trying to read items.per.page parameter",
////                    e);
////
////            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
////                    ErrorCodes.ERROR_PARAMETER_PARSING);
//        } catch (Exception e) {
//            this.logger.error("error trying to read items.per.page parameter",
//                    e);
//
//            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
//                    ErrorCodes.ERROR_PARAMETER_MISSING);
//        } finally {
//            this.getPersistenceManagerHolder().closePersistenceManager(pm);
//        }
//
//        return this.getGson().toJson(jsonResponse);
//    }

    /**
     * Creates a new user
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    public String create(@RequestBody String data) {
        this.logger.debug("calling UserCtrl.create()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            JsonObject jsonObject = this.getGson().fromJson(data, JsonObject.class);
            E_RoleType roleTypeEnum = this.getGson().fromJson(jsonObject.get("roleType"), E_RoleType.class);
            RoleTypeVo roleTypeVo = this.getRoleTypeDao().getByRoleTypeEnum(pm,roleTypeEnum);

            RoleVo roleVo = new RoleVo();
            roleVo.setRoleType(roleTypeVo);
//            roleVo.copyOrUpdateFromRoleType(roleTypeVo);

            UserVo userVo = this.getGson().fromJson(jsonObject.get("user"), UserVo.class);
            userVo.setRole(roleVo);

            Fx_CreateUser fx = this.getFxFactory().getNewFxInstance(
                    Fx_CreateUser.class);

            fx.setVo(userVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_CreateUser");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Deletes a user.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    public String delete(@RequestBody String data) {
        this.logger.debug("calling UserCtrl.delete()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            UserVo userVo = this.getGson().fromJson(data, UserVo.class);

            Fx_DeleteUser fx = this.getFxFactory().getNewFxInstance(
                    Fx_DeleteUser.class);

            fx.setVo(userVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_DeleteUser");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Modifies a user.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modify.json", method = RequestMethod.POST)
    public String modifiy(@RequestBody String data) {
        this.logger.debug("calling UserCtrl.modifiy()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            UserVo userVo = this.getGson().fromJson(data, UserVo.class);

            Fx_ModifyUser fx = this.getFxFactory().getNewFxInstance(
                    Fx_ModifyUser.class);

            fx.setVo(userVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_ModifyUser");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
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

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public PersistenceManagerHolder getPersistenceManagerHolder() {
        return persistenceManagerHolder;
    }

    public void setPersistenceManagerHolder(PersistenceManagerHolder persistenceManagerHolder) {
        this.persistenceManagerHolder = persistenceManagerHolder;
    }

    public WebContextHolder getWebContextHolder() {
        return webContextHolder;
    }

    public void setWebContextHolder(WebContextHolder webContextHolder) {
        this.webContextHolder = webContextHolder;
    }

    public I_UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(I_UserDao userDao) {
        this.userDao = userDao;
    }

    public I_RoleTypeDao getRoleTypeDao() {
        return roleTypeDao;
    }

    public void setRoleTypeDao(I_RoleTypeDao roleTypeDao) {
        this.roleTypeDao = roleTypeDao;
    }

    public I_RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(I_RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public I_ParameterDao getParameterDao() {
        return parameterDao;
    }

    public void setParameterDao(I_ParameterDao parameterDao) {
        this.parameterDao = parameterDao;
    }

    public I_FxFactory getFxFactory() {
        return fxFactory;
    }

    public void setFxFactory(I_FxFactory fxFactory) {
        this.fxFactory = fxFactory;
    }

//    public I_FunctionDao getFunctionDao() {
//        return functionDao;
//    }
//
//    public void setFunctionDao(I_FunctionDao functionDao) {
//        this.functionDao = functionDao;
//    }
}