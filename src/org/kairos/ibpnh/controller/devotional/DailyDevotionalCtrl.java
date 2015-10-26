package org.kairos.ibpnh.controller.devotional;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.dao.devotional.I_DailyDevotionalDao;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.devotional.Fx_CreateDailyDevotional;
import org.kairos.ibpnh.fx.devotional.Fx_DeleteDailyDevotional;
import org.kairos.ibpnh.fx.devotional.Fx_ModifyDailyDevotional;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.devotional.DailyDevotional;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.utils.I_DateUtils;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
import org.kairos.ibpnh.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
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
    @RequestMapping(value = "/public/list.json", method = RequestMethod.POST)
    public String list(@RequestBody String paginationData) {
        this.logger.debug("calling DailyDevotionalCtrl.list()");
        JsonResponse jsonResponse = null;

        try {
            PaginatedRequestVo paginatedRequest = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
            PaginatedListVo<DailyDevotionalVo> paginatedList = this.getDailyDevotionalDao().listPage(paginatedRequest, 10l);
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
     * Searches dailyDevotionals.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    public String search(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.search()");
        JsonResponse jsonResponse = null;

        try {
            Type type = new TypeToken<PaginatedSearchRequestVo<DailyDevotionalVo>>() {}.getType();
            PaginatedSearchRequestVo<DailyDevotionalVo> paginatedSearchRequest = this.getGson().fromJson(data, type);
            PaginatedListVo<DailyDevotionalVo> paginatedList = this.getDailyDevotionalDao().searchPage(paginatedSearchRequest,10L
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
     * Creates a new dailyDevotional
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    public String create(@RequestBody String formData,HttpServletRequest req,@RequestParam("data") String data) {
        this.logger.debug("calling DailyDevotionalCtrl.create()");
        JsonResponse jsonResponse = null;

        try {
            BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
            Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

            DailyDevotionalVo dailyDevotional = this.getGson().fromJson(data, DailyDevotionalVo.class);
            dailyDevotional.setImageBlobKey(blobs.get("file").get(0).getKeyString());

            Fx_CreateDailyDevotional fx = this.getFxFactory().getNewFxInstance(
                    Fx_CreateDailyDevotional.class);

            fx.setVo(dailyDevotional);
            this.logger.debug("executing Fx_CreateDailyDevotional");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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
        JsonResponse jsonResponse = null;

        try {
            DailyDevotionalVo dailyDevotional = this.getGson().fromJson(data, DailyDevotionalVo.class);

            Fx_DeleteDailyDevotional fx = this.getFxFactory().getNewFxInstance(
                    Fx_DeleteDailyDevotional.class);

            fx.setVo(dailyDevotional);
            this.logger.debug("executing Fx_DeleteDailyDevotional");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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
        JsonResponse jsonResponse = null;

        try {
            DailyDevotionalVo dailyDevotional = this.getGson().fromJson(data, DailyDevotionalVo.class);

            Fx_ModifyDailyDevotional fx = this.getFxFactory().getNewFxInstance(
                    Fx_ModifyDailyDevotional.class);

            fx.setVo(dailyDevotional);
            this.logger.debug("executing Fx_ModifyDailyDevotional");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Lists last daily devotionals.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/public/lastDevotionals.json", method = RequestMethod.POST)
    public String lastDevotionals(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.lastDevotionals()");
        JsonResponse jsonResponse = null;
        try {
            JsonObject jsonObject = this.getGson().fromJson(data,JsonObject.class);
            Long lastDevotionalsAmount = jsonObject.get("amount").getAsLong();
            Date date = this.getDateUtils().parseDate(jsonObject.get("today").getAsString());
            List<DailyDevotionalVo> dailyDevotionals =
                    this.getDailyDevotionalDao().listLastDevotionals(lastDevotionalsAmount,date);
            jsonResponse = JsonResponse.ok(this.getGson().toJson(dailyDevotionals));
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Lists last daily devotionals.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/public/find.json", method = RequestMethod.POST)
    public String find(@RequestBody String data) {
        this.logger.debug("calling DailyDevotionalCtrl.find()");
        JsonResponse jsonResponse = null;
        try {
            DailyDevotionalVo dailyDevotional = this.getGson().fromJson(data,DailyDevotionalVo.class);
            dailyDevotional = this.getDailyDevotionalDao().getById(dailyDevotional.getId());
            jsonResponse = JsonResponse.ok(this.getGson().toJson(dailyDevotional));
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
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
