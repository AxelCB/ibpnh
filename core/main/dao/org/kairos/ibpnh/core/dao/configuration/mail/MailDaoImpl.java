package org.kairos.ibpnh.core.dao.configuration.mail;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.kairos.ibpnh.core.dao.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.model.configuration.mail.Mail;
import org.kairos.ibpnh.core.model.configuration.mail.Mail_;
import org.kairos.ibpnh.core.services.mail.I_MailJobScheduler;
import org.kairos.ibpnh.core.utils.StringUtils;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * DAO for the Mail entities.
 * 
 * @author Axel collard Bovy
 * 
 */
public class MailDaoImpl extends AbstractDao<Mail, MailVo> implements I_MailDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(MailDaoImpl.class);

	/**
	 * Parameter DAO
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Mail Job Secheduler.
	 */
	@Autowired
	private I_MailJobScheduler mailJobScheduler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<Mail> getClazz() {
		return Mail.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<MailVo> getVoClazz() {
		return MailVo.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractDao#addFilters(javax.persistence.criteria
	 * .Root, javax.persistence.criteria.CriteriaBuilder,
	 * javax.persistence.criteria.Predicate, AbstractVo)
	 */
	@Override
	protected Predicate addFilters(Root<Mail> root, CriteriaBuilder builder,
			Predicate filters, MailVo vo) {

		if (vo.getBodyJavascript() != null) {
			filters = builder.and(
					filters,
					builder.equal(root.get(Mail_.bodyJavascript),
							vo.getBodyJavascript()));
		}

		if (StringUtils.isNotBlank(vo.getCcList())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.ccList)), ("%"
							+ vo.getCcList() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getCcoList())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.ccoList)), ("%"
							+ vo.getCcoList() + "%").toLowerCase()));
		}

		if (vo.getEnabled() != null) {
			filters = builder.and(filters,
					builder.equal(root.get(Mail_.enabled), vo.getEnabled()));
		}

		if (vo.getCron() != null) {
			filters = builder.and(filters,
					builder.equal(root.get(Mail_.cron), vo.getCron()));
		}

		if (StringUtils.isNotBlank(vo.getFooter())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.footer)), ("%"
							+ vo.getFooter() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getMailFxClass())) {
			filters = builder.and(filters, builder.like(
					builder.lower(root.get(Mail_.mailFxClass)),
					("%" + vo.getMailFxClass() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getName())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.name)),
							("%" + vo.getName() + "%").toLowerCase()));
		}

		if (vo.getRepetition() != null) {
			filters = builder.and(
					filters,
					builder.equal(root.get(Mail_.repetition),
							vo.getRepetition()));
		}

		if (StringUtils.isNotBlank(vo.getSender())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.sender)), ("%"
							+ vo.getSender() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getSubject())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.subject)), ("%"
							+ vo.getSubject() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getToList())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Mail_.toList)), ("%"
							+ vo.getToList() + "%").toLowerCase()));
		}

		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_MailDao#checkNameUniqueness
	 * (javax.persistence.EntityManager, java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean checkNameUniqueness(EntityManager em, String name,
			Long excludeId) {
		this.logger.debug("searching mails by name: {}, id !=: {}", name,
				excludeId);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Mail> query = builder.createQuery(Mail.class);
		Root<Mail> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by name
		filters = builder.and(filters, builder.like(
				builder.lower(root.get(Mail_.name).as(String.class)),
				name.toLowerCase()));

		if (excludeId != null) {
			// filters for ID different than the excluded ID
			filters = builder.and(filters, builder.notEqual(root.get(Mail_.id)
					.as(Long.class), excludeId));
		}

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(Mail_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		// fetch the role types
		List<Mail> mails = em.createQuery(query).getResultList();

		return mails.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_MailDao#compileField(javax
	 * .persistence.EntityManager, java.lang.String, java.util.Map)
	 */
	@Override
	public String compileField(EntityManager em, String field,
			Map<String, String> compileTimeParameters) {
		String template = field;

		List<String> referencedParametersList = StringUtils
				.referencedParametersList(template);

		List<String> referencedCreationTimeParameters = StringUtils
				.creationTimeParametersList(referencedParametersList);
		List<String> referencedCompileTimeParameters = StringUtils
				.compileTimeParametersList(referencedParametersList);

		template = StringUtils.compileText(em, this.getParameterDao(),
				template, compileTimeParameters,
				referencedCreationTimeParameters,
				referencedCompileTimeParameters);

		return template;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_MailDao#findByName(javax.
	 * persistence.EntityManager, java.lang.String)
	 */
	@Override
	public MailVo findByName(EntityManager em, String name) {
		this.logger.debug("searching mail by name: {}", name);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Mail> query = builder.createQuery(Mail.class);
		Root<Mail> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by name
		filters = builder.and(filters, builder.like(
				builder.lower(root.get(Mail_.name).as(String.class)),
				name.toLowerCase()));

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(Mail_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		try {
			// fetch the role types
			Mail mail = em.createQuery(query).getSingleResult();

			return this.map(mail);
		} catch (NoResultException nre) {
			this.logger.debug("no result");

			return null;
		}
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
	 * @return the mailJobScheduler
	 */
	public I_MailJobScheduler getMailJobScheduler() {
		return this.mailJobScheduler;
	}

	/**
	 * @param mailJobScheduler
	 *            the mailJobScheduler to set
	 */
	public void setMailJobScheduler(I_MailJobScheduler mailJobScheduler) {
		this.mailJobScheduler = mailJobScheduler;
	}

}
