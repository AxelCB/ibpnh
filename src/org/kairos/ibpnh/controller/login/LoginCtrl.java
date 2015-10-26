package org.kairos.ibpnh.controller.login;

import com.google.gson.Gson;
import org.kairos.ibpnh.controller.I_URIValidator;
import org.kairos.ibpnh.fx.I_FxFactory;
import org.kairos.ibpnh.fx.login.Fx_Login;
import org.kairos.ibpnh.fx.login.Fx_Logout;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.vo.user.UserVo;
import org.kairos.ibpnh.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Login Controller.
 *
 * @author AxelCollardBovy ,created on 27/02/2015.
 */
@RequestMapping(value = "/login", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class LoginCtrl implements I_URIValidator {
    /**
     * List of excluded URIs
     */
    private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
            Arrays.asList(new String[]{"/login/logout",
                    "/login/getLoggedUser", "/login/registrationEnabled" }));

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(LoginCtrl.class);

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
	 * org.kairos.ibpnh.core.controller.I_URIValidator#validate(java.lang.String)
	 */
    @Override
    public Boolean validate(String uri) {
        return !EXCLUDED_URIS.contains(uri);
    }

    /**
     * Tries to login the user.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login.json")
    public String login(@RequestBody String data) {
        this.logger.debug("calling LoginCtrl.login()");
        JsonResponse jsonResponse = null;

        try {
            UserVo user = this.getGson().fromJson(data,
                    UserVo.class);

            Fx_Login fx = this.getFxFactory().getNewFxInstance(Fx_Login.class);
            fx.setVo(user);
            this.logger.debug("executing Fx_Login");
            jsonResponse = fx.execute();
        } catch (Exception e) {
            this.logger.debug("unexpected error", e);

            jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
                    ErrorCodes.ERROR_UNEXPECTED);
        }

        return this.getGson().toJson(jsonResponse);
    }

    /**
     * Logouts the user.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout.json")
    public String login() {
        this.logger.debug("calling LoginCtrl.logout()");

        Fx_Logout fx = this.getFxFactory().getNewFxInstance(Fx_Logout.class);

        this.logger.debug("executing Fx_Logout");

        return this.getGson().toJson(fx.execute());
    }

    /**
     * Gets the registration enabled parameter.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registrationEnabled.json")
    public String registrationEnabled() {
        this.logger.debug("calling LoginCtrl.registrationEnabled()");
        JsonResponse response = null;
        try {
            //Parameter parameter = this.getParameterDao().getByName(pm, ParameterVo.USER_REGISTRATION);

//          response = JsonResponse.ok(this.getGson().toJson(
//                    parameterVo.getValue(Boolean.class)));
            response = JsonResponse.ok(this.getGson().toJson(Boolean.FALSE));
//        } catch (ParseException pe) {
//            this.logger.error("error parsing parameter", pe);
//
//            response = this.getWebContextHolder().unexpectedErrorResponse(
//                    ErrorCodes.ERROR_PARAMETER_PARSING);
        } catch (Exception e) {
            this.logger.error("unexpected error", e);

            response = this.getWebContextHolder().unexpectedErrorResponse();
        }

        return this.getGson().toJson(response);
    }

    /**
     * Retrieves the logged user data.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLoggedUser.json")
    public String getLoggedUser() {
        this.logger.debug("calling LoginCtrl.getLoggedUser()");

        String data = this.getGson().toJson(
                this.getWebContextHolder().getUser());

        JsonResponse jsonResponse = JsonResponse.ok(data);

        return this.getGson().toJson(jsonResponse);
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public I_FxFactory getFxFactory() {
        return fxFactory;
    }

    public void setFxFactory(I_FxFactory fxFactory) {
        this.fxFactory = fxFactory;
    }

    public WebContextHolder getWebContextHolder() {
        return webContextHolder;
    }

    public void setWebContextHolder(WebContextHolder webContextHolder) {
        this.webContextHolder = webContextHolder;
    }
}
