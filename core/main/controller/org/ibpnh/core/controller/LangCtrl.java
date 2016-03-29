package org.ibpnh.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ibpnh.core.utils.HttpUtils;
import org.ibpnh.core.web.WebContextHolder;

/**
 * Language getter.
 * 
 * @author Axel Collard Bovy
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
			return HttpUtils.extractLanguageTag(request, defaultLocale,
					Boolean.FALSE);
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

}
