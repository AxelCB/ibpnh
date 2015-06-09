package org.kairos.ibpnh.controller.configuration.parameter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.dao.PersistenceManagerHolder;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.configuration.parameter.Fx_CreateParameter;
import org.kairos.ibpnh.fx.configuration.parameter.Fx_DeleteParameter;
import org.kairos.ibpnh.fx.configuration.parameter.Fx_ModifyParameter;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.utils.HashUtils;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
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
 * Parameter Controller.
 *
 * @author AxelCollardBovy ,created on 09/03/2015.
 */
@Controller
@RequestMapping(value = "/parameter", produces = "text/json;charset=utf-8")
public class ParameterCtrl implements I_URIValidator {

    /**
     * List of excluded URIs
     */
    private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
            Arrays.asList(new String[]{"/parameter/init","/parameter/listRoleTypeForCreation"}));

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(ParameterCtrl.class);

    /**
     * Gson Holder
     */
    @Autowired
    private Gson gson;

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
     * Lists all parameters.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    public String list(@RequestBody String paginationData) {
        this.logger.debug("calling ParameterCtrl.list()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
            PaginatedListVo<ParameterVo> paginatedListVo = this.getParameterDao()
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
     * Searches parameters.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    public String search(@RequestBody String data) {
        this.logger.debug("calling ParameterCtrl.search()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            Type type = new TypeToken<PaginatedSearchRequestVo<ParameterVo>>() {}.getType();
            PaginatedSearchRequestVo<ParameterVo> paginatedSearchRequestVo = this.getGson().fromJson(data, type);
            PaginatedListVo<ParameterVo> paginatedListVo = this.getParameterDao()
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
     * Creates a new parameter
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    public String create(@RequestBody String data) {
        this.logger.debug("calling ParameterCtrl.create()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
//            JsonObject jsonObject = this.getGson().fromJson(data, JsonObject.class);
//            E_ParameterType parameterTypeEnum = this.getGson().fromJson(jsonObject.get("parameterType"), E_ParameterType.class);

            ParameterVo parameterVo = this.getGson().fromJson(data, ParameterVo.class);
//                    jsonObject.get("parameter"), ParameterVo.class);
//            parameterVo.setType(parameterTypeEnum);

            Fx_CreateParameter fx = this.getFxFactory().getNewFxInstance(
                    Fx_CreateParameter.class);

            fx.setVo(parameterVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_CreateParameter");
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
     * Deletes a parameter.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    public String delete(@RequestBody String data) {
        this.logger.debug("calling ParameterCtrl.delete()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            ParameterVo parameterVo = this.getGson().fromJson(data, ParameterVo.class);

            Fx_DeleteParameter fx = this.getFxFactory().getNewFxInstance(
                    Fx_DeleteParameter.class);

            fx.setVo(parameterVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_DeleteParameter");
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
     * Modifies a parameter.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modify.json", method = RequestMethod.POST)
    public String modifiy(@RequestBody String data) {
        this.logger.debug("calling ParameterCtrl.modifiy()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            ParameterVo parameterVo = this.getGson().fromJson(data, ParameterVo.class);

            Fx_ModifyParameter fx = this.getFxFactory().getNewFxInstance(
                    Fx_ModifyParameter.class);

            fx.setVo(parameterVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_ModifyParameter");
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
    @RequestMapping(value = "/listParameterTypeForCreation.json")
    public String listRoleTypeForCreation() {
        this.logger.debug("calling ParameterCtrl.listRoleTypeForCreation()");
        JsonResponse jsonResponse = null;

        try {
            E_ParameterType[] parameterTypes = E_ParameterType.values();

            List<E_ParameterType> finalParameterTypes = new ArrayList<>();

            for (int i = 0; i < parameterTypes.length; i++) {
                finalParameterTypes.add(parameterTypes[i]);
            }

            jsonResponse = JsonResponse.ok(this.getGson()
                    .toJson(finalParameterTypes));
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
