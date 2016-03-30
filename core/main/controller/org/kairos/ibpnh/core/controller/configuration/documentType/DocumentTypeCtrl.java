package org.kairos.ibpnh.core.controller.configuration.documentType;

import java.lang.reflect.Type;
import java.text.ParseException;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.dao.person.I_DocumentTypeDao;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.configuration.documentType.Fx_CreateDocumentType;
import org.kairos.ibpnh.core.fx.configuration.documentType.Fx_DeleteDocumentType;
import org.kairos.ibpnh.core.fx.configuration.documentType.Fx_ModifyDocumentType;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.PaginatedListVo;
import org.kairos.ibpnh.core.vo.PaginatedRequestVo;
import org.kairos.ibpnh.core.vo.PaginatedSearchRequestVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.person.DocumentTypeVo;
import org.kairos.ibpnh.core.web.WebContextHolder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Controller for the Document Type entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/documentType", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class DocumentTypeCtrl {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(DocumentTypeCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Document Type DAO
	 */
	@Autowired
	private I_DocumentTypeDao documentTypeDao;
	
	/**
	 * Parameter DAO
	 */
	@Autowired
	private I_ParameterDao parameterDao;

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

	/**
	 * Lists all document types.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public String list(@RequestBody String paginationData) {
		this.logger.debug("calling DocumentTypeCtrl.list()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
			PaginatedListVo<DocumentTypeVo> paginatedListVo = this.getDocumentTypeDao()
					.listPage(
							em,
							paginatedRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
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
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Searches documentTypes.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search.json")
	public String search(@RequestBody String data) {
		this.logger.debug("calling DocumentTypeCtrl.search()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			Type type = new TypeToken<PaginatedSearchRequestVo<DocumentTypeVo>>() {}.getType();
			PaginatedSearchRequestVo<DocumentTypeVo> paginatedSearchRequestVo = this.getGson().fromJson(data, type);
			PaginatedListVo<DocumentTypeVo> paginatedListVo = this.getDocumentTypeDao()
					.searchPage(
							em,
							paginatedSearchRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
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
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Creates a new documentType
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create.json")
	public String create(@RequestBody String data) {
		this.logger.debug("calling DocumentTypeCtrl.create()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			DocumentTypeVo documentTypeVo = this.getGson().fromJson(data, DocumentTypeVo.class);
	
			Fx_CreateDocumentType fx = this.getFxFactory().getNewFxInstance(
					Fx_CreateDocumentType.class);
	
			fx.setVo(documentTypeVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_CreateDocumentType");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Deletes a documentType.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public String delete(@RequestBody String data) {
		this.logger.debug("calling DocumentTypeCtrl.delete()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;
		
		try {
			DocumentTypeVo documentTypeVo = this.getGson().fromJson(data, DocumentTypeVo.class);
	
			Fx_DeleteDocumentType fx = this.getFxFactory().getNewFxInstance(Fx_DeleteDocumentType.class);
	
			fx.setVo(documentTypeVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_DeleteDocumentType");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Modifies a documentType.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify.json")
	public String modifiy(@RequestBody String data) {
		this.logger.debug("calling DocumentTypeCtrl.modifiy()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			DocumentTypeVo documentTypeVo = this.getGson().fromJson(data, DocumentTypeVo.class);
	
			Fx_ModifyDocumentType fx = this.getFxFactory().getNewFxInstance(
					Fx_ModifyDocumentType.class);
	
			fx.setVo(documentTypeVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_ModifyDocumentType");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * @return the entityManagerHolder
	 */
	public EntityManagerHolder getEntityManagerHolder() {
		return this.entityManagerHolder;
	}

	/**
	 * @param entityManagerHolder
	 *            the entityManagerHolder to set
	 */
	public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
		this.entityManagerHolder = entityManagerHolder;
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

	/**
	 * @return the gson
	 */
	public Gson getGson() {
		return this.gson;
	}

	/**
	 * @param gson
	 *            the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * @return the fxFactory
	 */
	public I_FxFactory getFxFactory() {
		return this.fxFactory;
	}

	/**
	 * @param fxFactory
	 *            the fxFactory to set
	 */
	public void setFxFactory(I_FxFactory fxFactory) {
		this.fxFactory = fxFactory;
	}

	/**
	 * @return the documentTypeDao
	 */
	public I_DocumentTypeDao getDocumentTypeDao() {
		return this.documentTypeDao;
	}

	/**
	 * @param documentTypeDao
	 *            the documentTypeDao to set
	 */
	public void setDocumentTypeDao(I_DocumentTypeDao documentTypeDao) {
		this.documentTypeDao = documentTypeDao;
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