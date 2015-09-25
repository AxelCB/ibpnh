package org.kairos.ibpnh.controller;

import com.google.gson.Gson;
import org.kairos.ibpnh.utils.HttpUtils;
import org.kairos.ibpnh.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Language getter.
 * 
 * @author fgonzalez
 * 
 */
@RequestMapping(value = "/lang", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class LangCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(LangCtrl.class);

	/**
	 * The web context holder.
	 */
	@Autowired
	private WebContextHolder webContextHolder;

	/**
	 * Gson.
	 */
	@Autowired
	private Gson gson;

	/**
	 * Returns the accept languages.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/l")
	public String lang(HttpServletRequest request) {
		String defaultLocale = this.getWebContextHolder().getMessageSolver()
				.getDefaultMessageLocale();

		try {
			return getGson().toJson(HttpUtils.extractLanguageTag(request, defaultLocale,
					Boolean.FALSE));
		} catch (Exception e) {
			this.logger
					.error("error getting accept language from client, returning default '{}'",
							e, defaultLocale);

			return defaultLocale;
		}
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

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}
}
