package org.kairos.ibpnh.controller.devotional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.dao.PersistenceManagerHolder;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.devotional.I_DailyDevotionalDao;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.devotional.Fx_CreateDailyDevotional;
import org.kairos.ibpnh.fx.devotional.Fx_DeleteDailyDevotional;
import org.kairos.ibpnh.fx.devotional.Fx_ModifyDailyDevotional;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.utils.I_DateUtils;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
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
 * DailyDevotional Controller.
 *
 * @author AxelCollardBovy ,created on 09/03/2015.
 */
@Controller
@RequestMapping(value = "/dailyDevotional", produces = "text/json;charset=utf-8")
public class DailyDevotionalCtrl implements I_URIValidator {

    /**
     * List of excluded URIs
     */
    private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
            Arrays.asList(new String[]{}));

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(DailyDevotionalCtrl.class);

    /**
     * Gson Holder
     */
    @Autowired
    private Gson gson;

    /**
     * DailyDevotional Dao
     */
    @Autowired
    private I_DailyDevotionalDao dailyDevotionalDao;

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
     * Date utils
     */
    @Autowired
    private I_DateUtils dateUtils;

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
     * Lists all dailyDevotional.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    public String list(@RequestBody String paginationData) {
        this.logger.debug("calling DailyDevotionalCtrl.list()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
            PaginatedListVo<DailyDevotionalVo> paginatedListVo = this.getDailyDevotionalDao()
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
     * Searches dailyDevotionals.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    public String search(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.search()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            Type type = new TypeToken<PaginatedSearchRequestVo<DailyDevotionalVo>>() {}.getType();
            PaginatedSearchRequestVo<DailyDevotionalVo> paginatedSearchRequestVo = this.getGson().fromJson(data, type);
            PaginatedListVo<DailyDevotionalVo> paginatedListVo = this.getDailyDevotionalDao()
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
     * Creates a new dailyDevotional
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    public String create(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.create()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            DailyDevotionalVo dailyDevotionalVo = this.getGson().fromJson(data,DailyDevotionalVo.class);

            Fx_CreateDailyDevotional fx = this.getFxFactory().getNewFxInstance(
                    Fx_CreateDailyDevotional.class);

            fx.setVo(dailyDevotionalVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_CreateDailyDevotional");
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
     * Deletes a dailyDevotional.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    public String delete(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.delete()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            DailyDevotionalVo dailyDevotionalVo = this.getGson().fromJson(data, DailyDevotionalVo.class);

            Fx_DeleteDailyDevotional fx = this.getFxFactory().getNewFxInstance(
                    Fx_DeleteDailyDevotional.class);

            fx.setVo(dailyDevotionalVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_DeleteDailyDevotional");
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
     * Modifies a dailyDevotional.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modify.json", method = RequestMethod.POST)
    public String modifiy(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.modifiy()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            DailyDevotionalVo dailyDevotionalVo = this.getGson().fromJson(data, DailyDevotionalVo.class);

            Fx_ModifyDailyDevotional fx = this.getFxFactory().getNewFxInstance(
                    Fx_ModifyDailyDevotional.class);

            fx.setVo(dailyDevotionalVo);
            fx.setPm(pm);
            this.logger.debug("executing Fx_ModifyDailyDevotional");
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
     * Lists last daily devotionals.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lastDevotionals.json", method = RequestMethod.POST)
    public String lastDevotionals(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.lastDevotionals()");
        JDOPersistenceManager pm = this.getPersistenceManagerHolder().getPersistenceManager();
        JsonResponse jsonResponse = null;

        try {
            JsonObject jsonObject = this.getGson().fromJson(data,JsonObject.class);
            Long lastDevotionalsAmount = jsonObject.get("amount").getAsLong();
            Date date = this.getDateUtils().parseDate(jsonObject.get("today").getAsString());
            List<DailyDevotionalVo> dailyDevotionalVos =
                    this.getDailyDevotionalDao().listLastDevotionals(pm,lastDevotionalsAmount,date);

            jsonResponse = JsonResponse.ok(this.getGson().toJson(dailyDevotionalVos));
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

    public I_DailyDevotionalDao getDailyDevotionalDao() {
        return dailyDevotionalDao;
    }

    public void setDailyDevotionalDao(I_DailyDevotionalDao dailyDevotionalDao) {
        this.dailyDevotionalDao = dailyDevotionalDao;
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

    public I_DateUtils getDateUtils() {
        return dateUtils;
    }

    public void setDateUtils(I_DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }
}
