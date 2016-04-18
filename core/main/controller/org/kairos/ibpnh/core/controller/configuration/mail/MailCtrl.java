package org.kairos.ibpnh.core.controller.configuration.mail;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.controller.I_URIValidator;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.fx.configuration.mail.Fx_CreateMail;
import org.kairos.ibpnh.core.fx.configuration.mail.Fx_DeleteMail;
import org.kairos.ibpnh.core.fx.configuration.mail.Fx_ModifyMail;
import org.kairos.ibpnh.core.fx.configuration.mail.Fx_TriggerMail;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.configuration.mail.E_Repetition;
import org.kairos.ibpnh.core.services.mail.job.GenericMailSenderJob;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.vo.*;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.web.WebContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

/**
 * Controller for the Mail entities.
 *
 * @author Axel Collard Bovy
 * 
 */
@RequestMapping(value = "/mail", produces = "text/json;charset=utf-8", method = RequestMethod.POST)
public class MailCtrl implements I_URIValidator {

	/**
	 * List of excluded URIs
	 */
	private static final Set<String> EXCLUDED_URIS = new HashSet<String>(
			Arrays.asList(new String[] { "/mail/listRepetitions",
					"/mail/listFx", "/mail/testMail" }));

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(MailCtrl.class);

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Parameter DAO
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Mail DAO
	 */
	@Autowired
	private I_MailDao mailDao;

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
	 * List of Mail FXs.
	 */
	@Autowired
	private List<I_MailFx> mailFxs;

	/**
	 * The generic mail sender job (just for testing)
	 */
	@Autowired
	private GenericMailSenderJob mailSenderJob;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.controller.I_URIValidator#validate(java.lang.String)
	 */
	@Override
	public Boolean validate(String uri) {
		return !EXCLUDED_URIS.contains(uri);
	}

	/**
	 * List the repetitions enum values.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listRepetitions.json")
	public String listRepetitions() {
		this.logger.debug("calling MailCtrl.listRepetitions()");

		String data = this.getGson().toJson(E_Repetition.values());

		JsonResponse jsonResponse = JsonResponse.ok(data);

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * List the current implementors of the I_MailFx interface.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listFx.json")
	public String listFx() {
		this.logger.debug("calling MailCtrl.listFx()");

		// sorts the FXs
		Collections.sort(this.getMailFxs(), new Comparator<I_MailFx>() {

			@Override
			public int compare(I_MailFx mfx1, I_MailFx mfx2) {
				return mfx1.getClass().getName()
						.compareTo(mfx2.getClass().getName());
			}

		});

		// prepares the array
		JsonArray fxArray = new JsonArray();

		for (I_MailFx mailFx : this.getMailFxs()) {
			JsonArray parametersArray = new JsonArray();
			for (String parameter : mailFx.exposedParameters()) {
				parametersArray.add(new JsonPrimitive(parameter));
			}

			JsonObject mailFxJsonObject = new JsonObject();

			mailFxJsonObject.add("className", new JsonPrimitive(mailFx
					.getClass().getSimpleName()));
			mailFxJsonObject.add("classCanonicalName", new JsonPrimitive(mailFx
					.getClass().getCanonicalName()));
			mailFxJsonObject.add("exposedParameters", parametersArray);

			fxArray.add(mailFxJsonObject);
		}

		String data = this.getGson().toJson(fxArray);

		JsonResponse jsonResponse = JsonResponse.ok(data);

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Lists all mails.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public String list(@RequestBody String paginationData) {
		this.logger.debug("calling MailCtrl.list()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse;

		try {
			PaginatedRequestVo paginatedRequestVo = this.getGson().fromJson(paginationData, PaginatedRequestVo.class);
			PaginatedListVo<MailVo> paginatedListVo = this.getMailDao()
					.listPage(
							em,
							paginatedRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class),
							new OrderVo(new OrderItemVo("name", Boolean.TRUE)));

			String data = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(data);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = JsonResponse.error(this.getWebContextHolder()
					.errorMessage(ErrorCodes.ERROR_PARAMETER_PARSING));
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Searches mails.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search.json")
	public String search(@RequestBody String data) {
		this.logger.debug("calling MailCtrl.search()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse;

		try {
			Type type = new TypeToken<PaginatedSearchRequestVo<MailVo>>() {}.getType();
			PaginatedSearchRequestVo<MailVo> paginatedSearchRequestVo = this.getGson().fromJson(data, type);
			PaginatedListVo<MailVo> paginatedListVo = this.getMailDao()
					.searchPage(
							em,
							paginatedSearchRequestVo,
							this.getParameterDao()
									.getByName(em, ParameterVo.ITEMS_PER_PAGE)
									.getValue(Long.class),
							new OrderVo(new OrderItemVo("name", Boolean.TRUE)));

			String responseData = this.getGson().toJson(paginatedListVo);

			jsonResponse = JsonResponse.ok(responseData);
		} catch (ParseException e) {
			this.logger.error("error trying to read items.per.page parameter",
					e);
			jsonResponse = JsonResponse.error(this.getWebContextHolder()
					.errorMessage(ErrorCodes.ERROR_PARAMETER_PARSING));
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Creates a new mail
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create.json")
	public String create(@RequestBody String data) {
		this.logger.debug("calling MailCtrl.create()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;
		
		try {
			MailVo mailVo = this.getGson().fromJson(data, MailVo.class);
	
			Fx_CreateMail fx = this.getFxFactory().getNewFxInstance(
					Fx_CreateMail.class);
	
			fx.setVo(mailVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_CreateMail");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Deletes a mail
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public String delete(@RequestBody String data) {
		this.logger.debug("calling MailCtrl.delete()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			MailVo mailVo = this.getGson().fromJson(data, MailVo.class);
	
			Fx_DeleteMail fx = this.getFxFactory().getNewFxInstance(
					Fx_DeleteMail.class);
	
			fx.setVo(mailVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_DeleteMail");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Triggers a mail
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/trigger.json")
	public String trigger(@RequestBody String data) {
		this.logger.debug("calling MailCtrl.trigger()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			MailVo mailVo = this.getGson().fromJson(data, MailVo.class);
	
			Fx_TriggerMail fx = this.getFxFactory().getNewFxInstance(
					Fx_TriggerMail.class);
	
			fx.setVo(mailVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_TriggerMail");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Modifies a mail
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify.json")
	public String modifiy(@RequestBody String data) {
		this.logger.debug("calling MailCtrl.modifiy()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		JsonResponse jsonResponse = null;

		try {
			MailVo mailVo = this.getGson().fromJson(data, MailVo.class);
	
			Fx_ModifyMail fx = this.getFxFactory().getNewFxInstance(
					Fx_ModifyMail.class);
	
			fx.setVo(mailVo);
			fx.setEm(em);
			this.logger.debug("executing Fx_ModifyMail");
			jsonResponse = fx.execute();
		} catch (Exception e) {
			this.logger.debug("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_UNEXPECTED);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}

		return this.getGson().toJson(jsonResponse);
	}

	/**
	 * Testes a mail (without sending it)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/testMail.json")
	public String testMail(@RequestBody String data) {
		this.logger.debug("calling testMail.testMail()");

		MailVo mailVo = this.getGson().fromJson(data, MailVo.class);

		return this.getGson().toJson(
				JsonResponse.ok(this.getGson().toJson(
						this.getMailSenderJob().testAndGetFields(mailVo))));
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
	 * @return the mailDao
	 */
	public I_MailDao getMailDao() {
		return this.mailDao;
	}

	/**
	 * @param mailDao
	 *            the mailDao to set
	 */
	public void setMailDao(I_MailDao mailDao) {
		this.mailDao = mailDao;
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

	/**
	 * @return the mailFxs
	 */
	public List<I_MailFx> getMailFxs() {
		return this.mailFxs;
	}

	/**
	 * @param mailFxs
	 *            the mailFxs to set
	 */
	public void setMailFxs(List<I_MailFx> mailFxs) {
		this.mailFxs = mailFxs;
	}

	/**
	 * @return the mailSenderJob
	 */
	public GenericMailSenderJob getMailSenderJob() {
		return this.mailSenderJob;
	}

	/**
	 * @param mailSenderJob
	 *            the mailSenderJob to set
	 */
	public void setMailSenderJob(GenericMailSenderJob mailSenderJob) {
		this.mailSenderJob = mailSenderJob;
	}

}