package org.kairos.ibpnh.controller.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.user.I_UserDao;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.user.Fx_CreateUser;
import org.kairos.ibpnh.fx.user.Fx_DeleteUser;
import org.kairos.ibpnh.fx.user.Fx_ModifyUser;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.utils.HashUtils;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
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
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        this.logger.debug("calling UserCtrl.init()");
        JsonResponse jsonResponse = null;

        try {
//            if(this.getUserDao().countByRoleType(pm,Boolean.FALSE,E_RoleType.ADMIN)==0){
            if(this.getUserDao().listAll().size()==0){
                //We create the Admin user
                User admin = new User();
                admin.setEnabled(Boolean.TRUE);
                admin.setFirstLogin(false);
                admin.setLoginAttempts(Integer.valueOf(0));
                admin.setUsername("admin");
                admin.setHashCost(Long.valueOf("10"));
                admin.setPassword(HashUtils.hashPassword("krwlng", admin.getHashCost()));
                admin.setRoleType(E_RoleType.ADMIN);

                admin = this.getUserDao().persist(admin);

                jsonResponse = JsonResponse.ok(this.getGson().toJson(admin));
            }

            this.logger.debug("executing Init_User(Admin)");
        } catch (Exception e) {
            this.logger.error("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
                    ErrorCodes.ERROR_UNEXPECTED);
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
        JsonResponse jsonResponse = null;

        try {
            PaginatedRequestVo paginatedRequest = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
            PaginatedListVo<User> paginatedList = this.getUserDao().listPage(paginatedRequest, 10l);
//            this.getParameterDao()
//                    .getByName(pm, ParameterVo.ITEMS_PER_PAGE)
//                    .getValue(Long.class);

            String data = this.getGson().toJson(paginatedList);

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
        JsonResponse jsonResponse = null;

        try {
            Type type = new TypeToken<PaginatedSearchRequestVo<User>>() {}.getType();
            PaginatedSearchRequestVo<User> paginatedSearchRequest = this.getGson().fromJson(data, type);
            PaginatedListVo<User> paginatedList = this.getUserDao()
                    .searchPage(paginatedSearchRequest,10L
//                            this.getParameterDao()
//                                    .getByName(Parameter.ITEMS_PER_PAGE)
//                                    .getValue(Long.class)
                    );

            String responseData = this.getGson().toJson(paginatedList);

            jsonResponse = JsonResponse.ok(responseData);
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
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Creates a new user
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    public String create(@RequestBody String data) {
        this.logger.debug("calling UserCtrl.create()");
        JsonResponse jsonResponse = null;

        try {
            JsonObject jsonObject = this.getGson().fromJson(data, JsonObject.class);
            E_RoleType roleTypeEnum = this.getGson().fromJson(jsonObject.get("roleType"), E_RoleType.class);

            User user = this.getGson().fromJson(jsonObject.get("user"), User.class);
            user.setRoleType(roleTypeEnum);

            Fx_CreateUser fx = this.getFxFactory().getNewFxInstance(
                    Fx_CreateUser.class);
            fx.setEntity(user);
            this.logger.debug("executing Fx_CreateUser");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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
        JsonResponse jsonResponse = null;

        try {
            User user = this.getGson().fromJson(data, User.class);

            Fx_DeleteUser fx = this.getFxFactory().getNewFxInstance(
                    Fx_DeleteUser.class);
            fx.setEntity(user);
            this.logger.debug("executing Fx_DeleteUser");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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
        JsonResponse jsonResponse = null;
        try {
            User user = this.getGson().fromJson(data, User.class);

            Fx_ModifyUser fx = this.getFxFactory().getNewFxInstance(
                    Fx_ModifyUser.class);

            fx.setEntity(user);
            this.logger.debug("executing Fx_ModifyUser");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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

}