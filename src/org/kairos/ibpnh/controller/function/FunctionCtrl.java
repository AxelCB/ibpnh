package org.kairos.ibpnh.controller.function;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.dao.PersistenceManagerHolder;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.user.I_FunctionDao;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.function.Fx_CreateFunction;
import org.kairos.ibpnh.fx.function.Fx_DeleteFunction;
import org.kairos.ibpnh.fx.function.Fx_ModifyFunction;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.vo.user.FunctionVo;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Axel on 18/05/2015.
 * @author AxelCollardBovy
 */
@Controller
@RequestMapping(value = "/function", produces = "text/json;charset=utf-8")
public class FunctionCtrl implements I_URIValidator {

    /**
     * List of excluded URIs
     */
    private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
            Arrays.asList(new String[]{""}));

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(FunctionCtrl.class);

    /**
     * Gson Holder
     */
    @Autowired
    private Gson gson;

    /**
     * Function Dao
     */
    @Autowired
    private I_FunctionDao functionDao;

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
     * Lists all document types.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    public String list(@RequestBody String paginationData) {
        this.logger.debug("calling FunctionCtrl.list()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
            PaginatedListVo<FunctionVo> paginatedListVo = this.getFunctionDao()
                    .listPage(
                            pm,
                            paginatedRequestVo,
                            this.getParameterDao()
                                    .getByName(pm, ParameterVo.ITEMS_PER_PAGE)
                                    .getValue(Long.class));

            String data = this.getGson().toJson(paginatedListVo);

            jsonResponse = JsonResponse.ok(data);
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
     * Searches functions.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    public String search(@RequestBody String data) {
        this.logger.debug("calling FunctionCtrl.search()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            Type type = new TypeToken<PaginatedSearchRequestVo<FunctionVo>>() {}.getType();
            PaginatedSearchRequestVo<FunctionVo> paginatedSearchRequestVo = this.getGson().fromJson(data, type);
            PaginatedListVo<FunctionVo> paginatedListVo = this.getFunctionDao()
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
     * Creates a new function
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    public String create(@RequestBody String data) {
        this.logger.debug("calling FunctionCtrl.create()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            FunctionVo functionVo = this.getGson().fromJson(data, FunctionVo.class);

            Fx_CreateFunction fx = this.getFxFactory().getNewFxInstance(
                    Fx_CreateFunction.class);

            fx.setVo(functionVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_CreateFunction");
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
     * Deletes a function.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    public String delete(@RequestBody String data) {
        this.logger.debug("calling FunctionCtrl.delete()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            FunctionVo functionVo = this.getGson().fromJson(data, FunctionVo.class);

            Fx_DeleteFunction fx = this.getFxFactory().getNewFxInstance(
                    Fx_DeleteFunction.class);

            fx.setVo(functionVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_DeleteFunction");
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
     * Modifies a function.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modify.json", method = RequestMethod.POST)
    public String modifiy(@RequestBody String data) {
        this.logger.debug("calling FunctionCtrl.modifiy()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            FunctionVo functionVo = this.getGson().fromJson(data, FunctionVo.class);

            Fx_ModifyFunction fx = this.getFxFactory().getNewFxInstance(
                    Fx_ModifyFunction.class);

            fx.setVo(functionVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_ModifyFunction");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
        } finally {
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
        }

        return this.getGson().toJson(jsonResponse);
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public I_FunctionDao getFunctionDao() {
        return functionDao;
    }

    public void setFunctionDao(I_FunctionDao functionDao) {
        this.functionDao = functionDao;
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
}
