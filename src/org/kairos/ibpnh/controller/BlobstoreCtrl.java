package org.kairos.ibpnh.controller;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.Gson;
import org.kairos.ibpnh.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Blobstore Service Controller.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/blobstore", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class BlobstoreCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(BlobstoreCtrl.class);

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
	 * Blobstore Service
	 */
	@Autowired
	private BlobstoreService blobstoreService;

	/**
	 * Images Service
	 */
	@Autowired
	private ImagesService imagesService;



	/**
	 * Returns the upload url.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadUrl")
	public String uploadUrl(@RequestBody String data) {
		String uploadUrl=null;
		try {
			uploadUrl = blobstoreService.createUploadUrl(data);
		} catch (Exception e) {
			this.logger.error("error getting blobstore upload url", e);
		}
		return uploadUrl;
	}


	/**
	 * Returns the upload url.
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/serveImageUrl")
	public String serveImageUrl(@RequestBody String data,HttpServletResponse response) {
		String imageServingUrl = null;
		try {
			BlobKey blobKey = new BlobKey(data);
			imageServingUrl = imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey));
		} catch (Exception e) {
			this.logger.error("error getting blobstore image from blobkey", e);
		}
		return imageServingUrl;
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
