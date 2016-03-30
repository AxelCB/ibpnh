package org.kairos.ibpnh.core.dao.person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.kairos.ibpnh.core.dao.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kairos.ibpnh.core.model.person.DocumentType;
import org.kairos.ibpnh.core.model.person.DocumentType_;
import org.kairos.ibpnh.core.utils.StringUtils;
import org.kairos.ibpnh.core.vo.person.DocumentTypeVo;

/**
 * Dao for the DocumentType's entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class DocumentTypeDaoImpl extends AbstractDao<DocumentType, DocumentTypeVo> implements
		I_DocumentTypeDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(DocumentTypeDaoImpl.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<DocumentType> getClazz() {
		return DocumentType.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<DocumentTypeVo> getVoClazz() {
		return DocumentTypeVo.class;
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
	protected Predicate addFilters(Root<DocumentType> root, CriteriaBuilder builder,
			Predicate filters, DocumentTypeVo vo) {

		if (StringUtils.isNotBlank(vo.getAcronym())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(DocumentType_.acronym)), ("%"
							+ vo.getAcronym() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getDescription())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(DocumentType_.description)), ("%"
							+ vo.getDescription()+ "%").toLowerCase()));
		}
		
		return filters;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kairos.ibpnh.core.dao.documentType.I_DocumentTypeDao#findByAcronym(javax.persistence.
	 * EntityManager, java.lang.String)
	 */
	@Override
	public DocumentTypeVo findByAcronym(EntityManager em, String acronym) {
		this.logger.debug("getting document type by acronym: {}", acronym);

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<DocumentType> query = builder.createQuery(this.getClazz());
		Root<DocumentType> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by acronym
		filters = builder.and(filters, builder.like(
				builder.lower(root.get(DocumentType_.acronym).as(String.class)),
				acronym.toLowerCase()));
		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(DocumentType_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		try {
			// fetch the document type
			DocumentType documentType= em.createQuery(query).getSingleResult();

			return this.map(documentType);
		} catch (NoResultException e) {
			// there was no document type with required acronym
			return null;
		}
	}

	@Override
	public Boolean checkAcronymUniqueness(EntityManager em, String acronym,
			Long excludeId) {
		this.logger.debug("searching Document Type by acronym: {}, id !=: {}", acronym,
				excludeId);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<DocumentType> query = builder.createQuery(DocumentType.class);
		Root<DocumentType> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by acronym
		filters = builder.and(filters, builder.like(
				builder.lower(root.get(DocumentType_.acronym).as(String.class)),
				acronym.toLowerCase()));

		if (excludeId != null) {
			// filters for ID different than the excluded ID
			filters = builder.and(filters, builder.notEqual(root.get(DocumentType_.id)
					.as(Long.class), excludeId));
		}

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(DocumentType_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		// fetch the document types
		List<DocumentType> documentTypes= em.createQuery(query).getResultList();

		return documentTypes.size() == 0;
	}
}
