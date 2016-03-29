package org.ibpnh.core.dao.person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ibpnh.core.dao.AbstractDao;
import org.ibpnh.core.model.person.Person;
import org.ibpnh.core.model.person.Person_;
import org.ibpnh.core.vo.person.PersonVo;

/**
 * Dao for the Person's entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class PersonDaoImpl extends AbstractDao<Person, PersonVo> implements
		I_PersonDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<Person> getClazz() {
		return Person.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<PersonVo> getVoClazz() {
		return PersonVo.class;
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
	protected Predicate addFilters(Root<Person> root, CriteriaBuilder builder,
			Predicate filters, PersonVo vo) {

		if (StringUtils.isNotBlank(vo.getName())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Person_.name)), ("%"
							+ vo.getName() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getSurname())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Person_.surname)), ("%"
							+ vo.getSurname() + "%").toLowerCase()));
		}

		if (vo.getDocumentType() != null) {
			filters = builder.and(
					filters,
					builder.equal(root.get(Person_.documentType),
							vo.getDocumentType()));
		}

		if (StringUtils.isNotBlank(vo.getDocumentNumber())) {
			filters = builder.and(filters, builder.like(
					builder.lower(root.get(Person_.documentNumber)),
					("%" + vo.getDocumentNumber() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getEmail())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Person_.email)), ("%"
							+ vo.getEmail() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getCellphoneNumber())) {
			filters = builder.and(filters, builder.like(
					builder.lower(root.get(Person_.cellphoneNumber)),
					("%" + vo.getCellphoneNumber() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getPhoneNumber())) {
			filters = builder.and(filters, builder.like(
					builder.lower(root.get(Person_.phoneNumber)),
					("%" + vo.getPhoneNumber() + "%").toLowerCase()));
		}

		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_PersonDao#checkEmailUniqueness(javax.
	 * persistence.EntityManager, java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean checkEmailUniqueness(EntityManager em, String email,
			Long excludeId) {
		this.logger.debug("searching person by email: {}, id !=: {}", email,
				excludeId);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by name
		filters = builder.and(filters, builder.like(
				builder.lower(root.get(Person_.email).as(String.class)),
				email.toLowerCase()));

		if (excludeId != null) {
			// filters for ID different than the excluded ID
			filters = builder.and(filters, builder.notEqual(root
					.get(Person_.id).as(Long.class), excludeId));
		}

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(Person_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		// fetch the role types
		List<Person> persons = em.createQuery(query).getResultList();

		return persons.size() == 0;
	}
}
