package org.ibpnh.core.controller;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * Controller for serving images.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/images", method = RequestMethod.GET)
public class ImagesCtrl {

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(ImagesCtrl.class);

	/**
	 * Serves an image.
	 * 
	 * @param image the image ID.
	 * 
	 * @return Image bytes
	 */
	private byte[] image(String image) {
		this.logger.debug("trying to acces image with id: {}", image);

		try {
			ParameterVo parameterVo = this.getParameterDao().getByName(null,
					ParameterVo.IMAGE_PATH_PREFIX + image);

			if (parameterVo == null) {
				this.logger.debug("the image does not exists");

				return null;
			} else {
				this.logger.debug("image found! trying to get its bytes");

				Path path = FileSystems.getDefault().getPath(
						parameterVo.getValue());
				byte[] filearray = Files.readAllBytes(path);

				// return image;
				return filearray;
			}
		} catch (NullPointerException npe) {
			this.logger.error("error trying to get parameter {}",
					ParameterVo.IMAGE_PATH_PREFIX + image);

			return null;
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			return null;
		}
	}
	
	/**
	 * Serves an image as PNG.
	 * 
	 * @param image
	 *            image identification
	 * 
	 * @return byte array or nothing
	 */
	@ResponseBody
	@RequestMapping(value = "/{image}.png", produces = "image/png")
	public byte[] pngImage(@PathVariable String image) {
		this.logger.debug("trying to access PNG image with ID {}", image);
		
		return this.image(image);
	}
	
	/**
	 * Serves an image as ICO.
	 * 
	 * @param image
	 *            image identification
	 * 
	 * @return byte array or nothing
	 */
	@ResponseBody
	@RequestMapping(value = "/{image}.ico", produces = "image/x-icon")
	public byte[] icoImage(@PathVariable String image) {
		this.logger.debug("trying to access ICO image with ID {}", image);
		
		return this.image(image);
	}

	/**
	 * @return the parameterDao
	 */
	public I_ParameterDao getParameterDao() {
		return this.parameterDao;
	}

	/**
	 * @param parameterDao
	 *            the parameterDao to set
	 */
	public void setParameterDao(I_ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

}
